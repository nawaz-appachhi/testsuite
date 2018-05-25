package com.myntra.apiTests.portalservices.pricingpromotionservices;

import com.myntra.apiTests.common.Myntra;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.PricingEngineDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class PricingEngine extends PricingEngineDP {
	Initialize init = new Initialize("/Data/configuration");

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void startPricingEngine() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.STARTPRICINGENGINE,
				init.Configurations);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		String jsonRes = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes,"$.instanceid").toString();
		System.out.println("Messagee -- >" + msg1);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				reqGenerator.response.getStatus());
		
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void getPricingStatus() {
		String instanceId = startEngine();
		System.out.println(">>>>>>>>>>>>>>>>" + instanceId);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.GETPRICINGENGINESTATUS, init.Configurations,
				PayloadType.JSON, new String[] { instanceId },
				PayloadType.JSON);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		String jsonRes = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.instanceUUID").toString();
		System.out.println("Messagee -- >" + msg1);
		AssertJUnit.assertEquals("Instance UUid fails to compare", msg1, instanceId);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				reqGenerator.response.getStatus());

	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getNegativeInstanceId")
	public void getPricingStatusNegative(String instanceId) {
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.GETPRICINGENGINESTATUS, init.Configurations,
				PayloadType.JSON, new String[] { instanceId }, PayloadType.JSON);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		String jsonRes = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.message").toString();
		System.out.println("Messagee -- >" + msg1);
		AssertJUnit.assertEquals("Invalid instanceid, instance details not found", msg1,
				"Invalid instanceid, instance details not found");
		AssertJUnit.assertEquals("Status not equal to 200", 500,
				reqGenerator.response.getStatus());

	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getNegativeInstanceId1")
	public void getPricingStatusNegative1(String instanceId) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.GETPRICINGENGINESTATUS, init.Configurations,
				PayloadType.JSON, new String[] { instanceId }, PayloadType.JSON);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		AssertJUnit.assertEquals("Status not equal to 400", 404,
				reqGenerator.response.getStatus());

	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" })
	public void getPricingEngineOutput() {
		String instanceId = startEngine();
		System.out.println(">>>>>>>>>>>>>>>>" + instanceId);
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.GETPRICINGENGINEOUTPUT, init.Configurations,
				PayloadType.JSON, new String[] { instanceId }, PayloadType.JSON);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		String jsonRes = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
//		String msg1 = JsonPath.read(jsonRes, "$.instanceUUID").toString();
//		System.out.println("Messagee -- >" + msg1);
//		AssertJUnit.assertEquals("Instance UUid fails to compare", msg1,
//				instanceId);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				reqGenerator.response.getStatus());

	}

	
	private String startEngine(){ 
	
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.STARTPRICINGENGINE,
				init.Configurations);
		RequestGenerator reqGenerator = new RequestGenerator(service);
		
		String jsonRes = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.instanceid").toString();
		System.out.println("Messagee -- >" + msg1);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				reqGenerator.response.getStatus());
		return  msg1;

	}
}
