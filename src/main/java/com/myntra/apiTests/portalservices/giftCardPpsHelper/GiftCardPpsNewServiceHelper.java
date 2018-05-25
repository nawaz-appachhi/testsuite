package com.myntra.apiTests.portalservices.giftCardPpsHelper;

import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.legolas.Commons;

public class GiftCardPpsNewServiceHelper extends CommonUtils{
	Commons commonUtil = new Commons();

	static Initialize init = new Initialize("/Data/configuration");
	
	public RequestGenerator checkGiftcardBalance(String type, String cardNumber,
			String pin, String login){

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTCARDNEWBALANCE,
				init.Configurations, new String[] { type, cardNumber, pin,
						login }, PayloadType.JSON, PayloadType.JSON);

//		HashMap getParam = new HashMap();
//		getParam.put("user", "jitender.kumar1@myntra.com");
		System.out.println("Check Balance service URl---> \n" + service.URL);
		System.out.println("Request Payload--->>" + service.Payload);
		return new RequestGenerator(service);

	}
	
	public RequestGenerator getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMDEBITGIFTCARD,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>>\n"+service.URL);
		System.out.println("DEBIT Debit CHECKSUM payload------>>>>>\n"+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");

		return new RequestGenerator(service,getParam); 
	}
	
	public RequestGenerator getAutoDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITCHECKSUMUSER,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Auto DEBIT CHEKSUM Url------>>>>>\n"+service.URL);
		System.out.println("Auto Debit CHECKSUM payload------>>>>>\n"+service.Payload);
		return new RequestGenerator(service); 
	}
	
	public RequestGenerator debitGiftCard(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITGIFTCARDNEW,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>>\n"+service.URL);
		System.out.println("DEBIT Debit CHECKSUM payload------>>>>>\n"+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");

		return new RequestGenerator(service,getParam); 
	}
	
	
	public String getGiftcardBalance(String type, String cardNumber, String pin,
			String login) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTCARDNEWBALANCE,
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
	
	public String GetDebitGiftCardClientTranscation(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String partner_name,
			String cardNumber, String pinNumber, String bill_amount) {

		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITGIFTCARDNEW,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						partner_name, cardNumber, pinNumber, bill_amount },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("DEBIT   Url------>>>>> "+service.URL);
		//System.out.println("DEBIT __   payload------>>>>> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Debit Response " + jsonRes);
		String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
        System.out.println("Client Debit tcn id -----  > > > > " + clientId);
		;
		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
		return clientId;
	}
	
	public RequestGenerator refundGiftCardAmount(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.REFUNDGIFTCARDQC,
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
	
	public RequestGenerator refundAutoGiftCardAmount(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name,
			String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.REFUNDGIFTCARDUSER,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Refund URL---- \n" + service.URL);
		System.out.println("Refund Payload ---- \n" + service.Payload);
		return new RequestGenerator(service); 
	}
	
	
	public RequestGenerator voidAutoGiftCardAmount(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name,
			String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTVOIDUSERTRANSACTION,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Void URL---- \n" + service.URL);
		System.out.println("Void Payload ---- \n" + service.Payload);
		return new RequestGenerator(service); 
	}
	
	
	
public RequestGenerator getChecksumDebitReq(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount){
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMDEBITGIFTCARD,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
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
public RequestGenerator getRefundGiftCardReq(String checksum, String ppsID,
		String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,
		String clientTransactionID, String partner_name, String cardNumber,
		String pinNumber,String bill_amount){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.REFUNDGIFTCARDQC,
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


public RequestGenerator giftCardPurchase(String id, String orderId,String sku, String mrp,String skuId,  String RecEMailId, String SenderEmail,String customizedMessage)
{

	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTCARDPURCHASEQC,
			init.Configurations, new String[] {id, orderId, sku, mrp, skuId, RecEMailId, SenderEmail,customizedMessage}, new String[] {orderId} , PayloadType.JSON,  PayloadType.JSON);

//	HashMap getParam = new HashMap();
//	getParam.put("user", "jitender.kumar1@myntra.com");
	System.out.println("Request Payload--->>" + service.Payload);
	return new RequestGenerator(service);

}

public RequestGenerator giftCardPurchaseReset(String id, String skuId)
{

	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTCARDRESET,
			init.Configurations, PayloadType.JSON ,new String[] { id, skuId}, PayloadType.JSON);
	System.out.println( "json paylod---- >" + service.Payload);

System.out.println( "json URL---- >" + service.URL);
//	HashMap getParam = new HashMap();
//	getParam.put("user", "jitender.kumar1@myntra.com");
//	System.out.println("Request Payload--->>" + service);
	return new RequestGenerator(service);

}


public RequestGenerator getDefaultChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMGIFTCARD,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
			System.out.println("default CHEKSUM Url------>>>>> "+service.URL);
			System.out.println("DEfault  CHECKSUM payload------>>>>> "+service.Payload);

	return new RequestGenerator(service); 

}

public RequestGenerator getAutoDefaultChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String bill_amount,String instrumentActionType){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMREFUNDGIFTCARDUSER,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,bill_amount,instrumentActionType}, PayloadType.JSON,PayloadType.JSON);
			System.out.println("default CHEKSUM Url------>>>>> "+service.URL);
			System.out.println("Default  CHECKSUM payload------>>>>> "+service.Payload);
	return new RequestGenerator(service); 

}

