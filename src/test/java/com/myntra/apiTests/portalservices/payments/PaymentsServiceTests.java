package com.myntra.apiTests.portalservices.payments;

import argo.saj.InvalidSyntaxException;
import com.jayway.jsonpath.JsonPath;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PaymentsServiceTests extends BaseTest{
	
	private static final SlackSession session = SlackSessionFactory.
			createWebSocketSlackSession("xoxb-143439462852-E4gWQ9P0VNm6Tm10yn5NAYXY");
	private static boolean isSlackEnabled = true;

	String skuId = "3866"; // style id = 1530 (skuid = 3830,3834)
	String skuId_online = "3834";
	String styleId="1541";
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
	String instrumentId=null;
	String encodedCsrfToken = null;
	
	PaymentsServiceHelper paymentsServiceHelper = new PaymentsServiceHelper();
	GiftCardPpsNewServiceHelper giftcardHelper=new GiftCardPpsNewServiceHelper();
	static GiftCardPpsNewServiceHelper GiftCardNewhelper = new GiftCardPpsNewServiceHelper();
    NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(PPSServiceHelper.class);
	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
	String env="stage";
	String isDockinsEnabled="true"; 
	String dockEnvName="payments";

	
	 @BeforeClass(alwaysRun=true) 
	 public void beforeSuite() {
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
			
		   send(slackChannel, "*............ Payments Service Regression started ............*");
		   send(slackChannel,"Starting time : " + ft.format(dNow));
	    }
	
    
    @Test(groups= {"Regression"}, dataProvider = "formParametersOnline",dataProviderClass=PaymentsServiceDP.class)
    public void placeOnlineOrderCombinations (String TestCaseDesc, String email,String password, 
    		String pm, String isAutoGCEnable,String isAutoGCOrder,String isManualGCEnable, String isManualGCOrder, 
    		String isMyntOrder, String isLoyalty, String isGiftWrapEnable, String isTryNBuyEnable,String isCancel, String isReturn, String isExchange) throws UnsupportedEncodingException, InvalidSyntaxException, NumberFormatException, ParseException, JAXBException, InterruptedException, SCMExceptions, JSONException {
	System.out.println("Env is :"+env+"dockEnvName is : "+dockEnvName+"isDockinsEnabled is : "+isDockinsEnabled);
	String clientCode = "responsive";
    	String cartContext = "default";
    	String userGroup = "normal";
    	String wallet_enabled = "true";
    	String other_cards = "false";										
    	String card_number = "5123456789012346";
    	String bill_name = "Payu testcard";
    	String card_month = "05";
    	String card_year = "20";
    	String cvv_code = "123";
    	String useSavedCard = "false";
    	
    	String slackMessage = ">*`"+TestCaseDesc+"`*";
    	
    	send(slackChannel, slackMessage);

    	String uidx=giftcardHelper.getUidxFromEmail(email);
    	String qty="1";
    	//Adding the item to cart
    	HashMap<String,String>cartHeader=addItemToCartPage(email,password,qty);;
    	
    	// Navigate to Payment Page
    		String response = paymentPage(email, password,cartHeader,useSavedCard,pm,clientCode,cartContext,profile, userGroup,wallet_enabled, other_cards,  card_number,  bill_name,
       		 card_month, card_year, cvv_code,uidx);
		Thread.sleep(10000);

		System.out.println("The response is : "+ response);
		// extract orderid from html response
		String result1[]=paymentsServiceHelper.getPPSIdAndOrderIdFromResponse(response);
        String ppsId=result1[0];
        String orderId=result1[1];
       
        send(slackChannel, "Order ID : " +orderId);
        send(slackChannel, "PPS ID : " +ppsId);
        Reporter.log("Order ID : " +orderId);
        Reporter.log("PPS ID : " +ppsId);

        // Verify payment plan
		Thread.sleep(5000);
        send(slackChannel, "Order State : "+"PPFSM Order Taking done");
    }
    

    		@Test(groups= {"Regression"}, dataProvider = "getSavedCards",dataProviderClass=PaymentsServiceDP.class)
    		public void getSavedCards(String TestCaseDesc,String email) throws UnsupportedEncodingException, JSONException {
    			String uidx=getUidx(email);
    			log.info("Uidx is :"+uidx);
			RequestGenerator getSavedCards = paymentsServiceHelper.getSavedCards(uidx);
			Assert.assertEquals(getSavedCards.response.getStatus(), 200);
			String jsonRes = getSavedCards.respvalidate.returnresponseasstring();
			
			log.info("Get card details ---> " + jsonRes);
			String csrfToken=JsonPath.read(jsonRes, "$.csrf_token").toString();
			encodedCsrfToken = forJSON(csrfToken);
			String cardsArray=JsonPath.read(jsonRes, "$.cards").toString();
			JSONArray jsonArray=new JSONArray(cardsArray);
			org.json.JSONObject jsonObject=null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject=jsonArray.getJSONObject(i);
				instrumentId=jsonObject.getString("instrumentId");
				System.out.println("The response objects are as below: "+jsonObject.toString() + "instrumentId is : "+instrumentId);
			}
			String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			log.info("Messgage- >>>>" + msg1);
			//TODO need to put asserts
    	    }
    
  
    		
    		@Test(groups= {"Regression"},dependsOnMethods= {"getSavedCards"}, dataProvider = "getSavedCards",dataProviderClass=PaymentsServiceDP.class)
    		public void getSavedCard(String TestCaseDesc,String email)
    	    {
    			String uidx=getUidx(email);
    			log.info("Uidx is :"+uidx);
			RequestGenerator getSavedCard = paymentsServiceHelper.getSavedCard(instrumentId,uidx);
			Assert.assertEquals(getSavedCard.response.getStatus(), 200);
			String jsonRes = getSavedCard.respvalidate.returnresponseasstring();
			log.info("Get card details ---> " + jsonRes);
			String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			log.info("Messgage- >>>>" + msg1);
			//TODO need to put asserts
    	    }
    		
    		
    		@Test(groups= {"Regression"},dependsOnMethods= {"getSavedCards"}, dataProvider = "addSavedCards",dataProviderClass=PaymentsServiceDP.class)
    		public void addSavedCard(String TestCaseDesc,String email,String cardNumber,String expMonth,String expYear,String cardName)
    	    {
    			String uidx=getUidx(email);
    			log.info("Uidx is :"+uidx);
			RequestGenerator addSavedCard = paymentsServiceHelper.addSavedCard(uidx,cardNumber,expMonth,expYear,cardName,encodedCsrfToken);
			Assert.assertEquals(addSavedCard.response.getStatus(), 200);
			String jsonRes = addSavedCard.respvalidate.returnresponseasstring().replaceAll("\n","").replaceAll("\r","");
			log.info("Add card details ---> " + jsonRes);
			String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			log.info("Messgage- >>>>" + msg1);
			//TODO need to put asserts
    	    }
    		
    		@Test(groups= {"Regression"},dependsOnMethods= {"getSavedCards"}, dataProvider = "updateSavedCards",dataProviderClass=PaymentsServiceDP.class)
    		public void updateSavedCard(String TestCaseDesc,String email,String expMonth,String expYear)
    	    {
    			String uidx=getUidx(email);
    			log.info("Uidx is :"+uidx);
			RequestGenerator updateSavedCard = paymentsServiceHelper.updateSavedCard(uidx,expMonth,expYear,encodedCsrfToken,instrumentId);
			Assert.assertEquals(updateSavedCard.response.getStatus(), 200);
			String jsonRes = updateSavedCard.respvalidate.returnresponseasstring().replaceAll("\n","").replaceAll("\r","");
			log.info("Update card details ---> " + jsonRes);
			String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			log.info("Messgage- >>>>" + msg1);
			//TODO need to put asserts
    	    }
    		
    		
    		@Test(groups= {"Regression"},dependsOnMethods= {"getSavedCards"},dataProvider = "deleteSavedCards",dataProviderClass=PaymentsServiceDP.class)
    		public void deleteSavedCard(String TestCaseDesc,String email)
    	    {
    			String uidx=getUidx(email);
    			log.info("Uidx is :"+uidx);
			RequestGenerator deleteSavedCard = paymentsServiceHelper.deleteSavedCard(uidx,instrumentId);
			Assert.assertEquals(deleteSavedCard.response.getStatus(), 200);
			String jsonRes = deleteSavedCard.respvalidate.returnresponseasstring();
			log.info("Delete card details ---> " + jsonRes);
			String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			log.info("Messgage- >>>>" + msg1);
			//TODO need to put asserts
    	    }
    		
    		
    		
    		
    		public String getUidx(String email)
    		{
    			RequestGenerator req1=GiftCardNewhelper.getEmailToUidx(email);
    			Assert.assertEquals(req1.response.getStatus(), 200);
    			String jsonRes1=req1.respvalidate.returnresponseasstring();
    			String uidx=JsonPath.read(jsonRes1, "$.entry.uidx").toString();
    			return uidx;
    		}
    		
    		
    		public String forJSON(String aText){
    		    final StringBuilder result = new StringBuilder();
    		    StringCharacterIterator iterator = new StringCharacterIterator(aText);
    		    char character = iterator.current();
    		    while (character != StringCharacterIterator.DONE){
    		      if( character == '\"' ){
    		        result.append("\\\"");
    		      }
    		      else if(character == '\\'){
    		        result.append("\\\\");
    		      }
    		      else if(character == '/'){
    		        result.append("\\/");
    		      }
    		      else if(character == '\b'){
    		        result.append("\\b");
    		      }
    		      else if(character == '\f'){
    		        result.append("\\f");
    		      }
    		      else if(character == '\n'){
    		        result.append("\\n");
    		      }
    		      else if(character == '\r'){
    		        result.append("\\r");
    		      }
    		      else if(character == '\t'){
    		        result.append("\\t");
    		      }
    		      else {
    		        //the char is not a special one
    		        //add it to the result as is
    		        result.append(character);
    		      }
    		      character = iterator.next();
    		    }
    		    return result.toString();    
    		  }
    		
    		
    		
    		
    		
    private String paymentPage(String email, String password, HashMap<String, String> cartHeader,
    		String useSavedCard, String pm, String clientCode, String cartContext, String profile,
    		String userGroup, String wallet_enabled, String other_cards, String card_number, String bill_name, String card_month, 
    		String card_year, String cvv_code,String uidx) throws ParseException, JSONException {
    	HashMap<String,String>addressDetails = navigateToPaymentPage(email, password,cartHeader);
    	
        // Get Payments Page and extract token
      	String encodedcsrf=paymentsServiceHelper.getEncodedTokenFromPaymentPage(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(),PaymentPage_Host,PaymentPage_Referer);

      	String payload = createPayloadOnline ( email,useSavedCard, addressDetails.get("addressId").toString(),  pm,  clientCode,
        		cartContext, profile, userGroup,  addressDetails.get("mobile").toString(),
        		 wallet_enabled, other_cards,  card_number,  bill_name,
        		 card_month, card_year, cvv_code, 
        		 addressDetails.get("firstname").toString(),  addressDetails.get("defaultAddress").toString(),  addressDetails.get("city").toString(),  addressDetails.get("state").toString(),
        		 addressDetails.get("zipcode").toString(),  addressDetails.get("country").toString(), encodedcsrf,uidx);
      	
      	System.out.println("payload in paymentPage is : "+payload);  	
      	String responseN = paymentsServiceHelper.getPaynowResponse(cartHeader.get("xid").toString(),PPS_Host,encodedcsrf,payload);
    	
		String cardInfoPayload=extractCardInfoDetails(responseN);
		
        // Hit test pg response API 
		responseN=paymentsServiceHelper.getPayuPGResponse(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(),PPS_Host,Pps_Referer,cardInfoPayload);
		System.out.println("The PaymentPage response : "+responseN);
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
    		String b_zipcode, String b_country,String token,String uidx)
    {
    	StringBuffer encodedURL = new StringBuffer();
    	
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
    	encodedURL.append("user=");encodedURL.append(uidx.trim());encodedURL.append("&");
    	
    	String payload = encodedURL.toString();
    	System.out.println("Payload is : "+payload);
        return payload;
	}
    
    private String extractCardInfoDetails(String responseN)
    {
        Document doc1 = Jsoup.parseBodyFragment(responseN);
        Element body1 = doc1.body();
        
        Element address1Element = body1.select("input[name=address1]").first();
        String address1 = address1Element.attr("value").toString();
        System.out.println("address1="+address1);
        
        Element amountElement = body1.select("input[name=amount]").first();
        String amount = amountElement.attr("value").toString();
        System.out.println("amount="+amount);

        Element bankcodeElement = body1.select("input[name=bankcode]").first();
        String bankcode = bankcodeElement.attr("value").toString();
        System.out.println("bankcode="+bankcode);

        Element ccexpmonElement = body1.select("input[name=ccexpmon]").first();
        String ccexpmon = ccexpmonElement.attr("value").toString();
        System.out.println("ccexpmon="+ccexpmon);

        Element ccexpyrElement = body1.select("input[name=ccexpyr]").first();
        String ccexpyr = ccexpyrElement.attr("value").toString();
        System.out.println("ccexpyr="+ccexpyr);

        Element ccnameElement = body1.select("input[name=ccname]").first();
        String ccname = ccnameElement.attr("value").toString();
        System.out.println("ccname="+ccname);

        Element ccnumElement = body1.select("input[name=ccnum]").first();
        String ccnum = ccnumElement.attr("value").toString();
        System.out.println("ccnum="+ccnum);

        Element ccvvElement = body1.select("input[name=ccvv]").first();
        String ccvv = ccvvElement.attr("value").toString();
        System.out.println("ccvv="+ccvv);
        
        Element cityElement = body1.select("input[name=city]").first();
        String city = cityElement.attr("value").toString();
        System.out.println("city="+city);

        Element countryElement = body1.select("input[name=country]").first();
        String country = countryElement.attr("value").toString();
        System.out.println("country="+country);

        Element curlElement = body1.select("input[name=curl]").first();
        String curl = curlElement.attr("value").toString();
        System.out.println("curl="+curl);
        
        Element emailElement = body1.select("input[name=email]").first();
        String email = emailElement.attr("value").toString();
        System.out.println("email="+email);

        Element firstnameElement = body1.select("input[name=firstname]").first();
        String firstname = firstnameElement.attr("value").toString();
        System.out.println("firstname="+firstname);
        
        Element furlElement = body1.select("input[name=furl]").first();
        String furl = furlElement.attr("value").toString();
        System.out.println("furl="+furl);

        Element hashElement = body1.select("input[name=hash]").first();
        String hash = hashElement.attr("value").toString();
        System.out.println("hash="+hash);

        Element keyElement = body1.select("input[name=key]").first();
        String key = keyElement.attr("value").toString();
        System.out.println("key="+key);
        
        Element pgElement = body1.select("input[name=pg]").first();
        String pg = pgElement.attr("value").toString();
        System.out.println("pg="+pg);
        
        Element phoneElement = body1.select("input[name=phone]").first();
        String phone = phoneElement.attr("value").toString();
        System.out.println("phone="+phone);

        Element productinfoElement = body1.select("input[name=productinfo]").first();
        String productinfo = productinfoElement.attr("value").toString();
        System.out.println("productinfo="+productinfo);
        
        Element stateElement = body1.select("input[name=state]").first();
        String state = stateElement.attr("value").toString();
        System.out.println("state="+state);

        Element surlElement = body1.select("input[name=surl]").first();
        String surl = surlElement.attr("value").toString();
        System.out.println("surl="+surl);

        Element txnidElement = body1.select("input[name=txnid]").first();
        String txnid = txnidElement.attr("value").toString();
        System.out.println("txnid="+txnid);
        
        Element zipcodeElement = body1.select("input[name=zipcode]").first();
        String zipcode = zipcodeElement.attr("value").toString();
        System.out.println("zipcode="+zipcode);
        
        String cardInfoPayload = createPayloadCardInfo(address1,amount,bankcode,ccexpmon,ccexpyr,ccname,ccnum,ccvv,
        		city,country,curl,email,firstname,furl,hash,key,pg,phone,productinfo,state,surl,txnid,zipcode);
        
        return cardInfoPayload;

    }
    
    
    private String createPayloadCardInfo(String address1,String amount,String bankcode,String ccexpmon,
    		String ccexpyr,String ccname,String ccnum,String ccvv,
    		String city,String country,String curl,String email,
    		String firstname, String furl,String hash,String key,String pg,String phone,String productinfo,String state,String surl,String txnid,String zipcode) {
    		StringBuffer encodedURL = new StringBuffer();
    		encodedURL.append("address1=");encodedURL.append(address1);encodedURL.append("&");
    		encodedURL.append("amount=");encodedURL.append(amount.trim());encodedURL.append("&");
    		encodedURL.append("bankcode=");encodedURL.append(bankcode.trim());encodedURL.append("&");
        encodedURL.append("ccexpmon=");encodedURL.append(ccexpmon.trim());encodedURL.append("&");
        encodedURL.append("ccexpyr=");encodedURL.append(ccexpyr.trim());encodedURL.append("&");
        encodedURL.append("ccname=");encodedURL.append(ccname.trim());encodedURL.append("&");
        encodedURL.append("ccnum=");encodedURL.append(ccnum.trim());encodedURL.append("&");
        encodedURL.append("ccvv=");encodedURL.append(ccvv.trim());encodedURL.append("&");
        encodedURL.append("city=");encodedURL.append(city.trim());encodedURL.append("&");
        encodedURL.append("country=");encodedURL.append(country.trim());encodedURL.append("&");
        encodedURL.append("curl=");encodedURL.append(curl.trim());encodedURL.append("&");
        encodedURL.append("email=");encodedURL.append(email.trim());encodedURL.append("&");
        encodedURL.append("firstname=");encodedURL.append(firstname.trim());encodedURL.append("&");
    		encodedURL.append("furl=");encodedURL.append(furl.trim());encodedURL.append("&");
    		encodedURL.append("hash=");encodedURL.append(hash.trim());encodedURL.append("&");
        encodedURL.append("key=");encodedURL.append(key.trim());encodedURL.append("&");
        encodedURL.append("pg=");encodedURL.append(pg.trim());encodedURL.append("&");
        encodedURL.append("phone=");encodedURL.append(phone.trim());encodedURL.append("&");
        encodedURL.append("productinfo=");encodedURL.append(productinfo.trim());encodedURL.append("&");
        encodedURL.append("state=");encodedURL.append(state.trim());encodedURL.append("&");
        encodedURL.append("surl=");encodedURL.append(surl.trim());encodedURL.append("&");
        encodedURL.append("txnid=");encodedURL.append(txnid.trim());encodedURL.append("&");
        encodedURL.append("zipcode=");encodedURL.append(zipcode.trim());
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
