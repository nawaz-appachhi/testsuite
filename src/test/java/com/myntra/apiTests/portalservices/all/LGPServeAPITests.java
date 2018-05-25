package com.myntra.apiTests.portalservices.all;

import java.util.List;

import com.myntra.apiTests.dataproviders.LgpServicesDP;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class LGPServeAPITests extends LgpServicesDP {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LGPServeAPITests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHepler=new LgpServicesHelper();
	String xID;
	String env;
	
  //Test Cases LGPSERVE-ACTIONS
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_ActionDP")
  public void lgpServe_Action_verifySuccessResponse(String username, String password) 
  {
	  RequestGenerator ActionRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.ACTION);
	  String ActionResponse = ActionRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Action API response :\n\n"+ActionResponse+"\n");
	  log.info("\nPrinting LGP Serve Action API response :\n\n"+ActionResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Post Action API is not working]", 200, ActionRequest.response.getStatus());  
  }
  
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_BulkActionsDP")
  public void lgpServe_BulkActions_verifySuccessResponse(String username, String password) 
  {
	  RequestGenerator BulkActionsRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.BULKACTIONS);
	  String BulkActionResponse = BulkActionsRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve BulkActions API response :\n\n"+BulkActionResponse+"\n");
	  log.info("\nPrinting LGP Serve BulkActions API response :\n\n"+BulkActionResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Post BulkActions API is not working]", 200, BulkActionsRequest.response.getStatus());

  }
  
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_ActionFollowDP")
  public void lgpServe_ActionFollow_verifySuccessResponse(String username, String password) 
  {
	  RequestGenerator FollowActionRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.FOLLOW);
	  String FollowActionResponse = FollowActionRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Follow Action API response :\n\n"+FollowActionResponse+"\n");
	  log.info("\nPrinting LGP Serve Follow Action API response :\n\n"+FollowActionResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Follow Action API is not working]", 200, FollowActionRequest.response.getStatus());

	  
  }
  
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_ActionUnFollowDP")
  public void lgpServe_ActionUnFollow_verifySuccessResponse(String username, String password) 
  {
	  RequestGenerator UnFollowActionRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.UNFOLLOW);
	  String UnFollowActionResponse = UnFollowActionRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve UnFollow Action API response :\n\n"+UnFollowActionResponse+"\n");
	  log.info("\nPrinting LGP Serve UnFollow Action API response :\n\n"+UnFollowActionResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve UnFollow Action API is not working]", 200, UnFollowActionRequest.response.getStatus());

  }
  
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_ActionFollowAllDP")
  public void lgpServe_ActionFollowAll_verifySuccessResponse(String username, String password) {
	  
	  RequestGenerator FollowAllActionRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.FOLLOWALL);
	  String FollowAllActionResponse = FollowAllActionRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve FollowAll Action API response :\n\n"+FollowAllActionResponse+"\n");
	  log.info("\nPrinting LGP Serve FollowAll Action API response :\n\n"+FollowAllActionResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve FollowAll Action API is not working]", 200, FollowAllActionRequest.response.getStatus());

  }
  
