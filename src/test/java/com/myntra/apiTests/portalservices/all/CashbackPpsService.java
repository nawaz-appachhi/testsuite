package com.myntra.apiTests.portalservices.all;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.CashbackServiceDP;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.Random;

public class CashbackPpsService extends CashbackServiceDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CashbackPpsService.class);
	APIUtilities apiUtil = new APIUtilities();

	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression",
//	"ExhaustiveRegression"}, dataProvider = "debitcashbackFromEarnedAmount", priority=0, description="\n 1. Get stored cashback request \n 2. Get earned cashback request \n 3. Debit cashback \n 4. Verify earned balanced before and after")
//	public void CashbackService_debitFromEarnedBalance(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
//		int Debit_amount =(Integer.parseInt(amount))/100;
//		System.out.println("Debuted amount" + Debit_amount);
//		String stored = getStoredCashBackRequest(customerID);
////		int stored_int =Integer.parseInt(stored);
//		System.out.println("stored----->>" + stored);
//		String earnedbalance = getEarnedCashBackRequest(customerID);
//		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
//		System.out.println("earnedbalance----->>" + earnedBalance_Int);
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
//				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();	
//		System.out.println("Debit Payload Request------- >>>>>" + service.Payload);
//		
//        log.info(service.URL);
//		String storedbalanceAfter= getStoredCashBackRequest(customerID);
//		System.out.println("StoreAFter----->>" + storedbalanceAfter);
//		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
//		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));
//
//		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
////		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Debit Response---->>>>" +response );
//		AssertJUnit.assertEquals("balanc", earnedBalance_Int-Debit_amount, earnedbalanceAfter_int);
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("debit is not working is not working", 200,
//				rs.response.getStatus());
//	}
//	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity","Regression",
//	"ExhaustiveRegression"}, dataProvider = "debitcashbackFromStoredAmount", priority=1, description="\n 1. Get stored cashback request \n 2. Get earned cashback request \n 3. Hit Debit cashback pps api \n 4. Get stored cashback and earned cashback after debit \n 5. Verify stored balance before and after.")
//	public void CashbackService_debitFromStoredBalance(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
//		
//		int Debit_amount =(Integer.parseInt(amount))/100;
//		System.out.println("Debuted amount" + Debit_amount);
//		String stored = getStoredCashBackRequest(customerID);
//		int stored_Int =Math.round(Float.parseFloat(stored)) ;
//
////		int stored_int =Integer.parseInt(stored);
//		System.out.println("stored----->>" + stored);
//		String earnedbalance = getEarnedCashBackRequest(customerID);
//		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
//		System.out.println("earnedbalance----->>" + earnedBalance_Int);
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
//				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();
//		System.out.println("Debit Payload Request------- >>>>>" + service.Payload);
//		log.info(service.URL);
//		String storedbalanceAfter= getStoredCashBackRequest(customerID);
//		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));
//
//		System.out.println("StoreAFter----->>" + storedbalanceAfter);
//		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
//		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));
//
//		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
////		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
//		AssertJUnit.assertEquals("", stored_Int-Debit_amount, storedbalanceAfter_int);
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("debit is not working is not working", 200,
//				rs.response.getStatus());
//	}
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","Fox7Sanity",
//	"ExhaustiveRegression"}, dataProvider = "debitcashbackFromStoredAmountAndEarned", priority=2,description="\n 1.Get stored cashback request \n 2. Get earned cashback request \n 3. Hit debit cashback pps \n 4. Get stored cashback and earned cashback after debit \n 5. Verify stored balance before and after. ")
//	public void CashbackService_debitFromStoredAndEarnedBalance(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
//		
//		int Debit_amount =(Integer.parseInt(amount))/100;
//		System.out.println("Debuted amount" + Debit_amount);
//		String stored = getStoredCashBackRequest(customerID);
//		int stored_Int =Math.round(Float.parseFloat(stored)) ;
//
////		int stored_int =Integer.parseInt(stored);
//		System.out.println("stored----->>" + stored);
//		String earnedbalance = getEarnedCashBackRequest(customerID);
//		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
//		System.out.println("earnedbalance----->>" + earnedBalance_Int);
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
//				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();
//		
//		log.info(service.URL);
//		String storedbalanceAfter= getStoredCashBackRequest(customerID);
//		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));
//
//		System.out.println("StoreAFter----->>" + storedbalanceAfter);
//		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
//		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));
//
//		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
////		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
//		int deductFromBothBalance = (stored_Int-storedbalanceAfter_int)+(earnedBalance_Int-earnedbalanceAfter_int);
//		AssertJUnit.assertEquals("", deductFromBothBalance, Debit_amount);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("debit is not working is not working", 200,
//				rs.response.getStatus());
//	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","Fox7Sanity",
	"ExhaustiveRegression"}, dataProvider = "debitcashbackMoreThanActiveCashBack", priority=2,description="\n 1.Get stored cashback request \n 2. Get earned cashback request \n 3. Hit debit cashback pps and debit more than active cashback \n 4. Verify debit failure"  )
	public void CashbackService_debitMoreThanActiveCashBack(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getEarnedCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
		AssertJUnit.assertEquals("NEGATIVE DEDUSTED", earnedBalance_Int, earnedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"}, dataProvider = "debitcashbackNegativeCashBack", priority=2,description="\n 1. Get stored cashback request \n 2. Get earned cashback request. \n 3. Debit negative cashback \n 4. Get stored and earned cashback request after negative debit cashback \n 5. Verify debit failure message")
	public void CashbackService_DebitNegativeCashback(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("DEBit -------------- >>>>>>>>"  + Debit_amount);
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
		System.out.println("both amount ==========================> " +deductFromBothBalance);
		AssertJUnit.assertTrue("negative cashback deducted", deductFromBothBalance>Debit_amount);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackDP", description="\n 1. Get stored cashback request \n 2. Get earned cashback request.\n 3. Refund cashback to earned points \n 4. Get stored and earned balance after refund. \n 5. Verify debit failure message.")
	public void CashbackService_RefundToEarnedPoint(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertFalse("negative cashback deducted", deductFromBothBalance>Debit_amount);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackToStoredAcountDP", description="\n 1. Get stored cashback request. \n 2. Get earned cashback request \n 3. Refund to store \n 4. Get stored and earned cashback after refund \n 5. Verify stored + debit amount matches with stored balance after refund ")
	public void CashbackService_RefundToStored(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackToStoredAndEarnedAcountDP",description="\n 1. Get stored cashback request. \n 2. Get earned cashback request \n 3. Refund to store and earned \n 4. Verify stored balance +(debit amount)/2 matches with stored balance after refund. \n 5. Verify earned balance +(debit amount)/2 matches with earned balance after refund")
	public void CashbackService_RefundToStoredAndEarned(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));
		String storedbalanceAfter_string = String.valueOf(storedbalanceAfter_int*100);

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
		AssertJUnit.assertEquals("cashback mismatched", stored_Int+(Debit_amount)/2, storedbalanceAfter_int);
		AssertJUnit.assertEquals("cashback mismatched", earnedBalance_Int+(Debit_amount)/2, earnedbalanceAfter_int);

//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,customerID,"ORDER");
		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,customerID,"ORDER");
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackLessthanToStoredAndEarnedAcountDP", description="\n 1. Get stored cashback request. \n 2. Get earned cashback request \n 3. Refund less than store and earned \n 4.")
	public void CashbackService_Refund_LessThanToStoredAndEarned(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;
		
		
//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
//		String refund_amount = amount;
//		System.out.println("Refund amount -----  " + refund_amount);
//		int refund_amount_int = Math.round(Integer.parseInt(refund_amount)/100);
//		int stored_amount = Math.round((refund_amount_int-(Debit_amount-refund_amount_int)));
//		
//		System.out.println("stored_amount[[[[[[[[[[[[[[[[[[" + stored_amount);
//		int Caluclation_On_RefundStored = (stored_amount/Debit_amount)*refund_amount_int;
//		
//		System.out.println("Refunded on stores ------------------------ ================" +Caluclation_On_RefundStored);
//		int Caluclation_On_RefundEarned = (refund_amount_int-Caluclation_On_RefundStored);
//		
//		System.out.println("Refunded on earned *********************************" +Caluclation_On_RefundEarned);

		
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int refunded_Actual = Integer.parseInt(amount)/100;
		int Refunded = (storedbalanceAfter_int-stored_Int)+(earnedbalanceAfter_int-earnedBalance_Int);
		
		System.out.println("======================"  +Refunded );
		AssertJUnit.assertEquals("Refunded mismatch", refunded_Actual, Refunded);
//		AssertJUnit.assertEquals("Stored refund mismatch", storedbalanceAfter_int, Caluclation_On_RefundStored);
//		AssertJUnit.assertEquals("earned refund mismatch", earnedbalanceAfter_int, Caluclation_On_RefundEarned);

		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+(Debit_amount)/2, storedbalanceAfter_int);
//		AssertJUnit.assertEquals("cashback mismatched", earnedBalance_Int+(Debit_amount)/2, earnedbalanceAfter_int);

//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
//		String random_number4 = generateRandomNumber();
//		String random_String4 = generateRandomString();
//		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter,customerID,"ORDER");
//		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter,customerID,"ORDER");
		
	}
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackMoreThanDebit", description="\n 1. Get stored cashback request. \n 2. Get earned cashback request \n 3. Refund more than debit \n 4. Verify debit failure status message. \n 5. Verify TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT in comments section ")
	public void CashbackService_RefundMoreThanDebit(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
	String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("Response---->>>>" +response );
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("Message display wrong", "TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", coments);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackwithSameClienTXn",description="\n 1. Get stored cashback request. \n 2. Get earned cashback request \n 3.Refund with same client transaction id. \n 4. Verify earned balance before and after refund \n 5. On refunding again verify ->TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT in comments section. ")
	public void CashbackService_RefundWithSameCLientTXN(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
//	String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("Response---->>>>" +response );
//		
////		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
////		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
////		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
//		AssertJUnit.assertEquals("Message display wrong", "TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", coments);
//		AssertJUnit.assertEquals("debit is not working is not working", 200,
//				rs.response.getStatus());
//		
		
		
		if(msg1.contains("TX_FAILURE"))
		{
			String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(earnedbalanceAfter_int, earnedBalance_Int);
			AssertJUnit.assertEquals("Getting refunded again", "TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", coments);


		}
		else
		{
			System.out.println("Next dataprovider will be run to verify the this testcase");

		}
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackWithSameCLientTXNAndDifferentPpsID",description="\n 1. Get earned cashback request. \n 2. Get earned cashback request. \n 3. Refund cashback more than twice with same client transaction id and with different pps id. \n 4. Verify earned balance before and after refund.")
	public void CashbackService_Refund_MorethanTwiceWithSameCLientTXNAndDifferentPpsID(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;
		
		
//		int stored_int =Integer.parseInt(stored)
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
//		String refund_amount = amount;
//		System.out.println("Refund amount -----  " + refund_amount);
//		int refund_amount_int = Math.round(Integer.parseInt(refund_amount)/100);
//		int stored_amount = Math.round((refund_amount_int-(Debit_amount-refund_amount_int)));
//		
//		System.out.println("stored_amount[[[[[[[[[[[[[[[[[[" + stored_amount);
//		int Caluclation_On_RefundStored = (stored_amount/Debit_amount)*refund_amount_int;
//		
//		System.out.println("Refunded on stores ------------------------ ================" +Caluclation_On_RefundStored);
//		int Caluclation_On_RefundEarned = (refund_amount_int-Caluclation_On_RefundStored);
//		
//		System.out.println("Refunded on earned *********************************" +Caluclation_On_RefundEarned);

		
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int refunded_Actual = Integer.parseInt(amount)/100;
		int Refunded = (storedbalanceAfter_int-stored_Int)+(earnedbalanceAfter_int-earnedBalance_Int);
		
		System.out.println("======================"  +Refunded );

		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		if(msg1.contains("TX_FAILURE"))
		{
			String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(earnedbalanceAfter_int, earnedBalance_Int);
			AssertJUnit.assertEquals("Getting refunded again", "TOTAL REFUND AMOUNT EXCEEDED DEBIT AMOUNT", coments);


		}
		else
		{
			System.out.println("Next dataprovider will be run to verify the this testcase");

		}
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackWithInvalidUser",description="\n 1. Get stored cashbck request. \n 2. Get earned cashback request. \n 3. Refund to stored with invalid user. \n 4. Verify txt-> CUSTOMER IDS DOES NOT MATCH in comments section.")
	public void CashbackService_RefundToStoredWithInvalidUser(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("commmnets ============> " + coments);
		AssertJUnit.assertEquals(" success invalid user ", coments, "CUSTOMER IDS DOES NOT MATCH");
		AssertJUnit.assertEquals("success ", msg1, "TX_FAILURE");

		
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackWithInvalidClienTXN", description="\n 1. Get stored cashback request \n 2. Get earned cashback request. \n 3. Refund to stored with invalid client transaction id. \n 4. Verify txt -> NO SUCH CLIENT TXN ID in comments section.")
	public void CashbackService_RefundToStoredWithInvalidClient(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		String coments = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("commmnets ============> " + coments);
		AssertJUnit.assertEquals(" success invalid user ", coments, "NO SUCH CLIENT TXN ID");
		AssertJUnit.assertEquals("success ", msg1, "TX_FAILURE");

		
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "voidTransactionDP")
	public void CashbackService_VoidTransactionClient(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		float earnedBalance_Int = Float.parseFloat(earnedbalance) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.VOIDCASHBACKTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		float earnedbalanceAfter_int = Float.parseFloat(earnedbalanceAfter);

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		float deducted_AMount = earnedbalanceAfter_int-earnedBalance_Int;
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		

			AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);
			AssertJUnit.assertEquals("cashback mismatched",earnedBalance_Int+deducted_AMount, earnedbalanceAfter_int, earnedbalanceAfter_int);


		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "voidTransactionMultipleWithDebitTxn")
	public void CashbackService_VoidTransactionMultipletimesWithDebitTxn(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.VOIDCASHBACKTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int deducted_AMount = earnedbalanceAfter_int-earnedBalance_Int;
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(earnedBalance_Int+deducted_AMount, earnedbalanceAfter_int);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);


		}
		
		else {
			System.out.println("This testcase validate  when next data provider will run");
		}

			


		
		
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "VoidTransactionOnDebitTransactionIDAfterrefund")
	public void CashbackService_VoidTransactionOnDebitTransactionIDAfterrefund(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.VOIDCASHBACKTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int deducted_AMount = earnedbalanceAfter_int-earnedBalance_Int;
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(earnedBalance_Int+deducted_AMount, earnedbalanceAfter_int);
			AssertJUnit.assertEquals("CLIENT TXN ALREADY VOIDED", comment);


		}
		
		else {
			System.out.println("This testcase validate  when next data provider will run");
		}

			


		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "VoidTransactionOnRefundedTransactionID")
	public void CashbackService_VoidTransactionOnRefundedTransactionID(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.VOIDCASHBACKTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		int deducted_AMount = earnedbalanceAfter_int-earnedBalance_Int;
		
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals("cashback mismatched", stored_Int+Debit_amount, storedbalanceAfter_int);
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		
		
			AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);
			AssertJUnit.assertEquals( earnedbalanceAfter_int-earnedBalance_Int,deducted_AMount);


		}

	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "GetStatusOnDebitDP")
	public void CashbackService_getStatusOnDebitTxn(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID,String Amount) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.GETSTATUSCASHBACK,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		String amount_response = JsonPath.read(response, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String clientTxn = JsonPath.read(response, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("Amont mismatched", amount_response, Amount);
		AssertJUnit.assertEquals("clientTxn mismatched", clientTransactionID, clientTxn);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);


		}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "GetStatusOnRefundedDP")
	public void CashbackService_getStatusOnRefundedTxn(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID,String Amount) {
		
		int Debit_amount =(Integer.parseInt(Amount))/100;
		String debit_amount_string = String.valueOf(Debit_amount);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.GETSTATUSCASHBACK,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		
		String amount_response = JsonPath.read(response, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String clientTxn = JsonPath.read(response, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		Integer amount_int = (Integer.valueOf(amount_response)/100);

		String amount_str = String.valueOf(amount_int);
		System.out.println("amount response ==================" + amount_response);
		System.out.println("amount response ==================" + debit_amount_string);

		
		AssertJUnit.assertEquals("Amont mismatched", amount_str, debit_amount_string);
		AssertJUnit.assertEquals("clientTxn mismatched", clientTransactionID, clientTxn);
		AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);


		}
	
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
//	"ExhaustiveRegression"},dataProvider = "GetStatusOnVoidedDP")
//	public void CashbackService_getStatusOnVoided(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
//		
//
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.GETSTATUSCASHBACK,
//				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
//		System.out.println("Url------>>>>> "+service.URL);
//		System.out.println("payload------>>>>> "+service.Payload);
//
//		RequestGenerator req = new RequestGenerator(service);		
//		String response = req.returnresponseasstring();
//		
//
//		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
//		System.out.println("Response---->>>>" +response );
//		
//		
//
//		String amount_response = JsonPath.read(response, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String clientTxn = JsonPath.read(response, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		AssertJUnit.assertEquals("Amont mismatched", amount_response, "0");
//		AssertJUnit.assertEquals("clientTxn mismatched", clientTransactionID, clientTxn);
//		
//			AssertJUnit.assertEquals("Getting Succes msg", "TX_SUCCESS", msg1);
//
//
//		}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "GetStatusINvalidClientTxnDP")
	public void CashbackService_getStatusWithInvalidClientTXN(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.GETSTATUSCASHBACK,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	    System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		

		
			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);


		}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundCashbackWithVoidedClienTXN")
	public void CashbackService_RefundwithVoidedTransactionClient(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		int earnedBalance_Int =Math.round(Float.parseFloat(earnedbalance)) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		int storedbalanceAfter_int = Math.round(Float.parseFloat(storedbalanceAfter));

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		int earnedbalanceAfter_int = Math.round(Float.parseFloat(earnedbalanceAfter));

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --->>>>>>>>>>>>>>>>>> > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );
		

		
		if(msg1.contains("TX_FAILURE"))
		{
			String comment = JsonPath.read(response, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

			AssertJUnit.assertEquals("Getting Succes msg", "TX_FAILURE", msg1);
			AssertJUnit.assertEquals(earnedBalance_Int, earnedbalanceAfter_int);
			AssertJUnit.assertEquals("TXN ALREADY VOIDED", comment);


		}
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"}, dataProvider = "debitcashbackFromEarnedAmountWithFloat", priority=0)
	public void CashbackService_debitFromEarnedBalanceWithFloat(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		float earnedBalance_Int =Float.parseFloat(earnedbalance);
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();	
		System.out.println("Debit Payload Request------- >>>>>" + service.Payload);
		
        log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		float earnedbalanceAfter_int = Float.parseFloat(earnedbalanceAfter);

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
		System.out.println("Debit Response---->>>>" +response );
		AssertJUnit.assertEquals("balance mismatched",earnedBalance_Int-Debit_amount, earnedbalanceAfter_int, earnedBalance_Int-Debit_amount);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression", "Fox7Sanity",
	"ExhaustiveRegression"},dataProvider = "refundFloatCashbackToStoredAcountDP")
	public void CashbackService_FloatRefundToEraned(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		int Debit_amount =(Integer.parseInt(amount))/100;
		System.out.println("Debuted amount" + Debit_amount);
		String stored = getStoredCashBackRequest(customerID);
		float stored_Int =Float.parseFloat(stored) ;
		float Amount = Float.parseFloat(amount);

//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		String earnedbalance = getEarnedCashBackRequest(customerID);
		float earnedBalance_Int =Float.parseFloat(earnedbalance) ;
		System.out.println("earnedbalance----->>" + earnedBalance_Int);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		
		log.info(service.URL);
		String storedbalanceAfter= getStoredCashBackRequest(customerID);
		float storedbalanceAfter_int = Float.parseFloat(storedbalanceAfter);

		System.out.println("StoreAFter----->>" + storedbalanceAfter);
		String earnedbalanceAfter = getEarnedCashBackRequest(customerID);
		float earnedbalanceAfter_int = Float.parseFloat(earnedbalanceAfter);

		System.out.println("earnedbalanceAfter ----->>" + earnedbalanceAfter_int);
		String msg1 = JsonPath.read(response, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	System.out.println("mesagge --- > >>  > >"  + msg1);
		System.out.println("Response---->>>>" +response );

		AssertJUnit.assertEquals("Cashback Mismatched", earnedBalance_Int+(Amount/100), storedbalanceAfter_int, earnedBalance_Int+(Amount/100));
//		int deductFromBothBalance = (stored_Int)+earnedBalance_Int;
//		AssertJUnit.assertEquals(storedbalanceAfter_int, 0);
//		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
//		int count = getCount(dbConnection_Fox7);
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
		AssertJUnit.assertEquals("debit is not working is not working", 200,
				rs.response.getStatus());
		
	}
	
	private String getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID,String ppsType)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMDEBITCASHBACK,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Debit chcksum Url------>>>>> "+service.URL);
		System.out.println("debit payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}
	
	
	private String getEarnedCashBackRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("Earened Balance Response---- >>>" +req.returnresponseasstring() );
		String response = req.returnresponseasstring();
		String earnedAmt = JsonPath.read(response, "$.earnedCreditbalance").toString().replace("[", "").replace("]", "").trim();

		
		return earnedAmt;

		
	}
	
	public void creditCashBack(String param1, String param2,
			String param3, String param4, String param5, String param6,
			String param7, String param8) {
		// System.out.println("Before: "+balanceBefoe);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
				init.Configurations, new String[] { param1, param2, param3,
						param4, param5, param6, param7});
		RequestGenerator rs = new RequestGenerator(service);
		System.out.println("Response IN credit---"+ rs.returnresponseasstring());
		String earnedamt = getEarnedCashBackRequest(param1);
		System.out.println("earned amt after debit " +earnedamt );
		String Storedamt = getStoredCashBackRequest(param1);
		System.out.println("earned amt after debit " +Storedamt );
		
		
	}
	
	private String getStoredCashBackRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("Check Stored  Response---- >>>" +req.returnresponseasstring() );
		String response = req.returnresponseasstring();
		String storedAmt = JsonPath.read(response, "$.storeCreditBalance").toString().replace("[", "").replace("]", "").trim();

		return storedAmt;

		
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
	
	// Verify checksum from here
	
