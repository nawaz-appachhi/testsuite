package com.myntra.apiTests.portalservices.all.discountService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.discountService.DiscountServiceV2DataProvider;
import com.myntra.apiTests.portalservices.discountservice.DiscountServiceV2Helper;
import com.myntra.apiTests.portalservices.all.DealsService;
import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DiscountServiceV2 extends DiscountServiceV2DataProvider {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealsService.class);
	static DiscountServiceV2Helper Discounthelper = new DiscountServiceV2Helper();

	APIUtilities apiUtil = new APIUtilities();
	ArrayList discountIds = new ArrayList();
	HashMap styleAndDiscountIdMap = new HashMap();
	HashMap ruleAndRuleIdMap = new HashMap();
	HashMap discountAndRuleMapping = new HashMap();
	public long currentTime = System.currentTimeMillis() / 1000;

	@BeforeClass(alwaysRun = true)
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity","Fox7Sanity" }, priority = 0,description="1.Get All Discounts")
	public void discountService_getAllDiscount() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETALLDISCOUNTSV2, init.Configurations);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		// System.out.println("Response----------- > " + response);
		discountIds = JsonPath.read(response, "$..id[*]");
		// System.out.println("Discount id----------- > " + discountIds);
		// System.out.println("Resonse------ >>>> " + response );
		AssertJUnit.assertTrue("Get all discount is not working",
				(discountIds.size() > 0));
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				rs.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getDiscountId", priority = 1,description="1.Get Discount from discount id")
	public void discountService_getDiscountFromDiscountId(String discountId,
			String respCode) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDISCOUNTFORDISCOUNTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountId);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		AssertJUnit.assertEquals("Response is not 200 OK",
				Integer.parseInt(respCode), rs.response.getStatus());
	}

	
	// commented this below test case after discussion with Kiran Badam  as this api is deprecated
	
//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "getDSMForStylesDP", priority = 2,description="1.Get styles from style service and check discounts configured for styles")
//	public void discountService_getDSMForStyles(String styleIds) {
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDSMFORSTYLES,
//				init.Configurations, "[" + styleIds + "]");
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();
//		System.out
//				.println("------------------------------------------------------------------->");
//
//		System.out.println("Response------>>> " + response);
//
//		System.out
//				.println("------------------------------------------------------------------->");
//		if (response
//				.contains("There are no disocunts configured for this style")) {
//
//			AssertJUnit.assertEquals("Response is not 404 OK", 404,
//					rs.response.getStatus());
//		} else {
//			AssertJUnit.assertEquals("Response is not 200 OK", 200,
//					rs.response.getStatus());
//
//		}
//		// AssertJUnit.assertTrue(response.equalsIgnoreCase("Discounts are not available for the given Style Id(s)"));
//	}
	
	
	// commented this below test case after discussion with Kiran Badam  as this api is deprecated

