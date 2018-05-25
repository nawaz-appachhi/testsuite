package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.paymentplan.domain.response.PaymentPlanResponse;
import com.myntra.test.commons.testbase.BaseTest;


public class AdditionalChargesSellerCancellationTests extends BaseTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	String login = OMSTCConstants.LoginAndPassword.AddidionalChargeLogin;
	String uidx;
	String password = OMSTCConstants.LoginAndPassword.AddidionalChargePassword;
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(OMSServiceHelper.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	private String orderID;
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private HashMap<String, Object> xCartOrderEntry;
	private long additionalChargeId;
	private String additionalChargesSellerID;
	private OrderReleaseEntry orderReleaseEntry;
	private Double additionalChargeOnRelease;
	private double additionalChargesSellerIdDB;
	private Double additionalGiftCharge;
	private OrderReleaseResponse cancellationRes;
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private PaymentPlanResponse response;
	
	/*//Fox8 sellerIds
	private int sellerId_HEAL = 21;
	private int sellerId_CONS = 32;
	private int sellerId_HEAL_JIT = 21;
	private int sellerId_CONS_JIT = 4;*/
	
	//Fox7 SellerIds
	private int sellerId_HEAL = 21;
	private int sellerId_CONS = 25;
	private int sellerId_HEAL_JIT = 21;
	private int sellerId_CONS_JIT = 25;
	
	private String orderReleaseId2;
	private String supplyTypeOnHand = "ON_HAND";
	private String supplyTypeJIT = "JUST_IN_TIME";
	private Double giftCharges = 0.0;
	private Double zeroCharges = 0.0;
	private Double shippingCharges = 149.0;
	SoftAssert sft;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		omsServiceHelper.updateGiftWrapCharges(25);
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
        
	}
	
	public long getRefundAmountAfterCancellation(String ppsId) throws JsonParseException, JsonMappingException, JAXBException, IOException, JSONException, XMLStreamException{
		
		response = ppsServiceHelper.getRefundAmountFromPaymentPlan(ppsId);
		Long amountRefunded = response.getPaymentPlanEntry().getPaymentPlanInstrumentDetails().get(0).getTotalPrice();
		log.info("Amount Refunded After cancellation: "+amountRefunded);
		return amountRefunded;
		
	}
	
	/**
	 * @param lineId
	 * @return
	 * This is to get PPS Id for order Line cancellation
	 * @throws InterruptedException 
	 */
	public String getLineCancellationPPsId(String lineId){
		HashMap<String,Object> hash = (HashMap<String, Object>) omsServiceHelper.getOrderLineAdditionalInfoDBEntry
				(""+lineId, "LINE_CANCELLATION_PPS_ID");
		
		return (String) hash.get("value");
	}
	
	/**
	 * @param orderId
	 * @return
	 * This is to get PPS Id for order cancellation
	 */
	public String getOrderCancellationPPsId(String orderId){
		HashMap<String,Object> hash = (HashMap<String, Object>) omsServiceHelper.getOrderAdditionalInfoDBEntry
				(orderId, "ORDER_CANCELLATION_PPS_ID");
		return (String) hash.get("value");
	}
	
	/**
	 * @param orderReleaseId
	 * @return
	 * This is to get PPS Id for order Release cancellation
	 */
	public String getOrderReleaseCancellationPPsId(String orderReleaseId){
		HashMap<String,Object> hash = (HashMap<String, Object>) omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry
				(orderReleaseId, "RELEASE_CANCELLATION_PPS_ID");
		return (String) hash.get("value");
	}
	
	public void verifyFinalAmountIsCorrectForAllReleases(OrderEntry orderEntry){
		List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
		
		for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
			Double finalAmount = orderReleaseEntry.getFinalAmount();
			Double finalAmountCalculated = orderReleaseEntry.getMrpTotal() + 
					orderReleaseEntry.getShippingCharge() + orderReleaseEntry.getGiftCharge() - orderReleaseEntry.getDiscount();
			sft.assertEquals(finalAmount, finalAmountCalculated,"Final amount is mismatched for releaseId: "+orderReleaseEntry.getId());
		}
	}
	
	//Passed on delta 7 - Sneha
	@Test(enabled = true,groups={"regression","sanity","additionalSellerCharge"}, description = "TC001:Place a order with additional charges and multiple releases with multiple sellers")
	public void orderWithOneAdditionalCharges() throws Exception {
		/*//Fox8 Skus Mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_749877+":2",OMSTCConstants.OtherSkus.skuId108+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_749877+":2",OMSTCConstants.OtherSkus.skuId_47584+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS,OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL},supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        verifyFinalAmountIsCorrectForAllReleases(orderEntry);
        
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderReleaseAdditionalInfo(orderEntry,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+shippingCharges+" and "+giftCharges);
        sft.assertAll();
               
	}

	
	//Passed working on d7-Sneha
		//This testcase is disabled unless online orders are working.
	@Test(enabled = false,groups={"regression","additionalSellerCharge"}, description = "TC002:Place a order with multiple additional charges and multiple releases")
	public void orderWithMultipleAdditionalCharges() throws Exception {
		/*Fox8 Skus
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3835:sellerId_HEAL  3836:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_30055+":2",OMSTCConstants.OtherSkus.skuId_30056+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_30055+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_30056+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_30055+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_30056+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS},supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, true, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderReleaseAdditionalInfo(orderEntry,sellerId_HEAL);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        Double shippingCharges = orderReleaseEntry.getShippingCharge();
        sft.assertTrue(shippingCharges > 0,"Shipping Charges not applied for order");
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+shippingCharges+" and "+giftCharges);
        sft.assertAll();
        
        
	}
	
	//Passed on delta 7-sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC003:Release cancellation when there are other releases for the additionalchargeSeller")
	public void releaseCancellationWhenOtherReleasesForAdditionalChargeAvailable() throws Exception {
		/* fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_749877+":2",OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS},supplyTypeOnHand);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

        List<String> activeReleasesOfParticularSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesOfParticularSeller,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesOfParticularSeller,shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+shippingCharges+" and "+giftCharges);
		Thread.sleep(5000L);
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        End2EndHelper.sleep(5000);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        
        //Verify charges are not on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,zeroCharges,zeroCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
        
        //Verify charges moved to next active release
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesOfParticularSeller,shippingCharges,giftCharges,activeReleaseWithAdditionalCharge),
        		"Additional charges are still on old release "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        /*//Verify Refund happened for Release order amount fox8
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "20400",
				"Additional charges should not be refunded");*/
        //Verify Refund happened for Release order amount fox7
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge,zeroCharges),
				"Additional charges should not be refunded");
        sft.assertAll();
        
	}
	
	//Failing on delta7
		@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC008:Release cancellation when there are other releases for the additionalchargeSeller")
		public void releaseCancellationWhenOtherReleasesForNoAdditionalChargeAvailable() throws Exception {
			/*fox8 sku mapping
			String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
			imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
					OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
					OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
			
			//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
			sft=new SoftAssert();
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_749877+":2",OMSTCConstants.OtherSkus.skuId_3831+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
			imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
					OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
					OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
			
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
			verifySellerIdForOrderInBounty(orderID,-1);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
			//orderID = 79913542L;

	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        List<String> activeReleasesOfHealSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
	        List<String> activeReleasesOfConsSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS);
	        
	        //verify there is no additional charges
	        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
	        		"Additional charges should not be available");
	        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfConsSeller,shippingCharges,zeroCharges),
	        		"Additional charges should not be available");
			Thread.sleep(5000L);
			orderReleaseId = activeReleasesOfHealSeller.get(0);
	        //cancel Additional Charge Release
	        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
	        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
	        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
	        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
	        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
	        //verify there is no additional charges
	        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
	        		"Additional charges should not be available "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
	        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfConsSeller,shippingCharges,zeroCharges),
	        		"Additional charges should not be available "+sellerId_CONS+" Expected:"+zeroCharges+" and "+zeroCharges);
	        
	        /*//Verify Refund happened for Release order amount fox8
	        sft.assertEquals(""+getRefundAmountAfterCancellation
					(getOrderReleaseCancellationPPsId(orderReleaseId)), "159800",
					"Additional charges should not be refunded");*/
	        
	        //Verify Refund happened for Release order amount fox7
	        sft.assertEquals(""+getRefundAmountAfterCancellation
					(getOrderReleaseCancellationPPsId(orderReleaseId)), getRefundedAmountForCancelledRelease(orderReleaseId,zeroCharges),
					"Additional charges should not be refunded");
	        sft.assertAll();
	        
		}
		
		//Passed on delta7-Sneha
		@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC009:Release cancellation when there are other releases for the additionalchargeSeller but not in active state (marked as DL)")
		public void releaseCancellationWhenOtherReleasesIsNotActiveForNoAdditionalChargeAvailable() throws Exception {
			/*fox8 sku mapping		
			String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
					atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
					imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
							OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
							OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
					
					//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
					sft=new SoftAssert();
					String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
					atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
					imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
							OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
							OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
					
					orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
					verifySellerIdForOrderInBounty(orderID,-1);
			        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
					//orderID = 79913542L;

			        orderEntry = omsServiceHelper.getOrderEntry(orderID);
			        List<String> activeReleasesOfHealSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
			        List<String> activeReleasesOfConsSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS);
			        
			        orderReleaseId = activeReleasesOfHealSeller.get(0);
			        updateReleaseStatusForOtherActiveRelease(activeReleasesOfHealSeller, "PK", orderReleaseId);
			        updateReleaseStatusForOtherActiveRelease(activeReleasesOfConsSeller, "PK", orderReleaseId);

			        //verify there is no additional charges
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
			        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfConsSeller,shippingCharges,zeroCharges),
			        		"Additional charges are not on correct seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+zeroCharges);
			        //cancel Additional Charge Release
			        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
			        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
			        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
			        //verify there is no additional charges
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
			        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfConsSeller,shippingCharges,zeroCharges),
			        		"Additional charges are not on correct seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+zeroCharges);
			        
			        /*//Verify Refund happened for Release order amount fox8
			        sft.assertEquals(""+getRefundAmountAfterCancellation
							(getOrderReleaseCancellationPPsId(orderReleaseId)), "159800",
							"Additional charges should not be refunded");*/
			        End2EndHelper.sleep(5000);
			        //Verify Refund happened for Release order amount fox7
			        sft.assertEquals(""+getRefundAmountAfterCancellation
							(getOrderReleaseCancellationPPsId(orderReleaseId)),getRefundedAmountForCancelledRelease(orderReleaseId,zeroCharges),
							"Additional charges should not be refunded");
			        sft.assertAll();
			        
			}
				
			//Passed  on delta7-Sneha
			@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC010:Release cancellation when there are no other releases for the additionalchargeSeller")
			public void releaseCancellationWhenNoOtherReleaseAvailableForNoAdditionalChargeAvailable() throws Exception {
				/*fox8 Sku mapping	
				String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2"};
					atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
					imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
							OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
							OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
					
					//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
					sft=new SoftAssert();
					String skuId[] = {OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":1"};
					atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1"},supplyTypeOnHand);
					imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL},supplyTypeOnHand);
					orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
					verifySellerIdForOrderInBounty(orderID,-1);
			        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
					//orderID = 79913542L;

			        orderEntry = omsServiceHelper.getOrderEntry(orderID);
			        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
			        
			        //verify there is no additional charges
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,zeroCharges),
			        		"Additional charges should not be applied "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
			        
			        //cancel Additional Charge Release
			        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
			        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
			        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
			        //verify there is no additional charges
			        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,zeroCharges),
			        		"Additional charges should not be applied "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);

			        /*//Verify Refund happened for Release order amount fox8
			        sft.assertEquals(""+getRefundAmountAfterCancellation
							(getOrderReleaseCancellationPPsId(orderReleaseId)), "159800",
							"Additional charges should not be refunded");*/
			        
			        //Verify Refund happened for Release order amount fox7
			        sft.assertEquals(""+getRefundAmountAfterCancellation
							(getOrderReleaseCancellationPPsId(orderReleaseId)),getRefundedAmountForCancelledRelease(orderReleaseId,zeroCharges),
							"Additional charges should not be refunded");
			        sft.assertAll();
			        
		}
		
		//Passed on delta 7-Sneha
		@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC011:Release cancellation when there are no other releases for the additionalchargeSeller but there are active release for other sellers like Madura")
		public void releaseCancellationWhenMaduraReleasesAvailableForNoAdditionalChargeAvailable() throws Exception {
			/*			
			String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
						atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
						imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
								OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
								OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
						
			//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
			sft=new SoftAssert();
			String skuId[] = {OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
						atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
						imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OMSReleaseCancellation.cancelReleaseForWhichWHIsAssigned_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
								OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
								OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
						
						orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
						verifySellerIdForOrderInBounty(orderID,-1);
				        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
						//orderID = 79913542L;

				        orderEntry = omsServiceHelper.getOrderEntry(orderID);
				        List<String> activeReleasesOfHealSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
				        List<String> activeReleasesOfConsSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS);
				        
				        orderReleaseId = activeReleasesOfHealSeller.get(0);
				        orderReleaseId2 = activeReleasesOfConsSeller.get(0);

				        //verify there is no additional charges
				        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
				        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
				        //cancel Additional Charge Release
				        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
				        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
				        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
				        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
				        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
				        //verify there is no additional charges
				        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(activeReleasesOfHealSeller,shippingCharges,zeroCharges),
				        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
				        
				        /*//Verify Refund happened for Release order amount fox8
				        sft.assertEquals(""+getRefundAmountAfterCancellation
								(getOrderReleaseCancellationPPsId(orderReleaseId)), "159800",
								"Additional charges should not be refunded");*/
				        
				        //Verify Refund happened for Release order amount fox7
				        sft.assertEquals(""+getRefundAmountAfterCancellation
								(getOrderReleaseCancellationPPsId(orderReleaseId)),getRefundedAmountForCancelledRelease(orderReleaseId,zeroCharges),
								"Additional charges should not be refunded");
				        sft.assertAll();
				        
			}		
	
	//Passed
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC004:Release cancellation when there are other releases for the additionalchargeSeller but not in active state (marked as PK<SH<DL<RTO<F<L)")
	public void releaseCancellationWhenOtherReleasesAreNonActiveState() throws Exception {
		/*fox8 mapping sku
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
		//String orderId = 79913549L;
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleases  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges);
        updateReleaseStatusForOtherActiveRelease(activeReleases, "PK", activeReleaseWithAdditionalCharge);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");

        /*//Verify Refund happened for Release order amount fox8
		sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),
				"159800","Additional charges should be refunded");*/
		
        //Verify Refund happened for Release order amount fox7
		sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),
				getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge,shippingCharges),"Additional charges should be refunded");
		sft.assertAll();
        
	}
	
	//Passed
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC005:Release cancellation when there are no other releases for the additionalchargeSeller")
	public void releaseCancellationWhenNoOtherReleasesAreAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_749877+":12"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesOfHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesOfHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //cancel Additional Charge Release
        log.info("activeReleaseWithAdditionalCharge: "+activeReleaseWithAdditionalCharge);
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        //Verify charges are on same release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");
         
       /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "122500","Additional charges should be refunded");
 		*/
        
 		//Verify Refund happened for Release order amount fox7
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge,shippingCharges),"Additional charges should be refunded");
 		sft.assertAll();
        
	}
	
	
	
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC006:Release cancellation when there are no other releases for the additionalchargeSeller but there are active release for other sellers like Madura")
	public void releaseCancellationWhenMaduraReleasesAreAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2","3832:2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId105+":36,28:2:0:"+sellerId_HEAL+":1","3832:36,28:2:0:19:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":2:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:"+sellerId_HEAL+":1",
				"3832:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1:0:18:1","3832:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1:0:18:1","3832:1:0:0:18:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesOfHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesOfHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        //Verify charges are on same release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");
        //Verify charges not moved to next seller
        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),shippingCharges,giftCharges),
        		"Additional charges are moved to incorrect seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+giftCharges);
      /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "122500","Additional charges should be refunded");
         */
 		//Verify Refund happened for Release order amount fox7
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge,shippingCharges),"Additional charges should be refunded");
 		sft.assertAll();
	}
	
	
	//Passed on delta7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC007:Cancellation of a release other than the one which has additional charges")
	public void cancelReleaseNotHavingAdditionalCharge() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleases  = listOfActiveReleases(orderID);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges);
        sft.assertNotNull(activeReleaseWithAdditionalCharge,"Active release with additional charge is null");
        String activeReleaseWithoutAdditionalCharge = null;
        for(String release:activeReleases){
        	if(!(release.compareTo(activeReleaseWithAdditionalCharge)==0)){
        		activeReleaseWithoutAdditionalCharge = release;
        		break;
        	}
        }
        log.info("Active Releases:"+activeReleaseWithoutAdditionalCharge+" "+activeReleaseWithAdditionalCharge);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller");
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithoutAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithoutAdditionalCharge, "F", 10),"Release is not in F status");
        //Verify charges are on same release
        log.info("activeReleaseWithAdditionalCharge: "+activeReleaseWithAdditionalCharge);
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");
        //Verify charges not moved to next seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),zeroCharges,zeroCharges),
        		"Additional charges are moved to incorrect seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+zeroCharges);
      /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithoutAdditionalCharge)), "20000","Additional charges should be refunded");
        */
 		//Verify Refund happened for Release order amount fox7
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithoutAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithoutAdditionalCharge,zeroCharges),"Additional charges should be refunded");
 		sft.assertAll();
	}

	//Passed on delta 7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC012:Cancellation of one qty of a line and release for the additionalChargeSeller")
	public void oneQtyCancellationWhenOtherReleasesHavingSingleLineWithAdditionalCharge() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesOfParticularSeller  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesOfParticularSeller,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),shippingCharges,zeroCharges),
        		"Additional charges are not on correct seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+giftCharges);
        //cancel one qty in line
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(activeReleaseWithAdditionalCharge);
        String lineID = ""+orderLineEntries.get(0).getId();
        cancellationRes = omsServiceHelper.cancelLine(activeReleaseWithAdditionalCharge, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        //Verify charges moved to next active release
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesOfParticularSeller,shippingCharges,giftCharges,activeReleaseWithAdditionalCharge),
        		"Additional charges are still on old release "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Verify Refund happened for item cost
      
      /*//Verify Refund happened for Line fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(newlineID)), "79900","Additional charges should be refunded");*/
 		
 		//Verify Refund happened for Line fox7
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge,zeroCharges),"Additional charges should be refunded");
        
 		sft.assertAll();
	}


	
	//passed on delta7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC015.1:Line cancellation when there are no other releases for the additionalchargeSeller for SingleLine order")
	public void lineCancellationWhenNoOtherReleaseWithAdditionalChargeSingleLine() throws Exception {
		//fox8 sku mapping
		/*String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesForHeal,shippingCharges,giftCharges),
        		"Additional charges are not on correct seller");

        //cancel one line from Release
      //  Double finalAmountBeforeCancel = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge).getFinalAmount();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(activeReleaseWithAdditionalCharge);
        String lineID = ""+orderLineEntries.get(0).getId();
        
        cancellationRes = omsServiceHelper.cancelLine(activeReleaseWithAdditionalCharge, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        //Verify charges are on same seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
      /*//Verify Refund happened for Line fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), "3100","Additional charges should be refunded");*/
 		
 		//Verify Refund happened for Line fox7
        OrderLineEntry orderLineEntryCancelled = omsServiceHelper.getOrderLineEntry(lineID);
        Long cancelledAmount = (long) (orderLineEntryCancelled.getUnitPrice()*100) + (long)(shippingCharges*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), ""+cancelledAmount,"Additional charges should be refunded");
 		sft.assertAll();
	}

	// passed on delta 7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC015.2:Line cancellation when there are no other releases for the additionalchargeSeller")
	public void lineCancellationWhenNoOtherReleaseWithAdditionalCharge() throws Exception {
		//fox8 sku mapping
		/*String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesForHeal,shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);

        //cancel one line from Release
      //  Double finalAmountBeforeCancel = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge).getFinalAmount();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(activeReleaseWithAdditionalCharge);
        String lineID = ""+orderLineEntries.get(0).getId();
        cancellationRes = omsServiceHelper.cancelLine(activeReleaseWithAdditionalCharge, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);

        //Verify charges are on same seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Verify charges not moved to other seller
        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+giftCharges);
      /*//Verify Refund happened for Line fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), "3100","Additional charges should be refunded");
 				 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID2)), "162300","Additional charges should be refunded");*/
 		
 		//Verify Refund happened for Line fox7
        OrderLineEntry orderLineEntryCancelled = omsServiceHelper.getOrderLineEntry(lineID);
        Long cancelledAmount = (long) (orderLineEntryCancelled.getUnitPrice()*100) + (long)(shippingCharges*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)),""+cancelledAmount,"Additional charges should be refunded");

 		sft.assertAll();
	}

	//Passed on delta7-Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC016:Line cancellation when there are no other releases for the additionalchargeSeller but there are active release for other sellers like Madura")
	public void oneLineCancellationWhenMaduraActiveReleaseAvailable() throws Exception {
		/*//fox 8
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:32:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),shippingCharges,zeroCharges),
        		"Additional charges are not on correct seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+zeroCharges);
        //cancel one line from Release
      //  Double finalAmountBeforeCancel = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge).getFinalAmount();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(activeReleaseWithAdditionalCharge);
        String lineID = ""+orderLineEntries.get(0).getId();
        cancellationRes = omsServiceHelper.cancelLine(activeReleaseWithAdditionalCharge, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        
        //Verify charges are on same seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Verify charges not moved to next seller
        sft.assertTrue(!verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS),shippingCharges,zeroCharges),
        		"Additional charges are moved to incorrect seller "+sellerId_CONS+" Expected:"+zeroCharges+" and "+zeroCharges);
        /*//Verify Refund happened for Line fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), "3100","Additional charges should be refunded");*/
        
 		//Verify Refund happened for Line fox7
        OrderLineEntry orderLineEntryCancelled = omsServiceHelper.getOrderLineEntry(lineID);
        Long cancelledAmount = (long) (orderLineEntryCancelled.getUnitPrice()*100) + (long)(shippingCharges*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), ""+cancelledAmount,"Additional charges should be refunded");
 		sft.assertAll();
 	}

 	//passed on delta7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC017:Line cancellation when there are other releases for the additionalchargeSeller but not in active state (marked as DL)")
	public void lineCancellationWhenActiveReleaseNotAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Update other Release as DL
        updateReleaseStatusForOtherActiveRelease(activeReleasesForHeal, "DL", activeReleaseWithAdditionalCharge);
        //cancel one line from Release
        //  Double finalAmountBeforeCancel = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge).getFinalAmount();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(activeReleaseWithAdditionalCharge);
        String lineID = null;
        for(OrderLineEntry orderLineEntry:orderLineEntries){
        	if(orderLineEntry.getSellerId().toString().equalsIgnoreCase(""+sellerId_HEAL))
        	lineID = ""+orderLineEntry.getId();
        }
        
        cancellationRes = omsServiceHelper.cancelLine(activeReleaseWithAdditionalCharge, login, new String[] {lineID +":1"}, 41);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are still on old release");
        //Verify charges not moved to next seller

        /*//Verify Refund happened for Line fox8
        sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), "2900","Additional charges should be refunded");*/
        
      //Verify Refund happened for Line fox7
        OrderLineEntry orderLineEntryCancelled = omsServiceHelper.getOrderLineEntry(lineID);
        Long cancelledAmount = (long) (orderLineEntryCancelled.getUnitPrice()*100) + (long)(shippingCharges*100);
        sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getLineCancellationPPsId(lineID)), ""+cancelledAmount,"Additional charges should be refunded");
        sft.assertAll();

	}

	//passed on delta7- Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC018:A release/line having additional charges is RTOed and the seller has other active releases")
	public void markReleaseRTOWhenOtherActiveReleasesAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        //Verify charges applied for correct Seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Update other Release as SH
        omsServiceHelper.updateReleaseStatusDB(activeReleaseWithAdditionalCharge, "SH");

        //Mark RTO Additional Charge Release
        //Updating packedon date
        omsServiceHelper.updateDateInRelease(activeReleaseWithAdditionalCharge);
        OrderReleaseResponse cancellationRes = omsServiceHelper.markOrderReleaseRTO(activeReleaseWithAdditionalCharge,login);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1008);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "RTO");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "RTO", 10),"Release is not in F status");
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are still on old release");

      /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "2900","Additional charges should be refunded");
 		*/
 		//Verify Refund happened for Release order amount fox7
        OrderReleaseEntry orderReleaseEntryForLostRelease = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge);
        Long refundedAmount = (long) ((orderReleaseEntryForLostRelease.getMrpTotal()+shippingCharges)*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), ""+refundedAmount,"Additional charges should be refunded");
 		sft.assertAll();
	}

	//passed on delta7-Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC019:A release/line having additional charges is RTOed and the seller has other active releases")
	public void markReleaseRTOWhenOtherActiveReleasesNotAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        //Verify charges applied for correct Seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesForHeal,shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Update other Release as SH
        omsServiceHelper.updateReleaseStatusDB(activeReleaseWithAdditionalCharge, "SH");
        //Update other Release as DL
        updateReleaseStatusForOtherActiveRelease(activeReleasesForHeal, "DL", activeReleaseWithAdditionalCharge);
        //Mark RTO Additional Charge Release
        //Updating packedon date
        omsServiceHelper.updateDateInRelease(activeReleaseWithAdditionalCharge);
        OrderReleaseResponse cancellationRes = omsServiceHelper.markOrderReleaseRTO(activeReleaseWithAdditionalCharge,login);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1008);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);

        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "RTO", 10),"Release is not in RTO status");
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are still on old release");
        
      /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "2900","Additional charges should be refunded");
 		*/
 		//Verify Refund happened for Release order amount fox7
        OrderReleaseEntry orderReleaseEntryForLostRelease = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge);
        Long refundedAmount = (long) ((orderReleaseEntryForLostRelease.getMrpTotal()+shippingCharges)*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),(""+refundedAmount),"Additional charges should be refunded");
 		sft.assertAll();
	}


	//Passed on delta 7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC020:A release/line having additional charges is RTOed and the seller has other active releases")
	public void markReleaseLOSTWhenOtherActiveReleasesAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        //Verify charges applied for correct Seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(activeReleasesForHeal,shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);

        //Update Release as SH
        omsServiceHelper.updateReleaseStatusDB(activeReleaseWithAdditionalCharge, "SH");

        //Mark RTO Additional Charge Release
        //Updating packedon date
        omsServiceHelper.updateDateInRelease(activeReleaseWithAdditionalCharge);
        OrderReleaseResponse cancellationRes = omsServiceHelper.markOrderReleaseLost(activeReleaseWithAdditionalCharge,login,1);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1008);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "L");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "L", 10),"Release is not in L status");
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameActiveRelease(activeReleasesForHeal,shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are still on old release");

        /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "2900","Additional charges should be refunded");
 		*/
 		//Verify Refund happened for Release order amount fox7
        OrderReleaseEntry orderReleaseEntryForLostRelease = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge);
        Long refundedAmount = (long) ((orderReleaseEntryForLostRelease.getMrpTotal()+shippingCharges)*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),(""+refundedAmount),
 				"Additional charges should be refunded");
 		sft.assertAll();
	}


	//passed on delta7 - Sneha
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC021:A release/line having additional charges is RTOed and the seller has other active releases")
	public void markReleaseLOSTWhenOtherActiveReleasesNotAvailable() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":2",OMSTCConstants.OtherSkus.skuId_47584+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS },supplyTypeOnHand);

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        List<String> activeReleasesForHeal  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(activeReleasesForHeal,shippingCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL);
        //Verify charges applied for correct Seller
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL+" Expected:"+zeroCharges+" and "+giftCharges);
        //Update other Release as SH
        omsServiceHelper.updateReleaseStatusDB(activeReleaseWithAdditionalCharge, "SH");
        //Update other Release as DL
        updateReleaseStatusForOtherActiveRelease(activeReleasesForHeal, "DL", activeReleaseWithAdditionalCharge);
        //Mark RTO Additional Charge Release
        //Updating packedon date
        omsServiceHelper.updateDateInRelease(activeReleaseWithAdditionalCharge);
        OrderReleaseResponse cancellationRes = omsServiceHelper.markOrderReleaseLost(activeReleaseWithAdditionalCharge,login,1);
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1008);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "L");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "L", 10),"Release is not in L status");
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL),shippingCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are still on old release");
        
      /*//Verify Refund happened for Release order amount fox8
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "2900","Additional charges should be refunded");
 		*/
 		//Verify Refund happened for Release order amount fox7
        OrderReleaseEntry orderReleaseEntryForLostRelease = omsServiceHelper.getOrderReleaseEntry(activeReleaseWithAdditionalCharge);
        Long refundedAmount = (long) ((orderReleaseEntryForLostRelease.getMrpTotal()+shippingCharges)*100);
 		sft.assertEquals(""+getRefundAmountAfterCancellation
 				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),(""+refundedAmount),"Additional charges should be refunded");
 		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC022:A release/line of JIT Items having additional charges is cancelled and the seller has other active releases")
	public void releaseCancellationWhenOtherReleasesForAdditionalChargeAvailableInJIT_Items() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {"407403:1","1251668:1","1153280:1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"407403:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1","1153280:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1","1251668:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:4:1"},supplyTypeJIT);
		imsServiceHelper.updateInventoryForSeller(new String[]{"407403:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1","1153280:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",
				"1251668:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:4:1"},supplyTypeJIT);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_818509+":2",OMSTCConstants.OtherSkus.skuId_818510+":2",OMSTCConstants.OtherSkus.skuId_1190292+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_818509+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT+":1",OMSTCConstants.OtherSkus.skuId_818510+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_HEAL_JIT+":1",OMSTCConstants.OtherSkus.skuId_1190292+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_CONS+":1"},supplyTypeJIT);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_818509+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT,
				OMSTCConstants.OtherSkus.skuId_818510+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_HEAL_JIT,
				OMSTCConstants.OtherSkus.skuId_1190292+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_CONS_JIT},supplyTypeJIT);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL_JIT);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"RFR:WP");
		//orderID = 79913542L;
        List<String> activeReleases  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT),zeroCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL_JIT);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL_JIT);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT),zeroCharges,giftCharges),
        		"Additional charges are not on correct seller");
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,zeroCharges,zeroCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");
        
        //Verify charges moved to next active release
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT),zeroCharges,giftCharges,activeReleaseWithAdditionalCharge),
        		"Additional charges are still on old release");
        
        /*//Verify Refund happened for Release order amount fox8
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "79900",
				"Additional charges should not be refunded");*/
            
      //Verify Refund happened for Release order amount fox7
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge, zeroCharges),
				"Additional charges should not be refunded");
        sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","additionalSellerCharge"}, description = "TC023:A release/line of JIT Items having additional charges is cancelled and the seller has no other active releases")
	public void releaseCancellationWhenOtherReleasesAreNonActiveStateJIT_ITEMS() throws Exception {
		/*fox8 sku mapping
		String skuId[] = {"407403:1","1251668:1","1153280:1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"407403:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1","1153280:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1","1251668:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:4:1"},supplyTypeJIT);
		imsServiceHelper.updateInventoryForSeller(new String[]{"407403:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1","1153280:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:21:1",
				"1251668:"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:4:1"},supplyTypeJIT);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_818509+":2",OMSTCConstants.OtherSkus.skuId_818510+":2",OMSTCConstants.OtherSkus.skuId_1190292+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_818509+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT+":1",
				OMSTCConstants.OtherSkus.skuId_818510+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT+":1",
				OMSTCConstants.OtherSkus.skuId_1190292+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_CONS_JIT+":1"},supplyTypeJIT);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_818509+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT,
				OMSTCConstants.OtherSkus.skuId_818510+":"+OMSTCConstants.WareHouseIds.warehouseId26_BW+":100:0:"+sellerId_HEAL_JIT,
				OMSTCConstants.OtherSkus.skuId_1190292+":"+OMSTCConstants.WareHouseIds.warehouseId47_BW+":100:0:"+sellerId_CONS_JIT },supplyTypeJIT);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL_JIT);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"RFR:WP");
		//orderID = 79913542L;
        List<String> activeReleases  = listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT);
        String activeReleaseWithAdditionalCharge = getActiveReleaseWithAdditionalCharge
        		(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT),zeroCharges,giftCharges);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //verifyAdditionalChargesSellerInXcart_orders(orderID,sellerId_HEAL_JIT);
        verifyAdditionalChargesSellerIDInOrderAdditionalInfo(orderID,sellerId_HEAL_JIT);
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_HEAL_JIT),zeroCharges,giftCharges),
        		"Additional charges are not on correct seller "+sellerId_HEAL_JIT+" Expected:"+zeroCharges+" and "+giftCharges);
        //Update other Release as DL
        updateReleaseStatusForOtherActiveRelease(activeReleases, "DL", activeReleaseWithAdditionalCharge);
        //cancel Additional Charge Release
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(activeReleaseWithAdditionalCharge, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(activeReleaseWithAdditionalCharge, "F", 10),"Release is not in F status");
        
        //Verify charges are on same active release
        sft.assertTrue(verifyAdditionalChargeOnSameRelease(activeReleaseWithAdditionalCharge,zeroCharges,giftCharges,
        		activeReleaseWithAdditionalCharge),"Additional charges are not on old release");
        
        //Verify charges not moved to next active release
        sft.assertTrue(verifyAdditionalChargeOnActiveRelease(listOfActiveReleasesOfParticularSeller(orderID,sellerId_CONS_JIT),zeroCharges,zeroCharges,activeReleaseWithAdditionalCharge),
        		"Additional charges are still on old release");
        
        /*//Verify Refund happened for Release order amount fox8
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)), "82400",
				"Additional charges should not be refunded");*/ 
        
      //Verify Refund happened for Release order amount fox7
        sft.assertEquals(""+getRefundAmountAfterCancellation
				(getOrderReleaseCancellationPPsId(activeReleaseWithAdditionalCharge)),getRefundedAmountForCancelledRelease(activeReleaseWithAdditionalCharge, zeroCharges),
				"Additional charges should not be refunded");
        sft.assertAll();
	}
	
	
	/**
	 * This function is to verify if Additional charges sellerId is persisted to order table from Bounty
	 * @return
	 */
	public void verifyAdditionalChargesSellerIDInOrderAdditionalInfo(String orderID, int sellerID) throws UnsupportedEncodingException, JAXBException{
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		additionalChargesSellerID  = omsServiceHelper.getOrderAdditionalInfoDBEntry
				(orderID, "ADDITIONAL_CHARGES_SELLER_ID").get("value").toString();
		sft.assertEquals(additionalChargesSellerID, sellerID+"","Additional charges are not applied on correct seller in OMS");
		
	}
	
	/**
	 * This function is to verify if Additional charges sellerId is persisted to order release additional table from Bounty
	 * @return
	 */
	public void verifyAdditionalChargesSellerIDInOrderReleaseAdditionalInfo(OrderEntry orderEntry, int sellerID) throws UnsupportedEncodingException, JAXBException{
		List<OrderReleaseEntry> orderreleaseEntries = orderEntry.getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry:orderreleaseEntries){
			orderReleaseId = ""+orderReleaseEntry.getId();
			
			additionalChargesSellerID  = (String) omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry
					(orderReleaseId, "ADDITIONAL_CHARGES_SELLER_ID").get("value");
			sft.assertEquals(additionalChargesSellerID, sellerID+"","Additional charges are not applied on correct seller"+sellerID+" in OMS Actual:"+additionalChargesSellerID);
			
		}
		
	}
	
	/**
	 * This function is to verify if Additional charges applied for Particular seller
	 * @return Boolean
	 */
	public Boolean verifyAdditionalChargeOnActiveRelease(List<String> activeRelease, Double additionalShippingCharge,Double giftWrapCharge) throws UnsupportedEncodingException, JAXBException{
		boolean isAdditionalChargeAvailable = false;
		for(String releaseId : activeRelease){
			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
			additionalChargeOnRelease = orderReleaseEntry.getShippingCharge();
			additionalGiftCharge = orderReleaseEntry.getGiftCharge();
			if(additionalChargeOnRelease.equals(additionalShippingCharge) && additionalGiftCharge.equals(giftWrapCharge)){
				isAdditionalChargeAvailable = true;
				log.info("additionalChargeOnRelease: "+additionalChargeOnRelease);
				log.info("additionalGiftCharge: "+additionalGiftCharge);
			}
		}

		return isAdditionalChargeAvailable;
	}
	
	/**
	 * This function is to verify if Additional charges are applied for another active release not for old release
	 * @return Boolean
	 */
	public Boolean verifyAdditionalChargeOnActiveRelease(List<String> activeRelease, Double additionalShippingCharge,Double giftWrapCharge,String oldRelease) throws UnsupportedEncodingException, JAXBException{
		boolean isAdditionalChargeAvailable = false;
		for(String releaseId : activeRelease){
			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
			additionalChargeOnRelease = orderReleaseEntry.getShippingCharge();
			additionalGiftCharge = orderReleaseEntry.getGiftCharge();
			if(additionalChargeOnRelease.equals(additionalShippingCharge) && additionalGiftCharge.equals(giftWrapCharge)
					&& releaseId!=oldRelease){
				isAdditionalChargeAvailable = true;
			}
		}
		
		return isAdditionalChargeAvailable;
	}
	
	/**
	 * This function is to verify if Additional charges are applied for same active release
	 * @return Boolean
	 */
	public Boolean verifyAdditionalChargeOnSameActiveRelease(List<String> activeRelease, Double additionalShippingCharge,Double giftWrapCharge,String oldRelease) throws UnsupportedEncodingException, JAXBException{
		boolean isAdditionalChargeAvailable = false;
		for(String releaseId : activeRelease){
			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
			additionalChargeOnRelease = orderReleaseEntry.getShippingCharge();
			additionalGiftCharge = orderReleaseEntry.getGiftCharge();
			if(additionalChargeOnRelease.equals(additionalShippingCharge) && additionalGiftCharge.equals(giftWrapCharge)
					&& releaseId.equals(oldRelease)){
				isAdditionalChargeAvailable = true;
			}
		}
		if (activeRelease.size()==0)
			isAdditionalChargeAvailable = true;

		
		return isAdditionalChargeAvailable;
	}
	
	/**
	 * This function is to verify if Additional charges are applied for same release
	 * @return Boolean
	 */
	public Boolean verifyAdditionalChargeOnSameRelease(String activeRelease, Double additionalShippingCharge,Double giftWrapCharge,String oldRelease) throws UnsupportedEncodingException, JAXBException{
		boolean isAdditionalChargeAvailable = false;

			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(activeRelease);
			additionalChargeOnRelease = orderReleaseEntry.getShippingCharge();
			additionalGiftCharge = orderReleaseEntry.getGiftCharge();
			log.info(additionalChargeOnRelease+" "+additionalGiftCharge);
			if(additionalChargeOnRelease.equals(additionalShippingCharge) && additionalGiftCharge.equals(giftWrapCharge)
					&& activeRelease.equals(oldRelease)){
				isAdditionalChargeAvailable = true;
			}
		
		
		return isAdditionalChargeAvailable;
	}
	/**
	 * This function is to get ReleaseId having additional charges
	 * @return releaseId
	 */
	public String getActiveReleaseWithAdditionalCharge(List<String> activeRelease,Double additionalShippingCharge,Double giftWrapCharge) throws UnsupportedEncodingException, JAXBException{
		for(String releaseId : activeRelease){
			orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
			additionalChargeOnRelease = orderReleaseEntry.getShippingCharge();
			additionalGiftCharge = orderReleaseEntry.getGiftCharge();
			if(additionalChargeOnRelease.equals(additionalShippingCharge) && additionalGiftCharge.equals(giftWrapCharge)){
				return releaseId;
			}
		}
		
		return null;
	}
	
	/**
	 * This function is to get List of Active release for particular seller
	 * @return List<ReleaseIds>
	 */
	public List<String> listOfActiveReleasesOfParticularSeller(String orderId,int sellerId) throws UnsupportedEncodingException, JAXBException{
		List<OrderReleaseEntry> orderReleaseEntries = null;
		ArrayList<String> orderReleaseList = new ArrayList<String>();
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseEntries = orderEntry.getOrderReleases();
		for (OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
			if(orderReleaseEntry.getStatus()!="PK"||
					orderReleaseEntry.getStatus()!="SH"||orderReleaseEntry.getStatus()!="DL"||
					orderReleaseEntry.getStatus()!="RTO"||orderReleaseEntry.getStatus()!="F"||orderReleaseEntry.getStatus()!="L"){
				if(checkIfReleaseBelongToSeller(orderReleaseEntry.getId().toString(),sellerId)){
					orderReleaseList.add(orderReleaseEntry.getId().toString());
				}
				
				
			}
		}
		
		return orderReleaseList;
	}
	
	/**
	 * This function is to get List of Active release for particular seller
	 * @return List<ReleaseIds>
	 */
	public List<String> listOfActiveReleases(String orderId) throws UnsupportedEncodingException, JAXBException{
		List<OrderReleaseEntry> orderReleaseEntries = null;
		ArrayList<String> orderReleaseList = new ArrayList<String>();
		orderEntry = omsServiceHelper.getOrderEntry(orderId);
		orderReleaseEntries = orderEntry.getOrderReleases();
		for (OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
			if(orderReleaseEntry.getStatus()!="PK"||
					orderReleaseEntry.getStatus()!="SH"||orderReleaseEntry.getStatus()!="DL"||
					orderReleaseEntry.getStatus()!="RTO"||orderReleaseEntry.getStatus()!="F"||orderReleaseEntry.getStatus()!="L"){
					orderReleaseList.add(orderReleaseEntry.getId().toString());				
				
			}
		}
		
		return orderReleaseList;
	}
	
	/**
	 * @param releaseId
	 * @param sellerId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * This function is to check if release belong to some particular seller
	 */
	public Boolean checkIfReleaseBelongToSeller(String releaseId, int sellerId) throws UnsupportedEncodingException, JAXBException{
		List<OrderLineEntry> lines = omsServiceHelper.getOrderLineEntries(releaseId);
		for(OrderLineEntry line: lines){
			if(line.getSellerId()==sellerId){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This function is to Update All Active Releases in given status other than provided releaseId
	 * @return List<ReleaseIds>
	 */
	public void updateReleaseStatusForOtherActiveRelease(List<String> activeReleases,String status, String oldRelease){
		for(String releaseId : activeReleases){
			if(!releaseId.equalsIgnoreCase(oldRelease)){
			  omsServiceHelper.updateReleaseStatusDB(releaseId,status );
			  Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId, status, 5));
			}
		}
		
	}
	
	/**
	 * @param releaseId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * This function is to get final amount of Release
	 */
	public Long finalAmountOfRelease(String releaseId) throws UnsupportedEncodingException, JAXBException{
		Long finalAmount =  (long)(omsServiceHelper.getOrderReleaseEntry(releaseId).getFinalAmount() * 100);
		return finalAmount;
	}
	
	
	//Passed in delta7 -Sneha
	@Test(enabled = true,groups={"regression"}, description = "TC024:Split whole line in release for new courier which doesn't support Shipment Consolidation.")
	public void reassignWarehouseForWholeLinesInRelease() throws Exception {
		/*//fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_47584+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		
        ArrayList<Integer> countBeforePlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforePlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforePlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        End2EndHelper.sleep(10000L);
        
        ArrayList<Integer> countAfterPlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterPlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterPlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
        
		sft.assertEquals(countBeforePlacingOrder3831, countAfterPlacingOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder3834, countAfterPlacingOrder3834,OMSTCConstants.OtherSkus.skuId_47584+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder749874, countAfterPlacingOrder749874,OMSTCConstants.OtherSkus.skuId_749877+" count should be matched");
		
		
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        //sft.assertEquals(orderEntry.getOrderReleases().size(), 1,"There should be only 1 release");
        
//		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
//				OMSTCConstants.OtherSkus.skuId106+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
//				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_CONS},supplyTypeOnHand);
//		
        String query = "update wh_inventory set inventory_count=1000,blocked_order_count=0 where supply_type='"+supplyTypeOnHand+"' and sku_id in("+OMSTCConstants.OtherSkus.skuId_47583+","+OMSTCConstants.OtherSkus.skuId_47584+") "
				+ "and warehouse_id='"+OMSTCConstants.WareHouseIds.warehouseId36_BW+"' and store_id=1 and seller_id="+sellerId_HEAL;
		DBUtilities.exUpdateQuery(query, "myntra_ims");
		
		query = "update wh_inventory set inventory_count=1000,blocked_order_count=0 where supply_type='"+supplyTypeOnHand+"' and sku_id in("+OMSTCConstants.OtherSkus.skuId_749877+") "
				+ "and warehouse_id='"+OMSTCConstants.WareHouseIds.warehouseId36_BW+"' and store_id=1 and seller_id="+sellerId_CONS;
		DBUtilities.exUpdateQuery(query, "myntra_ims");
		
        ArrayList<Integer> countBeforeSplitOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforeSplitOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforeSplitOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_CONS,"1");
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();
		
		OrderLineEntry orderLine2 = orderReleaseEntry.get(1).getOrderLines().get(0);
		String lineID2 = "" + orderLine2.getId();
		
		OrderLineEntry orderLine3 = orderReleaseEntry.get(2).getOrderLines().get(0);
		String lineID3 = "" + orderLine3.getId();
		
        String LineIDAndQuantity[] = { lineID+":1"};
	    String LineIDAndQuantity2[] = { lineID2+":1"};
        String LineIDAndQuantity3[]= { lineID3+":1"};
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder
				(orderEntry.getOrderReleases().get(0).getId().toString(),36, LineIDAndQuantity, "IP", LineMovementAction.REASSIGN_WAREHOUSE);
		
		OrderReleaseResponse orderReleaseResponse2=omsServiceHelper.splitOrder
				(orderEntry.getOrderReleases().get(1).getId().toString(),36, LineIDAndQuantity2, "IP", LineMovementAction.REASSIGN_WAREHOUSE);
		
		OrderReleaseResponse orderReleaseResponse3=omsServiceHelper.splitOrder
				(orderEntry.getOrderReleases().get(2).getId().toString(),36, LineIDAndQuantity3, "IP", LineMovementAction.REASSIGN_WAREHOUSE);

		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(),Type.SUCCESS,"Reassignment failed");
		sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType(),Type.SUCCESS,"Reassignment failed");
		sft.assertEquals(orderReleaseResponse3.getStatus().getStatusType(),Type.SUCCESS,"Reassignment failed");
		
		
		End2EndHelper.sleep(5000L);
        ArrayList<Integer> countAfterSplitOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",0,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterSplitOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",0,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterSplitOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",0,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,sellerId_CONS,"1");
        
		sft.assertEquals(countBeforeSplitOrder3831, countAfterSplitOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforeSplitOrder3834, countAfterSplitOrder3834,OMSTCConstants.OtherSkus.skuId_47584+" count should be matched");
		sft.assertEquals(countBeforeSplitOrder749874, countAfterSplitOrder749874,OMSTCConstants.OtherSkus.skuId_749877+" count should be matched");
		
		//Check Warehouse should be 36 in WMS
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		for(OrderReleaseEntry releases: orderEntry.getOrderReleases()){
			sft.assertEquals("36",""+wmsServiceHelper.getCaptureOrderReleaseData(releases.getId().toString()).getWarehouseId(),
					"Release"+releases.getId()+" should be assigned to warehouse 36");
		}
		End2EndHelper.sleep(10000L);
		//Verify there should be 3 releases
		sft.assertEquals(3, orderEntry.getOrderReleases().size(),"There should be 3 releases");
		
        countAfterPlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",-1,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        countAfterPlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",-1,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        countAfterPlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",-1,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
         
 		sft.assertEquals(countBeforePlacingOrder3831, countAfterPlacingOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
 		sft.assertEquals(countBeforePlacingOrder3834, countAfterPlacingOrder3834,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
 		sft.assertEquals(countBeforePlacingOrder749874, countAfterPlacingOrder749874,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
 		sft.assertAll();
        
	}
	
	//Passed for delta 7-Sneha
	@Test(enabled = true,groups={"regression"}, description = "TC025:Split whole line in release for new courier which doesn't support Shipment Consolidation.")
	public void reassignWarehouseForFewLinesInRelease() throws Exception {
		/*//fox8 sku mapping
		String skuId[] = {OMSTCConstants.OtherSkus.skuId108+":2",OMSTCConstants.OtherSkus.skuId105+":2",OMSTCConstants.OtherSkus.skuId_749877+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId108+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId108+":19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId_749877+":2:0:0:32:1",
				OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:21:1",OMSTCConstants.OtherSkus.skuId105+":1:0:0:21:1",OMSTCConstants.OtherSkus.skuId105+":2:0:0:21:1"},supplyTypeOnHand);*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		sft=new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_47584+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
        ArrayList<Integer> countBeforePlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforePlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countBeforePlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		verifySellerIdForOrderInBounty(orderID,sellerId_HEAL);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        End2EndHelper.sleep(10000L);
        
        ArrayList<Integer> countAfterPlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterPlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        ArrayList<Integer> countAfterPlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
        
		sft.assertEquals(countBeforePlacingOrder3831, countAfterPlacingOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder3834, countAfterPlacingOrder3834,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder749874, countAfterPlacingOrder749874,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
       // sft.assertEquals(orderEntry.getOrderReleases().size(), 1,"There should be only 1 release");
        	
		String query = "update wh_inventory set inventory_count=1000,blocked_order_count=0 where supply_type='"+supplyTypeOnHand+"' and sku_id in("+OMSTCConstants.OtherSkus.skuId_47583+","+OMSTCConstants.OtherSkus.skuId_47584+") "
				+ "and warehouse_id='"+OMSTCConstants.WareHouseIds.warehouseId36_BW+"' and store_id=1 and seller_id="+sellerId_HEAL;
		DBUtilities.exUpdateQuery(query, "myntra_ims");
		
		query = "update wh_inventory set inventory_count=1000,blocked_order_count=0 where supply_type='"+supplyTypeOnHand+"' and sku_id in("+OMSTCConstants.OtherSkus.skuId_749877+") "
				+ "and warehouse_id='"+OMSTCConstants.WareHouseIds.warehouseId36_BW+"' and store_id=1 and seller_id="+sellerId_CONS;
		DBUtilities.exUpdateQuery(query, "myntra_ims");
		
		
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		String lineID = null,lineID2 = null,lineID3 = null;
		OrderLineEntry orderLine = null,orderLine2 = null,orderLine3 = null;
		
		for(int i=0;i<orderEntry.getOrderReleases().size();i++)
		{
		for(OrderLineEntry orderLineEntry:orderReleaseEntry.get(i).getOrderLines()){
			if(orderLineEntry.getSkuId().toString().equalsIgnoreCase("47583")){
				lineID = "" + orderLineEntry.getId();
				orderLine = orderLineEntry;
			}else if(orderLineEntry.getSkuId().toString().equalsIgnoreCase("47584")){
				lineID2 = "" + orderLineEntry.getId();
				orderLine2 = orderLineEntry;
			}else if(orderLineEntry.getSkuId().toString().equalsIgnoreCase("749877")){
				lineID3 = "" + orderLineEntry.getId();
				orderLine3 = orderLineEntry;
			}
		}}
		
        ArrayList<Integer> countBeforeSplitOrder3831 = getIMSAndATPCountForSKU(""+orderLine.getSkuId(),0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,Integer.parseInt(""+orderLine.getSellerId()),"1");
        ArrayList<Integer> countBeforeSplitOrder3834 = getIMSAndATPCountForSKU(""+orderLine2.getSkuId(),0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,Integer.parseInt(""+orderLine2.getSellerId()),"1");
        ArrayList<Integer> countBeforeSplitOrder749874 = getIMSAndATPCountForSKU(""+orderLine3.getSkuId(),0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,Integer.parseInt(""+orderLine3.getSellerId()),"1");
		
        String LineIDAndQuantity[] = { lineID2+":1"};
        String LineIDAndQuantity2[]= { lineID3+":1"};
		//OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder
		//		(orderReleaseId,36, LineIDAndQuantity, "IP", LineMovementAction.REASSIGN_WAREHOUSE);
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder
				(orderEntry.getOrderReleases().get(1).getId().toString(),36, LineIDAndQuantity, "IP", LineMovementAction.REASSIGN_WAREHOUSE);
		
		OrderReleaseResponse orderReleaseResponse2=omsServiceHelper.splitOrder
				(orderEntry.getOrderReleases().get(2).getId().toString(),36, LineIDAndQuantity2, "IP", LineMovementAction.REASSIGN_WAREHOUSE);
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(),Type.SUCCESS,"Reassignment failed");
		sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType(),Type.SUCCESS,"Reassignment failed");
		
		End2EndHelper.sleep(5000L);

        ArrayList<Integer> countAfterSplitOrder3831 = getIMSAndATPCountForSKU(""+orderLine.getSkuId(),0,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,Integer.parseInt(""+orderLine.getSellerId()),"1");
        ArrayList<Integer> countAfterSplitOrder3834 = getIMSAndATPCountForSKU(""+orderLine2.getSkuId(),0,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,Integer.parseInt(""+orderLine2.getSellerId()),"1");
        ArrayList<Integer> countAfterSplitOrder749874 = getIMSAndATPCountForSKU(""+orderLine3.getSkuId(),0,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId36_BW,Integer.parseInt(""+orderLine3.getSellerId()),"1");
        
		sft.assertEquals(countBeforeSplitOrder3831, countAfterSplitOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforeSplitOrder3834, countAfterSplitOrder3834,OMSTCConstants.OtherSkus.skuId_47584+" count should be matched");
		sft.assertEquals(countBeforeSplitOrder749874, countAfterSplitOrder749874,OMSTCConstants.OtherSkus.skuId_749877+" count should be matched");
		
		//Verify there should be 3 releases
		//sft.assertEquals(orderEntry.getOrderReleases().size(),3 ,"There should be 3 releases");
				
				
		//Check Warehouse should be 36 in WMS
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		for(OrderReleaseEntry releases: orderEntry.getOrderReleases()){
			log.info(orderReleaseId+" "+releases.getId());
			if((""+releases.getId()).equals(""+orderReleaseId)){
				sft.assertEquals("28",""+wmsServiceHelper.getCaptureOrderReleaseData(releases.getId().toString()).getWarehouseId(),
						"Release"+releases.getId()+" should be assigned to warehouse 36");
			}else{
				sft.assertEquals("36",""+wmsServiceHelper.getCaptureOrderReleaseData(releases.getId().toString()).getWarehouseId(),
						"Release"+releases.getId()+" should be assigned to warehouse 36");
			}

		}
		
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        countAfterPlacingOrder3831 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47583+"",-1,0,-1,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        countAfterPlacingOrder3834 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_47584+"",-1,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_HEAL,"1");
        countAfterPlacingOrder749874 = getIMSAndATPCountForSKU(OMSTCConstants.OtherSkus.skuId_749877+"",-1,0,0,0,""+OMSTCConstants.WareHouseIds.warehouseId28_GN,sellerId_CONS,"1");
        
		sft.assertEquals(countBeforePlacingOrder3831, countAfterPlacingOrder3831,OMSTCConstants.OtherSkus.skuId_47583+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder3834, countAfterPlacingOrder3834,OMSTCConstants.OtherSkus.skuId_47584+" count should be matched");
		sft.assertEquals(countBeforePlacingOrder749874, countAfterPlacingOrder749874,OMSTCConstants.OtherSkus.skuId_749877+" count should be matched");
		sft.assertAll();
        
	}

	
	public ArrayList<Integer> getIMSAndATPCountForSKU(String sku,int increaseBlockCountatp, int increaseInventoryCountatp,
			int increaseBlockCountims, int increaseInventoryCountims,
			String warehouse,int sellerId,String storeId) throws SQLException{
		
		HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper
                .getAtpInvAndBlockOrderCount(new String[] { sku });

        HashMap<String, int[]> inventoryCountInIMSBeforePlacingOrder = imsServiceHelper
                .getIMSInvAndBlockOrderCount(new String[] { sku },warehouse,storeId,""+sellerId);

        int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get( sku );
        int inventoryCountATPBefore = blockCountATP[0];
        int blockCountATPBefore = blockCountATP[1];
        
        int[] blockCountIMS = inventoryCountInIMSBeforePlacingOrder.get( sku );
        int inventoryCountIMSBefore = blockCountIMS[0];
        int blockCountIMSBefore = blockCountIMS[1];
        
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(0, inventoryCountATPBefore+increaseInventoryCountatp);
        arrayList.add(1, blockCountATPBefore+increaseBlockCountatp);
        arrayList.add(2, inventoryCountIMSBefore+increaseInventoryCountims);
        arrayList.add(3, blockCountIMSBefore+increaseBlockCountims);
        
		return arrayList;

        
	}
	
	/**
	 * @param orderReleaseId
	 * @param additionalCharge
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public String getRefundedAmountForCancelledRelease(String orderReleaseId,Double additionalCharge) throws UnsupportedEncodingException, JAXBException{
        OrderReleaseEntry orderReleaseEntryForCancelledRelease = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        Long refundedAmount = (long) ((orderReleaseEntryForCancelledRelease.getMrpTotal()+additionalCharge)*100);
        
        return ""+refundedAmount;
	}
	
	
	public void verifySellerIdForOrderInBounty(String orderID,long sellerId) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
    	String storeOrderId = omsServiceHelper.getOrderEntry(orderID).getStoreOrderId();
    	if(sellerId != -1){
        	xCartOrderEntry = bountyServiceHelper.getxCartOrderTableDBEntry(storeOrderId);
        	additionalChargeId = (long) xCartOrderEntry.get("additional_charges_seller_id");
        	sft.assertEquals(additionalChargeId, sellerId,"Additional charges are not applied on correct seller in Bounty");
        }
    	sft.assertAll();

	}
		
}
	
