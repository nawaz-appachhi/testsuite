package com.myntra.apiTests.inbound.FIFA;

import java.sql.SQLException;

import com.myntra.apiTests.inbound.dp.ArtieServiceDP;
import com.myntra.apiTests.inbound.helper.RecipeReferenceProducts;
import com.myntra.apiTests.inbound.response.ArtieService.Data;
import com.myntra.apiTests.inbound.helper.ArtieServiceHelper;
import com.myntra.apiTests.inbound.response.ArtieService.ArtieServiceResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.myntra.lordoftherings.Initialize;
import com.myntra.recipe.client.codes.RecipeErrorCodes;
import com.myntra.recipe.client.codes.RecipeSuccessCodes;
import com.myntra.recipe.enums.DesignStatus;
import com.myntra.recipe.enums.DropStatus;
import com.myntra.recipe.enums.RecipeStatus;
import com.myntra.test.commons.testbase.BaseTest;

public class ArtieServiceTests extends BaseTest {
	static Logger log = Logger.getLogger(ArtieServiceTests.class);
	public static Initialize init;
	public static ArtieServiceHelper artieServiceHelper;
	public static String categoryId;
	public static String dropId;
	public static String recipeId;
	public static String designId;
	public static String styleIdAdded;
	public static String productId;
	
