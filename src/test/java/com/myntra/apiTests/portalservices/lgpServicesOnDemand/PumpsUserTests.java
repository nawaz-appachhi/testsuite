package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.PumpsDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class PumpsUserTests extends PumpsDP {
	
	static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(ABTests.class);
	static APIUtilities utilities = new APIUtilities();
	private String globalCaseValue;
	
	static HashMap<String, String> headers = new HashMap<String, String>();
	
	@BeforeClass(alwaysRun=true)
	public void init(){
		
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
	}
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("=====================================================================================================");
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "userPrivacyDP", priority = 1)
	public void updateUserPrivacyTests(String privacyStatus, String uidx ,String caseValue) throws IOException {
		
		globalCaseValue = caseValue;
		String payload = utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateUserPrivacy");
		payload = utilities.preparepayload(payload, new String[]{privacyStatus});
		MyntraService pumpsService = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEUSERPRIVACY ,init.Configurations,payload);
		pumpsService.URL = utilities.prepareparameterizedURL(pumpsService.URL, uidx);
		
		RequestGenerator requestGen = new RequestGenerator(pumpsService, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		
		if(caseValue.equalsIgnoreCase("case1") || caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "SUCCESS","Status Type Mismatch");
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "ERROR","Status Type Mismatch");
		}
	}
	
	@Test(groups = {"Sanity"}, dataProvider = "followContactsDP", priority = 2)
	public void followContactsTests(String uidx ,String caseValue) throws IOException {
		
		globalCaseValue = caseValue;
		String payload = utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/followContacts");
		MyntraService pumpsService = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.FOLLOWCONTACTS ,init.Configurations,payload);
		pumpsService.URL = utilities.prepareparameterizedURL(pumpsService.URL, uidx);
		
		RequestGenerator requestGen = new RequestGenerator(pumpsService, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "SUCCESS","Status Type Mismatch");
			
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), "ERROR","Status Type Mismatch");
		}
		
	}

}














