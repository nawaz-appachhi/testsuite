package com.myntra.apiTests.portalservices.absolutService;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.ExpressionEval;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

import argo.saj.InvalidSyntaxException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class AbsolutHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AbsolutHelper.class);
	static String prevUser, prevPass;

	APIUtilities apiUtil = new APIUtilities();
	
	static APIUtilities utilities = new APIUtilities();
	
	String envName = init.Configurations.GetTestEnvironemnt().name();
	
	String URL = "api/exchanges/%2F/amq.default/publish";

	
	 public HashMap signInAndGetSessionHeader(String userName, String password) throws UnsupportedEncodingException, InvalidSyntaxException {
	        APIUtilities utilities = new APIUtilities();
	        HashMap<String, String> header = new HashMap();
	        header.put("Accept","application/json");
	        header.put("Content-Type","application/json");
	        String payload = "{\"userId\":\""+userName+"\",\"password\":\""+password+"\"}";
	        Svc svc = HttpExecutorService.executeHttpService(Constants.IDP_PATH.SIGN_IN, null, SERVICE_TYPE.IDP_SERVICE.toString(), HTTPMethods.POST, payload, header);
	        Assert.assertTrue(ExpressionEval.evaluateForJsonPath(
	                "status.statusType=='SUCCESS'",
	                new com.jayway.restassured.path.json.JsonPath(svc.getResponseBody())),
	                          "Login Failed For Username " + userName);

	        Map<String, String> signInResponseHeader = svc.getResponseHeadersAsMap();
	        String xid = signInResponseHeader.get("xid");
	        String csrfToken = utilities.GetChildNodeValueUsingIndex("xsrfToken",svc.getResponseBody());

	        if (csrfToken.contains("'"))
	            csrfToken = csrfToken.substring(csrfToken.indexOf("'") + 1, csrfToken.lastIndexOf("'"));
	        else
	            csrfToken = csrfToken.substring(csrfToken.indexOf("[") + 1, csrfToken.lastIndexOf("]"));

	        header.put("X-CSRF-Token", csrfToken);
	        header.put("xid", xid);
	        return header;
	    }



		public String[] idp_GetTokens(String userName, String password) {
			String xID = "", sXid ="", statusMessage ="";
			log.info("Printing Username :" + userName + " \n Password: "+ password);
			MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[] { userName,password });
			log.info("Printing IDP Service API URL : " + serviceSignIn.URL);

			log.info("Printing IDP Service API Payload : "
					+ serviceSignIn.Payload);

			RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
			log.info("Printing IDP Service API response ....."+ reqSignIn.respvalidate.returnresponseasstring());

			MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
			for (Map.Entry entry : map.entrySet()) {
				if (entry.getKey().toString().equalsIgnoreCase("xid"))
					xID = entry.getValue().toString();
				// System.out.println("xID="+xID);
			}

			log.info("\nPrinting xID from Headers  : " + xID);

			xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
			sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		    String statusResponse = reqSignIn.respvalidate.NodeValue(AbsolutNodes.STATUS_MESSAGE.toString(), true);
			statusMessage = statusResponse.split("\"")[1];
			log.info("\nPrinting sXid from Response  : " + sXid);

			if (sXid.contains("'"))
				sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
			else
				sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

			log.info("Printing final xID : " + xID);

			log.info("Printing final sxid : " + sXid);

			log.info("Printing IDP Service response : " + reqSignIn.response);

			String[] idpInfo = { xID, sXid, statusMessage};

			return idpInfo;
		}
		

		/**
		 * This method is used to invoke IDPService SignOut API
		 * 
		 * @param userName
		 * @param signInReqGen
		 * @return RequestGenerator
		 */
		public RequestGenerator performSignOutOperation(String userName, String xsrfToken)
		{
			MyntraService signOutService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNOUT, init.Configurations, new String[]{ userName });
			String token = "X-CSRF-TOKEN="+xsrfToken;
			System.out.println("token value is:"+token);
			signOutService.URL = signOutService.URL+"?"+token;
			
		    System.out.println("\nPrinting IDPService SignOut API URL : "+signOutService.URL);
			log.info("\nPrinting IDPService SignOut API URL : "+signOutService.URL);
			
			System.out.println("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);
			log.info("\nPrinting IDP Service SignOut API Payload : \n\n"+signOutService.Payload);
			
			return new RequestGenerator(signOutService);
		}
	/**
	 * This method is used to add the item to the cart based on the qty.
	 *
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param quantity
	 * @param operation
	 * @return RequestGenerator
	 */
	public RequestGenerator addItemToCart(String xid,String skuId, String quantity, String operation) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.ADDITEMTOCART, init.Configurations, new String[] {
						skuId, quantity, operation });
		System.out.println("\nPrinting addItemToCart API URL : "+ service.URL);
		System.out.println("\nPrinting addItemToCart API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}
	
	/**
	 * This method is used to add the item to the cart based on the qty.
	 *
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param quantity
	 * @param operation
	 * @return RequestGenerator
	 */
	public RequestGenerator addEGiftCardToCart(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.ADDEGIFTCARDTOCART, init.Configurations);
		System.out.println("\nPrinting addEGiftCardToCart API URL : "+ service.URL);
		System.out.println("\nPrinting addEGiftCardToCart API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		headers.put("Cache-Control", "no-cache");
		return new RequestGenerator(service, headers);
	}
	/**
	 * This method is used to update the item in the cart
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @param operation
	 * @return RequestGenerator
	 */
	public RequestGenerator updateItemInCart(String xid,String itemId, String skuId,
			String quantity, String operation) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.UPDATEITEMINCART, init.Configurations, new String[] {itemId, skuId, quantity, operation });
		System.out.println("\nPrinting updateItemInCart API URL : "+ service.URL);
		System.out.println("\nPrinting updateItemInCart API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the item from the cart
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @param operation
	 * @return RequestGenerator
	 */
	public RequestGenerator removeItemFromCart(String xid, String itemId, String operation) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.REMOVEITEMFROMCART, init.Configurations, new String[] {itemId, operation });
		System.out.println("\nPrinting removeItemFromCart API URL : "+ service.URL);
		System.out.println("\nPrinting removeItemFromCart API Payload : "+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to fetch the cart data
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator fetchCart(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.OPERATIONFETCHCART, init.Configurations);
		System.out.println("\nPrinting operationFetchCart API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to fetch the cart data with different contexts
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator fetchCartByContext(String xid,String context) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.FETCHCARTCONTEXT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, context);
		System.out.println("\nPrinting fetchCartContext API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to clear the cart
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator clearCart(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting clearCart API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}
	
	/**
	 * This method is used to add EGiftCard to the cart based on the skuid 
	 * where for each EGiftCard amount one skuId is mapped.
	 *
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param quantity
	 * @param operation
	 * @return RequestGenerator
	 *//*
	public RequestGenerator addEGiftCardToCart(String xid,String userName, String password,
			String skuId) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.ADDEGIFTCARD, init.Configurations, new String[] {skuId});
		System.out.println("\nPrinting addEGiftCardToCart API URL : "+ service.URL);
		System.out.println("\nPrinting addEGiftCardToCart API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}*/
	
	
	/**
	 * This method is used to add the giftWrap with message
	 *
	 * @param userName
	 * @param password
	 * @param sender
	 * @param message
	 * @param recipient
	 * @return RequestGenerator
	 */
	public RequestGenerator addGiftWrapAndMessage(String xid,String sender, String message, String recipient) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.ADDGIFTWRAPANDMESSAGE, init.Configurations,new String[] { sender, message, recipient });
		System.out.println("\nPrinting addGiftWrapAndMessage API URL : "+ service.URL);
		System.out.println("\nPrinting addGiftWrapAndMessage API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the gift wrap
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	public RequestGenerator removeGiftWrapAndMessage(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.REMOVEGIFTWRAPANDMESSAGE, init.Configurations);
		System.out.println("\nPrinting removeGiftWrapAndMessage API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to apply coupon
	 *
	 * @param user
	 * @param pass
	 * @param couponId
	 * @return RequestGenerator
	 */
	public RequestGenerator applyCoupon(String xid,String couponId) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.APPLYCOUPON, init.Configurations,new String[] { couponId });
		System.out.println("\nPrinting applyCoupon API URL : "+ service.URL);
		System.out.println("\nPrinting applyCoupon API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to get all coupons
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator fetchAllCoupons(String xid) {
		MyntraService service = Myntra.getService(
			ServiceType.PORTAL_COUPONSERVICE, APINAME.BESTCOUPON,init.Configurations);
		System.out.println("\nPrinting fetchAllCoupons API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the coupon
	 *
	 * @param user
	 * @param pass
	 * @param couponId
	 * @return RequestGenerator
	 */
	public RequestGenerator removeCoupon(String xid,String couponId) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,APINAME.REMOVECOUPON, 
			init.Configurations,new String[] { couponId }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting removeCoupon API URL : "+ service.URL);
		System.out.println("\nPrinting removeCoupon API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to get the available Loyalty Points
	 * 
	 * @param user
	 * @return RequestGenerator
	 */
	public RequestGenerator getLoyaltyPoints(String user) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, user);
		System.out.println("\nPrinting getLoyaltyPoints API URL : "+ service.URL);
		return new RequestGenerator(service);
	}

	/**
	 * This method is used to remove the applied Loyalty Points
	 * 
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator removeLoyaltyPoints(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.REMOVELOYALTYPOINTS, init.Configurations);
		System.out.println("\nPrinting removeLoyaltyPoints API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to apply Loyalty Points
	 * 
	 * @param user
	 * @param pass
	 * @param points
	 * @return RequestGenerator
	 */
	public RequestGenerator applyLoyaltyPoints(String xid,String points) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.APPLYLOYALTYPOINTS, init.Configurations,new String[] { points });
		System.out.println("\nPrinting applyLoyaltyPoints API URL : "+ service.URL);
		System.out.println("\nPrinting applyLoyaltyPoints API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}
	
	/**
	 * This method is used to apply mynt credit
	 *
	 * @param user
	 * @param pass
	 * @param creditAmt
	 * @return RequestGenerator
	 */
	public RequestGenerator applyMyntCredit(String xid,String creditAmt) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
			APINAME.APPLYMYNTCREDIT, init.Configurations,new String[] { creditAmt });
		System.out.println("\nPrinting CheckoutService applyMyntCredit API URL : "+ service.URL);
		System.out.println("\nPrinting CheckoutService applyMyntCredit API Payload : \n\n"
			+ service.Payload);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}
	
	/**
	 * This method is used to get the seller config data
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator getSellerConfig(String sellerId) {
		MyntraService service = Myntra.getService(ServiceType.ERP_SELLERSERVICES,APINAME.GETSELLERCONFIG, 
			init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, sellerId);
		System.out.println("\nPrinting getSellerConfig API URL : "
						+ service.URL);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Basic YTpi");
		headers.put("cache-control", "no-cache");
		return new RequestGenerator(service, headers);
	}


	/**
	 * This method is used to get the myntra credit
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator getMyntCredit(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,APINAME.GETMYNTCREDIT, init.Configurations);
		System.out.println("\nPrinting CheckoutService getMyntCredit API URL : "
						+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the myntra credit
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator removeMyntCredit(String xid) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,APINAME.REMOVEMYNTCREDIT, init.Configurations);
		System.out.println("\nPrinting CheckoutService removeMyntCredit API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);

		return new RequestGenerator(service, headers);
	}

	/**
	 * @author This method is used to Opt TryNBuy On Delivery API
	 * 
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator optTryNBuyOnDelivery(String xid,String itemID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,APINAME.OPTTRYANDBUY, 
				init.Configurations,new String[] { itemID });
		System.out.println("\nPrinting CheckoutService optTryAndBuy API URL : "
				+ service.URL);
		System.out.println("\nPrinting CheckoutService optTryAndBuy API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("xid", xid);
		return new RequestGenerator(service, headers);
	}
	
	/**
	 * This method is used to get the  FG/widget-key configValues
	 * 
	 * @param user
	 * @return RequestGenerator
	 */
	public RequestGenerator getSwitchConfigValues(String configType) {
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SWITCH,
				APINAME.GETCONFIG, init.Configurations,
				PayloadType.JSON, new String[] { configType }, PayloadType.JSON);
		//service.URL = apiUtil.prepareparameterizedURL(service.URL,configType );
		System.out.println("\nPrinting getSwitchConfigValues API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("switch-key", "0c792d1b-f100-465d-b817-a58aef6ffce9");
		return new RequestGenerator(service);
	}
	
	/**
	 * This method is used to get the  FG/widget-key configValues
	 * 
	 * @param user
	 * @return RequestGenerator
	 */
	public RequestGenerator updateSwitchConfigValues(String configType, String key, String variant) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_SWITCH,
				APINAME.UPDATECONFIG, init.Configurations, new String[]{key});
		String value[] = {configType,variant};
		service.URL = apiUtil.prepareparameterizedURL(service.URL,value);
		//service.URL = apiUtil.prepareparameterizedURL(service.URL,configType,variant );
		System.out.println("\nPrinting updateSwitchConfigValues API URL : "+ service.URL);
		System.out.println("\nPrinting updateSwitchConfigValues API Payload : "+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("switch-key", "0c792d1b-f100-465d-b817-a58aef6ffce9");
		return new RequestGenerator(service,headers);
	}
	
	public HashMap<String, String> extractSellerConfigFlagValues(RequestGenerator reqGen){
		HashMap <String,String> keys = new HashMap<String,String>();
		JSONArray data,configAttributes;
		data = JsonPath.read(reqGen.respvalidate.returnresponseasstring(), "$.data");
		configAttributes =JsonPath.read(data.get(0), "$.configurationAttributes");
		for(int i=0;i<configAttributes.size();i++){
			//System.out.println("at index"+i+"  "+configAttributes.get(i));
			String key = JsonPath.read(configAttributes.get(i), "$.configurationKey");
			String value = JsonPath.read(configAttributes.get(i), "$.configurationValue");
			
			keys.put(key,value);
			
		}
		return keys;
	}

	/**
	 * This method is used to get the itemId form the skuId
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String getItemData(RequestGenerator reqGen, String pSKUId) {
		if (null != pSKUId) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.styleOptionsData.styleOptions[*].skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.styleOptionsData.styleOptions["+selectedSKUIdCount+"].skuAvailabilityDetailMap.sellableInventoryCount")
								.toString().replace("[", "").replace("]", "").trim();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									System.out.println("form second sku id sent,"+JsonPath.read(
											reqGen.respvalidate.returnresponseasstring(),
											"$.data..cartItemEntries[" + selectedSKUIdCount
													+ "].sellerId").toString().replace("[", "").replace("]", "").trim());
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].sellerId")
											.toString().replace("[", "").replace("]", "").trim();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} 

		return null;
	}
	public String getSelleableInvCountFromStyleData(JSONObject styleData, String skuId){
		String invCount="";
		if(skuId !=null){
			List<Integer> selectedSkuIds = JsonPath.read(styleData,
					"$.styleOptionsData.styleOptions[*].skuId");
			System.out.println("skuids selected are:"+selectedSkuIds);
			for(int sku : selectedSkuIds){
				int selectedCount =0;
				if(sku == Integer.parseInt(skuId)){
					invCount = JsonPath.read(styleData,
						"$.styleOptionsData.styleOptions["+selectedCount+"].skuAvailabilityDetailMap.sellableInventoryCount")
							.toString().replace("[", "").replace("]", "").trim();
					return invCount;
				}
				selectedCount++;
			}
		}else
			return null;
		return invCount;
	}
	public String getExpectedCartLevelFlags(List<HashMap<String,String>> keys, String configKey, int totalCartCount, int canBeMixed){
		int notApplicable=0;
		String applicable="";
		for(int i=0; i<keys.size();i++){
			if(keys.get(i).get(configKey).equals("false")){
				notApplicable++;
			}
		}
		if(canBeMixed==1){
			if(notApplicable ==0)
			{
				applicable = "TRUE";
			}
			else if(notApplicable == totalCartCount)
				
			{
				applicable ="FALSE";}
				else
					applicable="MIXED";
		}else{
			if(notApplicable ==0)
			{
				applicable = "true";
			}
			else
				applicable ="false";
		}
		return applicable;
	}
	
	
	
	/**
	 * This method is used to get the itemId form the skuId
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String getSellerIdFromSKUId(RequestGenerator reqGen, String pSKUId) {
		if (null != pSKUId) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..cartItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].sellerId").toString().replace("[", "").replace("]", "").trim();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									System.out.println("form second sku id sent,"+JsonPath.read(
											reqGen.respvalidate.returnresponseasstring(),
											"$.data..cartItemEntries[" + selectedSKUIdCount
													+ "].sellerId").toString().replace("[", "").replace("]", "").trim());
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].sellerId")
											.toString().replace("[", "").replace("]", "").trim();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} 

		return null;
	}
	/**
	 * This method is used to get the itemId form the skuId
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String getItemIdFromSKUId(RequestGenerator reqGen, String pSKUId) {
		if (null != pSKUId) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..cartItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].itemId").toString().replace("[", "").replace("]", "").trim();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									System.out.println("form second sku id sent,"+JsonPath.read(
											reqGen.respvalidate.returnresponseasstring(),
											"$.data..cartItemEntries[" + selectedSKUIdCount
													+ "].itemId").toString().replace("[", "").replace("]", "").trim());
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].itemId")
											.toString().replace("[", "").replace("]", "").trim();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} 

		return null;
	}

	/**
	 * This method is used to find "Is the passed skuId exists in APIs response"
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String doesSkuIDExists(RequestGenerator reqGen, String pSKUId) {
		if (null != pSKUId) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..cartItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						return "Exists";
					} 
					
				}
			}
			return "Not Exists";
		} 

		return "Null SkuID Sent";
	}
	
	/**
	 * This method is used to get the itemId form the given skuId
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String getItemQtyFromSKUId(RequestGenerator reqGen, String pSKUId) {
		if (null != pSKUId) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..cartItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].quantity").toString().replace("[", "").replace("]", "").trim();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									System.out.println("form second sku id sent,"+JsonPath.read(
											reqGen.respvalidate.returnresponseasstring(),
											"$.data..cartItemEntries[" + selectedSKUIdCount
													+ "].quantity").toString().replace("[", "").replace("]", "").trim());
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].quantity")
											.toString().replace("[", "").replace("]", "").trim();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} 

		return null;
	}
	
	public String getNodeData(String NodePath, String JsonString) {
		/*
		 * JsonParser parser = new JsonParser();
		 * ResponseFromService.bufferEntity(); String jsonLine =
		 * ResponseFromService.readEntity(String.class),result = null;
		 */
		JsonReader jsonReader = new JsonReader(new StringReader(JsonString));
		jsonReader.setLenient(true);

		String result = null;
		JsonArray jarray;
		JsonObject jobject = null;

		JsonElement jelement = new JsonParser().parse(jsonReader);
		if (jelement.isJsonArray()) {
			jarray = jelement.getAsJsonArray();
			for (int i = 0; i < jarray.size(); i++) {
				jobject = jarray.get(i).getAsJsonObject();
			}
		} else {
			jobject = jelement.getAsJsonObject();
		}

		String[] nodepath = NodePath.split("\\.");

		int counter = nodepath.length;

		for (String node : nodepath) {
			JsonElement element = jobject.get(node);
			log.info("Node is : " + node + " : " + counter + "  element is " + element.toString());
			if (counter > 1) {
				if (element.isJsonObject()) {
					jobject = jobject.getAsJsonObject(node);
				} else {
					if (element.isJsonArray()) {
						jarray = jobject.getAsJsonArray(node);
						for (int i = 0; i < jarray.size(); i++) {
							jobject = jarray.get(i).getAsJsonObject();
						}
					} else {

						log.info("Node is neither a json array or a jason object. FOllowing is the Node \n\n\n"
								+ jobject.toString() + "\n\n");
					}
				}

			} else {
				result = jobject.get(node).toString();
			}
			counter--;

		}
		return result;
	}
	
	/**
	 * This method is used to fetch the cart data with different contexts
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator getServiceabilityForPincode(String pincode, String xid) {
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,APINAME.PINCODESERVICEABILITY, 
				init.Configurations);
			service.URL = apiUtil.prepareparameterizedURL(service.URL, pincode);
			System.out.println("\nPrinting getSellerConfig API URL : "
							+ service.URL);			
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			headers.put("xid", xid);

			return new RequestGenerator(service, headers);
	}
	
	public String getCartSecuredAPI(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.CART_PATH.CART_SECURED, new String[] {uidx + "/default"},
				SERVICE_TYPE.ABSOLUT_SVC.toString(), HTTPMethods.GET, null, getCartConsumerHeaders(uidx));
		return service.getResponseBody();
	}
	
	public String deleteCartSecuredAPI(String uidx) throws UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.CART_PATH.CART_SECURED, new String[] {uidx + "/default"},
				SERVICE_TYPE.ABSOLUT_SVC.toString(), HTTPMethods.DELETE, null, getCartConsumerHeaders(uidx));
		return service.getResponseBody();
	}
	
	public void publishMessageToQueue(String message, String queueName) throws UnsupportedEncodingException {
		Object[] strPayload = {queueName, message};
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/publishmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(URL, new String[] {""},
				SERVICE_TYPE.ERPRABBITMQ.toString(), HTTPMethods.POST, payload, getRabbitMQHeaders());
	}

	private HashMap<String, String> getRabbitMQHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "Application/json");
		headers.put("Accept", "Application/json");
		headers.put("authorization", "Basic Z3Vlc3Q6Z3Vlc3Q=");
		return headers;
	} 
	
	private HashMap<String, String> getCartConsumerHeaders(String uidx) {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "Application/json");
		headers.put("Accept", "Application/json");
		String xMyntCtx = "storeid=4603;uidx=" + uidx;
		headers.put("x-mynt-ctx", xMyntCtx);
		System.out.println(headers);
		return headers;
	}

}
