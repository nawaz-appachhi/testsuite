package com.myntra.apiTests.inbound.dp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myntra.apiTests.inbound.FIFA.ArtieServiceTests;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.recipe.client.entry.DropEntry;
import com.myntra.recipe.client.entry.RecipeEntry;
import com.myntra.recipe.client.entry.RecipeReferenceProductEntry;
import com.myntra.recipe.enums.ProductType;

public class ArtieServiceDP {
	
	public static Date date = new Date();
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static String enddate=df.format(date);
	
	public static String randomGen(int length) {
        Random random = new Random();
        int n = random.nextInt(10);
        for (int i = 0; i < length - 1; i++) {
            n = n * 10;
            n += random.nextInt(10);
        }       
    String ar = Integer.toString(Math.abs(n));
    return ar;
	}
	
	@DataProvider(name = "addCategory")
	public static Object[][] addCategory(ITestContext testContext) throws Exception {
			String[] arr1 = { "Men_T-Shirt"+randomGen(4),"sravan.kumar" };
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	@DataProvider(name = "getAllDropsForACategoryId")
	public static Object[][] getAllDropsForACategoryId(ITestContext testContext) throws Exception {
			String[] arr1 = { "16"};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	@DataProvider(name = "getDropDetailsByDropId")
	public static Object[][] getDropDetailsByDropId(ITestContext testContext) throws Exception {
			String[] arr1 = { "11"};
			Object[][] dataSet = new Object[][] { arr1,  };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	// "categoryId":"16",
	// "startDate":"20160826",
	// "endDate":"20160828",
	// "optionsRequired":10,
	// "name":"test drop",
	// "createdBy":"sravan.kumar"

	@DataProvider(name = "createDropForCategoryId")
	public static Object[][] createDropForCategoryId(ITestContext testContext) throws Exception {
		
		
		DropEntry dropEntry=new DropEntry();
		dropEntry.setCategoryId(16L);
		dropEntry.setCatalogStartDate(date);
		dropEntry.setCatalogEndDate(date);
		dropEntry.setCreatedBy("sravan.kumar");
		dropEntry.setCreatedOn(date);
		dropEntry.setEndDate(date);
		dropEntry.setName("test drop_" + randomGen(3));
		dropEntry.setOptionsRequired(10);
		dropEntry.setStartDate(date);
		dropEntry.setOrderStartDate(date);
		dropEntry.setOrderEndDate(date);
		dropEntry.setRecipeEndDate(date);
		
		String payload = APIUtilities.getObjectToJSON(dropEntry);
		
		String[] arr1 = { payload};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "updateDropDetailsByDropId")
	public static Object[][] updateDropDetailsByDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { randomGen(2) ,"test drop_" + randomGen(3)};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**********Close Drop By DropId******
	 * {
	    "id":18,
	   
	}*/
	@DataProvider(name = "closeDropByDropId")
	public static Object[][] closeDropByDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "11"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getRecipeMetadataByCategoryId")
	public static Object[][] getRecipeMetadataByCategoryId(ITestContext testContext) throws Exception {
		String[] arr1 = { "16"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "listOfAllRecipesForADropId")
	public static Object[][] listOfAllRecipesForADropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "11"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getRecipeDetailsByRecipeId")
	public static Object[][] getRecipeDetailsByRecipeId(ITestContext testContext) throws Exception {
		String[] arr1 = { "83"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	
	/***************** Add New Recipe Under DropId Payload*****************
	 * "dropId":,
	 * "title":, 
	 * "description": , 
	 * "imageURL":, 
	 * "decisionTreeData": , 
	 * "endDate": 
	 * "optionsRequired": , 
	 * "recipeData": { "Colour Name": , "Collar Type":
	 * }, 
	 * "recipeReferenceProducts": [
	 * "styleId": , 
	 * "source": , 
	 * "imageURL": , 
	 * "styleType": ,
	 * "styleParams": { "ros": , "asp": } } ] }
	 */
	
	
	@DataProvider(name = "addNewRecipeUnderDropId")
	public static Object[][] addNewRecipeUnderDropId(ITestContext testContext) throws Exception {
		RecipeEntry recipeEntry=new RecipeEntry();
		recipeEntry.setDropId(Long.valueOf(ArtieServiceTests.dropId));
		recipeEntry.setTitle("Sample Recipe");
		recipeEntry.setDescription("Sample recipe");
		recipeEntry.setImageURL("http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg");
		recipeEntry.setDecisionTreeData("decision tree");
		recipeEntry.setEndDate(date);
		recipeEntry.setOptionsApproved(10);
		ObjectMapper objMapper = new ObjectMapper();
		
		JSONObject recipeData = new JSONObject();
        recipeData.put("baseFabricColours", "Pink");
        recipeData.put("colourCombinations", "grey");
        recipeData.put("bodies", "solid");
        recipeData.put("keyAttributes", "pattern");
        recipeData.put("others", "NA");
        recipeData.put("secondaryAttributesManual", "green");
        JsonNode recipeData1 = objMapper.readTree(recipeData.toString());
		recipeEntry.setRecipeData(recipeData1);
		
		Set<RecipeReferenceProductEntry> recipeReferenceProducts=new HashSet<RecipeReferenceProductEntry>();
		RecipeReferenceProductEntry recipeReferenceProductEntry=new RecipeReferenceProductEntry();
		recipeReferenceProductEntry.setStyleId("123456");
		recipeReferenceProductEntry.setSource("CMS");
		recipeReferenceProductEntry.setImageURL("http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg");
		recipeReferenceProductEntry.setStyleType(ProductType.TS);
//		Map<String,String> styleParams=new HashMap<String, String>();
//		styleParams.put("asp", "2.4");
//		styleParams.put("ros", "1.98");
//		recipeReferenceProductEntry.setStyleParams(styleParams);
		recipeReferenceProducts.add(recipeReferenceProductEntry);
		recipeEntry.setRecipeReferenceProducts(recipeReferenceProducts);
		
		String payload = objMapper.writeValueAsString(recipeEntry);
		System.out.println(payload);
	
	
		

		//recipeEntry.setRecipeData(recipeData1);;

		
		
		String[] arr1 = { "Sample Recipe","Sample recipe","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg","decision tree",enddate,"10",
				"mandarin","blue",
				"1451423","CMS","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg","TS","3","499",
				"1482794","CMS","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg","BS",".3","499",
				payload};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	/******************Update Recipe under a RecipeId***********
	 * { "id":, 
	 * "createdBy":,
	 * "createdOn":, 
	 * "lastModifiedOn": , 
	 * "dropId":,
	 * "title":, 
	 * "imageURL":, 
	 * "decisionTreeData": , 
	 * "description": , 
	 * "status": ,
	 * "endDate": 
	 * "optionsRequired": , 
	 * "optionsSubmitted":, 
	 * "optionsApproved":,
	 * "score": , 
	 * "assignee": , 
	 * "recipeData": { 
	 * "Colour Name": , 
	 * "Collar Type":
	 * }, 
	 * "recipeReferenceProducts": [
	 *  { "id": , "createdBy": , "createdOn": ,
	 * "lastModifiedOn":, 
	 * "styleId": , 
	 * "source": , 
	 * "imageURL": , 
	 * "styleType": ,
	 * "styleParams": { "ros": , "asp": } } ] }
	 */
	
	@DataProvider(name = "updateRecipeDetailsByRecipeId")
	public static Object[][] updateRecipeDetailsByRecipeId(ITestContext testContext) throws Exception {
		RecipeEntry recipeEntry=new RecipeEntry();
		recipeEntry.setDropId(Long.valueOf(ArtieServiceTests.dropId));
		recipeEntry.setTitle("Sample Recipe");
		recipeEntry.setDescription("Sample recipe");
		recipeEntry.setImageURL("http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg");
		recipeEntry.setDecisionTreeData("decision tree");
		recipeEntry.setEndDate(date);
		recipeEntry.setOptionsApproved(10);
		ObjectMapper objMapper = new ObjectMapper();
		
		JSONObject recipeData = new JSONObject();
        recipeData.put("baseFabricColours", "Orange");
        recipeData.put("colourCombinations", "Yellow");
        recipeData.put("bodies", "solid");
        recipeData.put("keyAttributes", "pattern");
        recipeData.put("others", "NA");
        recipeData.put("secondaryAttributesManual", "green");
        JsonNode recipeData1 = objMapper.readTree(recipeData.toString());
		recipeEntry.setRecipeData(recipeData1);
		
		Set<RecipeReferenceProductEntry> recipeReferenceProducts=new HashSet<RecipeReferenceProductEntry>();
		RecipeReferenceProductEntry recipeReferenceProductEntry=new RecipeReferenceProductEntry();
		recipeReferenceProductEntry.setStyleId("123456");
		recipeReferenceProductEntry.setSource("CMS");
		recipeReferenceProductEntry.setImageURL("http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg");
		recipeReferenceProductEntry.setStyleType(ProductType.TS);
		Map<String,String> styleParams=new HashMap<String, String>();
		styleParams.put("asp", "2.4");
		styleParams.put("ros", "1.98");
		recipeReferenceProductEntry.setStyleParams(styleParams);
		recipeReferenceProducts.add(recipeReferenceProductEntry);
		recipeEntry.setRecipeReferenceProducts(recipeReferenceProducts);
		
		String payload = objMapper.writeValueAsString(recipeEntry);
		System.out.println(payload);
		
		
		String[] arr1 = { "system", "1472376825000", "1472376825000", "Sample Recipe" + randomGen(2),
				"http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg", "decision tree", "Sample Recipe" + randomGen(2), "DRAFT", enddate, "10", "0", "0", "0",
				"blue", "mandarin", 
				"2154", "system", "1472376825000", "1472376825000", "1451423", "CMS", "http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg", "TS",
				"3", "499",payload };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	/**
	{
	    "id":"<recipe_id>",
	    "productsToRemove":[<id1>,<id2>,..] //id=recipe_reference_product table id field
	}
	**/
	
	@DataProvider(name = "removeStylesFromRecipe")
	public static Object[][] removeStylesFromRecipe(ITestContext testContext) throws Exception {
		Object[] productsToRemove={3332};
		Object[] arr1 = { productsToRemove};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	 * {
      "id": 43,
      "score":0.2,
      "comment":"Too bad",
      "createdBy":"anuj.modi"
	}*/
	@DataProvider(name = "archiveRecipeByRecipeId")
	public static Object[][] archiveRecipeByRecipeId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0.2","Too bad","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	 * {
      "id": 43,
      "score":4,
      "comment":"Nice",
      "assignee":"sravan.kumar",
      "createdBy":"anuj.modi"
	}*/
	
	@DataProvider(name = "approve_publish_recipe")
	public static Object[][] approve_publish_recipe(ITestContext testContext) throws Exception {
		String[] arr1 = { "0.2","Too bad","sravan.kumar","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	 * {
      "id": 43,
      "comment":"All Designs Done",
      "createdBy":"sravan.kumar"
	}*/
	
	@DataProvider(name = "closeRecipeByRecipeId")
	public static Object[][] closeRecipeByRecipeId(ITestContext testContext) throws Exception {
		String[] arr1 = { "All Designs Done","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	/**
	{
	    "recipeId":18,
	    "imageURL":"sampleImageURL",
	    "createdBy":"anuj.modi"
	}
	**/
	
	@DataProvider(name = "addDesignUnderRecipe")
	public static Object[][] addDesignUnderRecipe(ITestContext testContext) throws Exception {
		String[] arr1 = { "http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-98147-1.jpg","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	
	@DataProvider(name = "getAllDesignsOfRecipe")
	public static Object[][] getAllDesignsOfRecipe(ITestContext testContext) throws Exception {
		String[] arr1 = { "83"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getDesignDetailsByDesignId")
	public static Object[][] getDesignDetailsByDesignId(ITestContext testContext) throws Exception {
		String[] arr1 = { "45"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	/**{
    "recipeId":18,
    "imageURL":"sampleImageURL-updated",
    "createdBy":"anuj.modi"
	}*/
	@DataProvider(name = "updateDesignDetailsByDesignId")
	public static Object[][] updateDesignDetailsByDesignId(ITestContext testContext) throws Exception {
		String[] arr1 = { "http://assets.myntassets.com/assets/images/2016/9/17/11474110231613-test-design-upload-women.jpg","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**{
    "designId":3,
    "createdBy":"anuj.modi",
    "text":"Good design"
	}*/
	@DataProvider(name = "addCommentsOnDesign")
	public static Object[][] addCommentsOnDesign(ITestContext testContext) throws Exception {
		String[] arr1 = { "sravan.kumar","Good design"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * { "id":3, "createdBy":"anuj.modi" }
	 */
	@DataProvider(name = "approveDesign")
	public static Object[][] approveDesign(ITestContext testContext) throws Exception {
		String[] arr1 = { "sravan.kumar" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * { "id":3, "createdBy":"anuj.modi" }
	 */
	@DataProvider(name = "rejectDesign")
	public static Object[][] rejectDesign(ITestContext testContext) throws Exception {
		String[] arr1 = {  "sravan.kumar" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * 
	 * { "name":"Mens_TShirt", "createdBy":"sravan.kumar" }
	 */

	@DataProvider(name = "addDuplicateCategory")
	public static Object[][] addDuplicateCategory(ITestContext testContext) throws Exception {
		String[] arr1 = { "Mens_TShirt", "sravan.kumar" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	/**
	 * "categoryId":"16", "startDate":"20160826", "endDate":"20160828",
	 * "optionsRequired":10, "name":"test drop", "createdBy":"sravan.kumar"
	 **/

	@DataProvider(name = "createDuplicateDropForCategoryId")
	public static Object[][] createDuplicateDropForCategoryId(ITestContext testContext) throws Exception {

		DropEntry dropEntry=new DropEntry();
		dropEntry.setCategoryId(16L);
		dropEntry.setCatalogStartDate(date);
		dropEntry.setCatalogEndDate(date);
		dropEntry.setCreatedBy("sravan.kumar");
		dropEntry.setCreatedOn(date);
		dropEntry.setEndDate(date);
		dropEntry.setName("test_duplicate_drop");
		dropEntry.setOptionsRequired(10);
		dropEntry.setStartDate(date);
		dropEntry.setOrderStartDate(date);
		dropEntry.setOrderEndDate(date);
		dropEntry.setRecipeEndDate(date);
		
		String payload = APIUtilities.getObjectToJSON(dropEntry);
		String[] arr1 = { payload };
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "getDropDetailsForNonExistingDropId")
	public static Object[][] getDropDetailsForNonExistingDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0" };
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	/**
	  {
    "categoryId":16,
    "startDate":"2016-08-01",
    "endDate":"2016-08-31",
    "optionsRequired":10,
    "name":"Test Drop 1",
    "createdBy":"anuj.modi"
	}
	 */
	
	@DataProvider(name = "updateDropDetailsForNonExistingDropId")
	public static Object[][] updateDropDetailsForNonExistingDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0",randomGen(2) ,"Test Drop 1"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/** {
	    "id":18,
	   
	}*/
	@DataProvider(name = "closeDropForNonExistingDropId")
	public static Object[][] closeDropForNonExistingDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getRecipeDetailsForInvalidRecipeId")
	public static Object[][] getRecipeDetailsForInvalidRecipeId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/***************** Add duplicate Recipe Under DropId Payload*****************
	 * "dropId":,
	 * "title":, 
	 * "description": , 
	 * "imageURL":, 
	 * "decisionTreeData": , 
	 * "endDate": 
	 * "optionsRequired": , 
	 * "recipeData": { "Colour Name": , "Collar Type":
	 * }, 
	 * "recipeReferenceProducts": [
	 * "styleId": , 
	 * "source": , 
	 * "imageURL": , 
	 * "styleType": ,
	 * "styleParams": { "ros": , "asp": } } ] }
	 */
	
	
	@DataProvider(name = "addDuplicateRecipeUnderDropId")
	public static Object[][] addDuplicateRecipeUnderDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "Sample Recipe","Sample Recipe","image url","decision tree",enddate,"10",
				"mandarin","blue",
				"1451423","CMS","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg","TS","3","499",
				"1482794","CMS","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-981472031511247-1.jpg","BS",".3","499"};
		
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
	@DataProvider(name = "addRecipeWithInvalidStyleIdForDropId")
	public static Object[][] addRecipeWithInvalidStyleIdForDropId(ITestContext testContext) throws Exception {
		String[] arr1 = { "T-Shirts Drop","T-Shirts Drop","image url","decision tree",enddate,"10",
				"mandarin","blue",
				"00","CMS","testURL","TS","3","499",
				"11","CMS","testURL","BS",".3","499"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	{
	    "id":"<recipe_id>",
	    "productsToRemove":[<id1>,<id2>,..] //id=recipe_reference_product table id field
	}
	**/
	
	@DataProvider(name = "removeInvalidStylesFromRecipe")
	public static Object[][] removeInvalidStylesFromRecipe(ITestContext testContext) throws Exception {
		String[] productsToRemove={"0"};
		Object[] arr1 = { productsToRemove};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	 * {
      "id": 43,
      "score":0.2,
      "comment":"Too bad",
      "createdBy":"anuj.modi"
	}*/
	@DataProvider(name = "archiveRecipeWithoutComments")
	public static Object[][] archiveRecipeWithoutComments(ITestContext testContext) throws Exception {
		String[] arr1 = { "0.2","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	 * {
      "id": 43,
      "score":4,
      "comment":"Nice",
      "assignee":"sravan.kumar",
      "createdBy":"anuj.modi"
	}*/
	
	@DataProvider(name = "approvePublishRecipeWithoutAssignee")
	public static Object[][] approvePublishRecipeWithoutAssignee(ITestContext testContext) throws Exception {
		String[] arr1 = { "0.2","Too bad","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	/**
	{
	    "recipeId":18,
	    "imageURL":"sampleImageURL",
	    "createdBy":"anuj.modi"
	}
	**/
	
	@DataProvider(name = "addDuplicateDesignUnderRecipe")
	public static Object[][] addDuplicateDesignUnderRecipe(ITestContext testContext) throws Exception {
		String[] arr1 = { "http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-98147-1.jpg","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "addDesignToAnInvalidRecipeId")
	public static Object[][] addDesignToAnInvalidRecipeId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0","http://myntra.myntassets.com/assets/images/1378908/2016/8/24/11472031511202-LOCOMOTIVE-Men-Black-Printed-Polo-Collar-T-Shirt-98147-1.jpg","sravan.kumar"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	
	@DataProvider(name = "getDesignDetailsForInvalidDesignId")
	public static Object[][] getDesignDetailsForInvalidDesignId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}
	//
	/**{
    "designId":3,
    "createdBy":"anuj.modi",
    "text":"Good design"
	}*/
	@DataProvider(name = "addCommentsForInvalidDesignId")
	public static Object[][] addCommentsForInvalidDesignId(ITestContext testContext) throws Exception {
		String[] arr1 = { "0","sravan.kumar","Good design"};
		Object[][] dataSet = new Object[][] { arr1, };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	

}
