package com.myntra.apiTests.portalservices.devapiservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceConstants;
import org.apache.log4j.Logger;

import com.eaio.uuid.UUID;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import net.minidev.json.JSONArray;
import com.myntra.apiTests.common.Myntra;

/**
 * @author shankara.c
 *
 */
public class DevApiServiceHelper extends CommonUtils implements NotificationServiceConstants
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApiServiceHelper.class);
	static String xId, sXid, uidx;
	public static String ppid;
	APIUtilities apiUtil=new APIUtilities();
	static String EncryptKey = "myntramobileapps";
	
	
	
	/**
	 * This method is used to invoke DevApiService signIn API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiSignIn(String userName, String password)
	{
		MyntraService devApiSignInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName, password });
		
		System.out.println("\nPrinting DevApiService SignIn API URL : "+devApiSignInService.URL);
		log.info("\nPrinting DevApiService SignIn API URL : "+devApiSignInService.URL);
		
		System.out.println("\nPrinting DevApiService SignIn API Payload : \n\n"+devApiSignInService.Payload);
		log.info("\nPrinting DevApiService SignIn API Payload : \n\n"+devApiSignInService.Payload);
		
		return new RequestGenerator(devApiSignInService);
	}
	
/**
	 * This method is used to invoke DevApiService signUp API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiSignUp(String userName, String password)
	{
		MyntraService devApiSignUpService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNUP, init.Configurations, new String[] { userName, password });
		
		System.out.println("\nPrinting DevApiService SignUp API URL : "+devApiSignUpService.URL);
		log.info("\nPrinting DevApiService SignUp API URL : "+devApiSignUpService.URL);
		
		System.out.println("\nPrinting DevApiService SignUp API Payload : \n\n"+devApiSignUpService.Payload);
		log.info("\nPrinting DevApiService SignUp API Payload : \n\n"+devApiSignUpService.Payload);
		
		return new RequestGenerator(devApiSignUpService);
	}
	
	/**
	 * This method is used to invoke DevApiService refresh API
	 * 
	 * @param at
	 * @param rt
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiRefresh(String at, String rt)
	{
		MyntraService devApiRefreshService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPIREFRESH, init.Configurations, new String[] { at, rt });
		
		System.out.println("\nPrinting DevApiService refresh API URL : "+devApiRefreshService.URL);
		log.info("\nPrinting DevApiService refresh API URL : "+devApiRefreshService.URL);
		
		System.out.println("\nPrinting DevApiService refresh API Payload : \n\n"+devApiRefreshService.Payload);
		log.info("\nPrinting DevApiService refresh API Payload : \n\n"+devApiRefreshService.Payload);
		
		return new RequestGenerator(devApiRefreshService);
	}
	
	/**
	 * This method is used to invoke DevApiService forgotPassword API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiSignOut(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService devApiSignOutService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPILOGOUT, init.Configurations);
		
		HashMap<String, String> devApiSignOutHeaders = new HashMap<String, String>();
		devApiSignOutHeaders.put("xsrf", sXid);
		devApiSignOutHeaders.put("xid", xId);
		
		System.out.println("HEADERS : \nXID : "+xId+"\nXSRF : "+sXid);
		System.out.println("\nPrinting DevApiService signOut API URL : "+devApiSignOutService.URL);
		log.info("\nPrinting DevApiService signOut API URL : "+devApiSignOutService.URL);
		
		System.out.println("\nPrinting DevApiService signOut API Payload : \n\n"+devApiSignOutService.Payload);
		log.info("\nPrinting DevApiService signOut API Payload : \n\n"+devApiSignOutService.Payload);
		
		return new RequestGenerator(devApiSignOutService, devApiSignOutHeaders);
	}
	
	/**
	 * This method is used to invoke DevApiService forgotPassword API
	 * 
	 * @param userName
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiForgotPassword(String userName)
	{
		MyntraService devApiForgotPasswordService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPIFORGOTPASSWORD, init.Configurations,
				new String[]{ userName });
		
		System.out.println("\nPrinting DevApiService forgotPassword API URL : "+devApiForgotPasswordService.URL);
		log.info("\nPrinting DevApiService forgotPassword API URL : "+devApiForgotPasswordService.URL);
		
		System.out.println("\nPrinting DevApiService forgotPassword API Payload : \n\n"+devApiForgotPasswordService.Payload);
		log.info("\nPrinting DevApiService forgotPassword API Payload : \n\n"+devApiForgotPasswordService.Payload);
		
		return new RequestGenerator(devApiForgotPasswordService);
	}
	
	/**
	 * This method is used to invoke DevApiService nav API
	 * 
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiNav()
	{
		MyntraService devApiNavService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPINAV, init.Configurations);
		
		System.out.println("\nPrinting DevApiService nav API URL : "+devApiNavService.URL);
		log.info("\nPrinting DevApiService nav API URL : "+devApiNavService.URL);
		
		System.out.println("\nPrinting DevApiService nav API Payload : \n\n"+devApiNavService.Payload);
		log.info("\nPrinting DevApiService nav API Payload : \n\n"+devApiNavService.Payload);
		
		return new RequestGenerator(devApiNavService);
	}
	
	/**
	 * This method is used to invoke DevApiService style API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiStyle(String styleId)
	{
		MyntraService devApiStyleService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations, new String[]{ styleId });
		devApiStyleService.URL = new APIUtilities().prepareparameterizedURL(devApiStyleService.URL, styleId);
		
		System.out.println("\nPrinting DevApiService style API URL : "+devApiStyleService.URL);
		log.info("\nPrinting DevApiService style API URL : "+devApiStyleService.URL);
		
		System.out.println("\nPrinting DevApiService style API Payload : \n\n"+devApiStyleService.Payload);
		log.info("\nPrinting DevApiService style API Payload : \n\n"+devApiStyleService.Payload);
		
		return new RequestGenerator(devApiStyleService);
	}
	
	
	/**
	 * This method is used to invoke DevApiService styleOffers API
	 * 
	 * @param styleId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiStyleOffers(String styleId)
	{
		MyntraService devApiStyleOffersService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLEOFFERS, init.Configurations, new String[] { styleId });
		devApiStyleOffersService.URL = new APIUtilities().prepareparameterizedURL(devApiStyleOffersService.URL, styleId);
		
		System.out.println("\nPrinting DevApiService styleOffers API URL : "+devApiStyleOffersService.URL);
		log.info("\nPrinting DevApiService styleOffers API URL : "+devApiStyleOffersService.URL);
		
		System.out.println("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleOffersService.Payload);
		log.info("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleOffersService.Payload);
		
		return new RequestGenerator(devApiStyleOffersService);
	}
	
	/**
	 * This method is used to invoke DevApiService search API
	 * 
	 * @param searchString
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiSearch(String searchString)
	{
		MyntraService devApiSearchService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISEARCH, init.Configurations, null, new String[] { searchString });
		
		System.out.println("\nPrinting DevApiService search API URL : "+devApiSearchService.URL);
		log.info("\nPrinting DevApiService search API URL : "+devApiSearchService.URL);
		
		System.out.println("\nPrinting DevApiService search API Payload : \n\n"+devApiSearchService.Payload);
		log.info("\nPrinting DevApiService search API Payload : \n\n"+devApiSearchService.Payload);
		
		return new RequestGenerator(devApiSearchService);
	}
	
	/**
	 * This method is used to invoke DevApiService searchWithFacets API
	 * 
	 * @param searchString
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiSearchWithFacets(String searchString)
	{
		MyntraService devApiSearchWithFacetsService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISEARCHWITHFACETS, init.Configurations, null,
				new String[] { searchString });
		
		System.out.println("\nPrinting DevApiService searchWithFacets API URL : "+devApiSearchWithFacetsService.URL);
		log.info("\nPrinting DevApiService searchWithFacets API URL : "+devApiSearchWithFacetsService.URL);
		
		System.out.println("\nPrinting DevApiService searchWithFacets API Payload : \n\n"+devApiSearchWithFacetsService.Payload);
		log.info("\nPrinting DevApiService searchWithFacets API Payload : \n\n"+devApiSearchWithFacetsService.Payload);
		
		return new RequestGenerator(devApiSearchWithFacetsService);
	}
	
	
	/**
	 * This method is used to invoke DevApiService styleWithServicability API
	 * 
	 * @param searchString
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiStyleWithServicability(String searchString)
	{
		MyntraService devApiStyleWithServicabilityService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLESERVICABILITY, init.Configurations, null,
				new String[] { searchString });
		
		System.out.println("\nPrinting DevApiService styleWithServicability API URL : "+devApiStyleWithServicabilityService.URL);
		log.info("\nPrinting DevApiService StyleWithServicability API URL : "+devApiStyleWithServicabilityService.URL);
		
		System.out.println("\nPrinting DevApiService styleWithServicability API Payload : \n\n"+devApiStyleWithServicabilityService.Payload);
		log.info("\nPrinting DevApiService styleWithServicability API Payload : \n\n"+devApiStyleWithServicabilityService.Payload);
		
		return new RequestGenerator(devApiStyleWithServicabilityService);
	}
	
	
	/**
	 * This method is used to clear the cart
	 * 
	 * @param userName
	 * @param password
	 * @return NA
	 */
	
	public void userClearCart(String username,String password)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting CheckoutService clearCart API URL : "+service.URL);
		log.info("\nPrinting CheckoutService clearCart API URL : "+service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xId);
		RequestGenerator clearCartReqGen =new RequestGenerator(service, headers);
		System.out.println("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartReqGen.respvalidate.returnresponseasstring());
		log.info("\nPrinting CheckoutService clearCart API response :\n\n"+clearCartReqGen.respvalidate.returnresponseasstring());
	}
	
	public RequestGenerator invokeClearCartService(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.CLEARCART, init.Configurations);
		System.out.println("\nPrinting CheckoutService clearCart API URL : "+service.URL);
		log.info("\nPrinting CheckoutService clearCart API URL : "+service.URL);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	/**
	 * This method is used to invoke DevApiService addItemToCart API
	 * 
	 * @param userName
	 * @param password
	 * @param skuId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiAddToCart(String userName, String password, String skuId)
	{
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService devApiAddToCartService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIADDTOCART, init.Configurations, new String[]{ skuId });
		
		HashMap<String, String> devApiAddToCartHeaders = new HashMap<String, String>();
		devApiAddToCartHeaders.put("xsrf", sXid);
		devApiAddToCartHeaders.put("xid", xId);
		
		System.out.println("\nPrinting DevApiService addToCart API URL : "+devApiAddToCartService.URL);
		log.info("\nPrinting DevApiService addToCart API URL : "+devApiAddToCartService.URL);
		
		System.out.println("\nPrinting DevApiService addToCart API Payload : \n\n"+devApiAddToCartService.Payload);
		log.info("\nPrinting DevApiService addToCart API Payload : \n\n"+devApiAddToCartService.Payload);
		
		return new RequestGenerator(devApiAddToCartService, devApiAddToCartHeaders);
	}
	
	/**
	 * This method is used to invoke DevApiService addItemToCart API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiGetCart(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService devApiGetCartService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETCART, init.Configurations);
		
		HashMap<String, String> devApiGetCartHeaders = new HashMap<String, String>();
		devApiGetCartHeaders.put("xsrf", sXid);
		devApiGetCartHeaders.put("xid", xId);
		
		System.out.println("\nPrinting DevApiService getCart API URL : "+devApiGetCartService.URL);
		log.info("\nPrinting DevApiService getCart API URL : "+devApiGetCartService.URL);
		
		return new RequestGenerator(devApiGetCartService, devApiGetCartHeaders);
	}
	
	/**
	 * This method is used to invoke DevApiService addItemToWishlist API
	 * 
	 * @param userName
	 * @param password
	 * @param skuId
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiAddToWishlist(String userName, String password, String skuId)
	{
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService devApiAddToWishlistService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIADDTOWISHLIST, init.Configurations, new String[]{ skuId });
		
		HashMap<String, String> devApiAddToWishlistHeaders = new HashMap<String, String>();
		devApiAddToWishlistHeaders.put("xsrf", sXid);
		devApiAddToWishlistHeaders.put("xid", xId);
		
		System.out.println("\nPrinting DevApiService addToWishlist API URL : "+devApiAddToWishlistService.URL);
		log.info("\nPrinting DevApiService addToWishlist API URL : "+devApiAddToWishlistService.URL);
		
		System.out.println("\nPrinting DevApiService addToWishlist API Payload : \n\n"+devApiAddToWishlistService.Payload);
		log.info("\nPrinting DevApiService addToWishlist API Payload : \n\n"+devApiAddToWishlistService.Payload);
		
		return new RequestGenerator(devApiAddToWishlistService, devApiAddToWishlistHeaders);
	}
	
	/**
	 * This method is used to invoke DevApiService getWishlist API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	public RequestGenerator invokeDevApiGetWishlist(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService devApiGetWishlistService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETWISHLIST, init.Configurations);
		
		HashMap<String, String> devApiGetWishlistHeaders = new HashMap<String, String>();
		devApiGetWishlistHeaders.put("xsrf", sXid);
		devApiGetWishlistHeaders.put("xid", xId);
		
		System.out.println("\nPrinting DevApiService getWishlist API URL : "+devApiGetWishlistService.URL);
		log.info("\nPrinting DevApiService getWishlist API URL : "+devApiGetWishlistService.URL);
		
		return new RequestGenerator(devApiGetWishlistService, devApiGetWishlistHeaders);
	}
	
	
	private void getAndSetTokens(String userName, String password)
	{
		System.out.println("User name :" + userName + "  Pass: " + password);
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,
				APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName,
						password });
		System.out.println(serviceSignIn.URL);
		log.info(serviceSignIn.URL);
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println("Response GET and SET:"+ reqSignIn.response.toString());
		System.out.println(reqSignIn.response.getHeaderString("sxid"));
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xId = entry.getValue().toString();
			}
		
		String Response = reqSignIn.respvalidate.returnresponseasstring();
		System.out.println(Response);
		uidx = JsonPath.read(Response, "$.data.profile.uidx");
		System.out.println("UIDX from Response  : " + uidx);
		System.out.println("xID from Headers  : " + xId);
		xId = JsonPath.read(Response, "$.meta.token");
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		System.out.println("sXid from Response  : " + sXid);
		if (sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));
		System.out.println("xID :" + xId);
		System.out.println("sxid : " + sXid);
		log.info(reqSignIn.response);
	}
	
	public HashMap<String,String> getXIDandSXidHeader(String userName, String password)
	{
		String xID = "", sXid="";
		HashMap<String, String> xidAndsxId=new HashMap<String, String>();
		System.out.println("\nPrinting \n Username : "+userName+" \n Password : "+password);
		log.info("\nPrinting \n Username :"+userName+" \n Password: "+password);
		
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[]{ userName, password });
		System.out.println("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		log.info("\nPrinting IDP Service API URL : "+serviceSignIn.URL);
		
		System.out.println("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		log.info("\nPrinting IDP Service API Payload : \n\n"+serviceSignIn.Payload);
		
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		System.out.println("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		log.info("\nPrinting IDP Service API response .....\n\n"+reqSignIn.respvalidate.returnresponseasstring());
		
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		System.out.println("\nPrinting xID from Headers  : "+xID);
		log.info("\nPrinting xID from Headers  : "+xID);
		
		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		
		System.out.println("\nPrinting final xID : "+xID);
		log.info("\nPrinting final xID : "+xID);
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		
		if(sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
		System.out.println("\nPrinting final sxid : " + sXid);
		log.info("\nPrinting final sxid : " + sXid);
		xidAndsxId.put("xid", xID);
		xidAndsxId.put("X-CSRF-Token", sXid);
		
		return xidAndsxId;
	}
	
	public RequestGenerator invokeGetProfileInfo(String username,
			String password) {
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,
				APINAME.GETPROFILEINFO, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		HashMap<String, String> devApiGetPrifileInfoHeaders = new HashMap<String, String>();
		devApiGetPrifileInfoHeaders.put("xsrf", sXid);
		devApiGetPrifileInfoHeaders.put("xid", xId);
		System.out.println("Printing getprofile url: " + service.URL);

		return new RequestGenerator(service, devApiGetPrifileInfoHeaders);
	}

	public RequestGenerator invokeDevApiGetCityList(String userName,
			String password, String city) {
		getAndSetTokensWithPPID(userName, password);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS, APINAME.CITYLIST,
				init.Configurations, PayloadType.JSON, PayloadType.JSON);

		HashMap<String, String> devApiGetWishlistHeaders = new HashMap<String, String>();
		devApiGetWishlistHeaders.put("xsrf", sXid);
		System.out.println("SXID USED:"+sXid);
		devApiGetWishlistHeaders.put("xid", xId);
		System.out.println("XID USED:"+xId);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, city);
		System.out.println("\nPrinting DevApiService Likelist API URL : "
				+ service.URL);
		log.info("\nPrinting DevApiService Likelist API URL : " + service.URL);

		return new RequestGenerator(service, devApiGetWishlistHeaders);
	}

	public RequestGenerator invokeDevApiLikeProducts(String username,
			String password, String styleIds) {
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,APINAME.LIKEPRODUCTS, init.Configurations,
				new String[] { styleIds }, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> devApiLikeProductsHeaders = new HashMap<String, String>();
		devApiLikeProductsHeaders.put("xsrf", sXid);
		devApiLikeProductsHeaders.put("xid", xId);
		System.out.println("Printing Like products url: " + service.URL);

		return new RequestGenerator(service, devApiLikeProductsHeaders);
	}

	public boolean verifyStyleIdsInLikeSummeryAfterUnlikeProduct(
			String username, String password, String styleId) {

		boolean flag = true;
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,APINAME.LIKESUMMERY, init.Configurations, new String[] {},PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> devApiLikeSummeryHeaders = new HashMap<String, String>();
		devApiLikeSummeryHeaders.put("xsrf", sXid);
		devApiLikeSummeryHeaders.put("xid", xId);
		RequestGenerator req = new RequestGenerator(service,
				devApiLikeSummeryHeaders);
		System.out.println("Like summery response code: "
				+ req.response.getStatus());
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println("[Response of like summery]\n" + jsonResponse);
		try {
			JSONArray styleIdArray = JsonPath.read(jsonResponse,
					"$.data.products");

			for (int i = 0; i < styleIdArray.size(); i++) {
				if (styleId.equals(styleIdArray.get(i).toString())) {
					flag = false;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error in like summery api\n" + e.getMessage());

		}
		return flag;

	}

	public boolean verifyStyleIdsInLikeSummery(String username,
			String password, String styleId) {

		boolean flag = false;
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,
				APINAME.LIKESUMMERY, init.Configurations, new String[] {},
				PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> devApiLikeSummeryHeaders = new HashMap<String, String>();
		devApiLikeSummeryHeaders.put("xsrf", sXid);
		devApiLikeSummeryHeaders.put("xid", xId);
		RequestGenerator req = new RequestGenerator(service,
				devApiLikeSummeryHeaders);
		System.out.println("Like summery response code: "
				+ req.response.getStatus());
		String jsonResponse = req.respvalidate.returnresponseasstring();
		try {
			JSONArray styleIdArray = JsonPath.read(jsonResponse,
					"$.data.products");

			for (int i = 0; i < styleIdArray.size(); i++) {
				if (styleId.equals(styleIdArray.get(i).toString())) {
					flag = true;
					System.out.println("Product code:"+styleIdArray.get(i)+" exists in like summery");
					break;
					
				}
			}
		} catch (Exception e) {
			System.out.println("Error in like summery api\n" + e.getMessage());

		}
		return flag;

	}

	public RequestGenerator invokeDevApiUnLikeProducts(String username,
			String password, String styleIds) {

		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,
				APINAME.UNLIKEPRODUCTS, init.Configurations,
				new String[] { styleIds }, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> devApiUnLikeProductsHeaders = new HashMap<String, String>();
		devApiUnLikeProductsHeaders.put("xsrf", sXid);
		devApiUnLikeProductsHeaders.put("xid", xId);
		System.out.println("Printing UnLike products url: " + service.URL);

		return new RequestGenerator(service, devApiUnLikeProductsHeaders);
	}

	public void likeProducts(String username, String password, String styleId) {

		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS,APINAME.LIKEPRODUCTS, init.Configurations,new String[] { styleId }, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> devApiLikeSummeryHeaders = new HashMap<String, String>();
		devApiLikeSummeryHeaders.put("xsrf", sXid);
		devApiLikeSummeryHeaders.put("xid", xId);
		RequestGenerator req = new RequestGenerator(service,devApiLikeSummeryHeaders);
		if (req.response.getStatus() == 200) {
			System.out.println("Like products response code: "	+ req.response.getStatus());
			System.out.println("Product code: " + styleId + " is liked");
		} else {
			System.out.println("Error in Like service: "+ req.response.getStatus());
		}
	}
	
	public boolean isProductCodeExists(String jsonResponse,String styleId){
		boolean flag=false;
		
		JSONArray productIds=JsonPath.read(jsonResponse, "$.data.products");
		for(int i=0;i<productIds.size();i++){
			if(productIds.get(i).toString().equals(styleId)){
				System.out.println("Product Code: "+productIds.get(i)+" exists");
				flag=true;
				break;
			}
		}
		return flag;
	}
	public RequestGenerator invokeDevApiGetLikeList(String userName, String password,String limit,String offset)
	{
		String[] params={limit,offset};
		getAndSetTokensWithPPID(userName, password);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS, APINAME.LIKELIST, init.Configurations,PayloadType.JSON,PayloadType.JSON);
		
		HashMap<String, String> devApiGetWishlistHeaders = new HashMap<String, String>();
		devApiGetWishlistHeaders.put("xsrf", sXid);
		devApiGetWishlistHeaders.put("xid", xId);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, params);
		System.out.println("\nPrinting DevApiService Likelist API URL : "+service.URL);
		log.info("\nPrinting DevApiService Likelist API URL : "+service.URL);
		
		return new RequestGenerator(service, devApiGetWishlistHeaders);
	}
	public RequestGenerator invokeDevApiStyleWithWidth(String styleId,String width)
	{
		String[] urlParams={styleId,width};
		MyntraService devApiStyleService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLEWITHWIDTH, init.Configurations, new String[] { styleId,width });
		devApiStyleService.URL = new APIUtilities().prepareparameterizedURL(devApiStyleService.URL, urlParams);
		
		System.out.println("\nPrinting DevApiService style service API URL : "+devApiStyleService.URL);
		log.info("\nPrinting DevApiService style Service API URL : "+devApiStyleService.URL);
		
		//System.out.println("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		//log.info("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		
		return new RequestGenerator(devApiStyleService);
	}

	public RequestGenerator invokeDevApiStyleWithWidthAndQuality(String styleId,String width,String quality)
	{
		String[] urlParams={styleId,width,quality};
		MyntraService devApiStyleService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations, new String[] { styleId });
		devApiStyleService.URL = new APIUtilities().prepareparameterizedURL(devApiStyleService.URL, urlParams);
		
		System.out.println("\nPrinting DevApiService style service API URL : "+devApiStyleService.URL);
		log.info("\nPrinting DevApiService style Service API URL : "+devApiStyleService.URL);
		
		//System.out.println("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		//log.info("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		
		return new RequestGenerator(devApiStyleService);
	}
	
	public RequestGenerator invokeDevApiStyleWithWidthAndHeight(String styleId,String width,String height)
	{
		String[] urlParams={styleId,width,height};
		MyntraService devApiStyleService = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISTYLE, init.Configurations, new String[] { styleId });
		devApiStyleService.URL = new APIUtilities().prepareparameterizedURL(devApiStyleService.URL, urlParams);
		
		System.out.println("\nPrinting DevApiService style service API URL : "+devApiStyleService.URL);
		log.info("\nPrinting DevApiService style Service API URL : "+devApiStyleService.URL);
		
		//System.out.println("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		//log.info("\nPrinting DevApiService styleOffers API Payload : \n\n"+devApiStyleService.Payload);
		
		return new RequestGenerator(devApiStyleService);
	}
	
	public boolean doesYoutubeVideoNodeExist(String response){
		boolean flag=false;
		try{
		JSONArray videoAlbumList=JsonPath.read(response, "$.data.styleVideoAlbumList[0].videos");
		System.out.println("Total video album list present: "+videoAlbumList.size());
			for( int i=0;i<videoAlbumList.size();i++){
				String videoHost=JsonPath.read(response, "$.data.styleVideoAlbumList[0].videos["+Integer.toString(i)+"].videoHost").toString();
				if(videoHost.toLowerCase().equals("youtube")){
					System.out.println(videoHost+" video tag found");
					flag=true;
					break;
				}
			}
		}catch(Exception e){
			System.out.println("No video album list present");
			flag=false;
		}
			return flag;
		}

	public RequestGenerator invokeEditProfile(String userName,String password,String name,String bio){
		getAndSetTokensWithPPID(userName, password);
		HashMap<String, String> tokens=new HashMap<String, String>();
		tokens.put("xid", xId);
		tokens.put("sxid", sXid);
		String saveProfilePayload=prepareSaveProfilePayloadUrlEncoded(name,bio);
		System.out.println("New Payload " +saveProfilePayload);
		MyntraService devApiProfileService = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS, APINAME.UPDATEPROFILE, init.Configurations, new String[] { saveProfilePayload },PayloadType.URLENCODED,PayloadType.JSON);
		System.out.println("New URL : "+devApiProfileService.URL);
		System.out.println("Printing devapi save profile url"+devApiProfileService.URL);
		System.out.println(devApiProfileService.Payload);
		
		return new RequestGenerator(devApiProfileService,tokens);
	}
	
	public String invokeCreateNotificationsForUser(String userId)
	{
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		List<String>  notificationTypes=getNotificationTypes();
		List<String> portalGroups=getPortalGroups();
		String createNotificationForUserPayload = prepareCreateNotificationForUserPayloadURLEncoded(TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				startTime, endTime, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE,
				String.valueOf(new UUID()), notificationTypes.get(0));
		
		MyntraService createNotificationForUserService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.CREATENOTIFICATIONFORUSER, init.Configurations,
				new String[] { createNotificationForUserPayload }, new String[] { userId, portalGroups.get(0) }, PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		log.info("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		log.info("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		
		RequestGenerator req=new RequestGenerator(createNotificationForUserService);
		String createNotificationForUserResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		
		return createNotificationForUserResponse;
	}
	
	public List<String> invokeCreateNotificationsForUserAndGetIds(String userId,String path)
	{
		List<String> id=new ArrayList<String>();
		Calendar startTimeCal = Calendar.getInstance();
		//startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		List<String>  notificationTypes=getNotificationTypes();
		List<String> portalGroups=getPortalGroups();
		String createNotificationForUserPayload = prepareCreateNotificationForUserPayloadURLEncoded(TITAN_PROD_NOTIFICATION_IMG_URL, TITAN_PROD_NOTIFICATION_PAGE_URL,
				startTime, endTime, TITAN_PROD_NOTIFICATION_TEXT, TITAN_PROD_NOTIFICATION_TITLE,
				String.valueOf(new UUID()), notificationTypes.get(0));
		
		MyntraService createNotificationForUserService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS, APINAME.CREATENOTIFICATIONFORUSER, init.Configurations,
				new String[] { createNotificationForUserPayload }, new String[] { userId, portalGroups.get(0) }, PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		log.info("\nPrinting NotificationService createNotificationForUser API URL :"+createNotificationForUserService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		log.info("\nPrinting NotificationService createNotificationForUser API Payload :\n\n"+createNotificationForUserService.Payload);
		
		RequestGenerator req=new RequestGenerator(createNotificationForUserService);
		String createNotificationForUserResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting NotificationService createNotificationForUser API response :\n\n"+createNotificationForUserResponse);
		id=JsonPath.read(createNotificationForUserResponse, path);
		
		return id;
	}
	
	
	public String prepareSaveProfilePayloadUrlEncoded(String name,String bio){
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("name=");
		encodedURL.append(name.trim());
		encodedURL.append("&");
		encodedURL.append("bio=");
		encodedURL.append(bio.trim());
		
		return encodedURL.toString();
	}
	
	private String prepareCreateNotificationForUserPayloadURLEncoded(String imageUrl, String pageUrl,
			 String startTime, String endTime, String notificationText, String notificationTitle, String notificationId, String notificationType)
	{
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("image-url=");
		encodedURL.append(imageUrl.trim());
		encodedURL.append("&");
		
		encodedURL.append("page-url=");
		encodedURL.append(pageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("start-time=");
		encodedURL.append(startTime.trim());
		encodedURL.append("&");

		encodedURL.append("end-time=");
		encodedURL.append(endTime.trim());
		encodedURL.append("&");

		encodedURL.append("notification-text=");
		encodedURL.append(notificationText.trim());
		encodedURL.append("&");

		encodedURL.append("notification-title=");
		encodedURL.append(notificationTitle.trim());
		encodedURL.append("&");

		encodedURL.append("notification-id=");
		encodedURL.append(notificationId.trim());
		encodedURL.append("&");
		
		encodedURL.append("notification-type=");
		encodedURL.append(notificationType.trim());
		
		return encodedURL.toString();
	}
	public List<String> getNotificationTypes()
	{
		List<String> notificationTypesList = new ArrayList<String>();
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_MARKETING);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_REVENUE_LABS);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_ORDER_STATUS_CHANGE);
		notificationTypesList.add(NotificationServiceConstants.NOTIFICATION_TYPE_PRICE_CHANGE);
		
		return notificationTypesList;
	}
	
	public List<String> getPortalGroups()
	{
		List<String> portalGroupsList = new ArrayList<String>();
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_MYNTRA);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_MY_URBAN_LOOK);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_GAP);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_PUMA);
		portalGroupsList.add(NotificationServiceConstants.PORTAL_GROUP_LEVIS);
		
		return portalGroupsList;
	}

	/*public List<String> getNotificationIds(String userName, String password) {
		List<String> notificationIds=new ArrayList<String>();
		HashMap<String, String> tokens = new HashMap<String, String>();
		tokens = getXIDandSXidHeader(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,APINAME.DEVAPIGETALLNOTIFICATIONS, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting DevApiService  GET Notifications API URL : "+ service.URL);
		RequestGenerator req = new RequestGenerator(service, tokens);

		log.info("\nPrinting DevApiService gett API GET Notifications : "
				+ service.URL);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		notificationIds=JsonPath.read(jsonResponse,"$..data.notifications.notification[*].id");
		
		return notificationIds;
	}*/
	
	public RequestGenerator invokeGetAllNotification(String userName, String password) {
		
		HashMap<String, String> tokens = new HashMap<String, String>();
		tokens = getXIDandSXidHeader(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,APINAME.DEVAPIGETALLNOTIFICATIONS, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("\nPrinting DevApiService  GET Notifications API URL : "+ service.URL);
		RequestGenerator req = new RequestGenerator(service, tokens);

		log.info("\nPrinting DevApiService gett API GET Notifications : "
				+ service.URL);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println("Printing GetAll Notifications:\n"+jsonResponse);
		
		//notificationIds=JsonPath.read(jsonResponse,"$..data.notifications.notification[*].masterNotificationId");
		
		return req;
	}
	
	public RequestGenerator invokeLikesCountService(String userName,String password,String styleId){
		
		getAndSetTokensWithPPID(userName, password);
		HashMap<String, String> likesCountHeader=new HashMap<String, String>();
		likesCountHeader.put("xid", xId);
		likesCountHeader.put("sxid", sXid);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISLIKESCOUNT,APINAME.LIKESCOUNT, init.Configurations,new String[] { styleId }, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Devapis likes count url:"+service.URL);
		System.out.println("Devapis likes count payload:"+service.Payload);
		RequestGenerator devapisLikesCountReq=new RequestGenerator(service, likesCountHeader);
		
		return devapisLikesCountReq;
	}
	
	public RequestGenerator invokeDevapiNavv2(){
		MyntraService service=Myntra.getService(ServiceType.PORTAL_DEVAPIS,APINAME.DEVAPINAVV2,init.Configurations,new String[]{},PayloadType.JSON,PayloadType.JSON);
		System.out.println("Devapis nav v2 url:"+service.URL);
		RequestGenerator devapiNav2Req=new RequestGenerator(service);
		return devapiNav2Req;
	}
	
	public void removeItemsFromWishList(String username,String password){
	
		RequestGenerator getWishListReq=invokeGetWishList(username,password);
		
			//System.out.println(getWishListReq.respvalidate.returnresponseasstring());
			String totalWishListCount=JsonPath.read(getWishListReq.respvalidate.returnresponseasstring(), "$.data.totalWishlistCount").toString();
			if(Integer.parseInt(totalWishListCount)>0){
				List<String> itemIds=JsonPath.read(getWishListReq.respvalidate.returnresponseasstring(), "$.data.wishListItemEntries[*].itemId");
				for(String itemId:itemIds){
					InvokeremoveItemFromWishList(username,password,itemId,"DELETE");
				}
				
			}else{
				System.out.println("Wishlist is empty");
			}
		
	}
	
	public RequestGenerator invokeGetWishList(String username,String password){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETWISHLIST, init.Configurations);
		System.out.println("\nPrinting CheckoutService operationFetchWishList API URL : "+service.URL);
		log.info("\nPrinting CheckoutService operationFetchWishList API URL : "+service.URL);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xId);
		
		return new RequestGenerator(service, headers);
	}
	public RequestGenerator InvokeremoveItemFromWishList(String userName, String password, String itemId, String operation) 
	{
		getAndSetTokens(userName, password);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.REMOVEITEMFROMWISHLIST, init.Configurations, new String[]{ itemId, operation });
		System.out.println("\nPrinting CheckoutService removeItemFromWishList API URL : "+service.URL);
		log.info("\nPrinting CheckoutService removeItemFromWishList API URL : "+service.URL);
		
		System.out.println("\nPrinting CheckoutService removeItemFromWishList API Payload : \n\n"+service.Payload);
		log.info("\nPrinting CheckoutService removeItemFromWishList API Payload : \n\n"+service.Payload);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-CSRF-Token", sXid);
		headers.put("xid", xId);
		
		return new RequestGenerator(service, headers);
	}
	
	public RequestGenerator createMasterNotification(){
		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		String createMasterNotificationPayload = prepareCreateMasterNotificationPayloadURLEncoded(FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE, 
				FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, publishTime, 
				startTime, endTime,  NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA);
		MyntraService createMasterNotificationService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,	APINAME.CREATEMASTERNOTIFICATION, init.Configurations,
				new String[] { createMasterNotificationPayload },
				PayloadType.URLENCODED, PayloadType.JSON);
		System.out.println("Master notification url:"+createMasterNotificationService.URL);
		RequestGenerator masterNotificationReq=new RequestGenerator(createMasterNotificationService);
		
		//String createMasterNotificationResponse = masterNotificationReq.ResponseValidations.GetResponseAsString();
		
		return  masterNotificationReq;
		
		
		
	}
	
	
	public RequestGenerator invokeCreateMasterNotification()
	{

		String publishTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.HOUR, 2);
		String startTime = String.valueOf(startTimeCal.getTimeInMillis());
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.add(Calendar.HOUR, 4);
		String endTime = String.valueOf(endTimeCal.getTimeInMillis());
		MyntraService createMasterNotification = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,APINAME.CREATEMASTERNOTIFICATION,init.Configurations,new String[] {FOSSIL_PROD_NOTIFICATION_TEXT, FOSSIL_PROD_NOTIFICATION_TITLE,
				FOSSIL_PROD_NOTIFICATION_IMG_URL, FOSSIL_PROD_NOTIFICATION_PAGE_URL, publishTime, startTime, endTime,  NOTIFICATION_TYPE_MARKETING, PORTAL_GROUP_MYNTRA});
		
		System.out.println("Create Notification Service URL : "+createMasterNotification.URL);
		System.out.println("Create Notification Service PAYLOAD : "+createMasterNotification.Payload);
		return new RequestGenerator(createMasterNotification);

		
	}
	
	
	public RequestGenerator invokeCreateNotificationsForUser(String userId, String portalGroup, String masterNotificationId)
	{
		MyntraService createNotificationsForSingleUserService = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONS,	APINAME.CREATENOTIFICATIONSINGLEUSER,
				init.Configurations,PayloadType.JSON, new String[]{ userId, portalGroup, masterNotificationId }, PayloadType.JSON);
		System.out.println("\nPrinting NotificationService createNotificationsForSingleUser API URL :"+createNotificationsForSingleUserService.URL);
		log.info("\nPrinting NotificationService createNotificationsForSingleUser API URL :"+createNotificationsForSingleUserService.URL);
		
		System.out.println("\nPrinting NotificationService createNotificationsForSingleUser API Payload :\n\n"+createNotificationsForSingleUserService.Payload);
		log.info("\nPrinting NotificationService createNotificationsForSingleUser API Payload :\n\n"+createNotificationsForSingleUserService.Payload);
		
		return new RequestGenerator(createNotificationsForSingleUserService);
	}
	
	private String prepareCreateMasterNotificationPayloadURLEncoded(String notificationText, String notificationTitle, String imageUrl, String pageUrl, String publishTime,
			String startTime, String endTime, String notificationType, String forPortalGroup)
	{
		StringBuffer encodedURL = new StringBuffer();
		encodedURL.append("notification-title=");
		encodedURL.append(notificationTitle.trim());
		encodedURL.append("&");

		encodedURL.append("publish-time=");
		encodedURL.append(publishTime.trim());
		encodedURL.append("&");

		encodedURL.append("start-time=");
		encodedURL.append(startTime.trim());
		encodedURL.append("&");
		
		encodedURL.append("end-time=");
		encodedURL.append(endTime.trim());
		encodedURL.append("&");

		encodedURL.append("notification-type=");
		encodedURL.append(notificationType.trim());
		encodedURL.append("&");

		encodedURL.append("page-url=");
		encodedURL.append(pageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("image-url=");
		encodedURL.append(imageUrl.trim());
		encodedURL.append("&");

		encodedURL.append("notification-text=");
		encodedURL.append(notificationText.trim());
		encodedURL.append("&");

		encodedURL.append("forPortalGroup=");
		encodedURL.append(forPortalGroup.trim());

		return encodedURL.toString();
	}
	
	/**
	 * This method is used to invoke LooksDevApiService GetAllStickers API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	
	public RequestGenerator invokeDevApiGetAllStickers(String userName, String password)
	{
		getAndSetTokens(userName, password);
		
		MyntraService devApiGetAllStickersService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, APINAME.GETALLSTICKERS, init.Configurations);
		
		HashMap<String, String> devApiGetAllStikcersHeaders = new HashMap<String, String>();
		devApiGetAllStikcersHeaders.put("xid", xId);
		System.out.println("\nPrinting Looks DevApiService getAllStickers API URL : "+devApiGetAllStickersService.URL);
		log.info("\nPrinting Looks DevApiService getAllStickers API URL : "+devApiGetAllStickersService.URL);
		
		return new RequestGenerator(devApiGetAllStickersService, devApiGetAllStikcersHeaders);
		
	}
	
	/**
	 * This method is used to invoke LooksDevApiService GetLookDetails API
	 * 
	 * @param userName
	 * @param password
	 * @param API
	 * @param byID - Look ID, Occasion ID, Remyx ID
	 * @return RequestGenerator
	 */
	
	public RequestGenerator invokeDevApiGetLookDetails(String userName, String password, APINAME ApitoUse, String byId)
	{
		getAndSetTokens(userName, password);
		
		MyntraService devApiGetLookDetailsService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, ApitoUse, init.Configurations);
		
		HashMap<String, String> devApiGetLookDetailsHeaders = new HashMap<String, String>();
		devApiGetLookDetailsHeaders.put("xid", xId);
		devApiGetLookDetailsService.URL = apiUtil.prepareparameterizedURL(devApiGetLookDetailsService.URL, byId);
		System.out.println("\nPrinting Looks DevApiService getLookDetails API URL : "+devApiGetLookDetailsService.URL);
		log.info("\nPrinting Looks DevApiService getLookDetails API URL : "+devApiGetLookDetailsService.URL);
		
		return new RequestGenerator(devApiGetLookDetailsService, devApiGetLookDetailsHeaders);
		
	}
	

	/**
	 * This method is used to invoke LooksDevApiService GetLooksForUser API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	
	public RequestGenerator invokeDevApiGetLooksForUser(String userName, String password)
	{
		getAndSetTokens(userName, password);
		
		MyntraService devApiGetLooksForUserService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, APINAME.GETLOOKDETAILS, init.Configurations);
		HashMap<String, String> devApiGetLooksForUserHeaders = new HashMap<String, String>();
		devApiGetLooksForUserHeaders.put("xid", xId);
		System.out.println("\nPrinting Looks DevApiService getLooksForUser API URL : "+devApiGetLooksForUserService.URL);
		log.info("\nPrinting Looks DevApiService getLooksForUser API URL : "+devApiGetLooksForUserService.URL);
		
		return new RequestGenerator(devApiGetLooksForUserService, devApiGetLooksForUserHeaders);
		
	}
	
	/**
	 * This method is used to invoke LooksDevApiService CreateLook API
	 * 
	 * @param userName
	 * @param password
	 * @param PayLoad Parameters
	 * @return RequestGenerator
	 */
	
	public RequestGenerator invokeDevApiCreateLook(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
	{
		getAndSetTokens(userName, password);
		MyntraService devApiLooksCreateLookService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, APINAME.CREATELOOK, init.Configurations, new String [] {title, description, isActive, isDraft, isRemyx});
		HashMap<String, String> devApiLooksCreateLookHeaders = new HashMap<String, String>();
		devApiLooksCreateLookHeaders.put("xid", xId);
		System.out.println("\nPrinting Looks DevApiService CreateLook API URL: "+devApiLooksCreateLookService.URL);
		log.info("\nPrinting Looks DevApiService CreateLook API URL: "+devApiLooksCreateLookService.URL);
		
		return new RequestGenerator(devApiLooksCreateLookService, devApiLooksCreateLookHeaders);
		
	}
	
	public RequestGenerator invokeDevApiUpdateLook(String userName, String password, String lookId, String title, String description, String isActive, String isDraft, String isRemyx)
	{
		getAndSetTokens(userName, password);
		MyntraService devApiLooksUpdateLookService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, APINAME.UPDATELOOK, init.Configurations, new String [] {title, description, isActive, isDraft, isRemyx});
		HashMap<String, String> devApiUpdateLookHeaders = new HashMap<String, String>();
		devApiUpdateLookHeaders.put("xid", xId);
		devApiLooksUpdateLookService.URL = apiUtil.prepareparameterizedURL(devApiLooksUpdateLookService.URL, lookId);
		System.out.println("\nPrinting Looks DevApiService UpdateLook API URL : "+devApiLooksUpdateLookService.URL);
		log.info("\nPrinting Looks DevApiService UpdateLook API URL : "+devApiLooksUpdateLookService.URL);
		
		return new RequestGenerator(devApiLooksUpdateLookService, devApiUpdateLookHeaders);
		
	}

	/**
	 * This method is used to invoke LooksDevApiService GetAllOccasions API
	 * 
	 * @param userName
	 * @param password
	 * @return RequestGenerator
	 */
	
	public RequestGenerator invokeDevApiGetAllOccasions(String userName, String password)
	{
		getAndSetTokens(userName, password);
		
		MyntraService devApiGetAllOccasionsService = Myntra.getService(ServiceType.PORTAL_DEVAPILOOKS, APINAME.GETALLOCCASIONS, init.Configurations);
		
		HashMap<String, String> devApiGetAllOccasionsHeaders = new HashMap<String, String>();
		devApiGetAllOccasionsHeaders.put("xid", xId);
		System.out.println("\nPrinting LGP Looks Service getAllOccasions API URL : "+devApiGetAllOccasionsService.URL);
		log.info("\nPrinting LGP Looks Service getAllOccasions API URL : "+devApiGetAllOccasionsService.URL);
		
		return new RequestGenerator(devApiGetAllOccasionsService, devApiGetAllOccasionsHeaders);
		
	}

	public RequestGenerator invokeGetAutoSuggestFeature(String userName, String password, String keyword)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETAUTOSUGGEST, init.Configurations);
		HashMap<String, String> headers = new HashMap<String, String>();
		service.URL = apiUtil.prepareparameterizedURL(service.URL, keyword);
		System.out.println("Get AutoSuggest Service URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetCartCount(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETCARTCOUNT,init.Configurations);
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("xid", xId);
		System.out.println("Get Cart Count URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetCartCountWithSessionCheck(String userName, String password, boolean tamperSession)
	{
		
		getAndSetTokensWithPPID(userName, password);
		String localXid = xId;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETCARTCOUNT,init.Configurations);
		if(tamperSession)
		{
			localXid = localXid+"qwerty";
		}
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("xid", localXid);
		System.out.println("Get Cart Count With session Check URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public void getAndSetTokensWithPPID(String userName, String password)
	{
		System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName, password });
		System.out.println("\nSign In Service URL : "+signInService.URL);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		RequestGenerator signInRequest = new RequestGenerator(signInService,headers);
		
		String Response = signInRequest.respvalidate.returnresponseasstring();
		System.out.println("\nSign In Response : "+Response);
		uidx = JsonPath.read(Response, "$.data.uidx").toString();
		xId = JsonPath.read(Response, "$.meta.token").toString();
		ppid= JsonPath.read(Response, "$.data.profile.publicProfileId");
		
		System.out.println("\nXID : "+xId);
		System.out.println("\nUIDX : "+uidx);
		System.out.println("\nPPID : "+ppid);
	}
	
	private void getAndSetTokens_NoIDP_Response(String userName, String password)
	{
		//System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { userName, password });
		//System.out.println("\nSign In Service URL : "+signInService.URL);
		
		RequestGenerator signInRequest = new RequestGenerator(signInService);
		
		String Response = signInRequest.respvalidate.returnresponseasstring();
		//System.out.println("\nSign In Response : "+Response);
		uidx = JsonPath.read(Response, "$.data.uidx").toString();
		xId = JsonPath.read(Response, "$.meta.token").toString();
		ppid= JsonPath.read(Response, "$.data.profile.publicProfileId");
		
		System.out.println("\nXID : "+xId);
		//System.out.println("\nUIDX : "+uidx);
		//System.out.println("\nPPID : "+ppid);
	}
	
	
	public RequestGenerator invokeGetProfileCount(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIPROFILECOUNTV3,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {ppid});
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("xid", xId);
		
		System.out.println("Get Profile Count URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetProfileCountWithPPID(String userName, String password, String PPID)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIPROFILECOUNTV3,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {PPID});
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("xid", xId);		
		System.out.println("Get Profile Count URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetProfileCountV3(String userName, String password)
	{
		getAndSetTokensWithPPID(userName, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIPROFILECOUNTV3,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {ppid});
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("xid", xId);
		
		System.out.println("Get Profile Count URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeGetVisualSearch(String URL, String Flag)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETVISUALSEARCH,init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {URL,Flag});
		HashMap<String, String> headers = new  HashMap<String, String>();
		headers.put("visual", "true");		
		System.out.println("Get Visual Search URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	
	public RequestGenerator invokeSearchWithRequestID(String data, String requestID, String pageNumber)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISEARCHWITHREQUESTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {data,requestID, pageNumber});
		System.out.println("Search With Request ID Service URL : "+service.URL);
		return new RequestGenerator(service);
	}	
	
	public String randomize(String Data)
	{
		String RandomizedData = Data;
		
		int RangeStart = 1000;
		int RangeEnd = 9999;
		Random obj = new Random();
		RandomizedData = RandomizedData+((obj.nextInt(RangeEnd - RangeStart)+RangeStart));
		
		return RandomizedData;	
	}
	
	public RequestGenerator invokePostFeedbackService(String mode, String userName, String password, String email, String feedbackMsg, String feedbackType, String isRooted)
	{
		String xid_l="";
		String sxid_l="";
		String uidx_l="";
		String xsrf_l="";
		
		if (mode.equals("RegisteredUser"))
		{
			getAndSetTokensWithPPID(userName,password);
			xid_l=xId;
			sxid_l=sXid;
			uidx_l=uidx;			
		}
		
		long currentTime = System.currentTimeMillis();
		String timeStamp = String.valueOf(currentTime);
		String deviceID = randomize("device");
		String payload[] = {deviceID,email,feedbackMsg,feedbackType,isRooted,sxid_l,timeStamp,uidx_l,xid_l,xsrf_l};
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS,APINAME.DEVAPIFEEDBACK,init.Configurations,payload);
		System.out.println("Feedback Service URL : "+service.URL);
		System.out.println("Feedback Service Payload : \n "+service.Payload);
		return new RequestGenerator(service);
	}
	
	public RequestGenerator invokeGetUserLikeSummary(String username, String password)
	{
		getAndSetTokensWithPPID(username,password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISPROFILEANDPRODUCTS, APINAME.LIKESUMMERY,init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);
		headers.put("Content-Type", "application/json");
		System.out.println("Get User Like Summary Service URL : "+service.URL);
		System.out.println("Get User Like Summary Payload : \n "+service.Payload);
		return new RequestGenerator(service,headers);
	}
	
	public RequestGenerator invokeSetUserPrivacy(String username, String password, String privacyFlag)
	{
		getAndSetTokensWithPPID(username,password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISETUSERPRIVACY,init.Configurations, new String[] {privacyFlag});
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("X-MYNTRA-APP", "appFamily=MyntraRetailAndroid; appVersion=3.2.1; appBuild=80110113; deviceCategory=Phone; osVersion=4.4.4; sdkVersion=19; deviceID=54ffe1a7dc0547be; installationID=2d1922ee-a3e9-37d0-9c42-615375c7acbd; sessionID=8ea30f2e-6536-4cf4-9f2a-7aad59f83ee2-54ffe1a7dc0547be;customerID=f254c549.9525.4211.ae06.a20a73f8e7d7BHQE719fCO;");
		System.out.println("Set User Privacy Service URL : "+service.URL);
		System.out.println("Set User Privacy Service Payload : \n "+service.Payload);
		return new RequestGenerator(service,headers);
	
	}
	
	
	public RequestGenerator checkPPIDAvailability(String username, String password, String newPPID)
	{
		getAndSetTokensWithPPID(username,password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPICHECKPPIDAVAILABILITY,init.Configurations);
		service.URL=apiUtil.prepareparameterizedURL(service.URL,newPPID);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);
		//headers.put("Content-Type", "application/json");
		System.out.println("Check PPID Availability URL : "+service.URL);
		return new RequestGenerator(service,headers);

	}
	
	public RequestGenerator saveUserProfileInfo(String username, String password, String bio, String dob, String gender, String location, String name, String newPPID)
	{
		getAndSetTokensWithPPID(username,password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISAVEPROFILEINFO,init.Configurations,new String[] {bio,dob,gender,location,name,newPPID});
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);
		headers.put("Content-Type", "application/json");
		System.out.println("Save User Profile URL : "+service.URL);
		return new RequestGenerator(service,headers);

	
	}
	
	//HallMark Service Helpers - GET and SAVE On boarding user state.
	
	public HashMap<String,String> SetScenario(String username, String password, String Scn, String appStat, String deviceId)
	{
		getAndSetTokens_NoIDP_Response(username,password);
		String deviceID_D =deviceId;
		boolean addXid = true;
		
		switch(Scn)
		{
			case "NONE":
				{
					deviceID_D="";
					addXid=false;
					System.out.println("Get/Set Data without Device ID and XID");
					break;
				}
			case "DEVICEID":
				{
					addXid=false;
					System.out.println("Get/Set Data with Device ID alone");
					break;
				}
			case "XID":
				{
					deviceID_D="";
					addXid=true;
					System.out.println("Get/Set Data with  XID alone");
					break;
				}
			case "BOTH":
			{
				
				addXid=true;
				System.out.println("Get/Set Data with XID & Device ID both");
				break;
			}	
		}
		String xid_L="";
		if(addXid)
		{
			xid_L = xId;
		}
		
		HashMap<String,String> Scenario = new HashMap<String,String>();
		Scenario.put("XID", xid_L);
		Scenario.put("DEVICE_ID", deviceID_D);
		
		return Scenario;
	}	
	
	public RequestGenerator getHallmarkUserOnboardingData(String username, String password, String Scn, String appStat, String deviceId)
	{
		
		HashMap<String,String> scn = SetScenario(username, password, Scn,  appStat,  deviceId);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIHALLMARKGETONBOARDINGDATA,init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		if(!(scn.get("XID").toString().isEmpty()))
			{
				headers.put("xid", xId);
			}
		headers.put("Content-Type", "application/json");
		service.URL=apiUtil.prepareparameterizedURL(service.URL, new String[] {appStat,scn.get("DEVICE_ID").toString()});
		System.out.println("Get user onboarding state service URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	//Hallmark Services - Helpers
	public String getSingleStrategy(String response, String StrategyType)
	{
		String strategy = "";
		String Path="";
		if(StrategyType.equals("DEPENDENT"))
		{
			Path = "$.data.steps[*].props.dependent[*]";
		}
		if(StrategyType.equals("MAIN"))
		{
			Path = "$.data.steps[*].strategy-id";
		}
		
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			System.out.println(StrategyType+" Strategy Array Length : "+Strategies.size());
			int strategyIndex = getRandomNumberInRange(0,Strategies.size());
			System.out.println("Random Index : "+strategyIndex);
			strategy = Strategies.get(strategyIndex).toString();
		}
		
		
		return strategy;
		
	}
	
	public int getRandomNumberInRange(int min, int max)
	{
		
		return (min + (int)(Math.random() * max));
		
	}
	
	public String getStrategyJSONPath(String StrategyType)
	{
		String Path="";
		if(StrategyType.equals("DEPENDENT"))
		{
			Path = "$.data.steps[*].props.dependent[*]";
		}
		if(StrategyType.equals("MAIN"))
		{
			Path = "$.data.steps[*].strategy-id";
		}
		return Path;
	}
	
	
	
	public String getMultipleStrategies(String response, String StrategyType, int numberOfStrategies)
	{
		String strategy = "";
		String Path=getStrategyJSONPath(StrategyType);
		
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			//System.out.println("Strategy Array Length : "+ArraySize);
			//System.out.println("Required number of Strategies : "+numberOfStrategies);
			if(numberOfStrategies>ArraySize)
			{
				numberOfStrategies=ArraySize;
			}
			//System.out.println("Processed number of Strategies : "+numberOfStrategies);
			int randomIndices[] = new int[numberOfStrategies];
			int strategyIndex =-1;
			boolean contains=false;
			//Random Order of Strategies
			for(int k=0;k<randomIndices.length;k++)
			{
				randomIndices[k]=-1;
			}
			for(int i=0; i<numberOfStrategies;i++)
			{
				do{	
					/*for(int k=0;k<randomIndices.length;k++)
					{
						System.out.println("Random Indices : "+randomIndices[k]);
					}*/
					contains=false;
					strategyIndex = getRandomNumberInRange(0,Strategies.size());
					//System.out.println("Calculated Index : "+strategyIndex);
					for(int j=0;j<randomIndices.length;j++)
					{
						if (randomIndices[j]==strategyIndex)
						{
							//System.out.println("FOUND MATCH!!!");
							contains=true;
						}
					}				
				}while(contains);
				
				randomIndices[i]=strategyIndex;
			}
			
			for(int i=0;i<randomIndices.length;i++)
			{
				//System.out.println("Random Indices : "+randomIndices[i]);
				
				if(i==randomIndices.length-1)
				{
					strategy = strategy+Strategies.get(randomIndices[i]);
				}
				else
				{
				strategy = strategy+Strategies.get(randomIndices[i])+"\",\"";
				}
			}
									
			System.out.println("Final Strategy List :\n "+strategy);
			//strategy = Strategies.get(strategyIndex).toString();
		}
				
		return strategy;
		
	}
	
	
	public String[] getMultipleStrategiesArr(String response, String StrategyType, int numberOfStrategies)
	{
		String strategy = "";
		String Path=getStrategyJSONPath(StrategyType);
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		String[] StrategiesArr = null;
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			//System.out.println("Strategy Array Length : "+ArraySize);
			//System.out.println("Required number of Strategies : "+numberOfStrategies);
			if(numberOfStrategies>ArraySize)
			{
				numberOfStrategies=ArraySize;
			}
			StrategiesArr = new String[numberOfStrategies];
			//System.out.println("Processed number of Strategies : "+numberOfStrategies);
			
			int randomIndices[] = new int[numberOfStrategies];
			int strategyIndex =-1;
			boolean contains=false;
			//Random Order of Strategies
			for(int k=0;k<randomIndices.length;k++)
			{
				randomIndices[k]=-1;
			}
			for(int i=0; i<numberOfStrategies;i++)
			{
				do{	
					/*for(int k=0;k<randomIndices.length;k++)
					{
						System.out.println("Random Indices : "+randomIndices[k]);
					}*/
					contains=false;
					strategyIndex = getRandomNumberInRange(0,Strategies.size());
					//System.out.println("Calculated Index : "+strategyIndex);
					for(int j=0;j<randomIndices.length;j++)
					{
						if (randomIndices[j]==strategyIndex)
						{
							//System.out.println("FOUND MATCH!!!");
							contains=true;
						}
					}				
				}while(contains);
				
				randomIndices[i]=strategyIndex;
			}
			
			for(int i=0;i<randomIndices.length;i++)
			{
				//System.out.println("Random Indices : "+randomIndices[i]);
				StrategiesArr[i]=Strategies.get(randomIndices[i]).toString();
				if(i==randomIndices.length-1)
				{
					strategy = strategy+Strategies.get(randomIndices[i]);
				}
				else
				{
				strategy = strategy+Strategies.get(randomIndices[i])+"\",\"";
				}
			}
									
			System.out.println("Final Strategy List :\n "+strategy);
			//strategy = Strategies.get(strategyIndex).toString();
		}
				
		
		return StrategiesArr;
		
	}
	
	
	
	
	public boolean isStrategyRemoved(String response, String strategyType, String Strategy)
	{
		String Path=getStrategyJSONPath(strategyType);
		boolean Removed = true;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				if(Strategies.get(i).equals(Strategy))
				{
					Removed = false;
				}
			}
		}		
		System.out.println("IS STRATEGY REMOVED? - "+Removed);
		return Removed;
	}
	
	public String[] getDependantStrategies(String response, String MainStrategy)
	{
		
		String[] dependentStrategies = new String[0];
		String Path=getStrategyJSONPath("MAIN");		
		JSONArray DependentStrategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				if(Strategies.get(i).equals(MainStrategy))
				{
					String newPath = "$.data.steps["+i+"].props.dependent[*]";					
					DependentStrategies = JsonPath.read(response,newPath);
					dependentStrategies=new String[DependentStrategies.size()];
				}
			}
		}
		
		for(int i=0;i<DependentStrategies.size();i++)
		{
			dependentStrategies[i]=DependentStrategies.get(i).toString();
		}
		
		return dependentStrategies;		
	}
	
	public String getMainStrategyWithoutDependents(String response)
	{
		String Path=getStrategyJSONPath("MAIN");
		String Strategy=null;
		String newPath="";
		JSONArray D_Strategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				newPath = "$.data.steps["+i+"].props.dependent[*]";
				try
				{	D_Strategies = JsonPath.read(response,newPath);
					if(D_Strategies.isEmpty())
					{
						Strategy = Strategies.get(i).toString();
						return Strategy;
					} 
				}
				catch(com.jayway.jsonpath.PathNotFoundException e)
				{
					continue;
				}
			}
		}
		
		return Strategy;
		
	}
	
	public String getMainStrategyWithDependents(String response)
	{
		String Path=getStrategyJSONPath("MAIN");
		String Strategy=null;
		String newPath="";
		JSONArray D_Strategies = null;
		int responseStatus = Integer.parseInt(JsonPath.read(response,"$.meta.code").toString());
		if(responseStatus==200)
		{
			JSONArray Strategies = JsonPath.read(response,Path);
			int ArraySize = Strategies.size();
			for(int i =0; i<ArraySize;i++)
			{
				newPath = "$.data.steps["+i+"].props.dependent[*]";
				try
				{	D_Strategies = JsonPath.read(response,newPath);
					if(!D_Strategies.isEmpty())
					{
						Strategy = Strategies.get(i).toString();
						return Strategy;
					} 
				}
				catch(com.jayway.jsonpath.PathNotFoundException e)
				{
					continue;
				}
			}
		}
		
		return Strategy;
		
	}
	
	
	//Hallmark Services - Save onboarding Data
	public RequestGenerator SaveHallmarkUserOnboardingData(String username, String password, String Scn, String appStat, String deviceId, String Strategy)
	{
		HashMap<String,String> scn = SetScenario(username, password, Scn,  appStat,  deviceId);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIHALLMARKSAVEONBOARDINGDATA,init.Configurations,new String[] {scn.get("DEVICE_ID").toString(),"\""+Strategy+"\""});
		HashMap<String, String> headers = new HashMap<String,String>();
		if(!(scn.get("XID").toString().isEmpty()))
			{
				headers.put("xid", xId);
			}
		headers.put("Content-Type", "application/json");
		System.out.println("Save user onboarding state service URL : "+service.URL);
		System.out.println("Save user onboarding state service URL : "+service.URL);

		return new RequestGenerator(service,headers);
	}
	
		
	//Cashback
	public RequestGenerator getUserCashBack(String username, String password)
	{
		getAndSetTokensWithPPID(username,password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETCASHBACK,init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);		
		headers.put("Content-Type", "application/json");
		System.out.println("Get user cashback value service URL : "+service.URL);
		return new RequestGenerator(service,headers);
	}
	
	// Notifications
	
	//Create Master Notification
	public RequestGenerator CreateLGPMasterNotification(String username)
	{
		
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.add(Calendar.SECOND, 5);
		String StartTime = String.valueOf(startTimeCal.getTimeInMillis());
		String NotificationID = randomize("5989");
		String Heading = "FOSSIL";
		String Title = FOSSIL_PROD_NOTIFICATION_TITLE;
		String Description=FOSSIL_PROD_NOTIFICATION_TEXT;
		String PageURL = FOSSIL_PROD_NOTIFICATION_PAGE_URL;
		String ImageURL = FOSSIL_PROD_NOTIFICATION_IMG_URL;
		String User = username;
		
		String payload[] = new String[] {NotificationID,StartTime,Heading,Title,Description,PageURL,ImageURL,User};
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LGPNOTIFICATIONS,	APINAME.CREATELGPMASTERNOTIFICATION, init.Configurations,payload);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("Authorization", "Basic VTNsemRHVnRmbE41YzNSbGJUcFRlWE4wWlcwOg==");
		return new RequestGenerator(service,headers);
		
	}
	
	public RequestGenerator getAllUserNotifications(String username, String password)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,	APINAME.DEVAPIGETALLNOTIFICATIONS, init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	public RequestGenerator getAllUserNotificationsWithBatch(String username, String password, String BatchSize)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,	APINAME.DEVAPIGETNOTIFICATIONWITHBATCH, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, BatchSize);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	public RequestGenerator markNotificationAsRead(String username, String password, String[] Params)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,	APINAME.DEVAPINOTIFICATIONMARKASREAD, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, Params);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	public RequestGenerator getNotificationNext(String username, String password, String[] Params)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,	APINAME.DEVAPIGETNOTIFICATIONNEXT, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, Params);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	public RequestGenerator getNotificationCount(String username, String password)
	{
		getAndSetTokensWithPPID(username, password);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPISNOTIFICATION,	APINAME.DEVAPIGETALLNOTIFICATIONSCOUNT, init.Configurations);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);
	
	}
	
	public RequestGenerator invokeV2Serviceability(String SearchString)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV2, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, SearchString);
		System.out.println("V2 Style Serviceability Service URL : "+service.URL);
		return new RequestGenerator(service);
	}
	
	public RequestGenerator invokeV3Serviceability(String SearchString)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV3, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, SearchString);
		System.out.println("V3 Style Serviceability Service URL : "+service.URL);
		return new RequestGenerator(service);
	}
	
	public RequestGenerator invokeV4Serviceability(String[] ServicePayload)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPISERVICEABILITYV4, init.Configurations,ServicePayload);
		System.out.println("V4 Style Serviceability Service URL : "+service.URL);
		System.out.println("V4 Style Serviceability Service Payload : "+service.Payload);
		return new RequestGenerator(service);
	}
	
	public RequestGenerator getSimilarItems(String StyleName, String StyleID, String UseVisualSearch)
	{
		String VisuallySimilarItemsHeader = "pdp.details=table; lgp.rollout=enabled; lgp.cardloadevent=enabled; d0.newuser=disabled; pdp.forum=enabled; lga.shots=enabled; nav.links=store; lgp.personalization.rollout=enabled; rn.update=default; pdp.video=enabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; lgp.timeline.cardloadevent=enabled; contactus.number=outside; nav.guided=enabled; search.additionalInfo=test; search.visual=enabled; rn.update.ios=default; testset=test2; recommendations.visual=enabled; lgp.stream=enabled; lgp.rollout.ios=enabled; lgp.stream.ios=disabled; pps=enabled";
		String SimilarItemsHeader = "pdp.details=table; lgp.rollout=enabled; lgp.cardloadevent=enabled; d0.newuser=disabled; pdp.forum=enabled; lga.shots=enabled; nav.links=store; lgp.personalization.rollout=enabled; rn.update=default; pdp.video=enabled; nav.store=disabled; search.sampling=enabled; cart.juspay=enabled; lga.forum=disabled; lgp.timeline.cardloadevent=enabled; contactus.number=outside; nav.guided=enabled; search.additionalInfo=test; search.visual=enabled; rn.update.ios=default; testset=test2; recommendations.visual=disabled; lgp.stream=enabled; lgp.rollout.ios=enabled; lgp.stream.ios=disabled; pps=enabled";

		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETSIMILARITEMS, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[] {StyleName, StyleID});
		System.out.println("Get Similar Items URL : "+service.URL);
		HashMap<String, String> headers = new HashMap<String,String>();
		if(UseVisualSearch.equals("true"))
		{
		headers.put("X-MYNTRA-ABTEST", VisuallySimilarItemsHeader);
		}
		else
		{
		headers.put("X-MYNTRA-ABTEST", SimilarItemsHeader);
		}
		return new RequestGenerator(service,headers);		
	}
	
	public RequestGenerator getUserCoupons(String username, String password, String useMail)
	{
		getAndSetTokensWithPPID(username, password);
		String serviceParam =uidx;
		if(useMail.equals("true"))
		serviceParam=username;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DEVAPIS, APINAME.DEVAPIGETUSERCOUPONS, init.Configurations);
		service.URL=apiUtil.prepareparameterizedURL(service.URL, serviceParam);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("xid", xId);
		return new RequestGenerator(service,headers);	
	}
	
	//New Commit
}
