package com.myntra.apiTests.portalservices.all;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.myntra.apiTests.dataproviders.ShortenURLDP;
import com.myntra.apiTests.portalservices.shortenurl.ShortenURLServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class ShortenURLTests extends ShortenURLDP

{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ShortenURLTests.class);
	static ShortenURLServiceHelper shortenURLServiceHelper = new ShortenURLServiceHelper();
	
	WebTarget target;
	ClientConfig config;
	Client client;
	Invocation.Builder invBuilder;
	
	public String randomize(String data)
	{
		int minimum = 1001;
		int maximum = 9999;
		Integer randomNum = minimum  + (int)(Math.random()*maximum); 
		return (data+"_code_"+randomNum.toString());
	}
	
	public String encodeURL(String url) throws UnsupportedEncodingException
	{
		return(java.net.URLEncoder.encode(url.toString(), "ISO-8859-1"));
	}
	
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingPayload")
	public void CreateShortCodeWithAliasUsingPayload_VerifySuccess(String url, String alias)
	{
		url = randomize(url);
		alias=randomize(alias);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingPayload")
	public void CreateShortCodeWithAliasUsingPayload_VerifyData(String url, String alias)
	{
		url = randomize(url);
		alias=randomize(alias);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - URL is not returned", url,JsonPath.read(response, "$.url"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		AssertJUnit.assertEquals("Create Short URL API is not working - Alias is not returned", alias,JsonPath.read(response, "$.alias"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Alias is not prepended in code", alias,JsonPath.read(response, "$.code").toString().substring(0,alias.length()));
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingPayload")
	public void CreateShortCodeWithAliasUsingPayload_VerifySchema(String url, String alias)
	{
		url = randomize(url);
		alias=randomize(alias);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());

		try{ 
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/createShortenURL-schemaSet.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
        	CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
				{
					e.printStackTrace();
				}
			
	}

	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingPayload")
	public void CreateShortCodeWithOutAliasUsingPayload_VerifySuccess(String url, String alias)
	{
		url = randomize(url);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingPayload")
	public void CreateShortCodeWithOutAliasUsingPayload_VerifyData(String url, String alias)
	{
		url = randomize(url);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - URL is not returned", url,JsonPath.read(response, "$.url"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingPayload")
	public void CreateShortCodeWithOutAliasUsingPayload_VerifySchema(String url, String alias)
	{
		url = randomize(url);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());

		try{ 
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/createShortenURL-schemaSet.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
        	CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
				{
					e.printStackTrace();
				}
			
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingQueryParams")
	public void CreateShortCodeWithAliasUsingQueryParams_VerifySuccess(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		alias=randomize(alias);
		
					
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithQueryParams(url,alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
	}

	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingQueryParams")
	public void CreateShortCodeWithAliasUsingQueryParams_VerifyData(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		alias=randomize(alias);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - URL is not returned", url,JsonPath.read(response, "$.url"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		AssertJUnit.assertEquals("Create Short URL API is not working - Alias is not returned", alias,JsonPath.read(response, "$.alias"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Alias is not prepended in code", alias,JsonPath.read(response, "$.code").toString().substring(0,alias.length()));
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingQueryParams")
	public void CreateShortCodeWithAliasUsingQueryParams_VerifySchema(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		alias=randomize(alias);
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());

		try{ 
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/createShortenURL-schemaSet.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
        	CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
				{
					e.printStackTrace();
				}
			
	}

	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingQueryParams")
	public void CreateShortCodeWithOutAliasUsingQueryParams_VerifySuccess(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingQueryParams")
	public void CreateShortCodeWithOutAliasUsingQueryParams_VerifyData(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - URL is not returned", url,JsonPath.read(response, "$.url"));
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithoutAliasUsingQueryParams")
	public void CreateShortCodeWithOutAliasUsingQueryParams_VerifySchema(String url, String alias) throws UnsupportedEncodingException
	{
		url = encodeURL(randomize(url));
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());

		try{ 
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/createShortenURL-schemaSet.txt");
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleForum Create poll API response", 
        	CollectionUtils.isEmpty(missingNodeList));
			} catch (Exception e) 
				{
					e.printStackTrace();
				}
			
	}

	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingQueryParams")
	public void CheckSameCodeisGeneratedusingVariousMethods_VerifySuccess(String url, String alias)
	{
		url = randomize(url);
		alias=randomize(alias);
		String ShortCode = null;
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		ShortCode = JsonPath.read(response, "$.code").toString();
		
		//Using Payload and Alias
		request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and Alias]", ShortCode,JsonPath.read(response, "$.code"));
		
		//Using Payload and Different Alias
		request = shortenURLServiceHelper.shortenUrlWithPayload(url, randomize(alias));
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and Different Alias]", ShortCode,JsonPath.read(response, "$.code"));
	
		//Using Payload and no Alias
		request = shortenURLServiceHelper.shortenUrlWithPayload(url, "");
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and no Alias]", ShortCode,JsonPath.read(response, "$.code"));
	
		//Using QueryParams and no Alias
		request = shortenURLServiceHelper.shortenUrlWithQueryParams(url, "");
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and no Alias]", ShortCode,JsonPath.read(response, "$.code"));

		//Using QueryParams and Different Alias
		request = shortenURLServiceHelper.shortenUrlWithQueryParams(url, randomize(alias));
		response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and no Alias]", ShortCode,JsonPath.read(response, "$.code"));

	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "CreateShortCodeWithAliasUsingQueryParams")
	public void CheckSameCodeisGeneratedMultipleTimes_VerifySuccess(String url, String alias)
	{
		url = randomize(url);
		alias=randomize(alias);
		String ShortCode = null;
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		ShortCode = JsonPath.read(response, "$.code").toString();
		
		for(int i=0; i<10;i++)
		{
			request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
			response = request.respvalidate.returnresponseasstring();
			System.out.println("Create Code Response: [Trial - "+i+"] :"+response);
			AssertJUnit.assertEquals("Create Short URL API is not working - Same Code is not returned -[Using Payload and no Alias]", ShortCode,JsonPath.read(response, "$.code"));
		}
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },dataProvider = "GetURLFromCode")
	public void GetUrlFromShortCode_VerifySuccess(String url, String alias)
	{
		String ShortCode = null;
		RequestGenerator request = shortenURLServiceHelper.shortenUrlWithPayload(url, alias);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println("Create Code Response : \n "+response);
		AssertJUnit.assertEquals("Create Short URL API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Create Short URL API is not working - Code is not returned", false,JsonPath.read(response, "$.code").equals(null));
		ShortCode = JsonPath.read(response, "$.code").toString();
		
		request = shortenURLServiceHelper.getURLfromShortCode(ShortCode);
		response=request.respvalidate.returnresponseasstring();
		//System.out.println(response);
		AssertJUnit.assertEquals("Get Original URL from Code API is not working", 200,request.response.getStatus());
		AssertJUnit.assertEquals("Get Original URL from Code API is not working- [Unable to Validate Content]", true,response.contains(alias));

		
		
	}
	
	
}
	