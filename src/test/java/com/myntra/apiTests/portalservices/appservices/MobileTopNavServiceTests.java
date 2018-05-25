package com.myntra.apiTests.portalservices.appservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.dataproviders.MobileTopNavDP;
import net.minidev.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.InitializeFramework;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class MobileTopNavServiceTests extends MobileTopNavDP
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileTopNavServiceTests.class);
	
	/**
	 * This TestCase is used to invoke MobileTopNavService getTopNav API and verification for success response
	 * 
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void MobileTopNavService_getTopNav_verifySuccessResponse() 
	{
		RequestGenerator mobileTopNavServiceReqGen = mobileTopNavServiceHelper.invokeMobileTopNavService();
		String mobileTopNavServiceResponse = mobileTopNavServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		log.info("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);

		AssertJUnit.assertEquals("MobileTopNavService getTopNav API is not working", 200, mobileTopNavServiceReqGen.response.getStatus());
		
	}
	
	/**
	 * This TestCase is used to invoke MobileTopNavService getTopNav API and verification for master top nav count
	 * 
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void MobileTopNavService_getTopNav_verifyMasterCatagoryTopNavCount() 
	{
		int i = 0;
		RequestGenerator mobileTopNavServiceReqGen = mobileTopNavServiceHelper.invokeMobileTopNavService();
		String mobileTopNavServiceResponse = mobileTopNavServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		log.info("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		
		AssertJUnit.assertEquals("MobileTopNavService getTopNav API is not working", 200, mobileTopNavServiceReqGen.response.getStatus());
		
		List<String> masterCatagoryTopNavList = JsonPath.read(mobileTopNavServiceResponse, "$.data.categories[*].name");
		
		System.out.println("\nPrinting MasterCatagoryTopNavCount : "+masterCatagoryTopNavList.size());
		log.info("\nPrinting MasterCatagoryTopNavCount : "+masterCatagoryTopNavList.size());
		
		System.out.println("\nPrinting MasterCatagoryTopNav : \n");
		log.info("\nPrinting MasterCatagoryTopNav : \n");
		
		for(String masterTopNav : masterCatagoryTopNavList){
			System.out.println(i+". : " +masterTopNav);
			log.info(i+". : " +masterTopNav);
			i++;
		}

		AssertJUnit.assertTrue("MasterCatagory Top Nav Tabs count for Page Header is not greater than 1", masterCatagoryTopNavList.size() > 1);
	}
	
	/**
	 * This TestCase is used to invoke MobileTopNavService getTopNav API and verification for master top nav count
	 * 
	 * @param masterNavUrl
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" })
	public void MobileTopNavService_getTopNav_verifySubCatagoryTopNavCount() 
	{
		List<JSONObject> masterCatagoryTopNavList = new ArrayList<JSONObject>();
		List<JSONObject> subCatagoryTopNavList = new ArrayList<JSONObject>();
		List<JSONObject> childCatagoryTopNavList = new ArrayList<JSONObject>();
		
		RequestGenerator mobileTopNavServiceReqGen = mobileTopNavServiceHelper.invokeMobileTopNavService();
		String mobileTopNavServiceResponse = mobileTopNavServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		log.info("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		
		AssertJUnit.assertEquals("MobileTopNavService getTopNav API is not working", 200, mobileTopNavServiceReqGen.response.getStatus());
		
		masterCatagoryTopNavList = JsonPath.read(mobileTopNavServiceResponse, "$.data.categories[*]");
		
		for(int i = 0; i < masterCatagoryTopNavList.size(); i++)
		{
			System.out.println("MasterCatagoryTopNav : "+i+". "+JsonPath.read(mobileTopNavServiceResponse, "$.data.categories["+i+"].name"));
			
			subCatagoryTopNavList = JsonPath.read(mobileTopNavServiceResponse, "$.data.categories["+i+"].categories[*]");
			
			for(int j = 0; j < subCatagoryTopNavList.size(); j++)
			{
				System.out.println("SubCatagoryTopNav : "+j+". "+JsonPath.read(mobileTopNavServiceResponse, "$.data.categories["+i+"].categories["+j+"].name"));
				childCatagoryTopNavList = JsonPath.read(mobileTopNavServiceResponse, "$.data.categories["+i+"].categories["+j+"].categories[*]");
				
				for(int k = 0; k < childCatagoryTopNavList.size(); k++)
				{
					System.out.println("ChildCatagoryTopNav : "+k+". "+JsonPath.read(mobileTopNavServiceResponse, "$.data.categories["+i+"].categories["+j+"].categories["+k+"].name"));
					//k++;
				}
				//j++;
			}
			//i++;
		}
		
		AssertJUnit.assertTrue("MasterCatagory Top Nav Tabs count for Page Header is not greater than 1", masterCatagoryTopNavList.size() > 1);
		
		//AssertJUnit.assertTrue("SubCatagory Top Nav Tabs count for Page Header is not greater than 1", subCatagoryTopNavList.size() > 1);
		
	}

	/**
	 * @param urlFromService
	 * @throws IOException
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileTopNavDP_getAllMyntraUrlsFromTopNav")
	public void MobileTopNavService_getTopNav_invokeAllTopNavUrl(String urlFromService) throws IOException
	{
		urlFromService = urlFromService.trim();
		System.out.println("\nPrinting the url is going to invoke/trigger : "+urlFromService+"\n");
		log.info("\nPrinting the url is going to invoke/trigger : "+urlFromService+"\n");
		
		if (urlFromService.substring(urlFromService.length() - 1) == "/") 
		{
			urlFromService = urlFromService.substring(0, urlFromService.length() - 1);
		}

		HttpClient client = HttpClientBuilder.create().build();
		final String USER_AGENT = "Mozilla/5.0";
		System.setProperty("http.agent", USER_AGENT);
		HttpGet request = new HttpGet(urlFromService);
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		AssertJUnit.assertEquals("Response Code for url : '" + urlFromService + "' is not 200", 200, responseCode);
	}
	
	@Test(groups = { "SchemaValidation" })
	public void MobileTopNavService_getTopNav_verifyResponseDataNodesUsingSchemaValidations() 
	{
		RequestGenerator mobileTopNavServiceReqGen = mobileTopNavServiceHelper.invokeMobileTopNavService();
		String mobileTopNavServiceResponse = mobileTopNavServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		log.info("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);

		AssertJUnit.assertEquals("MobileTopNavService getTopNav API is not working", 200, mobileTopNavServiceReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobiletopnavservice-gettopnav-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mobileTopNavServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileTopNavService getTopNav API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
