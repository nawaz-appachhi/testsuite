package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.dataproviders.LGPLooksAPIsDataProvider;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceDataNodes;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.testng.annotations.Test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.testng.AssertJUnit;


import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class LGPLooksAPIs extends LGPLooksAPIsDataProvider
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LGPLooksAPIs.class);
	static APIUtilities apiUtil = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	static String lookId;
	WebTarget target;
	ClientConfig config;
	Client client;
	Invocation.Builder invBuilder;
    
			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_getAllStickers_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getAllStickers_verifySuccessResponse(String userName, String password)
			{
			RequestGenerator LGPLooksAPILooksGetAllStickersReqGen = devApiServiceHelper.invokeDevApiGetAllStickers(userName, password);
			String LGPLooksAPILooksGetAllStickersResponse = LGPLooksAPILooksGetAllStickersReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetAllStickersResponse+"\n");
			log.info("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetAllStickersResponse+"\n");

			AssertJUnit.assertEquals("[LGP API Looks Service getAllStickers API is not working.]", 200, LGPLooksAPILooksGetAllStickersReqGen.response.getStatus());

			}

			@Test(groups = { "SchemaValidation" }, dataProvider = "LGPLooksAPIs_looks_getAllStickers_verifyResponseUsingSchemaValidation")
			public void LGPLooksAPIs_looks_getAllStickers_verifyResponseUsingSchemaValidation(String userName, String password)
			{
			RequestGenerator LGPLooksAPILooksGetAllStickersReqGen = devApiServiceHelper.invokeDevApiGetAllStickers(userName, password);
			String LGPLooksAPILooksGetAllStickersResponse = LGPLooksAPILooksGetAllStickersReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetAllStickersResponse+"\n");
			log.info("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetAllStickersResponse+"\n");

			AssertJUnit.assertEquals("[LGP API Looks Service getAllStickers API is not working.]", 200, LGPLooksAPILooksGetAllStickersReqGen.response.getStatus());

			try {
			    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getallstickers-schema.txt");
			    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPILooksGetAllStickersResponse, jsonschema);
			    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getAllStickers API response",CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_getLookDetails_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getLookDetails_verifySuccessResponse(String userName, String password, String lookId)
			{
			RequestGenerator LGPLooksAPILooksGetLookDetailsReqGen = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKDETAILS, lookId);
			String LGPLooksAPILooksGetLookDetailsResponse = LGPLooksAPILooksGetLookDetailsReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting LGP API Looks Service getLookDetails API response :\n\n"+LGPLooksAPILooksGetLookDetailsResponse+"\n");
			log.info("\nPrinting LGPLooksAPIService getLookDetails API response :\n\n"+LGPLooksAPILooksGetLookDetailsResponse+"\n");

			AssertJUnit.assertEquals("[LGP API Looks Service getAllStickers API is not working.]", 200, LGPLooksAPILooksGetLookDetailsReqGen.response.getStatus());

			}

			@Test(groups = { "SchemaValidation" }, dataProvider = "LGPLooksAPIs_looks_getLookDetails_verifyResponseUsingSchemaValidation")
			public void LGPLooksAPIs_looks_getLookDetails_verifyResponseUsingSchemaValidation(String userName, String password, String lookId)
			{
			RequestGenerator LGPLooksAPIs_looks_getLookDetailsReqGen = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKDETAILS, lookId);
			String LGPLooksAPIs_looks_getLookDetailsResponse = LGPLooksAPIs_looks_getLookDetailsReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPIs_looks_getLookDetailsResponse+"\n");
			log.info("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPIs_looks_getLookDetailsResponse+"\n");

			AssertJUnit.assertEquals("[LGP API Looks Service getLookDetails API is not working.]", 200, LGPLooksAPIs_looks_getLookDetailsReqGen.response.getStatus());

			try {
			    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getlookdetails-schema.txt");
			    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPIs_looks_getLookDetailsResponse, jsonschema);
			    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getLookDetails API response",CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_getAllStickers_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getLooksForUser_verifySuccessResponse(String userName, String password)
			{
			RequestGenerator LGPLooksAPILooksGetLooksForUserReqGen = devApiServiceHelper.invokeDevApiGetLooksForUser(userName, password);
			String LGPLooksAPILooksGetLooksForUserResponse = LGPLooksAPILooksGetLooksForUserReqGen.respvalidate.returnresponseasstring();
			System.out.println("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetLooksForUserResponse+"\n");
			log.info("\nPrinting LGP API Looks Service getAllStickers API response :\n\n"+LGPLooksAPILooksGetLooksForUserResponse+"\n");

			AssertJUnit.assertEquals("[LGP API Looks Service getLooksForUser API is not working.]", 200, LGPLooksAPILooksGetLooksForUserReqGen.response.getStatus());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLook_verifySuccessResponse")
			public void LGPLooksAPIs_looks_createLook_verifySuccessResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 200, LGPLooksAPILooksCreateLookRequest.response.getStatus());
			}

			@Test(groups = {"SchemaValidation"}, dataProvider= "LGPLooksAPIs_looks_createLook_VerifySuccess_SchemaValidation")
			public void LGPLooksAPIs_looks_createLook_verifySuccessResponseSchemaValidation(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not Working.]", 200, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				
				try {
				    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-createlook-schema.txt");
				    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPILooksCreateLookResponse, jsonschema);
				    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getLookDetails API response",CollectionUtils.isEmpty(missingNodeList));
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
			}

			@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider= "LGPLooksAPIs_looks_createLook_VerifyDataTagNodes")
			public void LGPLooksAPIs_looks_createLook_verifyDataTagNodes(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not Working.]", 200, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				
				List<String> createLookDataTagNodes = DevApiServiceDataNodes.getCreateLookDataTagNodes();
				for(String DataNode : createLookDataTagNodes)
				{
					AssertJUnit.assertTrue("["+DataNode+"] does not exist", LGPLooksAPILooksCreateLookRequest.respvalidate.DoesNodeExists(DataNode));
				}
				lookId=DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_LOOKID.toString();
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_TITLE.toString()+"] Node Value is Invalid", title.trim(),LGPLooksAPILooksCreateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_TITLE.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString()+"] Node Value is Invalid", description.trim(),LGPLooksAPILooksCreateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISACTIVE.toString()+"] Node Value is Invalid", isActive.trim(),LGPLooksAPILooksCreateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISACTIVE.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISDRAFT.toString()+"] Node Value is Invalid", isDraft.trim(),LGPLooksAPILooksCreateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISDRAFT.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISREMYX.toString()+"] Node Value is Invalid", isRemyx.trim(),LGPLooksAPILooksCreateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISREMYX.toString(),false));
				
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLookAsDraft_verifySuccessResponse")
			public void LGPLooksAPIs_looks_createLookAsDraft_verifySuccessResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service createLookAsDraft API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service createLookAsDraft API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 200, LGPLooksAPILooksCreateLookRequest.response.getStatus());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLookAsRemyx_verifyFailureResponse")
			public void LGPLooksAPIs_looks_createLookAsRemyx_verifyFailureResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service createLookAsRemyx API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service createLookAsRemyx API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 500, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				AssertJUnit.assertEquals("[DecApiLooksService Create Look API is not working.]", "For remyx look, parentId must be supplied", JsonPath.read(LGPLooksAPILooksCreateLookResponse, "$.data.errorDetail").toString().trim());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLookWithoutIsActive_verifyFailureResponse")
			public void LGPLooksAPIs_looks_createLookWithoutIsActive_verifyFailureResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service createLook without IsActive Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service createLook without IsActive Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 500, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				AssertJUnit.assertEquals("[DecApiLooksService Create Look API is not working.]", "IsActive, IsDraft, IsRemyx, creatorId, detailJson are mandatory fields", JsonPath.read(LGPLooksAPILooksCreateLookResponse, "$.data.errorDetail").toString().trim());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLookWithoutIsDraft_verifyFailureResponse")
			public void LGPLooksAPIs_looks_createLookWithoutIsDraft_verifyFailureResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service createLook without IsDraft Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service createLook without IsDraft Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 500, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				AssertJUnit.assertEquals("[DecApiLooksService Create Look API is not working.]", "IsActive, IsDraft, IsRemyx, creatorId, detailJson are mandatory fields", JsonPath.read(LGPLooksAPILooksCreateLookResponse, "$.data.errorDetail").toString().trim());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLookWithoutIsDraft_verifyFailureResponse")
			public void LGPLooksAPIs_looks_createLookWithoutIsRemyx_verifyFailureResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksCreateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksCreateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service createLook without IsRemyx Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service createLook without IsRemyx Field API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 500, LGPLooksAPILooksCreateLookRequest.response.getStatus());
				AssertJUnit.assertEquals("[DecApiLooksService Create Look API is not working.]", "IsActive, IsDraft, IsRemyx, creatorId, detailJson are mandatory fields", JsonPath.read(LGPLooksAPILooksCreateLookResponse, "$.data.errorDetail").toString().trim());
			}

			@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "LGPLooksAPIs_looks_createLook_verifySuccessResponse")
			public void LGPLooksAPIs_looks_UpdateLook_verifySuccessResponse(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksUpdateLookRequest = devApiServiceHelper.invokeDevApiCreateLook(userName, password, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksCreateLookResponse = LGPLooksAPILooksUpdateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				log.info("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksCreateLookResponse);
				
				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not working.]", 200, LGPLooksAPILooksUpdateLookRequest.response.getStatus());
			}

			@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider= "LGPLooksAPIs_looks_UpdateLook_verifyDataTagNodes")
			public void LGPLooksAPIs_looks_UpdateLook_verifyDataTagNodes(String userName, String password, String title, String description, String isActive, String isDraft, String isRemyx)
			{
				RequestGenerator LGPLooksAPILooksUpdateLookRequest = devApiServiceHelper.invokeDevApiUpdateLook(userName, password, lookId, title, description, isActive, isDraft, isRemyx);
				String LGPLooksAPILooksUpdateLookResponse = LGPLooksAPILooksUpdateLookRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksUpdateLookResponse);
				log.info("\nPrinting LGP API Looks Service CreateLook API response :\n\n"+LGPLooksAPILooksUpdateLookResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service Create Look API is not Working.]", 200, LGPLooksAPILooksUpdateLookRequest.response.getStatus());
				
				List<String> UpdateLookDataTagNodes = DevApiServiceDataNodes.getCreateLookDataTagNodes();
				for(String DataNode : UpdateLookDataTagNodes)
				{
					AssertJUnit.assertTrue("["+DataNode+"] does not exist", LGPLooksAPILooksUpdateLookRequest.respvalidate.DoesNodeExists(DataNode));
				}
				
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_LOOKID.toString()+"] Node Value is Invalid", lookId.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_TITLE.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_TITLE.toString()+"] Node Value is Invalid", title.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_TITLE.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString()+"] Node Value is Invalid", description.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISACTIVE.toString()+"] Node Value is Invalid", isActive.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISACTIVE.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISDRAFT.toString()+"] Node Value is Invalid", isDraft.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISDRAFT.toString(),false));
				AssertJUnit.assertEquals("["+DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISREMYX.toString()+"] Node Value is Invalid", isRemyx.trim(),LGPLooksAPILooksUpdateLookRequest.respvalidate.NodeValue(DevApiServiceDataNodes.LOOKS_CREATE_RESP_DATA_ISREMYX.toString(),false));
			}

			@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider= "LGPLooksAPIs_looks_getLooksByOccasion_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getLooksByOccasion_verifySuccessResponse(String userName, String password, String occasionId)
			{
				RequestGenerator LGPLooksAPIsGetLooksByOccasionRequest = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKDETAILS, occasionId);
				String LGPLooksAPIsGetLooksByOccasionResponse = LGPLooksAPIsGetLooksByOccasionRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service GetLooksByOccasion API Response :\n\n"+LGPLooksAPIsGetLooksByOccasionResponse);
				log.info("\nPrinting LGP API Looks Service GetLooksByOccasion API Response :\n\n"+LGPLooksAPIsGetLooksByOccasionResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service getLooksByOccasion API is not working.]",200,LGPLooksAPIsGetLooksByOccasionRequest.response.getStatus());
			}

			@Test(groups = { "SchemaValidation" }, dataProvider= "LGPLooksAPIs_looks_getLooksByOccasion_verifySchema")
			public void LGPLooksAPIs_looks_getLooksByOccasion_verifySchema(String userName, String password, String occasionId)
			{
				RequestGenerator LGPLooksAPIsGetLooksByOccasionRequest = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKSBYOCCASION, occasionId);
				String LGPLooksAPIsGetLooksByOccasionResponse = LGPLooksAPIsGetLooksByOccasionRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service GetLooksByOccasion API Response :\n\n"+LGPLooksAPIsGetLooksByOccasionResponse);
				log.info("\nPrinting LGP API Looks Service GetLooksByOccasion API Response :\n\n"+LGPLooksAPIsGetLooksByOccasionResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service GetLooksByOccasion API is not working.]",200,LGPLooksAPIsGetLooksByOccasionRequest.response.getStatus());
				try {
				    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getlooksbyoccasion-schema.txt");
				    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPIsGetLooksByOccasionResponse, jsonschema);
				    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getLooksByOccasion API response",CollectionUtils.isEmpty(missingNodeList));
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				
			}

			@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider= "LGPLooksAPIs_looks_getLooksByRemyx_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getLooksByRemyx_verifySuccessResponse(String userName, String password, String remyxId)
			{
				RequestGenerator LGPLooksAPIsGetLooksByRemyxRequest = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKSBYREMYX, remyxId);
				String LGPLooksAPIsGetLooksByRemyxResponse = LGPLooksAPIsGetLooksByRemyxRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service GetLooksByRemyx API Response :\n\n"+LGPLooksAPIsGetLooksByRemyxResponse);
				log.info("\nPrinting LGP API Looks Service GetLooksByRemyx API Response :\n\n"+LGPLooksAPIsGetLooksByRemyxResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service getLooksByRemyx API is not working.]",200,LGPLooksAPIsGetLooksByRemyxRequest.response.getStatus());
			}

			@Test(groups = { "SchemaValidation" }, dataProvider= "LGPLooksAPIs_looks_getLooksByRemyx_verifySchema")
			public void LGPLooksAPIs_looks_getLooksByRemyx_verifySchema(String userName, String password, String RemyxId)
			{
				RequestGenerator LGPLooksAPIsGetLooksByRemyxRequest = devApiServiceHelper.invokeDevApiGetLookDetails(userName, password, APINAME.GETLOOKDETAILS, RemyxId);
				String LGPLooksAPIsGetLooksByRemyxResponse = LGPLooksAPIsGetLooksByRemyxRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service getLooksByRemyx API Response :\n\n"+LGPLooksAPIsGetLooksByRemyxResponse);
				log.info("\nPrinting LGP API Looks Service getLooksByRemyx API Response :\n\n"+LGPLooksAPIsGetLooksByRemyxResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service getLooksByRemyx API is not working.]",200,LGPLooksAPIsGetLooksByRemyxRequest.response.getStatus());
				try {
				    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getlooksbyremyx-schema.txt");
				    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPIsGetLooksByRemyxResponse, jsonschema);
				    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getLooksByRemyx API response",CollectionUtils.isEmpty(missingNodeList));
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}

			@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, dataProvider= "LGPLooksAPIs_looks_getallOccasions_verifySuccessResponse")
			public void LGPLooksAPIs_looks_getallOccasions_verifySuccessResponse(String userName, String password)
			{
				RequestGenerator LGPLooksAPIsGetAllOccasionsRequest = devApiServiceHelper.invokeDevApiGetAllOccasions(userName, password);
				String LGPLooksAPIsGetAllOccassionsResponse = LGPLooksAPIsGetAllOccasionsRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service getAllOccasions API Response :\n\n"+LGPLooksAPIsGetAllOccassionsResponse);
				log.info("\nPrinting LGP API Looks Service getAllOccasions API Response :\n\n"+LGPLooksAPIsGetAllOccassionsResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service getAllOccasions API is not working.]",200,LGPLooksAPIsGetAllOccasionsRequest.response.getStatus() );
			}

			@Test(groups = { "SchemaValidation" }, dataProvider= "LGPLooksAPIs_looks_getAllOccasions_verifySchema")
			public void LGPLooksAPIs_looks_getAllOccasions_verifySchema(String userName, String password)
			{

				RequestGenerator LGPLooksAPIsGetAllOccasionsRequest = devApiServiceHelper.invokeDevApiGetAllOccasions(userName, password);
				String LGPLooksAPIsGetAllOccassionsResponse = LGPLooksAPIsGetAllOccasionsRequest.respvalidate.returnresponseasstring();
				System.out.println("\nPrinting LGP API Looks Service getAllOccasions API Response :\n\n"+LGPLooksAPIsGetAllOccassionsResponse);
				log.info("\nPrinting LGP API Looks Service getAllOccasions API Response :\n\n"+LGPLooksAPIsGetAllOccassionsResponse);

				AssertJUnit.assertEquals("[LGP API Looks Service getAllOccasions API is not working.]",200,LGPLooksAPIsGetAllOccasionsRequest.response.getStatus() );
				
				try {
				    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapiservice-getalloccasions-schema.txt");
				    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(LGPLooksAPIsGetAllOccassionsResponse, jsonschema);
				    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP API Looks Service getAllOccasions API response",CollectionUtils.isEmpty(missingNodeList));
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}

	  
  }