//Test Cases LGPSERVE-FEED
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetFeedDP")
public void lgpServe_getFeed_verifySuccessResponse(String username, String password) {
	  
	  RequestGenerator GetFeedRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETFEED);
	  String GetFeedResponse = GetFeedRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve GetFeed API response :\n\n"+GetFeedResponse+"\n");
	  log.info("\nPrinting LGP Serve GetFeed API response :\n\n"+GetFeedResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve GetFeed API is not working]", 200, GetFeedRequest.response.getStatus());

}
  
  @Test(groups = { "Schema Validation" }, dataProvider = "lgpServe_GetFeedDP")
  public void lgpServe_getFeed_verifySuccessResponseSchema(String username, String password)
  {
	  RequestGenerator GetFeedRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETFEED);
	  String GetFeedResponse = GetFeedRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve GetFeed API response :\n\n"+GetFeedResponse+"\n");
	  log.info("\nPrinting LGP Serve GetFeed API response :\n\n"+GetFeedResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve GetFeed API is not working]", 200, GetFeedRequest.response.getStatus());
	  
	  try {
		    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserve-getfeed-schemaset.txt");
		    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(GetFeedResponse, jsonschema);
		    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP Serve GetFeed API response",CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

    
  
  @Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetObjectDP")
public void lgpServe_getObject_verifySuccessResponse(String username, String password) {
	  
	  RequestGenerator GetObjectRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETOBJECT);
	  String GetObjectResponse = GetObjectRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve GetObject API response :\n\n"+GetObjectResponse+"\n");
	  log.info("\nPrinting LGP Serve GetObject API response :\n\n"+GetObjectResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve GetObject API is not working]", 200, GetObjectRequest.response.getStatus());

}
  
  @Test(groups = { "Schema Validation" }, dataProvider = "lgpServe_GetObjectDP")
  public void lgpServe_getObject_verifySuccessResponseSchema(String username, String password)
  {
	  RequestGenerator GetObjectRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETOBJECT);
	  String GetObjectResponse = GetObjectRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve GetObject API response :\n\n"+GetObjectResponse+"\n");
	  log.info("\nPrinting LGP Serve GetObject API response :\n\n"+GetObjectResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve GetObject API is not working]", 200, GetObjectRequest.response.getStatus());
	  
	  try {
		    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserve-getobject-schemaset.txt");
		    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(GetObjectResponse, jsonschema);
		    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP Serve GetObject API response",CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
  
  //LGP SERVE - USER Test cases
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetUIDX_DP")
  	public void lgpServe_getUIDX_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetUIDXRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETUIDX);
	  String GetUIDXResponse = GetUIDXRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve GetUIDX for PPID API response :\n\n"+GetUIDXResponse+"\n");
	  log.info("\nPrinting LGP Serve GetUIDX for PPID API response :\n\n"+GetUIDXResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve GetUIDX for PPID API is not working]", 200, GetUIDXRequest.response.getStatus());
    }
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetUIDX_DP")
  	public void lgpServe_getUIDX_verifyDataTagNodes(String username, String password)
  	{
  	  RequestGenerator GetUIDXRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETUIDX);
  	  String GetUIDXResponse = GetUIDXRequest.respvalidate.returnresponseasstring();
  	  System.out.println("\nPrinting LGP Serve GetUIDX for PPID API response :\n\n"+GetUIDXResponse+"\n");
  	  log.info("\nPrinting LGP Serve GetUIDX for PPID API response :\n\n"+GetUIDXResponse+"\n");
  	  AssertJUnit.assertEquals("[LGP Serve GetUIDX for PPID API is not working]", 200, GetUIDXRequest.response.getStatus());
  	  AssertJUnit.assertTrue("[UDIX] Node does not exist",GetUIDXRequest.respvalidate.DoesNodeExists("udix"));
  	}
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetProfileByUDIX_DP")
  	public void lgpServe_getProfileByUDIX_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetProfileByUDIXRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPROFILE);
	  String GetProfileByUdixResponse = GetProfileByUDIXRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Profile by UDIX ID API response :\n\n"+GetProfileByUdixResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Profile by UDIX ID API response :\n\n"+GetProfileByUdixResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Profile by UDIX ID APIb is not working]", 200, GetProfileByUDIXRequest.response.getStatus());
    }
  	
  	@Test(groups = { "SchemaValidation" }, dataProvider = "lgpServe_GetProfileByUDIX_DP")
  	public void lgpServe_getProfileByUDIX_verifySuccessSchemaValidation(String username, String password) 
  	{
	  RequestGenerator GetProfileByUDIXRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPROFILE);
	  String GetProfileByUdixResponse = GetProfileByUDIXRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Profile by UDIX ID API response :\n\n"+GetProfileByUdixResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Profile by UDIX ID API response :\n\n"+GetProfileByUdixResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Profile by UDIX ID APIb is not working]", 200, GetProfileByUDIXRequest.response.getStatus());
	  try {
		    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserve-getprofilebyudix-schemaset.txt");
		    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(GetProfileByUdixResponse, jsonschema);
		    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP Serve Get Profile by UDIX ID API response",CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	 }
  	
  	
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetProfileByPPID_DP")
  	public void lgpServe_getProfileByPPID_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetProfileByPPIDRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPROFILEBYPPID);
	  String GetProfileByPPIDResponse = GetProfileByPPIDRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Profile by PPID API response :\n\n"+GetProfileByPPIDResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Profile by PPID API response :\n\n"+GetProfileByPPIDResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Profile by PPID APIb is not working]", 200, GetProfileByPPIDRequest.response.getStatus());
    }
  	
  	@Test(groups = { "SchemaValidation" }, dataProvider = "lgpServe_GetProfileByPPID_DP")
  	public void lgpServe_getProfileByPPID_verifySuccessSchemaValidation(String username, String password) 
  	{
	  RequestGenerator GetProfileByPPIDRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPROFILEBYPPID);
	  String GetProfileByPPIDResponse = GetProfileByPPIDRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Profile by PPID API response :\n\n"+GetProfileByPPIDResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Profile by PPID API response :\n\n"+GetProfileByPPIDResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Profile by PPID API is not working]", 200, GetProfileByPPIDRequest.response.getStatus());
	  try {
		    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserve-getprofilebyppid-schemaset.txt");
		    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(GetProfileByPPIDResponse, jsonschema);
		    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP Serve Get Profile by PPID ID API response",CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	 }

  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetPrivateProfileDP")
  	public void lgpServe_getPrivateProfile_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetPrivateProfileRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPRIVATEPROFILE);
	  String GetPrivateProfileResponse = GetPrivateProfileRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get PrivateProfile API response :\n\n"+GetPrivateProfileResponse+"\n");
	  log.info("\nPrinting LGP Serve Get PrivateProfile API response :\n\n"+GetPrivateProfileResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get PrivateProfile API is not working]", 200, GetPrivateProfileRequest.response.getStatus());
    }
  	
  	@Test(groups = { "SchemaValidation" }, dataProvider = "lgpServe_GetPrivateProfileDP")
  	public void lgpServe_getPrivateProfile_verifySuccessSchemaValidation(String username, String password) 
  	{
	  RequestGenerator GetPrivateProfileRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETPRIVATEPROFILE);
	  String GetPrivateProfileResponse = GetPrivateProfileRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get PrivateProfile API response :\n\n"+GetPrivateProfileResponse+"\n");
	  log.info("\nPrinting LGP Serve Get PrivateProfile API response :\n\n"+GetPrivateProfileResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get PrivateProfile API is not working]", 200, GetPrivateProfileRequest.response.getStatus());
	  try {
		    String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserve-getprivateprofile-schemaset.txt");
		    List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(GetPrivateProfileResponse, jsonschema);
		    AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LGP Serve Get PrivateProfile API response",CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	 }
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetFollowing_DP")
  	public void lgpServe_getFollowing_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetFollowingRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETFOLLOWING);
	  String GetFollowingResponse = GetFollowingRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Following API response :\n\n"+GetFollowingResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Following API response :\n\n"+GetFollowingResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Following API is not working]", 200, GetFollowingRequest.response.getStatus());
    }
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetFollowers_DP")
  	public void lgpServe_getFollowers_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetFollowersRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETFOLLOWERS);
	  String GetFollowersResponse = GetFollowersRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Following API response :\n\n"+GetFollowersResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Following API response :\n\n"+GetFollowersResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Following API is not working]", 200, GetFollowersRequest.response.getStatus());
    }
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetInfluencers_DP")
  	public void lgpServe_getInfluencers_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetInfluencersRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETINFLUENCERS);
	  String GetInfluencersResponse = GetInfluencersRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Influencers API response :\n\n"+GetInfluencersResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Influencers API response :\n\n"+GetInfluencersResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Influencers API is not working]", 200, GetInfluencersRequest.response.getStatus());
    }
  	
  	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "lgpServe_GetContacts_DP")
  	public void lgpServe_getContacts_verifySuccessResponse(String username, String password) 
  	{
	  RequestGenerator GetContactsRequest = lgpServiceHepler.invokeLGPServeActions(username, password, APINAME.GETINFLUENCERS);
	  String GetContactsResponse = GetContactsRequest.respvalidate.returnresponseasstring();
	  System.out.println("\nPrinting LGP Serve Get Contacts API response :\n\n"+GetContactsResponse+"\n");
	  log.info("\nPrinting LGP Serve Get Contacts API response :\n\n"+GetContactsResponse+"\n");
	  AssertJUnit.assertEquals("[LGP Serve Get Contacts API is not working]", 200, GetContactsRequest.response.getStatus());
    }
}
