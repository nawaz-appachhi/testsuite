package com.myntra.apiTests.portalservices.all;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.GiftCardNewServiceDP;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.giftCardPpsHelper.GiftCardPpsNewServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftCardServicesTest extends BaseTest{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(GiftCardServicesTest.class);
	static GiftCardPpsNewServiceHelper GiftCardNewhelper = new GiftCardPpsNewServiceHelper();
	CommonUtils commonUtil= new CommonUtils();
	String configId,updatedGCImage,customizedMessage;

	@Test(groups = {  "Sanity", "MiniRegression", "Regression",
	"ExhaustiveRegression" }, dataProvider = "checkBalance",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance of the gift card. \n 2. Verify 200 status response.")
	public void GiftServiceNew_checkBalance(String type, String cardNumber,
			String pin, String login) {
				
		RequestGenerator checkBalance = GiftCardNewhelper.checkGiftcardBalance(type, cardNumber, pin, login);

		String jsonRes = checkBalance.respvalidate.returnresponseasstring();
		log.info("response for check balance--->" + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		log.info("Messgage- >>>>" + msg1);

		Assert.assertEquals(msg1, "Check Balance Successful","Check balance is not successful");
		Assert.assertEquals(200, checkBalance.response.getStatus(),"Check balance is not working");

}

@Test(groups = { "Regression"}, dataProvider = "checkBalanceExpired",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance of the gift card. \n 2. Verify 200 status response.")
public void GiftServiceNew_checkBalanceExpiredCard(String type, String cardNumber,String pin, String login) 
{		
		RequestGenerator checkBalance = GiftCardNewhelper.checkGiftcardBalance(type, cardNumber, pin, login);

		String jsonRes = checkBalance.respvalidate.returnresponseasstring();
		log.info("response for check balance--->" + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		log.info("Messgage- >>>>" + msg1);
		Assert.assertEquals(msg1, "Card has expired","Card is not expired");
		Assert.assertEquals(checkBalance.response.getStatus(),200,"Check balance is not working");
}
	

@Test(groups = {  "Sanity", "Regression" }, dataProvider = "debitGiftCard",dataProviderClass=GiftCardNewServiceDP.class,description="\n 1. Check Balance before debiting giftcard \n 2. Get debit checksum \n 3. Apply Gift card \n 4. Get clientTransactionId \n 5. debit Amount \n 6. Verify amount before debit and after redeem gift card")
	public void GiftServiceNew_DebitGiftCard(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID, String partner_name,
		String cardNumber, String pinNumber, String bill_amount) {
	
	String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
			customerID);
	Float remainingBalance = Float.valueOf(Balance);
	log.info("Amount Before debited in card \n" + remainingBalance);
	RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
	String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();
	
	String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
	
	log.info("Checksum for debit: "+ checksum_Debit);
	RequestGenerator redeemCard = GiftCardNewhelper.debitGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
	
	String redeemCardResponse = redeemCard.returnresponseasstring();
	
	log.info("Debit Response:  " + redeemCardResponse);
	String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
	Float debitamount = (Float.valueOf(msg1)) / 100;
	String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
			pinNumber, customerID);
	Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
	Assert.assertEquals(debitamount, remainingBalance - balanceAfterDebit_flt,"The amount after redeem is not matching");
	log.info("Balance after debit ===\n" + balanceAfterDebit_flt);
}
	

@Test(groups = {"Regression"}, dataProvider = "debitGiftCardNegativebalance",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance before redeem \n 2. Get debit checksum \n 3. Redeem gift card with negative balance \n 4. Get msg from redeemGiftCard response \n 5. Verify msg with string ->Amount is incorrect. \n 6. Verify 200 status response code with redeem giftcard response.")
public void GiftBigService_DebitGiftCardNegativeBalance(String checksum,
	String ppsID, String ppsTransactionID, String orderId,
	String amount, String ppsType, String customerID,
	String partner_name, String cardNumber, String pinNumber,
	String bill_amount) {

String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,customerID);
Float remainingBalance = Float.valueOf(Balance);
//String remainingBalance = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
log.info("Main remaining balance: " + Balance);
RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

log.info("Checksum for debit: "+ checksum_Debit);
RequestGenerator redeemCard = GiftCardNewhelper.debitGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);

String redeemCardResponse = redeemCard.returnresponseasstring();
log.info("response for redeemgift card \n"+redeemCardResponse);
String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
		.replace("[", "").replace("]", "").replace("\"", "").trim();
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
		pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
Assert.assertEquals(balanceAfterDebit_flt,remainingBalance, "Balance is not matching");
Assert.assertEquals(msg1,"Amount is incorrect.","Success with debit amount more than active balance");
String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
		.replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(textMessage,"TX_FAILURE","Txt Success with debit amount more than active balance");
Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}


@Test(groups = { "Regression" }, dataProvider = "redeemGiftCardLessThan100",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Get balance before redeem. \n 2. Get debit checksum. \n 3. Redeem gift card and get comments from redeemGiftCard response. \n 4. Get balance after redeem. \n 5. Verify balance before and after redeem of giftcard \n 6. Verify msg with string --> Amount less than min redeem limit. \n 7. Verify text status \n 8. Verify status code response for redeemGiftCard response. ")
public void GiftBigService_DebitGiftCardLessThan100(String checksum,
	String ppsID, String ppsTransactionID, String orderId,
	String amount, String ppsType, String customerID,
	String partner_name, String cardNumber, String pinNumber,
	String bill_amount) {

String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
		customerID);
Float remainingBalance = Float.valueOf(Balance);
log.info("Main remaining balance: " + Balance);
RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

log.info("checksum for debit: "+ checksum_Debit);
RequestGenerator redeemCard = GiftCardNewhelper.debitGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);

String redeemCardResponse = redeemCard.returnresponseasstring();
log.info("Response for redeem gift card \n" +redeemCardResponse);
String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
		.replace("[", "").replace("]", "").replace("\"", "").trim();

String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
		pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
Assert.assertEquals(balanceAfterDebit_flt, (remainingBalance-Float.valueOf(amount)/100),"Balance is not correct");
Assert.assertEquals(msg1,"Redeem Successful","Success with debit amount more than active balance");
String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
		.replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(textMessage,"TX_SUCCESS","Txt Success with debit amount more than active balance");
Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}
	

//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "debitGiftCardFloatingbalance",description="\n 1. Check Balance before debiting giftcard \n 2. Get debit checksum \n 3. Apply Gift card \n 4. Get clientTransactionId \n 5. debit Amount \n 6. Verify amount before debit and after redeem gift card")
//public void GiftServiceNew_DebitGiftCardFloating(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID, String partner_name,
//String cardNumber, String pinNumber, String bill_amount) {
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("AMount Before debited in card \n" + remainingBalance);
//RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();
//
//String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
//
//log.info("checksum for debit=====\n"+ checksum_Debit);
//RequestGenerator redeemCard = GiftCardNewhelper.debitGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//
//String redeemCardResponse = redeemCard.returnresponseasstring();
//String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//log.info("Debit Response======>> \n" + redeemCardResponse);
//String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//Float debitamount = (Float.valueOf(msg1)) / 100;
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//AssertJUnit.assertEquals(remainingBalance - balanceAfterDebit_flt,
//	debitamount, debitamount);
//log.info("Balance after debit ===\n" + balanceAfterDebit_flt);
//
//
//}


//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftCard", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
//public void GiftBigService_RefundGiftCard(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount,
//String ppsType, String customerID, String clientTransactionID, String partner_name, String cardNumber, String pinNumber,
//String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//
//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//String jsonRes = req.respvalidate.returnresponseasstring();
//
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//AssertJUnit.assertEquals(remainingBalance + debitAmount,
//	balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}
//
//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftCardMoreThanDebit",description="\n 1. Debit amount from gift card \n 2. Check balance \n 3. Refund gift card more than debit amount. \n 4. Get comments from refund giftcard response. \n 5. Get balance after debit \n 6. Verify remaining balance + debit amount matches with balance after debit.")
//public void GiftBigService_RefundGiftMoreThanDebit(String checksum,
//String ppsID, String ppsTransactionID, String orderId,
//String amount, String ppsType, String customerID,
//String clientTransactionID, String partner_name, String cardNumber,
//String pinNumber, String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//String jsonRes = req.respvalidate.returnresponseasstring();
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//// AssertJUnit.assertEquals(remainingBalance + debitAmount,
//// balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}
//
//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftCardLessThanDebit",description="\n 1. Debit amount from gift card. \n 2. check balance before debit. \n 3. Get refund gift card \n 4. Check balance after debit \n 5. verify remaining balance + debit amount matches with balance after debit.")
//public void GiftBigService_RefundGiftLessThanDebit(String checksum,
//String ppsID, String ppsTransactionID, String orderId,
//String amount, String ppsType, String customerID,
//String clientTransactionID, String partner_name, String cardNumber,
//String pinNumber, String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//
//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//
//String jsonRes = req.respvalidate.returnresponseasstring();
//
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//// AssertJUnit.assertEquals(remainingBalance + debitAmount,
//// balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}
//
//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftCardWithSameTXN")
//public void GiftBigService_RefundGiftWithSameClientId(String checksum,
//String ppsID, String ppsTransactionID, String orderId,
//String amount, String ppsType, String customerID,
//String clientTransactionID, String partner_name, String cardNumber,
//String pinNumber, String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//String jsonRes = req.respvalidate.returnresponseasstring();
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//
//String duplicateMsg = JsonPath.read(jsonRes, "$..duplicate").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//
//if (duplicateMsg.contains("true")) {
//
//AssertJUnit.assertEquals(remainingBalance + debitAmount,
//		balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//} else {
//System.out
//		.println("Next dataprovider will be run to verify the this testcase");
//AssertJUnit.assertEquals(remainingBalance + debitAmount,
//		balanceAfterDebit_flt, balanceAfterDebit_flt);
//}
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}


