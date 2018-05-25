package com.myntra.apiTests.end2end;

import argo.saj.InvalidSyntaxException;
import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.PaymentInstruments;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.paymentPlan.PaymentPlanGenerator;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanAndPaymentPlanItems;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.lmsClient.LMSOrderEntries;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.loyalitypointsservice.LoyalityPointsServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.apiTests.portalservices.pricingpromotionservices.helper.CashBackBalanceEntry;
import com.myntra.apiTests.portalservices.pricingpromotionservices.helper.PnPServiceHelper;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.ims.client.response.StoreLvlWhInventoryResponse;
import com.myntra.lms.client.response.OrderEntry;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.loyaltyPointsService.domain.response.LoyaltyPointsServiceCreditResponse;
import com.myntra.loyaltyPointsService.domain.response.LoyaltyPointsServiceDebitResponse;
import com.myntra.loyaltyPointsService.domain.response.entry.LoyaltyPointsUserAccountEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.common.enums.code.ReturnStatus;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class End2EndHelper {
	int waitTime = 15;
    static Initialize init = new Initialize("/Data/configuration");
    static org.slf4j.Logger log = LoggerFactory.getLogger(End2EndHelper.class);
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    LMSHelper lmsHelper = new LMSHelper();
	private String orderID;
	private Map<String, Object> xCartOrderEntry;
	private long additionalChargeId;

    /**
     * Create Order In Myntra System
     * @param createOrderEntry
     * @return
     */
    public String createOrder(CreateOrderEntry createOrderEntry) throws InvalidSyntaxException, SCMExceptions, IOException, JAXBException, ParseException, ManagerException {
        String storeOrderID = null;
        CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
        Svc svc = checkoutTestsHelper.getXIDForMultipleItemToCart(createOrderEntry);
		String sessionID = svc.getResponseHeadersAsMap().get("Set-Cookie").toString().split(";")[0].split("=")[1];

        PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
        if(createOrderEntry.getPaymentInstruments().isCOD())
            storeOrderID = ppsServiceHelper.payNowFormCOD(""+createOrderEntry.getAddressId(), sessionID);
        else
            storeOrderID = ppsServiceHelper.payNowFormOnline(""+createOrderEntry.getAddressId(), sessionID, createOrderEntry.isEGC());

        ExceptionHandler.handleNotNull(storeOrderID, "PPS Create Order Failed");
        orderID = omsServiceHelper.getOrderEntryByStoreOrderID(storeOrderID).getId().toString();
        return orderID;
    }
    
    public PaymentPlanAndPaymentPlanItems createOrderWithMock(CreateOrderEntry createOrderEntry){
    	
    	PaymentPlanGenerator paymentPlanGenerator = new PaymentPlanGenerator();
    	return paymentPlanGenerator.getPaymentPlan(createOrderEntry);
    }
    
    
    
    /**
     * Create Order with coupon/cashback/loyalty/giftwrap/COD
     *
     * @param username
     * @param password
     * @param skuIdAndQuantities
     * @param coupon
     * @param cashback
     * @param loyalty
     * @param isGiftWrap
     * @return 
     * @throws ParseException 
     * @throws SCMExceptions 
     * @throws ManagerException 
     * @throws Exception
     */
    public String createOrder(String username, String password, String pincode, String[] skuIdAndQuantities,
                              String coupon, Boolean cashback, Boolean loyalty, Boolean isGiftWrap, String giftcard, Boolean isTryAndBuy) throws IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions, ManagerException {
    	
    	List<SkuEntry> skuEntries = new ArrayList<>();
    	for(String skuIdAndQty:skuIdAndQuantities){
    		String [] tempArray = skuIdAndQty.split(":");
    		SkuEntry skuEntry = new SkuEntry(tempArray[0],Integer.parseInt(tempArray[1]));
    		skuEntries.add(skuEntry);
    	}
    	ShipmentType shipmentType;
    	
		if(isTryAndBuy==true){
    		shipmentType =ShipmentType.TRY_AND_BUY;
    	}else{
    		shipmentType = ShipmentType.DL;
    	}
    	
    	CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder()
    			.userName(username)
    			.password(password)
    			.pincode(Long.parseLong(pincode))
    			.skuEntries(skuEntries)
    			.couponCode(coupon)
    			.paymentInstruments(new PaymentInstruments(true, loyalty, false, false))
    			.shipmentType(shipmentType)
    			.giftWrapEnabled(isGiftWrap)
    			.build();
    	orderID = createOrder(createOrderEntry);
        return orderID;
    }
       
    
    /**
     * Create Order with coupon/cashback/loyalty/giftwrap with any paymentMode
     * @param username
     * @param password
     * @param skuIdAndQuantities
     * @param coupon
     * @param cashback
     * @param loyalty
     * @param isGiftWrap
     * @param giftcard
     * @param isTryAndBuy
     * @param paymentMode
     * @return
     * @throws ParseException 
     * @throws SCMExceptions 
     * @throws ManagerException 
     * @throws Exception
     */
    public String createOrder(String username, String password, String pincode, String[] skuIdAndQuantities,
                            String coupon, Boolean cashback, Boolean loyalty, Boolean isGiftWrap, String giftcard, Boolean isTryAndBuy, PaymentMode paymentMode) throws IOException, JAXBException, InvalidSyntaxException, JSONException, InterruptedException, ManagerException, SCMExceptions, ParseException {
        LMSHelper lmsHelper = new LMSHelper();
        List<SkuEntry> skuEntries = new ArrayList<>();
    	for(String skuIdAndQty:skuIdAndQuantities){
    		String [] tempArray = skuIdAndQty.split(":");
    		SkuEntry skuEntry = new SkuEntry(tempArray[0],Integer.parseInt(tempArray[1]));
    		skuEntries.add(skuEntry);
    	}
    	ShipmentType shipmentType;
    	
		if(isTryAndBuy==true){
    		shipmentType =ShipmentType.TRY_AND_BUY;
    	}else{
    		shipmentType = ShipmentType.DL;
    	}
    	
    	CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder()
    			.userName(username)
    			.password(password)
    			.pincode(Long.parseLong(pincode))
    			.skuEntries(skuEntries)
    			.couponCode(coupon)
    			.paymentInstruments(new PaymentInstruments(true, loyalty, false, false))
    			.shipmentType(shipmentType)
    			.giftWrapEnabled(isGiftWrap)
    			.build();
    	orderID = createOrder(createOrderEntry);
        Thread.sleep(3000);
        changeOrderPaymentMode(orderID,paymentMode);
        return orderID;

    }

    /**
     * changePaymentMethodInPPS
     * @param orderId
     * @param instrumentType
     */
    public void changePaymentMethodInPPS(String orderId,int instrumentType){
        DBUtilities.exUpdateQuery("update payment_plan_instrument_details set paymentInstrumentType = "+instrumentType+" where pps_Id = (select id from payment_plan where orderId = "+orderId+" and actionType = 'SALE')","pps");
        DBUtilities.exUpdateQuery("update payment_plan_item_instrument set paymentInstrumentType = "+instrumentType+" where ppsItemId in(select id from payment_plan_item where pps_Id = (select id from payment_plan where orderId = "+orderId+" and actionType = 'SALE'))","pps");
    }

    /**
     * changePaymentMethodOMS
     * @param orderId
     * @param paymentMethod
     */
    public void changePaymentMethodOMS(String orderId, String paymentMethod){
        DBUtilities.exUpdateQuery("update orders set payment_method = '"+paymentMethod+"' where id = "+orderId,"oms");
        DBUtilities.exUpdateQuery("update order_release set payment_method = '"+paymentMethod+"' where order_id_fk = "+orderId,"oms");
    }

    /**
     * changeOrderPaymentMode
     * @param orderId
     * @param paymentMode
     */
    public void changeOrderPaymentMode(String orderId, PaymentMode paymentMode){

        String paymentMethod="on";
        int instrumentType =1;
        if (paymentMode == PaymentMode.CC){
            instrumentType = 1;
        }else if (paymentMode == PaymentMode.DC){
            instrumentType = 2;
        }else if (paymentMode == PaymentMode.WALLET){
            instrumentType = 10;
        }else if (paymentMode == PaymentMode.LP){
            instrumentType = 7;
        }else if (paymentMode == PaymentMode.NETBANKING){
            instrumentType = 4;
        }else if (paymentMode == PaymentMode.COD){
            paymentMethod = "cod";
            instrumentType = 5;
        }
        changePaymentMethodInPPS(orderId, instrumentType);
        changePaymentMethodOMS(orderId, paymentMethod);
    }

    /**
     * Process Order in WMS and Do RTS SCAN
     *
     * @param portalOrderReleaseID
     */
    @Deprecated
    public void processOrderTillDLState(String portalOrderReleaseID, String toStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper= new LmsServiceHelper();
        WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
        List<OrderReleaseEntry> list = omsServiceHelper.getOrderEntry(portalOrderReleaseID).getOrderReleases();

        for (OrderReleaseEntry list1 : list) {
            // verify OrderStatus is WP or not in ORDER RELEASE Table
            String releaseID = list1.getId().toString();
            String warehouseID = "" + list1.getWarehouseId();
            String trackingNo = "" + list1.getTrackingNo();
            String pincode = "" + list1.getZipcode();

            wmsServiceHelper.processOrderInWMS(releaseID, warehouseID);

            // verify inventory Counts(ATP/IMS) after RTS Scan

            lmsServiceHelper.processOrderInLMSTillSH(releaseID.toString(), warehouseID,toStatus);
            lmsServiceHelper.processOrderInLMSFromSHToDL(releaseID, warehouseID, pincode, trackingNo, EnumSCM.DL);
        }

    }

    
    /**
     * Mark Order to a particular status for a given Order ID for for simple orders
     * @param id
     * @param toStatus
     * @param type : type can be EnumSCM.ORDER or EnumSCM.RELEASE. If you pass order then it will mark the same status(passed status) for all the release present in the order.
     * @throws Exception
     */
    @Deprecated
    public void markOrderDelivered(String id, String toStatus, String type) throws Exception {
        List<Map> list = null;
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        if (type.equalsIgnoreCase(EnumSCM.ORDER)) {
        	
            list = omsServiceHelper.getOrderReleaseDBEntry(id);
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(id, 15), "Order status is OnHold");
            System.out.println("Order ID : " + id);

            for (Map list1 : list) {
                String releaseId = "" + list1.get("id");
                log.debug("Order Release ID - " + releaseId);
                if (omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals("Q") || omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals(EnumSCM.WP)) {
                	Assert.assertTrue(resolveOrderOnHold34AndMarkOrderWP(releaseId),"order release is not in WP status");
                }
                OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
                markReleaseDelivered(orderReleaseEntry, toStatus);
            }
            return;
        }else if (type.equalsIgnoreCase(EnumSCM.RELEASE)) {
        	String releaseId = id;
        	String orderId = omsServiceHelper.getOrderIdFromReleaseId(releaseId);
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15), "Order status is OnHold");
            System.out.println("Order ID : " + orderId+", ReleaseId: "+releaseId);
            if(omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals("Q") || omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals(EnumSCM.WP)){
            	Assert.assertTrue(resolveOrderOnHold34AndMarkOrderWP(""+releaseId),"order release is not in WP status");
            }
            OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
            markReleaseDelivered(orderReleaseEntry, toStatus);
            return;
        }else {
            log.info("No Orders found with the matching Order/Release ID : " + id);
        }
    }

    /**
     * Mark Order to a particular status for a given Order ID for TRY_AND_BUY order
     * @param id
     * @param toStatus
     * @param type : type can be EnumSCM.ORDER or EnumSCM.RELEASE. If you pass order then it will mark the same status(passed status) for all the release present in the order.
     * @param skuWithStatus : pass Skus along with the TOD status you want to mark for that sku. Pass it in a HashMap
     * @throws Exception
     */
    @Deprecated
    public void markOrderDelivered(String id, String toStatus, String type, HashMap<String, String> skuWithStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        if (type.equalsIgnoreCase(EnumSCM.ORDER)) {
            List<Map> list = null;
            String orderId = id;
            list = omsServiceHelper.getOrderReleaseDBEntry(id);
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15), "Order status is OnHold");
            System.out.println("Order ID : " + orderId);
            for (Map list1 : list) {
                String releaseId = "" + list1.get("id");
                log.debug("Order Release ID - " + releaseId);
                if (omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals("Q") || omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals(EnumSCM.WP)) {
                    Assert.assertTrue(resolveOrderOnHold34AndMarkOrderWP(releaseId),"order release is not in WP status");
                }
                OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
                markReleaseDelivered(orderReleaseEntry, toStatus, skuWithStatus);
            }
            return;
        }else if (type.equalsIgnoreCase(EnumSCM.RELEASE)) {
        	String releaseId = id;
        	String orderId = omsServiceHelper.getOrderIdFromReleaseId(releaseId);
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderId, 15), "Order status is OnHold");
            System.out.println("Order ID : " + orderId+", ReleaseId: "+releaseId);
            if(omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals("Q") || omsServiceHelper.getOrderReleaseStatusFromOMS(releaseId).equals(EnumSCM.WP)){
            	Assert.assertTrue(resolveOrderOnHold34AndMarkOrderWP(""+releaseId),"order release is not in WP status");
            }
            OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
            markReleaseDelivered(orderReleaseEntry, toStatus, skuWithStatus);
            return;
        }else {
            log.info("No Orders found with the matching Order/Release ID : " + id);
        }

    }

    
    public void createAndCompleteReturnInRMS(String lineID, int quantity, ReturnMode returnMode, long returnReasonID)
            throws JAXBException, IOException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineID, quantity, returnMode, returnReasonID);
        String returnID = returnResponse.getData().get(0).getId().toString();
        completeOrderInRMS(returnID);
    }
    
    public void createAndCompleteReturnInRMSWithRefundMode(String lineID, int quantity,ReturnMode returnMode, long returnReasonID,RefundMode refundMode)
            throws JAXBException, IOException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineID, quantity, returnMode, returnReasonID,refundMode);
        String returnID = returnResponse.getData().get(0).getId().toString();
        completeOrderInRMS(returnID);
    }

    public void completeOrderInRMS(String returnID) throws IOException {
        RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
        ReturnResponse returnResponse = rmsServiceHelper.returnStatusProcessNew(returnID, ReturnStatus.RRQP, ReturnStatus.RPI);
        ReturnResponse returnResponse1 = rmsServiceHelper.returnStatusProcessNew(returnID, ReturnStatus.RPI, ReturnStatus.RPU);
        rmsServiceHelper.receiveAtWareHouse(returnID, ""+returnResponse1.getData().get(0).getReturnAdditionalDetailsEntry().getIdealReturnWarehouse());
        rmsServiceHelper.markQAPassForReturn(""+returnID, "Q1");
    }
    
    public void processOrderTillPk(OrderReleaseEntry orderReleaseEntry, String toStatus) throws JAXBException, SQLException, IOException, InterruptedException, ManagerException {
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        SellerApiHelper sellerApiHelper = new SellerApiHelper();
        if (orderReleaseEntry.getStatus().equals(EnumSCM.PK)||orderReleaseEntry.getStatus().equals(EnumSCM.SH)||orderReleaseEntry.getStatus().equals(EnumSCM.DL)||
                orderReleaseEntry.getStatus().equals(EnumSCM.C)||orderReleaseEntry.getStatus().equals(EnumSCM.L)||orderReleaseEntry.getStatus().equals(EnumSCM.RTO)){
            return ;
        }
        String sellerID = "";
        String supplyType = "";
        String isGstLive = omsServiceHelper.getApplicationPropertyValue(EnumSCM.USE_GST_ENGINE);
        Map<String, String> data = new HashMap<>();
        String releaseID = orderReleaseEntry.getId().toString();
            if ((orderReleaseEntry.getTrackingNo() == null)
                    || (orderReleaseEntry.getTrackingNo().equalsIgnoreCase("")))
                Assert.assertEquals(omsServiceHelper.assignTrackingNumber(releaseID, false).getStatus().getStatusType().toString(), "SUCCESS", "Unable to assign Tracking Number");
            Assert.assertTrue(omsServiceHelper.validateTrackingNumberNotNullInOrderRelease(orderReleaseEntry.getId().toString(),8),"Tracking number is not assigned yet !!");
            String warehouseID = "" + orderReleaseEntry.getWarehouseId();
            String trackingNo = "" + orderReleaseEntry.getTrackingNo();
            String pincode = "" + orderReleaseEntry.getZipcode();
            String statusCode = "" + orderReleaseEntry.getStatus();

            // Get Seller information from OrderLine Table
            List<Map> listLine = omsServiceHelper.getOrderLineDBEntryforRelease(releaseID);
            Map<String, Object> hm = listLine.get(0);
            sellerID = "" + hm.get("seller_id");
            supplyType = "" + hm.get("supply_type");
            if (statusCode.equals(EnumSCM.SMDL)) {
            	statusCode = EnumSCM.DL;
			}
            if (statusCode.equals(toStatus) || statusCode.equalsIgnoreCase(EnumSCM.C)) {
            	SlackMessenger.send("scm_e2e_order_sanity", "Order status is already delivered");
                log.info("The Order is already in " + toStatus + " For Release ID " + releaseID);
            } else if (statusCode.equalsIgnoreCase(EnumSCM.WP)) {
                omsServiceHelper.assignTrackingNumber(releaseID,false);
                log.info("Order in WP status For Release ID " + releaseID + "Status :-" + statusCode);
                // Process Order in WMS
                log.info(" Start Process Order in WMS/PARTNER PORTAL");
                SellerConfig sellerConfig = new SellerConfig();
                if (sellerConfig.isVector(sellerID) && supplyType.equalsIgnoreCase(EnumSCM.ON_HAND)) {
                    if(isGstLive.equalsIgnoreCase("false")) omsServiceHelper.stampGovtTaxForVectorSuccess(releaseID);
                	wmsServiceHelper.processOrderInWMS(releaseID, warehouseID);
                } else if (sellerConfig.isVector(sellerID) && supplyType.equalsIgnoreCase(EnumSCM.JUST_IN_TIME)) {
                	if(isGstLive.equalsIgnoreCase("false")) omsServiceHelper.stampGovtTaxForVectorSuccess(releaseID);
                    processOrderForJITItems(releaseID, warehouseID);
                }else if (sellerID.equals("18")) {
                    sellerApiHelper.processMaduraInSellerServiceTillPK(releaseID);
                } else {
                	if(isGstLive.equalsIgnoreCase("false")) omsServiceHelper.stampGovtTaxForVectorSuccess(releaseID);
                    sellerApiHelper.processSmallSellerTillPK(releaseID);
                }
            }

    }
    
    public void markReleaseDelivered(OrderReleaseEntry orderReleaseEntry, String toStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        if (toStatus.equalsIgnoreCase(EnumSCM.WP)) {
            log.info("Order Status is WP");
            return;
        }
      processOrderTillPk(orderReleaseEntry, toStatus);
      Long releaseID = orderReleaseEntry.getId();
      String trackingNo = orderReleaseEntry.getTrackingNo();
        String warehouseID = ""+orderReleaseEntry.getWarehouseId();
        String pincode = orderReleaseEntry.getZipcode();
        if (toStatus.equalsIgnoreCase(EnumSCM.PK)) {
            log.info("Order Status is PK");
            return;
        }
        log.info("Process Order in LMS FROM PK to SH");
        lmsServiceHelper.processOrderInLMSTillSH(releaseID.toString(), warehouseID, toStatus);
        if (toStatus.equalsIgnoreCase(EnumSCM.SH)||toStatus.equalsIgnoreCase(EnumSCM.ADDED_TO_MB)||toStatus.equalsIgnoreCase(EnumSCM.RECEIVE_IN_DC)) {
            log.info("Order Status is SH/ADDED_TO_MB/RECEIVE_IN_DC");
            return;
        }else{
            log.info("Process Order in LMS FROM SH to "+toStatus);
            lmsServiceHelper.processOrderInLMSFromSHToDL(releaseID.toString(), warehouseID, pincode, trackingNo, toStatus);
        }
    }

    public void markReleaseDelivered(String releaseID, String toStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        log.info("Process Order in LMS FROM PK to SH");
        OrderEntry orderEntry = ((OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseID)).getOrders().iterator().next();
        String warehouseID = orderEntry.getWarehouseId().toString();
        lmsServiceHelper.processOrderInLMSTillSH(releaseID, warehouseID, toStatus);
        if (lmsHelper.getOrderToShipStatus(releaseID).equals(toStatus)) {
            return;
        }
        if (toStatus.equalsIgnoreCase(EnumSCM.SH)) {
            log.info("Order Status is SH");
            return;
        }else{
            log.info("Process Order in LMS FROM SH to "+toStatus);
            lmsServiceHelper.processOrderInLMSFromSHToDL(releaseID, warehouseID, orderEntry.getZipcode(), orderEntry.getTrackingNumber(), toStatus);
        }
    }

    public void markReleaseDelivered(LMSOrderEntries lmsOrderEntries) throws Exception {
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        log.info("Process Order in LMS FROM PK to SH");
        long masterBagId = lmsServiceHelper.processOrderInLMSFromPKToSH(lmsOrderEntries);
        lmsServiceHelper.processOrderInLMSFromSHToTerminalState(lmsOrderEntries);
    }

    /**
     * 
     * @param orderReleaseEntry
     * @param toStatus
     * @param skuWithStatus
     * @throws Exception
     */
    public void markReleaseDelivered(OrderReleaseEntry orderReleaseEntry, String toStatus, HashMap<String, String> skuWithStatus) throws Exception {
        OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
        LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        if (toStatus.equalsIgnoreCase(EnumSCM.WP)) {
            log.info("Order Status is WP");
            return;
        }
        processOrderTillPk(orderReleaseEntry, toStatus);
        Long releaseID = orderReleaseEntry.getId();
        String trackingNo = orderReleaseEntry.getTrackingNo();
        String warehouseID = ""+orderReleaseEntry.getWarehouseId();
        String pincode = orderReleaseEntry.getZipcode();
        if (toStatus.equalsIgnoreCase(EnumSCM.PK)) {
                log.info("Order Status is PK");
                return;
            }
            log.info("Process Order in LMS FROM PK to SH");
            lmsServiceHelper.processOrderInLMSTillSH(releaseID.toString(), warehouseID, toStatus);
            if (toStatus.equalsIgnoreCase(EnumSCM.SH)) {
                log.info("Order Status is SH");
                return;
            }else {
                log.info("Process Order in LMS FROM SH to "+toStatus);
                lmsServiceHelper.processOrderInLMSFromSHToDL(releaseID.toString(), toStatus, skuWithStatus);
            }
    }

    
    private void processOrderForJITItems(String releaseID, String warehouseID) {
        log.info("For JIT Items Need to Associate Item From Partener Portal");
    }

    /**
     * Move Order to WP to PK state for a Seller
     *
     * @param releaseID
     * @param sellerID
     * @return
     */
    private void processOrderInVMS(String releaseID, String sellerID){
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        MyntraService dispatchOrderService = Myntra.getService(ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
                                                               APINAME.DISPATCHORDER, init.Configurations, new String[]{}, new String[]{sellerID, ""+releaseID},
                                                               PayloadType.JSON, PayloadType.JSON);
        End2EndHelper.sleep(1000L);
        if (!(omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.PK, 2) || omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.SH, 2))) {
            RequestGenerator dispatchOrderServiceRes = new RequestGenerator(dispatchOrderService,
                                                                            WMSServiceHelper.getWMSHeader());

            String response = dispatchOrderServiceRes.returnresponseasstring();
            log.info("Dispatch Order Response \n" + response);

            // Check the RTS SCAN ERROR AND Check for the Order Status in OMS
            // ERROR
            String statusMessage = APIUtilities.getElement(response, "status.statusType", "JSON");

            if (omsServiceHelper.validateReleaseStatusInOMS(releaseID, EnumSCM.PK, waitTime)) {
                log.info("Order Release moved to PK For Release ID " + releaseID);
            } else if (statusMessage.equals("ERROR")) {
                log.info("RTS Scan Response message="
                                           + APIUtilities.getElement(response, "status.statusMessage", "JSON"));
                log.info("RTS Scan Failed");
                assertFalse(true, "RTS Scan failed. for seller");
            }
            assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseID, EnumSCM.PACKED, waitTime), "Order not in PK state in LMS");
            log.info("Order with Release ID moved to LMS for release ID" + releaseID);
        }
    }

	private void verifyNotification(String query, String db) {
		try {

			List rSet = DBUtilities.exSelectQuery(query, db);
			if (rSet.size() <= 0) {
				log.info("Unable to Receive the Notification - " + query);
			} else {

			}

		} catch (Exception ex) {
			log.info("Verify Notification Exception :-" + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	public void verifyNotificationInDB(String abc) {

	}

	/**
	 * Validate Email Notification in DB
	 * 
	 * @param sendTo
	 * @param templateID
	 * @param orderID
	 * @param delaytoCheck
	 * @return
	 */
	private boolean verifyEmailNotificationInDB(String sendTo, String templateID, String orderID, int delaytoCheck) {
		log.info("Validate Email Notification in 'myntra_notification_email_details' table");
		boolean validateStatus = false;
		String status_code = "";

		try {
			for (int i = 0; i < delaytoCheck; i++) {
				List<HashMap> list = DBUtilities
						.exSelectQuery(
								"select count(1) as recordeCount from myntra_notification.myntra_notification_email_details where template_id="
										+ templateID + " and sendto='" + sendTo + "' and body like '%" + orderID + "%'",
								"LMS");

				if (list.isEmpty()) {
					log.info(" No Records Found in DB");
				} else {
					HashMap hm = list.get(0);
					status_code = "" + hm.get("recordeCount");
					if (status_code.equalsIgnoreCase("1")) {
						validateStatus = true;
						break;
					} else {
						Thread.sleep(1000);
					}
					log.info("waiting for Email Notification in DB \t " + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			validateStatus = false;
		}
		return validateStatus;
	}

	/**
	 * Validate SMS Notification in DB
	 * 
	 * @param sendTo
	 * @param templateID
	 * @param orderID
	 * @param delaytoCheck
	 * @return
	 */
	private boolean verifySMSNotificationInDB(String sendTo, String templateID, String orderID, int delaytoCheck) {
		log.info("Validate Email Notification in 'myntra_notification_email_details' table");
		boolean validateStatus = false;
		String status_code = "";

		try {
			for (int i = 0; i < delaytoCheck; i++) {
				List<HashMap> list = DBUtilities
						.exSelectQuery(
								"select count(1) as recordeCount from myntra_notification.myntra_notification_sms_details where template_id="
										+ templateID + " and sendto='" + sendTo + "' and body like '%" + orderID + "%'",
								"LMS");

				if (list.isEmpty()) {
					log.info(" No Records Found in DB");
				} else {
					HashMap hm = list.get(0);
					status_code = "" + hm.get("recordeCount");
					if (status_code.equalsIgnoreCase("1")) {
						validateStatus = true;
						break;
					} else {
						Thread.sleep(1000);
					}
					log.info("waiting for SMS Notification in DB \t " + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			validateStatus = false;
		}
		return validateStatus;
	}

    /**
     * Get CahsBack Amount
     * @param login
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
	public Double getCashBackPoints(String login) throws IOException, JSONException {
        CashBackBalanceEntry cashBackBalanceEntry = PnPServiceHelper.getCashbackBalanceEntry(login);
		return cashBackBalanceEntry.getCashbackBalance();
	}

    /**
     * Get Loyalty Amount
     * @param login
     * @return
     * @throws IOException
     * @throws JAXBException
     */
	public float getLoyaltyPointsCashEquivalent(String login) throws IOException, JAXBException {
		LoyaltyPointsUserAccountEntry loyaltyPointsUserAccountEntry = LoyalityPointsServiceHelper
				.getLoyaltyPointAccountInfo(login);
		return loyaltyPointsUserAccountEntry.getCashEqualantAmount();
	}

	public int getLoyaltyPoints(String login) throws IOException, JAXBException {
		LoyaltyPointsUserAccountEntry loyaltyPointsUserAccountEntry = LoyalityPointsServiceHelper
				.getLoyaltyPointAccountInfo(login);
		return loyaltyPointsUserAccountEntry.getActivePointsBalance();
	}

	/**
	 * Update Loyalty and CB for an Login
	 * 
	 * @param login
	 * @param loyalty
	 * @param cashback
	 * @throws SQLException
	 * @throws JAXBException 
	 */
	public void updateloyalityAndCashBack(String login, int loyalty, int cashback) throws SQLException, IOException, JSONException, JAXBException {
        IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
        String loginUxID = ideaApiHelper.getUIDXForLoginViaEmail("myntra",login);

        CashBackBalanceEntry cashBackBalanceEntry = PnPServiceHelper.getCashbackBalanceEntry(loginUxID);
        if(cashBackBalanceEntry != null && cashBackBalanceEntry.getMigrationStatus().equalsIgnoreCase("MIGRATED")){
            PnPServiceHelper.debitAmountFromWallet(loginUxID, cashBackBalanceEntry.getCashbackBalance());
            PnPServiceHelper.creditAmountToWallet(loginUxID, cashback);
        }else{
            DBUtilities.exUpdateQuery("update cashback_account SET balance=0 where login='" + loginUxID + "';",
                                      "myntra");
            DBUtilities.exUpdateQuery("update cashback_account SET balance=" + cashback + " where login='" + loginUxID
                                              + "' and account_type=1;", "myntra");
            DBUtilities.exUpdateQuery("update cashback_account SET balance=" + cashback + " where login='" + loginUxID
                                              + "' and account_type=1;", "MYNTRA");
        }

        //DBUtilities.exUpdateQuery("update loyalty_points_account set points=0 where login='" + loginUxID + "';",
		//		"lp");
//		DBUtilities.exUpdateQuery("update loyalty_points_account SET points=" + loyalty + " where login='"
//				+ loginUxID + "' and account_type=1;", "lp");
        int debitLoyality =  getLoyaltyPoints(loginUxID);
        if(debitLoyality!=0){
        LoyaltyPointsServiceDebitResponse debitResponse = debitLoyalityPoints(loginUxID, debitLoyality);
		Assert.assertEquals(debitResponse.getStatus().getStatusType(), Type.SUCCESS,"Loyality points not debited.");
		Assert.assertEquals(debitResponse.getStatus().getStatusMessage(), "debited successfuly","Loyality points not debited.");
        }
		sleep(5000L);
		LoyaltyPointsServiceCreditResponse response = updateLoyalityPoints(loginUxID, loyalty);		
		Assert.assertEquals(response.getStatus().getStatusType(), Type.SUCCESS,"Loyality points not updated.");
		Assert.assertEquals(response.getStatus().getStatusMessage(), "credited successfuly","Loyality points not updated.");
		refreshCBCache(loginUxID);
	}
	
	/**
	 * Update Loyalty for an Login
	 * 
	 * @param loginUidx
	 * @param loyality
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws JAXBException 
	 * @throws SQLException
	 */
	public LoyaltyPointsServiceCreditResponse updateLoyalityPoints(String loginUidx , int loyality) throws UnsupportedEncodingException, JAXBException
	{
		String payload = "{\"login\":\""+loginUidx+"\",\"activeCreditPoints\":"+loyality+",\"inActiveCreditPoints\":0,\"activeDebitPoints\":0,\"inActiveDebitPoints\":0,\"description\":\"test test\",\"itemType\":\"ORDER\",\"itemId\":123,\"businessProcess\":\"GOOD_WILL\"}";
		Svc service = HttpExecutorService.executeHttpService(Constants.LOYALTY_PATH.UPDATE_LOYALTY_POINTS, null,SERVICE_TYPE.LOYALTY_SVC.toString(), HTTPMethods.POST, payload, getPnPHeader());
		LoyaltyPointsServiceCreditResponse loyaltyPointsServiceCreditResponse = (LoyaltyPointsServiceCreditResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new LoyaltyPointsServiceCreditResponse());
		return loyaltyPointsServiceCreditResponse;
	
	}
	
	private static HashMap<String, String> getPnPHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "Application/json");
		return createOrderHeaders;
	}



	/**
	 * Validate In DB
	 * 
	 * @param dbName
	 * @param table
	 * @param columnAndValues
	 * @param whereclause
	 */
	public void validateInDB(String dbName, String table, String[] columnAndValues, String whereclause) {
		
		String query = "select * from " + table + " where " + whereclause + ";";

		Map<String, String> expectedMap = new HashMap<>();
		for (String columnandvalue : columnAndValues) {
			String[] keyValue = columnandvalue.split(":");
			expectedMap.put(keyValue[0], keyValue[1]);
		}

		log.info("Query : - " +query);
			List<HashMap> list = DBUtilities.exSelectQuery(query, dbName);
			if (list == null || list.isEmpty()) {
				Assert.fail("No Records Found for the given Clause " + whereclause);
				return;
			}
			Map<String, Object> hm = list.get(0);

			for (Entry<String, String> entry : expectedMap.entrySet()) {
				String valueActual = "" + hm.get(entry.getKey());
				String valueExpected = "" + entry.getValue();
				if (valueExpected.equalsIgnoreCase("not null")) {
					Assert.assertNotNull(valueActual);
					Assert.assertNotSame(valueActual, "");
				} else {
					log.info("Actual Value :" + valueActual + "  Expected Value: " + valueExpected);
					Assert.assertEquals(valueActual, valueExpected);
				}
				log.info("Key : " + entry.getKey() + " Value : " + entry.getValue());
			}
	}

	/**
	 * Refrsh CashBack for a particular login
	 * 
	 * @param login
	 */
	public void refreshCBCache(String login) {
		MyntraService refreshCBService = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.RECACHECASHBACK,
				init.Configurations, PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		HashMap<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		header.put("Accept", "application/json");
		RequestGenerator refreshCBCache = new RequestGenerator(refreshCBService, header);
		String response = refreshCBCache.returnresponseasstring();
		log.info("refreshCBCache - refresh CB " + response);
		Assert.assertEquals(response, "true");
	}

	public static void sleep(long time) {
		try {
			log.info("Sleeping for Time " + time);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Get rabbitMq connection for rabbitMq
	public RabbitTemplate getRabbitMqConnection(String queue, String routingKey, String exchnageName) {
		RabbitTemplate amqpTemplate;
		ConnectionFactory connectionFactory = new CachingConnectionFactory(queue);
		amqpTemplate = new RabbitTemplate(connectionFactory);
		amqpTemplate.setRoutingKey(routingKey);
		amqpTemplate.setExchange(exchnageName);
		return amqpTemplate;
	}

	/**
	 * Push Message to rabbitMQ
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 */
		public void pushMessageToQueue(String message, String routingKey, String exchangeName) {
			String envName = init.Configurations.GetTestEnvironemnt().name();
			String rabbitMqName = null;
			if (envName.equalsIgnoreCase("fox7")) {
				rabbitMqName = "d7erprabbitmq.myntra.com";
			}else{
				rabbitMqName = "pp1erprabbitmq.myntra.com";
			}
			Message mess = new Message(message.getBytes(), getMessageProperties());
			RabbitTemplate rt = getRabbitMqConnection(rabbitMqName,routingKey,exchangeName);
			rt.send(mess);

		}


    //Added by Sneha

    @SuppressWarnings("static-access")
	public CartEntry getTheCartEntryDetails(String username, String password, String addressID, String[] skuIdAndQuantities,
                                       String coupon, String cashback, String loyality, String codAmount, String isGiftWrap, String giftcard) throws JAXBException, UnsupportedEncodingException{
        CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
        String sessionID = checkoutTestsHelper.getxID(username, password, skuIdAndQuantities, "ADD", coupon, cashback, loyality, isGiftWrap);
        return checkoutTestsHelper.getCart(username,"default", sessionID);
    }


    /**
     * Push Message to rabbitMQ
     * @param message
     * @param queueName
     */
    public void pushMessageToQueue(String message, String queueName) {
        SystemConfigProvider.getTopology().getQueueTemplate(queueName).convertAndSend(message);
    }

    /**
     * Refresh Tools Service Apllication Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void refreshToolsApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.TOOLS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.TOOLS_SVC.toString(), HTTPMethods.GET, null, getXMLHeader());
        ApplicationPropertiesResponse cacheRefresh = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        Assert.assertEquals(cacheRefresh.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    }
    
    /**
     * Update Tools Application Properties
     * @param key
     * @param value
     * @param owner
     * @throws JAXBException 
     * @throws UnsupportedEncodingException 
     */
    public void updateToolsApplicationProperties(String key, String value, String owner) throws UnsupportedEncodingException, JAXBException{
    	DBUtilities.exUpdateQuery("update core_application_properties set value='"+ value + "' where name='"+ key +"' and owner= '"+ owner +"';", "myntra_tools");
    	refreshToolsApplicationPropertyCache();
    }

    /**
     * Get OMS Header
     * @return {@link HashMap}
     */
    public static HashMap<String, String> getXMLHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/xml");
        return createOrderHeaders;
    }

    private static MessageProperties getMessageProperties() {
	        MessageProperties messageProperties = new MessageProperties();
	        messageProperties.setHeader("Authorization",
	                "Basic YXBpYWRtaW5+c3lzdGVtOm0xbnRyYVIwY2tldDEzISM=");
	        messageProperties.setContentType("text/plain");
	        messageProperties.setContentEncoding("UTF-8");
	        return messageProperties;
	    }	

	/**
	 * Debit Loyalty for an Login
	 * 
	 * @param loginUidx
	 * @param debitLoyality
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws JAXBException 
	 * @throws SQLException
	 */
	public LoyaltyPointsServiceDebitResponse debitLoyalityPoints(String loginUidx , int debitLoyality) throws UnsupportedEncodingException, JAXBException
	{
		String payload = "{\"login\":\""+loginUidx+"\",\"activeDebitPoints\":"+debitLoyality+",\"inActiveCreditPoints\":0,\"activeCreditPoints\":0,\"inActiveDebitPoints\":0,\"description\":\"test test\",\"itemType\":\"ORDER\",\"itemId\":123,\"businessProcess\":\"GOOD_WILL\"}";
		Svc service = HttpExecutorService.executeHttpService(Constants.LOYALTY_PATH.DEBIT_LOYALTY_POINTS, null,SERVICE_TYPE.LOYALTY_SVC.toString(), HTTPMethods.POST, payload, getPnPHeader());
		LoyaltyPointsServiceDebitResponse loyaltyPointsServiceDebitResponse = (LoyaltyPointsServiceDebitResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new LoyaltyPointsServiceDebitResponse());
		return loyaltyPointsServiceDebitResponse;
	
	}

    /**
     *
     * @param orderReleaseId
     * @return
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
	public Boolean resolveOrderOnHold34AndMarkOrderWP(String orderReleaseId) throws UnsupportedEncodingException, JAXBException, ManagerException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
		
			OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
				
			//Check if release is stuck in Onhold with reasonId 34, if yes resolve hold and mark orderWP

			Boolean isOrderReleaseWP = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId,EnumSCM.WP,10);
			if(isOrderReleaseWP){
				return isOrderReleaseWP;
			}else if(orderReleaseEntry.getOnHold() && orderReleaseEntry.getOnHoldReasonId().toString().equals("34")){
				
				//check if IMS is down
				StoreLvlWhInventoryResponse response = imsServiceHelper.getStoreLvlWhInventory(new String[]{"21,3831,on_hand"},1L, new long[]{28L});
		        if(!response.getStatus().getStatusType().equals(Type.SUCCESS)){
		        	log.info("There is some error in IMS, please check either IMS is down or some other issue");
		        	return false;
		        }
		        
				List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
				for(OrderLineEntry orderLineEntry: orderLineEntries){
					String skuId = orderLineEntry.getSkuId().toString();
					String sellerId = orderLineEntry.getSellerId().toString();
					String supplyType = orderLineEntry.getSupplyType();
					if(supplyType.equals(EnumSCM.ON_HAND)){
						imsServiceHelper.updateInventoryForSeller(new String[]{skuId+":28,36,19:1000:0:"+sellerId}, supplyType);
					}else if(supplyType.equals(EnumSCM.JUST_IN_TIME)){
						imsServiceHelper.updateInventoryForSeller(new String[]{skuId+":20:1000:0:"+sellerId}, supplyType);
					}
				}
				
				omsServiceHelper.resolveOnHoldOrderRelease(orderReleaseId);
				isOrderReleaseWP = omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId,EnumSCM.WP,5);
				return isOrderReleaseWP;
								
			}else if(!orderReleaseEntry.getOnHoldReasonId().toString().equals("34") && orderReleaseEntry.getOnHold() ){
				log.info("orderRelease is in onHold status with reasonId: "+orderReleaseEntry.getOnHoldReasonId()+" with release status"+orderReleaseEntry.getStatus());
				return false;
			}
			return false;	
	}
}
