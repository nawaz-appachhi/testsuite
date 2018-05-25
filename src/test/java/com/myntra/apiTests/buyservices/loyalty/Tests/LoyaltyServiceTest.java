package com.myntra.apiTests.buyservices.loyalty.Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.LoyaltyServiceDP;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.loyalitypointsservice.LoyalityPointsServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntAssert;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author Achal
 *
 */

public class LoyaltyServiceTest extends LoyaltyServiceDP {
	static LoyalityPointsServiceHelper loyalityPointsServiceHelper = new LoyalityPointsServiceHelper();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LoyaltyServiceTest.class);
	static String[] expAct;
	APIUtilities apiUtil = new APIUtilities();
	CommonUtils commonUtil = new CommonUtils();
	static String tenantId="2297";
	static String tenantIdJabong="4603";

	public static int getloyaltypointsquery(String query,String db) {
		List<HashMap> currentbalance = DBUtilities.exSelectQuery(query, db);
log.info(currentbalance);
Scanner in = new Scanner(currentbalance.toString())
				.useDelimiter("[^0-9]+");
		int integer = in.nextInt();
		return integer;
	}

	public static String getquery(String query,String db) {
		List<HashMap> value = DBUtilities.exSelectQuery(query, db);
		Scanner in = new Scanner(value.toString()).useDelimiter("[^A-Z]+");
		String val = in.next();
		return val;
	}

	public static String getcurrentdate(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}

	/**
	 * Return Hash256 of String value
	 *
	 * @param text
	 * @return
	 */
	public static String getHash256(String text) {
		try {
			return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
		} catch (Exception ex) {
			return "";
		}
	}
	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "gerUser", description = "\n1. Checking the info. of User")
	public void LoyaltyService_userInfo(String emailid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.USERINFO,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		log.info(service.URL);
		String msg1 = JsonPath
				.read(response, "$.userAccountInfo..activePointsBalance")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("Response---->>>>" + response);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				rs.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "debitloyaltyDP", description = "\n1. debit Loyalty points \n2.Verify 200 response")
	public void LoyaltyService_Debit(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String customerID, String ppsType) {

		creditLoyalityPoints(customerID, "100", "0", "0", "0", "automation",
				"ORDER", "123", "GOOD_WILL");
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited = getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer
				.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > "
				+ current_Points_AfterDebited);
		int deducted_Points = (current_Points_Int - current_Points_AfterDebited_Int);
		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(100, deducted_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitloyaltyMorethanActivePointDP", description = "\n1.Debit Loyalty Points more than active points \n2.Verify Failure response")
	public void LoyaltyService_DebitMoreThanActivePoint(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String customerID, String ppsType) {

		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited = getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer
				.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > "
				+ current_Points_AfterDebited);

		AssertJUnit.assertEquals(current_Points_Int,
				current_Points_AfterDebited_Int);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "debitloyaltyNegativePointDP", description = "\n1.Debit negative points \n2.Verify 200 status \n3. Verify Failure message")
	public void LoyaltyService_DebitNegativeLoyaltyPoint(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String customerID, String ppsType) {

		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited = getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer
				.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > "
				+ current_Points_AfterDebited);
		AssertJUnit.assertEquals(current_Points_Int,
				current_Points_AfterDebited_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("message mismatched", "INCOMPLETE TXN ENTRY",
				comment);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitloyaltyLessThanPointDP", description = "\n1.Debit less than 50 Loyalty Points \n2. Verify Failure response")
	public void LoyaltyService_DebitLessThan50LoyaltyPoint(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("response ------ >>>" + response);
		String current_Points_AfterDebited = getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer
				.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debit---- > "
				+ current_Points_AfterDebited);

		AssertJUnit.assertEquals(current_Points_Int,
				current_Points_AfterDebited_Int);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "refundloyaltyDP", description = "\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status")
	public void LoyaltyService_Refund(String checksum, String ppsID,
			String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {

		creditLoyalityPoints(customerID, "100", "0", "0", "0", "automation",
				"ORDER", "123", "GOOD_WILL");
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		AssertJUnit.assertEquals(current_Points_AfterRefunded_Int,
				current_Points_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "refundloyaltyForVoidedTransactionDP", description = "\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status \n4. Void the same client Tx.id")
	public void LoyaltyService_RefundwithVoidedTransaction(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {

		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		System.out.println("current balance---- > " + current_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		if (msg1.contains("TX_FAILURE")) {
			String comment = JsonPath.read(jsonRes, "$.params..comments")
					.toString().replace("[", "").replace("]", "")
					.replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);

		}

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "refundloyaltyDP_MoreThan_Debit", description = "\n1. Debit Loyalty Points \n2. Try to Refund the loyalty points more than debited loyalty points \n3. Verify 200 status \n4.Verfiy the failure message")
	public void LoyaltyService_Refund_withRefundMoreThanDebit(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg",
				"REFUND AMOUNT IS MORE THAN DEBIT AMOUNT", comment);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "refundloyaltyDP_withSameTXN", description = "\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status \n4. Again Refund the same loyalty points with same client Tx. id \n5.Verfiy the failure message")
	public void LoyaltyService_RefundTwice_WithSameCLientTXN(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {

		int count = 0;
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);
		count++;

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		if (msg1.contains("TX_FAILURE")) {
			String comment = JsonPath.read(jsonRes, "$.params..comments")
					.toString().replace("[", "").replace("]", "")
					.replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(current_Points_AfterRefunded_Int,
					current_Points_Int);
			AssertJUnit.assertEquals("Getting refunded again",
					"TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", comment);

		} else {
			System.out
					.println("Next dataprovider will be run to verify the this testcase");
			AssertJUnit.assertEquals(current_Points_AfterRefunded_Int,
					current_Points_Int + 100);

		}

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundloyaltyDP_LessThan_Debit", description = "\n1. Debit Loyalty Points \n2. Refund partial loyalty points \n3. Verify 200 status \n4.Verify Success message")
	public void LoyaltyService_Refund_withRefundLessThanDebit(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "refundloyaltyDP_ToInvalidEMail", description = "\n1. Debit Loyalty Points of invalid user \n2. Refund the loyalty points \n3. Verify 200 status \n4. Verfiy failure message")
	public void LoyaltyService_Refund_withInvalidUser(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg",
				"CUSTOMER ID NOT PRESENT OR DIFFERENT", comment);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "refundloyaltyDP_ToInvalidTXN", description = "\n1. Debit Loyalty Points \n2. Refund the loyalty points for Invalid Client tx. \n3. Verify 200 status")
	public void LoyaltyService_Refund_withInvalidClientId(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg", "NO SUCH CLIENT TXN ID",
				comment);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getStatus", description = "\n1.Get the status of user \n2.Verify 200 response \n3. Verify success response")
	public void LoyaltyService_getStatus(String clientTransactionID,
			String checksum, String ppsId, String orderId) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.GETSTATUS,
				init.Configurations, new String[] { clientTransactionID,
						checksum, ppsId, orderId }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "voidTransactionDP", description = "\n1. Debit Loyalty Points \n2. Void the tx. \n3. Verify 200 status")
	public void LoyaltyService_VoidTransaction(String checksum, String ppsID,
			String ppsTransactionID, String clientTransactionID,String amount, String customerID,String ppsType) {
		String orderId = null;
		String ppsActionType = null;
		String instrumentActionType = null;
		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID,orderId,amount,ppsType,ppsActionType,instrumentActionType,customerID },
						PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterVoided_Int);
		AssertJUnit.assertEquals(current_Points_AfterVoided_Int,
				current_Points_AfterDebit_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "voidTransactionAlreadyVoideDP", description = "\n1. Debit Loyalty Points \n2.Take debit client tx. id and void the tx. \n3. Verify 200 status \n4.Again Void the same tx. with same Client tx. id \n5. Verify the failure response")
	public void LoyaltyService_VoidTransactionOnAlreadyVoidedTxn(
			String checksum, String ppsID, String ppsTransactionID,
			String clientTransactionID,String amount, String customerID,String ppsType) {
		String orderId = null;
		String ppsActionType = null;
		String instrumentActionType = null;
		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID,orderId,amount,ppsType,ppsActionType,instrumentActionType,customerID}, PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > "
				+ current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		if (msg1.contains("TX_FAILURE")) {
			String comment = JsonPath.read(jsonRes, "$.params..comments")
					.toString().replace("[", "").replace("]", "")
					.replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(current_Points_AfterVoided_Int,
					current_Points_AfterDebit_Int);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);

		}

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "voidTransactionOnRefundedDP", description = "\n1. Debit Loyalty Points \n2. Take debit client tx. and refund the loyalty points \n3. Verify 200 status \n4.Void the same debit tx. using the same client tx.")
	public void LoyaltyService_VoidTransactionOnDebitTransactionID(
			String checksum, String ppsID, String ppsTransactionID,
			String clientTransactionID,String amount, String customerID,String ppsType) {
		String orderId = null;
		String ppsActionType = null;
		String instrumentActionType = null;
		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID,orderId,amount,ppsType,ppsActionType,instrumentActionType,customerID}, PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > "
				+ current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		if (msg1.contains("TX_FAILURE")) {
			String comment = JsonPath.read(jsonRes, "$.params..comments")
					.toString().replace("[", "").replace("]", "")
					.replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(
					"CANNOT VOID SINCE REFUND WAS DONE ON CLIENT TXN", comment);

		}

	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "voidTransactionOnRefundedTxnDP", description = "\n1. Debit Loyalty Points \n2. Take debit client tx. and refund the loyalty points \n3. Verify 200 status \n4.Void the same debit tx. using the same client tx.")
	public void LoyaltyService_VoidTransactionOnRefundedTransactionID(
			String checksum, String ppsID, String ppsTransactionID,
			String clientTransactionID,String amount, String customerID,String ppsType) {
		String orderId = null;
		String ppsActionType = null;
		String instrumentActionType = null;
		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID,orderId,amount,ppsType,ppsActionType,instrumentActionType,customerID}, PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > "
				+ current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);

	}

	private String getClientTransactionID(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String customerID, String ppsType) {

		creditLoyalityPoints(customerID, "100", "0", "0", "0", "automation",
				"ORDER", "123", "GOOD_WILL");
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited = getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer
				.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > "
				+ current_Points_AfterDebited);
		int deducted_Points = (current_Points_Int - current_Points_AfterDebited_Int);
		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(100, deducted_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String clientId = JsonPath
				.read(jsonRes, "$.params..clientTransactionID").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		return clientId;
	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "voidInvalidTransactionDP", description = "\n1. Debit Loyalty Points \n2. Take Invalid client tx. and void the loyalty points \n3. Verify 200 status \n4.Verify failure message")
	public void LoyaltyService_VoidTransactionWithInvalidClientTransaction(
			String checksum, String ppsID, String ppsTransactionID,
			String clientTransactionID,String amount, String customerID,String ppsType) {
		String orderId = null;
		String ppsActionType = null;
		String instrumentActionType = null;
		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID,orderId,amount,ppsType,ppsActionType,instrumentActionType,customerID }, PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterVoided_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("NO SUCH CLIENT TXN ID", comment);

	}

	private String getCurrentbalance(String emailid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.USERINFO,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		log.info(service.URL);
		String msg1 = JsonPath
				.read(response, "$.userAccountInfo..activePointsBalance")
				.toString().replace("[", "").replace("]", "").trim();
		return msg1;
	}

	private int getCount(String conString) {
		Statement stmt = null;
		Connection con = null;
		int count = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			System.out.println(":::::::::::::::" + con);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String query = "select count(*) from pps_transactions";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
				// System.out.println("count(*)"+ count);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return count;

	}

	private String generateRandomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit
					+ (int) (new Random().nextFloat() * (rightLimit - leftLimit));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		System.out.println(generatedString);
		return generatedString;
	}

	private String generateRandomNumber() {
		Random randomno = new Random();
		int number = randomno.nextInt(10000);
		String randomNumber = String.valueOf(number);
		return randomNumber;
	}

	public void creditLoyalityPoints(String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYPOINTS, init.Configurations,
				new String[] { login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "checksum")
	public void LoyaltyService_getDebitAndRefundChecksum(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String customerID, String ppsType) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUNDDEBIT,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.XML);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);

		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("responsr checksome------ >>>" + jsonRes);
	}

	@Test(groups = { "SchemaValidation", "Regression", "Fox7Sanity", }, dataProvider = "debitloyalty", priority = 32, description = "1.debit Loyalty Points \n 2. verify status code 200 response \n 3. validate nodes and schema")
	public void debitLoyalty_ServiceUsingSchemaValidations(String checksum,
			String ppsID, String ppsTransactionID, String orderId,
			String amount, String customerID, String ppsType) {
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);

		String value = JsonPath.read(response, "$.params.txStatus").toString()
				.replace("[", "").replace("]", "").trim();

		System.out.println("Printing response of Debit api: " + value);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalty-service-debitresponse.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(response,
							jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing in LoyaltyService API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation", "Regression", "Fox7Sanity", }, dataProvider = "refundloyalty", priority = 32, description = "1.debit Loyalty Points \n 2.Take the client tx. id and refund the amount 2. verify status code 200 response \n 4. validate nodes and schema")
	public void refundLoyalty_ServiceUsingSchemaValidations(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String orderId, String amount, String ppsType, String customerID) {
		creditLoyalityPoints(customerID, "100", "0", "0", "0", "automation",
				"ORDER", "123", "GOOD_WILL");
		String current_Points = getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, clientTransactionID, orderId, amount,
						ppsType, customerID }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded = getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer
				.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterRefunded);

		String value = JsonPath.read(response, "$.params.txStatus").toString()
				.replace("[", "").replace("]", "").trim();

		System.out.println("Printing refund api status : " + value);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalty-service-refundresponse.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(response,
							jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing in LoyaltyService API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation", "Regression", "Fox7Sanity" }, dataProvider = "voidTx", priority = 32, description = "1.debit Loyalty Points \n 2.Take the client tx. id and refund the amount 2. verify status code 200 response \n 4. validate nodes and schema")
	public void voidLoyalty_ServiceUsingSchemaValidations(String checksum,
			String ppsID, String ppsTransactionID, String clientTransactionID,
			String customerID) {

		String current_Points_AfterDebit = getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer
				.parseInt(current_Points_AfterDebit);
		System.out.println("current balance---- > "
				+ current_Points_AfterDebit_Int);
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALTYSERVICE,
						APINAME.VOIDTRANSACTION, init.Configurations,
						new String[] { checksum, ppsID, ppsTransactionID,
								clientTransactionID },
						new String[] { customerID }, PayloadType.JSON,
						PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided = getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer
				.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Refund---- > "
				+ current_Points_AfterVoided_Int);

		AssertJUnit.assertEquals(current_Points_AfterVoided_Int,
				current_Points_AfterDebit_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalty-service-voidresponse.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(response,
							jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing in LoyaltyService API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "Smoke", "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"Fox7Sanity", "ExhaustiveRegression" }, dataProvider = "getStatusSchema", description = "\n1. get response from user info api \n2. Verify schema according to response \n3. Verify 200 status ")
	public void LoyaltyService_getStatusSchema(String clientTransactionID,
			String checksum, String ppsId, String orderId) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.GETSTATUS,
				init.Configurations, new String[] { clientTransactionID,
						checksum, ppsId, orderId }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);
		AssertJUnit.assertEquals("Debit loyalty not working is not working",
				200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalty-service-getstatusresponse.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(response,
							jsonschema);
			AssertJUnit.assertTrue(missingNodeList
					+ " nodes are missing in LoyaltyService API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifySuccessResponse(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		String query = "select sum(`balance`) from loyalty_points_credit_entry where login="+'"'+login+'"';
		int currentpoint = getloyaltypointsquery(query,"lp");
		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);
		// System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String conversionFactor = req.respvalidate
				.NodeValue("userAccountEntry.conversionFactor", true).replaceAll("\"", "")
				.trim();
		String updatedbalance = req.respvalidate
				.NodeValue("userAccountEntry.usableActivePoints", true).replaceAll("\"", "")
				.trim();
		String cashEqualantAmount = req.respvalidate
				.NodeValue("userAccountEntry.cashEqualantAmount", true).replaceAll("\"", "")
				.trim();
		float expectedbalance=Float.parseFloat(updatedbalance)*Float.parseFloat(conversionFactor);

		String activeCreditPoint = req.respvalidate
				.NodeValue("transactiontEntry.activeCreditPoints", true)
				.replaceAll("\"", "").trim();
		int expectedpoint = currentpoint + Integer.parseInt(activeCreditPoint);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("Credited successfully for the user : "+login));
		int updatedpoint = getloyaltypointsquery(query,"lp");
		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
				expectedpoint, updatedpoint);
		AssertJUnit.assertEquals("cashEqualantAmount is not correct",
				expectedbalance, Float.parseFloat(cashEqualantAmount));

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyStatusMessage(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("Credited successfully for the user : "+login));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyStatusType(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println(statusType);

		AssertJUnit.assertTrue("StatusType does not match",
				req.respvalidate.NodeValue("status.statusType", true)
						.toLowerCase().contains("success"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}


	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyLogin(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyaltyPointsDataProvider")
	public void LoyaltyPoints_creditLoyalityPoints_verifyTransactionEntry(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		RequestGenerator req = creditLoyaltyPoints(login, activeCreditPoints,
				inActiveCreditPoints, activeDebitPoints, inActiveDebitPoints,
				description, itemType, itemId, businessProcess);

		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Description in request is :" + description);
		String description_response = req.respvalidate
				.NodeValue("transactiontEntry.description", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Description in transactiontEntry Node's response is: "
						+ description_response);
		log.info(description_response);

		System.out.println("Item Type in request is :" + itemType);
		String itemType_response = req.respvalidate
				.NodeValue("transactiontEntry.itemType", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("item Type in transactiontEntry Node's response is: "
						+ itemType_response);
		log.info(itemType_response);

		System.out.println("Item ID in request is :" + itemId);
		String itemId_response = req.respvalidate
				.NodeValue("transactiontEntry.itemId", true)
				.replaceAll("\"", "").trim();
		System.out.println("itemID in transactiontEntry Node's response is: "
				+ itemId_response);
		log.info(itemId_response);

		System.out
				.println("Business Process in request is :" + businessProcess);
		String businessProcess_response = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Business Process in transactiontEntry Node's response is: "
						+ businessProcess_response);
		log.info(businessProcess_response);

		AssertJUnit
				.assertEquals(
						"Description in transactiontEntry Node's response does not match",
						description, description_response);
		AssertJUnit
				.assertEquals(
						"Item Type in transactiontEntry Node's response does not match",
						itemType, itemType_response);
		AssertJUnit.assertEquals(
				"Item ID in transactiontEntry Node's response does not match",
				itemId, itemId_response);
		AssertJUnit
				.assertEquals(
						"Business Process in transactiontEntry Node's response does not match",
						businessProcess, businessProcess_response);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	private Double getActiveLoyaltyPointBalance(String loginID) {
		String activePointsBalance;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		activePointsBalance = req.respvalidate.NodeValue(
				"userAccountEntry.activePointsBalance", true);
		AssertJUnit.assertEquals("Get Transaction History API is not working",
				200, req.response.getStatus());

		return Double.parseDouble(activePointsBalance);
	}

	private Double getInActiveLoyaltyPointBalance(String loginID) {
		String InactivePointsBalance;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		InactivePointsBalance = req.respvalidate.NodeValue(
				"userAccountEntry.inActivePointsBalance", true);
		AssertJUnit.assertEquals(
				"Get Transaction History Inactive API is not working", 200,
				req.response.getStatus());

		return Double.parseDouble(InactivePointsBalance);
	}

	static RequestGenerator getActiveLoyaltyPointBalanceV2(String login,String tenantId) {
		String ActivePointsBalance;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTBALANCEV2, init.Configurations, PayloadType.JSON,
				PayloadType.JSON);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-mynt-ctx", "storeid="+ tenantId +";uidx=" + login+";nidx=myntra;");
		header.put("authorization", "client=qa;version=1.0;");
		RequestGenerator req =  new RequestGenerator(service,header);

		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Get Account Balance of Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;
	}

	private RequestGenerator debitLoyaltyPoints(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	private RequestGenerator debitLoyaltyPointsV2(String login,
												String points, String ppsID,
												String ppsTransactionID, String orderId,
												String businessProcess,String tenantId,String retry) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTSV2, init.Configurations, new String[] {
						login, points, ppsID,
						ppsTransactionID, orderId, businessProcess,retry }, PayloadType.JSON,
				PayloadType.JSON);
		String keyval="qa|1.0|"+ppsID+"|"+points+"|"+ppsTransactionID+"|"+orderId+"|"+retry+"|"+login+"|qa";
        String hashkey=getHash256(keyval);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-mynt-ctx", "storeid="+ tenantId +";uidx=" + login+";nidx=myntra;");
		header.put("authorization", "client=qa;version=1.0;signature="+hashkey+";");
		RequestGenerator req =  new RequestGenerator(service,header);

		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	private RequestGenerator creditLoyaltyPoints(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYPOINTS, init.Configurations,
				new String[] { login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}
	
	static RequestGenerator creditLoyaltyPointsV2(String login,String points, String businessProcess,
			String expiryDate,
			String description,String tenantId) {
		String keyval="qa|1.0|"+points+"|"+expiryDate+"|"+login+"|"+businessProcess+"|qa";
		String hashkey=getHash256(keyval);
		System.out.println("keyval- "+keyval+" hashkey- "+hashkey);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYPOINTSV2, init.Configurations,
				new String[] { login, points,businessProcess,
				expiryDate, description }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-mynt-ctx", "storeid="+ tenantId +";uidx=" + login+";nidx=myntra;");
		header.put("authorization", "client=qa;version=1.0;signature="+hashkey+";");
		RequestGenerator req =  new RequestGenerator(service,header);

		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	static RequestGenerator bulkCreditLoyaltyPointsV2(String transactionId,String batchId, String login1,String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3,String tenantId) {
		String keyval="qa|1.0|"+transactionId+"|"+batchId+"|qa";
		String hashkey=getHash256(keyval);
		System.out.println("keyval- "+keyval+" hashkey- "+hashkey);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.BULKCREDIT, init.Configurations,
				new String[] { transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3 }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("x-mynt-ctx", "storeid="+ tenantId +";uidx=" + login1+";nidx=myntra;");
		header.put("authorization", "client=qa;version=1.0;signature="+hashkey+";");
		RequestGenerator req =  new RequestGenerator(service,header);

		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
		return req;

	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifySuccessResponse(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		String expiryDate=getcurrentdate();
		String query = "select sum(`balance`) from loyalty_points_credit_entry where login="+'"'+login+'"'+ " and `expiry_date`>'"+expiryDate+"'";
		int currentpoint = getloyaltypointsquery(query,"lp");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						activeDebitPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("debited successfuly"));
		String activeDebitPoint = req.respvalidate
				.NodeValue("transactiontEntry.activeDebitPoints", true)
				.replaceAll("\"", "").trim();
		int expectedpoint = currentpoint - Integer.parseInt(activeDebitPoint);
		int updatedpoint = getloyaltypointsquery(query,"lp");
		AssertJUnit.assertEquals("Loyalty points is not debited into DB",
				expectedpoint, updatedpoint);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusMessage(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			log.info(statusMessage);
			System.out.println(statusMessage);
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusType(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

		}

		else {

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			log.info(statusType);
			System.out.println(statusType);

			AssertJUnit.assertTrue("StatusType does not match",
					req.respvalidate.NodeValue("status.statusType", true)
							.toLowerCase().contains("success"));
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyStatusCode(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		Double balanceBefore, DebitPoints;
		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));

		}

		else {

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);
			System.out.println(service.URL);
			MyntAssert.assertEquals("Debit Loyality Points is not working",
					200, req.response.getStatus());

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			log.info(statusCode);
			System.out.println(statusCode);

			AssertJUnit.assertTrue("StatusCode does not match",
					req.respvalidate.NodeValue("status.statusCode", true)
							.toLowerCase().contains("1"));
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_debitActiveLoyalityPointsCalculations_positiveCases(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Points to be Debited is greater than the balanace available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch", balanceBefore,
					balanceAfter);
		}

		else {
			// System.out.println("........Debiting Active Loyalty Points....");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore
					- Double.parseDouble(activeDebitPoints);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);

			MyntAssert.assertEquals(
					"Debit Active Loyality Points API is not working", 200,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyaltyPointsNegativeCasesDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_negativeCases(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {
		Double balanceBefore, DebitPoints;

		DebitPoints = Double.parseDouble(activeDebitPoints);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit: "
				+ balanceBefore);
		System.out.println("Points to be debited: " + DebitPoints);

		if (DebitPoints < 0) {
			System.out.println("Points to be Debited is lesser than zero");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("-1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("incomplete transaction entry"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));
		}

		else {
			// Expected 500 internal server error
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			AssertJUnit.assertEquals("API response code mismatch", 500,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitInActiveLoyaltyPointsPositiveCasesDataProvider")
	public void LoyaltyPoints_debitInActiveLoyalityPoints_verifyCalculation(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(inActiveDebitPoints);

		balanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance before debit:"
				+ balanceBefore);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than Balance available");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("SUCCESS"));

			balanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch", 0.0, balanceAfter);

		}

		else {

			System.out
					.println(".....Debiting Inactive Loyalty Points from account..... ");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore
					- Double.parseDouble(inActiveDebitPoints);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);

			MyntAssert.assertEquals(
					"Debit InActive Loyality Points API is not working", 200,
					req.response.getStatus());
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitBothActiveAndInactiveLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitBothActiveAndInactiveLoyaltyPoints_verifyCalculations(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		Double ActivePointsBalanceBefore, ActivePointsBalanceAfter, FinalActivePointsBalance;
		Double InactivePointsBalanceBefore, InactivePointsBalanceAfter, FinalInactivePointsBalance, DebitActivePoints, DebitInactivePoints;

		DebitActivePoints = Double.parseDouble(activeDebitPoints);
		DebitInactivePoints = Double.parseDouble(inActiveDebitPoints);

		ActivePointsBalanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points Balance before Debit :"
				+ ActivePointsBalanceBefore);

		System.out.println("Active Points to be Debited :" + DebitActivePoints);

		InactivePointsBalanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points Balance before Debit :"
				+ InactivePointsBalanceBefore);

		System.out.println("In Active Points to be Debited :"
				+ DebitInactivePoints);

		// If Debit Active points is > Active Points Balance, irrespective of
		// whether In Active Debit Points is greater or lesser than In Active
		// Points Balance
		if (DebitActivePoints > ActivePointsBalanceBefore) {
			System.out
					.println(" Active Debit Points is greater than Active Points Balance");
			RequestGenerator newReq = debitLoyaltyPoints(login,
					activeCreditPoints, inActiveCreditPoints,
					activeDebitPoints, inActiveDebitPoints, description,
					itemType, itemId, businessProcess);

			String statusMessage = newReq.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			String statusType = newReq.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusType : " + statusType);

			String statusCode = newReq.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();

			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance does not change:"
					+ ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out
					.println("In Active Loyalty Points balance does not change  :"
							+ InactivePointsBalanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch for Active Points",
					ActivePointsBalanceBefore, ActivePointsBalanceAfter);
			AssertJUnit.assertEquals("Balance Mismatch for In Active Points",
					InactivePointsBalanceBefore, InactivePointsBalanceAfter);

		}

		// If Debit Active points is < Active Points Balance, and In Active
		// Debit Points is > than In Active Points Balance
		else if ((DebitActivePoints < ActivePointsBalanceBefore)
				&& (DebitInactivePoints > InactivePointsBalanceBefore)) {
			System.out
					.println(" Active Points will be debited. Inactive points will become zero");

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);

			RequestGenerator req = new RequestGenerator(service);

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ ActivePointsBalanceAfter);

			FinalActivePointsBalance = ActivePointsBalanceBefore
					- DebitActivePoints;
			System.out.println("Final Active Poinys Balance:"
					+ FinalActivePointsBalance);

			AssertJUnit.assertEquals(
					"Balance Mismatch for Active Debit Points",
					FinalActivePointsBalance, ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points balance after debit :"
					+ InactivePointsBalanceAfter);

			AssertJUnit.assertEquals("Balance Mismatch for In Active Points",
					0.0, InactivePointsBalanceAfter);

		}

		else if ((DebitActivePoints < ActivePointsBalanceBefore)
				&& (DebitInactivePoints < InactivePointsBalanceBefore)) {
			System.out
					.println("Both Active and In Active Points will be debited");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY, APINAME.DEBITLOYALITYPOINTS,
					init.Configurations, new String[] { login,
							activeCreditPoints, inActiveCreditPoints,
							activeDebitPoints, inActiveDebitPoints,
							description, itemType, itemId, businessProcess },
					PayloadType.JSON, PayloadType.JSON);

			RequestGenerator req = new RequestGenerator(service);

			ActivePointsBalanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points Balance after Debit :"
					+ ActivePointsBalanceAfter);

			InactivePointsBalanceAfter = getInActiveLoyaltyPointBalance(login);
			System.out.println("InActive Loyalty Points Balance after Debit :"
					+ InactivePointsBalanceAfter);

			FinalActivePointsBalance = ActivePointsBalanceBefore
					- Double.parseDouble(activeDebitPoints);
			System.out
					.println("Final Active Loyalty Points Balance after Debit :"
							+ FinalActivePointsBalance);

			FinalInactivePointsBalance = InactivePointsBalanceBefore
					- Double.parseDouble(inActiveDebitPoints);
			System.out
					.println("Final InActive Loyalty Points Balance after Debit :"
							+ FinalInactivePointsBalance);

			AssertJUnit.assertEquals("Active Debit Points Balance Mismatch",
					FinalActivePointsBalance, ActivePointsBalanceAfter);

			AssertJUnit.assertEquals("InActive Debit Points Balance Mismatch",
					FinalInactivePointsBalance, InactivePointsBalanceAfter);

		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyLogin(String login,
			String activeCreditPoints, String inActiveCreditPoints,
			String activeDebitPoints, String inActiveDebitPoints,
			String description, String itemType, String itemId,
			String businessProcess) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						inActiveCreditPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsDataProvider")
	public void LoyaltyPoints_debitLoyalityPoints_verifyTransactionEntry(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYPOINTS, init.Configurations, new String[] {
						login, activeCreditPoints, inActiveCreditPoints,
						inActiveCreditPoints, inActiveDebitPoints, description,
						itemType, itemId, businessProcess }, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);

		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());

		System.out.println("Description in request is :" + description);
		String description_response = req.respvalidate
				.NodeValue("transactiontEntry.description", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Description in transactiontEntry Node's response is: "
						+ description_response);
		log.info(description_response);

		System.out.println("Item Type in request is :" + itemType);
		String itemType_response = req.respvalidate
				.NodeValue("transactiontEntry.itemType", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("item Type in transactiontEntry Node's response is: "
						+ itemType_response);
		log.info(itemType_response);

		System.out.println("Item ID in request is :" + itemId);
		String itemId_response = req.respvalidate
				.NodeValue("transactiontEntry.itemId", true)
				.replaceAll("\"", "").trim();
		System.out.println("itemID in transactiontEntry Node's response is: "
				+ itemId_response);
		log.info(itemId_response);

		System.out
				.println("Business Process in request is :" + businessProcess);
		String businessProcess_response = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();
		System.out
				.println("Business Process in transactiontEntry Node's response is: "
						+ businessProcess_response);
		log.info(businessProcess_response);

		AssertJUnit
				.assertEquals(
						"Description in transactiontEntry Node's response does not match",
						description, description_response);
		AssertJUnit
				.assertEquals(
						"Item Type in transactiontEntry Node's response does not match",
						itemType, itemType_response);
		AssertJUnit.assertEquals(
				"Item ID in transactiontEntry Node's response does not match",
				itemId, itemId_response);
		AssertJUnit
				.assertEquals(
						"Business Process in transactiontEntry Node's response does not match",
						businessProcess, businessProcess_response);
		MyntAssert.setExpectedAndActualForMyntListen(true);

	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"transactionHistoryInactivePoints API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out
				.println("Status Message for Transaction History Inactive Point API is :"
						+ statusMessage);

		AssertJUnit
				.assertTrue(
						"Status Message for Transaction History Inactive Point API does not match",
						statusMessage.contains("transaction history"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyStatusType(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for Transaction History Inactive Point API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for Transaction History Inactive Point API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryInactivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryInactivePoints_verifyLogin(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYINACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Transaction History Inactive Point API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"transactionHistoryActivePoints API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out
				.println("Status Message for Transaction History Active Point API is :"
						+ statusMessage);

		AssertJUnit
				.assertTrue(
						"Status Message for Transaction History Active Point API does not match",
						statusMessage.contains("transaction history"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyStatusType(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for Transaction History Active Point API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for Transaction History Active Point API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "transactionHistoryActivePointsDataProvider")
	public void LoyaltyPoints_transactionHistoryActivePoints_verifyLogin(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.TRANSACTIONHISTORYACTIVE, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Transaction History Active Point API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Smoke", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifySuccessResponse(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		MyntAssert.assertEquals(
				"Response code of getAccountInfo API does not match", 200,
				req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyStatusMessage(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println("Status Message for Get Account Info API is :"
				+ statusMessage);

		AssertJUnit.assertTrue(
				"Status Message for Get Account Info API does not match",
				statusMessage
						.contains("account info for user : "
								+ login));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyStatusType(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println("Status Type for Get Account Info API is :"
				+ statusType);

		AssertJUnit.assertTrue(
				"Status Type for Get Account Info API does not match",
				statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "getAccountInfoDataProvider")
	public void LoyaltyPoints_getAccountInfo_verifyLogin(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID sent in request is :" + login);
		String loginID = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		log.info(loginID);
		System.out
				.println("Login ID captured from Get Account Info API's response is :"
						+ loginID);

		AssertJUnit.assertEquals("Login ID does not match", login, loginID);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getAccountInfoDataProvider_negativeCases")
	public void LoyaltyPoints_getAccountInfoNegativeCases(String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCOUNTINFOLOYALITY, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("Response code", 200, req.response.getStatus());
		MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points in inactive credit points account
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for creditLoyalityOrderConfirmation API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyStatusMessage(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("credited successfuly"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyStatusType(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for creditLoyalityOrderConfirmation API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for creditLoyalityOrderConfirmation API does not match",
						statusType.contains("SUCCESS"));
		// MyntAssert.setJsonResponse(req.respvalidate.returnresponseasstring());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyLogin(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points to inactive points balance account
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBalancePositiveCases(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance before credit:"
				+ balanceBefore);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		balanceAfter = getInActiveLoyaltyPointBalance(login);
		System.out.println("InActive Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(points);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationNegativeCasesDataProvider", enabled = false)
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBalanceNegativeCases(
			String login, String points, String orderID, String description) {
		Double OrderId, Debitpoints;
		OrderId = Double.parseDouble(orderID);
		Debitpoints = Double.parseDouble(points);

		if ((OrderId < 0) || ((OrderId < 0 && Debitpoints < 0))) {
			System.out
					.println("Either only Order ID is lesser than zero or both OrderId and DebitPoints are lesser than zero");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
					init.Configurations, new String[] { login, points, orderID,
							description, }, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("10008"));
			AssertJUnit
					.assertTrue(
							"Status Message doesn't match",
							statusMessage
									.contains("Points for item cancellation could not be debited"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));

		}

		else {
			System.out.println("Only Debit Points is lesser than zero");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.ACTIVATELOYALITYPOINTSFORORDER,
					init.Configurations, new String[] { login, points, orderID,
							description, }, PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();

			System.out.println("StatusCode : " + statusCode);
			System.out.println("statusMessage : " + statusMessage);
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status code doesn't match",
					statusCode.contains("1"));
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("activated points successfuly"));
			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("SUCCESS"));

		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYORDERCONFIRMATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("POINTS_AWARDED"));

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points in active credit points account
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert
				.assertEquals(
						"Response code for creditLoyalityItemCancellation API does not match",
						200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyStatusMessage(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals(
				"creditLoyalityItemCancellation API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("credited successfuly"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyStatusType(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out
				.println("Status Type for creditLoyalityItemCancellation API is :"
						+ statusType);

		AssertJUnit
				.assertTrue(
						"Status Type for creditLoyalityItemCancellation API does not match",
						statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyLogin(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will add points in active credit points account
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellationPositiveCases_verifyBalance(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance;

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before credit:"
				+ balanceBefore);

		System.out.println("Points to be credited :" + points);

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		balanceAfter = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance after credit :"
				+ balanceAfter);

		finalBalance = balanceBefore + Double.parseDouble(points);
		System.out.println("Final Balance:" + finalBalance);

		AssertJUnit
				.assertEquals("Balance Mismatch", finalBalance, balanceAfter);

		MyntAssert.assertEquals(
				"creditLoyalityItemCancellation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityItemCancellationPositiveCasesDataProvider")
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREDITLOYALITYITEMCANCELLATION, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("ITEM_CANCELLATION"));

		MyntAssert.assertEquals(
				"creditLoyalityOrderConfirmation API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will deduct points from active points balance against a
	// orderid
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifySuccessResponse(
			String login, String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals(
				"Response code for debitLoyalityOrderusage API does not match",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusMessage(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("Loyalty Points Debit Failure"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true)
					.replaceAll("\"", "").trim();
			System.out.println("statusMessage :" + statusMessage);
			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("debited successfuly"));
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusType(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusType : " + statusType);

			AssertJUnit.assertTrue("Status Type doesn't match",
					statusType.contains("ERROR"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);

			String statusType = req.respvalidate
					.NodeValue("status.statusType", true).replaceAll("\"", "")
					.trim();
			log.info(statusType);
			System.out
					.println("Status Type for debitLoyalityOrderUsage API is :"
							+ statusType);

			AssertJUnit
					.assertTrue(
							"Status Type for debitLoyalityOrderUsage API does not match",
							statusType.contains("SUCCESS"));
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyStatusCode(
			String login, String points, String orderID, String description) {

		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusCode : " + statusCode);

			AssertJUnit.assertTrue("Status Code doesn't match",
					statusCode.contains("10004"));
		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);
			log.info(service.URL);

			String statusCode = req.respvalidate
					.NodeValue("status.statusCode", true).replaceAll("\"", "")
					.trim();
			log.info(statusCode);
			System.out
					.println("Status Code for debitLoyalityOrderUsage API is :"
							+ statusCode);

			AssertJUnit
					.assertTrue(
							"Status Code for debitLoyalityOrderUsage API does not match",
							statusCode.contains("1"));
			MyntAssert.setExpectedAndActualForMyntListen(true);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyLogin(String login,
			String points, String orderID, String description) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID = req.respvalidate
				.NodeValue("transactiontEntry.login", true)
				.replaceAll("\"", "").trim();
		System.out.println("Login ID in transactiontEntry Node's response is: "
				+ loginID);
		log.info(loginID);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountEntry.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountEntry Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in transactiontEntry Node does not match", login,
				loginID);
		AssertJUnit.assertEquals(
				"Login ID in userAccountEntry Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	// This test case will debit points from active points balance account
	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyBalance(
			String login, String points, String orderID, String description) {
		Double balanceBefore, balanceAfter, finalBalance, DebitPoints;

		DebitPoints = Double.parseDouble(points);

		balanceBefore = getActiveLoyaltyPointBalance(login);
		System.out.println("Active Loyalty Points balance before debit:"
				+ balanceBefore);
		System.out.println("Points to be Debited:" + DebitPoints);

		if (DebitPoints > balanceBefore) {
			System.out
					.println("Amount to be debited is greater than balance available. Balance will remain as such");
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			System.out
					.println("Active points balance after debit captured from it's API response :"
							+ req.respvalidate.NodeValue(
									"userAccountEntry.activePointsBalance",
									true));
			System.out
					.println("Active points balance after debit captured from API Validation's response :");
			balanceAfter = getActiveLoyaltyPointBalance(login);

			AssertJUnit.assertEquals("Balance Mismatch", balanceBefore,
					balanceAfter);

		}

		else {
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_LOYALITY,
					APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
					new String[] { login, points, orderID, description, },
					PayloadType.JSON, PayloadType.JSON);
			RequestGenerator req = new RequestGenerator(service);

			balanceAfter = getActiveLoyaltyPointBalance(login);
			System.out.println("Active Loyalty Points balance after debit :"
					+ balanceAfter);

			finalBalance = balanceBefore - Double.parseDouble(points);
			System.out.println("Final Balance:" + finalBalance);

			AssertJUnit.assertEquals("Balance Mismatch", finalBalance,
					balanceAfter);
		}
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "debitLoyalityOrderUsageDataProvider")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyBusinessProcess(
			String login, String points, String orderID, String description) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.DEBITLOYALITYORDERUSAGE, init.Configurations,
				new String[] { login, points, orderID, description, },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);

		String businessProcess = req.respvalidate
				.NodeValue("transactiontEntry.businessProcess", true)
				.replaceAll("\"", "").trim();

		System.out.println("Business Process captured in response is : "
				+ businessProcess);

		AssertJUnit.assertTrue("Business Process does not match",
				businessProcess.contains("POINTS_REDEEMED"));

		MyntAssert.assertEquals("debitLoyalityOrderusage API is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifySuccessResponse(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals(
				"Response code for Mymyntra Account Info API does not match",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyStatusMessage(
			String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		MyntAssert.assertEquals("myMyntraAccountInfo API is not working", 200,
				req.response.getStatus());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyStatusType(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		log.info(statusType);
		System.out.println("Status Type for myMyntraAccountInfo API is :"
				+ statusType);

		AssertJUnit.assertTrue(
				"Status Type for myMyntraAccountInfo API does not match",
				statusType.contains("SUCCESS"));
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "mymyntraAccountInfoDataProvider")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyLogin(String login) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.MYMYNTRAACCOUNTINFO, init.Configurations,
				PayloadType.JSON, new String[] { login }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("Login ID in request is :" + login);

		String loginID1 = req.respvalidate
				.NodeValue("userAccountInfo.login", true).replaceAll("\"", "")
				.trim();
		System.out.println("Login ID in userAccountInfo Node's response is: "
				+ loginID1);
		log.info(loginID1);

		AssertJUnit.assertEquals(
				"Login ID in userAccountInfo Node does not match", login,
				loginID1);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "acceptTermsAndConditionsDataProvider")
	public void LoyaltyPoints_acceptTermsAndConditions_verifySuccessResponse(
			String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCEPTTERMSANDCONDITIONS, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		log.info(req.response);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"LoyalityPoints_acceptTermsAndConditions is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "acceptTermsAndConditionsDataProvider")
	public void LoyaltyPoints_acceptTermsAndConditions_verifyNodeValues(
			String loginID) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.ACCEPTTERMSANDCONDITIONS, init.Configurations,
				PayloadType.JSON, new String[] { loginID }, PayloadType.JSON);
		log.info(service.URL);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusType : " + statusType);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusCode : " + statusCode);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("successfully recorded TNC action"));

		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));

		AssertJUnit.assertTrue("Status Code doesn't match",
				statusCode.contains("1"));

	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValueDataProvider")
	public void LoyaltyPoints_configurationKeyValue_verifySuccessResponse(
			String key, String value) {
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALITY,
						APINAME.LOYALITYCONFIGURATION, init.Configurations,
						new String[] { key, value }, PayloadType.JSON,
						PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"configurationKeyValuePositiveCases is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValueDataProvider")
	public void LoyaltyPoints_configurationKeyValue_verifyNodeValues(
			String key, String value) {
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALITY,
						APINAME.LOYALITYCONFIGURATION, init.Configurations,
						new String[] { key, value }, PayloadType.JSON,
						PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		System.out.println("statusMessage : " + statusMessage);

		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusType : " + statusType);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();

		System.out.println("statusCode : " + statusCode);

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("saved the given key value pair"));

		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));

		AssertJUnit.assertTrue("Status Code doesn't match",
				statusCode.contains("1"));
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "loyalityPointsConfigurationKeyValue_negativeCases")
	public void LoyaltyPoints_configurationKeyValueNegativeCases(String key,
			String value) {
		MyntraService service = Myntra
				.getService(ServiceType.PORTAL_LOYALITY,
						APINAME.LOYALITYCONFIGURATION, init.Configurations,
						new String[] { key, value }, PayloadType.JSON,
						PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic bG95YWx0eXVzZXJ+bG95YWx0eXVzZXI6Q29uZmlnbG95YWx0eSQlXiYjQA==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals("configurationKeyValue API is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "geKkeyNameDataProvider_positiveCases")
	public void LoyaltyPoints_configurationKeyValueGetKeyName_positiveCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		MyntAssert.assertEquals(
				"configurationKeyValueKeyNamePositiveCases is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "ProdSanity", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "geKkeyNameDataProvider_positiveCases")
	public void LoyaltyPoints_configurationKeyValueGetKeyName_verifyNodeValues(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String value = req.respvalidate.NodeValue("keyValuePairs.value", true);
		System.out.println("Value found for given key - " + key + "is :"
				+ value);

		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();
		String key1 = req.respvalidate.NodeValue("keyValuePairs.key", true)
				.replaceAll("\"", "").trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);
		System.out.println("Key Captured in response is :" + key1);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
		AssertJUnit.assertEquals("Key doesn't match", key, key1);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getKeyNameDataProvider_negativeCases")
	public void LoyaltyPoints_configurationKeyValueKeyName_verifyResponse_NegativeCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals("configurationKeyValueFatchAll is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getKeyNameDataProvider_negativeCases")
	public void LoyaltyPoints_configurationKeyValueKeyName_verifyNodeValues_NegativeCases(
			String key) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.GETKEYNAME, init.Configurations, PayloadType.JSON,
				new String[] { key }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void LoyaltyPoints_configurationKeyValueFetchAll_verifySuccessResponse() {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.KEYVALUEFETCHALL, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		MyntAssert.assertEquals("configurationKeyValueFatchAll is not working",
				200, req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" })
	public void LoyaltyPoints_configurationKeyValueFetchAll_verifyNodeValues() {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.KEYVALUEFETCHALL, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// AssertJUnit.assertEquals(req.response.getStatus(), 200);
		String statusCode = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
		String statusType = req.respvalidate
				.NodeValue("status.statusType", true).replaceAll("\"", "")
				.trim();

		System.out.println("StatusCode : " + statusCode);
		System.out.println("statusMessage : " + statusMessage);
		System.out.println("statusType : " + statusType);

		AssertJUnit.assertTrue("Status code doesn't match",
				statusCode.contains("1"));
		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("value found for the given key"));
		AssertJUnit.assertTrue("Status Type doesn't match",
				statusType.contains("SUCCESS"));
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider", enabled = false)
	public void LoyaltyPoints_createLoyaltyGroup_VerifySuccessResponse(
			String brands, String genders, String articleTypes,
			String groupName, String groupDescription,
			String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		MyntAssert.assertEquals("createLoyalityGroup API is not working", 200,
				req.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV1","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createLoyaltyGroupDataProvider", enabled = false)
	public void LoyaltyPoints_createLoyaltyGroup_verifyGroupName(String brands,
			String genders, String articleTypes, String groupName,
			String groupDescription, String conversionFactorPoints) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALITY,
				APINAME.CREATELOYALITYGROUP, init.Configurations, new String[] {
						brands, genders, articleTypes, groupName,
						groupDescription, conversionFactorPoints },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);

		System.out.println("GroupName in request is :" + groupName);

		String GroupName = req.respvalidate
				.NodeValue(
						"lpGroupResponseEntry.loyaltyPointsGroupEntry.groupName",
						true).replaceAll("\"", "").trim();
		System.out.println("Group Name Captured in response is : " + GroupName);

		AssertJUnit.assertEquals(
				"Group Name in request and response does not match", groupName,
				GroupName);
		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "SchemaValidation", "Fox7Sanity" }, dataProvider = "LoyalityDP_creditLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_creditLoyalityPoints_verifyResponseDataNodesUsingSchemaValidations(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		RequestGenerator creditLoyalityPointsReqGen = loyalityPointsServiceHelper
				.invokeCreditLoyaltyPoints(login, activeCreditPoints,
						inActiveCreditPoints, activeDebitPoints,
						inActiveDebitPoints, description, itemType, itemId,
						businessProcess);
		String creditLoyalityPointsResponse = creditLoyalityPointsReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService creditLoyalityPoints API response :\n\n"
						+ creditLoyalityPointsResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityPoints API response :\n\n"
				+ creditLoyalityPointsResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService creditLoyalityPoints API is not working",
						200, creditLoyalityPointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalitypoints-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							creditLoyalityPointsResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService creditLoyalityPoints API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation", "Fox7Sanity" }, dataProvider = "LoyalityDP_debitLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_debitLoyalityPoints_verifyResponseDataNodesUsingSchemaValidations(
			String login, String activeCreditPoints,
			String inActiveCreditPoints, String activeDebitPoints,
			String inActiveDebitPoints, String description, String itemType,
			String itemId, String businessProcess) {
		RequestGenerator debitLoyalityPointsReqGen = loyalityPointsServiceHelper
				.invokeDebitLoyaltyPoints(login, activeCreditPoints,
						inActiveCreditPoints, activeDebitPoints,
						inActiveDebitPoints, description, itemType, itemId,
						businessProcess);
		String debitLoyalityPointsResponse = debitLoyalityPointsReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService debitLoyaltyPoints API response :\n\n"
						+ debitLoyalityPointsResponse);
		log.info("\nPrinting LoyalityPointsService debitLoyaltyPoints API response :\n\n"
				+ debitLoyalityPointsResponse);

		MyntAssert.assertEquals(
				"LoyalityPointsService debitLoyalityPoints API is not working",
				200, debitLoyalityPointsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-debitloyalitypoints-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							debitLoyalityPointsResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService debitLoyaltyPoints API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation", "Fox7Sanity" }, dataProvider = "LoyalityDP_getTransactionHistoryOfInactivePoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_getTransactionHistoryOfInactivePoints_verifyResponseDataNodesUsingSchemaValidations(
			String login) {
		RequestGenerator getTransactionHistoryOfInactivePointsReqGen = loyalityPointsServiceHelper
				.invokeGetTransactionHistoryOfInactivePoints(login);
		String getTransactionHistoryOfInactivePointsResponse = getTransactionHistoryOfInactivePointsReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API response :\n\n"
						+ getTransactionHistoryOfInactivePointsResponse);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfInactivePoints API response :\n\n"
				+ getTransactionHistoryOfInactivePointsResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService getTransactionHistoryOfInactivePoints API is not working",
						200,
						getTransactionHistoryOfInactivePointsReqGen.response
								.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-gettransactionhistoryofinactivepoints-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							getTransactionHistoryOfInactivePointsResponse,
							jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService getTransactionHistoryOfInactivePoints API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_getTransactionHistoryOfActivePoints_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPointsService_getTransactionHistoryOfActivePoints_verifyResponseDataNodesUsingSchemaValidations(
			String login) {
		RequestGenerator getTransactionHistoryOfActivePointsReqGen = loyalityPointsServiceHelper
				.invokeGetTransactionHistoryOfActivePoints(login);
		String getTransactionHistoryOfActivePointsResponse = getTransactionHistoryOfActivePointsReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API response :\n\n"
						+ getTransactionHistoryOfActivePointsResponse);
		log.info("\nPrinting LoyalityPointsService getTransactionHistoryOfActivePoints API response :\n\n"
				+ getTransactionHistoryOfActivePointsResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService getTransactionHistoryOfActivePoints API is not working",
						200, getTransactionHistoryOfActivePointsReqGen.response
								.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-gettransactionhistoryofactivepoints-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							getTransactionHistoryOfActivePointsResponse,
							jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService getTransactionHistoryOfActivePoints API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation", "Fox7Sanity" }, dataProvider = "LoyalityDP_getAccountInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_getAccountInfo_verifyResponseDataNodesUsingSchemaValidations(
			String login) {
		RequestGenerator getAccountInfoReqGen = loyalityPointsServiceHelper
				.invokeGetAccountInfo(login);
		String getAccountInfoResponse = getAccountInfoReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService getAccountInfo API response :\n\n"
						+ getAccountInfoResponse);
		log.info("\nPrinting LoyalityPointsService getAccountInfo API response :\n\n"
				+ getAccountInfoResponse);

		MyntAssert.assertEquals(
				"LoyalityPointsService getAccountInfo API is not working", 200,
				getAccountInfoReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-getaccountinfo-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							getAccountInfoResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService getAccountInfo API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_creditLoyalityPointsOrderConfirmation_verifyResponseDataNodesUsingSchemaValidations", enabled = false)
	public void LoyaltyPoints_creditLoyalityOrderConfirmation_verifyResponseDataNodesUsingSchemaValidations(
			String login, String points, String orderId, String description) {
		RequestGenerator creditLoyalityOrderConfirmationReqGen = loyalityPointsServiceHelper
				.invokeCreditLoyalityOrderConfirmation(login, points, orderId,
						description);
		String creditLoyalityOrderConfirmationResponse = creditLoyalityOrderConfirmationReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API response :\n\n"
						+ creditLoyalityOrderConfirmationResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityOrderConfirmation API response :\n\n"
				+ creditLoyalityOrderConfirmationResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService creditLoyalityOrderConfirmation API is not working",
						200, creditLoyalityOrderConfirmationReqGen.response
								.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalityorderconfirmation-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							creditLoyalityOrderConfirmationResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService creditLoyalityOrderConfirmation API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_creditLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations", enabled = false)
	public void LoyaltyPoints_creditLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations(
			String login, String points, String orderId, String description) {
		RequestGenerator creditLoyalityItemCancellationReqGen = loyalityPointsServiceHelper
				.invokeCreditLoyalityItemCancellation(login, points, orderId,
						description);
		String creditLoyalityItemCancellationResponse = creditLoyalityItemCancellationReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API response :\n\n"
						+ creditLoyalityItemCancellationResponse);
		log.info("\nPrinting LoyalityPointsService creditLoyalityItemCancellation API response :\n\n"
				+ creditLoyalityItemCancellationResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService creditLoyalityItemCancellation API is not working",
						200, creditLoyalityItemCancellationReqGen.response
								.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-creditloyalityitemcancellation-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							creditLoyalityItemCancellationResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService creditLoyalityItemCancellation API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_debitLoyalityOrderUsage_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_debitLoyalityOrderusage_verifyResponseDataNodesUsingSchemaValidations(
			String login, String points, String orderId, String description) {
		RequestGenerator debitLoyalityOrderusageReqGen = loyalityPointsServiceHelper
				.invokeDebitLoyalityOrderusage(login, points, orderId,
						description);
		String debitLoyalityOrderusageResponse = debitLoyalityOrderusageReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService debitLoyalityOrderusage API response :\n\n"
						+ debitLoyalityOrderusageResponse);
		log.info("\nPrinting LoyalityPointsService debitLoyalityOrderusage API response :\n\n"
				+ debitLoyalityOrderusageResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService debitLoyalityOrderusage API is not working",
						200, debitLoyalityOrderusageReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-debitloyalityorderusage-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							debitLoyalityOrderusageResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService debitLoyalityOrderusage API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_myMyntraAccountInfo_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_myMyntraAccountInfo_verifyResponseDataNodesUsingSchemaValidations(
			String login) {
		RequestGenerator myMyntraAccountInfoReqGen = loyalityPointsServiceHelper
				.invokeMyMyntraAccountInfo(login);
		String myMyntraAccountInfoResponse = myMyntraAccountInfoReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService myMyntraAccountInfo API response :\n\n"
						+ myMyntraAccountInfoResponse);
		log.info("\nPrinting LoyalityPointsService myMyntraAccountInfo API response :\n\n"
				+ myMyntraAccountInfoResponse);

		MyntAssert.assertEquals(
				"LoyalityPointsService myMyntraAccountInfo API is not working",
				200, myMyntraAccountInfoReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-mymyntraaccountinfo-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							myMyntraAccountInfoResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService myMyntraAccountInfo API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "LoyalityDP_acceptTermsAndConditions_verifyResponseDataNodesUsingSchemaValidations")
	public void LoyaltyPoints_acceptTermsAndConditions_verifyResponseDataNodesUsingSchemaValidations(
			String login) {
		RequestGenerator acceptTermsAndConditionsReqGen = loyalityPointsServiceHelper
				.invokeAcceptTermsAndConditions(login);
		String acceptTermsAndConditionsResponse = acceptTermsAndConditionsReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService acceptTermsAndConditions API response :\n\n"
						+ acceptTermsAndConditionsResponse);
		log.info("\nPrinting LoyalityPointsService acceptTermsAndConditions API response :\n\n"
				+ acceptTermsAndConditionsResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService acceptTermsAndConditions API is not working",
						200,
						acceptTermsAndConditionsReqGen.response.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-accepttermsandconditions-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							acceptTermsAndConditionsResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService acceptTermsAndConditions API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation" })
	public void LoyaltyPoints_fetchAllConfigurationKeyValue_verifyResponseDataNodesUsingSchemaValidations() {
		RequestGenerator fetchAllConfigurationKeyValueReqGen = loyalityPointsServiceHelper
				.invokeFetchAllConfigurationKeyValue();
		String fetchAllConfigurationKeyValueResponse = fetchAllConfigurationKeyValueReqGen.respvalidate
				.returnresponseasstring();
		System.out
				.println("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API response :\n\n"
						+ fetchAllConfigurationKeyValueResponse);
		log.info("\nPrinting LoyalityPointsService fetchAllConfigurationKeyValue API response :\n\n"
				+ fetchAllConfigurationKeyValueResponse);

		MyntAssert
				.assertEquals(
						"LoyalityPointsService fetchAllConfigurationKeyValue API is not working",
						200, fetchAllConfigurationKeyValueReqGen.response
								.getStatus());
		MyntAssert.setExpectedAndActualForMyntListen(true);

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/loyalitypointsservice-fetchallconfigurationkeyvalue-schema.txt");
			List<String> missingNodeList = commonUtil
					.validateServiceResponseNodesUsingSchemaValidator(
							fetchAllConfigurationKeyValueResponse, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in LoyalityPointsService fetchAllConfigurationKeyValue API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditPositiveCasesDataProvider")
	public void LoyaltyPoints_creditBulkV2Success(String transactionId,String batchId, String login1,
			String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) {
		String query = "select bulk_transaction_type from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
//		String statusMessage = req.respvalidate
//				.NodeValue("statusMessage", true).replaceAll("\"", "").trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("Added to credit queue successfully"));
		String currentval = getquery(query,"lp");
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				"CREDIT", currentval);

	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditPositiveCasesWithExpiredPointsDataProvider")
	public void LoyaltyPoints_creditBulkV2SuccessWithExpiredPoints(String transactionId,String batchId, String login1,
												  String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) throws InterruptedException {
		String query = "select bulk_transaction_type from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		String query1 = "select failure from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
//		String statusMessage = req.respvalidate
//				.NodeValue("statusMessage", true).replaceAll("\"", "").trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("Added to credit queue successfully"));
		String currentval = getquery(query,"lp");
		int failureval=getloyaltypointsquery(query1,"lp");
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				"CREDIT", currentval);
		AssertJUnit.assertEquals("bulk credit is credited expired points into DB",
				2, failureval);

	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditPositiveCasesDataProvider")
	public void LoyaltyPoints_creditBulkV2Transaction_Size(String transactionId,String batchId, String login1,String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) throws InterruptedException {
		String query = "select size from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
//		String statusMessage = req.respvalidate
//				.NodeValue("statusMessage", true).replaceAll("\"", "").trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("Added to credit queue successfully"));
		int currentval = getloyaltypointsquery(query,"lp");
		System.out.print(currentval);
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				5, currentval);

	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditPositiveCasesDataProvider")
	public void verifyLoyaltyPoints_creditBulkV2EachUserInCreditTable(String transactionId,String batchId, String login1,String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) throws InterruptedException {
		String currentDate=getcurrentdate();
		String querygetloyalty ="select sum(`balance`) from loyalty_points_credit_entry where login="+'"'+login1+'"'+" and `expiry_date`>'"+currentDate+"'"+" and `start_date`<'"+currentDate+"'";
		System.out.print(querygetloyalty);
		String query = "select bulk_transaction_type from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
//		String statusMessage = req.respvalidate
//				.NodeValue("statusMessage", true).replaceAll("\"", "").trim();
//		log.info(statusMessage);
//		System.out.println(statusMessage);
//		AssertJUnit.assertTrue("Status Message doesn't match",
//				statusMessage.contains("Added to credit queue successfully"));
		String currentval = getquery(query,"lp");
		System.out.print(currentval);
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				"CREDIT", currentval);

	}


	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditNegativeCasesDataProvider")
	public void LoyaltyPoints_creditBulkV2Failure(String transactionId,String batchId, String login1,String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) {

			String query = "select size from loyalty_points_bulk_transactions where batch_id='"
					+ batchId + "' and transaction_id="+transactionId +" and success=3 order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
			MyntAssert.assertEquals("Credit Loyality Points is not working", 400,
					req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("message", true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		String statuscode = req.respvalidate
				.NodeValue("code", true).replaceAll("\"", "").trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit
				.assertTrue(
						"Status Message doesn't match",
						statusMessage
								.contains("Transaction already existing with same batch id and transaction id. Batch Id and Transaction Id must be unique per each transaction"));
		AssertJUnit
				.assertTrue(
						"Status Code doesn't match",
						statuscode
								.contains("40025"));
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "creditLoyalityPointsBulkCreditAllNegativePointsCasesDataProvider")
	public void LoyaltyPoints_creditBulkV2FailureAllExpiredorNegative(String transactionId,String batchId, String login1,String points,String expirydate,String points2,String expirydate2,String points3,String expirydate3) {

		String query1 = "select size from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		String query2="select success from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		String query3="select failure from loyalty_points_bulk_transactions where batch_id='"
				+ batchId + "' and transaction_id="+transactionId +" order by updated_on desc limit 1";
		RequestGenerator req = bulkCreditLoyaltyPointsV2(transactionId,batchId, login1,points,expirydate,points2,expirydate2,points3,expirydate3,tenantId);
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());

		int currentval1 = getloyaltypointsquery(query1,"lp");
		int currentval2 = getloyaltypointsquery(query2,"lp");
		int currentval3 = getloyaltypointsquery(query3,"lp");
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				5, currentval1);
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				0, currentval2);
		AssertJUnit.assertEquals("bulk credit is not updated into DB",
				5, currentval3);

	}


	//Loyalty Service V2 Starts from here........................
	//########################
	//########################
	//
	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsV2DataProvider")
	public static void LoyaltyPoints_creditLoyalityPointsV2_verifySuccessResponse(
			String login,
			String points,String businessProcess,String expiryDate, 
			String description) {
		RequestGenerator req = creditLoyaltyPointsV2(login, points,businessProcess,
				expiryDate, description,tenantId);
		// System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		String activeCreditPoint = req.respvalidate
				.NodeValue("points", true)
				.replaceAll("\"", "").trim();
		String creditPointtransactionId = req.respvalidate
				.NodeValue("transactionId", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(points);
		String query = "select `points` from lp_transaction_log where id="+creditPointtransactionId+" and login='"+login+"'";
		int updatedpoint = getloyaltypointsquery(query,"lp");
		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsV2DataProvider")
	public void LoyaltyPoints_creditLoyalityPointsV2_verifyPointID(
			String login,
			String points,String businessProcess,String expiryDate,
			String description) {
		RequestGenerator req = creditLoyaltyPointsV2(login, points,businessProcess,
				expiryDate, description,tenantId);
		// System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		String activeCreditPoint = req.respvalidate
				.NodeValue("points", true)
				.replaceAll("\"", "").trim();
		String creditPointtransactionId = req.respvalidate
				.NodeValue("transactionId", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(points);
		String query = "select `points` from lp_transaction_log where id="+creditPointtransactionId+" and login='"+login+"'";
		int updatedpoint = getloyaltypointsquery(query,"lp");
		AssertJUnit.assertEquals("Loyalty points id is not created into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsV2DataProvider")
	public void LoyaltyPoints_debitLoyalityPointsV2_verifySuccessResponse(
			String login,
			String points,String ppsID,String ppsTransactionID,String orderID,String businessProcess,String retry) {
		String expiryDate="1519208750000";
		String description="automation";
		String creditpoints="200";
		login="automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5"+ppsID;
		creditLoyaltyPointsV2(login,creditpoints,"GOOD_WILL",expiryDate, description,tenantId);
		creditLoyaltyPointsV2(login,creditpoints,"GOOD_WILL",expiryDate, description,tenantId);
		RequestGenerator req = debitLoyaltyPointsV2(login,points,ppsID,ppsTransactionID, orderID,businessProcess,tenantId,retry);
		// System.out.println("test change");
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		String totalActivePoints = req.respvalidate
				.NodeValue("balance.totalActivePoints", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(creditpoints);
		expectedpoint=expectedpoint+expectedpoint;
		expectedpoint=expectedpoint-Integer.parseInt(points);
		AssertJUnit.assertEquals("Loyalty points is not updated into Response",
				expectedpoint, Integer.parseInt(totalActivePoints));
//		String query = "select `balance` from loyalty_points_credit_entry where id="+creditPointId+" and login='"+login+"'";
//		int updatedpoint = getloyaltypointsquery(query,"lp");
//		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
//				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsV2DataProvider")
	public void LoyaltyPointsJabong_creditLoyalityPointsV2_verifySuccessResponse(
			String login,
			String points,String businessProcess,String expiryDate,
			String description) {
		RequestGenerator req = creditLoyaltyPointsV2(login, points,businessProcess,
				expiryDate, description,tenantIdJabong);
		// System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		String activeCreditPoint = req.respvalidate
				.NodeValue("points", true)
				.replaceAll("\"", "").trim();
		String creditPointtransactionId = req.respvalidate
				.NodeValue("transactionId", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(points);
		String query = "select `points` from lp_transaction_log where id="+creditPointtransactionId+" and login='"+login+"'";
		int updatedpoint = getloyaltypointsquery(query,"jabong_lp");
		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "creditLoyaltyPointsV2DataProvider")
	public void LoyaltyPointsJabong_creditLoyalityPointsV2_verifyPointID(
			String login,
			String points,String businessProcess,String expiryDate,
			String description) {
		RequestGenerator req = creditLoyaltyPointsV2(login, points,businessProcess,
				expiryDate, description,tenantIdJabong);
		// System.out.println("test change");
		MyntAssert.assertEquals("Credit Loyality Points is not working", 200,
				req.response.getStatus());
		String activeCreditPoint = req.respvalidate
				.NodeValue("points", true)
				.replaceAll("\"", "").trim();
		String creditPointtransactionId = req.respvalidate
				.NodeValue("transactionId", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(points);
		String query = "select `points` from lp_transaction_log where id="+creditPointtransactionId+" and login='"+login+"'";
		int updatedpoint = getloyaltypointsquery(query,"jabong_lp");
		AssertJUnit.assertEquals("Loyalty points id is not created into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "debitLoyaltyPointsV2DataProvider")
	public void LoyaltyPointsJabong_debitLoyalityPointsV2_verifySuccessResponse(
			String login,
			String points,String ppsID,String ppsTransactionID,String orderID,String businessProcess,String retry) {
		String expiryDate="1519208750000";
		String description="automation";
		String creditpoints="200";
		login="automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5"+ppsID;
		creditLoyaltyPointsV2(login,creditpoints,"GOOD_WILL",expiryDate, description,tenantIdJabong);
		creditLoyaltyPointsV2(login,creditpoints,"GOOD_WILL",expiryDate, description,tenantIdJabong);
		RequestGenerator req = debitLoyaltyPointsV2(login,points,ppsID,ppsTransactionID, orderID,businessProcess,tenantIdJabong,retry);
		// System.out.println("test change");
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		String totalActivePoints = req.respvalidate
				.NodeValue("balance.totalActivePoints", true)
				.replaceAll("\"", "").trim();
		int expectedpoint =Integer.parseInt(creditpoints);
		expectedpoint=expectedpoint+expectedpoint;
		expectedpoint=expectedpoint-Integer.parseInt(points);
		AssertJUnit.assertEquals("Loyalty points is not updated into Response",
				expectedpoint, Integer.parseInt(totalActivePoints));
//		String query = "select `balance` from loyalty_points_credit_entry where id="+creditPointId+" and login='"+login+"'";
//		int updatedpoint = getloyaltypointsquery(query,"lp");
//		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
//				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}

	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "getAccountBalanceV2DataProvider")
	public void VerifyAccountBalanceLoyalityPointsV2_verifySuccessResponse(
			String login) {
        String currentDate=getcurrentdate();
		String query ="select sum(`balance`) from loyalty_points_credit_entry where login="+'"'+login+'"'+" and `expiry_date`>'"+currentDate+"'"+" and `start_date`<'"+currentDate+"'";
		int expectedpoint = getloyaltypointsquery(query,"lp");
		RequestGenerator req = getActiveLoyaltyPointBalanceV2(login,tenantId);
		// System.out.println("test change");
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		String totalActivePoints = req.respvalidate
				.NodeValue("totalActivePoints", true)
				.replaceAll("\"", "").trim();
		int updatedpoint =Integer.parseInt(totalActivePoints);
		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
	@Test(groups = { "Sanity","LPV2","LPAll", "MiniRegression", "Regression",
			"ExhaustiveRegression", "Fox7Sanity" }, dataProvider = "getAccountBalanceV2DataProvider")
	public void VerifyJabongAccountBalanceLoyalityPointsV2_verifySuccessResponse(
			String login) {
		String currentDate=getcurrentdate();
		String query ="select sum(`balance`) from loyalty_points_credit_entry where login="+'"'+login+'"'+" and `expiry_date`>'"+currentDate+"'"+" and `start_date`<'"+currentDate+"'";
		int expectedpoint = getloyaltypointsquery(query,"jabong_lp");
		RequestGenerator req = getActiveLoyaltyPointBalanceV2(login,tenantIdJabong);
		// System.out.println("test change");
		MyntAssert.assertEquals("Debit Loyality Points is not working", 200,
				req.response.getStatus());
		String totalActivePoints = req.respvalidate
				.NodeValue("totalActivePoints", true)
				.replaceAll("\"", "").trim();
		int updatedpoint =Integer.parseInt(totalActivePoints);
		AssertJUnit.assertEquals("Loyalty points is not updated into DB",
				expectedpoint, updatedpoint);

		MyntAssert.setExpectedAndActualForMyntListen(true);
	}
}
