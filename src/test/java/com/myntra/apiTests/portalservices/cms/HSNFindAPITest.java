package com.myntra.apiTests.portalservices.cms;



import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author Vaishali Behere
 * 
 */

public class HSNFindAPITest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(HSNFindAPITest.class);
	String main_registry_error = "Entries found for the given attributes combination in Myntra HSN Master, but the same are not present in Global (Main) Registry";
	String synonym_table_error = "No entry in Synonym table (`article_type_to_tax_attribute_map`) for Article Type: ";
	String myntra_table_error = "No entry present in Myntra HSN Master for the given combination of attributes";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Find HSN for a given articletype, tax attribute combination where gender is null")
	public void findHSNForNullGender() throws Exception {
		String articleType = "HDBG";
		String ageGroup = "";
		String attr1 = "Leather";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "42022110";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("hsnCode is not present",
				JsonPath.read(response, "$.data[0].hsnCode").toString(),
				myntraHSN);
		AssertJUnit.assertEquals("taxArticleTypeCode is not present", JsonPath
				.read(response, "$.data[0].taxArticleTypeCode").toString(),
				articleType);
		AssertJUnit.assertEquals("taxAttribute1 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute1").toString(),
				attr1);
		AssertJUnit.assertEquals("isActive is not present",
				JsonPath.read(response, "$.data[0].isActive").toString(), "true");
		Assert.assertNull("taxAttribute2 is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAttribute2", true));
		Assert.assertNull("taxAttribute3 is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAttribute3", true));
		Assert.assertNull("taxAgeGroup is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAgeGroup", true));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Find HSN for a given articletype, tax attribute combination where gender is null, but entered random value")
	public void findHSNForAnyGender() throws Exception {
		String articleType = "HDBG";
		String ageGroup = "Adults-Women";
		String attr1 = "Leather";
		String attr2 = "Random1";
		String attr3 = "Random2";
		String myntraHSN = "42022110";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("hsnCode is not present",
				JsonPath.read(response, "$.data[0].hsnCode").toString(),
				myntraHSN);
		AssertJUnit.assertEquals("taxArticleTypeCode is not present", JsonPath
				.read(response, "$.data[0].taxArticleTypeCode").toString(),
				articleType);
		AssertJUnit.assertEquals("taxAttribute1 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute1").toString(),
				attr1);
		AssertJUnit.assertEquals("isActive is not present",
				JsonPath.read(response, "$.data[0].isActive").toString(), "true");
		Assert.assertNull("taxAttribute2 is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAttribute2", true));
		Assert.assertNull("taxAttribute3 is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAttribute3", true));
		Assert.assertNull("gender is not null", e2eRegressionCmsHelper
				.getNodeValue(req, "data[0].taxAgeGroup", true));
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Find HSN for a given articletype with multiple tax attributes")
	public void findHSNWithMultipleTaxAttributes() throws Exception {
		String articleType = "BLZR";
		String ageGroup = "Adults-Women";
		String attr1 = "Suede";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String myntraHSN = "42031010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("hsnCode is not present",
				JsonPath.read(response, "$.data[0].hsnCode").toString(),
				myntraHSN);
		AssertJUnit.assertEquals("taxArticleTypeCode is not present", JsonPath
				.read(response, "$.data[0].taxArticleTypeCode").toString(),
				articleType);
		AssertJUnit.assertEquals("taxAttribute1 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute1").toString(),
				attr1);
		AssertJUnit.assertEquals("taxAttribute2 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute2").toString(),
				attr2);
		AssertJUnit.assertEquals("taxAttribute3 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute3").toString(),
				attr3);
		AssertJUnit.assertEquals("taxAgeGroup is not present",
				JsonPath.read(response, "$.data[0].taxAgeGroup").toString(),
				ageGroup);
		AssertJUnit.assertEquals("isActive is not present",
				JsonPath.read(response, "$.data[0].isActive").toString(), "true");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Verify error message for incomplete hsn tree where some parent nodes are not present in main registry")
	public void errorIncompleteHSNTree_SomeNodesMissingInRegistry()
			throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polycotton";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "61069090";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message", main_registry_error, JsonPath
				.read(response, "$.status.statusMessage").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify error message for incomplete hsn tree where HSN itself is missing in registry")
	public void errorIncompleteHSNTree_HSNMissingInRegistry() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Acrylic";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "61062010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message", main_registry_error, JsonPath
				.read(response, "$.status.statusMessage").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify error message for incomplete hsn tree where HSN is present in registry without any parent")
	public void errorIncompleteHSNTree_HSNWithoutParentInRegistry()
			throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Modal";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "61062020";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message", main_registry_error, JsonPath
				.read(response, "$.status.statusMessage").toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify error message for missing article type attribute map")
	public void errorATMapHSNTree() throws Exception {
		String articleType = "JENS";
		String ageGroup = "Adults-Women";
		String attr1 = "Cotton";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "42031010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.status.statusMessage").toString(),
				synonym_table_error + articleType);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify odd and zero HSN")
	public void verifyOddAndZeroHSN() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("hsnCode is not present",
				JsonPath.read(response, "$.data[0].hsnCode").toString(),
				myntraHSN);
		AssertJUnit.assertEquals("taxArticleTypeCode is not present", JsonPath
				.read(response, "$.data[0].taxArticleTypeCode").toString(),
				articleType);
		AssertJUnit.assertEquals("taxAttribute1 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute1").toString(),
				attr1);
		AssertJUnit.assertEquals("taxAttribute2 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute2").toString(),
				attr2);
		AssertJUnit.assertEquals("taxAttribute3 is not present",
				JsonPath.read(response, "$.data[0].taxAttribute3").toString(),
				attr3);
		AssertJUnit.assertEquals("taxAgeGroup is not present",
				JsonPath.read(response, "$.data[0].taxAgeGroup").toString(),
				ageGroup);
		AssertJUnit.assertEquals("isActive is not present",
				JsonPath.read(response, "$.data[0].isActive").toString(), "true");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "ErrorMessage for incorrect article type")
	public void incorrectArticleType() throws Exception {
		String articleType = "ABC";
		String ageGroup = "Random1";
		String attr1 = "Random2";
		String attr2 = "Random3";
		String attr3 = "Random4";
		String myntraHSN = "";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.status.statusMessage").toString(),
				synonym_table_error + articleType);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "More than 1 HSNs for a combination + Jira ticket CMS-1517")
	public void verifyMultipleHSNsAndCMS1517() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN1 = "006106201";
		String myntraHSN2 = "42031090";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].hsnCode").toString(),
				myntraHSN1);
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[1].hsnCode").toString(),
				myntraHSN2);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "Error message for entry not present in myntra hsn master")
	public void verifyHSNForAttsPresentInMapTable() throws Exception {
		String articleType = "BLZR";
		String ageGroup = "Adults-Women";
		String attr1 = "Suede";
		String attr2 = "";
		String attr3 = "";
		String myntraHSN = "";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_FINDAPI, new String[] { articleType,
								ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.status.statusMessage").toString(),
				myntra_table_error);
	}

}
