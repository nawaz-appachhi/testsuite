package com.myntra.apiTests.inbound.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.inbound.response.ArtieService.ArtieServiceResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

public class ArtieServiceHelper {
	
	static Logger log = Logger.getLogger(ArtieServiceHelper.class);

	private HashMap<String, String> getArtieAPIHeader() {
		HashMap<String, String> createArtieServiceHeaders = new HashMap<String, String>();
		createArtieServiceHeaders.put("Authorization", "Basic U3JhdmFuIEt1bWFyfmVycGFkbWlufnNyYXZhbi5rdW1hckBteW50cmEuY29t");
		createArtieServiceHeaders.put("Content-Type", "Application/json");
		createArtieServiceHeaders.put("Accept", "Application/json");
		createArtieServiceHeaders.put("user-role", "{\"isDS\":false,\"isDesigner\":false,\"isCM\":true,\"isExtDesigner\":false}");
		
		return createArtieServiceHeaders;
	}

	public ArtieServiceResponse addCategory(String name, String createdBy) throws Exception {
		
//		Map<String, Object> transaction_detail =
//				 DBUtilities.exSelectQueryForSingleRecord("select * from recipe_drop where category_id=16", "artie");
//		 
//		 System.out.println(transaction_detail.size());
		
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setName(name);
		artiePayloadGenerator.setCreatedBy(createdBy);
		// RecipeEntry recipeEntry=new RecipeEntry();
		// //recipeEntry.setN
		// //recipeEntry.
		// RecipeActionEntry r=new RecipeActionEntry();
		// RecipeDataEntry d=new RecipeDataEntry();
		// RecipeCommentEntry c=new RecipeCommentEntry();
		// c.set
		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.CREATE_CATEGORY, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new ArtieServiceResponse());
		
		 
				 //return transaction_detail.get("amount").toString();
		
