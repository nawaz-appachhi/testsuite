/*
 * @author: Aravind Raj R
 * @mail: aravind.raj@myntra.com
 */


package com.myntra.apiTests.portalservices.all;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.myntra.apiTests.dataproviders.LGAListsDP;
import com.myntra.apiTests.portalservices.LGAListsHelper.LGAListsServiceHelper;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import net.minidev.json.JSONArray;

public class LGAListsAPITests extends LGAListsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LGAListsAPITests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LGAListsServiceHelper listsServiceHelper = new LGAListsServiceHelper();
	static DevApiServiceHelper devapiServiceHelper = new DevApiServiceHelper();
	static boolean EncounteredFailureInTearDown = false;
	
	private static void printAndLog(String Message, String Param)
	{
		System.out.println(Message+Param);
		log.info(Message+Param);
	}
	
	//Get User Lists
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "getUserLists")
	public void Lists_GetUserLists_verifySuccess(String userName, String password)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
	}
	
	@Test(groups = { "Schema Validation" },dataProvider = "getUserLists")
	public void Lists_GetUserLists_verifySchema(String userName, String password)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-getUserLists-Schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Get User Lists API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "getUserLists")
	public void Lists_GetUserLists_verifyData(String userName, String password)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","2",JsonPath.read(response, "$.data.listCount").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
		Assert.assertEquals(null,JsonPath.read(response, "$.meta.errorDetail").toString(),"LGA Lists - Get User List API is returning invalid Message");

	}
	
	
	//Create List
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateUserList")
	public void Lists_CreateList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	} 
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateUserList")
	public void Lists_CreateListWithSameName_VerifyFailure(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 404,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working. User able to create list with existing name", 400, Integer.parseInt(JsonPath.read(response,"$.meta.statusCode")));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working. User able to create list with existing name", "LISTNAME_EXISTS",JsonPath.read(response,"$.meta.error"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working. User able to create list with existing name", "List name already used.",JsonPath.read(response,"$.meta.errorDetail"));

		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	} 
	
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateUserList")
	public void Lists_CreateList_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		String listId = JsonPath.read(response,"$.data.listID").toString();
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - List name Mismatching",listName, JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag2, JsonPath.read(response, "$.data.tags[0]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag1, JsonPath.read(response, "$.data.tags[1]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Access type is not matching",listAccess, JsonPath.read(response, "$.data.access"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Description is not matching",listDescription, JsonPath.read(response, "$.data.description"));

		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	} 
	
	@Test(groups = { "Schema Validation" },dataProvider = "CreateUserList")
	public void Lists_CreateList_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		String listId = JsonPath.read(response,"$.data.listID").toString();
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-createUserList-Schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Create User Lists API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	
	}
	
	//Create List Flow
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateUserList")
	public void Lists_CreateList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","2",JsonPath.read(response, "$.data.listCount").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message",null,JsonPath.read(response, "$.meta.errorDetail").toString());

		request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		String listId = JsonPath.read(response,"$.data.listID").toString();
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - List name Mismatching",listName, JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag2, JsonPath.read(response, "$.data.tags[0]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag1, JsonPath.read(response, "$.data.tags[1]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Access type is not matching",listAccess, JsonPath.read(response, "$.data.access"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Description is not matching",listDescription, JsonPath.read(response, "$.data.description"));

		request = listsServiceHelper.invokeGetUserListsService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","3",JsonPath.read(response, "$.data.listCount").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message",null,JsonPath.read(response, "$.meta.errorDetail").toString());

		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	}
	
	//Edit Collection
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "EditUserList")
	public void Lists_EditList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
	
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
	
		request = listsServiceHelper.invokeEditListService(userName, password, "public", "Edited Description", listId, "Edited Name");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Edit List Response : ", response);
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working.", 200,request.response.getStatus());
	
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "EditUserList")
	public void Lists_EditList_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
	
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
	
		request = listsServiceHelper.invokeEditListService(userName, password, "public", "Edited Description", listId, "Edited Name");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Edit List Response : ", response);
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List Id Mismatching",listId, JsonPath.read(response, "$.data.listID"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
		//AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Description is not matching","Edited Description", JsonPath.read(response, "$.data.description"));
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "EditUserList")
	public void Lists_EditList_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
	
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
	
		request = listsServiceHelper.invokeEditListService(userName, password, "public", "Edited Description", listId, "Edited Name");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Edit List Response : ", response);
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List Id Mismatching",listId, JsonPath.read(response, "$.data.listID"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
		//AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Description is not matching","Edited Description", JsonPath.read(response, "$.data.description"));
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-editUserList-Schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Edit User Lists API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "EditUserList")
	public void Lists_EditList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","2",JsonPath.read(response, "$.data.listCount").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message",null,JsonPath.read(response, "$.meta.errorDetail").toString());

		request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		String listId = JsonPath.read(response,"$.data.listID").toString();
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - List name Mismatching",listName, JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag2, JsonPath.read(response, "$.data.tags[0]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag1, JsonPath.read(response, "$.data.tags[1]"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Access type is not matching",listAccess, JsonPath.read(response, "$.data.access"));
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Description is not matching",listDescription, JsonPath.read(response, "$.data.description"));

		request = listsServiceHelper.invokeGetUserListsService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","3",JsonPath.read(response, "$.data.listCount").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message",null,JsonPath.read(response, "$.meta.errorDetail").toString());

		request = listsServiceHelper.invokeEditListService(userName, password, "public", "Edited Description", listId, "Edited Name");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Edit List Response : ", response);
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List Id Mismatching",listId, JsonPath.read(response, "$.data.listID"));
		AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
		
		request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List By Id Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - GET User List By ID API is not working.", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
		AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
		AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - Description is not matching","Edited Description", JsonPath.read(response, "$.data.description"));

		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
	
	}
	//Delete List
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteList_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete User List API is not working. - Message is not proper","SUCCESS",JsonPath.read(response, "$.meta.error"));
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteList_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete User List API is not working. - Message is not proper","SUCCESS",JsonPath.read(response, "$.meta.error"));
		
	try {
        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-deleteUserList-Schema.txt");
        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Edit User Lists API response", 
        		CollectionUtils.isEmpty(missingNodeList));
    } catch (Exception e) {
        e.printStackTrace();
    }
	
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteOwnList_VerifyFailure(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		int listCount = JsonPath.read(response, "$.data.listCount");
		String OwnListID = null;
		
		for(int i=0;i<listCount;i++)
		{
			if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
			{
				OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
			}
		}
		
		AssertJUnit.assertEquals("Wish List Not Available",false, OwnListID.isEmpty());
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, OwnListID);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Delete WishList Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working.",404,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Error Code is not proper",403,Integer.parseInt(JsonPath.read(response, "$.meta.statusCode")));
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Message is not proper","FORBIDDEN",JsonPath.read(response, "$.meta.error"));
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Message is not proper","Deletion of own_list forbidden",JsonPath.read(response, "$.meta.errorDetail"));

	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteWishList_VerifyFailure(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		int listCount = JsonPath.read(response, "$.data.listCount");
		String WishListID = null;
		
		for(int i=0;i<listCount;i++)
		{
			if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
			{
				WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
			}
		}
		
		AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, WishListID);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Delete WishList Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working.",404,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Error Code is not proper",403,Integer.parseInt(JsonPath.read(response, "$.meta.statusCode")));
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Message is not proper","FORBIDDEN",JsonPath.read(response, "$.meta.error"));
		AssertJUnit.assertEquals("LGA Lists - Delete WishList API is not working. - Message is not proper","Deletion of wishlist forbidden",JsonPath.read(response, "$.meta.errorDetail"));

	}

	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "DeleteUserList")
	public void Lists_DeleteList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","2",JsonPath.read(response, "$.data.listCount").toString());
		
		
		request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeGetUserListsService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","3",JsonPath.read(response, "$.data.listCount").toString());
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete User List API is not working. - Message is not proper","SUCCESS",JsonPath.read(response, "$.meta.error"));
	
		request = listsServiceHelper.invokeGetUserListsService(userName, password);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("Get User List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","2",JsonPath.read(response, "$.data.listCount").toString());
	
	}
	
	//Tag List
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "TagUnTagList")
	public void Lists_TagList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeTagService(userName, password, APINAME.TAGLIST, listId, "NEWTAG");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Tag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Tag a List API is not working.",200,request.response.getStatus());
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	} 

	//UnTag List
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "TagUnTagList")
	public void Lists_UnTagList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeTagService(userName, password, APINAME.TAGLIST, listId, "NEWTAG");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Tag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Tag a List API is not working.",200,request.response.getStatus());
		
		request = listsServiceHelper.invokeTagService(userName, password, APINAME.UNTAGLIST, listId, "NEWTAG");
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - UnTag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - UnTag a List API is not working.",200,request.response.getStatus());

		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	} 

	//Add Style to List
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
	public void Lists_DeleteStyleFromList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, listId, style2, listType);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Tag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Delete Style from List API is not working.",200,request.response.getStatus());
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
	public void Lists_DeleteStyleFromList_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, listId, style2, listType);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Tag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Delete Style from List API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Delete Style from List API is not working. List ID is invalid",listId,JsonPath.read(response, "$.data.listID").toString());
		AssertJUnit.assertEquals("LGA Lists - Delete Style from List API is not working. Style ID is invalid",style2,JsonPath.read(response, "$.data.productID").toString());
		AssertJUnit.assertEquals("LGA Lists - Delete Style from List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	}

	@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToList")
	public void Lists_DeleteStyleFromList_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
	{
		RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
		String response = request.respvalidate.returnresponseasstring();
		printAndLog("Create Lists Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
		String listId = JsonPath.read(response,"$.data.listID").toString();
		
		request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, listId, StyleID, listType);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("LGA Lists - Tag a List Response : ",response);
		AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
		AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",listId,JsonPath.read(response, "$.data.listID").toString());
		AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
		AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200, Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-deleteStyleFromList-Schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Edit User Lists API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
		response = request.respvalidate.returnresponseasstring();
		printAndLog("[Tear Down] - Delete List Response : ",response);
		AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
	}

	//Add Style to List
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToList_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, listId, StyleID, listType);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			
			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToList_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, listId, StyleID, listType);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",listId,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			
		}

		@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToList_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, listId, StyleID, listType);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",listId,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200, Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			try {
		        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-addStyleToList-Schema.txt");
		        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Add Style to User Lists API response", 
		        		CollectionUtils.isEmpty(missingNodeList));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			
		}

		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToWishList_VerifySuccess(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToWishList_VerifyData(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",WishListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		}
		
		@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToWishList_VerifySchema(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			try {
		        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-addStyleToList-Schema.txt");
		        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Add Style to wish list API response", 
		        		CollectionUtils.isEmpty(missingNodeList));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		//Delete Style from Wishlist
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromWishList_VerifySuccess(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Delete Style from WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete style from WishList API is not working",200,request.response.getStatus());
			
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromWishList_VerifyData(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",WishListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete style from WishList API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Delete style from WishList API is not working. List ID is invalid",WishListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Delete style from WishList API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Delete style from WishList API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		}
		
		@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromWishList_VerifySchema(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String WishListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					WishListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, WishListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, WishListID, StyleID1, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to WishList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete Style from WishList API is not working",200,request.response.getStatus());
		
			
			try {
		        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-deleteStyleFromList-Schema.txt");
		        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Delete Style from wish list API response", 
		        		CollectionUtils.isEmpty(missingNodeList));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		//Own List
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToOwnList_VerifySuccess(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("OwnList Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToOwnList_VerifyData(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("OwnList Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "OwnList");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",OwnListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		}
		
		@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_AddStyleToOwnList_VerifySchema(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			try {
		        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-addStyleToList-Schema.txt");
		        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Add Style to own list API response", 
		        		CollectionUtils.isEmpty(missingNodeList));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		//Delete Style from OwnList
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromOwnList_VerifySuccess(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Delete Style from OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete style from OwnList API is not working",200,request.response.getStatus());
			
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromOwnList_VerifyData(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",OwnListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete style from OwnList API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Delete style from OwnList API is not working. List ID is invalid",OwnListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Delete style from OwnList API is not working. Style ID is invalid",StyleID1,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Delete style from OwnList API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

		}
		
		@Test(groups = { "Schema Validation" },dataProvider = "AddRemoveStyleToDefaultList")
		public void Lists_DeleteStyleFromOwnList_VerifySchema(String userName, String password, String StyleID1, String StyleID2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			AssertJUnit.assertEquals("Wish List Not Available",false, OwnListID.isEmpty());
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working",200,request.response.getStatus());
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, OwnListID, StyleID1, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Add Style to OwnList Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Delete Style from OwnList API is not working",200,request.response.getStatus());
		
			
			try {
		        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-deleteStyleFromList-Schema.txt");
		        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Delete Style from own list API response", 
		        		CollectionUtils.isEmpty(missingNodeList));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		//Add Style To List Complete Flow
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID) throws JSONException
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, listId, StyleID, listType);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",listId,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeGetUserListsService(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			int listCount = JsonPath.read(response, "$.data.listCount");
			boolean styleAvailableinList = false;
						
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].id").equals(listId))
				{
					JSONArray styleArray = JsonPath.read(response,"$.data.lists["+i+"].styleIds");
					int StyleCount = styleArray.size();
					System.out.println(" Total Style Count : "+StyleCount);
					for(int j=0; j<StyleCount; j++)
					{
						if(JsonPath.read(response,"$.data.lists["+i+"].styleIds["+j+"]").toString().equals(StyleID))
						{
							styleAvailableinList=true;
						}
					}
				}
			}			
			
			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working - Added style is not available",true, styleAvailableinList);
		}

		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToWishList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID) throws JSONException
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			
			int listCount = JsonPath.read(response, "$.data.listCount");
			String wishlistID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					wishlistID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
					System.out.println("Wish List ID Found : "+wishlistID);
				}
			}
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, wishlistID, StyleID, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",wishlistID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeGetUserListsService(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			boolean styleAvailableinList = false;
						
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].id").equals(wishlistID))
				{
					
					JSONArray styleArray = JsonPath.read(response,"$.data.lists["+i+"].styleIds");
					int StyleCount = styleArray.size();
					System.out.println(" Total Style Count : "+StyleCount);
					for(int j=0; j<StyleCount; j++)
					{
						if(JsonPath.read(response,"$.data.lists["+i+"].styleIds["+j+"]").toString().equals(StyleID))
						{
							styleAvailableinList=true;
						}
					}
				}
			}			
			
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working - Added style is not available",true, styleAvailableinList);
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_AddStyleToOwnList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID) throws JSONException
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
				}
			}
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",OwnListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeGetUserListsService(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			boolean styleAvailableinList = false;
						
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].id").equals(OwnListID))
				{
					JSONArray styleArray = JsonPath.read(response,"$.data.lists["+i+"].styleIds");
					int StyleCount = styleArray.size();
					System.out.println(" Total Style Count : "+StyleCount);
					for(int j=0; j<StyleCount; j++)
					{
						if(JsonPath.read(response,"$.data.lists["+i+"].styleIds["+j+"]").toString().equals(StyleID))
						{
							styleAvailableinList=true;
						}
					}
				}
			}			
			
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working - Added style is not available",true, styleAvailableinList);
		}

		//Get List By ID
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "getUserLists")
		public void Lists_GetListByID_VerifySuccess(String userName, String password)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			String ListID = JsonPath.read(response,"$.data.lists[0].id").toString();
			
			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, ListID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List By ID Response", response);
			AssertJUnit.assertEquals("Get List By ID API is not working",200,request.response.getStatus());
		
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "getUserLists")
		public void Lists_GetListByID_VerifyData(String userName, String password)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			String ListID = JsonPath.read(response,"$.data.lists[0].id").toString();
			
			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, ListID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List By ID Response", response);
			AssertJUnit.assertEquals("Get List By ID API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("Get List By ID API is not working - List ID is not matching",ListID,JsonPath.read(response, "$.data.id"));

		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "getUserLists")
		public void Lists_GetListByID_VerifySchema(String userName, String password)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			String ListID = JsonPath.read(response,"$.data.lists[0].id").toString();
			
			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, ListID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List By ID Response", response);
			AssertJUnit.assertEquals("Get List By ID API is not working",200,request.response.getStatus());
			AssertJUnit.assertEquals("Get List By ID API is not working - List ID is not matching",ListID,JsonPath.read(response, "$.data.id"));

			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-getListById-Schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Get User Lists API response", 
	            		CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "ModifyListAccess")
		public void Lists_ModifyListAccess_VerifySuccess(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2,String Access2)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeModifyListAccessService(userName, password, listId, Access2);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Modify Access Response",response);
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working.", 200, request.response.getStatus());
			
			
			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			
		} 

		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "ModifyListAccess")
		public void Lists_ModifyListAccess_VerifyData(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2,String Access2)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeModifyListAccessService(userName, password, listId, Access2);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Modify Access Response",response);
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working.", 200, request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - List ID is not matching", listId, JsonPath.read(response,"$.data.listID"));
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - List Access is not matching", Access2, JsonPath.read(response,"$.data.access"));
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - Status code is not matching", 200, Integer.parseInt(JsonPath.read(response,"$.meta.code")));

			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
			
		} 
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "ModifyListAccess")
		public void Lists_ModifyListAccess_VerifySchema(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2,String Access2)
		{
			RequestGenerator request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
			
			request = listsServiceHelper.invokeModifyListAccessService(userName, password, listId, Access2);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Modify Access Response",response);
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working.", 200, request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - List ID is not matching", listId, JsonPath.read(response,"$.data.listID"));
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - List Access is not matching", Access2, JsonPath.read(response,"$.data.access"));
			AssertJUnit.assertEquals("LGA Lists - Modify Access API is not working. - Status code is not matching", 200, Integer.parseInt(JsonPath.read(response,"$.meta.code")));
			
			try {
	            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lists-modifyListAccess-Schema.txt");
	            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
	            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in Get User Lists API response", 
	            		CollectionUtils.isEmpty(missingNodeList));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		} 
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "RemoveStylefromDefaultList")
		public void Lists_RemoveStyleFromWishList_CompleteFlow(String userName, String password, String StyleID, String StyleID1) throws JSONException
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			
			int listCount = JsonPath.read(response, "$.data.listCount");
			String wishlistID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("wishlist"))
				{
					wishlistID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
					System.out.println("Wish List ID Found : "+wishlistID);
				}
			}
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, wishlistID, StyleID, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",wishlistID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));


			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, wishlistID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Lists By ID response : ",response);
			
			int StyleCountinList = JsonPath.read(response, "$.data.count");
			System.out.println("Total Products available in List : "+StyleCountinList);
			String ItemID = null;
			for(int i = 0; i<StyleCountinList;i++)
			{
				System.out.println("Analyzing Products in List at index : "+i);
				if(JsonPath.read(response,"$.data.products["+i+"].styleid").toString().equals(StyleID))
				{
					System.out.println("StyleID Found in List");
					ItemID = JsonPath.read(response,"$.data.products["+i+"].item_id").toString();
					System.out.println("ITEM ID : "+ItemID);
				}
				
			}
			
			AssertJUnit.assertEquals("LGA Lists - Get List By ID API is not working. Unable to find Item ID from response",false,ItemID.isEmpty());
			
			try {
				System.out.println("Delaying Execution for 20 Seconds!!!");
				TimeUnit.SECONDS.sleep(20);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request = listsServiceHelper.invokeRemoveStyleFromListUsingItemIDService(userName, password, StyleID, "wishlist", wishlistID, ItemID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Remove Style From List Response",response);
			
			AssertJUnit.assertEquals("LGA Lists - Remove Style From list API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. List ID is not matching",wishlistID,JsonPath.read(response, "$.data.listID"));
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Product ID is not matching",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Meta Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			try {
				System.out.println("Delaying Execution for 20 Seconds!!!");
				TimeUnit.SECONDS.sleep(20);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, wishlistID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Lists By ID response : ",response);
			boolean StyleRemoved = true;
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Style Count is not updated",StyleCountinList-1,Integer.parseInt(JsonPath.read(response, "$.data.count")));
			StyleCountinList = JsonPath.read(response, "$.data.count");
			for(int i = 0; i<StyleCountinList;i++)
			{
				if(JsonPath.read(response,"$.data.products["+i+"].styleid").toString().equals(StyleID))
				{
					StyleRemoved = false;
				}
				
			}
			
			AssertJUnit.assertEquals("LGA Lists - Remove Style From list API is not working. Removed Style is available in response",true,StyleRemoved);
					
			
		}

		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "AddRemoveStyleToList")
		public void Lists_RemoveStyleFromOwnList_CompleteFlow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2, String StyleID) throws JSONException
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			
			int listCount = JsonPath.read(response, "$.data.listCount");
			String OwnListID = null;
			
			for(int i=0;i<listCount;i++)
			{
				if(JsonPath.read(response, "$.data.lists["+i+"].listType").equals("own_list"))
				{
					OwnListID=JsonPath.read(response, "$.data.lists["+i+"].id").toString();
					System.out.println("\"ITEMS I OWN LIST\" ID Found : "+OwnListID);
				}
			}
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.ADDSTYLETOLIST, OwnListID, StyleID, "wishlist");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("LGA Lists - Tag a List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. List ID is invalid",OwnListID,JsonPath.read(response, "$.data.listID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Style ID is invalid",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Add Style to List API is not working. Status Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));


			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, OwnListID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Lists By ID response : ",response);
			
			int StyleCountinList = JsonPath.read(response, "$.data.count");
			System.out.println("Total Products available in List : "+StyleCountinList);
			
			request = listsServiceHelper.invokeAddRemoveStyleToListService(userName, password, APINAME.DELETESTYLEFROMLIST, OwnListID, StyleID, "own_list");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Remove Style From List Response",response);
			
			AssertJUnit.assertEquals("LGA Lists - Remove Style From list API is not working.",200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. List ID is not matching",OwnListID,JsonPath.read(response, "$.data.listID"));
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Product ID is not matching",StyleID,JsonPath.read(response, "$.data.productID").toString());
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Meta Code is invalid",200,Integer.parseInt(JsonPath.read(response, "$.meta.code")));

			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, OwnListID);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Lists By ID response : ",response);
			boolean StyleRemoved = true;
			AssertJUnit.assertEquals("LGA Lists - Remove Style From List API is not working. Style Count is not updated",StyleCountinList,Integer.parseInt(JsonPath.read(response, "$.data.count")));
			StyleCountinList = JsonPath.read(response, "$.data.count");
			for(int i = 0; i<StyleCountinList;i++)
			{
				if(JsonPath.read(response,"$.data.products["+i+"].styleid").toString().equals(StyleID))
				{
					StyleRemoved = false;
				}
				
			}
			
			AssertJUnit.assertEquals("LGA Lists - Remove Style From list API is not working. Removed Style is available in response",true,StyleRemoved);
					
			
			}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "ModifyListAccess")
		public void Lists_GetProfileCountWithList_Flow(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2,String Access2)
		{
			
			RequestGenerator request = devapiServiceHelper.invokeGetProfileCount(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Profile Count Response : ",response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", 2,Integer.parseInt(JsonPath.read(response, "$.data.collection.count")));
		
			request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			String listId = JsonPath.read(response,"$.data.listID").toString();
		
			request = devapiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Profile Count Response : ",response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", 3,Integer.parseInt(JsonPath.read(response, "$.data.collection.count")));
		
			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());

			request = devapiServiceHelper.invokeGetProfileCount(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get Profile Count Response : ",response);
			AssertJUnit.assertEquals("Get Profile Count API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("Get Profile Count API is not working", 2,Integer.parseInt(JsonPath.read(response, "$.data.collection.count")));
	
		}
		
		@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "EditUserList")
		public void Lists_CreateAndEditList_DataValidation(String userName, String password,String listAccess, String listDescription,String listType, String listName, String style1, String style2, String tag1, String tag2)
		{
			RequestGenerator request = listsServiceHelper.invokeGetUserListsService(userName, password);
			String response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Count is Null",false,JsonPath.read(response, "$.data.listCount").toString().equals(null));
			
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Name is Null",false,JsonPath.read(response, "$.data.lists[0].name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Access is Null",false,JsonPath.read(response, "$.data.lists[0].access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Type is Null",false,JsonPath.read(response, "$.data.lists[0].listType").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] ID is Null",false,JsonPath.read(response, "$.data.lists[0].id").toString().equals(null));

			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Name is Null",false,JsonPath.read(response, "$.data.lists[1].name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Access is Null",false,JsonPath.read(response, "$.data.lists[1].access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Type is Null",false,JsonPath.read(response, "$.data.lists[1].listType").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] ID is Null",false,JsonPath.read(response, "$.data.lists[1].id").toString().equals(null));
			
			request = listsServiceHelper.invokeCreateListService(userName, password, listAccess, listDescription,listType, listName, style1, style2, tag1, tag2);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Create Lists Response : ",response);
			String listId = JsonPath.read(response,"$.data.listID").toString();
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - List name Mismatching",listName, JsonPath.read(response, "$.data.name"));
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag2, JsonPath.read(response, "$.data.tags[0]"));
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Tags are not valid",tag1, JsonPath.read(response, "$.data.tags[1]"));
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Access type is not matching",listAccess, JsonPath.read(response, "$.data.access"));
			AssertJUnit.assertEquals("LGA Lists - Create User List API is not working - Description is not matching",listDescription, JsonPath.read(response, "$.data.description"));
			
			
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Name is Null",false,JsonPath.read(response, "$.data.name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Access is Null",false,JsonPath.read(response, "$.data.access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List ID is Null",false,JsonPath.read(response, "$.data.listID").toString().equals(null));
		
			request = listsServiceHelper.invokeGetUserListsService(userName, password);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - Get User Lists API is not working", 200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid list count","3",JsonPath.read(response, "$.data.listCount").toString());
			AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Status Code","200",JsonPath.read(response, "$.meta.statusCode").toString());
			AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message","SUCCESS",JsonPath.read(response, "$.meta.error").toString());
			AssertJUnit.assertEquals("LGA Lists - Get User List API is returning invalid Message",null,JsonPath.read(response, "$.meta.errorDetail").toString());

			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Name is Null",false,JsonPath.read(response, "$.data.lists[0].name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Access is Null",false,JsonPath.read(response, "$.data.lists[0].access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] Type is Null",false,JsonPath.read(response, "$.data.lists[0].listType").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[0] ID is Null",false,JsonPath.read(response, "$.data.lists[0].id").toString().equals(null));

			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Name is Null",false,JsonPath.read(response, "$.data.lists[1].name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Access is Null",false,JsonPath.read(response, "$.data.lists[1].access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] Type is Null",false,JsonPath.read(response, "$.data.lists[1].listType").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[1] ID is Null",false,JsonPath.read(response, "$.data.lists[1].id").toString().equals(null));

			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[2] Name is Null",false,JsonPath.read(response, "$.data.lists[1].name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[2] Access is Null",false,JsonPath.read(response, "$.data.lists[1].access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[2] Type is Null",false,JsonPath.read(response, "$.data.lists[1].listType").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List[2] ID is Null",false,JsonPath.read(response, "$.data.lists[1].id").toString().equals(null));

			request = listsServiceHelper.invokeEditListService(userName, password, "public", "Edited Description", listId, "Edited Name");
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Edit List Response : ", response);
			AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
			AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - List Id Mismatching",listId, JsonPath.read(response, "$.data.listID"));
			AssertJUnit.assertEquals("LGA Lists - Edit User List API is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
			
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Name is Null",false,JsonPath.read(response, "$.data.name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Access is Null",false,JsonPath.read(response, "$.data.access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List ID is Null",false,JsonPath.read(response, "$.data.listID").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - User ID is Null",false,JsonPath.read(response, "$.data.userID").toString().equals(null));

			
			request = listsServiceHelper.invokeGetUserListsByIdService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("Get User List By Id Response : ",response);
			AssertJUnit.assertEquals("LGA Lists - GET User List By ID API is not working.", 200,request.response.getStatus());
			AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - List name Mismatching","Edited Name", JsonPath.read(response, "$.data.name"));
			AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - Access type is not matching","public", JsonPath.read(response, "$.data.access"));
			AssertJUnit.assertEquals("LGA Lists - GET User List By IDAPI is not working - Description is not matching","Edited Description", JsonPath.read(response, "$.data.description"));

			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Name is Null",false,JsonPath.read(response, "$.data.name").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Access is Null",false,JsonPath.read(response, "$.data.access").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List ID is Null",false,JsonPath.read(response, "$.data.id").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List URL is Null",false,JsonPath.read(response, "$.data.link").toString().equals(null));
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - List Type is Null",false,JsonPath.read(response, "$.data.listType").toString().equals(null));

			request = listsServiceHelper.invokeDeleteListService(userName, password, listId);
			response = request.respvalidate.returnresponseasstring();
			printAndLog("[Tear Down] - Delete List Response : ",response);
			AssertJUnit.assertEquals("Tear Down Activity Error - Delete List API is not working.",200,request.response.getStatus());
		
			AssertJUnit.assertEquals("LGA Lists - Data Validation Error - Success Message is Null",false,JsonPath.read(response, "$.data").toString().equals(null));
		
			
			
		}

}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

