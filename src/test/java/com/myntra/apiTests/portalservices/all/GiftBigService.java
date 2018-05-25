package com.myntra.apiTests.portalservices.all;

import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.GiftBigDP;
import com.myntra.apiTests.portalservices.giftCardPpsHelper.GiftCardPpsServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class GiftBigService extends GiftBigDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(GiftBigService.class);
	static GiftCardPpsServiceHelper GiftCardhelper = new GiftCardPpsServiceHelper();


	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "checkBalance", description="\n 1. Check balance of the gift card. \n 2. Verify 200 status response.")
	public void GiftBigService_checkBalance(String type, String cardNumber,
			String pin, String login) {
		//kjlkjl
		RequestGenerator checkBalance = GiftCardhelper.checkGiftcardBalance(type, cardNumber, pin, login);
		
		String jsonRes = checkBalance.respvalidate.returnresponseasstring();
		System.out.println("response for check balance" + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..statusMessage").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Messgage- >>>>" + msg1);

		AssertJUnit.assertEquals("unsuccessful", msg1,
				"Check Balance Successful");
		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				checkBalance.response.getStatus());

	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "redeemGiftCard",description="\n 1. Check Balance before redeem giftcard \n 2. Get debit checksum \n 3. Redeem Gift card \n 4. Get clientTransactionId \n 5. Redeem Amount \n 6. Verify amount before redeem and after redeem gift card")
	public void GiftBigService_DebitGiftCard(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String partner_name,
			String cardNumber, String pinNumber, String bill_amount) {

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("AMount Before debited in card \n" + RemainingBalance);
		RequestGenerator getDebitChecksum = GiftCardhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("checksum for debit=====\n"+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String redeemCardResponse = redeemCard.returnresponseasstring();
		String clientId = JsonPath.read(redeemCardResponse, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("Debit Response======>> \n" + redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..amount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		Float debitamount = (Float.valueOf(msg1)) / 100;
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		AssertJUnit.assertEquals(RemainingBalance - balcnceAfterDebit_flt,
				debitamount, debitamount);
		System.out.println("Balance after debited ===\n" + balcnceAfterDebit_flt);
		
		RequestGenerator getDefaultCHeksum = GiftCardhelper.getDefaultChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientId, partner_name, cardNumber, pinNumber, bill_amount);
		String getDefaultCHeksumResp = getDefaultCHeksum.returnresponseasstring();
		System.out.println("Resomse default checksum\n" + getDefaultCHeksumResp);
		String defaulchecksum= JsonPath.read(getDefaultCHeksumResp, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
		System.out.println("default checksum----\n" + defaulchecksum);
		RequestGenerator refundAMount = GiftCardhelper.refundGiftCardAmount(defaulchecksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientId, partner_name, cardNumber, pinNumber, bill_amount);
		
		String refundAMountResp = refundAMount.returnresponseasstring();
		System.out.println("Refunf Amount Response \n" +refundAMountResp );

		
		String balanceAfterRefund = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		
		System.out.println("Balance after refunding --===\n" + balanceAfterRefund );
		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				redeemCard.response.getStatus());
	}

//	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
//			"ExhaustiveRegression" }, dataProvider = "redeemGiftCardMoreThanBalance", description="\n 1. Check balance before debit \n 2. Get debit checksum \n 3. Redeem giftcard more than balance \n 4.Get Balance \n 5. Verify msg --> Success with debit amount more than active balance \n 6. Verify 200 status resoponse code.")
//	public void GiftBigService_DebitGiftCardMoreThanBalance(String checksum,
//			String ppsID, String ppsTransactionID, String orderId,
//			String amount, String ppsType, String customerID,
//			String partner_name, String cardNumber, String pinNumber,
//			String bill_amount) {
//
//		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
//				customerID);
//		Float RemainingBalance = Float.valueOf(Balance);
//		RequestGenerator getDebitChecksum = GiftCardhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//		String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();
//
//		String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();
//
//		System.out.println("checksum for debit=====\n"+ checksum_Debit);
//		RequestGenerator redeemCard = GiftCardhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
//		
//		String redeemCardResponse = redeemCard.returnresponseasstring();
//		System.out.println("response for redeemgift card \n"+redeemCardResponse);
//		String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
//				.replace("[", "").replace("]", "").replace("\"", "").trim();
//		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
//				pinNumber, customerID);
//		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
//		AssertJUnit.assertEquals(RemainingBalance, balcnceAfterDebit_flt,
//				balcnceAfterDebit_flt);
//		AssertJUnit.assertEquals(
//				"Success with debit amount more than active balance", msg1,
//				"Balance is insufficient.");
//		String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
//				.replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals(
//				"Txt Success with debit amount more than active balance",
//				textMessage, "TX_FAILURE");
//
//		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
//				redeemCard.response.getStatus());
//	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "redeemGiftCardNegativebalance", description="\n 1. Check balance before redeem \n 2. Get debit checksum \n 3. Redeem gift card with negative balance \n 4. Get msg from redeemGiftCard response \n 5. Verify msg with string ->Amount is incorrect. \n 6. Verify 200 status response code with redeem giftcard response.")
	public void GiftBigService_DebitGiftCardNegativeBalance(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		RequestGenerator getDebitChecksum = GiftCardhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("checksum for debit=====\n"+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String redeemCardResponse = redeemCard.returnresponseasstring();
		System.out.println("response for redeemgift card \n"+redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		AssertJUnit.assertEquals(RemainingBalance, balcnceAfterDebit_flt,
				balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(
				"Success with debit amount more than active balance", msg1,
				"Amount is incorrect.");
		String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals(
				"Txt Success with debit amount more than active balance",
				textMessage, "TX_FAILURE");

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				redeemCard.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "redeemGiftCardLessThan100", description="\n 1. Get balance before redeem. \n 2. Get debit checksum. \n 3. Redeem gift card and get comments from redeemGiftCard response. \n 4. Get balance after redeem. \n 5. Verify balance before and after redeem of giftcard \n 6. Verify msg with string --> Amount less than min redeem limit. \n 7. Verify text status \n 8. Verify status code response for redeemGiftCard response. ")
	public void GiftBigService_DebitGiftCardLessThan100(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		RequestGenerator getDebitChecksum = GiftCardhelper.getDebitChecksum(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		String getDebitChecksumresponse = getDebitChecksum.returnresponseasstring();

		String checksum_Debit = JsonPath.read(getDebitChecksumresponse, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("checksum for debit=====\n"+ checksum_Debit);
		RequestGenerator redeemCard = GiftCardhelper.redeemGiftCard(checksum_Debit, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String redeemCardResponse = redeemCard.returnresponseasstring();
		System.out.println("Response for redeem gift card \n" +redeemCardResponse);
		String msg1 = JsonPath.read(redeemCardResponse, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		AssertJUnit.assertEquals(RemainingBalance, balcnceAfterDebit_flt,
				balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(
				"Success with debit amount more than active balance", msg1,
				"Redeem Successful");
		String textMessage = JsonPath.read(redeemCardResponse, "$..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals(
				"Txt Success with debit amount more than active balance",
				textMessage, "TX_SUCCESS");

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				redeemCard.response.getStatus());
		
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftCard", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
	public void GiftBigService_RefundGiftCard(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		String jsonRes = req.respvalidate.returnresponseasstring();
		
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftCardMoreThanDebit",description="\n 1. Debit amount from gift card \n 2. Check balance \n 3. Refund gift card more than debit amount. \n 4. Get comments from refund giftcard response. \n 5. Get balance after debit \n 6. Verify remaining balance + debit amount matches with balance after debit.")
	public void GiftBigService_RefundGiftMoreThanDebit(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		// AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
		// balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftCardLessThanDebit",description="\n 1. Debit amount from gift card. \n 2. check balance before debit. \n 3. Get refund gift card \n 4. Check balance after debit \n 5. verify remaining balance + debit amount matches with balance after debit.")
	public void GiftBigService_RefundGiftLessThanDebit(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		// AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
		// balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftCardWithSameTXN")
	public void GiftBigService_RefundGiftWithSameClientId(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);

		String duplicateMsg = JsonPath.read(jsonRes, "$..duplicate").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();

		if (duplicateMsg.contains("true")) {

			AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
					balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		} else {
			System.out
					.println("Next dataprovider will be run to verify the this testcase");
			AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
					balcnceAfterDebit_flt, balcnceAfterDebit_flt);
		}

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftOnDifferentCard")
	public void GiftBigService_RefundGiftOnDifferentCard(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		// AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
		// balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftWithInvalid")
	public void GiftBigService_RefundGiftWithInvalid(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		
		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		// String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
		// .replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundGiftWithVoidedTxn", description="\n 1. Check balance before voided transaction. \n 2. Refund gift card. \n 3. Check balance after voided transaction. \n 4. Verify balance before transaction + debit amount matches with balance after debit .")
	public void GiftBigService_RefundGiftWithVoidedTransaction(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		System.out.println("Main remaining balance =====" + RemainingBalance);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, cardNumber,
						pinNumber, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");

		System.out.println("REFUND Url------>>>>> " + service.URL);
		System.out.println("REFUND  payload------>>>>> " + service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		//prob
		
//		RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		String txStatus = JsonPath.read(jsonRes, "$..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();

		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", txStatus);
		// AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);
		AssertJUnit
				.assertEquals(
						"Cancel Redeem Successful",
						msg1);
		System.out.println("succesfull");

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "gifftVoidTransaction", description ="\n 1. Check balance before void transaction. \n 2. Get void transaction request. \n 3. Check balance after void transaction. \n 4. Verify remaining balance + debit amount matching with balance after debit amount.")
	private void GiftBigService_VoidTransaction(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		
		RequestGenerator req=GiftCardhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Void Msg------>>>>> " + msg1);
		System.out.println("voide response -----===??? " + response);
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "gifftVoidTransactionOnAlreadyVoidedTxn", description="\n 1.Check balance before void transaction. \n 2. Create void transaction on already voided transaction. \n 3. Check balance after void transaction. \n 4. Verify remaining balance + debit amount matching with balance after debit. ")
	private void GiftBigService_VoidTransactionOnAlreadyVoidedTxn(
			String checksum, String ppsID, String ppsTransactionID,
			String orderId, String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
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
		String response = req.returnresponseasstring();
		
		//prob : first dp pass, second dp fails
		
//		RequestGenerator req=GiftCardhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
//		String response = req.returnresponseasstring();
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Void Msg------>>>>> " + msg1);
		System.out.println("voide response -----===??? " + response);
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);

		if (msg1.contains("TX_FAILURE")) {
			String comment = JsonPath.read(jsonRes, "$.params..comments")
					.toString().replace("[", "").replace("]", "")
					.replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(
					"Amount being cancelled is exceeding the original transaction amount",
					comment);
			System.out.println("succesfull");

		}

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "gifftVoidTransactionOnRefundTxn", description="\n 1. Check balance before debit. \n 2. Get void transaction request. \n 3. Debit amount form gift card. \n 4 Check balance after debit. \n 5. Verify remaining balance + debit amount matches with balance after debit. ")
	private void GiftBigService_VoidTransactionOnrefundTxn(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		
		RequestGenerator req=GiftCardhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String response = req.returnresponseasstring();

		String jsonRes = req.respvalidate.returnresponseasstring();
		 String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
		 .replace("[", "").replace("]", "").replace("\"", "").trim();
		// System.out.println("Void Msg------>>>>> " + msg1);
		System.out.println("voide response -----===??? " + response);
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);
		AssertJUnit.assertEquals("successful", msg1, "TX_FAILURE");

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "gifftVoidTransactionWithInvalidTxn", description="\n 1. Get balance before void transaction. \n 2. Get void transaction. \n 3. Get balance after void transction. \n 4. Verify remaining balance + debit amount matches with balance after void debit transaction. \n 5. Verify 200 status response code for redeem gift card")
	private void GiftBigService_VoidTransactionWithInvalidTxn(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String ppsType, String customerID,
			String clientTransactionID, String partner_name, String cardNumber,
			String pinNumber, String bill_amount) {

		Float DebitAMount = (Float.valueOf(amount)) / 100;

		String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
				customerID);
		Float RemainingBalance = Float.valueOf(Balance);
		
		RequestGenerator req=GiftCardhelper.getVoidTransactionReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
		
		String response = req.returnresponseasstring();

		String jsonRes = req.respvalidate.returnresponseasstring();
		// String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
		// .replace("[", "").replace("]", "").replace("\"", "").trim();
		// System.out.println("Void Msg------>>>>> " + msg1);
		System.out.println("voide response -----===??? " + response);
		String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
				pinNumber, customerID);
		Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
		System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
		AssertJUnit.assertEquals(RemainingBalance + DebitAMount,
				balcnceAfterDebit_flt, balcnceAfterDebit_flt);
		
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("TX_FAILURE", msg1);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
	}


	
	//--
	
		@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
		"ExhaustiveRegression" }, dataProvider = "vDebitChecksum", description="\n 1. Get checksum from checksum debit gift api \n 2. Debit gift card \n 3. Verify checksum in debit checksum response.")//vDebitChecksum , redeemGiftCard
		public void GiftBigService_vDebitChecksumResponse(String checksum, String ppsID,
				String ppsTransactionID, String orderId, String amount,
				String ppsType, String customerID, String partner_name,
				String cardNumber, String pinNumber, String bill_amount) {

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
			RequestGenerator req1 = new RequestGenerator(service1, getParam1);
			//prob
			
//			RequestGenerator req1=GiftCardhelper.getChecksumDebitReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount);
			
			String jsonDebitRes = req1.respvalidate.returnresponseasstring();
			System.out.println("Debit Response " + jsonDebitRes);
			String amt = JsonPath.read(jsonDebitRes, "$..amount").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("AMount : _----> "+amt);
			String Chksum=JsonPath.read(jsonDebitRes, "$..checksum").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("Checksum from debit response1: ----> "+Chksum);
			String dup=JsonPath.read(jsonDebitRes,"$..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			String clientTransactionId=JsonPath.read(jsonDebitRes, "$..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("Client Transaction id is: "+clientTransactionId);
			String txStatus=JsonPath.read(jsonDebitRes, "$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("Status : "+txStatus);
			String coments=JsonPath.read(jsonDebitRes, "$..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("Comments: "+coments);
			//String amt=JsonPath.read(jsonDebitRes, "$..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			
			MyntraService service2 = Myntra.getService(
					ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITCHCKSUMRESPONSE,
					init.Configurations, new String[] { checksum, ppsTransactionID,clientTransactionId, ppsID,
							 orderId,dup, amt, customerID,txStatus,coments},
					PayloadType.JSON, PayloadType.JSON);
			HashMap getParam2 = new HashMap();
			getParam2.put("user", "manishkumar.gupta@myntra.com");
			System.out.println("Service request payload chcks--- ? " + service2.Payload);
			RequestGenerator req2 = new RequestGenerator(service2, getParam2);
			
//			RequestGenerator req2=GiftCardhelper.getDebitChckRespReq(checksum, ppsTransactionID, clientTransactionId, ppsID, orderId, dup, amt, customerID, txStatus, coments);
			
			String jsonDebitRes1 = req2.respvalidate.returnresponseasstring();
			System.out.println("Checksum Response---- \n "+jsonDebitRes1);
			//Assert.assertEquals(Chksum, jsonDebitRes1);
			Assert.assertTrue(Chksum.contains(jsonDebitRes1));
			
			
		}
		
		
		@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
		"ExhaustiveRegression" }, dataProvider = "vRefundChecksum",description="\n 1. Get checksum from checksum debit gift api \n 2. Debit gift card \n 3. Refund gift card \n 4. Verify checksum in debit checksum response with refund gift card response.")//vDebitChecksum , redeemGiftCard
		public void GiftBigService_vRefundChecksumResponse(String checksum, String ppsID,
				String ppsTransactionID, String orderId, String amount,
				String ppsType, String customerID, String clientTransactionID, String partner_name,
				String cardNumber, String pinNumber, String bill_amount) throws InterruptedException  {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMDEBITGIFT,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user", "manishkumar.gupta@myntra.com");
			RequestGenerator req = new RequestGenerator(service, getParam);
			String jsoncheckRes = req.respvalidate.returnresponseasstring();
			String check = JsonPath.read(jsoncheckRes, "$..checksum").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("Checksum giftcard  resp: "+ check);
			
			MyntraService service1 = Myntra.getService(
					ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITGIFTCARD,
					init.Configurations, new String[] { check, ppsID,
							ppsTransactionID, orderId, amount, ppsType, customerID,
							partner_name, cardNumber, pinNumber, bill_amount },
					PayloadType.JSON, PayloadType.JSON);

			HashMap getParam1 = new HashMap();
			getParam1.put("user", "manishkumar.gupta@myntra.com");
			System.out.println("Service request --- For Debit? " + service1.Payload);
			RequestGenerator req1 = new RequestGenerator(service1, getParam1);
			System.out.println("PAYLOAD CHECKSUM---- " +service1.Payload );
			String jsonDebitRes = req1.respvalidate.returnresponseasstring();
			System.out.println("Debit giftcard Response " + jsonDebitRes);
			
			MyntraService service2 = Myntra.getService(
					ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD,
					init.Configurations, new String[] { check, ppsID,
							ppsTransactionID, orderId, amount, ppsType, customerID,
							clientTransactionID, partner_name, cardNumber,
							pinNumber, bill_amount },
					PayloadType.JSON, PayloadType.JSON);
			
			HashMap getParam2 = new HashMap();
			getParam1.put("user", "manishkumar.gupta@myntra.com");
			System.out.println("Service request --- for refund ? " + service1.Payload);
			RequestGenerator req2 = new RequestGenerator(service1, getParam2);
			String jsonRefundRes = req2.respvalidate.returnresponseasstring();
			System.out.println("Refund Response " + jsonRefundRes);
			String Chksum=JsonPath.read(jsonRefundRes, "$..checksum").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			System.out.println("chsm res: "+Chksum);
			String txStatus=JsonPath.read(jsonRefundRes, "$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			String coments=JsonPath.read(jsonRefundRes, "$..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			String dup=JsonPath.read(jsonRefundRes,"$..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			String clientTranscId=JsonPath.read(jsonRefundRes, "$..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
			
			
				MyntraService service3 = Myntra.getService(
						ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITCHCKSUMRESPONSE,
						init.Configurations, new String[] { checksum, ppsTransactionID,clientTranscId, ppsID,
								 orderId,dup,amount, customerID,txStatus,coments},
						PayloadType.JSON, PayloadType.JSON);
				HashMap getParam3 = new HashMap();
				getParam3.put("user", "manishkumar.gupta@myntra.com");
				System.out.println("Service request payload chcks--- ? " + service3.Payload);
				RequestGenerator req3 = new RequestGenerator(service3, getParam3);
				Thread.sleep(1000);
				String jsonRefundchcksmRes1 = req3.respvalidate.returnresponseasstring();
				System.out.println("RESPONSE CHECK SUM FOR REFUND____ \n" + req3.respvalidate.returnresponseasstring());
				System.out.println("Refund chcksm resp: "+jsonRefundchcksmRes1);
				//Assert.assertEquals(Chksum, jsonRefundchcksmRes1);
				Assert.assertTrue(Chksum.contains(jsonRefundchcksmRes1));
			
			

		}
		
		@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
		"ExhaustiveRegression" }, dataProvider = "checkBalanceNegative", description="\n 1. Get card balance \n 2. Verify status msg from cardbalance response with --> Failure to check balance for expiry cards.")
		public void GiftBigService_checkBalanceForExpiryCard(String type, String cardNumber,
		String pin, String login) {
			
	RequestGenerator req= GiftCardhelper.getCardBlnceReq(type, cardNumber, pin, login);
	String jsonRes = req.respvalidate.returnresponseasstring();
	System.out.println("response for check balance" + jsonRes);
	String failureMsg = JsonPath.read(jsonRes, "$..statusMessage").toString()
			.replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("Status Messgage: " + failureMsg);

	//log.info(service.URL);
	AssertJUnit.assertEquals("unsuccessful", failureMsg,
			"Card has expired");
	AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
			req.response.getStatus());

}
		@Test(groups = { "Smoke", "Sanity","Fox7Sanity", "MiniRegression", "Regression",
		"ExhaustiveRegression" },priority=10, dataProvider = "refundGiftCardAfterTestCaseRun", description ="\n 1.Debit amount to gift card \n 2. Get balance before refund of gift card \n 3. Get comments form refund gift card response. \n 4. Verify remaining balance + Debit amount with balance amount after debit \n 5. Verify 200 status response code for redeem gift card ")
		public void giftCardRefundAfterClass(String checksum, String ppsID,
				String ppsTransactionID, String orderId, String amount,
				String ppsType, String customerID, String clientTransactionID,
				String partner_name, String cardNumber, String pinNumber,
				String bill_amount){
			
			String Balance = GiftCardhelper.getBalance(partner_name, cardNumber, pinNumber,
					customerID);
			Float RemainingBalance = Float.valueOf(Balance);
			System.out.println("Main remaining balance =====" + RemainingBalance);
			
			RequestGenerator req=GiftCardhelper.getRefundGiftCardReq(checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber, pinNumber, bill_amount);
			String jsonRes = req.respvalidate.returnresponseasstring();
			
			System.out.println("Refund Response " + jsonRes);
			String msg1 = JsonPath.read(jsonRes, "$..comments").toString()
					.replace("[", "").replace("]", "").replace("\"", "").trim();
			String balcnceAfterDebit = GiftCardhelper.getBalance(partner_name, cardNumber,
					pinNumber, customerID);
			Float balcnceAfterDebit_flt = Float.valueOf(balcnceAfterDebit);
			System.out.println("AFter Refund -----------" + balcnceAfterDebit_flt);
			AssertJUnit.assertEquals(balcnceAfterDebit_flt, balcnceAfterDebit_flt);

			AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
					req.response.getStatus());
			//assert

			
		}
		
		
}
