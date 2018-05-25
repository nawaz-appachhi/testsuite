package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.myntra.apiTests.portalservices.OrderFlowWIthDiscountHelper.OrderFlowWIthDiscountServiceHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.DealsServiceDP;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author shankara.c
 *
 */
public class DealsService extends DealsServiceDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealsService.class);
	APIUtilities apiUtil = new APIUtilities();
	static String rcvSytleId="";
	StyleServiceApiHelper styleServiceApiHelper=new StyleServiceApiHelper();
	static OrderFlowWIthDiscountServiceHelper OrderDiscounhelper = new OrderFlowWIthDiscountServiceHelper();

	/**
	 * Verify createDeal api status.
	 * @author jhansi.bai
	 */
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealDataProvider", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3. Verify Nodes and values")
	public void dealsService_createnewdeal_vDealStatusAdNodesAdVals(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				200, req.response.getStatus());
		//nodes
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealNodesWithOutDiscId()));
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealStylesNodes()));
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealChannesNodes()));
		// nodes values
		AssertJUnit.assertEquals("name does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(2), true).replaceAll("\"", ""), name);
		AssertJUnit.assertEquals("description does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(4), true).replaceAll("\"", ""), desc);
		AssertJUnit.assertEquals("dealType does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(5), true).replaceAll("\"", ""), dealType);
		AssertJUnit.assertEquals("startTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(6), true).replaceAll("\"", ""), startTime);
		AssertJUnit.assertEquals("endTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(7), true).replaceAll("\"", ""), endTime);
		AssertJUnit.assertEquals("discountPercent does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(8), true).replaceAll("\"", ""), discPercent);
		AssertJUnit.assertEquals("visible does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(9), true).replaceAll("\"", ""), visible);
		AssertJUnit.assertEquals("state does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(10), true).replaceAll("\"", ""), state);
		AssertJUnit.assertEquals("bannerURL does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(11), true).replaceAll("\"", ""), banner);
	}	

	/**
	 * Verify createnewdealwithmultiplestyleidschanneltypes api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealWithMultipleStyleIdsChannelTypesDP", priority=1, description="\n 1. Create new deal with multiple style id \n 2. Verify 200 status code \n 3. Verify nodes and values")
	public void dealsService_createnewdealWithMultipleStyleIdsChannelTypes(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId1, String styleId2, String styleId3,
			String channelType1, String channelType2, String channelType3)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEALWITHMULTIPLESTYLEIDSCHANNELTYPES, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId1, styleId2, styleId3, channelType1, channelType2, channelType3);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"createnewdealwithmultiplestyleidschanneltypes API is not working ",
				200, req.response.getStatus());
		//nodes
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealNodesWithOutDiscId()));
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealStylesNodes()));
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealChannesNodes()));
		// nodes values
		AssertJUnit.assertEquals("name does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(2), true).replaceAll("\"", ""), name);	
		AssertJUnit.assertEquals("description does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(4), true).replaceAll("\"", ""), desc);
		AssertJUnit.assertEquals("dealType does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(5), true).replaceAll("\"", ""), dealType);
		AssertJUnit.assertEquals("startTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(6), true).replaceAll("\"", ""), startTime);
		AssertJUnit.assertEquals("endTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(7), true).replaceAll("\"", ""), endTime);
		AssertJUnit.assertEquals("visible does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(9), true).replaceAll("\"", ""), visible);
		AssertJUnit.assertEquals("state does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(10), true).replaceAll("\"", ""), state);
		AssertJUnit.assertEquals("discPercent does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(8), true).replaceAll("\"", ""), discPercent);
		AssertJUnit.assertEquals("bannerURL does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(11), true).replaceAll("\"", ""), banner);
	}	

	/**
	 * Verify createnewdeal api nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "MiniRegression", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealDataProvider_duplicate_negative", priority=2,description="\n 1.Create new deal \n 2. create duplicate deal \n 3. Verify status message \n 4. Verify error message")
	public void dealsService_createnewdeal_vDuplicateErrorStatusNodesAdVals(String name, String desc, String dealType, 
			String startTime, String endTime, String visible, String state, String discPercent, String banner, String styleId, 
			String channelType, String status, String ErrMessage)
	{
		RequestGenerator crreq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType, status, ErrMessage);
		RequestGenerator dupreq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType, status, ErrMessage);
		System.out.println(dupreq.respvalidate.returnresponseasstring());
		//nodes
		AssertJUnit.assertTrue("Inval createnewdeal API status nodes.", 
				dupreq.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				dupreq.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), status);	
		AssertJUnit.assertEquals("message does't match", 
				dupreq.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), ErrMessage);		
	}

	/**
	 * Verify createnewdeal api negative status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealDataProvider_negative", priority=3,description="\n 1.Create deal with zero discount percentage \n 2. Verify for 409 status code")
	public void dealsService_createnewdeal_negative(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		 //System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				409, req.response.getStatus());
	}
	
	/**
	 * Verify createnewdeal api negative status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealDataProvider_negative1", priority=4,description="\n 1. Create new deal with invalid deal type \n 2. Verify for 500 status code")
	public void dealsService_createnewdeal_negative1(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		//System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				500, req.response.getStatus());
	}
	
	/**
	 * Verify createnewdeal api negative status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealDataProvider_negative2", priority=5,description="\n 1. create existing deal \n 2. Verify 409 status code")
	public void dealsService_createnewdeal_negative2(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		 System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				409, req.response.getStatus());
	}


	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createInvisibleNewDealDP", priority=6, description="\n 1. Create new deal \n 2. Verify Nodes and Values \n 3. Add styleids to existing Deal \n 4.Verify 200 status code for add styleids for existing deal request")
	public void dealsService_addStyleIds_vStatusAdNodesAdVals(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		//	System.out.println(crReq.respvalidate.returnresponseasstring());
		String dealId = crReq.respvalidate.NodeValue(getDealNodes().get(0), true).replaceAll("\"", "");	

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("addStyleIds API is not working ",
				200, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealNodesWithOutDiscId()));
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealStylesNodes()));
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealChannesNodes()));
		// nodes values
		AssertJUnit.assertTrue(JsonPath.read(req.respvalidate.returnresponseasstring(), "$.dealStyles..styleId").toString().contains(styleId));	
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "addStyleToDealIdDP_negative" , priority=7)
	public void dealsService_addStyleIds_vNegativeStatusadVals(String dealId, String styleId, String failStatus, String errMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("addStyleIds API is not working ",
				409, req.response.getStatus());	
		//nodes
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), failStatus);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), errMessage);	
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "addStyleToDealIdDP_negative1", priority=8,description="\n 1. Add invalid style to existing deal \n 2. Verify 409 status code \n 3. Verify status nodes \n 4. Verify status and message")
	public void dealsService_addStyleIds_vNegativeStatusadVals1(String dealId, String styleId, String failStatus, String errMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("addStyleIds API is not working ",
				409, req.response.getStatus());	

		//nodes
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), failStatus);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), errMessage);	
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "addStyleToDealIdDP_duplicate_negative", priority=9, description="\n 1. Add duplicate style to existing deal \n 2. Verify 409 status code")
	public void dealsService_addStyleIds_duplicate_negative(String dealId, String styleId, String failStatus, String errMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("addStyleIds API is not working ",	409, req.response.getStatus());		
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
			dataProvider = "addStyleToDealIdDP_duplicate_negative", priority=10, description="\n 1. Add duplicat invalid style ids \n 2.Verify invalid added style id status nodes \n 3. Verify status \n 4.Verify err message ")
	public void dealsService_addStyleIds_duplicate_vNegativeStatusadVals(String dealId, String styleId, String failStatus, String errMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		//nodes
		AssertJUnit.assertTrue("Inval addStyleIds API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), failStatus);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), errMessage);	
	}

	/**
	 * Delete style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createInvisibleNewDealDPForDelete", priority=11, description="\n 1. Create new deal \n 2. Delete style ids \n 3. Verify status code for delete style id \n 4. Verify delete style id status nodes \n 5. Verify style id which got deleted")
	public void dealsService_deleteStyleIds_vStatusAdNodesAdVals(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{

		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		String dealId = crReq.respvalidate.NodeValue(getDealNodes().get(0), true).replaceAll("\"", "");	

		//RequestGenerator req = getQuerryPayLoadReq(APINAME.DELETESTYLEIDS, styleId, dealId);
		RequestGenerator req = getPayLoadReqByQuery(APINAME.DELETESTYLEIDS, styleId, dealId);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("deleteStyleIds API is not working ",
				200, req.response.getStatus());		
		//nodes
		AssertJUnit.assertTrue("Inval deleteStyleid API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealNodesWithOutDiscId()));
		// nodes values
		AssertJUnit.assertFalse(JsonPath.read(req.respvalidate.returnresponseasstring(), "$.dealStyles..styleId").toString().contains(styleId));	
	}

	/**
	 * Delete style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "deleteDealIdDP_negative", priority=12, description="\n 1. Get deal by passing deal id \n 2. If style id is not empty then delete style ids \n 3. Verify 500 status code")
	public void dealsService_deleteStyleIds_negative(String dealId, String failStatus, String errMessage)
	{
		RequestGenerator req1 = getUrlRequest(APINAME.GETDEAL, dealId);
		String styleId = req1.respvalidate.NodeValue(getDealStylesNodes().get(2), true);
		if(!styleId.isEmpty()){
			RequestGenerator req = getQuerryPayLoadRequest(APINAME.DELETESTYLEIDS, styleId, dealId);
			System.out.println(req.respvalidate.returnresponseasstring());
			AssertJUnit.assertEquals("deleteStyleIds API is not working ",
					500, req.response.getStatus());	
		}else
		{
			System.out.println("The deal "+dealId+" is not having style id.");
		}
	}

	/**
	 * Delete deal
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "deleteDealIdDP_negative", priority=13, description="\n 1.Get deal from deal id \n 2. Delete styles from deal \n 3. Verify invalid delete deal api status node \n 4.Verify status and err message ")
	public void dealsService_deleteStyleIds_vNegativeStatusadVals(String dealId, String failStatus, String errMessage)
	{
		RequestGenerator req1 = getUrlRequest(APINAME.GETDEAL, dealId);
		String styleId = req1.respvalidate.NodeValue(getDealStylesNodes().get(2), true);
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.DELETESTYLEIDS, styleId, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		//nodes
		AssertJUnit.assertTrue("Inval deletedeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), failStatus);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), errMessage);	
	}

	/**
	 * Verify getAllDeals api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Prodsanity", "Regression", "RFPQA" }, 
			dataProvider = "getallDealsDP", priority=14, description="\n 1. Get All deals \n 2. Verify 200 status response")
	public void dealsService_getAllDeals(String visible, String dataType, String state, String channel)
	{
		RequestGenerator req = getRequest(APINAME.GETALLDEALS, visible, dataType, state, channel);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"getAllDeals API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify getAllDeals api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "getallDealsDP", priority=15, description="\n 1. Get all deals \n 2. Verify expiry date")
	public void dealsService_getAllDeals_vExpiryDate(String visible, String dataType, String state, String channel)
	{
		RequestGenerator req = getRequest(APINAME.GETALLDEALS, visible, dataType, state, channel);
		System.out.println(req.respvalidate.returnresponseasstring());
		List<Integer> endTimes = getDealEndTimes(visible, dataType, state, channel);
		for(int i = 0; i < endTimes.size(); i++){
			AssertJUnit.assertTrue("Expired deals are displayed.", endTimes.get(i)>=Integer.valueOf((int) currentTime));
		}
	}

	/**
	 * Verify getAllDeals api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "getallDealsDP_negative", priority=16, description="\n 1. Get all deals \n 2. Enter invalid channel \n 3. Verify 500 status response ")
	public void dealsService_getAllDeals_negative(String visible, String dataType, String state, String channel)
	{
		RequestGenerator req = getRequest(APINAME.GETALLDEALS, visible, dataType, state, channel);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"getAllDeals API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * Verify getDeal api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Prodsanity", "Regression", "RFPQA" }, 
			dataProvider = "dealIdDP", priority=17, description="\n 1. Get all deals \n 2. verify for 200 status response \n 3. Veify for expired deals \n 4. Verify isVisible attribute is true or false")
	public void dealsService_getDeal_vStatusAdExpiryDateAdVisible(String dealId)
	{
		RequestGenerator req = getUrlRequest(APINAME.GETDEAL, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"getDeal API is not working ",
				200, req.response.getStatus());
		AssertJUnit.assertTrue("Expired deals are displayed.",
				Integer.parseInt(req.respvalidate.NodeValue(getDealNodes().get(7), true).replaceAll("\"", ""))>=(int)currentTime );
		String visibility = req.respvalidate.NodeValue(getDealNodes().get(9), true).replaceAll("\"", "");
		AssertJUnit.assertTrue("visible does't match", 
				visibility.equalsIgnoreCase("true")||visibility.equalsIgnoreCase("false"));
	}

	/**
	 * Verify getAllDeals api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "dealIdDP_negative", priority=18, description ="\n 1. Get All deals \n 2. Verify for 404 status response code")
	public void dealsService_getDeal_negative(String dealId)
	{
		RequestGenerator req = getUrlRequest(APINAME.GETDEAL, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"getAllDeals API is not working ",
				404, req.response.getStatus());
	}

	/**
	 * Verify update deal status, nodes and values.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "updateDealDP", priority=19, description="\n 1. Update deal \n 2. Verify for 200 response status code \n 3. Verify status nodes without discounid \n 4. Verify node values")
	public void dealsService_updateDeal_vStatusAdNodesAdVals(String dealId, String name, String desc, String dealType, 
			String startTime, String endTime, String discPercent, String visible, String state, String banner)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATEDEAL, dealId, name, desc, dealType, 
				startTime, endTime, discPercent, visible, state, banner);
		System.out.println(req.returnresponseasstring());

		//status
		AssertJUnit.assertEquals(
				"updateDeal API is not working ",
				200, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval updatedeal API status nodes.", 
				req.respvalidate.DoesNodesExists(getDealNodesWithOutDiscId()));

		// nodes values
		AssertJUnit.assertEquals("name does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(2), true).replaceAll("\"", ""), name);	
		AssertJUnit.assertEquals("description does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(4), true).replaceAll("\"", ""), desc);
		AssertJUnit.assertEquals("dealType does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(5), true).replaceAll("\"", ""), dealType);
		AssertJUnit.assertEquals("description does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(6), true).replaceAll("\"", ""), startTime);
		AssertJUnit.assertEquals("dealType does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(7), true).replaceAll("\"", ""), endTime);
		AssertJUnit.assertEquals("state does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(8), true).replaceAll("\"", ""), discPercent);
		AssertJUnit.assertEquals("description does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(9), true).replaceAll("\"", ""), visible);
		AssertJUnit.assertEquals("dealType does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(10), true).replaceAll("\"", ""), state);
		AssertJUnit.assertEquals("bannerURL does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(11), true).replaceAll("\"", ""), banner);
	}

	/**
	 * Verify update deal status, nodes and values.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "updateDealDP_negative", priority=20, description="\n 1. Update deal with negative values \n 2. Verify for 409 status response code \n 3. Verify for status and err message nodes ")
	public void dealsService_updateDeal_vNegativeStatusAdNodesAdVals(String dealId, String name, String desc, String dealType, 
			String startTime, String endTime, String discPercent, String visible, String state, String banner, 
			String status, String ErrMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATEDEAL, dealId, name, desc, dealType, 
				startTime, endTime, discPercent, visible, state, banner);
		System.out.println(req.returnresponseasstring());

		//status
		AssertJUnit.assertEquals(
				"updateDeal API is not working ",
				409, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval updateState API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), status);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), ErrMessage);	
	}

	/**
	 * Verify deleteDeal api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
			dataProvider = "createInvisibleNewDealDP", priority=21, description ="\n 1. Get all deals \n 2. if deal exists then delete deal using deleteDeal api \n 3. Verify for 200 status response")
	public void dealsService_deleteDeal(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		/*
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		System.out.println(crReq.respvalidate.returnresponseasstring());
		String dealId = crReq.respvalidate.NodeValue(getDealNodes().get(0), true).replaceAll("\"", "");	
		*/
		
		RequestGenerator reqGetAllDeals = getRequest(APINAME.GETALLDEALS);
		List allDeals = JsonPath.read(reqGetAllDeals.respvalidate.returnresponseasstring(), "$[*].id");
		RequestGenerator req = getUrlRequest(APINAME.DELETEDEALS, allDeals.get(0).toString());
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"deleteDeal API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify deleteDeal api status.
	 * @author jhansi.bai
	 */
