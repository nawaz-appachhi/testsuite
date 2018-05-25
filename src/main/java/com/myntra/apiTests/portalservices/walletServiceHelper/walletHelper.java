package com.myntra.apiTests.portalservices.walletServiceHelper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;

import com.google.common.hash.Hashing;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.legolas.Commons;

public class walletHelper extends CommonUtils {
	Commons commonUtil = new Commons();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(walletHelper.class);
	APIUtilities apiUtil=new APIUtilities();
	String merchantId = "M2306160483220675579140";
	String salt="7e002dc7-a268-42d3-bec6-9a03aca45b27";
	String authorization="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtZXJjaGFudC1zZXJ2aWNlIiwiZXhwIjozMzAyMzQ5OTI5MCwiaWF0IjoxNDY2NTkwNDkwLCJzdWIiOiJtZXJjaGFudC1zZXJ2aWNlLW15bnRyYS1hdXRob3JpemF0aW9uLWtleSIsInJvbGUiOiJjb25zdW1lciIsInR5cGUiOiJzdGF0aWMifQ.cNbEBdVMoXVyq71fts7PWjlsB6NMgKnK1LinYbTRqFHfp0Xo4cKHOy4_CWt8IrQ_PfmzXRPIxmLYUfrqv_8CFw";
    String Salt_Index ="1"; 
    String amount= "10";
	
	
    public RequestGenerator checkBalance(String UIDX)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.WALLETBALANCE,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	
	
