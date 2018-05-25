package com.myntra.apiTests.portalservices.lgpServicesOnDemand;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.PumpsTagsDP;
import com.myntra.apiTests.portalservices.lgpservices.LGPPumpsTagsHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import net.minidev.json.JSONArray;
import java.util.Random;
public class PumpsTagsTest extends PumpsTagsDP
{	
	LGPPumpsTagsHelper helper = new LGPPumpsTagsHelper();
	Random rn = new Random();
	public String getRandomData(String data)
	{	
		if((data.length()==0)||data.replaceAll("\\s", "").length()==0)
		{
			return data;
		}
		else
		{
			return data+rn.nextInt((999999999-111)+111);
		}

	}
	
	@Test(groups = {"Regression","Sanity"}, dataProvider = "CreateSingleTag", description = "Create a Single Tag")
	public void createSingleTag_VerifySuccess(String name, String type, String statusCode, String statusMessage, String status) 
	{
		RequestGenerator request = helper.createSingleTag(name, type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Single Tag Response : " +response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
	}

	@Test(groups = {"Regression"}, dataProvider = "CreateSingleTag", description = "Create a Single Tag", priority=0)
	public void createSingleTag_VerifySuccessWithData(String name, String type, String statusCode, String statusMessage, String statusType) 
	{
		String Name = name;
		String Type = type;
		if(statusCode.equals("1021"))
		{
			System.out.println("GENERATING RANDOM DATA");
			Name = getRandomData(name);
			Type = getRandomData(type);
		}
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Single Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Improper Status Code",Integer.parseInt(statusCode), Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()));
		AssertJUnit.assertEquals("Improper Status Message", statusMessage, JsonPath.read(response, "$.status.statusMessage"));
		AssertJUnit.assertEquals("Improper Status Type", statusType, JsonPath.read(response, "$.status.statusType"));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "CreateSingleTag_DuplicateTagCreation", description = "Create a Single Tag")
	public void createDuplicateTags_VerifyCountNotIncremented(String name, String type, String statusCode, String statusMessage, String statusType) 
	{
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		String TagCount = JsonPath.read(response, "$.status.totalCount").toString();
		
		request = helper.createSingleTag(Name, Type);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		AssertJUnit.assertEquals("Duplicate Tags are Created", true, JsonPath.read(response, "$.status.totalCount").toString().equals(TagCount));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "CreateSingleTag_DuplicateTagCreation", description = "Create a Single Tag")
	public void createDuplicateTagsWithDifferentBrand_VerifyCountIncremented(String name, String type, String statusCode, String statusMessage, String statusType) 
	{
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		int TagCount = Integer.parseInt(JsonPath.read(response, "$.status.totalCount").toString());
		
		Type = getRandomData(type);
		request = helper.createSingleTag(Name, Type);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		AssertJUnit.assertEquals("Duplicate Tags Are Created", TagCount+1, Integer.parseInt(JsonPath.read(response, "$.status.totalCount").toString()));

	}
	
	@Test(groups = {"Regression"}, dataProvider = "FetchDuplicateTags", description = "Create a Single Tag")
	public void FetchDuplicateTagsWithDifferentBrand_VerifyCountIncremented(String name, String type, String statusCode, String statusMessage, String statusType) throws JSONException 
	{
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		int TagCount = Integer.parseInt(JsonPath.read(response, "$.status.totalCount").toString());
		
		String Type2 = getRandomData(type);
		request = helper.createSingleTag(Name, Type2);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());
		AssertJUnit.assertEquals("Duplicate Tags Are Created", TagCount+1, Integer.parseInt(JsonPath.read(response, "$.status.totalCount").toString()));
		
		request = helper.findTagByName(Name);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Find Tag Response : "+response);
		AssertJUnit.assertEquals("Find Tag Service Failure", 200, request.response.getStatus());
		
		JSONArray TagsArr = JsonPath.read(response, "$.data[*].name");
		AssertJUnit.assertEquals("All the tags are not retrieved", TagCount+1, TagsArr.size());
		
	}
	
	@Test(groups = {"Regression","Sanity"}, dataProvider = "CreateMultipleTags", description = "Create a Single Tag")
	public void createMultipleTag_VerifySuccess(String name, String type, String statusCode, String statusMessage, String status) 
	{
		LinkedHashMap<String, String> Tags = new LinkedHashMap<String, String>();
		for(int i=0; i<5;i++)
		{
			String NewName = getRandomData(name);
			System.out.println("NEWNAME : "+NewName);
			String NewType = getRandomData(type);
			System.out.println("NEWTYPE : "+NewType);
			Tags.put(NewName, NewType);
		}
		
		RequestGenerator request = helper.createMultipleTags(Tags);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Single Tag Response : " +response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
	
		
	}
	
