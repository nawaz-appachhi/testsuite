package com.myntra.apiTests.portalservices.devapis;

import java.io.IOException;
import java.util.List;

import com.myntra.apiTests.dataproviders.devapis.CartTestsDP;
import com.myntra.apiTests.portalservices.devapiservice.CartTestsHelper;
import com.myntra.apiTests.portalservices.devapiservice.DevAPICommonMethods;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class CartTests extends CartTestsDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevAPITests.class);
	static CartTestsHelper Helper = new CartTestsHelper();
	static DevAPICommonMethods DevAPIHelper = new DevAPICommonMethods();
	
	//Verify Get User Cart API
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_Cart_GetUserCart_Sanity_DP",description="Get User Cart API. Verify Response Code")
	public void DevAPI_Cart_GetUserCart_VerifySuccess(String email, String password)
	{
		RequestGenerator request = Helper.getUserCart(email, password,false);
		System.out.println("Get User Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get User Cart API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"Regression"}, dataProvider="DevAPI_Cart_GetUserCart_Regression_DP",description="Get User Cart API. Verify New Session while invalid session is used")
	public void DevAPI_Cart_GetUserCart_VerifyData(String email, String password, String tamperSession)
	{
		RequestGenerator request = Helper.getUserCart(email, password, Boolean.parseBoolean(tamperSession));
		System.out.println("Get User Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get User Cart API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"NodeValidation"}, dataProvider="DevAPI_Cart_GetUserCart_Sanity_DP",description="Get User Cart API. Verify Schema")
	public void DevAPI_Cart_GetUserCart_VerifyNodes(String email, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getUserCart(email, password,false);
		String response = request.returnresponseasstring();
		System.out.println("Get User Cart Response : "+response);
		AssertJUnit.assertEquals("Get User Cart API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"NodeValidation"}, dataProvider="DevAPI_Cart_GetUserCart_Sanity_DP",description="Get User Cart API. Verify Schema ")
	public void DevAPI_Cart_GetUserCart_VerifySchema(String email, String password) throws JsonProcessingException, IOException
	{
		RequestGenerator request = Helper.getUserCart(email, password,false);
		String response = request.returnresponseasstring();
		System.out.println("Get User Cart Response : "+response);
		AssertJUnit.assertEquals("Get User Cart API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Cart_GetUserCart_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Get User Cart response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	//Add to Cart
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_AddToCart_Sanity_DP",description="Add item to Cart API. Verify Response Code",enabled=true)
	public void DevAPI_Cart_AddToCart_VerifySuccess(String email, String password, String skuid, String tamperSession)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCart(email, password,skuid,Boolean.parseBoolean(tamperSession));
		System.out.println("Add to Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"}, dataProvider="DevAPI_AddToCart_Sanity_DP",description="Add item to Cart API. Verify Response Code",enabled=true)
	public void DevAPI_Cart_AddToCart_VerifySchema(String email, String password, String skuid, String tamperSession)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCart(email, password,skuid,Boolean.parseBoolean(tamperSession));
		String response =request.respvalidate.returnresponseasstring();
		System.out.println("Add to Cart Response : "+response);
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Cart_AddItemToCart_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Add Item to Cart response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_AddToCartWithStyle_Sanity_DP",description="Add item to Cart with StyleId API. Verify Response Code",enabled=true)
	public void DevAPI_Cart_AddToCartWithStyle_VerifySuccess(String email, String password, String skuid, String styleId, String tamperSession)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCartWithStyleId(email, password,skuid,styleId,Boolean.parseBoolean(tamperSession));
		System.out.println("Add to Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
	}
	
	@Test(groups={"SchemaValidation"}, dataProvider="DevAPI_AddToCartWithStyle_Sanity_DP",description="Add item to Cart with StyleId API. Verify Response Schema",enabled=true)
	public void DevAPI_Cart_AddToCartWithStyle_VerifySchema(String email, String password, String skuid, String styleId, String tamperSession)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCartWithStyleId(email, password,skuid,styleId,Boolean.parseBoolean(tamperSession));
		String response =request.respvalidate.returnresponseasstring();
		System.out.println("Add to Cart Response : "+response);
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/DevAPIs/Cart_AddItemToCart_Schema.txt");
			List < String > missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList + " nodes are missing in DevAPI Add Item to Cart response",
					CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Schema Verification Failure");
		}
	}
	
	@Test(groups={"Sanity", "Regression"}, dataProvider="DevAPI_AddToCartWithStyle_NegativeCase_DP",description="Add item to Cart with StyleId API. Verify Failure Code",enabled=true)
	public void DevAPI_Cart_AddToCartWithStyle_VerifyFailure(String email, String password, String skuid, String styleId, String tamperSession, String responseCode)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCartWithStyleId(email, password,skuid,styleId,Boolean.parseBoolean(tamperSession));
		System.out.println("Add to Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Add to Cart API is down",Integer.parseInt(responseCode),request.response.getStatus());
	}
	
	@Test(groups={"NodeValidations"}, dataProvider="DevAPI_AddToCart_Sanity_DP",description="Add item to Cart API. Verify Nodes Code",enabled=true)
	public void DevAPI_Cart_AddToCart_VerifyNodes(String email, String password, String skuid, String tamperSession) throws JsonProcessingException, IOException
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCart(email, password,skuid,Boolean.parseBoolean(tamperSession));
		String response=request.respvalidate.returnresponseasstring();
		System.out.println("Add to Cart Response : "+response);
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
		boolean containsEmptyNodes = DevAPIHelper.doesContainEmptyNodes(response);
		AssertJUnit.assertEquals("Found Empty Nodes in Response", false,containsEmptyNodes);
	}
	
	@Test(groups={"Regression"}, dataProvider="DevAPI_AddToCart_Sanity_DP",description="Add item to Cart API. Verify Response Code",enabled=true)
	public void DevAPI_Cart_AddToCart_VerifyData(String email, String password, String skuid, String tamperSession)
	{
		Helper.ClearUserCart(email, password);
		RequestGenerator request = Helper.AddItemToCart(email, password,skuid,Boolean.parseBoolean(tamperSession));
		System.out.println("Add to Cart Response : "+request.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Add to Cart API is down",200,request.response.getStatus());
	}
	
}
