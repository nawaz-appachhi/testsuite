package com.myntra.apiTests.portalservices.cms;

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

public class HSNValidateAPITest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(HSNValidateAPITest.class);
	String mandatory_att_error = "Some mandatory attributes' values are missing. For the given Article Type, these are the tax related attributes: ";
	String synonym_table_error = ": This Article Type is not configured in our synonym table (`article_type_to_tax_attribute_map`)";
	String no_entry_error = "No entry present for the given attribute combination in Myntra HSN Master for the given Article Type and attribute combination.";
	String neg_one_error = "-1 is not a valid HSN code";
	String no_entry_in_registry = "Given HSN code is not present in the global HSN set (`hsn_main_registry` table). Possible HSNs for the given attribute combination as per our Myntra HSN Master: ";
	String invalid_hsn_error = "Given HSN is not valid for the given attribute combination. Possible HSNs as per our Myntra HSN Master: "; 
	String no_suggestion_error = "Entries found for the given attribute combination in Myntra HSN Master, but the same are not present in the main registry.";

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Validate HSN for a given articletype, tax attribute combination where gender is null")
	public void validateHSNForNullGender_TwoDigits() throws Exception {
		String articleType = "HDBG";
		String ageGroup = "";
		String attr1 = "Leather";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "42022110";
		String myntraHSN = "42022110";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Validate HSN for a given articletype, tax attribute combination where gender is null")
	public void validateHSNForRandomGender() throws Exception {
		String articleType = "HDBG";
		String ageGroup = "RANDOM1";
		String attr1 = "Leather";
		String attr2 = "RANDOM2";
		String attr3 = "RANDOM3";
		String vendorHSN = "420221";
		String myntraHSN = "42022110";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "Validate HSN for a given articletype with multiple tax attributes")
	public void validateHSNWithMultipleTaxAttributes() throws Exception {
		String articleType = "BLZR";
		String ageGroup = "Adults-Women";
		String attr1 = "Suede";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "42031010";
		String myntraHSN = "42031010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "Validate HSN for a given articletype with mandatory tax attribute as null")
	public void validateHSNWithMandatoryAttAsNull() throws Exception {
		String articleType = "BLZR";
		String ageGroup = "Adults-Women";
		String attr1 = "Suede";
		String attr2 = "";
		String attr3 = "Nylon";
		String vendorHSN = "42031010";
		String myntraHSN = "42031010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message", mandatory_att_error
				+ "Fabric, Fabric1, Fabric2",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString());
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "Verify error message for missing article type attribute map")
	public void errorATMapHSNTree() throws Exception {
		String articleType = "JENS";
		String ageGroup = "Adults-Women";
		String attr1 = "Cotton";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "42031010";
		String myntraHSN = "42031010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), articleType + synonym_table_error);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "Verify odd and zero HSN")
	public void validateOddAndZeroHSN_1() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "0";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "Verify odd and zero HSN")
	public void validateOddAndZeroHSN_2() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "006";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "Verify odd and zero HSN")
	public void validateOddAndZeroHSN_3() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "00610";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "Verify odd and zero HSN")
	public void validateOddAndZeroHSN_4() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "0061062";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "Verify odd and zero HSN")
	public void validateOddAndZeroHSN_5() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "Nylon";
		String attr3 = "Nylon";
		String vendorHSN = "006106201";
		String myntraHSN = "006106201";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("isValid is not present",
				JsonPath.read(response, "$.data[0].isValid").toString(), "true");
		AssertJUnit.assertEquals("myntra hsn validation failed",
				JsonPath.read(response, "$.data[0].myntraHSN").toString(),
				myntraHSN);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "ErrorMessage for incorrect article type")
	public void validateIncorrectArticleType() throws Exception {
		String articleType = "ABC";
		String ageGroup = "Random1";
		String attr1 = "Random2";
		String attr2 = "Random3";
		String attr3 = "Random4";
		String vendorHSN = "123";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), articleType + synonym_table_error);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "Error message for tax attribute combination not present in myntra")
	public void errorTaxAttNotPresentInMyntra() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "a";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "42031090";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), no_entry_error);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "Error message for vendor hsn as -1")
	public void errorNegOne() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Linen";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "-1";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), neg_one_error);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "Vendor HSN present in myntra_hsn table but not in registry table - with suggestions")
	public void vendorHSNNotInRegistry_WithSuggestions() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "61062010";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), no_entry_in_registry
						+ "006106201, 42031090");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "Invalid vendor hsn for the attributes, but present in main_registry - with suggestions")
	public void inValidHSNPresentInRegistry_WithSuggestions() throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Polyester";
		String attr2 = "";
		String attr3 = "";
		String vendorHSN = "11";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), invalid_hsn_error + "006106201, 42031090");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 16, description = "Vendor HSN with incomplete tree - with suggestions")
	public void vendorHSNWithIncompleteTree_WithSuggestions() throws Exception {
		String articleType = "BLZR";
		String ageGroup = "Adults-Women";
		String attr1 = "PU";
		String attr2 = "Leather";
		String attr3 = "Leather";
		String vendorHSN = "61043300";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), no_entry_in_registry + "61033990");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 17, description = "Vendor HSN with incomplete tree - without suggestions")
	public void vendorHSNWithIncompleteTree_WithoutSuggestions()
			throws Exception {
		String articleType = "TOPS";
		String ageGroup = "Adults-Women";
		String attr1 = "Linen";
		String attr2 = "Random1";
		String attr3 = "Random2";
		String vendorHSN = "61069090";
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_HSN,
						APINAME.HSN_VALIDATEAPI, new String[] { vendorHSN,
								articleType, ageGroup, attr1, attr2, attr3 });
		String response = req.returnresponseasstring();
		AssertJUnit.assertEquals("Error Message",
				JsonPath.read(response, "$.data[0].validationMessage")
						.toString(), no_suggestion_error);
	}

}