	public RequestGenerator GetPhonePeBalance(String UIDX)
	{
		String Sha= computeSha(merchantId+UIDX+salt);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PHONEPE, APINAME.PHONEPEBALANCE,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("X-VERIFY", Sha);
		getParam1.put("X-SALT-INDEX", "1");
		getParam1.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtZXJjaGFudC1zZXJ2aWNlIiwiZXhwIjozMzAyMzQ5OTI5MCwiaWF0IjoxNDY2NTkwNDkwLCJzdWIiOiJtZXJjaGFudC1zZXJ2aWNlLW15bnRyYS1hdXRob3JpemF0aW9uLWtleSIsInJvbGUiOiJjb25zdW1lciIsInR5cGUiOiJzdGF0aWMifQ.cNbEBdVMoXVyq71fts7PWjlsB6NMgKnK1LinYbTRqFHfp0Xo4cKHOy4_CWt8IrQ_PfmzXRPIxmLYUfrqv_8CFw");
		System.out.println(getParam1);
        RequestGenerator req = new RequestGenerator(service,getParam1);
        
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	
	
	public RequestGenerator CreditPhonePeSide(String randomTransactionId,String UIDX,String amount)
	{
		
		System.out.println("transajhkjsahcs====" +  randomTransactionId);
		System.out.println("amount ====" +  amount);

		String Sha= computeSha(merchantId+randomTransactionId+amount+salt);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PHONEPE, APINAME.PHONEPESIDECREDIT,init.Configurations,new String[]{randomTransactionId,UIDX,amount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("X-VERIFY", Sha);
		getParam1.put("X-SALT-INDEX", "1");
		getParam1.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJwYXltZW50cyIsImV4cCI6MzMwMzE3OTk4ODYsImlhdCI6MTQ3NDg5MTA4Niwic3ViIjoiTTIzMDYxNjA0ODMyMjA2NzU1NzkxNDAiLCJyb2xlIjoibWVyY2hhbnQiLCJ0eXBlIjoic3RhdGljIn0.HjXjJHoNt_44jEttUeBt-8WtcVqqzEB8bBaDc1JUSViWDvDsTYHlW8VoHuC-1qdL5qzPgo_9qDJ5HA234OdBng");
		System.out.println(getParam1);
        RequestGenerator req = new RequestGenerator(service,getParam1);
        
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator DebitPhonePeSide(String randomTransactionId,String UIDX,String amount)
	{
		
		System.out.println("transajhkjsahcs====" +  randomTransactionId);
		System.out.println("amount ====" +  amount);

		String Sha= computeSha(merchantId+randomTransactionId+amount+salt);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PHONEPE, APINAME.PHONEPESIDEDEBIT,init.Configurations,new String[]{randomTransactionId,UIDX,amount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("X-VERIFY", Sha);
		getParam1.put("X-SALT-INDEX", "1");
		getParam1.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtZXJjaGFudC1zZXJ2aWNlIiwiZXhwIjozMzAyMzQ5OTI5MCwiaWF0IjoxNDY2NTkwNDkwLCJzdWIiOiJtZXJjaGFudC1zZXJ2aWNlLW15bnRyYS1hdXRob3JpemF0aW9uLWtleSIsInJvbGUiOiJjb25zdW1lciIsInR5cGUiOiJzdGF0aWMifQ.cNbEBdVMoXVyq71fts7PWjlsB6NMgKnK1LinYbTRqFHfp0Xo4cKHOy4_CWt8IrQ_PfmzXRPIxmLYUfrqv_8CFw");
		System.out.println(getParam1);
        RequestGenerator req = new RequestGenerator(service,getParam1);
        
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator CreditBackToSourcePhonePeSide(String randomTransactionId,String UIDX,String amount,String providerRefereceID)
	{
		
		System.out.println("transajhkjsahcs====" +  randomTransactionId);
		System.out.println("amount ====" +  amount);

		String Sha= computeSha(merchantId+randomTransactionId+amount+salt);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PHONEPE, APINAME.PHONEPESIDEBACKTOSOURCE,init.Configurations,new String[]{randomTransactionId,UIDX,amount,providerRefereceID},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("X-VERIFY", Sha);
		getParam1.put("X-SALT-INDEX", "1");
		getParam1.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtZXJjaGFudC1zZXJ2aWNlIiwiZXhwIjozMzAyMzQ5OTI5MCwiaWF0IjoxNDY2NTkwNDkwLCJzdWIiOiJtZXJjaGFudC1zZXJ2aWNlLW15bnRyYS1hdXRob3JpemF0aW9uLWtleSIsInJvbGUiOiJjb25zdW1lciIsInR5cGUiOiJzdGF0aWMifQ.cNbEBdVMoXVyq71fts7PWjlsB6NMgKnK1LinYbTRqFHfp0Xo4cKHOy4_CWt8IrQ_PfmzXRPIxmLYUfrqv_8CFw");
		System.out.println(getParam1);
        RequestGenerator req = new RequestGenerator(service,getParam1);
        System.out.println("payload---->>>>" + service.Payload);
        
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
//	public RequestGenerator checkBalance_PPS(String UIDX)
//	{
//		
//		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.WALLETBALANCEPPS,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		AssertJUnit.assertEquals("Response is not 200 OK", 200,
//				req.response.getStatus());
//		return req; 
//	}
	
	public RequestGenerator migrateStatus(String UIDX)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.MIGRATESTATUS,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Migrate status Service URL --->> \n" + service.URL);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	
	
	
	public RequestGenerator creditPPS(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITWALLETPPS,init.Configurations,new String[]{checksum, ppsId, ppsTransactionID, orderId, amount, customerId},PayloadType.JSON,PayloadType.JSON);
		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req;
	}
	
	
	public RequestGenerator creditPPSFLoat(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITWALLETPPS,init.Configurations,new String[]{checksum, ppsId, ppsTransactionID, orderId, amount, customerId},PayloadType.JSON,PayloadType.JSON);
		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req;
	}
	
	public RequestGenerator debitPhonePe(String UIDX,String DebitAmount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.DEBITPHONEPE,init.Configurations,new String[]{UIDX,DebitAmount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator creditPhonePe(String UIDX,String creditAmount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITPHONEPE,init.Configurations,new String[]{UIDX,creditAmount},PayloadType.JSON,PayloadType.JSON);
		
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator voidPhonePe(String UIDX,String creditAmount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITPHONEPE,init.Configurations,new String[]{UIDX,creditAmount},PayloadType.JSON,PayloadType.JSON);
		
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	//public RequestGenerator transactionStatus()
	
	public String computeSha(String key) {
        String hashValue = Hashing.sha256().hashString(key, StandardCharsets.UTF_8).toString();
        return hashValue;
    }
	
	public RequestGenerator debitPPS(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.DEBITWALLETPPS,init.Configurations,new String[]{checksum,ppsID,ppsTransactionID,orderId,amount,customerID},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload debitPPS \n" + service.Payload );

		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("PAyload debitPPS response \n" + req.respvalidate.returnresponseasstring() );

		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator debitNormal(String UIDX, String amount, String businessProcess, String itemType )
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.NORMALDEBIT,init.Configurations,new String[]{UIDX,amount,businessProcess,itemType},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload debit \n" + service.Payload );

		HashMap getParam1 = new HashMap();
		getParam1.put("user", "ramakrishna.g@myntra.com");
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		System.out.println("PAyload debit response \n" + req.respvalidate.returnresponseasstring() );

		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator creditNormal(String UIDX, String amount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.NORMALCREDIT,init.Configurations,new String[]{UIDX,amount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "ramakrishna.g@myntra.com");
		System.out.println("PAyload credit \n" + service.Payload );
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		System.out.println("PAyload credit response \n" + req.respvalidate.returnresponseasstring() );
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req;
	}
	
	public RequestGenerator debitNormalNegative(String UIDX, String amount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.NORMALDEBIT,init.Configurations,new String[]{UIDX,amount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "ramakrishna.g@myntra.com");
		RequestGenerator req = new RequestGenerator(service,getParam1);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req;
	}
	
	public RequestGenerator refundPPS(String checksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload refund \n" + service.Payload );

		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("PAyload refund response \n" + req.respvalidate.returnresponseasstring() );

		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator voidPPS(String checksum,String ppsId,String ppsTransactionID,String clientTx)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,clientTx},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload Void \n" + service.Payload );

		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("PAyload Void response \n" + req.respvalidate.returnresponseasstring() );

		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public RequestGenerator withDraw(String UIDX, String emailId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.WITHDRAWPHONEPE,init.Configurations,new String[]{UIDX,emailId},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload WithDraw \n" + service.Payload );

		
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("PAyload WithDraw response \n" + req.respvalidate.returnresponseasstring() );

		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req;  
	}
	
	
	public RequestGenerator walletLogs(String UIDX, String limit, String offSet)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.WALLETTXLOGS,init.Configurations,PayloadType.JSON,new String[]{UIDX, limit, offSet},PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	

	public RequestGenerator creditSuggest(String UIDX, String ph)
	{
		String th =		"\"PHONEPE\"";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITSUGGEST,init.Configurations,PayloadType.JSON,new String[]{UIDX,th },PayloadType.JSON);
		System.out.println("PRINT PHONE PE SERVICE URL \n" + service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 

	}
	
	
	public RequestGenerator debitSuggest(String UIDX)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.DEBITSUGGEST,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
		System.out.println("Print Debit suggest URL--->> \n"+ service.URL);
		System.out.println("Print Debit suggest Payload--->> \n"+ service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}
	
	public String getRefundChecksum(String checksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUNDCASHBACKPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service.URL);
		System.out.println("Refund payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("response ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}
	
	public String getCreditChecksum(String checksum, String ppsId, String ppsTransactionID,String orderId,String amount,String customerID)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMCREDITCASHBACKPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,orderId,amount,customerID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Credit chcksum Url------>>>>> "+service.URL);
		System.out.println("Credit payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("response ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}
	
	public String getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID)
	{	

		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMDEBITCASHBACKPPS,init.Configurations,new String[]{checksum,ppsID,ppsTransactionID,orderId,amount,customerID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Debit chcksum Url------>>>>> "+service.URL);
		System.out.println("debit payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println(" debit response ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}
	
	
	public RequestGenerator balanceCheck(String UIDX)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.BALANCECHECK,init.Configurations,PayloadType.JSON,new String[]{UIDX},PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
		return req; 
	}

	
	public String getVoidChecksum(String checksum,String ppsId,String ppsTransactionID,String clientTx)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMVOIDCASHBACKPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,clientTx}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service.URL);
		System.out.println("Void payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("response ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}
	
public String getDebitClientTransaction(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID) {
		
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.DEBITWALLETPPS,init.Configurations,new String[]{checksum,ppsID,ppsTransactionID,orderId,amount,customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Debit Url------>>>>> "+service.URL);
		System.out.println("DEBit payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("debit client id-===========================" + clientId );
		return clientId;
	}

public String getCreditClientTransaction(String checksum ,String ppsId, String ppsTransactionID ,String orderId, String amount, String customerID) {
	
	
	
	MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.CREDITWALLETPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,orderId,amount,customerID}, PayloadType.JSON,PayloadType.JSON);
	System.out.println("Credit Url------>>>>> "+service.URL);
	System.out.println("Credit payload------>>>>> "+service.Payload);

	RequestGenerator req = new RequestGenerator(service);		
	String response = req.returnresponseasstring();
	System.out.println("response of creditPPs ------ >>>" + response);
	String jsonRes = req.respvalidate.returnresponseasstring();
	String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("Credit Tx ID -===========================" + clientId );
	return clientId;
}

public String generateRandomString()
    {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
        int randomLimitedInt = leftLimit + (int) 
        (new Random().nextFloat() * (rightLimit - leftLimit));
        buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        System.out.println(generatedString);
        return generatedString;
    }
	
private String generateRandomNumber()
{
	Random randomno = new Random();
	int number = randomno.nextInt(10000);
	String randomNumber = String.valueOf(number);
	return randomNumber;
}
	
}
