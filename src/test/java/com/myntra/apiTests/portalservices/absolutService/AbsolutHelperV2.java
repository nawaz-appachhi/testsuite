package com.myntra.apiTests.portalservices.absolutService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.ExpressionEval;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.monk.core.MonkUtils;
import com.myntra.monk.core.XMetaRequestContext;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

import argo.saj.InvalidSyntaxException;

public class AbsolutHelperV2 {
	
	public AbsolutHelperV2() {
		
	}

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AbsolutHelperV2.class);
	static String prevUser, prevPass;
	APIUtilities apiUtil = new APIUtilities();


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
	
	public String getXMetaCtxData(String storeId, String nidx) {
		HashMap<String, String> xMyntCtxMap = new HashMap<>();
		xMyntCtxMap.put(XMetaRequestContext.X_META_CTX_STORE_ID, storeId);
		xMyntCtxMap.put(XMetaRequestContext.X_META_CTX_NIDX, nidx);
		String xMyntCtx = MonkUtils.generateXMetaString(xMyntCtxMap);
		return xMyntCtx;
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
	public RequestGenerator addItemToCart(String xid,String tenantId, String skuId, String quantity) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART,
				APINAME.ADDITEMTOCARTV2, init.Configurations, new String[] {skuId, quantity});
		System.out.println("\nPrinting addItemToCart API URL : "+ service.URL);
		System.out.println("\nPrinting addItemToCart API Payload : \n\n"+ service.Payload);
		HashMap<String, String> headers = new HashMap<String, String>();
		String xMyntCtx = getXMetaCtxData(tenantId, xid);
		System.out.println("xMyntCtx = "+xMyntCtx);
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");		
		headers.put(XMetaRequestContext.X_META_CTX_HEADER, xMyntCtx);
		return new RequestGenerator(service, headers);
	}
}