//		@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" }, 
//			dataProvider = "dealIdDP_negative", priority=22)
	@Test(dependsOnMethods={"dealsService_createnewdeal_vDiscountInPDP"},dataProvider="dealIdDP_negative",priority=22, description="\n 1. Delete deal by passing invalid deal id \n 2. Verify for 409 status response")
	public void dealsService_deleteDeal_negative(String dealId)
	{
		RequestGenerator req = getUrlRequest(APINAME.DELETEDEALS, dealId);
		System.out.println(req.respvalidate.returnresponseasstring());
		//status
		AssertJUnit.assertEquals(
				"deleteDeal API is not working ",
				404, req.response.getStatus());
	}

	/**
	 * get all visible deals
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Prodsanity", "Regression", "RFPQA" },
			dataProvider = "dealvisibilityDP", priority=23, description="\n 1. Get all visible deals \n 2. Verify for 200 status response code")
	public void dealsService_getAllVisibleDeals_vStatusAdNodesAdVals(String dataType, String state, String channel)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVISIBLEDEALS, dataType, state, channel);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllVisibleDeals API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * get all visible deals
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "dealvisibilityDP_negative", priority=24, description="\n 1. Get all visible deals with invalid attributes \n 2. Verify for 500 status response code")
	public void dealsService_getAllVisibleDeals_vNegativeStatus(String dataType, String state, String channel)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVISIBLEDEALS, dataType, state, channel);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllVisibleDeals API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateStateDP", priority=25, description ="\n 1. Update state of the deal by passing deal id \n 2. Verify for 200 status response code")
	public void dealsService_updateState_vStatusAdNodesAdVals(String dealId, String state)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATESTATE, dealId, state);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateState API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateStateDP_negative", priority=26, description="\n 1. Update state by negative value \n 2. Verify for 500 status response code")
	public void dealsService_updateState_vNegativeStatus(String dealId, String state)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATESTATE, dealId, state);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateState API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateStateDP_negative1", priority=27, description="\n 1. Update state with invalid deal id \n 2.Verify for 409 status response code \n 3. Verify for status nodes for invalid update state \n 4. Verify status and err message")
	public void dealsService_updateState_vNegativeStatusAdNodesAdVals(String dealId, String state, String status, String ErrMessage)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATESTATE, dealId, state);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateState API is not working ",
				409, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval updateState API status nodes.", 
				req.respvalidate.DoesNodesExists(getErrorMessgeNodes()));
		// nodes values
		AssertJUnit.assertEquals("status does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(0), true).replaceAll("\"", ""), status);	
		AssertJUnit.assertEquals("message does't match", 
				req.respvalidate.NodeValue(getErrorMessgeNodes().get(1), true).replaceAll("\"", ""), ErrMessage);	
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateStateDP_negative2", priority=28, description="\n 1. Update state with negative value \n 2. Verify for 404 status response code")
	public void dealsService_updateState_vNegativeStatus2(String dealId, String state)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATESTATE, dealId, state);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateState API is not working ",
				404, req.response.getStatus());
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateVisibilityDP", priority=29, description="\n 1. Update visibility of the deal \n 2. Verify for 200 status response code \n 3. Verify for invalid update visibility status nodes \n 4. Verify for visibility match")
	public void dealsService_updateVisibility_vStatusAdNodesAdVals(String dealId, String visibility)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATEVISIBILITY, dealId, visibility);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateVisibility API is not working ",
				200, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval upodate visibility API status nodes.", 
				req.respvalidate.DoesNodesExists(updateDealNodes(dealId)));			

		// nodes values
		AssertJUnit.assertEquals("visibility does't match", 
				req.respvalidate.NodeValue(updateDealNodes(dealId).get(8), true).replaceAll("\"", ""), visibility);	
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression",  "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateVisibilityDP_negative", priority=30, description ="\n 1. Update visibility of deal \n 2. Verify for 200 status response code \n 3. Verify for update visibility status nodes")
	public void dealsService_updateVisibility_vNegativeStatusAdNodesAdVals(String dealId, String visibility)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATEVISIBILITY, dealId, visibility);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateVisibility API is not working ",
				200, req.response.getStatus());

		//nodes
		AssertJUnit.assertTrue("Inval upodate visibility API status nodes.", 
				req.respvalidate.DoesNodesExists(updateDealNodes(dealId)));	
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" },
			dataProvider = "updateVisibilityDP_negative1", priority=31, description="\n 1. Update visibility of deal id \n 2. Verify for 200 status response code")
	public void dealsService_updateVisibility_vNegativeStatusAdNodes1(String dealId, String visibility)
	{
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.UPDATEVISIBILITY, dealId, visibility);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updateVisibility API is not working ",
				200, req.response.getStatus());
	}
	
	@AfterClass(groups = { "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPQA" })
	public void deleteAllDeals(){
		RequestGenerator req = getRequest(APINAME.GETALLDEALS);
		List allDeals = JsonPath.read(req.respvalidate.returnresponseasstring(), "$[*].id");
		for(Object dealId : allDeals){
			RequestGenerator reqDelete = getUrlRequest(APINAME.DELETEDEALS, dealId.toString());
		}
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_createNewDeal_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_createNewDeal_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator createNewDealReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, 
				channelType);
		String createNewDealResponse = createNewDealReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService createNewDeal API response :\n\n"+createNewDealResponse);
		log.info("\nPrinting DealsService createNewDeal API response :\n\n"+createNewDealResponse);
		
		AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, createNewDealReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-createnewdeal-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNewDealResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService createNewDeal API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_createNewDealWithMultipleStyleIdsChannelTypes_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_createNewDealWithMultipleStyleIdsChannelTypes_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, 
			String startTime, String endTime, String visible, String state, String discPercent, String banner, String styleId1, String styleId2, String styleId3,
			String channelType1, String channelType2, String channelType3)
	{
		RequestGenerator createnewdealWithMultipleStyleIdsChannelTypesReqGen = getRequest(APINAME.CREATENEWDEALWITHMULTIPLESTYLEIDSCHANNELTYPES, name, desc, dealType, 
				startTime, endTime, visible, state, discPercent, banner, styleId1, styleId2, styleId3, channelType1, channelType2, channelType3);
		String createnewdealWithMultipleStyleIdsChannelTypesResponse = createnewdealWithMultipleStyleIdsChannelTypesReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService createNewDealWithMultipleStyleIdsChannelTypes API response :\n\n"+createnewdealWithMultipleStyleIdsChannelTypesResponse);
		log.info("\nPrinting DealsService createNewDealWithMultipleStyleIdsChannelTypes API response :\n\n"+createnewdealWithMultipleStyleIdsChannelTypesResponse);
		
		AssertJUnit.assertEquals("DealsService createNewDealWithMultipleStyleIdsChannelTypes API is not working", 200, 
				createnewdealWithMultipleStyleIdsChannelTypesReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-createnewdealwithmultiplestyleidschanneltypes-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createnewdealWithMultipleStyleIdsChannelTypesResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService createNewDealWithMultipleStyleIdsChannelTypes API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_createInvisibleNewDeal_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_addStyleIds_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator createNewDealReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, 
				channelType);

		AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, createNewDealReqGen.response.getStatus());
		String dealId = createNewDealReqGen.respvalidate.NodeValue(getDealNodes().get(0), true).replaceAll("\"", "");	

		RequestGenerator addStyleIdsReqGen = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId, dealId);
		String addStyleIdsResponse = addStyleIdsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService addStyleIds API response :\n\n"+addStyleIdsResponse);
		log.info("\nPrinting DealsService addStyleIds API response :\n\n"+addStyleIdsResponse);
		
		AssertJUnit.assertEquals("DealsService addStyleIds API is not working", 200, addStyleIdsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-addstyleids-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(addStyleIdsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService addStyleIds API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_createInvisibleNewDealForDelete_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_deleteStyleIds_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, channelType);
		String dealId = crReq.respvalidate.NodeValue(getDealNodes().get(0), true).replaceAll("\"", "");	

		RequestGenerator deleteStyleIdsReqGen = getQuerryPayLoadRequest(APINAME.DELETESTYLEIDS, styleId, dealId);
		String deleteStyleIdsResponse = deleteStyleIdsReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService deleteStyleIds API response :\n\n"+deleteStyleIdsResponse);
		log.info("\nPrinting DealsService deleteStyleIds API response :\n\n"+deleteStyleIdsResponse);
		
		AssertJUnit.assertEquals("DealsService deleteStyleIds API is not working", 200, deleteStyleIdsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-deletestyleids-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deleteStyleIdsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService deleteStyleIds API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_getAllDeals_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_getAllDeals_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, channelType);
		AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		
		RequestGenerator getAllDealsReqGen = getRequest(APINAME.GETALLDEALS);
		String getAllDealsResponse = getAllDealsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DealsService getAllDeals API response :\n\n"+getAllDealsResponse);
		log.info("\nPrinting DealsService getAllDeals API response :\n\n"+getAllDealsResponse);
		
		AssertJUnit.assertEquals("DealsService getAllDeals API is not working", 200, getAllDealsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-getalldeals-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllDealsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService getAllDeals API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_getDealById_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_getDealById_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		List<Integer> dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		if(CollectionUtils.isEmpty(dealIds)){
			RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, 
					channelType);
			AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		}
		getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		RequestGenerator getDealByIdReqGen = getUrlRequest(APINAME.GETDEAL, dealIds.get(0).toString());
		String getDealByIdResponse = getDealByIdReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService getDealById API response :\n\n"+getDealByIdResponse);
		log.info("\nPrinting DealsService getDealById API response :\n\n"+getDealByIdResponse);
		
		AssertJUnit.assertEquals("DealsService getDealById API is not working", 200, getDealByIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-getdealbyid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getDealByIdResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService getDealById API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_updateDeal_verifyResponseDataNodesUsingSchemaValidations")
	public void dealsService_updateDeal_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, channelType);
		AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		
		RequestGenerator getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		List<Integer> dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		int one = new Random().nextInt(100);
		startTime = String.valueOf(currentTime+2000);
		endTime = String.valueOf(currentTime+45000);
		
		RequestGenerator updateDealReqGen = getQuerryPayLoadRequest(APINAME.UPDATEDEAL, dealIds.get(0).toString(), "Update name"+one, "Updated Desc"+one, "1", startTime,
				endTime, "60", "false", "0", "https://summeressentials1.com");
		String updateDealResponse = updateDealReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting DealsService updateDeal API response :\n\n"+updateDealResponse);
		log.info("\nPrinting DealsService updateDeal API response :\n\n"+updateDealResponse);
		
		AssertJUnit.assertEquals("DealsService updateDeal API is not working", 200, updateDealReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-updatedeal-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateDealResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService updateDeal API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/* 
	 * This scenario is invalid due to no response from the dealsService_deleteDeal api, hence deprecating
	 *
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_deleteDeal_verifyResponseDataNodesUsingSchemaValidations")
	public void DealsService_deleteDeal_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		List<Integer> dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		if(CollectionUtils.isEmpty(dealIds)){
			getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, channelType);
			AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		}
		getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		RequestGenerator deleteDealsReqGen = getUrlRequest(APINAME.DELETEDEALS, dealIds.get(0).toString());
		String deleteDealsResponse = deleteDealsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DealsService deleteDeals API response :\n\n"+deleteDealsResponse);
		log.info("\nPrinting DealsService deleteDeals API response :\n\n"+deleteDealsResponse);
		
		AssertJUnit.assertEquals("DealsService deleteDeals API is not working", 200, deleteDealsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-deletedeals-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deleteDealsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService deleteDeals API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}*/
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_getAllVisibleDeals_verifyResponseDataNodesUsingSchemaValidations")
	public void dealsService_getAllVisibleDeals_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, channelType);
		AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		
		RequestGenerator getAllVisibleDealsReqGen = getRequest(APINAME.GETALLVISIBLEDEALS, dealType, state, channelType);
		String getAllVisibleDealsResponse = getAllVisibleDealsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting DealsService getAllVisibleDeals API response :\n\n"+getAllVisibleDealsResponse);
		log.info("\nPrinting DealsService getAllVisibleDeals API response :\n\n"+getAllVisibleDealsResponse);
		
		AssertJUnit.assertEquals("DealsService getAllVisibleDeals API is not working", 200, getAllVisibleDealsReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-getallvisibledeals-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllVisibleDealsResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService getAllVisibleDeals API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_updateState_verifyResponseDataNodesUsingSchemaValidations")
	public void dealsService_updateState_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType, String updatedState)
	{
		RequestGenerator getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		List<Integer> dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		if(CollectionUtils.isEmpty(dealIds)){
			RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, 
					channelType);
			AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		}
		getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		RequestGenerator updateStateReqGen = getQuerryPayLoadRequest(APINAME.UPDATESTATE, dealIds.get(0).toString(), updatedState);
		String updateStateResponse = updateStateReqGen.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("DealsService updateState API is not working", 200, updateStateReqGen.response.getStatus());
		
		JSONObject resp = JsonPath.read(updateStateResponse, "$."+dealIds.get(0).toString());
		updateStateResponse = resp.toJSONString();
		System.out.println("\nPrinting DealsService updateState API response :\n\n"+updateStateResponse);
		log.info("\nPrinting DealsService updateState API response :\n\n"+updateStateResponse);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-updatestate-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateStateResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService updateState API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "DealsServiceDP_updateVisibility_verifyResponseDataNodesUsingSchemaValidations")
	public void dealsService_updateVisibility_verifyResponseDataNodesUsingSchemaValidations(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType, String updatedVisibility)
	{
		RequestGenerator getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		List<Integer> dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		if(CollectionUtils.isEmpty(dealIds)){
			RequestGenerator crReqGen = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state, discPercent, banner, styleId, 
					channelType);
			AssertJUnit.assertEquals("DealsService createNewDeal API is not working", 200, crReqGen.response.getStatus());
		}
		getAllDealReqGen = getRequest(APINAME.GETALLDEALS);
		AssertJUnit.assertEquals("DealsService getAllDeal API is not working", 200, getAllDealReqGen.response.getStatus());
		dealIds = JsonPath.read(getAllDealReqGen.respvalidate.returnresponseasstring(), "$[*].id");
		
		RequestGenerator updateVisibilityReqGen = getQuerryPayLoadRequest(APINAME.UPDATEVISIBILITY, dealIds.get(0).toString(), updatedVisibility);
		String updateVisibilityResponse = updateVisibilityReqGen.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("DealsService updateVisibility API is not working", 200, updateVisibilityReqGen.response.getStatus());
		
		JSONObject resp = JsonPath.read(updateVisibilityResponse, "$."+dealIds.get(0).toString());
		updateVisibilityResponse = resp.toJSONString();
		System.out.println("\nPrinting DealsService updateVisibility API response :\n\n"+updateVisibilityResponse);
		log.info("\nPrinting DealsService updateVisibility API response :\n\n"+updateVisibilityResponse);
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/dealsservice-updatevisibility-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updateVisibilityResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in DealsService updateVisibility API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	//---------------------------------
	//API NAME: getstyledataget
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealAndMatchDiscountwithPDP", priority=32)
	public void dealsService_createnewdeal_vDiscountInPDP(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		System.out.println(req.respvalidate.returnresponseasstring());
		int dealdiscount=JsonPath.read(req.respvalidate.returnresponseasstring(), "$[*].discountPercent");
		String dealDisStr=String.valueOf(dealdiscount).toString();
		String dealStyleId=JsonPath.read(req.respvalidate.returnresponseasstring(),"$[*].dealStyles..styleId").toString();// "$[*].dealStyles..styleId[0]");
		System.out.println("Deal Discount createad---->>> "+dealDisStr);
	    System.out.println("Deal style id---->>> "+dealStyleId);
	    RequestGenerator discountPercentFromStyleServiceReq = styleServiceApiHelper.getStyleDataForSingleStyleId1(styleId);//, "$.data..discountData..discountPercent");
	    String stylServresp=discountPercentFromStyleServiceReq.respvalidate.returnresponseasstring();
	    System.out.println("StyleService discount data : ---> "+stylServresp);
	    double discountPercentFromStyleResponse=JsonPath.read(stylServresp, "$.data[0].discountData.discountPercent");
	    System.out.println("Disount percent from Style service:---> "+discountPercentFromStyleResponse);
		//status 
	    int disPerInt=(int)discountPercentFromStyleResponse;
	  // String disPercentFrmStylResp=String.valueOf(discountPercentFromStyleResponse).toString();
	    System.out.println("Disount percent from Style service:---> "+disPerInt);
		//status
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				200, req.response.getStatus());
		AssertJUnit.assertEquals(dealdiscount,disPerInt);
		
	}
	
	//--

	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
			dataProvider = "createNewDealAndVerifyEndTime", priority=33)
	public void dealsService_createnewdeal_Vendtime(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType) throws InterruptedException
	{
		RequestGenerator createNewDealreq = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		System.out.println("Create New Deal response: --> "+createNewDealreq.respvalidate.returnresponseasstring());
		RequestGenerator getAlldealreqbeforeEndTime = getRequest(APINAME.GETALLDEALS);
		System.out.println("Get All deal response: "+getAlldealreqbeforeEndTime.returnresponseasstring());
		String getAllDealResId=JsonPath.read(getAlldealreqbeforeEndTime.returnresponseasstring(), "$..id").toString();
		System.out.println("Created Deal id reflecting before endtime:  "+getAllDealResId);
		AssertJUnit.assertEquals(
			"get deal API is not working before end time",200, getAlldealreqbeforeEndTime.response.getStatus());
		//Wait for 4 minutes and check deal visibility in getDeal api after end time
		System.out.println("started 4 minutes wait to check endtime : ");
		TimeUnit.MINUTES.sleep(4);
		System.out.println("4 minutes completed");
		RequestGenerator getdealreqAfterEndTime = getRequest(APINAME.GETALLDEALS);
		String getDealResp=getdealreqAfterEndTime.returnresponseasstring();
		System.out.println("Get All Deals API Response ---->> : "+getDealResp);
		//JSONArray x = JsonPath.read(getDealResp, "$..id".toString());
		if(getDealResp.length()==0){
			AssertJUnit.assertEquals("No empty response after endtime, deal id visible", "[]",null);
		}
		AssertJUnit.assertEquals("get deal API is not working ",200, getAlldealreqbeforeEndTime.response.getStatus());
		
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
			dataProvider = "addNewStyleForExistingDealDataProvider", priority=34)
	public void dealsService_VaddStleToexistingDeal(String name, String desc, String dealType, String startTime, String endTime, 
			String visible, String state, String discPercent, String banner, String styleId, String channelType)
	
	{
		List<Integer>styleidAfterAddingdeal=new ArrayList<Integer>();
		List<Integer>StyleIdFromGetDeal=new ArrayList<Integer>();
		RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
				discPercent, banner, styleId, channelType);
		String createNewDealRes=req.respvalidate.returnresponseasstring();
		System.out.println("create new deal resp: __> "+createNewDealRes);
		String dealId=JsonPath.read(createNewDealRes,"$.id").toString();
		//String dealdId1=dealId.toString();
		System.out.println("Deal Id is : "+dealId);
		//status
		AssertJUnit.assertEquals(
				"createnewdeal API is not working ",
				200, req.response.getStatus());
		List<Integer> styleIdList = OrderDiscounhelper.getStyleIdsUsingSearchAPI();
		String styleId1=styleIdList.get(0).toString();
		System.out.println("New style id from style service : "+styleId1);
		RequestGenerator addStyleToExistingDealReq = getQuerryPayLoadRequest(APINAME.ADDSTYLETOEXISTINGDEAL, styleId1, dealId);
		String addStylToExistingDealRes=addStyleToExistingDealReq.respvalidate.returnresponseasstring();
		System.out.println("Add style to existing deal response ---> "+ addStylToExistingDealRes);
		styleidAfterAddingdeal=JsonPath.read(addStylToExistingDealRes, "$.dealStyles..styleId".toString());
		RequestGenerator GetDealReq = getUrlRequest(APINAME.GETDEAL, dealId);
		String GetDealRes=GetDealReq.respvalidate.returnresponseasstring();
		System.out.println("GetDealRes :----> "+GetDealRes);
		StyleIdFromGetDeal=JsonPath.read(GetDealRes, "$.dealStyles..styleId".toString());
		for (int i=0;i<styleidAfterAddingdeal.size();i++) {
			System.out.println("Style ids after adding for existing deal---> "+styleidAfterAddingdeal.get(i));
			Assert.assertEquals(styleidAfterAddingdeal, StyleIdFromGetDeal);

		}
		
	}
	
	//As delete deal is deleting deals from db but deal visible in cache, therefore dealid reflecting after deleting deal. Dev needs to fix this. Will work on this test case after fix
	
	
		@Test
		(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
				dataProvider = "addNewStyleForExistingDealDataProvider", priority=35)
		public void dealsService_VdeleteDealBeforeEndTime(String name, String desc, String dealType, String startTime, String endTime, 
				String visible, String state, String discPercent, String banner, String styleId, String channelType)
		
		{
			
			RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
					discPercent, banner, styleId, channelType);
			String createNewDealRes=req.respvalidate.returnresponseasstring();
			System.out.println("create new deal resp: __> "+createNewDealRes);
			String dealId=JsonPath.read(createNewDealRes,"$.id").toString();
			System.out.println("Deal id ----> "+dealId);
			RequestGenerator getAlldealreqbeforeEndTime = getRequest(APINAME.GETALLDEALS);
			System.out.println("Get All deal response: "+getAlldealreqbeforeEndTime.returnresponseasstring());
			String getAllDealResId=JsonPath.read(getAlldealreqbeforeEndTime.returnresponseasstring(), "$..id[0]").toString();
			System.out.println("Get All Deal response id ---> "+getAllDealResId);
			//Assert.assertEquals(dealId, getAllDealResId);
			RequestGenerator reqGetAllDeals = getRequest(APINAME.GETALLDEALS);
			List allDeals = JsonPath.read(reqGetAllDeals.respvalidate.returnresponseasstring(), "$[*].id");
			RequestGenerator deleteDealId = getUrlRequest(APINAME.DELETEDEALS, allDeals.get(0).toString());
			System.out.println(deleteDealId.respvalidate.returnresponseasstring());
			RequestGenerator getAlldealreqAfterEndTime = getRequest(APINAME.GETALLDEALS);
			System.out.println("Get All deal response: "+getAlldealreqAfterEndTime.returnresponseasstring());
			String Response = deleteDealId.respvalidate.returnresponseasstring();
			JSONArray array = new JSONArray();
			array = JsonPath.read(Response, "$[*]");
			int ArraySize = array.size();
			if(ArraySize==0){
				AssertJUnit.assertEquals("No empty response after delete deal, deal id visible", ArraySize,0);
			}
			
			
		}
		
		@Test
		(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
				dataProvider = "createNewDealStartTimeEndtimeDataProvider", priority=36)
		public void dealsService_createnewdeal_vStartTimeAndEndTime(String name, String desc, String dealType, String startTime, String endTime, 
				String visible, String state, String discPercent, String banner, String styleId, String channelType)
		{
			RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
					discPercent, banner, styleId, channelType);
			System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("startTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(6), true).replaceAll("\"", ""), startTime);
		AssertJUnit.assertEquals("EndTime does't match", 
				req.respvalidate.NodeValue(getDealNodes().get(7), true).replaceAll("\"", ""), endTime);
		}
		
		@Test
		(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression", "RFPQA" }, 
				dataProvider = "createNewDealStartTimeEndtimeDataProvider", priority=0)
		public void dealsService_createnewdeal_vVisibility(String name, String desc, String dealType, String startTime, String endTime, 
				String visible, String state, String discPercent, String banner, String styleId, String channelType)
		{
			RequestGenerator req = getRequest(APINAME.CREATENEWDEAL, name, desc, dealType, startTime, endTime, visible, state,
					discPercent, banner, styleId, channelType);
			String createDealRes=req.respvalidate.returnresponseasstring();
			System.out.println("Create deal resp "+createDealRes);
			String dealId=JsonPath.read(createDealRes, "$.id".toString()).toString();
			System.out.println("Create new deal, dealid: "+dealId);
			
			RequestGenerator updateVisibility = getQuerryPayLoadRequest(APINAME.UPDATEVISIBILITY, dealId,"false");
			System.out.println("Update Visibility response: "+updateVisibility.respvalidate.returnresponseasstring());
			RequestGenerator req1 = getUrlRequest(APINAME.GETDEAL, dealId);
			String dealIdAfterUpdate = req1.respvalidate.returnresponseasstring();
			System.out.println("Deal id after update in get deal"+ dealIdAfterUpdate);
			boolean dealvisiblityAftrUpdat=JsonPath.read(dealIdAfterUpdate, "$.visible");
			Assert.assertFalse(dealvisiblityAftrUpdat);
			
			
		}
}
	
	
