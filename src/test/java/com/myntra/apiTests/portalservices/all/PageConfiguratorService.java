package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.dataproviders.PageConfiguratorDP;
import com.myntra.apiTests.portalservices.configservice.pageconfig.PageConfigServiceApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author shankara.c
 *
 */
public class PageConfiguratorService extends PageConfiguratorDP
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PageConfiguratorService.class);
	APIUtilities apiUtil = new APIUtilities();

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllPageType()
	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllPageType API is not working ", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", 
			"ExhaustiveRegression", "Regression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllPageVariants()
	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVARIANTS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllPageVariants API is not working ", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "ExhaustiveRegression",
			"Regression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllPageVersions()
	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllPageVersions API is not working ", 200,
				req.response.getStatus());
	}

	/*	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "getAllInfoGivenPageTypeAndPageUrlDataProvider")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl(
			String param1, String param2)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, param1);
		System.out.println(service.URL);
		HashMap inputHeader = new HashMap();
		inputHeader.put("pageURL", param2);
		RequestGenerator req = new RequestGenerator(service, inputHeader);
		log.info(service.URL);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllPageVersions API is not working ", 200,
				req.response.getStatus());
	}
	 */
/*	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "getAllPageVersionsAssociatedWithWidgetIdDataProvider")
	public void PageConfigurator_getAllPageVersionsAssociatedWithWidgetId(
			String version)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID,
				init.Configurations, PayloadType.JSON,
				new String[] { version }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllPageVersionsAssociatedWithWidgetId API is not working",
				200, req.response.getStatus());
	}
*/
	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression",
			"Regression", "MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllWidgetsAssociatedWithPageVersion API is not working",
				200, req.response.getStatus());
	}

	@Test(groups = { "ProdSanity", "Sanity", "ExhaustiveRegression","Regression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllWidgets()
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllWidgets API is not working ", 200,
				req.response.getStatus());
	}

	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 	
			"MiniRegression", "RFPFOX7", "RFPlaxmiTc" }, dataProvider = "createNewPageVersionDataProvider")
	public void PageConfigurator_createNewPageVersion(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageVersion API is not working ",
				200, req.response.getStatus());
	}

	/*	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageTypeDataProvider")
	public void PageConfigurator_createNewPageType(String param1, String param2)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.CREATENEWPAGETYPE,
				init.Configurations, new String[] { param1, param2 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageType API is not working ", 200,
				req.response.getStatus());
	}
	 */
/*	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageVariantDataProvider")
	public void PageConfigurator_createNewPageVariant(String param1,
			String param2, String param3)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.CREATENEWPAGEVARIANT,
				init.Configurations, new String[] { param1, param2, param3 },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageVariant API is not working ",
				200, req.response.getStatus());
	}*/
/*
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", "RFPFOX7" }, dataProvider = "mapPageVariantToversionDataProvider")
	public void PageConfigurator_mapPageVariantToversion(String param1,
			String param2, String param3, String param4)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.MAPPAGEVARIANTTOPAGEVERSION, init.Configurations,
				new String[] { param1, param2, param3, param4 },
				PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, param4);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("mapPageVariantToversion API is not working ",
				200, req.response.getStatus());
	}
*/
/*	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", "RFPFOX7" }, dataProvider = "mapPageTypeToversionDataProvider")
	public void PageConfigurator_mapPageTypeToversion(String param1,
			String param2, String param3)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.MAPPAGETYPETOPAGEVERSION, init.Configurations,
				new String[] { param1, param2, param3 }, PayloadType.JSON,
				PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, param3);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("mapPageTypeToversion API is not working ",
				200, req.response.getStatus());
	}*/