//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftCard", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
//public void GiftBigService_RefundGiftCard(String checksum, String ppsID,
//String ppsTransactionID, String orderId, String amount,
//String ppsType, String customerID, String clientTransactionID,
//String partner_name, String cardNumber, String pinNumber,
//String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//
//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//String jsonRes = req.respvalidate.returnresponseasstring();
//
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//AssertJUnit.assertEquals(remainingBalance + debitAmount,
//	balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}


@Test(groups = { "Regression" }, dataProvider = "giftCardnewPurchaseDuplicate",dataProviderClass=GiftCardNewServiceDP.class,dependsOnMethods="GCPersonalisationService_CreateGCConfig", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
public void GiftBigService_GiftcardPurchaseDuplicateOrderid(String id, String orderId,String sku, String mrp,String skuId, String RecEMailId, String SenderEmail ) {

RequestGenerator req = GiftCardNewhelper.giftCardPurchase(id, orderId, sku, mrp, skuId, RecEMailId, SenderEmail,customizedMessage);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Response of purchase api: " + jsonRes);
String msg1= JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
String status= JsonPath.read(jsonRes, "$.status..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

Assert.assertEquals(msg1,"GiftCard purchase Successful","GiftCard purchase is not successful");
Assert.assertEquals(status,"SUCCESS","GiftCard purchase status is not successful");
Assert.assertEquals(req.response.getStatus(),200,"GiftCard purchase is not working");
}


@Test(groups = { "Regression" }, dataProvider = "giftCardnewPurchaseNegative",dataProviderClass=GiftCardNewServiceDP.class, dependsOnMethods="GCPersonalisationService_CreateGCConfig",description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
public void GiftBigService_GiftcardPurchaseNegativeAmount(String id, String orderId,String sku, String mrp, String skuId, String RecEMailId, String SenderEmail ) {

RequestGenerator req = GiftCardNewhelper.giftCardPurchase(id, orderId, sku, mrp, skuId, RecEMailId, SenderEmail,customizedMessage);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);

//RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
String jsonRes = req.respvalidate.returnresponseasstring();

log.info("Response of purchase api: " + jsonRes);


String msg1= JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
String status= JsonPath.read(jsonRes, "$.status..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

Assert.assertEquals(msg1,"GiftCard purchase Successful","GiftCard purchase is not successful");
Assert.assertEquals(status,"SUCCESS","GiftCard purchase status is not successful");
Assert.assertEquals(req.response.getStatus(),200,"GiftCard purchase is not working");
}
//
//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "giftCardnewPurchaseResetPin", description ="\n 1.purchase a GC \n 2. Reset of card pin   \n 3. Verify 200 status response code for gift card purchase")
//public void GiftBigService_GiftcardPurchaseReset(String id, String orderId,String sku, String mrp, String skuId, String RecEMailId, String SenderEmail ) {
//
//
//RequestGenerator req = GiftCardNewhelper.giftCardPurchase(id, orderId, sku, mrp, skuId, RecEMailId, SenderEmail);
////Float remainingBalance = Float.valueOf(Balance);
////log.info("Main remaining balance =====" + remainingBalance);
//
////RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//String jsonRes = req.respvalidate.returnresponseasstring();
//
//log.info("response of purchase api-->>" + jsonRes);
//
//
//String msg1= JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//String status= JsonPath.read(jsonRes, "$.status..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//AssertJUnit.assertEquals("GiftCard purchase is not successful", msg1,"GiftCard purchase Successful");
//AssertJUnit.assertEquals("GiftCard purchase status is not successful", status,"SUCCESS");
//
//
//AssertJUnit.assertEquals("GiftCard purchase is not working", 200, req.response.getStatus());
//
//RequestGenerator resetPin = GiftCardNewhelper.giftCardPurchaseReset(id, skuId);
//log.info("RESPONSE RESET \n"+resetPin.respvalidate.returnresponseasstring());
//String balResponse = resetPin.respvalidate.returnresponseasstring();
//log.info("\nPrinting ResetPin API response : \n\n"+balResponse+"\n");
////String totalBalance = JsonPath.read(balResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//
//
//
//
//
//}


@Test(groups = {  "Sanity", "Regression" }, dataProvider = "checkBalance",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance of the gift card. \n 2. Verify 200 status response.")
public void GiftBigService_checkBalance(String type, String cardNumber,
String pin, String login) {
RequestGenerator checkBalance = GiftCardNewhelper.checkGiftcardBalance(type, cardNumber, pin, login);

String jsonRes = checkBalance.respvalidate.returnresponseasstring();
log.info("response for check balance" + jsonRes);
String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Messgage: " + msg1);
Assert.assertEquals(msg1,"Check Balance Successful","unsuccessful");
Assert.assertEquals(checkBalance.response.getStatus(),200,"Check balance is not working");
}


@Test(groups = {  "Sanity","Regression" }, dataProvider = "redeemGiftCard",dataProviderClass=GiftCardNewServiceDP.class,description="\n 1. Check Balance before redeem giftcard \n 2. Get debit checksum \n 3. Redeem Gift card \n 4. Get clientTransactionId \n 5. Redeem Amount \n 6. Verify amount before redeem and after redeem gift card")
public void GiftBigService_DebitGiftCard(String checksum, String ppsID,
String ppsTransactionID, String orderId, String amount,
String ppsType, String customerID, String partner_name,
String cardNumber, String pinNumber, String bill_amount) {

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Amount before debited in card \n" + remainingBalance);
RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

log.info("Checksum for debit: "+ checksum_Debit);
RequestGenerator redeemCard = GiftCardNewhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);

String redeemCardResponse = redeemCard.returnresponseasstring();
String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

log.info("Debit Response: " + redeemCardResponse);
String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
Float debitamount = (Float.valueOf(msg1)) / 100;
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("Balance after debited: " + balanceAfterDebit_flt);
Assert.assertEquals(debitamount, remainingBalance - balanceAfterDebit_flt,"Amount is not matching");

checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();
RequestGenerator getDefaultCHeksum = GiftCardNewhelper.getDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientId, partner_name, cardNumber, pinNumber, bill_amount);
String getDefaultChecksumResponse = getDefaultCHeksum.returnresponseasstring();
log.info("Response default checksum\n" + getDefaultChecksumResponse);
String defaulchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
log.info("Default checksum: " + defaulchecksum);
RequestGenerator refundAMount = GiftCardNewhelper.refundGiftCardAmount(defaulchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientId, partner_name, cardNumber, pinNumber, bill_amount);

String refundAmountResp = refundAMount.returnresponseasstring();
log.info("Refund amount response: " +refundAmountResp );


String balanceAfterRefund = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,customerID);

log.info("Balance after refunding: " + balanceAfterRefund );
Assert.assertEquals(Float.valueOf(balanceAfterRefund), remainingBalance,"Amount is not matching");
Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}

//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "redeemGiftCardMoreThanBalance", description="\n 1. Check balance before debit \n 2. Get debit checksum \n 3. Redeem giftcard more than balance \n 4.Get Balance \n 5. Verify msg --> Success with debit amount more than active balance \n 6. Verify 200 status resoponse code.")
//public void GiftBigService_DebitGiftCardMoreThanBalance(String checksum,
//String ppsID, String ppsTransactionID, String orderId,
//String amount, String ppsType, String customerID,
//String partner_name, String cardNumber, String pinNumber,
//String bill_amount) {
//
//String Balance = GiftCardNewhelper.getBalance(partner_name, cardNumber, pinNumber,
//	customerID);
//Float remainingBalance = Float.valueOf(Balance);
//RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();
//
//String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
//
//log.info("checksum for debit=====\n"+ checksum_Debit);
//RequestGenerator redeemCard = GiftCardNewhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//
//String redeemCardResponse = redeemCard.returnresponseasstring();
//log.info("response for redeemgift card \n"+redeemCardResponse);
//String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getBalance(partner_name, cardNumber,
//	pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//AssertJUnit.assertEquals(remainingBalance, balanceAfterDebit_flt,
//	balanceAfterDebit_flt);
//AssertJUnit.assertEquals(
//	"Success with debit amount more than active balance", msg1,
//	"Balance is insufficient.");
//String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
//	.replace("[", "").replace("]", "").replace("\"", "").trim();
//AssertJUnit.assertEquals(
//	"Txt Success with debit amount more than active balance",
//	textMessage, "TX_FAILURE");
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	redeemCard.response.getStatus());
//}