	@BeforeClass()
	public void testAfterClass() throws SQLException {
		init = new Initialize("Data/configuration");
		artieServiceHelper = new ArtieServiceHelper();

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, description = "Lists all the Categories in category table")
	public void listAllCategories() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.getAllCategories();
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.CATEGORY_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusMessage(), RecipeSuccessCodes.CATEGORY_FETCHED_SUCCESSFULLY.getStatusMessage());

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "addCategory", description = "Adds a new Category in category table")
	public void addCategory(String name, String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.addCategory(name,
				createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.CATEGORY_ADDED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		categoryId=data[0].getId();
		

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {"addCategory" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "createDropForCategoryId", description = "Creates a new Drop for a CategoryId")
	public void createDropForCategoryId(String payload) throws Exception {
		artieServiceHelper.closeExistingOpenDrops();
		System.out.println(RecipeSuccessCodes.DROP_ADDED_SUCCESSFULLY.getStatusCode());
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.createDropForCategoryId(payload);

		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DROP_ADDED_AND_ARTIE_TRIGGERED.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		//Assert.assertEquals(data[0].getCategoryId(), categoryId, "categoryId : ");
		Assert.assertEquals(data[0].getStatus(), DropStatus.OPEN.toString(), "Drop Status : ");
		dropId = data[0].getId();
		ArtieServiceResponse artieServiceResponse1 = (ArtieServiceResponse) artieServiceHelper
				.closeDropByDropId(dropId);
		Assert.assertEquals(artieServiceResponse1.getStatus().getStatusCode(),
				RecipeSuccessCodes.DROP_CLOSED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse1.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"addCategory" }, description = "Fetches Drop Details available for a CategoryId")
	public void getAllDropsForACategoryId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getAllDropsForCategoryId(categoryId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DROP_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++)
			Assert.assertEquals(data[i].getCategoryId(), categoryId, "Category Id : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods={"createDropForCategoryId"}, description = "Fetches Drop Details by DropId")
	public void getDropDetailsByDropId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getDropDetailsByDropId(dropId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DROP_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		Assert.assertEquals(data[0].getId(), dropId, "Drop Id : ");

	}
	
	
	
	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods={"createDropForCategoryId"}, dataProviderClass = ArtieServiceDP.class, dataProvider = "updateDropDetailsByDropId", description = "Updates Drop Details By DropId")
	public void updateDropDetailsByDropId(String optionsRequired,String name) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.updateDropDetailsByDropId(dropId,categoryId,optionsRequired, name);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DROP_UPDATED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		Assert.assertEquals(data[0].getOptionsRequired(), optionsRequired, "categoryId : ");
		Assert.assertEquals(data[0].getName(), name, "Drop Status : ");

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"createDropForCategoryId" },  description = "Closes a Drop for a DropId")
	public void closeDropByDropId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.closeDropByDropId(dropId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DROP_CLOSED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		// Assert.assertEquals(data[0].getCategoryId(), categoryId, "categoryId
		// : ");
		Assert.assertEquals(data[0].getStatus(), DropStatus.CLOSED.toString(), "Drop Status : ");
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"createDropForCategoryId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addNewRecipeUnderDropId", description = "Adds a new Recipe under a DropId")
	public void addNewRecipeUnderDropId(String title, String description, String imageURL,
			String decisionTreeData, String endDate, String optionsRequired, String collarType, String colourName,
			String styleId, String source, String recipeRefImageURL, String styleType, String ros, String asp,
			String styleId1, String source1, String recipeRefImageURL1, String styleType1, String ros1, String asp1, String payload)
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.addNewRecipeUnderDropId(
				dropId, title, description, imageURL, decisionTreeData, endDate, optionsRequired, collarType,
				colourName, styleId, source, recipeRefImageURL, styleType, ros, asp, styleId1, source1,
				recipeRefImageURL1, styleType1, ros1, asp1,payload);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.RECIPE_ADDED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getDropId(), dropId, "DropId : ");
		}
	//	RecipeReferenceProducts[] recipeReferenceProducts = data[0].getRecipeReferenceProducts();
		//Assert.assertEquals(recipeReferenceProducts.length, 2, "Reference Recipe Products :");
		recipeId=data[0].getId();
		RecipeReferenceProducts[] styles=data[0].getRecipeReferenceProducts();
		styleIdAdded=styles[0].getStyleId();
		productId=styles[0].getId();
		
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addNewRecipeUnderDropId" }, description = "Get recipe metadata for specified category")
	public void getRecipeMetadataByCategoryId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getRecipeMetadataByCategoryId(categoryId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.RECIPE_METADATA_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++)
			Assert.assertEquals(data[i].getCategoryId(), categoryId, "Category Id : ");
	}
	
	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" },description = "Get recipe metadata for specified category")
	public void listOfAllRecipesForADropId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.listOfAllRecipesForADropId(dropId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++)
			Assert.assertEquals(data[i].getDropId(), dropId, "DropId : ");
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" }, description = "Get recipe metadata for specified category")
	public void getRecipeDetailsByRecipeId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getRecipeDetailsByRecipeId(recipeId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++)
			Assert.assertEquals(data[i].getId(), recipeId, "RecipeId : ");
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

	
	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "updateRecipeDetailsByRecipeId",description = "updates an existing Recipe by RecipeId")
	public void updateRecipeDetailsByRecipeId(String createdBy, String createdOn,String lastModifiedOn, String title,  String imageURL,
			String decisionTreeData, String description,String status,String endDate, String optionsRequired, String optionsSubmitted, String optionsApproved,
			String score, 
			String colourName,String collarType, 
			String refProductId, String refProductCreatedBy,String refProductCreatedOn,String refProductlastModifiedOn,String styleId,String source ,String recipeRefImageURL, String styleType, String ros, String asp,String payload) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.updateRecipeDetailsByRecipeId( recipeId, createdBy,  createdOn, 
				lastModifiedOn,dropId,  title,   imageURL,
				 decisionTreeData,  description,status, endDate,  optionsRequired,  optionsSubmitted,  optionsApproved,
				 score, 
				 colourName, collarType, 
				 refProductId,  refProductCreatedBy, refProductCreatedOn, lastModifiedOn, styleId, source , recipeRefImageURL,  styleType,  ros,  asp,payload);
		
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_UPDATED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++){
			Assert.assertEquals(data[i].getId(), recipeId, "recipeid : ");
			Assert.assertEquals(data[i].getDropId(), dropId, "DropId : ");
		}
	}

	
	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" }, description = "Removes Specific products from Recipe")
	public void removeStylesFromRecipe()
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.removeStylesFromRecipe(recipeId, new String[]{productId});
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_UPDATED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		RecipeReferenceProducts[] styles=data[0].getRecipeReferenceProducts();
		//Assert.assertEquals(styles[0].gets, "INACTIVE", "Drop Status : ");
		for(int i=0;i<styles.length;i++){
			if(styles[i].getId().contains(styleIdAdded)){
				Assert.assertEquals(styles[i].getStyleType(),"INACTIVE","Style status :");
			}
		}
	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addNewRecipeUnderDropId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "archiveRecipeByRecipeId", description = "Archives a Recipe by recipeId")
	public void archiveRecipeByRecipeId(String score, String comment, String createdBy)
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.archiveRecipeByRecipeId(recipeId, score, comment, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_ARCHIEVED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), recipeId, "recipeid : ");
			Assert.assertEquals(data[0].getStatus(), RecipeStatus.ARCHIVED.toString(), "Recipe Status : ");
		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "approve_publish_recipe", description = "Approves/Publishes a Recipe ")
	public void approve_publish_recipe( String score, String comment, String assignee, String createdBy)
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.approve_publish_recipe(recipeId, score, comment, assignee, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_PUBLISHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), recipeId, "recipeid : ");
			Assert.assertEquals(data[0].getStatus(), RecipeStatus.PUBLISHED.toString(), "Recipe Status : ");
		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addNewRecipeUnderDropId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "closeRecipeByRecipeId", description = "Close a recipe by RecipeId")
	public void closeRecipeByRecipeId( String comment, String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.closeRecipeByRecipeId(recipeId, comment, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.RECIPE_CLOSED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), recipeId, "recipeid : ");
			Assert.assertEquals(data[0].getStatus(), RecipeStatus.CLOSED.toString(), "Recipe Status : ");
		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addDesignUnderRecipe", description = "Add a design under a recipe")
	public void addDesignUnderRecipe(String imageURL,String createdBy)
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.addDesignUnderRecipe(recipeId, imageURL, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DESIGN_ADDED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getRecipeId(), recipeId, "recipeid : ");
			Assert.assertEquals(data[0].getStatus(),DesignStatus.OPEN.toString() , "Recipe Status : ");
		}
		designId=data[0].getId();

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addDesignUnderRecipe" },  description = "Lists all designs under a Recipe")
	public void getAllDesignsOfRecipe() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getAllDesignsOfRecipe(recipeId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DESIGN_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getRecipeId(), recipeId, "recipeid : ");
			// Assert.assertEquals(data[0].getStatus(), "SUBMITTED", "Recipe
			// Status : ");
		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addDesignUnderRecipe" }, description = "Get details of a design by DesignId")
	public void getDesignDetailsByDesignId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getDesignDetailsByDesignId(designId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DESIGN_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), designId, "DesignId : ");
			// Assert.assertEquals(data[0].getStatus(), "SUBMITTED", "Recipe
			// Status : ");
		}

	}

	
	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addDesignUnderRecipe" },dataProviderClass = ArtieServiceDP.class, dataProvider = "updateDesignDetailsByDesignId", description = "Update design by DesignId")
	public void updateDesignDetailsByDesignId(String imageURL,String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.updateDesignDetailsByDesignId(designId, recipeId, imageURL, createdBy );
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(), RecipeSuccessCodes.DESIGN_UPDATED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
//		for (int i = 0; i < data.length; i++) {
//			Assert.assertEquals(data[i].getId(), designId, "DesignId : ");
//			// Assert.assertEquals(data[0].getStatus(), "SUBMITTED", "Recipe
//			// Status : ");
//		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addDesignUnderRecipe" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addCommentsOnDesign", description = "Add Comments on Design")
	public void addCommentsOnDesign( String createdBy, String text) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.addCommentsOnDesign(designId, createdBy, text);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),RecipeSuccessCodes.DESIGN_UPDATED_SUCCESSFULLY.getStatusCode(),
				"Error Message : "+artieServiceResponse.getStatus().getStatusMessage()+" Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), designId, "DesignId : ");
			
		}

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"addDesignUnderRecipe" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "approveDesign", description = "Approve design by DesignId")
	public void approveDesign(String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.approveDesign(designId,
				createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DESIGN_APPROVED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), designId, "DesignId : ");
			Assert.assertEquals(data[0].getStatus(), DesignStatus.APPROVED.toString(), "Recipe Status : ");

		}

	}

	@Test(enabled = true, groups = { "sanity", "Regression" }, dependsOnMethods = {
			"addDesignUnderRecipe","approveDesign" }, description = "gets All Approved designs available for a DropId")
	public void getapprovedDesignsForDropId() throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.getapprovedDesignsForDropId(dropId,DesignStatus.APPROVED.toString());
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DESIGN_FETCHED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), dropId, "dropId : ");
			Assert.assertEquals(data[0].getStatus(), DesignStatus.APPROVED.toString(), "Recipe Status : ");

		}

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addDesignUnderRecipe" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "rejectDesign", description = "Reject Design by DesignId")
	public void rejectDesign(String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.rejectDesign(designId,
				createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeSuccessCodes.DESIGN_REJECTED_SUCCESSFULLY.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		Data[] data = artieServiceResponse.getData();
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(data[i].getId(), designId, "DesignId : ");
			Assert.assertEquals(data[0].getStatus(), DesignStatus.REJECTED.toString(), "Recipe Status : ");
		}

	}

	/************* Negative Scenarios ****************/

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addCategory" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addDuplicateCategory", description = "Adds a Category which is already existing in db and verifies the error response")
	public void addDuplicateCategory(String name, String createdBy) throws Exception {

		ArtieServiceResponse artieServiceResponse = artieServiceHelper.addCategory(name, createdBy);
		artieServiceHelper.addCategory(name, createdBy);

		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.CATEGORY_ALREADY_EXIST.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"createDropForCategoryId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "createDuplicateDropForCategoryId", description = "Creates a Drop for a CategoryId where a Open drop is already available and validates the error response")
	public void createDuplicateDropForCategoryId(String payload) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.createDropForCategoryId(payload);

		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.DROP_ALREADY_EXIST.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "getDropDetailsForNonExistingDropId", description = "Tries to Fetch Drop Details for an Non existing DropId")
	public void getDropDetailsForNonExistingDropId(String dropId) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getDropDetailsByDropId(dropId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.INVALID_ID.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "updateDropDetailsForNonExistingDropId", description = "Updates Drop Details for an Non existing DropId and fails to update")
	public void updateDropDetailsForNonExistingDropId(String DropId,String optionsRequired,
			String name) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.updateDropDetailsByDropId(DropId, categoryId, optionsRequired, name);

		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.RECORD_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "closeDropForNonExistingDropId", description = "Closes a Drop for a DropId which is not valid/available and returns an error")
	public void closeDropForNonExistingDropId(String dropId) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.closeDropByDropId(dropId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.DROP_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " -- Artie Service Response statuscode : ");
		// RecipeErrorCodes.r

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "getRecipeDetailsForInvalidRecipeId", description = "Validationg Error response when request is made to fetch recipe details for an invalid recipeId")
	public void getRecipeDetailsForInvalidRecipeId(String recipeId) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getRecipeDetailsByRecipeId(recipeId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.INVALID_ID.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " -- Artie Service Response statuscode : ");
		
	}

