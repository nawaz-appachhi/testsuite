package com.myntra.apiTests.erpservices.partners.Tests;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.PartnerPortalTestDP;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.ManualTest;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class PartnerPortalService extends PartnerPortalTestDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();
	private String styleid;
	
	
	
	/** API Name - ReLoad Vendor Data **/

	@Test(groups = { "Smoke","Regression" }, priority = 0,dataProvider = "validVendors",description="1.Reload the Vendor Data")
	public void PPVendor_reloadVendorData(String id, String brandName,
			String paramStatusCode, String paramStatusType,
			String paramStatusMessage)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.RELOADVENDORDATA, init.Configurations, PayloadType.JSON,
				new String[] { id, brandName, paramStatusCode, paramStatusType,
						paramStatusMessage }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"Load Vendor Data statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Load Vendor Data statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Load Vendor Data statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	/** API Name - Load Vendor Data **/

	@Test(groups = { "Smoke","Regression" }, priority = 1, dataProvider = "validVendors",description="1.Load Vendor Data")
	public void PPVendor_loadVendorData(String id, String brandName,
			String paramStatusCode, String paramStatusType,
			String paramStatusMessage)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.LOADVENDORDATA, init.Configurations, PayloadType.JSON,
				new String[] { id, brandName, paramStatusCode, paramStatusType,
						paramStatusMessage }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"Load Vendor Data statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Load Vendor Data statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Load Vendor Data statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

		/*
		 * String statusMessage = req.respvalidate
		 * .NodeValue("status.statusCode", true).replaceAll("\"", "") .trim();
		 * log.info(statusMessage); System.out.println(statusMessage);
		 * AssertJUnit.assertTrue("Status Code doesn't match",
		 * statusMessage.equalsIgnoreCase("3"));
		 */
	}

	@Test(groups = { "Regression" }, dataProvider = "inValidVendors",description="1.Load Invalid Vendor Data")
	public void PPVendor_loadInValidVendorData(String data,
			String paramStatusCode, 
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.LOADVENDORDATA, init.Configurations, PayloadType.JSON,
				new String[] { data, paramStatusCode, 
						paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		/*String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");*/
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"Load Invalid Vendor Data statusCode is not matching, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		/*AssertJUnit.assertTrue(
				"Load Invalid Vendor Data statusMessage is not matching, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));*/

		AssertJUnit.assertTrue(
				"Load Invalid Vendor Data statusType is not matching, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Category Hierarchy **/

	@Test(groups = { "Regression"}, dataProvider = "validVendors", priority = 2,description="1.Get Category Hierarchy of Vendor")
	public void PPVendor_CategoryHierarchy(String id,
			String brandName, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETCATEGORYHIERARCHY, init.Configurations,
				PayloadType.JSON, new String[] { id, brandName,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"Category Hierarchy statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Category Hierarchy statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Category Hierarchy statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	@Test(groups = {"Regression" }, dataProvider = "validVendors", priority = 2,description="1.Get Category Hierarchy of Valid Vendors")
	public void PPVendor_ValidCategoryHierarchy(String id,
			String brandName, String paramStatusCode,
			String paramStatusMessage, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETCATEGORYHIERARCHY, init.Configurations,
				PayloadType.JSON, new String[] { id, brandName },
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"Category Hierarchy statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"Category Hierarchy statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Category Hierarchy statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	 * "ExhaustiveRegression" }, dataProvider = "validVendors", priority = 2)
	 * public void getCategoryHierarchy_getBrand(String id, String brandName) {
	 * 
	 * String value = brandName;
	 * System.out.println("initail value of brand name = " + value); if
	 * (value.contains(" ")) { String modify = value.replaceAll(" ", "%20");
	 * System.out.println("final = " + modify); value = modify; } //
	 * System.out.println("expected brand name = " + value);
	 * 
	 * MyntraService service = Myntra.getService(
	 * ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.GETCATEGORYHIERARCHY,
	 * init.Configurations, PayloadType.JSON, new String[] { id, value },
	 * PayloadType.JSON);
	 * 
	 * HashMap getParam = new HashMap(); getParam.put("Authorization",
	 * "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
	 * 
	 * System.out.println(service.URL); RequestGenerator req = new
	 * RequestGenerator(service, getParam);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * 
	 * String jsonResp = req.respvalidate.returnresponseasstring(); String name
	 * = JsonPath.read(jsonResp, "$.data..styleEntityList[*].name");
	 * 
	 * // System.out.println("expected brand name = " +expectedBrandName); //
	 * String value1 = value.replace("%20", " ").trim(); // //
	 * System.out.println("value1 :" + value1); // String expectedBrandName =
	 * value1.replaceAll("\"", "").trim(); String expectedBrandName =
	 * brandName.replaceAll("\"", "").trim();
	 * System.out.println("expected brand name:" + expectedBrandName);
	 * System.out.println("actual brand name = " + name.replaceAll("\"",
	 * "").trim()); AssertJUnit.assertTrue("Brand Name Mismatch",
	 * name.contains(expectedBrandName));
	 * 
	 * AssertJUnit.assertEquals(200, req.response.getStatus()); }
	 */

	@Test(groups = { "Regression" }, dataProvider = "categoryInValidVendors",description="1.Get Category Hierarchy of InValid Vendors")
	public void InvalidCategoryHierarchy(String data,
			String paramStatusCode, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETCATEGORYHIERARCHY, init.Configurations,
				PayloadType.JSON, new String[] { data, paramStatusCode,
						paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"CategoryHierarchy Invalid statusCode is not Matching, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"CategoryHierarchy Invalid statusType is not Matching, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));
	}

	/** API Name - Get Category Dashboard **/

	@Test(groups = {"Regression"}, dataProvider = "validVendors", priority = 2,description="1.Get Category Dashboard of Valid Vendors")
	public void PPVendor_CategoryDashboard(String id, String brandName,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETCATEGORYDASHBOARD, init.Configurations,
				PayloadType.JSON, new String[] { id, brandName,
						paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"CategoryDashboard statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"CategoryDashboard statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"CategoryDashboard statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Discount Summary **/

	@Test(groups = { "Regression"}, dataProvider = "validVendors", priority = 2,description="1.Get Discount Summary of Valid Vendors")
	public void PPVendor_DiscountSummary(String id, String brandName,
			String paramStatusCode, String paramStatusMessage,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.GETDISCOUNTS,
				init.Configurations, PayloadType.JSON, new String[] { id,
						brandName, paramStatusCode, paramStatusMessage,
						paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue(
				"DiscountSummary statusCode is invalid, Expected: <"
						+ paramStatusCode + "> but Actual: <"
						+ responseStatusCode + ">",
				responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue(
				"DiscountSummary statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"DiscountSummary statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Styles By Brand **/

	@Test(groups = {"Regression" }, dataProvider = "validVendorsBrands", priority = 2,description="1.Get Styles by Brand Name \n 2.Validates the Brand Name is coming correct for the Vendor" )
	public void PPVendor_StylesByBrand(String id, String brandName)
	{

		String value = brandName;
		System.out.println("initail value of brand name = " + value);
		if (value.contains(" "))
		{
			String modify = value.replaceAll(" ", "%20");
			System.out.println("final = " + modify);
			value = modify;
		}
		// System.out.println("expected brand name = " +value);

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESBYBRAND, init.Configurations,
				PayloadType.JSON, new String[] { id, value }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		String jsonResp = req.respvalidate.returnresponseasstring();
		List<String> names = JsonPath.read(jsonResp,
				"$.data..styleEntityList[*].brand");

		for (String s : names)
		{

			// System.out.println("expected brand name = " +expectedBrandName);
			// String value1 = value.replace("%20"," ").trim();
			// System.out.println("value1 :" +value1);
			// String expectedBrandName = value1.replaceAll("\"","").trim();
			String expectedBrandName = brandName.replaceAll("\"", "").trim();
			System.out.println("expected brand name:" + expectedBrandName);
			System.out.println("actual brand name = "
					+ s.replaceAll("\"", "").trim());
			AssertJUnit.assertTrue("Brand Name Mismatch",
					s.contains(expectedBrandName));
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());

	}

	@Test(groups = {"Regression"}, dataProvider = "vendorsWithoutBrands", priority = 1,description="1.Get Styles without Brand")
	public void PPVendor_StylesWithoutBrand(String data, String paramStatusCode,
			String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESWITHOUTBRAND, init.Configurations,
				PayloadType.JSON, new String[] { data, paramStatusCode,
						paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Styles By Master Category **/

	@Test(groups = { "Regression" }, dataProvider = "vendorsAndMasterCategory", priority = 1,description="1.Get Styles by Master Category \n 2.Validates the master category is coming correct for the vendor")
	public void PPVendor_ByMasterCategory(String id, String masterCategory)
	{

		String value = masterCategory;
		System.out.println("initail value of master category name = " + value);
		if (value.contains(" "))
		{
			String modify = value.replaceAll(" ", "%20");
			System.out.println("final = " + modify);
			value = modify;
		}

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESBYMASTERCATEGORY, init.Configurations,
				PayloadType.JSON, new String[] { id, value }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		String jsonResp = req.respvalidate.returnresponseasstring();
		List<String> names = JsonPath.read(jsonResp,
				"$..data..styleEntityList[*].masterCategory");
		for (String s : names)
		{

			String expectedMasterCategory = masterCategory.replaceAll("\"", "")
					.trim();
			System.out.println("expected master category:"
					+ expectedMasterCategory);
			System.out.println("actual master category = "
					+ s.replaceAll("\"", "").trim());
			AssertJUnit.assertTrue("Master Name Mismatch",
					s.contains(expectedMasterCategory));
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("3"));

	}

	/** API Name - Get Styles By Master Sub Category **/

	@Test(groups = { "Regression"}, dataProvider = "vendorsAndMasterSubCategory", priority = 1,description="1.Get Styles by master SubCategory \n 2.Validates the master subcategory is coming correct for the vendor")
	public void PPVendor_ByMasterSubCategory(String id, String masterCategory,
			String subCategory)
	{

		String value = subCategory;
		System.out.println("initail value of sub category name = " + value);
		if (value.contains(" "))
		{
			String modify = value.replaceAll(" ", "%20");
			System.out.println("final = " + modify);
			value = modify;
		}

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESBYMASTERSUBCATEGORY, init.Configurations,
				PayloadType.JSON, new String[] { id, masterCategory, value },
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		String jsonResp = req.respvalidate.returnresponseasstring();
		List<String> names = JsonPath.read(jsonResp,
				"$..data..styleEntityList[*].subCategory");
		for (String s : names)
		{

			String expectedSubCategory = subCategory.replaceAll("\"", "")
					.trim();
			System.out.println("expected sub category :" + expectedSubCategory);
			System.out.println("actual sub category = "
					+ s.replaceAll("\"", "").trim());
			AssertJUnit.assertTrue("Sub Category Name Mismatch",
					s.contains(expectedSubCategory));
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("3"));
	}

	@Test(groups = {"Regression"}, dataProvider = "vendorsWithoutMaster", priority = 2,description="1.Get SubCategory without master")
	public void PPVendor_SubCategoryWithoutMaster(String id, String subCategory,
			String paramStatusCode, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSUBCATEGORYWITHOUTMASTER, init.Configurations,
				PayloadType.JSON, new String[] { id, subCategory,
						paramStatusCode, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Styles By Article Type **/

	@Test(groups = {"Regression" }, dataProvider = "subCategoryAndArticleType", priority = 2,description="1.Get styles by Article Type \n 2. validates the article type is coming correct for the vendor")
	public void PPVendor_ByArticleType(String id, String subCategory,
			String articleType, String paramStatusCode, String paramStatusType)
	{

		String value = articleType;
		System.out.println("initail value of article type = " + value);
		if (value.contains(" "))
		{
			String modify = value.replaceAll(" ", "%20");
			System.out.println("final = " + modify);
			value = modify;
		}

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESBYARTICLETYPE, init.Configurations,
				PayloadType.JSON, new String[] { id, subCategory, value,
						paramStatusCode, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		String jsonResp = req.respvalidate.returnresponseasstring();
		List<String> names = JsonPath.read(jsonResp,
				"$..data..styleEntityList[*].articleType");
		for (String s : names)
		{

			String expectedArticleType = articleType.replaceAll("\"", "")
					.trim();
			System.out.println("expected article type :" + expectedArticleType);
			System.out.println("actual article type = "
					+ s.replaceAll("\"", "").trim());
			AssertJUnit.assertTrue("Article Type Name Mismatch",
					s.contains(expectedArticleType));
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("3"));

	}

	@Test(groups = { "Regression" }, dataProvider = "subCategoryAndArticleType", priority = 2,description="1.Get Article type witout master")
	public void PPVendor_ArticleTypeWithoutMaster(String id, String subCategory,
			String articleType, String paramStatusCode, String paramStatusType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETARTICLETYPEWITHOUTMASTER, init.Configurations,
				PayloadType.JSON, new String[] { id, subCategory, articleType,
						paramStatusCode, paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("StatusCode is invalid, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("StatusType is invalid, Expected: <"
				+ paramStatusType + "> but Actual: <" + responseStatusType
				+ ">", responseStatusType.equals(paramStatusType));

	}

	/** API Name - Get Styles By Filter String **/

	@Test(groups = { "Regression" }, dataProvider = "filters", priority = 2,description="1.Get styles by Filter String \n 2. Validates the styles are coming correctly by filter search")
	public void PPVendor_ByFilterString(String id, String brandName,
			String filter)
	{

		String value = brandName;
		System.out.println("initail value of filter = " + value);
		if (value.contains(" "))
		{
			String modify = value.replaceAll(" ", "%20");
			System.out.println("final = " + modify);
			value = modify;
		}

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.GETSTYLESBYFILTERSTRING, init.Configurations,
				PayloadType.JSON, new String[] { id, value, filter },
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());

		String jsonResp = req.respvalidate.returnresponseasstring();
		List<String> names = JsonPath.read(jsonResp,
				"$..data..styleEntityList[*].color");
		for (String s : names)
		{

			String expectedfilter = filter.replaceAll("\"", "").trim();
			System.out.println("expected filter :" + expectedfilter);
			System.out.println("actual filter = "
					+ s.replaceAll("\"", "").trim());
			AssertJUnit.assertTrue("filter Name Mismatch",
					s.contains(expectedfilter));
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match",
				statusMessage.equalsIgnoreCase("3"));

	}

	/** API Name - Get Styles By Style Ids **/

	/*
	 * @Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
	 * "ExhaustiveRegression" }, dataProvider = "styleIds", priority = 2) public
	 * void getStylesByStyleIds(String id, String styleId) {
	 * 
	 * MyntraService service = Myntra.getService(
	 * ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.GETSTYLESBYSTYLEIDS,
	 * init.Configurations, PayloadType.JSON, new String[] { id, styleId },
	 * PayloadType.JSON);
	 * 
	 * HashMap getParam = new HashMap(); getParam.put("Authorization",
	 * "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
	 * 
	 * System.out.println(service.URL); RequestGenerator req = new
	 * RequestGenerator(service, getParam);
	 * System.out.println(req.respvalidate.returnresponseasstring());
	 * 
	 * String jsonResp = req.respvalidate.returnresponseasstring(); String name
	 * = JsonPath.read(jsonResp, "$.data..styleEntityList.id");
	 * 
	 * System.out.println("name=" + name);
	 * 
	 * String expectedStyleid = styleid.replaceAll("\"", "").trim();
	 * System.out.println("expected brand name:" + expectedStyleid);
	 * System.out.println("actual brand name = " + name.replaceAll("\"",
	 * "").trim()); AssertJUnit.assertTrue("Styleid Mismatch",
	 * name.contains(expectedStyleid));
	 * 
	 * AssertJUnit.assertEquals(200, req.response.getStatus()); String
	 * statusMessage = req.respvalidate .NodeValue("status.statusCode",
	 * true).replaceAll("\"", "") .trim(); log.info(statusMessage);
	 * System.out.println(statusMessage);
	 * AssertJUnit.assertTrue("Status Code doesn't match",
	 * statusMessage.equalsIgnoreCase("3"));
	 * 
	 * }
	 */

	/** API Name - Create Discount by Param **/

	@Test(groups = {"Regression" }, dataProvider = "createDiscount", priority = 1,description="1.Select styles by param \n 2. Validates discounts applied successfully")
	public void PPVendor_createDiscount(String discountType, String value,
			String startDate, String endDate, String paramStatusCode)
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.CREATEDISCOUNTBYPARAM, init.Configurations,
				new String[] { discountType, value, startDate, endDate,
						paramStatusCode });

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("Create Discount is not working, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertEquals("Create Discount is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Regression"}, dataProvider = "invalidDiscountBlank", priority = 1,description="1.Applies discount which are not valid \n 2.Validates discount should not got apply for styles")
	public void PPVendor_invalidDiscountBlank(String discountType,
			String value, String startDate, String endDate, String code)
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.CREATEDISCOUNTBYPARAM, init.Configurations,
				new String[] { discountType, value, startDate, endDate });

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Status Code doesn't match", statusMessage
				.trim().contains(code));

		AssertJUnit.assertEquals("Create Discount is not working", 200,
				req.response.getStatus());

	}

	@Test(groups = {"Regression" }, dataProvider = "invalidDiscount", priority = 1,description="1.Applies discount which are not valid \n 2.Validates discount should not got apply for styles")
	public void PPVendor_invalidDiscount(String discountType, String value,
			String startDate, String endDate, String code)
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.CREATEDISCOUNTBYPARAM, init.Configurations,
				new String[] { discountType, value, startDate, endDate });

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		/*String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();*/
		/*log.info(statusMessage);*/
		
		String statusMessage = req.respvalidate.returnresponseasstring();
		
		List<Integer> names = JsonPath.read(statusMessage,
				"$..data..bulkDiscountEntityList[*]..statusCode");
		
		log.info(statusMessage);
		
		System.out.println(statusMessage);
		/*AssertJUnit.assertTrue("Status Code doesn't match", statusMessage
				.trim().contains(code));*/
		for (Object s : names)
		{
			/*AssertJUnit.assertTrue("filter Name Mismatch",
					s.contains(code));*/
			AssertJUnit.assertEquals("Status Code Mismacth", code.trim(), s.toString());
		}
		AssertJUnit.assertEquals("Create Discount is not working", 200,
				req.response.getStatus());

	}

	/** API Name - Remove Discount By Param **/

	@Test(groups = { "Regression" }, priority = 2,description="1.Remove discount \n 2.Validates discount should got removed from the styles")
	public void PPVendor_removeDiscount()
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.REMOVEDISCOUNTBYPARAM, init.Configurations,
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String statusMessage = req.respvalidate
				.NodeValue("status.statusCode", true).replaceAll("\"", "")
				.trim();
		log.info(statusMessage);
		System.out.println(statusMessage);
		AssertJUnit.assertTrue("Remove Discount is not working",
				statusMessage.equalsIgnoreCase("3"));

		AssertJUnit.assertEquals("Remove Discount is not working", 200,
				req.response.getStatus());
	}

	/** API Name - Create Discount By StyleId **/

	@Test(groups = { "Regression" }, dataProvider = "createDiscount", priority = 1,description="1.Applies discount on some selected styles \n 2.Validates discount got applied to the selected styles")
	public void PPVendor_createDiscountByStyleId(String discountType, String value,
			String startDate, String endDate, String paramStatusCode)
	{

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL,
				APINAME.CREATEDISCOUNTBYSTYLEID, init.Configurations,
				new String[] { discountType, value, startDate, endDate,
						paramStatusCode });

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");
		System.out.println("url =" + service.URL);
		System.out.println("payload = " + service.Payload);

		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println("response = " + req.returnresponseasstring());

		String responseStatusCode = req.respvalidate.NodeValue(
				StatusNodes.STATUS_CODE.toString(), false).replace("\"", "");

		AssertJUnit.assertTrue("Create Discount is not working, Expected: <"
				+ paramStatusCode + "> but Actual: <" + responseStatusCode
				+ ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertEquals("Create Discount is not working", 200,
				req.response.getStatus());
	}
	
	@ManualTest
	@Test(groups = { "Regression" },
			description="1.Apply Discount \n 2.Verify Discount in App PDP Page")
	public void PPVendor_ManualTest1(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Select 5 Styles \n"
			+ "2.Click on Download Link \n"
			+ "3.Verify the Downloaded file \n"
			+ "4.Check 5 styles are downloaded and the value are correct")
	public void PPVendor_ManualTest2(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Select Page 1 Styles \n"
			+ "2.Click on Download Link \n"
			+ "3.Verify the Downloaded file \n"
			+ "4.Check All Page 1 styles are downloaded and the value are correct")
	public void PPVendor_ManualTest3(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Select All \n"
			+ "2.Click on Download Link \n"
			+ "3.Verify the Downloaded file \n"
			+ "4.Check All Styles are downloaded and the value are correct")
	public void PPVendor_ManualTest4(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for 10mins \n"
			+ "2.Check after 10mins \n"
			+ "3.Discount got removed")
	public void PPVendor_ManualTest5(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Click on Discounted Tab \n"
			+ "2.Only Discounted Styles Should be visible"
			)
	public void PPVendor_ManualTest6(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Click on NonDiscounted Tab \n"
			+ "2.Only NonDiscounted Styles Should be visible"
			)
	public void PPVendor_ManualTest7(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Today \n"
			+ "2.Active discount notify in green color"
			)
	public void PPVendor_ManualTest8(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Tomorrow \n"
			+ "2.InActive discount notify in orange color")
	public void PPVendor_ManualTest9(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Tomorrow \n"
			+ "2.InActive discount can be edited by vendor")
	public void PPVendor_ManualTest10(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Tomorrow \n"
			+ "2.InActive discount can be removed by vendor")
	public void PPVendor_ManualTest11(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Today \n"
			+ "2.Active discount cannot be removed by vendor for 24hrs")
	public void PPVendor_ManualTest12(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Apply Discount for Today \n"
			+ "2.Active discount cannot be edited by vendor 24hrs")
	public void PPVendor_ManualTest13(){
		Assert.fail("Manual Test");
	}

}
