package com.myntra.apiTests.portalservices.discountservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

/**
 * @author ashu.v
 * 
 */

public class DiscountSchedulingHelper {

	Initialize init = new Initialize("/Data/configuration");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	/**
	 * It calculates and returns the time to wait for the thread, based on the
	 * time at which control comes to the thread
	 * 
	 * @param timeFromOrigin
	 *            in seconds
	 * @return timeToWait in seconds
	 * 
	 */
	public long getWaitTime(long timeFromOrigin, long startTime) {
		long timeToWait;
		System.out.println("\ntimeFromOrigin " + timeFromOrigin);
		System.out.println("curren time"
				+ dateFormat.format(new Date(getCurrentEpochTime() * 1000)));
		timeToWait = (timeFromOrigin - (getCurrentEpochTime() - startTime));
		timeToWait *= 1000;
		System.out.println("time To Wait" + timeToWait);
		return timeToWait;
	}

	/**
	 * @return current epoch time in milliseconds
	 */
	public long getCurrentEpochTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 
	 * @param styleId
	 * @return response in String
	 */
	public String getTradeAndDealDiscount(String styleId) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,
				new String[] { styleId }, new String[] { "default" },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		return req.respvalidate.returnresponseasstring();
	}

	/**
	 * This hits get trade & deal discounts API
	 * 
	 * @param req
	 * @param styleID
	 * @return discount ID
	 */
	public int getDiscountIdFromGetTradeAndDealDiscount(String response,
			String styleId) {
		int discountId;
		if (response.contains(styleId)) {
			discountId = JsonPath.read(response, "$." + styleId
					+ ".tradeDiscount[0].displayText.params.discountId");
		} else
			discountId = 0;
		System.out.println("\nDiscount Id " + discountId);
		return discountId;
	}

	/**
	 * 
	 * @param assertStartTime
	 * @param testStartTime
	 * @param styleId
	 * @return
	 * @throws InterruptedException
	 */
	public int sleepAndGetDiscountID(long assertStartTime, long testStartTime,
			String styleId) throws InterruptedException {
		Thread.sleep(getWaitTime(assertStartTime, testStartTime));
		return getDiscountIdFromGetTradeAndDealDiscount(
				getTradeAndDealDiscount(styleId), styleId);
	}

	/**
	 * 
	 * @param testStartTime
	 * @param discountStartOffset
	 * @param discountEndOffset
	 * @param discountPercent
	 * @param styleId
	 * @param source
	 * @return created Discount ID
	 */
	public int createFlatDiscountAndGetDiscountID(long testStartTime,
			int discountStartOffset, int discountEndOffset,
			String discountPercent, String styleId, String source) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFLATDISCOUNTV2, init.Configurations,
				new String[] {
						Long.toString((testStartTime + discountEndOffset)),
						Long.toString((testStartTime + discountStartOffset)),
						discountPercent, styleId, source }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("\nstart time -> "
				+ dateFormat.format(new Date(
						(testStartTime + discountStartOffset) * 1000)));
		System.out.println("end time -> "
				+ dateFormat.format(new Date(
						(testStartTime + discountEndOffset) * 1000)));
		return getDiscountIdFromCreateDiscount(req);
	}

	/**
	 * 
	 * @param req
	 * @return discountID
	 */
	public int getDiscountIdFromCreateDiscount(RequestGenerator req) {
		int id;
		String resp = req.returnresponseasstring();
		if (req.response.getStatus() == 200) {
			id = JsonPath.read(resp, "$.id");
		} else {
			id = -1;
			System.out.println("\nFailed Response\n" + resp);
		}
		System.out.println("\nDiscount ID " + id);
		return id;
	}

}
