package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.ExpressRefundDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.PacketResponse;
import com.myntra.oms.common.enums.ShippingMethod;
import com.myntra.paymentplan.domain.response.PaymentPlanResponse;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpressRefundServiceTest extends BaseTest {

	static String orderID;
    static Initialize init = new Initialize("/Data/configuration");
    private static Logger log = Logger.getLogger(ExpressRefundServiceTest.class);
	String login = OMSTCConstants.LoginAndPassword.ExpressRefundServiceTestLogin;
	String password = OMSTCConstants.LoginAndPassword.ExpressRefundServiceTestPassword;
	String uidx ;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	ProcessRelease processRelease = new ProcessRelease();
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderReleaseID;
	DecimalFormat twoDForm = new DecimalFormat("#.00");
	private static Long vectorSellerID;
	SoftAssert sft;
	private int atpIMSInventory = 1000;
	private String supplyTypeOnHand="ON_HAND";
	private int delaytoCheck=5000;
	private PacketResponse packetResponse;
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private PaymentPlanResponse response;
	private int sellerId_HEAL = 21;


    @BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws JAXBException, SQLException, IOException {
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
	}
    

	@Test(enabled = true, description = "Express Refund For an Already refunded Order",groups={"regression","expressRefund"})
	public void expressRefundForAnExpressOrderCODSingleQty() throws Exception {
		sft=new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);

    	orderReleaseID = ""+orderReleaseEntries.get(0).getId();
    	orderID  = omsServiceHelper.getOrderIdFromReleaseId(orderReleaseID);
    	
    	markReleaseDelivered(orderID);
    	omsServiceHelper.updateShippingMethodForOrder(orderReleaseEntries,ShippingMethod.XPRESS.name());
    	String packetId = omsServiceHelper.getPacketIdFromReleasId(orderReleaseID);
    	expressRefundSuccess(packetId,1008);
    	validateExpressRefundId(orderID);
		sft.assertAll();
	}
	
	@Test(enabled = true, description = "Express refund For an Express Order which include the payment method as COD",groups={"regression","expressRefund"})
	public void expressRefundOfAlreadyRefundedOrder() throws Exception {
		sft=new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);

    	orderReleaseID = ""+orderReleaseEntries.get(0).getId();
    	orderID  = omsServiceHelper.getOrderIdFromReleaseId(orderReleaseID);
    	
    	markReleaseDelivered(orderID);
    	omsServiceHelper.updateShippingMethodForOrder(orderReleaseEntries,ShippingMethod.XPRESS.name());
    	//First time express refund
    	String packetId = omsServiceHelper.getPacketIdFromReleasId(orderReleaseID);
    	expressRefundSuccess(packetId,1008);
    	//Old express refund PPSID
		String expressRefundPPSIdOld = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_PPS_ID).get("value");
		String expressRefundRefIdOld = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_TX_REF_ID).get("value");
    	validateExpressRefundId(orderID);
    	
    	//Do Refund Again
    	expressRefundSuccess(packetId,1008);
    	//New express refund PPSID
		String expressRefundPPSIdNew = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_PPS_ID).get("value");
		String expressRefundRefIdNew = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_TX_REF_ID).get("value");
    	validateExpressRefundId(orderID);
    	//Verify Old and new Refund PPSIds are same
    	sft.assertEquals(expressRefundPPSIdOld, expressRefundPPSIdNew,"There should not be double refund, ppsid should be same");
    	sft.assertEquals(expressRefundRefIdOld, expressRefundRefIdNew,"There should not be double refund, refid should be same");
		sft.assertAll();
	}

	@Test(enabled = true, description = "Express Refund For a normal Order Which Do Not Have Shipping Charges",groups={"regression","expressRefund"})
	public void expressRefundForNormalOrderWhichDoNotHaveShippingCharges() throws Exception {
		sft=new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);

    	orderReleaseID = ""+orderReleaseEntries.get(0).getId();
    	orderID  = omsServiceHelper.getOrderIdFromReleaseId(orderReleaseID);
    	
    	markReleaseDelivered(orderID);
    	omsServiceHelper.updateShippingMethodForOrder(orderReleaseEntries,ShippingMethod.XPRESS.name());
    	for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
    		String packetId = omsServiceHelper.getPacketIdFromReleasId(orderReleaseEntry.getId().toString());
    		log.info("PacketId:"+packetId);
    		expressRefundSuccess(packetId,1008);
    	}

    	validateExpressRefundIdNotGenerated(orderID);
		sft.assertAll();
	}

	@Test(enabled = true, priority = 6, description = "Express Refund For An Packet Not Exsists",groups={"regression","expressRefund"})
	public void expressRefundForAnPacketNotExist() throws Exception {
		sft=new SoftAssert();
		
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);

    	orderReleaseID = ""+orderReleaseEntries.get(0).getId();
    	orderID  = omsServiceHelper.getOrderIdFromReleaseId(orderReleaseID);
    	
    	markReleaseDelivered(orderID);
    	omsServiceHelper.updateShippingMethodForOrder(orderReleaseEntries,ShippingMethod.XPRESS.name());
    	String packetId = omsServiceHelper.getPacketIdFromReleasId(orderReleaseID);
    	expressRefundError(packetId+"1234",8312); //Adding some random id to make packet non existence in system
		sft.assertAll();
	}
	
	
	/**
	 * This helper is to create Order
	 * @param skuID
	 * @param pincode
	 * @param supplyType
	 * @param sellerId
	 * @param expectedStatus
	 * @return
	 * @throws ManagerException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InvalidSyntaxException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws SCMExceptions
	 */
	private List<OrderReleaseEntry> createOrderHelper(String[] skuID,String pincode,String supplyType, long sellerId,String expectedStatus) throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions{

    	orderID = end2EndHelper.createOrder(login, password,pincode, skuID, "", false, false, false, "", false);
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,expectedStatus);
    	return omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
	}
	
	/**
	 * @param orderID
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void markReleaseDelivered(String orderID) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
        orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
        for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
        	orderReleaseID = ""+orderReleaseEntry.getId();
        	omsServiceHelper.updateDateInRelease(orderReleaseID);
            omsServiceHelper.markReleaseDeliveredInOMS(orderReleaseID, login);
            sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, EnumSCM.DL, delaytoCheck), "Release "+orderReleaseID+"is not in "+EnumSCM.DL);
        }
        sft.assertAll();
		
	}
	
	/**
	 * @param packetID
	 * @param statusCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void expressRefundSuccess(String packetID,int statusCode) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		packetResponse = omsServiceHelper.expressRefundForPacket(packetID);
		sft.assertEquals(packetResponse.getStatus().getStatusType(), Type.SUCCESS,"Verify statusType in packetResponse");
		sft.assertEquals(packetResponse.getStatus().getStatusCode(), statusCode,"Verify statusCode in packetResponse");
		sft.assertAll();
	}
	

	/**
	 * @param packetID
	 * @param statusCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void expressRefundError(String packetID, int statusCode) throws UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		packetResponse = omsServiceHelper.expressRefundForPacket(packetID);
		sft.assertEquals(packetResponse.getStatus().getStatusType(), Type.ERROR,"Verify statusType in packetResponse");
		sft.assertEquals(packetResponse.getStatus().getStatusCode(), statusCode,"Verify statusCode in packetResponse");
		sft.assertAll();
		
	}
	
	/**
	 * @param orderId
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JSONException
	 * @throws XMLStreamException
	 */
	public void validateExpressRefundId(String orderId) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, JAXBException, IOException, JSONException, XMLStreamException{
		String expressRefundPPSId = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_PPS_ID).get("value");
		String expressRefundRefId = ""+omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_TX_REF_ID).get("value");
		sft = new SoftAssert();
		sft.assertNotNull(expressRefundPPSId, "EXPRESS_REFUND_PPS_ID should not be null");
		sft.assertNotNull(expressRefundRefId, "EXPRESS_REFUND_TX_REF_ID should not be null");
		sft.assertEquals(totalShippingChargeOfOrder(orderId), getExpressRefundAmount(expressRefundPPSId),"Express Refund amount should be same");
		sft.assertAll();
	}
	
	/**
	 * @param orderId
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JSONException
	 * @throws XMLStreamException
	 */
	public void validateExpressRefundIdNotGenerated(String orderId) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, JAXBException, IOException, JSONException, XMLStreamException{
		HashMap<String,Object> expressRefundPPSId = omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_PPS_ID);
		HashMap<String,Object> expressRefundRefId = omsServiceHelper.getOrderAdditionalInfoDBEntry(orderID, EnumSCM.EXPRESS_REFUND_TX_REF_ID);
		sft = new SoftAssert();
		sft.assertNull(expressRefundPPSId, "EXPRESS_REFUND_PPS_ID should not be null");
		sft.assertNull(expressRefundRefId, "EXPRESS_REFUND_TX_REF_ID should not be null");
		sft.assertAll();
	}
	
	/**
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public Long totalShippingChargeOfOrder(String orderId) throws UnsupportedEncodingException, JAXBException{
		Double shippingAmount =0.0;
		for(OrderReleaseEntry orderReleaseEntry:omsServiceHelper.getOrderEntry(orderId).getOrderReleases()){
			shippingAmount += orderReleaseEntry.getShippingCharge();
		}
		return (long)(shippingAmount*100);
	}
	
	/**
	 * @param ppsId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JSONException
	 * @throws XMLStreamException
	 */
	public Long getExpressRefundAmount(String ppsId) throws JsonParseException, JsonMappingException, JAXBException, IOException, JSONException, XMLStreamException{
		
		response = ppsServiceHelper.getRefundAmountFromPaymentPlan(ppsId);
		Long expressRefund = response.getPaymentPlanEntry().getPaymentPlanInstrumentDetails().get(0).getTotalPrice();
		log.info("Amount Refunded After cancellation: "+expressRefund);
		return expressRefund;
		
	}




}