@Test(groups = { "Regression" }, dataProvider = "redeemGiftCardNegativebalance",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance before redeem \n 2. Get debit checksum \n 3. Redeem gift card with negative balance \n 4. Get msg from redeemGiftCard response \n 5. Verify msg with string ->Amount is incorrect. \n 6. Verify 200 status response code with redeem giftcard response.")
public void GiftBigService_DebitGiftCardNegativeBalance1(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String partner_name, String cardNumber, String pinNumber,
String bill_amount) {

String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(Balance);
log.info("Main remaining balance: " + remainingBalance);
RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

log.info("checksum for debit: "+ checksum_Debit);
RequestGenerator redeemCard = GiftCardNewhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);

String redeemCardResponse = redeemCard.returnresponseasstring();
log.info("response for redeemgift card \n"+redeemCardResponse);
String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance);
Assert.assertEquals(msg1,"Amount is incorrect.","Success with debit amount more than active balance");
String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(textMessage,"TX_FAILURE","Txt Success with debit amount more than active balance");
Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}

@Test(groups = { "Regression" }, dataProvider = "redeemGiftCardLessThan100",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Get balance before redeem. \n 2. Get debit checksum. \n 3. Redeem gift card and get comments from redeemGiftCard response. \n 4. Get balance after redeem. \n 5. Verify balance before and after redeem of giftcard \n 6. Verify msg with string --> Amount less than min redeem limit. \n 7. Verify text status \n 8. Verify status code response for redeemGiftCard response. ")
public void GiftBigService_DebitGiftCardLessThan1001(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String partner_name, String cardNumber, String pinNumber,
String bill_amount) {

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Main remaining balance: " + remainingBalance);
RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

log.info("checksum for debit: "+ checksum_Debit);
RequestGenerator redeemCard = GiftCardNewhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);

String redeemCardResponse = redeemCard.returnresponseasstring();
log.info("Response for redeem gift card \n" +redeemCardResponse);
String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();

String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
Assert.assertEquals(balanceAfterDebit_flt, (remainingBalance-Float.valueOf(amount)/100),"Balance mismatch");
Assert.assertEquals(msg1,"Redeem Successful","Success with debit amount more than active balance");
String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(textMessage, "TX_SUCCESS","Txt Success with debit amount more than active balance");
Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}

@Test(groups = {  "Sanity", "Regression" }, dataProvider = "refundGiftCard",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
public void GiftBigService_RefundGiftCard1(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,
String ppsType, String customerID, String clientTransactionID,String partner_name, String cardNumber, String pinNumber,String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Main remaining balance: " + remainingBalance);

RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
String jsonRes = req.respvalidate.returnresponseasstring();

log.info("Refund Response " + jsonRes);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);

//AssertJUnit.assertEquals(remainingBalance + debitAmount,balanceAfterDebit_flt, balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
Assert.assertEquals(req.response.getStatus(),200,"redeemGiftCard is not working");
}

@Test(groups = { "Regression" }, dataProvider = "refundGiftCardMoreThanDebit",dataProviderClass=GiftCardNewServiceDP.class,description="\n 1. Debit amount from gift card \n 2. Check balance \n 3. Refund gift card more than debit amount. \n 4. Get comments from refund giftcard response. \n 5. Get balance after debit \n 6. Verify remaining balance + debit amount matches with balance after debit.")
public void GiftBigService_RefundGiftMoreThanDebit(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber,
String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Main remaining balance: " + remainingBalance);
RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Refund Response " + jsonRes);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After Refund -----------" + balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
Assert.assertEquals(req.response.getStatus(),200,"refundGiftCard is not working");
}

@Test(groups = {  "Regression" }, dataProvider = "refundGiftCardLessThanDebit",dataProviderClass=GiftCardNewServiceDP.class,description="\n 1. Debit amount from gift card. \n 2. check balance before debit. \n 3. Get refund gift card \n 4. Check balance after debit \n 5. verify remaining balance + debit amount matches with balance after debit.")
public void GiftBigService_RefundGiftLessThanDebit(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber,
String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(Balance);
log.info("Main remaining balance: " + remainingBalance);

RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String jsonRes = req.respvalidate.returnresponseasstring();

log.info("Refund Response " + jsonRes);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
Assert.assertEquals(req.response.getStatus(),200,"refundGiftCard is not working");
}

@Test(groups = {  "Regression" }, dataProvider = "refundGiftCardWithSameTXN",dataProviderClass=GiftCardNewServiceDP.class)
public void GiftBigService_RefundGiftWithSameClientId(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber,
String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(Balance);
log.info("Main remaining balance: " + remainingBalance);
RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Refund Response " + jsonRes);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);

String duplicateMsg = JsonPath.read(jsonRes, "$..duplicate").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();

if (duplicateMsg.contains("true")) {
	Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
} 
else {
log.info("Next dataprovider will be run to verify the this testcase");
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
}
Assert.assertEquals(req.response.getStatus(),200,"refundGiftCard is not working");
}

@Test(groups = { "Regression" }, dataProvider = "refundGiftOnDifferentCard",dataProviderClass=GiftCardNewServiceDP.class)
public void GiftBigService_RefundGiftOnDifferentCard(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber,
String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Main remaining balance: " + remainingBalance);

RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Refund Response " + jsonRes);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);
// AssertJUnit.assertEquals(remainingBalance + debitAmount,
// balanceAfterDebit_flt, balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
Assert.assertEquals(req.response.getStatus(),200,"refundGiftCard is not working");
}

@Test(groups = { "Regression" }, dataProvider = "refundGiftWithInvalid",dataProviderClass=GiftCardNewServiceDP.class)
public void GiftBigService_RefundGiftWithInvalid(String checksum,
String ppsID, String ppsTransactionID, String orderId,
String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber,
String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);
log.info("Main remaining balance: " + remainingBalance);

RequestGenerator req=GiftCardNewhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Refund response " + jsonRes);
// String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
// .replace("[", "").replace("]", "").replace("\"", "").trim();
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);

Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount,"Amount mismatch");
Assert.assertEquals(req.response.getStatus(),200,"refundGiftCard is not working");
}

//@Test(groups = {  "Sanity", "MiniRegression", "Regression",
//"ExhaustiveRegression" }, dataProvider = "refundGiftWithVoidedTxn", description="\n 1. Check balance before voided transaction. \n 2. Refund gift card. \n 3. Check balance after voided transaction. \n 4. Verify balance before transaction + debit amount matches with balance after debit .")
//public void GiftBigService_RefundVoidedTransaction(String checksum, String ppsID, String ppsTransactionID, String orderId,String amount, String ppsType, String customerID,String clientTransactionID, String partner_name, String cardNumber,
//String pinNumber, String bill_amount) {
//
//Float debitAmount = (Float.valueOf(amount)) / 100;
//
//String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
//Float remainingBalance = Float.valueOf(Balance);
//log.info("Main remaining balance =====" + remainingBalance);
//MyntraService service = Myntra.getService(
//	ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD, init.Configurations, new String[] { checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber,pinNumber, bill_amount }, PayloadType.JSON, PayloadType.JSON);
//
//HashMap getParam = new HashMap();
//getParam.put("user", "manishkumar.gupta@myntra.com");
//
//log.info("REFUND Url------>>>>> " + service.URL);
//log.info("REFUND  payload------>>>>> " + service.Payload);
//RequestGenerator req = new RequestGenerator(service, getParam);
//
//String jsonRes = req.respvalidate.returnresponseasstring();
//log.info("Refund Response " + jsonRes);
//String msg1 = JsonPath.read(jsonRes, "$..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
//Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
//log.info("AFter Refund -----------" + balanceAfterDebit_flt);
//AssertJUnit.assertEquals(remainingBalance + debitAmount, balanceAfterDebit_flt, balanceAfterDebit_flt);
//
//String txStatus = JsonPath.read(jsonRes, "$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", txStatus);
//// AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);
//AssertJUnit.assertEquals("Cancel Redeem Successful",msg1);
//log.info("succesfull");
//
//AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//	req.response.getStatus());
//}

@Test(groups = {  "Regression" }, dataProvider = "giftVoidTransaction",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Check balance before void transaction. \n 2. Get void transaction request. \n 3. Check balance after void transaction. \n 4. Verify remaining balance + debit amount matching with balance after debit amount.")
public void GiftBigService_VoidTransaction(String checksum, String ppsID,
String ppsTransactionID, String orderId, String amount,
String ppsType, String customerID, String clientTransactionID,
String partner_name, String cardNumber, String pinNumber,
String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);

RequestGenerator req=GiftCardNewhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String response = req.returnresponseasstring();
log.info("Response: " + response);

String jsonRes = req.respvalidate.returnresponseasstring();
String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
	.replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Void message: " + msg1);
log.info("Void response: " + response);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,
	pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After void: " + balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount);
Assert.assertEquals(msg1,"TX_SUCCESS");
}


@Test(groups = { "Regression" }, dataProvider = "giftVoidTransactionOnAlreadyVoidedTxn",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1.Check balance before void transaction. \n 2. Create void transaction on already voided transaction. \n 3. Check balance after void transaction. \n 4. Verify remaining balance + debit amount matching with balance after debit. ")
public void GiftBigService_VoidTxnOnAlreadyVoidedTxn(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,String clientTransactionID, String partner_name, String cardNumber,String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
Float remainingBalance = Float.valueOf(balance);
MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.GIFTVOIDTRANSACTIONQC,init.Configurations, new String[] { checksum, ppsID,ppsTransactionID, orderId, amount, ppsType, customerID,clientTransactionID, partner_name, cardNumber,pinNumber, bill_amount }, new String[] { customerID },PayloadType.JSON, PayloadType.JSON);
log.info("Void url: " + service.URL);
log.info("Void payload: " + service.Payload);
HashMap<String,String> getParam = new HashMap<String,String>();
getParam.put("user", "manishkumar.gupta@myntra.com");
RequestGenerator req = new RequestGenerator(service, getParam);
String response = req.returnresponseasstring();


String jsonRes = req.respvalidate.returnresponseasstring();
String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Void message: " + msg1);
log.info("Void response: " + response);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After void: " + balanceAfterDebit_flt);
AssertJUnit.assertEquals(remainingBalance + debitAmount, balanceAfterDebit_flt, balanceAfterDebit_flt);

if (msg1.contains("TX_FAILURE")) {
String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(msg1, "TX_FAILURE","Getting Success msg");
Assert.assertEquals(comment,"A");
log.info("succesful");

}
Assert.assertEquals(req.response.getStatus(), 200,"Void transaction is not working");
}