//	@Test(enabled = true, groups = { "sanity",
//			"Regression" },dependsOnMethods = {
//	"addNewRecipeUnderDropId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "addDuplicateRecipeUnderDropId", description = "Validates the Error Response when api is trying to add a duplicate Recipe for a dropId ")
//	public void addDuplicateRecipeUnderDropId(String title, String description, String imageURL,
//			String decisionTreeData, String endDate, String optionsRequired, String collarType, String colourName,
//			String styleId, String source, String recipeRefImageURL, String styleType, String ros, String asp,
//			String styleId1, String source1, String recipeRefImageURL1, String styleType1, String ros1, String asp1)
//			throws Exception {
//		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.addNewRecipeUnderDropId(
//				dropId, title, description, imageURL, decisionTreeData, endDate, optionsRequired, collarType,
//				colourName, styleId, source, recipeRefImageURL, styleType, ros, asp, styleId1, source1,
//				recipeRefImageURL1, styleType1, ros1, asp1,payload);
//		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
//				RecipeErrorCodes.RECIPE_ALREADY_EXISTS.getStatusCode(),
//				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
//						+ " -- Artie Service Response statuscode : ");
//
//	}

//	@Test(enabled = true, groups = { "sanity",
//			"Regression" }, dependsOnMethods = {
//	"addNewRecipeUnderDropId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addRecipeWithInvalidStyleIdForDropId", description = "Validates the Error Response when api is trying to add invalid style_ids in Recipe creation for a dropId ")
//	public void addRecipeWithInvalidStyleIdForDropId( String title, String description, String imageURL,
//			String decisionTreeData, String endDate, String optionsRequired, String collarType, String colourName,
//			String styleId, String source, String recipeRefImageURL, String styleType, String ros, String asp,
//			String styleId1, String source1, String recipeRefImageURL1, String styleType1, String ros1, String asp1)
//			throws Exception {
//		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper.addNewRecipeUnderDropId(
//				dropId, title, description, imageURL, decisionTreeData, endDate, optionsRequired, collarType,
//				colourName, styleId, source, recipeRefImageURL, styleType, ros, asp, styleId1, source1,
//				recipeRefImageURL1, styleType1, ros1, asp1);
//		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
//				RecipeErrorCodes.RECIPE_PRODUCT_NOT_FOUND.getStatusCode(),
//				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
//						+ " -- Artie Service Response statuscode : ");
//
//	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addNewRecipeUnderDropId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "removeInvalidStylesFromRecipe", description = "Validates the Error Response when api is trying to remove a style_id which is not available in Recipe Reference Products")
	public void removeInvalidStylesFromRecipe(String[] productsToRemove) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.removeStylesFromRecipe(recipeId, productsToRemove);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.RECIPE_PRODUCT_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ "-- Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addNewRecipeUnderDropId" },dataProviderClass = ArtieServiceDP.class, dataProvider = "archiveRecipeWithoutComments", description = "Validates the Error Response when api is trying to archive a recipe without providing comments")
	public void archiveRecipeWithoutComments(String score, String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.archiveRecipeWithoutComments(recipeId, score, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.RECIPE_REJECT_REASON_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ "-- Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addNewRecipeUnderDropId" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "approvePublishRecipeWithoutAssignee", description = "Validates the Error Response when api is trying to approve/Publish a recipe without providing assignee details")
	public void approvePublishRecipeWithoutAssignee(String score, String comment, String createdBy)
			throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.approvePublishRecipeWithoutAssignee(recipeId, score, comment, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.RECIPE_ASSIGNEE_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dependsOnMethods = {
	"addDesignUnderRecipe" },dataProviderClass = ArtieServiceDP.class, dataProvider = "addDuplicateDesignUnderRecipe", description = "Validates the Error Response when api is trying to add a duplicate design for a recipe")
	public void addDuplicateDesignUnderRecipe(String imageURL, String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.addDesignUnderRecipe(recipeId, imageURL, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.DESIGN_ALREADY_EXISTS.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" },dependsOnMethods = {
	"addDesignUnderRecipe" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "addDesignToAnInvalidRecipeId", description = "Validates the Error Response when api is trying to add Designs to an Invalid/Not available RecipeId")
	public void addDesignToAnInvalidRecipeId(String recipeId,String imageURL, String createdBy) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.addDesignUnderRecipe(recipeId, imageURL, createdBy);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.DESIGN_CREATION_FAILURE.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}
	

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "getDesignDetailsForInvalidDesignId", description = "Validates the Error Response when api is trying to fetch Design details for an Invalid/Not available DesignId")
	public void getDesignDetailsForInvalidDesignId(String designId) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.getDesignDetailsByDesignId(designId);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.INVALID_ID.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");

	}

	@Test(enabled = true, groups = { "sanity",
			"Regression" }, dataProviderClass = ArtieServiceDP.class, dataProvider = "addCommentsForInvalidDesignId", description = "Validates the Error Response when api is trying to add comments for an Invalid/Not available DesignId")
	public void addCommentsForInvalidDesignId(String designId, String createdBy, String text) throws Exception {
		ArtieServiceResponse artieServiceResponse = (ArtieServiceResponse) artieServiceHelper
				.addCommentsOnDesign(designId, createdBy, text);
		Assert.assertEquals(artieServiceResponse.getStatus().getStatusCode(),
				RecipeErrorCodes.DESIGN_NOT_FOUND.getStatusCode(),
				"Error Message : " + artieServiceResponse.getStatus().getStatusMessage()
						+ " Artie Service Response statuscode : ");
		
	}
	
//	@AfterClass
//	public void tearDown(){
//		artieServiceHelper.deleteFifaDataConfigForCategory(Long.valueOf(categoryId));
//	}

}