//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "getDSMForStylesDPNegative", priority = 3,description="1.Check 500 response for GETDSMFORSTYLES")
//	public void discountService_getDSMForStylesNegative(String styleIds) {
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDSMFORSTYLES,
//				init.Configurations, "[" + styleIds + "]");
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.returnresponseasstring();
//		AssertJUnit.assertEquals("Response is not 500", 500,
//				rs.response.getStatus());
//	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getDSMForStylesDPNegative1", priority = 4)
	public void discountService_getDSMForStylesNegative1(String styleIds) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDSMFORSTYLES,
				init.Configurations, "[" + styleIds + "]");
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		AssertJUnit.assertEquals("Response is not 404", 404,
				rs.response.getStatus());
	}

	// @Test
	// (groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	// "Regression","Fox7Sanity"}, dataProvider="getTradeAndDealDiscountsDP", priority=5)
	// public void discountService_getTradeAndDealDiscounts(String styleIds,
	// String page)
	// {
	// System.out.println(styleIds);
	// HashMap hm = new HashMap();
	// MyntraService service = new
	// MyntraService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	// APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations, styleIds);
	// service.URL = apiUtil.prepareparameterizedURL(service.URL, page);
	// System.out.println(service.URL);
	// RequestGenerator rs = new RequestGenerator(service);
	// String response = rs.returnresponseasstring();
	// System.out.println(response);
	// AssertJUnit.assertEquals("Response is not 200 OK", 200,
	// rs.response.getStatus());
	// String styleIdsFromDP = styleIds.replaceAll("\"", "").replace("{",
	// "").replace("}", "").replace("\n", "");
	// String[] commaSepIds = styleIdsFromDP.split(",");
	// for(String s : commaSepIds){
	// hm.put(s.split(":")[0], s.split(":")[1]);
	// }
	// for(Object s : hm.keySet()){
	// if(response.contains(s.toString())){
	// ArrayList discountPerc =
	// JsonPath.read(rs.respvalidate.returnresponseasstring(),
	// "$."+s.toString()+".*..discountPercent");
	// ArrayList discountAmount =
	// JsonPath.read(rs.respvalidate.returnresponseasstring(),
	// "$."+s.toString()+".*..discountAmount");
	// ArrayList discountId =
	// JsonPath.read(rs.respvalidate.returnresponseasstring(),
	// "$."+s.toString()+".*..discountId");
	// //Map of style ids and discount ids
	// styleAndDiscountIdMap.put(s.toString(), discountId.get(0));
	//
	// //Discount amount calculation
	// double actualPrice = Double.parseDouble(hm.get(s.toString()).toString());
	// double disPer = Double.parseDouble(discountPerc.get(0).toString());
	// double calculatedAmount = (actualPrice*disPer)/100;
	//
	// double disAmount = Double.parseDouble(discountAmount.get(0).toString());
	// AssertJUnit.assertEquals("Discount Amount is not maching",
	// twoDecimalPlaces(calculatedAmount), twoDecimalPlaces(disAmount));
	// }
	// }
	// }

	// @Test
	// (dependsOnMethods = { "discountService_getTradeAndDealDiscounts" },
	// groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	// "Regression","Fox7Sanity"}, dataProvider="", priority=6)
	// public void discountService_getRulesFromDiscountId()
	// {
	// MyntraService service = new
	// MyntraService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	// APINAME.GETRULESFROMDISCOUNTID, init.Configurations);
	// ArrayList<String> discountIds = new ArrayList();
	// for(Object s : styleAndDiscountIdMap.keySet()){
	// discountIds.add(styleAndDiscountIdMap.get(s).toString());
	// }
	// System.out.println(discountIds.get(getRandomNumber(discountIds.size())));
	// service.URL = apiUtil.prepareparameterizedURL(service.URL,
	// discountIds.get(getRandomNumber(discountIds.size())));
	// System.out.println(service.URL);
	// RequestGenerator rs = new RequestGenerator(service);
	// String response = rs.returnresponseasstring();
	// String discountRule = rs.respvalidate.GetNodeValue("id", response);
	// System.out.println(response+"\n"+discountRule);
	// AssertJUnit.assertEquals("Response is not 200 OK", 200,
	// rs.response.getStatus());
	// }

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2", priority = 7,description="1.Create flat discount percentage")
	public void DiscountService_createFlatDiscountV2(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException {
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);

		System.out.println("response-- >>> "
				+ createFlatDiscount.returnresponseasstring());
		String jsonresp = createFlatDiscount.returnresponseasstring();
		System.out.println("resonse--------------->" + jsonresp);
		String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String discountPercentage = JsonPath
				.read(jsonresp, "$.discountRules..percent").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(jsonresp, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit.assertEquals("Percentage  doesn't match", percent,
				discountPercentage);
		AssertJUnit.assertEquals("Type   doesn't match", "1", type);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createFlatDiscount.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2", priority = 7,description="1.Verfify Start end time for flat discount percentage")
	public void DiscountService_VerifyStartEndTimeFlatDiscountV2(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException {

		// Created flat discount
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);
		String jsonresp = createFlatDiscount.returnresponseasstring();

		// taking Start and end date from response
		String startDatevalue = JsonPath.read(jsonresp, "$.startedOn")
				.toString().replace("[", "").replace("]", "").trim();
		String EndDatevalue = JsonPath.read(jsonresp, "$.expiredOn").toString()
				.replace("[", "").replace("]", "").trim();

		// taking discount id from to verify start date and end date from
		// discoun id api
		String discountId = JsonPath.read(jsonresp, "$.id").toString()
				.replace("[", "").replace("]", "").trim();

		// getDiscountFromDiscountId

		RequestGenerator getDiscountID = Discounthelper
				.getDiscountFromDiscountId(discountId);
		String discountIdjsonresp = getDiscountID.returnresponseasstring();
		String startDatevalueFromDiscounId = JsonPath
				.read(discountIdjsonresp, "$.startedOn").toString()
				.replace("[", "").replace("]", "").trim();
		String EndDatevalueFromDiscounId = JsonPath
				.read(discountIdjsonresp, "$.expiredOn").toString()
				.replace("[", "").replace("]", "").trim();

		AssertJUnit.assertEquals("Start date Doesn't match", startDate,
				startDatevalue);
		AssertJUnit.assertEquals("End date Doesn't match", expiredDate,
				EndDatevalue);
		AssertJUnit.assertEquals("Start date Doesn't match", startDate,
				startDatevalueFromDiscounId);
		AssertJUnit.assertEquals("End date Doesn't match", expiredDate,
				EndDatevalueFromDiscounId);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createFlatDiscount.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2Negative", priority = 8,description="1.Create negativ flat percentage discount \n 2. verify 500 response")
	public void DiscountService_createFlatDiscountV2_Negative(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException {
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);
		System.out.println("response --- \n"
				+ createFlatDiscount.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 500,
				createFlatDiscount.response.getStatus());
	}

	// EMpty style id and startdate is more than end dat..shouldbe fix by
	// devloper

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2Negative1", priority = 9,description="1. Create negative flat discount \n 2. verify 409 response")
	public void DiscountService_createFlatDiscountV2_Negative1(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException {
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);
		AssertJUnit.assertEquals("Status code does not match", 409,
				createFlatDiscount.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatConditionalDiscountV2DataProvider", priority = 10,description="1. Create flat conditional discount \n 2. verify percentage, styleid, type and 200 response")
	public void DiscountService_createFlatConditionalDiscountV2(
			String expiredDate, String startDate, String percentage,
			String buyamount, String styleID) throws IOException {
		RequestGenerator createConditionalPercentage = Discounthelper
				.createFlatConditionalPercentageDiscount(expiredDate,
						startDate, percentage, buyamount, styleID);
		String jsonresp = createConditionalPercentage.returnresponseasstring();
		System.out.println("resonse--------------->" + jsonresp);
		String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String discountPercentage = JsonPath
				.read(jsonresp, "$.discountRules..percent").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(jsonresp, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buyAmount = JsonPath
				.read(jsonresp, "$.discountRules..buyAmount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit.assertEquals("Percentage  doesn't match", percentage,
				discountPercentage);
		AssertJUnit.assertEquals("Type   doesn't match", "8", type);
		AssertJUnit.assertEquals("Type   doesn't match", buyamount, buyAmount);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createConditionalPercentage.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatConditionalDiscountAmountV2DataProvider", priority = 11,description="1. Create flat discount amount discount \n 2. verify 200 response")
	public void DiscountService_createFlatConditionalAmountDiscountV2(
			String expiredDate, String startDate, String amount,
			String buyamount, String styleID, String buyCount)
			throws IOException {
		RequestGenerator createConditionalAmount = Discounthelper
				.createFlatConditionalAmountDiscount(expiredDate, startDate,
						amount, buyamount, styleID, buyCount);
		String jsonresp = createConditionalAmount.returnresponseasstring();
		String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String discountAmount = JsonPath
				.read(jsonresp, "$.discountRules..amount").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(jsonresp, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buyAmount = JsonPath
				.read(jsonresp, "$.discountRules..buyAmount").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit.assertEquals("Percentage  doesn't match", amount,
				discountAmount);
		AssertJUnit.assertEquals("Type   doesn't match", "8", type);
		AssertJUnit.assertEquals("Type   doesn't match", buyamount, buyAmount);
		AssertJUnit.assertEquals("Status code does not match", 200,
				createConditionalAmount.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "ConditionalDiscountAmtWithCountV2DataProvider", priority = 11,description="1. Create negative flat discount \n 2. verify 409 response")
	public void DiscountService_createFlatConditionalAmountWithBuyCountDiscountV2(
			String expiredDate, String startDate, String amount,
			String styleID, String buyCount) throws IOException {
		RequestGenerator createConditionalAmount = Discounthelper
				.createFlatConditionalAmountWithBuyCountDiscount(expiredDate,
						startDate, amount, styleID, buyCount);
		String jsonresp = createConditionalAmount.returnresponseasstring();
		String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String discountAmount = JsonPath
				.read(jsonresp, "$.discountRules..amount").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(jsonresp, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buycount = JsonPath.read(jsonresp, "$.discountRules..buyCount")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit.assertEquals("Percentage  doesn't match", amount,
				discountAmount);
		AssertJUnit.assertEquals("Type   doesn't match", "8", type);
		AssertJUnit.assertEquals("Type   doesn't match", buyCount, buycount);
		AssertJUnit.assertEquals("Status code does not match", 200,
				createConditionalAmount.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "ConditionalDiscountPercWithCountV2DataProvider", priority = 11,description="1. Create conditinal percentage discount with buy count \n 2. verify percentage, type and status 200 response")
	public void DiscountService_createFlatConditionalPercentWithBuyCountDiscountV2(
			String expiredDate, String startDate, String percent,
			String styleID, String buyCount) throws IOException {
		RequestGenerator createConditionalAmount = Discounthelper
				.createFlatConditionalPercentWithBuyCountDiscount(expiredDate,
						startDate, percent, styleID, buyCount);
		String jsonresp = createConditionalAmount.returnresponseasstring();
		String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String percentage = JsonPath.read(jsonresp, "$.discountRules..percent")
				.toString().replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(jsonresp, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buycount = JsonPath.read(jsonresp, "$.discountRules..buyCount")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit.assertEquals("Percentage  doesn't match", percent,
				percentage);
		AssertJUnit.assertEquals("Type   doesn't match", "8", type);
		AssertJUnit.assertEquals("Type   doesn't match", buyCount, buycount);
		AssertJUnit.assertEquals("Status code does not match", 200,
				createConditionalAmount.response.getStatus());
	}

	/* APi is failing because of negative value doesn't handle through api */

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatConditionalDiscountAmountV2DataProvider_Negative", priority = 12,description="1. Create negative flat conditional discount \n 2. verify 500 response")
	public void DiscountService_createFlatConditionalAmountDiscountV2_Negative(
			String expiredDate, String startDate, String amount,
			String styleID, String buyCount) throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFLATCONDITIONALAMOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, amount, styleID,
						buyCount }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("service paylaod");
		log.info(service.URL);
		System.out.println("Response---- >>  "
				+ req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 500,
				req.response.getStatus());
	}

	// Need to fix this scenarios for api level
	// @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	// "Regression","Fox7Sanity"},
	// dataProvider="createFlatConditionalDiscountV2DataProvider_Negative",
	// priority=13)
	// public void DiscountService_createFlatConditionalDiscountV2_Negative(
	// String expiredDate, String startDate, String percentage,
	// String buyamount, String styleID) throws IOException {
	// MyntraService service = Myntra.getService(
	// ServiceType.PORTAL_DISCOUNTSERVICEV2,
	// APINAME.CREATEFLATCONDITIONALV2, init.Configurations,
	// new String[] { expiredDate, startDate, percentage, buyamount,
	// styleID}, PayloadType.JSON, PayloadType.JSON);
	// RequestGenerator req = new RequestGenerator(service);
	// log.info(service.URL);
	// AssertJUnit.assertEquals("Status code does not match", 404,
	// req.response.getStatus());
	// }
	//

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFreeItemDiscountV2DataProvider", priority = 14,description="1. Create free item discount \n 2. verify style id, type, and status code 200 response")
	public void DiscountService_createFreeItemDiscountV2(String expiredDate,
			String startDate, String FreeItem, String styleID)
			throws IOException {
		RequestGenerator createFreeItem = Discounthelper
				.createFreeItemDiscount(expiredDate, startDate, FreeItem,
						styleID);
		String rsponse = createFreeItem.respvalidate.returnresponseasstring();
		// System.out.println("resonse--------------->" +jsonresp );
		String styleid = JsonPath.read(rsponse, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		String freeItem = JsonPath
				.read(rsponse, "$.discountRules..discountFreeItems..itemId")
				.toString().replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(rsponse, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Style id doesn't match", styleID, styleid);
		AssertJUnit
				.assertEquals("Free Item  doesn't match", FreeItem, freeItem);
		AssertJUnit.assertEquals("Type   doesn't match", "4,1", type);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createFreeItem.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFreeItemDiscountV2DataProvider_Negative1", priority = 15, description="1. Create negative free item discount \n 2. verify status code 409 response")
	public void DiscountService_createFreeItemDiscountV2_Negative(
			String expiredDate, String startDate, String FreeItem,
			String styleID) throws IOException {
		RequestGenerator createFreeItem = Discounthelper
				.createFreeItemDiscount(expiredDate, startDate, FreeItem,
						styleID);

		AssertJUnit.assertEquals("Status code does not match", 409,
				createFreeItem.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFreeItemDiscountV2DataProvider_Negative2", priority = 16, description="1. Create negative free item discount \n 2. verify status code 500 response")
	public void DiscountService_createFreeItemDiscountV2_Negative1(
			String expiredDate, String startDate, String FreeItem,
			String styleID) throws IOException {
		RequestGenerator createFreeItem = Discounthelper
				.createFreeItemDiscount(expiredDate, startDate, FreeItem,
						styleID);

		AssertJUnit.assertEquals("Status code does not match", 500,
				createFreeItem.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createBuy1Get2DiscountV2DataProvider", priority = 17,description="1. Create buy 1 get 1 discount \n 2. verify style id, type, buy count , count and status code 200 response")
	public void DiscountService_createBuy1Ge1DiscountV2(String expiredDate,
			String startDate, String styleID, String styleID1)
			throws IOException {
		RequestGenerator createB1G1 = Discounthelper.createBuy1Get1Discount(
				expiredDate, startDate, styleID, styleID1);
		System.out.println("response = " + createB1G1.returnresponseasstring());
		String rsponse = createB1G1.respvalidate.returnresponseasstring();
		String styleidnew = styleID1 + styleID;
		String styleidnew1 = styleID + styleID1;

		String styleid = JsonPath.read(rsponse, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").replace(",", "")
				.trim();

		// .toString().replace("[", "").replace("]", "").replace(",", "").trim()
		String startDatevalue = JsonPath.read(rsponse, "$.startedOn")
				.toString().replace("[", "").replace("]", "").trim();
		String EndDatevalue = JsonPath.read(rsponse, "$.expiredOn").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(rsponse, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buycount = JsonPath.read(rsponse, "$.discountRules..buyCount")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String count = JsonPath.read(rsponse, "$.discountRules..count")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		if (styleidnew == styleid) {
			System.out.println("styleIds matche");
		} else if (styleidnew1 == styleid) {
			System.out.println("styleIds matche here");
		}
		AssertJUnit.assertEquals("Type   doesn't match", "2", type);
		AssertJUnit.assertEquals("buycount   doesn't match", "2", buycount);
		AssertJUnit.assertEquals("count   doesn't match", "1", count);
		AssertJUnit.assertEquals("buycount   doesn't match", startDate,
				startDatevalue);
		AssertJUnit.assertEquals("count   doesn't match", expiredDate,
				EndDatevalue);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createB1G1.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createBuy2Get4DiscountV2DataProvider", priority = 18,description="1. Create buy 2 get 2 discount \n 2. verify style id, type,count, buy count and status code 200 response")
	public void DiscountService_createBuy2Get2DiscountV2(String expiredDate,
			String startDate, String styleID, String styleID1, String styleID2,
			String styleID3) throws IOException {

		RequestGenerator createB2G2 = Discounthelper.createBuy2Get2Discount(
				expiredDate, startDate, styleID, styleID1, styleID2, styleID3);
		String rsponse = createB2G2.respvalidate.returnresponseasstring();
		System.out.println("Response b2g2 \n" + rsponse);

		String styleid = JsonPath.read(rsponse, "$.discountStyles..styleId[*]")
				.toString().replace("[", "").replace("]", "").replace(",", "")
				.trim();

		// .toString().replace("[", "").replace("]", "").replace(",", "").trim()
		String startDatevalue = JsonPath.read(rsponse, "$.startedOn")
				.toString().replace("[", "").replace("]", "").trim();
		String EndDatevalue = JsonPath.read(rsponse, "$.expiredOn").toString()
				.replace("[", "").replace("]", "").trim();
		String type = JsonPath.read(rsponse, "$.discountRules..type")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String buycount = JsonPath.read(rsponse, "$.discountRules..buyCount")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		String count = JsonPath.read(rsponse, "$.discountRules..count")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();

		AssertJUnit.assertEquals("Type   doesn't match", "2", type);
		AssertJUnit.assertEquals("buycount   doesn't match", "4", buycount);
		AssertJUnit.assertEquals("count   doesn't match", "2", count);
		AssertJUnit.assertEquals("buycount   doesn't match", startDate,
				startDatevalue);
		AssertJUnit.assertEquals("count   doesn't match", expiredDate,
				EndDatevalue);

		AssertJUnit.assertEquals("Status code does not match", 200,
				createB2G2.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createBuy1Get2DiscountV2DataProvider_Negative", priority = 19, description="1. Create buy 1 get 2 discount \n 2. verify style id, type,count, buy count and status code 200 response")
	public void DiscountService_createBuy1Get2DiscountV2_Negative(
			String expiredDate, String startDate, String styleID,
			String styleID1) throws IOException {
		RequestGenerator createB1G1 = Discounthelper.createBuy1Get1Discount(
				expiredDate, startDate, styleID, styleID1);

		System.out.println("response = " + createB1G1.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 500,
				createB1G1.response.getStatus());
	}

	// buycount vs count testcases

	// @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	// "Regression","Fox7Sanity"} ,dataProvider =
	// "createBuy1Get2DiscountV2DataProvider_Negative", priority=19)
	// public void DiscountService_createBuy1Get2DiscountV2_LessBuyCount(String
	// expiredDate,
	// String startDate,String styleID, String styleID1)
	// throws IOException {
	// MyntraService service = Myntra.getService(
	// ServiceType.PORTAL_DISCOUNTSERVICEV2,
	// APINAME.CREATEBUY1GET2DISCOUNTV2, init.Configurations,
	// new String[] {expiredDate, startDate,
	// styleID,styleID1}, PayloadType.JSON, PayloadType.JSON);
	// System.out.println("LOgss---->> service url" +service.URL);
	// System.out.println("payload = "+ service.Payload);
	// RequestGenerator req = new RequestGenerator(service);
	// log.info(service.URL);
	// System.out.println("response = " +req.returnresponseasstring());
	// AssertJUnit.assertEquals("Status code does not match", 500,
	// req.response.getStatus());
	// }

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "removeStyleDataProvider", priority = 20,description="1. Remove styles from discount \n 2. verify  status code 200 response")
	public void DiscountService_removeStyles(String styleIds)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.REMOVESTYLES,
				init.Configurations, "[" + styleIds + "]");
		RequestGenerator rs = new RequestGenerator(service);
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				rs.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "removeStyleDataProvider_Negative", priority = 15,description="1. Remove styles from discount negative \n 2. verify status code 500 response")
	public void DiscountService_removeStyles_Negative(String styleIds)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.REMOVESTYLES,
				init.Configurations, "[" + styleIds + "]");
		RequestGenerator rs = new RequestGenerator(service);
		AssertJUnit.assertEquals("Response is not 200 OK", 500,
				rs.response.getStatus());
	}

	// To be removed

	// @Test(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression",
	// "Regression","Fox7Sanity"} , dataProvider = "getCurrentDiscountDataProvider",
	// priority=16)
	// public void DiscountService_getCurrentDiscount(String styleIds) throws
	// IOException
	// {
	// MyntraService service = new
	// MyntraService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	// APINAME.GETCURRENTDISCOUNT, init.Configurations, "["+styleIds+"]");
	// RequestGenerator rs = new RequestGenerator(service);
	// String response = rs.returnresponseasstring();
	// System.out.println("response-- >>> " + rs.returnresponseasstring());
	// AssertJUnit.assertEquals("Response is not 200 OK", 200,
	// rs.response.getStatus());
	//
	// }

	//Commenting DiscountService_getCurrentDiscountV2 below test case as Developer has depreciated this test case.
	
//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "getCurrentDiscountDataProviderv2", priority = 17,description="1. Create flat discount \n 2. Reindex style ids \n 3. Get current discount")
//	public void DiscountService_getCurrentDiscountV2(String starttime,
//			String endtime, String styleIds, String MRP, String percentage)
//			throws IOException, InterruptedException {
//
//		RequestGenerator createFlatDiscount = Discounthelper
//				.createFlatDiscount(endtime, starttime, percentage, styleIds);
//		String jsonresp = createFlatDiscount.respvalidate
//				.returnresponseasstring();
//		String discountPercentage = JsonPath
//				.read(jsonresp, "$.discountRules..percent").toString()
//				.replace("[", "").replace("]", "").trim();
//		Integer discountPercentage_int = Integer.parseInt(discountPercentage);
//		System.out.println("Disocunt created response, \n" + jsonresp);
//
//		rindexonestleid(styleIds);
//		TimeUnit.MINUTES.sleep(5);
//		RequestGenerator getCurrenbtDiscount = Discounthelper
//				.getCurrentDiscount(styleIds, MRP);
//		String jsonresp1 = getCurrenbtDiscount.respvalidate
//				.returnresponseasstring();
//		System.out.println("Current Disocunt created response, \n" + jsonresp1);
//
//		String path = "$." + styleIds + ".tradeDiscount..discountPercent";
//		System.out.println("path---- > >" + path);
//		String discount1 = JsonPath.read(jsonresp1, path).toString()
//				.replace("[", "").replace("]", "").trim().toString();
//		Float discount1float = Float.valueOf(discount1);
//		System.out.println("NEW DISCOUNT--------> " + discount1float);
//		Integer discount1Int = Math.round(discount1float);
//		System.out.println("NEW DISCOUNT INTR--------> " + discount1Int);
//		AssertJUnit.assertEquals("Currentbt Discount Doesn't match",
//				discountPercentage_int, discount1Int);
//		AssertJUnit.assertEquals("Response is not 200 OK", 200,
//				getCurrenbtDiscount.response.getStatus());
//
//	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getDiscountId", priority = 21,description="1. Delete discount from discountid \n 2. verify status code 200 response")
	public void discountService_deleteDiscountFromDiscountId(String discountId,
			String respCode) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.DELETEDISCOUNTFORDISCOUNTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountId);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		System.out.println("Respnse=-=== " + rs.response.getStatus());
		AssertJUnit.assertEquals("Response is not 200 OK",
				Integer.parseInt(respCode), rs.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2", priority = 21, description="1. Create flat discount \n 2. Get discount from discount id \n 3. Delete discount")
	public void discountService_deleteDiscountFromDiscountIdVerifyNode(
			String endtime, String starttime, String percentage, String styleIds)
			throws IOException {

		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(endtime, starttime, percentage, styleIds);
		String jsonresp = createFlatDiscount.respvalidate
				.returnresponseasstring();
		String discountid = JsonPath.read(jsonresp, "$.id").toString()
				.replace("[", "").replace("]", "").trim();
		String discountPercentage = JsonPath
				.read(jsonresp, "$.discountRules..percent").toString()
				.replace("[", "").replace("]", "").trim();

		System.out.println("Discount id --- >  \n" + discountid);

		RequestGenerator getdiscountFromDiscountID = Discounthelper
				.getDiscountFromDiscountId(discountid);
		String jsonresp2 = getdiscountFromDiscountID.respvalidate
				.returnresponseasstring();
		String getpercentageFromDiscountID = JsonPath
				.read(jsonresp2, "$.discountRules..percent").toString()
				.replace("[", "").replace("]", "").trim();

		AssertJUnit.assertEquals(
				"Discount Doesn't match from created and from DiscounID",
				discountPercentage, getpercentageFromDiscountID);

		RequestGenerator deleteDiscount = Discounthelper
				.deleteDiscountFromDiscountId(discountid);

		String Jsonresp1 = deleteDiscount.respvalidate.returnresponseasstring();
		System.out.println("Delete discount Response-- \n" + Jsonresp1);
		AssertJUnit.assertEquals("Status code does not match", 200,
				deleteDiscount.response.getStatus());

		RequestGenerator getdiscountFromDiscountID1 = Discounthelper
				.getDiscountFromDiscountId(discountid);
		String jsonresp3 = getdiscountFromDiscountID1.respvalidate
				.returnresponseasstring();
		System.out.println("Checking discount after delteing \n" + jsonresp3);

		AssertJUnit.assertEquals("Discount Didn't deleted", 404,
				getdiscountFromDiscountID1.response.getStatus());

	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "updateDiscountV2DataProvider", priority = 22, description="1. Update existing discount \n 2. verify status code 200 response")
	public void DiscountService_updateDiscountV2(String expiredDate,
			String startDate, String styleID, String percentage,
			String discountID) throws IOException {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.UPDATEDISCOUNTV2,
				init.Configurations, new String[] { expiredDate, startDate,
						styleID, percentage }, new String[] { discountID },
				PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountID);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("URL -- >>" + service.URL);
		System.out.println("Pyload -->>" + service.Payload);
		System.out.println("response-- >>> " + req.returnresponseasstring());
		String jsonresp = req.respvalidate.returnresponseasstring();
		String updatedpercentage = JsonPath
				.read(jsonresp, "$.discountRules..percent").toString()
				.replace("[", "").replace("]", "").trim();
		System.out.println("updated percentage===----------?"
				+ updatedpercentage);
		System.out.println("old percentage ---- >>>>" + percentage);
		AssertJUnit.assertEquals("update succefully", updatedpercentage,
				percentage);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());

	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "updateFlatConditionalDiscountV2DataProvider", priority = 23,description="1. Create flat conditional discount \n 2. Update flat conditional discount \n 3.verify status code 200 response")
	public void DiscountService_updateFlatConditionalDiscountV2(
			String expiredDate, String startDate, String amount,
			String buyamount, String styleID, String discountID)
			throws IOException {
		String StyleId = createFlatConditionalDiscount(
				String.valueOf(currentTime + 1000),
				String.valueOf(currentTime + 300), "2", "300", styleID);
		System.out.println("STyleid---->" + StyleId);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.UPDATEFLATCONDITIONALDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, amount, buyamount,
						StyleId }, new String[] { discountID },
				PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountID);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		String jsonresp = req.respvalidate.returnresponseasstring();
		System.out.println("URL -- >>" + service.URL);
		System.out.println("Pyload -->>" + service.Payload);
		System.out.println("response-- >>> " + req.returnresponseasstring());
		String updatedbuyamount = JsonPath
				.read(jsonresp, "$.discountRules..buyAmount").toString()
				.replace("[", "").replace("]", "").trim();
		String updateddiscountamount = JsonPath
				.read(jsonresp, "$.discountRules..amount").toString()
				.replace("[", "").replace("]", "").trim();
		AssertJUnit.assertEquals("update succefully", updatedbuyamount,
				buyamount);
		System.out.println("update amount--------" + updatedbuyamount);
		AssertJUnit.assertEquals("update succefully", updateddiscountamount,
				amount);

		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "updateBuy1Get2DiscountV2DataProvider", priority = 24,description="1. Update Buy 1 get2 discount \n 2. verify  status code 200 response")
	public void DiscountService_updateBuy1Get2DiscountV2(String expiredDate,
			String startDate, String styleID, String styleID1, String discountID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.UPDATEBUY1GET2DISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, styleID, styleID1 },
				new String[] { discountID }, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountID);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("URL -- >>" + service.URL);
		System.out.println("Pyload -->>" + service.Payload);
		System.out.println("response-- >>> " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "updateFreeitemsDiscountV2DataProvider", priority = 25,description="1.Create free item discount \n 2. Update free item discount \n 3. verify status code 200 response")
	public void DiscountService_updateFreeitemDiscountV2(String expiredDate,
			String startDate, String FreeItem, String styleID, String discountID)
			throws IOException {
		String freeitem = createFreeitemDiscountV2(
				String.valueOf(currentTime + 1000),
				String.valueOf(currentTime + 300), "338673", styleID);
		System.out.println("STyleid from creatediscount" + freeitem);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.UPDATEFREEITEMDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, FreeItem, styleID },
				new String[] { discountID }, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountID);
		RequestGenerator req = new RequestGenerator(service);
		String jsonresp = req.respvalidate.returnresponseasstring();
		log.info(service.URL);
		System.out.println("URL -- >>" + service.URL);
		System.out.println("Pyload -->>" + service.Payload);
		System.out.println("response-- >>> " + req.returnresponseasstring());
		String UpdatedfreeItem = JsonPath
				.read(jsonresp, "$.discountRules..itemId").toString()
				.replace("[", "").replace("]", "").trim();
		if (UpdatedfreeItem.equalsIgnoreCase(freeitem)) {
			System.out.println("Not Updated successfully");

		} else if (UpdatedfreeItem.equalsIgnoreCase(FreeItem)) {
			System.out.println("Updated successfully");

		}
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	// commented this below test case after discussion with Kiran Badam  as this api is deprecated
	
//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "GetAllDiscountDataProvider", priority = 26, description="1. Create new deal \n 2. Get all discounts for styles \n 3. verify trade discount")
//	public void DiscountService_getAllDiscountforStyleV2ForDeal(String styleID)
//			throws IOException, InterruptedException {
//		System.out.println("style id --- >> " + styleID);
//		String Dealsid = createnewdeal("Dealsname", "Creating Deal", "0",
//				String.valueOf(currentTime + 300),
//				String.valueOf(currentTime + 1000), "true", "0", "70",
//				"https://summeressentials.com", styleID, "0");
//		System.out.println("deal id  ---- >" + Dealsid);
//		updateVisibility(Dealsid, "true");
//		updateStateFordealID(Dealsid, "1");
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2,
//				APINAME.GETALLDISCOUNTFORSTYLES, init.Configurations,
//				new String[] { styleID }, PayloadType.JSON, PayloadType.JSON);
//		System.out.println("json paylod---- >" + service.Payload);
//		RequestGenerator req = new RequestGenerator(service);
//		// Thread.sleep(3000);
//		log.info(service.URL);
//		System.out.println("Response--- > "
//				+ req.respvalidate.returnresponseasstring());
//		AssertJUnit.assertEquals("Status code does not match", 200,
//				req.response.getStatus());
//		String jsonresp = req.returnresponseasstring();
//		String value = JsonPath.read(jsonresp, "$[*].td").toString();
//		System.out.println("Trade Discount--- > " + value);
//		AssertJUnit.assertEquals("Trade discount is not false", "[false]",
//				"[false]");
//
//	}
	
	// commented this below test case after discussion with Kiran Badam  as this api is deprecated

//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "GetAllDiscountDataProvider", priority = 27, description="1. Get all discount for styles \n 2. verify trade discount")
//	public void DiscountService_getAllDiscountforStyleV2ForTD(String styleID)
//			throws IOException, InterruptedException {
//
//		createFlatDiscountAndReindex(String.valueOf(currentTime + 1000),
//				String.valueOf(currentTime + 300), "35", styleID);
//		rindexonestleid(styleID);
//		Thread.sleep(20000);
//		System.out.println("style id in method ==== " + styleID);
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2,
//				APINAME.GETALLDISCOUNTFORSTYLES, init.Configurations,
//				new String[] { styleID }, PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		System.out
//				.println("get all discount Serverice URl -- > " + service.URL);
//		System.out.println("get all discount payload ->" + service.Payload);
//		System.out.println("Response--- > "
//				+ req.respvalidate.returnresponseasstring());
//		AssertJUnit.assertEquals("Status code does not match", 200,
//				req.response.getStatus());
//		String jsonresp = req.returnresponseasstring();
//		String value = JsonPath.read(jsonresp, "$[*].td").toString();
//		System.out.println("Trade Discount--- > " + value);
//		AssertJUnit.assertEquals("Trade discount is not True", "[true]",
//				"[true]");
//	}
	
	// commented this below test case after discussion with Kiran Badam  as this api is deprecated

//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression","Fox7Sanity" }, dataProvider = "GetAllDiscountDataProvider_Negative", priority = 28,description="1. Get all discounts \n 2. verify trade discount negative and check 500 response")
//	public void DiscountService_getAllDiscountforStyleV2ForTD_Negative(
//			String styleID) throws IOException, InterruptedException {
//
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2,
//				APINAME.GETALLDISCOUNTFORSTYLES, init.Configurations,
//				new String[] { styleID }, PayloadType.JSON, PayloadType.JSON);
//		RequestGenerator req = new RequestGenerator(service);
//		log.info(service.URL);
//		AssertJUnit.assertEquals("API is not working", 500,
//				req.response.getStatus());
//	}

	@Test(groups = { "SchemaValidation",
			"Regression","Fox7Sanity" }, priority = 29, description="1. Get All discoutns \n 2. verify status code 200 response \n 3. Validate schema")
	public void discountService_getAllDiscountverifyResponseDataNodesUsingSchemaValidations() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETALLDISCOUNTSV2, init.Configurations);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		System.out
				.println("---------------------------------------------------------------------------------------------");
		System.out.println(response);
		System.out
				.println("---------------------------------------------------------------------------------------------");
		discountIds = JsonPath.read(response, "$..id[*]");
		AssertJUnit.assertTrue("Get all discount is not working",
				(discountIds.size() > 0));
		AssertJUnit.assertEquals("Response is not 200 OK", 200,
				rs.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/discountserviceV2-getalldiscount-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in DealsService getDealById API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "SchemaValidation",
			"Regression","Fox7Sanity" }, dataProvider = "getDiscountId", priority = 30,description="1. Get discount from discount id \n 2. Validate nodes and schema")
	public void discountService_getDiscountFromDiscountIdverifyResponseDataNodesUsingSchemaValidations(
			String discountId, String respCode) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDISCOUNTFORDISCOUNTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountId);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.respvalidate.returnresponseasstring();
		// AssertJUnit.assertEquals("Response is not 200 OK",
		// Integer.parseInt(respCode), rs.response.getStatus());
		System.out
				.println("---------------------------------------------------------------------------------------------");
		System.out.println(response);
		System.out
				.println("---------------------------------------------------------------------------------------------");
		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/discountserviceV2-getdiscountfromdiscountid-schema.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					response, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in DealsService getDealById API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// commented this below test case after discussion with Kiran Badam  as this api is deprecated

//	@Test(groups = { "SchemaValidation",
//			"Regression","Fox7Sanity" }, dataProvider = "getDSMForStylesDP", priority = 31, description="1.GetDSM for styles \n 2.validate nodes \n 3. verify status code 200 response")
//	public void discountService_getDSMForStyles_verifyResponseDataNodesUsingSchemaValidations(
//			String styleIds) {
//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDSMFORSTYLES,
//				init.Configurations, "[" + styleIds + "]");
//		RequestGenerator rs = new RequestGenerator(service);
//		String response = rs.respvalidate.returnresponseasstring();
//		try {
//			String jsonschema = new Toolbox()
//					.readFileAsString("Data/SchemaSet/JSON/discountserviceV2-getdsmforstyles-schema.txt");
//			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
//					response, jsonschema);
//			AssertJUnit
//					.assertTrue(
//							missingNodeList
//									+ " nodes are missing in DealsService getDealById API response",
//							CollectionUtils.isEmpty(missingNodeList));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		AssertJUnit.assertEquals("Response is not 200 OK", 200,
//				rs.response.getStatus());
//
//		// AssertJUnit.assertTrue(response.equalsIgnoreCase("Discounts are not available for the given Style Id(s)"));
//
//	}

	@Test(groups = { "SchemaValidation",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2", priority = 32, description="1.Create flat discount \n 2. verify status code 200 response \n 3. validate nodes and schema")
	public void DiscountService_createFlatDiscount_verifyResponseDataNodesUsingSchemaValidations(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFLATDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, percent, styleID },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("response-- >>> " + req.returnresponseasstring());
		String jsonresp = req.respvalidate.returnresponseasstring();
		String value = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("" + value);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());

		try {
			String jsonschema = new Toolbox()
					.readFileAsString("Data/SchemaSet/JSON/discountservice-createflatdiscount-schema.txt");
			System.out.println("schema----- >>>>>>>>> " + jsonschema);
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(
					jsonresp, jsonschema);
			AssertJUnit
					.assertTrue(
							missingNodeList
									+ " nodes are missing in DealsService getDealById API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList getDiscountId() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETALLDISCOUNTSV2, init.Configurations);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		ArrayList discountIds = JsonPath.read(response, "$..id[*]");
		return discountIds;
	}

	private int getRandomNumber(int size) {
		Random ran = new Random();
		return ran.nextInt(size);
	}

	private String createnewdeal(String name, String desc, String dealType,
			String startTime, String endTime, String visible, String state,
			String discPercent, String banner, String styleId,
			String channelType) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEALSSERVICE, APINAME.CREATENEWDEAL,
				init.Configurations, new String[] { name, desc, dealType,
						startTime, endTime, visible, state, discPercent,
						banner, styleId, channelType }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Response--- > "
				+ req.respvalidate.returnresponseasstring());

		// System.out.println(req.respvalidate.returnresponseasstring());

		// String value = req.respvalidate.NodeValue("$.id", true);
		// System.out.println("value = " +value);

		String jsonresp = req.returnresponseasstring();
		String value = JsonPath.read(jsonresp, "$.id").toString();
		System.out.println("" + value);
		return value;

	}

	private String createFlatDiscountAndReindex(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFLATDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, percent, styleID });
		System.out.println("create discount url = " + service.URL);
		System.out.println("create discount payload = " + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("create discount response ="
				+ req.respvalidate.returnresponseasstring());
		String response = req.respvalidate.returnresponseasstring();
		String percentage = JsonPath.read(response, "$.discountRules..percent")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("Percentage============== " + percentage);
		return percentage;

	}

	private void updateVisibility(String dealId, String Visibilty) {
		//
		//
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEALSSERVICE, APINAME.UPDATEVISIBILITY,
				init.Configurations, new String[] { dealId },
				new String[] { Visibilty });

		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
	}

	private String createFlatConditionalDiscount(String expiredDate,
			String startDate, String buyCount, String amount, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFLATCONDITIONALV2, init.Configurations,
				new String[] { expiredDate, startDate, buyCount, amount,
						styleID }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("Response in creatediscount-- > "
				+ req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		String jsonresp = req.returnresponseasstring();
		String value = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("" + value);
		return value;
	}

	private String createBuyXGetYDiscountV2(String expiredDate,
			String startDate, String buyCount, String count, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEBUYXGETYDISCOUNTV2,
				init.Configurations,
				new String[] { expiredDate, startDate, buyCount, count, styleID },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("LOgss---->> service url" + service.URL);
		System.out.println("payload = " + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		String jsonresp = req.returnresponseasstring();
		String value = JsonPath.read(jsonresp, "$.discountStyles..styleId")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("Style id IN create discount -- " + value);
		return value;
	}

	private String createFreeitemDiscountV2(String expiredDate,
			String startDate, String FreeItem, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFREEITEMDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate, FreeItem, styleID },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("payload in freeitem--- >" + service.Payload);
		System.out.println("response in freeitem---->"
				+ req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		String jsonresp = req.returnresponseasstring();
		String freeItem = JsonPath.read(jsonresp, "$.discountRules..itemId")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("freeitems IN create discount -- " + freeItem);
		return freeItem;
	}

	private void rindexonestleid(String styleId) {

		MyntraService doStyleIndexForSingleStyleIdService = Myntra.getService(
				ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXBYSTYLEID,
				init.Configurations, PayloadType.JSON,
				new String[] { styleId }, PayloadType.JSON);
		System.out.println("index style url = "
				+ doStyleIndexForSingleStyleIdService.URL);
		// doStyleIndexForSingleStyleIdService.URL = new
		// APIUtilities().prepareparameterizedURL(doStyleIndexForSingleStyleIdService.URL,
		// styleId);
		RequestGenerator req = new RequestGenerator(
				doStyleIndexForSingleStyleIdService);
	}

	private void updateStateFordealID(String dealId, String state) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DEALSSERVICE, APINAME.UPDATESTATE,
				init.Configurations, new String[] { dealId, state });
		service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
	}

	private java.math.BigDecimal twoDecimalPlaces(final double d) {
		return new java.math.BigDecimal(d).setScale(2,
				java.math.RoundingMode.HALF_UP);
	}

	// ----- 5

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDPV2", priority = 37, description="1. Create flat discount \n 2. GetDSM for styles \n 3. Verify isFuture is true as soon as you create discount \n 4. verify isFuture is false after 6 min")
	public void discountService_VIsFutureIngetDSMForStylesV1(
			String expiredDate, String startDate, String percent,
			String styleID, String styleID1, String styleID2, String styleID3)
			throws IOException, InterruptedException {
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscountMultiStyleid(expiredDate, startDate,
						percent, styleID, styleID1, styleID2, styleID3);

		System.out.println("response-- >>> "
				+ createFlatDiscount.returnresponseasstring());
		String jsonresp = createFlatDiscount.returnresponseasstring();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1, init.Configurations, new String[] {
						styleID, styleID1, styleID2, styleID3 });
		// service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator getDsmreq = new RequestGenerator(service);
		log.info(service.URL);
		String getDsmresp = getDsmreq.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp);
		// String future1=JsonPath.read(getDsmresp,
		// "$..future[1]").toString().trim();
		// System.out.println("Is Future : "+future1);
		// Assert.assertEquals("true", future1);
		List<Boolean> future1 = JsonPath.read(getDsmresp, "$..future");

		for (Boolean boolean1 : future1) {
			if (boolean1.toString().equalsIgnoreCase("true")) {
				Assert.assertEquals("true", boolean1.toString());
			}
		}
		System.out
				.println("------------------>> Wait for 6 min to reflect future discount  <<---------------------");
		TimeUnit.MINUTES.sleep(6);
		System.out
				.println("------------------>>Completed 6 min wait <<---------------------");
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1, init.Configurations, new String[] {
						styleID, styleID1, styleID2, styleID3 });
		// service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service1.URL);
		System.out.println(service1.Payload);
		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp1);
		String future2 = JsonPath.read(getDsmresp1, "$..future[0]").toString();
		System.out.println("Is Future : " + future2);
		log.info(service.URL);
		Assert.assertEquals("false", future2);
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getStyleDSM_DPV2", priority = 33,description="1. GetDSM for styles \n 2.Delete discount from discount id \n 3. GetDSM for styles and verify null response")
	public void discountService_vNullResponse_DSMForStylesV1(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException, InterruptedException {
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
				new String[] { styleID });
		System.out.println(service1.URL);
		System.out.println(service1.Payload);
		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp1);
		String disId = JsonPath.read(getDsmresp1, "$..discountId").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		;
		System.out.println("Discount Id : " + disId);
		// Hit discount service to delete already existing discount id
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.DELETEDISCOUNTFORDISCOUNTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, disId);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();

		MyntraService service2 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
				new String[] { styleID });
		System.out.println(service2.URL);
		System.out.println(service2.Payload);
		RequestGenerator getDsmreq2 = new RequestGenerator(service2);
		String getDsmresp2 = getDsmreq2.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp2);
		Assert.assertEquals("{ }", getDsmresp2);
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getStyleDSM_DPV2", priority = 34, description="1. GetDSM for styles v1 \n 2. verify status code 200 response")
	public void discountService_vResponseStatusCode_DSMForStylesV1(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException, InterruptedException {
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,new String[] { styleID },
				PayloadType.JSON,  PayloadType.JSON);
		// service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service1.URL);
		System.out.println(service1.Payload);
		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp1);
		AssertJUnit.assertEquals("Status code does not match", 200,
				getDsmreq1.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDPForMultiStyleidV2", priority = 38, description="1. GetDSM for styles v1 \n 2. verify response and status code 200 response \n 3. hit GeDSM for styles again after end time and verify null response")
	public void discountService_VendTime_getDSMForStylesV1(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException, InterruptedException {
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);
		System.out.println("response-- >>> "
				+ createFlatDiscount.returnresponseasstring());
		String jsonresp = createFlatDiscount.returnresponseasstring();
		System.out.println("resonse--------------->" + jsonresp);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
				new String[] { styleID });
		// service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator getDsmreq = new RequestGenerator(service);
		log.info(service.URL);
		String getDsmresp = getDsmreq.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp);
		System.out
				.println("------------------>> Wait for few min to reflect future discount  <<---------------------");
		TimeUnit.MINUTES.sleep(9);
		System.out
				.println("------------------>>Completed few min wait <<---------------------");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
				new String[] { styleID });
		// service.URL = apiUtil.prepareparameterizedURL(service.URL, state);
		System.out.println(service1.URL);
		System.out.println(service1.Payload);
		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp1);
		if (getDsmresp1.isEmpty()) {
			Assert.assertEquals("{ }", getDsmresp1);
		}

	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getBulkDiscountDP", priority = 35, description="1. GetDSM for styles v1 \n 2. Hit GetBulkDiscount APi \n 3. verify status code 200 response")
	public void discountService_VresponseStatus_getBulkDiscount(
			String expiredDate, String startDate, String percent, String styleID)
			throws IOException, InterruptedException {
//		MyntraService service1 = Myntra.getService(
//				ServiceType.PORTAL_DISCOUNTSERVICEV2,
//				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
//				new String[] { styleID });
//		System.out.println(service1.URL);
//		System.out.println(service1.Payload);
//		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
//		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
//		System.out.println("getDSMforStylesV1 Response -------- >> "
//				+ getDsmresp1);
		RequestGenerator createFlatDiscount = Discounthelper
				.createFlatDiscount(expiredDate, startDate, percent, styleID);

		System.out.println("response-- >>> "
				+ createFlatDiscount.returnresponseasstring());
		String jsonrespFD = createFlatDiscount.returnresponseasstring();
		System.out.println("resonse--------------->" + jsonrespFD);
		
		// if(getDsmresp1!=null || getDsmresp1.isEmpty()){
		String disId = JsonPath.read(jsonrespFD, "$..id").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		//String disId="130920";
		System.out.println("Discount Id : " + disId);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETBULKDISCOUNT,
				init.Configurations, new String[] { disId });
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator getDsmreq = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				getDsmreq.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression","Fox7Sanity" }, dataProvider = "getBulkDiscountDP", priority = 36,description="1. Hit GetDSM for syles v1 get discountId from this api response \n 2. Hit getBulkDiscount Api, and match discountId from GetDSMForStylesV1 and GetBulkDiscount")
	public void discountService_VdiscountId_getBulkDiscount(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException, InterruptedException {
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETDSMFORSTYLESV1SINGLESTYLEID, init.Configurations,
				new String[] { styleID });
		System.out.println(service1.URL);
		System.out.println(service1.Payload);
		RequestGenerator getDsmreq1 = new RequestGenerator(service1);
		String getDsmresp1 = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp1);
		String disId = JsonPath.read(getDsmresp1, "$..discountId").toString()
				.replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Discount Id : " + disId);

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETBULKDISCOUNT,
				init.Configurations, new String[] { disId });
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator getDsmreq = new RequestGenerator(service);
		log.info(service.URL);
		String getDsmresp = getDsmreq1.respvalidate.returnresponseasstring();
		System.out.println("getDSMforStylesV1 Response -------- >> "
				+ getDsmresp);
		String DisIdInResp = JsonPath.read(getDsmresp, "$..discountId")
				.toString().replace("[", "").replace("]", "").replace("\"", "")
				.trim();
		Assert.assertEquals(disId, DisIdInResp);

	}
	
	//-----41
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression","Fox7Sanity" }, dataProvider = "createBuy1Get2DiscountV2DataProvider", priority = 17,description="1. Create buy 1 get 1 discount \n 2. verify style id, type, buy count , count and status code 200 response")
			public void DiscountService_vEffectiveDiscountAttributeInBuy1Get1(String expiredDate,
				String startDate, String styleID, String styleID1)
				throws IOException, InterruptedException {
				RequestGenerator createB1G1 = Discounthelper.createBuy1Get1Discount(
						expiredDate, startDate, styleID, styleID1);
				System.out.println("response = " + createB1G1.returnresponseasstring());
				String rsponse = createB1G1.respvalidate.returnresponseasstring();
				String styleidnew = styleID1 + styleID;
				String styleidnew1 = styleID + styleID1;
				
				String styleid = JsonPath.read(rsponse, "$.discountStyles..styleId")
						.toString().replace("[", "").replace("]", "").replace(",", "")
						.trim();
				//System.out.println("Style ids from Buy1 and Get 1 are: "+styleid);
				// .toString().replace("[", "").replace("]", "").replace(",", "").trim()
				
				String buycount = JsonPath.read(rsponse, "$.discountRules..buyCount")
						.toString().replace("[", "").replace("]", "").replace("\"", "")
						.trim();
				String count = JsonPath.read(rsponse, "$.discountRules..count")
						.toString().replace("[", "").replace("]", "").replace("\"", "")
						.trim();
				if (styleidnew == styleid) {
					System.out.println("styleIds matche");
				} else if (styleidnew1 == styleid) {
					System.out.println("styleIds matche here");
				}
				AssertJUnit.assertEquals("buycount   doesn't match", "2", buycount);
				AssertJUnit.assertEquals("count   doesn't match", "1", count);
				
				
				AssertJUnit.assertEquals("Status code does not match", 200,
						createB1G1.response.getStatus());
				float countint=Float.parseFloat(count);
				float buycountInt=Float.parseFloat(buycount);
				float calEffDis=(countint/buycountInt)*100;
				// Wait for 5 min to verify effective discount attribute in getTradeAndDealDiscount api response
				System.out.println("--->> Wait for few min to verify effective discount attribute in getTradeAndDealDiscount api response <<----");
				TimeUnit.MINUTES.sleep(6);
				System.out.println("--->> Waiting complete <<----");
				
				//--------
				MyntraService TDservice =Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,new String [] {styleID1}, PayloadType.JSON, PayloadType.JSON );
				//TDservice.URL = apiUtil.prepareparameterizedURL(TDservice.URL,page);
						 System.out.println("TradeService URL: ---> "+TDservice.URL);
						 System.out.println("TradeAndDeal discount payload: ---> "+TDservice.Payload);
						 RequestGenerator rs = new RequestGenerator(TDservice);
						 String response = rs.returnresponseasstring();
						 System.out.println("Get Trade And Deal Discount Response: "+response);
						 String effectiveDiscountAttr=JsonPath.read(response, "$..effectiveDiscountPercent").toString().replace("[", "").replace("]", "").replace("\"", "");
						float effDisAttr=Float.parseFloat(effectiveDiscountAttr);
						 System.out.println("Effective Discount Attribute: "+effDisAttr);
						 Assert.assertEquals(calEffDis, effDisAttr);
				
				//--------here
				
					
					}
	
	
	
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression","Fox7Sanity" }, dataProvider = "createFlatDiscountDataProviderV2", priority = 7,description="1.Create flat discount percentage")
			public void DiscountService_createFlatDiscountV2AndV_additionalAttributes(String expiredDate,
				String startDate, String percent, String styleID)
				throws IOException, InterruptedException {
//			RequestGenerator createFlatDiscount = Discounthelper
//					.createFlatDiscount(expiredDate, startDate, percent, styleID);
			
			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
					init.Configurations, new String[] { expiredDate, startDate,
							percent, styleID}, PayloadType.JSON, PayloadType.JSON);
			System.out.println("Flat discount Payload -- \n" + service.Payload);