@Test(groups = { "Regression" }, dataProvider = "giftVoidTransactionOnRefundTxn",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Check balance before debit. \n 2. Get void transaction request. \n 3. Debit amount form gift card. \n 4 Check balance after debit. \n 5. Verify remaining balance + debit amount matches with balance after debit. ")
public void GiftBigService_VoidTxnOnRefundTxn(String checksum,String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID,
String clientTransactionID, String partner_name, String cardNumber, String pinNumber, String bill_amount) {

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
Float remainingBalance = Float.valueOf(balance);

RequestGenerator req=GiftCardNewhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String response = req.returnresponseasstring();

String jsonRes = req.respvalidate.returnresponseasstring();
String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Void response: " + response);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After void: " + balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount);
Assert.assertEquals(msg1, "TX_FAILURE","successful");
Assert.assertEquals(req.response.getStatus(),200,"Void on refund is not working");
}


@Test(groups = { "Regression" }, dataProvider = "giftVoidTransactionWithInvalidTxn",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Get balance before void transaction. \n 2. Get void transaction. \n 3. Get balance after void transction. \n 4. Verify remaining balance + debit amount matches with balance after void debit transaction. \n 5. Verify 200 status response code for redeem gift card")
public void GiftBigService_VoidTransactionWithInvalidTxn(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID, String clientTransactionID, String partner_name, String cardNumber, String pinNumber, String bill_amount) 
{

Float debitAmount = (Float.valueOf(amount)) / 100;

String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,
	customerID);
Float remainingBalance = Float.valueOf(balance);

RequestGenerator req=GiftCardNewhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);

String response = req.returnresponseasstring();

String jsonRes = req.respvalidate.returnresponseasstring();
// String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
// .replace("[", "").replace("]", "").replace("\"", "").trim();
// log.info("Void Msg------>>>>> " + msg1);
log.info("Void response: " + response);
String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber, customerID);
Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
log.info("After refund: " + balanceAfterDebit_flt);
Assert.assertEquals(balanceAfterDebit_flt, remainingBalance + debitAmount);
String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(msg1,"TX_FAILURE");
Assert.assertEquals(req.response.getStatus(),200,"Void service is not working");
}


@Test(groups = { "Regression" }, dataProvider = "checkBalanceExpired",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Get card balance \n 2. Verify status msg from cardbalance response with --> Failure to check balance for expiry cards.")
public void GiftBigService_checkBalanceForExpiryCard(String type, String cardNumber,
String pin, String login) {

RequestGenerator req= GiftCardNewhelper.checkGiftcardBalance(type, cardNumber, pin, login);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("response for check balance" + jsonRes);
String failureMesssage = JsonPath.read(jsonRes, "$..statusMessage").toString()
.replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Status Messgage: " + failureMesssage);
Assert.assertEquals(failureMesssage, "Card has expired","Unsuccessful");
Assert.assertEquals(req.response.getStatus(),200,"Check balance is not working");
}

@Test(groups = { "Regression" }, dataProvider = "checkBalanceWithWrongPinAndBlockCard",dataProviderClass=GiftCardNewServiceDP.class, description="\n 1. Get card balance \n 2. Verify status msg from cardbalance response with --> Failure to check balance for expiry cards.")
public void GiftBigService_checkBalanceWithWrongPinAndBlockCard(String type, String cardNumber,
String pin, String login) {
String jsonRes = null;
RequestGenerator req = null;
for(int i=0;i<3;i++)
{
req= GiftCardNewhelper.checkGiftcardBalance(type, cardNumber, pin, login);
jsonRes = req.respvalidate.returnresponseasstring();
}
log.info("response for check balance" + jsonRes);
String failureMesssage = JsonPath.read(jsonRes, "$..statusMessage").toString()
.replace("[", "").replace("]", "").replace("\"", "").trim();
log.info("Status Messgage: " + failureMesssage);
String statusType=JsonPath.read(jsonRes, "$..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();;
Assert.assertEquals(statusType, "ERROR","GiftCard purchase is not successful");
Assert.assertEquals(failureMesssage, "Your card has been temporarily deactivated for the day due to multiple attempts with invalid PIN.Please retry tomorrow","Card is not blocked after 3 attempts");
Assert.assertEquals(req.response.getStatus(),200,"Check balance is not working");
}


@Test(groups = {  "Sanity", "Regression" }, dataProvider = "giftCardnewPurchaseSchema",dataProviderClass=GiftCardNewServiceDP.class,dependsOnMethods="GCPersonalisationService_CreateGCConfig", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
public void GiftBigService_GiftcardPurchaseSchema(String id, String orderId,String sku, String mrp, String skuId, String RecEMailId, String senderEmail) {

RequestGenerator req = GiftCardNewhelper.giftCardPurchase(id, orderId, sku, mrp, skuId, RecEMailId, senderEmail,customizedMessage);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Response of purchase api-->>" + jsonRes);
String msg1= JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
String status= JsonPath.read(jsonRes, "$.status..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(msg1, "GiftCard purchase Successful","GiftCard purchase is not successful");
Assert.assertEquals(status, "SUCCESS","GiftCard purchase is not successful");
Assert.assertEquals(req.response.getStatus(),200,"GiftCard purchase is not working");
try {
	String jsonschema = new Toolbox()
			.readFileAsString("Data/SchemaSet/JSON/giftcard-purchase-schema.txt");
	log.info("Schema:\n " + jsonschema);
	List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(jsonRes, jsonschema);
	Assert.assertTrue(CollectionUtils.isEmpty(missingNodeList),missingNodeList + " Nodes are missing in Giftcard Purchase API response");
	} catch (Exception e) {
	e.printStackTrace();
	}
}

// Gift card management test cases
@Test(groups = {  "Sanity", "Regression" }, dataProvider = "addGCtoUsr",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1.Add Gift card to user \n 2.Validated status code, status message, and status type in status section \n 3. validate giftcard number, uidx, card balance in data section of the response.")
public void GiftBigService_AddGCToUser(String gc_num, String pin,String email , String type ) {
String expectedStatusCode="18001";
String expectedStatusMsg="Gift Card Addition Successful";
String expectedStatusType="SUCCESS";
RequestGenerator req1=GiftCardNewhelper.getEmailToUidx(email);
Assert.assertEquals(req1.response.getStatus(), 200);
String jsonRes1=req1.respvalidate.returnresponseasstring();
String uidx=JsonPath.read(jsonRes1, "$.entry.uidx").toString();
log.info("Uidx is :"+uidx);
RequestGenerator req = GiftCardNewhelper.addGCtoUser(gc_num, pin, uidx, type);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("Response of purchase api: " + jsonRes);
String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);

// Trying to add same gift card
expectedStatusCode="19001";
expectedStatusMsg="Gift Card has already been added.";
expectedStatusType="ERROR";
req = GiftCardNewhelper.addGCtoUser(gc_num, pin, uidx, type);
jsonRes = req.respvalidate.returnresponseasstring();
log.info("Response of purchase api: " + jsonRes);
actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
// Deleting the card so that it can be used for next run
String query1 = "DELETE FROM myntra_giftcard.user_giftcard_mapping where gc_number="+"'"+gc_num+"'"+";";
log.info("The query is : " + query1);
DBUtilities.exUpdateQuery(query1, "giftcard");
}


@Test(groups = { "Regression" }, dataProvider = "addWrongGCtoUsr",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1.Add Gift card to user \n 2.Validated status code, status message, and status type in status section \n 3. validate giftcard number, uidx, card balance in data section of the response.")
public void GiftBigService_AddWrongGCToUser(String gc_num, String pin,String email , String type,String expectedStatusCode,String  expectedStatusMessage) {
	
String expectedStatusType="ERROR";
String expectedTotalCount="0";
String uidx=GiftCardNewhelper.getUidxFromEmail(email);
RequestGenerator req = GiftCardNewhelper.addGCtoUser(gc_num, pin, uidx, type);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("response of purchase api-->>" + jsonRes);

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMessage);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);
}


@Test(groups = {  "Sanity", "MiniRegression", "Regression","ExhaustiveRegression" }, dataProvider = "getAllActiveCouponsBalance",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Check balance and other fields of the gift card and verify same in db. \n 2. Verify 200 status response.")
public void GCManagementService_GetAllActiveCouponsBalance(String email,String statusCode,String statusMessage,String statusType,String totalCount)
{
RequestGenerator req1=GiftCardNewhelper.getEmailToUidx(email);
Assert.assertEquals(req1.response.getStatus(), 200);
String jsonRes1=req1.respvalidate.returnresponseasstring();
String uidx=JsonPath.read(jsonRes1, "$.entry.uidx").toString();
log.info("Uidx is :"+uidx);
RequestGenerator req = GiftCardNewhelper.getAllActiveCouponsBalanceReq(uidx);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, statusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, statusMessage);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, statusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, totalCount);

String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
String actual_ActiveGiftCardsCount=JsonPath.read(jsonRes, "$.data.activeGiftCardsCount").toString();

String query1="select sum(balance) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now() group by uidx" + ";";
String query2="select count(uidx) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now()" + ";";
Map<String,Object> balanceObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query1,"giftcard");
String totalBalanceFromDB=balanceObjectFromDB.get("sum(balance)").toString();
Map<String,Object> countObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query2,"giftcard");
String activeGiftCardsCountFromDB=countObjectFromDB.get("count(uidx)").toString();
log.info("The amount from db is : " + totalBalanceFromDB);
Assert.assertEquals(Double.parseDouble(actual_TotalBalance), Double.parseDouble(totalBalanceFromDB), "Giftcard Balance is not matching");
Assert.assertEquals(Integer.parseInt(actual_ActiveGiftCardsCount), Integer.parseInt(activeGiftCardsCountFromDB), "Active Giftcard count is not matching");
}

