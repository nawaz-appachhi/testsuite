package com.myntra.apiTests.portalservices.cms;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SheetHelper extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SheetHelper.class);
	String errorInfo;
	String[] errorMessage = new String[3];
	String vbErrorInfo;
	String vbErrorMsg;
	

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	public MultipartEntityBuilder multipartBuilder(String file) {

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addPart("file", new FileBody(new File(file)));
		return builder;
	}
	
	public Svc uploadDIYSheet(String file, String studio, String source, String lotName) throws Exception {
		 MultipartEntityBuilder builder = multipartBuilder(file);
		 HashMap<String, String> headers = new HashMap<String, String>();
		 headers.put("Authorization","Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		 headers.put("Accept","application/json");
		 headers.put("Content-Type","multipart/form-data");
		 
		 // 1082 mentioned in url below is a test_vendor. Don't use any other id.
		 Svc service = HttpExecutorService.executeHttpServiceForUpload("diy/import/sheetValidator/1082?studio="+studio+"&source="+source+"&lotName="+lotName, null, SERVICE_TYPE.CMS_CATALOG.toString(),
					HTTPMethods.POST, builder, headers);
	     
		 return service;
	}
	
	public void assertValidateSuccessEntries(String responseAsString){
		AssertJUnit.assertNotNull("Task Id is null", JsonPath.read(responseAsString, "$.data.sheetValidatorSuccessEntry.taskId"));
		AssertJUnit.assertNotNull("Sheet url is null", JsonPath.read(responseAsString, "$.data.sheetValidatorSuccessEntry.sheetUrl"));
	}
	
	public String getDIYTaskIdFromSheetUploadResponse(String responseAsString){
		String taskId = JsonPath.read(responseAsString, "$.data.sheetValidatorSuccessEntry.taskId").toString();
		return taskId;
	}
	
	public String getDIYTaskResponseObject(String responseAsString){
		String taskId = getDIYTaskIdFromSheetUploadResponse(responseAsString);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_TASK, new String[]{taskId});
		return req.returnresponseasstring();
	}
	
	public String getDIYGetStylesResponseObject(String responseAsString){
		String taskId = getDIYTaskIdFromSheetUploadResponse(responseAsString);
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_GETSTYLES, new String[]{taskId});
		return req.returnresponseasstring();
	}
	
	public void errorEntry(ErrorEntryType errorType, String[] dynamicString){
		switch(errorType){
			case BU_MISMATCH_ERROR1:
				errorInfo = "Sheet validation error: BU Validation failed!";
				errorMessage[0] = "This lot seems to have multiple Brand - Article Type - Gender combinations. Please create new lots for each of the combinations: ";
				for(int i=0 ; i<dynamicString.length ; i++){
					int j=i+1;
					errorMessage[0] = errorMessage[0]+ j + ". {"+dynamicString[i]+"} ";
				}
					break;
			
			case BU_MISMATCH_ERROR2:
				errorInfo = "Sheet validation error: BU Validation failed!";
				for(int i=0 ; i<dynamicString.length ; i++){
					errorMessage[i] = "We are unable to verify the {"+dynamicString[i]+"} combination. Please contact your on-boarding specialist for further assistance.";
				}
				errorMessage[dynamicString.length] = "We are unable to verfiy the Brand-ArticleType-Gender combination. Please contact your on-boarding specialist for further assistance";
				break;
			
			case BRAND_MISMATCH_ERROR:
				errorInfo = "Sheet validation error: Brand Validation failed!";
				errorMessage[0] = "All the products uploaded in the sheet do not belong to one brand";
				break;
				
			case EMPTY_SHEET_ERROR:
				errorInfo = "Empty file obtained";
				errorMessage[0] = "Please upload a file with entries";
				break;
				
			case INVALID_STUDIO:
				errorInfo = "Sheet validation error: Studio Validation failed!";
				errorMessage[0] = "You have entered: test which is not a valid Partner Portal Studio. Please choose a studio from the dropdown provided.";
				break;
				
			case BU_PARSE_ERROR:
				errorInfo = "Sheet validation error: BU Validation failed!";
				errorMessage[0] ="Brand-ArticleType-Gender combinations could not be parsed from the input sheet, insufficient data provided for one or two rows";
				break;
				
			case INVALID_GTIN:
				errorMessage[0] ="GTIN can only contain numbers";
				break;
				
			case EMPTY_VAN:
				errorMessage[0] ="Vendor article number cannot be empty";
				break;
				
			case EMPTY_VENDORNAME:
				errorMessage[0] ="Vendor article name cannot be empty";
				break;
				
			case INVALID_ARTICLE:
				errorMessage[0] ="Article type should match with sheet name which represents article type";
				break;
				
			case EMPTY_BRAND:
				errorMessage[0] ="Brand cannot be empty. Please select a value from the drop down.";
				break;
				
			case INVALID_MRP:
				errorMessage[0] ="MRP cannot be empty or non numeric";
				break;
				
			case ZERO_MRP:
				errorMessage[0]="MRP is not a +ve value";
				break;
				
			case EMPTY_PRODUCTSIZE:
				errorMessage[0] ="Product Size cannot be null. it is equivalent of vendorSize but uses myntra's terminology";
				break;
				
			case EMPTY_HSN:
				errorMessage[0]="HSN cannot be empty";
				break;
				
			case NONNUMERIC_HSN:
				errorMessage[0] ="HSN must be a number, value entered in the sheet: "+dynamicString[0];
				break;
				
			case PROD_LEVEL_VALIDATIONS:
				errorMessage[0]="Product Level validation failed: "+dynamicString[0]+" is not same for the article type: "+dynamicString[1]+" and Style Group ID: "+dynamicString[2];
				break;
				
			case NON_EIGHTDIGIT_HSN:
				errorMessage[0] = "We accept only 8-digit HSNs";
				break;
				
			case MISSING_HSN_IN_REGISTRY:
				errorMessage[0] = "HSN '"+dynamicString[0]+"' not present in main registry";
				break;
			
			case SKUCREATION_DIFF_GRPID:
				errorInfo = "Style Group Validation failed! All products do not belong to one style group ID";
				errorMessage[0] = "Style Group Validation failed! The list of SKUs should all belong to only one product. This API creates only one product at a time";
				break;
				
			case SKUCREATION_EMPTYSKUCODE:
				errorMessage[0] = "Vendor SKU Code can't be null or empty";
				break;
				
			case SKUCREATION_INVALIDGTIN:
				errorMessage[0] = "GTIN must be a number, value entered in sheet: "+dynamicString[0];
				break;
			
			case SKUADDITION_DIFFSELLER:
				errorMessage[0] = "The seller mapped to this style:"+dynamicString[0]+" is incorrect. This style should be mapped to this seller: 21 with seller ID = 21. Please reach out to seller-change@myntra.com with this message.";
				break;
				
			default:
				break;
		}
	}
	
	public void assertValidateErrorEntries(String responseAsString, ErrorEntryType errorType, String[] BAG_Combinations){
		errorEntry(errorType, BAG_Combinations);
		AssertJUnit.assertEquals("errorInfo incorrect", errorInfo, JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[0].errorInfo").toString());
		List<String> errorMsgs = JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[*].errorMessage");
		AssertJUnit.assertEquals("errorMessage incorrect", errorMessage[0], JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[0].errorMessage").toString());
		if(errorType==ErrorEntryType.BU_MISMATCH_ERROR2 && errorMsgs.size() == 3){
			AssertJUnit.assertEquals("errorInfo incorrect", errorInfo, JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[1].errorInfo").toString());
			AssertJUnit.assertEquals("errorMessage incorrect", errorMessage[1], JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[1].errorMessage").toString());
			AssertJUnit.assertEquals("errorInfo incorrect", errorInfo, JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[2].errorInfo").toString());
			AssertJUnit.assertEquals("errorMessage incorrect", errorMessage[2], JsonPath.read(responseAsString, "$.data.sheetValidatorErrorEntries[2].errorMessage").toString());
		}
	}
	
	public void assertDIYSyncErrorEntries(String response, ErrorEntryType[] errorMsgs, String[] dynamicString){
		List<String> errorMsgsFromResponse = JsonPath.read(response, "$.data.sheetValidatorErrorEntries[*].errorMessage");
		for(int i=0;i<errorMsgs.length;i++){
			errorEntry(errorMsgs[i], dynamicString);
			AssertJUnit.assertTrue("Message "+errorMessage[0]+" not present in response", errorMsgsFromResponse.contains(errorMessage[0]));
		}
	}
	
	/** dynamicString should be constant or null for all errorMsgs else use this method only when response has only 1 error message **/
	public void assertSyncSKUCreationErrorEntries(String response, ErrorEntryType[] errorMsgs, String[] dynamicString){
		List<String> errorMsgsFromResponse = JsonPath.read(response, "$.data.errorEntries[*].errorMessage");
		for(int i=0;i<errorMsgs.length;i++){
			errorEntry(errorMsgs[i], dynamicString);
			AssertJUnit.assertTrue("Message "+errorMessage[0]+" not present in response", errorMsgsFromResponse.contains(errorMessage[0]));
		}
	}
	
	public enum ErrorEntryType {BU_MISMATCH_ERROR1, BU_MISMATCH_ERROR2, BRAND_MISMATCH_ERROR, EMPTY_SHEET_ERROR, INVALID_STUDIO, BU_PARSE_ERROR, INVALID_GTIN, EMPTY_VAN, EMPTY_VENDORNAME, INVALID_ARTICLE, EMPTY_BRAND, INVALID_MRP, EMPTY_PRODUCTSIZE,
		EMPTY_HSN, NONNUMERIC_HSN, PROD_LEVEL_VALIDATIONS, SKUCREATION_DIFF_GRPID, NON_EIGHTDIGIT_HSN, MISSING_HSN_IN_REGISTRY, ZERO_MRP, SKUCREATION_EMPTYSKUCODE, SKUADDITION_DIFFSELLER, SKUCREATION_INVALIDGTIN}
	
	
	public boolean verifyDIYTaskStatus(String status, String uploadDIYSheetResponse) throws InterruptedException{
		Thread.sleep(60000);
		String diyTaskResponse = getDIYTaskResponseObject(uploadDIYSheetResponse);
		if (!JsonPath.read(diyTaskResponse, "$.data[0].status").toString()
				.equals(status)) {
			Thread.sleep(60000);
		}
		return JsonPath.read(diyTaskResponse, "$.data[0].status").toString()
				.equals(status);
	}
	
	public RequestGenerator rejectDIYLot(String taskId){
		RequestGenerator req = e2eRegressionCmsHelper
				.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_REJECTLOT,
						new String[] { taskId });
		return req;
	}
	
	public String getInCurationSearchResponse(String taskId, String styleStatus){
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.DIY_INCURATIONSTYLESEARCH, new String[]{taskId, "QC%20Reject"});
		String styleSearchResponse = req.returnresponseasstring();
		return styleSearchResponse;
	}
	
	public List<String> getStylesFromIncurationSearchAPI(String taskId, String styleStatus){
		String styleSearchResponse = getInCurationSearchResponse(taskId, styleStatus);
		List<String> styleList = JsonPath.read(styleSearchResponse, "$.data[*].id");
		List<String> styleListAsString = new ArrayList<String>();
		for(int i=0;i<styleList.size();i++){
			String id = JsonPath.read(styleSearchResponse, "$.data["+i+"].id").toString();
			styleListAsString.add(id);
		}
		return styleListAsString;
	}
	
	
	public void virtualBundleError(VBErrorType errorType, String[] dynamicString){
		switch(errorType){
			case ZERO_QUANTITY_INDIVIDUAL:
				vbErrorInfo = "Individual Quantity Count Validation failed";
				vbErrorMsg = "Quantity per style ID(product) cannot be less than 1. For input style ID: "+dynamicString[0]+", quantity entered: "+dynamicString[1]+". Please correct and try again";
				break;
				
			case ZERO_QUANTITY_TOTAL:
				vbErrorInfo = "Total Quantity Count Validation failed";
				vbErrorMsg = "Total quantity in the bundle cannot be less than 2. Total quantity in the bundle obtained: "+dynamicString[0]+". Please correct and try again";
				break;
				
			case NONEXISTENTSTYLE:
				vbErrorInfo = "Validation error";
				vbErrorMsg = "Exception occurred while validating products["+dynamicString[0]+"].";
				break;
				
			case QUANTITY_LIMIT_INDIVIDUAL:
				vbErrorInfo = "Individual Quantity Count Validation failed";
				vbErrorMsg = "Quantity per style ID(product) cannot exceed 4. For input style ID: "+dynamicString[0]+" quantity entered: "+dynamicString[1]+". Please correct and try again";
				break;
				
			case QUANTITY_LIMIT_TOTAL:
				vbErrorInfo = "Total Quantity Count Validation failed";
				vbErrorMsg = "Total quantity in the bundle cannot exceed 4. Please correct and try again";
				break;
				
			case DUPLICATE_PRODUCT:
				vbErrorInfo = "Product Validation failed";
				vbErrorMsg = "Obtained a duplicate product in the input: "+dynamicString[0]+". Please check and try again";
				break;
				
			case INPUT_STYLE_ERROR:
				vbErrorInfo = "Input Style Count Validation failed";
				vbErrorMsg = "Number of input styles cannot exceed 4. Please correct and try again.";
				break;
				
			case DIFF_SIZE_CHARTS:
				vbErrorInfo = "Sizing Validation failed";
				vbErrorMsg = "Scale mismatch for the constituent styles.[styleID="+dynamicString[0]+", unifiedScaleCode='size', styleID="+dynamicString[1]+", unifiedScaleCode='numerical_size']";
				break;
				
			case DIFF_BRANDS:
				vbErrorInfo = "Brand Validation Error!";
				vbErrorMsg = "All the input styles do not belong to a single brand. Please check and try again";
				break;
				
			case STYLE_STATUS:
				vbErrorInfo = "Product Status Validation Failed";
				vbErrorMsg = "The input style ID: "+dynamicString[0]+" is in status: DEL. Please choose only Live / Bundled-Only styles.";
				break;
				
			case DIFF_SELLER:
				vbErrorInfo = "Seller Validation failed";
				vbErrorMsg = "All the input styles do not belong to a single seller. Please check and try again";
				break;
				
			default:
				break;
		}
	}	
	
	public enum VBErrorType {ZERO_QUANTITY_INDIVIDUAL, ZERO_QUANTITY_TOTAL, NONEXISTENTSTYLE, QUANTITY_LIMIT_INDIVIDUAL, QUANTITY_LIMIT_TOTAL, DUPLICATE_PRODUCT, INPUT_STYLE_ERROR, DIFF_SIZE_CHARTS, DIFF_BRANDS, STYLE_STATUS, DIFF_SELLER};
	
	public void assertVirtualBundleErrorEntries(String response, VBErrorType[] errorMsgs, String[] dynamicString){
		List<String> errorMsgsFromResponse = JsonPath.read(response, "$.data[0].errorEntries[*].errorMessage");
		HashMap<String, String> errorInfoMsgMap = new HashMap<String, String>();
		
		for(int i=0; i<errorMsgsFromResponse.size(); i++){
			String errorMsg = JsonPath.read(response, "$.data[0].errorEntries["+i+"].errorMessage").toString();
			String errorInfo = JsonPath.read(response, "$.data[0].errorEntries["+i+"].errorInfo").toString();
			errorInfoMsgMap.put(errorMsg, errorInfo);
		}
		for(int i=0;i<errorMsgs.length;i++){
			virtualBundleError(errorMsgs[i], dynamicString);
			log.info("VB Error Message: "+vbErrorMsg);
			log.info("VB Error Info: "+vbErrorInfo);
			AssertJUnit.assertTrue("Message "+vbErrorMsg+" not present in response", errorInfoMsgMap.containsKey(vbErrorMsg));
			AssertJUnit.assertTrue("Info "+vbErrorInfo+" not present in response", errorInfoMsgMap.get(vbErrorMsg).equals(vbErrorInfo));
		}
	}
		
	
}
