package com.myntra.apiTests.portalservices.devapis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.dataproviders.devapis.StyleTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.DevAPICommonMethods;
import com.myntra.apiTests.portalservices.devapiservice.StyleTestsHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import net.minidev.json.JSONArray;

public class StyleTests extends StyleTestsDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleTests.class);
	static StyleTestsHelper StyleHelper = new StyleTestsHelper();
	static DevAPICommonMethods DevAPIHelper = new DevAPICommonMethods();
	
	public String encodeStringSpaces(String S)
	{
		return S.replace(" ","%20");
	}
	
	//Verify Get Style
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetStyleInfo_Sanity",description="Get Style Information Based on Style ID. Verify Response")
	public void DevAPI_Style_GetStyle_VerifySuccess(String styleId)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		System.out.println("Get Style Info Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_Style_GetStyleInfo_Sanity",description="Get Style Information Based on Style ID. Verify Response")
	public void DevAPI_Style_GetStyle_VerifyNodes(String styleId) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_GetStyleInfo_Sanity",description="Get Style Information Based on Style ID. Verify Response")
	public void DevAPI_Style_GetStyle_VerifyData(String styleId)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Style Info API is down - Style ID does not Match",Integer.parseInt(styleId), (int) JsonPath.read(response, "$.data.id"));
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_GetStyleInfo_Sanity",description="Get Style Information Based on Style ID. Verify Response")
	public void DevAPI_Style_GetStyle_VerifySchema(String styleId) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_GetStyleInfo_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get Style Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			System.out.println("Caught Exception in Schema Validation");
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_GetStyleInfo_NegativeCases",description="Get Style Information Based on Style ID. Negative Cases")
	public void DevAPI_Style_GetStyle_VerifyFailCases(String styleId, String errorCode)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Info API is down",Integer.parseInt(errorCode),request.response.getStatus());
	}
	
	//Get Similar Styles
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetNormalStyleRecommendations_Sanity",description="Get Style Recommendations Based on Style ID. Verify Responses")
	public void DevAPI_Style_GetStyleRecommendations_VerifyResponseCode(String styleId, String visualSearchFlag, String colorRecommendationsFlag)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		String ProductName= encodeStringSpaces(JsonPath.read(response,"$.data.productDisplayName").toString());
		
		request = StyleHelper.getSimilarStyles(styleId, ProductName, visualSearchFlag, colorRecommendationsFlag);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Recommendations Response : "+response);
		AssertJUnit.assertEquals("Get Style recommendations Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_GetNormalStyleRecommendations_Sanity",description="Get Style Recommendations Based on Style ID. Verify Responses")
	public void DevAPI_Style_GetStyleRecommendations_VerifyNodes(String styleId, String visualSearchFlag, String colorRecommendationsFlag) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		String ProductName= encodeStringSpaces(JsonPath.read(response,"$.data.productDisplayName").toString());
		
		request = StyleHelper.getSimilarStyles(styleId, ProductName, visualSearchFlag, colorRecommendationsFlag);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Recommendations Response : "+response);
		AssertJUnit.assertEquals("Get Style recommendations Info API is down",200,request.response.getStatus());
		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_GetStyleRecommendations_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get Style Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_GetNormalStyleRecommendations_Regression",description="Get Style Recommendations Based on Style ID. Verify Responses")
	public void DevAPI_Style_GetStyleRecommendations_VerifySchemaForAll(String styleId, String visualSearchFlag, String colorRecommendationsFlag) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		String ProductName= encodeStringSpaces(JsonPath.read(response,"$.data.productDisplayName").toString());
		
		request = StyleHelper.getSimilarStyles(styleId, ProductName, visualSearchFlag, colorRecommendationsFlag);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Recommendations Response : "+response);
		AssertJUnit.assertEquals("Get Style recommendations Info API is down",200,request.response.getStatus());
		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_GetStyleRecommendations_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get Style Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetNormalStyleRecommendations_Sanity",description="Get Visual Style Recommendations Based on Style ID. Verify Responses")
	public void DevAPI_Style_GetStyleVisualRecommendations_VerifyResponseCode(String styleId, String visualSearchFlag, String colorRecommendationsFlag)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		String ProductName= encodeStringSpaces(JsonPath.read(response,"$.data.productDisplayName").toString());
		
		request = StyleHelper.getSimilarStyles(styleId, ProductName, "true", "false");
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Recommendations Response : "+response);
		AssertJUnit.assertEquals("Get Style recommendations Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetNormalStyleRecommendations_Sanity",description="Get Style Color Recommendations Based on Style ID. Verify Responses")
	public void DevAPI_Style_GetStyleColorRecommendations_VerifyResponseCode(String styleId, String visualSearchFlag, String colorRecommendationsFlag)
	{
		RequestGenerator request = StyleHelper.getStyleInfo(styleId);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Info Response : "+response);
		AssertJUnit.assertEquals("Get Style Info API is down",200,request.response.getStatus());
		String ProductName= encodeStringSpaces(JsonPath.read(response,"$.data.productDisplayName").toString());
		
		request = StyleHelper.getSimilarStyles(styleId, ProductName, "false", "true");
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Recommendations Response : "+response);
		AssertJUnit.assertEquals("Get Style recommendations Info API is down",200,request.response.getStatus());

	}
	
	//Serviceability Test Cases
	//V1 Serviceability
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_V1Serviceiability_Sanity",description="Get Style Serviceability [V1]. Verify Responses")
	public void DevAPI_Style_Serviceability_V1_VerifyResponseCode(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV1(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"SchemaValidations"},dataProvider="DevAPI_Style_V1Serviceiability_Sanity",description="Get Style Serviceability [V1]. Verify Responses")
	public void DevAPI_Style_Serviceability_V1_VerifySchema(String searchString) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV1(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());

		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_Serviceability_V1_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get V1 Serviceability Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_V1Serviceability_Regression",description="Get Style Serviceability [V1]. Verify Data")
	public void DevAPI_Style_Serviceability_V1_VerifyData(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV1(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_V2Serviceiability_Sanity",description="Get Style Serviceability [V2]. Verify Responses")
	public void DevAPI_Style_Serviceability_V2_VerifyResponseCode(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV2(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_V2Serviceiability_Sanity",description="Get Style Serviceability [V2]. Verify Responses")
	public void DevAPI_Style_Serviceability_V2_VerifySchema(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV2(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_Serviceability_V2_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get V2 Serviceability Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//V3 Serviceability
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_V3Serviceiability_Sanity",description="Get Style Serviceability [V3]. Verify Responses")
	public void DevAPI_Style_Serviceability_V3_VerifyResponseCode(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());

	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_V3Serviceiability_Regression",description="Get Style Serviceability [V3]. Verify Data")
	public void DevAPI_Style_Serviceability_V3_VerifyData(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",true, (boolean) JsonPath.read(response,"$.data.serviceable"));
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",true, (boolean) JsonPath.read(response,"$.data.serviceable"));
		List<String> Services = new ArrayList<String>();
		boolean foundServiceableData=true;
		boolean foundPdpData=true;
		Services.add("TRY AND BUY AVAILABLE!!");
		Services.add("STANDARD DELIVERY TIME");
		Services.add("FASTER DELIVERY AVAILABLE");
		Services.add("CASH ON DELIVERY (COD)");
		Services.add("Try and buy available!!");
		Services.add("Standard Delivery in max 3 days");
		Services.add("Faster delivery available");
		Services.add("Cash on delivery available");
		JSONArray SerCheck = JsonPath.read(response,"$.data.options[*].serviceability_check.title");
		JSONArray PdpCheck = JsonPath.read(response,"$.data.options[*].pdp.title");
		for(int i=0; i<SerCheck.size();i++)
		{
			if(!Services.contains(SerCheck.get(i).toString()))
			{
				foundServiceableData = false;
			}
			if(!Services.contains(PdpCheck.get(i).toString()))
			{
				foundPdpData = false;
			}
		}
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - All Serviceable Types are not Available",true,foundServiceableData);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - All Serviceable Types are not Available",true,foundPdpData);
		for(int i=0;i<4;i++)
		{
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - All Serviceable Types are not Available",true,Boolean.parseBoolean(JsonPath.read(response,"$.data.options["+i+"].serviceability_check.available")));
		}
		
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_V3Serviceiability_Sanity",description="Get Style Serviceability [V3]. Verify Schema")
	public void DevAPI_Style_Serviceability_V3_VerifySchema(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_Serviceability_V3_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get V3 Serviceability Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_Style_V3Serviceiability_Sanity",description="Get Style Serviceability [V3]. Verify Nodes")
	public void DevAPI_Style_Serviceability_V3_VerifyNode(String searchString) throws JsonProcessingException, IOException
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);

	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_V3Serviceiability_FailCases",description="Get Style Serviceability [V3]. Verify Failure")
	public void DevAPI_Style_Serviceability_V3_VerifyFailure(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",500,request.response.getStatus());

	}
	
	//V4 Serviceability
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_V4Serviceiability_Sanity",description="Get Style Serviceability [V4]. Verify Responses")
	public void DevAPI_Style_Serviceability_V4_VerifyResponseCode(String styleId, String pincode)
	{
		RequestGenerator getStyleInfoRequest= StyleHelper.getStyleInfo(styleId);
		String getStyleInfoResponse = getStyleInfoRequest.respvalidate.returnresponseasstring();
		if(getStyleInfoRequest.response.getStatus()==200)
		{
			RequestGenerator getV4ServiceabilityRequest = StyleHelper.getStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String getV4ServiceabilityResponse = getV4ServiceabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Get V4 Style Serviceiability Response : "+getV4ServiceabilityResponse);
			AssertJUnit.assertEquals("Get Style Serviceability Info API is down",200,getV4ServiceabilityRequest.response.getStatus());
		}
		else
		{
			AssertJUnit.fail("Get Style Info API is Down");
		}

	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_V4Serviceiability_Sanity",description="Get Style Serviceability [V4]. Verify Data")
	public void DevAPI_Style_Serviceability_V4_VerifyData(String styleId, String pincode)
	{
		RequestGenerator getStyleInfoRequest= StyleHelper.getStyleInfo(styleId);
		String getStyleInfoResponse = getStyleInfoRequest.respvalidate.returnresponseasstring();
		if(getStyleInfoRequest.response.getStatus()==200)
		{
			RequestGenerator getV4ServiceabilityRequest = StyleHelper.getStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String response = getV4ServiceabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Get Style Serviceiability Response : "+response);
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,getV4ServiceabilityRequest.response.getStatus());
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - Serviceability Fail",true, (boolean) JsonPath.read(response,"$.data.serviceable"));
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - Pincode Mismatch",pincode,JsonPath.read(response,"$.data.pincode"));

			for(int i=0;i<4;i++)
			{
				AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - All Serviceable Types are not Available",true,Boolean.parseBoolean(JsonPath.read(response,"$.data.options["+i+"].serviceability_check.available")));
			}
			
		}
		else
		{
			AssertJUnit.fail("Get Style Info API is Down");
		}

	}
	
	@Test(groups={"Regression"},dataProvider="DevAPI_Style_V4Serviceiability_NonServiceable",description="Get Style Serviceability [V4]. Verify Data")
	public void DevAPI_Style_Serviceability_V4_VerifyNonServiceable(String styleId, String pincode)
	{
		RequestGenerator getStyleInfoRequest= StyleHelper.getStyleInfo(styleId);
		String getStyleInfoResponse = getStyleInfoRequest.respvalidate.returnresponseasstring();
		if(getStyleInfoRequest.response.getStatus()==200)
		{
			RequestGenerator getV4ServiceabilityRequest = StyleHelper.getStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String response = getV4ServiceabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Get Style Serviceiability Response : "+response);
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,getV4ServiceabilityRequest.response.getStatus());
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - Serviceability Fail",false,(boolean)JsonPath.read(response,"$.data.serviceable"));
			AssertJUnit.assertEquals("Get Style Serviceiability Info API is down - Pincode Mismatch",pincode,JsonPath.read(response,"$.data.pincode"));

		}
		else
		{
			AssertJUnit.fail("Get Style Info API is Down");
		}

	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_V4Serviceiability_Sanity",description="Get Style Serviceability [V3]. Verify Schema")
	public void DevAPI_Style_Serviceability_V4_VerifySchema(String searchString)
	{
		RequestGenerator request = StyleHelper.getStyleServiceabilityV3(searchString);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get Style Serviceiability Response : "+response);
		AssertJUnit.assertEquals("Get Style Serviceiability Info API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_Serviceability_V4_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get V4 Serviceability Info API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_Style_V4Serviceiability_Sanity",description="Get Style Serviceability [V3]. Verify Nodes")
	public void DevAPI_Style_Serviceability_V4_VerifyNode(String styleId, String pincode) throws JsonProcessingException, IOException
	{
		RequestGenerator getStyleInfoRequest= StyleHelper.getStyleInfo(styleId);
		String getStyleInfoResponse = getStyleInfoRequest.respvalidate.returnresponseasstring();
		if(getStyleInfoRequest.response.getStatus()==200)
		{
			RequestGenerator getV4ServiceabilityRequest = StyleHelper.getStyleServiceabilityV4(getStyleInfoResponse, pincode);
			String getV4ServiceabilityResponse = getV4ServiceabilityRequest.respvalidate.returnresponseasstring();
			System.out.println("Get V4 Style Serviceiability Response : "+getV4ServiceabilityResponse);
			AssertJUnit.assertEquals("Get Style Serviceability Info API is down",200,getV4ServiceabilityRequest.response.getStatus());
			boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(getV4ServiceabilityResponse);
			AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
		}
		else
		{
			AssertJUnit.fail("Get Style Info API is Down");
		}
		

	}
	
	//Get Style Offers
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetStyleOffers_Sanity",description="Get Style Offer Information Based on Style ID. Verify Response")
	public void DevAPI_Style_GetStyleOffers_Verify(String styleId)
	{
		RequestGenerator request = StyleHelper.getStyleOffers(styleId);
		System.out.println("Get Style Offers  Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style Offers Info API is down",200,request.response.getStatus());
	}
	
	//Get Style From Sku Code
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetStyleFromSku_Sanity",description="Get Style Information Based on Sku Code. Verify Response")
	public void DevAPI_Style_GetStyleFromSkuCode_VerifySuccess(String skuCode)
	{
		RequestGenerator request = StyleHelper.getStyleIdFromSkuCode(skuCode);
		System.out.println("Get Style From SKUcode Service  Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Style From SKUcode API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetStyleFromSku_Data",description="Get Style Offer Information Based on Style ID. Verify Data")
	public void DevAPI_Style_GetStyleFromSkuCode_VerifyData(String skuCode, String styleId)
	{
		RequestGenerator request = StyleHelper.getStyleIdFromSkuCode(skuCode);
		String response = request.returnresponseasstring();
		//JSONObject resObj = JsonPath.read(response,"$");
		//System.out.println("Get Style From SKUcode Service  Response : "+response);
		//System.out.println("TESTXXX: "+resObj.getAsString("data.id"));
 		//AssertJUnit.assertEquals("Get Style From SKUcode API is down - StyleID Count Mismatch","1",count);

	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Style_GetStyleFromSku_Sanity",description="Get Style Offer Information Based on Style ID. Verify Schema")
	public void DevAPI_Style_GetStyleFromSkuCode_VerifySchema(String skuCode)
	{
		RequestGenerator request = StyleHelper.getStyleIdFromSkuCode(skuCode);
		String response = request.returnresponseasstring();
		System.out.println("Get Style From SKUcode Service  Response : "+response);
		AssertJUnit.assertEquals("Get Style From SKUcode API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Style_GetStyleFromSkuCode_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get Style From SkuCode API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Style_GetStyleFromSku_FailCases",description="Get Style Offer Information Based on Style ID. Verify Data")
	public void DevAPI_Style_GetStyleFromSkuCode_VerifyFailCases(String skuCode, String statusCode)
	{
		RequestGenerator request = StyleHelper.getStyleIdFromSkuCode(skuCode);
		String response = request.returnresponseasstring();
		System.out.println("Get Style From SKUcode Service  Response : "+response);
		AssertJUnit.assertEquals("Get Style From SKUcode API is down",Integer.parseInt(statusCode),request.response.getStatus());
		if(!(statusCode.equals("500"))){
		AssertJUnit.assertEquals("Get Style From SKUcode API is down - Error Message Mismatch","Error occurred while retrieving/processing data",JsonPath.read(response, "$.meta.statusMessage").toString());
		AssertJUnit.assertEquals("Get Style From SKUcode API is down - Expected Count to be 0",0,(int) JsonPath.read(response, "$.meta.totalCount"));
		}
	}

}