@Test(groups = { "Regression" }, dataProvider = "getAllActiveCouponsBalanceNegativeCases",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Checking the negative cases \n 2. Verify 200 status response.")
public void GCManagementService_GetAllActiveCouponsBalanceNegativeCases(String uidx,String statusCode,String statusMessage,String statusType,String totalCount)
{
	
RequestGenerator req = GiftCardNewhelper.getAllActiveCouponsBalanceReq(uidx);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();

	
String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, statusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, statusMessage);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, statusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, totalCount);
}



@Test(groups = { "Sanity", "Regression" }, dataProvider = "getGiftCardAddedToUserAccount",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Check balance and other fields of the gift card and verify same in db. \n 2. Verify 200 status response.\n 3. Verify the order in which the gc numbers are coming in reponse")
public void GCManagementService_GetGiftCardAddedToUserAccount(String email) throws JSONException {
String uidx=GiftCardNewhelper.getUidxFromEmail(email);
log.info("Uidx is :"+uidx);
RequestGenerator req = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();	

String expectedStatusCode="18004";
String expectedStatusMsg="Get Gift Card added to user's account Successful";
String expectedStatusType="SUCCESS";
String expectedTotalCount="1";

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);

String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
String actual_ActiveGiftCardsCount=JsonPath.read(jsonRes, "$.data.activeGiftCardsCount").toString();

String query1="select sum(balance) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now() group by uidx" + ";";
String query2="select count(uidx) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now()" + ";";
Map<String,Object> balanceObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query1,"giftcard");
String totalBalanceFromDB=balanceObjectFromDB.get("sum(balance)").toString();
Map<String,Object> countObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query2,"giftcard");
String activeGiftCardsCountFromDB=countObjectFromDB.get("count(uidx)").toString();
Assert.assertEquals(Double.parseDouble(actual_TotalBalance), Double.parseDouble(totalBalanceFromDB), "Giftcard Balance is not matching");
Assert.assertEquals(Integer.parseInt(actual_ActiveGiftCardsCount), Integer.parseInt(activeGiftCardsCountFromDB), "Active Giftcard count is not matching");

String giftCardDetailsArray = JsonPath.read(jsonRes, "$.data").toString().replace("=", ":");
JSONObject giftCardObject=new JSONObject(giftCardDetailsArray);
JSONArray giftCardArray=giftCardObject.getJSONArray("giftCardDetails");

Assert.assertEquals(giftCardArray.length(), Integer.parseInt(activeGiftCardsCountFromDB));

// Checking the order of gc number
String query3="select gc_number from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now() order by created_on desc" + ";";
List<?> resultSetFromDB=DBUtilities.exSelectQuery(query3, "giftcard");
for(int i=0;i<giftCardArray.length();i++)
{
	JSONObject GCDetail = giftCardArray.getJSONObject(i);
	log.info("Content is : " + resultSetFromDB.get(i));
	Assert.assertTrue(resultSetFromDB.get(i).toString().contains(GCDetail.getString("giftCardNumber")),"Giftcard order is not correct");
}
}


/**
 *
 * @param isExpired
 * @param offset
 * @param limit
 */
@Test(groups = {  "Sanity", "Regression" }, dataProviderClass=GiftCardNewServiceDP.class,dataProvider = "getGiftCardAddedToUserAccountWithParam", description ="\n 1. Check balance and other fields of the gift card and verify same in db. \n 2. Verify 200 status response.\n 3. Verify the order in which the gc numbers are coming in reponse")
public void GCManagementService_GetGiftCardAddedToUserAccountWithParam(String email,String isExpired,String offset,String limit) throws JSONException {
	
String uidx=GiftCardNewhelper.getUidxFromEmail(email);
log.info("Uidx is :"+uidx);
RequestGenerator req2 = GiftCardNewhelper.getGiftCardAddedToUserAccountWithParam(uidx,isExpired,offset,limit);
Assert.assertEquals(req2.response.getStatus(), 200);
String jsonRes2=req2.respvalidate.returnresponseasstring();	

	
String expectedStatusCode="18004";
String expectedStatusMsg="Get Gift Card added to user's account Successful";
String expectedStatusType="SUCCESS";
String expectedTotalCount="1";

String actual_Statuscode=JsonPath.read(jsonRes2, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes2, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes2, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes2, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);




String query1="select sum(balance) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now()" + ";";
Map<String,Object> balanceObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query1,"giftcard");
String totalBalanceFromDB=balanceObjectFromDB.get("sum(balance)").toString();
String query2=null,query3=null;
if(isExpired.equalsIgnoreCase("true"))
	{
		String actual_ExpiredGiftCardsCount=JsonPath.read(jsonRes2, "$.data.expiredGiftCardsCount").toString();
		query2="select count(uidx) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry < now()" + ";";
		Map<String,Object> expiredGCObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query2,"giftcard");
		String expiredGiftCardsCountFromDB=expiredGCObjectFromDB.get("count(uidx)").toString();
		Assert.assertEquals(Integer.parseInt(actual_ExpiredGiftCardsCount), Integer.parseInt(expiredGiftCardsCountFromDB), "Active Giftcard count is not matching");
		query3="select gc_number from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry < now() order by created_on desc" + ";";
	}
	else
	{
		String actual_ActiveGiftCardsCount=JsonPath.read(jsonRes2, "$.data.activeGiftCardsCount").toString();
		String actual_TotalBalance=JsonPath.read(jsonRes2, "$.data.totalBalance").toString();
		query2="select count(uidx) from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now()" + ";";
		Map<String,Object> activeGCObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query2,"giftcard");
		String activeGiftCardsCountFromDB=activeGCObjectFromDB.get("count(uidx)").toString();
		Assert.assertEquals(Double.parseDouble(actual_TotalBalance), Double.parseDouble(totalBalanceFromDB), "Giftcard Balance is not matching");
		Assert.assertEquals(Integer.parseInt(actual_ActiveGiftCardsCount), Integer.parseInt(activeGiftCardsCountFromDB), "Active Giftcard count is not matching");
		query3="select gc_number from myntra_giftcard.user_giftcard_mapping where uidx="+"'"+uidx+"'"+ " and expiry > now() order by created_on desc" + ";";
	}

String giftCardDetailsArray = JsonPath.read(jsonRes2, "$.data").toString().replace("=", ":");
JSONObject giftCardObject=new JSONObject(giftCardDetailsArray);
JSONArray giftCardArray=giftCardObject.getJSONArray("giftCardDetails");

// Checking the order of gc number
	
List<?> resultSetFromDB=DBUtilities.exSelectQuery(query3, "giftcard");
for(int i=0;i<giftCardArray.length();i++)
{
	JSONObject GCDetail = giftCardArray.getJSONObject(i);
	log.info("Content is : " + resultSetFromDB.get(i));
	Assert.assertTrue(resultSetFromDB.get(i).toString().contains(GCDetail.getString("giftCardNumber")),"Giftcard order is not correct");
}
}


@Test(groups = {  "Sanity", "Regression" }, dataProvider = "resetPin",dataProviderClass=GiftCardNewServiceDP.class, description ="\\n 1. Check balance and other fields of the gift card and verify same in db. \\n 2. Verify 200 status response.")
	public void GCManagementService_ResetPin(String orderId,String orderLine,String emailBeforeReset,String emailAfterReset)
	{
		
	RequestGenerator req = GiftCardNewhelper.resetPin(orderId,orderLine,emailAfterReset,emailBeforeReset);
	Assert.assertEquals(req.response.getStatus(), 200);
	String jsonRes=req.respvalidate.returnresponseasstring();	

		
	String expectedStatusCode="11002";
	String expectedStatusMsg="Reset of card pin is Successful";
	String expectedStatusType="SUCCESS";
	String expectedTotalCount="1";

	String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
	Assert.assertEquals(actual_Statuscode, expectedStatusCode);
	String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
	Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
	String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
	Assert.assertEquals(actual_StatusType, expectedStatusType);
	String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
	Assert.assertEquals(actual_TotalCount, expectedTotalCount);


	String query1="select recipient_email from myntra_giftcard.giftcard_details where order_id="+"'"+orderId+"'"+ ";";
	Map<String,Object> balanceObjectFromDB = DBUtilities.exSelectQueryForSingleRecord(query1,"giftcard");
	String recipientEmailIdFromDB=balanceObjectFromDB.get("recipient_email").toString();
	log.info("The email from db is : " + recipientEmailIdFromDB);
	Assert.assertEquals(emailAfterReset, recipientEmailIdFromDB, "EmailId is not matching");
	//Resetting back the emailid
	RequestGenerator req1 = GiftCardNewhelper.resetPin(orderId,orderLine,emailBeforeReset,emailAfterReset);
	Assert.assertEquals(req1.response.getStatus(), 200);
	}


