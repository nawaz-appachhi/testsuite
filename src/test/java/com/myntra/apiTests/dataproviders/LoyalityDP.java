package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.DataOrc;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */

// test comment
public class LoyalityDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public LoyalityDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");

	}

	@DataProvider
	public Object[][] creditLoyaltyPointsDataProvider(ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// credit only active credit points
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "Fox7", "randomeuserhsdfdgfbg@gmail.com", "10", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "Fox7", "randomeuserhsdfdgfbg@gmail.com", "0", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional", "randomeuserhsdfdgfbg@gmail.com", "10",
				"0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr8 = { "Functional", "randomeuserhsdfdgfbg@gmail.com", "0",
				"0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr11 = { "Stage", "randomeuserhsdfdgfbg@gmail.com", "10",
				"0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr12 = { "Stage", "randomeuserhsdfdgfbg@gmail.com", "0", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditInActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// credit only inactive credit points
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7", "randomeuserhsdfdgfbg@gmail.com", "0", "10",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "Functional", "randomeuserhsdfdgfbg@gmail.com", "0",
				"10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr6 = { "Stage", "randomeuserhsdfdgfbg@gmail.com", "0", "10",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] creditBothActiveAndInactiveLoyaltyPointsDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7", "randomeuserhsdfdgfbg@gmail.com", "10", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "Functional", "randomeuserhsdfdgfbg@gmail.com", "10",
				"0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr6 = { "Stage", "randomeuserhsdfdgfbg@gmail.com", "10", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] creditLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// credit only active credit points
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit only inactive credit points
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit both active and inactive points
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// do not credit any extra active credit point and inactive credit point
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// change values for active debit points and inactive debit points-- no
		// effect on balance
		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "20", "automation", "ORDER", "123",
				"GOOD_WILL" };

		// credit only active credit points
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit only inactive credit points
		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit both active and inactive points
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// do not credit any extra active credit point and inactive credit point
		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// change values for active debit points and inactive debit points-- no
		// effect on balance
		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "20", "automation", "ORDER", "123",
				"GOOD_WILL" };
		// credit only active credit points
		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit only inactive credit points
		String[] arr12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit both active and inactive points
		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// do not credit any extra active credit point and inactive credit point
		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// change values for active debit points and inactive debit points-- no
		// effect on balance
		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "20", "automation", "ORDER", "123",
				"GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitLoyaltyPointsDataProvider(ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr6 = { "Fox7", "mohittest150@myntra.com", "10", "10", "10",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr11 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr12 = { "Functional", "mohittest150@myntra.com", "10", "10",
				"10", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr16 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr17 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr18 = { "Stage", "mohittest150@myntra.com", "10", "10",
				"10", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}

	@DataProvider
	public Object[][] debitActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// Do Not Debit Any Points for user which does not exist
		String[] arr1 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr2 = { "Fox7", "mohittest150@myntra.com", "0", "0", "10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr6 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		// Do Not Debit Any Points for user which does not exist
		String[] arr9 = { "Stage", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr10 = { "Stage", "mohittest150@myntra.com", "0", "0", "10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitLoyaltyPointsNegativeCasesDataProvider(
			ITestContext testContext) {

		// Expected 200 Response
		String[] arr1 = { "Fox7", "mohittest150@myntra.com", "0", "0", "-10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr2 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123ab", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDERyy", "123",
				"GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILLil" };

		// Expected 500 Internal Server Error
		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "-10", "automation", "ORDER", "123",
				"GOOD_WILLil" };

		String[] arr6 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"-10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr7 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"0", "0", "automation", "ORDER", "123ab", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDERyy", "123",
				"GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILLil" };

		// Expected 500 Internal Server Error
		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "-10", "automation", "ORDER", "123",
				"GOOD_WILLil" };
		// Expected 200 Response
		String[] arr11 = { "Stage", "mohittest150@myntra.com", "0", "0", "-10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr12 = { "Stage", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123ab", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDERyy", "123",
				"GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILLil" };

		// Expected 500 Internal Server Error
		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "-10", "automation", "ORDER", "123",
				"GOOD_WILLil" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitInActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {

		// Do Not Debit Any Points for user which does not exist
		String[] arr1 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr2 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for user which does not exist
		String[] arr5 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr6 = { "Functional", "mohittest150@myntra.com", "0", "0",
				"0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for user which does not exist
		String[] arr9 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr10 = { "Fox7", "mohittest150@myntra.com", "0", "0", "0",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr11 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr12 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitBothActiveAndInactiveLoyaltyPointsDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "400", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "400", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "2", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "400", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "400", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "2", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr11 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr12 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr13 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "400", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr14 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "400", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr15 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "2", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] loyaltyPoints_deleteGroupByName_negativeCases(
			ITestContext testContext) {

		String[] urlparam1 = { "Fox7", "1234" };
		String[] urlparam2 = { "Functional", "1234" };
		String[] urlparam3 = { "Stage", "1234" };
		Object[][] dataSet = new Object[][] { urlparam1, urlparam2, urlparam3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] transactionHistoryInactivePointsDataProvider(
			ITestContext testContext) {
		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "Fox7", "randomuserid@gmail.com" }; // id does not
																// exist
		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login5 = { "Functional", "mohittest100678@myntra.com" }; // @other
																			// id
		String[] login6 = { "Functional", "randomuserid@gmail.com" }; // id does
																		// not
																		// exist
		String[] login7 = { "Production", "mohit.jain@myntra.com" };
		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login9 = { "Stage", "mohittest100678@myntra.com" }; // @other
																		// id
		String[] login10 = { "Stage", "randomuserid@gmail.com" }; // id does not

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] transactionHistoryActivePointsDataProvider(
			ITestContext testContext) {

		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "Fox7", "randomuserid@gmail.com" }; // id does not
																// exist

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login5 = { "Functional", "mohittest100678@myntra.com" }; // @other
																			// id
		String[] login6 = { "Functional", "randomuserid@gmail.com" }; // id does
																		// not
																		// exist
		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login9 = { "Stage", "mohittest100678@myntra.com" }; // @other
																		// id
		String[] login10 = { "Stage", "randomuserid@gmail.com" }; // id does not
																	// exist

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getAccountInfoDataProvider(ITestContext testContext) {

		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "Fox7", "randomuserid@gmail.com" }; // id does not
																// exist

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login5 = { "Functional", "mohittest100678@myntra.com" }; // @other
																			// id
		String[] login6 = { "Functional", "randomuserid@gmail.com" }; // id does
																		// not
																		// exist

		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login9 = { "Stage", "mohittest100678@myntra.com" }; // @other
																		// id
		String[] login10 = { "Stage", "randomuserid@gmail.com" }; // id does not
																	// exist

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getAccountInfoDataProvider_negativeCases(
			ITestContext testContext) {

		// String[] login1 = { null };
		String[] login1 = { "Fox7", "123456" };
		String[] login2 = { "Functional", "123456" };
		String[] login3 = { "Stage", "123456" };

		Object[][] dataSet = new Object[][] { login1, login2, login3

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };

		String[] arr7 = { "Functional", "mohittest150fgfgfgf@gmail.com", "0",
				"6576916", "testing" };

		String[] arr8 = { "Functional", "mohittest150fgfgfgf@gmail.com", "2",
				"6576916", "testing" };
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr11 = { "Stage", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr12 = { "Stage", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyalityPointsOrderConfirmationNegativeCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "-2",
				"-10733949", "testing" };

		String[] arr4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "6576916", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-6576916", "testing" };

		String[] arr6 = { "Functional", "mohittest150fgfgfgf@gmail.com", "-2",
				"-6576916", "testing" };
		String[] arr7 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr9 = { "Stage", "mohittest150fgfgfgf@gmail.com", "-2",
				"-10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] creditLoyalityItemCancellationPositiveCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };

		String[] arr7 = { "Functional", "mohittest150fgfgfgf@gmail.com", "0",
				"6576916", "testing" };

		String[] arr8 = { "Functional", "mohittest150fgfgfgf@gmail.com", "2",
				"6576916", "testing" };
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr11 = { "Stage", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr12 = { "Stage", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyalityItemCancellationNegativeCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "-2",
				"-10733949", "testing" };

		String[] arr4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "6576916", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-6576916", "testing" };

		String[] arr6 = { "Functional", "mohittest150fgfgfgf@gmail.com", "-2",
				"-6576916", "testing" };
		String[] arr7 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr9 = { "Stage", "mohittest150fgfgfgf@gmail.com", "-2",
				"-10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] debitLoyalityOrderUsageDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "Fox7", "mohittest150@myntra.com", "10", "10733949",
				"testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150@myntra.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150@myntra.com", "10",
				"6576916", "testing" };
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr12 = { "Stage", "mohittest150@myntra.com", "10",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitLoyalityItemCancellationDataProvider_positiveCases(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150@myntra.com", "0", "10733949",
				"testing" };

		String[] arr4 = { "Fox7", "mohittest150@myntra.com", "10", "10733949",
				"testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };

		String[] arr7 = { "Functional", "mohittest150@myntra.com", "0",
				"6576916", "testing" };

		String[] arr8 = { "Functional", "mohittest150@myntra.com", "10",
				"6576916", "testing" };
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr11 = { "Stage", "mohittest150@myntra.com", "0", "10733949",
				"testing" };

		String[] arr12 = { "Stage", "mohittest150@myntra.com", "10",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitLoyalityItemCancellationDataProvider_negativeCases(
			ITestContext testContext) {

		// User ID exist, Debit Points - negative
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "10733949", "testing" };

		// User ID exist, Order ID - negative
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "-10733949", "testing" };

		// User ID exist, Debit Points and Order ID - both negative
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "-10733949", "testing" };

		// User ID does not exist, Debit Points - negative
		String[] arr4 = { "Fox7", "mohittest756@myntra.com", "-10", "10733949",
				"testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "6576916", "testing" };

		// User ID exist, Order ID - negative
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "-6576916", "testing" };

		// User ID exist, Debit Points and Order ID - both negative
		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "-6576916", "testing" };

		// User ID does not exist, Debit Points - negative
		String[] arr8 = { "Functional", "mohittest756@myntra.com", "-10",
				"6576916", "testing" };

		// Null Values
		// String[] arr5 = { null, "-10", "10733949", "testing" };
		// User ID exist, Debit Points - negative
		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "10733949", "testing" };

		// User ID exist, Order ID - negative
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "-10733949", "testing" };

		// User ID exist, Debit Points and Order ID - both negative
		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "-10733949", "testing" };

		// User ID does not exist, Debit Points - negative
		String[] arr12 = { "Stage", "mohittest756@myntra.com", "-10",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] acivateLoyalityPointsForOrderDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "Fox7", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "Fox7", "mohittest150fgfgf@myntra.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };

		String[] arr7 = { "Functional", "mohittest150fgfgf@myntra.com", "0",
				"6576916", "testing" };

		String[] arr8 = { "Functional", "mohittest150fgfgf@myntra.com", "2",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr11 = { "Stage", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };

		String[] arr12 = { "Stage", "mohittest150fgfgf@myntra.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] acivateLoyalityPointsForOrderDataProvider_negativeCases(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "-10733949", "testing" };

		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-6576916", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "-6576916", "testing" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "6576916", "testing" };

		String[] arr7 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "-10733949", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] mymyntraAccountInfoDataProvider(ITestContext testContext) {

		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login2 = { "Fox7", "mohitmj11@gmail.com" }; // @other id
		String[] login3 = { "Fox7", "dfjhndfh@gmail.com" }; // id does not exist

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login5 = { "Functional", "mohitmj11@gmail.com" }; // @other id
		String[] login6 = { "Functional", "dfjhndfh@gmail.com" }; // id does not
																	// exist

		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
																				// id
		String[] login9 = { "Stage", "mohitmj11@gmail.com" }; // @other id
		String[] login10 = { "Stage", "dfjhndfh@gmail.com" }; // id does not
																// exist

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] mymyntraAccountInfoDataProvider_negativeCases(
			ITestContext testContext) {

		// String[] login1 = { null };
		String[] login2 = { "Fox7", "123456" };
		String[] login3 = { "Functional", "123456" };
		String[] login4 = { "Stage", "123456" };

		Object[][] dataSet = new Object[][] { login2, login3, login4

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] acceptTermsAndConditionsDataProvider(
			ITestContext testContext) {

		String[] login1 = { "Fox7", generateRandonId() + "@gmail.com" };
		String[] login2 = { "Fox7", generateRandonId() + "@myntra.com" };
		String[] login3 = { "Fox7", generateRandonId() + "@hotmail.com" };
		String[] login4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		String[] login5 = { "Functional", generateRandonId() + "@gmail.com" };
		String[] login6 = { "Functional", generateRandonId() + "@myntra.com" };
		String[] login7 = { "Functional", generateRandonId() + "@hotmail.com" };
		String[] login8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		String[] login9 = { "Stage", generateRandonId() + "@gmail.com" };
		String[] login10 = { "Stage", generateRandonId() + "@myntra.com" };
		String[] login11 = { "Stage", generateRandonId() + "@hotmail.com" };
		String[] login12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10, login11,
				login12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	public String generateRandonId() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}

	@DataProvider
	public Object[][] computeTierInfoDataProvider(ITestContext testContext) {

		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohittest150@myntra.com" };
		String[] login3 = { "Fox7", "ddfdfhdjfhn@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohittest150@myntra.com" };
		String[] login6 = { "Functional", "ddfdfhdjfhn@gmail.com" };

		String[] login7 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login8 = { "Stage", "mohittest150@myntra.com" };
		String[] login9 = { "Stage", "ddfdfhdjfhn@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] processUsersTiersDataProvider(ITestContext testContext) {

		String[] arr1 = { "Fox7", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] processOrdersActivatePointsDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "Fox7", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] loyalityPointsConfigurationKeyValueDataProvider(
			ITestContext testContext) {

		String[] key_value1 = { "Fox7", "test9", "10" };
		String[] key_value2 = { "Functional", "test9", "10" };
		String[] key_value3 = { "Stage", "test9", "10" };
		Object[][] dataSet = new Object[][] { key_value1, key_value2,
				key_value3

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] loyalityPointsConfigurationKeyValue_negativeCases(
			ITestContext testContext) {

		// String[] key_value1 = { null, "10" };
		// String[] key_value2 = { "test", null };
		// String[] key_value3 = { null, null };
		String[] key_value1 = { "Fox7", "1234", "10" }; // numbers in key field
		String[] key_value2 = { "Fox7", "test", "abc" }; // alphabets in value
															// field
		String[] key_value3 = { "Fox7", "test", "-10" }; // negative numbers in
															// value
		// field

		String[] key_value4 = { "Functional", "1234", "10" }; // numbers in key
																// field
		String[] key_value5 = { "Functional", "test", "abc" }; // alphabets in
																// value field
		String[] key_value6 = { "Functional", "test", "-10" }; // negative
																// numbers in
																// value
		// field

		String[] key_value7 = { "Stage", "1234", "10" }; // numbers in key field
		String[] key_value8 = { "Stage", "test", "abc" }; // alphabets in value
															// field
		String[] key_value9 = { "Stage", "test", "-10" }; // negative numbers in
															// value
		// field

		Object[][] dataSet = new Object[][] { key_value1, key_value2,
				key_value3, key_value4, key_value5, key_value6, key_value7,
				key_value8, key_value9

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] geKkeyNameDataProvider_positiveCases(
			ITestContext testContext) {

		String[] key1 = { "Fox7", "loyaltyPointsTierAwardFactors" };
		String[] key2 = { "Fox7", "loyaltyPointsConversionFactor" };
		String[] key3 = { "Fox7", "loyaltyPointsUsageThreshold" };

		String[] key4 = { "Functional", "loyaltyPointsTierAwardFactors" };
		String[] key5 = { "Functional", "loyaltyPointsConversionFactor" };
		String[] key6 = { "Functional", "loyaltyPointsUsageThreshold" };

		String[] key7 = { "Stage", "loyaltyPointsTierAwardFactors" };
		String[] key8 = { "Stage", "loyaltyPointsConversionFactor" };
		String[] key9 = { "Stage", "loyaltyPointsUsageThreshold" };

		Object[][] dataSet = new Object[][] { key1, key2, key3, key4, key5,
				key6, key7, key8, key9

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getKeyNameDataProvider_negativeCases(
			ITestContext testContext) {

		// String[] key1 = { null };
		String[] key1 = { "Fox7", "1234" };
		String[] key2 = { "Functional", "1234" };
		String[] key3 = { "Stage", "1234" };

		Object[][] dataSet = new Object[][] { key1, key2, key3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] createLoyaltyGroupDataProvider(ITestContext testContext) {

		String[] arr1 = { "Fox7", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "Fox7", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "Fox7", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "Fox7", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "Fox7", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "Fox7", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "Fox7", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr8 = { "Functional", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr9 = { "Functional", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr10 = { "Functional", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr11 = { "Functional", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr12 = { "Functional", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr13 = { "Functional", "United Colors of Benetton", "Boys",
				"72", "TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr14 = { "Functional", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr15 = { "Stage", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr16 = { "Stage", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr17 = { "Stage", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr18 = { "Stage", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr19 = { "Stage", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr20 = { "Stage", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr21 = { "Stage", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}

	public String generateRandomId() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}

	/*
	 * @DataProvider public Object[][]
	 * createLoyaltyGroupNegativeCasesDataProvider_200Response( ITestContext
	 * testContext) {
	 * 
	 * String[] arr1 = { null, "Women", "43", "TestGroup" + generateRandomId(),
	 * "description1", "1" }; String[] arr2 = { "Nike", null, "43", "TestGroup"
	 * + generateRandomId(), "description2", "2" }; String[] arr3 = {
	 * "Joe Black", "Men", "51", null, "description4", "4" }; String[] arr4 = {
	 * "Unamia", "Girls", "71", "TestGroup" + generateRandomId(), null, "5" };
	 * 
	 * Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
	 * 
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 4, 3); }
	 */

	/*
	 * @DataProvider public Object[][]
	 * createLoyaltyGroupNegativeCasesDataProvider_500Response( ITestContext
	 * testContext) {
	 * 
	 * // String[] arr1 = { null, null, null, null, null, null };
	 * 
	 * String[] arr2 = { "Fastrack", "Women", null, "auto3", "description3", "3"
	 * };
	 * 
	 * String[] arr3 = { "United Colors of Benetton", "Boys", "72", "auto6",
	 * "description6", null };
	 * 
	 * Object[][] dataSet = new Object[][] { arr2, arr3 };
	 * 
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 2, 2); }
	 */

	@DataProvider
	public Object[][] indexstyleDataProvider(ITestContext testContext) {

		String[] arr1 = { "Fox7", "143056", "Roadster", "Women", "43" };
		String[] arr2 = { "Fox7", "153062", "Nike", "Women", "43" };
		String[] arr3 = { "Fox7", "25636", "Fastrack", "Women", "66" };
		String[] arr4 = { "Fox7", "139981", "Joe Black", "Men", "51" };
		String[] arr5 = { "Fox7", "179806", "Unamia", "Girls", "71" };
		String[] arr6 = { "Fox7", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr7 = { "Fox7", "175274", "WOLY", "Unisex", "403" };

		String[] arr8 = { "Functional", "143056", "Roadster", "Women", "43" };
		String[] arr9 = { "Functional", "153062", "Nike", "Women", "43" };
		String[] arr10 = { "Functional", "25636", "Fastrack", "Women", "66" };
		String[] arr11 = { "Functional", "139981", "Joe Black", "Men", "51" };
		String[] arr12 = { "Functional", "179806", "Unamia", "Girls", "71" };
		String[] arr13 = { "Functional", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr14 = { "Functional", "175274", "WOLY", "Unisex", "403" };

		String[] arr15 = { "Stage", "143056", "Roadster", "Women", "43" };
		String[] arr16 = { "Stage", "153062", "Nike", "Women", "43" };
		String[] arr17 = { "Stage", "25636", "Fastrack", "Women", "66" };
		String[] arr18 = { "Stage", "139981", "Joe Black", "Men", "51" };
		String[] arr19 = { "Stage", "179806", "Unamia", "Girls", "71" };
		String[] arr20 = { "Stage", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr21 = { "Stage", "175274", "WOLY", "Unisex", "403" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}

	@DataProvider
	public Object[][] indexstyleErrorCases200ResponseDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "Fox7", "175274", null, "Unisex", "403" };
		String[] arr2 = { "Fox7", "175274", "WOLY", null, "403" };

		String[] arr3 = { "Functional", "175274", null, "Unisex", "403" };
		String[] arr4 = { "Functional", "175274", "WOLY", null, "403" };

		String[] arr5 = { "Stage", "175274", null, "Unisex", "403" };
		String[] arr6 = { "Stage", "175274", "WOLY", null, "403" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6

		};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] indexstyleErrorCases500ResponseDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "Fox7", null, "WOLY", "Unisex", "403" };
		String[] arr2 = { "Fox7", "175274", "WOLY", "Unisex", null };

		String[] arr3 = { "Functional", null, "WOLY", "Unisex", "403" };
		String[] arr4 = { "Functional", "175274", "WOLY", "Unisex", null };

		String[] arr5 = { "Stage", null, "WOLY", "Unisex", "403" };
		String[] arr6 = { "Stage", "175274", "WOLY", "Unisex", null };

		// String[] arr3 = { null, null, null, null };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6

		};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] dp_activate_points_positive_cases_dataorc(
			ITestContext testContext) {

		/*
		 * String[] case1 = {
		 * "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT", "10" };
		 * // @myntra id String[] case2 = { "mohitmj11@gmail.com", "10" }; //
		 * other id String[] case3 = { "dfjhjj@dsjfh.com", "10" }; // id which
		 * does not
		 */// exist

		List<String> emailids = new ArrayList<String>();
		emailids.add("automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT");
		emailids.add("ashish.bajpai@myntra.com");
		emailids.add("vivek.vasvani@myntra.com");
		emailids.add("7681762@@@@@@._@myntra.com");
		emailids.add("ashishmyntra.com");

		List<String> integerval = new ArrayList<String>();
		integerval.add("10");
		integerval.add("20");
		integerval.add("30");
		integerval.add("-50");
		integerval.add("ajdhj");
		integerval.add("$%^$%^");

		DataOrc combinations = new DataOrc(emailids, integerval);

		return combinations.Explode();

		/*
		 * Object[][] dataSet = new Object[][] { new Object[] { case1 }, new
		 * Object[] { case2 }, new Object[] { case3 },
		 * 
		 * };
		 */
		// return
		// Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),
		// 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_creditLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_debitLoyaltyPoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr3 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr6 = { "Fox7", "mohittest150@myntra.com", "10", "10", "10",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr10 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr11 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr12 = { "Functional", "mohittest150@myntra.com", "10", "10",
				"10", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr14 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr15 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr16 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr17 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr18 = { "Stage", "mohittest150@myntra.com", "10", "10",
				"10", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_getTransactionHistoryOfInactivePoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" };
		String[] login3 = { "Fox7", "randomuserid@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohittest100678@myntra.com" };
		String[] login6 = { "Functional", "randomuserid@gmail.com" };
		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login9 = { "Stage", "mohittest100678@myntra.com" };
		String[] login10 = { "Stage", "randomuserid@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_getTransactionHistoryOfActivePoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" };
		String[] login3 = { "Fox7", "randomuserid@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohittest100678@myntra.com" };
		String[] login6 = { "Functional", "randomuserid@gmail.com" };
		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login9 = { "Stage", "mohittest100678@myntra.com" };
		String[] login10 = { "Stage", "randomuserid@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_getAccountInfo_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohittest100678@myntra.com" };
		String[] login3 = { "Fox7", "randomuserid@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohittest100678@myntra.com" };
		String[] login6 = { "Functional", "randomuserid@gmail.com" };

		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login9 = { "Stage", "mohittest100678@myntra.com" };
		String[] login10 = { "Stage", "randomuserid@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_creditLoyalityPointsOrderConfirmation_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150fgfgfgf@gmail.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150fgfgfgf@gmail.com", "2",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr12 = { "Stage", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_creditLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "Fox7", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150fgfgfgf@gmail.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150fgfgfgf@gmail.com", "2",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr12 = { "Stage", "mohittest150fgfgfgf@gmail.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_debitLoyalityOrderUsage_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "Fox7", "mohittest150@myntra.com", "10", "10733949",
				"testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150@myntra.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150@myntra.com", "10",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr12 = { "Stage", "mohittest150@myntra.com", "10",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_debitLoyalityItemCancellation_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "Fox7", "mohittest150@myntra.com", "10", "10733949",
				"testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150@myntra.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150@myntra.com", "10",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr12 = { "Stage", "mohittest150@myntra.com", "10",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_activateLoyalityPointsForOrder_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "Fox7", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "Fox7", "mohittest150fgfgf@myntra.com", "2",
				"10733949", "testing" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "6576916", "testing" };
		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "6576916", "testing" };
		String[] arr7 = { "Functional", "mohittest150fgfgf@myntra.com", "0",
				"6576916", "testing" };
		String[] arr8 = { "Functional", "mohittest150fgfgf@myntra.com", "2",
				"6576916", "testing" };

		String[] arr9 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr11 = { "Stage", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };
		String[] arr12 = { "Stage", "mohittest150fgfgf@myntra.com", "2",
				"10733949", "testing" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_myMyntraAccountInfo_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {

		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohitmj11@gmail.com" };
		String[] login3 = { "Fox7", "dfjhndfh@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohitmj11@gmail.com" };
		String[] login6 = { "Functional", "dfjhndfh@gmail.com" };

		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login9 = { "Stage", "mohitmj11@gmail.com" };
		String[] login10 = { "Stage", "dfjhndfh@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_acceptTermsAndConditions_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] login1 = { "Fox7", generateRandonId() + "@gmail.com" };
		String[] login2 = { "Fox7", generateRandonId() + "@myntra.com" };
		String[] login3 = { "Fox7", generateRandonId() + "@hotmail.com" };
		String[] login4 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		String[] login5 = { "Functional", generateRandonId() + "@gmail.com" };
		String[] login6 = { "Functional", generateRandonId() + "@myntra.com" };
		String[] login7 = { "Functional", generateRandonId() + "@hotmail.com" };
		String[] login8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		String[] login9 = { "Stage", generateRandonId() + "@gmail.com" };
		String[] login10 = { "Stage", generateRandonId() + "@myntra.com" };
		String[] login11 = { "Stage", generateRandonId() + "@hotmail.com" };
		String[] login12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10, login11,
				login12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_computeTierInfo_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] login1 = { "Fox7",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "Fox7", "mohittest150@myntra.com" };
		String[] login3 = { "Fox7", "ddfdfhdjfhn@gmail.com" };

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login5 = { "Functional", "mohittest150@myntra.com" };
		String[] login6 = { "Functional", "ddfdfhdjfhn@gmail.com" };

		String[] login7 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login8 = { "Stage", "mohittest150@myntra.com" };
		String[] login9 = { "Stage", "ddfdfhdjfhn@gmail.com" };

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_processUsersTiers_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_processOrdersActivatePoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_configurationKeyValue_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] key_value1 = { "Fox7", "test9", "10" };
		String[] key_value2 = { "Functional", "test9", "10" };
		String[] key_value3 = { "Stage", "test9", "10" };

		Object[][] dataSet = new Object[][] { key_value1, key_value2,
				key_value3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_createLoyaltyGroupData_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "Fox7", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "Fox7", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "Fox7", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "Fox7", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "Fox7", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "Fox7", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr8 = { "Functional", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr9 = { "Functional", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr10 = { "Functional", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr11 = { "Functional", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr12 = { "Functional", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr13 = { "Functional", "United Colors of Benetton", "Boys",
				"72", "TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr14 = { "Functional", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr15 = { "Stage", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr16 = { "Stage", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr17 = { "Stage", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr18 = { "Stage", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr19 = { "Stage", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr20 = { "Stage", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr21 = { "Stage", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 4);
	}

	@DataProvider
	public Object[][] LoyalityDP_deleteLoyaltyGroupById_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "Fox7", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "Fox7", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "Fox7", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "Fox7", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "Fox7", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "Fox7", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr8 = { "Functional", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr9 = { "Functional", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr10 = { "Functional", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr11 = { "Functional", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr12 = { "Functional", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr13 = { "Functional", "United Colors of Benetton", "Boys",
				"72", "TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr14 = { "Functional", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr15 = { "Stage", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr16 = { "Stage", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr17 = { "Stage", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr18 = { "Stage", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr19 = { "Stage", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr20 = { "Stage", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr21 = { "Stage", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 4);
	}

	@DataProvider
	public Object[][] LoyalityDP_deleteLoyaltyGroupByName_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "Fox7", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "Fox7", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "Fox7", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "Fox7", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "Fox7", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "Fox7", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr8 = { "Functional", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr9 = { "Functional", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr10 = { "Functional", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr11 = { "Functional", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr12 = { "Functional", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr13 = { "Functional", "United Colors of Benetton", "Boys",
				"72", "TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr14 = { "Functional", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		String[] arr15 = { "Stage", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr16 = { "Stage", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr17 = { "Stage", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr18 = { "Stage", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr19 = { "Stage", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr20 = { "Stage", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr21 = { "Stage", "WOLY", "Unisex", "403",
				"TestGroup" + generateRandomId(), "description7", "7" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 4);
	}

	@DataProvider
	public Object[][] LoyalityDP_indexStyleListLoyaltyGroup_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "Fox7", "143056", "Roadster", "Women", "43" };
		String[] arr2 = { "Fox7", "153062", "Nike", "Women", "43" };
		String[] arr3 = { "Fox7", "25636", "Fastrack", "Women", "66" };
		String[] arr4 = { "Fox7", "139981", "Joe Black", "Men", "51" };
		String[] arr5 = { "Fox7", "179806", "Unamia", "Girls", "71" };
		String[] arr6 = { "Fox7", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr7 = { "Fox7", "175274", "WOLY", "Unisex", "403" };

		String[] arr8 = { "Functional", "143056", "Roadster", "Women", "43" };
		String[] arr9 = { "Functional", "153062", "Nike", "Women", "43" };
		String[] arr10 = { "Functional", "25636", "Fastrack", "Women", "66" };
		String[] arr11 = { "Functional", "139981", "Joe Black", "Men", "51" };
		String[] arr12 = { "Functional", "179806", "Unamia", "Girls", "71" };
		String[] arr13 = { "Functional", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr14 = { "Functional", "175274", "WOLY", "Unisex", "403" };

		String[] arr15 = { "Stage", "143056", "Roadster", "Women", "43" };
		String[] arr16 = { "Stage", "153062", "Nike", "Women", "43" };
		String[] arr17 = { "Stage", "25636", "Fastrack", "Women", "66" };
		String[] arr18 = { "Stage", "139981", "Joe Black", "Men", "51" };
		String[] arr19 = { "Stage", "179806", "Unamia", "Girls", "71" };
		String[] arr20 = { "Stage", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr21 = { "Stage", "175274", "WOLY", "Unisex", "403" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 4);
	}
}