package com.myntra.apiTests.portalservices.checkoutservice;

import argo.saj.InvalidSyntaxException;

import com.google.gson.JsonArray;
import com.jayway.jsonpath.JsonPath;
import com.myntra.absolut.cart.client.response.CartResponse;
import com.myntra.absolut.cart.client.response.entry.CartEntry;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.*;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import net.minidev.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author shankara.c
 */
public class CheckoutTestsHelper extends CommonUtils {
    static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CheckoutTestsHelper.class);
	static String xID, sXid;
	static String prevUser, prevPass;
	APIUtilities apiUtil = new APIUtilities();

	/**
	 * This method is used to set and get the tokens based on valid user logins.
	 *
	 * @param userName
	 * @param password
	 */
	private String getAndSetTokens(String userName, String password) {
		System.out.println("\nPrinting \n Username : " + userName
				+ " \n Password : " + password);
		log.info("\nPrinting \n Username :" + userName + " \n Password: "
				+ password);

		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[] { userName,
						password });
		System.out.println("\nPrinting IDP Service API URL : "
				+ serviceSignIn.URL);
		log.info("\nPrinting IDP Service API URL : " + serviceSignIn.URL);

		System.out.println("\nPrinting IDP Service API Payload : \n\n"
				+ serviceSignIn.Payload);
		log.info("\nPrinting IDP Service API Payload : \n\n"
				+ serviceSignIn.Payload);

		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println("\nPrinting IDP Service API response .....\n\n"
				+ reqSignIn.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDP Service API response .....\n\n"
				+ reqSignIn.respvalidate.returnresponseasstring());

		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		System.out.println("\nPrinting xID from Headers  : " + xID);
		log.info("\nPrinting xID from Headers  : " + xID);

		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");

		System.out.println("\nPrinting sXid from Response  : " + sXid);
		log.info("\nPrinting sXid from Response  : " + sXid);

		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

		System.out.println("\nPrinting final xID : " + xID);
		log.info("\nPrinting final xID : " + xID);

		System.out.println("\nPrinting final sxid : " + sXid);
		log.info("\nPrinting final sxid : " + sXid);

		System.out.println("\nPrinting IDP Service response : "
				+ reqSignIn.response);
		log.info("\nPrinting IDP Service response : " + reqSignIn.response);
		return reqSignIn.returnresponseasstring();
	}


	public static String[] idp_GetTokens(String userName, String password) {

		// List<String> idpInfo=new ArrayList<String>();

		log.info("Printing Username :" + userName + " \n Password: "
				+ password);

		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[] { userName,
						password });
		log.info("Printing IDP Service API URL : " + serviceSignIn.URL);

		log.info("Printing IDP Service API Payload : "
				+ serviceSignIn.Payload);

		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		log.info("Printing IDP Service API response ....."
				+ reqSignIn.respvalidate.returnresponseasstring());

		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
			// System.out.println("xID="+xID);
		}

		log.info("\nPrinting xID from Headers  : " + xID);

		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");

		log.info("\nPrinting sXid from Response  : " + sXid);

		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

		log.info("Printing final xID : " + xID);

		log.info("Printing final sxid : " + sXid);

		log.info("Printing IDP Service response : " + reqSignIn.response);

		String[] idpInfo = { xID, sXid };

		return idpInfo;
	}
	
	public static String[] idp_GetNewTokens(String userName, String password) {

		// List<String> idpInfo=new ArrayList<String>();

		log.info("Printing Username :" + userName + " \n Password: "
				+ password);

		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP,
				APINAME.SIGNIN, init.Configurations, new String[] { userName,
						password });
		log.info("Printing IDP Service API URL : " + serviceSignIn.URL);

		log.info("Printing IDP Service API Payload : "
				+ serviceSignIn.Payload);

		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		log.info("Printing IDP Service API response ....."
				+ reqSignIn.respvalidate.returnresponseasstring());

		String session_id = "";
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				{xID = entry.getValue().toString();
			 System.out.println("xID="+xID);}
			 else if (entry.getKey().toString().equalsIgnoreCase("AT"))
				 {session_id=entry.getValue().toString();
			System.out.println("session id="+session_id);}
		}

		System.out.println(("\nPrinting xID from Headers  : " + xID));

		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]")).trim();
		
		session_id = session_id.substring((session_id.indexOf("[")+1), session_id.lastIndexOf("]")).trim();
		System.out.println("\n Printing session id from Headers :" + session_id);