//TODO need to explore on custom payload and automate the scenario where request is only sent with email as pay load
@Test(groups = {  "Sanity", "Regression" }, dataProvider = "createGCConfig",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Creating config and verifying the status. \n 2. Verify 200 status response.")
public void GCPersonalisationService_CreateGCConfig(String senderName,String senderEmail,String recipientName,String recipientEmail,String message,String gcImage,String coverImage,String cardImage,String isPersonalised)
{
	
RequestGenerator req = GiftCardNewhelper.createGCConfig(senderName,senderEmail,recipientName,recipientEmail,message,gcImage,coverImage,cardImage,isPersonalised);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();	

	
String expectedStatusCode="11003";
String expectedStatusMsg="GiftCard config created successfully";
String expectedStatusType="SUCCESS";
String expectedTotalCount="1";

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);
configId=JsonPath.read(jsonRes, "$.data.configId").toString();
updatedGCImage=JsonPath.read(jsonRes, "$.data.gcImage").toString();
customizedMessage="\"{\\\"configId\\\": \\\""+configId+"\\\"}\"";
}	


@Test(groups = { "Regression" }, dataProvider = "createGCConfigNegative",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Creating config and verifying the status. \n 2. Verify 200 status response.")
public void GCPersonalisationService_CreateGCConfigNegative(String senderName,String senderEmail,String recipientName,String recipientEmail,String message,String gcImage,String coverImage,String cardImage,String isPersonalised)
{
	
RequestGenerator req = GiftCardNewhelper.createGCConfig(senderName,senderEmail,recipientName,recipientEmail,message,gcImage,coverImage,cardImage,isPersonalised);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();	

	
String expectedStatusCode="12009";
String expectedStatusMsg="Email is invalid";
String expectedStatusType="ERROR";
String expectedTotalCount="0";

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);
}	



@Test(groups = {  "Sanity", "Regression" }, dataProvider = "updateGCConfig",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Updating config and verifying the status. \n 2. Verify 200 status response.")
public void GCPersonalisationService_UpdateGCConfig(String gcImage)
{
log.info("The gc image is : " + gcImage);
log.info("The configId is : " + configId);
RequestGenerator req = GiftCardNewhelper.updateGCConfig(configId,gcImage);
Assert.assertEquals(req.response.getStatus(), 200);
String jsonRes=req.respvalidate.returnresponseasstring();	

	
String expectedStatusCode="11005";
String expectedStatusMsg="GiftCard config updated successfully";
String expectedStatusType="SUCCESS";
String expectedTotalCount="1";

String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
Assert.assertEquals(actual_Statuscode, expectedStatusCode);
String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
Assert.assertEquals(actual_StatusType, expectedStatusType);
String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
Assert.assertEquals(actual_TotalCount, expectedTotalCount);
updatedGCImage=JsonPath.read(jsonRes, "$.data.gcImage").toString();
Assert.assertEquals(updatedGCImage, gcImage,"GC Image is not updated successfully");
}	


@Test(groups = {  "Sanity", "Regression" },dependsOnMethods="GCPersonalisationService_CreateGCConfig", dataProvider = "giftCardnewPurchase",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Purchasing the giftcard. \n2. Verify 200 status response code for redeem gift card ")
public void GiftBigService_GiftcardPurchase(String id, String orderId,String sku, String mrp, String skuId, String RecEMailId, String SenderEmail ) {
log.info("ConfigId is :" + customizedMessage);
RequestGenerator req = GiftCardNewhelper.giftCardPurchase(id, orderId, sku, mrp, skuId, RecEMailId, SenderEmail,customizedMessage);
String jsonRes = req.respvalidate.returnresponseasstring();
log.info("response of purchase api-->>" + jsonRes);

String msg1= JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
String status= JsonPath.read(jsonRes, "$.status..statusType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
Assert.assertEquals(msg1, "GiftCard purchase Successful","GiftCard purchase is not successful");
Assert.assertEquals(status,"SUCCESS","GiftCard purchase status is not successful");
Assert.assertEquals(req.response.getStatus(),200,"GiftCard purchase is not working");
}

@Test(groups = {  "Sanity", "MiniRegression", "Regression","ExhaustiveRegression" }, dataProvider = "updateGCConfigWithWrongConfigId",dataProviderClass=GiftCardNewServiceDP.class, description ="\n 1. Updating config with wrong configId and verifying the status. \n 2. Verify 200 status response.")
public void GCPersonalisationService_UpdateGCConfigWithWrongConfigId(String gcImage,String wrongConfigId)
{
	RequestGenerator req = GiftCardNewhelper.updateGCConfigWithWrongConfigId(gcImage,wrongConfigId);
	Assert.assertEquals(req.response.getStatus(), 200);
	String jsonRes=req.respvalidate.returnresponseasstring();	
	String expectedStatusCode="53";
	String expectedStatusMsg="Row with given id not found";
	String expectedStatusType="ERROR";
	String expectedTotalCount="0";
	String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
	Assert.assertEquals(actual_Statuscode, expectedStatusCode);
	String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
	Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
	String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
	Assert.assertEquals(actual_StatusType, expectedStatusType);
	String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
	Assert.assertEquals(actual_TotalCount, expectedTotalCount);
	
}
	
@Test(groups = {  "Sanity","Regression" }, dataProvider = "createGCConfig",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Calling the getConfig api and verifying data response. \n 2. Verify 200 status response.",dependsOnMethods="GCPersonalisationService_CreateGCConfig")
	public void GCPersonalisationService_GetGCConfig(String senderName,String senderEmail,String recipientName,String recipientEmail,String message,String gcImage,String coverImage,String cardImage,String isPersonalised)
	{
		
	RequestGenerator req = GiftCardNewhelper.getGCConfig(configId,senderName,recipientName,recipientEmail,message,updatedGCImage);
	Assert.assertEquals(req.response.getStatus(), 200);
	String jsonRes=req.respvalidate.returnresponseasstring();	

	System.out.println("fnjkdfsjkansdf"+updatedGCImage);	
	String expectedStatusCode="11004";
	String expectedStatusMsg="GiftCard config retrieved successfully";
	String expectedStatusType="SUCCESS";
	String expectedTotalCount="1";

	String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
	Assert.assertEquals(actual_Statuscode, expectedStatusCode);
	String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
	Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
	String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
	Assert.assertEquals(actual_StatusType, expectedStatusType);
	String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
	Assert.assertEquals(actual_TotalCount, expectedTotalCount);
	
	String expectedSenderName=senderName;
	String expectedRecipientName=recipientName;
	String expectedRecipientEmail=recipientEmail;
	String expectedMessage=message;
	String expectedGCImage=updatedGCImage;
	
	String actual_SenderName=JsonPath.read(jsonRes, "$.data.senderName").toString();
	Assert.assertEquals(actual_SenderName, expectedSenderName);
	String actual_RecipientName=JsonPath.read(jsonRes, "$.data.recipientName").toString();
	Assert.assertEquals(actual_RecipientName, expectedRecipientName);
	String actual_RecipientEmail=JsonPath.read(jsonRes, "$.data.recipientEmail").toString();
	Assert.assertEquals(actual_RecipientEmail, expectedRecipientEmail);
	String actual_Message=JsonPath.read(jsonRes, "$.data.message").toString();
	Assert.assertEquals(actual_Message, expectedMessage);
	String actual_GCImage=JsonPath.read(jsonRes, "$.data.gcImage").toString();
	Assert.assertEquals(actual_GCImage, expectedGCImage);
	Assert.assertEquals(JsonPath.read(jsonRes, "$.data.cardImage").toString(), cardImage);
	Assert.assertEquals(JsonPath.read(jsonRes, "$.data.coverImage").toString(), coverImage);
	Assert.assertEquals(JsonPath.read(jsonRes, "$.data.isPersonalised").toString(), isPersonalised);
	
	}

@Test(groups = {  "Regression" }, dataProvider = "getGCConfigWithWrongConfigId",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Calling the getConfig api and verifying data response. \n 2. Verify 200 status response.")
public void GCPersonalisationService_GetGCConfigWrongConfigId(String wrongConfigId)
{
	RequestGenerator req = GiftCardNewhelper.getGCConfigWithWrongConfigId(wrongConfigId);
	Assert.assertEquals(req.response.getStatus(), 200);
	String jsonRes=req.respvalidate.returnresponseasstring();	
	String expectedStatusCode="404";
	String expectedStatusMsg="NOT_FOUND";
	String expectedStatusType="ERROR";
	String expectedTotalCount="0";
	String actual_Statuscode=JsonPath.read(jsonRes, "$.status.statusCode").toString();
	Assert.assertEquals(actual_Statuscode, expectedStatusCode);
	String actual_StatusMsg=JsonPath.read(jsonRes, "$.status.statusMessage").toString();
	Assert.assertEquals(actual_StatusMsg, expectedStatusMsg);
	String actual_StatusType=JsonPath.read(jsonRes, "$.status.statusType").toString();
	Assert.assertEquals(actual_StatusType, expectedStatusType);
	String actual_TotalCount=JsonPath.read(jsonRes, "$.status.totalCount").toString();
	Assert.assertEquals(actual_TotalCount, expectedTotalCount);
}

@Test(groups = {  "Sanity", "Regression" }, dataProvider = "autoGiftcardRedemption",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Autoremption and refund \n 2. Verify 200 status response.")
	public void GiftBigService_AutoDebitGiftCard(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,String ppsType, String email, String partner_name,
			String bill_amount) {

			String uidx=GiftCardNewhelper.getUidxFromEmail(email);
			log.info("Uidx is :"+uidx);
			RequestGenerator req = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
			Assert.assertEquals(req.response.getStatus(), 200);
			String jsonRes=req.respvalidate.returnresponseasstring();	
	
			String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
			log.info("Amount before debited in card \n" + actual_TotalBalance);
			Float actual_TotalBalance_flt=Float.valueOf(actual_TotalBalance);
			RequestGenerator getDebitChecksum = GiftCardNewhelper.getAutoDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);
			String getAutoDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

			String checksum_Debit = JsonPath.read(getAutoDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

			log.info("Checksum for debit: "+ checksum_Debit);
			RequestGenerator redeemCard = GiftCardNewhelper.redeemAutoGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);

			String redeemCardResponse = redeemCard.returnresponseasstring();
			String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			log.info("Debit Response: " + redeemCardResponse);
			String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
			Float debitamount = (Float.valueOf(msg1)) / 100;
			RequestGenerator req1 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
			Assert.assertEquals(req1.response.getStatus(), 200);
			String jsonRes1=req1.respvalidate.returnresponseasstring();	
			String actual_TotalBalanceAfterDebit=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
			Float balanceAfterDebit_flt = Float.valueOf(actual_TotalBalanceAfterDebit);
			log.info("Balance after debited: " + balanceAfterDebit_flt);
			Assert.assertEquals(debitamount, actual_TotalBalance_flt - balanceAfterDebit_flt,"Amount is not matching");

			checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();String instrumentActionType="REFUND";
			RequestGenerator getDefaultChecksum = GiftCardNewhelper.getAutoDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount,instrumentActionType);
			String getDefaultChecksumResponse = getDefaultChecksum.returnresponseasstring();
			log.info("Response default checksum\n" + getDefaultChecksumResponse);
			String defaultchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
			log.info("Default checksum: " + defaultchecksum);
			RequestGenerator refundAmount = GiftCardNewhelper.refundAutoGiftCardAmount(defaultchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount);

			String refundAmountResp = refundAmount.returnresponseasstring();
			log.info("Refund amount response: " +refundAmountResp );


			RequestGenerator req2 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
			Assert.assertEquals(req2.response.getStatus(), 200);
			String jsonRes2=req2.respvalidate.returnresponseasstring();	
	
			String actual_TotalBalanceAfterRefund=JsonPath.read(jsonRes2, "$.data.totalBalance").toString();

			log.info("Balance after refunding: " + actual_TotalBalanceAfterRefund );
			Assert.assertEquals(actual_TotalBalanceAfterRefund, actual_TotalBalance,"Amount is not matching");
			Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}



@Test(groups = {  "Sanity", "Regression" }, dataProvider = "manualDebitAddToAccountAndRefundGiftCard",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Manual redemption\n 2.Add to account\n 3.Refund\n 4. Verify 200 status response.")
public void GiftBigService_manualDebitAddToAccountAndRefundGiftCard(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,String ppsType, String email, String partner_name,
		String bill_amount,String cardNumber,String pinNumber) throws InterruptedException {

		try {
			String balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,email);
			Float remainingBalance = Float.valueOf(balance);
			log.info("Amount before debited in card \n" + remainingBalance);
			RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, email, partner_name, cardNumber, pinNumber, bill_amount);
			String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

			String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

			log.info("Checksum for debit: "+ checksum_Debit);
			RequestGenerator redeemCard = GiftCardNewhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, email, partner_name, cardNumber, pinNumber, bill_amount);

			String redeemCardResponse = redeemCard.returnresponseasstring();
			String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			log.info("Debit Response: " + redeemCardResponse);
			String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
			
			//Add to account
			String uidx=GiftCardNewhelper.getUidxFromEmail(email);
			log.info("Uidx is :"+uidx);
			RequestGenerator req = GiftCardNewhelper.addGCtoUser(cardNumber, pinNumber, uidx, partner_name);
			String jsonRes = req.respvalidate.returnresponseasstring();
			log.info("Response of purchase api: " + jsonRes);
			
			Float debitamount = (Float.valueOf(msg1)) / 100;
			String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, email);
			Float balanceAfterDebit_flt = Float.valueOf(balanceAfterDebit);
			log.info("Balance after debited: " + balanceAfterDebit_flt);
			Assert.assertEquals(debitamount, remainingBalance - balanceAfterDebit_flt,"Amount is not matching");

			checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();
			RequestGenerator getDefaultCheksum = GiftCardNewhelper.getDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, email, clientId, partner_name, cardNumber, pinNumber, bill_amount);
			String getDefaultChecksumResponse = getDefaultCheksum.returnresponseasstring();
			log.info("Response default checksum\n" + getDefaultChecksumResponse);
			String defaulchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
			log.info("Default checksum: " + defaulchecksum);
			RequestGenerator refundAMount = GiftCardNewhelper.refundGiftCardAmount(defaulchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, email, clientId, partner_name, cardNumber, pinNumber, bill_amount);

			Thread.sleep(5000);
			String refundAmountResp = refundAMount.returnresponseasstring();
			log.info("Refund amount response: " +refundAmountResp );


			String balanceAfterRefund = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,email);

			RequestGenerator getGiftcardBalanceAddedToUser=GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
			Assert.assertEquals(getGiftcardBalanceAddedToUser.response.getStatus(), 200);
			String jsonRes1=getGiftcardBalanceAddedToUser.respvalidate.returnresponseasstring();	
	
			String actual_TotalBalance=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
			log.info("Amount before debited in card \n" + actual_TotalBalance);
			
			log.info("Balance after refunding: " + balanceAfterRefund );
			Assert.assertEquals(balanceAfterRefund, balance,"Amount is not matching");
			Assert.assertEquals(balanceAfterRefund,balanceAfterRefund,"Amount is not matching");
			Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
		} finally {
			// Deleting the card so that it can be used for next run
			log.info("Deleting the card from user_giftcard_mapping table");
			String query1 = "DELETE FROM myntra_giftcard.user_giftcard_mapping where gc_number="+"'"+cardNumber+"'"+";";
			log.info("The query is : " + query1);
			DBUtilities.exUpdateQuery(query1, "giftcard");
		}
}

