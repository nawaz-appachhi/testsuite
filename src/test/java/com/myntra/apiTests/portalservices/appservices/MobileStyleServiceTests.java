package com.myntra.apiTests.portalservices.appservices;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.dataproviders.MobileStyleServiceDP;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.InitializeFramework;
import com.myntra.apiTests.portalservices.mobileappservices.MobileStyleServiceDataNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class MobileStyleServiceTests extends MobileStyleServiceDP
{
	static Initialize init = InitializeFramework.init;
	static Logger log = Logger.getLogger(MobileStyleServiceTests.class);
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for success response 
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"  }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifySuccessResponse")
	public void MobileStyleService_getStyleById_verifySuccessResponse(String styleId)
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
				
		MultivaluedMap<String, Object> mobileStyleServiceRespHeaders = mobileStyleServiceReqGen.response.getHeaders();
		System.out.println("\nPrinting mobile style service response headers : "+mobileStyleServiceReqGen.response.getHeaders()+"\n");
		
		AssertJUnit.assertTrue("Date is not present in the mobile style service response headers", mobileStyleServiceRespHeaders.containsKey("Date"));
		
		AssertJUnit.assertEquals("MobileStyleService getStyleById API is not working",  200, mobileStyleServiceReqGen.response.getStatus());
	}
	
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for meta tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyMetaTagNodes")
	public void MobileStyleService_getStyleById_verifyMetaTagNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid MetaTag data nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleServiceResponseMetaTagNodes()));
		
		AssertJUnit.assertTrue("MetaTag code data value should be 200 in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertNotNull("MetaTag token data should not be null in MobileStyleService getStyleById API response",
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_REQ_ID.getNodePath(), mobileStyleServiceResponse));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for notification tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyNotificationTagNodes")
	public void MobileStyleService_getStyleById_verifyNotificationTagNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		
		AssertJUnit.assertTrue("Invalid NotificationTag data nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleServiceResponseNotificationTagNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for data tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyDataTagNodes")
	public void MobileStyleService_getStyleById_verifyDataTagNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		
		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid data nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleServiceResponseDataNodes()));
		
		AssertJUnit.assertEquals("MobileStyleService getStyleById API request and response styleId are not same", Long.parseLong(styleId), 
				Long.parseLong(mobileStyleServiceReqGen.respvalidate.NodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_DATA_ID.getNodePath(), true)));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for article attributes data tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyArticleAttributeDataNodes")
	public void MobileStyleService_getStyleById_verifyArticleAttributeDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid articleAttributes nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseArticleAttributeDataNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for style images data tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyStyleImagesDataNodes")
	public void MobileStyleService_getStyleById_verifyStyleImagesDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid styleImages nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseStyleImagesDataNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for master catagory data tag nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyMasterCatagoryDataNodes")
	public void MobileStyleService_getStyleById_verifyMasterCatagoryDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid masterCatagory nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseMasterCatagoryDataNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for sub catagory data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifySubCatagoryDataNodes")
	public void MobileStyleService_getStyleById_verifySubCatagoryDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid subCatagory nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseSubCatagoryDataNodes()));
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for sub article tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifySubArticleDataNodes")
	public void MobileStyleService_getStyleById_verifySubArticleDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));

		AssertJUnit.assertTrue("Invalid subArticle data nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseSubArticleDataNodes()));
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for product descriptors tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyProductDescriptorDataNodes")
	public void MobileStyleService_getStyleById_verifyProductDescriptorDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid productDescriptors nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseProductDescriptorDataNodes()));
		
	}
	
	/**
	 * This TestCase is used to invoke MobileStyleService getStyleById API and verification for style options tag data nodes in the response
	 * 
	 * @param styleId
	 */
	@Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyStyleOptionsDataNodes")
	public void MobileStyleService_getStyleById_verifyStyleOptionsDataNodes(String styleId) 
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");

		AssertJUnit.assertTrue("Error while invoking MobileStyleService getStyleById API", 
				mobileStyleServiceReqGen.respvalidate.GetNodeValue(MobileStyleServiceDataNodes.MOBILE_STYLE_RESP_META_CODE.getNodePath(), 
						mobileStyleServiceResponse).equalsIgnoreCase("200"));
		
		AssertJUnit.assertTrue("Invalid style options nodes in MobileStyleService getStyleById API response", 
				mobileStyleServiceReqGen.respvalidate.DoesNodesExists(MobileStyleServiceDataNodes.getMobileStyleResponseStyleOptionsDataNodes()));

	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "MobileStyleServiceDP_getStyleByIdDataProvider_verifyResponseDataNodesUsingSchemaValidations")
	public void MobileStyleService_getStyleById_verifyResponseDataNodesUsingSchemaValidations(String styleId)
	{
		RequestGenerator mobileStyleServiceReqGen = mobileStyleServiceHelper.invokeGetStyleById(styleId);
		String mobileStyleServiceResponse = mobileStyleServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
		log.info("\nPrinting MobileStyleService getStyleById API response :\n\n"+mobileStyleServiceResponse+"\n");
				
		AssertJUnit.assertEquals("MobileStyleService getStyleById API is not working",  200, mobileStyleServiceReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/mobilestyleservice-getstyledata-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mobileStyleServiceResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in MobileStyleService getStyleById API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
