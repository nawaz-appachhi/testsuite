package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.NavDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author shankara.c
 *
 */
public class TopNavService extends NavDP
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(TopNavService.class);

	@Test(groups = {  "Smoke", "Sanity", "Prodsanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "ProdSanity", "RFPFOX7", "RFPPROD" } )
	public void GetTopNav()	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TOPNAVCONFIGURATION, APINAME.GETTOPNAV,init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Get Top Nav API is not working", 200,req.response.getStatus());
	}
	
	@Test(groups = {  "Sanity", "Prodsanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "ProdSanity", "RFPFOX7", "RFPPROD" } )
	public void GetTopNav_vTopAdStatusNodes()	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TOPNAVCONFIGURATION, APINAME.GETTOPNAV,init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertTrue("Inval Get Top Nav API status nodes.", 
				req.respvalidate.DoesNodesExists(topNavNodes()));
		AssertJUnit.assertTrue("Inval  Get Top Nav  API status nodes.", 
				req.respvalidate.DoesNodesExists(topNavStatusNodes()));
	}
	
	@Test(groups = {  "Sanity", "Prodsanity", "Regression", "MiniRegression",
			"ExhaustiveRegression", "RFPFOX7", "RFPPROD" } )
	public void GetTopNav_vStatusNodesVals()	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_TOPNAVCONFIGURATION, APINAME.GETTOPNAV,init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does't match", req.respvalidate.NodeValue(topNavStatusNodes().get(0), true), "15001");			
		AssertJUnit.assertEquals("Status message dosn't match",	req.respvalidate.NodeValue(topNavStatusNodes().get(1), true), 
				"\"TopNav fetch successfully\"");		 
		AssertJUnit.assertEquals("Status type dosn't match", req.respvalidate.NodeValue(topNavStatusNodes().get(2), true), "\"SUCCESS\"");
		AssertJUnit.assertEquals("Total count dosn't match", req.respvalidate.NodeValue(topNavStatusNodes().get(3), true), "0");
	}

//-------------------------------------------------------------------------------
	
	public static String statusBasePath = "status.";

	/**
	 * key value pair status nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> topNavStatusNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add(statusBasePath+"statusCode");
		addNodes.add(statusBasePath+"statusMessage");
		addNodes.add(statusBasePath+"statusType");
		addNodes.add(statusBasePath+"totalCount");
		return addNodes;		
	}
	
	/**
	 * key value pair top nodes
	 * @author jhansi.bai
	 * @return
	 */
	public static List<String> topNavNodes (){
		List<String> addNodes = new ArrayList<>();
		addNodes.add("status");
		addNodes.add("categories");
		return addNodes;		
	}
	
	@Test(groups = { "SchemaValidation" })
	public void TopNavService_getTopNav_verifyResponseDataNodesUsingSchemaValidations()	
	{
		MyntraService topNavConfigurationService = Myntra.getService(ServiceType.PORTAL_TOPNAVCONFIGURATION, APINAME.GETTOPNAV, init.Configurations);
		RequestGenerator topNavConfigurationReqGen = new RequestGenerator(topNavConfigurationService);
		String topNavConfigurationResponse = topNavConfigurationReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting TopNavService getTopNav API response :\n\n"+topNavConfigurationResponse);
		log.info("\nPrinting TopNavService getTopNav API response :\n\n"+topNavConfigurationResponse);
		
		AssertJUnit.assertEquals("TopNavService getTopNav API is not working", 200, topNavConfigurationReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/configservice-topnav-gettopnav-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(topNavConfigurationResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in TopNavService getTopNav API response", CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
