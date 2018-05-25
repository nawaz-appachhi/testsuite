package com.myntra.apiTests.portalservices.atsService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.aerospike.client.lua.LuaAerospikeLib.log;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.DBValidator;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

public class ATSHelperV2 implements ATSConstants {
	
	static APIUtilities utilities = new APIUtilities();	
    static org.slf4j.Logger log = LoggerFactory.getLogger(DBValidator.class);
    static Random rand = new Random();
    
//	String tenantId;
//
//	public ATSHelperV2() {
//	}
//
//	public ATSHelperV2(String tenantId) {
//		super();
//		this.tenantId = tenantId;
//	}
    
    public String randomNumGenerate() {
    	 long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    	  return  String.valueOf(number);
    }
    
    private Boolean isV2TestCase() {
    		return version.equals("v1") ? false : true;
    }
    
	private HashMap<String, String> getHeaders(Boolean withTenentId){
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "Application/json");
		headers.put("Accept", "Application/json");
		System.out.println("version: " + version);
		System.out.println("tenantId: " + tenantId);
		if(isV2TestCase() && withTenentId) {
			if(tenantId.equals("jabong"))
				headers.put("x-mynt-ctx", "storeid=4603");
			else if(tenantId.equals("myntra"))
				headers.put("x-mynt-ctx", "storeid=2297");
		}
        log.debug("Passed Headers:  " + headers);
		return headers;
	}
	
	public String getPathURL(String pathURL) {
		if(isV2TestCase()) 
			pathURL =  "v2/" + pathURL ;
		System.out.println("isV2TestCase: " + isV2TestCase());
		return pathURL;
	}
	
	public Svc getATSUserDetails(String uidx, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL  = Constants.ATS_PATH.GET_ATSUSERDETAILS;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}
	
	public Svc postATSUserDetails(String uidx, String reasonReturnAbuser, Boolean isCODThrottled,
			String reasonRTOAbuser, Boolean isFakeEmail, String reasonFakeEmail, String maxcod, Boolean isReturnAbuser,
			String amount, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = { uidx, reasonReturnAbuser, isCODThrottled, reasonRTOAbuser, isFakeEmail, reasonFakeEmail,
				maxcod, isReturnAbuser, amount };
		String payload = null;
		String pathURL = Constants.ATS_PATH.POST_ATSUSERDETAILS;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/postatsuserdetails");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.POST, payload, getHeaders(withTenentId));
		return service;
	}
	
	public Svc getATSAddresscore(Object[] strPayload, Boolean withTenentId) throws UnsupportedEncodingException {
		String payload = null;
		String pathURL  = Constants.ATS_PATH.GET_ADDRESSCORE;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putaddresscore");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				null,SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload,
				getHeaders(withTenentId));
		return service;
	}

	public Svc getATSOpenOrdersValue(String uidx, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_OPENORDERS_VALUE;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}
	
	public Svc putOpenOrdersValue(String uidx, Double amount, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, amount};
		String payload = null;
		String pathURL = Constants.ATS_PATH.SET_OPENORDERS_VALUE;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putopenordersvalue");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);	
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;
	}

	public Svc postATSUserDataForOutstandingLimit(String uidx, String lable, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, lable};
		String payload = null;
		String pathURL = Constants.ATS_PATH.POST_ATSUSERDETAILS;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/postatsdetailsoutstanding");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.POST, payload, getHeaders(withTenentId));
		return service;		
	}

	public Svc getATSUserDataForOutstandingLimit(String uidx, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_OUTSTANDINGCCOD;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}

	public Svc putReturnAbuserDetails(String uidx, Boolean isReturnAbuser, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, isReturnAbuser};
		String payload = null;
		String pathURL = Constants.ATS_PATH.PUT_RETURNABUSERDETAILS;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putreturnabuser");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;		
	}

	public Svc getReturnAbuserDetails(String uidx, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_RETURNABUSERDETAILS;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}

	public Svc putFakeEmailDetails(String uidx, Boolean isFakeEmail, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, isFakeEmail};
		String payload = null;
		String pathURL = Constants.ATS_PATH.PUT_FAKEEMAILDETAILS;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putfakeemail");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;		
	}
	
	public Svc getFakeEmailDetails(String uidx, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_FAKEEMAILDETAILS;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + uidx }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}

	public Svc putLinkedPhone(String uidx, String phoneNum, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, phoneNum};
		String payload = null;
		String pathURL = Constants.ATS_PATH.PUT_LINKEDPHONE;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putlinkedphone");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;
	}

	public Svc getLinkedCustomerByPhone(String phoneNum, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_LINKEDPHONE;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + phoneNum }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}

	public Svc getLinkedCustomerByEmail(String email, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_LINKEDACCOUNTS;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { "" + email }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}
	
	public Svc getLinkedCustomerByDevice(String device, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_LINKEDDEVICE;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] {"" + device}, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}

	public Svc getIsCustomerBlocked(String email, Boolean withTenentId) throws UnsupportedEncodingException {
		String[] strPayload = {email};
		String payload = null;
		String pathURL = Constants.ATS_PATH.PUT_ISCUSTOMERBLOCKED;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putiscustomerblocked");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;
	}

	public Svc putCustomerBlocked(String userAttribute, Boolean value, int numberKeys, String nameSpace, Boolean withTenentId) throws UnsupportedEncodingException {
		Object[] strPayload = {userAttribute, value, numberKeys, nameSpace};
		String payload = null;
		String pathURL = Constants.ATS_PATH.PUT_CUSTOMERBLOCKED;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/putcustomerblocked");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.PUT, payload, getHeaders(withTenentId));
		return service;
	}

	public Svc getBlockedCustomerBy(String blockedBy, String userAttribute, Boolean withTenentId) throws UnsupportedEncodingException {
		String pathURL = Constants.ATS_PATH.GET_BLOCKEDBY;
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL),
				new String[] { blockedBy + "/" + userAttribute }, SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.GET, null, getHeaders(withTenentId));
		return service;
	}
	
	public Svc postATSUserDetails(String uidx, String label, Boolean isReturnAbuser, Boolean withTenentId)
			throws UnsupportedEncodingException {
		Object[] strPayload = { uidx, label, isReturnAbuser };
		String pathURL = Constants.ATS_PATH.POST_ATSUSERDETAILS;
		String payload = null;
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/postatsdetails");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payload = utilities.preparepayload(payload, strPayload);
		Svc service = HttpExecutorService.executeHttpService(getPathURL(pathURL), null,
				SERVICE_TYPE.ATS_SVC.toString(), HTTPMethods.POST, payload, getHeaders(withTenentId));
		return service;
	}
}
