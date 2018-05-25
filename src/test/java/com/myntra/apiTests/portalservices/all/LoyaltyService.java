package com.myntra.apiTests.portalservices.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.LoyaltyServiceDP;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;


/**
 * @author Manishkumar.gupta
 *
 */

public class LoyaltyService extends LoyaltyServiceDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LoyaltyService.class);
	APIUtilities apiUtil = new APIUtilities();
	CommonUtils commonUtil= new CommonUtils();

	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"}, dataProvider = "gerUser", description="\n1. Checking the info. of User")
	public void LoyaltyService_userInfo(String emailid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.USERINFO,
				init.Configurations);
		
		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		log.info(service.URL);
		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
		System.out.println("Response---->>>>" +response );
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				rs.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "debitloyaltyDP", description="\n1. debit Loyalty points \n2.Verify 200 response")
	public void LoyaltyService_Debit(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		
		creditLoyalityPoints(
				customerID, "100","0", "0",
				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited= getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > " + current_Points_AfterDebited);
		int deducted_Points = (current_Points_Int-current_Points_AfterDebited_Int);
		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(100, deducted_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200, req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "debitloyaltyMorethanActivePointDP", description="\n1.Debit Loyalty Points more than active points \n2.Verify Failure response")
	public void LoyaltyService_DebitMoreThanActivePoint(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited= getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > " + current_Points_AfterDebited);
//		int deducted_Points = (current_Points_Int-current_Points_AfterDebited_Int);
//		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(current_Points_Int, current_Points_AfterDebited_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("JSON----->>"+jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();


//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
//				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("message mismatched", "Loyalty Points Debit Failure", comment);

		
		
	}
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "debitloyaltyNegativePointDP", description="\n1.Debit negative points \n2.Verify 200 status \n3. Verify Failure message")
	public void LoyaltyService_DebitNegativeLoyaltyPoint(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited= getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer.parseInt(current_Points_AfterDebited);
		

		System.out.println("current balance after Debited---- > " + current_Points_AfterDebited);
//		int deducted_Points = (current_Points_Int-current_Points_AfterDebited_Int);
//		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(current_Points_Int, current_Points_AfterDebited_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();


//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
//				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("message mismatched", "INCOMPLETE TXN ENTRY", comment);

		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "debitloyaltyLessThanPointDP", description="\n1.Debit less than 50 Loyalty Points \n2. Verify Failure response")
	public void LoyaltyService_DebitLessThan50LoyaltyPoint(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID,String ppsType) 
	{
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY, init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("response ------ >>>" + response);
		String current_Points_AfterDebited= getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debit---- > " + current_Points_AfterDebited);

		AssertJUnit.assertEquals(current_Points_Int, current_Points_AfterDebited_Int);
//		String jsonRes = req.respvalidate.returnresponseasstring();
//		System.out.println("JSON RESPONSE---->>"+ jsonRes);
		String msg1 = JsonPath.read(response, "$.params.txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();


//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
//				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("message mismatched", "CANNOT DEBIT ZERO ACTIVE POINTS", comment);

		
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP", description="\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status")
	public void LoyaltyService_Refund(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
		creditLoyalityPoints(
				customerID, "100","0", "0",
				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression", "Fox7Sanity"},dataProvider = "refundloyaltyForVoidedTransactionDP", description="\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status \n4. Void the same client Tx.id")
	public void LoyaltyService_RefundwithVoidedTransaction(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) 
	{
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
		System.out.println("current balance---- > " + current_Points);
		

//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
//		AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int+100);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
//			AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);


		}

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP_MoreThan_Debit", description="\n1. Debit Loyalty Points \n2. Try to Refund the loyalty points more than debited loyalty points \n3. Verify 200 status \n4.Verfiy the failure message")
	public void LoyaltyService_Refund_withRefundMoreThanDebit(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
//		AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int+100);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg", "REFUND AMOUNT IS MORE THAN DEBIT AMOUNT", comment);

		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP_withSameTXN", description="\n1. Debit Loyalty Points \n2. Refund the loyalty points \n3. Verify 200 status \n4. Again Refund the same loyalty points with same client Tx. id \n5.Verfiy the failure message")
	public void LoyaltyService_RefundTwice_WithSameCLientTXN(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int count =0;
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);
		count++;

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int);
			AssertJUnit.assertEquals("Getting refunded again", "TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", comment);


		}
		else
		{
			System.out.println("Next dataprovider will be run to verify the this testcase");
			AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int+100);

		}
		
		
		
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP_LessThan_Debit", description="\n1. Debit Loyalty Points \n2. Refund partial loyalty points \n3. Verify 200 status \n4.Verify Success message")
	public void LoyaltyService_Refund_withRefundLessThanDebit(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
//		AssertJUnit.assertEquals(current_Points_AfterRefunded_Int, current_Points_Int+100);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);

		
	}
	

	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP_ToInvalidEMail", description="\n1. Debit Loyalty Points of invalid user \n2. Refund the loyalty points \n3. Verify 200 status \n4. Verfiy failure message")
	public void LoyaltyService_Refund_withInvalidUser(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg", "CUSTOMER ID NOT PRESENT OR DIFFERENT", comment);

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundloyaltyDP_ToInvalidTXN", description="\n1. Debit Loyalty Points \n2. Refund the loyalty points for Invalid Client tx. \n3. Verify 200 status")
	public void LoyaltyService_Refund_withInvalidClientId(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Getting Succes msg", "NO SUCH CLIENT TXN ID", comment);

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "getStatus", description="\n1.Get the status of user \n2.Verify 200 response \n3. Verify success response")
	public void LoyaltyService_getStatus(String clientTransactionID,String checksum,String ppsId,String orderId ) {
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.GETSTATUS,
				init.Configurations,new String[]{clientTransactionID,checksum,ppsId,orderId}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);

		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "voidTransactionDP", description="\n1. Debit Loyalty Points \n2. Void the tx. \n3. Verify 200 status")
	public void LoyaltyService_VoidTransaction(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points_AfterDebit= getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
		System.out.println("current balance---- > " + current_Points_AfterDebit_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided= getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Refund---- > " + current_Points_AfterVoided_Int);
//		int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "voidTransactionAlreadyVoideDP", description="\n1. Debit Loyalty Points \n2.Take debit client tx. id and void the tx. \n3. Verify 200 status \n4.Again Void the same tx. with same Client tx. id \n5. Verify the failure response")
	public void LoyaltyService_VoidTransactionOnAlreadyVoidedTxn(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points_AfterDebit= getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > " + current_Points_AfterDebit_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided= getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > " + current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);


		}
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "voidTransactionOnRefundedDP", description="\n1. Debit Loyalty Points \n2. Take debit client tx. and refund the loyalty points \n3. Verify 200 status \n4.Void the same debit tx. using the same client tx.")
	public void LoyaltyService_VoidTransactionOnDebitTransactionID(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points_AfterDebit= getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > " + current_Points_AfterDebit_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided= getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > " + current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
//			AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int);
			AssertJUnit.assertEquals("CANNOT VOID SINCE REFUND WAS DONE ON CLIENT TXN", comment);


		}
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "voidTransactionOnRefundedTxnDP", description="\n1. Debit Loyalty Points \n2. Take debit client tx. and refund the loyalty points \n3. Verify 200 status \n4.Void the same debit tx. using the same client tx." )
	public void LoyaltyService_VoidTransactionOnRefundedTransactionID(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points_AfterDebit= getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
		System.out.println("current balance after Debit---- > " + current_Points_AfterDebit_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided= getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Voided---- > " + current_Points_AfterVoided_Int);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		

			AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);
//			AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int);


		
		
	}
	
	
private String getClientTransactionID(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		
		creditLoyalityPoints(
				customerID, "100","0", "0",
				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterDebited= getCurrentbalance(customerID);
		int current_Points_AfterDebited_Int = Integer.parseInt(current_Points_AfterDebited);

		System.out.println("current balance after Debited---- > " + current_Points_AfterDebited);
		int deducted_Points = (current_Points_Int-current_Points_AfterDebited_Int);
		System.out.println("dedcuted point" + deducted_Points);
		AssertJUnit.assertEquals(100, deducted_Points);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		return clientId;
	}


@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", 
"ExhaustiveRegression"},dataProvider = "voidInvalidTransactionDP", description="\n1. Debit Loyalty Points \n2. Take Invalid client tx. and void the loyalty points \n3. Verify 200 status \n4.Verify failure message" )
public void LoyaltyService_VoidTransactionWithInvalidClientTransaction(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
	
	
//	creditLoyalityPoints(
//			customerID, "100","0", "0",
//			"0","automation", "ORDER", "123", "GOOD_WILL");
	String current_Points_AfterDebit= getCurrentbalance(customerID);
	int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
	System.out.println("current balance---- > " + current_Points_AfterDebit_Int);
	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
			init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
	System.out.println("Url------>>>>> "+service.URL);
	System.out.println("payload------>>>>> "+service.Payload);

	RequestGenerator req = new RequestGenerator(service);		
	String response = req.returnresponseasstring();
	System.out.println("responsr ------ >>>" + response);
	String current_Points_AfterVoided= getCurrentbalance(customerID);
	int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

	System.out.println("current balance after Refund---- > " + current_Points_AfterVoided_Int);
//	int deducted_Points = (current_Points_Int-current_Points_AfterRefunded_Int);
//	System.out.println("dedcuted point" + deducted_Points);
//	AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int+100);
	String jsonRes = req.respvalidate.returnresponseasstring();
	String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("Msg------>>>>> "+msg1);
	String comment = JsonPath.read(jsonRes, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();


//	String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//	System.out.println("Response---->>>>" +response );
////	String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////	int count = getCount(dbConnection_Fox7);
	AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
			req.response.getStatus());
	AssertJUnit.assertEquals("Getting Faliure msg", "TX_FAILURE", msg1);
	AssertJUnit.assertEquals("NO SUCH CLIENT TXN ID", comment);

	
	
}

	private String getCurrentbalance(String emailid)
	{ 
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.USERINFO,
				init.Configurations);
		
		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		log.info(service.URL);
		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
		return msg1;
	}
	
	
	
	

	
	
	private int getCount(String conString){
		Statement stmt = null;
		Connection con = null;
		int count = 0;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString);
			System.out.println(":::::::::::::::"+con);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select count(*) from pps_transactions";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count(*)");
				//System.out.println("count(*)"+ count);
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
	
	private String generateRandomString()
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
	
	public void creditLoyalityPoints(
			String login, String activeCreditPoints,
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
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression",
	"ExhaustiveRegression"},dataProvider = "checksum")
	public void LoyaltyService_getDebitAndRefundChecksum(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUNDDEBIT,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("responsr checksome------ >>>" + jsonRes);
	}
	
	@Test(groups = { "SchemaValidation","Regression", "Fox7Sanity", }, dataProvider = "debitloyalty", priority = 32, description = "1.debit Loyalty Points \n 2. verify status code 200 response \n 3. validate nodes and schema")
	public void debitLoyalty_ServiceUsingSchemaValidations(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.DEBITLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);

	String value = JsonPath.read(response, "$.params.txStatus").toString().replace("[", "").replace("]", "").trim();

	System.out.println("Printing response of Debit api: " + value);
	AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());

	try {
	String jsonschema = new Toolbox()
			.readFileAsString("Data/SchemaSet/JSON/loyalty-service-debitresponse.txt");
	System.out.println("schema----- >>>>>>>>> " + jsonschema);
	List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	AssertJUnit.assertTrue(missingNodeList + " nodes are missing in LoyaltyService API response",
			CollectionUtils.isEmpty(missingNodeList));
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	@Test(groups = { "SchemaValidation","Regression", "Fox7Sanity", }, dataProvider = "refundloyalty", priority = 32, description = "1.debit Loyalty Points \n 2.Take the client tx. id and refund the amount 2. verify status code 200 response \n 4. validate nodes and schema")
	public void refundLoyalty_ServiceUsingSchemaValidations(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String ppsType,String customerID) {
		creditLoyalityPoints(
				customerID, "100","0", "0",
				"0","automation", "ORDER", "123", "GOOD_WILL");
		String current_Points= getCurrentbalance(customerID);
		int current_Points_Int = Integer.parseInt(current_Points);
		System.out.println("current balance---- > " + current_Points_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.REFUNDLOYALTY,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,ppsType,customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterRefunded= getCurrentbalance(customerID);
		int current_Points_AfterRefunded_Int = Integer.parseInt(current_Points_AfterRefunded);

		System.out.println("current balance after Refund---- > " + current_Points_AfterRefunded);

	String value = JsonPath.read(response, "$.params.txStatus").toString().replace("[", "").replace("]", "").trim();

	System.out.println("Printing refund api status : " + value);
	AssertJUnit.assertEquals("Status code does not match", 200, req.response.getStatus());

	try {
	String jsonschema = new Toolbox()
			.readFileAsString("Data/SchemaSet/JSON/loyalty-service-refundresponse.txt");
	System.out.println("schema----- >>>>>>>>> " + jsonschema);
	List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	AssertJUnit.assertTrue(missingNodeList + " nodes are missing in LoyaltyService API response",
			CollectionUtils.isEmpty(missingNodeList));
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	
	
	@Test(groups = { "SchemaValidation","Regression", "Fox7Sanity" }, dataProvider = "voidTx", priority = 32, description = "1.debit Loyalty Points \n 2.Take the client tx. id and refund the amount 2. verify status code 200 response \n 4. validate nodes and schema")
	public void voidLoyalty_ServiceUsingSchemaValidations(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
	
		String current_Points_AfterDebit= getCurrentbalance(customerID);
		int current_Points_AfterDebit_Int = Integer.parseInt(current_Points_AfterDebit);
		System.out.println("current balance---- > " + current_Points_AfterDebit_Int);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOYALTYSERVICE, APINAME.VOIDTRANSACTION,
						init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String current_Points_AfterVoided= getCurrentbalance(customerID);
		int current_Points_AfterVoided_Int = Integer.parseInt(current_Points_AfterVoided);

		System.out.println("current balance after Refund---- > " + current_Points_AfterVoided_Int);

		AssertJUnit.assertEquals(current_Points_AfterVoided_Int, current_Points_AfterDebit_Int);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);


	    AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
						req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);


	try {
	String jsonschema = new Toolbox()
			.readFileAsString("Data/SchemaSet/JSON/loyalty-service-voidresponse.txt");
	System.out.println("schema----- >>>>>>>>> " + jsonschema);
	List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	AssertJUnit.assertTrue(missingNodeList + " nodes are missing in LoyaltyService API response",
			CollectionUtils.isEmpty(missingNodeList));
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "getStatusSchema", description="\n1. get response from user info api \n2. Verify schema according to response \n3. Verify 200 status ")
	public void LoyaltyService_getStatusSchema(String clientTransactionID,String checksum,String ppsId,String orderId ) {
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.GETSTATUS,
				init.Configurations,new String[]{clientTransactionID,checksum,ppsId,orderId}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);
		AssertJUnit.assertEquals("Debit loyalty not working is not working", 200,
				req.response.getStatus());
		AssertJUnit.assertEquals("Getting Faliure msg", "TX_SUCCESS", msg1);
		
		try {
		String jsonschema = new Toolbox()
				.readFileAsString("Data/SchemaSet/JSON/loyalty-service-getstatusresponse.txt");
		System.out.println("schema----- >>>>>>>>> " + jsonschema);
		List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		AssertJUnit.assertTrue(missingNodeList + " nodes are missing in LoyaltyService API response",
				CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	
	
}