//	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression", "Fox7Sanity",
//	"ExhaustiveRegression" }, dataProvider = "vDebitChckSm1")//vDebitChecksum , redeemGiftCard
//	public void cashBackService_vDebitChecksumResponse(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
//
//			MyntraService cksservice = Myntra.getService(ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMDEBITCASHBACK,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.XML);
//				HashMap cksgetParam = new HashMap();
//				cksgetParam.put("user", "manishkumar.gupta@myntra.com");
//				RequestGenerator req = new RequestGenerator(cksservice, cksgetParam);
//				String jsoncheckRes = req.respvalidate.returnresponseasstring();
//				System.out.println("Json resp chksum : -------> "+jsoncheckRes);
//				
//				MyntraService dbCbservice = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
//				init.Configurations, new String[] { jsoncheckRes, ppsID,ppsTransactionID, orderId, amount,customerID, ppsType, },PayloadType.JSON, PayloadType.JSON);
//				HashMap dbCbgetParam = new HashMap();
//				dbCbgetParam.put("user", "manishkumar.gupta@myntra.com");
//				//System.out.println("Service request payload--- ? " + dbCbservice.Payload);
//				RequestGenerator req1 = new RequestGenerator(dbCbservice, dbCbgetParam);
//				String jsonDebCbRes = req1.respvalidate.returnresponseasstring();
//				System.out.println("Debit Checksum response: ---->> "+jsonDebCbRes);
//				String dbtChksmResp=JsonPath.read(jsonDebCbRes, "$..checksum").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				System.out.println("Debit checksum Resp is :  =====>> "+dbtChksmResp);
//				String txStatus=JsonPath.read(jsonDebCbRes, "$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				System.out.println("Status : "+txStatus);
//				String ppsType1=JsonPath.read(jsonDebCbRes, "$..ppsType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				String ppsActionType=JsonPath.read(jsonDebCbRes, "$..ppsActionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				String instrumentActionType=JsonPath.read(jsonDebCbRes, "$..instrumentActionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				String clientTranscId=JsonPath.read(jsonDebCbRes, "$..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				System.out.println("ClientTranscID: ---->> "+clientTranscId);
//				String dup=JsonPath.read(jsonDebCbRes, "$..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//				System.out.println("Duplicate ? -----> "+dup);
//				
//				MyntraService service2 = Myntra.getService(
//						ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CASHBACKPPSCHECKSUMRESP,
//						init.Configurations, new String[] {checksum, ppsTransactionID, ppsID,orderId, amount, ppsType1,ppsActionType,instrumentActionType,customerID,txStatus,clientTranscId,dup},
//						PayloadType.JSON, PayloadType.JSON);
//				
////				HashMap getParam2 = new HashMap();
////				getParam2.put("user", "manishkumar.gupta@myntra.com");
//				//System.out.println("Service request payload chcks--- ? " + service2.Payload);
//				RequestGenerator req2 = new RequestGenerator(service2);
//				String jsonDebitRes1 = req2.respvalidate.returnresponseasstring();
//				System.out.println("Checksum Resp from lp checksum api: ---->> "+jsonDebitRes1);
//				Assert.assertTrue(dbtChksmResp.contains( jsonDebitRes1));
//				//Assert.assertEquals(dbtChksmResp, jsonDebitRes1);
//	}
	
	
	
	
	

