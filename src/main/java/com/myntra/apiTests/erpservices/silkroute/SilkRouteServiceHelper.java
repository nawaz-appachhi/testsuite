/**
 *
 */
package com.myntra.apiTests.erpservices.silkroute;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.Constants.SILKROUTE_PATH;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.silkroute.client.entry.flipkart.*;
import com.myntra.silkroute.client.enums.flipkart.ReturnEventType;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
/**
 * @author santwana.samantray
 *
 */
public class SilkRouteServiceHelper {

    static Logger log = Logger.getLogger(SilkRouteServiceHelper.class);

    private final SimpleDateFormat httpDateFormat = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss zzz");
    private final String SHA_1 = "SHA-1";
    private long waitTime = 10000L;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();


    // fkmarketplace properties
    String fkUrl = "https://sandbox-api.flipkart.net";
    String fk_oAuth_appId = "319b908a861425a09692589736a69a746719";
    String fk_oAuth_appSecret = "5590efb1dd39e9b6d27a55f4b6f9db6e";
    String fk_oAuth_tokenURL = "https://sandbox-api.flipkart.net/oauth-service/oauth/token?grant_type=client_credentials&scope=Seller_Api";
    String fk_oauth_silkroute_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart3/v0/order/";
    String fk_oauth_silkroute_return_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart3/v0/returns/process";

    // fkmarketplace properties - Binola WH
    String fk2_oAuth_appId = "153a863456746449248873bab614a66b0047a";
    String fk2_oAuth_appSecret = "3531c88b4b5bad1ba03f4bcab940512f4";
    String fk2_oauth_silkroute_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart4/v0/order/";
    String fk2_oauth_silkroute_return_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart4/v0/returns/process";

    // fkmarketplace properties - Mumbai WH
    String fk3_oAuth_appId = "19b31a49777a2969b844531ba3520b5792ab7";
    String fk3_oAuth_appSecret = "22e8ec527abb525ac09bbbf9d42791056";
    String fk3_oauth_silkroute_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart5/v0/order/";
    String fk3_oauth_silkroute_return_callbackurl = "https://silkroute-devint.myntapi.com/myntra-silkroute-service/external/flipkart5/v0/returns/process";

    public void fkcreateOrder(String storeID, String OrderReleaseId, String OrderEventType,String orderItemID,
                              String status, String Hold,
                              String DispatchDate, String DispatchAfter, String quantity,
                              String cancelledQuantity, String ListingID, String SKU_barcode,
                              String price, String pincode, String response, String warehouse, String Order_status, String order_line_status) throws IOException, JAXBException, ParseException {
        Svc createOrderReqGen = invokeSilkRouteServiceCreateOrderAPI(storeID,OrderReleaseId, OrderEventType, status,
                        Hold, DispatchDate, DispatchAfter, quantity,
                        cancelledQuantity, ListingID, SKU_barcode, price,
                        pincode);
        String createOrderResponse = createOrderReqGen.toString();
        End2EndHelper.sleep(waitTime);
        DBUtilities.exUpdateQuery("update order_release set status_code='" + Order_status + "', packed_on=now() where store_release_id='" + OrderReleaseId + "';", "myntra_oms");
        DBUtilities.exUpdateQuery("update order_line set status_code='" + order_line_status + "',packed_on=now() where store_line_id='" + OrderReleaseId + "';", "myntra_oms");
    }

    public Svc invokeSilkRouteServiceCreateOrderAPI(String store, String OrderReleaseId, String OrderEventType, String status,String Hold, String DispatchDate,
                                                                  String DispatchAfterDate, String quantity,String cancelledQuantity, String ListingID, String SKU_barcode,String price, String pincode) throws IOException, JAXBException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        OrderItemAttributes orderReleaseAttributes=new OrderItemAttributes();
        orderReleaseAttributes.setTitle("Automation Order");
        orderReleaseAttributes.setStatus(status);
        orderReleaseAttributes.setHold(Boolean.valueOf(Hold));
        if(!DispatchAfterDate.equals("")){
            orderReleaseAttributes.setDispatchAfterDate(format.parse(DispatchAfterDate));
        }

