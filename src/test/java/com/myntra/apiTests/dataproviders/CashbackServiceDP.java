package com.myntra.apiTests.dataproviders;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

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

public class CashbackServiceDP {
	
	APIUtilities apiUtil = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	
	
	@DataProvider
	public Object[][] debitcashbackFromEarnedAmount(ITestContext testContext)
	{
		String uidx = getUidx("cashbackservice@myntra.com");
		creditCashBack(uidx,"100","0","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString,randomNumber, "5000",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"5000",uidx,"ORDER"};


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] debitcashbackFromEarnedAmountWithFloat(ITestContext testContext)
	{
		String uidx = getUidx("floatcashbackpoint@myntra.com");
		creditCashBack(uidx,"100","0","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString,randomNumber, "1201",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"1201",uidx,"ORDER"};

		
		
		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String debitChecksum1 = getDebitChecksum(randomString1, randomNumber1, randomString1,randomNumber1, "2587",uidx,"ORDER");
		String[] arr2 = { debitChecksum1,randomNumber1,randomString1,randomNumber1,"2587",uidx,"ORDER"};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public Object[][] debitcashbackFromStoredAmount(ITestContext testContext)
	{
		String uidx = getUidx("cashbackservice@myntra.com");
		creditCashBack(uidx,"0","100","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString, randomNumber, "10000",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"10000",uidx,"ORDER"};
//		String[] arr2 = { generateRandomString(),generateRandomNumber(),generateRandomString(),generateRandomNumber(),"5000","lyaltytestingnew@myntra.com","ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] debitcashbackFromStoredAmountAndEarned(ITestContext testContext)
	{
		String uidx = getUidx("cashbackservice@myntra.com");
		String stored = getStoredCashBackRequest(uidx);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;	
		String storedbalanceAfter_string = String.valueOf(stored_Int*100);



//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
			
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		creditCashBack(uidx,"100","100","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString,randomNumber, "20000",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"20000",uidx,"ORDER"};
//		String[] arr2 = { generateRandomString(),generateRandomNumber(),generateRandomString(),generateRandomNumber(),"5000","lyaltytestingnew@myntra.com","ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] debitcashbackMoreThanActiveCashBack(ITestContext testContext)
	{
		String uidx = getUidx("cashbackservice@myntra.com");
		creditCashBack(uidx,"10","0","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString,randomNumber, "10000000",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"10000000",uidx,"ORDER"};
//		String[] arr2 = { generateRandomString(),generateRandomNumber(),generateRandomString(),generateRandomNumber(),"5000","lyaltytestingnew@myntra.com","ORDER"};
		creditCashBack(uidx,"0","10","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String uidx1 = getUidx("cashbackservice1@myntra.com");
		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String debitChecksum1= getDebitChecksum(randomString1, randomNumber1, randomString1,randomNumber1, "3000000",uidx1,"ORDER");
		String[] arr2 = { debitChecksum1,randomNumber1,randomString1,randomNumber1,"3000000",uidx1,"ORDER"};
		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	

	@DataProvider
	public Object[][] debitcashbackNegativeCashBack(ITestContext testContext)
	{
		String uidx = getUidx("cashbackservice@myntra.com");
		creditCashBack(uidx,"100","0","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString =  generateRandomString();
		String randomNumber = generateRandomNumber();
		String debitChecksum = getDebitChecksum(randomString, randomNumber, randomString,randomNumber, "-100",uidx,"ORDER");
		String[] arr1 = { debitChecksum,randomNumber,randomString,randomNumber,"-100",uidx,"ORDER"};
		String uidx1 = getUidx("cashbackservice1@myntra.com");
		creditCashBack(uidx1,"200","0","0","description","CASHBACK_TO_BANK_TRANSFER","NEFT_ID", "001","manishkumar.gupta@myntra.com");

		String randomString1 =  generateRandomString();
		String randomNumber1 = generateRandomNumber();
		String debitChecksum1= getDebitChecksum(randomString1, randomNumber1, randomString1,randomNumber1, "-5000",uidx1,"ORDER");
		String[] arr2 = { debitChecksum1,randomNumber1,randomString1,randomNumber1,"-5000",uidx1,"ORDER"};
		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	public void creditCashBack(String param1, String param2,
			String param3, String param4, String param5, String param6,
			String param7, String param8,String param9) {
		// System.out.println("Before: "+balanceBefoe);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CREDITCASHBACK,
				init.Configurations, new String[] { param1, param2, param3,
						param4, param5, param6, param7,param8});
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("user", param9);
		RequestGenerator req = new RequestGenerator(service, hm);
		
		System.out.println("Response IN credit---"+ req.returnresponseasstring());
		String earnedamt = getEarnedCashBackRequest(param1);
		System.out.println("earned amt after Credit =========== " +earnedamt );
		String Storedamt = getStoredCashBackRequest(param1);
		System.out.println("stored amt after Credit ===========" +Storedamt );
		
		
	}
	
	
	@DataProvider
	public Object[][] refundCashbackDP(ITestContext testContext)
	{
		String uidx = getUidx("test0123@myntra.com");
		creditCashBack(uidx,"0","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"1000",uidx,"ORDER");
		
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000","test0123@myntra.com","ORDER");
//		String clientTransactionID1= getClientTransactionID1(checksum3,random_number3 ,random_String3 ,random_number3,"5000","test0123@myntra.com","ORDER");

		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"1000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number,"1000",uidx,"ORDER"};
//		System.out.println("Transaction Id ---- " +clientTransactionID);
//		String random_number2 = generateRandomNumber();
//		String random_String2 = generateRandomString();
//		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"5000","lyaltytestingnew@myntra.com","ORDER");
//		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number1,"5000","lyaltytestingnew@myntra.com","ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] refundFloatCashbackToStoredAcountDP(ITestContext testContext)
	{
		String uidx = getUidx("floatcashbackpoint@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"1201",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"1201",uidx,"ORDER");
		
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000","test0123@myntra.com","ORDER");
//		String clientTransactionID1= getClientTransactionID1(checksum3,random_number3 ,random_String3 ,random_number3,"5000","test0123@myntra.com","ORDER");

		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"1201",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number,"1201",uidx,"ORDER"};
		
		
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"1201",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"1201",uidx,"ORDER");
		
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"1200",uidx,"ORDER");
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number1,"1200",uidx,"ORDER"};

		
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getRefundChecksum(random_String4,random_number4,random_String4,clientTransactionID1,random_number2,"1",uidx,"ORDER");
		String[] arr3 = { checksum4,random_number4,random_String4,clientTransactionID1,random_number4,"1",uidx,"ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	@DataProvider
	public Object[][] refundCashbackToStoredAcountDP(ITestContext testContext)
	{
		String uidx = getUidx("manish123@myntra.com");
		creditCashBack(uidx,"0","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"10000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number1,"10000",uidx,"ORDER"};
		
		
		creditCashBack(uidx,"0","50","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"5000",uidx,"ORDER");
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number2,"5000",uidx,"ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackWithInvalidUser(ITestContext testContext)
	{
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"50","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"5000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"5000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String uidx1 = getUidx("manishkumar.gupta@myntra.com");
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"5000",uidx1,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number1,"5000",uidx1,"ORDER"};
		
		String uidx2 = getUidx("manish123@myntra.com");
		creditCashBack(uidx2,"30","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"3000",uidx2,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"3000",uidx2,"ORDER");
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"3000",uidx,"ORDER");
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number2,"3000",uidx,"ORDER"};


		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackWithInvalidClienTXN(ITestContext testContext)
	{
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"50","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"5000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"5000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,random_String1,random_number1,"5000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,random_String1,random_number1,"5000",uidx,"ORDER"};
		
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackToStoredAndEarnedAcountDP(ITestContext testContext)
	{ 
		String uidx = getUidx("cashback3@myntra.com");
		String stored = getStoredCashBackRequest(uidx);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;	
		String storedbalanceAfter_string = String.valueOf(stored_Int*100);



//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
		// Empty stored amount	
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		
		creditCashBack(uidx,"100","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"20000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"20000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"20000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number1,"20000",uidx,"ORDER"};
		
		
		
		
//		creditCashBack("cashback3@myntra.com","200","200","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000","cashback3@myntra.com","ORDER");
//		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000","cashback3@myntra.com","ORDER");
//		System.out.println("Transaction Id ---- " +clientTransactionID);
//		
//		
//		
//		String random_number2 = generateRandomNumber();
//		String random_String2 = generateRandomString();
//		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"5000","cashback3@myntra.com","ORDER");
//		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number1,"5000","cashback3@myntra.com","ORDER"};
		
		


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackLessthanToStoredAndEarnedAcountDP(ITestContext testContext)
	{
		String uidx = getUidx("newcashback1@myntra.com");
		String stored = getStoredCashBackRequest(uidx);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;	
		String storedbalanceAfter_string = String.valueOf(stored_Int*100);



//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
			
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		
		creditCashBack(uidx,"300","300","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"50000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"50000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"30000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number1,"30000",uidx,"ORDER"};
		
		
		creditCashBack(uidx,"100","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"60000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"60000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"40000",uidx,"ORDER");
		
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number2,"40000",uidx,"ORDER"};
		


		


		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackMoreThanDebit(ITestContext testContext)
	{
		String uidx = getUidx("refundmorethandebit@myntra.com");
		String stored = getStoredCashBackRequest(uidx);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;	
		String storedbalanceAfter_string = String.valueOf(stored_Int*100);
		




//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
			
//		String random_number4 = generateRandomNumber();
//		String random_String4 = generateRandomString();
//		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,"refundmorethandebit@myntra.com","ORDER");
//		System.out.println("Debit checksum :: :::: " +checksum4 );
//		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,"refundmorethandebit@myntra.com","ORDER");
		
		creditCashBack(uidx,"500","500","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"40000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"40000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"90000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number1,"90000",uidx,"ORDER"};
		
		
		creditCashBack(uidx,"200","200","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"20000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"20000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID1,random_number2,"70000",uidx,"ORDER");
		
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID1,random_number2,"70000",uidx,"ORDER"};
		


		


		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] refundCashbackwithSameClienTXn(ITestContext testContext)
	{
		String uidx = getUidx("refundmorethandebit@myntra.com");
		String stored = getStoredCashBackRequest(uidx);
		int stored_Int =Math.round(Float.parseFloat(stored)) ;	
		String storedbalanceAfter_string = String.valueOf(stored_Int*100);



//		int stored_int =Integer.parseInt(stored);
		System.out.println("stored----->>" + stored);
			
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getDebitChecksum(random_String4,random_number4,random_String4,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		getDebitClientTransaction(checksum4,random_number4 ,random_String4 ,random_number4,storedbalanceAfter_string,uidx,"ORDER");
		
		creditCashBack(uidx,"300","300","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"30000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"30000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"30000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number,"30000",uidx,"ORDER"};
		
		
//		creditCashBack("refundmorethandebit@myntra.com","100","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");
//
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
//		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID,random_number2,"10000",uidx,"ORDER");
		
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID,random_number2,"10000",uidx,"ORDER"};
		


		


		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackWithSameCLientTXNAndDifferentPpsID(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"1000","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"60000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"60000",uidx,"ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"20000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number1,random_String1,clientTransactionID,random_number,"20000",uidx,"ORDER"};
		
		
//		creditCashBack("refundmorethandebit@myntra.com","100","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");
//
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
//		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
		


		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String2,random_number2,random_String2,clientTransactionID,random_number2,"20000",uidx,"ORDER");
		
		String[] arr2 = { checksum2,random_number2,random_String2,clientTransactionID,random_number2,"20000",uidx,"ORDER"};
		
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getRefundChecksum(random_String3,random_number3,random_String3,clientTransactionID,random_number3,"20000",uidx,"ORDER");
		
		String[] arr3 = { checksum3,random_number3,random_String3,clientTransactionID,random_number3,"20000",uidx,"ORDER"};
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getRefundChecksum(random_String4,random_number4,random_String4,clientTransactionID,random_number4,"20000",uidx,"ORDER");
		
		String[] arr4 = { checksum4,random_number4,random_String4,clientTransactionID,random_number4,"20000",uidx,"ORDER"};
		


		


		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}
	
	
	
	
	
	
	@DataProvider
	public Object[][] refundCashbackWithVoidedClienTXN(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String1, random_number1, random_String1, clientTransactionID);
		CreateVoidCashbackTransaction(voidchecksum, random_number1, random_String1, clientTransactionID, uidx);
		System.out.println("Transaction Id ---- " +clientTransactionID);
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String3,random_number3,random_String3,clientTransactionID,random_number3,"10000",uidx,"ORDER");
		
		String[] arr1 = { checksum1,random_number3,random_String3,clientTransactionID,random_number3,"10000",uidx,"ORDER"};
		
		
//		creditCashBack("refundmorethandebit@myntra.com","100","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");
//
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
//		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"20000","refundmorethandebit@myntra.com","ORDER");
		
		


		


		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] voidTransactionDP(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"1500",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"1500",uidx,"ORDER");
		

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String1, random_number1, random_String1, clientTransactionID);		
		
		String[] arr1 = { voidchecksum,random_number1,random_String1,clientTransactionID,uidx};
		
		
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getDebitChecksum(random_String2,random_number2,random_String2,random_number2,"1201",uidx,"ORDER");
		String clientTransactionID2= getDebitClientTransaction(checksum2,random_number2 ,random_String2 ,random_number2,"1201",uidx,"ORDER");
		

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidchecksum3 = getvoidAndStatusChecksum(random_String3, random_number3, random_String3, clientTransactionID2);		
		
		String[] arr3 = { voidchecksum3,random_number3,random_String3,clientTransactionID2,uidx};
		

		Object[][] dataSet = new Object[][] { arr1,arr3};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] voidTransactionMultipleWithDebitTxn(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String1, random_number1, random_String1, clientTransactionID);		
		
		String[] arr1 = { voidchecksum,random_number1,random_String1,clientTransactionID,uidx};
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidchecksum2 = getvoidAndStatusChecksum(random_String3, random_number3, random_String3, clientTransactionID);		
		
		String[] arr2 = { voidchecksum2,random_number3,random_String3,clientTransactionID,uidx};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] VoidTransactionOnDebitTransactionIDAfterrefund(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"1000",uidx,"ORDER");
		CreateCashbackRefund(checksum1, random_number1, random_String1, clientTransactionID, random_number1,"10000",uidx,"ORDER");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, clientTransactionID);		
		
		String[] arr1 = { voidchecksum,random_number2,random_String2,clientTransactionID,uidx};
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidchecksum2 = getvoidAndStatusChecksum(random_String3, random_number3, random_String3, clientTransactionID);		
		
		String[] arr2 = { voidchecksum2,random_number3,random_String3,clientTransactionID,uidx};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] VoidTransactionOnRefundedTransactionID(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"10000",uidx,"ORDER");
		System.out.println("chekcum for refund -----"  + checksum1);
		
		String RefundTxn = CreateCashbackRefund(checksum1, random_number1, random_String1, clientTransactionID, random_number1,"10000",uidx,"ORDER");
		System.out.println("Refund token--==-=-=-= " + RefundTxn);
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, RefundTxn);		
		
		String[] arr1 = { voidchecksum,random_number2,random_String2,RefundTxn,uidx};
		
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
		
		
		
		System.out.println("Transaction Id ---- " +clientTransactionID);
		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String checksum4 = getRefundChecksum(random_String4,random_number4,random_String4,clientTransactionID1,random_number4,"5000",uidx,"ORDER");
		String RefundTxn2 = CreateCashbackRefund(checksum4, random_number4, random_String4, clientTransactionID1, random_number4,"5000",uidx,"ORDER");
		
		
		
		
		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String voidchecksum2 = getvoidAndStatusChecksum(random_String5, random_number5, random_String5, RefundTxn2);		

		String[] arr2 = { voidchecksum2,random_number5,random_String5,RefundTxn2,uidx};

		
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] GetStatusOnDebitDP(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		
		

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String statuschecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, clientTransactionID);		
		
		String[] arr1 = { statuschecksum,random_number2,random_String2,clientTransactionID,uidx,"10000"};
		
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
		
	
		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String statusChecksum2 = getvoidAndStatusChecksum(random_String5, random_number5, random_String5, clientTransactionID1);		

		String[] arr2 = { statusChecksum2,random_number5,random_String5,clientTransactionID1,uidx,"5000"};

		
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	@DataProvider
	public Object[][] GetStatusINvalidClientTxnDP(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		
		

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String statuschecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, random_number2);		
		
		String[] arr1 = { statuschecksum,random_number2,random_String2,random_number2,uidx};
		
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
		
	
		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String statusChecksum2 = getvoidAndStatusChecksum(random_String5, random_number5, random_String5, random_number3);		

		String[] arr2 = { statusChecksum2,random_number5,random_String5,random_number3,uidx};

		
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] GetStatusOnRefundedDP(ITestContext testContext)
	{
		
		String uidx = getUidx("transactioncashback@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String1,random_number1,random_String1,clientTransactionID,random_number1,"10000",uidx,"ORDER");
		String refundTxn1 =CreateCashbackRefund(checksum1, random_number1, random_String1, clientTransactionID, random_number1,"10000",uidx,"ORDER");
		

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String statuschecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, refundTxn1);		
		
		String[] arr1 = { statuschecksum,random_number2,random_String2,refundTxn1,uidx,"10000"};
		
		
		
		
		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
		
		
		String random_number6 = generateRandomNumber();
		String random_String6 = generateRandomString();
		String checksum6 = getRefundChecksum(random_String6,random_number6,random_String6,clientTransactionID1,random_number6,"5000",uidx,"ORDER");
		String refundTxn= CreateCashbackRefund(checksum6, random_number6, random_String6, clientTransactionID1, random_number6,"5000",uidx,"ORDER");
	
		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String statusChecksum2 = getvoidAndStatusChecksum(random_String5, random_number5, random_String5, refundTxn);		

		String[] arr2 = { statusChecksum2,random_number5,random_String5,refundTxn,uidx,"5000"};

		
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	@DataProvider
	public Object[][] GetStatusOnVoidedDP(ITestContext testContext)
	{
		
		String uidx = getUidx("test58@myntra.com");
		creditCashBack(uidx,"100","0","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000",uidx,"ORDER");
		String clientTransactionID= getDebitClientTransaction(checksum,random_number ,random_String ,random_number,"10000",uidx,"ORDER");
		
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String voidchecksum = getvoidAndStatusChecksum(random_String1, random_number1, random_String1, clientTransactionID);
		String voidTransaction= CreateVoidCashbackTransaction(voidchecksum, random_number1, random_String1, clientTransactionID,uidx);
		

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String statuschecksum = getvoidAndStatusChecksum(random_String2, random_number2, random_String2, voidTransaction);		
		
		String[] arr1 = { statuschecksum,random_number2,random_String2,voidTransaction,uidx};
		
		
		
		
//		String random_number3 = generateRandomNumber();
//		String random_String3 = generateRandomString();
//		String checksum3 = getDebitChecksum(random_String3,random_number3,random_String3,random_number3,"5000",uidx,"ORDER");
//		String clientTransactionID1= getDebitClientTransaction(checksum3,random_number3 ,random_String3 ,random_number3,"5000",uidx,"ORDER");
//		
//		
//		
//		String random_number4 = generateRandomNumber();
//		String random_String4 = generateRandomString();
//		String voidchecksum4 = getvoidAndStatusChecksum(random_String4, random_number4, random_String4, clientTransactionID1);
//		String voidTransaction1= CreateVoidCashbackTransaction(voidchecksum4, random_number4, random_String4, clientTransactionID1, uidx);
//		
//		
//		String random_number5 = generateRandomNumber();
//		String random_String5 = generateRandomString();
//		String statusChecksum2 = getvoidAndStatusChecksum(random_String5, random_number5, random_String5, voidTransaction1);		
//
//		String[] arr2 = { statusChecksum2,random_number5,random_String5,voidTransaction1,uidx};

		
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	private String getEarnedCashBackRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Check Balance Response---- >>>" +req.returnresponseasstring() );
		String response = req.returnresponseasstring();
		String earnedAmt = JsonPath.read(response, "$.earnedCreditbalance").toString().replace("[", "").replace("]", "").trim();

		
		return earnedAmt;

		
	}
	
	private String getStoredCashBackRequest(String userID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_MYNTCASHSERVICE, APINAME.CHECKBALANCE,init.Configurations, PayloadType.JSON, new String[]{userID}, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Check Balance Response---- >>>" +req.returnresponseasstring() );
		String response = req.returnresponseasstring();
		String storedAmt = JsonPath.read(response, "$.storeCreditBalance").toString().replace("[", "").replace("]", "").trim();

		return storedAmt;

		
	}
	
	
public String getDebitClientTransaction(String checksum,String ppsID ,String ppsTransactionID ,String orderId,String amount,String customerID,String ppsType) {
		
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.DEBITCASHBACKPPS,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Debit Url------>>>>> "+service.URL);
		System.out.println("DEBit payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String debitamount = getEarnedCashBackRequest(customerID);
		
	System.out.println("defit -==========================="   + debitamount);
		return clientId;
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
	
	private String getRefundChecksum(String checksum,String ppsID ,String ppsTransactionID ,String transactionID,String orderId,String amount,String customerID,String ppsType)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUNDCASHBACK,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,transactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Checksum Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("refund checksum --- ? ?? " + response);
		return response;
	}
	
	
	
	private String CreateVoidCashbackTransaction(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String customerID) {
		
		
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.VOIDCASHBACKTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+msg1);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		String transcationId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+transcationId);
		
		return transcationId;
		
	}
	
	private String getvoidAndStatusChecksum(String checksum,String ppsID ,String ppsTransactionID,String client)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMVOIDCASHBACK,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,client}, PayloadType.JSON,PayloadType.XML);
		System.out.println("Url------>>>>> "+service.URL);
		System.out.println("payload------>>>>> "+service.Payload);

		RequestGenerator req = new RequestGenerator(service);		
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}

	private String CreateCashbackRefund(String checksum,String ppsID ,String ppsTransactionID ,String clientTransactionID,String orderId,String amount,String customerID,String ppsType) {
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CASHBACKSERVICE, APINAME.REFUNDCASHBACKPPS,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,clientTransactionID,orderId,amount,customerID,ppsType}, PayloadType.JSON,PayloadType.JSON);
		RequestGenerator rs = new RequestGenerator(service);
		System.out.println("responsr ------ >>>" + rs.returnresponseasstring());
		System.out.println("Refund Payload =========>"  + service.Payload);
		System.out.println("Refund Url-------->>> " + service.URL);

