package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

//Helper methods for DevAPI - IDP related test cases
public class IDPTestsHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IDPTestsHelper.class);
	public static String XID;
	static String UIDX;
	static String PPID;
	static String SXID;
	DevAPICommonMethods Util;
	
	//Method #1 - To invoke SignIn API
	public RequestGenerator signIn(String email, String password)
	{
		MyntraService SignInService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPISIGNIN, init.Configurations, new String[] { email, password });
		System.out.println("SignIn URL : "+SignInService.URL);
		System.out.println("SignIn Payload :\n "+SignInService.Payload);
		return new RequestGenerator(SignInService);
	}
	
	//Method #2 - To invoke SignUp API
	public RequestGenerator emailSignUp(String email, String password, String name, String gender, String mobile)
	{
		MyntraService SignUpService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.SIGNUP, init.Configurations, new String[] { email, password, name, gender, mobile });
		System.out.println("SignUp URL : "+SignUpService.URL);
		System.out.println("SignUp Payload :\n "+SignUpService.Payload);
		return new RequestGenerator(SignUpService);
	}
	
	//Method #3 - Method to invoke API Refresh call
	public RequestGenerator idpRefresh(String userName, String password, boolean tamperSXID, boolean tamperXID)
	{
		getAndSetTokens(userName, password);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("sxid", SXID);
		headers.put("xid", XID);
		if(tamperXID) XID="$$##@@";
		if(tamperSXID) SXID="$$##@@";
		MyntraService IDPRefreshService =Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPIREFRESH, init.Configurations, new String[] { SXID, XID });
		System.out.println("IDP Refresh URL : "+IDPRefreshService.URL);
		System.out.println("IDP Refresh Payload :\n "+IDPRefreshService.Payload);
		return new RequestGenerator(IDPRefreshService,headers);
	}
	
	//Method #4 - Method to invoke SignOut
	public RequestGenerator SignOut(String email, String password)
	{
		getAndSetTokens(email, password);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("sxid", SXID);
		headers.put("xid", XID);
		MyntraService SignOutService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPILOGOUT, init.Configurations);
		System.out.println("Signout Service URL : "+SignOutService.URL);
		System.out.println("Signout Service Payload : "+SignOutService.Payload);
		return new RequestGenerator(SignOutService,headers);
	}
	
	//Method #5 - Method to invoke Password Reset Call
	public RequestGenerator ResetPassword(String email)
	{
		MyntraService PasswordResetService = Myntra.getService(ServiceType.PORTAL_DEVAPISHTTPS, APINAME.DEVAPIFORGOTPASSWORD, init.Configurations, new String[]{ email });
		System.out.println("Reset Password Service URL : "+PasswordResetService.URL);
		System.out.println("Reset Password Service Payload : "+PasswordResetService.Payload);
		return new RequestGenerator(PasswordResetService);
	}
	
	//Method #6 - Method to Sign In and get following Tokens - XID, SXID, UIDX, PPID
	public void getAndSetTokens(String email, String password)
	{
		RequestGenerator SignInRequest = signIn(email,password);
		String response = SignInRequest.respvalidate.returnresponseasstring();
		System.out.println("SignIn Service Response :\n "+response);
		
		try
		{
			SXID = SignInRequest.response.getHeaderString("sxid");
			XID = JsonPath.read(response, "$.meta.token").toString();
			UIDX = JsonPath.read(response, "$.data.login").toString();
			System.out.println("XID : "+XID);
			System.out.println("UIDX : "+UIDX);	
			PPID = JsonPath.read(response, "$.data.profile.publicProfileId").toString();
			System.out.println("PPID : "+PPID);
		}
		catch(PathNotFoundException E)
		{
			System.out.println("Unable to Set Tokens - Verify Sign In Response");
		}
		catch(Exception E)
		{
			System.out.println("Unable to Set Tokens - Verify Sign In Response");
		}
	}
	
	

}
