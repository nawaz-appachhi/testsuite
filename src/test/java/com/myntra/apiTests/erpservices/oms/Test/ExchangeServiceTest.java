package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.ExchangeServiceDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.paymentplan.exception.ErrorResponse;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * Created by abhijit.pati on 23/03/16.
 */
public class ExchangeServiceTest extends BaseTest{


    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    static org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeServiceTest.class);
    SoftAssert sft;
	private Long vectorSellerID;
	private String supplyTypeOnHand = "ON_HAND";
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	String reason = "DNL";
	private String orderID;
	private OrderEntry orderEntry;
    static String username = OMSTCConstants.LoginAndPassword.ExchangeServiceLogin;
    static String password = OMSTCConstants.LoginAndPassword.ExchangeServicePassword;
	long delayedTime = 5000;
	private String orderReleaseId;
	private String secondOrderReleaseId;
	private int atpIMSInventory = 10000;
	private int atpIMSInventoryZero = 0;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35,23,37);", "myntra_oms");
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		End2EndHelper.sleep(delayedTime);
	}
	
	public enum ExchangeTyeNegative{
		MORE_QTY,
		NEGATIVE_QTY,
		ZERO_QTY,
		NO_INVENTORY
	}
	
	/**
	 * @param skuId
	 * @param warehouseId
	 * @param qty
	 * @param supplyType
	 * @param sellerId
	 */
	private void updateInventory(String skuId,int warehouseId,int qty,String supplyType,long sellerId){
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId+":"+warehouseId+":"+qty+":0:"+sellerId+":1"},supplyType);
    	imsServiceHelper.updateInventoryForSeller(new String[]{skuId+":"+warehouseId+":"+qty+":0:"+sellerId},supplyType);
    	
	}
	
	/**
	 * @param skuId
	 * @param quantity
	 * @param warehouseId
	 * @param pincode
	 * @return
	 * @throws ManagerException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InvalidSyntaxException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws SCMExceptions
	 */
	private String createOrderHelper(String skuId,String quantity,int warehouseId,String pincode,int availableQty,String supplyType, long sellerId) throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions{
		String[] skuID = {skuId+":"+quantity};
		updateInventory(skuId, warehouseId, availableQty, supplyType, sellerId);
    	orderID = end2EndHelper.createOrder(username, password,pincode, skuID, "", false, false, false, "", false);
    	omsServiceHelper.checkReleaseStatusForOrder(orderID, EnumSCM.WP);
    	return orderID;
	}
	
	/**
	 * @param orderID
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws ManagerException
	 */
	private void markWholeOrderDelivered(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException{
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
    	for(OrderReleaseEntry orderReleaseEntry:orderEntry.getOrderReleases()){
    		String orderReleaseId = ""+orderReleaseEntry.getId();
            omsServiceHelper.updateDateInRelease(orderReleaseId);
            omsServiceHelper.markReleaseDeliveredInOMS(orderReleaseId, username);
    	}
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"DL");
	}
	
	/**
	 * @param orderID
	 * @param lineID
	 * @param quantity
	 * @param exchangeSku
	 * @param reason
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	private ExchangeOrderResponse createExchangeSuccess(String orderID,String lineID,int quantity,String exchangeSku,String reason) throws JAXBException, IOException{
		sft = new SoftAssert();
		ExchangeOrderResponse exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, lineID, reason, quantity, exchangeSku);
        sft.assertTrue(exchangeOrderResponse.getSuccess(),"There should be success but Actual:"+exchangeOrderResponse.getSuccess());
        sft.assertNotNull(exchangeOrderResponse.getExchangeOrderId(),"Exchange order should not be null but Actual:"+exchangeOrderResponse.getExchangeOrderId());
        sft.assertNotEquals(exchangeOrderResponse.getExchangeOrderId(), orderID,"Exchange order should not be same as orderId");
        sft.assertAll();
        return exchangeOrderResponse;
	}
	
	/**
	 * @param orderID
	 * @param lineID
	 * @param quantity
	 * @param exchangeSku
	 * @param reason
	 * @param errorReason
	 * @param errorCode
	 * @throws JAXBException
	 * @throws IOException
	 */
	private void createExchangeFailure(String orderID,String lineID,int quantity,String exchangeSku,String reason,int errorCode,String errorReason) throws JAXBException, IOException{
		sft = new SoftAssert();
		ErrorResponse errorResponse =(ErrorResponse) ppsServiceHelper.createExchange(""+orderID, lineID, reason, quantity, exchangeSku);
        sft.assertEquals(errorResponse.getErrorReason(), errorReason,"Error reason should be EXCHANGE_ORDER_CALL_FAILED but Actual:"+errorResponse.getErrorReason());
        sft.assertEquals(errorResponse.getErrorCode().intValue(), errorCode,"ErrorCode code should be 2023 but Actual:"+errorResponse.getErrorCode().intValue());
        sft.assertAll();
	}
	
    @Test(enabled = true, groups={"smoke","ExchangeService","regression","sanity"}, description = "Create exchange for Single Item and Single quantity Order")
    public void createExchangeForSingleItemSingleQuantityOrder() throws Exception {
    	sft=new SoftAssert();
    	
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3831,"1",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,atpIMSInventory,supplyTypeOnHand,vectorSellerID);
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        markWholeOrderDelivered(orderID);
        
        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
        String lineID = ""+orderLineEntry.getId();
       
    	int quantity = orderLineEntry.getQuantity();
    	createExchangeSuccess(orderID, lineID, quantity, OMSTCConstants.OtherSkus.skuId_3831, reason);
        sft.assertAll();
    }
    
    @Test(enabled = true, groups={"regression","ExchangeService"}, description = "Create exchange for different quantity separately of Single Item and Multiple quantity Order")
    public void createExchangeForSingleItemMultipleQuantityOrder() throws Exception {
    	sft=new SoftAssert();
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3831,"2",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,atpIMSInventory,supplyTypeOnHand,vectorSellerID);
    	
    	markWholeOrderDelivered(orderID);
    	
    	//Create first exchange
    	orderReleaseId = ""+omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId();
    	secondOrderReleaseId = ""+omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(1).getId();
        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
        String lineID = ""+orderLineEntry.getId();
        int quantity = orderLineEntry.getQuantity();       
        ExchangeOrderResponse exchangeOrderResponse = createExchangeSuccess(orderID, lineID, quantity, OMSTCConstants.OtherSkus.skuId_3831, reason);
        
        String exchangePPSID1 = exchangeOrderResponse.getPpsId();
        sft.assertNotNull(exchangePPSID1,"PPSId should not be Null but Actual:"+exchangePPSID1);
        
        //Create exchange for second release
    	
        orderLineEntry = omsServiceHelper.getOrderLineEntries(secondOrderReleaseId).get(0);
        lineID = ""+orderLineEntry.getId();
        quantity = orderLineEntry.getQuantity();
        ExchangeOrderResponse exchangeOrderResponse2 =createExchangeSuccess(orderID, lineID, quantity, OMSTCConstants.OtherSkus.skuId_3831, reason);
        String exchangePPSID2 = exchangeOrderResponse2.getPpsId();
        sft.assertNotNull(exchangePPSID2,"PPSId should not be Null but Actual:"+exchangePPSID2);
        sft.assertNotEquals(exchangePPSID1,exchangePPSID2,"Both PPSId should not be same");
        sft.assertAll();
    }

    

    @Test(enabled = true, groups={"smoke","regression","exchangeService"},description = "1. Deliver Single Item 2 Quantity. \n2. Create Exchange for 2 Quantity.")
    public void createMultipleExchangeAnd2ndExchangeQuantityIsGreaterThanTheRemainingQuantity() throws Exception {
    	sft=new SoftAssert();
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3831,"2",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,atpIMSInventory,supplyTypeOnHand,vectorSellerID);
    	
    	markWholeOrderDelivered(orderID);
    	
    	//Create first exchange
    	orderReleaseId = ""+omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId();
    	secondOrderReleaseId = ""+omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(1).getId();
        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
        String lineID = ""+orderLineEntry.getId();
        int quantity = orderLineEntry.getQuantity();       
        ExchangeOrderResponse exchangeOrderResponse = createExchangeSuccess(orderID, lineID, quantity, OMSTCConstants.OtherSkus.skuId_3831, reason);
        
        String exchangePPSID1 = exchangeOrderResponse.getPpsId();
        sft.assertNotNull(exchangePPSID1,"PPSId should not be Null but Actual:"+exchangePPSID1);
        quantity = orderLineEntry.getQuantity() + 1;
        createExchangeFailure(""+orderID, lineID, quantity, OMSTCConstants.OtherSkus.skuId_3831,reason,2023,"EXCHANGE_ORDER_CALL_FAILED");
        sft.assertAll();
    }

    
    @Test(enabled = true, groups={"smoke","regression","exchangeService"}, dataProvider = "exchangeNegativeCasesDP", dataProviderClass = ExchangeServiceDP.class, description = "Create exchange negative Test cases")
    public void exchangeCreationNegativeFlows(int quantity,String skuId, String reason, int errorCode, String errorMessage,String type) throws Exception {
    	sft=new SoftAssert();
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3831,"1",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,atpIMSInventory,supplyTypeOnHand,vectorSellerID);
    	markWholeOrderDelivered(orderID);
    	orderReleaseId = ""+omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId();
        OrderLineEntry orderLineEntry = omsServiceHelper.getOrderLineEntries(orderReleaseId).get(0);
        String lineID = ""+orderLineEntry.getId();
        if(type.equalsIgnoreCase(ExchangeTyeNegative.NO_INVENTORY.name())){
        	updateInventory(skuId, OMSTCConstants.WareHouseIds.warehouseId36_BW,atpIMSInventoryZero,supplyTypeOnHand,vectorSellerID);
        }
        createExchangeFailure(""+orderID, lineID, quantity, skuId,reason,errorCode,errorMessage);        
        sft.assertAll();
    }

    //Should move to LMS
    @Test(enabled = false, groups={"smoke","regression","exchangeService"},dataProvider = "exchangeNegativeCasesDP", dataProviderClass = ExchangeServiceDP.class, description = "Create exchange negative Test cases")
    public void checkReturnShouldBeCreateOnLMSExchangePickup(){

    }

    //Should move to RMS
    @Test(enabled = false, groups={"smoke","regression","exchangeService"},dataProvider = "exchangeNegativeCasesDP", dataProviderClass = ExchangeServiceDP.class, description = "1. Create Exchange for an Item and Return the exchanged Item. 2. Verify the refund should be triggered")
    public void returnAnExchangedItem(){

    }


    @Test(enabled = false, groups={"smoke","regression","exchangeService"},dataProvider = "exchangeNegativeCasesDP", dataProviderClass = ExchangeServiceDP.class, description = "User can able to exchange again after cancellation of Exchanged product")
    public void cancelExchangeAndAgainCreateExchange(){

    }

}
