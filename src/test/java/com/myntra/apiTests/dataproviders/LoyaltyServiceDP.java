package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.DataOrc;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * @author Manishkumar.gupta
 *
 */

public class LoyaltyServiceDP extends BaseTest {
	APIUtilities apiUtil = new APIUtilities();
	static Initialize init = new Initialize("/Data/configuration");

	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public LoyaltyServiceDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");

	}

	@DataProvider
	public Object[][] creditLoyaltyPointsDataProvider(ITestContext testContext) {
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"20", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };


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

		Object[][] dataSet = new Object[][] { arr1, arr2,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyaltyPointsV2DataProvider(ITestContext testContext) {
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "GOOD_WILL", "1538418599000", "automation"};

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"30", "GOOD_WILL", "1538418599000", "automation" };

		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"40", "GOOD_WILL", "1538418599000", "automation" };

		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"20", "GOOD_WILL", "1538418599000", "automation" };

		String[] arr5 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr6 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr7 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr8 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr9 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr10 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "GOOD_WILL", "1538418599000", "automation"};

		String[] arr11 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"30", "GOOD_WILL", "1538418599000", "automation" };

		String[] arr12 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "GOOD_WILL", "1538418599000", "automation" };

		String[] arr13 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"20", "GOOD_WILL", "1538418599000", "automation" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	@DataProvider
	public Object[][] creditActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// credit only active credit points
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"20", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };


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

		Object[][] dataSet = new Object[][] { arr1, arr2, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditInActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// credit only inactive credit points
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa", "randomeuserhsdfdgfbg@gmail.com", "0", "10",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa", "randomeuserhsdfdgfbg@gmail.com", "10", "0",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit only inactive credit points
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// credit both active and inactive points
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// do not credit any extra active credit point and inactive credit point
		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// change values for active debit points and inactive debit points-- no
		// effect on balance
		String[] arr5 = { "sfqa",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr6 = { "sfqa", "mohittest150@myntra.com", "10", "10", "10",
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


		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}

	@DataProvider
	public Object[][] debitLoyaltyPointsV2DataProvider(ITestContext testContext) {
		String ppsID="ppsidtest"+generateRandomNumber()+generateRandomString();
		String ppsTransactionID="ppsTransactionIDtest"+generateRandomNumber()+generateRandomString();
		String orderId="10010"+generateRandomNumber();
		String[] arr1 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxTDebit",
				"300", ppsID, ppsTransactionID, orderId, "USAGE_ON_ORDER"};
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxTDebit",
				"300", ppsID, ppsTransactionID, orderId, "USAGE_ON_ORDER"};


		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 3, 4);
	}

	@DataProvider
	public Object[][] debitActiveLoyaltyPointsPositiveCasesDataProvider(
			ITestContext testContext) {
		// Do Not Debit Any Points for user which does not exist
		String[] arr1 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr2 = { "sfqa", "mohittest150@myntra.com", "0", "0", "10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr4 = { "sfqa",
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
		String[] arr1 = { "sfqa", "mohittest150@myntra.com", "0", "0", "-10",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr2 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123ab", "GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDERyy", "123",
				"GOOD_WILL" };

		// Expected 500 Internal Server Error
		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILLil" };

		// Expected 500 Internal Server Error
		String[] arr5 = { "sfqa",
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
		String[] arr1 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr2 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr4 = { "sfqa",
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
		String[] arr9 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"0", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for user which does not exist
		String[] arr10 = { "sfqa", "mohittest150@myntra.com", "0", "0", "0",
				"10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Debit Points for existing user
		String[] arr11 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };

		// Do Not Debit Any Points for existing user
		String[] arr12 = { "sfqa",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "400", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "400", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr5 = { "sfqa",
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

		String[] arr11 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "0", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr12 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr13 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "200", "400", "automation", "ORDER", "123",
				"GOOD_WILL" };

		String[] arr14 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "2", "400", "automation", "ORDER", "123", "GOOD_WILL" };

		String[] arr15 = { "sfqa",
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

		String[] urlparam1 = { "sfqa", "1234" };
		String[] urlparam2 = { "Functional", "1234" };
		String[] urlparam3 = { "Stage", "1234" };
		Object[][] dataSet = new Object[][] { urlparam1, urlparam2, urlparam3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] transactionHistoryInactivePointsDataProvider(
			ITestContext testContext) {
		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login2 = { "sfqa", "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "sfqa", "randomuserid@gmail.com" }; // id does not
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

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login2 = { "sfqa", "mohittest100678@myntra.com" }; // @other id
		String[] login3 = { "sfqa", "randomuserid@gmail.com" }; // id does not
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

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login2 = { "sfqa", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @other id
		String[] login3 = { "sfqa", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // id does not
		// exist

		String[] login4 = { "Functional",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login5 = { "Functional", "mohittest100678@myntra.com" }; // @other
		// id
		String[] login6 = { "Functional", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // id does
		// not
		// exist

		String[] login7 = { "Production", "mohit.jain@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login9 = { "Stage", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @other
		// id
		String[] login10 = { "Stage", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // id does not
		// exist

		Object[][] dataSet = new Object[][] { login1, login2, login3, login4,
				login5, login6, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getAccountBalanceV2DataProvider(ITestContext testContext) {

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		// exist

		String[] login7 = { "Production", "achal.sharma@myntra.com" };

		String[] login8 = { "Stage",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login9 = { "Stage", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @other
		// id
		String[] login10 = { "Stage", "automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // id does not
		// exist

		Object[][] dataSet = new Object[][] { login1, login7, login8, login9, login10 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getAccountInfoDataProvider_negativeCases(
			ITestContext testContext) {

		// String[] login1 = { null };
		String[] login1 = { "sfqa", "123456" };
		String[] login2 = { "Functional", "123456" };
		String[] login3 = { "Stage", "123456" };

		Object[][] dataSet = new Object[][] { login1, login2, login3

		};
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars
					.length())));
		}

		return sb.toString();
	}

	@DataProvider
	public Object[][] creditLoyalityPointsBulkCreditPositiveCasesDataProvider(
			ITestContext testContext) {
		String batchId=generateRandomChars("1234567890", 12);
		String transactionId=generateRandomChars("1234567890", 4);
		String[] arr1 = { "sfqa",transactionId, batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","30","1550051705000","20","1560419705000","10","1547373305000" };

		String[] arr2 = { "stage",transactionId,batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64btest1","20","1560419705000"};
		String[] arr3 = { "stage",transactionId, batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64btest2","10","1547373305000"};


		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyalityPointsBulkCreditPositiveCasesWithExpiredPointsDataProvider(
			ITestContext testContext) {
		String batchId=generateRandomChars("1234567890", 12);
		String transactionId=generateRandomChars("1234567890", 4);
		String[] arr1 = { "sfqa",transactionId, batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","30","1550051705000","20","1560411","-10","1547373305000" };

		String[] arr2 = { "stage",transactionId,batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64btest1","20","1560419705000"};
		String[] arr3 = { "stage",transactionId, batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64btest2","10","1547373305000"};


		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	@DataProvider
	public Object[][] creditLoyalityPointsBulkCreditNegativeCasesDataProvider(
			ITestContext testContext) {
		String batchId=generateRandomChars("1234567890", 12);
		String transactionId=generateRandomChars("1234567890", 4);
		String[] arr1 = { "sfqa","634728422088", "2457","automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","-30","1550051705000","-20","1560411","-10","1547373305000" };
		String[] arr2 = { "sfqa","634728422088", "2457","automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","30","1550051705000","20","1560411","-10","1547373305000" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyalityPointsBulkCreditAllNegativePointsCasesDataProvider(
			ITestContext testContext) {
		String batchId=generateRandomChars("1234567890", 12);
		String transactionId=generateRandomChars("1234567890", 4);
		String batchId1=generateRandomChars("1234567890", 12);
		String transactionId1=generateRandomChars("1234567890", 4);
		String[] arr1 = { "sfqa",transactionId, batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","-30","1550051705000","-20","1560411","-10","1547373305000" };
		String[] arr2 = { "sfqa",transactionId1, batchId1,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","30","15500517","20","1560411","10","15305000" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] debitLoyalityPointsBulkDebitPositiveCasesDataProvider(
			ITestContext testContext) {
		String batchId=generateRandomChars("1234567890", 12);
		String[] arr1 = { "sfqa", batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };

		String[] arr2 = { "Functional", batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };
		String[] arr3 = { "Stage", batchId,"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	@DataProvider
	public Object[][] debitLoyalityPointsBulkDebitNegativeCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "sfqa", "441715565618","automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };

		String[] arr2 = { "Functional", "441715565618","automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };
		String[] arr3 = { "Stage", "441715565618","automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT","74920202.a282.468b.9ee0.6fd2356b7fbedtzVSBj2M1","achal@myntra.com" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	@DataProvider
	public Object[][] creditLoyalityPointsOrderConfirmationPositiveCasesDataProvider(
			ITestContext testContext) {
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "-2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "-2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "sfqa", "mohittest150@myntra.com", "10", "10733949",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150@myntra.com", "0", "10733949",
				"testing" };

		String[] arr4 = { "sfqa", "mohittest150@myntra.com", "10", "10733949",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "10733949", "testing" };

		// User ID exist, Order ID - negative
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "-10733949", "testing" };

		// User ID exist, Debit Points and Order ID - both negative
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-10", "-10733949", "testing" };

		// User ID does not exist, Debit Points - negative
		String[] arr4 = { "sfqa", "mohittest756@myntra.com", "-10", "10733949",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };

		String[] arr3 = { "sfqa", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };

		String[] arr4 = { "sfqa", "mohittest150fgfgf@myntra.com", "2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "-10733949", "testing" };

		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"-2", "-10733949", "testing" };

		String[] arr3 = { "sfqa",
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

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" }; // @myntra
		// id
		String[] login2 = { "sfqa", "mohitmj11@gmail.com" }; // @other id
		String[] login3 = { "sfqa", "dfjhndfh@gmail.com" }; // id does not exist

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
		String[] login2 = { "sfqa", "123456" };
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

		String[] login1 = { "sfqa", generateRandonId() + "@gmail.com" };
		String[] login2 = { "sfqa", generateRandonId() + "@myntra.com" };
		String[] login3 = { "sfqa", generateRandonId() + "@hotmail.com" };
		String[] login4 = { "sfqa",
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

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohittest150@myntra.com" };
		String[] login3 = { "sfqa", "ddfdfhdjfhn@gmail.com" };

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

		String[] arr1 = { "sfqa", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };

		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] processOrdersActivatePointsDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "sfqa", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] loyalityPointsConfigurationKeyValueDataProvider(
			ITestContext testContext) {

		String[] key_value1 = { "sfqa", "test9", "10" };
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
		String[] key_value1 = { "sfqa", "1234", "10" }; // numbers in key field
		String[] key_value2 = { "sfqa", "test", "abc" }; // alphabets in value
		// field
		String[] key_value3 = { "sfqa", "test", "-10" }; // negative numbers in
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

		String[] key1 = { "sfqa", "loyaltyPointsTierAwardFactors" };
		String[] key2 = { "sfqa", "loyaltyPointsConversionFactor" };
		String[] key3 = { "sfqa", "loyaltyPointsUsageThreshold" };

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
		String[] key1 = { "sfqa", "1234" };
		String[] key2 = { "Functional", "1234" };
		String[] key3 = { "Stage", "1234" };

		Object[][] dataSet = new Object[][] { key1, key2, key3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] createLoyaltyGroupDataProvider(ITestContext testContext) {

		String[] arr1 = { "sfqa", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "sfqa", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "sfqa", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "sfqa", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "sfqa", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "sfqa", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "sfqa", "WOLY", "Unisex", "403",
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

	@DataProvider
	public Object[][] indexstyleDataProvider(ITestContext testContext) {

		String[] arr1 = { "sfqa", "143056", "Roadster", "Women", "43" };
		String[] arr2 = { "sfqa", "153062", "Nike", "Women", "43" };
		String[] arr3 = { "sfqa", "25636", "Fastrack", "Women", "66" };
		String[] arr4 = { "sfqa", "139981", "Joe Black", "Men", "51" };
		String[] arr5 = { "sfqa", "179806", "Unamia", "Girls", "71" };
		String[] arr6 = { "sfqa", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr7 = { "sfqa", "175274", "WOLY", "Unisex", "403" };

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

		String[] arr1 = { "sfqa", "175274", null, "Unisex", "403" };
		String[] arr2 = { "sfqa", "175274", "WOLY", null, "403" };

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

		String[] arr1 = { "sfqa", null, "WOLY", "Unisex", "403" };
		String[] arr2 = { "sfqa", "175274", "WOLY", "Unisex", null };

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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "sfqa",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "12", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr3 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "0", "10", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr4 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "0", "10", "0", "automation", "ORDER", "123", "GOOD_WILL" };
		String[] arr5 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"10", "10", "10", "10", "automation", "ORDER", "123",
				"GOOD_WILL" };
		String[] arr6 = { "sfqa", "mohittest150@myntra.com", "10", "10", "10",
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
		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohittest100678@myntra.com" };
		String[] login3 = { "sfqa", "randomuserid@gmail.com" };

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
		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohittest100678@myntra.com" };
		String[] login3 = { "sfqa", "randomuserid@gmail.com" };

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
		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohittest100678@myntra.com" };
		String[] login3 = { "sfqa", "randomuserid@gmail.com" };

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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "sfqa", "mohittest150fgfgfgf@gmail.com", "2",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2000", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "sfqa", "mohittest150@myntra.com", "10", "10733949",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150@myntra.com", "0", "10733949",
				"testing" };
		String[] arr4 = { "sfqa", "mohittest150@myntra.com", "10", "10733949",
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
		String[] arr1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"2", "10733949", "testing" };
		String[] arr2 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT",
				"0", "10733949", "testing" };
		String[] arr3 = { "sfqa", "mohittest150fgfgf@myntra.com", "0",
				"10733949", "testing" };
		String[] arr4 = { "sfqa", "mohittest150fgfgf@myntra.com", "2",
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

		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohitmj11@gmail.com" };
		String[] login3 = { "sfqa", "dfjhndfh@gmail.com" };

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
		String[] login1 = { "sfqa", generateRandonId() + "@gmail.com" };
		String[] login2 = { "sfqa", generateRandonId() + "@myntra.com" };
		String[] login3 = { "sfqa", generateRandonId() + "@hotmail.com" };
		String[] login4 = { "sfqa",
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
		String[] login1 = { "sfqa",
				"automation-733a57d2.3ad0.47ac.a2fa.f1e059c1b5f64b3s5vkKxT" };
		String[] login2 = { "sfqa", "mohittest150@myntra.com" };
		String[] login3 = { "sfqa", "ddfdfhdjfhn@gmail.com" };

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
		String[] arr1 = { "sfqa", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_processOrdersActivatePoints_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "sfqa", " 10", "14", "2014" };
		String[] arr2 = { "Functional", " 10", "14", "2014" };
		String[] arr3 = { "Stage", " 10", "14", "2014" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(Env, dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] LoyalityDP_configurationKeyValue_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] key_value1 = { "sfqa", "test9", "10" };
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
		String[] arr1 = { "sfqa", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "sfqa", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "sfqa", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "sfqa", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "sfqa", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "sfqa", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "sfqa", "WOLY", "Unisex", "403",
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
		String[] arr1 = { "sfqa", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "sfqa", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "sfqa", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "sfqa", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "sfqa", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "sfqa", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "sfqa", "WOLY", "Unisex", "403",
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
		String[] arr1 = { "sfqa", "Roadster", "Women", "43",
				"TestGroup" + generateRandomId(), "description1", "1" };
		String[] arr2 = { "sfqa", "Nike", "Women", "43",
				"TestGroup" + generateRandomId(), "description2", "2" };
		String[] arr3 = { "sfqa", "Fastrack", "Women", "66",
				"TestGroup" + generateRandomId(), "description3", "3" };
		String[] arr4 = { "sfqa", "Joe Black", "Men", "51",
				"TestGroup" + generateRandomId(), "description4", "4" };
		String[] arr5 = { "sfqa", "Unamia", "Girls", "71",
				"TestGroup" + generateRandomId(), "description5", "5" };
		String[] arr6 = { "sfqa", "United Colors of Benetton", "Boys", "72",
				"TestGroup" + generateRandomId(), "description6", "6" };
		String[] arr7 = { "sfqa", "WOLY", "Unisex", "403",
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
		String[] arr1 = { "sfqa", "143056", "Roadster", "Women", "43" };
		String[] arr2 = { "sfqa", "153062", "Nike", "Women", "43" };
		String[] arr3 = { "sfqa", "25636", "Fastrack", "Women", "66" };
		String[] arr4 = { "sfqa", "139981", "Joe Black", "Men", "51" };
		String[] arr5 = { "sfqa", "179806", "Unamia", "Girls", "71" };
		String[] arr6 = { "sfqa", "183127", "United Colors of Benetton",
				"Boys", "72" };
		String[] arr7 = { "sfqa", "175274", "WOLY", "Unisex", "403" };

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

	@DataProvider
	public Object[][] gerUser(ITestContext testContext) {
		String[] arr1 = { "lyaltytesting@myntra.com" };
		String[] arr2 = { "lyaltytestingnew@myntra.com" };
		String[] arr3 = { "cashbackservice@myntra.com" };
		String[] arr4 = { "achal@myntra.com" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] debitloyaltyDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String[] arr1 = { checksum, random_number, random_String,
				random_number, "5000", "lyaltytesting@myntra.com", "ORDER" };
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String1, random_number1,
				random_String1, random_number1, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String[] arr2 = { checksum1, random_number1, random_String1,
				random_number1, "5000", "lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] debitloyaltyMorethanActivePointDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "7000",
				"lyaltypointtesting@myntra.com", "ORDER");
		String[] arr1 = { checksum, random_number, random_String,
				random_number, "7000", "lyaltypointtesting@myntra.com", "ORDER" };
		// String random_number1 = generateRandomNumber();
		// String random_String1 = generateRandomString();
		// String checksum1 =
		// getDebitChecksum(random_String1,random_number1,random_String1,random_number,"7000","lyaltytesting1@myntra.com","ORDER");
		// String[] arr2 = {
		// checksum1,random_number1,random_String1,random_number1,"7000","lyaltypointtesting1@myntra.com","ORDER"};

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] debitloyaltyNegativePointDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "-3000",
				"lyaltypointtesting@myntra.com", "ORDER");
		String[] arr1 = { checksum, random_number, random_String,
				random_number, "-3000", "lyaltypointtesting@myntra.com",
				"ORDER" };
		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String1, random_number1,
				random_String1, random_number1, "-2000",
				"lyaltypointtesting1@myntra.com", "ORDER");
		String[] arr2 = { checksum1, random_number1, random_String1,
				random_number1, "-2000", "lyaltypointtesting1@myntra.com",
				"ORDER" };
		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum2 = getDebitChecksum(random_String2, random_number2,
				random_String2, random_number2, "-400",
				"lyaltypointtesting@myntra.com", "ORDER");
		String[] arr3 = { checksum2, random_number2, random_String2,
				random_number2, "-400", "lyaltypointtesting@myntra.com",
				"ORDER" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public Object[][] debitloyaltyLessThanPointDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "0.5",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		System.out.println("debit Checksum======== " + checksum);
		String[] arr1 = { checksum, random_number, random_String,
				random_number, "0.5",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] getStatus(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "1000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "1000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String1, random_number1,
				random_String1, random_number1, "1000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(checksum1,
				random_number1, random_String1, random_number1, "1000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String statusChecksum = getStatusChecksum(random_String3,
				random_number3, clientTransactionID, random_number3);
		String[] arr1 = { clientTransactionID, statusChecksum, random_String3,
				random_number3 };

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String statusChecksum1 = getStatusChecksum(random_String4,
				random_number4, clientTransactionID1, random_number4);
		String[] arr2 = { clientTransactionID1, statusChecksum1,
				random_String4, random_number4 };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] refundloyaltyDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String checksum3 = getDebitChecksum(random_String3, random_number3,
				random_String3, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(checksum3,
				random_number3, random_String3, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);
		// String random_number1 = generateRandomNumber();
		// String random_String1 = generateRandomString();
		String checksum1 = getRefundChecksum(random_String, random_number,
				random_String, clientTransactionID, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String[] arr1 = { checksum1, random_number, random_String,
				clientTransactionID, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER" };
		System.out.println("Transaction Id ---- " + clientTransactionID);
		// String random_number2 = generateRandomNumber();
		// String random_String2 = generateRandomString();
		String checksum2 = getRefundChecksum(random_String3, random_number3,
				random_String3, clientTransactionID1, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String[] arr2 = { checksum2, random_number3, random_String3,
				clientTransactionID1, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] voidTransactionDP(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String Debitchecksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		// String clientTransactionID1=
		// getClientTransactionID1(generateRandomString(),generateRandomNumber()
		// ,generateRandomString()
		// ,generateRandomNumber(),"5000","lyaltytestingnew@myntra.com","ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);
		// String voidChecksum = getvoidChecksum(random_String, random_number,
		// random_String,clientTransactionID);
		String voidChecksum = getvoidChecksum(random_String, random_number,
				random_String, clientTransactionID);

		String[] arr1 = { voidChecksum, random_number, random_String,
				clientTransactionID,"5000", "lyaltytesting@myntra.com", "ORDER" };
		// String[] arr1 = {
		// voidChecksum,generateRandomNumber(),generateRandomString(),clientTransactionID,"lyaltytesting@myntra.com"};
		// String[] arr2 = {
		// generateRandomString(),generateRandomNumber(),generateRandomString(),clientTransactionID1,"lyaltytestingnew@myntra.com"};

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] checksum(ITestContext testContext) {

		String[] arr1 = { generateRandomString(), generateRandomNumber(),
				generateRandomString(), generateRandomNumber(), "5000",
				"lyaltytesting@myntra.com", "ORDER" };
		// String[] arr2 = {
		// generateRandomString(),generateRandomNumber(),generateRandomString(),clientTransactionID1,"lyaltytestingnew@myntra.com"};

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] voidInvalidTransactionDP(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String Debitchecksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String Debitchecksum1 = getDebitChecksum(random_String1,
				random_number1, random_String1, random_number1, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(Debitchecksum1,
				random_number1, random_String1, random_number1, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String voidCheckscum = getvoidChecksum(random_String2, random_number2,
				random_String2, random_number2);
		String[] arr1 = { voidCheckscum, random_number2, random_String2,
				random_number2,"5000", "lyaltytesting@myntra.com" , "ORDER"};

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidCheckscum1 = getvoidChecksum(random_String3, random_number3,
				random_String3, random_number3);
		String[] arr2 = { voidCheckscum1, random_number3, random_String3,
				random_number3,"5000", "lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] voidTransactionOnRefundedDP(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String refundCheckSum = getRefundChecksum(random_String1,
				random_number1, random_String1, clientTransactionID,
				random_number, "5000", "lyaltytesting@myntra.com", "ORDER");
		CreateRefund(refundCheckSum, random_number1, random_String1,
				clientTransactionID, random_number1, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String2, random_number2,
				random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(checksum1,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String refundCheckSum2 = getRefundChecksum(random_String3,
				random_number3, random_String3, clientTransactionID1,
				random_number3, "5000", "lyaltytestingnew@myntra.com", "ORDER");
		CreateRefund(refundCheckSum2, random_number3, random_String3,
				clientTransactionID1, random_number3, "500",
				"lyaltytestingnew@myntra.com", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String voidchecksum = getvoidChecksum(random_String4, random_number4,
				random_String4, clientTransactionID);
		String[] arr1 = { voidchecksum, generateRandomNumber(), random_String4,
				clientTransactionID, "5000", "lyaltytesting@myntra.com", "ORDER" };

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String voidchecksum1 = getvoidChecksum(random_String5, random_number5,
				random_String5, clientTransactionID1);
		String[] arr2 = { voidchecksum1, generateRandomNumber(),
				random_String4, clientTransactionID1, "5000",
				"lyaltytestingnew@myntra.com" , "ORDER"};

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] voidTransactionOnRefundedTxnDP(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String refundCheckSum = getRefundChecksum(random_String1,
				random_number1, random_String1, clientTransactionID,
				random_number1, "5000", "lyaltytesting@myntra.com", "ORDER");
		String RefundTransactionID = CreateRefund(refundCheckSum,
				random_number1, random_String1, clientTransactionID,
				random_number1, "5000", "lyaltytesting@myntra.com", "ORDER");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String2, random_number2,
				random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(checksum1,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String refundCheckSum1 = getRefundChecksum(random_String3,
				random_number3, random_String3, clientTransactionID1,
				random_number3, "5000", "lyaltytestingnew@myntra.com", "ORDER");
		String RefundTransactionID1 = CreateRefund(refundCheckSum1,
				random_number3, random_String3, clientTransactionID1,
				random_number3, "5000", "lyaltytestingnew@myntra.com", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String voidChecksum = getvoidChecksum(random_String4, random_number4,
				random_String4, RefundTransactionID);
		String[] arr1 = { voidChecksum, random_number4, random_String4,
				RefundTransactionID,"5000", "lyaltytesting@myntra.com", "ORDER" };

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String voidChecksum2 = getvoidChecksum(random_String5, random_number5,
				random_String5, RefundTransactionID1);
		String[] arr2 = { voidChecksum2, random_number5, random_String5,
				RefundTransactionID1,"4000", "lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] voidTransactionAlreadyVoideDP(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String checksum1 = getDebitChecksum(random_String1, random_number1,
				random_String1, random_number1, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(checksum1,
				random_number1, random_String1, random_number1, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String voidChecksum = getvoidChecksum(random_String2, random_number2,
				random_String2, clientTransactionID);
		String[] arr1 = { voidChecksum, random_number2, random_String2,
				clientTransactionID, "5000", "lyaltytesting@myntra.com", "ORDER" };

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidChecksum2 = getvoidChecksum(random_String3, random_number3,
				random_String3, clientTransactionID);
		String[] arr2 = { voidChecksum2, random_number3, random_String3,
				clientTransactionID, "4000", "lyaltytesting@myntra.com", "ORDER" };

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String voidChecksum3 = getvoidChecksum(random_String4, random_number4,
				random_String4, clientTransactionID1);
		String[] arr3 = { voidChecksum3, random_number4, random_String4,
				clientTransactionID1, "1000", "lyaltytestingnew@myntra.com", "ORDER" };

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String voidChecksum4 = getvoidChecksum(random_String5, random_number5,
				random_String5, clientTransactionID1);
		String[] arr4 = { voidChecksum4, random_number5, random_String5,
				clientTransactionID1, "8000", "lyaltytestingnew@myntra.com", "ORDER" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] refundloyaltyForVoidedTransactionDP(
			ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String Debitchecksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String clientTransactionID = getClientTransactionID1(Debitchecksum,
				random_number, random_String, random_number, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String voidchecksum = getvoidChecksum(random_String1, random_number1,
				random_String1, clientTransactionID);
		CreateVoidTransaction(voidchecksum, random_number1, random_String1,
				clientTransactionID, "lyaltytesting@myntra.com");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String Debitchecksum2 = getDebitChecksum(random_String2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String clientTransactionID1 = getClientTransactionID1(Debitchecksum2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidchecksum2 = getvoidChecksum(random_String3, random_number3,
				random_String3, clientTransactionID1);
		CreateVoidTransaction(voidchecksum2, random_number3, random_String3,
				clientTransactionID1, "lyaltytestingnew@myntra.com");

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String Debitchecksum4 = getRefundChecksum(random_String4,
				random_number4, random_String4, clientTransactionID,
				random_number4, "5000", "lyaltytesting@myntra.com", "ORDER");
		String[] arr1 = { Debitchecksum4, random_number4, random_String4,
				clientTransactionID, random_number4, "5000",
				"lyaltytesting@myntra.com", "ORDER" };

		// String random_number5 = generateRandomNumber();
		// String random_String5 = generateRandomString();
		// String Debitchecksum5 =
		// getRefundChecksum(random_String5,random_number5,random_String5,clientTransactionID1,random_number5,"5000","lyaltytesting@myntra.com","ORDER");
		// String[] arr3 = {
		// Debitchecksum5,random_number5,random_String5,clientTransactionID1,random_number5,"5000","lyaltytestingnew@myntra.com","ORDER"};

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] refundloyaltyDP_withSameTXN(ITestContext testContext) {

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String Debitchecksum1 = getDebitChecksum(random_String1,
				random_number1, random_String1, random_number1, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum1,
				random_number1, random_String1, random_number1, "5000",
				"lyaltytesting@myntra.com", "ORDER");

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String Debitchecksum2 = getDebitChecksum(random_String2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(Debitchecksum2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String voidChecksum = getRefundChecksum(random_String3, random_number3,
				random_String3, clientTransactionID, random_number3, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String[] arr1 = { voidChecksum, random_number3, random_String3,
				clientTransactionID, random_number3, "5000",
				"lyaltytesting@myntra.com", "ORDER" };

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String voidChecksum2 = getRefundChecksum(random_String4,
				random_number4, random_String4, clientTransactionID,
				random_number4, "5000", "lyaltytesting@myntra.com", "ORDER");
		String[] arr2 = { voidChecksum2, random_number4, random_String4,
				clientTransactionID, random_number4, "5000",
				"lyaltytesting@myntra.com", "ORDER" };

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String voidChecksum3 = getRefundChecksum(random_String5,
				random_number5, random_String5, clientTransactionID1,
				random_number5, "5000", "lyaltytestingnew@myntra.com", "ORDER");
		String[] arr3 = { voidChecksum3, random_number5, random_String5,
				clientTransactionID1, random_number5, "5000",
				"lyaltytestingnew@myntra.com", "ORDER" };

		String random_number6 = generateRandomNumber();
		String random_String6 = generateRandomString();
		String voidChecksum4 = getRefundChecksum(random_String6,
				random_number6, random_String6, clientTransactionID1,
				random_number6, "5000", "lyaltytestingnew@myntra.com", "ORDER");
		String[] arr4 = { voidChecksum4, random_number6, random_String6,
				clientTransactionID1, random_number6, "5000",
				"lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] refundloyaltyDP_MoreThan_Debit(ITestContext testContext) {

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String Debitchecksum1 = getDebitChecksum(random_String1,
				random_number1, random_String1, random_number1, "1000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		System.out.println("debitchecskjcs========== " + Debitchecksum1);

		String clientTransactionID = getClientTransactionID1(Debitchecksum1,
				random_number1, random_String1, random_number1, "1000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String Debitchecksum2 = getDebitChecksum(random_String2,
				random_number2, random_String2, random_number2, "500",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(Debitchecksum2,
				random_number2, random_String2, random_number2, "500",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String refundChecksum = getRefundChecksum(random_String3,
				random_number3, random_String3, clientTransactionID,
				random_number3, "5000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");

		String[] arr1 = { refundChecksum, random_number3, random_String3,
				clientTransactionID, random_number3, "5000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER" };

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String refundChecksum2 = getRefundChecksum(random_String4,
				random_number4, random_String4, clientTransactionID1,
				random_number4, "3000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		String[] arr2 = { refundChecksum2, random_number4, random_String4,
				clientTransactionID1, random_number4, "3000",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] refundloyaltyDP_LessThan_Debit(ITestContext testContext) {

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String Debitchecksum1 = getDebitChecksum(random_String1,
				random_number1, random_String1, random_number1, "6000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum1,
				random_number1, random_String1, random_number1, "6000",
				"lyaltytesting@myntra.com", "ORDER");
		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String Debitchecksum2 = getDebitChecksum(random_String2,
				random_number2, random_String2, random_number2, "6000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(Debitchecksum2,
				random_number2, random_String2, random_number2, "6000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String refundChecksum = getRefundChecksum(random_String3,
				random_number3, random_String3, clientTransactionID,
				random_number3, "5000", "lyaltytesting@myntra.com", "ORDER");
		String[] arr1 = { refundChecksum, random_number3, random_String3,
				clientTransactionID, random_number3, "5000",
				"lyaltytesting@myntra.com", "ORDER" };

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String refundChecksum2 = getRefundChecksum(random_String4,
				random_number4, random_String4, clientTransactionID1,
				random_number4, "5000", "lyaltytestingnew@myntra.com", "ORDER");
		String[] arr2 = { refundChecksum2, random_number4, random_String4,
				clientTransactionID1, random_number4, "5000",
				"lyaltytestingnew@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] refundloyaltyDP_ToInvalidEMail(ITestContext testContext) {

		String random_number2 = generateRandomNumber();
		String random_String2 = generateRandomString();
		String Debitchecksum2 = getDebitChecksum(random_String2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum2,
				random_number2, random_String2, random_number2, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String Debitchecksum3 = getDebitChecksum(random_String3,
				random_number3, random_String3, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");
		String clientTransactionID1 = getClientTransactionID1(Debitchecksum3,
				random_number3, random_String3, random_number3, "5000",
				"lyaltytestingnew@myntra.com", "ORDER");

		String random_number4 = generateRandomNumber();
		String random_String4 = generateRandomString();
		String refundChecksum2 = getRefundChecksum(random_String4,
				random_number4, random_String4, clientTransactionID,
				random_number4, "5000", "achal@myntra.com", "ORDER");
		String[] arr1 = { refundChecksum2, random_number4, random_String4,
				clientTransactionID, random_number4, "5000",
				"achal@myntra.com", "ORDER" };

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String refundChecksum3 = getRefundChecksum(random_String5,
				random_number5, random_String5, clientTransactionID1,
				random_number5, "5000", "achal@myntra.com", "ORDER");
		String[] arr2 = { refundChecksum3, random_number5, random_String5,
				clientTransactionID1, random_number5, "5000",
				"achal@myntra.com", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] refundloyaltyDP_ToInvalidTXN(ITestContext testContext) {

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String Debitchecksum3 = getDebitChecksum(random_String3,
				random_number3, random_String3, random_number3, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum3,
				random_number3, random_String3, random_number3, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number5 = generateRandomNumber();
		String random_String5 = generateRandomString();
		String refundChecksum3 = getRefundChecksum(random_String5,
				random_number5, random_String5, "6767", random_number5, "5000",
				"lyaltytesting@myntra.com", "ORDER");
		String[] arr1 = { refundChecksum3, random_number5, random_String5,
				"6767", random_number5, "5000", "lyaltytesting@myntra.com",
				"ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	public String getClientTransactionID1(String checksum, String ppsID,
										  String ppsTransactionID, String orderId, String amount,
										  String customerID, String ppsType) {

		creditLoyalityPoints(customerID, "100", "0", "0", "0", "automation",
				"ORDER", "123", "GOOD_WILL");

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
		String balnacee = getCurrentbalance(customerID);
		System.out.println("--------------------------------->>>>>>>>>>>>>>>");
		System.out.println("current balacn after debit ------ >>>>>>"
				+ balnacee);

		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String clientId = JsonPath
				.read(jsonRes, "$.params..clientTransactionID").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		return clientId;
	}

	private void CreateVoidTransaction(String checksum, String ppsID,
									   String ppsTransactionID, String clientTransactionID,
									   String customerID) {

		// creditLoyalityPoints(
		// customerID, "100","0", "0",
		// "0","automation", "ORDER", "123", "GOOD_WILL");

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

		String jsonRes = req.respvalidate.returnresponseasstring();
		String msg1 = JsonPath.read(jsonRes, "$.params..txStatus").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + msg1);

		// String msg1 = JsonPath.read(response,
		// "$.userAccountInfo..activePointsBalance").toString().replace("[",
		// "").replace("]", "").trim();
		// System.out.println("Response---->>>>" +response );
		// // String dbConnection_Fox7 =
		// "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
		// // int count = getCount(dbConnection_Fox7);

	}

	private void creditLoyalityPoints(String login, String activeCreditPoints,
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
		System.out.println("service payload----- ? > ? ? ? " + service.Payload);

		String jsonres = req.returnresponseasstring();
		System.out.println("response --=====>>>>>>>>>>>--   " + jsonres);
		String balnce = getCurrentbalance(login);
		System.out.println("Current balance after credit -" + balnce);
		System.out.println("service payload in credit->>" + service.Payload);
		System.out.println("Response payload in credit->>"
				+ req.returnresponseasstring());

	}

	private String CreateRefund(String checksum, String ppsID,
								String ppsTransactionID, String clientTransactionID,
								String orderId, String amount, String customerID, String ppsType) {

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

		String jsonRes = req.respvalidate.returnresponseasstring();
		String transcationId = JsonPath
				.read(jsonRes, "$.params..clientTransactionID").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Msg------>>>>> " + transcationId);
		return transcationId;

		// String msg1 = JsonPath.read(response,
		// "$.userAccountInfo..activePointsBalance").toString().replace("[",
		// "").replace("]", "").trim();
		// System.out.println("Response---->>>>" +response );
		// // String dbConnection_Fox7 =
		// "jdbc:mysql://"+"54.179.37.12"+"/myntra_lp?"+"user=MyntStagingUser&password=9eguCrustuBR1&port=3306";
		// // int count = getCount(dbConnection_Fox7);

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

	private String getDebitChecksum(String checksum, String ppsID,
									String ppsTransactionID, String orderId, String amount,
									String customerID, String ppsType) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUNDDEBIT,
				init.Configurations,
				new String[] { checksum, ppsID, ppsTransactionID, orderId,
						amount, customerID, ppsType }, PayloadType.JSON,
				PayloadType.XML);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr debit checksum ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}

	private String getRefundChecksum(String checksum, String ppsID,
									 String ppsTransactionID, String transactionID, String orderId,
									 String amount, String customerID, String ppsType) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMREFUND,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, transactionID, orderId, amount,
						customerID, ppsType }, PayloadType.JSON,
				PayloadType.XML);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}

	private String getvoidChecksum(String checksum, String ppsID,
								   String ppsTransactionID, String client) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMVOID,
				init.Configurations, new String[] { checksum, ppsID,
						ppsTransactionID, client }, PayloadType.JSON,
				PayloadType.XML);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}

	private String getStatusChecksum(String checksum, String ppsID,
									 String client, String orderID) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.CHECKSUMSTATUS,
				init.Configurations, new String[] { checksum, ppsID, client,
						orderID }, PayloadType.JSON, PayloadType.XML);
		System.out.println("Url------>>>>> " + service.URL);
		System.out.println("payload------>>>>> " + service.Payload);

		RequestGenerator req = new RequestGenerator(service);
		String response = req.returnresponseasstring();
		System.out.println("responsr ------ >>>" + response);
		String jsonRes = req.respvalidate.returnresponseasstring();
		return response;
	}

	private String getCurrentbalance(String emailid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_LOYALTYSERVICE, APINAME.USERINFO,
				init.Configurations);

		service.URL = apiUtil.prepareparameterizedURL(service.URL, emailid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		String msg1 = JsonPath
				.read(response, "$.userAccountInfo..activePointsBalance")
				.toString().replace("[", "").replace("]", "").trim();

		return msg1;
	}

	@DataProvider
	public Object[][] debitloyalty(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "50",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER");
		String[] arr1 = { checksum, random_number, random_String,
				random_number, "50",
				"e716662a.55b8.4741.8778.9e84a995acb7SXD3duUpFL", "ORDER" };

		/*
		 * String[] discount10 = {"%^^&%&"}; String[] discount11 = {"%^^&%&"};
		 * String[] discount12 = {"%^^&%&"}; String[] discount13 = {"%^^&%&"};
		 * String[] discount14 = {"%^^&%&"};
		 */

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] refundloyalty(ITestContext testContext) {

		String random_number1 = generateRandomNumber();
		String random_String1 = generateRandomString();
		String Debitchecksum1 = getDebitChecksum(random_String1,
				random_number1, random_String1, random_number1, "1000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");
		System.out.println("debitchecskjcs========== " + Debitchecksum1);

		String clientTransactionID = getClientTransactionID1(Debitchecksum1,
				random_number1, random_String1, random_number1, "1000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");
		System.out.println("Transaction Id ---- " + clientTransactionID);

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String refundChecksum = getRefundChecksum(random_String3,
				random_number3, random_String3, clientTransactionID,
				random_number3, "1000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");

		String[] arr1 = { refundChecksum, random_number3, random_String3,
				clientTransactionID, random_number3, "1000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] voidTx(ITestContext testContext) {
		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String Debitchecksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "5000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");
		String clientTransactionID = getClientTransactionID1(Debitchecksum,
				random_number, random_String, random_number, "5000",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");

		System.out.println("Transaction Id ---- " + clientTransactionID);

		String voidChecksum = getvoidChecksum(random_String, random_number,
				random_String, clientTransactionID);

		String[] arr1 = { voidChecksum, random_number, random_String,
				clientTransactionID,
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] getStatusSchema(ITestContext testContext) {

		String random_number = generateRandomNumber();
		String random_String = generateRandomString();
		String checksum = getDebitChecksum(random_String, random_number,
				random_String, random_number, "100",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");
		String clientTransactionID = getClientTransactionID1(checksum,
				random_number, random_String, random_number, "100",
				"4b68d762.ec06.4585.9a4d.ba53a8225520Z6Pn8UJwQb", "ORDER");

		String random_number3 = generateRandomNumber();
		String random_String3 = generateRandomString();
		String statusChecksum = getStatusChecksum(random_String3,
				random_number3, clientTransactionID, random_number3);
		String[] arr1 = { clientTransactionID, statusChecksum, random_String3,
				random_number3 };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

}
