package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.giftcardservice.GiftCardServiceConstants;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceDataProviderEnum;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SanityOfAllServicesDP implements GiftCardServiceConstants {
	String environment;
	Configuration con = new Configuration("./Data/configuration");
	List<Integer> styleIdNike = new ArrayList<Integer>();
	List<Integer> skuIdNike = new ArrayList<Integer>(); 

	public SanityOfAllServicesDP() {
		if (System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT.name()) == null)
			environment = CONFIGURATION.GetTestEnvironemnt().toString();
		else
			environment = System.getenv(IDPServiceDataProviderEnum.ENVIRONMENT
					.name());
		StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
		styleIdNike = styleHelper.performSeachServiceToGetStyleIds(
				"nike", "15", "true", "false");
		if(styleIdNike.size()==0){
			styleIdNike.add(297082);
			styleIdNike.add(266324);
			styleIdNike.add(19304);
			styleIdNike.add(109212);
			styleIdNike.add(119305);
			styleIdNike.add(263979);
			styleIdNike.add(252472);
		}
		try {
		skuIdNike = styleHelper.getSkuIds(styleIdNike
				.get(new Random().nextInt(styleIdNike.size())) + "");
		}catch(com.jayway.jsonpath.PathNotFoundException e){
			e.printStackTrace();
		}
		
		if(skuIdNike.size() ==0 ){
			skuIdNike.add(848079);
			skuIdNike.add(848078);
			skuIdNike.add(930816);
			skuIdNike.add(930818);
			skuIdNike.add(930819);
			skuIdNike.add(930820);
			skuIdNike.add(930821);
		}
			
	}

	

	@DataProvider
	public Object[][] getABTestDataProvider(ITestContext testContext) {
		String[] ABTestName = { "njswHP" };
		Object[][] dataSet = new Object[][] { ABTestName };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] styleIdAjaxSearchDataProvider(ITestContext iTestContext) {
		String[] arr1 = { "Nike", "1 TO *", "0", "50", "false" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getProductByIdDataProvider(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] { ""
				+ styleIdNike.get(getRandomNumber(styleIdNike.size())) } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] CheckoutServiceDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] {
				"checkoutservicetest420@myntra.com", "123456" } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] CMSServiceDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] { ""
				+ styleIdNike.get(getRandomNumber(styleIdNike.size())) } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] IdpServiceDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] {
				"vasvani11@gmail.com", "onmobile" } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] getKeyValuePairDataProvider(ITestContext testContext) {
		String[] arr1 = { "shipping.charges.amount" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getAccountInfoDataProvider(ITestContext testContext) {
		String[] login1 = { "mohittest100@myntra.com" }; // @myntra id
		String[] login2 = { "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "randomuserid@gmail.com" }; // id does not exist
		Object[][] dataSet = new Object[][] { login1, login2, login3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] checkbalanceDataProvider(ITestContext testContext) {
		String[] arr1 = { "mohittest100@myntra.com", "false" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] getActiveNotificationsCountDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "mohit.jain@myntra.com", "myntra" };
		String[] arr2 = { "fnsdfdfghhfg@gmail.com", "myntra" };
		Object dataset[][] = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataset,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] refundAPIDataProvider(ITestContext testContext) {
		String[] arr1 = { "10734664", generateRandomId(), "0.01", "qa", "1.0" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] SearchServiceDP(ITestContext context) {
		Object[][] dataSet = new Object[][] { new String[] { "Nike", "10",
				"true", "false" } };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] SessionServiceDP(ITestContext context) {
		Object[][] dataSet = new Object[][] { new String[] {
				"vasvani11@gmail.com", "onmobile" } };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] StyleServiceDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] { ""
				+ styleIdNike.get(getRandomNumber(styleIdNike.size())) } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] styleDataForListOfStyleIdDP(ITestContext Context) {
		String[] arr1 = { styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size())) };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] pdpDataForSingleStyleIdDP(ITestContext Context) {
		String[] arr1 = { styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] pdpDataForMultipleStyleIdDP(ITestContext Context) {
		String[] arr3 = { styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size()))
				+ "," + styleIdNike.get(getRandomNumber(styleIdNike.size())) };
		Object[][] dataSet = new Object[][] { arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] pdpDataBySingleSkuIdDP(ITestContext Context) {
		String[] arr1 = { skuIdNike.get(getRandomNumber(skuIdNike.size())) + "" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 1, 2);
	}

	public Object[][] servicabilitycheck(ITestContext testContext) {
		String[] arr1 = { "560068" };
		String[] arr2 = { "560102" };
		String[] arr3 = { "560001" };
		String[] arr4 = { "560095" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] devApisSignInDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] {
				"checkoutservicetest420@myntra.com", "123456" } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] devApisStyleServiceDP(ITestContext testContext) {
		Object[][] dataSet = new Object[][] { new String[] { styleIdNike
				.get(getRandomNumber(styleIdNike.size())) + "" } };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] servicabilitycheckDP(ITestContext testContext) {
		String[] arr1 = { "560068" };
		String[] arr2 = { "560102" };
		String[] arr3 = { "560001" };
		String[] arr4 = { "560095" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] CRMPortalCustomerProfileServiceDP(ITestContext testContext) {
		String[] arr1 = new String[] { "portal-automation@myntra.com", "200",
				"706", "1", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMERPOrderServiceDP(ITestContext testContext) {
		String[] arr = { "start=5&sortBy=id&sortOrder=desc", "fetchSize=1" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMRightNowGetNEFTABankccountServiceDP(
			ITestContext testContext) {
		String[] arr = { "eshita.singh@myntra.com", "38" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] giftcardserviceDP(ITestContext testContext) {
		String[] arr1 = new String[] { ENVIRONMENT_FOX7,
				FOX7_VALID_GIFT_CARD_TYPE1, FOX7_VALID_GIFT_CARD_NUMBER1,
				FOX7_VALID_GIFT_CARD_PINCODE1, FOX7_VALID_GIFT_CARD_LOGIN1 };
		String[] arr2 = new String[] { ENVIRONMENT_FUNC,
				FUNCTIONAL_VALID_GIFT_CARD_TYPE1,
				FUNCTIONAL_VALID_GIFT_CARD_NUMBER1,
				FUNCTIONAL_VALID_GIFT_CARD_PINCODE1,
				FUNCTIONAL_VALID_GIFT_CARD_LOGIN1 };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(environment, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	public String generateRandomId() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}

	private int getRandomNumber(int size) {
		Random ran = new Random();
		return ran.nextInt(size);
	}

	@DataProvider
	public Object[][] CRMServiceDP_CustomerProfileDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr1 = new String[] { "portal-automation@myntra.com", "200",
				"706", "1", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMRightNowServiceDP_createcustomerprofile_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "manishkumar.gupta@myntra.com" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMRightNowServiceDP_getNEFTBankAccount_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "eshita.singh@myntra.com", "38" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

}
