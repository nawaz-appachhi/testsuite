package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;

import com.myntra.apiTests.dataproviders.LgpServDP;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class FeedObjectTests extends LgpServDP {
	
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(FeedObjectTests.class);
	
	String[] versionListings = {"0", "1.0", "1.1", "1.2", "2.0", "2.1"};
	
	String versionSpecification= System.getenv("API_VERSION");
	FeedObjectHelper feedObjectHelper= new FeedObjectHelper();
	public static String envValue = init.Configurations.properties.getPropertyValue("environment");
	
	@BeforeClass
	public void setup()
	{
		if(versionSpecification==null)
		{
			versionSpecification="v2.5";
		}
	}
	
	@Test(groups = { "Sanity","SchemaValidation" })
	public void keyFindTest() throws IOException
	{
		
		feedObjectHelper.jsonKeyListFindUtility();
		
	}
	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "articleCardFeedObjectEntityDP", priority = 1)
	public void feedObject_Schema_Validation_articleCard(String ObjectReferenceId) throws IOException
	{
		
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.ARTICLE_CARD, versionSpecification);
		
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.ARTICLE_CARD, versionSpecification, response);
					
//		Assert.assertEquals(schemaValidationResult, true);
//	    Assert.assertEquals(jsonKeyValidationResult, true);
		
	}


	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "questionCardFeedObjectEntityDP", priority = 2)
	public void feedObject_Schema_Validation_questionCard(String ObjectReferenceId) throws IOException{
		
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.QUESTION_CARD, versionSpecification);		
		
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.QUESTION_CARD, versionSpecification, response);
						
//		Assert.assertEquals(schemaValidationResult, true);
//		Assert.assertEquals(jsonKeyValidationResult, true);
					
		
	}
	
	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "bannerCardFeedObjectEntityDP", priority = 3)
	public void feedObject_Schema_Validation_bannerCard(String ObjectReferenceId) throws IOException{
	
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
		 
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.BANNER_CARD, versionSpecification);	
		
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 			
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.BANNER_CARD, versionSpecification, response);
			
//		Assert.assertEquals(schemaValidationResult, true);
//		Assert.assertEquals(jsonKeyValidationResult, true);
						
		
	}

	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "splitBannerCardFeedObjectEntityDP", priority = 4)
	public void feedObject_Schema_Validation_splitBannerCard(String ObjectReferenceId) throws IOException{
	
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
	
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.SPLIT_BANNER_CARD, versionSpecification);	
		
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.SPLIT_BANNER_CARD, versionSpecification, response);
			
//		Assert.assertEquals(schemaValidationResult, true);
//		Assert.assertEquals(jsonKeyValidationResult, true);
				
		
	}
	
	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "videoCardFeedObjectEntityDP", priority = 5)
	public void feedObject_Schema_Validation_videoCard(String ObjectReferenceId) throws IOException{
	
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
					
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.VIDEO_CARD, versionSpecification);
		
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 	
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.VIDEO_CARD, versionSpecification, response);
		
//		Assert.assertEquals(schemaValidationResult, true);
//    	Assert.assertEquals(jsonKeyValidationResult, true);
		
	}

	
	@Test(groups = { "Sanity","SchemaValidation" }, dataProvider = "pollCardFeedObjectEntityDP", priority = 6)
	public void feedObject_Schema_Validation_pollCard(String ObjectReferenceId) throws IOException{
		
		RequestGenerator request = getQueryRequestMultiParam(APINAME.GETFEEDOBJECTENTITY, ObjectReferenceId, versionSpecification);
		String response = request.respvalidate.returnresponseasstring();
		System.out.println(response);
					
//		String jsonSchemaRetrieved = feedObjectHelper.getComponentCardSchema(FeedCards.POLL_CARD, versionSpecification);
			
//		boolean schemaValidationResult = feedObjectHelper.componentCardSchemaValidation(response, jsonSchemaRetrieved, versionSpecification); 
//		boolean jsonKeyValidationResult = feedObjectHelper.jsonKeyValidationsForFeedCards(FeedCards.POLL_CARD, versionSpecification, response);
		
//		Assert.assertEquals(schemaValidationResult, true);
//		Assert.assertEquals(jsonKeyValidationResult, true);
							
	}
	
}

















