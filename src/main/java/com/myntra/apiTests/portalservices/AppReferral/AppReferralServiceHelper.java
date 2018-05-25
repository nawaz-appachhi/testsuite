package com.myntra.apiTests.portalservices.AppReferral;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.portalservices.notificationservice.NotificationServiceConstants;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
/*
 * @author: Aravind Raj R
 * @date: 09/18/2015
 */
public class AppReferralServiceHelper extends CommonUtils implements NotificationServiceConstants
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(AppReferralServiceHelper.class);
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static String xId, sXid, uidx;
	APIUtilities apiUtil=new APIUtilities();
	static String EncryptKey = "myntramobileapps";
	static String DeviceID_R;
	static String InstallationID_R;
	static String IMEI_R;
	static String userName_R;
	static String magasin_header_key = "43fa61a5cc532a7bf70efb5c0e7c7907";
	static String magasin_header = "x-magasin-key";

	
	
	private void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
	}
	
	private String randomize(String Data)
	{
		String RandomizedData = Data;
		
		int RangeStart = 1000;
		int RangeEnd = 9999;
		Random obj = new Random();
		RandomizedData = RandomizedData+((obj.nextInt(RangeEnd - RangeStart)+RangeStart));
		
		return RandomizedData;	
	}
	
	
	private void getAndSetTokens(String userName, String password)
	{
		System.out.println("\nUsername : "+userName+"\nPassword : "+password);
		MyntraService signInService = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[] { userName, password });
		printAndLog("\nSign In Service URL : ",signInService.URL);
		
		RequestGenerator signInRequest = new RequestGenerator(signInService);
		MultivaluedMap<String, Object> map = signInRequest.response.getHeaders();
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
			{
				xId = entry.getValue().toString();
			}
		}
		String Response = signInRequest.respvalidate.returnresponseasstring();
		printAndLog("\nSign In Response : ",Response);
		uidx = JsonPath.read(Response, "$.user.uidx").toString();
		xId = xId.substring((xId.indexOf("[") + 1), xId.lastIndexOf("]"));
		sXid = signInRequest.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		if (sXid.contains("'"))
		{
			sXid = sXid.substring(sXid.indexOf("'") + 1, sXid.lastIndexOf("'"));
		}
		else
		{
			sXid = sXid.substring(sXid.indexOf("[") + 1, sXid.lastIndexOf("]"));
		}
		printAndLog("\nXID : ",xId);
		printAndLog("\nSXID : ",sXid);
		printAndLog("\nUIDX : ",uidx);
	}
	
	//Encrypt Request	
	public static String encryptRequest(String EncryptKey, String RequestToEncrypt) throws Exception
	{
		try
		{
			SecretKeySpec skeySpec = getKey(EncryptKey);
            byte[] clearText = RequestToEncrypt.getBytes("UTF8");
            
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            String EncryptedRequest = DatatypeConverter.printBase64Binary(cipher.doFinal(clearText));
        
            return EncryptedRequest;
          	}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
        	return "";
		}
	
	//Decrypt Response
	public static String decryptResponse(String DecryptKey, String ResponseToDecrypt) throws Exception
	{
		try
		{
			SecretKeySpec skeySpec = getKey(DecryptKey);
			byte[] decryptedResponse = DatatypeConverter.parseBase64Binary(ResponseToDecrypt);
		
                      
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            String DecryptedResponse = new String (cipher.doFinal(decryptedResponse));
            return DecryptedResponse;
          	}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
        	return "";
		}
	
	
	//Get Encryption Key
	public static SecretKeySpec getKey(String Key) throws Exception 
		{
			int keyLength = 128;
			byte[] keyBytes = new byte[keyLength / 8];
			Arrays.fill(keyBytes, (byte) 0x0);
			byte[] passwordBytes = Key.getBytes("UTF-8");
			int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
			System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
			return key;
		}
	
	public void TamperXid()
	{
		xId = xId.concat("qwerty");
		printAndLog("TAMPERED XID : ",xId);
	}
	
	
	
	
	//Referral Service: Helper Methods	
	//Get Referral Code
	public RequestGenerator invokeGetReferralCodeService(String userName, String password, String TestType)
	{
		getAndSetTokens(userName, password);
		MyntraService AppReferralGetReferralCodeService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALGETCODE, init.Configurations);
		HashMap<String, String> AppReferralGetReferralCodeServiceHeaders = new HashMap<String, String>();
		
		if(TestType.equals("N")) TamperXid();
		AppReferralGetReferralCodeServiceHeaders.put("xid", xId);
		printAndLog("\nApp Referral - Get Referral Code URL : ",AppReferralGetReferralCodeService.URL);
		return new RequestGenerator(AppReferralGetReferralCodeService,AppReferralGetReferralCodeServiceHeaders);
		
	}
	
	//Get My details including earnings and referrals
	public RequestGenerator invokeGetMyEarningsService(String userName, String password, String TestType)
	{
		getAndSetTokens(userName, password);
		MyntraService AppReferralGetMyDetailsService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALGETDETAILS, init.Configurations);
		HashMap<String, String> AppReferralGetMyDetailsServiceHeaders = new HashMap<String, String>();
		
		if(TestType.equals("N")) TamperXid();
		AppReferralGetMyDetailsServiceHeaders.put("xid", xId);
		printAndLog("\nApp Referral - Get My Earnings Service URL : ",AppReferralGetMyDetailsService.URL);
		return new RequestGenerator(AppReferralGetMyDetailsService,AppReferralGetMyDetailsServiceHeaders);
		
	}
	
	//Welcome API for the referee on installing the app
	public RequestGenerator invokeWelcomeAPIService(String userName, String password, String referralCode, String TestType)
	{
		getAndSetTokens(userName, password);
		MyntraService AppReferralGetMyDetailsService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALWELCOME, init.Configurations);
		HashMap<String, String> AppReferralGetMyDetailsServiceHeaders = new HashMap<String, String>();
		
		if(TestType.equals("N")) TamperXid();
		AppReferralGetMyDetailsService.URL = apiUtil.prepareparameterizedURL(AppReferralGetMyDetailsService.URL , referralCode);
		AppReferralGetMyDetailsServiceHeaders.put("xid", xId);
		printAndLog("\nApp Referral - Welcome API URL : ",AppReferralGetMyDetailsService.URL);
		return new RequestGenerator(AppReferralGetMyDetailsService,AppReferralGetMyDetailsServiceHeaders);
		
	}
		
	
	//Get OTP Code during installation
	public RequestGenerator invokeGetOTPService(String userName, String password, String Mobile, String TestType)
	{
		getAndSetTokens(userName, password);
		MyntraService AppReferralGetOTPService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALGETOTP, init.Configurations, new String[] {Mobile});
		HashMap<String, String> AppReferralGetOTPServiceHeaders = new HashMap<String, String>();
		if(TestType.equals("N")) TamperXid();
		AppReferralGetOTPServiceHeaders.put("xid", xId);
		printAndLog("\nApp Referral - Get OTP URL : ",AppReferralGetOTPService.URL);
		printAndLog("\nApp Referral - Get OTP Payload : ",AppReferralGetOTPService.Payload);
		return new RequestGenerator(AppReferralGetOTPService,AppReferralGetOTPServiceHeaders);
	}
	
	//Verify Referral Code - Fraud Check
	public RequestGenerator invokeVerifyReferralCodeService_Magasin(String userName, String password,String referCode, String isRooted)
	{
		getAndSetTokens(userName, password);
		MyntraService DevAPIVerifyReferralCodeService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALVERIFYREFERCODE_MAGASIN, init.Configurations, new String[] { referCode, uidx, userName, userName, DeviceID_R, IMEI_R, InstallationID_R, isRooted});
		HashMap <String, String> DevAPIVerifyReferralCodeServiceHeaders = new HashMap <String, String> ();
		DevAPIVerifyReferralCodeServiceHeaders.put("xid",xId);	
		DevAPIVerifyReferralCodeServiceHeaders.put(magasin_header, magasin_header_key);
		printAndLog("\nAppReferral - VerifyReferralCode - URL : ",DevAPIVerifyReferralCodeService.URL);
		printAndLog("\nAppReferral - VerifyReferralCode - Payload : ",DevAPIVerifyReferralCodeService.Payload);

		return new RequestGenerator(DevAPIVerifyReferralCodeService,DevAPIVerifyReferralCodeServiceHeaders);
	}
	
	//Verify Referral Code - App Referral
	public RequestGenerator invokeVerifyReferralCodeService_AppReferral(String userName, String password,String referCode, String isRooted, int TrialCount)
	{
		if(TrialCount == 0)
		{
		userName_R=randomize(userName).concat("@myntra.com");
		}
		
		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName_R, password);		
		System.out.println("\n USER SIGN UP RESPONSE : \n\n"+devApiSignUpReqGen.respvalidate.returnresponseasstring());
		
		getAndSetTokens(userName_R, password);
		
		MyntraService DevAPIVerifyReferralCodeService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALVERIFYREFERCODE, init.Configurations, new String[] { referCode, uidx, userName_R, userName_R, DeviceID_R, IMEI_R, InstallationID_R, isRooted});
		HashMap <String, String> DevAPIVerifyReferralCodeServiceHeaders = new HashMap <String, String> ();
		DevAPIVerifyReferralCodeServiceHeaders.put("xid",xId);	
		printAndLog("\nAppReferral - VerifyReferralCode - URL : ",DevAPIVerifyReferralCodeService.URL);
		printAndLog("\nAppReferral - VerifyReferralCode - Payload : ",DevAPIVerifyReferralCodeService.Payload);

		return new RequestGenerator(DevAPIVerifyReferralCodeService,DevAPIVerifyReferralCodeServiceHeaders);
	}
	
	//Magasin - Un Registered Device
	public RequestGenerator invokeMagasinService_UnRegisteredDevice(String userName, String password,String referCode, String DeviceID, String InstallationID, String IMEI, String isRooted)
	{
		getAndSetTokens(userName, password);
		MyntraService MagasinUnRegisteredDeviceFlowService = Myntra.getService(ServiceType.PORTAL_DEVAPIAPPREFERRAL, APINAME.DEVAPIREFERRALVERIFYREFERCODE_MAGASIN, init.Configurations, new String[] { referCode, uidx, userName, userName, randomize(DeviceID), randomize(IMEI), randomize(InstallationID), isRooted});
		HashMap <String, String> MagasinUnRegisteredDeviceFlowServiceHeaders = new HashMap <String, String> ();
		MagasinUnRegisteredDeviceFlowServiceHeaders.put("xid",xId);	
		MagasinUnRegisteredDeviceFlowServiceHeaders.put(magasin_header, magasin_header_key);

		printAndLog("\nMagasin - UnRegisteredDevice - URL : ",MagasinUnRegisteredDeviceFlowService.URL);
		printAndLog("\nMagasin - UnRegisteredDevice - Payload : ",MagasinUnRegisteredDeviceFlowService.Payload);

		return new RequestGenerator(MagasinUnRegisteredDeviceFlowService,MagasinUnRegisteredDeviceFlowServiceHeaders);
	}
	
	//Magasin - Create and Update Device Data
	public RequestGenerator invokeMagasinService_CreateAndUpdateDeviceData(String userName, String password, APINAME ApiName, String DeviceID, String InstallationID, String IMEI, String isRooted, String Flow, String Data)
	{
		getAndSetTokens(userName, password);
		if(Flow.equals("createFlow"))
		{
			DeviceID_R = randomize(DeviceID);
			InstallationID_R = randomize(InstallationID);
			IMEI_R = randomize(IMEI);
		}
		if(Flow.equals("updateFlow"))
		{
			DeviceID_R = DeviceID_R.concat(Data);
			IMEI_R = IMEI_R.concat(Data);
		}
		MyntraService MagasinRegisterDeviceService = Myntra.getService(ServiceType.PORTAL_MAGASIN, ApiName, init.Configurations, new String[] { userName, DeviceID_R, IMEI_R, isRooted});
		HashMap <String, String> MagasinRegisterDeviceServiceHeaders = new HashMap <String, String> ();
		MagasinRegisterDeviceServiceHeaders.put("xid",xId);	
		MagasinRegisterDeviceServiceHeaders.put(magasin_header, magasin_header_key);

		MagasinRegisterDeviceService.URL = apiUtil.prepareparameterizedURL(MagasinRegisterDeviceService.URL,InstallationID_R);
		printAndLog("\nMagasin - Register New Device - URL : ",MagasinRegisterDeviceService.URL);
		printAndLog("\nMagasin - Register New Device - Payload : ",MagasinRegisterDeviceService.Payload);
		return new RequestGenerator(MagasinRegisterDeviceService,MagasinRegisterDeviceServiceHeaders);
	}
}