		String jsonRes = rs.respvalidate.returnresponseasstring();
		String transcationId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> "+transcationId);
		
		return transcationId;

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);

	}
		
		// Checksum Verification db
		
		@DataProvider
		public Object[][] vDebitChckSm(ITestContext testContext)
		{
			
			String uidx = getUidx("manishkumar.gupta@myntra.com");
			
			String random_string = generateRandomString();
			String random_number = generateRandomNumber();
			
			String[] arr1 = {random_string, random_number, random_string, random_number, "110", "ORDER", uidx, "GIFTBIG", "1991200030000374", "757960", "2000"};
			
			Object[][] dataSet = new Object[][] { arr1};
			return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1,1);
		}
		

		@DataProvider
		public Object[][] vDebitChckSm1(ITestContext testContext)
		{
			String uidx = getUidx("test0123@myntra.com");
			creditCashBack(uidx,"0","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

			String randomString =  generateRandomString();
			String randomNumber = generateRandomNumber();
			String uidx1 = getUidx("cashbackservice@myntra.com");
			String[] arr1 = { randomString,randomNumber,randomString,randomNumber,"5000",uidx1,"ORDER"};


			Object[][] dataSet = new Object[][] { arr1};
			return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
		}
		
		@DataProvider
		public Object[][] vRefundCashback(ITestContext testContext)
		{
			String uidx = getUidx("test0123@myntra.com");
			creditCashBack(uidx,"0","100","0","description","GOOD_WILL", "GOOD_WILL", "001","manishkumar.gupta@myntra.com");

			String random_number = generateRandomNumber();
			String random_String = generateRandomString();
			//String checksum = getDebitChecksum(random_String,random_number,random_String,random_number,"10000","test0123@myntra.com","ORDER");
			String clientTransactionID= getDebitClientTransaction(random_String,random_number ,random_String ,random_number,"1000",uidx,"ORDER");
			

			System.out.println("Transaction Id ---- " +clientTransactionID);
			
			String[] arr1 = { random_String,random_number,random_String,random_number,random_number,"1000",uidx,"ORDER"};
		
			Object[][] dataSet = new Object[][] { arr1};
			return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
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

	
}
