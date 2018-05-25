package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.lang.reflect.Method;

import com.myntra.apiTests.dataproviders.PumpsDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class PumpsAppTests extends PumpsDP {
 
	static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(ABTests.class);
	
	private String globalCaseValue;
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("=====================================================================================================");
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "getActionByIdDP", priority = 1)
	public void getActionByIdTests(String actionId, String caseValue) {
		
		globalCaseValue = caseValue;
		RequestGenerator request = getQueryRequestSingleParam(APINAME.GETACTIONBYID, actionId);
		String response = request.respvalidate.returnresponseasstring();
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode")), 1033,"Status Code MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), "App(s) retrieved successfully","Status Message MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "SUCCESS","Status Type MisMatch");
			
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode")), 64,"Status Code MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), "Invalid id","Status Message MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "ERROR","Status Type MisMatch");
		}
		else if(caseValue.equalsIgnoreCase("case3")){
			
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode")), 2011,"Status Code MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), "App not found","Status Message MisMatch");
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "SUCCESS","Status Type MisMatch");
		}
		
	}
	
}
