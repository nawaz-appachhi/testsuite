package com.myntra.apiTests.portalservices.all;

import argo.saj.InvalidSyntaxException;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.PPSServiceDP;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.giftCardPpsHelper.GiftCardPpsNewServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
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
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.SkipException;
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


public class PPS_Service extends BaseTest{
	
	private static final SlackSession session = SlackSessionFactory.
			createWebSocketSlackSession("xoxb-143439462852-E4gWQ9P0VNm6Tm10yn5NAYXY");
//	private static boolean isSlackEnabled = System.getenv("slackenabled") == null ? false : true;
	private static boolean isSlackEnabled = true;

	String skuId = "3866"; // style id = 1530 (skuid = 3830,3834)
	String skuId_online = "3834";
	String styleId="1541";
	String exchangeSkuId="3833";
	//String styleId_AutoGC="256978";
	//String skuId_AutoGC="862081";
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
	//static String slackChannel ="buyservice_automation";
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
	String env=System.getenv("environment");

//	String isDockinsEnabled="true"; 
//	String dockEnvName="reftest1";
//	String tenantId="jabong";
	String tenantId=System.getenv("tenantId");
	String isDockinsEnabled=System.getenv("isDockinsEnabled"); 
	String dockEnvName=System.getenv("dockEnvName");
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper(tenantId);


	
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
			else if((env.equalsIgnoreCase("sfqa")) && (isDockinsEnabled.equalsIgnoreCase("false")))
			{
				PPS_Host = "pps.sfqa.myntra.com";
				CartPage_Host="sfqa.myntra.com";
				CartPage_Referer="https://sfqa.myntra.com/";
				AddressPage_Host="sfqa.myntra.com";
				AddressPage_Referer="https://sfqa.myntra.com/checkout/cart";
				PaymentPage_Host="sfqa.myntra.com";
				PaymentPage_Referer="https://sfqa.myntra.com/checkout/address";
				Pps_Referer="http://pps.sfqa.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = "sfqa.myntra.com";
			}
			else if((env.equalsIgnoreCase("scmqa")) && (isDockinsEnabled.equalsIgnoreCase("false")))
			{
				PPS_Host = "pps.scmqa.myntra.com";
				CartPage_Host="scmqa.myntra.com";
				CartPage_Referer="https://scmqa.myntra.com/";
				AddressPage_Host="scmqa.myntra.com";
				AddressPage_Referer="https://scmqa.myntra.com/checkout/cart";
				PaymentPage_Host="scmqa.myntra.com";
				PaymentPage_Referer="https://scmqa.myntra.com/checkout/address";
				Pps_Referer="http://pps.scmqa.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = "scmqa.myntra.com";
			}
			else if((env.equalsIgnoreCase("mjint")) && (isDockinsEnabled.equalsIgnoreCase("false")))
			{
				PPS_Host = "pps.mjint.myntra.com";
				CartPage_Host="mjint.myntra.com";
				CartPage_Referer="https://mjint.myntra.com/";
				AddressPage_Host="mjint.myntra.com";
				AddressPage_Referer="https://mjint.myntra.com/checkout/cart";
				PaymentPage_Host="mjint.myntra.com";
				PaymentPage_Referer="https://mjint.myntra.com/checkout/address";
				Pps_Referer="http://pps.mjint.myntra.com/myntra-payment-plan-service/v1/paynowform";
				profile = "mjint.myntra.com";
			}
			else
			{
				Assert.fail();
			}
			