/*		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");

		log.info("\nPrinting sXid from Response  : " + sXid);

		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));

		log.info("Printing final xID : " + xID);

		log.info("Printing final sxid : " + sXid);

		log.info("Printing IDP Service response : " + reqSignIn.response);
*/
		String[] idpInfo = { xID, session_id };

		return idpInfo;
	}


	/**
	 * This method is used for getting all the address for the registered user
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator getAllAddress(String userName, String password) {
		getAndSetTokens(userName, password);

		MyntraService serviceGetAdd = Myntra.getService(
				ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESS,
				init.Configurations);

		System.out
				.println("\nPrinting CheckoutService getAllAddress API URL : "
						+ serviceGetAdd.URL);
		log.info("\nPrinting CheckoutService getAllAddress API URL : "
				+ serviceGetAdd.URL);

		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);

		return new RequestGenerator(serviceGetAdd, headersGetAdd);
	}

	/**
	 * This method is used to fetch single address based on addressid
	 *
	 * @param userName
	 * @param password
	 * @param addressId
	 * @return RequestGenerator
	 */
	public RequestGenerator fetchSingleAddress(String userName,
			String password, String addressId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.FETCHSINGLEADDRESS, init.Configurations,
				PayloadType.JSON, new String[] { addressId }, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService fetchSingleAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService fetchSingleAddress API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService fetchSingleAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService fetchSingleAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		service.responsedataformat = PayloadType.JSON;

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to find out of the service availability for the
	 * address(s) of the registered user
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator getAllAddressWithServiceAbility(String userName,
			String password) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.LISTALLADDRESSWITHSERVICEABILITY, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService listAllAddressWithServiceAbility API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService listAllAddressWithServiceAbility API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to adding new address for the registered user
	 *
	 * @param userName
	 * @param password
	 * @param address
	 * @param city
	 * @param countryCode
	 * @param defaultAddress
	 * @param emailId
	 * @param locality
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobile
	 * @return RequestGenerator
	 */
	public RequestGenerator addNewAddress(String userName, String password,
			String address, String city, String countryCode,
			String defaultAddress, String emailId, String locality,
			String name, String pincode, String stateCode, String stateName,
			String userId, String mobile) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.ADDNEWADDRESS, init.Configurations, new String[] {
						address, city, countryCode, defaultAddress, emailId,
						locality, name, pincode, stateCode, stateName, userId,
						mobile }, PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService addNewAddress API URL : "
						+ service.URL);
		log.info("\nPrinting addNewAddress API URL : " + service.URL);

		System.out
				.println("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);

	}

	/**
	 * This method is used to update the address for the registered user.
	 *
	 * @param userName
	 * @param password
	 * @param address
	 * @param city
	 * @param countryCode
	 * @param defaultAddress
	 * @param emailId
	 * @param locality
	 * @param name
	 * @param pincode
	 * @param stateCode
	 * @param stateName
	 * @param userId
	 * @param mobile
	 * @param addressId
	 */
	public RequestGenerator updateAddress(String userName, String password,
			String address, String city, String countryCode,
			String defaultAddress, String emailId, String locality,
			String name, String pincode, String stateCode, String stateName,
			String userId, String mobile, String addressId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.UPDATEADDRESS, init.Configurations, new String[] {
						addressId, address, city, countryCode, defaultAddress,
						emailId, locality, name, pincode, stateCode, stateName,
						userId, mobile });
		service.URL = apiUtil.prepareparameterizedURL(service.URL, addressId);
		System.out
				.println("\nPrinting CheckoutService updateAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService updateAddress API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService updateAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService updateAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to delete the address.
	 *
	 * @param userName
	 * @param password
	 * @param addressId
	 * @return RequestGenerator
	 */
	public RequestGenerator deleteAddress(String userName, String password,
			String addressId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.DELETEADDRESS, init.Configurations, PayloadType.JSON,
				new String[] { addressId }, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService deleteAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService deleteAddress API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService deleteAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService deleteAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

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
	public RequestGenerator addItemToCart(String userName, String password,
			String skuId, String quantity, String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDITEMTOCART, init.Configurations, new String[] {
						skuId, quantity, operation });
		System.out
				.println("\nPrinting CheckoutService addItemToCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService addItemToCart API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}
	
	
	public RequestGenerator addItemToCart(String skuId,String quantity,String operation,HashMap<String, String> headers)
	{
		MyntraService service_addItemToCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDITEMTOCART, init.Configurations, new String[] {
				skuId, quantity, operation });
		RequestGenerator req_addItemToCart = new RequestGenerator (service_addItemToCart,headers);
		return req_addItemToCart;
	}
	
	public RequestGenerator addItemToCartWithQty(String skuId,String quantity,String operation,HashMap<String, 
			String> headers)
	{
		MyntraService service_addItemToCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDITEMTOCART, init.Configurations, new String[] {
				skuId, quantity, operation });
		RequestGenerator req_addItemToCart = new RequestGenerator (service_addItemToCart,headers);
		return req_addItemToCart;
	}

	
	public RequestGenerator addGiftcardToCartWithQty(String styleId,String skuId,String operation,HashMap<String, 
			String> headers)
	{
		MyntraService service_addGiftcardToCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDEGIFTCARDTOCART, init.Configurations, new String[] {
						styleId,skuId});
		RequestGenerator req_addItemToCart = new RequestGenerator (service_addGiftcardToCart,headers);
		return req_addItemToCart;
	}
	
	public RequestGenerator addGiftcardToCartWithQty(String styleId,String skuId,String customizedMessage,String itemId,String message,String recipientEmailId,HashMap<String, 
			String> headers)
	{
		MyntraService service_addGiftcardToCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.UPDATEEGIFTCARDTOCART, init.Configurations, new String[] {
						styleId, skuId,customizedMessage,itemId,message,recipientEmailId });
		RequestGenerator req_addItemToCart = new RequestGenerator (service_addGiftcardToCart,headers);
		return req_addItemToCart;
	}
	
	
	public RequestGenerator addItemToCartAndDelete(String userName, String password,
			String skuId, String quantity, String operation) {

		getAndSetTokens(userName, password);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		RequestGenerator req = new RequestGenerator(service, headers);


		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDITEMTOCART, init.Configurations, new String[] {
						skuId, quantity, operation });
		System.out
				.println("\nPrinting CheckoutService addItemToCart API URL : "
						+ service1.URL);
		log.info("\nPrinting CheckoutService addItemToCart API URL : "
				+ service1.URL);

		System.out
				.println("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
						+ service1.Payload);
		log.info("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
				+ service1.Payload);

		HashMap<String, String> headers1 = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service1, headers1);
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
	public RequestGenerator updateItemInCart(String userName, String password,
			String itemId, String skuId, String quantity, String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.UPDATEITEMINCART, init.Configurations, new String[] {
						itemId, skuId, quantity, operation });
		System.out
				.println("\nPrinting CheckoutService updateItemInCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService updateItemInCart API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService updateItemInCart API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService updateItemInCart API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

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
	public RequestGenerator removeItemFromCart(String userName,
			String password, String itemId, String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVEITEMFROMCART, init.Configurations, new String[] {
						itemId, operation });
		System.out
				.println("\nPrinting CheckoutService removeItemFromCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService removeItemFromCart API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService removeItemFromCart API Payload : "
						+ service.Payload);
		log.info("\nPrinting CheckoutService removeItemFromCart API Payload : "
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to fetch the cart data
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator operationFetchCart(String userName, String password) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.OPERATIONFETCHCART, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService operationFetchCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService operationFetchCart API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to clear the cart
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator clearCart(String userName, String password) {
		getAndSetTokens(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}
	
	
	public RequestGenerator deleteCart(HashMap<String, String> headers) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService clearCart API URL : "
				+ service.URL);
		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to add the gift with message
	 *
	 * @param userName
	 * @param password
	 * @param sender
	 * @param message
	 * @param recipient
	 * @return RequestGenerator
	 */
	public RequestGenerator addGiftWrapAndMessage(String userName,
			String password, String sender, String message, String recipient) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.ADDGIFTWRAPANDMESSAGE, init.Configurations,
				new String[] { sender, message, recipient });
		System.out
				.println("\nPrinting CheckoutService addGiftWrapAndMessage API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService addGiftWrapAndMessage API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addGiftWrapAndMessage API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the gift wrap
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	public RequestGenerator removeGiftWrapAndMessage(String userName,
			String password) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVEGIFTWRAPANDMESSAGE, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService removeGiftWrapAndMessage API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService removeGiftWrapAndMessage API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to change the free gift
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @param skuId
	 * @return RequestGenerator
	 */
	public RequestGenerator changeFreeGift(String userName, String password,
			String itemId, String skuId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CHANGEFREEGIFT, init.Configurations, new String[] {
						itemId, skuId });
		System.out
				.println("\nPrinting CheckoutService changeFreeGift API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService changeFreeGift API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService changeFreeGift API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService changeFreeGift API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to move item to wishlist
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator moveItemToWishList(String userName,
			String password, String itemId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.MOVETOWISHLIST, init.Configurations,
				new String[] { itemId });
		System.out
				.println("\nPrinting CheckoutService moveItemToWishList API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService moveItemToWishList API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService moveItemToWishList API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService moveItemToWishList API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to customize the cart
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator operationCustomize(String userName,
			String password, String itemId, String operation,
			String customName, String customNumber, String doCustomize) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.OPERATIONCUSTOMIZE, init.Configurations, new String[] {
						itemId, operation, customName, customNumber,
						doCustomize }, PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService operationCustomize API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService operationCustomize API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService operationCustomize API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService operationCustomize API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to fetch the wishlist
	 *
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator operationFetchWishList(String userName,
			String password) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.OPERATIONFETCHWISHLIST, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService operationFetchWishList API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService operationFetchWishList API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to add the item to wishlist by using skuId/styleId
	 *
	 * @param userName
	 * @param password
	 * @param skuId
	 * @param styleId
	 * @param quantity
	 * @param operation
	 * @return
	 */
	public RequestGenerator addItemToWishList(String userName, String password,
			String skuId, String styleId, String quantity, String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = null;

		if (null != skuId) {
			service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDITEMTOWISHLISTUSINGSKUID, init.Configurations,
					new String[] { skuId, quantity, operation });
		} else if (null != styleId) {
			service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDITEMTOWISHLISTUSINGSTYLEID, init.Configurations,
					new String[] { styleId, quantity, operation });
		}

		System.out
				.println("\nPrinting CheckoutService addItemToWishList API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService addItemToWishList API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService addItemToWishList API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addItemToWishList API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the item from wishlist
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @param operation
	 * @return RequestGenerator
	 */
	public RequestGenerator removeItemFromWishList(String userName,
			String password, String itemId, String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVEITEMFROMWISHLIST, init.Configurations,
				new String[] { itemId, operation });
		System.out
				.println("\nPrinting CheckoutService removeItemFromWishList API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService removeItemFromWishList API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService removeItemFromWishList API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService removeItemFromWishList API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to update the item in wishlist
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @param operation
	 * @return
	 */
	public RequestGenerator updateItemInWishList(String userName,
			String password, String itemId, String skuId, String quantity,
			String operation) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.UPDATEITEMINWISHLIST, init.Configurations,
				new String[] { itemId, skuId, quantity, operation });
		System.out
				.println("\nPrinting CheckoutService updateItemInWishList API URL : "
						+ service.URL);
		log.info("\nPrinting updateItemInWishList API URL : " + service.URL);

		System.out
				.println("\nPrinting CheckoutService updateItemInWishList API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService updateItemInWishList API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to move the item to cart.
	 *
	 * @param userName
	 * @param password
	 * @param itemId
	 * @return
	 */
	public RequestGenerator moveItemToCart(String userName, String password,
			String itemId) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.MOVEITEMTOCART, init.Configurations,
				new String[] { itemId });
		System.out
				.println("\nPrinting CheckoutService moveItemToCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService moveItemToCart API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService moveItemToCart API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService moveItemToCart API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

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
	public RequestGenerator applyMyntCredit(String user, String pass,
			String creditAmt) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.APPLYMYNTCREDIT, init.Configurations,
				new String[] { creditAmt });
		System.out
				.println("\nPrinting CheckoutService applyMyntCredit API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService applyMyntCredit API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService applyMyntCredit API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService applyMyntCredit API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to get the myntra credit
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator getMyntCredit(String user, String pass) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.GETMYNTCREDIT, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService getMyntCredit API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService getMyntCredit API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to remove the myntra credit
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator removeMyntCredit(String user, String pass) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVEMYNTCREDIT, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService removeMyntCredit API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService removeMyntCredit API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

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
	public RequestGenerator applyCoupon(String user, String pass,
			String couponId) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.APPLYCOUPON, init.Configurations,
				new String[] { couponId });
		System.out.println("\nPrinting CheckoutService applyCoupon API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService applyCoupon API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService applyCoupon API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService applyCoupon API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to get all coupons
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator fetchAllCoupons(String user, String pass) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BESTCOUPON,
				init.Configurations);
		System.out
				.println("\nPrinting CheckoutService fetchAllCoupons API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService fetchAllCoupons API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

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
	public RequestGenerator removeCoupon(String user, String pass,
			String couponId) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVECOUPON, init.Configurations,
				new String[] { couponId }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting CheckoutService removeCoupon API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService removeCoupon API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService removeCoupon API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService removeCoupon API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}


    /**
     * This method is used to get the itemId from the skuId
     * @param reqGen
     * @return
     */
    public Integer[] getItemIdsFromSKUIds(RequestGenerator reqGen) {
        String response = reqGen.returnresponseasstring();
        List<Integer> listItemIds = JsonPath.read(
                response,
                "$.data..cartItemEntries[*].itemId");
        return listItemIds.toArray(new Integer[listItemIds.size()]);
    }
    
    /**
     * This method is used to get the itemId from the skuId
     * @param response
     * @return
     */
    public Integer[] getItemIdsFromSKUIds(String response) {
        List<Integer> listItemIds = JsonPath.read(
                response,
                "$.data..cartItemEntries[*].itemId");
        return listItemIds.toArray(new Integer[listItemIds.size()]);
    }
    
	/**
	 * This method is used to get the itemId form the skuId
	 *
	 * @param reqGen
	 * @param pSKUId
	 * @return String
	 */
	public String getItemIdFromSKUId(RequestGenerator reqGen, String pSKUId,
			String opnType) {
		if (null != pSKUId
				&& opnType.equals(CheckoutDataProviderEnum.CART.name())) {

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
										+ "].itemId").toString();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].itemId")
											.toString();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} else if (null != pSKUId
				&& opnType.equals(CheckoutDataProviderEnum.WISHLIST.name())) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..wishListItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..wishListItemEntries["
										+ selectedSKUIdCount + "].itemId")
								.toString();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..wishListItemEntries["
										+ selectedSKUIdCount
										+ "].availableSizes.skuId");

						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..wishListItemEntries["
															+ selectedSKUIdCount
															+ "].itemId")
											.toString();
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
	public String getTryAndBuyFlagFromSKUId(RequestGenerator reqGen,
			String pSKUId, String opnType) {
		if (null != pSKUId
				&& opnType.equals(CheckoutDataProviderEnum.CART.name())) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..cartItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						JSONArray str= JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].tryAndBuyEnabled");
						 return str.get(0).toString();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..cartItemEntries[" + selectedSKUIdCount
										+ "].availableSizes.skuId");
						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..cartItemEntries["
															+ selectedSKUIdCount
															+ "].tryAndBuyEnabled")
											.toString();
								}
							}
						}
					}
					selectedSKUIdCount++;
				}
			}
		} else if (null != pSKUId
				&& opnType.equals(CheckoutDataProviderEnum.WISHLIST.name())) {

			List<Integer> selectedSKUIds = JsonPath.read(
					reqGen.respvalidate.returnresponseasstring(),
					"$.data..wishListItemEntries[*].selectedSize.skuId");

			if (!CollectionUtils.isEmpty(selectedSKUIds)) {
				int selectedSKUIdCount = 0;

				for (Integer selectedSKUId : selectedSKUIds) {
					if (selectedSKUId == Integer.parseInt(pSKUId)) {
						return JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..wishListItemEntries["
										+ selectedSKUIdCount
										+ "].tryAndBuyEnabled").toString();
					} else {
						List<Integer> availableSKUIds = JsonPath.read(
								reqGen.respvalidate.returnresponseasstring(),
								"$.data..wishListItemEntries["
										+ selectedSKUIdCount
										+ "].availableSizes.skuId");

						if (!CollectionUtils.isEmpty(availableSKUIds)
								&& availableSKUIds.toString().contains(pSKUId)) {
							for (Integer availableSKUId : availableSKUIds) {
								if (availableSKUId == Integer.parseInt(pSKUId)) {
									return JsonPath
											.read(reqGen.respvalidate
													.returnresponseasstring(),
													"$.data..wishListItemEntries["
															+ selectedSKUIdCount
															+ "].tryAndBuyEnabled")
											.toString();
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
	 * This method is used to adding new address for the registered user
	 *
	 * @param userId
	 * @param defaultAddress
	 * @param name
	 * @param address
	 * @param city
	 * @param stateCode
	 * @param countryCode
	 * @param pincode
	 * @param email
	 * @param mobile
	 * @param locality
	 * @param role
	 * @param login
	 * @param token
	 * @return
	 */
	public RequestGenerator createNewAddress(String userId,
			String defaultAddress, String name, String address, String city,
			String stateCode, String countryCode, String pincode, String email,
			String mobile, String locality, String role, String login,
			String token) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.CREATENEWADDRESS, init.Configurations, new String[] {
						userId, defaultAddress, name, address, city, stateCode,
						countryCode, pincode, email, mobile, locality },
				PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService addNewAddress API URL : "
						+ service.URL);
		log.info("\nPrinting addNewAddress API URL : " + service.URL);

		System.out
				.println("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("role", role);
		headers.put("login", login);
		headers.put("token", token);

		return new RequestGenerator(service, headers);

	}

	/**
	 * Edit Address
	 *
	 * @param addressId
	 * @param userId
	 * @param defaultAddress
	 * @param name
	 * @param address
	 * @param city
	 * @param stateCode
	 * @param countryCode
	 * @param pincode
	 * @param email
	 * @param mobile
	 * @param locality
	 * @param role
	 * @param login
	 * @param token
	 * @return
	 */
	public RequestGenerator editAddress(String addressId, String userId,
			String defaultAddress, String name, String address, String city,
			String stateCode, String countryCode, String pincode, String email,
			String mobile, String locality, String role, String login,
			String token) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.EDITADDRESS, init.Configurations, new String[] {
						addressId, userId, defaultAddress, name, address, city,
						stateCode, countryCode, pincode, email, mobile,
						locality }, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, addressId);
		System.out.println("\nPrinting CheckoutService editAddress API URL : "
				+ service.URL);
		log.info("\nPrinting editAddress API URL : " + service.URL);

		System.out
				.println("\nPrinting CheckoutService editAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService editAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("role", role);
		headers.put("login", login);
		headers.put("token", token);

		return new RequestGenerator(service, headers);

	}

	public RequestGenerator securityFixDifferentXID_getAllAddress(
			String userName, String password) {
		getAndSetTokens(userName, password);

		String newID = modifyXID(xID);

		MyntraService serviceGetAdd = Myntra.getService(
				ServiceType.PORTAL_ADDRESS, APINAME.GETALLADDRESS,
				init.Configurations);

		System.out
				.println("\nPrinting CheckoutService getAllAddress API URL : "
						+ serviceGetAdd.URL);
		log.info("\nPrinting CheckoutService getAllAddress API URL : "
				+ serviceGetAdd.URL);

		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", newID);

		return new RequestGenerator(serviceGetAdd, headersGetAdd);
	}

	public RequestGenerator securityFixDifferentXID_upDateAddress(
			String userName, String password, String address, String city,
			String countryCode, String defaultAddress, String emailId,
			String locality, String name, String pincode, String stateCode,
			String stateName, String userId, String mobile, String addressId) {
		getAndSetTokens(userName, password);

		String newID = modifyXID(xID);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.UPDATEADDRESS, init.Configurations, new String[] {
						addressId, address, city, countryCode, defaultAddress,
						emailId, locality, name, pincode, stateCode, stateName,
						userId, mobile });

		service.URL = apiUtil.prepareparameterizedURL(service.URL, addressId);
		System.out
				.println("\nPrinting CheckoutService updateAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService updateAddress API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService updateAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService updateAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", newID);

		return new RequestGenerator(service, headers);
	}

	public RequestGenerator securityFixDifferentXID_addNewAddress(
			String userName, String password, String address, String city,
			String countryCode, String defaultAddress, String emailId,
			String locality, String name, String pincode, String stateCode,
			String stateName, String userId, String mobile) {
		getAndSetTokens(userName, password);
		String newID = modifyXID(xID);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.ADDNEWADDRESS, init.Configurations, new String[] {
						address, city, countryCode, defaultAddress, emailId,
						locality, name, pincode, stateCode, stateName, userId,
						mobile }, PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService addNewAddress API URL : "
						+ service.URL);
		log.info("\nPrinting addNewAddress API URL : " + service.URL);

		System.out
				.println("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService addNewAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", newID);

		return new RequestGenerator(service, headers);

	}

	public RequestGenerator securityFixDifferentXID_deleteAddress(
			String userName, String password, String addressId) {
		getAndSetTokens(userName, password);
		String newID = modifyXID(xID);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ADDRESS,
				APINAME.DELETEADDRESS, init.Configurations, PayloadType.JSON,
				new String[] { addressId }, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService deleteAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService deleteAddress API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService deleteAddress API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService deleteAddress API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", newID);

		return new RequestGenerator(service, headers);
	}

	private String modifyXID(String xID2) {

		String Id = "";
		char[] xIDArray = xID2.toCharArray();
		System.out.println("Old XID :" + xID2);
		for (int i = 3; i < xIDArray.length; i = i + 4) {
			if (xIDArray[i] == '-')
				continue;
			xIDArray[i] = 'Z';
		}

		for (int j = 0; j < xIDArray.length; j++)
			Id += xIDArray[j];
		System.out.println("New XID :" + Id);

		return Id;

	}

	/**
	 * Add Multiple Item to Cart with Coupon, CashBack, Loyality Points
	 *
	 * @param userName
	 * @param password
	 * @param skuAndQuantities
	 *            Array of SkuID and Quantity with Colon separated
	 * @param operation
	 * @param couponId
	 * @param cashBack
	 * @param loyalityPoint
	 * @return
	 */
	public RequestGenerator getXIDForMultipleItemToCart(String userName,
			String password, String[] skuAndQuantities, String operation,
			String couponId, String cashBack, String loyalityPoint,
			String isGiftWrap) {

		End2EndHelper end2EndHelper = new End2EndHelper();
		RequestGenerator requestGenerator = null;
		String response = getAndSetTokens(userName, password);
		Assert.assertTrue(ExpressionEval.evaluateForJsonPath(
				"status.statusType=='SUCCESS'",
				new com.jayway.restassured.path.json.JsonPath(response)),
				"Login Failed For Username " + userName);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		log.info("Cleaning the Cart Entries Started");
		MyntraService deleteCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		requestGenerator = new RequestGenerator(deleteCart, headers);
		log.info("Cleaning the Cart Entries Completed");

		for (String skuAndQuantity : skuAndQuantities) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String skuId = skuAndQuantitySplit[0];
			String quantity = skuAndQuantitySplit[1];
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDITEMTOCART, init.Configurations, new String[] {
							skuId, quantity, operation }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, headers);
			end2EndHelper.sleep(100L);
		}

		if ((null != couponId) && (!couponId.equals(""))) {
			log.info("Applying Coupon to CART");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CARTCOUPON, APINAME.APPLYCOUPON,
					init.Configurations, new String[] { couponId },
					PayloadType.JSON, PayloadType.JSON);

			requestGenerator = new RequestGenerator(service, headers);
		}

		if (isGiftWrap.equalsIgnoreCase("1")
				|| isGiftWrap.equalsIgnoreCase("true")) {
			log.info("Applying Giftwrap");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDGIFTWRAPANDMESSAGE, init.Configurations,
					new String[] { "Abhijit", "Happy BirthDay",
							"OMS Automation" }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, headers);
		}

		if ((null != loyalityPoint)
				&& (!loyalityPoint.equals("") && (!loyalityPoint.equals("0")))) {
			log.info("Applying Loyality for Amount : " + loyalityPoint);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYLOYALTYPOINTS, init.Configurations,
					new String[] { loyalityPoint }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, headers);
			System.out.println("Loyalty Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if ((null != cashBack) && (!cashBack.equals(""))
				&& (!cashBack.equals("0"))) {
			log.info("Applying CashBack for Amount : " + cashBack);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYMYNTCREDIT, init.Configurations,
					new String[] { cashBack }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, headers);
		}
		return requestGenerator;
	}

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

	public RequestGenerator getXIDForMultipleItemToCartAuto(String userName,
			String password, String[] skuAndQuantities, String couponId,
			Boolean cashBack, Boolean loyalityPoint, Boolean isGiftWrap,
			Boolean isTryNBuy) throws JAXBException,
            UnsupportedEncodingException, InvalidSyntaxException {

		End2EndHelper end2EndHelper = new End2EndHelper();
		CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();

		RequestGenerator requestGenerator = null;
        HashMap header = signInAndGetSessionHeader(userName, password);
		log.info("Cleaning the Cart Entries Started");
		MyntraService deleteCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		requestGenerator = new RequestGenerator(deleteCart, header);
		log.info("Cleaning the Cart Entries Completed");

		for (String skuAndQuantity : skuAndQuantities) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String skuId = skuAndQuantitySplit[0];
			String quantity = skuAndQuantitySplit[1];
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDITEMTOCART, init.Configurations, new String[] {
							skuId, quantity, "ADD" }, PayloadType.JSON,
					PayloadType.JSON);

			requestGenerator = new RequestGenerator(service, header);
			end2EndHelper.sleep(5L);
		}

		if ((null != couponId) && (!couponId.equals(""))) {
			log.info("Applying Coupon to CART");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYCOUPONCART, init.Configurations,
					new String[] { couponId }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Coupon Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (isGiftWrap) {
			log.info("Applying Giftwrap");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDGIFTWRAPANDMESSAGE, init.Configurations,
					new String[] { "Abhijit", "Happy BirthDay",
							"OMS Automation" }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("GiftWrap Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (isTryNBuy) {
			log.info("Applying TrynBuy in Cart : " + isTryNBuy);
			String tnbSingle = "";

            Integer[] itemIds = getItemIdsFromSKUIds(requestGenerator);

            for (Integer itemId:itemIds) {
                tnbSingle = "{\"itemId\":" + itemId
                        + ",\"tryAndBuyOpted\":true}" + "," + tnbSingle;
            }

			tnbSingle = "[" + tnbSingle.substring(0, tnbSingle.length() - 1)
					+ "]";
			Svc service = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.OPTTRYNBUY, null,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, tnbSingle,
					header);
			System.out.println("try and buy service :"
					+ service.getResponseBody());
		}

		if (loyalityPoint) {
			log.info("Applying Loyality for Amount : " + loyalityPoint);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYLOYALTYPOINTS, init.Configurations,
					new String[] { "1" }, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Loyalty Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (cashBack) {
			log.info("Applying CashBack for Amount : " + cashBack);
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYMYNTCREDIT, init.Configurations,
					new String[] { "1" }, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("CashBack Response:"
					+ requestGenerator.returnresponseasstring());
		}
		return requestGenerator;
	}
	
	public RequestGenerator fetchCart(HashMap<String,String>headers) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.GETCART, init.Configurations, PayloadType.JSON,PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService fetchCart API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService fetchCart API URL : "
				+ service.URL);

		return new RequestGenerator(service, headers);
	}

	public String fetchBalanceFromCart(HashMap<String,String>headers) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.GETCART, init.Configurations, PayloadType.JSON,PayloadType.JSON);
		log.info("\nPrinting CheckoutService fetchCart API URL : "
				+ service.URL);
		RequestGenerator requestGenerator=fetchCart(headers);
		String response = requestGenerator.respvalidate.returnresponseasstring();
		String value=JsonPath.read(response, "$.data..totalPriceWithoutMyntCash").toString().replace("[", "").replace("]", "");
		System.out.println("The value is : "+value);
		return value;
	}
	
	
	public RequestGenerator ppsFormGetXIDForMultipleItemToCartAuto(String userName,
			String password, String[] skuAndQuantities, String couponId,
			Boolean cashBack, Boolean loyalityPoint, String totalLoyaltyPointsAvailable, Boolean isGiftWrap,
			Boolean isTryNBuy,Boolean autogiftcard,Boolean manualgiftcard,String myntCode,
			HashMap<String,String>header, HashMap<String, String> giftCardHeader) throws JAXBException,
            UnsupportedEncodingException, InvalidSyntaxException {

		End2EndHelper end2EndHelper = new End2EndHelper();
		CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
		RequestGenerator requestGenerator = null;

/*		RequestGenerator requestGenerator = null;
        HashMap header = signInAndGetSessionHeader(userName, password);
		log.info("Cleaning the Cart Entries Started");
		MyntraService deleteCart = new MyntraService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		requestGenerator = new RequestGenerator(deleteCart, header);
		log.info("Cleaning the Cart Entries Completed");

		for (String skuAndQuantity : skuAndQuantities) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String skuId = skuAndQuantitySplit[0];
			String quantity = skuAndQuantitySplit[1];
			MyntraService service = new MyntraService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDITEMTOCART, init.Configurations, new String[] {
							skuId, quantity, "ADD" }, PayloadType.JSON,
					PayloadType.JSON);

			requestGenerator = new RequestGenerator(service, header);
			end2EndHelper.sleep(5L);
		}
*/
		if ((null != couponId) && (!couponId.equals(""))) {
			log.info("Applying Coupon to CART");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYCOUPONCART, init.Configurations,
					new String[] { couponId }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Coupon Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (isGiftWrap) {
			log.info("Applying Giftwrap");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.ADDGIFTWRAPANDMESSAGE, init.Configurations,
					new String[] { "Abhijit", "Happy BirthDay",
							"OMS Automation" }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("GiftWrap Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (isTryNBuy) {
			log.info("Applying TrynBuy in Cart : " + isTryNBuy);
			String tnbSingle = "";

            Integer[] itemIds = getItemIdsFromSKUIds(requestGenerator);
            String itemID = Integer.toString(itemIds[0]);
            System.out.println("item id="+itemID);
/*            for (Integer itemId:itemIds) {
                tnbSingle = "{\"itemId\":" + itemId
                        + ",\"tryAndBuyOpted\":true}" + "," + tnbSingle;
            }

			tnbSingle = "[" + tnbSingle.substring(0, tnbSingle.length() - 1)
					+ "]";
			Svc service = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.OPTTRYNBUY, null,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, tnbSingle,
					header);
			System.out.println("try and buy service :"
					+ service.getResponseBody());
										
			*/	MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,APINAME.OPTTRYANDBUY, 
					init.Configurations,new String[] { itemID });
			System.out.println("\nPrinting CheckoutService optTryAndBuy API URL : "
					+ service.URL);
			System.out.println("\nPrinting CheckoutService optTryAndBuy API Payload : \n\n"+ service.Payload);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			headers.put("xid", header.get("xid"));
			requestGenerator= new RequestGenerator(service, headers);
			System.out.println("TryNBuy Response:"
					+ requestGenerator.returnresponseasstring());
            }

		if (loyalityPoint) {
			log.info("Applying Loyality for Amount : " + loyalityPoint);
			System.out.println("Applying Loyality for Amount : " + loyalityPoint);

			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYLOYALTYPOINTS, init.Configurations,
					new String[] { totalLoyaltyPointsAvailable }, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Loyalty Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if (cashBack) {
			log.info("Applying CashBack for Amount : " + cashBack);
			System.out.println("Applying CashBack for Amount : " + cashBack);

			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYMYNTCREDIT, init.Configurations,
					new String[] { "1" }, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("CashBack Response:"
					+ requestGenerator.returnresponseasstring());
		}
		
		if(autogiftcard)
		{
			System.out.println("Applying Autogiftcard");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYAUTOGIFTCARD, init.Configurations,
					new String[] { "1" }, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Auto giftcard Response:"
					+ requestGenerator.returnresponseasstring());
			
		}
		
		
		if ((null != myntCode) && (!myntCode.equals(""))) {
			log.info("Applying Myntcode to CART");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
					APINAME.APPLYMYNTS, init.Configurations,
					new String[] { myntCode }, PayloadType.JSON,
					PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println("Mynt Response:"
					+ requestGenerator.returnresponseasstring());
		}

		if(manualgiftcard)
		{
			System.out.println("Applying Manualgiftcard");
			MyntraService service = Myntra.getService(ServiceType.PORTAL_UI,
					APINAME.APPLYMANUALGIFTCARD, init.Configurations,
					new String[] { "BALANCE",giftCardHeader.get("gcNumber"),giftCardHeader.get("gcPin"), header.get("token").toString()}, PayloadType.JSON, PayloadType.JSON);
			requestGenerator = new RequestGenerator(service, header);
			System.out.println(service.Payload);
			System.out.println(service.URL);
			System.out.println("Manual giftcard Response:"
					+ requestGenerator.returnresponseasstring());
			
		}
		
		return requestGenerator;
	}

	
	/**
	 * To getXID for MultipleItems added to cart
	 * @param userName
	 * @param password
	 * @param skuAndQuantities
	 * @param couponId
	 * @param cashBack
	 * @param loyalityPoint
	 * @param isGiftWrap
	 * @param isTryNBuy
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidSyntaxException
	 */
	public Svc getXIDForMultipleItemToCartAutoSvc(String userName,
			String password, String[] skuAndQuantities, String couponId,
			Boolean cashBack, Boolean loyalityPoint, Boolean isGiftWrap,
			Boolean isTryNBuy) throws JAXBException,
            UnsupportedEncodingException, InvalidSyntaxException {

		End2EndHelper end2EndHelper = new End2EndHelper();
		Svc svc = null;
		
		HashMap header = signInAndGetSessionHeader(userName, password);
		log.info("Cleaning the Cart Entries Started");
		
		svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.CLEAR_CART, null,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.DELETE, null,
				header);
		
		log.info("Cleaning the Cart Entries Completed");

		for (String skuAndQuantity : skuAndQuantities) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String skuId = skuAndQuantitySplit[0];
			String quantity = skuAndQuantitySplit[1];
			String payload = "{\"skuId\": "+skuId+",\"quantity\":"+ quantity +",\"operation\": \"ADD\"}";
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.ADD_ITEMTO_CART, null ,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
					header);
			
			end2EndHelper.sleep(5L);
		}

		if ((null != couponId) && (!couponId.equals(""))) {
			log.info("Applying Coupon to CART");
			
			String payload = "{\"coupon\": \""+couponId+"\"}";
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.APPLY_COUPON, null ,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
					header);
			
			log.info("Coupon Response:"
					+ svc.getResponseBody());
		}

		if (isGiftWrap) {
			log.info("Applying Giftwrap");
			
			String payload = "{\"sender\": \"Abhijit\",\"message\": \"Happy BirthDay\",\"recipient\": \"OMS Automation\"}";
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.ADD_GIFTWRAPANDMESSAGE, null ,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
					header);
			
			log.info("GiftWrap Response:"
					+ svc.getResponseBody());
		}

		if (isTryNBuy) {
			log.info("Applying TrynBuy in Cart : " + isTryNBuy);
			String tnbSingle = "";

            Integer[] itemIds = getItemIdsFromSKUIds(svc.getResponseBody());

            for (Integer itemId:itemIds) {
                tnbSingle = "{\"itemId\":" + itemId
                        + ",\"tryAndBuyOpted\":true}" + "," + tnbSingle;
            }

			tnbSingle = "[" + tnbSingle.substring(0, tnbSingle.length() - 1)
					+ "]";
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.OPTTRYNBUY, null,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, tnbSingle,
					header);
			log.info("try and buy service :"
					+ svc.getResponseBody());
		}

		if (loyalityPoint) {
			log.info("Applying Loyality for Amount : " + loyalityPoint);
			String payload = "{\"usePoints\":1}"; 
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.APPLY_LOYALTYPOINTS, null,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
					header);

			log.info("Loyalty Response:"
					+ svc.getResponseBody());
		}

		if (cashBack) {
			log.info("Applying CashBack for Amount : " + cashBack);
			String payload = "{\"useAmount\": 1}";
			svc = HttpExecutorService.executeHttpService(
					Constants.CART_PATH.APPLY_CASHBACK, null,
					SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
					header);
			
			log.info("CashBack Response:"
					+ svc.getResponseBody());
		}
		return svc;
	}

	public void clearCart(HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.CLEAR_CART, null,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.DELETE, null,
				header);
		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Delete Cart"+ svc.getResponseStatus());
		}
	}

	private Svc addItemToCart(long skuID, int quantity, HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String payload = "{\"skuId\": "+skuID+",\"quantity\":"+ quantity +",\"operation\": \"ADD\"}";
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.ADD_ITEMTO_CART, null ,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Add Item Cart"+ svc.getResponseStatus());
		}
		return svc;
	}

	private Svc applyCouponOnCart(String couponCode, HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String payload = "{\"coupon\": \""+ couponCode +"\"}";
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.APPLY_COUPON, null ,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to add coupon to Cart"+ svc.getResponseStatus());
		}
		return svc;
	}

	private Svc applyGiftWrapOnCart(HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String payload = "{\"sender\": \"Abhijit\",\"message\": \"Happy BirthDay\",\"recipient\": \"OMS Automation\"}";
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.ADD_GIFTWRAPANDMESSAGE, null ,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to add Gift Wrap message"+ svc.getResponseStatus());
		}
		return svc;
	}

	private Svc optForTryNBuy(String cartResponse, HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String tnbSingle = "";
		Integer[] itemIds = getItemIdsFromSKUIds(cartResponse);


		for (Integer itemId:itemIds) {
			tnbSingle = "{\"itemId\":" + itemId
					+ ",\"tryAndBuyOpted\":true}" + "," + tnbSingle;
		}

		tnbSingle = "[" + tnbSingle.substring(0, tnbSingle.length() - 1)
				+ "]";

		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.OPTTRYNBUY, null,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, tnbSingle,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Opt Try N Buy "+ svc.getResponseStatus());
		}
		return svc;
	}

	private Svc optForLoyaltyPoint(HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String payload = "{\"usePoints\":1}";
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.APPLY_LOYALTYPOINTS, null,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Opt For Loyalty Point " + svc.getResponseStatus());
		}
		return svc;
	}

	private Svc optForWalletPoint(HashMap header) throws UnsupportedEncodingException, SCMExceptions {
		String payload = "{\"useAmount\": 1}";
		Svc svc = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.APPLY_CASHBACK, null,
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.PUT, payload,
				header);

		if(svc.getResponseStatus() != 200){
			throw new SCMExceptions("Unable to Opt For Wallet Point " + svc.getResponseStatus());
		}
		return svc;
	}

	/**
	 * To getXID for MultipleItems added to cart
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidSyntaxException
	 */
	public Svc getXIDForMultipleItemToCart(CreateOrderEntry createOrderEntry) throws JAXBException,
			UnsupportedEncodingException, InvalidSyntaxException, SCMExceptions {

		String userName = createOrderEntry.getUserName();
		String password = createOrderEntry.getPassword();
		End2EndHelper end2EndHelper = new End2EndHelper();
		Svc svc = null;

		HashMap header = signInAndGetSessionHeader(userName, password);
		log.info("Cleaning the Cart Entries Started");
		clearCart(header);

		log.debug("Adding Items to Cart");
		for (SkuEntry skuEntry : createOrderEntry.getSkuEntries()) {
			svc = addItemToCart(Long.parseLong(skuEntry.getSkuId()), skuEntry.getQuantity(), header);
			end2EndHelper.sleep(5);
		}


		if(createOrderEntry.getCouponCode() != null && !createOrderEntry.getCouponCode().equals("")){
			log.debug("Applying Coupon on CART");
			svc = applyCouponOnCart(createOrderEntry.getCouponCode(), header);
		}


		if(createOrderEntry.isGiftWrapEnabled()){
			log.debug("Applying Gift Wrap");
			svc = applyGiftWrapOnCart(header);
		}

		log.debug("Opt For Try N Buy "+ createOrderEntry.getShipmentType().name());
		if(createOrderEntry.getShipmentType() == ShipmentType.TRY_AND_BUY){
			svc = optForTryNBuy(svc.getResponseBody(), header);
		}

		log.debug("Opt For Loyalty Point " + createOrderEntry.getPaymentInstruments().isUseLoyaltyPoints());
		if(createOrderEntry.getPaymentInstruments().isUseLoyaltyPoints()){
			svc = optForLoyaltyPoint(header);
		}

		log.debug("Opt For Loyalty Point " + createOrderEntry.getPaymentInstruments().isUseWallet());
		if(createOrderEntry.getPaymentInstruments().isUseWallet()){
			svc = optForWalletPoint(header);
		}

		return svc;
	}

	public Svc getXIDForMultipleItemToCartAutoForGiftCard(
			String userName, String password, String[] skuAndQuantities,
			String sender, String message, String recipient,
			String recipientEmailId) throws JAXBException,
            UnsupportedEncodingException, InvalidSyntaxException {

        Svc svc=null;
        HashMap header = signInAndGetSessionHeader(userName, password);
		log.info("Cleaning the Cart Entries Started");
		MyntraService deleteCart = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.CLEARCART, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(deleteCart, header);
		log.info("Cleaning the Cart Entries Completed");

		for (String skuAndQuantity : skuAndQuantities) {
			String[] skuAndQuantitySplit = skuAndQuantity.split(":");
			String skuId = skuAndQuantitySplit[0];
			String quantity = skuAndQuantitySplit[1];
			
			addGiftCardItemToCart(skuId,quantity);
			svc = saveGiftCardInfo(sender,message,recipient,recipientEmailId);
		}

		return svc;
	}
	
	public Svc addGiftCardItemToCart(String Skuid,String quantity) throws UnsupportedEncodingException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
	    String payload = "{\"skuId\":"+Skuid+",\"quantity\":"+quantity+",\"operation\":\"ADD\"}";
	    Svc svc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.CREATE_GIFTCARD_ORDER, null, SERVICE_TYPE.ABSOLUT_SVC.toString(), HTTPMethods.PUT, payload, headers);
	    return svc;
	}
	
	public Svc saveGiftCardInfo(String sender,String message,String recipient,String recipientEmailId) throws UnsupportedEncodingException{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
	   String payload = "{\"sender\":\""+sender+"\",\"message\":\""+message+"\",\"recipient\":\""+recipient+"\",\"recipientEmailId\":\""+recipientEmailId+"\"}";
	   Svc svc = HttpExecutorService.executeHttpService(Constants.OMS_PATH.SAVE_GIFTCARD_INFO, null, SERVICE_TYPE.ABSOLUT_SVC.toString(), HTTPMethods.PUT, payload, headers);
	   return svc;
	}

	public String getxID(String userName, String password,
			String[] skuAndQuantities, String operation, String couponId,
			String cashBack, String loyalityPoint, String giftWrap) {
		RequestGenerator requestGenerator = getXIDForMultipleItemToCart(
				userName, password, skuAndQuantities, operation, couponId,
				cashBack, loyalityPoint, giftWrap);
		String sessionId = requestGenerator.response.getHeaderString(
				"Set-Cookie").split(";")[0].split("=")[1];
		log.info("\nPrinting sessionId : " + sessionId + "\n");
		return sessionId;
	}

	public String getxIDForCoupon(String userName, String password,
			String[] skuAndQuantities, String operation, String couponId,
			boolean cashBack, boolean loyalityPoint, boolean giftWrap)
            throws UnsupportedEncodingException, JAXBException, InvalidSyntaxException {
		RequestGenerator requestGenerator = getXIDForMultipleItemToCartAuto(
				userName, password, skuAndQuantities, couponId, cashBack,
				loyalityPoint, giftWrap, false);
		String sessionId = requestGenerator.response.getHeaderString(
				"Set-Cookie").split(";")[0].split("=")[1];
		log.info("\nPrinting sessionId : " + sessionId + "\n");
		return sessionId;
	}

	/**
	 * Get Cart Entry
	 *
	 * @param userName
	 * @param password
	 * @param skuAndQuantities
	 * @param operation
	 * @return
	 */
	public RequestGenerator getCartEntry(String userName, String password,
			String[] skuAndQuantities, String operation) {
		RequestGenerator requestGenerator = null;
		getXIDForMultipleItemToCart(userName, password, skuAndQuantities,
				operation, "", null, null, "");
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		headers.put("Accept", "application/xml");
		headers.put("Content-Type", "application/xml");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.GETCART, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		log.info("\nPrinting CheckoutService addItemToCart API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
				+ service.Payload);
		requestGenerator = new RequestGenerator(service, headers);
		return requestGenerator;
	}

	/**
	 * @author Shrinkhala This method is used to get the available Loyalty Points
	 * 
	 * @param user
	 * @return RequestGenerator
	 */
	public RequestGenerator getLoyaltyPoints(String user) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, user);
		System.out
				.println("\nPrinting CheckoutService getLoyaltyPoints API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService getLoyaltyPoints API URL : "
				+ service.URL);

		return new RequestGenerator(service);
	}

	/**
	 * @author Shrinkhala This method is used to remove the applied Loyalty
	 *         Points
	 * 
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator removeLoyaltyPoints(String user, String pass) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.REMOVELOYALTYPOINTS, init.Configurations);
		System.out
				.println("\nPrinting CheckoutService removeLoyaltyPoints API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService removeLoyaltyPoints API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * @author Shrinkhala This method is used to apply Loyalty Points
	 * 
	 * @param user
	 * @param pass
	 * @param points
	 * @return RequestGenerator
	 */
	public RequestGenerator applyLoyaltyPoints(String user, String pass,
			String points) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.APPLYLOYALTYPOINTS, init.Configurations,
				new String[] { points });
		System.out
				.println("\nPrinting CheckoutService applyLoyaltyPoints API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService applyLoyaltyPoints API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService applyLoyaltyPoints API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService applyLoyaltyPoints API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * Get Cart Entry with Coupon Applied
	 *
	 * @param userName
	 * @param password
	 * @param skuAndQuantities
	 * @param operation
	 * @param coupon
	 * @return
	 */
	public RequestGenerator getCartEntry(String userName, String password,
			String[] skuAndQuantities, String operation, String coupon) {
		RequestGenerator requestGenerator = null;
		getXIDForMultipleItemToCart(userName, password, skuAndQuantities,
				operation, coupon, null, null, "");
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		headers.put("Accept", "application/xml");
		headers.put("Content-Type", "application/xml");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.GETCART, init.Configurations, PayloadType.JSON,
				PayloadType.XML);
		log.info("\nPrinting CheckoutService addItemToCart API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService addItemToCart API Payload : \n\n"
				+ service.Payload);
		requestGenerator = new RequestGenerator(service, headers);
		return requestGenerator;
	}

	public static CartEntry getCart(String login, String cartContext,
			String sessionId) throws UnsupportedEncodingException,
			JAXBException {

		HashMap<String, String> requestMap = new HashMap<>();
		requestMap.put("xid", sessionId);
		requestMap.put("realTime", "true");
		requestMap.put("RETRY_TH", "1");
		requestMap.put("SO_TIMEOUT", "10000");

		Svc service = HttpExecutorService.executeHttpService(
				Constants.CART_PATH.VIEW_CART, new String[] { cartContext, "?rt=true" },
				SERVICE_TYPE.CART_SVC.toString(), HTTPMethods.GET, null, requestMap);
		CartResponse response = (CartResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(),
						new CartResponse());

		log.debug("Cart response : " + response.toString());
		log.debug("Returning data : "
				+ (response.getData() == null || response.getData().isEmpty() ? "NONE NONE"
						: response.getData().get(0).toString()));

		return response.getData() == null || response.getData().isEmpty() ? null
				: response.getData().get(0);
	}

	/**
	 * This method is used to fetch address based on pincode serviceablity
	 * 
	 * @param serviceName
	 * @param apiName
	 * @param userName
	 * @param password
	 * @param pinCode
	 * @return {@link RequestGenerator}
	 */
	public RequestGenerator fetchPincodeServicabilityAddress(
			ServiceType serviceName, APINAME apiName, String userName,
			String password, String pinCode) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(serviceName, apiName,
				init.Configurations, new String[] { pinCode });

		service.URL = apiUtil.prepareparameterizedURL(service.URL, pinCode);

		System.out
				.println("\nPrinting CheckoutService pincodeServiceabilityAddress API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService pincodeServiceabilityAddress API URL : "
				+ service.URL);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		service.responsedataformat = PayloadType.JSON;

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to place orders using PayNow PPS API
	 *
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */

	public RequestGenerator placeOrderUsingPayNowAPI(String user, String pass,
			String payload) {
		getAndSetTokens(user, pass);
		// System.out.println("amount is"+amount);
		//
		// String xId = ""+xID+"";
		System.out.println("Xid is" + xID);
		payload = payload.replace("${0}", xID);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
				APINAME.PAYNOW, init.Configurations, payload);
		System.out
				.println("\nPrinting CheckoutService PlaceOrdersUsingPayNow API URL : "
						+ service.URL);
		log.info("\nPrinting CheckoutService PlaceOrdersUsingPayNow API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService PlaceOrdersUsingPayNow API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService PlaceOrdersUsingPayNow API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	/**
	 * This method is used to place orders using PayNow PPS API
	 * 
	 * @param ppsID
	 * @return RequestGenerator
	 */

	public RequestGenerator getPaymentPlan(String ppsID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PPS,
				APINAME.GETPAYMENTPLAN, init.Configurations,
				new String[] {ppsID}, PayloadType.JSON, PayloadType.JSON);
		System.out
				.println("\nPrinting CheckoutService getPaymentPlan API URL :"
						+ service.URL);
		log.info("\nPrinting CheckoutService getPaymentPlan API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService getPaymentPlan API Payload :\n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService getPaymentPlan API Payload : \n\n"
				+ service.Payload);

		return new RequestGenerator(service);
	}

	/**
	 * @author Shrinkhala This method is used to Opt TryNBuy On Delivery
	 * 
	 * @param user
	 * @param pass
	 * @return RequestGenerator
	 */
	public RequestGenerator optTryNBuyOnDelivery(String user, String pass,
			String itemID) {
		getAndSetTokens(user, pass);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT,
				APINAME.OPTTRYANDBUY, init.Configurations,
				new String[] { itemID });
		System.out.println("\nPrinting CheckoutService optTryAndBuy API URL : "
				+ service.URL);
		log.info("\nPrinting CheckoutService optTryAndBuy API URL : "
				+ service.URL);

		System.out
				.println("\nPrinting CheckoutService optTryAndBuy API Payload : \n\n"
						+ service.Payload);
		log.info("\nPrinting CheckoutService optTryAndBuy API Payload : \n\n"
				+ service.Payload);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);

		return new RequestGenerator(service, headers);
	}

	
	public JSONObject getDefaultAddress(String user,String password) throws JSONException {
		RequestGenerator req= getAllAddress(user, password);
		String allAddress=req.respvalidate.NodeValue("data", true).trim();
		//allAddress=allAddress.replace("[", "").replace("]", "");
		System.out.println("allAddress is : "+allAddress);
		org.json.JSONArray jsonArray=new org.json.JSONArray(allAddress);
		JSONObject jsonObject=null;
		System.out.println("Lenght of the array is : "+jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject=(JSONObject) jsonArray.get(i);
			if((boolean) jsonObject.get("defaultAddress"))
				return jsonObject;
		}
		return null;
	}
	
	// Extract xid from idp login API
	public String getXID(String email,String password)
	{
		MyntraService service_login = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations,new String[] { email,
				password });
        RequestGenerator req_signin=new RequestGenerator(service_login);
		String xid="";
        MultivaluedMap<String, Object> map = req_signin.response.getHeaders();
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xid = entry.getValue().toString();
		}
		
		 xid = xid.substring((xid.indexOf("[") + 1), xid.lastIndexOf("]"));
		return xid;
	}
	
	// Extract sxid and csrf token from get session API
	public String[] getSxidAndUserToken(String xid)
	{
		String[] result = new String[2];
        MyntraService service_getSession = Myntra.getService(ServiceType.PORTAL_SESSIONNEW,APINAME.GETNEWSESSION,init.Configurations,PayloadType.JSON, new String[]{xid}, PayloadType.JSON);
        System.out.println(service_getSession.URL);
        RequestGenerator req_session=new RequestGenerator(service_getSession);
        result[0] = req_session.respvalidate.NodeValue("USER_TOKEN", false);
        result[1] = req_session.respvalidate.NodeValue("sxid", false);
		return result;
        
	}
	
	public RequestGenerator getCartPage(String xid,String host,String referer)
	{
		String value_cart = "fox-xid="+xid; 
    	System.out.println("value1:"+value_cart);
    	System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    	HashMap<String,String>headerCart = new HashMap<String,String>();
    	headerCart.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:51.0) Gecko/20100101 Firefox/51.0");
    	headerCart.put("Host", host);
    	headerCart.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	headerCart.put("Accept-Language", "en-US,en;q=0.5");
    	headerCart.put("Cookie", value_cart);
    	headerCart.put("Referer", referer);
		MyntraService service_cart = Myntra.getService(ServiceType.PORTAL_UI, APINAME.GETCARTPAGE, init.Configurations,PayloadType.XML,PayloadType.HTML);
        return(new RequestGenerator (service_cart,headerCart));
	}
	
	
	public static void main(String[] args) {
		CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
		RequestGenerator req = checkoutTestsHelper.getAllAddress(
				"TestAutomation_205164@myntra.com", "123456");
		System.out.println(req.returnresponseasstring());
	}


	public RequestGenerator getAddressPage(String xid, String sxid, String host,String referer) {
		String value_add = "fox-xid="+xid +"; fox-sxid="+sxid;
    	System.out.println("value1:"+value_add);
    	System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    	HashMap<String,String>header_fetchAddress = new HashMap<String,String>();
        header_fetchAddress.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:51.0) Gecko/20100101 Firefox/51.0");
        header_fetchAddress.put("Host", host);
        header_fetchAddress.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header_fetchAddress.put("Accept-Language", "en-US,en;q=0.5");
        header_fetchAddress.put("Cookie", value_add);
        header_fetchAddress.put("Referer", referer);
		MyntraService service_address = Myntra.getService(ServiceType.PORTAL_UI, APINAME.GETADDRESSPAGE, init.Configurations,PayloadType.XML,PayloadType.HTML);
		return(new RequestGenerator (service_address,header_fetchAddress));
	}


	public String getSessionDetails(String xid) {
		MyntraService service_fetchSession = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.GETNEWSESSION, init.Configurations,PayloadType.JSON,new String [] {xid},PayloadType.JSON);
        RequestGenerator req_fetchSession = new RequestGenerator (service_fetchSession);
        String response_fetchSession = req_fetchSession.respvalidate.returnresponseasstring();
        System.out.println("Get Session response="+response_fetchSession);
        return response_fetchSession;
	}
	
	
	public String updateSessionDetails(String xid,String newResponse) {
		MyntraService service_updatesession = Myntra.getService(ServiceType.PORTAL_SESSIONNEW, APINAME.SAVESESSIONINFO, init.Configurations,new String [] {xid},newResponse);
		System.out.println(service_updatesession.URL);
        RequestGenerator req_updateSession = new RequestGenerator (service_updatesession);
        String response_sessionU = req_updateSession.respvalidate.returnresponseasstring();
        System.out.println("Update Session response="+response_sessionU);
        return response_sessionU;
	}


	public RequestGenerator operationFetchCartContext(String userName, String password,String context) {
		getAndSetTokens(userName, password);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_ABSOLUT, APINAME.FETCHCARTCONTEXT, init.Configurations, PayloadType.JSON, new String[] {context}, PayloadType.JSON);
	    System.out.println("\nPrinting CheckoutService operationFetchCart API URL : "+ service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xID);
		return new RequestGenerator(service, headers);
	}
	

}
