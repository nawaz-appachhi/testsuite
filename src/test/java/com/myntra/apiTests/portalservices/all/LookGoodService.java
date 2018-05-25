package com.myntra.apiTests.portalservices.all;

import java.util.ArrayList;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.LookGoodDP;
import com.myntra.apiTests.portalservices.lookgoodservice.LookGoodHelper;
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
public class LookGoodService extends LookGoodDP
{

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LookGoodService.class);
	LookGoodHelper lookGoodHelper = new LookGoodHelper();
	
	@Test(groups = { "Smoke", "Sanity" ,"ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression"} , dataProvider = "lookGoodIdsDataNodeProvider")
	public void LookGoodService_getLookBookById(String lookId) 
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOOKGOOD, APINAME.GETLOOKBOOKBYID,init.Configurations, null, new String[]{ lookId });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.returnresponseasstring());
		System.out.println(req.response);
		log.info(service.URL);
		AssertJUnit.assertEquals("Look Good service is not working", 200,req.response.getStatus());
		AssertJUnit.assertTrue("Invalid meta nodes", req.respvalidate.DoesNodesExists(lookGoodNodes()));
		AssertJUnit.assertTrue("Value of id should be same as LookGoodId", 
				req.respvalidate.GetNodeValue("id", req.respvalidate.returnresponseasstring()).contains(lookId));
	}
	
	@Test(groups = { "Smoke", "Sanity" , "Regression", "MiniRegression", "ExhaustiveRegression"} , dataProvider = "lookGoodIdsDataNodeProvider")
	public void LookGoodService_getLookBookById_verifyDataNodes(String lookId) 
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOOKGOOD, APINAME.GETLOOKBOOKBYID,init.Configurations, null, new String[]{ lookId });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.returnresponseasstring());
		System.out.println(req.response);
		log.info(service.URL);
		AssertJUnit.assertTrue("Invalid meta nodes", req.respvalidate.DoesNodesExists(lookGoodNodes()));
		
	}
	
	//verify look good ids
	@Test(groups = { "Smoke", "Sanity" , "Regression", "MiniRegression", "ExhaustiveRegression"} , dataProvider = "lookGoodIdsDataNodeNegetiveIdsProvider")
	public void LookGoodService_getLookBookByIdNegetiveValues(String lookId) 
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_LOOKGOOD, APINAME.GETLOOKBOOKBYID,init.Configurations, null, new String[]{ lookId });
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.returnresponseasstring());
		System.out.println(req.response);
		log.info(service.URL);
		
		AssertJUnit.assertEquals("Look Good service is not working", 404,req.response.getStatus());
	}
	
	private ArrayList<String> lookGoodNodes() {
		ArrayList<String> response = new ArrayList<String>();
		response.add("id");
		response.add("looks");
		response.add("name");
		response.add("summary");
		response.add("description");
		response.add("is_active");
		return response;
	}

	@Test(groups = { "SchemaValidation" } , dataProvider = "LookGoodDP_getLookBookById_verifyResponseDataNodesUsingSchemaValidations")
	public void LookGoodService_getLookBookById_verifyResponseDataNodesUsingSchemaValidations(String lookId) 
	{
		RequestGenerator getLookBookByIdReqGen = lookGoodHelper.invokeGetLookBookById(lookId);
		String createNotificationForUserResponse = getLookBookByIdReqGen.ResponseValidations.GetResponseAsString();
		System.out.println("\nPrinting LookGoodService getLookBookById API response :\n\n"+createNotificationForUserResponse);
		log.info("\nPrinting LookGoodService getLookBookById API response :\n\n"+createNotificationForUserResponse);
		
		AssertJUnit.assertEquals("LookGoodService getLookBookById API is not working", 200, getLookBookByIdReqGen.response.getStatus());
		
		try {
            String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lookgoodservice-getlookbookbyid-schema.txt");
            List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(createNotificationForUserResponse, jsonschema);
            AssertJUnit.assertTrue(missingNodeList+" nodes are missing in LookGoodService getLookBookById API response", 
            		CollectionUtils.isEmpty(missingNodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