/*	@Test(groups = { "ExhaustiveRegression", "Sanity", "Regression", "RFPFOX7" }, dataProvider = "createPageVersionWithWidgetDataDataProvider")
	public void PageConfigurator_createPageVersionWithWidgetData(String param1,
			String param2, String param3, String param4, String param5,
			String param6, String param7)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.CREATEPAGEVERSIONWITHWIDGETDATA, init.Configurations,
				new String[] { param1, param2, param3, param4, param5, param6,
						param7 }, PayloadType.JSON, PayloadType.JSON);
		// service.URL = apiUtil.prepareparameterizedURL(service.URL,
		// payload[3]);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"createPageVersionWithWidgetData API is not working ", 200,
				req.response.getStatus());
	}
*/
	/*@Test(groups = { "ExhaustiveRegression", "Sanity", "Regression", "RFPFOX7" }, dataProvider = "createUpdateListOfWidgetsDataProvider")
	public void PageConfigurator_createUpdateListOfWidgets(String param1,
			String param2, String param3, String param4, String param5,
			String param6)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.CREATEUPDATELISTOFWIDGETS,
				init.Configurations,
				new String[] { param1, param2, param3, param4, param5, param6 },
				PayloadType.JSON, PayloadType.JSON);
		// service.URL = apiUtil.prepareparameterizedURL(service.URL,
		// payload[3]);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(service.Payload);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"createUpdateListOfWidgets API is not working ", 200,
				req.response.getStatus());
	}*/

	@Test(groups = { "ProdSanity", "MiniRegression", "ExhaustiveRegression", "Sanity", "Regression", "RFPFOX7", "RFPPROD" }, dataProvider = "getPageType")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllVariantsAssociatedWithPageType API is not working ",
				200, req.response.getStatus());
	}

	//-------------------------------------------------------------------------------------------


	/**
	 * Verify GETPAGETYPE api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllPageType_verifyStatusNodes()
	{
		RequestGenerator req = getRequest(APINAME.GETPAGETYPE);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllPageType API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify GETPAGETYPE api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity", "ProdSanity", "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getAllPageType_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETPAGETYPE);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageType retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify GETALLPAGEVARIANTS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"ExhaustiveRegression", "Regression", "MiniRegression",  "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getAllPageVariants_verifyStatusNodes()
	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVARIANTS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllPageVariants API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify GETALLPAGEVARIANTS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getAllPageVariants_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVARIANTS);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageVariant retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify GETALLPAGEVERSIONS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "ExhaustiveRegression",
			"Regression", "MiniRegression",  "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllPageVersions_verifyStatusNodes()
	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONS);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllPageVersions API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify GETALLPAGEVARIANTS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getAllPageVersions_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONS);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageVersion retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify GETALLWIDGETS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getAllWidgets_verifyStatusNodes()
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETS);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("getAllWidgetsStatusNodes not matching", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		/*
		AssertJUnit.assertTrue("getAllWidgetsDataNodes not matching", 
				req.respvalidate.DoesNodesExists(getAllWidgetDataNodes()));
		*/
	}

	/**
	 * Verify GETALLWIDGETS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getAllWidgets_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETS);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Widget retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	//These 2 test cases have moved to 'BestPractises' class.
	/*
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vStatusAdDataNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageVersionsWithWidgetDataNodes()));
	}
	
	 /*
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vSuccStatusMsg(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"PageVersion retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_negative(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_verifyNegativeStatusNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vNegativeSuccStatusMsg(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Could not get current version: No entity found for query\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
						dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_negative1(String userType, String abtestName, String abtestVariant, String pageType)	{
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_verifyNegativeStatusNodes1(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (Without widget data):.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariant_vNegativeSuccStatusMsg1(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Error occurred while retrieving/processing data\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	//These 2 test cases have moved to 'BestPractises' class
/*	*//**
	 * Verify getcurrentversionforpagetypeandvariant api status with (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 *//*
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	
	 /* Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { /* "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" */ }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vStatusAdDataNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageVersionsWithWidgetDataNodes()));
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllWidgetsNodes()));
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
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"PageVersion retrieved successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_negative(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vNegativeStatusNodes(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vNegativeSuccStatusMsg(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Could not get current version: No entity found for query\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_negative1(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 200,
				req.response.getStatus());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vNegativeStatusNodes1(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllWidgets API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative1")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_vNegativeSuccStatusMsg1(String userType, String abtestName, String abtestVariant, String pageType)	{
		
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Error occurred while retrieving/processing data\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify getcurrentversionforpagetypeandvariant api status (With widget data) - Need to set header "widgetDataRequired" = true.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
						dataProvider = "getCurrentVersionForPageTypeAndVariantDP_negative2")
	public void PageConfigurator_getcurrentversionforpagetypeandvariantWithwidgetDataRequired_negative2(String userType, String abtestName, String abtestVariant, String pageType)	{
		RequestGenerator req = getQuerryPayLoadAdHeadersRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, abtestName, abtestVariant, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getcurrentversionforpagetypeandvariant API is not working ", 500,
				req.response.getStatus());
	}

	/**
	 * Verify getallpageversionsassociatedwithwidgetid api status.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Prodsanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderPositive")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid(String widgetId){
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getallpageversionsassociatedwithwidgetid API is not working",200,req.response.getStatus());
	}

	/**
	 * Verify getallpageversionsassociatedwithwidgetid api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"},
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderPositive")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_vSuccStatusMsg(String widgetId)	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageVersion retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getallpageversionsassociatedwithwidgetid api status.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Smoke", "Sanity", "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderNegative")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_negative(String widgetId){
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getallpageversionsassociatedwithwidgetid API is not working",200,req.response.getStatus());
	}

	/**
	 * Verify GETALLPAGEVARIANTS api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderNegative")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_vSuccStatusMsg_negative(String widgetId)	{
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		if(!(widgetId == null)){
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Could not get current version for widget = "+widgetId+": No entity found for query\"");	
		}else
		{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Could not get current version for widget = ${0}: No entity found for query\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		System.out.println(req.respvalidate.returnresponseasstring());
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getallpageversionsassociatedwithwidgetid api status.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderPositive")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_verifyStatusAdDataNodes(String widgetId){
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageVersionsWithWidgetDataNodes()));	}
	}

	/**
	 * Verify getallpageversionsassociatedwithwidgetid api status.
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getallpageversionsassociatedwithwidgetidDataProviderNegative")
	public void PageConfigurator_getallpageversionsassociatedwithwidgetid_verifyStatusNodes_negative(String widgetId){		
		RequestGenerator req = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllWidgetsAssociatedWithPageVersion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllWidgetsAssociatedWithPageVersion API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getAllWidgetsAssociatedWithPageVersion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_vSuccStatusMsg(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Widget retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getAllWidgetsAssociatedWithPageVersion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = {"ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_vSuccStatusMsg_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		if(!(pageVer == null)){
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Could not get widgets for version = "+pageVer+": No entity found for query\"");	
		}else
		{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Could not get widgets for version = ${0}: No entity found for query\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getAllWidgetsAssociatedWithPageVersion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_verifyStatusAdDataNodes(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllIdAdWidgetIdNodes()));
		}	
	}

	/**
	 * Verify getAllWidgetsAssociatedWithPageVersion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_verifyStatusNodes_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getallpageversionsassociatedwithwidgetid API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "MiniRegression", "Regression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getPageType")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_verifyStatusNodes(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllVariantsAssociatedWithPageType API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"},
			dataProvider = "getPageType")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType__vSuccStatusMsg(String pageType)	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageType retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "Regression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_negative(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllVariantsAssociatedWithPageType API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "Regression", "MiniRegression", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative1")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_negative500Status(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllVariantsAssociatedWithPageType API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "MiniRegression", "Regression", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_negative_verifyStatusNodes(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllVariantsAssociatedWithPageType API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllVariantsAssociatedWithPageType api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_negative__vSuccStatusMsg(String pageType)	{
		RequestGenerator req = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "56");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Error occurred while retrieving/processing data\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpageversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression",
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpageversion(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		AssertJUnit.assertEquals(
				"getpageversion API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpageversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpageversion_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpageversion API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpageversion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpageversion_vSuccStatusMsg(String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageType retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpageversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpageversion_vNegativeSuccStatusMsg(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Page Version not found\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpageversion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpageversion_verifyStatusAdDataNodes(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		AssertJUnit.assertTrue("Inval getpageversion API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getpageversion API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageVersionsWithWidgetDataNodes()));
		}	
	}

	/**
	 * Verify getpageversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpageversion_verifyStatusNodes_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getpageversion API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getPageType")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllInfoGivenPageTypeAndPageUrl API is not working ", 200,
				req.response.getStatus());
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getPageType")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_verifyStatusAdDataNodes(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllInfoGivenPageTypeAndPageUrl API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getAllInfoGivenPageTypeAndPageUrl API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageTypeDataNodes()));
		}	
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = {"ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getPageType")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_vSuccStatusMsg(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15002");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"PageType retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_negative(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllInfoGivenPageTypeAndPageUrl API is not working ", 200,
				req.response.getStatus());
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_verifyNegativeStatusNodes(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllInfoGivenPageTypeAndPageUrl API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_vNegativeSuccStatusMsg(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Page Type not found\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getAllInfoGivenPageTypeAndPageUrl api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getPageTypeNegative1")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl1_negative500Status(
			String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllInfoGivenPageTypeAndPageUrl API is not working ", 500,
				req.response.getStatus());
	}

	/**
	 * Verify getpageversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ProdSanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpagemappingversion(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		//	System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpagemappingversion API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpagemappingversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpagemappingversion_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpagemappingversion API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpagemappingversion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpagemappingversion_vSuccStatusMsg(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Mapping retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpagemappingversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpagemappingversion_vSuccStatusMsg_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Page Version not found\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpagemappingversion api status, and data nodes.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "getVersionDP")
	public void PageConfigurator_getpagemappingversion_verifyStatusAdDataNodes(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getpagemappingversion API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getpagemappingversion API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapPageVersionsDataNodes()));
			AssertJUnit.assertTrue("Inval getpagemappingversion API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapVersionsDataNodes()));
		}	
	}

	/**
	 * Verify getpagemappingversion api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression", 
			"MiniRegression", "RFPFOX7" }, dataProvider = "getVersionDP_negative")
	public void PageConfigurator_getpagemappingversion_verifyStatusNodes_negative(
			String pageVer)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getpagemappingversion API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getpagemapPage api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "Prodsanity", "Regression", "MiniRegression", "RFPFOX7" , "RFPPROD"}, 
			dataProvider = "getPageType")
	public void PageConfigurator_getpagemapPage(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpagemapPage API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpagemapPage api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "MiniRegression", "Regression", "RFPFOX7", "RFPPROD" }, 
			dataProvider = "getPageType")
	public void PageConfigurator_getpagemapPage_verifyStatusNodes(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getpagemapPage API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapPageVersionsDataNodes()));
			AssertJUnit.assertTrue("Inval getpagemapPage API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapVersionsDataNodes()));
		}	
	}

	/**
	 * Verify getpagemapPage api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"},
			dataProvider = "getPageType")
	public void PageConfigurator_getpagemapPage_vSuccStatusMsg(String pageType)	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Mapping retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpagemapPage api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression","Regression", "ExhasutiveRegression", "Sanity", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getpagemapPage_negative(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpagemapPage API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpagemapPage api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression","Regression", "ExhasutiveRegression","Sanity", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative1")
	public void PageConfigurator_getpagemapPage_negative500Status(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getAllVariantsAssociatedWithPageType API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * Verify getpagemapPage api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7" }, 
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getpagemapPage_negative_verifyStatusNodes(String pageType)
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval getAllVariantsAssociatedWithPageType API status nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify getpagemapPage api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "getPageTypeNegative")
	public void PageConfigurator_getpagemapPage_negative_vSuccStatusMsg(String pageType)	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10008");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Page Type not found\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), 0+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify getpagemap api status.
	 * @author jhansi.bai
	 */
	@Test(groups = { "MiniRegression","Regression", "ExhasutiveRegression", "Sanity", "Prodsanity", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getpagemap()
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAP);
		//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(
				"getpagemap API is not working",
				200, req.response.getStatus());
	}

	/**
	 * Verify getpagemap api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ProdSanity", "Regression",
			"ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" })
	public void PageConfigurator_getpagemap_verifyStatusNodes()
	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAP);
		// System.out.println(req.respvalidate.returnresponseasstring());
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true).equals("0"))){
			AssertJUnit.assertTrue("Inval getpagemap API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapPageVersionsDataNodes()));
			AssertJUnit.assertTrue("Inval getpagemap API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageMapVersionsDataNodes()));
		}	
	}

	/**
	 * Verify getpagemap api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity","MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7", "RFPPROD"})
	public void PageConfigurator_getpagemap_vSuccStatusMsg()	{
		RequestGenerator req = getRequest(APINAME.GETPAGEMAP);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Mapping retrieved successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), req.respvalidate.GetArraySize("data")+"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify createNewPageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7", "RFPPROD" }, dataProvider = "createNewPageVersionDataProvider")
	public void PageConfigurator_createNewPageVersion_verifyStatusNodes(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		AssertJUnit.assertTrue("Inval createNewPageVersion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify createNewPageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"Sanity","MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "createNewPageVersionDataProvider")
	public void PageConfigurator_createNewPageVersion_vSuccStatusMsg(String param1,
			String param2, String param3)	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"New Version Created successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify createNewPageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageVersionDataProvider_negative")
	public void PageConfigurator_createNewPageVersion_verifyNegativeStatusNodes(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		AssertJUnit.assertTrue("Inval createNewPageVersion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify createNewPageVersion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageVersionDataProvider_negative")
	public void PageConfigurator_createNewPageVersion_negative(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageVersion API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify createNewPageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "createNewPageVersionDataProvider_negative")
	public void PageConfigurator_createNewPageVersion_vNegativeSuccStatusMsg(String param1,
			String param2, String param3)	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "54");
		if(!(req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true).contains("${1}"))){
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Error creating new version: Duplicate entry '"+param2+"' for key 'version'\"");	
		}
		else{
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Error creating new version: Duplicate entry '${1}' for key 'version'\"");	
		}
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify updatePageVersion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "updatePageVersionDP")
	public void PageConfigurator_updatePageVersion(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updatePageVersion API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify updatePageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "updatePageVersionDP")
	public void PageConfigurator_updatePageVersion_verifyStatusNodes(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		AssertJUnit.assertTrue("Inval updatePageVersion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify updatePageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "updatePageVersionDP")
	public void PageConfigurator_updatePageVersion_vSuccStatusMsg(String param1,
			String param2, String param3)	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true), "\"Version Updated successfully\"");
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify updatePageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "updatePageVersionDP_negative")
	public void PageConfigurator_updatePageVersion_verifyNegativeStatusNodes(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		AssertJUnit.assertTrue("Inval updatePageVersion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify updatePageVersion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "updatePageVersionDP_negative")
	public void PageConfigurator_updatePageVersion_negative(String param1,
			String param2, String param3)
	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("updatePageVersion API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify updatePageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "updatePageVersionDP_negative")
	public void PageConfigurator_updatePageVersion_vNegativeSuccStatusMsg(String param1,
			String param2, String param3)	{
		RequestGenerator req = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "58");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Error occurred while updating/processing data\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify deletePageVersion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "deletePageVersionDP")
	public void PageConfigurator_deletePageVersion(String createdBy, String version, String label)
	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("deletePageVersion API is not working ",
				200, req.response.getStatus());
		List<Integer> existedPageVersions = getAllPageVersions();
		Assert.assertTrue("Deleted version is displayed.", !(existedPageVersions.contains(version)));
	}

	/**
	 * Verify deletePageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression","ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "deletePageVersionDP")
	public void PageConfigurator_deletePageVersion_verifyStatusNodes(String createdBy, String version, String label)
	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		if(!createdBy.isEmpty()){
			AssertJUnit.assertTrue("Inval deletePageVersion API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		}
	}

	/**
	 * Verify deletePageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "deletePageVersionDP")
	public void PageConfigurator_deletePageVersion_vSuccStatusMsg(String createdBy, String version, String label)	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		System.out.println(req.respvalidate.returnresponseasstring());
		if(!createdBy.isEmpty()){
			AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Mapping Deleted successfully\"");		 
			AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
			AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "1");
			log.info(req.respvalidate.returnresponseasstring());
		}
	}

	/**
	 * Verify deletePageVersion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression","ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "deletePageVersionDP_negative")
	public void PageConfigurator_deletePageVersion_verifyNegativeStatusNodes(String createdBy, String version, String label)
	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		if(!createdBy.isEmpty()){
			AssertJUnit.assertTrue("Inval deletePageVersion API data nodes.", 
					req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
		}
	}

	/**
	 * Verify deletePageVersion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPlaxmiTc","RFPFOX7" }, dataProvider = "deletePageVersionDP_negative")
	public void PageConfigurator_deletePageVersion_negative(String createdBy, String version, String label)
	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("deletePageVersion API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify deletePageVersion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "deletePageVersionDP_negative")
	public void PageConfigurator_deletePageVersion_vNegativeSuccStatusMsg(String createdBy, String version, String label)	{
		RequestGenerator req = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		System.out.println(req.respvalidate.returnresponseasstring());
		if(!createdBy.isEmpty()){
			AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10008");
			AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
					"\"Page Version not found\"");	
			AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
			AssertJUnit.assertEquals("Status typItem count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
			log.info(req.respvalidate.returnresponseasstring());
		}
	}

	/**
	 * Verify mapwidgettoversion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPlaxmiTc","RFPFOX7" }, dataProvider = "mapWidgetToVersionDP")
	public void PageConfigurator_mapwidgettoversion(String widgetId, String version)
	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		System.out.println(req.respvalidate.returnresponseasstring());
		if(!(version.equals(""))){
		AssertJUnit.assertEquals("mapwidgettoversion API is not working ",
				200, req.response.getStatus());
		}
	}

	/**
	 * Verify mapwidgettoversion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression",	"ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "mapWidgetToVersionDP")
	public void PageConfigurator_mapwidgettoversion_verifyStatusNodes(String widgetId, String version)
	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		AssertJUnit.assertTrue("Inval mapwidgettoversion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify mapwidgettoversion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "mapWidgetToVersionDP")
	public void PageConfigurator_mapwidgettoversion_vSuccStatusMsg(String widgetId, String version)	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15003");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Widget is mapped to the given version successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify mapwidgettoversion api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPlaxmiTc","RFPFOX7" }, dataProvider = "mapWidgetToVersionDP_negative")
	public void PageConfigurator_mapwidgettoversion_negative(String widgetId, String version)
	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("mapwidgettoversion API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify mapwidgettoversion api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression",	"ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "mapWidgetToVersionDP_negative")
	public void PageConfigurator_mapwidgettoversion_verifyNegativeStatusNodes(String widgetId, String version)
	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		AssertJUnit.assertTrue("Inval mapwidgettoversion API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify mapwidgettoversion api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "mapWidgetToVersionDP_negative")
	public void PageConfigurator_mapwidgettoversion_vNegativeSuccStatusMsg(String widgetId, String version)	{
		RequestGenerator req = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "10009");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Error mapping widget to given version: No entity found for query\"");	
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"ERROR\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify createNewPageType api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageTypeDP")
	public void PageConfigurator_createNewPageType(String pageType, String pageUrl)
	{
		RequestGenerator req = getRequest(APINAME.CREATENEWPAGETYPE, pageType, pageUrl);
		// System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageType API is not working ", 200,
				req.response.getStatus());
	}

	/**
	 * Verify createNewPageType api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression",	"ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "createNewPageTypeDP")
	public void PageConfigurator_createNewPageType_verifyStatusNodes(String pageType, String pageUrl)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGETYPE, pageType, pageUrl);
		AssertJUnit.assertTrue("Inval createNewPageType API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify createNewPageType api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "createNewPageTypeDP")
	public void PageConfigurator_createNewPageType_vSuccStatusMsg(String pageType, String pageUrl)	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGETYPE, pageType, pageUrl);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"New PageType Created successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "1");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify createNewPageType api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Sanity", "ExhaustiveRegression", "Regression",
			"MiniRegression", "RFPlaxmiTc","RFPFOX7" }, dataProvider = "createNewPageTypeDP_negative")
	public void PageConfigurator_createNewPageType_negative(String pageType, String pageUrl)
	{
		RequestGenerator req = getRequest( APINAME.CREATENEWPAGETYPE, pageType, pageUrl);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createNewPageType API is not working ",
				500, req.response.getStatus());
	}

	/**
	 * Verify createupdatelistofwidgets api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "Regression", "RFPFOX7" }, 
			dataProvider = "createUpdateWidgetsDP")
	public void PageConfigurator_createUpdateListOfWidgets(String widget1, String widgetData1, String widget2, 
			String widgetData2, String widget3,	String widgetData3)
	{
		RequestGenerator req = getRequest( APINAME.CREATEUPDATELISTOFWIDGETS, widget1, widgetData1, widget2, widgetData2, 
				widget3, widgetData3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createUpdateListOfWidgets API is not working ",
				200, req.response.getStatus());
	}

	/**
	 * Verify createNewPageType api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "createUpdateWidgetsDP")
	public void PageConfigurator_createUpdateListOfWidgets_verifyStatusNodes(String widget1, String widgetData1, String widget2, 
			String widgetData2, String widget3,	String widgetData3)
	{
		RequestGenerator req = getRequest( APINAME.CREATEUPDATELISTOFWIDGETS, widget1, widgetData1, widget2, widgetData2, 
				widget3, widgetData3);
		AssertJUnit.assertTrue("Inval createUpdateListOfWidgets API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}

	/**
	 * Verify createUpdateListOfWidgets api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "createUpdateWidgetsDP")
	public void PageConfigurator_createUpdateListOfWidgets_vSuccStatusMsg(String widget1, String widgetData1, String widget2, 
			String widgetData2, String widget3,	String widgetData3)
	{
		RequestGenerator req = getRequest( APINAME.CREATEUPDATELISTOFWIDGETS, widget1, widgetData1, widget2, widgetData2, 
				widget3, widgetData3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "15001");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"New Widget Created successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "3");
		log.info(req.respvalidate.returnresponseasstring());
	}

	/**
	 * Verify createupdatelistofwidgets api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "MiniRegression", "Regression", "RFPFOX7" }, 
			dataProvider = "createUpdateWidgetsDP_negative")
	public void PageConfigurator_createUpdateListOfWidgets_negative(String widget1, String widgetData1, String widget2, 
			String widgetData2, String widget3,	String widgetData3)
	{
		RequestGenerator req = getRequest( APINAME.CREATEUPDATELISTOFWIDGETS, widget1, widgetData1, widget2, widgetData2, 
				widget3, widgetData3);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createUpdateListOfWidgets API is not working ",
				200, req.response.getStatus());
	}
	
	/**
	 * Verify invalidcache api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "MiniRegression", "Regression", "RFPFOX7" }, 
			dataProvider = "cachesDP")
	public void PageConfigurator_invalidcache(String cache)
	{
		RequestGenerator req = getRequest( APINAME.INVALIDCACHE, cache);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createUpdateListOfWidgets API is not working ",
				200, req.response.getStatus());
	}
	
	/**
	 * Verify invalidcache api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = {"Regression", "ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "cachesDP")
	public void PageConfigurator_invalidcache_verifyStatusNodes(String cache)
	{
		RequestGenerator req = getRequest( APINAME.INVALIDCACHE, cache);
		AssertJUnit.assertTrue("Inval invalidcache API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	/**
	 * Verify invalidcache api status node values
	 * @author jhansi.bai
	 */
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
			dataProvider = "cachesDP")
	public void PageConfigurator_invalidcache_vSuccStatusMsg(String cache)	{
		RequestGenerator req = getRequest(APINAME.INVALIDCACHE, cache);
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(0), true), "3");
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(1), true),
				"\"Cache with name: "+cache+" is cleared.\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(PageConfigServiceApiHelper.getAllPageStatusNodes().get(3), true), "0");
		log.info(req.respvalidate.returnresponseasstring());
	}
	
	/**
	 * Verify invalidcache api status 
	 * @author jhansi.bai
	 */
	@Test(groups = { "ExhaustiveRegression", "Sanity", "MiniRegression", "Regression", "RFPFOX7" }, 
			dataProvider = "cachesDP_negative")
	public void PageConfigurator_invalidcache_negative(String cache)
	{
		RequestGenerator req = getRequest( APINAME.INVALIDCACHE, cache);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("createUpdateListOfWidgets API is not working ",
				200, req.response.getStatus());
	}
	
	/**
	 * Verify invalidcache api status nodes 
	 * @author jhansi.bai
	 */
	@Test(groups = { "Regression",	"ExhaustiveRegression", "MiniRegression", "RFPFOX7" }, dataProvider = "cachesDP_negative")
	public void PageConfigurator_invalidcache_verifyNegativeStatusNodes(String cache)
	{
		RequestGenerator req = getRequest( APINAME.INVALIDCACHE, cache);
		AssertJUnit.assertTrue("Inval invalidcache API data nodes.", 
				req.respvalidate.DoesNodesExists(PageConfigServiceApiHelper.getAllPageStatusNodes()));
	}
	
	//This test case has moved to 'BestPractise' class
	
	/**
	 * Verify invalidcache api status node values
	 * @author jhansi.bai
	 *//*
	@Test(groups = {"MiniRegression","Regression", "ExhasutiveRegression", "RFPFOX7"},
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
	}*/
	
	@Test(groups = { "SchemaValidation" })
	public void PageConfigurator_getAllPageType_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllPageTypeReqGen = getRequest(APINAME.GETALLPAGEVERSIONS);
		String getAllPageTypeResponse = getAllPageTypeReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllPageType API response :\n\n"+getAllPageTypeResponse);
		log.info("\nPrinting PageConfigurator getAllPageType API response :\n\n"+getAllPageTypeResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllPageType API is not working", 200, getAllPageTypeReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallpagetype-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllPageTypeResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllPageType API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" })
	public void PageConfigurator_getAllPageVariants_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllPageVariantsReqGen = getRequest(APINAME.GETALLPAGEVARIANTS);
		String getAllPageVariantsResponse = getAllPageVariantsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllPageVariants API response :\n\n"+getAllPageVariantsResponse);
		log.info("\nPrinting PageConfigurator getAllPageVariants API response :\n\n"+getAllPageVariantsResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllPageVariants API is not working", 200, getAllPageVariantsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallpagevariants-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllPageVariantsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllPageVariants API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Test(groups = { "SchemaValidation" })
	public void PageConfigurator_getAllPageVersions_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllPageVersionsReqGen = getRequest(APINAME.GETALLPAGEVERSIONS);
		String getAllPageVersionsResponse = getAllPageVersionsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllPageVersions API response :\n\n"+getAllPageVersionsResponse);
		log.info("\nPrinting PageConfigurator getAllPageVersions API response :\n\n"+getAllPageVersionsResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllPageVersions API is not working", 200, getAllPageVersionsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallpageversions-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllPageVersionsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllPageVersions API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getAllInfoGivenPageTypeAndPageUrl_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getAllInfoGivenPageTypeAndPageUrl_verifyResponseDataNodesUsingSchemaValidations(String pageType)
	{
		RequestGenerator getAllInfoGivenPageTypeAndPageUrlReqGen = getRequest(APINAME.GETALLINFOGIVENPAGETYPEANDPAGEURL, pageType);
		String getAllInfoGivenPageTypeAndPageUrlResponse = getAllInfoGivenPageTypeAndPageUrlReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllInfoGivenPageTypeAndPageUrl API response :\n\n"+getAllInfoGivenPageTypeAndPageUrlResponse);
		log.info("\nPrinting PageConfigurator getAllInfoGivenPageTypeAndPageUrl API response :\n\n"+getAllInfoGivenPageTypeAndPageUrlResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllInfoGivenPageTypeAndPageUrl API is not working", 200, 
				getAllInfoGivenPageTypeAndPageUrlReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallinfogivenpagetypeandpageurl-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllInfoGivenPageTypeAndPageUrlResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllInfoGivenPageTypeAndPageUrl API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getAllPageVersionsAssociatedWithWidgetId_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getAllPageVersionsAssociatedWithWidgetId_verifyResponseDataNodesUsingSchemaValidations(String widgetId)
	{
		RequestGenerator getAllPageVersionsAssociatedWithWidgetIdReqGen = getRequest(APINAME.GETALLPAGEVERSIONSASSOCIATEDWITHWIDGETID, widgetId);
		String getAllPageVersionsAssociatedWithWidgetIdResponse = getAllPageVersionsAssociatedWithWidgetIdReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllPageVersionsAssociatedWithWidgetId API response :\n\n"+getAllPageVersionsAssociatedWithWidgetIdResponse);
		log.info("\nPrinting PageConfigurator getAllPageVersionsAssociatedWithWidgetId API response :\n\n"+getAllPageVersionsAssociatedWithWidgetIdResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllPageVersionsAssociatedWithWidgetId API is not working", 200, 
				getAllPageVersionsAssociatedWithWidgetIdReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallpageversionsassociatedwithwidgetid-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllPageVersionsAssociatedWithWidgetIdResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllPageVersionsAssociatedWithWidgetId API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getAllWidgetsAssociatedWithPageVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getAllWidgetsAssociatedWithPageVersion_verifyResponseDataNodesUsingSchemaValidations(String pageVer)
	{
		RequestGenerator getAllWidgetsAssociatedWithPageVersionReqGen = getRequest(APINAME.GETALLWIDGETSASSOCIATEDWITHPAGEVERSION, pageVer);
		String getAllWidgetsAssociatedWithPageVersionResponse = getAllWidgetsAssociatedWithPageVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllWidgetsAssociatedWithPageVersion API response :\n\n"+getAllWidgetsAssociatedWithPageVersionResponse);
		log.info("\nPrinting PageConfigurator getAllWidgetsAssociatedWithPageVersion API response :\n\n"+getAllWidgetsAssociatedWithPageVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllWidgetsAssociatedWithPageVersion API is not working", 200, 
				getAllWidgetsAssociatedWithPageVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallwidgetsassociatedwithpageversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllWidgetsAssociatedWithPageVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllWidgetsAssociatedWithPageVersion API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" })
	public void PageConfigurator_getAllWidgets_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getAllWidgetsReqGen = getRequest(APINAME.GETALLWIDGETS);
		String getAllWidgetsResponse = getAllWidgetsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllWidgets API response :\n\n"+getAllWidgetsResponse);
		log.info("\nPrinting PageConfigurator getAllWidgets API response :\n\n"+getAllWidgetsResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllWidgets API is not working", 200, getAllWidgetsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallwidgets-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllWidgetsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllWidgets API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getCurrentVersionForPageTypeAndVariant_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getCurrentVersionForPageTypeAndVariant_verifyResponseDataNodesUsingSchemaValidations(String userType, String abtestName, 
			String abtestVariant, String pageType)	
	{
		RequestGenerator getCurrentVersionForPageTypeAndVariantReqGen = getQuerryPayLoadAdHeaderRequest(APINAME.GETCURRENTVERSIONFORPAGETYPEANDVARIANT, userType, 
				abtestName, abtestVariant, pageType);
		String getCurrentVersionForPageTypeAndVariantResponse = getCurrentVersionForPageTypeAndVariantReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getCurrentVersionForPageTypeAndVariant API response :\n\n"+getCurrentVersionForPageTypeAndVariantResponse);
		log.info("\nPrinting PageConfigurator getCurrentVersionForPageTypeAndVariant API response :\n\n"+getCurrentVersionForPageTypeAndVariantResponse);

		AssertJUnit.assertEquals("PageConfigurator getCurrentVersionForPageTypeAndVariant API is not working", 200, 
				getCurrentVersionForPageTypeAndVariantReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getcurrentversionforpagetypeandvariant-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getCurrentVersionForPageTypeAndVariantResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getCurrentVersionForPageTypeAndVariant API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getAllVariantsAssociatedWithPageType_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getAllVariantsAssociatedWithPageType_verifyResponseDataNodesUsingSchemaValidations(String pageType)
	{
		RequestGenerator getAllVariantsAssociatedWithPageTypeReqGen = getRequest(APINAME.GETALLVARIANTSASSOCIATEDWITHPAGETYPE, pageType);
		String getAllVariantsAssociatedWithPageTypeResponse = getAllVariantsAssociatedWithPageTypeReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getAllVariantsAssociatedWithPageType API response :\n\n"+getAllVariantsAssociatedWithPageTypeResponse);
		log.info("\nPrinting PageConfigurator getAllVariantsAssociatedWithPageType API response :\n\n"+getAllVariantsAssociatedWithPageTypeResponse);

		AssertJUnit.assertEquals("PageConfigurator getAllVariantsAssociatedWithPageType API is not working", 200, 
				getAllVariantsAssociatedWithPageTypeReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getallvariantsassociatedwithpagetype-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getAllVariantsAssociatedWithPageTypeResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getAllVariantsAssociatedWithPageType API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getPageVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getPageVersion_verifyResponseDataNodesUsingSchemaValidations(String pageVer)
	{
		RequestGenerator getPageVersionReqGen = getRequest(APINAME.GETPAGEVERSION, pageVer);
		String getPageVersionResponse = getPageVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getPageVersion API response :\n\n"+getPageVersionResponse);
		log.info("\nPrinting PageConfigurator getPageVersion API response :\n\n"+getPageVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator getPageVersion API is not working", 200, getPageVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getpageversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPageVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getPageVersion API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getPageMappingVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getPageMappingVersion_verifyResponseDataNodesUsingSchemaValidations(String pageVer)
	{
		RequestGenerator getPageMappingVersionReqGen = getRequest(APINAME.GETPAGEMAPPINGVERSION, pageVer);
		String getPageMappingVersionResponse = getPageMappingVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getPageMappingVersion API response :\n\n"+getPageMappingVersionResponse);
		log.info("\nPrinting PageConfigurator getPageMappingVersion API response :\n\n"+getPageMappingVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator getPageMappingVersion API is not working", 200, getPageMappingVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getpagemappingversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPageMappingVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getPageMappingVersion API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_getPageMapPage_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_getPageMapPage_verifyResponseDataNodesUsingSchemaValidations(String pageType)
	{
		RequestGenerator getPageMapPageReqGen = getRequest(APINAME.GETPAGEMAPPAGE, pageType);
		String getPageMapPageResponse = getPageMapPageReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getPageMapPage API response :\n\n"+getPageMapPageResponse);
		log.info("\nPrinting PageConfigurator getPageMapPage API response :\n\n"+getPageMapPageResponse);

		AssertJUnit.assertEquals("PageConfigurator getPageMapPage API is not working", 200, getPageMapPageReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getpagemappage-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPageMapPageResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getPageMapPage API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" })
	public void PageConfigurator_getPageMap_verifyResponseDataNodesUsingSchemaValidations()
	{
		RequestGenerator getPageMapReqGen = getRequest(APINAME.GETPAGEMAP);
		String getPageMapResponse = getPageMapReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator getPageMap API response :\n\n"+getPageMapResponse);
		log.info("\nPrinting PageConfigurator getPageMap API response :\n\n"+getPageMapResponse);

		AssertJUnit.assertEquals("PageConfigurator getPageMap API is not working", 200, getPageMapReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-getpagemap-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(getPageMapResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator getPageMap API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_createNewPageVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_createNewPageVersion_verifyResponseDataNodesUsingSchemaValidations(String param1,String param2, String param3)
	{
		RequestGenerator createNewPageVersionReqGen = getRequest( APINAME.CREATENEWPAGEVERSION, param1, param2, param3);
		String createNewPageVersionResponse = createNewPageVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator createNewPageVersion API response :\n\n"+createNewPageVersionResponse);
		log.info("\nPrinting PageConfigurator createNewPageVersion API response :\n\n"+createNewPageVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator createNewPageVersion API is not working", 200, createNewPageVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-createnewpageversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNewPageVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator createNewPageVersion API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_updatePageVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_updatePageVersion_verifyResponseDataNodesUsingSchemaValidations(String param1, String param2, String param3)
	{
		RequestGenerator updatePageVersionReqGen = getRequest( APINAME.UPDATEPAGEVERSION, param1, param2, param3);
		String updatePageVersionResponse = updatePageVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator updatePageVersion API response :\n\n"+updatePageVersionResponse);
		log.info("\nPrinting PageConfigurator updatePageVersion API response :\n\n"+updatePageVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator updatePageVersion API is not working", 200, updatePageVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-updatepageversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(updatePageVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator updatePageVersion API response", CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_deletePageVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_deletePageVersion_verifyResponseDataNodesUsingSchemaValidations(String createdBy, String version, String label)
	{
		RequestGenerator deletePageVersionReqGen = getQuerryPayLoadRequest( APINAME.DELETEPAGEVERSION, createdBy, version, label);
		String deletePageVersionResponse = deletePageVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator deletePageVersion API response :\n\n"+deletePageVersionResponse);
		log.info("\nPrinting PageConfigurator deletePageVersion API response :\n\n"+deletePageVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator deletePageVersion API is not working", 200, deletePageVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-deletepageversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(deletePageVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator deletePageVersion API response", CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_createNewPageType_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_createNewPageType_verifyResponseDataNodesUsingSchemaValidations(String pageType, String pageUrl)
	{
		RequestGenerator createNewPageTypeReqGen = getRequest(APINAME.CREATENEWPAGETYPE, pageType, pageUrl);
		String createNewPageTypeResponse = createNewPageTypeReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator createNewPageType API response :\n\n"+createNewPageTypeResponse);
		log.info("\nPrinting PageConfigurator createNewPageType API response :\n\n"+createNewPageTypeResponse);

		AssertJUnit.assertEquals("PageConfigurator createNewPageType API is not working", 200, createNewPageTypeReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-createnewpagetype-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNewPageTypeResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator createNewPageType API response", CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_mapWidgetToVersion_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_mapWidgetToVersion_verifyResponseDataNodesUsingSchemaValidations(String widgetId, String version)
	{
		RequestGenerator mapWidgetToVersionReqGen = getQuerryPayLoadRequest1( APINAME.MAPWIDGETTOVERSION, widgetId, version);
		String mapWidgetToVersionResponse = mapWidgetToVersionReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator mapWidgetToVersion API response :\n\n"+mapWidgetToVersionResponse);
		log.info("\nPrinting PageConfigurator mapWidgetToVersion API response :\n\n"+mapWidgetToVersionResponse);

		AssertJUnit.assertEquals("PageConfigurator mapWidgetToVersion API is not working", 200, mapWidgetToVersionReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-mapwidgettoversion-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(mapWidgetToVersionResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator mapWidgetToVersion API response", CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_inValidateCache_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_inValidateCache_verifyResponseDataNodesUsingSchemaValidations(String cache)
	{
		RequestGenerator inValidateCacheReqGen = getRequest( APINAME.INVALIDCACHE, cache);
		String inValidateCacheResponse = inValidateCacheReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator inValidateCache API response :\n\n"+inValidateCacheResponse);
		log.info("\nPrinting PageConfigurator inValidateCache API response :\n\n"+inValidateCacheResponse);

		AssertJUnit.assertEquals("PageConfigurator inValidateCache API is not working", 200, inValidateCacheReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-invalidatecache-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(inValidateCacheResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator inValidateCache API response", CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Test(groups = { "SchemaValidation" }, dataProvider = "PageConfiguratorDP_createUpdateWidgets_verifyResponseDataNodesUsingSchemaValidations")
	public void PageConfigurator_createUpdateListOfWidgets_verifyResponseDataNodesUsingSchemaValidations(String widget1, String widgetData1, String widget2, 
			String widgetData2, String widget3,	String widgetData3)
	{
		RequestGenerator createUpdateListOfWidgetsReqGen = getRequest( APINAME.CREATEUPDATELISTOFWIDGETS, widget1, widgetData1, widget2, widgetData2, 
				widget3, widgetData3);
		String createUpdateListOfWidgetsResponse = createUpdateListOfWidgetsReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting PageConfigurator createUpdateListOfWidgets API response :\n\n"+createUpdateListOfWidgetsResponse);
		log.info("\nPrinting PageConfigurator createUpdateListOfWidgets API response :\n\n"+createUpdateListOfWidgetsResponse);

		AssertJUnit.assertEquals("PageConfigurator createUpdateListOfWidgets API is not working", 200, createUpdateListOfWidgetsReqGen.response.getStatus());
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-pageconfig-createupdatelistofwidgets-schema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createUpdateListOfWidgetsResponse, jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in PageConfigurator createUpdateListOfWidgets API response", 
	        		CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
