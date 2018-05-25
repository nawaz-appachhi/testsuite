package com.myntra.apiTests.portalservices.all;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.GiftCardNewServiceDP;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.giftCardPpsHelper.GiftCardPpsNewServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;

public class ManualGiftcardServiceTests extends BaseTest{

		
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
	log.info("Main remaining balance: " + remainingBalance);
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
	Float amount_flt = Float.valueOf(amount);
	Float tolerance=(float) 0.1;
	log.info("remainingBalance" +remainingBalance);
	log.info("balanceAfterDebit_flt" +balanceAfterDebit_flt);
	log.info("Diff" +(remainingBalance-balanceAfterDebit_flt));
	Assert.assertTrue((remainingBalance-balanceAfterDebit_flt)/1.0 < (amount_flt)/100.0+tolerance,"Amount mismatch");
	Assert.assertEquals(msg1,"Redeem Successful","Success with debit amount more than active balance");
	String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
	Assert.assertEquals(textMessage,"TX_SUCCESS","Txt Success with debit amount more than active balance");
	Assert.assertEquals(redeemCard.response.getStatus(),200,"redeemGiftCard is not working");
	}
		

	



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
	
}