@Test(groups = {  "Sanity", "Regression" }, dataProvider = "autoGiftcardRedemption",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Autoremption and refund \n 2. Verify 200 status response.")
public void GiftBigService_AutoDebitGiftCardAndVoid(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,String ppsType, String email, String partner_name,
		String bill_amount) throws InterruptedException {

		String uidx=GiftCardNewhelper.getUidxFromEmail(email);
		log.info("Uidx is :"+uidx);
		RequestGenerator req = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req.response.getStatus(), 200);
		String jsonRes=req.respvalidate.returnresponseasstring();	

		String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
		log.info("Amount before debited in card \n" + actual_TotalBalance);
		Float actual_TotalBalance_flt=Float.valueOf(actual_TotalBalance);
		RequestGenerator getDebitChecksum = GiftCardNewhelper.getAutoDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);
		String getAutoDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getAutoDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		log.info("Checksum for debit: "+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardNewhelper.redeemAutoGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);

		String redeemCardResponse = redeemCard.returnresponseasstring();
		String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		log.info("Debit Response: " + redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
		Float debitamount = (Float.valueOf(msg1)) / 100;
		RequestGenerator req1 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req1.response.getStatus(), 200);
		String jsonRes1=req1.respvalidate.returnresponseasstring();	
		String actual_TotalBalanceAfterDebit=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
		Float balanceAfterDebit_flt = Float.valueOf(actual_TotalBalanceAfterDebit);
		log.info("Balance after debited: " + balanceAfterDebit_flt);
		Assert.assertEquals(debitamount, actual_TotalBalance_flt - balanceAfterDebit_flt,"Amount is not matching");

		checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();String instrumentActionType="VOID";
		RequestGenerator getDefaultChecksum = GiftCardNewhelper.getAutoDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount,instrumentActionType);
		String getDefaultChecksumResponse = getDefaultChecksum.returnresponseasstring();
		log.info("Response default checksum\n" + getDefaultChecksumResponse);
		String defaultchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
		log.info("Default checksum: " + defaultchecksum);
		RequestGenerator voidAmount = GiftCardNewhelper.voidAutoGiftCardAmount(defaultchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount);

		String voidAmountResp = voidAmount.returnresponseasstring();
		log.info("Void amount response: " +voidAmountResp );

		Thread.sleep(5000);
		RequestGenerator req2 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req2.response.getStatus(), 200);
		String jsonRes2=req2.respvalidate.returnresponseasstring();	

		String actual_TotalBalanceAfterRefund=JsonPath.read(jsonRes2, "$.data.totalBalance").toString();

		log.info("Balance after refunding: " + actual_TotalBalanceAfterRefund );
		Assert.assertEquals(actual_TotalBalanceAfterRefund, actual_TotalBalance,"Amount is not matching");
		Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}