//	@Test(groups = { "Smoke", "Sanity", "MiniRegression", "Regression",
//	"ExhaustiveRegression" }, dataProvider = "vRefundCashback")//vDebitChecksum , redeemGiftCard
//	public void cashBack_vRefundChecksumResponse(String checksum,String ppsID ,String ppsTransactionID,String clientTransactionID,String orderId,String amount,String ppsType,String customerID) throws InterruptedException  {
//		
//		
//		
//		MyntraService cksservice = Myntra.getService(ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMDEBITCASHBACK,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID}, PayloadType.JSON,PayloadType.XML);
//		HashMap cksgetParam = new HashMap();
//		cksgetParam.put("user", "manishkumar.gupta@myntra.com");
//		RequestGenerator req = new RequestGenerator(cksservice, cksgetParam);
//		String jsoncheckRes = req.respvalidate.returnresponseasstring();
//		System.out.println("Json resp chksum : -------> "+jsoncheckRes);
//		
//		MyntraService dbCbservice = Myntra.getService(
//		ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,
//		init.Configurations, new String[] { jsoncheckRes, ppsID,ppsTransactionID, orderId, amount, ppsType,customerID},PayloadType.JSON, PayloadType.JSON);
//		HashMap dbCbgetParam = new HashMap();
//		dbCbgetParam.put("user", "manishkumar.gupta@myntra.com");
//		//System.out.println("Service request payload--- ? " + dbCbservice.Payload);
//		RequestGenerator req1 = new RequestGenerator(dbCbservice, dbCbgetParam);
//		String jsonDebCbRes = req1.respvalidate.returnresponseasstring();
//		System.out.println("Debit Checksum response: ---->> "+jsonDebCbRes);
//		
//		
//		
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
//				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();
//		System.out.println("Rfund Cashback pps resp"+response);
//		
//				
//				
//		
//		
//
//	}
	
}
