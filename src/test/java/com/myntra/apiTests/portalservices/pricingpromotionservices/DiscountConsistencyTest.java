package com.myntra.apiTests.portalservices.pricingpromotionservices;

import java.util.HashMap;
import java.util.Map;

import com.myntra.apiTests.common.Myntra;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Splitter;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DiscountConsistencyTest {

	Initialize init = new Initialize("/Data/configuration");

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void checkConsistencyWithRedis() {
		SoftAssert softAssert = new SoftAssert();
		System.out.println("Reached 1");
		int testCases = 5000;
		String styles = getStylesFromSolr(testCases);
		System.out.println("Reached 2");
		Map<String, String> redisDiscount = new HashMap<>();
		Map<String, String> dbDiscount = new HashMap<>();
		redisDiscount = normalizeRedisObject(convertStringToMutableHashMap(getDiscountValuesFromRedis(styles)));
		dbDiscount = convertStringToMutableHashMap(getCurrentDiscountsObject(styles));
		System.out
				.println("--------------------REDIS PAYLOAD------------------");
		printElementsOfHashMap(redisDiscount);
		System.out.println("--------------------DB PAYLOAD------------------");
		printElementsOfHashMap(dbDiscount);
		for (int i = 0; i < testCases; i++) {
			softAssert.assertEquals(dbDiscount, redisDiscount);
		}
	}

	public String getStylesFromSolr(int count) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PNPTESTTOOLS, APINAME.GETSTYLES,
				init.Configurations, PayloadType.JSON,
				new String[] { Integer.toString(count) }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String styles = req.returnresponseasstring();
		System.out.println("--------------------STYLES------------------");
		System.out.println(styles);
		return styles;
	}

	public String getDiscountValuesFromRedis(String payload) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PNPTESTTOOLS, APINAME.CHECKREDIS,
				init.Configurations, payload);
		RequestGenerator req = new RequestGenerator(service);
		String styleDiscountMap = req.returnresponseasstring();
		return styleDiscountMap;
	}

	private String getCurrentDiscountsObject(String payload) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PNPTESTTOOLS,
				APINAME.GETCURRENTTRADEDISCOUNTFROMDB, init.Configurations,
				payload);
		RequestGenerator req = new RequestGenerator(service);
		String styleDiscountMap = req.returnresponseasstring();
		return styleDiscountMap;
	}

	private Map<String, String> convertStringToMap(String json) {
		Map<String, String> map = Splitter.on(",").withKeyValueSeparator(":")
				.split(json);
		return map;
	}

	private Map<String, String> convertStringToMutableHashMap(String json) {
		Map<String, String> map = new HashMap<>();
		json = json.replace("{", "");
		json = json.replace("}", "");
		String[] KVPairs = json.split(",");
		for (String pair : KVPairs) {
			String[] entry = pair.split(":");
			map.put(entry[0].trim(), entry[1].trim().replace("\"", ""));
		}
		return map;
	}

	private Map<String, String> normalizeRedisObject(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			if (value.contains("Discount is not applicable on this style")
					|| value.contains("null")) {
				entry.setValue("0");
			}
		}
		return map;
	}

	private void printElementsOfHashMap(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(" Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
	}

}