        if(DispatchDate.contains("NULL")){
            orderReleaseAttributes.setDispatchByDate(null);
        }else if(DispatchDate.equals("")){
            log.debug("Not to Set Anything");
        }else{
            orderReleaseAttributes.setDispatchByDate(format.parse(DispatchDate));
        }
        orderReleaseAttributes.setQuantity(Integer.valueOf(quantity));
        orderReleaseAttributes.setCancelledQuantity(Integer.valueOf(cancelledQuantity));
        orderReleaseAttributes.setListingId(ListingID);

        if(price.contains("NULL")){
            orderReleaseAttributes.setPrice(null);
        }else if(price.equals("")){
            log.debug("Not to Set Anything");
        }else{
            orderReleaseAttributes.setPrice(Double.valueOf(price));
        }

        orderReleaseAttributes.setShippingPincode(pincode);
        orderReleaseAttributes.setSku(SKU_barcode);
        OrderItemEntry orderItemEntry=new OrderItemEntry();
        orderItemEntry.setOrderItemAttributes(orderReleaseAttributes);
        orderItemEntry.setEventType(OrderEventType);
        if(OrderReleaseId.contains("NA")){
            orderItemEntry.setOrderItemId("");
        }else{
            orderItemEntry.setOrderItemId(OrderReleaseId);
        }

        String payload = APIUtilities.getObjectToJSON(orderItemEntry);