@Test(groups = {  "Sanity", "Regression" }, dataProvider = "autoMultipleGiftcardRedemption",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Autoremption of multiple giftcards and void \n 2. Verify 200 status response.")
public void GiftBigService_AutoDebitMultipleGiftCardAndVoid(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,String ppsType, String email, String partner_name,
		String bill_amount) throws JSONException {
		String uidx=GiftCardNewhelper.getUidxFromEmail(email);
		log.info("Uidx is :"+uidx);
		RequestGenerator req = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req.response.getStatus(), 200);
		String jsonRes=req.respvalidate.returnresponseasstring();	

		String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
		log.info("Amount before debited in card \n" + actual_TotalBalance);
		Float actual_TotalBalance_flt=Float.valueOf(actual_TotalBalance);
		int activeGiftCardsCount=JsonPath.read(jsonRes, "$.data.activeGiftCardsCount");
		log.info("Active GCs are : "+activeGiftCardsCount);
		String giftCardDetailsArray = JsonPath.read(jsonRes, "$.data").toString().replace("=", ":");
		JSONObject giftCardObject=new JSONObject(giftCardDetailsArray);
		JSONArray giftCardArray=giftCardObject.getJSONArray("giftCardDetails");
		Map<String, Double> gcBalanceMap=new HashMap<String, Double>();
		JSONObject temp=null;
		for(int i=0;i<activeGiftCardsCount;i++)
		{
			temp=giftCardArray.getJSONObject(i);
			gcBalanceMap.put(temp.getString("giftCardNumber"), (Double) temp.get("cardBalance"));
		}
		
		RequestGenerator getDebitChecksum = GiftCardNewhelper.getAutoDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);
		String getAutoDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getAutoDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		log.info("Checksum for debit: "+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardNewhelper.redeemAutoGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);

		String redeemCardResponse = redeemCard.returnresponseasstring();
		String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		log.info("Debit Response: " + redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
		Float debitamount = (Float.valueOf(msg1)) / 100;
		RequestGenerator req1 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req1.response.getStatus(), 200);
		String jsonRes1=req1.respvalidate.returnresponseasstring();	
		String actual_TotalBalanceAfterDebit=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
		Float balanceAfterDebit_flt = Float.valueOf(actual_TotalBalanceAfterDebit);
		log.info("Balance after debited: " + balanceAfterDebit_flt);
		Assert.assertEquals(debitamount, actual_TotalBalance_flt - balanceAfterDebit_flt,"Amount is not matching");
		giftCardDetailsArray = JsonPath.read(jsonRes1, "$.data").toString().replace("=", ":");
		giftCardObject=new JSONObject(giftCardDetailsArray);
		giftCardArray=giftCardObject.getJSONArray("giftCardDetails");
		Double diffSum=0.0;
		for(int i=0;i<activeGiftCardsCount;i++)
		{
			temp=giftCardArray.getJSONObject(i);
			diffSum=diffSum + ((Double) gcBalanceMap.get(temp.getString("giftCardNumber")) - (Double) temp.get("cardBalance"));
		}
		log.info("Sum is :" + diffSum);
		Double actualDiffSum=Double.valueOf(bill_amount)/100;
		log.info("Actual sum is :" + actualDiffSum);
		Assert.assertEquals(actualDiffSum,diffSum);
		checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();String instrumentActionType="VOID";
		RequestGenerator getDefaultChecksum = GiftCardNewhelper.getAutoDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount,instrumentActionType);
		String getDefaultChecksumResponse = getDefaultChecksum.returnresponseasstring();
		log.info("Response default checksum\n" + getDefaultChecksumResponse);
		String defaultchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
		log.info("Default checksum: " + defaultchecksum);
		RequestGenerator voidAmount = GiftCardNewhelper.voidAutoGiftCardAmount(defaultchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, bill_amount);

		String voidAmountResp = voidAmount.returnresponseasstring();
		log.info("Void amount response: " +voidAmountResp );

		RequestGenerator req2 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req2.response.getStatus(), 200);
		String jsonRes2=req2.respvalidate.returnresponseasstring();	
		String actual_TotalBalanceAfterRefund=JsonPath.read(jsonRes2, "$.data.totalBalance").toString();
		log.info("Balance after refunding: " + actual_TotalBalanceAfterRefund );
		Assert.assertEquals(actual_TotalBalanceAfterRefund, actual_TotalBalance,"Amount is not matching");
		Assert.assertEquals(redeemCard.response.getStatus(),200,"refundGiftCard is not working");
}

@Test(groups = { "Regression" }, dataProvider = "debitAlreadyAddedGiftCard",dataProviderClass=GiftCardNewServiceDP.class,description="\n 1. Check Balance before debiting giftcard \n 2. Get debit checksum \n 3. Apply Gift card \n 4. Get clientTransactionId \n 5. debit Amount \n 6. Verify amount before debit and after redeem gift card")
public void GiftServiceNew_DebitAlreadyAddedGiftCard(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID, String partner_name,
		String cardNumber, String pinNumber, String bill_amount) {
		String Balance = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber, pinNumber,customerID);
		Float remainingBalance = Float.valueOf(Balance);
		log.info("Amount Before debited in card \n" + remainingBalance);
		RequestGenerator getDebitChecksum = GiftCardNewhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();
		
		String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
		
		log.info("Checksum for debit: "+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardNewhelper.debitGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String redeemCardResponse = redeemCard.returnresponseasstring();
		
		log.info("Debit Response:  " + redeemCardResponse);
		String txStatus = JsonPath.read(redeemCardResponse, "$.params.txStatus").toString();
		String comments = JsonPath.read(redeemCardResponse, "$.params.comments").toString();
		Assert.assertEquals(txStatus, "TX_FAILURE");
		Assert.assertEquals(comments, "Gift Card has already been added.");
		String balanceAfterDebit = GiftCardNewhelper.getGiftcardBalance(partner_name, cardNumber,pinNumber, customerID);
		log.info("Balance after debit ===\n" + balanceAfterDebit);
}

@Test(groups = {  "Sanity", "Regression" }, dataProvider = "autoGiftcardRedemption",dataProviderClass=GiftCardNewServiceDP.class,description ="\n 1. Autoremption and refund \n 2. Verify 200 status response.")
public void GiftBigService_AutoDebitGiftCardAndRefundMoreThanDebit(String checksum, String ppsID,String ppsTransactionID, String orderId, String amount,String ppsType, String email, String partner_name,
		String bill_amount) {

		String uidx=GiftCardNewhelper.getUidxFromEmail(email);
		log.info("Uidx is :"+uidx);
		RequestGenerator req = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req.response.getStatus(), 200);
		String jsonRes=req.respvalidate.returnresponseasstring();	

		String actual_TotalBalance=JsonPath.read(jsonRes, "$.data.totalBalance").toString();
		log.info("Amount before debited in card \n" + actual_TotalBalance);
		Float actual_TotalBalance_flt=Float.valueOf(actual_TotalBalance);
		RequestGenerator getDebitChecksum = GiftCardNewhelper.getAutoDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);
		String getAutoDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getAutoDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		log.info("Checksum for debit: "+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardNewhelper.redeemAutoGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, partner_name, bill_amount);

		String redeemCardResponse = redeemCard.returnresponseasstring();
		String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		log.info("Debit Response: " + redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
		Float debitamount = (Float.valueOf(msg1)) / 100;
		RequestGenerator req1 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req1.response.getStatus(), 200);
		String jsonRes1=req1.respvalidate.returnresponseasstring();	
		String actual_TotalBalanceAfterDebit=JsonPath.read(jsonRes1, "$.data.totalBalance").toString();
		Float balanceAfterDebit_flt = Float.valueOf(actual_TotalBalanceAfterDebit);
		log.info("Balance after debited: " + balanceAfterDebit_flt);
		Assert.assertEquals(debitamount, actual_TotalBalance_flt - balanceAfterDebit_flt,"Amount is not matching");

		checksum=GiftCardNewhelper.generateRandomString();ppsTransactionID=GiftCardNewhelper.generateRandomString();String instrumentActionType="REFUND";
		Float moreDebitAmount=Float.valueOf(bill_amount) + 100;
		log.info("More balance" + moreDebitAmount);
		RequestGenerator getDefaultChecksum = GiftCardNewhelper.getAutoDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, moreDebitAmount+"",instrumentActionType);
		String getDefaultChecksumResponse = getDefaultChecksum.returnresponseasstring();
		log.info("Response default checksum\n" + getDefaultChecksumResponse);
		String defaultchecksum= JsonPath.read(getDefaultChecksumResponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
		log.info("Default checksum: " + defaultchecksum);
		RequestGenerator refundAmount = GiftCardNewhelper.refundAutoGiftCardAmount(defaultchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, uidx, clientId, partner_name, moreDebitAmount+"");

		String refundAmountResp = refundAmount.returnresponseasstring();
		log.info("Refund amount response: " +refundAmountResp );

		RequestGenerator req2 = GiftCardNewhelper.getGiftCardAddedToUserAccount(uidx);
		Assert.assertEquals(req2.response.getStatus(), 200);
		String jsonRes2=req2.respvalidate.returnresponseasstring();	

		String actual_TotalBalanceAfterRefund=JsonPath.read(jsonRes2, "$.data.totalBalance").toString();
		Assert.assertEquals(Float.valueOf(actual_TotalBalanceAfterRefund), actual_TotalBalance_flt - Float.valueOf(bill_amount)/100,"Amount is not matching");
		Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
}



}