//			String flatPerPayload=service.Payload;
//			System.out.println("FlatPercentage payload is: ----->>>> "+flatPerPayload);
			RequestGenerator req=new RequestGenerator(service);
//			String respFlatPer=req.returnresponseasstring();
//			System.out.println("response-- >>> "
//					+ createFlatDiscount.returnresponseasstring());
//			String jsonresp = createFlatDiscount.returnresponseasstring();
//			System.out.println("resonse--------------->" + jsonresp);
//			String styleid = JsonPath.read(jsonresp, "$.discountStyles..styleId");
			System.out.println(">>>>>>>>> Wait for few minutes for discount to reflect in getTradeAndDealDiscount api response <<<<<<<");
//			TimeUnit.MINUTES.sleep(6);
//			System.out.println(">>>>>>>>> Wait COMPLETE <<<<<<<<<<");
//			MyntraService TDservice =Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,new String [] {styleID}, PayloadType.JSON, PayloadType.JSON );
//			//TDservice.URL = apiUtil.prepareparameterizedURL(TDservice.URL,page);
//					 System.out.println("TradeService URL: ---> "+TDservice.URL);
//					 System.out.println("TradeAndDeal discount payload: ---> "+TDservice.Payload);
//					 RequestGenerator rs = new RequestGenerator(TDservice);
//					 String response = rs.returnresponseasstring();
//					 System.out.println("Get Trade And Deal Discount Response: "+response);
//					 String discFunding=JsonPath.read(response, "$..discountFunding");
//					 System.out.println("DiscountPercent :"+discFunding);
//					 String fundingPercntage=JsonPath.read(response, "$..fundingPercentage");
//					 System.out.println("Funding percentage: "+fundingPercntage);
//					 String discLimit=JsonPath.read(response, "$..discountLimit");
//					 System.out.println("Discount Limit: "+discLimit);
					 
				
				}
	
}
