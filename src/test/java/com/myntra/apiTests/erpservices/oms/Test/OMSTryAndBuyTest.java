package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.worms.client.entry.OrderCaptureReleaseEntry;

import argo.saj.InvalidSyntaxException;

public class OMSTryAndBuyTest extends BaseTest {

    static Initialize init = new Initialize("/Data/configuration");
    private String login = OMSTCConstants.LoginAndPassword.OMSTryAndBuyTestLogin;
    private String password = OMSTCConstants.LoginAndPassword.OMSTryAndBuyTestPassword;
    private static Logger log = Logger.getLogger(OMSTryAndBuyTest.class);
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private static Long vectorSellerID;
	private String supplyTypeOnHand = "ON_HAND";
	private SoftAssert sft;
	private String orderID;
	private int atpInventory = 1000;
	private int imsInventory = 1000;
	private int onholdReason34 = 34;
	private WMSServiceHelper wmsservicehelper = new WMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();


    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
    	vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
    }


    

    @Test(enabled = true, groups = {"regression", "tryandbuy"}, description = "verify order is placed verify the tryandBuy flag is propagated to wms.")
    public void VerifyIfOrderIsPlacedTryAndBuyFlagShouldPassedOmsToWms() throws Exception {
    	sft = new SoftAssert();
        imsInventory = 0;
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3832,"1",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID);
    	orderReleaseId = waitForOrderReleaseToMoveONhold(orderID,onholdReason34,5);
        
    	updateLineAdditionalAsTryNBuy(orderReleaseId,Boolean.TRUE);
		
		//Increase Inventory and resolve onhold
    	imsInventory = 100;
    	resolveOnHoldVerifyRelaseStatus(orderID,orderReleaseId,OMSTCConstants.OtherSkus.skuId_3832, OMSTCConstants.WareHouseIds.warehouseId36_BW);		
		//
        OrderCaptureReleaseEntry orderCaptureReleaseEntry = wmsservicehelper .getCaptureOrderReleaseData(orderReleaseId);
        log.info("Descrption in WMS:"+orderCaptureReleaseEntry.getDescription());
        sft.assertTrue(orderCaptureReleaseEntry.getDescription().contains(EnumSCM.TRY_BUY),":TryAndBuy Description value should be TRY_BUY.");

        sft =new SoftAssert();
    }


	@Test(enabled = true, groups = {"regression", "tryandbuy"}, description = "Verify order is placed using TryAndBuy, TnB flag should passed from Oms To Lms After order status is PK.")
    public void VerifyIfOrderIsPlacedTryAndBuyFlagShouldPassedOmsToLmsAfterPK() throws Exception {
    	sft = new SoftAssert();
        imsInventory=0;
        
    	orderID = createOrderHelper(OMSTCConstants.OtherSkus.skuId_3832,"1",OMSTCConstants.WareHouseIds.warehouseId36_BW,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID);
    	orderReleaseId = waitForOrderReleaseToMoveONhold(orderID,onholdReason34,5);
        
    	updateLineAdditionalAsTryNBuy(orderReleaseId,Boolean.TRUE);
		
		//Increase Inventory and resolve onhold
    	imsInventory = 100;
    	resolveOnHoldVerifyRelaseStatus(orderID,orderReleaseId,OMSTCConstants.OtherSkus.skuId_3832, OMSTCConstants.WareHouseIds.warehouseId36_BW);		

		//Mark order as packed and check TryNBuy Flag in LMS
		omsServiceHelper.updateDateInRelease(orderReleaseId);
		omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(orderReleaseId,ReadyToDispatchType.POSITIVE.name());
		omsServiceHelper.checkReleaseStatusForOrder(orderID, EnumSCM.PK);
		
		//verify TryAndBuyFlagIn LMS
		String packetId = omsServiceHelper.getPacketIdFromReleasId(orderReleaseId);
		@SuppressWarnings("unchecked")
		com.myntra.lms.client.response.OrderEntry orderEntryInLMS = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(packetId)).getOrders().get(0);

		sft.assertEquals(orderEntryInLMS.getShipmentType().name(), EnumSCM.TRY_AND_BUY);
        sft =new SoftAssert();

    }
	
	/**
	 * @param skuId
	 * @param warehouseId
	 * @param qty
	 * @param supplyType
	 * @param sellerId
	 */
	private void updateInventory(String skuId,int warehouseId,String supplyType,long sellerId){
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{skuId+":"+warehouseId+":"+atpInventory+":0:"+sellerId+":1"},supplyType);
    	imsServiceHelper.updateInventoryForSeller(new String[]{skuId+":"+warehouseId+":"+imsInventory+":0:"+sellerId},supplyType);
    	
	}
	
	
	/**
	 * @param skuId
	 * @param quantity
	 * @param warehouseId
	 * @param pincode
	 * @param availableQty
	 * @param supplyType
	 * @param sellerId
	 * @return
	 * @throws ManagerException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InvalidSyntaxException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws SCMExceptions
	 */
	private String createOrderHelper(String skuId,String quantity,int warehouseId,String pincode,String supplyType,long sellerId) throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions{
		String[] skuID = {skuId+":"+quantity};
		updateInventory(skuId, warehouseId,supplyType,sellerId);
    	orderID = end2EndHelper.createOrder(login, password,pincode, skuID, "", false, false, false, "", false);
    	return orderID;
	}
	
	/**
	 * @param orderID
	 * @param onholdReasonId
	 * @param timeToDelay
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ManagerException
	 * @throws JAXBException
	 */
	public String waitForOrderReleaseToMoveONhold(String orderID,int onholdReasonId,int timeToDelay) throws UnsupportedEncodingException, ManagerException, JAXBException{
		sft = new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,EnumSCM.Q);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();       
        sft.assertTrue(omsServiceHelper.verifyOrderIsInOnHoldWithReasonId(orderReleaseId,onholdReasonId,timeToDelay),"Release is not in Onhold status");
        sft.assertAll();
        return orderReleaseId;
	}
	
	/**
	 * @param orderReleaseId
	 * @param isTryAndBuy
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void updateLineAdditionalAsTryNBuy(String orderReleaseId,Boolean isTryAndBuy) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		String lineId = ""+omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId();
		omsServiceHelper.updateOrderLineAdditionInfo(EnumSCM.TRY_AND_BUY, isTryAndBuy.toString(), lineId);
		sft.assertEquals(omsServiceHelper.getOrderLineEntry(lineId).getTryAndBuy(),isTryAndBuy,"TryAndBuy is updated at Line level");
		sft.assertAll();
	}
	

    /**
     * @param orderID
     * @param orderRelaseId
     * @param skuId
     * @param warehouseId
     * @throws UnsupportedEncodingException
     * @throws ManagerException
     * @throws JAXBException
     */
    private void resolveOnHoldVerifyRelaseStatus(String orderID,String orderRelaseId,String skuId,long warehouseId) throws UnsupportedEncodingException, ManagerException, JAXBException {
		// TODO Auto-generated method stub
		imsServiceHelper.updateInventoryForSeller(new String[] {skuId+":"+warehouseId+":"+imsInventory+":0:"+vectorSellerID},supplyTypeOnHand);
		omsServiceHelper.resolveOnHoldOrderRelease(orderRelaseId);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,EnumSCM.WP);
	}
    

}
      