        Svc service = HttpExecutorService.executeHttpService(SILKROUTE_PATH.CREATE_ORDER_V2, new String[] {store, "v0/order/"}, SERVICE_TYPE.SILKR_SVC.toString(), HTTPMethods.POST, payload, getHeaderForFKOauth(store));
        return service;
    }



    public static int getIMSboc(int skuid, String store, String warehouse) {
        int blockedOrderCount = 0;
        Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
        String query = "select blocked_order_count from wh_inventory where store_id ="
                + store + " and sku_id =" + skuid + " and seller_id="+ vectorSellerID +" and warehouse_id="+warehouse;
        System.out.println("QUERY:- " + query);
        Map<String, Object> rset = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_ims");
        System.out.println(rset);
        blockedOrderCount = (int) rset.get("blocked_order_count");
        return blockedOrderCount;
    }


    /**
     * Get Header for Silk Route API calls
     *
     * @param seller
     * @return
     * @throws ParseException
     */
    public HashMap<String, String> getHeaderForFKOauth(String seller)
            throws ParseException {
        String fkCallbackURL = null;
        String fkOAuthSecret = null;
        String fkOAuthAppId = null;
        String fkCallbackHTTPMethod = "POST";
        String x_date = "Tue, 12 Aug 2015 18:33:42 GMT+05:30";
        long epoch = getTimeUptoSeconds(x_date);

        if (seller.equals("flipkart3")) {
            fkCallbackURL = fk_oauth_silkroute_callbackurl;
            fkOAuthSecret = fk_oAuth_appSecret;
            fkOAuthAppId = fk_oAuth_appId;
        } else if (seller.equals("flipkart4")) {
            fkCallbackURL = fk2_oauth_silkroute_callbackurl;
            fkOAuthSecret = fk2_oAuth_appSecret;
            fkOAuthAppId = fk2_oAuth_appId;
        } else if (seller.equals("flipkart5")) {
            fkCallbackURL = fk3_oauth_silkroute_callbackurl;
            fkOAuthSecret = fk3_oAuth_appSecret;
            fkOAuthAppId = fk3_oAuth_appId;
        }

        String plainText = epoch + fkCallbackURL + fkCallbackHTTPMethod
                + fkOAuthSecret;
        String digest = sha1Digest(plainText);
        String stringToBeEncoded = fkOAuthAppId + ":" + digest;
        String encodedCredential = "FKLOGIN "
                + new String(Base64.encodeBase64(stringToBeEncoded.getBytes()));
        System.out.println(encodedCredential);
        HashMap<String, String> mp = new HashMap<String, String>();
        mp.put("X-Date", x_date);
        mp.put("X-Authorization", encodedCredential);
        mp.put("Content-Type", "Application/json");
        mp.put("Accept", "Application/json");
        return mp;
    }

    /**
     * Get Header for Silk Route API calls
     *
     * @param seller
     * @return
     * @throws ParseException
     */
    public Map<String, String> getHeaaderForFKOauthReturn(String seller)
            throws ParseException {
        String fkCallbackURL = null;
        String fkOAuthSecret = null;
        String fkOAuthAppId = null;
        String fkCallbackHTTPMethod = "POST";
        String x_date = "Tue, 12 Aug 2015 18:33:42 GMT+05:30";
        long epoch = getTimeUptoSeconds(x_date);

        if (seller.equals("flipkart3")) {
            fkCallbackURL = fk_oauth_silkroute_return_callbackurl;
            fkOAuthSecret = fk_oAuth_appSecret;
            fkOAuthAppId = fk_oAuth_appId;
        } else if (seller.equals("flipkart4")) {
            fkCallbackURL = fk2_oauth_silkroute_return_callbackurl;
            fkOAuthSecret = fk2_oAuth_appSecret;
            fkOAuthAppId = fk2_oAuth_appId;
        }else if (seller.equals("flipkart5")) {
            fkCallbackURL = fk3_oauth_silkroute_return_callbackurl;
            fkOAuthSecret = fk3_oAuth_appSecret;
            fkOAuthAppId = fk3_oAuth_appId;
        }

        String plainText = epoch + fkCallbackURL + fkCallbackHTTPMethod
                + fkOAuthSecret;
        String digest = sha1Digest(plainText);
        String stringToBeEncoded = fkOAuthAppId + ":" + digest;
        String encodedCredential = "FKLOGIN "
                + new String(Base64.encodeBase64(stringToBeEncoded.getBytes()));
        System.out.println(encodedCredential);
        HashMap<String, String> mp = new HashMap<String, String>();
        mp.put("X-Date", x_date);
        mp.put("X-Authorization", encodedCredential);
        mp.put("Content-Type", "Application/json");
        return mp;
    }

    private long getTimeUptoSeconds(String x_date) throws ParseException {
        return httpDateFormat.parse(x_date).getTime() / 1000;
    }

    private String sha1Digest(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_1);
            digest.reset();
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            log.error("unlikely error as algorithm is known to be part of JDK",
                      e);
            throw new RuntimeException(e);
        }
    }

    public List<String> store_release_id(String store, String OrderReleaseId, String OrderEventType,
                                                String status, String Hold, String DispatchDate,
                                                String DispatchAfter, String quantity, String cancelledQuantity,
                                                String ListingID, String SKU_barcode, String price, String pincode) throws IOException, JAXBException, ParseException {
        List<String> cancellationDetails = new ArrayList<String>();
        Svc createOrderReqGen = invokeSilkRouteServiceCreateOrderAPI(store,OrderReleaseId, OrderEventType, status,
                                                       Hold, DispatchDate, DispatchAfter, quantity,
                                                       cancelledQuantity, ListingID, SKU_barcode, price,
                                                       pincode);

        String orderid = APIUtilities.getElement(createOrderReqGen.getResponseBody(), "orderItemId", "json");
        cancellationDetails.add(orderid);
        return cancellationDetails;
    }


    public void updateOrderRelease(){

    }


    private com.myntra.silkroute.client.entry.jabong.OrderItemAttributes setOrderItemAttributes(String itemID, double itemMRP, double itemAmountBeforeTax,
                                                                                                int quantity, String skuID, String supplyType, String sellerID){
        com.myntra.silkroute.client.entry.jabong.OrderItemAttributes orderItemAttributes = new com.myntra.silkroute.client.entry.jabong.OrderItemAttributes();
        orderItemAttributes.setQuantity(quantity);
        orderItemAttributes.setSkuId(skuID);
        orderItemAttributes.setSupplyType(supplyType);
        orderItemAttributes.setItemId(itemID);
        orderItemAttributes.setItemMrp(itemMRP);
        orderItemAttributes.setItemAmountBeforeTax(itemAmountBeforeTax);
        orderItemAttributes.setSellerId(sellerID);
        return orderItemAttributes;
    }



    private List<com.myntra.silkroute.client.entry.jabong.OrderItemAttributes> addJabongOrderItemAttribute(String[] attributeList){
        List<com.myntra.silkroute.client.entry.jabong.OrderItemAttributes> orderItemAttributesEntries = new ArrayList<>();
        for (String attribute : attributeList) {
            String[] attr = attribute.split(":");
            orderItemAttributesEntries.add(setOrderItemAttributes(attr[0], Double.parseDouble(attr[1]),
                    Double.parseDouble(attr[2]), Integer.parseInt(attr[3]), attr[4], attr[5], attr[6]));
        }
        return orderItemAttributesEntries;
    }


    /**
     * Create Order Jabong
     * @param attributeList
     * @param totalAmount
     * @param shippingPincode
     * @param processingStartDate
     * @param processingEndDate
     */
    public Svc createOrderJabong(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type) throws IOException {
        com.myntra.silkroute.client.entry.jabong.OrderItemEntry  orderItemEntry = new com.myntra.silkroute.client.entry.jabong.OrderItemEntry();
        orderItemEntry.setItems(addJabongOrderItemAttribute(attributeList));
        orderItemEntry.setTotalAmount(totalAmount);
        orderItemEntry.setShippingPincode(shippingPincode);
        orderItemEntry.setProcessingStartDate(processingStartDate);
        orderItemEntry.setProcessingEndDate(processingEndDate);
        orderItemEntry.setPaymentMethod("cod");
        orderItemEntry.setType(type);
        String payload = APIUtilities.getObjectToJSON(orderItemEntry);
        System.out.println(payload);
        Svc service = HttpExecutorService.executeHttpService(SILKROUTE_PATH.CREATE_ORDER_JABONG_V1, null,SERVICE_TYPE.SILKR_SVC_JBG.toString(), HTTPMethods.POST, payload, getHeaderForJabong());
        return service;
    }



    /**
     * Update Order Jabong
     * @param attributeList
     * @param totalAmount
     * @param shippingPincode
     * @param processingStartDate
     * @param processingEndDate
     */
    public Svc updateOrderJabong(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type) throws IOException {
        com.myntra.silkroute.client.entry.jabong.OrderItemEntry  orderItemEntry = new com.myntra.silkroute.client.entry.jabong.OrderItemEntry();
        orderItemEntry.setItems(addJabongOrderItemAttribute(attributeList));
        orderItemEntry.setTotalAmount(totalAmount);
        orderItemEntry.setShippingPincode(shippingPincode);
        orderItemEntry.setProcessingStartDate(processingStartDate);
        orderItemEntry.setProcessingEndDate(processingEndDate);
        orderItemEntry.setType(type);
        String payload = APIUtilities.getObjectToJSON(orderItemEntry);

        Svc service = HttpExecutorService.executeHttpService(SILKROUTE_PATH.CREATE_ORDER_JABONG_V1, null,SERVICE_TYPE.SILKR_SVC.toString(), HTTPMethods.POST, payload, getHeaderForJabong());
        return service;
    }

    private HashMap getHeaderForJabong(){
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "Application/json");
        header.put("Accept", "Application/json");
        header.put("Authorization", "Basic YTPi");
        return header;
    }


    /**
     * Update Tracking Number Event
     * @param store
     * @param orderItemID
     * @param returnItemID
     * @param courierName
     * @param trackingId
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public Svc updateTrackingNumber(String store, String orderItemID, String returnItemID, String courierName, String trackingId) throws IOException, ParseException {
        FlipkartReturnEntry retunEntry = new FlipkartReturnEntry();
        List<FlipkartReturnItemEntry> fkList = new ArrayList<>();

        retunEntry.setReturnId(returnItemID);
        retunEntry.setEventType(ReturnEventType.return_item_tracking_id_update);
        retunEntry.setTimestamp(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        retunEntry.setSource("flipkart");

        FlipkartReturnItemEntry flipkartReturnItemEntry = new FlipkartReturnItemEntry();
        FlipkartReturnAttributeEntry flipkartReturnAttributeEntry = new FlipkartReturnAttributeEntry();

        flipkartReturnItemEntry.setCourierName(courierName);
        flipkartReturnItemEntry.setOrderItemId(orderItemID);
        flipkartReturnItemEntry.setOrderItemId(orderItemID);
        flipkartReturnItemEntry.setTrackingId(trackingId);
        fkList.add(flipkartReturnItemEntry);
        flipkartReturnAttributeEntry.setReturnItems(fkList);
        retunEntry.setAttributes(flipkartReturnAttributeEntry);

        String payload = APIUtilities.getObjectToJSON(retunEntry);

        Svc service = HttpExecutorService.executeHttpService(SILKROUTE_PATH.PROCESS_RETURN, new String[] {store, "v0/returns/process"}, SERVICE_TYPE.SILKR_SVC.toString(), HTTPMethods.POST, payload, (HashMap<String, String>) getHeaaderForFKOauthReturn(store));
        return service;
    }
    
    /**
     * @param response
     * @param status
     * @param delaytoCheck
     * @return
     */
    public Boolean verifyResponseInSilkRoute(int response,int status, int delaytoCheck){
    	
    	Boolean isResponseSuccess = false;
    	for(int i=0;i<delaytoCheck;i++){
    		if(response==status){
    			isResponseSuccess = true;
    			return isResponseSuccess;
    		}
    		End2EndHelper.sleep(5000);
    	}
    	
    	return isResponseSuccess;
    }
    
    
    /**
     * This function is to verify SLA in OMS and Worms
     * @param orderRelease
     * @param slaDate
     * @return
     */
    public Boolean verifySLAInOrderRelease(String orderRelease,String slaDate){
    	
    	Boolean isSlaUpdated = false;
    	
    	//SLA verification in OMS
    	slaDate = SilkRouteConstants.DISPATCH_DATE_SLA.replaceAll("T", " ").split("\\.", 2)[0];   	
    	String sellerStartTime = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderRelease, "SELLER_PROCESSING_START_TIME").get("value").toString();
    	String sellerEndTime = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderRelease, "SELLER_PROCESSING_END_TIME").get("value").toString();
    	log.info("SellerProcessing StartTime In OMS: "+sellerStartTime);
    	log.info("SellerProcessing EndTime In OMS: "+sellerEndTime);
    	log.info("SLA Date: "+slaDate);
    	
    	//SLA verification in Worms
    	LinkedHashMap<String,Object> captureOrderReleaseData = (LinkedHashMap<String, Object>) wmsServiceHelper.getCaptureOrderReleaseEntry(orderRelease);
    	String sellerStartTimeInWorms = captureOrderReleaseData.get("seller_processing_start_time").toString().split("\\.", 2)[0];
    	String sellerEndTimeInWorms = captureOrderReleaseData.get("seller_processing_end_time").toString().split("\\.", 2)[0];
    	log.info("SellerProcessing StartTime In Worms: "+sellerStartTimeInWorms);
    	log.info("SellerProcessing EndTime In Worms: "+sellerEndTimeInWorms);
    	log.info("SLA Date: "+slaDate);
    	
     	if(sellerStartTime.equalsIgnoreCase(slaDate) && sellerEndTime.equalsIgnoreCase(slaDate) &&
     			sellerStartTimeInWorms.equalsIgnoreCase(slaDate) && sellerEndTimeInWorms.equalsIgnoreCase(slaDate)){
    		isSlaUpdated = true;
    	}
    	
    	return isSlaUpdated;
    }
    @SuppressWarnings("unchecked")
	public String getOrderItemId(String listingId,int quantity) throws UnsupportedOperationException, IOException{
		JSONObject json = new JSONObject();
        json.put("shipmentType", "");
        ArrayList<JSONObject> al=new ArrayList<JSONObject>();
        JSONObject listingInfoSro=new JSONObject();
        listingInfoSro.put("listingId",listingId);
        listingInfoSro.put("quantity", quantity);
        al.add(listingInfoSro);
        json.put("orderItems", al);
        Svc service=null;
		try {
			service = HttpExecutorService.executeHttpService(Constants.FLIPKART_PATH.TEST_ORDERS,
			            null, SERVICE_TYPE.FLIPKART_SVC.toString(), HTTPMethods.POST,
			            json.toString(),(HashMap<String,String>)getHeaderForFlipkartSandbox());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String testOrdersResponse = service.getResponseBody();
		JsonObject jsonResponse=new JsonObject();
		JsonParser parse=new JsonParser();
		jsonResponse=(JsonObject) parse.parse(testOrdersResponse);
		String orderItemId=jsonResponse.get("orderItems").getAsJsonArray().get(0).getAsJsonObject().get("orderItemId").getAsString();
		return orderItemId;
	}

	private HashMap<String, String> getHeaderForFlipkartSandbox() {
		 HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
	     createOrderHeaders.put("Authorization", "Bearer 6fde237b-46ed-493f-996f-68cf9341ea2f");
	     createOrderHeaders.put("Content-Type", "Application/json");
	     return createOrderHeaders;// TODO Auto-generated method stub
	}
}
