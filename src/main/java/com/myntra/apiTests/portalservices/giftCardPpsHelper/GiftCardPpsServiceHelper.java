package com.myntra.apiTests.portalservices.giftCardPpsHelper;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.legolas.Commons;

import java.util.HashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class GiftCardPpsServiceHelper extends CommonUtils {
	Commons commonUtil = new Commons();

	static Initialize init = new Initialize("/Data/configuration");
	
	
	public RequestGenerator checkGiftcardBalance(String type, String cardNumber,
			String pin, String login){

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKCARDBALANCE,
				init.Configurations, new String[] { type, cardNumber, pin,
						login }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Request Payload--->>" + service.Payload);
		return new RequestGenerator(service,getParam); 

	}
	
	
	public RequestGenerator getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
		{
			
			MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMDEBITGIFT,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
			System.out.println("DEBIT CHEKSUM Url------>>>>>\n"+service.URL);
			System.out.println("DEBIT Debit CHECKSUM payload------>>>>>\n"+service.Payload);

			HashMap getParam = new HashMap();
			getParam.put("user","manishkumar.gupta@myntra.com");
	
			return new RequestGenerator(service,getParam); 
		}
	public RequestGenerator redeemGiftCard(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String partner_name,
			String cardNumber, String pinNumber, String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITGIFTCARD,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						partner_name, cardNumber, pinNumber, bill_amount },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("Redeem giftcard Url------>>>>>\n"+service.URL);
		System.out.println("Redeem giftcard  payload------>>>>>\n"+service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");

		return new RequestGenerator(service,getParam); 
		
	}
	
	
	public RequestGenerator refundGiftCardAmount(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, cardNumber,
						pinNumber, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");
		System.out.println("Refund URL---- \n" + service.URL);

		System.out.println("Refund Payload ---- \n" + service.Payload);

		return new RequestGenerator(service,getParam); 
		
	}
	
	public RequestGenerator getDefaultChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMGIFTCARD,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
				System.out.println("default CHEKSUM Url------>>>>> "+service.URL);
				System.out.println("DEfault  CHECKSUM payload------>>>>> "+service.Payload);

				HashMap getParam = new HashMap();
				getParam.put("user","manishkumar.gupta@myntra.com");

		
		return new RequestGenerator(service,getParam); 

	}
	

	
	
	
	public String getBalance(String type, String cardNumber, String pin,
			String login) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKCARDBALANCE,
				init.Configurations, new String[] { type, cardNumber, pin,
						login }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("response for check balance" + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..data.amount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Messgage- >>>>" + msg1);
		return msg1;
	}
	
	public RequestGenerator getRefundGiftCardReq(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber,String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, cardNumber,
						pinNumber, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Service request --- ? " + service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		
		return new RequestGenerator(service,getParam); 
	}
	
	public RequestGenerator getVoidTransactionReq(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.GIFTVOIDTRANSACTION,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, cardNumber,
						pinNumber, bill_amount }, new String[] { customerID },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("VOid Url------>>>>> " + service.URL);
		System.out.println("voidpayload------>>>>> " + service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		
		return new RequestGenerator(service,getParam);
	}
	
	
	public RequestGenerator getChecksumDebitReq(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMDEBITGIFT,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsoncheckRes = req.respvalidate.returnresponseasstring();
		String check = JsonPath.read(jsoncheckRes, "$..checksum").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Checksum ---> resp: "+ check);
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITGIFTCARD,
				init.Configurations, new String[] { check, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						partner_name, cardNumber, pinNumber, bill_amount },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam1 = new HashMap();
		getParam1.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Service request --- ? " + service1.Payload);
		RequestGenerator req1 = new RequestGenerator(service1, getParam);
		
		return new RequestGenerator(service, getParam);
	}

	
	public RequestGenerator getDebitChckRespReq(String checksum, String ppsTransactionID,String clientTransactionId, String ppsID,
			String orderId,String dup, String amt,String  customerID,String txStatus,String coments){
		
		MyntraService service2 = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITCHCKSUMRESPONSE,
				init.Configurations, new String[] { checksum, ppsTransactionID,clientTransactionId, ppsID,
						 orderId,dup, amt, customerID,txStatus,coments},
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam2 = new HashMap();
		getParam2.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Service request payload chcks--- ? " + service2.Payload);
		RequestGenerator req2 = new RequestGenerator(service2, getParam2);
		
		return new RequestGenerator(service2,getParam2);
	}
	
	public RequestGenerator getCardBlnceReq(String type, String cardNumber, String pin,String login){
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKCARDBALANCE,
				init.Configurations, new String[] { type, cardNumber, pin,
						login }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Request Payload--->>" + service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		
		return new RequestGenerator(service,getParam);
	}
	
	
}
