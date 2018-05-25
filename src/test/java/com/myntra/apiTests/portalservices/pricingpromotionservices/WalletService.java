package com.myntra.apiTests.portalservices.pricingpromotionservices;

import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.walletDP;
import org.apache.commons.collections.CollectionUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.walletServiceHelper.walletHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class WalletService extends walletDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(WalletService.class);
	APIUtilities apiUtil = new APIUtilities();
	static walletHelper walletServiceHelper = new walletHelper();
	CommonUtils commonUtil= new CommonUtils();
	
	
	
	

	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity","Regression","ExhaustiveRegression"}, enabled=false, dataProvider = "Checkbalance", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceBalance(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.checkBalance(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);

		
		
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity","PPSide", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "PhonePebalance", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceGetBalancePhonePe(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Phone Pe API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);

		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","PPSide", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "CreditPhonePeSide", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceCreditPhonePeSide(String randomTransactionId,String UIDX,String amount) {
		
		RequestGenerator ppSideBalance = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseBeforeCredit = ppSideBalance.respvalidate.returnresponseasstring();
		String checkbalnce = JsonPath.read(balanceResponseBeforeCredit, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("balance before credit\n" + checkbalnce);
		Integer checkbalnceInt = Integer.parseInt(checkbalnce);
		System.out.println("Before balance\n" + checkbalnceInt);

		
		RequestGenerator creditPPside = walletServiceHelper.CreditPhonePeSide(randomTransactionId,UIDX,amount);
		String creditResponse = creditPPside.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Phone Pe API response : \n\n"+creditResponse+"\n");
		String amountCredit = JsonPath.read(creditResponse, "$.data..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String Message = JsonPath.read(creditResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		Integer amountCreditInt = Integer.parseInt(amountCredit);
        System.out.println("uhsdkhfhsdufd=====>  " + amountCreditInt);
		
		RequestGenerator ppSideBalanceAfterCredit = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseAfterCredit = ppSideBalanceAfterCredit.respvalidate.returnresponseasstring();
		String checkbalnceAFtercredit = JsonPath.read(balanceResponseAfterCredit, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		System.out.println("balance before credit\n" + checkbalnceAFtercredit);
		Integer checkbalnceAfter = Integer.parseInt(checkbalnceAFtercredit);
		System.out.println("After balance\n" + checkbalnceAfter);
		Integer AMountDiffernece = checkbalnceAfter-checkbalnceInt;

		AssertJUnit.assertEquals(AMountDiffernece,amountCreditInt);
		AssertJUnit.assertEquals("Your payment is successful.", Message);
		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","PPSide", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "DebitPhonePeSide", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceDebitPhonePeSide(String randomTransactionId,String UIDX,String amount) {
		
		
		RequestGenerator ppSideBalance = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseBeforeCredit = ppSideBalance.respvalidate.returnresponseasstring();
		String checkbalnce = JsonPath.read(balanceResponseBeforeCredit, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("balance before credit\n" + checkbalnce);
		Integer checkbalnceInt = Integer.parseInt(checkbalnce);
		System.out.println("Before balance\n" + checkbalnceInt);

		
		
		RequestGenerator debitPPside = walletServiceHelper.DebitPhonePeSide(randomTransactionId,UIDX,amount);
		String debitResponse = debitPPside.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Phone Pe API response : \n\n"+debitResponse+"\n");
		String amountCredit = JsonPath.read(debitResponse, "$.data..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String Message = JsonPath.read(debitResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		Integer amountCreditInt = Integer.parseInt(amountCredit);
        System.out.println("uhsdkhfhsdufd=====>  " + amountCreditInt);
		RequestGenerator ppSideBalanceAfterCredit = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseAfterCredit = ppSideBalanceAfterCredit.respvalidate.returnresponseasstring();
		String checkbalnceAFtercredit = JsonPath.read(balanceResponseAfterCredit, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		System.out.println("balance before credit\n" + checkbalnceAFtercredit);
		Integer checkbalnceAfter = Integer.parseInt(checkbalnceAFtercredit);
		System.out.println("After balance\n" + checkbalnceAfter);
		
		Integer AMountDiffernece = checkbalnceInt-checkbalnceAfter;

		AssertJUnit.assertEquals(AMountDiffernece,amountCreditInt);
		AssertJUnit.assertEquals("Your payment is successful.", Message);
		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","PPSide", "Regression","ExhaustiveRegression", "Fox7Sanity"},enabled=false, dataProvider = "backTsourcePhonePeSide", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceBackToServicePhonePeSide(String randomTransactionId,String UIDX,String amount,String transactionId) {
		
		
		RequestGenerator ppSideBalance = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseBeforeCredit = ppSideBalance.respvalidate.returnresponseasstring();
		String checkbalnce = JsonPath.read(balanceResponseBeforeCredit, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("balance before credit\n" + checkbalnce);
		Integer checkbalnceInt = Integer.parseInt(checkbalnce);
		System.out.println("Before balance\n" + checkbalnceInt);

		
		
		RequestGenerator debitPPside = walletServiceHelper.DebitPhonePeSide(randomTransactionId,UIDX,amount);
		String debitResponse = debitPPside.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Phone Pe API response : \n\n"+debitResponse+"\n");
		String amountCredit = JsonPath.read(debitResponse, "$.data..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String Message = JsonPath.read(debitResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String providerReferenceId = JsonPath.read(debitResponse, "$.data..providerReferenceId").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting Phone Pe reference id : \n\n"+providerReferenceId+"\n");

		RequestGenerator backToSource = walletServiceHelper.CreditBackToSourcePhonePeSide(transactionId,UIDX,amount,providerReferenceId);
		String backToSourceResponse = backToSource.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting back to source Phone Pe API response : \n\n"+backToSourceResponse+"\n");
		String amountBackTosource = JsonPath.read(debitResponse, "$.data..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();


		
		Integer amountCreditInt = Integer.parseInt(amountBackTosource);
        System.out.println("uhsdkhfhsdufd=====>  " + amountCreditInt);
		RequestGenerator ppSideBalanceAfterBackToSource = walletServiceHelper.GetPhonePeBalance(UIDX);
		String balanceResponseAfterBackToSOurce = ppSideBalanceAfterBackToSource.respvalidate.returnresponseasstring();
		String checkbalnceAFterBackToSource = JsonPath.read(balanceResponseAfterBackToSOurce, "$.data..walletBalance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		System.out.println("balance before credit\n" + checkbalnceAFtercredit);
		Integer checkbalnceAfterBackToSOurce = Integer.parseInt(checkbalnceAFterBackToSource);
		System.out.println("After balance\n" + checkbalnceAfterBackToSOurce);
		

		AssertJUnit.assertEquals(checkbalnceAfterBackToSOurce,checkbalnceInt);
		AssertJUnit.assertEquals("Your payment is successful.", Message);
		
		
	}
	
	
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "Checkbalance", priority=0, description="\n 1. Get check balance ")
//	public void WalletServiceBalance_pps(String UIDX) {
//		
//		RequestGenerator Checkbalance = walletServiceHelper.checkBalance_PPS(UIDX);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
//
//		
//		
//	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "NewUserMigrateStatus", priority=0, description="\n 1. Get check migrate status ")
	public void WalletServiceMigratedStatusWIthNewUser(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

//		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");

		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NO_CB_AND_NOT_MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
}
	@Test(groups= { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"},enabled=false,  dataProvider = "cashbackMigratedStatus", priority=0, description="\n 1. GET CHECK MIGRATEDSTATUS")
	public void WalletServiceMigratedStatus(String UIDX) {
		RequestGenerator CheckBalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = CheckBalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		
	}
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled= false, dataProvider = "NonMigrateStatus", priority=0, description="\n 1. Get check migrate status ")
	public void WalletServiceMigratedStatusWIthNotMigrated(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "MigratedUserStatus", priority=0, description="\n 1. Get check migrate status ")
	public void WalletServiceMigratedStatusWIthMigrated(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"},enabled=false, dataProvider = "DebitPhonePe", priority=0, description="\n 1. Get check migrate status ")
	public void WalletService_DebitPhonepe(String UIDX,String debitAmount) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPhonePe = walletServiceHelper.debitPhonePe(UIDX,debitAmount);
		String debitResponse = debitPhonePe.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DebitPhonepe Api Response : \n\n"+debitResponse+"\n");


		


		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, enabled=false, dataProvider = "CreditPhonePe", priority=0, description="\n 1. Get check migrate status ")
	public void WalletService_CreditPhonepe(String UIDX,String creditAmount) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPhonePe = walletServiceHelper.creditPhonePe(UIDX,creditAmount);
		String creditResponse = creditPhonePe.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting CreditPhonepe Api Response : \n\n"+creditResponse+"\n");
		
		
		RequestGenerator CheckbalanceAfterCredit = walletServiceHelper.migrateStatus(UIDX);
		String balanceAfterCreditResponse = CheckbalanceAfterCredit.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceAfterCreditResponse+"\n");
		String checkbalnceAfterCredit = JsonPath.read(balanceAfterCreditResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nBalnace after credit : \n\n"+checkbalnceAfterCredit+"\n");

		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
}
	
	
	
	
//	@Test(groups={"Smoke", "Sanity","MiniRegression","Regression", "ExhaustivRegression"}, dataProvider="BalanceCheck", priority=0, description="\n Check Balance for the user")
//	//String randomTransactionId=walletServiceHelper.generateRandomString();
//	public void creditPhonePPS(String UIDX, String debitAmount){
//		String randomTransactionId=walletServiceHelper.generateRandomString();
//		RequestGenerator Checkbalance = walletServiceHelper.creditPPS(randomTransactionId,UIDX,debitAmount);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..customerID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String ppTxID = JsonPath.read(balanceResponse, "$..ppsTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String txStatus = JsonPath.read(balanceResponse, "$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String clientTxID = JsonPath.read(balanceResponse, "$..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+login+"\n");
//		System.out.println("\nMessage: \n\n"+ppTxID+"\n");
//		System.out.println("\n status : \n\n"+txStatus+"\n");
//		//RequestGenerator debitPhonePe = walletServiceHelper.debitPhonePe(UIDX,debitAmount);
//		//String debitResponse = debitPhonePe.respvalidate.returnresponseasstring();
//		//System.out.println("\nPrinting DebitPhonepe Api Response : \n\n"+debitResponse+"\n");
//		RequestGenerator CheckbalanceAfterCredit = walletServiceHelper.migrateStatus(UIDX);
//		String balanceAfterCreditResponse = CheckbalanceAfterCredit.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceAfterCreditResponse+"\n");
//		String checkbalnceAfterCredit = JsonPath.read(balanceAfterCreditResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		System.out.println("\nBalnace after credit : \n\n"+checkbalnceAfterCredit+"\n");
//
//		
//
//
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
//		//AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		AssertJUnit.assertEquals("Message doesn't success", txStatus, "TX_SUCCESS");
//		//AssertJUnit.assertEquals("Message doesn't success", status, "success");
//		
//}
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackAmount", priority=0, description="\n 1. Get check balance ")
//	public void creditPPS(String checksum, String ppsTransactionID, String amount, String UIDX) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//	
//		
//		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsTransactionID, amount, UIDX);
//		String creditResponse = creditPP.returnresponseasstring();
//		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
//		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		
//		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Message doesn't success", message, "success");
//		AssertJUnit.assertEquals("Message doesn't success", status, "success");
//		int balancebrforeCredit=Integer.parseInt(checkbalnce);
//		int balanceToCredit=Integer.parseInt(amount);
//		String balaanceAfterCredit=Integer.toString(balancebrforeCredit+balanceToCredit);
//		System.out.println("\n Balance Afetr Credit:"+ balaanceAfterCredit+"\n");
//		AssertJUnit.assertEquals("Balance are not equal", balaanceAfterCredit, balanceCredit);
//		
//	
//	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "creditNormalNonMigrated", priority=0, description="\n 1. check balance 2.Credit amount 3. Again check Balance to verify amount is credited  ")
	public void creditNonMigrated(String UIDX, String amount) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		String statusCredit= JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messageCredit= JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting creditPP Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		
		//String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		AssertJUnit.assertEquals("Message doesn't success", statusCredit, "success");
		AssertJUnit.assertEquals("Message doesn't success", messageCredit, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount));
		System.out.println("Amount before Credited/n/n--->" + balanceBeforeCredit);
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->"+ subtractFromCreditNEw);
		AssertJUnit.assertEquals("Status is not same", migrateStatus1, "NOT_MIGRATED");
		AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);

		
//		//float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
//		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);
//		AssertJUnit.assertEquals("Both the Balances are not equal", balanceafterCreditNEw, balanceBeforeCredit);
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditNormalMigrated", priority=1, description="\n 1. check balance 2.Credit amount for migrated user 3. Again check Balance to verify amount is credited")
	public void creditMigrated(String UIDX, String amount) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		String statusCredit= JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messageCredit= JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting Normal credit Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		//String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		AssertJUnit.assertEquals("Message doesn't success", statusCredit, "success");
		AssertJUnit.assertEquals("Message doesn't success", messageCredit, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount));
		System.out.println("Amount before Credited/n/n--->" + balanceBeforeCredit);
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->"+ subtractFromCreditNEw);
		AssertJUnit.assertEquals("Status is not same", migrateStatus1, "MIGRATED");
		AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);

		
//		//float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
//		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);
//		AssertJUnit.assertEquals("Both the Balances are not equal", balanceafterCreditNEw, balanceBeforeCredit);
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity","Regression","ExhaustiveRegression"}, dataProvider = "creditNegative", priority=2, description="\n 1. check balance 2.Credit Negative amount 3. Again check Balance to verify amount is credited or not" )
	public void creditNegativeBalance(String UIDX, String amount) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		System.out.println("\nPrinting Normal credit Api Response : \n\n"+ creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		//String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String msg1 = JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String msg2 = JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		System.out.println("Message of Credit response\n" + msg2);
		
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount));
		System.out.println("Amount to be Credited\n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount before credit\n--->"+ checkbalnce);
		
		System.out.println("Check Amount after credit\n--->"+ balanceAfterCredit);
		
		
		AssertJUnit.assertEquals("Credit is Successful\n", "error", msg1);
		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "creditNormalFloating", priority=3, description="\n 1. check balance 2.Credit floating amount 3. Again check Balance to verify amount is credited ")
	public void creditFloatingBalance(String UIDX, String amount) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		String statusCredit= JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messageCredit= JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting Normal credit Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		//String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		AssertJUnit.assertEquals("Message doesn't success", statusCredit, "success");
		AssertJUnit.assertEquals("Message doesn't success", messageCredit, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		double balanceBeforeCredit= Double.parseDouble(checkbalnce);
		double credit_Amount=(Double.parseDouble(amount));
		System.out.println("Amount before Credited --->\n" + balanceBeforeCredit);
		System.out.println("Amount to be Credited --->\n" + credit_Amount);
		double balanceAfterCredit= Double.parseDouble(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit --->\n"+ balanceAfterCredit);
		
		double subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->\n"+ subtractFromCreditNEw);
		AssertJUnit.assertEquals("Status is not Migrated", migrateStatus1, "MIGRATED");
		AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression","Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "debitNormalNonMigrated", priority=4, description="\n 1. check balance \n2.debit partial amount for non migrated user \n3. Again check Balance to verify amount is debited ")
	public void debitNonMigratedPartial_amount_in_bank(String UIDX, String amount, String businessProcess, String itemType) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		String statusDebit = JsonPath.read(debitResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		
		System.out.println("Debit message\n\n" + messagedebit);
		System.out.println("Debit status\n\n" + statusDebit);
		AssertJUnit.assertEquals("Message doesn't success\n\n", messagedebit, "Amount mismatch for NEFT DEBIT");
		AssertJUnit.assertEquals("Status doesn't success\n\n", statusDebit, "error");
		//float Debit_amount = 
		//System.out.println("Deducted amount" + Debit_amount);
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit balance was\n\n" + checkbalance );
		System.out.println("After Debit balance is --->\n\n"+ checkbalance2);
//		float balanceBeforeDebit= Float.parseFloat(checkbalance);
//		float debit_Amount=(Float.parseFloat(amount));
//		System.out.println("Balance before debit-->" + balanceBeforeDebit);
//		System.out.println("Amount to be debit/n/n--->" + debit_Amount);
//		float balanceAfterDebit= Float.parseFloat(checkbalance2);
//		System.out.println("Amount after debit/n/n--->"+ balanceAfterDebit);
		
//		float balanceafterDebitNEw=balanceAfterDebit+debit_Amount;
		//System.out.println("Balance after adding debited amount -->"+ balanceafterDebitNEw);
		//AssertJUnit.assertEquals("Balance are not equal", balanceafterDebitNEw, balanceBeforeDebit);
			
		

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitfullNormalAmount", priority=5, description="\n 1. check balance \n2.debit full amount for non migrated user \n3. Again check Balance to verify amount is debited ")
	public void fulldebitNonMigrated(String UIDX, String amount, String businessProcess, String itemType) {
		

		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		String statusCredit= JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messageCredit= JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting creditPP Api Response : \n\n"+creditResponse+"\n");

		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String amountbeforedebit = JsonPath.read(balanceResponse, "$.data..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		String amountDebit = JsonPath.read(debitResponse, "$.debitAmount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		float Debit_amount=Float.parseFloat(amount);
		//System.out.println("Debit message\n\n" + messagedebit);
		System.out.println("Debit status\n\n" + amountDebit);
		//AssertJUnit.assertEquals("Message doesn't success\n\n", messagedebit, "Amount mismatch for NEFT DEBIT");
		//AssertJUnit.assertEquals("Amount is not same\n\n", amountDebit, Debit_amount);
		//float Debit_amount = 
		//System.out.println("Deducted amount" + Debit_amount);
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit balance was\n\n" + checkbalance );
		System.out.println("After Debit balance is --->\n\n"+ checkbalance2);
	}
	

	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitNormalNegativeNonMigrated", priority=11, description="\n 1. check balance \n2.debit Negative amount for non migrated user \n3. Again check Balance to verify amount is debited ")
	public void debitNegativeNormal(String UIDX, String amount, String businessProcess, String itemType) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		//String businessProcess1= JsonPath.read(debitResponse, "$.businessProcess").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String statusDebit = JsonPath.read(debitResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
	
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit balance was\n\n" + checkbalance );
		System.out.println("After Debit balance is --->\n\n"+ checkbalance2);
		AssertJUnit.assertEquals("Balance is not equal\n\n", checkbalance, checkbalance2);
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression", "Fox7Sanity"}, dataProvider = "debitNormalMigrated", priority=7, description="\n 1. check balance \n2.debit amount for  migrated user \n3. Again check Balance to verify amount is debited ")
	public void debitMigratedNormal(String UIDX, String amount, String businessProcess, String itemType) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		//String businessProcess1= JsonPath.read(debitResponse, "$.businessProcess").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String statusDebit = JsonPath.read(debitResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
	
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit balance was\n\n" + checkbalance );
		System.out.println("After Debit balance is --->\n\n"+ checkbalance2);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		//AssertJUnit.assertEquals("Balance is not equal\n\n", checkbalance, checkbalance2);
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitNormalFloating", priority=8, description="\n 1. Get check balance \n2.debit floating amount for migrated user \n3. Again check Balance to verify amount is debited ")
	public void debitNormalFloatingAmount(String UIDX, String amount, String businessProcess, String itemType) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator debitNormal = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitNormal.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		String amountDebited = JsonPath.read(debitResponse, "$.debitAmount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		System.out.println("AMount Debited-->\n\n" + amountDebited);
		//System.out.println("Debit status\n\n" + statusDebit);
		//AssertJUnit.assertEquals("debit doesn't success\n\n", amountDebited, "amount");
		
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit, balance was\n\n" + checkbalance );
		System.out.println("After Debit, balance is --->\n\n"+ checkbalance2);
		double balanceBeforeDebit= Double.parseDouble(checkbalance);
//		double debit_Amount=(Float.parseFlo(amount));
//		System.out.println("Balance before debit-->" + balanceBeforeDebit);
//		System.out.println("Amount to be debit/n/n--->" + debit_Amount);
		double balanceAfterDebit= Double.parseDouble(checkbalance2);
		double AMountDebited= Double.parseDouble(amountDebited);
//		System.out.println("Amount after debit/n/n--->"+ balanceAfterDebit);
		
		double balanceafterDebitNEw=balanceAfterDebit+AMountDebited;
		System.out.println("Balance after adding debited amount -->"+ balanceafterDebitNEw);
		AssertJUnit.assertEquals("Balance are not equal", balanceafterDebitNEw, balanceBeforeDebit);
			
		

		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditCashbackAmount", priority=9, description="\n 1. check balance \n2.credit amount for migrated user \n3. Again check Balance to verify amount is credited ")
	public void creditPhonePPSforMigrated(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
		String creditResponse = creditPP.returnresponseasstring();
		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
		String creditStatus= JsonPath.read(creditResponse,"$..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//System.out.println("Status of creidt pps api:--->"+creditStatus);
		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Amount before Credited/n/n--->" + balanceBeforeCredit);
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->"+ subtractFromCreditNEw);
		System.out.println("Status of Credit PPS api:---->"+creditStatus);
		AssertJUnit.assertEquals("Status is not same", migrateStatus1, "MIGRATED");
		AssertJUnit.assertEquals("Status is Failure", "TX_SUCCESS", creditStatus);
		
		//AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "creditCashbackNegativeBalance", priority=0, description="\n 1. check balance \n2.Credit negative amount for migrated user \n3. Again check Balance to verify amount is credited ")
	public void creditPPSNegativeBalance(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
		String creditResponse = creditPP.returnresponseasstring();
		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String msg1 = JsonPath.read(creditResponse, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount before credit/n/n--->"+ checkbalnce);
		
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		
		AssertJUnit.assertEquals("Credit is Successful", "TX_FAILURE", msg1);
		
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditCashbackNonMigrated", priority=0, description="\n 1. check balance \n2.Credit amount for non migrated user \n3. Again check Balance to verify amount is credited ")
	public void creditPPSNonMigrated(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
		String creditResponse = creditPP.returnresponseasstring();
		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		
		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Amount before Credited/n/n--->" + balanceBeforeCredit);
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->"+ subtractFromCreditNEw);
		AssertJUnit.assertEquals("Status is not same", migrateStatus1, "NOT_MIGRATED");
		AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);

		
//		//float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
//		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);
//		AssertJUnit.assertEquals("Both the Balances are not equal", balanceafterCreditNEw, balanceBeforeCredit);
		
	}
	
	//Need to fix this.
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditPPSLimit", priority=0, description="\n 1. check balance \n2.Credit a amount that will breach the limit(10K) for migrated user \n3.Verify 200 status code \n4. Verify error message \n5. Again check Balance to verify amount is credited " )
//	public void creditPPSLimitBreachforMigrated(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//	
//		
//		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
//		String creditResponse = creditPP.returnresponseasstring();
//		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
//		String creditTxStatus = JsonPath.read(creditResponse, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
//		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		
//		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Message doesn't success", message, "success");
//		AssertJUnit.assertEquals("Message doesn't success", status, "success");
//		
//		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
//		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
//		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
//		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
//		float credit_Amount=(Float.parseFloat(amount))/100;
//		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
//		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
//		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
//		
//		float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
//		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);
//		//AssertJUnit.assertEquals("Balance are not equal", balanceafterCreditNEw, balanceBeforeCredit);
//		AssertJUnit.assertEquals("Balance is successful-->", "TX_FAILURE", creditTxStatus);
//		
//		
//	}
	
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditPPSFloat", priority=0, description="\n 1. Get check balance ")
//	public void creditPPSFloating(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//	
//		
//		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//		
//		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
//		String creditResponse = creditPP.returnresponseasstring();
//		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
//		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
//		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		
//		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Message doesn't success", message, "success");
//		AssertJUnit.assertEquals("Message doesn't success", status, "success");
//		
//		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
//		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
//		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
//		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
//		float credit_Amount=(Float.parseFloat(amount))/100;
//		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
//		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
//		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
//		
//		float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
//		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);
//		AssertJUnit.assertEquals("Balance are not equal", balanceafterCreditNEw, balanceBeforeCredit);
//		
//	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackAmount", priority=0, description="\n 1. check balance \n2.debit a amount for migrated user \n3.Verify 200 status code \n4. Verify success message \n5. Again check Balance to verify amount is debited " )
	public void debitPhonePPSforMigrated(String debitChecksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("MESSAGE FOR DEBIT API----------->>>>" + message);
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator debitPP = walletServiceHelper.debitPPS(debitChecksum, ppsID, ppsTransactionID, orderId, amount, customerID);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debitPPS Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerID);
		String balancedebit = JsonPath.read(debitResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After balance --->\n\n"+checkbalance2);
		
		
		float balanceBeforeDebit= Float.parseFloat(checkbalance);
		
		float debit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Balance Before Debit--->" + balanceBeforeDebit);
		System.out.println("Amount to debit/n/n--->" + debit_Amount);
		float balanceAfterDebit= Float.parseFloat(checkbalance2);
		System.out.println("Balance after debit/n/n--->"+ balanceAfterDebit);
		
		float balanceafterDebitNEw=balanceAfterDebit+debit_Amount;
		//System.out.println("Amount balance in end-->"+ balanceafterDebitNEw);
		AssertJUnit.assertEquals("Balance are not equal", balanceafterDebitNEw, balanceBeforeDebit);
		

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackNonMigrated", priority=0, description="\n 1. check balance \n2.debit a amount for non migrated user \n3.Verify 200 status code \n4. Verify success message \n5. Again check Balance to verify amount is debited " )
	public void debitPhonePPSforNonMigrated(String debitChecksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitPPS(debitChecksum, ppsID, ppsTransactionID, orderId, amount, customerID);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debitPPS Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerID);
		String balancedebit = JsonPath.read(debitResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		//float Debit_amount = 
		//System.out.println("Deducted amount" + Debit_amount);
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After balance --->\n\n"+checkbalance2);
		float balanceBeforeDebit= Float.parseFloat(checkbalance);
		float debit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Balance before debit-->" + balanceBeforeDebit);
		System.out.println("Amount to be debit/n/n--->" + debit_Amount);
		float balanceAfterDebit= Float.parseFloat(checkbalance2);
		System.out.println("Amount after debit/n/n--->"+ balanceAfterDebit);
		
		float balanceafterDebitNEw=balanceAfterDebit+debit_Amount;
		//System.out.println("Balance after adding debited amount -->"+ balanceafterDebitNEw);
		AssertJUnit.assertEquals("Balance are not equal", balanceafterDebitNEw, balanceBeforeDebit);
			
		

		
	}
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackAmountMoreThanBalance", priority=0, description="\n 1. Get check balance ")
//	public void debitPPSMoreThanBalance(String checksum, String ppsTransactionID, String amount, String UIDX) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//		RequestGenerator debitPP = walletServiceHelper.debitPPS(checksum, ppsTransactionID, amount, UIDX);
//		String debitResponse = debitPP.returnresponseasstring();
//		System.out.println("\nPrinting debitPPS Api Response : \n\n"+debitResponse+"\n");
//		String msg1 = JsonPath.read(debitResponse, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String balancedebit = JsonPath.read(debitResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
//		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		//AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
//		float debit_Amount=(Float.parseFloat(amount))/100;
//		float balanceBeforeDebit= Float.parseFloat(checkbalnce);
//		System.out.println("TOtal Balance of User\n\n:--->" + balanceBeforeDebit);
//		System.out.println("Balance to be debited for User\n\n:--->" + debit_Amount);
//		System.out.println("\nDebit amount is greater than Current Balance\n"+ debit_Amount+ ">"+balanceBeforeDebit);
//		
//		AssertJUnit.assertEquals("Debit Failure", "TX_FAILURE", msg1);
//	
//		
//		//AssertJUnit.assertEquals("debit is not working is not working", 200,
//		//		rs.response.getStatus());
//
//		
//	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackNegativeBalance", priority=0, description="\n 1. check balance \n2.debit a negative amount for migrated user \n3.Verify 200 status code \n4. Verify failure message \n5. Again check Balance to verify amount is debited ")
	public void debitPPSNegativeBalance(String debitChecksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitPPS(debitChecksum,ppsID ,ppsTransactionID,orderId,amount,customerID);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debitPPS Api Response : \n\n"+debitResponse+"\n");
		String msg1 = JsonPath.read(debitResponse, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String balancedebit = JsonPath.read(debitResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Login Doesn't match", login, customerID);
		
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		float debit_Amount=(Float.parseFloat(amount))/100;
		float balanceBeforeDebit= Float.parseFloat(checkbalnce);
		System.out.println("TOtal Balance of User\n\n:--->" + balanceBeforeDebit);
		System.out.println("Balance to be debited for User\n\n:--->" + debit_Amount);
		System.out.println("\nDebit amount is in Negative \n"+ debit_Amount+ "<"+balanceBeforeDebit);
		
		
		AssertJUnit.assertEquals("Debit is Successful", "TX_FAILURE", msg1);
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "voidCbMigrated", priority=0, description="\n 1. check balance \n2.debit a amount for migrated user \n3. Void the amount for the same user \n4.Verify 200 status code \n4. Verify success message \n5. Again check Balance to verify amount is credited " )
	public void voidPhonePePPS(String voidChecksum, String ppsId,String ppsTransactionID,String UIDX,String clientTxID ) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		

		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After debit amount of user is  --->\n\n"+checkbalance2);

		
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service1.URL);
		System.out.println("Void payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String voidResponse1 = req1.returnresponseasstring();
		System.out.println("response of void ------ >>>" + voidResponse1);
		String voidTxStatus = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse4+"\n");
		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
		System.out.println("\nMessage: \n\n"+message4+"\n");
		System.out.println("\n status : \n\n"+status4+"\n");
		AssertJUnit.assertEquals("Void Balance is notEqualTo balance before Debit--->>>","TX_SUCCESS",voidTxStatus);
		AssertJUnit.assertEquals("Duplicate is True", "false",duplicateTx);

	}
	
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "voidAfterRefund", priority=0, description="\n 1. Get check balance 2. Debit the amount 3. refund the amount 4.Try to Void the same tx.")
//	public void voidPPSAfterRefundClientTx(String voidChecksum, String ppsId,String ppsTransactionID,String orderID,String amount,String UIDX,String clientTxID ) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalance+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//		
//		
//		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
//		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//float balancebrforedebit=Float.parseFloat(checkbalance);
//		System.out.println("After balance --->\n\n"+checkbalance2);
//
//		MyntraService service1 = Myntra.getService(
//				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,UIDX}, PayloadType.JSON,PayloadType.XML);
//		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
//		System.out.println("Refund payload------>>>>> "+service1.Payload);
//
//		RequestGenerator req1 = new RequestGenerator(service1);		
//		String refundResponse1 = req1.returnresponseasstring();
//		System.out.println("response of refund ------ >>>\n" + refundResponse1);
//		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		RequestGenerator CheckbalanceAfterRefund = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse2 = CheckbalanceAfterRefund.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse2+"\n");
//		String checkbalnce1 = JsonPath.read(balanceResponse2, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login1 = JsonPath.read(balanceResponse2, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus1 = JsonPath.read(balanceResponse2, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message1 = JsonPath.read(balanceResponse2, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status1 = JsonPath.read(balanceResponse2, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_SUCCESS",refundTxStatus);
//		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);
//		
//		MyntraService service2 = Myntra.getService(
//				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
//		System.out.println("Void chcksum Url------>>>>> "+service2.URL);
//		System.out.println("Void payload------>>>>> "+service2.Payload);
//		
//
//		RequestGenerator req2 = new RequestGenerator(service2);		
//		String voidResponse1 = req2.returnresponseasstring();
//		System.out.println("response of void ------ >>>" + voidResponse1);
//		String voidTxStatus2 = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String duplicateTx2 = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
//		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse4+"\n");
//		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
//		System.out.println("\nMessage: \n\n"+message4+"\n");
//		System.out.println("\n status : \n\n"+status4+"\n");
//		AssertJUnit.assertEquals("Void Balance is notEqualTo balance before Debit--->>>","TX_SUCCESS",voidTxStatus2);
//		AssertJUnit.assertEquals("Duplicate is True", "false",duplicateTx);

//	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression","Regression","ExhaustiveRegression"}, dataProvider = "voidCbMigrated", priority=0, description="\n 1. check balance \n2.debit a amount for migrated user \n3. Void the amount for the same user \n4.Verify 200 status code \n5. Verify success message \n6. Again check Balance to verify amount is credited " )
	public void voidPPSPartial(String voidChecksum, String ppsId,String ppsTransactionID,String UIDX,String clientTxID ) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
	
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After debit balance is --->\n\n"+checkbalance2);
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service1.URL);
		System.out.println("Void payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String voidResponse1 = req1.returnresponseasstring();
		System.out.println("response of void ------ >>>" + voidResponse1);
		String voidTxStatus = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse4+"\n");
		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
		System.out.println("\nMessage: \n\n"+message4+"\n");
		System.out.println("\n status : \n\n"+status4+"\n");
		AssertJUnit.assertEquals("Void Balance is notEqualTo balance before Debit--->>>","TX_SUCCESS",voidTxStatus);
		AssertJUnit.assertEquals("Duplicate is True", "false",duplicateTx);

	}

	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "voidCbMigrated", priority=0, description="\n 1. check balance \n2.debit a amount for migrated user \n3. Void the amount for the same user \n4.Verify 200 status code  \n5. Again Void the same amount for the same user using the same client tx. id \n6. Verify success message \n6. Again check Balance to verify amount is credited " )
	public void voidPPSTwiceClientTx(String voidChecksum, String ppsId,String ppsTransactionID,String UIDX,String clientTxID ) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		

		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After balance --->\n\n"+checkbalance2);

		
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service1.URL);
		System.out.println("Void payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String voidResponse1 = req1.returnresponseasstring();
		System.out.println("response of void ------ >>>" + voidResponse1);
		String voidTxStatus = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		MyntraService service2 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service2.URL);
		System.out.println("Void payload------>>>>> "+service2.Payload);

		RequestGenerator req2 = new RequestGenerator(service2);		
		String voidResponse2 = req2.returnresponseasstring();
		System.out.println("response of void2 ------ >>>" + voidResponse2);
		String voidTxStatus2 = JsonPath.read(voidResponse2, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx2 = JsonPath.read(voidResponse2, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		
		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse4+"\n");
		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
		System.out.println("\nMessage: \n\n"+message4+"\n");
		System.out.println("\n status : \n\n"+status4+"\n");
		AssertJUnit.assertEquals("Void Balance is notEqualTo balance before Debit--->>>","TX_SUCCESS",voidTxStatus);
		AssertJUnit.assertEquals("Duplicate is false", "true",duplicateTx2);

	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "voidCreditClientTX", priority=0, description="\n 1. check balance \n2.credit a amount for migrated user \n3.Void the amount for the same user \n4.Verify status code  \n5. Verify failure message \n6. Again check Balance to verify amount is not credited ")
	public void voidPPSForCreditTx(String voidChecksum, String ppsId,String ppsTransactionID,String UIDX,String clientTxID ) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after credit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		

		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After balance --->\n\n"+checkbalance2);

		
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service1.URL);
		System.out.println("Void payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String voidResponse1 = req1.returnresponseasstring();
		System.out.println("response of void ------ >>>" + voidResponse1);
		String voidTxStatus = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		MyntraService service2 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service2.URL);
		System.out.println("Void payload------>>>>> "+service2.Payload);

		RequestGenerator req2 = new RequestGenerator(service2);		
		String voidResponse2 = req2.returnresponseasstring();
		System.out.println("response of void2 ------ >>>" + voidResponse2);
		String voidTxStatus2 = JsonPath.read(voidResponse2, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx2 = JsonPath.read(voidResponse2, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String comments = JsonPath.read(voidResponse2, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Comment of Void Tx\n" + comments );
		
		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
		System.out.println("\nMessage: \n\n"+message4+"\n");
		System.out.println("\n status : \n\n"+status4+"\n");
		AssertJUnit.assertEquals("\nVoid txStatus is true\n","TX_FAILURE", voidTxStatus2);

	
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmount", priority=0, description="\n 1. Get check balance \n2. Debit amount from migrated user \n3.check the balance \n4. Refund the amount \n5. Verify the status \n6.Check the amount is credited " )
	public void refundPPSFull(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
		System.out.println("Refund payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String refundResponse1 = req1.returnresponseasstring();
		System.out.println("response of refund ------ >>>" + refundResponse1);
		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_SUCCESS",refundTxStatus);
		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);
		
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmountPartial", priority=0, description="\n 1. Get check balance \n2. Debit amount from migrated user \n3.check the balance \n4. Refund the amount \n5. Verify the status \n6.Check the amount is credited ")
	public void refundPPSPartial(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
		System.out.println("Refund payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String refundResponse1 = req1.returnresponseasstring();
		System.out.println("response of refund ------ >>>" + refundResponse1);
		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_SUCCESS",refundTxStatus);
		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);

		
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmountPartial", priority=0, description="\n 1. Get check balance \n2. Debit amount from migrated user \n3.check the balance \n4. Refund the amount \n5. Verify the status \n6.Check the amount is credited \n7.Again try to refund the same amount using the same client tx. id" )
	public void refundTwiceClientTx(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
		System.out.println("Refund payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String refundResponse1 = req1.returnresponseasstring();
		System.out.println("response of refund ------ >>>" + refundResponse1);
		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator req2 = new RequestGenerator(service1);		
		String refundResponse2 = req2.returnresponseasstring();
		System.out.println("response of refund second time ------ >>>" + refundResponse2);
		String refundTxStatus2 = JsonPath.read(refundResponse2, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx2 = JsonPath.read(refundResponse2, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_SUCCESS",refundTxStatus2);
		AssertJUnit.assertEquals("\nDuplicate field in response is false---","true", duplicateTx2);

		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmountCredit", priority=0, description="\n 1. Get check balance \n2.credit amount from migrated user \n3.check the balance \n4. Try to Refund the amount using credit client tx. id \n5. Verify the failure message and status \n6.Check the amount is credited ")
	public void refundPPSCreditTx(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
		System.out.println("Refund payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String refundResponse1 = req1.returnresponseasstring();
		System.out.println("\nresponse of refund ------ >>>\n" + refundResponse1);
		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String comments = JsonPath.read(refundResponse1, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		System.out.println("\nComment of Refund Response\n"+ comments);
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_FAILURE",refundTxStatus);
		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);

		
		
	}
	
	
	
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmountCredit", priority=0, description="\n 1. Get check balance ")
//	public void refund(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
//		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
//		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
//		System.out.println("\nMessage: \n\n"+message+"\n");
//		System.out.println("\n status : \n\n"+status+"\n");
//
//		MyntraService service1 = Myntra.getService(
//				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
//		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
//		System.out.println("Refund payload------>>>>> "+service1.Payload);
//
//		RequestGenerator req1 = new RequestGenerator(service1);		
//		String refundResponse1 = req1.returnresponseasstring();
//		System.out.println("\nresponse of refund ------ >>>\n" + refundResponse1);
//		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String comments = JsonPath.read(refundResponse1, "$.params..comments").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		System.out.println("\nComment of Refund Response\n"+ comments);
//		
//		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
//		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
//		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_FAILURE",refundTxStatus);
//		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);
//
//		
//		
//	}
//	
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "walletLogsNormal", priority=0, description="\n 1. Check logs for user upto 20 tx. ")
	public void WalletTransactionLogsWithLimitNoOffset(String UIDX,String limit,String offSet) {
		
		RequestGenerator Checkbalance = walletServiceHelper.walletLogs(UIDX, limit, offSet);
		String logResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting wallet Txn Log API response : \n\n"+logResponse+"\n");

		
		String login = JsonPath.read(logResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		

		System.out.println("\nUidx: \n"+ login);
		//System.out.println("\nTranscation_Type \n"+ txType);
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		//AssertJUnit.assertEquals("txType Doesn't match", txType, );
		
	
	
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "walletLogsWithOffsetValue", priority=0, description="\n 1. Check logs for user with limit and offset for tx.")
	public void WalletTransactionLogsWithOffset(String UIDX,String limit,String offSet) {
		
		RequestGenerator Checkbalance = walletServiceHelper.walletLogs(UIDX, limit, offSet);
		String logResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting wallet Txn Log API response : \n\n"+logResponse+"\n");
//		String creditInflow = JsonPath.read(logResponse, "$..walletLogs[.1].creditInflow").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String creditOutflow = JsonPath.read(logResponse, "$..walletLogs[.*].creditOutflow").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		String login = JsonPath.read(logResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String txType = JsonPath.read(logResponse, "$...transactionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

//		System.out.println("\nPrinting creditInFlow : \n\n"+creditInflow+"\n");
//		System.out.println("\nPrinting creditOutFlow : \n\n"+creditOutflow+"\n");
		System.out.println("\nUidx: \n"+ login);
		//System.out.println("\nTranscation_Type \n"+ txType);
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		//AssertJUnit.assertEquals("txType Doesn't match", txType, );
		
	
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "walletLogsWithZeroOffsetValue", priority=0, description="\n Get logs of user with zero offset ")
	public void WalletTxnLogsWithZeroLimitNOffset(String UIDX,String limit,String offSet) {
		
		RequestGenerator Checkbalance = walletServiceHelper.walletLogs(UIDX, limit, offSet);
		String logResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting wallet Txn Log API response : \n\n"+logResponse+"\n");
		String creditInflow = JsonPath.read(logResponse, "$..walletLogs[*].creditInflow").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String creditOutflow = JsonPath.read(logResponse, "$..walletLogs[*].creditOutflow").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		String login = JsonPath.read(logResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String txType = JsonPath.read(logResponse, "$...transactionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting creditInFlow : \n\n"+creditInflow+"\n");
		System.out.println("\nPrinting creditOutFlow : \n\n"+creditOutflow+"\n");
		System.out.println("\nUidx: \n"+ login);
		//System.out.println("\nTranscation_Type \n"+ txType);
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		//AssertJUnit.assertEquals("txType Doesn't match", txType, );
		
	
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "balanceCheckMigrate", priority=0, description="\n 1. Get check balance for migrated users ")
	public void balanceCheckMigrated(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.balanceCheck(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse+"\n");
		String totalBalance = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String txType = JsonPath.read(logResponse, "$...transactionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Total balance of user : \n\n"+totalBalance+"\n");
//		System.out.println("\nPrinting creditOutFlow : \n\n"+creditOutflow+"\n");
//		System.out.println("\nUidx: \n"+ login);
		//System.out.println("\nTranscation_Type \n"+ txType);
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
	
	
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "balanceCheckNonMigrate", priority=0, description="\n 1. Get check balance for non migrated users ")
	public void balanceCheckNonMigrated(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.balanceCheck(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse+"\n");
		String totalBalance = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String txType = JsonPath.read(logResponse, "$...transactionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Total balance of user : \n\n"+totalBalance+"\n");
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		
		
	}
	
//	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "creditSuggestDP", priority=21, description="\n 1. Suggest how much amount can be credited for the user")
//	public void creditSuggest(String UIDX, String ph) {
//		RequestGenerator Checkbalance1 = walletServiceHelper.balanceCheck(UIDX);
//		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse1+"\n");
//		String totalBalance = JsonPath.read(balanceResponse1, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		//String login = JsonPath.read(balanceResponse1, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		RequestGenerator Checkbalance = walletServiceHelper.creditSuggest(UIDX, ph);
//		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
//		System.out.println("\nPrinting Credit Suggest API response : \n\n"+balanceResponse+"\n");
//		
//		String status= JsonPath.read(balanceResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String message = JsonPath.read(balanceResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		String migrateStatus = JsonPath.read(balanceResponse, "$..userWalletMigrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		
//		
//		String login = JsonPath.read(balanceResponse, "$.creditSuggestRepsonseData.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		//String txType = JsonPath.read(logResponse, "$...transactionType").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//
//		//System.out.println("\nPrinting Total balance of user : \n\n"+totalBalance+"\n");
//		
//		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
//		AssertJUnit.assertEquals(" Not Successful", status, "SUCCESS");
//		
//		
//	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitSuggestDP", priority=21, description="\n 1. Suggest how much amount can be credited for the user")
	public void debitSuggestMigrated(String UIDX) {
		
		RequestGenerator Checkbalance1 = walletServiceHelper.balanceCheck(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse1+"\n");
		String totalBalance = JsonPath.read(balanceResponse1, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		//String login = JsonPath.read(balanceResponse1, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance = walletServiceHelper.debitSuggest(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Debit Suggest API response : \n\n"+balanceResponse+"\n");
		
		String status= JsonPath.read(balanceResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..userWalletMigrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		String login = JsonPath.read(balanceResponse, "$.debitSuggestRepsonseData.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals(" Not Successful", status, "SUCCESS");
		
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitSuggestDPNonMigrated", priority=21, description="\n 1. Suggest how much amount can be credited for the user")
	public void debitSuggestNonMigrated(String UIDX) {
		
		RequestGenerator Checkbalance1 = walletServiceHelper.balanceCheck(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse1+"\n");
		String totalBalance = JsonPath.read(balanceResponse1, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		//String login = JsonPath.read(balanceResponse1, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance = walletServiceHelper.debitSuggest(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Credit Suggest API response : \n\n"+balanceResponse+"\n");
		
		String status= JsonPath.read(balanceResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String migrateStatus = JsonPath.read(balanceResponse, "$..userWalletMigrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		//String login = JsonPath.read(balanceResponse, "$.debitSuggestRepsonseData.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		//AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals(" Not Successful", status, "SUCCESS");
		
	}
	
	

	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "debitSuggestDPNoCbNotMigrated", priority=21, description="\n 1. Suggest how much amount can be credited for the user")
	public void debitSuggestNoCBNOTMIGRATED(String UIDX) {
		
		RequestGenerator Checkbalance1 = walletServiceHelper.balanceCheck(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance Check API response : \n\n"+balanceResponse1+"\n");
		String totalBalance = JsonPath.read(balanceResponse1, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String creditOutflow = JsonPath.read(balanceResponse, "").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		//String login = JsonPath.read(balanceResponse1, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance = walletServiceHelper.debitSuggest(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Credit Suggest API response : \n\n"+balanceResponse+"\n");
		
		String status= JsonPath.read(balanceResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//String migrateStatus = JsonPath.read(balanceResponse, "$..userWalletMigrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		//String login = JsonPath.read(balanceResponse, "$.debitSuggestRepsonseData.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		//AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals(" Not Successful", status, "SUCCESS");
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "withDraw", priority=0, description="\n ")
	public void withDrawPP(String UIDX, String emailId) {
		
		RequestGenerator withDrawBalance = walletServiceHelper.withDraw(UIDX, emailId);
		String withDrawResponse = withDrawBalance.respvalidate.returnresponseasstring();
		
        System.out.println("\nPrinting WithDraw API response : \n\n"+withDrawResponse+"\n");
		
		String status= JsonPath.read(withDrawResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(withDrawResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(withDrawResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals(" Not Successful", status, "SUCCESS");
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "withDrawNON", priority=0, description="\n 1. Get check balance ")
	public void withDrawPPNonMigrated(String UIDX, String emailId) {
		
		RequestGenerator withDrawBalance = walletServiceHelper.withDraw(UIDX, emailId);
		String withDrawResponse = withDrawBalance.respvalidate.returnresponseasstring();
		
        System.out.println("\nPrinting WithDraw API response : \n\n"+withDrawResponse+"\n");
		
		String status= JsonPath.read(withDrawResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(withDrawResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(withDrawResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		
		//AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Successful", status, "SUCCESS");
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "creditCashbackAmountSchema", priority=0, description="\n 1.credit amount \n 2. After crediting amount Validate the schema of the response" )
	public void creditPhonePPSforMigratedSchema(String checksum, String ppsId, String ppsTransactionID, String orderId, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator creditPP = walletServiceHelper.creditPPS(checksum, ppsId, ppsTransactionID, orderId, amount, customerId);
		String creditResponse = creditPP.returnresponseasstring();
		System.out.println("\nPrinting creditPPS Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerId);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
		
		String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float balanceafterCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount balance in end-->"+ balanceafterCreditNEw);

		
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/credit-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(creditResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in walletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "debitCashbackAmountSchema", priority=0, description="\n 1.debit amount \n 2. After debiting amount Validate the schema of the response ")
	public void debitPhonePPSforMigratedSchema(String debitChecksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String customerID) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator debitPP = walletServiceHelper.debitPPS(debitChecksum, ppsID, ppsTransactionID, orderId, amount, customerID);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debitPPS Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, customerID);
		String balancedebit = JsonPath.read(debitResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "MIGRATED");
//		AssertJUnit.assertEquals("Message doesn't success", message, "success");
//		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerID);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After balance --->\n\n"+checkbalance2);
		
		
		float balanceBeforeDebit= Float.parseFloat(checkbalance);
		
		float debit_Amount=(Float.parseFloat(amount))/100;
		System.out.println("Balance Before Debit--->" + balanceBeforeDebit);
		System.out.println("Amount to debit/n/n--->" + debit_Amount);
		float balanceAfterDebit= Float.parseFloat(checkbalance2);
		System.out.println("Balance after debit/n/n--->"+ balanceAfterDebit);
		
		float balanceafterDebitNEw=balanceAfterDebit+debit_Amount;
		//System.out.println("Amount balance in end-->"+ balanceafterDebitNEw);
//		AssertJUnit.assertEquals("Balance are not equal", balanceafterDebitNEw, balanceBeforeDebit);
		
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/debit-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(debitResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}

		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "voidCbMigratedSchema", priority=0, description="\n 1. Debit the amount \n2.Void the amount \3.Validate the response of void after crediting the amount ")
	public void voidPhonePePPSSchema(String voidChecksum, String ppsId,String ppsTransactionID,String UIDX,String clientTxID ) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		

		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("After debit amount of user is  --->\n\n"+checkbalance2);

		
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.VOIDWALLETPPS,init.Configurations,new String[]{voidChecksum,ppsId,ppsTransactionID,clientTxID}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Void chcksum Url------>>>>> "+service1.URL);
		System.out.println("Void payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String voidResponse1 = req1.returnresponseasstring();
		System.out.println("response of void ------ >>>\n" + voidResponse1);
		String voidTxStatus = JsonPath.read(voidResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(voidResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance4 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse4 = Checkbalance4.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse4+"\n");
		String checkbalance4 = JsonPath.read(balanceResponse4, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String login4 = JsonPath.read(balanceResponse4, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus4 = JsonPath.read(balanceResponse4, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message4 = JsonPath.read(balanceResponse4, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status4 = JsonPath.read(balanceResponse4, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after void : \n\n"+checkbalance4+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus4+"\n");
		System.out.println("\nMessage: \n\n"+message4+"\n");
		System.out.println("\n status : \n\n"+status4+"\n");
//		AssertJUnit.assertEquals("Void Balance is notEqualTo balance before Debit--->>>","TX_SUCCESS",voidTxStatus);
//		AssertJUnit.assertEquals("Duplicate is True", "false",duplicateTx);
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/void-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(voidResponse1, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}

	}
	
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "refundCashbackAmountSchema", priority=0, description="\n 1. Debit the amount \n2.refund the amount \3.Validate the response of refund after crediting the amount ")
	public void refundPPSFullSchema(String refundchecksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance after debit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{refundchecksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Refund chcksum Url------>>>>> "+service1.URL);
		System.out.println("Refund payload------>>>>> "+service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);		
		String refundResponse1 = req1.returnresponseasstring();
		System.out.println("response of refund ------ >>>" + refundResponse1);
		String refundTxStatus = JsonPath.read(refundResponse1, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String duplicateTx = JsonPath.read(refundResponse1, "$.params..duplicate").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(customerId);
		String balanceResponse1 = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce1 = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login1 = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message1 = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status1 = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
//		AssertJUnit.assertEquals("\nTransaction doesn't matches--->","TX_SUCCESS",refundTxStatus);
//		AssertJUnit.assertEquals("\nDuplicate field in response is True---","false", duplicateTx);
		
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/refund-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(refundResponse1, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}
		
		
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity","Regression","ExhaustiveRegression"}, dataProvider = "Checkbalance", priority=0, description="\n 1. Get check balance ")
	public void WalletServiceBalanceSchema(String UIDX) {
		
		RequestGenerator Checkbalance = walletServiceHelper.checkBalance(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$.balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$.login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance : \n\n"+checkbalnce+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/balance-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>>\n " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(balanceResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Regression","ExhaustiveRegression"}, dataProvider = "creditNormalNonMigrated", priority=0, description="\n 1. Get check balance \n2.Credit amount for non migrated user \n3.check the balance again ")
	public void creditNormalSchema(String UIDX, String amount) {
		
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalnce = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
	
		
		System.out.println("\nPrinting Balance before credit : \n\n"+checkbalnce+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		
		RequestGenerator creditPP = walletServiceHelper.creditNormal(UIDX, amount);
		String creditResponse = creditPP.returnresponseasstring();
		String statusCredit= JsonPath.read(creditResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String messageCredit= JsonPath.read(creditResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("\nPrinting creditPP Api Response : \n\n"+creditResponse+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
		
		//String balanceCredit = JsonPath.read(creditResponse, "$.params..amount").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Message doesn't success", message, "success");
		AssertJUnit.assertEquals("Message doesn't success", status, "success");
		
		AssertJUnit.assertEquals("Message doesn't success", statusCredit, "success");
		AssertJUnit.assertEquals("Message doesn't success", messageCredit, "success");
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalanceAfterCredit = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus1 = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("After Credit balance --->\n\n"+checkbalanceAfterCredit);
		//float balanceBeforeCredit= Float.parseFloat(checkbalance);
		float balanceBeforeCredit= Float.parseFloat(checkbalnce);
		float credit_Amount=(Float.parseFloat(amount));
		System.out.println("Amount before Credited/n/n--->" + balanceBeforeCredit);
		System.out.println("Amount to be Credited/n/n--->" + credit_Amount);
		float balanceAfterCredit= Float.parseFloat(checkbalanceAfterCredit);
		System.out.println("Check Amount after credit/n/n--->"+ balanceAfterCredit);
		
		float subtractFromCreditNEw=balanceAfterCredit-credit_Amount;
		System.out.println("Amount after subtraction from : -->"+ subtractFromCreditNEw);
		AssertJUnit.assertEquals("Status is not same", migrateStatus1, "NOT_MIGRATED");
		AssertJUnit.assertEquals("Balance are not equal", subtractFromCreditNEw, balanceBeforeCredit);
		

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/creditnormal-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>>\n " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(creditResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}

		

	}
	
	@Test(groups = { "Smoke", "Sanity","MiniRegression", "Fox7Sanity", "Regression","ExhaustiveRegression"}, dataProvider = "debitNormalMigrated", priority=0, description="\n 1. Get check balance \n2.debit the amount \n3. check the balance again ")
	public void debitNormalMigratedSchema(String UIDX, String amount, String businessProcess, String itemType) {
		RequestGenerator Checkbalance = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse = Checkbalance.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse+"\n");
		String checkbalance = JsonPath.read(balanceResponse, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//int balanceBefore = Integer.valueOf(checkbalance);
		String login = JsonPath.read(balanceResponse, "$..login").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String migrateStatus = JsonPath.read(balanceResponse, "$..migrationStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String message = JsonPath.read(balanceResponse, "$..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String status = JsonPath.read(balanceResponse, "$..status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		String amountbeforedebit = JsonPath.read(balanceResponse, "$.data..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();

		System.out.println("\nPrinting Balance before debit : \n\n"+checkbalance+"\n");
		System.out.println("\nmigrate status : \n\n"+migrateStatus+"\n");
		System.out.println("\nMessage: \n\n"+message+"\n");
		System.out.println("\n status : \n\n"+status+"\n");
		RequestGenerator debitPP = walletServiceHelper.debitNormal(UIDX, amount, businessProcess, itemType);
		String debitResponse = debitPP.returnresponseasstring();
		System.out.println("\nPrinting debit Api Response : \n\n"+debitResponse+"\n");
		//System.out.println("\nPrinting debitAmount Api Response :------> \n\n"+amount+"\n");
		AssertJUnit.assertEquals("Login Doesn't match", login, UIDX);
////		String statusDebit = JsonPath.read(debitResponse, "$.status").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
////		String messagedebit = JsonPath.read(debitResponse, "$.message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
////		AssertJUnit.assertEquals("Migrate status Mismatch", migrateStatus, "NOT_MIGRATED");
////		
//		System.out.println("Debit message\n\n" + messagedebit);
//		System.out.println("Debit status\n\n" + statusDebit);
//		AssertJUnit.assertEquals("Message doesn't success\n\n", messagedebit, "Amount mismatch for NEFT DEBIT");
//		AssertJUnit.assertEquals("Status doesn't success\n\n", statusDebit, "error");
//		//float Debit_amount = 
		//System.out.println("Deducted amount" + Debit_amount);
		
		RequestGenerator Checkbalance1 = walletServiceHelper.migrateStatus(UIDX);
		String balanceResponse1 = Checkbalance1.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting Balance API response : \n\n"+balanceResponse1+"\n");
		String checkbalance2 = JsonPath.read(balanceResponse1, "$..balance").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//float balancebrforedebit=Float.parseFloat(checkbalance);
		System.out.println("Before Debit balance was\n\n" + checkbalance );
		System.out.println("After Debit balance is --->\n\n"+ checkbalance2);
		
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/debitnormal-phonepepps-migrated-schema.txt");
			System.out.println("schema----- >>>>>>>>>\n " + jsonschema);
			List<String> missingNodeList = commonUtil.validateServiceResponseNodesUsingSchemaValidator(debitResponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in WalletService API response",
					CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
}
