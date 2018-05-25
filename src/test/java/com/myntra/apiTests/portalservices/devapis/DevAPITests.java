package com.myntra.apiTests.portalservices.devapis;

import java.io.IOException;
import java.util.List;

import com.myntra.apiTests.dataproviders.devapis.GeneralDevAPITestsDP;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.devapiservice.DevAPICommonMethods;
import com.myntra.apiTests.portalservices.devapiservice.GeneralDevAPITestsHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import net.minidev.json.JSONArray;

public class DevAPITests extends GeneralDevAPITestsDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevAPITests.class);
	static GeneralDevAPITestsHelper Helper = new GeneralDevAPITestsHelper();
	static DevAPICommonMethods DevAPIHelper = new DevAPICommonMethods();
	//Navigation Menu Config Tests
	
	//Verify Android Left Nav
	@Test(groups={"Sanity", "Regression"},description="Android Left Navigation Items. Verify Response Code")
	public void DevAPI_Nav_AndroidLeftNav_VerifySuccess()
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVLEFT);
		System.out.println("Get Android Left Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Left Nav API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},description="Android Left Navigation Items. Response Node Validations")
	public void DevAPI_Nav_AndroidLeftNav_VerifyNodes() throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVLEFT);
		System.out.println("Get Android Left Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Left Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
			
	}
	
	@Test(groups={"SchemaValidation"},description="Android Left Navigation Items. Response Schema Validations")
	public void DevAPI_Nav_AndroidLeftNav_VerifySchema() throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVLEFT);
		System.out.println("Get Android Left Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Left Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Nav_Android_LeftNav_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Nav API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	//Verify Android Right Nav For Guest Users
	@Test(groups={"Sanity","Regression"},description="Android Guest Right Navigation Items. Verify Response Code")
	public void DevAPI_Nav_AndroidRightGuestNav_VerifySuccess()
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTGUEST);
		System.out.println("Get Android Guest Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Guest Right Nav API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},description="Android Guest Right Navigation Items. Response Node Validations")
	public void DevAPI_Nav_AndroidRightGuestNav_VerifyNodes() throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTGUEST);
		System.out.println("Get Android Guest Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Guest Right Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
			
	}
	
	@Test(groups={"SchemaValidation"},description="Android Guest Right Navigation Items. Response Schema Validations")
	public void DevAPI_Nav_AndroidRightGuestNav_VerifySchema() throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getGuestNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTGUEST);
		System.out.println("Get Android Guest Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Guest Right Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Nav_Android_RightGuestNav_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Nav API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	//Verify Android Right Nav For Guest Users
	@Test(groups={"Sanity","Regression"},dataProvider="DevAPI_Nav_AndroidRightNav_User_DP",description="Android User Right Navigation Items. Verify Response Code")
	public void DevAPI_Nav_AndroidRightUserNav_VerifySuccess(String email, String password)
	{
		RequestGenerator request = Helper.getUserNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTUSER, email, password);
		System.out.println("Get Android Guest Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Guest Right Nav API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_Nav_AndroidRightNav_User_DP",description="Android User Right Navigation Items. Response Node Validations")
	public void DevAPI_Nav_AndroidRightUserNav_VerifyNodes(String email, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getUserNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTUSER, email, password);
		System.out.println("Get Android User Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android User Right Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
			
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Nav_AndroidRightNav_User_DP",description="Android User Right Navigation Items. Response Schema Validations")
	public void DevAPI_Nav_AndroidRightUserNav_VerifySchema(String email, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getUserNavMenuConfig(APINAME.DEVAPIGETANDROIDNAVRIGHTUSER, email, password);
		System.out.println("Get Android User Right Nav Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android User Right Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Nav_Android_RightUserNav_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Nav API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	//Android Layouts
	/*@Test(groups={"Sanity","Regression"}, dataProvider="DevAPI_Android_GetLayouts_DP",description="Get Android Layouts Items. Verify Response Code")
	public void DevAPI_AndroidLayout_VerifySuccess(String LayoutName)
	{
		RequestGenerator request = Helper.getAndroidLayouts(LayoutName);
		System.out.println("Get Android Layout Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Layout API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"},dataProvider="DevAPI_Android_GetLayouts_DP",description="Get Android Layouts Items. Verify Response Nodes")
	public void DevAPI_AndroidLayout_VerifyNodes(String LayoutName) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getAndroidLayouts(LayoutName);
		System.out.println("Get Android Layout Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Layout API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
			
	}
	
	@Test(groups={"SchemaValidation"},dataProvider="DevAPI_Android_GetLayouts_DP",description="Get Android Layouts Items. Verify Response Schema")
	public void DevAPI_Nav_AndroidLayout_VerifySchema(String LayoutName) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getAndroidLayouts(LayoutName);
		System.out.println("Get Android Layout Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Android Left Nav API is down",200,request.response.getStatus());
		String response = request.respvalidate.returnresponseasstring();
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Nav_AndroidLayout_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Nav API response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}*/
	
	//Get User Coupons
	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_Success",description = "Get User Coupons")
	public void DevAPI_GetUserCoupons_VerifySuccess(String username, String password, String useMail)
	{
		RequestGenerator request = Helper.getUserCoupons(username, password, useMail);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
	}

	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_Success",description = "Get User Coupons")
	public void DevAPI_GetUserCoupons_VerifySchema(String username, String password, String useMail)
	{
		RequestGenerator request = Helper.getUserCoupons(username, password, useMail);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
		
		try {
		 	   String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getusercoupons-schema.txt");
		 	   List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		 	   AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApi Serviceability V3 API response",
		 	    CollectionUtils.isEmpty(missingNodeList));
		 	  } catch (Exception e) {
		 	   e.printStackTrace();
		 	  }
	}

	//Get User Coupons
	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_ByEmail",description = "Get User Coupons By Email")
	public void DevAPI_GetUserCouponsByEmail_VerifySuccess(String username, String password, String useMail)
	{
		RequestGenerator request = Helper.getUserCoupons(username, password, useMail);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
	}


	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_ByUIDX",description = "Get User Coupons By UIDX")
	public void DevAPI_GetUserCouponsByUIDX_VerifySuccess(String username, String password, String useMail)
	{
		RequestGenerator request = Helper.getUserCoupons(username, password, useMail);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
	}

	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_Success",description = "Verify Schema of UIDX and Mail Response are same")
	public void DevAPI_GetUserCoupons_CompareSchema(String username, String password, String useMail)
	{
		RequestGenerator request = Helper.getUserCoupons(username, password, "true");
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
		
		try {
		 	   String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getusercoupons-schema.txt");
		 	   List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		 	   AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApi Serviceability V3 API response",
		 	    CollectionUtils.isEmpty(missingNodeList));
		 	  } catch (Exception e) {
		 	   e.printStackTrace();
		 	  }


		request = Helper.getUserCoupons(username, password, "false");
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Get User Coupons Service Response : "+response);
		AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
		
		try {
		 	   String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/devapi-getusercoupons-schema.txt");
		 	   List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		 	   AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApi Serviceability V3 API response",
		 	    CollectionUtils.isEmpty(missingNodeList));
		 	  } catch (Exception e) {
		 	   e.printStackTrace();
		 	  }
		}

		@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_Success",description = "Verify Same coupons are returned By mail and uidx")
		public void DevAPI_GetUserCoupons_VerifyCouponsCount(String username, String password, String useMail)
		{
			RequestGenerator request = Helper.getUserCoupons(username, password, "true");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Get User Coupons Service Response : "+response);
			AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
			
			JSONArray couponsFromEmail = JsonPath.read(response, "$.data[*].coupon");
			System.out.println("JSON Arr"+couponsFromEmail);
			
			request = Helper.getUserCoupons(username, password, "true");
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get User Coupons Service Response : "+response);
			AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
			
			JSONArray couponsFromUIDX = JsonPath.read(response, "$.data[*].coupon");
			System.out.println("JSON Arr"+couponsFromUIDX);
			
			AssertJUnit.assertEquals("Number of Coupons differ", couponsFromEmail.size(),couponsFromUIDX.size());
			
		}
		
		@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetUserCoupons_Success",description = "Verify Same coupons are returned By mail and uidx")
		public void DevAPI_GetUserCoupons_VerifyCoupons(String username, String password, String useMail)
		{
			RequestGenerator request = Helper.getUserCoupons(username, password, "true");
			String response = request.respvalidate.returnresponseasstring();
			System.out.println("Get User Coupons Service Response : "+response);
			AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
			
			JSONArray couponsFromEmail = JsonPath.read(response, "$.data[*].coupon");
			System.out.println("JSON Arr"+couponsFromEmail);
			
			request = Helper.getUserCoupons(username, password, "true");
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Get User Coupons Service Response : "+response);
			AssertJUnit.assertEquals("Get user coupons service is failing", 200,request.response.getStatus());
			
			JSONArray couponsFromUIDX = JsonPath.read(response, "$.data[*].coupon");
			System.out.println("JSON Arr"+couponsFromUIDX);
			boolean allCouponsFound = true;
			for(int i=0;i<couponsFromEmail.size();i++)
			{
				if(!couponsFromUIDX.contains(couponsFromEmail.get(i)))
					{
					allCouponsFound = false;
					}
			}
			
			AssertJUnit.assertEquals("The coupons do not match", true,allCouponsFound);
			
		}
		
	//Get LGP Referral
	@Test(groups={"ProdSanity","Sanity","MiniRegression","Regression","ExhaustiveRegression"},dataProvider = "GetLGPReferral",description = "Get Referral Data. Verify Success")
	public void DevAPI_GetLGPReferral_VerifySuccess(String username, String password, String version, String tamperSession)
	{
		boolean tamper = Boolean.parseBoolean(tamperSession);
		RequestGenerator request = Helper.getLgpReferral(username, password, version, tamper);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get LGP Referral Service Response : "+response);
		int resCode = 200;
		if(tamper) resCode = 401;
		AssertJUnit.assertEquals("Get LGP Referral Service Failure", resCode,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"},dataProvider = "GetLGPReferral",description = "Get LGP Referral. Verify Schema")
	public void DevAPI_GetLGPReferral_VerifySchema(String username, String password, String version, String tamperSession)
	{
		boolean tamper = Boolean.parseBoolean(tamperSession);
		RequestGenerator request = Helper.getLgpReferral(username, password, version, tamper);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Get LGP Referral Service Response : "+response);
		int resCode = 200;
		if(tamper) resCode = 401;
		AssertJUnit.assertEquals("Get LGP Referral Service Failure", resCode,request.response.getStatus());
		try {
		 	   String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/DevAPI_GetLGPReferral_Schema.txt");
		 	   List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
		 	   AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevApi LGP Referral API response",
		 	    CollectionUtils.isEmpty(missingNodeList));
		 	  } catch (Exception e) {
		 	   e.printStackTrace();
		 	  }

	}
	
	
}
