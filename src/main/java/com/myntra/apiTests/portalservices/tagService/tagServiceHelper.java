package com.myntra.apiTests.portalservices.tagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.legolas.Commons;

public class tagServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(tagServiceHelper.class);
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	List<Integer> styleIdNike = styleHelper.performSeachServiceToGetStyleIds(
			"nike", "15", "true", "false");
	List<Integer> styleIdAdidas = styleHelper.performSeachServiceToGetStyleIds(
			"Adidas", "15", "true", "false");
	List<Integer> styleIdPuma = styleHelper.performSeachServiceToGetStyleIds(
			"Puma", "15", "true", "false");
	List<Integer> styleIdJackets = styleHelper
			.performSeachServiceToGetStyleIds("Jackets", "15", "true", "false");
	List<Integer> styleIdShoes = styleHelper.performSeachServiceToGetStyleIds(
			"Shoes", "15", "true", "false");
	List<Integer> styleIdJeans = styleHelper.performSeachServiceToGetStyleIds(
			"Jeans", "15", "true", "false");
	List<Integer> styleIdShirts = styleHelper.performSeachServiceToGetStyleIds(
			"Shirts", "15", "true", "false");
	List<Integer> styleIdTshirts = styleHelper
			.performSeachServiceToGetStyleIds("Tshirts", "15", "true", "false");
	List<Integer> styleIdSunglasses = styleHelper
			.performSeachServiceToGetStyleIds("Sunglasses", "15", "true",
					"false");
	List<Integer> styleIdWallets = styleHelper
			.performSeachServiceToGetStyleIds("Wallets", "15", "true", "false");
	List<Integer> styleIdSportsShoes = styleHelper
			.performSeachServiceToGetStyleIds("flip-flops", "15", "true",
					"false");

	public static void publishTagRemoveAll(String tagNamespace) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGREMOVEALL,
				init.Configurations, new String[] { tagNamespace },
				PayloadType.JSON, PayloadType.JSON);
	}

	public static void fetchStyle(String tag, String tagNamespace) {
		MyntraService fetchService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.FETCHSTYLE,
				init.Configurations, new String[] { tag }, new String[] {
						tagNamespace, "0" }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator Generator = new RequestGenerator(fetchService);
		System.out.println("Fetch Response -----> "
				+ Generator.returnresponseasstring());
	}

	public static void fetchStyleWithFullNamespace(String tagNamespace) {
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE,
				APINAME.FETCHSTYLEWITHFULLNAMESPACE, init.Configurations,
				new String[] { "" }, new String[] { tagNamespace, "0" },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator generator = new RequestGenerator(myntraService);
		System.out.println("Fetch Response ----->"
				+ generator.returnresponseasstring());
	}

	public static void publishTagAdd(String styleId, String tagstring1,
			String tagstring2, String tagNamespace) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADD,
				init.Configurations, new String[] { styleId, tagstring1,
						tagstring2, tagNamespace }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator generator = new RequestGenerator(service);

	}

	public static void publishTagAddBulk(String styleId1, String styleId2,
			String styleId3, String styleId4, String styleId5, String styleId6,
			String styleId7, String styleId8, String styleId9,
			String styleId10, String styleId11, String styleId12,
			String tagNamespace, String tag) {
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADDBULK,
				init.Configurations, new String[] { styleId1, styleId2,
						styleId3, styleId4, styleId5, styleId6, styleId7,
						styleId8, styleId9, styleId10, styleId11, styleId12,
						tagNamespace, tag }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator generator = new RequestGenerator(myntraService);

	}

	public static void publishTagAddBulk(String styleIds, String tag,
			String tagNamespace) {
		MyntraService customMyntraService = Myntra.getService(
				ServiceType.PORTAL_TAGSERVICE, APINAME.PUBLISHTAGADDBULK,
				init.Configurations, createPublishTagAddBulkCustomPayload(tag,
						tagNamespace, styleIds));
		RequestGenerator requestGenerator = new RequestGenerator(
				customMyntraService);
	}

	public static boolean fetchTagResponseContainsTagStrings(
			RequestGenerator req, String tagstring1, String tagstring2) {
		List<String> list = JsonPath.read(
				req.respvalidate.returnresponseasstring(),
				"$.data[*].tags[*].tagString");
		boolean contains = false;
		if (list.contains(tagstring1) && list.contains(tagstring2)) {
			contains = true;
		}
		return contains;
	}

	public static boolean fetchStyleResponseTagStyleIdValidation(
			RequestGenerator req, String tagstring1, String tagstring2,
			String styleId) {
		List<String> tagList = JsonPath.read(
				req.respvalidate.returnresponseasstring(),
				"$.tagtoStyleMapList[*].tag");
		List<Object> styleIdList = JsonPath.read(req.returnresponseasstring(),
				"$.tagtoStyleMapList[*].styleIdList");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		for (int i = 0; i < tagList.size(); i++) {
			responseMap.put(tagList.get(i), styleIdList.get(i));
		}
		boolean contains = false;
		if (responseMap.get(tagstring1).toString().contains(styleId)
				&& responseMap.get(tagstring2).toString().contains(styleId)) {
			contains = true;
		}
		return contains;
	}

	public String getOneStyleID() {
		String styleIds = null;
		try {
			styleIds = Commons.getCommaSeperatedValuesFromArray(
					sumOfAllStyleIds(), 1, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return styleIds;
	}

	public String getAllStyleIDs(String eachStyleCount) {
		Object[] sumOfAllStyleIds = sumOfAllStyleIds(eachStyleCount);
		String styleIds = null;
		try {
			styleIds = Commons.getCommaSeperatedValuesFromArray(
					sumOfAllStyleIds, sumOfAllStyleIds.length, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return styleIds;
	}

	public String getAllStyleIDs(String eachStyleCount, String[] itemList) {
		Object[] sumOfAllStyleIds = sumOfAllStyleIds(eachStyleCount, itemList);
		String styleIds = null;
		try {
			styleIds = Commons.getCommaSeperatedValuesFromArray(
					sumOfAllStyleIds, sumOfAllStyleIds.length, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return styleIds;
	}

	public Object[] sumOfAllStyleIds() {
		List<Integer> allStyles = new ArrayList<Integer>();
		allStyles.addAll(styleIdNike);
		allStyles.addAll(styleIdAdidas);
		allStyles.addAll(styleIdPuma);
		allStyles.addAll(styleIdJackets);
		allStyles.addAll(styleIdShoes);
		allStyles.addAll(styleIdJeans);
		allStyles.addAll(styleIdShirts);
		allStyles.addAll(styleIdTshirts);
		allStyles.addAll(styleIdSunglasses);
		allStyles.addAll(styleIdWallets);
		allStyles.addAll(styleIdSportsShoes);
		return allStyles.toArray();
	}

	public Object[] sumOfAllStyleIds(String eachStyleCount) {
		List<Integer> styleIdNike = styleHelper
				.performSeachServiceToGetStyleIds("nike", eachStyleCount,
						"true", "false");
		List<Integer> styleIdAdidas = styleHelper
				.performSeachServiceToGetStyleIds("Adidas", eachStyleCount,
						"true", "false");
		List<Integer> styleIdPuma = styleHelper
				.performSeachServiceToGetStyleIds("Puma", eachStyleCount,
						"true", "false");
		List<Integer> styleIdJackets = styleHelper
				.performSeachServiceToGetStyleIds("Jackets", eachStyleCount,
						"true", "false");
		List<Integer> styleIdShoes = styleHelper
				.performSeachServiceToGetStyleIds("Shoes", eachStyleCount,
						"true", "false");
		List<Integer> styleIdJeans = styleHelper
				.performSeachServiceToGetStyleIds("Jeans", eachStyleCount,
						"true", "false");
		List<Integer> styleIdShirts = styleHelper
				.performSeachServiceToGetStyleIds("Shirts", eachStyleCount,
						"true", "false");
		List<Integer> styleIdTshirts = styleHelper
				.performSeachServiceToGetStyleIds("Tshirts", eachStyleCount,
						"true", "false");
		List<Integer> styleIdSunglasses = styleHelper
				.performSeachServiceToGetStyleIds("Sunglasses", eachStyleCount,
						"true", "false");
		List<Integer> styleIdWallets = styleHelper
				.performSeachServiceToGetStyleIds("Wallets", eachStyleCount,
						"true", "false");
		List<Integer> styleIdSportsShoes = styleHelper
				.performSeachServiceToGetStyleIds("flip-flops", eachStyleCount,
						"true", "false");
		List<Integer> allStyles = new ArrayList<Integer>();
		allStyles.addAll(styleIdNike);
		allStyles.addAll(styleIdAdidas);
		allStyles.addAll(styleIdPuma);
		allStyles.addAll(styleIdJackets);
		allStyles.addAll(styleIdShoes);
		allStyles.addAll(styleIdJeans);
		allStyles.addAll(styleIdShirts);
		allStyles.addAll(styleIdTshirts);
		allStyles.addAll(styleIdSunglasses);
		allStyles.addAll(styleIdWallets);
		allStyles.addAll(styleIdSportsShoes);
		return allStyles.toArray();
	}

	public Object[] sumOfAllStyleIds(String eachStyleCount, String[] itemList) {
		List<Integer> allStyles = new ArrayList<Integer>();
		for (String element : itemList) {
			List<Integer> styleId = styleHelper
					.performSeachServiceToGetStyleIds(element, eachStyleCount,
							"true", "false");
			allStyles.addAll(styleId);
		}
		return allStyles.toArray();
	}

	public static String createPublishTagAddBulkCustomPayload(String tag,
			String tagNamespace, String styleIds) {
		String[] styleId = styleIds.split(",");
		String styleIdAppend = ",        {\n           \"styleId\": "
				+ styleId[1] + "\n       }";
		for (int i = 2; i < styleId.length; i++) {
			styleIdAppend = styleIdAppend
					.concat(",        {\n           \"styleId\": " + styleId[i]
							+ "\n       }");
		}
		String customPayload = "{\n   \"tagsList\": [\n       {\n           \"styleId\": "
				+ styleId[0]
				+ "\n       }"
				+ styleIdAppend
				+ "\n   ],\n   \"operation\": \"add_bulk\",\n   \"tagNamespace\": \""
				+ tagNamespace
				+ "\",\n   \"ttl\": 10000000,\n   \"tag\": \""
				+ tag + "\"\n}";
		return customPayload;
	}

	public static String createPublishTagRemoveBulkCustomPayload(String tag,
			String tagNamespace, String styleIds) {
		String[] styleId = styleIds.split(",");
		String styleIdAppend = ",        {\n           \"styleId\": "
				+ styleId[1] + "\n       }";
		for (int i = 2; i < styleId.length; i++) {
			styleIdAppend = styleIdAppend
					.concat(",        {\n           \"styleId\": " + styleId[i]
							+ "\n       }");
		}
		String customPayload = "{\n   \"tagsList\": [\n       {\n           \"styleId\": "
				+ styleId[0]
				+ "\n       }"
				+ styleIdAppend
				+ "\n   ],\n   \"operation\": \"remove_bulk\",\n   \"tagNamespace\": \""
				+ tagNamespace + "\",\n   \"tag\": \"" + tag + "\"\n}";
		System.out.println("styleId.length------------------------>"
				+ styleId.length);
		return customPayload;
	}

}