public RequestGenerator redeemGiftCard(String checksum, String ppsID,
		String ppsTransactionID, String orderId, String amount,
		String ppsType, String customerID, String partner_name,
		String cardNumber, String pinNumber, String bill_amount){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITGIFTCARDNEW,
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


public RequestGenerator redeemAutoGiftCard(String checksum, String ppsID,
		String ppsTransactionID, String orderId, String amount,
		String ppsType, String customerID, String partner_name,String bill_amount){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITGIFTCARDUSER,
			init.Configurations, new String[] { checksum, ppsID,
					ppsTransactionID, orderId, amount, ppsType, customerID,
					partner_name, bill_amount },
			PayloadType.JSON, PayloadType.JSON);
	System.out.println("Redeem auto giftcard Url------>>>>>\n"+service.URL);
	System.out.println("Redeem auto giftcard  payload------>>>>>\n"+service.Payload);
	return new RequestGenerator(service); 
	
}

public RequestGenerator getVoidTransactionReq(String checksum, String ppsID,
		String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,
		String clientTransactionID, String partner_name, String cardNumber,
		String pinNumber, String bill_amount){
	
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTVOIDTRANSACTIONQC,
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

public RequestGenerator getCardBlnceReq(String type, String cardNumber, String pin,String login){
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKCARDBALANCE,
			init.Configurations, new String[] { type, cardNumber, pin,
					login }, PayloadType.JSON, PayloadType.JSON);

	HashMap getParam = new HashMap();
	getParam.put("user", "manishkumar.gupta@myntra.com");
	System.out.println("Request Payload--->>" + service.Payload);
	RequestGenerator req = new RequestGenerator(service, getParam);
	
	return new RequestGenerator(service,getParam);
}


public RequestGenerator addGCtoUser(String gc_num, String pin, String uidx,String type)
{

		MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.ADDGIFTCARDTOUSR,init.Configurations, new String[] { gc_num, pin, uidx, type}, PayloadType.JSON, PayloadType.JSON);

//	HashMap getParam = new HashMap();
//	getParam.put("user", "jitender.kumar1@myntra.com");
	System.out.println("Request Payload--->>" + service.Payload);
	return new RequestGenerator(service);

}

public RequestGenerator getAllActiveCouponsBalanceReq(String uidx){

    MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GETALLACTIVECOUPONSBALANCE, init.Configurations, PayloadType.JSON, new String[] {uidx}, PayloadType.JSON);
    return new RequestGenerator(service);
}


public RequestGenerator getGiftCardAddedToUserAccount(String uidx){

    MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GETGIFTCARDADDEDTOUSERACCOUNT, init.Configurations, PayloadType.JSON, new String[] {uidx}, PayloadType.JSON);
    return new RequestGenerator(service);
}


public RequestGenerator getGiftCardAddedToUserAccountWithParam(String uidx, String isExpired, String offset,String limit) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GETGIFTCARDADDEDTOUSERACCOUNTWITHPARAM, init.Configurations, PayloadType.JSON, new String[] {uidx,isExpired,offset,limit}, PayloadType.JSON);
    return new RequestGenerator(service);
}

public RequestGenerator resetPin(String orderId,String orderLine,String emailBeforeReset,String emailAfterReset){

    //MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.RESETPIN, init.Configurations, new String[] {orderId,orderLine,email} , PayloadType.JSON,  PayloadType.JSON);
    MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.RESETPIN, init.Configurations, new String[] {}, new String[] {orderId,orderLine,emailBeforeReset,emailAfterReset} , PayloadType.JSON,  PayloadType.JSON);
    System.out.println("Request Payload--->>" + service.Payload);
    return new RequestGenerator(service);
}

public RequestGenerator getGCConfig(String configId,String senderName,String recipientName,String recipientEmail,String message,String updatedGCImage) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GETGCCONFIG, init.Configurations, PayloadType.JSON, new String[] {configId,senderName,recipientName,recipientEmail,message,updatedGCImage}, PayloadType.JSON);
    return new RequestGenerator(service);
}

public RequestGenerator getGCConfigWithWrongConfigId(String wrongConfigId) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GETGCCONFIG, init.Configurations, PayloadType.JSON, new String[] {wrongConfigId}, PayloadType.JSON);
    return new RequestGenerator(service);
}

public RequestGenerator createGCConfig(String senderName,String senderEmail,String recipientName, String recipientEmail, String message,String gcImage,String coverImage,String cardImage,String isPersonalised) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CREATEGCCONFIG,init.Configurations, new String[] { senderName,senderEmail,recipientName,recipientEmail,message,gcImage,coverImage,cardImage,isPersonalised}, PayloadType.JSON, PayloadType.JSON);
	return new RequestGenerator(service);
}

public RequestGenerator getEmailToUidx(String email) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA, APINAME.GETUIDX,init.Configurations, PayloadType.JSON, new String[] {email}, PayloadType.JSON);
	return new RequestGenerator(service);
}

public RequestGenerator updateGCConfig(String configId,String gcImage) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.UPDATEGCCONFIG,init.Configurations, new String[] {gcImage},new String[] {configId}, PayloadType.JSON, PayloadType.JSON);
	return new RequestGenerator(service);
}

public RequestGenerator updateGCConfigWithWrongConfigId(String configId,String gcImage) {
	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.UPDATEGCCONFIG,init.Configurations, new String[] {gcImage},new String[] {configId}, PayloadType.JSON, PayloadType.JSON);
	return new RequestGenerator(service);
}

public String getUidxFromEmail(String email)
{
	RequestGenerator req1=getEmailToUidx(email);
	Assert.assertEquals(req1.response.getStatus(), 200);
	String jsonRes1=req1.respvalidate.returnresponseasstring();
	String uidx=JsonPath.read(jsonRes1, "$.entry.uidx").toString();
	return uidx;
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

}
