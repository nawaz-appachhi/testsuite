package com.myntra.apiTests.dataproviders;

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

public class GiftBigDP {
	
	APIUtilities apiUtil = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	
	
	
	@DataProvider
	public Object[][] checkBalance(ITestContext testContext)
	{
		String[] arr1 = { "GIFTBIG", "1991200030000450", "498393","manishkumar.gupta@myntra.com" };

		

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}		
	
	
	
	@DataProvider
	public Object[][] redeemGiftCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = { random_string,random_number, random_string, random_number, "2000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000"};

//		
//		
//		String random_string1 = generateRandomString();
//		String random_number1 = generateRandomNumber();
//		String[] arr2 = { random_string1,random_number1, random_string1, random_number1, "1199", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000368", "210966", "1111"};
//		
		
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}

	
	
	@DataProvider
	public Object[][] redeemGiftCardMoreThanBalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = { random_string,random_number, random_string, random_number, "110000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000"};

		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = { random_string1,random_number1, random_string1, random_number1, "105000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "1111"};
		
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public Object[][] redeemGiftCardNegativebalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = { random_string,random_number, random_string, random_number, "-1100", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000"};

		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = { random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", "manishkumar.gupta@myntra.com", "1991200030000450", "498393", "757960", "1111"};
		
		
		

		Object[][] dataSet = new Object[][] { arr1};
		//,arr2
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] redeemGiftCardLessThan100(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = { random_string,random_number, random_string, random_number, "10", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201054412120", "606134", "2000"};

		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = { random_string1,random_number1, random_string1, random_number1, "50", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG","6991201054412120", "606134", "1111"};
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String[] arr3 = { random_string2,random_number2, random_string2, random_number2, "99", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201054412120", "606134", "1111"};
		

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	@DataProvider
	public Object[][] refundGiftCard(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051714100", "949276", "1000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "100","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051714100", "949276", "1000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "6991201051714100", "949276", "1000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "100", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "6991201051714100", "949276", "1000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundGiftCardMoreThanDebit(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundGiftCardLessThanDebit(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "3000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] refundGiftOnDifferentCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG","1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG","1991200030000450", "498393", "2000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] refundGiftWithInvalid(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051701255", "174161", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051701255", "174161", "2000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG","6991201051701255", "174161", "2000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com","1320000", "GIFTBIG", "6991201051701255", "174161", "2000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] refundGiftCardWithSameTXN(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "10000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051714100", "949276", "1000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "10000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201051714100", "949276", "1000");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "6991201051714100", "498393", "1000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "6991201051714100", "949276", "1000"};
		String[] arr2 = { Checkcum1,random_number1, random_string1, random_number1, "4000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "6991201051714100", "949276", "1000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	@DataProvider
	public Object[][] refundGiftWithVoidedTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "6991201053995340", "254419", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG","6991201053995340", "254419", "2000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG","6991201053995340", "254419", "2000");
		CreateGiftVoidTransaction(voidchecksum, random_number1, random_string1, random_number1, "2000", "ORDER", "manishkumar.gupta@myntra.com", clientTxn ,"GIFTBIG","6991201053995340", "254419", "2000");
		
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String Checkcum2 = getDefaultChecksum(random_string2, random_number2, random_string2, random_number2, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG","6991201053995340", "254419", "2000");
		String[] arr1 = { Checkcum2,random_number2, random_string2, random_number2, "2000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG","6991201053995340", "254419", "2000"};

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] gifftVoidTransaction(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "3000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 ={voidchecksum, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com", clientTxn ,"GIFTBIG", "1991200030000450", "498393", "2000"};
		
		
		

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] gifftVoidTransactionOnAlreadyVoidedTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG","1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "3000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 ={voidchecksum, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com", clientTxn ,"GIFTBIG", "1991200030000450", "498393", "2000"};
//		
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getDefaultChecksum(random_string2, random_number2, random_string2, random_number2, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr2 ={voidchecksum2, random_number2, random_string2, random_number2, "3000", "ORDER", "manishkumar.gupta@myntra.com", clientTxn ,"GIFTBIG", "1991200030000450", "498393", "2000"};

		
		

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	
	@DataProvider
	public Object[][] gifftVoidTransactionOnRefundTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "3000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String refunTXn = createRefundGiftCard(voidchecksum, random_number1, random_string1, random_number1, "3000","ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		
		
		
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getDefaultChecksum(random_string2, random_number2, random_string2, random_number2, "3000", "ORDER", "manishkumar.gupta@myntra.com",refunTXn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 ={voidchecksum2, random_number2, random_string2, random_number2, "3000", "ORDER", "manishkumar.gupta@myntra.com", refunTXn ,"GIFTBIG", "1991200030000450", "498393", "2000"};

		
		

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] gifftVoidTransactionWithInvalidTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "300", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "300","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "300", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 ={voidchecksum, random_number1, random_string1, random_number1, "300", "ORDER", "manishkumar.gupta@myntra.com", "0909" ,"GIFTBIG", "1991200030000450", "498393", "2000"};
		
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getDefaultChecksum(random_string2, random_number2, random_string2, random_number2, "300", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr2 ={voidchecksum2, random_number2, random_string2, random_number2, "300", "ORDER", "manishkumar.gupta@myntra.com", "9809" ,"GIFTBIG", "1991200030000450", "498393", "2000"};

		
		

		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000384", "768179", "2000");
//		
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", "manishkumar.gupta@myntra.com",clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	
	private String getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMDEBITGIFT,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>> "+service.URL);
		//System.out.println("DEBIT Debit CHECKSUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();
		int status=req.response.getStatus();
		System.out.println("Status for debit checksum request: ----->> "+status);
		System.out.println("Respponse for debit checksum: ---->> "+response);

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Debit CHecksum------- > > >> > > > >" + msg1);
		System.out.println("DEBIT CHecksum RESPONSE ------>>>>"  + response);
		return msg1;
	}	
	
	
	private String getDefaultChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.CHECKSUMGIFTCARD,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEFAULT CHEKSCUM Url------>>>>> "+service.URL);
		//System.out.println(" DEFAULT CHEKSCUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user",
				"manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Deafault Checksum------- > > >> > > > >" + msg1);
		return msg1;
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
	
	
	public String GetDebitGiftCardClientTranscation(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String partner_name,
			String cardNumber, String pinNumber, String bill_amount) {

		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITGIFTCARD,
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
				//
		// log.info(service.URL);
		// AssertJUnit.assertEquals("unsuccessful", msg1,
		// "Check Balance Successful");
		AssertJUnit.assertEquals("redeemGiftCard is not working", 200,
				req.response.getStatus());
		return clientId;
	}
	
	
	private void CreateGiftVoidTransaction(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount) {
		
		 
//		creditLoyalityPoints(
//				customerID, "100","0", "0",
//				"0","automation", "ORDER", "123", "GOOD_WILL");
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.GIFTVOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("VOid Url------>>>>> "+service.URL);
		System.out.println("voidpayload------>>>>> "+service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();		
		System.out.println("responsr ------ >>>" + response);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Void Msg------>>>>> "+msg1);
		System.out.println("voide response -----===??? " + response);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		
	}
	
	
	
	private String createRefundGiftCard(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.REFUNDGIFTCARD,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, orderId, amount, ppsType, customerID,
						clientTransactionID, partner_name, cardNumber,
						pinNumber, bill_amount }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", "manishkumar.gupta@myntra.com");
		System.out.println("Service request --- ? " + service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String txnid = JsonPath.read(jsonRes, "$..clientTransactionID").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();

		return txnid;
	}
	
	
	private String generateRandomNumber()
	{
		Random randomno = new Random();
		int number = randomno.nextInt(10000);
		String randomNumber = String.valueOf(number);
		return randomNumber;
	}
	
	@DataProvider
	public Object[][] vDebitChecksum(ITestContext testContext)
	{
		
		
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		
		String[] arr1 = {random_string, random_number, random_string, random_number, "110", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "2000"};
		
//		String random_string = generateRandomString();
//		String random_number = generateRandomNumber();
//		String Checkcum = getDebitChecksumRes(random_string, random_string, random_number, random_number, random_number, "100", "manishkumar.gupta@myntra.com");
//		GetDebitGiftCardClientTranscation(Checkcum, random_number, random_number, random_number, "100", "manishkumar.gupta@myntra.com","GIFTBIG", "1991200030000384", "768179", "2000");
//		
		
//		String random_string1 = generateRandomString();
//		String random_number1 = generateRandomNumber();
//		String Checkcum1 = getDebitChecksum(random_string1, random_number1, random_string1, random_number1, "1199", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000374", "757960", "1111");
//		String[] arr2 = { Checkcum1,random_number1, random_string1, random_number1, "1199", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000374", "757960", "1111"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1,1);
	}
	
	private String getDebitChecksumRes(String checksum,String ppsTransactionID,String clientTransactionId,String ppsId,String orderId,String amount,String customerID)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITCHCKSUMRESPONSE,init.Configurations,new String[]{checksum,ppsTransactionID,clientTransactionId,ppsId,orderId,amount,customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>> "+service.URL);
		System.out.println("DEBIT Debit CHECKSUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user","manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();
		int status=req.response.getStatus();
		System.out.println("Status for debit checksum request: ----->> "+status);
		System.out.println("Respponse for debit checksum: ---->> "+response);

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Debit CHecksum------- > > >> > > > >" + msg1);
		System.out.println("DEBIT CHecksum RESPONSE ------>>>>"  + response);
		return msg1;
	}
	
	
	@DataProvider
	public Object[][] vRefundChecksum(ITestContext testContext)
	{
		
		
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		
		String[] arr1 = {random_string, random_number, random_string, random_number, "110", "ORDER", "manishkumar.gupta@myntra.com", random_number,"GIFTBIG", "1991200030000450", "498393", "200"};
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] checkBalanceNegative(ITestContext testContext)
	{
		String[] arr1 = { "GIFTBIG","2538881000000004","295579","manishkumar.gupta@myntra.com" };

		

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	//Refund gift card after all test case run
	

	@DataProvider
	public Object[][] refundGiftCardAfterTestCaseRun(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "200");
		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "100","ORDER", "manishkumar.gupta@myntra.com", "GIFTBIG", "1991200030000450", "498393", "200");
		
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "1700", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000");
		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "1700", "ORDER", "manishkumar.gupta@myntra.com",clientTxn, "GIFTBIG", "1991200030000450", "498393", "2000"};

		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
}