		return artieServiceResponse;

	}

	public ArtieServiceResponse getAllCategories() throws Exception {
		//
		//
		// Map<String, Object> transaction_detail =
		// DBUtilities.exSelectQueryForSingleRecord("select * from recipe_drop
		// where category_id=16", "artie");
		// //return transaction_detail.get("amount").toString();

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_CATEGORY_ALL, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getAllDropsForCategoryId(String categoryId) throws Exception {

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_DROPBYCATEGORYID,
				new String[] { "getAll?categoryId=" + categoryId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null,
				getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getDropDetailsByDropId(String DropId) throws Exception {

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_DROPBYCATEGORYID,
				new String[] { DropId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	// {
	// "categoryId":"16",
	// "startDate":"20160824",
	// "endDate":"20160824",
	// "optionsRequired":5,
	// "name":"test drop6",
	// "createdBy":"sravan.kumar"
	// }

	public ArtieServiceResponse createDropForCategoryId(String payload) throws Exception {
	
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.CREATE_DROPFORCATEGORYID, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse updateDropDetailsByDropId(String dropId, String categoryId, String optionsRequired,
			String name) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setCategoryId(categoryId);
		artiePayloadGenerator.setOptionsRequired(optionsRequired);
		artiePayloadGenerator.setName(name);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.UPDATE_DROPBYDROPID,
				new String[] { dropId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.PUT, payload, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse closeDropByDropId(String dropId) throws Exception {
		// RecipeEntry recipeEntry=new RecipeEntry();
		// recipeEntry.setId(id);
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(dropId);
		;

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.CLOSE_DROPBYDROPID, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getRecipeMetadataByCategoryId(String categoryId) throws Exception {

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_METADATABYCATEGORYID,
				new String[] { "get?categoryId=" + categoryId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null,
				getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse listOfAllRecipesForADropId(String dropId) throws Exception {

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_ALLRECIPESBYDROPID,
				new String[] { "getAll?dropId=" + dropId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null,
				getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getRecipeDetailsByRecipeId(String recipeId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_RECIPEDETAILSBYRECIPEID,
				new String[] { recipeId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	/*****************
	 * Add New Recipe Under DropId Payload***************** "dropId":, "title":,
	 * "description": , "imageURL":, "decisionTreeData": , "endDate":
	 * "optionsRequired": , "recipeData": { "Colour Name": , "Collar Type": },
	 * "recipeReferenceProducts": [ "styleId": , "source": , "imageURL": ,
	 * "styleType": , "styleParams": { "ros": , "asp": } } ] }
	 */
	public ArtieServiceResponse addNewRecipeUnderDropId(String dropId, String title, String description,
			String imageURL, String decisionTreeData, String endDate, String optionsRequired, String collarType,
			String colourName, String styleId, String source, String recipeRefImageURL, String styleType, String ros,
			String asp, String styleId1, String source1, String recipeRefImageURL1, String styleType1, String ros1,
			String asp1, String payload) throws Exception {
//		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
//		artiePayloadGenerator.setDropId(dropId);
//		artiePayloadGenerator.setTitle(title);
//		artiePayloadGenerator.setDescription(description);
//		artiePayloadGenerator.setImageURL(imageURL);
//		artiePayloadGenerator.setDecisionTreeData(decisionTreeData);
//		artiePayloadGenerator.setEndDate(endDate);
//		artiePayloadGenerator.setOptionsRequired(optionsRequired);
//
//		RecipeData recipeData = new RecipeData();
//		recipeData.setCollarType(collarType);
//		recipeData.setBaseColour(colourName);
//		artiePayloadGenerator.setRecipeData(recipeData);
//
//		List<RecipeReferenceProducts> data = new ArrayList<>();
//		RecipeReferenceProducts recipeReferenceProducts = new RecipeReferenceProducts();
//		recipeReferenceProducts.setStyleId(styleId); // Adding Recipe Ref
//														// Product Info for
//														// StyleId1
//		recipeReferenceProducts.setSource(source);
//		recipeReferenceProducts.setImageURL(recipeRefImageURL);
//		recipeReferenceProducts.setStyleType(styleType);
//		StyleParams styleParams = new StyleParams();
//		styleParams.setAsp(asp);
//		styleParams.setRos(ros);
//		recipeReferenceProducts.setStyleParams(styleParams);
//		data.add(recipeReferenceProducts);
//
//		RecipeReferenceProducts recipeReferenceProducts1 = new RecipeReferenceProducts();
//		recipeReferenceProducts1.setStyleId(styleId1); // Adding Recipe Ref
//														// Product Info for
//														// StyleId2
//		recipeReferenceProducts1.setSource(source1);
//		recipeReferenceProducts1.setImageURL(recipeRefImageURL1);
//		recipeReferenceProducts1.setStyleType(styleType1);
//		StyleParams styleParams1 = new StyleParams();
//		styleParams1.setAsp(asp1);
//		styleParams1.setRos(ros1);
//		recipeReferenceProducts1.setStyleParams(styleParams1);
//		data.add(recipeReferenceProducts1);
//
//		artiePayloadGenerator.setRecipeReferenceProducts(data);
//
//		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.ADD_RECIPEUNDERDROP, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	/*
	 * { "id":, "createdBy":, "createdOn":, "lastModifiedOn": , "dropId":,
	 * "title":, "imageURL":, "decisionTreeData": , "description": , "status": ,
	 * "endDate": "optionsRequired": , "optionsSubmitted":, "optionsApproved":,
	 * "score": , "assignee": , "recipeData": { "Colour Name": , "Collar Type":
	 * }, "recipeReferenceProducts": [ { "id": , "createdBy": , "createdOn": ,
	 * "lastModifiedOn":, "styleId": , "source": , "imageURL": , "styleType": ,
	 * "styleParams": { "ros": , "asp": } } ] }
	 */

	public ArtieServiceResponse updateRecipeDetailsByRecipeId(String recipeId, String createdBy, String createdOn,
			String lastModifiedOn, String dropId, String title, String imageURL, String decisionTreeData,
			String description, String status, String endDate, String optionsRequired, String optionsSubmitted,
			String optionsApproved, String score, String colourName, String collarType, String refProductId,
			String refProductCreatedBy, String refProductCreatedOn, String refProductReflastModifiedOn, String styleId,
			String source, String recipeRefImageURL, String styleType, String ros, String asp,String payload) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setCreatedBy(createdBy);
		artiePayloadGenerator.setCreatedOn(createdOn);
		artiePayloadGenerator.setLastModifiedOn(lastModifiedOn);
		artiePayloadGenerator.setDropId(dropId);
		artiePayloadGenerator.setTitle(title);
		artiePayloadGenerator.setDescription(description);
		artiePayloadGenerator.setStatus(status);
		artiePayloadGenerator.setImageURL(imageURL);
		artiePayloadGenerator.setDecisionTreeData(decisionTreeData);
		artiePayloadGenerator.setEndDate(endDate);
		artiePayloadGenerator.setOptionsRequired(optionsRequired);
		artiePayloadGenerator.setOptionsApproved(optionsApproved);
		artiePayloadGenerator.setOptionsSubmitted(optionsSubmitted);
		artiePayloadGenerator.setScore(score);

		RecipeData recipeData = new RecipeData();
		recipeData.setCollarType(collarType);
		recipeData.setBaseColour(colourName);
		artiePayloadGenerator.setRecipeData(recipeData);

		List<RecipeReferenceProducts> data = new ArrayList<>();
		RecipeReferenceProducts recipeReferenceProducts = new RecipeReferenceProducts();
		recipeReferenceProducts.setStyleId(styleId); // Adding Recipe Ref
														// Product Info for
														// StyleId1
		recipeReferenceProducts.setId(refProductId);
		recipeReferenceProducts.setCreatedBy(refProductCreatedBy);
		recipeReferenceProducts.setCreatedOn(refProductCreatedOn);
		recipeReferenceProducts.setLastModifiedOn(refProductReflastModifiedOn);
		recipeReferenceProducts.setSource(source);
		recipeReferenceProducts.setImageURL(recipeRefImageURL);
		recipeReferenceProducts.setStyleType(styleType);
		StyleParams styleParams = new StyleParams();
		styleParams.setAsp(asp);
		styleParams.setRos(ros);
		recipeReferenceProducts.setStyleParams(styleParams);
		data.add(recipeReferenceProducts);

		artiePayloadGenerator.setRecipeReferenceProducts(data);

		//String payload = APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.UPDATE_RECIPEBYRECIPEID,
				new String[] { recipeId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.PUT, payload, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse removeStylesFromRecipe(String recipeId, String[] productsToRemove) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setProductsToRemove(productsToRemove);

		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);
	

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.REMOVE_STYLESFROMRECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());
		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");
		
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	/**
	 * { "id": 43, "score":0.2, "comment":"Too bad", "createdBy":"anuj.modi" }
	 */

	public ArtieServiceResponse archiveRecipeByRecipeId(String recipeId, String score, String comment, String createdBy)
			throws Exception {

		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setScore(score);
		artiePayloadGenerator.setComment(comment);
		;

		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);
		// String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.ARCHIVE_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse approve_publish_recipe(String recipeId, String score, String comment, String assignee,
			String createdBy) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setScore(score);
		artiePayloadGenerator.setAssignee(assignee);
		artiePayloadGenerator.setCreatedBy(createdBy);
		artiePayloadGenerator.setComment(comment);
		;

		// String payload =
		// APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);
		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.PUBLISH_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse addDesignUnderRecipe(String recipeId, String imageURL, String createdBy)
			throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setRecipeId(recipeId);
		artiePayloadGenerator.setImageURL(imageURL);
		;
		artiePayloadGenerator.setCreatedBy(createdBy);

		// String payload =
		// APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);
		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.ADD_DESIGN_UNDER_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getAllDesignsOfRecipe(String recipeId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_ALL_DESIGNSFORRECIPEID,
				new String[] { "getAll?recipeId=" + recipeId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null,
				getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse closeRecipeByRecipeId(String recipeId, String comment, String createdBy)
			throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setComment(comment);
		artiePayloadGenerator.setCreatedBy(createdBy);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.CLOSE_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse getDesignDetailsByDesignId(String designId) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_DESIGNDETAILSBYDESIGNID,
				new String[] { designId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse updateDesignDetailsByDesignId(String designId, String recipeId, String imageURL,
			String createdBy) throws Exception {

		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setRecipeId(recipeId);
		artiePayloadGenerator.setImageURL(imageURL);
		artiePayloadGenerator.setCreatedBy(createdBy);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.UPDATE_DESIGNDETAILSBYDESIGNID,
				new String[] { designId }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.PUT, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse addCommentsOnDesign(String designId, String createdBy, String text) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setDesignId(designId);
		artiePayloadGenerator.setText(text);
		artiePayloadGenerator.setCreatedBy(createdBy);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.ADD_COMMENTSFORDESIGNID, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse approveDesign(String id, String createdBy) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(id);
		artiePayloadGenerator.setCreatedBy(createdBy);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.APPROVE_DESIGN, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse rejectDesign(String id, String createdBy) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(id);
		artiePayloadGenerator.setCreatedBy(createdBy);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.REJECT_DESIGN, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}
	
	/**
	 * { "id": 43, "score":0.2, "comment":"Too bad", "createdBy":"anuj.modi" }
	 */

	public ArtieServiceResponse archiveRecipeWithoutComments(String recipeId, String score, String createdBy)
			throws Exception {

		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setScore(score);
		

		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(artiePayloadGenerator);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.ARCHIVE_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;

	}

	public ArtieServiceResponse approvePublishRecipeWithoutAssignee(String recipeId, String score, String comment,
			String createdBy) throws Exception {
		ArtiePayloadGenerator artiePayloadGenerator = new ArtiePayloadGenerator();
		artiePayloadGenerator.setId(recipeId);
		artiePayloadGenerator.setScore(score);
		artiePayloadGenerator.setCreatedBy(createdBy);
		artiePayloadGenerator.setComment(comment);

		String payload = APIUtilities.getObjectToJSON(artiePayloadGenerator);

		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.PUBLISH_RECIPE, null,
				SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.POST, payload, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;
	}

	public ArtieServiceResponse getapprovedDesignsForDropId(String dropId,String status) throws Exception {
		Svc service = HttpExecutorService.executeHttpService(Constants.ARTIE_PATH.GET_APPROVEDDESIGNSFORDESIGNID,
				new String[] { "getForDrop?dropId="+dropId+"&status="+status }, SERVICE_TYPE.ARTIE_SVC.toString(), HTTPMethods.GET, null, getArtieAPIHeader());

		Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");

		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new ArtieServiceResponse());
		log.debug("API Response status Message: "+artieServiceResponse.getStatus().getStatusMessage());
		return artieServiceResponse;
	}
	
	
	
	
	
	/*
	 * Sql query to insert records
	 */
	
	
	
	
	public void insertFifaDataConfigForCategory(long categoryId){
	String query="INSERT INTO `fifa_data_config`(`created_by`,`created_on`,`last_modified_on`,`version`,`category_id`,`json_key`,`json_parent`,`source`) "
			+ "VALUES ('sravan.kumar',now(),now(),0,"+categoryId+",'base_colour','styleAttributes','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'MRP','styleAttributes','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'daysLiveLast30','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'asp_last_30_days','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'rosSizebreakLast30Days','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'revLast30SizeBreak','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'qtyLast30SizeBreak','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'discountPerc','aggMetrics','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'image','styleAttributes','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'attrs','styleAttributes','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'imageEnricher_attributes','styleAttributes','CMS'),"
			+ "('sravan.kumar',now(),now(),0,"+categoryId+",'','source','CMS');";
	DBUtilities.exUpdateQuery(query, "artie");
	
	}
	
	public void insertRecipeMetadataForCategory(long categoryId){
		String query="Insert into recipe_metadata (created_by, created_on, last_modified_on,version, category_id,name,type,mode, label, is_mandatory, is_editable) values "
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'topMRPs' ,'JSONARRAY','BULK','Top MRPs', true, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'topBrands' ,'JSONARRAY','BULK', 'Top Brands', true, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'topColours' ,'JSONARRAY','BULK', 'Top Colours', true, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'secondaryAttributes' ,'JSONARRAY','BULK', 'Secondary Attributes', true, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'primaryAttributes' ,'JSONARRAY','BULK', 'Primary Attributes', true, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'MaxPredictedROS' ,'FLOAT','BULK','Maximum Predicted ROS', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'MinPredictedROS' ,'FLOAT','BULK', 'Minimum Preicted ROS', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'optionsPercentage' ,'FLOAT','BULK', 'Options Percentage', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'NotToDoAttributes' ,'JSONARRAY','BULK','Not To Do Attributes', true, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'others','STRING','BULK','Others',false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'MaxPredictedROS' ,'FLOAT','MANUAL','Maximum Predicted ROS', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'MinPredictedROS' ,'FLOAT','MANUAL', 'Minimum Preicted ROS', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'optionsPercentage' ,'FLOAT','MANUAL', 'Options Percentage', false, false),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'baseFabricColours' ,'JSONARRAY','MANUAL','Base Fabric Colours', false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'colourCombinations' ,'JSONARRAY','MANUAL','Colour Combinations', false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'keyAttributes' ,'JSONARRAY','MANUAL','Key Attributes', false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'secondaryAttributesManual' ,'JSONARRAY','MANUAL','Secondary Attributes', false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'bodies' ,'JSONARRAY','MANUAL','Bodies', false, true),"
				+ "('sravan.kumar',now(),now(),0,"+categoryId+",'others' ,'JSONARRAY','MANUAL','Others', false, true);";
		
		DBUtilities.exUpdateQuery(query, "artie");
	}
	
	public void deleteFifaDataConfigForCategory(Long categoryId){
		  String query="Delete from `fifa_data_config` where `category_id`="+categoryId;
		  DBUtilities.exUpdateQuery(query, "artie");
	}
	public void deleteMetadataForCategory(Long categoryId){
		  String query="Delete from `fifa_data_config` where `category_id`="+categoryId;
		  DBUtilities.exUpdateQuery(query, "artie");
	}
	
	public void closeExistingOpenDrops(){
		String query="update recipe_drop set status='IN_PRODUCTION'  where category_id=16 and status ='OPEN';";
		DBUtilities.exUpdateQuery(query, "artie");
	}

}
