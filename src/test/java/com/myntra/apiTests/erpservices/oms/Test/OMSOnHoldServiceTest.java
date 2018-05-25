package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.ONHOLDServiceDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ats.helper.ATSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.sellerapi.service.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.worms.client.entry.OrderCaptureReleaseEntry;
import com.myntra.worms.client.response.OrderCaptureReleaseResponse;

public class OMSOnHoldServiceTest extends BaseTest{

	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private String uidx;
    private String login = OMSTCConstants.LoginAndPassword.OMSOnHoldServiceTestLogin;
	private String password = OMSTCConstants.LoginAndPassword.OMSOnHoldServiceTestPassword;
	private OrderEntry orderEntry;
   // private String login = "end2endautomation1@gmail.com";
	private String orderReleaseId;
	private static Long vectorSellerID;
	SoftAssert sft;
	private String supplyTypeOnHand = "ON_HAND";
	private Long defaultWaitTime = 10000L;

	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException {
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=1 where id in (35,23,37);", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE myntra_tools.core_application_properties SET value='10002' where name='condCODOH.unpaidamount.limit';", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE myntra_tools.core_application_properties SET value='true' where name='oms.knight_enable_dynamic_cod';", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE mk_feature_gate_key_value_pairs SET value='1-10003' where `key`='cod.limit.range';", "myntra");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":1000:0:"+vectorSellerID},supplyTypeOnHand);
        end2EndHelper.refreshToolsApplicationPropertyCache();
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
        End2EndHelper.sleep(defaultWaitTime);
	}

	@AfterClass(alwaysRun = true)
	public void testAfterClass() throws SQLException{
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23,37);", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE myntra_tools.core_application_properties SET value='20000000' where name='condCODOH.unpaidamount.limit'", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE myntra_tools.core_application_properties SET value='true' where name='oms.knight_enable_dynamic_cod';", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE mk_feature_gate_key_value_pairs SET value='1-10000' where `key`='cod.limit.range';", "myntra");
	}
	
	/**
	 * @param ids
	 * @param isHold
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void updateOnHoldInOnholdReasonsMaster(String ids, int isHold) throws UnsupportedEncodingException, JAXBException{
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled="+isHold+" where id in ("+ids+");", "myntra_oms");
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
	}

    @Test(enabled=true, dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"}, description="RTD call should fail for ON-HOLD Order")
    public void rtdCallFromSellerShouldFailIfOrderIsOnHoldForSeller() throws Exception {
    	sft = new SoftAssert();
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);

        ATSServiceHelper knightServiceHelper = new ATSServiceHelper();
        String login = "fraudcustomer@myntra.com";
        knightServiceHelper.addEmailToFraudList(login);
        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, new String[] {OMSTCConstants.OtherSkus.skuId_895235+":1"}, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"Verify order should be OnHold");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "23","Verify onhold reasonId");
        OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);
        if ((orderReleaseEntry.getTrackingNo() == null)
                || (orderReleaseEntry.getTrackingNo().equalsIgnoreCase(""))) {
            String trackingNumber = "ML" + System.currentTimeMillis();
            DBUtilities.exUpdateQuery("update order_release set courier_code='ML', tracking_no='" + trackingNumber
                                              + "' where ID=" + orderID, "myntra_oms");
        }
        End2EndHelper.sleep(defaultWaitTime);
        SellerApiHelper sellerApiHelper = new SellerApiHelper();
        sellerApiHelper.updateTax(orderID, new String[] {OMSTCConstants.OtherSkus.skuId_895235+":100:5.25:VAT"});
        OrderResponse orderResponse = sellerApiHelper.readyToDispatch(orderReleaseId, new String[] {OMSTCConstants.OtherSkus.skuId_895235+":1"});
        sft.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR, "RTD call should fail For ON-HOLD Order");
        sft.assertAll();
    }


    @Test(enabled=true, dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"}, description="RTD call should fail for ON-HOLD Order")
    public void rtdCallFromSellerShouldFailIfOrderIsOnHoldForMyntraManagedSeller() throws Exception {
    	sft = new SoftAssert();
    	updateOnHoldInOnholdReasonsMaster("37",0);		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+","+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},supplyTypeOnHand);

        String orderID =  end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, new String[] {"3831:1"}, "", false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        OrderReleaseEntry orderReleaseEntry = orderEntry.getOrderReleases().get(0);

        if ((orderReleaseEntry.getTrackingNo() == null)
                || (orderReleaseEntry.getTrackingNo().equalsIgnoreCase(""))) {
            String trackingNumber = "ML" + System.currentTimeMillis();
            DBUtilities.exUpdateQuery("update order_release set courier_code='ML', tracking_no='" + trackingNumber
                                              + "' where ID=" + orderID, "myntra_oms");
        }
        End2EndHelper.sleep(defaultWaitTime);
        omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);

        String warehouseID = ""+orderReleaseEntry.getWarehouseId();

        DBUtilities.exUpdateQuery("update capture_order_release set is_on_hold=0 where portal_order_release_id="+orderReleaseId, "myntra_wms");
        wmsServiceHelper.pushOrderToWave(orderReleaseId);
        
        if (!wmsServiceHelper.validateOrderInCoreOrderRelease(orderReleaseId, 15)) {
        	sft.assertTrue(false, "Order not pushed to Order wave, Please check WMS/Worms log");
        }

        // Item CheckOut in WMS
        wmsServiceHelper.orderItemsCheckout(orderReleaseId, warehouseID);
        End2EndHelper.sleep(defaultWaitTime);

        wmsServiceHelper.markItemQADONE(orderReleaseId);
        DBUtilities.exUpdateQuery("Update orders set is_on_hold=1,on_hold_reason_id_fk=23 where id ="+orderID+";", "myntra_oms");
        OrderCaptureReleaseResponse res = wmsServiceHelper.markReleasePacked(orderReleaseId);
        sft.assertEquals(res.getStatus().getStatusType(), StatusResponse.Type.ERROR,"Verify Error response in capture order release Actual:"+res.getStatus().getStatusType());
        updateOnHoldInOnholdReasonsMaster("37",1);
        sft.assertAll();
    }


    @Test(enabled=true, dataProvider="oneOrderSingleItemHavingCODLimitExceededPPSDP", dataProviderClass=ONHOLDServiceDP.class,groups={"smoke", "regression","OnHold"}, description="Resolve COD On-HOLD Order")
	public void resolveOnHoldCODOrder(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
    	sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		omsServiceHelper.resolveOnholdOrders(orderID);
		End2EndHelper.sleep(2*defaultWaitTime);
		orderEntry =  omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(Boolean.FALSE, orderEntry.getOnHold(), "Order status should not be ON-HOLD");
		sft.assertAll();
	}


	@Test(enabled=true, dataProvider="oneOrderSingleItemHavingCODLimitExceededPPSDP", dataProviderClass=ONHOLDServiceDP.class,groups={ "regression","OnHold"})
	public void oneOrderSingleItemHavingPendingCODLimitExceededPPS(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		End2EndHelper.sleep(2*defaultWaitTime);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="oneOrderSingleItemHavingCODLimitExceededPPSDP", dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"})
	public void resolveONHoldForOneOrderSingleItemHavingPendingCODLimitExceededPPS(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "35", "ON-HOLD Reason ID is not 35");
		omsServiceHelper.resolveOnholdOrders(orderID);
		End2EndHelper.sleep(2*defaultWaitTime);
		orderEntry =  omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(Boolean.FALSE, orderEntry.getOnHold(), "Order status should not be ON-HOLD");
		sft.assertAll();
	}


	@Test(enabled=true, dataProvider="oneOrderSingleReleaseHavingMultipleLinesCODLimitExcededPPSDP", dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"})
	public void oneOrderSingleReleaseHavingMultipleLinesACODLimitExceededPPS(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}

	@Test(enabled=true, dataProvider="oneOrdermultipleItemsHavingCODLimitExceededPPSDP", dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"})
	public void oneOrderMultipleItemsHavingCODLimitExceededPPS(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		End2EndHelper.sleep(2*defaultWaitTime);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="multipleOrdersHavingCODLimitExceededDP", dataProviderClass=ONHOLDServiceDP.class,groups={ "regression","OnHold"})
	public void multipleOrdersHavingCODLimitExceeded(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		End2EndHelper.sleep(defaultWaitTime);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="multipleOrdersHavingCODLimitExceededIncludingShippingChargesDP", dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"})
	public void multipleOrdersHavingCODLimitExceededIncludingShippingCharges(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}

	
	@Test(enabled=true, dataProvider="multipleOrdersWithOneOrdersReleaseCancelledDP", dataProviderClass=ONHOLDServiceDP.class,groups={"regression","OnHold"})
	public void multipleOrdersWithOneOrdersReleaseCancelled(String orderID) throws JAXBException, SQLException, IOException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
       
		sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Verify there is no Onhold on order");
		updateOnHoldInOnholdReasonsMaster("35",1);
		String order_pps_70042184 = "<order><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-18T15:37:56+05:30</createdOn><id>70042184</id><lastModifiedOn>2016-02-18T15:38:01+05:30</lastModifiedOn><version>2</version><cartDiscount>0</cartDiscount><cashBackCouponCode></cashBackCouponCode><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><channel>web</channel><codCharge>0</codCharge><couponCode></couponCode><couponDiscount>0</couponDiscount><customerName>Abhijit Pati</customerName><discount>0</discount><earnedCredit>0</earnedCredit><emiCharge>0</emiCharge><finalAmount>799</finalAmount><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><giftOrder>false</giftOrder><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mrpTotal>799</mrpTotal><notes></notes><onHold>false</onHold><orderProcessingFlowMode>OMS</orderProcessingFlowMode><orderReleases><orderRelease><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-18T15:37:56+05:30</createdOn><id>70042184</id><lastModifiedOn>2016-02-18T15:38:02+05:30</lastModifiedOn><version>8</version><address>Myntra Design Pvt. Ltd.</address><addressId>6118982</addressId><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><city>Bangalore</city><codCharge>0</codCharge><codPaymentStatus>pending</codPaymentStatus><country>India</country><couponDiscount>0</couponDiscount><courierCode>ML</courierCode><courierDisplayName>Myntra Logistics</courierDisplayName><customerPromiseTime>2016-02-24T17:00:00+05:30</customerPromiseTime><discount>0</discount><earnedCredit>0</earnedCredit><email>end2endautomation1@gmail.com</email><emiCharge>0</emiCharge><expectedCutoffTime>2016-02-22T17:00:00+05:30</expectedCutoffTime><expectedDeliveryPromise>2016-02-24T17:00:00+05:30</expectedDeliveryPromise><expectedPackingTime>2016-02-21T04:59:01+05:30</expectedPackingTime><expectedPickingTime>2016-02-19T16:59:01+05:30</expectedPickingTime><expectedQCTime>2016-02-20T10:59:01+05:30</expectedQCTime><finalAmount>799</finalAmount><gift>false</gift><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><locality>Begur</locality><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mobile>2121212121</mobile><mrpTotal>799</mrpTotal><onHold>false</onHold><orderId>70042184</orderId><orderLines><orderLine><createdBy>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</createdBy><createdOn>2016-02-18T15:37:56+05:30</createdOn><id>52699330</id><lastModifiedOn>2016-02-18T15:38:17+05:30</lastModifiedOn><version>2</version><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><couponDiscount>0</couponDiscount><customizedMessage></customizedMessage><discount>0</discount><discountRuleId>0</discountRuleId><discountRuleRevId>0</discountRuleRevId><discountedProduct>false</discountedProduct><discountedQuantity>0</discountedQuantity><earnedCredit>0</earnedCredit><finalAmount>799</finalAmount><fragile>false</fragile><giftCardAmount>0</giftCardAmount><govtTaxAmount>0</govtTaxAmount><govtTaxRate>5.5</govtTaxRate><hazMat>false</hazMat><isCustomizable>false</isCustomizable><isDiscountedProduct>false</isDiscountedProduct><isExchangeableProduct>true</isExchangeableProduct><isFragile>false</isFragile><isHazMat>false</isHazMat><isJewellery>false</isJewellery><isReturnableProduct>true</isReturnableProduct><jewellery>false</jewellery><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsAwarded>0</loyaltyPointsAwarded><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><optionId>4853</optionId><orderId>70042184</orderId><orderReleaseId>70042184</orderReleaseId><packagingStatus>NOT_PACKAGED</packagingStatus><packagingType>NORMAL</packagingType><pgDiscount>0</pgDiscount><poStatus>UNUSED</poStatus><quantity>1</quantity><sellerId>1</sellerId><skuId>3832</skuId><status>A</status><statusDisplayName>Assigned</statusDisplayName><storedCredit>0</storedCredit><styleId>1531</styleId><supplyType>ON_HAND</supplyType><taxAmount>0</taxAmount><taxRate>5.5</taxRate><unitPrice>799</unitPrice></orderLine></orderLines><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><personalized>false</personalized><pgDiscount>0</pgDiscount><queuedOn>2016-02-18T15:38:01+05:30</queuedOn><receiverName>Abhijit Pati </receiverName><refunded>false</refunded><sellerProcessingEndTime>2016-02-20T20:26:02+05:30</sellerProcessingEndTime><sellerProcessingStartTime>2016-02-18T15:38:02+05:30</sellerProcessingStartTime><shippingCharge>0</shippingCharge><shippingMethod>NORMAL</shippingMethod><singleItemRelease>false</singleItemRelease><specialPincode>false</specialPincode><state>KA</state><status>Q</status><statusDisplayName>Queued</statusDisplayName><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><trackingNo>ML0008933109</trackingNo><userContactNo>9439543863</userContactNo><warehouseId>1</warehouseId><zipcode>560068</zipcode></orderRelease></orderReleases><orderType>on</orderType><orderTypeDisplay>Online</orderTypeDisplay><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><paymentPpsId>1529db8e-8890-46f9-8e37-cc5c47852a61</paymentPpsId><pgDiscount>0</pgDiscount><queuedOn>2016-02-18T15:38:01+05:30</queuedOn><shippingCharge>0</shippingCharge><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><totalOrderValue>799</totalOrderValue><userContactNo>9439543863</userContactNo></order>";
		String payment_Plan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) VALUES ('1529db8e-8890-46f9-8e37-cc5c47852a61', 'PPS Plan created', 'SYSTEM', 1455475123554, 'SALE', NULL, NULL, 'end2endautomation1@gmail.com', '70042184', 'ORDER', '1529db8e-8890-46f9-8e37-cc5c47852a61--s2', 'PPFSM Order Taking done', 'JJN00410ae414c2049408ba76c45437d8968bf1455475051M', 'DEFAULT', 79900, NULL, NULL, NULL, '1.1.1.1');";
		String payment_plan_item = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES (99941413, 'Payment Plan Item created', 'SYSTEM', 1455790076235, 'SKU', 79900, 1, '1', '3832', '1529db8e-8890-46f9-8e37-cc5c47852a61');";
		String payment_plan_item_instrument = "INSERT INTO `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) VALUES (28588, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1455790076235, 79900, 5, 99941413, NULL);";
		String payment_plan_instrument_Details = "INSERT INTO `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES (19988, 'PPS Plan Instrument Details created', 'SYSTEM', 1455790076234, 5, 79900, '1529db8e-8890-46f9-8e37-cc5c47852a61', 9182720, 'DEBIT', NULL);";

		DBUtilities.exUpdateQuery(payment_Plan, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item_instrument, "pps");
		DBUtilities.exUpdateQuery(payment_plan_instrument_Details, "pps");
		
		omsServiceHelper.createOrderInOMS(order_pps_70042184);
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		End2EndHelper.sleep(defaultWaitTime);
		orderEntry =omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="multipleOrdersWithOneOrdersLinCancelledDP", dataProviderClass=ONHOLDServiceDP.class,groups={ "regression","OnHold"})
	public void evaluateCODOnHoldRuleForMultipleOrdersWithOneOrdersLineItemQuantityCancelled(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE, "Order should not be in ON-HOLD Status");
		updateOnHoldInOnholdReasonsMaster("37", 1);
		
		String order_pps_70042270 = "<order><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>70042270</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>3</version><cartDiscount>0</cartDiscount><cashBackCouponCode></cashBackCouponCode><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><channel>web</channel><codCharge>0</codCharge><couponCode></couponCode><couponDiscount>0</couponDiscount><customerName>END2END Automation1</customerName><discount>0</discount><earnedCredit>0</earnedCredit><emiCharge>0</emiCharge><finalAmount>799</finalAmount><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><giftOrder>false</giftOrder><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mrpTotal>799</mrpTotal><notes></notes><onHold>false</onHold><orderProcessingFlowMode>OMS</orderProcessingFlowMode><orderReleases><orderRelease><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>70042270</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>9</version><address>Myntra Design, AKR B</address><addressId>6118982</addressId><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><city>Bangalore</city><codCharge>0</codCharge><codPaymentStatus>pending</codPaymentStatus><country>India</country><couponDiscount>0</couponDiscount><courierCode>ML</courierCode><courierDisplayName>Myntra Logistics</courierDisplayName><customerPromiseTime>2016-02-25T17:00:00+05:30</customerPromiseTime><discount>0</discount><earnedCredit>0</earnedCredit><email>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</email><emiCharge>0</emiCharge><expectedCutoffTime>2016-02-23T17:00:00+05:30</expectedCutoffTime><expectedDeliveryPromise>2016-02-25T17:00:00+05:30</expectedDeliveryPromise><expectedPackingTime>2016-02-22T04:59:42+05:30</expectedPackingTime><expectedPickingTime>2016-02-20T16:59:42+05:30</expectedPickingTime><expectedQCTime>2016-02-21T10:59:42+05:30</expectedQCTime><finalAmount>799</finalAmount><gift>false</gift><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><locality>Bommanahalli  &#x28;Bangalore&#x29;</locality><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mobile>9439543863</mobile><mrpTotal>799</mrpTotal><onHold>false</onHold><orderId>70042270</orderId><orderLines><orderLine><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>52699500</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>3</version><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><couponDiscount>0</couponDiscount><customizedMessage></customizedMessage><discount>0</discount><discountRuleId>0</discountRuleId><discountRuleRevId>0</discountRuleRevId><discountedProduct>false</discountedProduct><discountedQuantity>0</discountedQuantity><earnedCredit>0</earnedCredit><finalAmount>799</finalAmount><fragile>false</fragile><giftCardAmount>0</giftCardAmount><govtTaxAmount>41.65</govtTaxAmount><govtTaxRate>5.5</govtTaxRate><govtTaxType>VAT</govtTaxType><govtTaxableAmount>757.35</govtTaxableAmount><hazMat>false</hazMat><isCustomizable>false</isCustomizable><isDiscountedProduct>false</isDiscountedProduct><isExchangeableProduct>true</isExchangeableProduct><isFragile>false</isFragile><isHazMat>false</isHazMat><isJewellery>false</isJewellery><isReturnableProduct>true</isReturnableProduct><jewellery>false</jewellery><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsAwarded>0</loyaltyPointsAwarded><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><optionId>4853</optionId><orderId>70042270</orderId><orderReleaseId>70042270</orderReleaseId><packagingStatus>NOT_PACKAGED</packagingStatus><packagingType>NORMAL</packagingType><pgDiscount>0</pgDiscount><poStatus>UNUSED</poStatus><quantity>1</quantity><sellerId>1</sellerId><skuId>3832</skuId><status>A</status><statusDisplayName>Assigned</statusDisplayName><storedCredit>0</storedCredit><styleId>1531</styleId><supplyType>ON_HAND</supplyType><taxAmount>0</taxAmount><taxRate>5.5</taxRate><unitPrice>799</unitPrice></orderLine></orderLines><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><personalized>false</personalized><pgDiscount>0</pgDiscount><queuedOn>2016-02-19T18:51:42+05:30</queuedOn><receiverName>END2END Automation1 </receiverName><refunded>false</refunded><sellerProcessingEndTime>2016-02-21T23:39:43+05:30</sellerProcessingEndTime><sellerProcessingStartTime>2016-02-19T18:51:43+05:30</sellerProcessingStartTime><shippingCharge>0</shippingCharge><shippingMethod>NORMAL</shippingMethod><singleItemRelease>false</singleItemRelease><specialPincode>false</specialPincode><state>KA</state><status>Q</status><statusDisplayName>Queued</statusDisplayName><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><trackingNo>ML0008933115</trackingNo><userContactNo>9439543863</userContactNo><warehouseId></warehouseId><zipcode>560068</zipcode></orderRelease></orderReleases><orderType>on</orderType><orderTypeDisplay>Online</orderTypeDisplay><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><paymentPpsId>d146032d-3cf7-472c-b52f-ac9a0cd396fa</paymentPpsId><pgDiscount>0</pgDiscount><queuedOn>2016-02-19T18:51:42+05:30</queuedOn><shippingCharge>0</shippingCharge><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><totalOrderValue>799</totalOrderValue><userContactNo>9439543863</userContactNo></order>";
		String payment_Plan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) VALUES ('d146032d-3cf7-472c-b52f-ac9a0cd396fa', 'PPS Plan created', 'SYSTEM', 1455888096685, 'SALE', NULL, NULL, 'end2endautomation1@gmail.com', '70042270', 'ORDER', 'a87912a8-4652-4c3e-b9b0-2354983f8da8--s2', 'PPFSM Order Taking done', 'JJN001fa754f49b8814c36be230ae9e5dfd1221455888025M', 'DEFAULT', 79900, NULL, NULL, NULL, '1.1.1.1');";
		String payment_plan_item = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES (99941453, 'Payment Plan Item created', 'SYSTEM', 1455888096915, 'SKU', 79900, 1, '1', '3832', 'd146032d-3cf7-472c-b52f-ac9a0cd396fa');";
		String payment_plan_item_instrument = "INSERT INTO `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) VALUES (28892, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1455888096925, 79900, 5, 99941453, NULL);";
		String payment_plan_instrument_Details = "INSERT INTO `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES (20199, 'PPS Plan Instrument Details created', 'SYSTEM', 1455888096915, 5, 79900, 'd146032d-3cf7-472c-b52f-ac9a0cd396fa', 9182736, 'DEBIT', NULL);";

		DBUtilities.exUpdateQuery(payment_Plan, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item_instrument, "pps");
		DBUtilities.exUpdateQuery(payment_plan_instrument_Details, "pps");
		
		omsServiceHelper.createOrderInOMS(order_pps_70042270);
		omsServiceHelper.evaluateOrderOnholdRules("70042270");
		End2EndHelper.sleep(defaultWaitTime);
		orderEntry = omsServiceHelper.getOrderEntry("70042270");
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		sft.assertAll();
		
	}
	
	@Test(enabled=true, dataProvider="evaluateCODOnHoldCheckOrderHavingmultipleReleasesAndOneReleaseDeliveredDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate COD ON-HOLD Check-  Multiple Order Releases And One of the Order release is in DL",groups={"regression","OnHold"})
	public void evaluateCODOnHoldCheckOrderHavingMultipleReleasesAndOneReleaseDelivered(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE, "Order should not be ON-HOLD status");
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="evaluateCODOnHoldCheckOrderHavingMultipleReleasesAndOneItemExchangedDP", dataProviderClass=ONHOLDServiceDP.class,groups={"smoke", "regression","OnHold"})
	public void evaluateCODOnHoldCheckOrderHavingMultipleReleasesAndOneItemExchanged(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE, "Order should not be ON-HOLD Status");
		String order_pps_70042270 = "<order><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>70042270</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>3</version><cartDiscount>0</cartDiscount><cashBackCouponCode></cashBackCouponCode><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><channel>web</channel><codCharge>0</codCharge><couponCode></couponCode><couponDiscount>0</couponDiscount><customerName>END2END Automation1</customerName><discount>0</discount><earnedCredit>0</earnedCredit><emiCharge>0</emiCharge><finalAmount>799</finalAmount><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><giftOrder>false</giftOrder><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mrpTotal>799</mrpTotal><notes></notes><onHold>false</onHold><orderProcessingFlowMode>OMS</orderProcessingFlowMode><orderReleases><orderRelease><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>70042270</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>9</version><address>Myntra Design, AKR B</address><addressId>6118982</addressId><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><city>Bangalore</city><codCharge>0</codCharge><codPaymentStatus>pending</codPaymentStatus><country>India</country><couponDiscount>0</couponDiscount><courierCode>ML</courierCode><courierDisplayName>Myntra Logistics</courierDisplayName><customerPromiseTime>2016-02-25T17:00:00+05:30</customerPromiseTime><discount>0</discount><earnedCredit>0</earnedCredit><email>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</email><emiCharge>0</emiCharge><expectedCutoffTime>2016-02-23T17:00:00+05:30</expectedCutoffTime><expectedDeliveryPromise>2016-02-25T17:00:00+05:30</expectedDeliveryPromise><expectedPackingTime>2016-02-22T04:59:42+05:30</expectedPackingTime><expectedPickingTime>2016-02-20T16:59:42+05:30</expectedPickingTime><expectedQCTime>2016-02-21T10:59:42+05:30</expectedQCTime><finalAmount>799</finalAmount><gift>false</gift><giftCardAmount>0</giftCardAmount><giftCharge>0</giftCharge><locality>Bommanahalli  &#x28;Bangalore&#x29;</locality><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><mobile>9439543863</mobile><mrpTotal>799</mrpTotal><onHold>false</onHold><orderId>70042270</orderId><orderLines><orderLine><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-19T18:51:37+05:30</createdOn><id>52699500</id><lastModifiedOn>2016-02-19T18:53:44+05:30</lastModifiedOn><version>3</version><cartDiscount>0</cartDiscount><cashRedeemed>0</cashRedeemed><cashbackOffered>0</cashbackOffered><couponDiscount>0</couponDiscount><customizedMessage></customizedMessage><discount>0</discount><discountRuleId>0</discountRuleId><discountRuleRevId>0</discountRuleRevId><discountedProduct>false</discountedProduct><discountedQuantity>0</discountedQuantity><earnedCredit>0</earnedCredit><finalAmount>799</finalAmount><fragile>false</fragile><giftCardAmount>0</giftCardAmount><govtTaxAmount>41.65</govtTaxAmount><govtTaxRate>5.5</govtTaxRate><govtTaxType>VAT</govtTaxType><govtTaxableAmount>757.35</govtTaxableAmount><hazMat>false</hazMat><isCustomizable>false</isCustomizable><isDiscountedProduct>false</isDiscountedProduct><isExchangeableProduct>true</isExchangeableProduct><isFragile>false</isFragile><isHazMat>false</isHazMat><isJewellery>false</isJewellery><isReturnableProduct>true</isReturnableProduct><jewellery>false</jewellery><loyaltyConversionFactor>0</loyaltyConversionFactor><loyaltyPointsAwarded>0</loyaltyPointsAwarded><loyaltyPointsCredit>0</loyaltyPointsCredit><loyaltyPointsUsed>0</loyaltyPointsUsed><optionId>4853</optionId><orderId>70042270</orderId><orderReleaseId>70042270</orderReleaseId><packagingStatus>NOT_PACKAGED</packagingStatus><packagingType>NORMAL</packagingType><pgDiscount>0</pgDiscount><poStatus>UNUSED</poStatus><quantity>1</quantity><sellerId>1</sellerId><skuId>3832</skuId><status>A</status><statusDisplayName>Assigned</statusDisplayName><storedCredit>0</storedCredit><styleId>1531</styleId><supplyType>ON_HAND</supplyType><taxAmount>0</taxAmount><taxRate>5.5</taxRate><unitPrice>799</unitPrice></orderLine></orderLines><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><personalized>false</personalized><pgDiscount>0</pgDiscount><queuedOn>2016-02-19T18:51:42+05:30</queuedOn><receiverName>END2END Automation1 </receiverName><refunded>false</refunded><sellerProcessingEndTime>2016-02-21T23:39:43+05:30</sellerProcessingEndTime><sellerProcessingStartTime>2016-02-19T18:51:43+05:30</sellerProcessingStartTime><shippingCharge>0</shippingCharge><shippingMethod>NORMAL</shippingMethod><singleItemRelease>false</singleItemRelease><specialPincode>false</specialPincode><state>KA</state><status>Q</status><statusDisplayName>Queued</statusDisplayName><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><trackingNo>ML0008933115</trackingNo><userContactNo>9439543863</userContactNo><warehouseId></warehouseId><zipcode>560068</zipcode></orderRelease></orderReleases><orderType>on</orderType><orderTypeDisplay>Online</orderTypeDisplay><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><paymentPpsId>d146032d-3cf7-472c-b52f-ac9a0cd396fa</paymentPpsId><pgDiscount>0</pgDiscount><queuedOn>2016-02-19T18:51:42+05:30</queuedOn><shippingCharge>0</shippingCharge><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0</storedCredit><taxAmount>0</taxAmount><totalOrderValue>799</totalOrderValue><userContactNo>9439543863</userContactNo></order>";
		String payment_Plan = "INSERT INTO `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`) VALUES ('d146032d-3cf7-472c-b52f-ac9a0cd396fa', 'PPS Plan created', 'SYSTEM', 1455888096685, 'SALE', NULL, NULL, 'end2endautomation1@gmail.com', '70042270', 'ORDER', 'a87912a8-4652-4c3e-b9b0-2354983f8da8--s2', 'PPFSM Order Taking done', 'JJN001fa754f49b8814c36be230ae9e5dfd1221455888025M', 'DEFAULT', 79900, NULL, NULL, NULL, '1.1.1.1');";
		String payment_plan_item = "INSERT INTO `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) VALUES (99941453, 'Payment Plan Item created', 'SYSTEM', 1455888096915, 'SKU', 79900, 1, '1', '3832', 'd146032d-3cf7-472c-b52f-ac9a0cd396fa');";
		String payment_plan_item_instrument = "INSERT INTO `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) VALUES (28892, 'Payment Plan Item Instrument Detail created', 'SYSTEM', 1455888096925, 79900, 5, 99941453, NULL);";
		String payment_plan_instrument_Details = "INSERT INTO `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) VALUES (20199, 'PPS Plan Instrument Details created', 'SYSTEM', 1455888096915, 5, 79900, 'd146032d-3cf7-472c-b52f-ac9a0cd396fa', 9182736, 'DEBIT', NULL);";

		DBUtilities.exUpdateQuery(payment_Plan, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item, "pps");
		DBUtilities.exUpdateQuery(payment_plan_item_instrument, "pps");
		DBUtilities.exUpdateQuery(payment_plan_instrument_Details, "pps");
		
		omsServiceHelper.createOrderInOMS(order_pps_70042270);
		omsServiceHelper.evaluateOrderOnholdRules("70042270");
		End2EndHelper.sleep(defaultWaitTime);
		
		orderEntry=omsServiceHelper.getOrderEntry("70042270");
		System.out.println("orderEntry :"+orderEntry.getOnHold());
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"On Hold value should be True.");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35");
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "35", "ON-HOLD Reason ID is not 35");
		updateOnHoldInOnholdReasonsMaster("37", 1);
		sft.assertAll();
		
	}
	
	@Test(enabled=true, dataProvider="evaluateCODOnHoldRuleForMixedPaymentInstrumentPPSDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate COD ON-Hold Rule for Mixed Payemnt Instument mode - PPS",groups={"regression","OnHold"})
	public void evaluateCODOnHoldRuleForMixedPaymentInstrumentPPS(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException{
		sft = new SoftAssert();
		omsServiceHelper.evaluateOrderOnholdRules(orderID);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Verify order is not in hold status");
		
		String order_pps_70041630 = "<order><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-15T00:29:10+05:30</createdOn><id>70041630</id><lastModifiedOn>2016-02-15T00:29:15+05:30</lastModifiedOn><version>0</version><cartDiscount>0.0</cartDiscount><cashBackCouponCode></cashBackCouponCode><cashRedeemed>0.0</cashRedeemed><cashbackOffered>0.0</cashbackOffered><channel>web</channel><codCharge>0.0</codCharge><couponCode></couponCode><couponDiscount>0.0</couponDiscount><customerName>abhijit</customerName><discount>0.0</discount><earnedCredit>0.0</earnedCredit><emiCharge>0.0</emiCharge><finalAmount>2299.0</finalAmount><giftCardAmount>0.0</giftCardAmount><giftCharge>0.0</giftCharge><giftOrder>false</giftOrder><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><mrpTotal>2299.0</mrpTotal><notes></notes><onHold>false</onHold><orderProcessingFlowMode>OMS</orderProcessingFlowMode><orderReleases><orderRelease><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-15T00:29:10+05:30</createdOn><id>70041630</id><lastModifiedOn>2016-02-15T00:29:16+05:30</lastModifiedOn><version>0</version><address>bangalore</address><addressId>6129093</addressId><cartDiscount>0.0</cartDiscount><cashRedeemed>0.0</cashRedeemed><city>Bangalore</city><codCharge>0.0</codCharge><codPaymentStatus>pending</codPaymentStatus><country>India</country><couponDiscount>0.0</couponDiscount><courierCode>ML</courierCode><courierDisplayName>Myntra Logistics</courierDisplayName><customerPromiseTime>2016-02-23T13:01:02+05:30</customerPromiseTime><discount>0.0</discount><earnedCredit>0.0</earnedCredit><email>end2endautomation1@gmail.com</email><emiCharge>0.0</emiCharge><expectedCutoffTime>2016-02-18T23:01:02+05:30</expectedCutoffTime><expectedDeliveryPromise>2016-02-23T13:01:02+05:30</expectedDeliveryPromise><expectedPackingTime>2016-02-17T11:00:15+05:30</expectedPackingTime><expectedPickingTime>2016-02-15T23:00:15+05:30</expectedPickingTime><expectedQCTime>2016-02-16T17:00:15+05:30</expectedQCTime><finalAmount>2299.0</finalAmount><gift>false</gift><giftCardAmount>0.0</giftCardAmount><giftCharge>0.0</giftCharge><locality>Hongasandra</locality><login>8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R</login><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><mobile>4343434343</mobile><mrpTotal>2299.0</mrpTotal><onHold>false</onHold><orderId>70041630</orderId><orderLines><orderLine><createdBy>end2endautomation1@gmail.com</createdBy><createdOn>2016-02-15T00:29:10+05:30</createdOn><id>52698242</id><lastModifiedOn>2016-02-15T00:29:28+05:30</lastModifiedOn><version>0</version><cartDiscount>0.0</cartDiscount><cashRedeemed>0.0</cashRedeemed><cashbackOffered>0.0</cashbackOffered><couponDiscount>0.0</couponDiscount><customizedMessage></customizedMessage><discount>0.0</discount><discountRuleId>0</discountRuleId><discountRuleRevId>0</discountRuleRevId><discountedProduct>false</discountedProduct><discountedQuantity>0</discountedQuantity><earnedCredit>0.0</earnedCredit><finalAmount>2299.0</finalAmount><fragile>false</fragile><giftCardAmount>0.0</giftCardAmount><govtTaxAmount>0.0</govtTaxAmount><govtTaxRate>5.5</govtTaxRate><hazMat>false</hazMat><isCustomizable>false</isCustomizable><isDiscountedProduct>false</isDiscountedProduct><isExchangeableProduct>true</isExchangeableProduct><isFragile>false</isFragile><isHazMat>false</isHazMat><isJewellery>false</isJewellery><isReturnableProduct>true</isReturnableProduct><jewellery>false</jewellery><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsAwarded>0.0</loyaltyPointsAwarded><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><optionId>202965</optionId><orderId>70041630</orderId><orderReleaseId>70041630</orderReleaseId><packagingStatus>NOT_PACKAGED</packagingStatus><packagingType>NORMAL</packagingType><pgDiscount>0.0</pgDiscount><poStatus>UNUSED</poStatus><quantity>1</quantity><sellerId>73</sellerId><skuId>209214</skuId><status>A</status><statusDisplayName>Assigned</statusDisplayName><storedCredit>0.0</storedCredit><styleId>53784</styleId><supplyType>ON_HAND</supplyType><taxAmount>0.0</taxAmount><taxRate>5.5</taxRate><unitPrice>2299.0</unitPrice></orderLine></orderLines><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><personalized>false</personalized><pgDiscount>0.0</pgDiscount><queuedOn>2016-02-15T00:29:15+05:30</queuedOn><receiverName>abhijit </receiverName><refunded>false</refunded><sellerProcessingEndTime>2016-02-17T05:17:16+05:30</sellerProcessingEndTime><sellerProcessingStartTime>2016-02-15T00:29:16+05:30</sellerProcessingStartTime><shippingCharge>0.0</shippingCharge><shippingMethod>NORMAL</shippingMethod><singleItemRelease>false</singleItemRelease><specialPincode>false</specialPincode><state>KA</state><status>Q</status><statusDisplayName>Queued</statusDisplayName><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0.0</storedCredit><taxAmount>0.0</taxAmount><trackingNo>ML0008926157</trackingNo><userContactNo>9439543863</userContactNo><warehouseId>1</warehouseId><zipcode>560068</zipcode></orderRelease></orderReleases><orderType>on</orderType><orderTypeDisplay>Online</orderTypeDisplay><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><pgDiscount>0.0</pgDiscount><queuedOn>2016-02-15T00:29:15+05:30</queuedOn><shippingCharge>0.0</shippingCharge><sellerId>1</sellerId><storeId>1</storeId><storedCredit>0.0</storedCredit><taxAmount>0.0</taxAmount><totalOrderValue>2299.0</totalOrderValue><userContactNo>9439543863</userContactNo></order>";
		
		omsServiceHelper.createOrderInOMS(order_pps_70041630);
	    omsServiceHelper.evaluateOrderOnholdRules("70041630");
	    End2EndHelper.sleep(defaultWaitTime);
	    orderEntry = omsServiceHelper.getOrderEntry("70041630");
	    System.out.println("orderEntry getOnHold :"+orderEntry.getOnHold());
	    sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
	    sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "35", "ON-HOLD Reason ID is not 35");
	    sft.assertAll();
	}	

	@Test(enabled=true, dataProvider="evaluateCODOnHoldRuleForOnlineOrdersDP", dataProviderClass=ONHOLDServiceDP.class, description = "Online Order should not Go On-Hold",groups={"regression","OnHold"})
	public void evaluateCODOnHoldRuleForOnlineOrders(String orderID) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		OrderEntry orderEntry = omsServiceHelper.evaluateOrderOnholdRules(orderID);
		sft.assertEquals(Boolean.FALSE, orderEntry.getOnHold(),"Verify order is not on hold");
		sft.assertAll();
	}

	@Test(enabled=true, dataProvider="fraudUserONHoldOrderShouldFlowToWMSDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate OnHold rule when email id of Fraud User is in fraud List",groups={"regression","OnHold"})
	public void validateOnHoldRuleForFraudUser_Email(String orderID) throws UnsupportedEncodingException, JAXBException {
        sft = new SoftAssert();
        End2EndHelper.sleep(2*defaultWaitTime);
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(), "23", "ON-HOLD Reason ID is not 23");
        sft.assertAll();
	}


	@Test(enabled=true, dataProvider="fraudUserONHoldOrderShouldFlowToWMSDP", dataProviderClass=ONHOLDServiceDP.class, description = "Resolve Fraud User ON-Hold",groups={"regression","OnHold"})
	public void resolveOnHoldRuleForFraudUser_Email(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
	    sft = new SoftAssert();
	    End2EndHelper.sleep(defaultWaitTime);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "23", "ON-HOLD Reason ID is not 23");
		omsServiceHelper.resolveOnholdOrders(orderID);
		End2EndHelper.sleep(defaultWaitTime);
		OrderEntry orderentry =  omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        sft.assertEquals(orderentry.getOnHold(),Boolean.FALSE, "On-Hold Should be resolved for the Order "+orderID);
        OrderCaptureReleaseEntry orderCaptureReleaseEntry = wmsServiceHelper.getCaptureOrderReleaseData(orderReleaseId);
        sft.assertEquals(orderCaptureReleaseEntry.getIsOnHold(), Boolean.FALSE, "Order Should go Off-Hold In Capture Order Service "+orderID);
        sft.assertAll();
	}

/*
	@Test(enabled=true, dataProvider="validateOnHoldRuleForFraudUserPhoneDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate OnHold rule when Phone of Fraud User is in fraud List",groups={"smoke", "regression","OnHold"})
	public void validateOnHoldRuleForFraudUser_Phone(String orderID) throws UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is not ON-Hold");
        sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "23","On-Hold Reason ID is not 23");
        sft.assertAll();
	}


	@Test(enabled=true, dataProvider="validateOnHoldRuleForFraudUserPhoneDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate OnHold rule when Phone of Fraud User is in fraud List",groups={"regression","OnHold"})
	public void fraudUserONHoldOrderShouldFlowToWMS(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException {
		sft = new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId = orderEntry.getOrderReleases().get(0).getId();
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is not ON-Hold");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "23","On-Hold Reason ID is not 23");
        HashMap captureOrderReleaseEntry = (HashMap) DBUtilities.exSelectQueryForSingleRecord("select * from capture_order_release where portal_order_release_id = "+orderReleaseId, "myntra_wms");
        sft.assertEquals(""+captureOrderReleaseEntry.get("is_on_hold"), "true","Order Is not ON-HOLD in capture_order_release");

        //Cancel On-Hold Order
        omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", "fraud.customer@myntra.com", "TestOrder Cancellation");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"F");
        OrderCaptureReleaseEntry orderCaptureReleaseEntry = wmsServiceHelper.getCaptureOrderReleaseData(orderReleaseId);
        sft.assertEquals(orderCaptureReleaseEntry.getOrderReleaseStatus(), OrderReleaseStatus.CANCELLED, "Check ON-HOLD order should be cancelled from WORMS");
        sft.assertAll();
	}
*/
	@Test(enabled=true, dataProvider="duplicateOrderOnHoldRuleShouldGoToWPStatusDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate OnHold rule when Phone of Fraud User is in fraud List",groups={"regression","OnHold"})
	public void validateOnHoldRuleForDuplicateSingleItemOrder(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
		sft = new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "37", "ON-HOLD Reason ID is not 37");
		updateOnHoldInOnholdReasonsMaster("35", 1);
		sft.assertAll();
	}

	@Test(enabled=true, dataProvider="duplicateOrderOnHoldRuleShouldGoToWPStatusDP", dataProviderClass=ONHOLDServiceDP.class, description = "Duplicate Order OnHold should go to WP Status",groups={"regression","OnHold"})
	public void duplicateOrderOnHoldRuleShouldGoToWPStatus(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException {
        sft = new SoftAssert();
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		boolean orderStatusWP = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15);
        boolean orderStatusRFR = false;
        if(!orderStatusWP){
            orderStatusRFR = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "RFR", 15);
           }
        End2EndHelper.sleep(defaultWaitTime);
		OrderEntry orderEntry =omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order Should go ON-HOLD in Order Table");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "37", "OnHold Reason ID should be 37");
		sft.assertTrue((orderStatusWP|orderStatusRFR), "Order Status is not in RFR or WP");
		if(orderStatusWP){
			HashMap captureOrderReleaseEntry = (HashMap) DBUtilities.exSelectQueryForSingleRecord("select * from capture_order_release where portal_order_release_id = "+orderReleaseId, "myntra_wms");
			sft.assertEquals(""+captureOrderReleaseEntry.get("is_on_hold"), "true", "Order is ON-HOLD in WORMS capture Order Release");
		}
		sft.assertAll();
		
	}

	@Test(enabled=true, dataProvider="duplicateOrderOnHoldRuleShouldGoToWPStatusMultipleQuantityDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate OnHold rule when Phone of Fraud User is in fraud List",groups={"regression","OnHold"})
	public void validateOnHoldRuleForDuplicateMultipleItemOrder(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
	    sft = new SoftAssert();
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE, "Order is Not ON-HOLD");
		sft.assertEquals(orderEntry.getOnHoldReasonId()+"", "37", "ON-HOLD Reason ID is not 37");
		updateOnHoldInOnholdReasonsMaster("35", 1);
		sft.assertAll();
	}
	
	@Test(enabled=true, dataProvider="validateOnHoldRuleForGiftOrderDP", dataProviderClass=ONHOLDServiceDP.class, description = "Validate Gift Order Should go ON-HOLD If Its from Different WH",groups={"regression","OnHold"})
	public void validateGiftOrderReleaseShouldGoOnHoldIfFromDifferentWareHouses(String orderID) throws UnsupportedEncodingException, JAXBException{
        sft = new SoftAssert();
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		boolean orderStatusWP = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 5);
		sft.assertTrue(orderStatusWP,"Order Status is not in WP");
		OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
		OrderReleaseEntry orderReleaseEntry1 = orderReleases.get(0);
		OrderReleaseEntry orderReleaseEntry2 = orderReleases.get(1);
		sft.assertEquals(orderReleaseEntry1.getOnHold(), Boolean.FALSE,"Verify first release in not on hold");
		sft.assertEquals(orderReleaseEntry2.getOnHold(), Boolean.TRUE,"Verify second release is on hold");
		String status_code = omsServiceHelper.getOrderReleaseStatusFromOMS(orderReleaseEntry1.getId().toString());
		String status_code1 = omsServiceHelper.getOrderReleaseStatusFromOMS(orderReleaseEntry2.getId().toString());
		sft.assertEquals(orderReleaseEntry2.getOnHoldReasonId(), "10", "ORDER IS not ON-HOLD for Gift Order");
		sft.assertTrue((orderStatusWP), "Order Status is not WP");
		sft.assertAll();
	}

    @Test(enabled=true, dataProvider="validateOnHoldRuleForOOSSkus", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate ON-HOLD rule for OOS Item",groups={"regression","OnHold"})
    public void validateOnHoldRuleForOrderReleaseForOOSSKUS(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException, ManagerException {
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
        OrderReleaseEntry orderReleaseEntry1 = orderReleases.get(0);
        sft.assertEquals(orderReleaseEntry1.getOnHold(), Boolean.TRUE, "Order Release ON-HOLD flag should be true, but found " + orderReleaseEntry1.getOnHold());
        sft.assertAll();
    }
    
    @Test(enabled=true, dataProvider="validateOnHoldRuleForOOSSkus", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate ON-HOLD rule for OOS Item",groups={"regression","OnHold"})
    public void resolveOnHoldRuleForOrderReleaseForOOSSKUS(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException, ManagerException {
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        OrderEntry orderEntry =  omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
        OrderReleaseEntry orderReleaseEntry1 = orderReleases.get(0);
        orderReleaseId = orderReleaseEntry1.getId().toString();
        End2EndHelper.sleep(defaultWaitTime);
        sft.assertEquals(orderReleaseEntry1.getOnHold(), Boolean.TRUE, "Order Release ON-HOLD flag should be true, but found " + orderReleaseEntry1.getOnHold());
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3919+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID}, supplyTypeOnHand);
        omsServiceHelper.resolveOnHoldOrderRelease(orderReleaseId);
        End2EndHelper.sleep(defaultWaitTime);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        sft.assertEquals(orderReleaseEntry.getOnHold(), Boolean.FALSE, "Order Release ON-HOLD flag should be false, but found " + orderReleaseEntry.getOnHold());
        sft.assertEquals(orderReleaseEntry.getStatus(), "WP", "Order should move to WP after On-Hold Resolution but found "+ orderReleaseEntry.getStatus());
        sft.assertAll();
    }

    @Test(enabled=true, dataProvider="validateOnHoldRuleForPNS", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate ON-HOLD rule for PNS",groups={"regression","OnHold"})
    public void validateOnHoldRuleForPNS(String orderID) throws UnsupportedEncodingException, JAXBException, SQLException, ManagerException {
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
    	End2EndHelper.sleep(defaultWaitTime);
        OrderEntry orderEntry 	=  omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
        OrderReleaseEntry orderReleaseEntry1 = orderReleases.get(0);
        sft.assertEquals(orderReleaseEntry1.getOnHold(), Boolean.TRUE,"Verify onhold on release");
        sft.assertEquals(""+orderReleaseEntry1.getOnHoldReasonId(), "31","Verify onhold reasonId on release");
        sft.assertAll();
    }

    @Test(enabled=true, dataProvider="onHoldResolveSequenceDP", dataProviderClass=ONHOLDServiceDP.class, description = "Evaluate ON-HOLD rule for PNS",groups={"regression","OnHold"})
	public void verifyOnHoldResolveSequence(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException {
    	sft = new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
    	End2EndHelper.sleep(defaultWaitTime);
        OrderEntry orderEntry 	=  omsServiceHelper.getOrderEntry(orderID);
        List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
        OrderReleaseEntry orderReleaseEntry1 = orderReleases.get(0);
        orderReleaseId = orderReleaseEntry1.getId().toString();
        sft.assertEquals(orderReleaseEntry1.getOnHold(), Boolean.TRUE, "ON-HOLD Check ");
        sft.assertEquals(""+orderReleaseEntry1.getOnHoldReasonId(), "34", "ON-HOLD Reason ID ");
        IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_8006+":"+OMSTCConstants.WareHouseIds.warehouseId1_BA+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+","+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10:0:"+vectorSellerID},supplyTypeOnHand );
        omsServiceHelper.resolveOnHoldOrderRelease(orderReleaseId);
        End2EndHelper.sleep(defaultWaitTime);
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        sft.assertEquals(orderReleaseEntry.getOnHold(), Boolean.TRUE, "ON-HOLD Check ");
        sft.assertEquals(""+orderReleaseEntry.getOnHoldReasonId(), "31", "ON-HOLD Reason ID ");
        DBUtilities.exUpdateQuery("update order_release set zipcode='560068' where order_id_fk="+orderID, "myntra_oms");
        omsServiceHelper.resolveOnHoldOrderRelease(orderReleaseId);
        End2EndHelper.sleep(defaultWaitTime);
        orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        sft.assertEquals(orderReleaseEntry.getOnHold(), Boolean.FALSE, "ON-HOLD Check ");
        sft.assertAll();
	}

    @Test(enabled = true, description = "PPS Order ON-HOld should propagate to WORMS Service", groups={"regression","OnHold"})
    public void verifyPPSONHoldResolveShouldResolveInWorms() throws Exception {
        sft = new SoftAssert();
    	end2EndHelper.updateloyalityAndCashBack(uidx, 0, 300);
        String orderID =  end2EndHelper.createOrder(login, password , OMSTCConstants.Pincodes.PINCODE_560067, new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":2"}, "", true, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        OrderCaptureReleaseEntry orderCaptureReleaseEntry = wmsServiceHelper.getCaptureOrderReleaseData(orderReleaseId);
        sft.assertEquals(orderCaptureReleaseEntry.getIsOnHold(), Boolean.TRUE, "Order Status should be ON-Hold in Capture order release for PPS CB and LP Orders");
        sft.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 20),"Verify onhold reason Id for order");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,  "Order status should not be ON-HOLD");
        orderCaptureReleaseEntry = wmsServiceHelper.getCaptureOrderReleaseData(orderReleaseId);
        sft.assertEquals(orderCaptureReleaseEntry.getIsOnHold(), Boolean.FALSE, "Order Should OFF-Hold in Capture order release for PPS Orders");
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "Verify order is put onHold when final amount is negative at order level", groups={"regression","OnHold"})
    public void verifyOnHoldIfFinalAmountIsNegativeOrderLevel() throws Exception {
        sft = new SoftAssert();
        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 0);
        updateOnHoldInOnholdReasonsMaster("2", 1);
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password , OMSTCConstants.Pincodes.PINCODE_560067, new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1"}, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(releaseId, "Q");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Order is already in onhold");
        
        String query = "update orders set final_amount='-801' where id="+orderID;
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        omsServiceHelper.evaluateOrderOnholdRules(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"Order should be in onhold");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(),"2","OnHold reason shoule be 2 but Actual: "+orderEntry.getOnHoldReasonId().toString());
        //Verify in DB
        HashMap<String, Object> orderDetail = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(orderID).get(0);
        
        sft.assertEquals(orderDetail.get("is_on_hold"), Boolean.TRUE,"Order should be in onhold in DB");
        sft.assertEquals(""+orderDetail.get("on_hold_reason_id_fk"),"2","OnHold reason shoule be 2 in DB but Actual: "+orderEntry.getOnHoldReasonId().toString());        
        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 1);
        updateOnHoldInOnholdReasonsMaster("2", 0);
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "Verify order is put onHold when final amount is negative at order level", groups={"regression","OnHold"})
    public void verifyOnHoldIfFinalAmountIsNegativeReleaseLevel() throws Exception {
        sft = new SoftAssert();
        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 0);
        updateOnHoldInOnholdReasonsMaster("2", 1);
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password , OMSTCConstants.Pincodes.PINCODE_560067, new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1"}, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(releaseId, "Q");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Order is already in onhold");
        
        String query = "update order_release set final_amount='-801' where id="+releaseId;
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        omsServiceHelper.evaluateOrderOnholdRules(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"Order should be in onhold");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(),"2","OnHold reason shoule be 2 but Actual: "+orderEntry.getOnHoldReasonId().toString());
        //Verify in DB
        HashMap<String, Object> orderDetail = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(orderID).get(0);
        
        sft.assertEquals(orderDetail.get("is_on_hold"), Boolean.TRUE,"Order should be in onhold in DB");
        sft.assertEquals(""+orderDetail.get("on_hold_reason_id_fk"),"2","OnHold reason shoule be 2 in DB but Actual: "+orderEntry.getOnHoldReasonId().toString());        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 1);
        updateOnHoldInOnholdReasonsMaster("2", 0);
        sft.assertAll();
    }
    
    @Test(enabled = true, description = "Verify order is put onHold when final amount is negative at order level", groups={"regression","OnHold"})
    public void verifyOnHoldIfFinalAmountIsNegativeLineLevel() throws Exception {
        sft = new SoftAssert();
        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 0);
        updateOnHoldInOnholdReasonsMaster("2", 1);
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password , OMSTCConstants.Pincodes.PINCODE_560067, new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1",OMSTCConstants.OtherSkus.skuId_3832+":1"}, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(releaseId, "Q");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        
        long lineId = omsServiceHelper.getOrderLineEntries(releaseId).get(0).getId();
        sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Order is already in onhold");
        
        String query = "update order_line set final_amount='-1598' where id="+lineId;
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        omsServiceHelper.evaluateOrderOnholdRules(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"Order should be in onhold");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(),"2","OnHold reason shoule be 2 but Actual: "+orderEntry.getOnHoldReasonId().toString());
        //Verify in DB
        HashMap<String, Object> orderDetail = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(orderID).get(0);
        
        sft.assertEquals(orderDetail.get("is_on_hold"), Boolean.TRUE,"Order should be in onhold in DB");
        sft.assertEquals(""+orderDetail.get("on_hold_reason_id_fk"),"2","OnHold reason shoule be 2 in DB but Actual: "+orderEntry.getOnHoldReasonId().toString());        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 1);
        updateOnHoldInOnholdReasonsMaster("2", 0);
        sft.assertAll();
    }
    
    
    
    @Test(enabled = true, description = "Verify order is put onHold when final amount is negative at order level", groups={"regression","OnHold"})
    public void verifyOnHoldIfFinalAmountIsNegativeReleaseLevelMultiLine() throws Exception {
        sft = new SoftAssert();
        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 0);
        updateOnHoldInOnholdReasonsMaster("2", 1);
        
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},"ON_HAND");
        
        String orderID =  end2EndHelper.createOrder(login, password , OMSTCConstants.Pincodes.PINCODE_560067, new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":1",OMSTCConstants.OtherSkus.skuId_3832+":1"}, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        End2EndHelper.sleep(10000);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String releaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(releaseId, "Q");
        omsServiceHelper.updateReleaseStatusDB(releaseId2, "Q");
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"Q");
        
        String lineId = omsServiceHelper.getOrderLineEntries(releaseId).get(0).getId().toString();
        sft.assertEquals(orderEntry.getOnHold(), Boolean.FALSE,"Order is already in onhold");
        
        String query = "update order_release set final_amount='-799' where id="+releaseId;
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        omsServiceHelper.evaluateOrderOnholdRules(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        
        sft.assertEquals(orderEntry.getOnHold(), Boolean.TRUE,"Order should be in onhold");
        sft.assertEquals(""+orderEntry.getOnHoldReasonId(),"2","OnHold reason shoule be 2 but Actual: "+orderEntry.getOnHoldReasonId().toString());
        //Verify in DB
        HashMap<String, Object> orderDetail = (HashMap<String, Object>) omsServiceHelper.getOrderEntryDB(orderID).get(0);
        
        sft.assertEquals(orderDetail.get("is_on_hold"), Boolean.TRUE,"Order should be in onhold in DB");
        sft.assertEquals(""+orderDetail.get("on_hold_reason_id_fk"),"2","OnHold reason shoule be 2 in DB but Actual: "+orderEntry.getOnHoldReasonId().toString());        
        updateOnHoldInOnholdReasonsMaster("35,23,37", 1);
        updateOnHoldInOnholdReasonsMaster("2", 0);
        sft.assertAll();
    }



    
}
