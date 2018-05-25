package com.myntra.apiTests.portalservices.atsService;

import java.util.UUID;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

public class ATSDataProvider implements ATSConstants {

	static Initialize init = new Initialize("Data/configuration");
	static String environment = init.Configurations.GetTestEnvironemnt().name();

	public static String generateRandomId() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}

	@DataProvider
	public static Object[][] ats_AddressScoreVerify(ITestContext testContext) {
		// Good Address
		Object[] arr1 = { "a6 hermes drome near kailash super market vimannagar puneviman nagar", true, true };
		Object[] arr2 = { "Webenza India Pvt Ltd #207 Regent Prime Whitefield main road", true, true };
		Object[] arr3 = { "Myntra, Flat 123,Near Kudlu Gate,Bommanhalli, 560068", true, true };
		Object[] arr4 = {
				"C/O-Shri Sushil Kumar Sinha, Doordarshan Staff Qtr, Qtr no-C-V/V, Chandmari road, Tarapur, Silchar, Cachar, Assam",
				true, true };
		Object[] arr5 = { "Kalpana chawla block(b block),Ladies hostel,VIT university,Vellore,Pin-632014", true, true };
		// Bad Address
		Object[] arr6 = { "somedummy Address", false, false };
		Object[] arr7 = { "inland container depot icd ppg ghazipur new delhi-110096ghazipur", false, true };
		Object[] arr8 = { "HCB Road", false, false };
		Object[] arr9 = { "ma suman plazabhagalpur central jail", false, false };
		Object[] arr10 = { "ram nagar colony khair road aligarh", false, true };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		// Object[][] dataSet = new Object[][] {arr1};
		return dataSet;
	}

	@DataProvider
	public static Object[][] ats_SetandGetUserDetails(ITestContext testContext) {
		Object[] arr1 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "1", "0.0", "0.0", "0.0" };
		Object[] arr2 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "2", "0.0", "0.0", "0.0" };
		Object[] arr3 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "2", "0.0", "299.0", "1500.0" };
		Object[] arr4 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "2", "0.0", "0.0", "0.0" };
		Object[] arr5 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "2", "0.0", "299.0", "1500.0" };
		Object[] arr6 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "2", "0.0", "0.0", "0.0" };
		Object[] arr7 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "2", "0.0", "299.0", "1500.0" };
		Object[] arr8 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "2", "0.0", "0.0", "0.0" };
		Object[] arr9 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "2", "0.0", "299.0", "1500.0" };
		Object[] arr10 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "2", "-1000.0", "299.0", "500.0" };
		Object[] arr11 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "3", "0.0", "0.0", "0.0" };
		Object[] arr12 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "3", "0.0", "299.0", "3000.0" };
		Object[] arr13 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "3", "0.0", "0.0", "0.0" };
		Object[] arr14 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "3", "0.0", "299.0", "3000.0" };
		Object[] arr15 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "3", "0.0", "0.0", "0.0" };
		Object[] arr16 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "3", "0.0", "299.0", "3000.0" };
		Object[] arr17 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "3", "0.0", "0.0", "0.0" };
		Object[] arr18 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "3", "0.0", "299.0", "3000.0" };
		Object[] arr19 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "3", "-1000.0", "299.0", "2000.0" };
		Object[] arr20 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "4", "0.0", "0.0", "0.0" };
		Object[] arr21 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "4", "0.0", "299.0", "49999.0" };
		Object[] arr22 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "4", "0.0", "0.0", "0.0" };
		Object[] arr23 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "4", "0.0", "299.0", "49999.0" };
		Object[] arr24 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "4", "0.0", "0.0", "0.0" };
		Object[] arr25 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "4", "0.0", "299.0", "49999.0" };
		Object[] arr26 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "4", "0.0", "0.0", "0.0" };
		Object[] arr27 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "4", "0.0", "299.0", "49999.0" };
		Object[] arr28 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "4", "-9999.0", "299.0", "49999.0" };
		Object[] arr29 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "5", "0.0", "0.0", "0.0" };
		Object[] arr30 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "5", "0.0", "299.0", "299.0" };
		Object[] arr31 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "5", "0.0", "0.0", "0.0" };
		Object[] arr32 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "5", "0.0", "299.0", "299.0" };
		Object[] arr33 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "5", "0.0", "0.0", "0.0" };
		Object[] arr34 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "5", "0.0", "299.0", "299.0" };
		Object[] arr35 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "5", "0.0", "0.0", "0.0" };
		Object[] arr36 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "5", "0.0", "299.0", "299.0" };
		Object[] arr37 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "6", "0.0", "0.0", "0.0" };
		Object[] arr38 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "6", "0.0", "299.0", "49999.0" };
		Object[] arr39 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "6", "0.0", "0.0", "0.0" };
		Object[] arr40 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "6", "0.0", "299.0", "49999.0" };
		Object[] arr41 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "6", "0.0", "0.0", "0.0" };
		Object[] arr42 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "6", "0.0", "299.0", "49999.0" };
		Object[] arr43 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "6", "0.0", "0.0", "0.0" };
		Object[] arr44 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "6", "0.0", "299.0", "49999.0" };
		Object[] arr45 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "6", "-9999.0", "299.0", "49999.0" };
		Object[] arr46 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "7", "0.0", "0.0", "0.0" };
		Object[] arr47 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "7", "0.0", "299.0", "24999.0" };
		Object[] arr48 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "7", "0.0", "0.0", "0.0" };
		Object[] arr49 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "7", "0.0", "299.0", "24999.0" };
		Object[] arr50 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "7", "0.0", "0.0", "0.0" };
		Object[] arr51 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "7", "0.0", "299.0", "24999.0" };
		Object[] arr52 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "7", "0.0", "0.0", "0.0" };
		Object[] arr53 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "7", "0.0", "299.0", "24999.0" };
		Object[] arr54 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "7", "-4999.0", "299.0", "20000.0" };
		Object[] arr55 = { true, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "8", "0.0", "0.0", "0.0" };
		Object[] arr57 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "8", "0.0", "299.0", "1500.0" };
		Object[] arr56 = { true, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "8", "0.0", "0.0", "0.0" };
		Object[] arr58 = { true, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "8", "0.0", "299.0", "1500.0" };
		Object[] arr59 = { false, "HIGH_RETURNS", true, "COD103", true, "DEFAULT", "8", "0.0", "0.0", "0.0" };
		Object[] arr60 = { false, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "8", "0.0", "299.0", "1500.0" };
		Object[] arr61 = { false, "HIGH_RETURNS", false, "COD103", true, "DEFAULT", "8", "0.0", "0.0", "0.0" };
		Object[] arr62 = { false, "HIGH_RETURNS", false, "COD103", false, "DEFAULT", "8", "0.0", "299.0", "1500.0" };
		Object[] arr63 = { true, "HIGH_RETURNS", true, "COD103", false, "DEFAULT", "8", "-1000.0", "299.0", "500.0" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12,
				arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27,
				arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36, arr37, arr38, arr39, arr40, arr41, arr42,
				arr43, arr44, arr45, arr46, arr47, arr48, arr49, arr50, arr51, arr52, arr53, arr54, arr55, arr56, arr57,
				arr58, arr59, arr60, arr61, arr62, arr63 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] ats_SetandGetOpenOrdersValue(ITestContext testContext) {
		Object[] arr1 = { 1000.0 };
		Object[] arr2 = { -2000.0 };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] ats_SetandGetOutstandingLimit(ITestContext testContext) {
		Object[] arr1 = { "1", 0.0, true };
		Object[] arr2 = { "2", 1500.0, true };
		Object[] arr3 = { "3", 3000.0, true };
		Object[] arr4 = { "4", 100000.0, false };
		Object[] arr5 = { "5", 299.0, true };
		Object[] arr6 = { "6", 100000.0, false };
		Object[] arr7 = { "7", 24999.0, true };
		Object[] arr8 = { "8", 1500.0, true };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] ats_PutandGetAbuserDetails(ITestContext testContext) {
		Object[] arr1 = { true };
		Object[] arr2 = { false };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] users_POSTATSUserDetails(ITestContext testContext) {
		Object[] arr0 = { 0, "20000.0", "299.0", "20000.0", true };
		Object[] arr1 = { 1, "0.0", "0.0", "0.0", true };
		Object[] arr2 = { 2, "1500.0", "299.0", "1500.0", true };
		Object[] arr3 = { 3, "3000.0", "299.0", "3000.0", true };
		Object[] arr4 = { 4, "100000.0", "299.0", "49999.0", false };
		Object[] arr5 = { 5, "299.0", "299.0", "299.0", true };
		Object[] arr6 = { 4, "100000.0", "299.0", "49999.0", false };
		Object[] arr7 = { 7, "24999.0", "299.0", "24999.0", true };
		Object[] arr8 = { 8, "1500.0", "299.0", "1500.0", true };
		Object[][] dataSet = new Object[][] { arr0, arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] users_POSTATSUserDetails1(ITestContext testContext) {
		// D,L,IC,RTO

		Object[] arr0 = { 0, "20000.0", "299.0", "20000.0", true, "D" };
		Object[] arr1 = { 0, "20000.0", "299.0", "20000.0", true, "L" };
		Object[] arr2 = { 0, "20000.0", "299.0", "20000.0", true, "IC" };
		Object[] arr3 = { 0, "20000.0", "299.0", "20000.0", true, "RTO" };

		Object[] arr4 = { 2, "1500.0", "299.0", "1500.0", true, "D" };
		Object[] arr5 = { 2, "1500.0", "299.0", "1500.0", true, "L" };
		Object[] arr6 = { 2, "1500.0", "299.0", "1500.0", true, "IC" };
		Object[] arr7 = { 2, "1500.0", "299.0", "1500.0", true, "RTO" };

		Object[] arr8 = { 3, "3000.0", "299.0", "3000.0", true, "D" };
		Object[] arr9 = { 3, "3000.0", "299.0", "3000.0", true, "L" };
		Object[] arr10 = { 3, "3000.0", "299.0", "3000.0", true, "IC" };
		Object[] arr11 = { 3, "3000.0", "299.0", "3000.0", true, "RTO" };

		Object[] arr12 = { 4, "100000.0", "299.0", "49999.0", false, "D" };
		Object[] arr13 = { 4, "100000.0", "299.0", "49999.0", false, "L" };
		Object[] arr14 = { 4, "100000.0", "299.0", "49999.0", false, "IC" };
		Object[] arr15 = { 4, "100000.0", "299.0", "49999.0", false, "RTO" };

		Object[] arr16 = { 4, "100000.0", "299.0", "49999.0", false, "D" };
		Object[] arr17 = { 4, "100000.0", "299.0", "49999.0", false, "L" };
		Object[] arr18 = { 4, "100000.0", "299.0", "49999.0", false, "IC" };
		Object[] arr19 = { 4, "100000.0", "299.0", "49999.0", false, "RTO" };

		Object[] arr20 = { 7, "24999.0", "299.0", "24999.0", true, "D" };
		Object[] arr21 = { 7, "24999.0", "299.0", "24999.0", true, "L" };
		Object[] arr22 = { 7, "24999.0", "299.0", "24999.0", true, "IC" };
		Object[] arr23 = { 7, "24999.0", "299.0", "24999.0", true, "RTO" };

		Object[] arr24 = { 8, "1500.0", "299.0", "1500.0", true, "D" };
		Object[] arr25 = { 8, "1500.0", "299.0", "1500.0", true, "L" };
		Object[] arr26 = { 8, "1500.0", "299.0", "1500.0", true, "IC" };
		Object[] arr27 = { 8, "1500.0", "299.0", "1500.0", true, "RTO" };

		Object[][] dataSet = new Object[][] { arr0, arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11,
				arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26,
				arr27 };
		return dataSet;
	}

	@DataProvider
	public static Object[][] users_ToCreateAndVerifyCCOD(ITestContext testContext) {

		String[] arr1 = { Stage_ENV, stage_atsUser1Uidx, "newUser", "20000.0", "true", "299.0", "20000.0" }; // new user
																												// 299-20k
		String[] arr2 = { Stage_ENV, stage_atsUser2Uidx, "2", "1500.0", "true", "299.0", "1500.0" };// 2 for codlimit
																									// 299-1500
		String[] arr3 = { Stage_ENV, stage_atsUser3Uidx, "7", "24999.0", "true", "299.0", "24999.0" }; // 7 for

		String[] arr4 = { Stage_ENV, stage_atsUser4Uidx, "6", "100000.0", "true", "299.0", "49999.0" }; // 6 for

		String[] arr5 = { Stage_ENV, stage_atsUser5Uidx, "1", "0.0", "true", "0.0", "0.0" }; // 1 for limit 0-0

		String[] arr6 = { Sfqa_ENV, sfqa_atsUser1Uidx, "newUser", "20000.0", "true", "299.0", "20000.0" }; // new user
																											// 299-20k
		String[] arr7 = { Sfqa_ENV, sfqa_atsUser2Uidx, "2", "1500.0", "true", "299.0", "1500.0" };// 2 for codlimit
																									// 299-1500
		String[] arr8 = { Sfqa_ENV, sfqa_atsUser3Uidx, "7", "24999.0", "true", "299.0", "24999.0" }; // 7 for cod-limit
																										// 299-24999
		String[] arr9 = { Sfqa_ENV, sfqa_atsUser4Uidx, "6", "100000.0", "true", "299.0", "49999.0" }; // 6 for cod-limit
																										// 299-49999
		String[] arr10 = { Sfqa_ENV, sfqa_atsUser5Uidx, "1", "0.0", "true", "0.0", "0.0" }; // 1 for limit 0-0

		Object[][] dataSet = new Object[][] { arr6, arr7, arr8, arr9, arr10 };

		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 5, 5);
	}

	@DataProvider
	public static Object[][] users_ToOrderandVerify(ITestContext testContext) {

		String[] arr1 = { Stage_ENV, stage_atsUser1, stage_atsUser1Uidx, "true", "20000.0", "299.0", "20000.0",
				"false" }; // new user 299-20k
		String[] arr2 = { Stage_ENV, stage_atsUser2, stage_atsUser2Uidx, "true", "1500.0", "299.0", "1500.0", "false" };// 2
		String[] arr3 = { Stage_ENV, stage_atsUser3, stage_atsUser3Uidx, "true", "24999.0", "299.0", "24999.0",
				"false" }; // 7 for cod-limit 299-24999
		String[] arr4 = { Stage_ENV, stage_atsUser4, stage_atsUser4Uidx, "true", "100000.0", "299.0", "49999.0",
				"true" }; // 6 for cod-limit 299-49999
		String[] arr5 = { Scmqa_ENV, "dxcfvgbhcjndkm@gmail.com", "00bea2ad.9206.47ab.af30.9a2a0f1cb40cbsKUN0bfvr",
				"true", "20000.0", "299.0", "20000.0", "false" }; // new user 299-20k
		String[] arr6 = { Sfqa_ENV, sfqa_atsUser2, sfqa_atsUser2Uidx, "true", "1500.0", "299.0", "1500.0", "false" };// 2

		String[] arr7 = { Sfqa_ENV, sfqa_atsUser3, sfqa_atsUser3Uidx, "true", "24999.0", "299.0", "24999.0", "false" }; // 7

		String[] arr8 = { Sfqa_ENV, sfqa_atsUser4, sfqa_atsUser4Uidx, "true", "100000.0", "299.0", "49999.0", "true" }; // 6

		Object[][] dataSet = new Object[][] { arr5 };

		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public static Object[][] user_ToOrderandVerifyForPrimeUser(ITestContext testContext) {

		String[] arr1 = { Stage_ENV, stage_atsUser4, stage_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "D" }; // 6
		String[] arr2 = { Stage_ENV, stage_atsUser4, stage_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "L" }; // 6
		String[] arr3 = { Stage_ENV, stage_atsUser4, stage_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0",
				"IC" };
		String[] arr4 = { Stage_ENV, stage_atsUser4, stage_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0",
				"RTO" }; // 6 for cod-limit 299-49999

		String[] arr5 = { Sfqa_ENV, sfqa_atsUser4, sfqa_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "D" };
		String[] arr6 = { Sfqa_ENV, sfqa_atsUser4, sfqa_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "L" };
		String[] arr7 = { Sfqa_ENV, sfqa_atsUser4, sfqa_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "IC" };
		String[] arr8 = { Sfqa_ENV, sfqa_atsUser4, sfqa_atsUser4Uidx, "false", "100000.0", "299.0", "49999.0", "RTO" };

		Object[][] dataSet = new Object[][] { arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(environment, dataSet, testContext.getIncludedGroups(), 4, 4);
	}

}
