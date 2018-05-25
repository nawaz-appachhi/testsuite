package com.myntra.apiTests.dataproviders;

import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.walletServiceHelper.walletHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class walletDP {
	
	APIUtilities apiUtil = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	walletHelper wallethelp= new walletHelper();


	
	@DataProvider
	public Object[][] Checkbalance(ITestContext testContext)
	{
		String[] arr1 = {"72bb7f49.7cb2.4199.8a65.11b2af688ebbBGkeUrtYGy"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	@DataProvider
	public Object[][] Txlogs(ITestContext testContext)
	{
		String[] arr1 = {"7254017f.9d81.4d6b.82c1.bd7326f424018XYbAYiZl2"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	
	
	@DataProvider
	public Object[][] balanceCheckMigrate(ITestContext testContext)
	{
		String[] arr1 = {"72bb7f49.7cb2.4199.8a65.11b2af688ebbBGkeUrtYGy"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] balanceCheckNonMigrate(ITestContext testContext)
	{
		String[] arr1 = {"465505a8.20a8.4931.a422.3952421a7c5eNBiTpaLBNZ"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] NewUserMigrateStatus(ITestContext testContext)
	{
		String[] arr1 = {"7254017f.9d81.4d6b.82c1.bd7326f424018XYbAYiZl2"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] NonMigrateStatus(ITestContext testContext)
	{
		String[] arr1 = {"fb2f7e48.9f9a.4d62.9430.3ebf8a0b3b7abLdmTwJcSV"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] CreditPPS(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		String[] arr1 = {uidx};
		//String creditChecksum = wallethelp.getCreditChecksum(checksum, ppsTransactionID, amount, customerID);
		//String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"5000",uidx,"ORDER"};

		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditNormalNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet809@myntra.com");
	   String amount="50";
	
		String[] arr1 = { uidx,amount};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditNormalMigrated(ITestContext testContext)
	{
		String uidx = getUidx("test57@myntra.com");

	   String amount="5";
	   
		String[] arr1 = { uidx,amount};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditNormalFloating(ITestContext testContext)
	{
		String uidx = getUidx("wallet3456@mayntra.com");
		String amount="50.50";
	
	   
		String[] arr1 = { uidx,amount};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditNegative(ITestContext testContext)
	{
		String uidx = getUidx("test56@myntra.com");
		String amount="-50";
	
	   
		String[] arr1 = { uidx,amount};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitfullNormalAmount(ITestContext testContext)
	{
		String uidx = getUidx("test60@myntra.com");
		//creditCashBack(uidx,"10");
		String amount="10";
		String businessProcess="CASHBACK_TO_BANK_TRANSFER";
		String itemType= "NEFT_ID";
		//creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

//		//String randomString =  generateRandomString();
//		String randomNumber = generateRandomNumber();
//		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, "5000", uidx);
	   
		String[] arr1 = { uidx,amount,businessProcess,itemType};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitNormalMigrated(ITestContext testContext)
	{
		String uidx = getUidx("test57@myntra.com");
		creditCashBack(uidx,"10");
		String amount="10";
		String businessProcess="CASHBACK_TO_BANK_TRANSFER";
		String itemType= "NEFT_ID";
		
		String[] arr1 = { uidx,amount,businessProcess,itemType};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitNormalNegativeNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet809@myntra.com");
		creditCashBack(uidx,"10");
		String amount="-10";
		String businessProcess="CASHBACK_TO_BANK_TRANSFER";
		String itemType= "NEFT_ID";
		
		String[] arr1 = { uidx,amount,businessProcess,itemType};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitNormalNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet810@myntra.com");
		creditCashBack(uidx,"20");
		String amount="20";
		String businessProcess="CASHBACK_TO_BANK_TRANSFER";
		String itemType= "NEFT_ID";
		//creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

//		//String randomString =  generateRandomString();
//		String randomNumber = generateRandomNumber();
//		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, "5000", uidx);
	   
		String[] arr1 = { uidx,amount,businessProcess,itemType};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] debitNormalFloating(ITestContext testContext)
	{

		String uidx = getUidx("wallet920@myntra.com");

		String amount="50.50";
		String businessProcess="CASHBACK_TO_BANK_TRANSFER";
		String itemType= "NEFT_ID";

	   
		String[] arr1 = { uidx,amount,businessProcess,itemType};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditCashbackAmount(ITestContext testContext)
	{

		String uidx = getUidx("test57@myntra.com");
		//creditCashBack(uidx,"5");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString, randomNumber,"500",uidx);
		String[] arr1 = { creditChecksum,randomNumber, randomString, randomNumber, "500",uidx};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	

	
	@DataProvider
	public Object[][] creditCashbackNegativeBalance(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"100");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,"-500",uidx);
		String[] arr1 = { creditChecksum,randomNumber, randomString,randomNumber, "-500",uidx};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditCashbackNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet810@myntra.com");//non migrated user
		creditCashBack(uidx,"10");
	
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
		String[] arr1 = { creditChecksum,randomNumber, randomString,randomNumber, "500",uidx};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] creditPPSLimit(ITestContext testContext)
	{
		
		String uidx = getUidx("wallet906@myntra.com");//more than 10k
		creditCashBack(uidx,"100");
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,"1000000",uidx);
		String[] arr1 = { creditChecksum,randomNumber, randomString,randomNumber, "1000000",uidx};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
//	@DataProvider
//	public Object[][] creditPPSFloat(ITestContext testContext)
//	{
//		String uidx = "034056da.2431.44d4.bd70.b83752c0f7086kWYVlYs0V";
//		//creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");
//		double amount1=100.32;
//		//String amount=amount1+"";
//		
//
//		String randomString =  generateRandomString();
//		String randomNumber = generateRandomNumber();
//		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,amount1,uidx);
//		String[] arr1 = { creditChecksum,randomNumber, randomString,randomNumber, amount1+"",uidx};
//
//
//		Object[][] dataSet = new Object[][] { arr1};
//		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
//	}
	
	@DataProvider
	public Object[][] debitCashbackAmount(ITestContext testContext)
	{
		String uidx = getUidx("test58@myntra.com");//more than 10k
		creditCashBack(uidx,"500");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
//		String randomString1 =  generateRandomString();
//		String randomNumber1 = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
		String[] arr1 = {debitChecksum,randomNumber, randomString,randomNumber, "500",uidx};
		System.out.println("debit checksum is:-->\n"+debitChecksum);


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitCashbackAmountSchema(ITestContext testContext)
	{
		String uidx = getUidx("wallet805@myntra.com");//more than 10k
		creditCashBack(uidx,"10");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();

		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
		String[] arr1 = {debitChecksum,randomNumber, randomString,randomNumber, "500",uidx};
		System.out.println("debit checksum is:-->\n"+debitChecksum);


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitCashbackFloating(ITestContext testContext)
	{
		String uidx = getUidx("wallet805@myntra.com");//more than 10k
		creditCashBack(uidx,"10");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
//		String randomString1 =  generateRandomString();
//		String randomNumber1 = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
		String[] arr1 = {debitChecksum,randomNumber, randomString,randomNumber, "500",uidx};
		System.out.println("debit checksum is:-->"+debitChecksum);


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
//	@DataProvider
//	public Object[][] debitCashbackAmountMoreThanBalance(ITestContext testContext)
//	{
//	   String uidx = "5d02c0dd.a4bb.48c5.985b.b3f6001041afZr71qRHTU3";
//		//creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");
//
//		String randomString =  generateRandomString();
//		String randomNumber = generateRandomNumber();
//		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, "50000", uidx);
//		String[] arr1 = { debitChecksum,randomString,"50000",uidx};
//
//
//		Object[][] dataSet = new Object[][] { arr1};
//		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
//	}
//	
	
	@DataProvider
	public Object[][] debitCashbackNegativeBalance(ITestContext testContext)
	{
		String uidx = getUidx("test56@myntra.com");//more than 10k
		//creditCashBack(uidx,"100");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
//		String randomString1 =  generateRandomString();
//		String randomNumber1 = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"-500",uidx);
		String[] arr1 = {debitChecksum,randomNumber, randomString,randomNumber, "-500",uidx};
		System.out.println("debit checksum is:-->"+debitChecksum);


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitCashbackNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet809@myntra.com");//more than 10k
		creditCashBack(uidx,"10");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
//		String randomString1 =  generateRandomString();
//		String randomNumber1 = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
		String[] arr1 = {debitChecksum,randomNumber, randomString,randomNumber, "500",uidx};
		System.out.println("debit checksum is:-->"+debitChecksum);


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] voidCbMigrated(ITestContext testContext)
	{

		String uidx = getUidx("wallet893@myntra.com");
//		creditCashBack(uidx,"5");
		
		

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String voidChecksum = wallethelp.getVoidChecksum(randomString1, randomString2,randomNumber1,clientTransactionID );
		
		String[] arr2 = { voidChecksum,randomString2,randomNumber1,uidx,clientTransactionID};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);

	}
	
	@DataProvider
	public Object[][] voidCbMigratedSchema(ITestContext testContext)
	{

		String uidx = getUidx("test58@myntra.com");
		creditCashBack(uidx,"5");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String voidChecksum = wallethelp.getVoidChecksum(randomString1, randomString2,randomNumber1,clientTransactionID );
		
		String[] arr2 = { voidChecksum,randomString2,randomNumber1,uidx,clientTransactionID};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);

	}
	
	
	@DataProvider
	public Object[][] voidAfterRefund(ITestContext testContext)
	{

		String uidx = getUidx("wallet807@myntra.com");
		creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber1, "500",  uidx);
		String refundStatus = refundPPS2(refundChecksum, randomNumber1,randomString2, clientTransactionID, randomNumber1, "500",  uidx);
		System.out.println("Refund Status\n "+ refundStatus);
		
		String randomString3 =  generateRandomString();
		String randomNumber2 = generateRandomNumber();
		String randomString4 = generateRandomString();
		String voidChecksum = wallethelp.getVoidChecksum(randomString1, randomString4,randomNumber2,clientTransactionID );
		
		String[] arr3 = { voidChecksum,randomString4,randomNumber2,uidx,clientTransactionID, randomString2, "500"};


		Object[][] dataSet = new Object[][] { arr3};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
		
	}
	
	
	@DataProvider
	public Object[][] voidCreditClientTX(ITestContext testContext)
	{

		String uidx = getUidx("wallet920@myntra.com");
		//creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getCreditClientTransaction(creditChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String voidChecksum = wallethelp.getVoidChecksum(randomString1, randomString2,randomNumber1,clientTransactionID );
		
		String[] arr2 = { voidChecksum,randomString2,randomNumber1,uidx,clientTransactionID};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
		
		
		
	}
	
	@DataProvider
	public Object[][] refundCashbackAmount(ITestContext testContext)
	{
		String uidx = getUidx("wallet808@myntra.com");
		creditCashBack(uidx,"5");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);
//		String[] arr1 = { debitChecksum,randomNumber,"500",uidx};
//		
//		String randomString1 =  generateRandomString();
//		String randomNumber1 = generateRandomNumber();
		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber1, "500",  uidx);
		String[] arr2 = { refundChecksum,randomNumber1,randomString2,clientTransactionID,randomNumber1,"500",uidx};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] refundCashbackAmountSchema(ITestContext testContext)
	{
		String uidx = getUidx("wallet808@myntra.com");
		creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber1, "500",  uidx);
		String[] arr2 = { refundChecksum,randomNumber1,randomString2,clientTransactionID,randomNumber1,"500",uidx};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] refundCashbackAmountCredit(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getCreditClientTransaction(creditChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber, "500",  uidx);
		String[] arr2 = { refundChecksum,randomNumber1,randomString2,clientTransactionID,randomNumber,"500",uidx};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] refundCashbackAmountPartial(ITestContext testContext)
	{
		String uidx = getUidx("wallet805@myntra.com");
		creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber, "200",  uidx);
		String[] arr2 = { refundChecksum,randomNumber1,randomString2,clientTransactionID,randomNumber,"200",uidx};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] refundCashbackTwice(ITestContext testContext)
	{
		String uidx = getUidx("wallet807@myntra.com");
		creditCashBack(uidx,"5");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = wallethelp.getDebitChecksum(randomString, randomNumber, randomString,randomNumber,"500",uidx);

		String clientTransactionID= wallethelp.getDebitClientTransaction(debitChecksum ,randomNumber,randomString,randomNumber, "500",uidx);

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String randomString2 = generateRandomString();
		String refundChecksum = wallethelp.getRefundChecksum(randomString1, randomNumber1,randomString2, clientTransactionID, randomNumber, "200",  uidx);
		String[] arr2 = { refundChecksum,randomNumber1,randomString2,clientTransactionID,randomNumber,"200",uidx};


		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] MigratedUserStatus(ITestContext testContext)
	{
		String[] arr1 = {"f560ef4a.22c1.485f.926b.d9e0aa7f1899jCa5Y08HMQ"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] CreditPhonePe(ITestContext testContext)
	{
		String[] arr1 = {"f560ef4a.22c1.485f.926b.d9e0aa7f1899jCa5Y08HMQ","10"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] DebitPhonePe(ITestContext testContext)
	{
		String[] arr1 = {"f560ef4a.22c1.485f.926b.d9e0aa7f1899jCa5Y08HMQ","10"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] PhonePebalance(ITestContext testContext)
	{
		String[] arr1 = {"f560ef4a.22c1.485f.926b.d9e0aa7f1899jCa5Y08HMQ"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	

	@DataProvider
	public Object[][] CreditPhonePeSide(ITestContext testContext)
	{
		String randomNo= generateRandomString();
		String[] arr1 = {randomNo,"72bb7f49.7cb2.4199.8a65.11b2af688ebbBGkeUrtYGy","10"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] DebitPhonePeSide(ITestContext testContext)
	{
		String randomNo= generateRandomString();
		String[] arr1 = {randomNo,"72bb7f49.7cb2.4199.8a65.11b2af688ebbBGkeUrtYGy","10"};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] backTsourcePhonePeSide(ITestContext testContext)
	{
		String randomNo= generateRandomString();
		String transactionId =generateRandomString();
		String[] arr1 = {randomNo,"72bb7f49.7cb2.4199.8a65.11b2af688ebbBGkeUrtYGy","10",transactionId};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] cashbackMigratedStatus(ITestContext testContext)
	{
		String[] arr1= {"e9b87a4f.a6fa.485f.a040.fc1baa06e7aaJjIjlqTBdF"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
		
	}
	
	
	@DataProvider
	public Object[][] walletLogsWithOffsetValue(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"5");
		
		String limit="20";
		String offSet="1";
		
		String[] arr1 = {uidx, limit, offSet};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] walletLogsNormal(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"5");
		
		String limit="20";
		String offSet="0";
		
		String[] arr1 = {uidx, limit, offSet};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] walletLogsWithZeroOffsetValue(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"5");
		
		String limit="0";
		String offSet="0";
		
		String[] arr1 = {uidx, limit, offSet};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] creditSuggestDP(ITestContext testContext)
	{
		String uidx="d2999929.7791.41ff.854a.bf4c9093f78836kOFYkaGS";
		String ph=    "\"PHONEPE\"" ;
		
//		\\\"Nike\"\\
		String[] arr1 = {uidx,  ph};
//		"\\\"PHONEPE\\\""
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] debitSuggestDP(ITestContext testContext)
	{
		String uidx = getUidx("wallet906@myntra.com");
		creditCashBack(uidx,"5");
		
		
		
		
		String[] arr1 = {uidx};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] debitSuggestDPNonMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet917@myntra.com");
		
		
		String[] arr1 = {uidx};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] debitSuggestDPNoCbNotMigrated(ITestContext testContext)
	{
		String uidx = getUidx("wallet918@myntra.com");
		//creditCashBack(uidx,"5");
		
		
		String[] arr1 = {uidx};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] creditCashbackAmountSchema(ITestContext testContext)
	{
		String uidx = getUidx("test56@myntra.com");
//		creditCashBack(uidx,"5");
		
		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String creditChecksum = wallethelp.getCreditChecksum(randomString, randomNumber, randomString, randomNumber,"500",uidx);
		String[] arr1 = { creditChecksum,randomNumber, randomString, randomNumber, "500",uidx};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] withDraw(ITestContext testContext)
	{
		
		String emailId="wallet132@myntra.com";
		String uidx = getUidx(emailId);
		creditCashBack(uidx,"5");
		
		
		
		String[] arr1 = {uidx, emailId};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	

	@DataProvider
	public Object[][] withDrawNON(ITestContext testContext)
	{
		String uidx="721ab213.1243.4b79.b53e.f15b1a4ba4bcUvE0b0hbFb";
		String emailId="wallet650@myntra.com";
		
		
		
		String[] arr1 = {uidx, emailId};
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
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
	
	public String refundPPS2(String checksum, String ppsId, String ppsTransactionID, String clientTxID, String orderID, String amount, String customerId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.REFUNDWALLETPPS,init.Configurations,new String[]{checksum,ppsId,ppsTransactionID,clientTxID,orderID,amount,customerId},PayloadType.JSON,PayloadType.JSON);
		System.out.println("PAyload refund \n" + service.Payload );

		//HashMap getParam1 = new HashMap();
		//getParam1.put("user", "manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		//RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);

		String jsonRes = req.respvalidate.returnresponseasstring();
		String transcationId = JsonPath.read(jsonRes, "$.params.clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+transcationId);
		String txStatus = JsonPath.read(jsonRes, "$.params.txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+txStatus);
		
		return txStatus;

		
		
	}

	public String getUidx(String loginId)

	{ 
		MyntraService service = Myntra.getService(ServiceType.PORTAL_IDEA,
				APINAME.GETUIDX, init.Configurations, PayloadType.JSON,new String[]{loginId},PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		String uidx = JsonPath.read(jsonRes,"$entry.uidx");
		System.out.println("UIDX------"+ uidx);
		return uidx;
	}
	
	
	public void creditCashBack(String UIDX, String amount)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_WALLETSERVICE, APINAME.NORMALCREDIT,init.Configurations,new String[]{UIDX,amount},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "ramakrishna.g@myntra.com");
		System.out.println("PAyload credit \n" + service.Payload );
		RequestGenerator req = new RequestGenerator(service,getParam1);
		//log.info(service.URL);
		System.out.println("PAyload credit response \n" + req.respvalidate.returnresponseasstring() );
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				req.response.getStatus());
	
	}
}
