package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.LgpServicesDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class ABTests extends LgpServicesDP{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ABTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHepler=new LgpServicesHelper();
	String xID;
	String env;
	
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "abTestDataprovider")
	public void LgpServiceAbTest(String username,String password) throws Exception{
		HashMap<String, String> headers=new HashMap<String, String>();
		env=init.Configurations.GetTestEnvironemnt().toString();
		if(env.toLowerCase().equals("functional")){
			xID=lgpServiceHepler.getxid(username, password);
			headers.put("xid", xID);
		}
		else{
			headers=lgpServiceHepler.getXIDandSXidHeader(username, password);
		}
		
		//headers.put("xid", "JJN15612b404bd46094fdda4e91f2ceb2f8aa81432707990M");
		MyntraService lgpService= Myntra.getService(ServiceType.LGP_ABTESTSERVICE, APINAME.ABTEST, init.Configurations);
		System.out.println("Abtest url: "+lgpService.URL);
		RequestGenerator lgpServiceReq=new RequestGenerator(lgpService,headers);
		System.out.println(lgpServiceReq.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Lgp service ",200, lgpServiceReq.response.getStatus());
		String rollout=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.data.*").toString();
		rollout=rollout.substring((rollout.indexOf("[") + 1), rollout.lastIndexOf("]")).replaceAll("\"", "");
		//System.out.println(rollout);
		String state=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.state").toString();
		
		if(lgpServiceHepler.getNumberFromString(state)<400){
			AssertJUnit.assertEquals("Lgp service ab test notworking", "control", rollout.toLowerCase());
		}else{
			AssertJUnit.assertEquals("Lgp service ab test notworking", "test", rollout.toLowerCase());
		}
		
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "abTestDataprovider")
	public void LgpServiceAbTestWithStateAsHeader(String username,String password) throws Exception{
		HashMap<String, String> headers=new HashMap<String, String>();
		env=init.Configurations.GetTestEnvironemnt().toString();
		if(env.toLowerCase().equals("functional")){
			xID=lgpServiceHepler.getxid(username, password);
			headers.put("xid", xID);
		}
		else{
			headers=lgpServiceHepler.getXIDandSXidHeader(username, password);
		}
		
		//headers.put("xid", "JJN15612b404bd46094fdda4e91f2ceb2f8aa81432707990M");
		MyntraService lgpService=Myntra.getService(ServiceType.LGP_ABTESTSERVICE, APINAME.ABTEST, init.Configurations);
		System.out.println("Abtest url: "+lgpService.URL);
		RequestGenerator lgpServiceReq=new RequestGenerator(lgpService,headers);
		//System.out.println(lgpServiceReq.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Lgp service ",200, lgpServiceReq.response.getStatus());
		System.out.println(lgpServiceReq.respvalidate.returnresponseasstring());
		String rollout=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.data.*").toString();
		rollout=rollout.substring((rollout.indexOf("[") + 1), rollout.lastIndexOf("]")).replaceAll("\"", "");
		System.out.println(rollout);
		String state=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.state").toString();
		if(lgpServiceHepler.getNumberFromString(state)<400){
			AssertJUnit.assertEquals("Lgp service ab test notworking", "control", rollout.toLowerCase());
		}else{
			AssertJUnit.assertEquals("Lgp service ab test notworking", "test", rollout.toLowerCase());
		}
		
		headers.put("AB-Test", state);
		System.out.println("With header:"+state);
		RequestGenerator lgpServiceReqWithHeaders=new RequestGenerator(lgpService,headers);
		
		AssertJUnit.assertEquals("Lgpservice with header as "+state+"Not working", 200, lgpServiceReqWithHeaders.response.getStatus());

		System.out.println(lgpServiceReqWithHeaders.respvalidate.returnresponseasstring());
		String jsonResponse=lgpServiceReqWithHeaders.respvalidate.returnresponseasstring();
		String rolloutfromJson=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.data.*").toString();
		rolloutfromJson=rolloutfromJson.substring((rolloutfromJson.indexOf("[") + 1), rolloutfromJson.lastIndexOf("]")).replaceAll("\"", "");
		AssertJUnit.assertEquals("Rollout is not same as expected", rollout, rolloutfromJson);
		AssertJUnit.assertEquals("State is not same as expected", state, JsonPath.read(jsonResponse, "$.state"));
		
	}
	
	@Test(groups = { "Smoke", "ProdSanity", "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" }, 
			dataProvider = "abTestDataprovider")
	public void LgpServiceAbTestWithSchemaValidation(String username,String password) throws Exception{
		HashMap<String, String> headers=new HashMap<String, String>();
		env=init.Configurations.GetTestEnvironemnt().toString();
		if(env.toLowerCase().equals("functional")){
			xID=lgpServiceHepler.getxid(username, password);
			headers.put("xid", xID);
		}
		else{
			
			headers=lgpServiceHepler.getXIDandSXidHeader(username, password);
			
		}
		
		//headers.put("xid", "JJN15612b404bd46094fdda4e91f2ceb2f8aa81432707990M");
		MyntraService lgpService=Myntra.getService(ServiceType.LGP_ABTESTSERVICE, APINAME.ABTEST, init.Configurations);
		System.out.println("Abtest url: "+lgpService.URL);
		RequestGenerator lgpServiceReq=new RequestGenerator(lgpService,headers);
		System.out.println(lgpServiceReq.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Lgp service ",200, lgpServiceReq.response.getStatus());
		String rollout=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.data.*").toString();
		rollout=rollout.substring((rollout.indexOf("[") + 1), rollout.lastIndexOf("]")).replaceAll("\"", "");
		
		String state=JsonPath.read(lgpServiceReq.respvalidate.returnresponseasstring(), "$.state").toString();
		if(lgpServiceHepler.getNumberFromString(state)<400){
			AssertJUnit.assertEquals("Lgp service ab test notworking", "control", rollout.toLowerCase());
		}else{
			AssertJUnit.assertEquals("Lgp service ab test notworking", "test", rollout.toLowerCase());
		}
		
		try {
	        String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgpserviceabtestschema.txt");
	        List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(lgpServiceReq.respvalidate.returnresponseasstring(), jsonschema);
	        AssertJUnit.assertTrue(missingNodeList+" nodes are missing in lgp service  response",CollectionUtils.isEmpty(missingNodeList));
	    } catch (Exception e) {
	        e.printStackTrace();
	}
	}
}
