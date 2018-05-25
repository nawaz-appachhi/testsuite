package com.myntra.apiTests.portalservices.all;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.myntra.apiTests.dataproviders.StyleServiceApiDP;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceNodes;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import net.minidev.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;


public class StyleServiceApiTests extends StyleServiceApiDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StyleServiceApiTests.class);
	static StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	static APIUtilities apiUtil = new APIUtilities();
	static Toolbox tools=new Toolbox();
	
	//------------------------------------------------Testcases for getPdpData------------------------------------------------------------------------------------------------------------------
    /* Test-cases 1.1:
     * GET SERVICE
     * getPdpData - verification for success response - default service
     * @param styleId
     * @author sneha.deep
     */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifySuccessResponse")
	public void StyleService_getStyleDataForSingleStyleId_verifySuccessResponse(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.2:
     * GET SERVICE
     * getPdpData - verification for success status response - verify success status response
     * @param styleId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 * @author sneha.deep
	 */
	
	@Test(groups = { "Sanity", "FoxSanity","MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifySuccessStatusResponse")
	public void StyleService_getStyleDataForSingleStyleId_verifySuccessStatusResponse(String styleId, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForSingleStyleId API response", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success code", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success message", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success type", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.3:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for response data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyDataNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyDataNodes(String requestStyleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(requestStyleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					AssertJUnit.assertEquals("Mismatch: styleId", requestStyleId, styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
						
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				
				System.out.println("\n");
				log.info("\n");
			}
			
			} else {
				System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
				log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.4:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for articleType data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyArticleTypeDataNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyArticleTypeDataNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response articleType data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.5:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for subCategory data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifySubCategoryDataNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifySubCategoryDataNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response subCategory data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.6:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for masterCategory data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyMasterCategoryDataNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyMasterCategoryDataNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.7:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for brandDetailsEntry data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyBrandDetailsEntryNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyBrandDetailsEntryNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.8:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for styleImages data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleImageNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyStyleImageNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].styleImages.search"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response styleImages data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.9:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for productDescriptors data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyProductDescriptorsNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyProductDescriptorsNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.10:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for styleOptions data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleOptionsNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyStyleOptionsNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/* Test-cases 1.11:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for articleAttributes data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyArticleAttributeNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyArticleAttributeNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticleAttributes data nodes in StyleService getStyleDataForSingleStyleId API response", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
		
	/* Test-cases 1.12:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for recommendations data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyRecommendationsNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyRecommendationsNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response recommendations data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
		
	/* Test-cases 1.13:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for styleVisibilityInfo data nodes
     * @param styleId
	 * @author sneha.deep
	 */
//	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
//			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyStyleVisibilityInfoNodes")
//	public void StyleService_getStyleDataForSingleStyleId_verifyStyleVisibilityInfoNodes(String styleId)
//	{
//		long startTime = Calendar.getInstance().getTimeInMillis();
//		
//		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
//		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
//		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
//		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
//		
//		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
//				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
//		
//		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
//		List<String> styleServiceStyleVisibilityDataNodeList = StyleServiceNodes.getStyleVisibilityInfoDataNodes();
//		
//		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
//			
//			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
//				
//				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
//				
//				if(!StringUtils.isEmpty(styleServiceResponseData)){
//					
//					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
//					System.out.println("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
//					log.info("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
//					
//					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_VISIBILITY_INFO.getNodePath(), styleServiceResponseData)){
//						
//						String responseStyleVisibilityData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].styleVisibilityInfo"));
//						
//						if(!StringUtils.isEmpty(responseStyleVisibilityData)){
//							
//							for(int j = 0; j < styleServiceStyleVisibilityDataNodeList.size(); j++){
//								
//								String styleServiceStyleVisibilityDataNode = styleServiceStyleVisibilityDataNodeList.get(j);
//								boolean isNodeExists = apiUtil.Exists(styleServiceStyleVisibilityDataNode, responseStyleVisibilityData);
//
//								AssertJUnit.assertTrue("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data node doesnt exists", isNodeExists);
//								
//								System.out.println("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
//								log.info("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
//								
//							}
//							
//						} else {
//							System.out.println("\nStyleService getStyleDataForSingleStyleId API response styleVisibilityInfo data nodes are empty\n");
//							log.info("\nStyleService getStyleDataForSingleStyleId API response styleVisibilityInfo data nodes are empty\n");
//						}
//						
//					} else {
//						System.out.println("\nstyleVisibilityInfo data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
//						log.info("\nstyleVisibilityInfo data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
//					}
//					
//				} else {
//					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
//					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
//				}
//				System.out.println("\n");
//				log.info("\n");
//			}
//			
//		} else {
//			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
//			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
//		}
//		long endTime = Calendar.getInstance().getTimeInMillis();
//		double timeTaken = (endTime - startTime)/1000.0;
//		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
//		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
//	}
		
	/* Test-cases 1.14:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for taxEntry data nodes
     * @param styleId
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyTaxEntryDataNodes")
	public void StyleService_getStyleDataForSingleStyleId_verifyTaxEntryDataNodes(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
    	System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
		
	/* Test-cases 1.15:
     * GET SERVICE
     * getPdpData - verification for success status response - verification for failure status response
     * @param styleId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 * @author sneha.deep
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyFailureStatusResponse")
	public void StyleService_styleDataForSingleStyleId_verifyFailureStatusResponse(String styleId, String failureStatusCode, String failureStatusMsg, String failureStatusType)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForSingleStyleId API response", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure code", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure message", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure type", 
				getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
    	System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		
	}

	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for success response
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifySuccessResponse")
	public void StyleService_getStyleDataForListOfStyleId_verifySuccessResponse(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForListOfStyleId API is not working", 200, getStyleDataForListOfStyleIdReqGen.response.getStatus());
		
	}

	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for success status response
	 * 
	 * @param styleIds
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifySuccessStatusResponse")
	public void StyleService_getStyleDataForListOfStyleId_verifySuccessStatusResponse(String styleIds, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForListOfStyleId API response", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a success code", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a success message", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a success type", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
	}
	
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for data nodes in the response
	 * 
	 * @param requestStyleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyDataNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyDataNodes(String requestStyleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(requestStyleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		List<String> reqStyleIds = Arrays.asList(requestStyleIds.split(","));
		List<String> responseStyleIds = new ArrayList<String>();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));

				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					responseStyleIds.add(styleIdFromResp);
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
					
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				
				System.out.println("\n");
				log.info("\n");
			}
			
			AssertJUnit.assertTrue("request & response styleIds are invalid", responseStyleIds.containsAll(reqStyleIds));
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for articleType data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyArticleTypeDataNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyArticleTypeDataNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response articleType data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for subCategory data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifySubCategoryDataNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifySubCategoryDataNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
	
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response subCategory data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for masterCategory data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyMasterCategoryDataNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyMasterCategoryDataNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 *  This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for brandDetailsEntry data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyBrandDetailsEntryNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyBrandDetailsEntryNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}	

	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for styleImages data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleImageNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyStyleImageNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].styleImages"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response styleImages data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for productDescriptors data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyProductDescriptorsNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyProductDescriptorsNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for styleOptions data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleOptionsNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyStyleOptionsNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for articleAttribute data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyArticleAttributeNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyArticleAttributeNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticalAttributes data nodes in StyleService getStyleDataForListOfStyleId API response", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));

	}	
		
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for recommendations data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyRecommendationsNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyRecommendationsNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response recommendations data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}	
		
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for styleVisibilityInfo data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyStyleVisibilityInfoNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyStyleVisibilityInfoNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleVisibilityDataNodeList = StyleServiceNodes.getStyleVisibilityInfoDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_VISIBILITY_INFO.getNodePath(), styleServiceResponseData)){
						
						String responseStyleVisibilityData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].styleVisibilityInfo"));
						
						if(!StringUtils.isEmpty(responseStyleVisibilityData)){
							
							for(int j = 0; j < styleServiceStyleVisibilityDataNodeList.size(); j++){
								
								String styleServiceStyleVisibilityDataNode = styleServiceStyleVisibilityDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleVisibilityDataNode, responseStyleVisibilityData);

								AssertJUnit.assertTrue("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								log.info("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response styleVisibilityInfo data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response styleVisibilityInfo data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleVisibilityInfo data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\nstyleVisibilityInfo data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for taxEntry data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyTaxEntryDataNodes")
	public void StyleService_getStyleDataForListOfStyleId_verifyTaxEntryDataNodes(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForListOfStyleId API", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getStyleDataForListOfStyleIdResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForListOfStyleId API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getStyleDataForListOfStyleId API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getStyleDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForListOfStyleId API and verification for failure status response 
	 * 
	 * @param styleIds
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyFailureStatusResponse")
	public void StyleService_getStyleDataForListOfStyleId_verifyFailureStatusResponse(String styleIds, String failureStatusCode, String failureStatusMsg, 
			String failureStatusType)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForListOfStyleId API response", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a failure code", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a failure message", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForListOfStyleId API response status code is not a failure type", 
				getStyleDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId and verification for success response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifySuccessResponse")
	public void StyleService_getPdpDataForSingleStyleId_verifySuccessResponse(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		
	}	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId and verification for success status response
	 * 
	 * @param styleId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity","FoxSanity", "MiniRegression", "Regression", "ExhaustiveRegression"} ,
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifySuccessStatusResponse")
	public void StyleService_getPdpDataForSingleStyleId_verifySuccessStatusResponse(String styleId, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForSingleStyleId API response", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a success code", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a success message", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a success type", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for response data nodes
	 * 
	 * @param requestStyleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyDataNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyDataNodes(String requestStyleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(requestStyleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					AssertJUnit.assertEquals("Mismatch: styleId", requestStyleId, styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
						
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for articleType data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyArticleTypeDataNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyArticleTypeDataNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response articleType data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for subCategory data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifySubCategoryDataNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifySubCategoryDataNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response subCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for masterCategory data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyMasterCategoryDataNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyMasterCategoryDataNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 *  This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for brandDetailsEntry data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyBrandDetailsEntryNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyBrandDetailsEntryNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}	

	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for styleImages data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleImageNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyStyleImageNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].styleImages"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response styleImages data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}

	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for productDescriptors data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyProductDescriptorsNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyProductDescriptorsNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for styleOptions data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleOptionsNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyStyleOptionsNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for articleAttributes data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyArticleAttributeNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyArticleAttributeNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticalAttributes data nodes in StyleService getPdpDataForSingleStyleId API response", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));

	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for recommendations data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyRecommendationsNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyRecommendationsNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response recommendations data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for styleVisibilityInfo data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyStyleVisibilityInfoNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyStyleVisibilityInfoNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleVisibilityDataNodeList = StyleServiceNodes.getStyleVisibilityInfoDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_VISIBILITY_INFO.getNodePath(), styleServiceResponseData)){
						
						String responseStyleVisibilityData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].styleVisibilityInfo"));
						
						if(!StringUtils.isEmpty(responseStyleVisibilityData)){
							
							for(int j = 0; j < styleServiceStyleVisibilityDataNodeList.size(); j++){
								
								String styleServiceStyleVisibilityDataNode = styleServiceStyleVisibilityDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleVisibilityDataNode, responseStyleVisibilityData);

								AssertJUnit.assertTrue("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								log.info("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response styleVisibilityInfo data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response styleVisibilityInfo data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleVisibilityInfo data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\nstyleVisibilityInfo data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for taxEntry data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyTaxEntryDataNodes")
	public void StyleService_getPdpDataForSingleStyleId_verifyTaxEntryDataNodes(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleId API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleId API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getPdpDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleId API and verification for failure status response
	 * 
	 * @param styleId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyFailureStatusResponse")
	public void StyleService_getPdpDataForSingleStyleId_verifyFailureStatusResponse(String styleId, String failureStatusCode, String failureStatusMsg,
			String failureStatusType)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForSingleStyleId API response", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a failure code", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a failure message", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API response status code is not a failure type", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for success response
	 *  
	 * @param styleIds
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifySuccessResponse")
	public void StyleService_getPdpDataForListOfStyleId_verifySuccessResponse(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForListOfStyleId API is not working", 200, getPdpDataForListOfStyleIdReqGen.response.getStatus());
		
	}

	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for success status response
	 * 
	 * @param styleIds
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifySuccessStatusResponse")
	public void StyleService_getPdpDataForListOfStyleId_verifySuccessStatusResponse(String styleIds, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForListOfStyleId API response", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleId API response status code is not a success code", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleId API response status code is not a success message", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleId API response status code is not a success type", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for data nodes in the response
	 * 
	 * @param requestStyleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyDataNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyDataNodes(String requestStyleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(requestStyleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		List<String> reqStyleIds = Arrays.asList(requestStyleIds.split(","));
		List<String> responseStyleIds = new ArrayList<String>();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					responseStyleIds.add(styleIdFromResp);
					
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
						
					}
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
			AssertJUnit.assertTrue("request & response styleIds are invalid", responseStyleIds.containsAll(reqStyleIds));
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for articleType data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyArticleTypeDataNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyArticleTypeDataNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response articleType data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for subCategory data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifySubCategoryDataNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifySubCategoryDataNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response subCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for masterCategory data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyMasterCategoryDataNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyMasterCategoryDataNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 *  This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for brandDetailsEntry data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyBrandDetailsEntryNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyBrandDetailsEntryNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}	

	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for styleImages data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleImageNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyStyleImageNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].styleImages"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response styleImages data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for productDescriptors data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyProductDescriptorsNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyProductDescriptorsNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for styleOptions data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleOptionsNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyStyleOptionsNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for articleAttributes data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyArticleAttributeNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyArticleAttributeNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticalAttributes data nodes in StyleService getPdpDataForListOfStyleId API response", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));

	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for recommendations data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyRecommendationsNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyRecommendationsNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response recommendations data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for styleVisibilityInfo data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyStyleVisibilityInfoNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyStyleVisibilityInfoNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceStyleVisibilityDataNodeList = StyleServiceNodes.getStyleVisibilityInfoDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleVisibilityInfo data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_VISIBILITY_INFO.getNodePath(), styleServiceResponseData)){
						
						String responseStyleVisibilityData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].styleVisibilityInfo"));
						
						if(!StringUtils.isEmpty(responseStyleVisibilityData)){
							
							for(int j = 0; j < styleServiceStyleVisibilityDataNodeList.size(); j++){
								
								String styleServiceStyleVisibilityDataNode = styleServiceStyleVisibilityDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleVisibilityDataNode, responseStyleVisibilityData);

								AssertJUnit.assertTrue("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								log.info("styleVisibilityInfo ---- "+styleServiceStyleVisibilityDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response styleVisibilityInfo data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response styleVisibilityInfo data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleVisibilityInfo data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\nstyleVisibilityInfo data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for taxEntry data nodes
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyTaxEntryDataNodes")
	public void StyleService_getPdpDataForListOfStyleId_verifyTaxEntryDataNodes(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForListOfStyleId API", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getPdpDataForListOfStyleIdResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForListOfStyleId API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForListOfStyleId API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getPdpDataForListOfStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForListOfStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
			log.info("\nStyleService getPdpDataForListOfStyleId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForListOfStyleId API and verification for failure status response 
	 * 
	 * @param styleIds
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyFailureStatusResponse")
	public void StyleService_getPdpDataForListOfStyleId_verifyFailureStatusResponse(String styleIds, String failureStatusCode, String failureStatusMsg, 
			String failureStatusType)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForListOfStyleIdReqGen API response", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleIdReqGen API response status code is not a failure code", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleIdReqGen API response status code is not a failure message", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForListOfStyleIdReqGen API response status code is not a failure type", 
				getPdpDataForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage and verification for success response
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySuccessResponse")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifySuccessResponse(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleIdWithImage API is not working", 200, 
				getPdpDataForSingleStyleIdWithImageReqGen.response.getStatus());
		
	}	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage and verification for success status response
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity","FoxSanity", "MiniRegression", "Regression", "ExhaustiveRegression"} ,
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySuccessStatusResponse")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifySuccessStatusResponse(String styleId, String fetchResolutions, String successStatusCode, 
			String successStatusMsg, String successStatusType)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForSingleStyleIdWithImage API response", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a success code", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a success message", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a success type", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for response data nodes
	 * 
	 * @param requestStyleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyDataNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyDataNodes(String requestStyleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(requestStyleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					AssertJUnit.assertEquals("Mismatch: styleId", requestStyleId, styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
						
					}
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for articleType data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyArticleTypeDataNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyArticleTypeDataNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
								"$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response articleType data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for subCategory data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifySubCategoryDataNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifySubCategoryDataNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
								"$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response subCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for masterCategory data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyMasterCategoryDataNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyMasterCategoryDataNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
								"$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 *  This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for brandDetailsEntry data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyBrandDetailsEntryNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyBrandDetailsEntryNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}	

	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for styleImagesf data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyStyleImageNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyStyleImageNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].styleImages"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response styleImages data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for productDescriptors data nodes
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyProductDescriptorsNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyProductDescriptorsNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
								"$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for styleOptions data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyStyleOptionsNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyStyleOptionsNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, 
											"$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for articalAttributes data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyArticleAttributeNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyArticleAttributeNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticalAttributes data nodes in StyleService getPdpDataForSingleStyleIdWithImage API response", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));

	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for recommendations data nodes
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyRecommendationsNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyRecommendationsNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response recommendations data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for taxEntry data nodes 
	 * 
	 * @param styleId
	 * @param fetchResolutions
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyTaxEntryDataNodes")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyTaxEntryDataNodes(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataForSingleStyleIdWithImage API", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdWithImageResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataForSingleStyleIdWithImage API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
			log.info("\nStyleService getPdpDataForSingleStyleIdWithImage API response data is empty\n");
		}
	}
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataForSingleStyleIdWithImage API and verification for failure status response
	 * 
	 * @param styleId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyFailureStatusResponse")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyFailureStatusResponse(String styleId, String fetchResolutions, String failureStatusCode, 
			String failureStatusMsg, String failureStatusType)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataForSingleStyleIdWithImage API response", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a failure code", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a failure message", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue("status.statusMessage", true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleIdWithImage API response status code is not a failure type", 
				getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.NodeValue("status.statusType", true).contains(failureStatusType));
		
	}
	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for success response
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifySuccessResponse")
	public void StyleService_getPdpDataBySingleSkuId_verifySuccessResponse(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertEquals("StyleService getPdpDataBySingleSkuId API is not working", 200, getPdpDataBySingleSkuIdReqGen.response.getStatus());
				
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for success status response
	 * 
	 * @param skuId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifySuccessStatusResponse")
	public void StyleService_getPdpDataBySingleSkuId_verifySuccessStatusResponse(String skuId, String successStatusCode, String successStatusMsg, 
			String successStatusType)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getPdpDataBySingleSkuId API response", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getPdpDataBySingleSkuId API response status code is not a success code", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getPdpDataBySingleSkuId API response status code is not a success message", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getPdpDataBySingleSkuId API response status code is not a success type", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
				
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for response data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyDataNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyDataNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceDataNodeList = StyleServiceNodes.getDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					
					System.out.println("data nodes verification for styleId : "+styleIdFromResp);
					log.info("data nodes verification for styleId : "+styleIdFromResp);
					
					for(int j = 0; j < styleServiceDataNodeList.size(); j++){
						
						String styleServiceDataNode = styleServiceDataNodeList.get(j);
						
						boolean isNodeExists = apiUtil.Exists(styleServiceDataNode, styleServiceResponseData);
						
						AssertJUnit.assertTrue("data --- "+styleServiceDataNode+" ---- node doesnt exists", isNodeExists);
						
						System.out.println("data ---- "+styleServiceDataNode+" ---- is Exists");
						log.info("data ---- "+styleServiceDataNode+" ----  is Exists");
						
					}
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for articleType data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyArticleTypeDataNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyArticleTypeDataNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceArticleTypeDataNodeList = StyleServiceNodes.getArticalTypeDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					
					System.out.println("articleType data nodes verification for styleId : "+styleIdFromResp);
					log.info("articleType data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_ARTICLE_TYPE.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].articleType"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < styleServiceArticleTypeDataNodeList.size(); j++){
								
								String styleServiceArticleTypeDataNode = styleServiceArticleTypeDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceArticleTypeDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								log.info("articleType ---- "+styleServiceArticleTypeDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response articleType data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for subCategory data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifySubCategoryDataNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifySubCategoryDataNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceSubCatagoryDataNodeList = StyleServiceNodes.getSubCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("subCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("subCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_SUB_CATEGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseSubCatagoryData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].subCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseSubCatagoryData)){
							
							for(int j = 0; j < styleServiceSubCatagoryDataNodeList.size(); j++){
								
								String styleServiceSubCatagoryDataNode = styleServiceSubCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceSubCatagoryDataNode, styleServiceResponseSubCatagoryData);

								AssertJUnit.assertTrue("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								log.info("subCategory ---- "+styleServiceSubCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response subCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response subCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nsubCategory data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nsubCategory data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for masterCategory data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyMasterCategoryDataNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyMasterCategoryDataNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceMasterCatagoryDataNodeList = StyleServiceNodes.getMasterCategoryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					log.info("masterCategory data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_MASTER_CATAGORY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseMasterCatagoryData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].masterCategory"));
						
						if(!StringUtils.isEmpty(styleServiceResponseMasterCatagoryData)){
							
							for(int j = 0; j < styleServiceMasterCatagoryDataNodeList.size(); j++){
								
								String styleServiceMasterCatagoryDataNode = styleServiceMasterCatagoryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceMasterCatagoryDataNode, styleServiceResponseMasterCatagoryData);

								AssertJUnit.assertTrue("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								log.info("masterCategory ---- "+styleServiceMasterCatagoryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response masterCategory data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response masterCategory data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nmasterCategory data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nmasterCategory data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 *  This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for brandDetailsEntry data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyBrandDetailsEntryNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyBrandDetailsEntryNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceBrandDetailsEntryDataNodeList = StyleServiceNodes.getBrandDetailsEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("brandDetailsEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_BRAND_DETAILS_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseBrandDetailsEntryData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, 
								"$.data["+i+"].brandDetailsEntry"));
						
						if(!StringUtils.isEmpty(styleServiceResponseBrandDetailsEntryData)){
							
							for(int j = 0; j < styleServiceBrandDetailsEntryDataNodeList.size(); j++){
								
								String styleServiceBrandDetailsEntryDataNode = styleServiceBrandDetailsEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceBrandDetailsEntryDataNode, styleServiceResponseBrandDetailsEntryData);

								AssertJUnit.assertTrue("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								log.info("brandDetailsEntry ---- "+styleServiceBrandDetailsEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response brandDetailsEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response brandDetailsEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nbrandDetailsEntry data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
						
	}	

	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for styleImages data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyStyleImageNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyStyleImageNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceStyleImagesDataNodeList = StyleServiceNodes.getStyleimagesDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("styleImages data nodes verification for styleId : "+styleIdFromResp);
					log.info("styleImages data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_IMAGES.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseStyleImagesData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].styleImages"));
						
						if(!StringUtils.isEmpty(styleServiceResponseStyleImagesData)){
							
							for(int j = 0; j < styleServiceStyleImagesDataNodeList.size(); j++){
								
								String styleServiceStyleImagesDataNode = styleServiceStyleImagesDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceStyleImagesDataNode, styleServiceResponseStyleImagesData);

								AssertJUnit.assertTrue("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								log.info("styleImages ---- "+styleServiceStyleImagesDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response styleImages data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response styleImages data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleImages data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nstyleImages data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for productDescriptors data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyProductDescriptorsNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyProductDescriptorsNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceProductDescriptorsDataNodeList = StyleServiceNodes.getProductDescriptorsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					log.info("productDescriptors data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_PROD_DESCRIPTORS.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseProdDescData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].productDescriptors"));
						
						if(!StringUtils.isEmpty(styleServiceResponseProdDescData)){
							
							for(int j = 0; j < styleServiceProductDescriptorsDataNodeList.size(); j++){
								
								String styleServiceProductDescriptorsDataNode = styleServiceProductDescriptorsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceProductDescriptorsDataNode, styleServiceResponseProdDescData);

								AssertJUnit.assertTrue("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								log.info("productDescriptors ---- "+styleServiceProductDescriptorsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response productDescriptors data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response productDescriptors data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nproductDescriptors data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nproductDescriptors data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}

		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for styleOptions data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyStyleOptionsNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyStyleOptionsNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceStyleOptionsDataNodeList = StyleServiceNodes.getStyleOptionsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_STYLE_OPTIONS.getNodePath(), styleServiceResponseData)){
						
						List<JSONObject> styleOptionsList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].styleOptions[*]");
						
						if(!CollectionUtils.isEmpty(styleOptionsList)){
							
							for(int j = 0; j < styleOptionsList.size(); j++){
								
								String styleOptionsData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].styleOptions["+j+"]"));
								
								if(!StringUtils.isEmpty(styleOptionsData)){
									
									String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
									String skuIdInRespStyleId = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].styleOptions["+j+"].id"));
									
									String msg = "styleOptions data nodes verification for styleId : "+styleIdFromResp+" & skuId : "+skuIdInRespStyleId;
									System.out.println(msg);
									log.info(msg);
									
									for(int k = 0; k < styleServiceStyleOptionsDataNodeList.size(); k++){
										
										String styleServiceStyleOptionsDataNode = styleServiceStyleOptionsDataNodeList.get(k);
										boolean isNodeExists = apiUtil.Exists(styleServiceStyleOptionsDataNode, styleOptionsData);

										AssertJUnit.assertTrue("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data node doesnt exists", isNodeExists);
										
										System.out.println("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										log.info("styleOptions ---- "+styleServiceStyleOptionsDataNode+" ---- data is Exists");
										
									}
								}
								System.out.println("\n");
								log.info("\n");
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response styleOptions data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response styleOptions data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nstyleOptions data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nstyleOptions data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for articalAttributes data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyArticleAttributeNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyArticleAttributeNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		AssertJUnit.assertTrue("Invalid ArticalAttributes data nodes in StyleService getPdpDataBySingleSkuId API response", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getArticleAttributesDataNodes()));
						
	}	
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for recommendations data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyRecommendationsNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyRecommendationsNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceRecommendationsDataNodeList = StyleServiceNodes.getRecommendationsDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("recommendations data nodes verification for styleId : "+styleIdFromResp);
					log.info("recommendations data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_RECOMMENDATIONS.getNodePath(), styleServiceResponseData)){
						
						String responseRecommendationsData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].recommendations"));
						
						if(!StringUtils.isEmpty(responseRecommendationsData)){
							
							for(int j = 0; j < styleServiceRecommendationsDataNodeList.size(); j++){
								
								String styleServiceRecommendationsDataNode = styleServiceRecommendationsDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceRecommendationsDataNode, responseRecommendationsData);

								AssertJUnit.assertTrue("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								log.info("recommendations ---- "+styleServiceRecommendationsDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response recommendations data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response recommendations data nodes are empty\n");
						}
						
					} else {
						System.out.println("\nrecommendations data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\nrecommendations data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}	
	
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for taxEntry data nodes
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyTaxEntryDataNodes")
	public void StyleService_getPdpDataBySingleSkuId_verifyTaxEntryDataNodes(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Error while invoking StyleService getPdpDataBySingleSkuId API", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));
		
		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data[*]");
		List<String> styleServiceTaxEntryDataNodeList = StyleServiceNodes.getTaxEntryDataNodes();
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].id"));
					System.out.println("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					log.info("taxEntry data nodes verification for styleId : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.STYLE_DATA_TAX_ENTRY.getNodePath(), styleServiceResponseData)){
						
						String responseTaxEntryData = String.valueOf(JsonPath.read(getPdpDataBySingleSkuIdResponse, "$.data["+i+"].taxEntry"));
						
						if(!StringUtils.isEmpty(responseTaxEntryData)){
							
							for(int j = 0; j < styleServiceTaxEntryDataNodeList.size(); j++){
								
								String styleServiceTaxEntryDataNode = styleServiceTaxEntryDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(styleServiceTaxEntryDataNode, responseTaxEntryData);

								AssertJUnit.assertTrue("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								log.info("taxEntry ---- "+styleServiceTaxEntryDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getPdpDataBySingleSkuId API response taxEntry data nodes are empty\n");
							log.info("\nStyleService getPdpDataBySingleSkuId API response taxEntry data nodes are empty\n");
						}
						
					} else {
						System.out.println("\ntaxEntry data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
						log.info("\ntaxEntry data node doesn't exists in StyleService getPdpDataBySingleSkuId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getPdpDataBySingleSkuId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
			log.info("\nStyleService getPdpDataBySingleSkuId API response data is empty\n");
		}
	}
		
	/**
	 * This TestCase is used to invoke StyleService getPdpDataBySingleSkuId API and verification for failure status response
	 * 
	 * @param skuId
	 * @param failureStatusCode
	 * @param failureStatusMsg
	 * @param failureStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyFailureStatusResponse")
	public void StyleService_getPdpDataBySingleSkuId_verifyFailureStatusResponse(String skuId, String failureStatusCode, String failureStatusMsg,
			String failureStatusType)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForSingleStyleId API response", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure code", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure message", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a failure type", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response data tag is not empty", 
				getPdpDataBySingleSkuIdReqGen.respvalidate.NodeValue("data", true).trim().contains("[]"));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleStyleId API and verification for success response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleStyleId_verifySuccessResponse")
	public void StyleService_doStyleIndexForSingleStyleId_verifySuccessResponse(String styleId)
	{
		RequestGenerator doStyleIndexForSingleStyleIdReqGen = styleServiceApiHelper.doStyleIndexForSingleStyleId(styleId);
		String getPdpDataBySingleSkuIdResponse = doStyleIndexForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleStyleId API is not working", 200, doStyleIndexForSingleStyleIdReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleStyleId API and verification for success status response
	 * 
	 * @param styleId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleStyleId_verifySuccessStatusResponse")
	public void StyleService_doStyleIndexForSingleStyleId_verifySuccessStatusResponse(String styleId, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator doStyleIndexForSingleStyleIdReqGen = styleServiceApiHelper.doStyleIndexForSingleStyleId(styleId);
		String getPdpDataBySingleSkuIdResponse = doStyleIndexForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");	
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForSingleStyleId API response", 
				doStyleIndexForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success code", 
				doStyleIndexForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success message", 
				doStyleIndexForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService getStyleDataForSingleStyleId API response status code is not a success type", 
				doStyleIndexForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
	}

	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleStyleId API and verification for failure status response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleStyleId_verifyFailureStatusResponse")
	public void StyleService_doStyleIndexForSingleStyleId_verifyFailureStatusResponse(String styleId)
	{
		RequestGenerator doStyleIndexForSingleStyleIdReqGen = styleServiceApiHelper.doStyleIndexForSingleStyleId(styleId);
		String getPdpDataBySingleSkuIdResponse = doStyleIndexForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleStyleId API response :\n\n"+getPdpDataBySingleSkuIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleStyleId API is not working", 500, doStyleIndexForSingleStyleIdReqGen.response.getStatus());
	}

	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfStyleId API and verification for success response
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForListOfStyleId_verifySuccessResponse")
	public void StyleService_doStyleIndexForListOfStyleId_verifySuccessResponse(String styleIds)
	{
		RequestGenerator doStyleIndexForListOfStyleIdReqGen = styleServiceApiHelper.doStyleIndexForListOfStyleId(styleIds);
		String doStyleIndexForListOfStyleIdResponse = doStyleIndexForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");	
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfStyleId API is not working", 200, doStyleIndexForListOfStyleIdReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfStyleId API and verification for success status response
	 * 
	 * @param styleIds
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForListOfStyleId_verifySuccessStatusResponse")
	public void StyleService_doStyleIndexForListOfStyleId_verifySuccessStatusResponse(String styleIds, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator doStyleIndexForListOfStyleIdReqGen = styleServiceApiHelper.doStyleIndexForListOfStyleId(styleIds);
		String doStyleIndexForListOfStyleIdResponse = doStyleIndexForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");		
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService doStyleIndexForListOfStyleId API response", 
				doStyleIndexForListOfStyleIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfStyleId API response status code is not a success code", 
				doStyleIndexForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfStyleId API response status code is not a success message", 
				doStyleIndexForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfStyleId API response status code is not a success type", 
				doStyleIndexForListOfStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
		
	}
	
	/**
	 *  This TestCase is used to invoke StyleService doStyleIndexForListOfStyleId API and verification for failure status response
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForListOfStyleId_verifyFailureStatusResponse")
	public void StyleService_doStyleIndexForListOfStyleId_verifyFailureStatusResponse(String styleIds)
	{
		RequestGenerator doStyleIndexForListOfStyleIdReqGen = styleServiceApiHelper.doStyleIndexForListOfStyleId(styleIds);
		String doStyleIndexForListOfStyleIdResponse = doStyleIndexForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");		
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfStyleId API is not working", 500, doStyleIndexForListOfStyleIdReqGen.response.getStatus());
	}
	
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleSkuId API and verification for success response
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleSkuId_verifySuccessResponse")
	public void StyleService_doStyleIndexForSingleSkuId_verifySuccessResponse(String skuId)
	{
		RequestGenerator doStyleIndexForSingleSkuIdReqGen = styleServiceApiHelper.doStyleIndexForSingleSkuId(skuId);
		String doStyleIndexForSingleSkuIdResponse = doStyleIndexForSingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleSkuId API is not working", 200, doStyleIndexForSingleSkuIdReqGen.response.getStatus());
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForSingleSkuId API and verification for success status response
	 * 
	 * @param skuId
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleSkuId_verifySuccessStatusResponse")
	public void StyleService_doStyleIndexForSingleSkuId_verifySuccessStatusResponse(String skuId, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator doStyleIndexForSingleSkuIdReqGen = styleServiceApiHelper.doStyleIndexForSingleSkuId(skuId);
		String doStyleIndexForSingleSkuIdResponse = doStyleIndexForSingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");	

		AssertJUnit.assertTrue("Invalid Status nodes in StyleService doStyleIndexForSingleSkuId API response", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a success code", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a success message", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a success type", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
				
	}
	
	/**
	 *  This TestCase is used to invoke StyleService doStyleIndexForSingleSkuId API and verification for failure status response
	 * 
	 * @param skuId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForSingleSkuId_verifyFailureStatusResponse")
	public void StyleService_doStyleIndexForSingleSkuId_verifyFailureStatusResponse(String skuId, String failureStatusCode, String failureStatusMsg, 
			String failureStatusType)
	{
		RequestGenerator doStyleIndexForSingleSkuIdReqGen = styleServiceApiHelper.doStyleIndexForSingleSkuId(skuId);
		String doStyleIndexForSingleSkuIdResponse = doStyleIndexForSingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");	
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService doStyleIndexForSingleSkuId API response", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a failure code", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(failureStatusCode));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a failure message", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(failureStatusMsg));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForSingleSkuId API response status code is not a failure type", 
				doStyleIndexForSingleSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(failureStatusType));
		
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for success response
	 * 
	 * @param skuIds
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForListOfSkuId_verifySuccessResponse")
	public void StyleService_doStyleIndexForListOfSkuId_verifySuccessResponse(String skuIds)
	{
		RequestGenerator doStyleIndexForListOfSkuIdReqGen = styleServiceApiHelper.doStyleIndexForListOfSkuId(skuIds);
		String doStyleIndexForListOfSkuIdResponse = doStyleIndexForListOfSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfSkuId API is not working", 200, doStyleIndexForListOfSkuIdReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for success status response
	 * 
	 * @param skuIds
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doStyleIndexForListOfSkuId_verifySuccessStatusResponse")
	public void StyleService_doStyleIndexForListOfSkuId_verifySuccessStatusResponse(String skuIds, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator doStyleIndexForListOfSkuIdReqGen = styleServiceApiHelper.doStyleIndexForListOfSkuId(skuIds);
		String doStyleIndexForListOfSkuIdResponse = doStyleIndexForListOfSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");	
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService doStyleIndexForListOfSkuId API response", 
				doStyleIndexForListOfSkuIdReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfSkuId API response status code is not a success code", 
				doStyleIndexForListOfSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfSkuId API response status code is not a success message", 
				doStyleIndexForListOfSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService doStyleIndexForListOfSkuId API response status code is not a success type", 
				doStyleIndexForListOfSkuIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
				
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for failure status response
	 * 
	 * @param skuIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="StyleServiceApiDP_doStyleIndexForListOfSkuId_verifyFailureStatusResponse")
	public void StyleService_doStyleIndexForListOfSkuId_verifyFailureStatusResponse(String skuIds)
	{
		RequestGenerator doStyleIndexForListOfSkuIdReqGen = styleServiceApiHelper.doStyleIndexForListOfSkuId(skuIds);
		String doStyleIndexForListOfSkuIdResponse = doStyleIndexForListOfSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfSkuId API is not working",500, doStyleIndexForListOfSkuIdReqGen.response.getStatus());
	}
	
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for success response
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doInvalidateStyles_verifySuccessResponse")
	public void StyleService_doInvalidateStyles_verifySuccessResponse(String styleIds)
	{
		RequestGenerator doInvalidateStylesReqGen = styleServiceApiHelper.doInvalidateStyles(styleIds);
		String doInvalidateStylesResponse = doInvalidateStylesReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");
		log.info("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doInvalidateStyles API is not working", 200, doInvalidateStylesReqGen.response.getStatus());
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for success status response
	 * 
	 * @param styleIds
	 * @param successStatusCode
	 * @param successStatusMsg
	 * @param successStatusType
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_doInvalidateStyles_verifySuccessStatusResponse")
	public void StyleService_doInvalidateStyles_verifySuccessStatusResponse(String styleIds, String successStatusCode, String successStatusMsg,
			String successStatusType)
	{
		RequestGenerator doInvalidateStylesReqGen = styleServiceApiHelper.doInvalidateStyles(styleIds);
		String doInvalidateStylesResponse = doInvalidateStylesReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");
		log.info("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");	
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService doInvalidateStyles API response", 
				doInvalidateStylesReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		AssertJUnit.assertTrue("StyleService doInvalidateStyles API response status code is not a success code", 
				doInvalidateStylesReqGen.respvalidate.NodeValue(StatusNodes.STATUS_CODE.getNodePath(), true).contains(successStatusCode));
		
		AssertJUnit.assertTrue("StyleService doInvalidateStyles API response status code is not a success message", 
				doInvalidateStylesReqGen.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.getNodePath(), true).contains(successStatusMsg));
		
		AssertJUnit.assertTrue("StyleService doInvalidateStyles API response status code is not a success type", 
				doInvalidateStylesReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(successStatusType));
				
	}
	
	/**
	 * This TestCase is used to invoke StyleService doStyleIndexForListOfSkuId API and verification for failure status response 
	 * 
	 * @param styleIds
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider="StyleServiceApiDP_doInvalidateStyles_verifyFailureStatusResponse")
	public void StyleService_doInvalidateStyles_verifyFailureStatusResponse(String styleIds)
	{
		RequestGenerator doInvalidateStylesReqGen = styleServiceApiHelper.doInvalidateStyles(styleIds);
		String doInvalidateStylesResponse = doInvalidateStylesReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");
		log.info("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService doInvalidateStyles API is not working", 500, doInvalidateStylesReqGen.response.getStatus());
	}
	
	// Below test case is risky to run on fox and QA. Full Re indexing API
	@Test(groups = {""})
	public void StyleService_fullReindexing(){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_STYLEAPI, APINAME.FULLREINDEXING, init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		AssertJUnit.assertEquals("fullReindexing API is not working",200,req.response.getStatus());
		String statusMessageFromResponse = req.respvalidate.NodeValue("status.statusType", true);
		//System.out.println(statusMessageFromResponse);
		System.out.println(req.respvalidate.returnresponseasstring());
		Assert.assertTrue(statusMessageFromResponse.contains("SUCCESS"), "StatusType String didn't matched");
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getStyleDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleId)
	{
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getstyledataforsinglestyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getStyleDataForSingleStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getStyleDataForSingleStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getStyleDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getStyleDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleIds)
	{
		RequestGenerator getStyleDataForListOfStyleIdReqGen = styleServiceApiHelper.getStyleDataForListOfStyleId(styleIds);
		String getStyleDataForListOfStyleIdResponse = getStyleDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForListOfStyleId API response : \n\n"+getStyleDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForListOfStyleId API is not working", 200, getStyleDataForListOfStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getstyledataforlistofstyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getStyleDataForListOfStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getStyleDataForListOfStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getPdpDataForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleId)
	{
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getpdpdataforsinglestyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPdpDataForSingleStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getPdpDataForSingleStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getPdpDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getPdpDataForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleIds)
	{
		RequestGenerator getPdpDataForListOfStyleIdReqGen = styleServiceApiHelper.getPdpDataForListOfStyleId(styleIds);
		String getPdpDataForListOfStyleIdResponse = getPdpDataForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForListOfStyleId API response : \n\n"+getPdpDataForListOfStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForListOfStyleId API is not working", 200, getPdpDataForListOfStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getpdpdataforlistofstyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPdpDataForListOfStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getPdpDataForListOfStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getPdpDataForSingleStyleIdWithImage_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getPdpDataForSingleStyleIdWithImage_verifyResponseDataNodesUsingSchemaValidations(String styleId, String fetchResolutions)
	{
		RequestGenerator getPdpDataForSingleStyleIdWithImageReqGen = styleServiceApiHelper.getPdpDataForSingleStyleIdWithImage(styleId, fetchResolutions);
		String getPdpDataForSingleStyleIdWithImageResponse = getPdpDataForSingleStyleIdWithImageReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleIdWithImage API response : \n\n"+getPdpDataForSingleStyleIdWithImageResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleIdWithImage API is not working", 200, 
				getPdpDataForSingleStyleIdWithImageReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getpdpdataforsinglestyleidwithimage-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPdpDataForSingleStyleIdWithImageResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getPdpDataForSingleStyleIdWithImage API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider="StyleServiceApiDP_getPdpDataBySingleSkuId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_getPdpDataBySingleSkuId_verifyResponseDataNodesUsingSchemaValidations(String skuId)
	{
		RequestGenerator getPdpDataBySingleSkuIdReqGen = styleServiceApiHelper.getPdpDataBySingleSkuId(skuId);
		String getPdpDataBySingleSkuIdResponse = getPdpDataBySingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);
		log.info("\nPrinting StyleService getPdpDataBySingleSkuId API response :\n\n"+getPdpDataBySingleSkuIdResponse);	

		AssertJUnit.assertEquals("StyleService getPdpDataBySingleSkuId API is not working", 200, getPdpDataBySingleSkuIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_getpdpdatabysingleskuid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPdpDataBySingleSkuIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService getPdpDataBySingleSkuId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
				
	}

	@Test(groups = { "SchemaValidation" }, dataProvider = "StyleServiceApiDP_doStyleIndexForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_doStyleIndexForSingleStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleId)
	{
		RequestGenerator doStyleIndexForSingleStyleIdReqGen = styleServiceApiHelper.doStyleIndexForSingleStyleId(styleId);
		String doStyleIndexForSingleStyleIdResponse = doStyleIndexForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService doStyleIndexForSingleStyleId API response : \n\n"+doStyleIndexForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService doStyleIndexForSingleStyleId API response : \n\n"+doStyleIndexForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleStyleId API is not working", 200, doStyleIndexForSingleStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_dostyleindexforsinglestyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(doStyleIndexForSingleStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService doStyleIndexForSingleStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "StyleServiceApiDP_doStyleIndexForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_doStyleIndexForListOfStyleId_verifyResponseDataNodesUsingSchemaValidations(String styleIds)
	{
		RequestGenerator doStyleIndexForListOfStyleIdReqGen = styleServiceApiHelper.doStyleIndexForListOfStyleId(styleIds);
		String doStyleIndexForListOfStyleIdResponse = doStyleIndexForListOfStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfStyleId API response :\n\n"+doStyleIndexForListOfStyleIdResponse+"\n");	
		
		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfStyleId API is not working", 200, doStyleIndexForListOfStyleIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_dostyleindexforlistofstyleid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(doStyleIndexForListOfStyleIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService doStyleIndexForListOfStyleId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "StyleServiceApiDP_doStyleIndexForSingleSkuId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_doStyleIndexForSingleSkuId_verifyResponseDataNodesUsingSchemaValidations(String skuId)
	{
		RequestGenerator doStyleIndexForSingleSkuIdReqGen = styleServiceApiHelper.doStyleIndexForSingleSkuId(skuId);
		String doStyleIndexForSingleSkuIdResponse = doStyleIndexForSingleSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForSingleSkuId API response :\n\n"+doStyleIndexForSingleSkuIdResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doStyleIndexForSingleSkuId API is not working", 200, doStyleIndexForSingleSkuIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_dostyleindexforsingleskuid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(doStyleIndexForSingleSkuIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService doStyleIndexForSingleSkuId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "StyleServiceApiDP_doStyleIndexForListOfSkuId_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_doStyleIndexForListOfSkuId_verifyResponseDataNodesUsingSchemaValidations(String skuIds)
	{
		RequestGenerator doStyleIndexForListOfSkuIdReqGen = styleServiceApiHelper.doStyleIndexForListOfSkuId(skuIds);
		String doStyleIndexForListOfSkuIdResponse = doStyleIndexForListOfSkuIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");
		log.info("\nPrinting StyleService doStyleIndexForListOfSkuId API response :\n\n"+doStyleIndexForListOfSkuIdResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doStyleIndexForListOfSkuId API is not working", 200, doStyleIndexForListOfSkuIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_dostyleindexforlistofskuid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(doStyleIndexForListOfSkuIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService doStyleIndexForListOfSkuId API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "StyleServiceApiDP_doInvalidateStyles_verifyResponseDataNodesUsingSchemaValidations")
	public void StyleService_doInvalidateStyles_verifyResponseDataNodesUsingSchemaValidations(String styleIds)
	{
		RequestGenerator doInvalidateStylesReqGen = styleServiceApiHelper.doInvalidateStyles(styleIds);
		String doInvalidateStylesResponse = doInvalidateStylesReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");
		log.info("\nPrinting StyleService doInvalidateStyles API response :\n\n"+doInvalidateStylesResponse+"\n");	

		AssertJUnit.assertEquals("StyleService doInvalidateStyles API is not working", 200, doInvalidateStylesReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/styleservice_doinvalidatestyles-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(doInvalidateStylesResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in StyleService doInvalidateStyles API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * This TestCase is used to invoke StyleService getStyleDataForSingleStyleId API and save API response for all styleID in local storage
	 * @author arunesh
	 * @param requestStyleId
	 * @throws IOException 
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }			)
	public void StyleService_getStyleDataForSingleStyleId_verifyDataNodes2() throws IOException
	{
			String styledata = tools.readFileAsString("/Users/13711/searchresponses/styles.txt");
			System.out.println(styledata);
			
			String[] arr = styledata.split("\\n");
			int range = arr.length;
			for(int i=0;i<=range-1;i++)
			{
				//if(!arr[i].trim().equals(""))
					
				//{
					String styleid = arr[i];
					RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleid);
					String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
					tools.WriteDatatoFile("/Users/13711/searchresponses/"+styleid, getStyleDataForSingleStyleIdResponse, true);
				//}
				
		/*
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	}
	
	/* Test-cases :
     * GET SERVICE
     * skucode to style id api, verifying the success response.
     * @author jitender.kumar1
     */
	
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "StyleServiceApiDP_getSkucodeToStyleId")
	public void StyleService_skuCodetoStyleId(String skuCode)
	{
		
		long startTime = Calendar.getInstance().getTimeInMillis();	
		RequestGenerator getSkuCodeReqGen = styleServiceApiHelper.getSkuCodetoStyleId(skuCode);
		String getSkuCodeStyleIdResponse = getSkuCodeReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getSkuCodeToStyleId API response : \n\n"+getSkuCodeStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getSkuCodeToStyleId API response : \n\n"+getSkuCodeStyleIdResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid Status nodes in StyleService getStyleDataForSingleStyleId API response", 
				getSkuCodeReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()));
		
		if(getSkuCodeReqGen.respvalidate.DoesNodesExists(StatusNodes.getStatusNodes()))
		{
			
				System.out.println("\nPrinting getSkuCodeToStyleId success message : \n\n"+getSkuCodeReqGen.respvalidate.DoesNodeExists(StatusNodes.getStatusNodes().get(1))+"\n");
				log.info("\nPrinting StyleService getSkuCodeToStyleId API response : \n\n"+getSkuCodeReqGen.respvalidate.DoesNodeExists(StatusNodes.getStatusNodes().get(1))+"\n");
			
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - StyleService_skuCodeToStyleid : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - StyleService_skuCodeToStyleid : "+timeTaken+" seconds\n");
	}
	
	
	/* Test-cases :
     * GET SERVICE
     * Virtual Bundling, verifying the success response and retunPeriod is 30 days and isReturnable field is true.
     * @author jitender.kumar1
     */
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_virtualBundling_verifySuccessResponse")
	public void StyleService_virtualBundling_verifySuccessandReturnResponse(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		String virtualNode = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Virtual Node is\n"+virtualNode);
		
		String returnPeriod= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.returnPeriod").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Return period is\n"+returnPeriod + "days");
		
		String isReturnable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.isReturnable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isReturnable);
		
		AssertJUnit.assertEquals("ReturnPeriod is less than 30 days", returnPeriod, "30");
		
		AssertJUnit.assertEquals("Returnable field is false", isReturnable, "true");

		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	
	
	/* Test-cases :
     * GET SERVICE
     * Virtual Bundling, verifying the success response and retunPeriod is 30 days and isExchangeable field is true.
     * @author jitender.kumar1
     */
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_virtualBundling_isExchangeableisTrue")
	public void StyleService_virtualBundling_isExchangeable(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		String virtualNode = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Virtual Node is\n"+virtualNode);
		
		String returnPeriod= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.returnPeriod").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Return period is\n"+returnPeriod + "days");
		
		String isReturnable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.isReturnable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isReturnable);
		
		String isExchangeable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.isExchangeable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isExchangeable);
		
		AssertJUnit.assertEquals("ReturnPeriod is less than 30 days", returnPeriod, "30");
		
		AssertJUnit.assertEquals("Returnable field is false", isReturnable, "true");
		
		AssertJUnit.assertEquals("Exchangeable field is false", isExchangeable, "true");

		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	
	
	/* Test-cases :
     * GET SERVICE
     * Virtual Bundling, verifying the success response and retunPeriod is less than 30 days and isReturnable field is true.
     * @author jitender.kumar1
     */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_virtualBundling_returnPeriodisLessthan30")
	public void StyleService_virtualBundling_returnPeriodLessThan30(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		String virtualNode = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Virtual Node is\n"+virtualNode);
		
		String returnPeriod= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.returnPeriod").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Return period is\n"+returnPeriod + "days");
		int returnPeriod1 = Integer.parseInt(returnPeriod);
		System.out.println("Return period is\n"+returnPeriod1 + "days");
		
		String isReturnable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.isReturnable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isReturnable);
		if(returnPeriod1<30)
		{
			AssertJUnit.assertEquals("Returnable field is false", isReturnable, "true");

		}
		else{
			System.out.println("Return Period is 30 days");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}

	
	
	
	/* Test-cases :
     * GET SERVICE
     * Virtual Bundling, verifying the success response and isreturnable and is exchangebale is false
     * @author jitender.kumar1
     */
	
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider="StyleServiceApiDP_virtualBundling_isExchangeableFalse")
	public void StyleService_virtualBundling_notExchangeableNotReturnable(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
		String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		log.info("\nPrinting StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API is not working", 200, getStyleDataForSingleStyleIdReqGen.response.getStatus());
		
		String virtualNode = JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Virtual Node is\n"+virtualNode);
		
		String returnPeriod= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.returnPeriod").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Return period is\n"+returnPeriod + "days");
		
		String isReturnable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.returnInfo.isReturnable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isReturnable);
		
		String isExchangeable= JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data..inferredAttributeValues.isExchangeable").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("isReturnable field is\n"+isExchangeable);
		
		AssertJUnit.assertEquals("isExchangebale field is false", isExchangeable, "false");
		
		AssertJUnit.assertEquals("Returnable field is false", isReturnable, "false");

		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - StyleService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	
	
	/* Test-cases :
     * GET SERVICE
     * PreOrder validation
     * @author jitender.kumar1
     */
	@Test(groups = { "DevAsk" },
			dataProvider="StyleServiceApiDP_preOrder")
	public void StyleService_getPdpDataForPreOrder_verifySuccessResponse(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));

		
		Object preOrderNode = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data..advanceOrderOptions").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("PREORDER Node is\n"+preOrderNode);
		
		String productTag= JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data..advanceOrderOptions.preOrder.productImageTag").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", "Pre-Order", productTag);
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
					
	}
	
	
	
	/* Test-cases :
     * GET SERVICE
     * PreOrder validation
     * @author jitender.kumar1
     */
	@Test(groups = { "DevAsk" },
			dataProvider="StyleServiceApiDP_preOrderProductTag")
	public void StyleService_getPdpDataForPreOrder_verifyWithCMS(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpreOderResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpreOderResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpreOderResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));

		
		Object preOrderNode = JsonPath.read(getPdpreOderResponse, "$.data..advanceOrderOptions").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("PREORDER Node is\n"+preOrderNode);
		
		String productImageTag= JsonPath.read(getPdpreOderResponse, "$.data..advanceOrderOptions.preOrder.productImageTag").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", "Pre-Order", productImageTag);
		
		RequestGenerator cmsRequest = styleServiceApiHelper.cmsPreOrder(styleId);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->"+cmsResponse);
		
		String productTag = JsonPath.read(getPdpreOderResponse, "$.data..productTag").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("response=====>"+ productTag);
		AssertJUnit.assertTrue("StyleService getPdpDataForSingleStyleId API is not working", productTag.toLowerCase().contains("preorder"));
		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
					
	}
	
	
	
	/* Test-cases :
     * GET SERVICE
     * PreOrder validation
     * @author jitender.kumar1
     */
	@Test(groups = { "DevAsk" },
			dataProvider="StyleServiceApiDP_preOrderWithoutPreOrderTag")
	public void StyleService_getPdpDataForPreOrder_verifyWithoutPreOrderTag(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpreOderResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpreOderResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpreOderResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));

		
		Object preOrderNode = JsonPath.read(getPdpreOderResponse, "$.data..advanceOrderOptions").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("PREORDER Node is\n"+preOrderNode);
		
		String productImageTag= JsonPath.read(getPdpreOderResponse, "$.data..advanceOrderOptions.preOrder.productImageTag").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", "Pre-Order", productImageTag);
		AssertJUnit.assertFalse("StyleService PreOrderNode and PreOrder Tag is present", productImageTag.toLowerCase().contains("pre-order"));
		
		
		RequestGenerator cmsRequest = styleServiceApiHelper.cmsPreOrder(styleId);
		String cmsResponse = cmsRequest.returnresponseasstring();
		System.out.println("CMS--->"+cmsResponse);
		
		String productTag = JsonPath.read(getPdpreOderResponse, "$.data..productTag").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("response=====>"+ productTag);
		AssertJUnit.assertFalse("CMs PreOrderNode and PreOrder Tag is present", productTag.toLowerCase().contains("preorder"));
		
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
					
	}

	
					
					
					
				
	

	/* Test-cases :
     * GET SERVICE
     * VErify Preorder nodes
     * @author jitender.kumar1StyleServiceApiDP_preOrderNegative
     */
	@Test(groups = { "DevAsk" },
			dataProvider="StyleServiceApiDP_preOrderNode")
	public void StyleService_getPdpDataForPreOrder_verifySuccessResponse1(String styleId)
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		RequestGenerator getPdpDataForSingleStyleIdReqGen = styleServiceApiHelper.getPdpDataForSingleStyleId(styleId);
		String getPdpDataForSingleStyleIdResponse = getPdpDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		log.info("Printing StyleService getPdpDataForSingleStyleId API response : \n\n"+getPdpDataForSingleStyleIdResponse+"\n");
		
		AssertJUnit.assertEquals("StyleService getPdpDataForSingleStyleId API is not working", 200, getPdpDataForSingleStyleIdReqGen.response.getStatus());
		AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API", 
				getPdpDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));

		List<JSONObject> styleServiceResponseDataList = JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data[*]");
		List<String> preOrderDataNodeList = StyleServiceNodes.getPreOrderDataNodes();
		System.out.println("response---->"+styleServiceResponseDataList);
		
		if(!CollectionUtils.isEmpty(styleServiceResponseDataList)){ 
			
			for(int i = 0; i < styleServiceResponseDataList.size(); i++){
				
				String styleServiceResponseData = String.valueOf(styleServiceResponseDataList.get(i));
				
				if(!StringUtils.isEmpty(styleServiceResponseData)){
					
					String styleIdFromResp = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data..advanceOrderOptions"));
					System.out.println("Preorder data nodes verification for Preorder Node : "+styleIdFromResp);
					log.info("Preorder data nodes verification for Preorder Node : "+styleIdFromResp);
					
					if(apiUtil.Exists(StyleServiceNodes.PREORDER_DATA.getNodePath(), styleServiceResponseData)){
						
						String styleServiceResponseArticleTypeData = String.valueOf(JsonPath.read(getPdpDataForSingleStyleIdResponse, "$.data..advanceOrderOptions.preOrder"));
						
						if(!StringUtils.isEmpty(styleServiceResponseArticleTypeData)){
							
							for(int j = 0; j < preOrderDataNodeList.size(); j++){
								
								String preOrderDataNode = preOrderDataNodeList.get(j);
								boolean isNodeExists = apiUtil.Exists(preOrderDataNode, styleServiceResponseArticleTypeData);

								AssertJUnit.assertTrue("articleType ---- "+preOrderDataNode+" ---- data node doesnt exists", isNodeExists);
								
								System.out.println("articleType ---- "+preOrderDataNode+" ---- data is Exists");
								log.info("articleType ---- "+preOrderDataNode+" ---- data is Exists");
								
							}
							
						} else {
							System.out.println("\nStyleService getStyleDataForSingleStyleId API response articleType data nodes are empty\n");
							log.info("\nStyleService getStyleDataForSingleStyleId API response articleType data nodes are empty\n");
						}
						
					} else {
						System.out.println("\narticleType data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
						log.info("\narticleType data node doesn't exists in StyleService getStyleDataForSingleStyleId API response\n");
					}
					
				} else {
					System.out.println("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
					log.info("\ndata tag nodes are empty in StyleService getStyleDataForSingleStyleId API response\n");
				}
				System.out.println("\n");
				log.info("\n");
			}
			
		} else {
			System.out.println("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
			log.info("\nStyleService getStyleDataForSingleStyleId API response data is empty\n");
		}
		
		long endTime = Calendar.getInstance().getTimeInMillis();
		double timeTaken = (endTime - startTime)/1000.0;
		System.out.println("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
		log.info("\nTime taken to execute - TestCase - SearchService_getQueryWithSuccessMessage : "+timeTaken+" seconds\n");
	}
	
	
	
}
