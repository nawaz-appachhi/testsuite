package com.myntra.apiTests.portalservices.all;

import argo.saj.InvalidSyntaxException;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.giftCardPpsHelper.GiftCardPpsNewServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MultiTenantOrderPlacementTest extends BaseTest{
	
	private static final SlackSession session = SlackSessionFactory.
			createWebSocketSlackSession("xoxb-143439462852-E4gWQ9P0VNm6Tm10yn5NAYXY");
	private static boolean isSlackEnabled = true;

	String skuId = "3866"; // style id = 1530 (skuid = 3830,3834)
	String skuId_online = "3834";
	String styleId="1541";
	String exchangeSkuId="3833";
	String styleId_AutoGC="1531";
	String skuId_AutoGC="3832";
	String exchange_AutoskuId="862081";
	String styleId_ManualGC="170187";
	String skuId_ManualGC="580644";
	String exchange_ManualskuId="1069172";
	String gcNumber = "6991201057856234";
	String gcPin = "240465";
	String gcType = "GIFTBIG";
	String csrf_token = "";
	String xid = "";
	static String slackChannel ="m_test";
	Document doc;
	
	
	static String PPS_Host="";
	static String CartPage_Host="";
	static String CartPage_Referer="";
	static String AddressPage_Host="";
	static String AddressPage_Referer="";
	static String PaymentPage_Host="";
	static String PaymentPage_Referer="";
	static String Pps_Referer="";
	static String profile = "";
	static String uidx="";
	
    NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	GiftCardPpsNewServiceHelper giftcardHelper = new GiftCardPpsNewServiceHelper();
	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
	String env="";
	String isDockinsEnabled="";
	String dockEnvName="";
	String tenantId="";
	
	PPSServiceHelper ppsServiceHelper; 

	 @BeforeClass(alwaysRun=true) 
	 public void beforeSuite() {
	       // SlackMessenger.connect();
	    	Date dNow = new Date( );
	        SimpleDateFormat ft = 
	        new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
			if(isSlackEnabled && !session.isConnected()){
				try {
					session.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//Getting the values from jenkins
	    env=System.getenv("environment");
		isDockinsEnabled=System.getenv("isDockinsEnabled"); 
		dockEnvName=System.getenv("dockEnvName");
	    	tenantId=System.getenv("tenantId");
		//env="stage";	
		//isDockinsEnabled="true";
		//dockEnvName="reftest1";
		//tenantId="jabong";
			 ppsServiceHelper = new PPSServiceHelper(tenantId);
			
			if((dockEnvName!=null) && (isDockinsEnabled.equalsIgnoreCase("true")))
			{
				System.out.println("Inside dockins");
				PPS_Host = dockEnvName + "-pps.dockins.myntra.com";
				CartPage_Host=dockEnvName+"-browsehaproxy.dockins.myntra.com";
				CartPage_Referer=dockEnvName+"-browsehaproxy.dockins.myntra.com";
				AddressPage_Host=dockEnvName+"-browsehaproxy.dockins.myntra.com";
				AddressPage_Referer="https://"+dockEnvName+"-browsehaproxy.dockins.myntra.com/checkout/cart";
				PaymentPage_Host=dockEnvName+"-browsehaproxy.dockins.myntra.com";
				PaymentPage_Referer="https://"+dockEnvName+"-browsehaproxy.dockins.myntra.com/checkout/address";
				Pps_Referer="http://"+dockEnvName+"-pps.dockins.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = dockEnvName+"-browsehaproxy.dockins.myntra.com";
			}
			else if((env.equalsIgnoreCase("stage")) && (isDockinsEnabled.equalsIgnoreCase("false")))
			{
				PPS_Host = "pps.stage.myntra.com";
				CartPage_Host="stage.myntra.com";
				CartPage_Referer="https://stage.myntra.com/";
				AddressPage_Host="stage.myntra.com";
				AddressPage_Referer="https://stage.myntra.com/checkout/cart";
				PaymentPage_Host="stage.myntra.com";
				PaymentPage_Referer="https://stage.myntra.com/checkout/address";
				Pps_Referer="http://pps.stage.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = "stage.myntra.com";
			}
			else if((env.equalsIgnoreCase("m7")) && (isDockinsEnabled.equalsIgnoreCase("false")))
			{
				PPS_Host = "m7pps.mpreprod.myntra.com";
				CartPage_Host="m7.mpreprod.myntra.com";
				CartPage_Referer="https://m7.mpreprod.myntra.com/";
				AddressPage_Host="m7.mpreprod.myntra.com";
				AddressPage_Referer="https://m7.mpreprod.myntra.com/checkout/cart";
				PaymentPage_Host="m7.mpreprod.myntra.com";
				PaymentPage_Referer="https://m7.mpreprod.myntra.com/checkout/address";
				Pps_Referer="http://m7pps.mpreprod.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = "m7.mpreprod.myntra.com";
			}
			else
			{
				Assert.fail();
			}
			
		   send(slackChannel, "*............ PPS Service Regression started ............*");
		   send(slackChannel,"Starting time : " + ft.format(dNow));
	    }
	
    
    @Test(groups= {"Regression"})
    public void placeOnlineOrderCombinations () throws UnsupportedEncodingException, InvalidSyntaxException, NumberFormatException, ParseException, JAXBException, InterruptedException, SCMExceptions, JSONException {
	System.out.println("Env is :"+env+"dockEnvName is : "+dockEnvName+"isDockinsEnabled is : "+isDockinsEnabled + "tenantId is: " +tenantId);
	String TestCaseDesc="Online order";
	String email="automationtest@myntra.com";
	String password="123456";
	String pm="creditcard";
	String clientCode = "responsive";
    	String cartContext = "default";
    	String userGroup = "normal";
    	String wallet_enabled = "true";
    	String other_cards = "false";										
    	String card_number = "4556053172667502";
    	String bill_name = "Test card";
    	String card_month = "07";
    	String card_year = "22";
    	String cvv_code = "123";
    	String useSavedCard = "false";
    	String manualGCAmount = "10";
    	String manualGCCount = "1";
    	String autoGCAmount = "10";
    	String gcNumber = "6991201057856234";
    	String gcPin = "240465";
    	String gcType = "GIFTBIG";
    
    	HashMap<String,String>giftCardHeader = new HashMap<String,String>();
    giftCardHeader.put("gcNumber", gcNumber);
    giftCardHeader.put("gcPin", gcPin);
    giftCardHeader.put("manualGCAmount", manualGCAmount);
    	
    	String slackMessage = ">*`"+TestCaseDesc+"`*";
    	
    	send(slackChannel, slackMessage);

    	String uidx=giftcardHelper.getUidxFromEmail(email);
    	String qty="1";
    	boolean isAutoGC = false;
    	boolean isManualGC = false;
    	boolean isGiftWrap = false;
    	boolean isTryNBuy = false;
    	
    	//Adding the item to cart
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    	HashMap<String,String>cartHeader=addItemToCartPage(email,password,qty);;
    	
    	// checkoutTestHelper.ppsFormGetXIDForMultipleItemToCartAuto(email, password,new String[]{"3834:1"}, "",false,isLoyaltyApplicable,totalLoyaltyPointsAvailable,isGiftWrap,isTryNBuy,isAutoGC,isManualGC,myntCode,cartHeader,giftCardHeader);
    	String isAutoGCEnable="false";
    	String isManualGCEnable="false";
    	// Navigate to Payment Page
    	String response = paymentPage(email, password,cartHeader,giftCardHeader,useSavedCard,pm,clientCode,cartContext,profile, userGroup,wallet_enabled, other_cards,  card_number,  bill_name,
       		 card_month, card_year, cvv_code,isAutoGCEnable,autoGCAmount,isManualGCEnable,manualGCCount);
		Thread.sleep(10000);
		
		// extract orderid from html response
		String result1[]=ppsServiceHelper.getPPSIdAndOrderIdFromResponse(response);
        String ppsId=result1[0];
        String orderId=result1[1];
       
        send(slackChannel, "Order ID : " +orderId);
        send(slackChannel, "PPS ID : " +ppsId);
        Reporter.log("Order ID : " +orderId);
        Reporter.log("PPS ID : " +ppsId);

        // Verify payment plan
		Thread.sleep(5000);
        String paymentPlanItems=ppsServiceHelper.verifyPaymentPlanAndReturnItemList(ppsId);
        send(slackChannel, "Order State : "+"PPFSM Order Taking done");
        
    }
    
    
  
    private String paymentPage(String email, String password, HashMap<String, String> cartHeader,
    		HashMap<String, String> giftCardHeader, String useSavedCard, String pm, String clientCode, String cartContext, String profile,
    		String userGroup, String wallet_enabled, String other_cards, String card_number, String bill_name, String card_month, 
    		String card_year, String cvv_code, String isAutoGCEnable, String autoGCAmount, String isManualGCEnable, String manualGCCount) throws ParseException, JSONException {
    		
    		HashMap<String,String>addressDetails = navigateToPaymentPage(email, password,cartHeader);
        // Get Payments Page and extract token
      	String encodedcsrf=ppsServiceHelper.getEncodedTokenFromPaymentPage(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(),PaymentPage_Host,PaymentPage_Referer);

      	String payload = createPayloadOnline ( email,useSavedCard, addressDetails.get("addressId").toString(),  pm,  clientCode,
        		cartContext, profile, userGroup,  addressDetails.get("mobile").toString(),
        		 wallet_enabled, other_cards,  card_number,  bill_name,
        		 card_month, card_year, cvv_code, 
        		 addressDetails.get("firstname").toString(),  addressDetails.get("defaultAddress").toString(),  addressDetails.get("city").toString(),  addressDetails.get("state").toString(),
        		 addressDetails.get("zipcode").toString(),  addressDetails.get("country").toString(), isAutoGCEnable,autoGCAmount,encodedcsrf,isManualGCEnable,giftCardHeader.get("gcNumber"),
        		 giftCardHeader.get("gcPin"),giftCardHeader.get("manualGCAmount"),manualGCCount);
      	
      	System.out.println("payload="+payload);  	
    	String responseN = ppsServiceHelper.getPaynowResponse(cartHeader.get("xid").toString(),PPS_Host,encodedcsrf,payload);
    	
    	
    	System.out.println("Paynowform response is :" + responseN);
    	if(pm.equalsIgnoreCase("creditcard"))
    	{
		String cardInfoPayload=extractCardInfoDetails (responseN);
		
        // Hit test pg response API 
		responseN=ppsServiceHelper.getPGResponse(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(),PPS_Host,Pps_Referer,cardInfoPayload);
    	}
		return responseN;
    	    
	}

    
    
	private HashMap<String, String> addItemToCartPage(String email, String password, String qty) {
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    	
    	// Login and clear all items in cart 
    	HashMap<String, String> cartHeader = loginAndClearItemsInCart(email, password);
    	
    // Add item to cart page
    	RequestGenerator req_addItemToCart=checkoutTestHelper.addItemToCartWithQty(skuId_online, qty, "ADD", cartHeader);	
    	return cartHeader;

	}
	
	

	private HashMap<String, String> navigateToPaymentPage(String email, String password, HashMap<String, String> cartHeader) throws ParseException, JSONException {
    	
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper();

    	//Get cart page
    	RequestGenerator req_fetchCart = checkoutTestHelper.getCartPage(cartHeader.get("xid").toString(), CartPage_Host, CartPage_Referer);

    	//Get address page
    	RequestGenerator req_fetchAddress = checkoutTestHelper.getAddressPage(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(), CartPage_Host, CartPage_Referer);

        
    	//Getting default address
    	org.json.JSONObject defaultAddress = checkoutTestHelper.getDefaultAddress(email, password);
        String mobile = defaultAddress.getString("mobile").trim();
        String addressId = defaultAddress.get("id").toString().trim();
        String address = defaultAddress.getString("address").trim();
        String b_firstname = defaultAddress.getString("name").trim();
        String b_city = defaultAddress.getString("city").replace(",", "").trim();
        String b_state = defaultAddress.getString("stateName").trim();
        String b_zipcode = defaultAddress.getString("pincode").trim();
        String b_country = defaultAddress.getString("countryCode").trim();
        
        HashMap<String,String>addressDetails = new HashMap<String,String>();
        addressDetails.put("mobile", mobile);
        addressDetails.put("addressId", addressId);
        addressDetails.put("defaultAddress", address);
        addressDetails.put("firstname", b_firstname);
        addressDetails.put("city", b_city);
        addressDetails.put("state", b_state);
        addressDetails.put("zipcode", b_zipcode);
        addressDetails.put("country", b_country);
        
        //Get session details
        String response_fetchSession=checkoutTestHelper.getSessionDetails(xid);
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(response_fetchSession.toString());
		jsonObj.put("payment_address", addressId);
		String newResponse = jsonObj.toString();
		
		// Update session details
		String response_sessionU=checkoutTestHelper.updateSessionDetails(xid,newResponse);
		
		return addressDetails;

	}

	private HashMap<String, String> loginAndClearItemsInCart(String email, String password) {
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    	
    	// Extract xid from idp login API
		xid=checkoutTestHelper.getXID(email, password);
		
		// Extract sxid and csrf token from get session API
		String result[]=checkoutTestHelper.getSxidAndUserToken(xid);
		String user_token = result[0];
		String sxid=result[1];

		// clear all items in cart
		HashMap<String, String> cartHeader=new HashMap<String,String>();
		cartHeader.put("X-CSRF-Token", sxid);
		cartHeader.put("xid", xid);
		cartHeader.put("token", user_token);
		checkoutTestHelper.deleteCart(cartHeader);
		return cartHeader;

	}

	public long getReleaseId(long orderID){
        Map<String,Object> orderReleaseDB = DBUtilities.exSelectQueryForSingleRecord("select id from order_release where order_id_fk = "+orderID,"oms");
        return (Long)orderReleaseDB.get("id");
    }

    
    private String createPayloadOnline(String email,String useSavedCard,String address, String pm, String clientCode,
    		String cartContext, String profile, String userGroup, String mobile,
    		String wallet_enabled,String other_cards, String card_number, String bill_name,
    		String card_month, String card_year, String cvv_code, 
    		String b_firstname, String b_address, String b_city, String b_state,
    		String b_zipcode, String b_country, String auto_giftcard_used,String auto_giftcard_amount
			,String token,String manual_giftcard_used,String cardNumber,String pin,String amount,String count)
    {
    	StringBuffer encodedURL = new StringBuffer();

    	if(pm.equalsIgnoreCase("cod"))
    	{
    		String userinputcaptcha ="246";
    		encodedURL.append("address=");encodedURL.append(address.trim());encodedURL.append("&");
    		encodedURL.append("clientCode=");encodedURL.append(clientCode.trim());encodedURL.append("&");
        encodedURL.append("cartContext=");encodedURL.append(cartContext.trim());encodedURL.append("&");
        encodedURL.append("pm=");encodedURL.append(pm.trim());encodedURL.append("&");
        encodedURL.append("profile=");encodedURL.append(profile.trim());encodedURL.append("&");
        encodedURL.append("userGroup=");encodedURL.append(userGroup.trim());encodedURL.append("&");
        encodedURL.append("email=");encodedURL.append(email.trim());encodedURL.append("&");
        encodedURL.append("mobile=");encodedURL.append(mobile.trim());encodedURL.append("&");
        encodedURL.append("b_firstname=");encodedURL.append(b_firstname.trim());encodedURL.append("&");
    		encodedURL.append("b_address=");encodedURL.append(b_address.trim());encodedURL.append("&");
        encodedURL.append("b_city=");encodedURL.append(b_city.trim());encodedURL.append("&");
        encodedURL.append("b_state=");encodedURL.append(b_state.trim());encodedURL.append("&");
    		encodedURL.append("b_zipcode=");encodedURL.append(b_zipcode.trim());encodedURL.append("&");
        encodedURL.append("b_country=");encodedURL.append(b_country.trim());encodedURL.append("&");
    		encodedURL.append("wallet_enabled=");encodedURL.append(wallet_enabled.trim());encodedURL.append("&");
    		encodedURL.append("csrf_token=");encodedURL.append(token.trim());encodedURL.append("&");
    		encodedURL.append("userinputcaptcha=");encodedURL.append(userinputcaptcha.trim());encodedURL.append("&");
    	}
    	
    	else if(pm.equalsIgnoreCase("giftcard")||(pm.equalsIgnoreCase("cashback")))
    	{
    		//String userinputcaptcha ="246";
    	encodedURL.append("address=");encodedURL.append(address.trim());encodedURL.append("&");
    	encodedURL.append("clientCode=");encodedURL.append(clientCode.trim());encodedURL.append("&");
        encodedURL.append("cartContext=");encodedURL.append(cartContext.trim());encodedURL.append("&");
        encodedURL.append("pm=");encodedURL.append(pm.trim());encodedURL.append("&");
        encodedURL.append("profile=");encodedURL.append(profile.trim());encodedURL.append("&");
        encodedURL.append("userGroup=");encodedURL.append(userGroup.trim());encodedURL.append("&");
        encodedURL.append("email=");encodedURL.append(email.trim());encodedURL.append("&");
        encodedURL.append("mobile=");encodedURL.append(mobile.trim());encodedURL.append("&");
        encodedURL.append("b_firstname=");encodedURL.append(b_firstname.trim());encodedURL.append("&");
    	encodedURL.append("b_address=");encodedURL.append(b_address.trim());encodedURL.append("&");
        encodedURL.append("b_city=");encodedURL.append(b_city.trim());encodedURL.append("&");
        encodedURL.append("b_state=");encodedURL.append(b_state.trim());encodedURL.append("&");
    	encodedURL.append("b_zipcode=");encodedURL.append(b_zipcode.trim());encodedURL.append("&");
        encodedURL.append("b_country=");encodedURL.append(b_country.trim());encodedURL.append("&");
    	encodedURL.append("wallet_enabled=");encodedURL.append(wallet_enabled.trim());encodedURL.append("&");
    	encodedURL.append("csrf_token=");encodedURL.append(token.trim());encodedURL.append("&");
 //   	encodedURL.append("userinputcaptcha=");encodedURL.append(userinputcaptcha.trim());
    	}

    	else if(pm.equalsIgnoreCase("creditcard"))
    	{
    	encodedURL.append("csrf_token=");encodedURL.append(token);encodedURL.append("&");
    	encodedURL.append("use_saved_card=");encodedURL.append(useSavedCard.trim());encodedURL.append("&");
    	encodedURL.append("address=");encodedURL.append(address.trim());encodedURL.append("&");
        encodedURL.append("pm=");encodedURL.append(pm.trim());encodedURL.append("&");
        encodedURL.append("clientCode=");encodedURL.append(clientCode.trim());encodedURL.append("&");
        encodedURL.append("cartContext=");encodedURL.append(cartContext.trim());encodedURL.append("&");
        encodedURL.append("profile=");encodedURL.append(profile.trim());encodedURL.append("&");
        encodedURL.append("userGroup=");encodedURL.append(userGroup.trim());encodedURL.append("&");
        encodedURL.append("email=");encodedURL.append(email.trim());encodedURL.append("&");
        encodedURL.append("mobile=");encodedURL.append(mobile.trim());encodedURL.append("&");
    	encodedURL.append("wallet_enabled=");encodedURL.append(wallet_enabled.trim());encodedURL.append("&");
        encodedURL.append("other_cards=");encodedURL.append(other_cards.trim());encodedURL.append("&");
        encodedURL.append("card_number=");encodedURL.append(card_number.trim());encodedURL.append("&");
    	encodedURL.append("bill_name=");encodedURL.append(bill_name.trim());encodedURL.append("&");
        encodedURL.append("card_month=");encodedURL.append(card_month.trim());encodedURL.append("&");
    	encodedURL.append("card_year=");encodedURL.append(card_year.trim());encodedURL.append("&");
    	encodedURL.append("cvv_code=");encodedURL.append(cvv_code.trim());encodedURL.append("&");
    	encodedURL.append("address-sel=");encodedURL.append(address.trim());encodedURL.append("&");
    	encodedURL.append("b_firstname=");encodedURL.append(b_firstname.trim());encodedURL.append("&");
    	encodedURL.append("b_address=");encodedURL.append(b_address.trim());encodedURL.append("&");
    	encodedURL.append("b_city=");encodedURL.append(b_city.trim());encodedURL.append("&");
    	encodedURL.append("b_state=");encodedURL.append(b_state.trim());encodedURL.append("&");
    	encodedURL.append("b_zipcode=");encodedURL.append(b_zipcode.trim());encodedURL.append("&");
    	encodedURL.append("b_country=");encodedURL.append(b_country.trim());encodedURL.append("&");
    	}
    	
    	if(auto_giftcard_used != null && auto_giftcard_used.equalsIgnoreCase("true"))
    	{
    	encodedURL.append("auto_giftcard_used=");encodedURL.append(auto_giftcard_used.trim());encodedURL.append("&");
    	encodedURL.append("auto_giftcard_amount=");encodedURL.append(auto_giftcard_amount.trim());encodedURL.append("&");
    	}
    	
    	if(manual_giftcard_used != null && manual_giftcard_used.equalsIgnoreCase("true"))
    	{
    	System.out.println("Inside manual_giftcard_used");
    	encodedURL.append("giftcard.0.number=");encodedURL.append(cardNumber.trim());encodedURL.append("&");
    	encodedURL.append("giftcard.0.pin=");encodedURL.append(pin.trim());encodedURL.append("&");
    	encodedURL.append("giftcard.0.amount=");encodedURL.append(amount.trim());encodedURL.append("&");
    	encodedURL.append("giftcard.count=");encodedURL.append(count.trim());encodedURL.append("&");
    	//encodedURL.append("pm=");encodedURL.append("giftcard");encodedURL.append("&");
    	}
    	
    	String payload = encodedURL.toString();
    	System.out.println("Payload is : "+payload);
        return payload;
	}
    
    
    private String extractCardInfoDetails(String responseN)
    {
        Document doc1 = Jsoup.parseBodyFragment(responseN);
        Element body1 = doc1.body();
        
        Element amount = body1.select("input[name=vpc_Amount]").first();
        String vpc_Amount = amount.attr("value").toString();
        System.out.println("amount="+vpc_Amount);
        
        Element info = body1.select("input[name=vpc_OrderInfo]").first();
        String vpc_orderInfo = info.attr("value").toString();
        System.out.println("amount="+vpc_orderInfo);

        Element message = body1.select("input[name=vpc_Message]").first();
        String vpc_Message = message.attr("value").toString();
        System.out.println("amount="+vpc_Message);

        Element desc = body1.select("input[name=vpc_Desc]").first();
        String vpc_Desc = desc.attr("value").toString();
        System.out.println("amount="+vpc_Desc);

        Element url = body1.select("input[name=vpc_ReturnURL]").first();
        String vpc_ReturnURL = url.attr("value").toString();
        System.out.println("amount="+vpc_ReturnURL);

        Element hash = body1.select("input[name=vpc_SecureHash]").first();
        String vpc_SecureHash = hash.attr("value").toString();
        System.out.println("amount="+vpc_SecureHash);

        Element cardNum = body1.select("input[name=vpc_CardNum]").first();
        String vpc_CardNum = cardNum.attr("value").toString();
        System.out.println("amount="+vpc_CardNum);

        Element authorized = body1.select("input[name=vpc_AuthorizeId]").first();
        String vpc_AuthorizeId = authorized.attr("value").toString();
        System.out.println("amount="+vpc_AuthorizeId);
        
        Element reference = body1.select("input[name=vpc_MerchTxnRef]").first();
        String vpc_MerchTxnRef = reference.attr("value").toString();
        System.out.println("amount="+vpc_MerchTxnRef);

        Element receipt = body1.select("input[name=vpc_ReceiptNo]").first();
        String vpc_ReceiptNo = receipt.attr("value").toString();
        System.out.println("amount="+vpc_ReceiptNo);

        Element expiry = body1.select("input[name=vpc_CardExp]").first();
        String vpc_CardExp = expiry.attr("value").toString();
        System.out.println("amount="+vpc_CardExp);
        
        Element code = body1.select("input[name=vpc_CardSecurityCode]").first();
        String vpc_CardSecurityCode = code.attr("value").toString();
        System.out.println("amount="+vpc_CardSecurityCode);

        Element card = body1.select("input[name=vpc_card]").first();
        String vpc_card = card.attr("value").toString();
        System.out.println("amount="+vpc_card);

        Element responseCode = body1.select("input[name=vpc_TxnResponseCode]").first();
        String vpc_TxnResponseCode = responseCode.attr("value").toString();
        System.out.println("amount="+vpc_TxnResponseCode);

        String cardInfoPayload = createPayloadCardInfo(vpc_Amount,vpc_orderInfo,vpc_Message,vpc_Desc,vpc_ReturnURL,vpc_SecureHash,vpc_CardNum,vpc_AuthorizeId,
        		vpc_MerchTxnRef,vpc_ReceiptNo,vpc_CardExp,vpc_CardSecurityCode,vpc_card,vpc_TxnResponseCode);
        
        return cardInfoPayload;

    }
    
    
    private String createPayloadCardInfo(String vpc_Amount,String vpc_orderInfo,String vpc_Message,String vpc_Desc,
    		String vpc_ReturnURL,String vpc_SecureHash,String vpc_CardNum,String vpc_AuthorizeId,
    		String vpc_MerchTxnRef,String vpc_ReceiptNo,String vpc_CardExp,String vpc_CardSecurityCode,
    		String vpc_card, String vpc_TxnResponseCode) {
    		StringBuffer encodedURL = new StringBuffer();
    		encodedURL.append("vpc_Amount=");encodedURL.append(vpc_Amount);encodedURL.append("&");
    		encodedURL.append("vpc_orderInfo=");encodedURL.append(vpc_orderInfo.trim());encodedURL.append("&");
    		encodedURL.append("vpc_Message=");encodedURL.append(vpc_Message.trim());encodedURL.append("&");
        encodedURL.append("vpc_Desc=");encodedURL.append(vpc_Desc.trim());encodedURL.append("&");
        encodedURL.append("vpc_ReturnURL=");encodedURL.append(vpc_ReturnURL.trim());encodedURL.append("&");
        encodedURL.append("vpc_SecureHash=");encodedURL.append(vpc_SecureHash.trim());encodedURL.append("&");
        encodedURL.append("vpc_CardNum=");encodedURL.append(vpc_CardNum.trim());encodedURL.append("&");
        encodedURL.append("vpc_AuthorizeId=");encodedURL.append(vpc_AuthorizeId.trim());encodedURL.append("&");
        encodedURL.append("vpc_MerchTxnRef=");encodedURL.append(vpc_MerchTxnRef.trim());encodedURL.append("&");
        encodedURL.append("vpc_ReceiptNo=");encodedURL.append(vpc_ReceiptNo.trim());encodedURL.append("&");
        encodedURL.append("vpc_CardExp=");encodedURL.append(vpc_CardExp.trim());encodedURL.append("&");
        encodedURL.append("vpc_CardSecurityCode=");encodedURL.append(vpc_CardSecurityCode.trim());encodedURL.append("&");
        encodedURL.append("vpc_card=");encodedURL.append(vpc_card.trim());encodedURL.append("&");
    		encodedURL.append("vpc_TxnResponseCode=");encodedURL.append(vpc_TxnResponseCode.trim());

    		String payload = encodedURL.toString();
        System.out.println(payload);
        return payload;
	}	
    
	public static void send( String slackchannel, String message){
		if(isSlackEnabled && session.isConnected()){
			SlackChannel channel = session.findChannelByName(slackchannel);
				session.sendMessage(channel, message);
		}
	}
    
   
	
    @AfterClass(alwaysRun=true)
    public static void afterSuite() {
    	Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        send(slackChannel,"Ending time : " + ft.format(dNow));
 	   send(slackChannel, "*............ PPS Service Regression ended ............*");

       // SlackMessenger.disconnect();
		if(isSlackEnabled ){
			try {
				session.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
