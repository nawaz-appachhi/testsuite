package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.BestPractisesDP;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;


public class BestPractises extends BestPractisesDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DevApis.class);
	APIUtilities apiUtil = new APIUtilities();
	static int quesId = 0;
	static int outfitId = 0;
	int idofPreviousCall = 0;
	int idofComment = 0;
	private int toCheckCommentId;
	
	@Test(groups = { /* "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression" */ }, dataProvider = "updateExistingNegativeDP")
	public void FasionDestination_updateExistingUserNegative(String id, String name, String email, String role, String from, String bio)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_FASHIONCORNERDESTINATION, APINAME.FCUPDATEEXISTINGUSER, init.Configurations, new String[]{name, email, role, from, bio});
		service.URL = apiUtil.prepareparameterizedURL(service.URL, id);
		System.out.println("URL : "+service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResponse);
		AssertJUnit.assertEquals(200, req.response.getStatus());	
	}
	
	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative")
	public void ABService_getVariantOfGivenABTest_negative(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}	
	
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getvariantofgivenabtestwithABtestDP_negative")
	public void ABService_getVariantOfGivenABTestWithABtest_negative(String userid, String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHABTEST, userid, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}
	
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getvariantofgivenabtestwithsegmentDP_negative")
	public void ABService_getVariantOfGivenABTestWithSegment_negative(String xid, String ABTestName) {
		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTESTWITHSEGMENT, xid, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 200,
				req.response.getStatus());
	}
	
	@Test(groups = { /* "Sanity", "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative1")
	public void ABService_getVariantOfGivenABTest_negative1(String ABTestName) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getVariantOfGivenABTest is not working", 405,
				req.response.getStatus());
	}
	
	@Test(groups = { /* "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative")
	public void ABService_getVariantOfGivenABTest_vNegativeStatusNodes(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval ABtest variants API status nodes.", 
				req.respvalidate.DoesNodesExists(abTestsStatusNodes()));
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	@Test(groups = { /* "Regression", "MiniRegression", "ExhaustiveRegression", "RFPFOX7" */ }, 
			dataProvider = "getVariantOfGivenABTestDP_negative")
	public void ABService_getVariantOfGivenABTest_vNegativeStatusNodesVals(String ABTestName, String errStatusCode, String errorMessage, 
			String errorStatus, String itemCount) {

		RequestGenerator req = getQuerryPayLoadRequest(APINAME.GETVARIANTOFGIVENABTEST, ABTestName);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(0), true), 
				errStatusCode);			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(abTestsStatusNodes().get(1), true), 
				errorMessage);		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(2), true), 
				errorStatus);
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(abTestsStatusNodes().get(3), true), 
				itemCount);
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vStatusAdDataNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(getAllPageStatusNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(getAllPageVersionsWithWidgetDataNodes()));
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vSuccStatusMsg(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(0), true), "15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
				"\"PageVersion retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status with (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vStatusAdDataNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(getAllPageStatusNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(getAllPageVersionsWithWidgetDataNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(getAllWidgetsNodes()));
	}
	
	/**
	 * Verify invalidcache api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7" */ },
			dataProvider = "cachesDP_negative")
	public void PageConfigurator_invalidcache_vNegativeSuccStatusMsg(String cache)	{
		RequestGenerator req = getRequest(APINAME.INVALIDCACHE, cache);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(0), true), "51");
		if(!(cache==null)){
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
				"\"Cache with name: "+cache+" is not found.\"");	
		}else{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
					"\"Cache with name: ${0} is not found.\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
		
	@Test(groups = { /* "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" */}, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_vSuccStatusMsg_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(0), true), "56");	
		if(!(pageVer == null)){
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
					"\"Could not get widgets for version = "+pageVer+": No entity found for query\"");	
		}else
		{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
					"\"Could not get widgets for version = ${0}: No entity found for query\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify GETALLPAGEVARIANTS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7" */ },
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderNegative")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_vSuccStatusMsg_negative(String widgetId)	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(0), true), "56");	
		if(!(widgetId == null)){
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
					"\"Could not get current version for widget = "+widgetId+": No entity found for query\"");	
		}else
		{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
					"\"Could not get current version for widget = ${0}: No entity found for query\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vSuccStatusMsg(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(0), true), "15002");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(getAllPageStatusNodes().get(1), true), 
				"\"PageVersion retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}
}
