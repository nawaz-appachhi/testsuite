package com.myntra.apiTests.dataproviders;

import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class GiftCardNewServiceDP {
	
	public static String environment;
	public static final String GIFT_CARD_USER_ONE = "manualgiftcardtesting@myntra.com";
	public static final String GIFT_CARD_USER_ONE_CARD_ONE = "6991201059093138";
	public static final String GIFT_CARD_USER_ONE_PIN_ONE = "442960";
	public static final String GIFT_CARD_USER_ONE_CARD_TWO = "6991201053296867";
	public static final String GIFT_CARD_USER_ONE_PIN_TWO = "292717";
	public static final String GIFT_CARD_USER_ONE_CARD_THREE = "6991201052776337";
	public static final String GIFT_CARD_USER_ONE_PIN_THREE = "208468";
	public static final String GIFT_CARD_USER_ONE_CARD_FOUR = "6991201054392034";
	public static final String GIFT_CARD_USER_ONE_PIN_FOUR = "245650";
	public static final String GIFT_CARD_USER_ONE_CARD_FIVE = "6991201055068040";
	public static final String GIFT_CARD_USER_ONE_PIN_FIVE = "225248";
	public static final String GIFT_CARD_USER_ONE_CARD_SIX = "6991201058312857";
	public static final String GIFT_CARD_USER_ONE_PIN_SIX = "744010";
	public static final String GIFT_CARD_USER_ONE_CARD_SEVEN = "6991201053189374";
	public static final String GIFT_CARD_USER_ONE_PIN_SEVEN = "285022";
	public static final String GIFT_CARD_USER_ONE_CARD_EIGHT = "6991201059392120";
	public static final String GIFT_CARD_USER_ONE_PIN_EIGHT = "732682";
	public static final String GIFT_CARD_USER_ONE_CARD_NINE = "6991201057633317";
	public static final String GIFT_CARD_USER_ONE_PIN_NINE = "391582";
	public static final String GIFT_CARD_USER_ONE_CARD_TEN = "6991201052701879";
	public static final String GIFT_CARD_USER_ONE_PIN_TEN = "216129";
	public static final String GIFT_CARD_USER_ONE_CARD_ELEVEN = "6991201053554114";
	public static final String GIFT_CARD_USER_ONE_PIN_ELEVEN = "237260";
	public static final String GIFT_CARD_USER_ONE_CARD_TWELVE = "6991201053664459";
	public static final String GIFT_CARD_USER_ONE_PIN_TWELVE = "889573";
	public static final String GIFT_CARD_USER_ONE_CARD_THIRTEEN = "6991201051839699";
	public static final String GIFT_CARD_USER_ONE_PIN_THIRTEEN = "234995";
	public static final String GIFT_CARD_USER_ONE_CARD_FOURTEEN = "6991201057942631";
	public static final String GIFT_CARD_USER_ONE_PIN_FOURTEEN = "338175";
	public static final String GIFT_CARD_USER_ONE_CARD_FIFTEEN = "2538881000000004";
	public static final String GIFT_CARD_USER_ONE_PIN_FIFTEEN = "295579";
	public static final String GIFT_CARD_USER_ONE_CARD_SIXTEEN = "6991201054466753";
	public static final String GIFT_CARD_USER_ONE_PIN_SIXTEEN = "395507";
	public static final String GIFT_CARD_USER_ONE_CARD_SEVENTEEN = "6991201054450526";
	public static final String GIFT_CARD_USER_ONE_PIN_SEVENTEEN = "944740";
	
	public static final String GIFT_CARD_USER_TWO = "giftcardtesting@myntra.com";
	public static final String GIFT_CARD_USER_TWO_CARD_ONE = "6991201052597404";
	public static final String GIFT_CARD_USER_TWO_PIN_ONE = "202613";
	public static final String GIFT_CARD_USER_TWO_CARD_TWO = "6991201058454511";
	public static final String GIFT_CARD_USER_TWO_PIN_TWO = "604327";
	public static final String GIFT_CARD_USER_TWO_CARD_THREE = "6991201054642202";
	public static final String GIFT_CARD_USER_TWO_PIN_THREE = "604312";
	
	public static final String GIFT_CARD_USER_THREE = "giftcardtestuser@myntra.com";
	public static final String GIFT_CARD_USER_FOUR = "tempemail@myntra.com";
	public static final String GIFT_CARD_USER_FIVE = "autodebittestuser@myntra.com";
	public static final String GIFT_CARD_USER_SIX = "multigiftcardtesting@myntra.com";
	
	public static final String GIFT_CARD_USER_SEVEN = "jitender.kumar1@myntra.com";
	public static final String GIFT_CARD_USER_SEVEN_CARD_ONE = "6991201053189374";
	public static final String GIFT_CARD_USER_SEVEN_PIN_ONE = "285022";
	public static final String GIFT_CARD_USER_SEVEN_CARD_TWO = "1991200030000382";
	public static final String GIFT_CARD_USER_SEVEN_PIN_TWO = "251485";
	public static final String GIFT_CARD_USER_SEVEN_CARD_THREE = "6991201057186156";
	public static final String GIFT_CARD_USER_SEVEN_PIN_THREE_WRONG = "3571";
	public static final String GIFT_CARD_USER_EIGHT = "myntra.test.user.temp@gmail.com";
	public static final String GIFT_CARD_USER_NINE = "umang.lavaniya@myntra.com";
	public static final String GIFT_CARD_USER_TEN = "umang.lavania@gmail.com";
	
	APIUtilities apiUtil = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");
	Configuration con = new Configuration("./Data/configuration");
	
	public GiftCardNewServiceDP() {
		if(System.getenv("Environment")== null)
			environment = con.GetTestEnvironemnt().toString();	
		else
			environment = System.getenv("Environment");
	}

	
	
	@DataProvider
	public static Object[][] checkBalance(ITestContext testContext)
	{
		String[] arr1 = { "M7","GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, GIFT_CARD_USER_SEVEN };
		String[] arr2 = { "stage","GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, GIFT_CARD_USER_SEVEN };

		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	@DataProvider
	public static Object[][] checkBalanceExpired(ITestContext testContext)
	{
		String[] arr1 = { "M7", "GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_TWO, GIFT_CARD_USER_SEVEN_PIN_TWO, GIFT_CARD_USER_SEVEN };
		String[] arr2 = { "stage", "GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_TWO, GIFT_CARD_USER_SEVEN_PIN_TWO, GIFT_CARD_USER_SEVEN };

		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public static Object[][] checkBalanceWithWrongPinAndBlockCard(ITestContext testContext)
	{
		String[] arr1 = {"M7", "GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_THREE, GIFT_CARD_USER_SEVEN_PIN_THREE_WRONG, GIFT_CARD_USER_SEVEN };
		String[] arr2 = {"stage", "GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_THREE, GIFT_CARD_USER_SEVEN_PIN_THREE_WRONG, GIFT_CARD_USER_SEVEN };
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] debitGiftCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_ONE, GIFT_CARD_USER_ONE_PIN_ONE, "3000"};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_ONE, GIFT_CARD_USER_ONE_PIN_ONE, "3000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] debitGiftCardNegativebalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = { "M7",random_string,random_number, random_string, random_number, "-1400", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWO, GIFT_CARD_USER_ONE_PIN_TWO, "4000"};
		String[] arr3 = {"stage", random_string,random_number, random_string, random_number, "-1400", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWO, GIFT_CARD_USER_ONE_PIN_TWO, "4000"};
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = { "M7",random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_TWO, GIFT_CARD_USER_ONE_PIN_TWO, "757960", "1111"};
		String[] arr4 = { "stage",random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_TWO, GIFT_CARD_USER_ONE_PIN_TWO, "757960", "1111"};

		Object[][] dataSet = new Object[][] { arr1, arr3};
		//,arr2,arr4
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] debitGiftCardFloatingbalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "1400.46", "ORDER", GIFT_CARD_USER_SEVEN, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "3000"};
		String[] arr3 = {"stage", random_string,random_number, random_string, random_number, "1400.46", "ORDER", GIFT_CARD_USER_SEVEN, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "3000"};
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = {"M7", random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_THREE, "225248", "757960", "1111"};		
		String[] arr4 = {"stage", random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_THREE, "225248", "757960", "1111"};		

		Object[][] dataSet = new Object[][] {arr1, arr3};
		//,arr2,arr4
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
//	@DataProvider
//	public Object[][] refundGiftCard(ITestContext testContext)
//	{
//		String random_string = generateRandomString();
//		String random_number = generateRandomNumber();
//		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "6991201051693439", "608959", "4000");
//		String clientTxn = GetDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "100","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "6991201051693439", "608959", "4000");
//		
//		
//		String random_string1 = generateRandomString();
//		String random_number1 = generateRandomNumber();
//		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000");
//		String[] arr1 = { Checkcum1,random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000"};
//
//		
//		
//		Object[][] dataSet = new Object[][] { arr1};
//		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
//	}
	
	
	@DataProvider
	public static Object[][] giftCardnewPurchase(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String random_number2 = generateRandomNumber();
		//String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000");
		String[] arr1 = {"M7", random_number ,random_number1, random_string, "3000", random_number2, GIFT_CARD_USER_EIGHT, GIFT_CARD_USER_NINE};
		String[] arr2 = {"stage", random_number ,random_number1, random_string, "3000", random_number2, GIFT_CARD_USER_EIGHT, GIFT_CARD_USER_NINE};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public static Object[][] giftCardnewPurchaseDuplicate(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		
		String random_number1 = generateRandomNumber();
		String random_number2 = generateRandomNumber();
		//String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000");
		String[] arr1 = {"M7", random_number ,random_number1, random_string, "3000", random_number2, "jitender.kumar1@myntra.com", "manualgiftcardtesting@myntra.com"};
		String[] arr2= {"M7", random_number ,random_number1, random_string, "3000",random_number2, "jitender.kumar1@myntra.com", "manualgiftcardtesting@myntra.com"};
		String[] arr3 = {"stage", random_number ,random_number1, random_string, "3000", random_number2, "jitender.kumar1@myntra.com", "manualgiftcardtesting@myntra.com"};
		String[] arr4= {"stage", random_number ,random_number1, random_string, "3000",random_number2, "jitender.kumar1@myntra.com", "manualgiftcardtesting@myntra.com"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public static Object[][] giftCardnewPurchaseNegative(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String random_number2 = generateRandomNumber();
		//String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000");
		String[] arr1 = {"M7", random_number ,random_number1, random_string, "-3000", random_number2,GIFT_CARD_USER_ONE, GIFT_CARD_USER_SEVEN};
		//String[] arr2= {"M7", random_number ,random_number1, random_string, "3000", GIFT_CARD_USER_ONE, GIFT_CARD_USER_SEVEN};
		String[] arr3 = {"stage", random_number ,random_number1, random_string, "-3000", random_number2,GIFT_CARD_USER_ONE, GIFT_CARD_USER_SEVEN};
		//String[] arr4= {"stage", random_number ,random_number1, random_string, "3000", GIFT_CARD_USER_ONE, GIFT_CARD_USER_SEVEN};		
		
		Object[][] dataSet = new Object[][] {arr1, arr3};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public static Object[][] giftCardnewPurchaseResetPin(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String random_number2 = generateRandomNumber();
		
		//String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "6991201051693439", "608959", "4000");
		String[] arr1 = {"M7", random_number ,random_number1, random_string, "3000",random_number2, GIFT_CARD_USER_SEVEN, GIFT_CARD_USER_ONE};
		String[] arr2 = {"stage", random_number ,random_number1, random_string, "3000",random_number2, GIFT_CARD_USER_SEVEN, GIFT_CARD_USER_ONE};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	public static String getDebitGiftCardClientTranscation(String checksum, String ppsID, String ppsTransactionID, String orderId, String amount, String ppsType, String customerID, String partner_name,
			String cardNumber, String pinNumber, String bill_amount) {

	MyntraService service = Myntra.getService(ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.DEBITGIFTCARDNEW, init.Configurations, new String[] { checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, partner_name, cardNumber, pinNumber, bill_amount }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", GIFT_CARD_USER_ONE);
		System.out.println("DEBIT   Url------>>>>> "+service.URL);
		//System.out.println("DEBIT __   payload------>>>>> "+service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Debit Response " + jsonRes);
		String clientId = JsonPath.read(jsonRes, "$.params..clientTransactionID").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
        System.out.println("Client Debit tcn id -----  > > > > " + clientId);

		AssertJUnit.assertEquals("redeemGiftCard is not working", 200, req.response.getStatus());
		return clientId;
	}
	
	private static String getDefaultChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMREFUNDGIFTCARDQC,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEFAULT CHEKSCUM Url------>>>>> "+service.URL);
		//System.out.println(" DEFAULT CHEKSCUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user", GIFT_CARD_USER_ONE);
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Deafault Checksum------- > > >> > > > >\n" + msg1);
		return msg1;
	}
	
	
	private static String getVoidChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMGIFTVOIDTRANSACTIONQC,init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEFAULT CHEKSCUM Url------>>>>> "+service.URL);
		//System.out.println(" DEFAULT CHEKSCUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user",GIFT_CARD_USER_ONE);
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();
		

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Void Checksum------- > > >> > > > >" + msg1);
		return msg1;
	}
	
	
	
	private static String getDebitChecksum(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String partner_name,String cardNumber,String pinNumber,String bill_amount)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.CHECKSUMDEBITGIFTCARD, init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,partner_name,cardNumber,pinNumber,bill_amount}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>>\n "+service.URL);
		//System.out.println("DEBIT Debit CHECKSUM payload------>>>>> "+service.Payload);

//		HashMap getParam = new HashMap();
//		getParam.put("user",GIFT_CARD_USER_ONE);
		RequestGenerator req = new RequestGenerator(service);	
		String response = req.returnresponseasstring();
		int status = req.response.getStatus();
		System.out.println("Status for debit checksum request: ----->> \n"+status);
		System.out.println("Respponse for debit checksum: ---->> \n"+response);

		String msg1 = JsonPath.read(response, "$.checksum").toString().replace("[", "").replace("]", "").replace("\"","").trim();

		System.out.println("Debit Checksum------- > > >> > > > >\n" + msg1);
		System.out.println("DEBIT Checksum Response ------>>>>\n"  + response);
		return msg1;
	}	

	
	
	private static String generateRandomString()
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
	
	
	@DataProvider
	public static Object[][] redeemGiftCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOUR, GIFT_CARD_USER_ONE_PIN_FOUR, "200"};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOUR, GIFT_CARD_USER_ONE_PIN_FOUR, "200"};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public static Object[][] redeemGiftCardMoreThanBalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "110000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		String[] arr3 = {"stage", random_string,random_number, random_string, random_number, "110000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = {"M7", random_string1,random_number1, random_string1, random_number1, "105000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "1111"};
		String[] arr4 = {"stage", random_string1,random_number1, random_string1, random_number1, "105000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "1111"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public static Object[][] redeemGiftCardNegativebalance(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "-1100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		String[] arr3 = {"stage", random_string,random_number, random_string, random_number, "-1100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};

		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = {"M7", random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "757960", "1111"};
		String[] arr4 = {"stage", random_string1,random_number1, random_string1, random_number1, "-5000", "ORDER", GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "757960", "1111"};

		Object[][] dataSet = new Object[][] {arr1, arr3};
		//,arr2, arr4
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] redeemGiftCardLessThan100(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "10", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "10"};
		String[] arr4 = {"stage", random_string,random_number, random_string, random_number, "10", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "10"};

		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String[] arr2 = {"M7", random_string1,random_number1, random_string1, random_number1, "50", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "50"};
		String[] arr5 = {"stage", random_string1,random_number1, random_string1, random_number1, "50", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "50"};
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String[] arr3 = {"M7", random_string2,random_number2, random_string2, random_number2, "99", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "99"};
		String[] arr6 = {"stage", random_string2,random_number2, random_string2, random_number2, "99", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "99"};

		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	@DataProvider
	public static Object[][] refundGiftCard(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "100","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "4000");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checksum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "4000");
		String[] arr1 = {"M7", Checksum1,random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "4000"};
		String[] arr2 = {"stage", Checksum1,random_number1, random_string1, random_number1, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "4000"};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] refundGiftCardMoreThanDebit(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SEVEN, GIFT_CARD_USER_ONE_PIN_SEVEN, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SEVEN, GIFT_CARD_USER_ONE_PIN_SEVEN, "4000");
			
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_EIGHT, GIFT_CARD_USER_ONE_PIN_EIGHT, "4000");
		String[] arr1 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_EIGHT, GIFT_CARD_USER_ONE_PIN_EIGHT, "4000"};
		String[] arr2 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_EIGHT, GIFT_CARD_USER_ONE_PIN_EIGHT, "4000"};
				
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] refundGiftCardLessThanDebit(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_NINE, GIFT_CARD_USER_ONE_PIN_NINE, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "3000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_NINE, GIFT_CARD_USER_ONE_PIN_NINE, "4000");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checksum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_NINE, GIFT_CARD_USER_ONE_PIN_NINE, "4000");
		String[] arr1 = {"M7", Checksum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_NINE, GIFT_CARD_USER_ONE_PIN_NINE, "4000"};
		String[] arr2 = {"stage", Checksum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_NINE, GIFT_CARD_USER_ONE_PIN_NINE, "4000"};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] refundGiftOnDifferentCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_TEN, GIFT_CARD_USER_ONE_PIN_TEN, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TEN, GIFT_CARD_USER_ONE_PIN_TEN, "4000");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_TEN, GIFT_CARD_USER_ONE_PIN_TEN, "4000");
		String[] arr1 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TEN, GIFT_CARD_USER_ONE_PIN_TEN, "4000"};
		String[] arr3 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TEN, GIFT_CARD_USER_ONE_PIN_TEN, "4000"};
	
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");
//			
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = {"M7", Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
//		String[] arr4 = {"stage", Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr3};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public static Object[][] refundGiftWithInvalid(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "2000");
		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "2000");	
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "2000");
		String[] arr1 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,"1320000", "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "2000"};
		String[] arr2 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,"1320000", "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THREE, GIFT_CARD_USER_ONE_PIN_THREE, "2000"};
			
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public static Object[][] refundGiftCardWithSameTXN(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "10000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000");
		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "10000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_FIVE, "1000");
		String[] arr1 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000"};
		String[] arr2 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "4000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000"};
		String[] arr3 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000"};
		String[] arr4 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "4000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SIX, GIFT_CARD_USER_ONE_PIN_SIX, "1000"};
			
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
//	@DataProvider
//	public Object[][] refundGiftWithVoidedTxn(ITestContext testContext)
//	{
//		
//		String random_string = generateRandomString();
//		String random_number = generateRandomNumber();
//		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000");
//		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "2000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG",GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000");
//		
//		String random_string1 = generateRandomString();
//		String random_number1 = generateRandomNumber();
//		String voidchecksum = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG",GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000");
//		CreateGiftVoidTransaction(voidchecksum, random_number1, random_string1, random_number1, "2000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG",GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000");
//		
//		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDefaultChecksum(random_string2, random_number2, random_string2, random_number2, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG",GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000");
//		String[] arr1 = { Checkcum2,random_number2, random_string2, random_number2, "2000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG",GIFT_CARD_USER_SEVEN_CARD_ONE, GIFT_CARD_USER_SEVEN_PIN_ONE, "2000"};
//
//		
////		String random_string2 = generateRandomString();
////		String random_number2 = generateRandomNumber();
////		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");
////		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");
////		
////		
////		String random_string3 = generateRandomString();
////		String random_number3 = generateRandomNumber();
////		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
////		String[] arr2 = { Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
//		
//		Object[][] dataSet = new Object[][] { arr1};
//		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
//	}
//	
	
	
	@DataProvider
	public static Object[][] giftVoidTransaction(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_ELEVEN, GIFT_CARD_USER_ONE_PIN_ELEVEN, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "3000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_ELEVEN, GIFT_CARD_USER_ONE_PIN_ELEVEN, "4000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getVoidChecksum(random_string1, random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_ELEVEN, GIFT_CARD_USER_ONE_PIN_ELEVEN, "4000");
		String[] arr1 ={"M7", voidchecksum, random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_ELEVEN, GIFT_CARD_USER_ONE_PIN_ELEVEN, "4000"};
		String[] arr3 ={"stage", voidchecksum, random_number1, random_string1, random_number1, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_ELEVEN, GIFT_CARD_USER_ONE_PIN_ELEVEN, "4000"};
		
//		String random_string2 = generateRandomString();
//		String random_number2 = generateRandomNumber();
//		String Checkcum2 = getDebitChecksum(random_string2, random_number2, random_string2, random_number2, "30000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String clientTxn2 = GetDebitGiftCardClientTranscation(Checkcum2, random_number2, random_string2, random_number2, "30000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", "1991200030000384", "768179", "2000");	
//		
//		String random_string3 = generateRandomString();
//		String random_number3 = generateRandomNumber();
//		String Checkcum3 = getDefaultChecksum(random_string3, random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", "1991200030000384", "768179", "2000");
//		String[] arr2 = {"M7", Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
//		String[] arr4 = {"stage", Checkcum3,random_number3, random_string3, random_number3, "30000", "ORDER", GIFT_CARD_USER_ONE,clientTxn2, "GIFTBIG", "1991200030000384", "768179", "2000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr3};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] giftVoidTransactionOnAlreadyVoidedTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG",GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "3000","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getVoidChecksum(random_string1, random_number1, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000");
		String[] arr1 ={"M7", voidchecksum, random_number1, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000"};
		String[] arr3 ={"stage", voidchecksum, random_number1, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000"};
			
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getVoidChecksum(random_string2, random_number2, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000");
		String[] arr2 ={"M7", voidchecksum2, random_number2, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000"};
		String[] arr4 ={"stage", voidchecksum2, random_number2, random_string1, random_number, "3000", "ORDER", GIFT_CARD_USER_ONE, clientTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_TWELVE, GIFT_CARD_USER_ONE_PIN_TWELVE, "4000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public static Object[][] giftVoidTransactionOnRefundTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "100","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checksum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number, "100", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000");
		String refundTxn = createRefundGiftCard(Checksum1, random_number1, random_string1, random_number, "100","ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000");
				
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getVoidChecksum(random_string2, random_number2, random_string2, random_number, "100", "ORDER", GIFT_CARD_USER_ONE,refundTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000");
		String[] arr1 ={"M7", voidchecksum2, random_number2, random_string2, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, refundTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000"};
		String[] arr2 ={"stage", voidchecksum2, random_number2, random_string2, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, refundTxn ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_THIRTEEN, GIFT_CARD_USER_ONE_PIN_THIRTEEN, "4000"};


		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public static Object[][] giftVoidTransactionWithInvalidTxn(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checksum = getDebitChecksum(random_string, random_number, random_string, random_number, "300", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000");
		String clientTxn = getDebitGiftCardClientTranscation(Checksum, random_number, random_string, random_number, "300","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000");
		
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String voidchecksum = getVoidChecksum(random_string1, random_number1, random_string1, random_number1, "300", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000");
		String[] arr1 ={"M7", voidchecksum, random_number1, random_string1, random_number1, "300", "ORDER", GIFT_CARD_USER_ONE, "0909" ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000"};
		String[] arr3 ={"stage", voidchecksum, random_number1, random_string1, random_number1, "300", "ORDER", GIFT_CARD_USER_ONE, "0909" ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000"};
		
		String random_string2 = generateRandomString();
		String random_number2 = generateRandomNumber();
		String voidchecksum2 = getVoidChecksum(random_string2, random_number2, random_string2, random_number2, "300", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000");
		String[] arr2 ={"M7", voidchecksum2, random_number2, random_string2, random_number2, "300", "ORDER", GIFT_CARD_USER_ONE, "9809" ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000"};
		String[] arr4 ={"stage", voidchecksum2, random_number2, random_string2, random_number2, "300", "ORDER", GIFT_CARD_USER_ONE, "9809" ,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FOURTEEN, GIFT_CARD_USER_ONE_PIN_FOURTEEN, "4000"};

		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	private void CreateGiftVoidTransaction(String checksum,String ppsID ,String ppsTransactionID,String orderId,String amount,String ppsType,String customerID,String clientTransactionID,String partner_name,String cardNumber,String pinNumber,String bill_amount) {
			
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.GIFTVOIDTRANSACTION,
				init.Configurations,new String[]{checksum,ppsID ,ppsTransactionID,orderId,amount,ppsType,customerID,clientTransactionID,partner_name,cardNumber,pinNumber,bill_amount}, new String[] {customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("VOid Url------>>>>> "+service.URL);
		System.out.println("voidpayload------>>>>> "+service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("user", GIFT_CARD_USER_ONE);
		RequestGenerator req = new RequestGenerator(service, getParam);	
		String response = req.returnresponseasstring();		
		System.out.println("responsr ------ >>>" + response);
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Void Msg------>>>>> \n"+msg1);
		System.out.println("voide response -----===???\n " + response);

//		String msg1 = JsonPath.read(response, "$.userAccountInfo..activePointsBalance").toString().replace("[", "").replace("]", "").trim();
//		System.out.println("Response---->>>>" +response );
////		String dbConnection_Fox7 = "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
////		int count = getCount(dbConnection_Fox7);
		
	}
	
	
	private static String createRefundGiftCard(String checksum, String ppsID,
			String ppsTransactionID, String orderId, String amount,
			String ppsType, String customerID, String clientTransactionID,
			String partner_name, String cardNumber, String pinNumber,
			String bill_amount) {

		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_GIFTCARDNEWSERVICE, APINAME.REFUNDGIFTCARDQC, init.Configurations, new String[] { checksum, ppsID, ppsTransactionID, orderId, amount, ppsType, customerID, clientTransactionID, partner_name, cardNumber,pinNumber, bill_amount }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("user", GIFT_CARD_USER_ONE);
		System.out.println("Service request --- ? " + service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Refund Response " + jsonRes);
		String clientTxId = JsonPath.read(jsonRes, "$..clientTransactionID").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();

		return clientTxId;
	}
	
	
	private static String generateRandomNumber()
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
		
		String[] arr1 = {"M7", random_string, random_number, random_string, random_number, "110", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		String[] arr2 = {"stage", random_string, random_number, random_string, random_number, "110", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1,1);
	}
	
	private String getDebitChecksumRes(String checksum,String ppsTransactionID,String clientTransactionId,String ppsId,String orderId,String amount,String customerID)
	{
		
		MyntraService service = Myntra.getService(
		ServiceType.PORTAL_GIFTBIGSERVICE, APINAME.DEBITCHCKSUMRESPONSE,init.Configurations,new String[]{checksum,ppsTransactionID,clientTransactionId,ppsId,orderId,amount,customerID}, PayloadType.JSON,PayloadType.JSON);
		System.out.println("DEBIT CHEKSUM Url------>>>>> "+service.URL);
		System.out.println("DEBIT Debit CHECKSUM payload------>>>>> "+service.Payload);

		HashMap getParam = new HashMap();
		getParam.put("user",GIFT_CARD_USER_ONE);
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
		String[] arr1 = {"M7", random_string, random_number, random_string, random_number, "110", "ORDER", GIFT_CARD_USER_ONE, random_number,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "200"};
		String[] arr2 = {"stage", random_string, random_number, random_string, random_number, "110", "ORDER", GIFT_CARD_USER_ONE, random_number,"GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "200"};
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1,1);
	}
	
	
	@DataProvider
	public Object[][] checkBalanceNegative(ITestContext testContext)
	{
		String[] arr1 = {"M7", "GIFTBIG",GIFT_CARD_USER_ONE_CARD_FIFTEEN,GIFT_CARD_USER_ONE_PIN_FIFTEEN,GIFT_CARD_USER_ONE };
		String[] arr2 = {"stage", "GIFTBIG",GIFT_CARD_USER_ONE_CARD_FIFTEEN,GIFT_CARD_USER_ONE_PIN_FIFTEEN,GIFT_CARD_USER_ONE };
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	//Refund gift card after all test case run
	

	@DataProvider
	public static Object[][] refundGiftCardAfterTestCaseRun(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String Checkcum = getDebitChecksum(random_string, random_number, random_string, random_number, "100", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "200");
		String clientTxn = getDebitGiftCardClientTranscation(Checkcum, random_number, random_string, random_number, "100","ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "200");
				
		String random_string1 = generateRandomString();
		String random_number1 = generateRandomNumber();
		String Checkcum1 = getDefaultChecksum(random_string1, random_number1, random_string1, random_number1, "1700", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000");
		String[] arr1 = {"M7", Checkcum1,random_number1, random_string1, random_number1, "1700", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};
		String[] arr2 = {"stage", Checkcum1,random_number1, random_string1, random_number1, "1700", "ORDER", GIFT_CARD_USER_ONE,clientTxn, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_FIVE, GIFT_CARD_USER_ONE_PIN_FIVE, "2000"};

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public static Object[][] giftCardnewPurchaseSchema(ITestContext testContext)
	{
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
				
		String random_number1 = generateRandomNumber();
		String random_number2 = generateRandomNumber();
		String[] arr1 = {"M7", random_number ,random_number1, random_string, "3000", random_number2, GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE};
		String[] arr2 = {"stage", random_number ,random_number1, random_string, "3000", random_number2, GIFT_CARD_USER_ONE, GIFT_CARD_USER_ONE};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}		
	
	@DataProvider
	public static Object[][] addGCtoUsr(ITestContext testContext)
	{
		String[] arr1 = {"M7", GIFT_CARD_USER_TWO_CARD_ONE,GIFT_CARD_USER_TWO_PIN_ONE,GIFT_CARD_USER_TWO,"GIFTBIG"};
		String[] arr2 = {"stage", GIFT_CARD_USER_TWO_CARD_ONE,GIFT_CARD_USER_TWO_PIN_ONE,GIFT_CARD_USER_TWO,"GIFTBIG"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
    public static Object[][] getAllActiveCouponsBalance(ITestContext testContext)
    {
            String[] arr1 = {"M7", GIFT_CARD_USER_THREE,"18002","Check Gift Cards Total Balance Successful", "SUCCESS" ,"1"};
            String[] arr2 = {"stage", GIFT_CARD_USER_THREE,"18002","Check Gift Cards Total Balance Successful", "SUCCESS" ,"1"};

            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
		
	@DataProvider
    public static Object[][] getAllActiveCouponsBalanceNegativeCases(ITestContext testContext)
    {
			// uidx,status code,status message,status type, total count
            String[] arr1 = {"M7", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qE","18003","Zero Gift Card balance associated with this account ","SUCCESS" ,"0"};
            String[] arr2 = {"M7", "","18005","No Gift Cards associated with this account ","SUCCESS","0" };
            String[] arr3 = {"stage", "526a78d7.e9e9.4760.a8e0.06247305b0fc9Dqw8qE","18003","Zero Gift Card balance associated with this account ","SUCCESS" ,"0"};
            String[] arr4 = {"stage", "","18005","No Gift Cards associated with this account ","SUCCESS","0" };

            Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
    }	
	
	@DataProvider
    public static Object[][] getGiftCardAddedToUserAccount(ITestContext testContext)
    {
            String[] arr1 = {"M7", GIFT_CARD_USER_THREE };
            String[] arr2 = {"stage", GIFT_CARD_USER_THREE };
            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }

	@DataProvider
    public static Object[][] getGiftCardAddedToUserAccountWithParam(ITestContext testContext)
    {
            String[] arr1 = {"M7", GIFT_CARD_USER_THREE,"false","0","2" };
            String[] arr2 = {"M7", GIFT_CARD_USER_THREE,"true","0","1" };
            String[] arr3 = {"stage", GIFT_CARD_USER_THREE,"false","0","2" };
            String[] arr4 = {"stage", GIFT_CARD_USER_THREE,"true","0","1" };
             
            Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
    }
	
	@DataProvider
    public static Object[][] resetPin(ITestContext testContext)
    {
            //String[] arr1 = { "7712344376","3123455",GIFT_CARD_USER_NINE,GIFT_CARD_USER_FOUR };
			String[] arr1 = {"M7", "5858","6931",GIFT_CARD_USER_TWO,GIFT_CARD_USER_FOUR };
			String[] arr2 = {"stage", "5858","6931",GIFT_CARD_USER_TWO,GIFT_CARD_USER_FOUR };

            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
	public static Object[][] addWrongGCtoUsr(ITestContext testContext)
	{
		String[] wrongGcNum1 = {"M7", GIFT_CARD_USER_TWO_CARD_TWO, GIFT_CARD_USER_TWO_PIN_TWO, GIFT_CARD_USER_TWO,"GIFTBIG","10004","Could not find card. Please enter valid card number."};
		String[] wrongPin1 = {"M7", GIFT_CARD_USER_TWO_CARD_THREE, GIFT_CARD_USER_TWO_PIN_THREE, GIFT_CARD_USER_TWO,"GIFTBIG","10007","Either card number or card pin is incorrect."};
		String[] wrongGcNum2 = {"stage", GIFT_CARD_USER_TWO_CARD_TWO, GIFT_CARD_USER_TWO_PIN_TWO, GIFT_CARD_USER_TWO,"GIFTBIG","10004","Could not find card. Please enter valid card number."};
		String[] wrongPin2 = {"stage", GIFT_CARD_USER_TWO_CARD_THREE, GIFT_CARD_USER_TWO_PIN_THREE, GIFT_CARD_USER_TWO,"GIFTBIG","10007","Either card number or card pin is incorrect."};
		
		Object[][] dataSet = new Object[][] {wrongGcNum1, wrongPin1, wrongGcNum2, wrongPin2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 2, 2);
	}
		
	@DataProvider
    public static Object[][] getGCConfigWithWrongConfigId(ITestContext testContext)
    {
            String[] arr1 = {"M7", generateRandomString()};
            String[] arr2 = {"stage", generateRandomString()};
            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public static Object[][] createGCConfig(ITestContext testContext)
    {
            String[] arr1 = {"M7", "Umang",GIFT_CARD_USER_TEN,"Myntra User",GIFT_CARD_USER_NINE,"Test message","test gc image","test cover image","card image","true"};
            String[] arr2 = {"stage", "Umang",GIFT_CARD_USER_TEN,"Myntra User",GIFT_CARD_USER_NINE,"Test message","test gc image","test cover image","card image","true"};

            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public static Object[][] createGCConfigNegative(ITestContext testContext)
    {
            String[] arr1 = {"M7", "Umang",GIFT_CARD_USER_TEN,"Myntra User","","Test message","test gc image","test cover image","card image","true"};
            String[] arr2 = {"stage", "Umang",GIFT_CARD_USER_TEN,"Myntra User","","Test message","test gc image","test cover image","card image","true"};

            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public static Object[][] updateGCConfig(ITestContext testContext)
    {
            String[] arr1 = {"M7", generateRandomString()};
            String[] arr2 = {"stage", generateRandomString()};
            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public static Object[][] updateGCConfigWithWrongConfigId(ITestContext testContext)
    {
            String[] arr1 = {"M7", generateRandomString(),generateRandomString()};
            String[] arr2 = {"stage", generateRandomString(),generateRandomString()};

            Object[][] dataSet = new Object[][] {arr1, arr2};
            return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
/////////////////////////////////////////////////////////	
	@DataProvider
    public static Object[][] autoGiftcardRedemption(ITestContext testContext)
    {
		String random_string=generateRandomString();
		String random_number=generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_FIVE,"GIFTBIG", "200"};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_FIVE,"GIFTBIG", "200"};
        
		Object[][] dataSet = new Object[][] {arr1, arr2};
        return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
    public static Object[][] manualDebitAddToAccountAndRefundGiftCard(ITestContext testContext)
    {
		String random_string=generateRandomString();
		String random_number=generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_ONE,"GIFTBIG", "200",GIFT_CARD_USER_ONE_CARD_SIXTEEN,GIFT_CARD_USER_ONE_PIN_SIXTEEN};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "200", "ORDER", GIFT_CARD_USER_ONE,"GIFTBIG", "200",GIFT_CARD_USER_ONE_CARD_SIXTEEN,GIFT_CARD_USER_ONE_PIN_SIXTEEN};        
		Object[][] dataSet = new Object[][] {arr1, arr2};
        return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }

	@DataProvider
    public static Object[][] autoMultipleGiftcardRedemption(ITestContext testContext)
    {
		String random_string=generateRandomString();
		String random_number=generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "110000", "ORDER", GIFT_CARD_USER_SIX,"GIFTBIG", "110000"};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "110000", "ORDER", GIFT_CARD_USER_SIX,"GIFTBIG", "110000"};
        
		Object[][] dataSet = new Object[][] {arr1, arr2};
        return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
    }
	
	@DataProvider
	public static Object[][] debitAlreadyAddedGiftCard(ITestContext testContext)
	{
		
		String random_string = generateRandomString();
		String random_number = generateRandomNumber();
		String[] arr1 = {"M7", random_string,random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SEVENTEEN, GIFT_CARD_USER_ONE_PIN_SEVENTEEN, "3000"};
		String[] arr2 = {"stage", random_string,random_number, random_string, random_number, "2000", "ORDER", GIFT_CARD_USER_ONE, "GIFTBIG", GIFT_CARD_USER_ONE_CARD_SEVENTEEN, GIFT_CARD_USER_ONE_PIN_SEVENTEEN, "3000"};
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(environment, dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
}
