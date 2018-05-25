package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.myntra.apiTests.dataproviders.LgpFeedObjectIndividualDP;
import com.myntra.apiTests.portalservices.lgpservices.LgpFeedObjectIndividualHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;

public class LgpFeedObjectIndividualTests extends LgpFeedObjectIndividualDP {
	
	
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LgpFeedObjectIndividualTests.class);
	private String globalCaseValue;
	private String versionSpecification=null;
	
	private LgpFeedObjectIndividualHelper feedObjectIndividualHelper = new LgpFeedObjectIndividualHelper() ;
	
	@BeforeMethod
	protected void initBeforeMethod(){
		
		versionSpecification = "v2.7";
	}
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("=====================================================================================================");
	}
	

	@Test(dataProvider = "diyIndividualCardDP", groups = "Sanity")
	public void diyTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			String[] toValidateJsonKeyList = getJsonKeyList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			boolean customJsonKeyComponentValidationResult = feedObjectIndividualHelper.jsonKeyValidationProcess(toValidateJsonKeyList, serveSideFeedObjectResponse, 0);
			
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			Assert.assertEquals(customJsonKeyComponentValidationResult, true,"Custom Json Key validations fail for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
				
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case8")){
			
			String objectId = objectValueIdentifier(mainMap);
			versionSpecification = "v2.6";		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
		}
	}
	
	@Test(dataProvider = "merchandisingIndividualCardDP", groups = "Sanity")
	public void merchandisingTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
				
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
				
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
	
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String objectId = objectValueIdentifier(mainMap);
			versionSpecification = "v2.6";
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
	}
	
	@Test(dataProvider = "topShotsIndividualCardDP", groups = "Sanity")
	public void topShotsIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
	
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
			
		}
		
		else if(caseValue.equalsIgnoreCase("case8")){
			
			String objectId = objectValueIdentifier(mainMap);		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case9")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
		
		else if(caseValue.equalsIgnoreCase("case10")){
			
			String objectId = objectValueIdentifier(mainMap);	
			versionSpecification="v2.5";
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
		}

	}
	
	@Test(dataProvider = "carousalIndividualCardDP", groups = "Sanity")
	public void carousalIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){

		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
	
			String objectId = objectValueIdentifier(mainMap);		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String objectId = objectValueIdentifier(mainMap);		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case8")){
			
			String objectId = objectValueIdentifier(mainMap);		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case9")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
		
		else if(caseValue.equalsIgnoreCase("case10")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}

		else if(caseValue.equalsIgnoreCase("case11")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
		
		else if(caseValue.equalsIgnoreCase("case12")){
	
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
		
		else if(caseValue.equalsIgnoreCase("case13")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
	
		else if(caseValue.equalsIgnoreCase("case14")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
		
		else if(caseValue.equalsIgnoreCase("case15")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);		
			
		}
			
	}
		
	@Test(dataProvider = "brandCarousalIndividualCardDP", groups = "Sanity")
	public void brandCarousalIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){

		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
	
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			String objectId = objectValueIdentifier(mainMap);		
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}

	}

	@Test(dataProvider = "articleIndividualCardDP", groups = "Sanity")
    public void articleIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			versionSpecification = "v2.6";
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
    	
    }

	@Test(dataProvider = "bannerIndividualCardDP", groups = "Sanity")
    public void bannerIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
	}
	
	@Test(dataProvider = "splitBannerIndividualCardDP", groups = "Sanity")
    public void splitBannerIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case2")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.count").toString(), toValidateList[0],"Response Assertion Fail for : "+caseValue);
			Assert.assertEquals(JsonPath.read(serveSideFeedObjectResponse, "$.cards").toString(), toValidateList[1],"Response Assertion Fail for : "+caseValue);
			
		}
	}
	
	
	@Test(dataProvider = "collectionsIndividualCardDP", groups = "Sanity")
    public void collectionsIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case3")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			versionSpecification = "v2.6";
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
	
	}
	
	
	@Test(dataProvider = "postoShotIndividualCardDP", groups = "Sanity")
    public void postoShotIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			versionSpecification = "v2.6";
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
	
	}


	@Test(dataProvider = "productStoryIndividualCardDP", groups = "Sanity")
    public void productStoryIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case2")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		if(caseValue.equalsIgnoreCase("case3")){
			
			String objectId = objectValueIdentifier(mainMap);
			versionSpecification = "v2.6";
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
		
		else if(caseValue.equalsIgnoreCase("case4")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		
		else if(caseValue.equalsIgnoreCase("case5")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case6")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
		
		else if(caseValue.equalsIgnoreCase("case7")){
			
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
			
		}
	
	}

	

	@Test(dataProvider = "productRackLargeIndividualCardDP", groups = "Sanity")
    public void productRackLargeIndividualTests(HashMap<String,HashMap<String,String[]>> mainMap){
		
		String caseValue = caseValueIdentifier(mainMap);
		globalCaseValue = caseValue;
		
		if(caseValue.equalsIgnoreCase("case1")){
			
			String objectId = objectValueIdentifier(mainMap);
			String serveSideFeedObjectResponse = feedObjectIndividualHelper.serveRequest(objectId, versionSpecification, caseValue);
			String[] toValidateList = getInnerList(mainMap);
			
			boolean mainComponentValidationResult = feedObjectIndividualHelper.mainComponentCardValidation(serveSideFeedObjectResponse, versionSpecification);
			boolean basicComponentValidationResult = feedObjectIndividualHelper.cardComponentsValidationProcess(toValidateList, serveSideFeedObjectResponse);
			boolean componentsJsonKeyValidationResult = feedObjectIndividualHelper.jsonKeyValidationForCardComponentList(toValidateList, versionSpecification, serveSideFeedObjectResponse);
			
			Assert.assertEquals(mainComponentValidationResult, true,"Main-Component validation fail for "+caseValue);
			Assert.assertEquals(basicComponentValidationResult, true,"Basic-Component validation fail for "+caseValue);
			Assert.assertEquals(componentsJsonKeyValidationResult, true,"Json Key validations fail for Components for "+caseValue);
		}
	}

}

























