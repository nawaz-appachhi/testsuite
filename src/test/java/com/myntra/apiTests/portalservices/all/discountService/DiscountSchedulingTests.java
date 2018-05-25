package com.myntra.apiTests.portalservices.all.discountService;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.myntra.apiTests.portalservices.discountservice.DiscountSchedulingHelper;
import com.myntra.apiTests.portalservices.tagService.tagServiceHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.lordoftherings.Initialize;

public class DiscountSchedulingTests {
	Initialize init = new Initialize("/Data/configuration");
	tagServiceHelper helper = new tagServiceHelper();
	DiscountSchedulingHelper dshelper = new DiscountSchedulingHelper();
	String styleId;
	int discountStartOffset = 30;
	int precautionTimeToCheckDiscount = 5;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	@BeforeClass(alwaysRun = true)
	public void getStyleIdsAndRemoveDiscountsOnThem() {
		styleId = helper.getOneStyleID();
		System.out.println("style id " + styleId);
	}

	// GENERAL TEST CASES

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2IsSubsetOfD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// create Discount D2 for 1 minute in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1IsSubsetOfD2()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2OverlapsD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "23", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2StartsBetweenD1AndEndsAfterD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "23", styleId, "null");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2StartsBeforeD1AndEndsBetweenD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "23", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1EndTimeIsEqualToD2StartTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1 minute
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "null");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1StartTimeIsEqualToD2EndTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 1 minute
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	// PAST TEST CASES -> Discounts will start from 5 min's from current

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForPastWhereD2IsSubsetOfD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 from 30 seconds in past active till 6 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-30, 360, "23", styleId, "null");

		// create Discount D2 for 20 seconds in past active till 5:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 330, "23", styleId, "null");

		// thread sleep for 10 seconds from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue1 != d1) && (dValue1 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue1);

		// thread sleep for 5 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(300
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 5:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(330
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 6 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(360
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForPastWhereD1IsSubsetOfD2()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 20 seconds in past active till 5:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 330, "23", styleId, "null");

		// create Discount D2 from 30 seconds in past active till 6 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-30, 360, "23", styleId, "null");

		// thread sleep for 10 seconds from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue1 != d1) && (dValue1 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue1);

		// thread sleep for 5 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(300
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 5:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(330
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 6 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(360
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForPastWhereD2OverlapsD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 20 seconds in past active till 6 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 360, "23", styleId, "null");

		// create Discount D2 from 20 seconds in past active till 6 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 360, "23", styleId, "null");

		// thread sleep for 10 seconds from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue1 != d1) && (dValue1 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue1);

		// thread sleep for 5 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(300
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 5:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(330
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 6 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(360
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForPastWhereD2StartsBetweenD1AndEndsAfterD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 30 seconds in past active till 5:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-30, 330, "23", styleId, "null");

		// create Discount D2 from 20 seconds in past active till 6 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 360, "23", styleId, "null");

		// thread sleep for 10 seconds from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue1 != d1) && (dValue1 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue1);

		// thread sleep for 5 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(300
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 5:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(330
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 6 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(360
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForPastWhereD2StartsBeforeD1AndEndsBetweenD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D2 from 20 seconds in past active till 6 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-20, 360, "23", styleId, "null");

		// create Discount D1 for 30 seconds in past active till 5:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime,
				-30, 330, "23", styleId, "null");

		// thread sleep for 10 seconds from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue1 != d1) && (dValue1 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue1);

		// thread sleep for 5 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(300
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 5:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(330
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 6 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(360
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	// D1 created through Pricing Engine, D2 created through MPS

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughMPSIsSubsetOfD1CreatedThroughPricingEngine()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 1 minute in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughPricingEngineIsSubsetOfD2CreatedThroughMPS()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughMPSOverlapsD1CreatedThroughPricingEngine()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "23", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughMPSStartsBetweenD1CreatedThroughPricingEngineAndEndsAfterD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "23", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughMPSStartsBeforeD1CreatedThroughPricingEngineAndEndsBetweenD1()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "23", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughPricingEngineEndTimeIsEqualToD2CreatedThroughMPSStartTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1 minute
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughPricingEngineStartTimeIsEqualToD2CreatedThroughMPSEndTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "\"Pricing Engine\"");

		// create Discount D2 for 10 seconds in future active till 1 minute
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "null");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	// D1 created through MPS, D2 created through Pricing Engine

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughPricingEngineIsSubsetOfD1CreatedThroughMPS()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "null");

		// create Discount D2 for 1 minute in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughMPSIsSubsetOfD2CreatedThroughPricingEngine()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				90, "23", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughPricingEngineOverlapsD1CreatedThroughMPS()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "23", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				120, "43", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughMPSStartsBeforeD2CreatedThroughPricingEngineAndEndsBetweenD2()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "43", styleId, "null");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "23", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d2, "Checkpoint 3 expected " + d2
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD1CreatedThroughMPSStartsBetweenD2CreatedThroughPricingEngineAndEndsAfterD2()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 1:30 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				90, "23", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 1:30 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue3 = dshelper.sleepAndGetDiscountID(90
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue3, d1, "Checkpoint 3 expected " + d1
				+ " actual " + dValue3);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 4");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughPricingEngineStartTimeIsEqualToD1CreatedThroughMPSEndTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 10 seconds in future active till 1 minute
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "null");

		// create Discount D2 for 1 minute in future active till 2 minutes
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d1, "Checkpoint 1 expected " + d1
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d2, "Checkpoint 2 expected " + d2
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void discountScheduledForFutureWhereD2CreatedThroughPricingEngineEndTimeIsEqualToD1CreatedThroughMPSStartTime()
			throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// get current time
		long testStartTime = dshelper.getCurrentEpochTime();
		System.out.println("test start time"
				+ dateFormat.format(new Date(testStartTime * 1000)));

		// create Discount D1 for 1 minute in future active till 2 minutes
		// from origin
		int d1 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 60,
				120, "43", styleId, "null");

		// create Discount D2 for 10 seconds in future active till 1 minute
		// from origin
		int d2 = dshelper.createFlatDiscountAndGetDiscountID(testStartTime, 10,
				60, "23", styleId, "\"Pricing Engine\"");

		// thread sleep for 10 seconds + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 1");
		int dValue1 = dshelper.sleepAndGetDiscountID(10
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue1, d2, "Checkpoint 1 expected " + d2
				+ " actual " + dValue1);

		// thread sleep for 1 minute + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 2");
		int dValue2 = dshelper.sleepAndGetDiscountID(60
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(dValue2, d1, "Checkpoint 2 expected " + d1
				+ " actual " + dValue2);

		// thread sleep for 2 minutes + 5 seconds(as a precaution) from test
		// start time & assert discount
		// value
		System.out.println("\nCheckpoint 3");
		int dValue4 = dshelper.sleepAndGetDiscountID(120
				+ precautionTimeToCheckDiscount + discountStartOffset,
				testStartTime, styleId);
		softAssert.assertEquals(true, ((dValue4 != d1) && (dValue4 != d2)),
				"Checkpoint 4 not expected " + d1 + "or " + d2
						+ " actual value is " + dValue4);

		// assert all soft asserts
		softAssert.assertAll();
	}

}