		   send(slackChannel, "*............ PPS Service Regression started ............*");
		   send(slackChannel,"Starting time : " + ft.format(dNow));
	    }
	

    
    @Test(groups= {"Sanity","Regression"}, dataProvider = "formParametersOnline",dataProviderClass=PPSServiceDP.class)
    public void placeOnlineOrderCombinations (String TestCaseDesc, String email,String password, 
    		String pm, String isAutoGCEnable,String isAutoGCOrder,String isManualGCEnable, String isManualGCOrder, 
    		String isMyntOrder, String isLoyalty, String isGiftWrapEnable, String isTryNBuyEnable,String isCancel, String isReturn, String isExchange) throws UnsupportedEncodingException, InvalidSyntaxException, NumberFormatException, ParseException, JAXBException, InterruptedException, SCMExceptions, JSONException {
    	//Getting the values from jenkins
    	//String env=System.getenv("environment");
	//String isDockinsEnabled=System.getenv("isDockinsEnabled"); 
	//String dockEnvName=System.getenv("dockEnvName");
	System.out.println("Env is :"+env+"dockEnvName is : "+dockEnvName+"isDockinsEnabled is : "+isDockinsEnabled);
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
    	//String gcNumber = "6991201055498416";
    	//String gcPin = "886844";
    	String manualGCAmount = "";
    	String manualGCCount = "1";
    	String autoGCAmount = "";
    	
    	String slackMessage = ">*`"+TestCaseDesc+"`*";
    	
    	send(slackChannel, slackMessage);

    	// isManualGCOrder signifies complete GC order. isManualGCOrder = false, means online order (manual GC+ online), isManualGCOrder = true, means complete GC order, pm = giftcard
    	// isAutoGCOrder signifies complete GC order. isAutoGCOrder = false, means online order (auto GC + online), isAutoGCOrder = true, means complete GC order, pm = cashback
    	String uidx=giftcardHelper.getUidxFromEmail(email);
    	String qty="1";
		if (isAutoGCEnable.equalsIgnoreCase("true"))
		{
			qty="2";
		}
    	boolean isAutoGC = false;
    	boolean isManualGC = false;
    	boolean isGiftWrap = false;
    	boolean isTryNBuy = false;
    	
    	//Adding the item to cart
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    	HashMap<String,String>cartHeader=addItemToCartPage(email,password,qty);;
    if((isAutoGCEnable.equalsIgnoreCase("true")) || (isManualGCEnable.equalsIgnoreCase("true")))
    {
    	
    		RequestGenerator checkBalance = giftcardHelper.checkGiftcardBalance(gcType, gcNumber, gcPin, email);
    		String jsonRes = checkBalance.respvalidate.returnresponseasstring();
    		String msg = JsonPath.read(jsonRes, "$..statusMessage").toString()
    			.replace("[", "").replace("]", "").replace("\"", "").trim();
    		if(msg.equalsIgnoreCase("Check Balance Successful"))
    			System.out.println("Giftcard Service is up and running");
    		else
    			throw new SkipException("Skipping "+TestCaseDesc+" as the giftcard service is not up. The status message from service is: "+msg);
    		
    	
    	if (isAutoGCEnable.equalsIgnoreCase("true"))
    	{
    		isAutoGC = true;
    		RequestGenerator req = giftcardHelper.getGiftCardAddedToUserAccount(uidx);
    		String jsonRes1=req.respvalidate.returnresponseasstring();	
    		autoGCAmount=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
    		if(Float.valueOf(autoGCAmount) == 0)
    			throw new SkipException("Skipping "+TestCaseDesc+" as the auto giftcard amount "+autoGCAmount+" is 0 for uidx: "+uidx);
    		System.out.println("Auto giftcard amount redeemed will be : "+autoGCAmount);
    	}
    	
    	if (isManualGCEnable.equalsIgnoreCase("true"))
    	{
    		isManualGC = true;
    		manualGCAmount=giftcardHelper.getGiftcardBalance(gcType, gcNumber, gcPin, email);
    		if(Float.valueOf(manualGCAmount) == 0)
    			throw new SkipException("Skipping "+TestCaseDesc+" as the manual giftcard amount "+manualGCAmount+" is 0");
    		System.out.println("Manual giftcard amount redeemed will be : "+manualGCAmount);
    	}
    	
    	if (isGiftWrapEnable.equalsIgnoreCase("true"))
    	{
    		isGiftWrap = true;
    	}
    		
    	if (isTryNBuyEnable.equalsIgnoreCase("true"))
    	{
    		isTryNBuy = true;
    		qty="2";
    	}
    	if ((isManualGCEnable.equalsIgnoreCase("true") && (isManualGCOrder.equalsIgnoreCase("false"))))
    	{
	    	qty="1";
	    	//pm ="creditcard";
    	}
    	
    	if (isManualGCEnable.equalsIgnoreCase("true") && (isManualGCOrder.equalsIgnoreCase("true")))
    	{
   		qty="1";
   		skuId_online=skuId_ManualGC;
	    	skuId=skuId_ManualGC;
	    	styleId=styleId_ManualGC;
	    	exchangeSkuId=exchange_ManualskuId;
	    	
   		cartHeader=addItemToCartPage(email,password,qty);
   		Float cartBalance=Float.valueOf(checkoutTestHelper.fetchBalanceFromCart(cartHeader));
   		if(cartBalance > Float.valueOf(manualGCAmount))
    			throw new SkipException("Skipping "+TestCaseDesc+ " as cart balance : "+cartBalance +" is greater than the manual giftcard amount: "+manualGCAmount);
	    	pm ="giftcard";
    	}
    	if (isAutoGCEnable.equalsIgnoreCase("true") && (isAutoGCOrder.equalsIgnoreCase("true")))
    	{
   		qty="1";
   		skuId_online=skuId_AutoGC;
   		skuId=skuId_AutoGC;
   		styleId=styleId_AutoGC;
   		//autoGCAmount="210";
   		exchangeSkuId=exchange_AutoskuId;
   		cartHeader=addItemToCartPage(email,password,qty);
		String cartBalance=checkoutTestHelper.fetchBalanceFromCart(cartHeader);
		if(Float.valueOf(cartBalance) > Float.valueOf(autoGCAmount))
			throw new SkipException("Skipping "+TestCaseDesc+ " as cart balance : "+cartBalance +" is greater than the auto giftcard amount: "+autoGCAmount + "for uidx: "+uidx);
		pm ="cashback";
			autoGCAmount = cartBalance;
    	}
    }
    	
     //  manualGCAmount=GiftCardNewhelper.getGiftcardBalance("GIFTBIG", gcNumber, gcPin, email);
        
        HashMap<String,String>giftCardHeader = new HashMap<String,String>();
        giftCardHeader.put("gcNumber", gcNumber);
        giftCardHeader.put("gcPin", gcPin);
        giftCardHeader.put("manualGCAmount", manualGCAmount);
        
        
        String myntCode = "";
    	if (isMyntOrder.equalsIgnoreCase("true"))
    	{
    		if(isManualGCEnable.equalsIgnoreCase("true")){
			myntCode = "MANUA10";
    		}
    		else {
			myntCode="AUTOM10P";
		}

    	}
    	
    	String totalLoyaltyPointsAvailable = "0";
    	boolean isLoyaltyApplicable = false;
    	if (isLoyalty.equalsIgnoreCase("true"))
    	{
    		isLoyaltyApplicable = true;
			System.out.println("uidx="+uidx);
		
		MyntraService serviceFetchLoyalty = Myntra.getService(ServiceType.PORTAL_LOYALITY, APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
		PayloadType.JSON,new String[] { uidx },  PayloadType.JSON);
		RequestGenerator reqFetchLoyalty = new RequestGenerator (serviceFetchLoyalty);
		totalLoyaltyPointsAvailable = reqFetchLoyalty.respvalidate.NodeValue("userAccountEntry.activePointsBalance", true).replaceAll("\"", "").trim();
		
		if (Double.parseDouble(totalLoyaltyPointsAvailable)==0)
		{
			 // credit LP
			MyntraService serviceCreditLoyalty = Myntra.getService(ServiceType.PORTAL_LOYALITY, APINAME.CREDITLOYALITYPOINTS, init.Configurations,
			new String[] { uidx,"200","0","0","0","automation","ORDER","123","GOOD_WILL"}, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator reqCreditLoyalty = new RequestGenerator (serviceCreditLoyalty);
	
		}

    	}

    	
    	//...........Apply components.........
    	
   // 	 userName, password,skuAndQuantities,couponId, cashBack,loyalityPoint,totalLoyaltyPointsAvailable,isGiftWrap,
   //	 isTryNBuy, autogiftcard, manualgiftcard, myntCode,header, giftCardHeader
    	 checkoutTestHelper.ppsFormGetXIDForMultipleItemToCartAuto(email, password,new String[]{"3834:1"}, "",false,isLoyaltyApplicable,totalLoyaltyPointsAvailable,isGiftWrap,isTryNBuy,isAutoGC,isManualGC,myntCode,cartHeader,giftCardHeader);

		String cartBalance = checkoutTestHelper.fetchBalanceFromCart(cartHeader);
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
        
        String itemPayloadForOrderCancel=PPSServiceHelper.getItemPayload(paymentPlanItems);
     //   String paymentMethodPayload= PPSServiceHelper.getPaymentMethodPayload();
        
        if (isCancel.equalsIgnoreCase("true"))
        {
		//Cancelling the same order
		System.out.println("Cancelling the order: "+orderId);
		RequestGenerator cancelRequest=ppsServiceHelper.cancelOrReturnOrder(orderId, ppsId, "CANCELLATION",itemPayloadForOrderCancel);
		System.out.println("Cancellation response is :" +cancelRequest.respvalidate.returnresponseasstring());
		System.out.println("Order cancelled");
		String statusMessage = cancelRequest.respvalidate
				.NodeValue("success", true).replaceAll("\"", "")
				.trim();
		System.out.println(statusMessage);
		Thread.sleep(5000);
		if (statusMessage.equalsIgnoreCase("true"))
		{
	        send(slackChannel, "Order Cancellation State : "+"PPSFM Refund Done");
	        send(slackChannel, "Test Case Passed");
		}
		else
		{
	        send(slackChannel, "Order Cancellation State : "+"PPSFM Refund Failed");
	        send(slackChannel, "Test Case Failed");
		}
		AssertJUnit.assertEquals("Cancellation is not suceess", "true", statusMessage);
        }

        
		
        if((pm.equalsIgnoreCase("creditcard")||pm.equalsIgnoreCase("giftcard")||pm.equalsIgnoreCase("cashback"))&&isReturn.equalsIgnoreCase("true"))
        {
        	System.out.println("credit card/ gift card return method");
		// Returning the same order
		System.out.println("Returning the order: "+orderId);
		RequestGenerator returnRequest=ppsServiceHelper.cancelOrReturnOrder(orderId, ppsId, "RETURN",itemPayloadForOrderCancel);
		System.out.println(returnRequest.respvalidate.returnresponseasstring());
		System.out.println("Order returned");
		String statusMessage = returnRequest.respvalidate
				.NodeValue("success", true).replaceAll("\"", "")
				.trim();
		System.out.println(statusMessage);
		if (statusMessage.equalsIgnoreCase("true"))
		{
	        send(slackChannel, "Order Return State : "+"PPSFM Refund Done");
	        send(slackChannel, "Test Case Passed");
		}
		else
		{
	        send(slackChannel, "Order Return State : "+"PPSFM Refund Failed");
	        send(slackChannel, "Test Case Failed");
		}
		AssertJUnit.assertEquals("Return is not suceess", "true", statusMessage);
        }
        
        if((pm.equalsIgnoreCase("cod")&&isReturn.equalsIgnoreCase("true")))
        {
        	System.out.println("cod return method");
		// Returning the same order
		System.out.println("Returning the order: "+orderId);
		RequestGenerator returnRequest=ppsServiceHelper.returnCODOrder(orderId, ppsId, "RETURN",itemPayloadForOrderCancel);
		System.out.println(returnRequest.respvalidate.returnresponseasstring());
		System.out.println("Order returned");
		
		String statusMessage = returnRequest.respvalidate
				.NodeValue("success", true).replaceAll("\"", "")
				.trim();
		System.out.println(statusMessage);
		if (statusMessage.equalsIgnoreCase("true"))
		{
	        send(slackChannel, "Order Return State : "+"PPSFM Refund Done");
	        send(slackChannel, "Test Case Passed");
		}
		else
		{
	        send(slackChannel, "Order Return State : "+"PPSFM Refund Failed");
	        send(slackChannel, "Test Case Failed");
		}
		AssertJUnit.assertEquals("Return is not suceess", "true", statusMessage);
        

        }
       
       try {
       if (isExchange.equalsIgnoreCase("true"))
        {
		//Exchanging the same order
		System.out.println("Exchanging the order: "+orderId);
		String orderIdFk=ppsServiceHelper.getOrderIdFk(orderId);
		String orderLineId=ppsServiceHelper.getOrderLineId(orderId);
		ppsServiceHelper.markOrderDLQuery(orderIdFk);
		System.out.println("uidx="+uidx);
		
		RequestGenerator exchangeRequest=ppsServiceHelper.exchangeOrder(orderIdFk,orderLineId, ppsId, skuId,styleId,exchangeSkuId,uidx);
		System.out.println(exchangeRequest.respvalidate.returnresponseasstring());
		System.out.println("Order exchanged");
		Thread.sleep(500);
		String statusMessage = exchangeRequest.respvalidate
				.NodeValue("success", true).replaceAll("\"", "")
				.trim();
		System.out.println(statusMessage);
		if (statusMessage.equalsIgnoreCase("true"))
		{
	        send(slackChannel, "Order Exchange State : "+"PPSFM Exchange Done");
	        send(slackChannel, "Test Case Passed");
		}
		else
		{
	        send(slackChannel, "Order Exchange State : "+"PPSFM Exchange Failed");
	        send(slackChannel, "Test Case Failed");
		}
		AssertJUnit.assertEquals("Exchange is not suceess", "true", statusMessage);
        }
       }
       finally {
    	// Return the parent order in case
   	    System.out.println("credit card/ gift card return method");
   		System.out.println("Returning the order: "+orderId);
   		RequestGenerator returnRequest=ppsServiceHelper.cancelOrReturnOrder(orderId, ppsId, "RETURN",itemPayloadForOrderCancel);
   		System.out.println(returnRequest.respvalidate.returnresponseasstring());
   		System.out.println("Order returned"); 
       }

		
		


    }

    @Test(groups= {"Sanity"},dataProvider = "gcPurchase",dataProviderClass=PPSServiceDP.class,invocationCount = 1)
    public void giftcardPurchase(String TestCaseDesc, String email,String password,String pm) throws UnsupportedEncodingException, InvalidSyntaxException, NumberFormatException, ParseException, JAXBException, InterruptedException, SCMExceptions, JSONException {
	String clientCode = "responsive";
    	String cartContext = "egiftcard";
    	String userGroup = "normal";
    	String wallet_enabled = "true";
    	String other_cards = "false";										
    	String card_number = "5415679107547290";
    	String bill_name = "Test card";
    	String card_month = "07";
    	String card_year = "22";
    	String cvv_code = "123";
    	String useSavedCard = "false";
    	String cardProgramGroupName="Myntra B2C eGift Cards";

    	//Login and Add giftcard in cart
    	//TODO
    	
    	HashMap<String,String>cartHeader=addGiftcardToCartPage(email,password,"1");
    	
    	//TODO get uidx
    	String uidx=giftcardHelper.getUidxFromEmail(email);
    	// Create config
    	String gcImage="{\\\"id\\\":61331293,\\\"relativePath\\\":\\\"assets/images/2017/11/28/11511872940847-1511872270952.png\\\",\\\"size\\\":455649,\\\"domain\\\":\\\"http://assets.myntassets.com/\\\",\\\"securedDomain\\\":\\\"https://assets.myntassets.com/\\\",\\\"resolutionFormula\\\":\\\"h_($height),q_($qualityPercentage),w_($width)/v1/assets/images/2017/11/28/11511872940847-1511872270952.png\\\",\\\"path\\\":\\\"http://assets.myntassets.com/assets/images/2017/11/28/11511872940847-1511872270952.png\\\",\\\"storedUploaderType\\\":\\\"CL\\\",\\\"servingUploaderType\\\":\\\"CL\\\",\\\"expireAfter\\\":1513600940869}";
    	String coverImage="https://assets.myntassets.com/v1/assets/images/2017/10/27/11509099700989-120553-1vbu8td.jpg";
    	String cardImage="https://assets.myntassets.com/v1/assets/images/2017/10/27/11509099738086-7696-x4kyia.png";
    	RequestGenerator req = giftcardHelper.createGCConfig("senderName","senderEmail","recipientName","umang.lavania@gmail.com","Test message",gcImage,coverImage,cardImage,"true");
    	Assert.assertEquals(req.response.getStatus(), 200);
    	String jsonRes=req.respvalidate.returnresponseasstring();
    	String configId=JsonPath.read(jsonRes, "$.data.configId").toString();
    	//String customizedMessage="\"{\\\"configId\\\": \\\""+configId+"\\\"}\"";
    	String customizedMessage="\"{\\\"configId\\\": \\\""+configId+"\\\",\\\"cardProgramGroupName\\\":\\\""+cardProgramGroupName+"\\\",\\\"login\\\":\\\""+uidx+"\\\"}\"";
    	// Fetch cart in egiftcard context and get the itemid
    	String context="egiftcard";
    	RequestGenerator fetchCartReq= checkoutTestHelper.operationFetchCartContext(email, password, context);
    	System.out.println("The response is : "+fetchCartReq.returnresponseasstring());
    	String fetchCartResponse=fetchCartReq.respvalidate.returnresponseasstring();
    	String itemId=JsonPath.read(fetchCartResponse, "$.data..cartItemEntries[*].itemId").toString().replace("[", "").replace("]", "");
    	System.out.println("Item is :" + itemId);
    	// Update giftcard with the fetched item
    	RequestGenerator req1=updateGiftcardToCartPage("1522720", "12094905",customizedMessage,itemId,"test message123","umang.lavania@gmail.com",cartHeader);
    	Assert.assertEquals(req1.response.getStatus(), 200);
    
    	// Navigate to Payment Page
    	String response = paymentPageForGiftcard(email, password,cartHeader,useSavedCard,pm,clientCode,cartContext,profile, userGroup,wallet_enabled, other_cards,  card_number,  bill_name,card_month, card_year, cvv_code);
		Thread.sleep(5000);

		// extract orderid from html response
				String result1[]=ppsServiceHelper.getPPSIdAndOrderIdFromResponse(response);
		        String ppsId=result1[0];
		        String orderId=result1[1];
		        System.out.println("OrderId is : "+orderId + "ppsid is :" + ppsId);
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

    
    private String paymentPageForGiftcard(String email, String password, HashMap<String, String> cartHeader, String useSavedCard, String pm, String clientCode, String cartContext, String profile,
    		String userGroup, String wallet_enabled, String other_cards, String card_number, String bill_name, String card_month, 
    		String card_year, String cvv_code) throws ParseException, JSONException {
    	HashMap<String,String>addressDetails = navigateToPaymentPage(email, password,cartHeader);
    	
        // Get Payments Page and extract token
      	String encodedcsrf=ppsServiceHelper.getEncodedTokenFromPaymentPageForGiftcard(cartHeader.get("xid").toString(),cartHeader.get("X-CSRF-Token").toString(),PaymentPage_Host,PaymentPage_Referer);

      	String payload = createPayloadGiftcardPurchase ( email,useSavedCard, addressDetails.get("addressId").toString(),  pm,  clientCode,
        		cartContext, profile, userGroup,  addressDetails.get("mobile").toString(),
        		 wallet_enabled, other_cards,  card_number,  bill_name,
        		 card_month, card_year, cvv_code, 
        		 addressDetails.get("firstname").toString(),  addressDetails.get("defaultAddress").toString(),  addressDetails.get("city").toString(),  addressDetails.get("state").toString(),
        		 addressDetails.get("zipcode").toString(),  addressDetails.get("country").toString(),encodedcsrf);
      	
      	System.out.println("payload="+payload);  	
    	String responseN = ppsServiceHelper.getPaynowResponse(cartHeader.get("xid").toString(),PPS_Host,encodedcsrf,payload);
    	
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
	
	
	private HashMap<String, String> addGiftcardToCartPage(String email, String password, String qty) {
    	CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    	
    	// Extract xid from idp login API
    			xid=checkoutTestHelper.getXID(email, password);
    			System.out.println("XID is : " + xid);
    			// Extract sxid and csrf token from get session API
    			String result[]=checkoutTestHelper.getSxidAndUserToken(xid);
    			String user_token = result[0];
    			String sxid=result[1];

    			// clear all items in cart
    			HashMap<String, String> cartHeader=new HashMap<String,String>();
    			cartHeader.put("X-CSRF-Token", sxid);
    			cartHeader.put("xid", xid);
    			cartHeader.put("token", user_token);
    			//checkoutTestHelper.deleteCart(cartHeader);
    	
    			// Add item to cart page
    			RequestGenerator req_addItemToCart=checkoutTestHelper.addGiftcardToCartWithQty("1522720", "12094905", "ADD", cartHeader);	
    			System.out.println("Response is : " + req_addItemToCart.returnresponseasstring());
    			return cartHeader;
	}
	
	
	private RequestGenerator updateGiftcardToCartPage(String style,String sku,String customizedMessage, String itemId,String message,String recipientEmailId,HashMap<String,String> cartHeader) {
				CheckoutTestsHelper checkoutTestHelper = new CheckoutTestsHelper ();
    			// Add item to cart page
    			RequestGenerator req_updateItemToCart=checkoutTestHelper.addGiftcardToCartWithQty(style,sku, customizedMessage,itemId,message, recipientEmailId,cartHeader);	
    			return req_updateItemToCart;
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

    
    private HashMap<String,String> setHeadersCod ()
    {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        HashMap<String,String>hm = new HashMap<String,String>();
        String value = "fox-xid="+xid;
        hm.put("cookie", value);
        hm.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        hm.put("Accept-Encoding", "gzip, deflate");
        hm.put("Accept-Language", "en-US,en;q=0.5");
        hm.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
        hm.put("Host", "d7pps.myntra.com");
        hm.put("Content-Type", "application/x-www-form-urlencoded");
        hm.put("csrf_token", csrf_token);
        return hm;
    }
    
    private String createPayloadCod(String email,String address, String clientCode,
			String cartContext, String pm, String profile, String userGroup,
			String mobile, String b_firstname, String b_address, String b_city,
			String b_state, String b_zipcode, String b_country,
			String wallet_enabled, String userinputcaptcha) {

    		StringBuffer encodedURL = new StringBuffer();
    	
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
    		encodedURL.append("csrf_token=");encodedURL.append(csrf_token.trim());encodedURL.append("&");
    		encodedURL.append("userinputcaptcha=");encodedURL.append(userinputcaptcha.trim());

    	String payload = encodedURL.toString();
        System.out.println("Payload is : "+payload);
        return payload;
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
    
    
    private String createPayloadGiftcardPurchase(String email,String useSavedCard,String address, String pm, String clientCode,
    		String cartContext, String profile, String userGroup, String mobile,
    		String wallet_enabled,String other_cards, String card_number, String bill_name,
    		String card_month, String card_year, String cvv_code, 
    		String b_firstname, String b_address, String b_city, String b_state,
    		String b_zipcode, String b_country,String token)
    {
    	StringBuffer encodedURL = new StringBuffer();
    	if(pm.equalsIgnoreCase("creditcard"))
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
    
/*	public static void connect(){
		if(isSlackEnabled && !session.isConnected()){
			try {
				session.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void disconnect(){
		if(isSlackEnabled ){
			try {
				session.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
*/	
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
