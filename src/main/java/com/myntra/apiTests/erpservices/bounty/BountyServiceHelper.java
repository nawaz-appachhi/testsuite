package com.myntra.apiTests.erpservices.bounty;

import argo.saj.InvalidSyntaxException;

import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.absolut.cart.client.response.entry.CartItemEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.portalservices.checkoutservice.AddressNodes;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.commons.IServiceConstants;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.bounty.client.entry.OrderCreationEntry;
import com.myntra.bounty.client.entry.OrderCreationRequestEntry;
import com.myntra.bounty.client.response.OrderCreationResponse;
import com.myntra.client.tools.response.ApplicationPropertiesResponse;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * @author Abhijit.Pati
 */
public class BountyServiceHelper {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(BountyServiceHelper.class);
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private int delaytoCheck=10;

    /**
     * Get Address ID
     *
     * @param paramLoginId
     * @param paramPassword
     * @return {@link String}
     */
    public String getAddress(String paramLoginId, String paramPassword) {
        String addressId = "";
        CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
        RequestGenerator getAllAddressReqGen = checkoutTestsHelper.getAllAddress(paramLoginId, paramPassword);
        String getAllAddressResponse = getAllAddressReqGen.respvalidate.returnresponseasstring();

        log.info("\nPrinting CheckoutService getAllAddress API response	 :\n\n" + getAllAddressResponse);

        String getAllAddressResponseType = getAllAddressReqGen.respvalidate
                .NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");

        if (getAllAddressReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TOTAL_COUNT.toString(), true)
                .equals(IServiceConstants.ZERO_VALUE)) {
            RequestGenerator addNewAddrReqGen = checkoutTestsHelper.addNewAddress(paramLoginId, paramPassword,
                                                                                  "#333, 33th cross, 18th main", "Bangalore", "IN", "false", paramLoginId, "Mico Layout",
                                                                                  paramLoginId.substring(0, paramLoginId.indexOf("@")), "560076", "KA", "Karnataka", paramLoginId,
                                                                                  "9880128920");
            String addNewAddressResponse = addNewAddrReqGen.respvalidate.returnresponseasstring();
            log.info("\nPrinting CheckoutService addNewAddress API response :\n\n" + addNewAddressResponse);

            String addNewAddrResponseType = addNewAddrReqGen.respvalidate
                    .NodeValue(StatusNodes.STATUS_TYPE.toString(), true).replace("\"", "");
            addressId = addNewAddrReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
        } else {
            addressId = getAllAddressReqGen.respvalidate.NodeValue(AddressNodes.ADDRESS_ID.toString(), true);
        }
        return addressId;
    }

    /**
     * Get XID for Single and multiple Skus.
     *
     * @param userName
     * @param password
     * @param skuAndQuantities
     * @param operation
     * @return
     */
    public String getXID(String userName, String password, String[] skuAndQuantities, String operation) {
        CheckoutTestsHelper helper = new CheckoutTestsHelper();
        RequestGenerator requestGenerator = helper.getXIDForMultipleItemToCart(userName, password, skuAndQuantities,
                                                                               operation, "", null, null, "");
        String sessionId = requestGenerator.response.getHeaderString("Set-Cookie").split(";")[0].split("=")[1];
        log.info("\nPrinting sessionId : " + sessionId + "\n");
        return sessionId;
    }


    /**
     * Create Cart Entry
     * @param userName
     * @param password
     * @param skuAndQuantities
     * @param operation
     * @param couponId
     * @param cashBack
     * @param loyaltyPoint
     * @param giftWrap
     * @return {@HashMap}
     */
    public HashMap<String, Object> getCreateOrderEntry(String userName, String password, String[] skuAndQuantities, String operation, String couponId,
                                                       String cashBack, String loyaltyPoint, String giftWrap) {
        CheckoutTestsHelper helper = new CheckoutTestsHelper();
        HashMap<String, Object> header = new HashMap<>();
        String sessionID = helper.getxID(userName, password, skuAndQuantities, operation, couponId, cashBack,
                                         loyaltyPoint, giftWrap);

        CartEntry cartEntry = null;

        try {
            cartEntry = CheckoutTestsHelper.getCart(userName, "DEFAULT", sessionID);
            List<CartItemEntry> cartItems = cartEntry.getCartItemEntries();
            for (CartItemEntry cartItemEntry : cartItems) {
                cartItemEntry.setTotalChargedAmount(BigDecimal.valueOf(cartItemEntry.getTotalPriceDecimal()));
            }
            cartEntry.setTotalChargedAmount(BigDecimal.valueOf(cartEntry.getSubTotalPriceDecimal()));
            header.put("xid", sessionID);
            header.put("cart", cartEntry);
        } catch (IOException | JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return header;
    }
    
	public HashMap<String, Object> getCreateOrderEntryWithCoupon(String userName,
			String password, String[] skuAndQuantities, String operation,
			String couponId, boolean cashBack, boolean loyaltyPoint,
			boolean giftWrap) throws UnsupportedEncodingException,
            JAXBException, InvalidSyntaxException {
		CheckoutTestsHelper helper = new CheckoutTestsHelper();
		HashMap<String, Object> header = new HashMap<>();
		String sessionID = helper.getxIDForCoupon(userName, password, skuAndQuantities,
				operation, couponId, cashBack, loyaltyPoint, giftWrap);

		CartEntry cartEntry = null;

		try {
			cartEntry = CheckoutTestsHelper.getCart(userName, "DEFAULT",
					sessionID);
			List<CartItemEntry> cartItems = cartEntry.getCartItemEntries();
			for (CartItemEntry cartItemEntry : cartItems) {
				cartItemEntry.setTotalChargedAmount(BigDecimal
						.valueOf(cartItemEntry.getTotalPriceDecimal()));
			}
			cartEntry.setTotalChargedAmount(BigDecimal.valueOf(cartEntry
					.getSubTotalPriceDecimal()));
			header.put("xid", sessionID);
			header.put("cart", cartEntry);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return header;
	}



    /**
     * @param loginID
     * @param password
     * @return
     */
    public static String Emptycart(String loginID, String password) {
        String[] sesid = CheckoutTestsHelper.idp_GetTokens(password, password);
        String sessionId = sesid[0];
        System.out.println("session id is:" + sessionId);
        return (sessionId);
    }

    public static HashMap<String, String> getBountyHeaderXML() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
        createOrderHeaders.put("Content-Type", "Application/xml");
        createOrderHeaders.put("Accept", "application/xml");
        return createOrderHeaders;
    }

    /**
     *
     * @param cartEntry
     * @param ppsId
     * @param login
     * @param sessionId
     * @param binNumber
     * @param pgDiscount
     * @param emiCharge
     * @param address
     * @param clientContext
     * @param cartContext
     * @param paymentMethod
     * @return
     * @throws IOException
     * @throws JAXBException
     */
    public OrderResponse createOrder(CartEntry cartEntry, String ppsId, String login, String sessionId,
                                     String binNumber, String pgDiscount, String emiCharge, String address, String clientContext,
                                     String cartContext, String paymentMethod) throws IOException, JAXBException {


        OrderCreationRequestEntry orderCreationRequestEntry=new OrderCreationRequestEntry();
        orderCreationRequestEntry.setCart(cartEntry);
        orderCreationRequestEntry.setPpsId(ppsId);
        orderCreationRequestEntry.setLogin(login);
        orderCreationRequestEntry.setSessionId(sessionId);
        orderCreationRequestEntry.setBinNumber(binNumber);
        orderCreationRequestEntry.setPgDiscount(Float.valueOf(pgDiscount));
        orderCreationRequestEntry.setEmiCharge(Float.valueOf(emiCharge));
        orderCreationRequestEntry.setAddress(address);
        orderCreationRequestEntry.setClientContext(clientContext);
        orderCreationRequestEntry.setCartContext(cartContext);
        orderCreationRequestEntry.setPaymentMethod(paymentMethod);


        String payload = APIUtilities.getObjectToJSON(orderCreationRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.CREATE_ORDER_V2,  null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.POST, payload, BountyServiceHelper.getBountyHeader());
        OrderResponse orderCreationResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderResponse());
        return orderCreationResponse;
    }


    /**
     *
     * @param storeOrderId
     * @param sessionId
     * @param loginId
     * @param cartContext
     * @param paymentStatus
     * @param putOnHold
     * @return
     * @throws JAXBException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public OrderCreationResponse queueOrder(String storeOrderId, String sessionId, String loginId,
                                            String cartContext, String paymentStatus,
                                            boolean putOnHold) throws JAXBException, IOException {
        OrderCreationRequestEntry orderCreationRequestEntry=new OrderCreationRequestEntry();
        orderCreationRequestEntry.setStoreOrderId(storeOrderId);
        orderCreationRequestEntry.setLogin(loginId);
        orderCreationRequestEntry.setSessionId(sessionId);
        orderCreationRequestEntry.setPaymentStatus(paymentStatus);
        orderCreationRequestEntry.setCartContext(cartContext);
        orderCreationRequestEntry.setPutOnHold(putOnHold);

        String payload = APIUtilities.getObjectToJSON(orderCreationRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.QUEUE_ORDER_V2,  null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.POST, payload, BountyServiceHelper.getBountyHeader());
        OrderCreationResponse orderCreationResponse = (OrderCreationResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderCreationResponse());
        return orderCreationResponse;
    }


    /**
     * Confirm OnHold Order - Bounty
     *
     * @param storeOrderId
     * @param paymentStatus
     * @return {@link OrderCreationResponse}
     */
    public OrderCreationResponse confirmOnHoldOrder(String storeOrderId, String paymentStatus, String ppsID, boolean putOnHold) throws IOException {

        OrderCreationRequestEntry orderCreationEntry = new OrderCreationRequestEntry();
        orderCreationEntry.setStoreOrderId(storeOrderId);
        orderCreationEntry.setPaymentStatus(paymentStatus);
        orderCreationEntry.setPpsId(ppsID);
        orderCreationEntry.setPutOnHold(putOnHold);


        String payload = APIUtilities.getObjectToJSON(orderCreationEntry);

        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.CONFIRM_ORDER_V2,  null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.POST, payload, BountyServiceHelper.getBountyHeader());
        OrderCreationResponse orderCreationResponse = (OrderCreationResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderCreationResponse());
        return orderCreationResponse;
    }


    /**
     * Decline Order API
     * @param storeOrderId
     * @param login
     * @param sessionID
     * @param cartContext
     * @param paymentStatus
     * @param declineReasonId
     * @param status
     * @return {@link OrderCreationResponse}
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderCreationResponse declineOrder(String storeOrderId, String login, String sessionID, String cartContext, String paymentStatus, long declineReasonId, boolean status) throws JAXBException, UnsupportedEncodingException {
        //Payload: {"id":70126806,"login":"5d3b5d38.3f1d.47e1.8c6a.a3e3c7bab280Xzkxh99FgC","sessionId":"JJN67f3adb95e0011e691b122000a90a0271470726651G",
        // "cartContext":"DEFAULT","paymentStatus":"FAILURE","putOnHold":false,"declineReasonId":2}
        OrderCreationRequestEntry orderCreationRequestEntry=new OrderCreationRequestEntry();
        orderCreationRequestEntry.setStoreOrderId(storeOrderId);
        orderCreationRequestEntry.setLogin(login);
        orderCreationRequestEntry.setSessionId(sessionID);
        orderCreationRequestEntry.setCartContext(cartContext);
        orderCreationRequestEntry.setPaymentStatus(paymentStatus);
        orderCreationRequestEntry.setPutOnHold(status);
        orderCreationRequestEntry.setDeclineReasonId(declineReasonId);
        orderCreationRequestEntry.setDoNotify(true);
        String payload = APIUtilities.convertXMLObjectToString(orderCreationRequestEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.DECLINE_ORDER_V2,  null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.POST, payload, BountyServiceHelper.getBountyHeaderXML());
        OrderCreationResponse orderCreationResponse = (OrderCreationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderCreationResponse());
        return orderCreationResponse;

    }

    /**
     * Put Order On-hold
     * @param storeOrderId
     * @param paymentStatus
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public OrderCreationResponse putOrderInHold(String storeOrderId, String paymentStatus) throws JAXBException, UnsupportedEncodingException {
        //Payload: {"id":70126806,"login":"5d3b5d38.3f1d.47e1.8c6a.a3e3c7bab280Xzkxh99FgC","sessionId":"JJN67f3adb95e0011e691b122000a90a0271470726651G",
        // "cartContext":"DEFAULT","paymentStatus":"FAILURE","putOnHold":false,"declineReasonId":2}
        //   OrderCreationRequestEntry orderCreationRequestEntry=new OrderCreationRequestEntry();
        OrderCreationEntry orderCreationEntry = new OrderCreationEntry();
        orderCreationEntry.setStoreOrderId(storeOrderId);
        orderCreationEntry.setPaymentStatus(paymentStatus);
        String payload = APIUtilities.convertXMLObjectToString(orderCreationEntry);
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.PUT_ORDER_ONHOLD,  null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.POST, payload, BountyServiceHelper.getBountyHeaderXML());
        OrderCreationResponse orderCreationResponse = (OrderCreationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderCreationResponse());
        return orderCreationResponse;

    }


    /**
     * Get Order API
     *
     * @param orderID
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public OrderResponse getOrder(String orderID) throws JsonParseException, JsonMappingException, IOException {

        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.GET_ORDER_V0, new String[]{""+orderID}, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.GET, null, getBountyHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderResponse());
        org.testng.Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderResponse;

    }

    /**
     * Get Order By Store Order ID
     * @param storeOrderId
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public OrderResponse getOrderByStoreOrderId(String storeOrderId) throws JsonParseException, JsonMappingException, IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.GET_ORDER_V0, new String[]{""+storeOrderId}, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.GET, null, getBountyHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderResponse());
        org.testng.Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        return orderResponse;

    }
    /**
     * Headers for the Bounty Service
     *
     * @return
     */
    private static HashMap<String, String> getBountyHeader() {
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/json");
        createOrderHeaders.put("Accept", "Application/json");
        return createOrderHeaders;
    }

    /**
     * Get Block Order count from ATP
     *
     * @param skuid
     * @return
     */
    public static int getATPboc(String skuid) {
        int blockedOrderCount = 0;
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            List resultSet = DBUtilities
                    .exSelectQuery("select blocked_order_count from inventory where sku_id =" + skuid + ";", "myntra_atp");
            HashMap<String, Object> row = (HashMap<String, Object>) resultSet.get(0);
            blockedOrderCount = (int) row.get("blocked_order_count");
            log.info("ATP Block Order Count : " + blockedOrderCount);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return blockedOrderCount;
    }


    /**
     *
     * @param skuQuanties
     * @param ppsId
     * @param login
     * @param password
     * @param binNumber
     * @param pgDiscount
     * @param emiCharge
     * @param address
     * @param clientContext
     * @param cartContext
     * @param paymentMethod
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws JAXBException
     */
    public HashMap<String, String> getOrderEntryFromCreateOrderResponse(String[] skuQuanties, String ppsId,
                                                                        String login, String password, String binNumber, String pgDiscount, String emiCharge, String address,
                                                                        String clientContext, String cartContext, String paymentMethod)
            throws IOException, JAXBException {
        HashMap<String, String> oce = new HashMap<>();
        String orderId=null;
        HashMap<String, Object> cartEntry = getCreateOrderEntry(login, password, skuQuanties, "ADD", "","","","");


        OrderResponse orderEntry = createOrder((CartEntry) cartEntry.get("cart"), ppsId, login, ""+cartEntry.get("xid"), binNumber, pgDiscount,
                                               emiCharge, address, clientContext, cartContext, paymentMethod);
        orderId = "" + orderEntry.getData().get(0).getId();
        String storeOrderID = orderEntry.getData().get(0).getStoreOrderId();

        log.info("Order ID From Create Order Response = " + orderId);
        oce.put("orderID", orderId);
        oce.put("storeOrderID", storeOrderID);
        oce.put("xid", ""+cartEntry.get("xid"));
        return oce;
    }

    public HashMap<String, Object> getOrderTableDBEntry(long orderID) {
        HashMap<String, Object> row = null;
        try {
            List resultSet = DBUtilities.exSelectQuery("select * from orders where id =" + orderID + ";", "myntra_oms");
            row = (HashMap<String, Object>) resultSet.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * @param orderNumber
     * Get Order Entry in xcart_orders
     * @return row entry
     */
    public HashMap<String, Object> getxCartOrderTableDBEntry(String orderNumber) {
        HashMap<String, Object> row = null;
        List resultSet = null;
        
        String shradID = orderNumber.subSequence(orderNumber.length()-2, orderNumber.length()).toString();
        for (int i = 0; i < delaytoCheck; i++) {
        	if(shradID.equals("01")) {
                resultSet = DBUtilities.exSelectQuery("select * from xcart_orders where order_number ='" + orderNumber + "';",
                        "myntra_order");
            }else if(shradID.equals("02")){
                resultSet = DBUtilities.exSelectQuery("select * from xcart_orders where order_number ='" + orderNumber + "';",
                        "myntra_order_2");
            }
        	if(resultSet!=null){
        		break;
        	}else{
        		End2EndHelper.sleep(5000);
        	}
        }
        
        row = (HashMap<String, Object>) resultSet.get(0);
        return row;
    }
    
    /**
     * @param orderId
     * Get Order Details Entry in xcart_order_details
     * @return List of row entries
     */
    public List getxCartOrderDetailsTableDBEntry(long orderId) {

        List resultSet = DBUtilities.exSelectQuery("select * from xcart_order_details where orderid =" + orderId + ";",
                                                   "myntra_order");
        return resultSet;
    }

    /**
     * @param orderNumber
     * Get Order Details Entry in xcart_order_details
     * @return List of row entries
     */
    public List getxCartOrderDetailsTableDBEntry(String orderNumber) {

        List resultSet = null;
        long orderid = (long) getxCartOrderTableDBEntry(orderNumber).get("orderid");
        String shradID = orderNumber.subSequence(orderNumber.length()-2, orderNumber.length()).toString();
        if(shradID.equals("01")){
            resultSet = DBUtilities.exSelectQuery("select * from xcart_order_details where orderid =" + orderid + ";",
                    "myntra_order");
        }else if(shradID.equals("02")){
            resultSet = DBUtilities.exSelectQuery("select * from xcart_order_details where orderid =" + orderid + ";",
                    "myntra_order_2");
        }

        return resultSet;
    }

    /**
     * @param orderNumber
     * Get Order Details Entry in xcart_order_details
     * @return List of row entries
     */
    public List getxCartOrderDetailsTableDBEntry(String orderNumber, String query) {

        List resultSet = null;
        String shradID = orderNumber.subSequence(orderNumber.length()-2, orderNumber.length()).toString();
        if(shradID.equals("01")){
            resultSet = DBUtilities.exSelectQuery(query, "myntra_order");
        }else if(shradID.equals("02")){
            resultSet = DBUtilities.exSelectQuery(query, "myntra_order_2");
        }

        return resultSet;
    }

    public HashMap<String, Object> getxcart_discount_couponsDBEntry(String couponId) {
        HashMap<String, Object> row = null;

        List resultSet = DBUtilities.exSelectQuery("select * from xcart_discount_coupons where coupon ='" + couponId + "';",
                                                   "myntra");
        row = (HashMap<String, Object>) resultSet.get(0);
        return row;
    }

    static HashMap<String, String> skuHashMap = null;
    static Vector<String> skuVect = new Vector<String>();
    static Vector<String> whVect = null;
    static final String AVAILABLE_WH = "1,2,19";
    static String tempWh = null;
    static int isDiffCount = 0;

    public static void getSkus() {
        String validSkuId = null;
        skuHashMap = new HashMap<String, String>();
        String categoryArr[] = { "Clothing" };
        CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
        skuHashMap.put("1", "3832");
        skuHashMap.put("19",
                       "934043,934044,934045,934046,915287,915288,915289,915290,930816,930818,930819,930820,915275,915276,915277,915278,1222485,1222487,915315,915317,915318,1100704,1100705,1100706,964430,964431,964432,964433,915279,915280,915281,915282,1136346,1136345,1136344,1136347,927533,927534,1171475,1171474,1171473,1171472,927551,927552,927553,927554,1059345,1059346,832857,832858,832860,921568,921569,921570,921571,1212626,1212628,1212629,934023,934024,934026,1100691,1100692,1100693,1100694,1100699,1100700,1100701,1100702,850985,850987,848055,848056");
        System.out.println("SKu List=" + skuHashMap);
        if (skuHashMap.keySet().size() == 1) {
            System.out.println(">>>>>Minimum two warehouse data is required to run.");
            skuHashMap = null;
        }

    }





    public static List<String> getMarketPlaceOrderID(String userId, String password, int itemSkuCount,
                                                     int[] itemCountArr, String payMode, boolean isDiffTest, String skuList) {
        return null;
    }

    public void deleteBountyDBEntries(String orderID) throws SQLException {
        String deleteXcartOrderEntry = "Delete from xcart_orders where orderid in (" + orderID + ");";
        String deleteXcartOrderDetailsEntry = "delete from xcart_order_details where orderid in (" + orderID + ");";

        DBUtilities.exUpdateQuery(deleteXcartOrderDetailsEntry, "myntra_order");
        DBUtilities.exUpdateQuery(deleteXcartOrderEntry, "myntra_order");
    }

    /**
     * Push PP/PV/Q Order to OMS
     * @param orderID
     * @throws JAXBException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public void pushOrderToOMS(String orderID) throws IOException {
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.PUSHORDERTOOMS, new String[]{orderID}, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.GET, null, getBountyHeader());
        OrderResponse orderResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new OrderResponse());
        org.testng.Assert.assertEquals(orderResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    }

    /**
     * Refresh BOUNTY Application Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void refreshBountyApplicationPropertyCache() throws IOException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.BOUNTY_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.BOUNTY_SVC.toString(), HTTPMethods.GET, null, getBountyRefreshHeader());
        ApplicationPropertiesResponse cacheRefresh = (ApplicationPropertiesResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ApplicationPropertiesResponse());
        Assert.assertEquals(cacheRefresh.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    }

    private HashMap<String, String> getBountyRefreshHeader(){
        HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
        createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
        createOrderHeaders.put("Content-Type", "Application/xml");
        createOrderHeaders.put("Accept", "Application/xml");
        return createOrderHeaders;
    }
    
    /**
     * @param uidx ,coupon ,discountPercentage
     * Create new coupon
     * @return couponId
     */
    public String createNewCoupon(String uidx, String coupon, double discountPercent)
    {
    	HashMap<String, Object> row = null;
    	List resultSet = null;
    	DBUtilities.exUpdateQuery(
				"Delete from xcart_discount_coupons where coupon in ('"+ coupon+"') and users='" + uidx
						+ "';",
				"myntra");
    	End2EndHelper.sleep(20000);
    	
    	String query = "INSERT INTO `xcart_discount_coupons` (`coupon`, `groupid`, `discount`, `coupon_type`, `productid`, `categoryid`, `minimum`, `times`, `per_user`, `times_used`, `expire`, `status`, `provider`, `recursive`, `apply_category_once`, `apply_product_once`, `freeship`, `styleid`, `maximum`, `producttypeid`, `promotionid`, `times_locked`, `startdate`, `groupName`, `isInfinite`, `maxUsagePerCart`, `subtotal`, `discountOffered`, `maxUsageByUser`, `isInfinitePerUser`, `maxAmount`, `users`, `excludeUsers`, `paymentMethod`, `timeCreated`, `comments`, `couponType`, `MRPAmount`, `MRPpercentage`, `freeShipping`, `excludeProductTypeIds`, `excludeStyleIds`, `catStyleIds`, `excludeCatStyleIds`, `excludeCategoryIds`, `excludeSKUs`, `productTypeIds`, `styleIds`, `categoryIds`, `SKUs`, `showInMyMyntra`, `description`, `lastEdited`, `lastEditedBy`, `timeReminderEmailSent`, `min_count`, `max_count`, `assigned_on`, `assigned_type`)"+
     "VALUES"+
	"('"+coupon+"', NULL, 0.00, '', 0, 0, 0.00, 1, '', 1, 1513017000, 'A', '', '', '', '', '', NULL, 0.00, NULL, NULL, 276, 1449858600, 'AshuPLCTest', 1, -1, 113066.00, 22613.20, 1, 1, 0.00, '"+uidx+"', '', NULL, 1452538016, 'test', 'percentage', 0.00,"+ discountPercent+", 0, '', '', NULL, NULL, '', '', NULL, '', '', '', 1, 'test', 1452538038, 'engg.provider', NULL, 0, 0, NULL, 'UIDX');";
    	
    	DBUtilities.exUpdateQuery(query,"myntra");
    	
    	resultSet = DBUtilities.exSelectQuery(
				"select * from xcart_discount_coupons where coupon in ('"+ coupon+"') and users='" + uidx
						+ "';",
				"myntra");
    	
    	row = (HashMap<String, Object>) resultSet.get(0);
    	
    	return (String) row.get("coupon");
    }
    
    /**
     * @param skuEntries
     * @param cartEntry
     * @return
     */
    public CartEntry setTryAndBuyInCart(HashMap<Long,Boolean> skuEntries,CartEntry cartEntry ){
    	
    	List<CartItemEntry> cartItemEntries = cartEntry.getCartItemEntries();
    	for(CartItemEntry cartItemEntry:cartItemEntries){
    		Long skuId = cartItemEntry.getSkuId();
    		if(skuEntries.get(skuId).equals(Boolean.TRUE)){
    			cartItemEntry.setTryAndBuyOpted(Boolean.TRUE);
    			cartItemEntry.setTryAndBuyEnabled(Boolean.TRUE);
    		}
    	}
    	
    	return cartEntry;
    }


}