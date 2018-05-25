package com.myntra.apiTests.portalservices.devapis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.devapis.ApifyTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.ApifyApiServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class ApifyTests extends ApifyTestsDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ApifyTests.class);
	static ApifyApiServiceHelper apifyServiceHelper = new ApifyApiServiceHelper();

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDP - Success", dataProvider = "ApifyDataProvider_ValidStyleId")
	public void apifyValidPdpVerifySuccess(String styleId) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		//AssertJUnit.assertTrue("Social compononents are not present", apifyServiceHelper.findAllCardsTypes(pdpResponse, "SOCIAL"));

		//Find all action url from pdp api and check the status
		for(Object allComponentActionUrl: apifyServiceHelper.findAllActionUrls(pdpResponse)) {
			//AssertJUnit.assertTrue(allComponentActionUrl.toString().contains(styleId));
			RequestGenerator pdpActionRequest = apifyServiceHelper.invokePdpAction(allComponentActionUrl.toString());
			System.out.println("Verify - ValidPDPActionComponentUrl - Success Response : "+pdpActionRequest.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("Verify - ValidPDPActionComponentUrl - Success is down", 200, pdpActionRequest.response.getStatus());
		}
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDPOutOfStock - Success", dataProvider = "ApifyDataProvider_outOfStockStyleId")
	public void apifyValidPdpOutOfStockVerifySuccess(String styleId) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		
		AssertJUnit.assertEquals("Verify - ValidPDPOutOfStock - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - ValidPDPOutOfStock - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		//AssertJUnit.assertFalse("Verify - Social Parameter is present", apifyServiceHelper.isSocialValuePresentOrNot(pdpResponse));
		//AssertJUnit.assertFalse("Social compononents are present", apifyServiceHelper.findAllCardsTypes(pdpResponse, "SOCIAL"));
		//Find all action url from pdp api and check the status
		for(Object allComponentActionUrl: apifyServiceHelper.findAllActionUrls(pdpResponse)) {
			//AssertJUnit.assertTrue(allComponentActionUrl.toString().contains(styleId));
			RequestGenerator pdpActionRequest = apifyServiceHelper.invokePdpAction(allComponentActionUrl.toString());
			System.out.println("Verify - ValidPDPActionComponentUrl - Success Response : "+pdpActionRequest.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("Verify - ValidPDPActionComponentUrl - Success is down", 200, pdpActionRequest.response.getStatus());
		}
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDP Innerwear - Success", dataProvider = "ApifyDataProvider_ValidInnerWearStyleId")
	public void apifyValidPdpInnerwearStyleIdVerifySuccess(String styleId) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id is present...
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		//AssertJUnit.assertFalse("Social compononents are present", apifyServiceHelper.findAllCardsTypes(pdpResponse, "SOCIAL"));
	}
	
	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDP PersonalCare - Success", dataProvider = "ApifyDataProvider_ValidPersonalCareStyleId")
	public void apifyValidPdpPersonalCareStyleIdVerifySuccess(String styleId) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id is present...
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		AssertJUnit.assertTrue("Verify - Colour Selector Type - Bad Response", apifyServiceHelper.findAllComponentTypes(pdpResponse, "COLOUR_SELECTOR"));
		AssertJUnit.assertTrue("Verify - Colour Selector - Bad Response", apifyServiceHelper.findAllComponentViewTypes(pdpResponse, "COLOUR_SELECTOR"));
	}
	
	@Test(groups={"Sanity", "Regression"},description="Verify - Collapse State - Success", dataProvider = "ApifyDataProvider_ValidStyleIdForCollapseState")
	public void apifyPdpVerifyCollapseState(String styleId, String connetionClass, String collapseState) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdpCollapseState(styleId, connetionClass);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP  - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - styleId - No Response", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		//AssertJUnit.assertEquals("Verify - Collapse State - No Response", Boolean.parseBoolean(collapseState), apifyServiceHelper.isCollapseStatus(pdpResponse).get(0));
	}

	@Test(groups={"Regression"},description="Verify - InValidPDP - Success", dataProvider="ApifyDataProvider_InvalidStyleId")
	public void apifyInValidPdpVerifySuccess(String styleId, String responseStatus) {
		int status = Integer.valueOf(responseStatus);
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		System.out.println("Verify - InValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - InValidPDP - Success is down", status, pdpRequest.response.getStatus());
	}

	@Test(groups={"Sanity", "Regression"}, description="Verify - ValidPDPBrand - Success", dataProvider = "ApifyDataProvider_ValidStyleId")
	public void apifyPdpValidBrandVerifySuccess(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpBrand(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDPBrand - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDPBrand - Success is down",200,request.response.getStatus());
		AssertJUnit.assertTrue("Verify - Brand Type - Bad Response", apifyServiceHelper.findAllComponentTypes(response, "BRAND_PDP"));
		AssertJUnit.assertTrue("Verify - Brand viewType - Bad Response", apifyServiceHelper.findAllComponentViewTypes(response, "BRAND_PDP"));
	}

	@Test(groups={"Regression"}, description="Verify - InValidPDPBrand - Success", dataProvider = "ApifyDataProvider_InvalidBrandStyleId")
	public void apifyPdpInvalidBrandVerifySuccess(String styleId, String responseStatus) {
		int status = Integer.valueOf(responseStatus);
		RequestGenerator request = apifyServiceHelper.invokePdpBrand(styleId);
		System.out.println("Verify - InValidPDPBrand - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - InValidPDPBrand - Success is down", status, request.response.getStatus());
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDPRelated - Success", dataProvider = "ApifyDataProvider_ValidStyleId")
	public void apifyPdpValidPdpRelatedVerifySuccess(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpRelated(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDPRelated - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDPRelated - Success is down",200,request.response.getStatus());
		AssertJUnit.assertTrue("Verify - Related Type - Bad Response", apifyServiceHelper.findAllComponentTypes(response, "RELATED_PDP"));
		AssertJUnit.assertTrue("Verify - Related viewType - Bad Response", apifyServiceHelper.findAllComponentViewTypes(response, "RELATED_PDP"));
	}

	@Test(groups={"Regression"},description="Verify - InValidPDPRelated - Success", dataProvider = "ApifyDataProvider_InvalidRelatedStyleId")
	public void apifyPdpInvalidPdpRelatedVerifySuccess(String styleId, String responseStatus) {
		int status = Integer.valueOf(responseStatus);
		RequestGenerator request = apifyServiceHelper.invokePdpRelated(styleId);
		System.out.println("Verify - InValidPDPRelated - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - InValidPDPRelated - Success is down", status, request.response.getStatus());
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDPLikeSummary - Success", dataProvider = "ApifyDataProvider_ValidStyleId")
	public void apifyPdpValidPDV3LikeSummaryPVerifySuccess(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpLikeSummary(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDPLikeSummary - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDPLikeSummary - Success is down",200,request.response.getStatus());
		AssertJUnit.assertTrue("Verify - Likers Type - Bad Response", apifyServiceHelper.findAllComponentTypes(response, "LIKERS"));
		AssertJUnit.assertTrue("Verify - Likers viewType - Bad Response", apifyServiceHelper.findAllComponentViewTypes(response, "LIKERS"));
	}

	@Test(groups={"Regression"},description="Verify - InValidPDPLikeSummary - Success", dataProvider = "ApifyDataProvider_InvalidLikeSummaryStyleId")
	public void apifyPdpInvalidPDV3LikeSummaryPVerifySuccess(String styleId, String responseStatus) {
		int status = Integer.valueOf(responseStatus);
		RequestGenerator request = apifyServiceHelper.invokePdpLikeSummary(styleId);
		System.out.println("Verify - InValidPDPLikeSummary - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - InValidPDPLikeSummary - Success is down", status, request.response.getStatus());
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDPOffers - Success", dataProvider = "ApifyDataProvider_ValidStyleId")
	public void apifyPdpValidOffersVerifySuccess(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpOffers(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDPOffers - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDPOffers - Success is down",200,request.response.getStatus());
		AssertJUnit.assertTrue("Verify - Best_Price Type - Bad Response", apifyServiceHelper.findAllComponentTypes(response, "BEST_PRICE"));
		AssertJUnit.assertTrue("Verify - Best_Price viewType - Bad Response", apifyServiceHelper.findAllComponentViewTypes(response, "BEST_PRICE"));
	}

	@Test(groups={"Regression"},description="Verify - InValidPDPOffers - Success", dataProvider = "ApifyDataProvider_InvalidOffersStyleId")
	public void apifyPdpInvalidOffersVerifySuccess(String styleId, String responseStatus) {
		int status = Integer.valueOf(responseStatus);
		RequestGenerator request = apifyServiceHelper.invokePdpOffers(styleId);
		System.out.println("Verify - InValidPDPOffers - Success Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - InValidPDPOffers - Success is down", status, request.response.getStatus());
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "ApifyDataProvider_ValidStyleId",
			description = "Verify offers response Schema")
	public void apifyPdpOffersSchemaValidations(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpOffers(styleId);
		String ressponse = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting apifyPdpOffers API response :\n\n" + ressponse + "\n");
		log.info("\nPrinting apifyPdpOffers API response :\n\n" + ressponse + "\n");

		AssertJUnit.assertEquals("[apifyPdpOffersSchemaValidations is not working.]", 200, request.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/Apify/offers.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(ressponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in pdp offers API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "ApifyDataProvider_ValidStyleId",
			description = "Verify offers response Schema")
	public void apifyPdpLikeSummarySchemaValidations(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpLikeSummary(styleId);
		String ressponse = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting apifyPdpLikeSummary API response :\n\n" + ressponse + "\n");
		log.info("\nPrinting apifyPdpLikeSummary response :\n\n" + ressponse + "\n");

		AssertJUnit.assertEquals("[apifyPdpLikeSummary is not working.]", 200, request.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/Apify/likessummary.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(ressponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in pdp offers API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "ApifyDataProvider_ValidStyleId",
			description = "Verify Brand response Schema")
	public void apifyPdpBrandSchemaValidations(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpBrand(styleId);
		String ressponse = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting apifyPdpBrand API response :\n\n" + ressponse + "\n");
		log.info("\nPrinting apifyPdpBrand response :\n\n" + ressponse + "\n");

		AssertJUnit.assertEquals("[apifyPdpBrand is not working.]", 200, request.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/Apify/brands.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(ressponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in pdp offers API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Test(groups = {
			"SchemaValidation"
	}, dataProvider = "ApifyDataProvider_ValidStyleId",
			description = "Verify Related response Schema")
	public void apifyPdpRelatedSchemaValidations(String styleId) {
		RequestGenerator request = apifyServiceHelper.invokePdpBrand(styleId);
		String ressponse = request.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting apifyPdpRelated API response :\n\n" + ressponse + "\n");
		log.info("\nPrinting apifyPdpRelated response :\n\n" + ressponse + "\n");

		AssertJUnit.assertEquals("[apifyPdpBrand is not working.]", 200, request.response.getStatus());

		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/Apify/related.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(ressponse, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in pdp offers API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(groups={"Sanity","Regression"},description="Serviceability Verify Responses", dataProvider="Apify_ServiceiabilityStyleId")
	public void apifyStyleServiceabilityVerifyResponseCode(String styleId, String pincode) {
		RequestGenerator styleInfoRequest= apifyServiceHelper.invokePdp(styleId);
		String getStyleInfoResponse = styleInfoRequest.respvalidate.returnresponseasstring();
		if(styleInfoRequest.response.getStatus()==200) {
			RequestGenerator getV4ServiceabilityRequest = apifyServiceHelper.invokeStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String getV4ServiceabilityResponse = getV4ServiceabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Serviceiability Response : "+getV4ServiceabilityResponse);
			AssertJUnit.assertEquals("Serviceability Info API is down",200,getV4ServiceabilityRequest.response.getStatus());
		} else {
			AssertJUnit.fail("Get apify Info API is Down");
		}
	}

	//todo for get status
	@Test(groups={"Regression"},description="InvalidPincodeServiceability Verify Responses", dataProvider="Apify_InvalidPincodeServiceiabilityStyleId")
	public void apifyStyleInvalidPincodeServiceabilityVerifyResponseCode(String styleId, String responseCode) {
		RequestGenerator styleInfoRequest= apifyServiceHelper.invokePdp("560078");
		String getStyleInfoResponse = styleInfoRequest.respvalidate.returnresponseasstring();
		if(styleInfoRequest.response.getStatus()==200) {
			RequestGenerator serviceiabilityRequest = apifyServiceHelper.invokeStyleServiceabilityV4(getStyleInfoResponse, styleId);
			String serviceiabilityResponse = serviceiabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Serviceiability Response : "+serviceiabilityResponse);
			System.out.println(responseCode + serviceiabilityRequest.response.getStatus());
			AssertJUnit.assertEquals("Serviceability Info API is down", responseCode, Integer.toString(serviceiabilityRequest.response.getStatus()));
		} else {
			AssertJUnit.fail("Get apify Info API is Down");
		}
	}

	@Test(groups={"Sanity","Regression"},description="NonServiceability Verify Responses", dataProvider="ApifyDataProvider_NonServiceableStyleId")
	public void apifyNonServiceabilityVerifyResponseCode(String styleId, String pincode) {
		RequestGenerator styleInfoRequest= apifyServiceHelper.invokePdp(styleId);
		String getStyleInfoResponse = styleInfoRequest.respvalidate.returnresponseasstring();
		if(styleInfoRequest.response.getStatus()==200) {
			RequestGenerator serviceiabilityRequest = apifyServiceHelper.invokeStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String serviceiabilityResponse = serviceiabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Serviceiability Response : "+serviceiabilityResponse);
			AssertJUnit.assertEquals("Serviceability Info API is down",200,serviceiabilityRequest.response.getStatus());
			AssertJUnit.assertEquals("Serviceability Info API is down", pincode, JsonPath.read(serviceiabilityResponse, "$.pincode"));
		} else {
			AssertJUnit.fail("Get apify Info API is Down");
		}
	}

	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDP - Success", dataProvider = "ApifyDataProvider_ValidSizeRelatedRecommendationEnabledState")
	public void apifySizeRelatedVerifySuccess(String styleId, String abTestType) {
		
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdpSizeRecommendationAbTestState(styleId, abTestType);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		
		if(abTestType.equals("disabled")) {
			AssertJUnit.assertFalse("sizeRecoLazy is present", apifyServiceHelper.isSizeRecoLazyPresent(pdpResponse));
		} else {
			AssertJUnit.assertTrue("sizeRecoLazy is not present", apifyServiceHelper.isSizeRecoLazyPresent(pdpResponse));
			//Find all Recommendation action url from pdp api and check the status
			for(Object relatedProductUrl: apifyServiceHelper.findAllRelatedProductUrl(pdpResponse)) {
				RequestGenerator sizeRelatedRequest = apifyServiceHelper.invokePdpAction(relatedProductUrl.toString());
				String sizeRelatedResponse = sizeRelatedRequest.respvalidate.returnresponseasstring();
				System.out.println("Verify - sizeRelated - Success Response : "+sizeRelatedRequest.respvalidate.returnresponseasstring());
				AssertJUnit.assertEquals("Verify - sizeRelated - Success is down", 200, sizeRelatedRequest.response.getStatus());
				AssertJUnit.assertTrue("Verify - sizeRelated Type - Bad Response", apifyServiceHelper.findAllComponentTypes(sizeRelatedResponse, "SIMILAR_WITH_SIZE"));
				AssertJUnit.assertTrue("Verify - sizeRelated viewType - Bad Response", apifyServiceHelper.findAllComponentViewTypes(sizeRelatedResponse, "SIMILAR_WITH_SIZE"));
			} 
		} 
	}
	
	@Test(groups={"Sanity", "Regression"},description="Verify - ValidPDP - Success", dataProvider = "ApifyDataProvider_ValidSizeRecommendation")
	public void apifySizeRecommendationVerifySuccess(String styleId, String abTestType, String uidxVal) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdpSizeRecommendationAbTestState(styleId, abTestType);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		System.out.println("Verify - ValidPDP - Success Response : "+pdpRequest.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		if(abTestType.equals("disabled")) {
			AssertJUnit.assertFalse("sizeRecoLazy is present", apifyServiceHelper.isSizeRecoLazyPresent(pdpResponse));
		} else {
			//Find all Recommendation action url from pdp api and check the status
			RequestGenerator sizeRelatedRequest = apifyServiceHelper.invokePdpSizeRecommendation(styleId, apifyServiceHelper.findAllSkus(pdpResponse).toString(), uidxVal);
			System.out.println("Verify - sizeRelated - Success Response : "+sizeRelatedRequest.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("Verify - sizeRecommendation - Success is down", 200, sizeRelatedRequest.response.getStatus());
		}
	}
	
	@Test(groups={"Sanity", "Regression"},description="Verify - apifyPDPCheckSbpStatusVerifySuccess - Success", dataProvider = "ApifyDataProvider_ValidStyleIdForSbp")
	public void apifyPDPCheckSbpStatusVerifySuccess(String styleId, String sbpStatus) {
		RequestGenerator pdpRequest = apifyServiceHelper.invokePdp(styleId);
		String pdpResponse = pdpRequest.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals("Verify - ValidPDP - Success is down", 200, pdpRequest.response.getStatus());
		//Checking style id
		AssertJUnit.assertEquals("Verify - apifyPDPCheckSbpStatusVerifySuccess - Success is down", styleId, JsonPath.read(pdpResponse, "$.info.id").toString());
		AssertJUnit.assertEquals("Verify - apifyPDPCheckSbpStatusVerifySuccess sbpStatus - Bad Response", sbpStatus, apifyServiceHelper.isSbpEnabled(pdpResponse).get(0).toString());
		AssertJUnit.assertEquals("Verify - apifyPDPCheckSbpStatusVerifySuccess sbpStatus - Bad Response", sbpStatus, apifyServiceHelper.isSbpEnabled(pdpResponse).get(1).toString());
	}
}