	@Test(groups = {"SchemaValidation"}, dataProvider = "CreateSingleTag_Schema", description = "Create a Single Tag")
	public void createSingleTag_SchemaValidation(String name, String type, String statusCode, String statusMessage, String status) 
	{
		RequestGenerator request = helper.createSingleTag(name, type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Single Tag Response : " +response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgp/pumps-tags/CreateTagSchema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing Create Tag response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Find Tag by Name
	@Test(groups = {"Sanity"}, dataProvider = "FindTagByName", description = "Find a Tag using the tag name")
	public void FindTagByName_VerifySuccess(String name, String type) 
	{
		
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());

		request = helper.findTagByName(Name);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Find Tag Response : "+response);
		AssertJUnit.assertEquals("Find Tag Service Failure", 200, request.response.getStatus());
	}
	
	@Test(groups = {"Sanity","ExhaustiveRegression"}, dataProvider = "FindTagByName_InvalidNames", description = "Find a Tag using the tag name")
	public void FindTagByName_InvalidNames(String name, String statusCode) 
	{
		
		RequestGenerator request = helper.findTagByName(name);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Find Tag Response : "+response);
		AssertJUnit.assertEquals("Find Tag Service Failure", Integer.parseInt(statusCode), request.response.getStatus());
	}
	
	@Test(groups = {"Regression"}, dataProvider = "FindTagByName", description = "Find a Tag using the tag name")
	public void FindTagByName_VerifyData(String name, String type) throws JSONException 
	{
		
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());

		request = helper.findTagByName(Name);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Find Tag Response : "+response);
		AssertJUnit.assertEquals("Find Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Tag Name Mismatch",Name.toLowerCase(), JsonPath.read(response,"$.data[0].name").toString());
		
		JSONArray TagsArr= JsonPath.read(response, "$.data");
		AssertJUnit.assertEquals("Multiple Tags are returned",false, TagsArr.size()>1);

	}
	
	@Test(groups = {"Schema"}, dataProvider = "FindTagByName", description = "Find a Tag using the tag name")
	public void FindTagByName_VerifySchema(String name, String type) throws JSONException 
	{
		
		String Name = name;
		String Type = type;		
		System.out.println("GENERATING RANDOM DATA");
		Name = getRandomData(name);
		Type = getRandomData(type);
		
		RequestGenerator request = helper.createSingleTag(Name, Type);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Tag Response : "+response);
		AssertJUnit.assertEquals("Create Tag Service Failure", 200, request.response.getStatus());
		AssertJUnit.assertEquals("Create Tag Service Failure", "1021", JsonPath.read(response, "$.status.statusCode").toString());

		request = helper.findTagByName(Name);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Find Tag Response : "+response);
		AssertJUnit.assertEquals("Find Tag Service Failure", 200, request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgp/pumps-tags/FindTagByNameSchema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing Create Tag response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	@Test(groups = {"Regression","Sanity"}, dataProvider = "validFetchTypeTag", description = "fetch by type")
	public void fetchByType_VerifySuccess(String type) {
		RequestGenerator fetchTypeRequest = helper.fetchByType(type);
		AssertJUnit.assertEquals("Fetch Type Service Failure", 200, fetchTypeRequest.response.getStatus());
		String fetchTypeResponse = fetchTypeRequest.respvalidate.returnresponseasstring();
		List<String> fetchType = JsonPath.read(fetchTypeResponse, "$.data[*].type");

		for(int i=0; i<fetchType.size(); i++) {
			AssertJUnit.assertEquals("Fetch Type Service Failure", type, JsonPath.read(fetchTypeResponse, "$.data["+i+"].type"));
		}
	}

	@Test(groups = {"Regression","Sanity"}, dataProvider = "invalidFetchTypeTag", description = "invalid fetch by type")
	public void invalidFetchByType(String type, String statusCode) {
		RequestGenerator fetchTypeRequest = helper.fetchByType(type);
		String fetchTypeResponse = fetchTypeRequest.respvalidate.returnresponseasstring();
		System.out.println("Fetch Tag Type Response : "+fetchTypeResponse);
		AssertJUnit.assertEquals("Fetch Tag Type Service Failure", Integer.parseInt(statusCode), fetchTypeRequest.response.getStatus());
	}

	@Test(groups = {"Regression","Sanity"}, dataProvider = "validFetchKeyTag", description = "fetch by key")
	public void fetchByKey_VerifySuccess(String type, String name) {
		RequestGenerator fetchKeyRequest = helper.fetchByKey(type, name);
		AssertJUnit.assertEquals("Fetch Key Service Failure", 200, fetchKeyRequest.response.getStatus());
		String fetchKeyResponse = fetchKeyRequest.respvalidate.returnresponseasstring();
		List<String> fetchType = JsonPath.read(fetchKeyResponse, "$.data[*].type");
		List<String> fetchName = JsonPath.read(fetchKeyResponse, "$.data[*].name");
		AssertJUnit.assertEquals(fetchType.size(), fetchName.size());
		for(int i=0; i<fetchType.size(); i++) {
			AssertJUnit.assertEquals("Fetch Key Service Failure", type, JsonPath.read(fetchKeyResponse, "$.data["+i+"].type"));
			AssertJUnit.assertEquals("Fetch Key Service Failure", name, JsonPath.read(fetchKeyResponse, "$.data["+i+"].name"));
		}
	}

	@Test(groups = {"Regression","Sanity"}, dataProvider = "invalidFetchKeyTag", description = "invalid fetch by key")
	public void invalidFetchByKey(String type, String name, String statusCode) {
		RequestGenerator fetchKeyRequest = helper.fetchByKey(type, name);
		String fetchKeyResponse = fetchKeyRequest.respvalidate.returnresponseasstring();
		System.out.println("Fetch Key Response : "+fetchKeyResponse);
		AssertJUnit.assertEquals("Fetch Key Response Failure", Integer.parseInt(statusCode), fetchKeyRequest.response.getStatus());
	}
}