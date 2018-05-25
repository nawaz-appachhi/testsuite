package com.myntra.apiTests.portalservices.lgpservices;


import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.HallmarkServicesDP;
import com.myntra.apiTests.portalservices.lgpServicesOnDemand.LGPIdeaServices;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.PayloadType;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class HallmarkServicesTest extends HallmarkServicesDP {
	

	static Logger logger = Logger.getLogger(LGPIdeaServices.class);
	static APIUtilities utilities = new APIUtilities();
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(SessionServiceTests.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	String uidx="f98d7987.0c72.465c.8e48.a5950652e276Y00Jz2mjGX";
	String username;
	String password;

	HashMap<String, String> headers;
	String globalCaseValue = "";
	@BeforeClass(alwaysRun=true)
	public void init(){
		
		headers = new HashMap<String,String>();
		headers.put(HttpHeaders.ACCEPT, "application/json");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
		
		username=System.getenv("username");
		password=System.getenv("password");
		if(username!=null && password!=null)
		{
			try {
				uidx=lgpServiceHelper.getUidxForCredential(username, password);
				System.out.println(uidx);
				System.out.println("Username : "+username);
				System.out.println("Password : "+password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@AfterMethod 
	protected void endOfTestMethod(Method method) throws Exception { 
		
		String methodName = method.getName();
		System.out.println("End of method -------- "+methodName +" For --> "+ globalCaseValue);
		System.out.println("=====================================================================================================");
	}
	

	 // @Test(dataProvider = "updateUserAttribute")
	  public void updateUserAttribute (Map<String, Object> testData) throws JSONException {
		  ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("attributeEntryList");
		  String attributesBeforeUpdateCall="";
		  Map<String,Map<String,Object>> baseAttributesMap=null;
		  ArrayList<String> failures = new ArrayList<>();
		  
		  //Clear the Attributes if required for testing
		  if(testData.containsKey("clearAttribute")){
			  String updateAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/updateUserAttributePayload";
			  //Build the attribute entry list in the json format
			  ArrayList<HallmarkServicesDP.AttributeEntry> clearAttribute=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("clearAttribute");
			  String clearAttributeListJson = buildAttributeEntryListJson(clearAttribute);
			  
			  MyntraService updateUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEUSERATTRIBUTE,init.Configurations,new String[]{uidx,clearAttributeListJson},updateAttributePayloadFilePath, PayloadType.JSON,PayloadType.JSON);
			  RequestGenerator updateAttributeRequest= new RequestGenerator(updateUserAttributeService,headers);
		  }
		  
		  if(!testData.containsKey("expectedErrorResponse")){ //If an error is expected in the update call
			  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
			  
			  String namespacesForGetAttributeCall = getNamespacesAsPayloadString(attributeEntryList);
			  MyntraService getUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYUIDX,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
			  getUserAttributeService.URL=apiUtil.prepareparameterizedURL(getUserAttributeService.URL, new String[]{uidx});
			  RequestGenerator getAttributeRequest= new RequestGenerator(getUserAttributeService,headers);
			  attributesBeforeUpdateCall = getAttributeRequest.returnresponseasstring();
			  log.info("Base Attributes from response: \n"+attributesBeforeUpdateCall);
			  //Convert the response into array format
			  baseAttributesMap=getAttributesAsMap(attributesBeforeUpdateCall,"formattedUser");
		  }

		  String updateAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/updateUserAttributePayload";
		  //Build the attribute entry list in the json format
		  String attributeEntryListJson = buildAttributeEntryListJson(attributeEntryList);
		  String uidx="";
		  //Get the uidx
		  if(testData.containsKey("uidx")){
			  uidx = testData.get("uidx").toString();
		  }else{
			  uidx=this.uidx;
		  }
		  
		  MyntraService updateUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEUSERATTRIBUTE,init.Configurations,new String[]{uidx,attributeEntryListJson},updateAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		  RequestGenerator updateAttributeRequest= new RequestGenerator(updateUserAttributeService,headers);
		  
		  
		  if(testData.containsKey("expectedErrorResponse")){
			  if((int)testData.get("expectedErrorResponse")==updateAttributeRequest.response.getStatus())
			  failures.add("expected error response: The expected error response "+testData.get("expectedErrorResponse")+" does not match the returned response "+updateAttributeRequest.response.getStatus());
		  }
		  else{
			  Map<String, Map<String,Object>> expectedAttributeResponseMap = getUpdatedAttributeMap(baseAttributesMap, attributeEntryList);
			  if(expectedAttributeResponseMap==null){	//The update call is expected to return error
				  if(!((updateAttributeRequest.response.getStatus()>=400) && (updateAttributeRequest.response.getStatus()<499))){
					  failures.add("expected error response: Error response expected due to invalid payload data. Returned response "+updateAttributeRequest.response.getStatus());
				  }
			  }else if(updateAttributeRequest.response.getStatus()==200){
				  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
				  
				  String namespacesForGetAttributeCall = getNamespacesAsPayloadString(attributeEntryList);
				  MyntraService getUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYUIDX,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
				  getUserAttributeService.URL=apiUtil.prepareparameterizedURL(getUserAttributeService.URL, new String[]{uidx});
				  RequestGenerator getAttributeRequest= new RequestGenerator(getUserAttributeService,headers);
				  if(getAttributeRequest.response.getStatus()==200){
					  String attributesAfterUpdateCall = getAttributeRequest.returnresponseasstring();
					  log.info("Update Attribute from response: \n"+attributesAfterUpdateCall);
					  //Convert the response into array format
					  Map<String, Map<String,Object>> updatedAttributesFromResponseMap=getAttributesAsMap(attributesAfterUpdateCall,"formattedUser");
					  //Compare the two map for value mismatch
					  compareMaps(expectedAttributeResponseMap, updatedAttributesFromResponseMap, failures);
				  }else{
					  failures.add("get call failed: Get attributes call returned error "+getAttributeRequest.response.getStatus());
				  }
			  }else{
				  failures.add("update call failed: The update call was not successfull. Error code returned "+updateAttributeRequest.response.getStatus());
			  }
		  
			  
		  }
		  
		  if(failures.size()>0){
				Assert.fail(getFailuresPoints(failures));
			}
		  

	  }
	  
	  

	  //@Test(dataProvider = "updateDeviceAttribute")
	  public void updateDeviceAttribute (Map<String, Object> testData) throws JSONException {
		  ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("attributeEntryList");
		  String deviceId="a1";
		  if(testData.containsKey("deviceId"))
			  deviceId=testData.get("deviceId").toString();
		  String attributesBeforeUpdateCall="";
		  Map<String,Map<String,Object>> baseAttributesMap=null;
		  ArrayList<String> failures = new ArrayList<>();
		  //Clear the Attributes if required for testing
		  if(testData.containsKey("clearAttribute")){
			  String updateAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/updateDeviceAttributePayload";
			  //Build the attribute entry list in the json format
			  ArrayList<HallmarkServicesDP.AttributeEntry> clearAttribute=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("clearAttribute");
			  String clearAttributeListJson = buildAttributeEntryListJson(clearAttribute);
			  
			  MyntraService updateDeviceAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEDEVICEATTRIBUTE,init.Configurations,new String[]{deviceId,clearAttributeListJson},updateAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
			  RequestGenerator updateAttributeRequest= new RequestGenerator(updateDeviceAttributeService,headers);
		  }
		  
		  if(!testData.containsKey("expectedErrorResponse")){ //If an error is expected in the update call
			  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
			  
			  String namespacesForGetAttributeCall = getNamespacesAsPayloadString(attributeEntryList);
			  MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYDEVICEID,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
			  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{deviceId});
			  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService,headers);
			  attributesBeforeUpdateCall = getAttributeRequest.returnresponseasstring();
			  log.info("Base Attributes from response: \n"+attributesBeforeUpdateCall);
			  //Convert the response into array format
			  baseAttributesMap=getAttributesAsMap(attributesBeforeUpdateCall,"formattedDeviceEntry");
		  }

		  String updateAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/updateDeviceAttributePayload";
		  //Build the attribute entry list in the json format
		  String attributeEntryListJson = buildAttributeEntryListJson(attributeEntryList);
		  
		  MyntraService updateUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEDEVICEATTRIBUTE,init.Configurations,new String[]{deviceId,attributeEntryListJson},updateAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		  RequestGenerator updateAttributeRequest= new RequestGenerator(updateUserAttributeService,headers);
		  
		  
		  if(testData.containsKey("expectedErrorResponse")){
			  if((int)testData.get("expectedErrorResponse")==updateAttributeRequest.response.getStatus())
			  failures.add("expected error response: The expected error response "+testData.get("expectedErrorResponse")+" does not match the returned response "+updateAttributeRequest.response.getStatus());
		  }
		  else{
			  Map<String, Map<String,Object>> expectedAttributeResponseMap = getUpdatedAttributeMap(baseAttributesMap, attributeEntryList);
			  if(expectedAttributeResponseMap==null){	//The update call is expected to return error
				  if(!((updateAttributeRequest.response.getStatus()>=400) && (updateAttributeRequest.response.getStatus()<499))){
					  failures.add("expected error response: Error response expected due to invalid payload data. Returned response "+updateAttributeRequest.response.getStatus());
				  }
			  }else if(updateAttributeRequest.response.getStatus()==200){
				  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
				  
				  String namespacesForGetAttributeCall = getNamespacesAsPayloadString(attributeEntryList);
				  MyntraService getUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYDEVICEID,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
				  getUserAttributeService.URL=apiUtil.prepareparameterizedURL(getUserAttributeService.URL, new String[]{deviceId});
				  RequestGenerator getAttributeRequest= new RequestGenerator(getUserAttributeService,headers);
				  if(getAttributeRequest.response.getStatus()==200){
					  String attributesAfterUpdateCall = getAttributeRequest.returnresponseasstring();
					  log.info("Update Attribute from response: \n"+attributesAfterUpdateCall);
					  //Convert the response into array format
					  Map<String, Map<String,Object>> updatedAttributesFromResponseMap=getAttributesAsMap(attributesAfterUpdateCall,"formattedDeviceEntry");
					  //Compare the two map for value mismatch
					  compareMaps(expectedAttributeResponseMap, updatedAttributesFromResponseMap, failures);
				  }else{
					  failures.add("get call failed: Get attributes call returned error "+getAttributeRequest.response.getStatus());
				  }
			  }else{
				  failures.add("update call failed: The update call was not successfull. Error code returned "+updateAttributeRequest.response.getStatus());
			  }
			  
		  }
		  
		  if(failures.size()>0){
				Assert.fail(getFailuresPoints(failures));
			}
		  

	  }
	  
	  
	  //@Test(dataProvider = "getAttributeByUidx")
	  public void getAttributeByUidx (Map<String, Object> testData) throws JSONException {
		  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
		  ArrayList<String> failures = new ArrayList<>();
		  String uidx = this.uidx;
		  if(testData.containsKey("uidx")){
			  uidx = testData.get("uidx").toString();
		  }
		  String namespacesForGetAttributeCall = (String)testData.get("namespaceList");
		  MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYUIDX,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{uidx});
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService,headers);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  log.info("Base Attributes from response: \n"+attributesResponse);
		  
		  if((int)testData.get("expectedResponse")!=getAttributeRequest.response.getStatus()){
			  failures.add("incorrect response: The response recieved "+getAttributeRequest.response.getStatus()+" does not match expected response "+testData.get("expectedResponse"));
		  }
		  
		  if(failures.size()>0){
				Assert.fail(getFailuresPoints(failures));
			}
	  }
	  
	  

	  //@Test(dataProvider = "getAttributeByDeviceId")
	  public void getAttributeByDeviceId (Map<String, Object> testData) throws JSONException {
		  String getAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/getAttributeByUidxPayload";
		  ArrayList<String> failures = new ArrayList<>();
		  String deviceId = "a1";
		  if(testData.containsKey("deviceId")){
			  deviceId = testData.get("deviceId").toString();
		  }
		  String namespacesForGetAttributeCall = (String)testData.get("namespaceList");
		  MyntraService getDeviceAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.GETATTRIBUTESBYDEVICEID,init.Configurations,new String[]{namespacesForGetAttributeCall},getAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		  getDeviceAttributeService.URL=apiUtil.prepareparameterizedURL(getDeviceAttributeService.URL, new String[]{deviceId});
		  RequestGenerator getAttributeRequest= new RequestGenerator(getDeviceAttributeService,headers);
		  String attributesResponse = getAttributeRequest.returnresponseasstring();
		  log.info("Base Attributes from response: \n"+attributesResponse);
		  
		  if((int)testData.get("expectedResponse")!=getAttributeRequest.response.getStatus()){
			  failures.add("incorrect response: The response recieved "+getAttributeRequest.response.getStatus()+" does not match expected response "+testData.get("expectedResponse"));
		  }
		  
		  if(failures.size()>0){
				Assert.fail(getFailuresPoints(failures));
			}
	  }
	  
	  
	  //@Test(dataProvider = "searchUserByAttribute")
	  public void searchUserByAttribute (Map<String, Object> testData) throws JSONException {
		  

		//Update the Attributes if required for testing
		  if(testData.containsKey("updateAttributeEntryList")){
			  String updateAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/updateUserAttributePayload";
			  //Build the attribute entry list in the json format
			  ArrayList<HallmarkServicesDP.AttributeEntry> updateAttribute=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("updateAttributeEntryList");
			  String clearAttributeListJson = buildAttributeEntryListJson(updateAttribute);
			  
			  MyntraService updateUserAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.UPDATEUSERATTRIBUTE,init.Configurations,new String[]{uidx,clearAttributeListJson},updateAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
			  RequestGenerator updateAttributeRequest= new RequestGenerator(updateUserAttributeService,headers);
		  }
		  String searchUserByAttributePayloadFilePath = "./Data/payloads/JSON/Hallmark/searchByAttributePayload";
		  ArrayList<HallmarkServicesDP.AttributeEntry> searchAttributeEntryList=(ArrayList<HallmarkServicesDP.AttributeEntry>)testData.get("searchAttributeEntryList");
		  String searchAttributeJson = buildAttributeSearchListJson(searchAttributeEntryList);
		  
		  MyntraService searchUserByAttributeService = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,APINAME.SEARCHUSERATTRIBUTES,init.Configurations,new String[]{searchAttributeJson},searchUserByAttributePayloadFilePath,PayloadType.JSON,PayloadType.JSON);
		  RequestGenerator searchUserByAttributeRequest= new RequestGenerator(searchUserByAttributeService,headers);
	  }
	  
	  @Test(dataProvider = "searchDeviceByAttribute")
	  public void searchDeviceByAttribute (HashMap<String, String> reqData,HashMap<String,String> resData) throws JSONException,IOException {
		  
		  String payloadFile=utilities.readFileAsString("./Data/payloads/JSON/Hallmark/searchDeviceByAttributePayload");
			System.out.println(reqData);
			payloadFile=utilities.preparepayload(payloadFile,new String[] {reqData.get("namespace"),reqData.get("key"),reqData.get("value")});
			
			System.out.println("the payload is"+payloadFile);
			MyntraService service = Myntra.getService(ServiceType.LGP_HALLMARKSERVICE,
					APINAME.SEARCHDEVICEATTRIBUTES, init.Configurations,payloadFile);
			RequestGenerator request =  new RequestGenerator(service);
			System.out.println("\nService URL---->"+service.URL);
			String resp = request.respvalidate.returnresponseasstring();
			System.out.println("\n-----------------Response-------------\n:"+resp);
			if(request.response.getStatus()==200)
			{
				AssertJUnit.assertEquals("Status not equal to "+resData.get("status"), Integer.parseInt(resData.get("status")),request.response.getStatus());
				AssertJUnit.assertEquals("Status Code not equal to "+resData.get("statusCode"), resData.get("statusCode").toLowerCase(),JsonPath.read(resp,"$.status.statusCode").toString().toLowerCase());
				AssertJUnit.assertEquals("Status message is not equal to "+resData.get("statusMessage"),resData.get("statusMessage").toLowerCase(), JsonPath.read(resp,"$.status.statusMessage").toString().toLowerCase());
				AssertJUnit.assertEquals("Status Type is not equal to "+resData.get("statusType"),resData.get("statusType").toLowerCase(), JsonPath.read(resp,"$.status.statusType").toString().toLowerCase());
				AssertJUnit.assertEquals("success is not true "+resData.get("success"), resData.get("success").toLowerCase(),JsonPath.read(resp,"$.status.success").toString().toLowerCase());
				AssertJUnit.assertEquals("total count not equal to"+resData.get("totalCount"), resData.get("totalCount").toLowerCase(),JsonPath.read(resp,"$.status.totalCount").toString().toLowerCase());
		 
				
			}
			else
			{
				AssertJUnit.assertEquals("Status not equal to "+resData.get("status"), Integer.parseInt(resData.get("status")),request.response.getStatus());
			}
	 }
	  
	  
	  public String getFailuresPoints(ArrayList<String> failures){
			String failureAsPoints="";
			int i=1;
			for(String errMsg:failures){
				failureAsPoints= failureAsPoints +(i++)+". "+errMsg;
			}
			return(failureAsPoints);
		}
	  
	  public String getNamespacesAsPayloadString(ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList){
		  String namespacesAsString = "[";
		  boolean first=true;
		  for(HallmarkServicesDP.AttributeEntry attributeEntry:attributeEntryList){
			  if(!first){
				  //Append commas
				  namespacesAsString=namespacesAsString+",";
				  
			  }
			  String namespace = attributeEntry.namespace;
			  namespacesAsString = namespacesAsString+"\""+namespace+"\"";
			  first=false;
		  }
		 return namespacesAsString+"]";
	  }
	  
	  public String buildAttributeEntryListJson(ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList){
		  String attributeEntryListJson="[";
		  boolean first=true;
		  for(HallmarkServicesDP.AttributeEntry attributeEntry:attributeEntryList){
			  if(!first){
				  //Append commas
				  attributeEntryListJson=attributeEntryListJson+",";
			  }
			  first=false;
			  attributeEntryListJson=attributeEntryListJson+
					  "{"+
					  	"\"namespace\":\""+attributeEntry.namespace+"\","+
					  	"\"key\":\""+attributeEntry.key+"\","+
					  	"\"value\":"+getValue(attributeEntry.value)+","+
					  	"\"action\":\""+attributeEntry.action+"\""+
					  "}";
		  }
		  attributeEntryListJson=attributeEntryListJson+"]";
		  return(attributeEntryListJson);
	  }
	  
	  
	  public String buildAttributeSearchListJson(ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList){
		  String attributeSearchListJson="[";
		  boolean first=true;
		  for(HallmarkServicesDP.AttributeEntry attributeEntry:attributeEntryList){
			  if(!first){
				  //Append commas
				  attributeSearchListJson=attributeSearchListJson+",";
			  }
			  first=false;
			  attributeSearchListJson=attributeSearchListJson+
					  "{"+
					  	"\"namespace\":\""+attributeEntry.namespace+"\","+
					  	"\"key\":\""+attributeEntry.key+"\","+
					  	"\"value\":"+getValue(attributeEntry.value)+","+
					  "}";
		  }
		  attributeSearchListJson=attributeSearchListJson+"]";
		  return(attributeSearchListJson);
	  }
	  
	  public String getValue(Object value){
		  if(value instanceof Object[]){
			  Object[] objArr = (Object[])value;
				String str="[";
				
				for(int i=0; i<objArr.length;i++){
					if(i!=0){
						str=str+",";
						}
					str=str+(objArr[i] instanceof String ? "\""+objArr[i]+"\"":objArr[i]);
					
				}
				str=str+"]";
				return(str);
		  }else{
			  return("\""+value.toString()+"\"");
		  }
	  }
	  
	  public Map<String,Map<String,Object>> getAttributesAsMap(String jsonObject,String attributeParent) throws JSONException{
		  
		  Map<String,Map<String,Object>> namespaceAttributesMap=new HashMap<>();
		  String namespaceJsonString=JsonPath.read(jsonObject, "$."+attributeParent).toString();
		  JSONObject namespaceJson = new JSONObject(namespaceJsonString);
		  Iterator<String> namespaceIterator =   namespaceJson.keys();
		  while(namespaceIterator.hasNext()){
			  Map<String,Object> attributeValueMap=new  HashMap<>();
			  String namespace = namespaceIterator.next();
			  JSONObject attributeJson = namespaceJson.getJSONObject(namespace);//new JSONObject(JsonPath.read(jsonObject, "$.formattedUser."+namespace));
			  Iterator<String> attributeIterator =   attributeJson.keys();
			  while(attributeIterator.hasNext()){
				  String attribute=attributeIterator.next();
				  if(attributeJson.get(attribute) instanceof JSONArray){
					  JSONArray jsonArray = attributeJson.getJSONArray(attribute);
					  Object[] valueArray = new Object[jsonArray.length()];
					  for(int i=0;i<jsonArray.length();i++){
						  valueArray[i]=jsonArray.get(i);
					  }
					  attributeValueMap.put(attribute, valueArray);
				  }else{
					  attributeValueMap.put(attribute, attributeJson.get(attribute));
				  }
				  
			  }
			  namespaceAttributesMap.put(namespace, attributeValueMap);
		  }
		  
		  return(namespaceAttributesMap);
	  }
	  
	  public Map<String,Map<String,Object>> getUpdatedAttributeMap(Map<String,Map<String,Object>> baseAttributeMap,ArrayList<HallmarkServicesDP.AttributeEntry> attributeEntryList){
		  for(HallmarkServicesDP.AttributeEntry attributeEntry:attributeEntryList){
			  switch(attributeEntry.action){
			  case "ADD": 
				  if(baseAttributeMap.containsKey(attributeEntry.namespace)){
					  if(baseAttributeMap.get(attributeEntry.namespace).containsKey(attributeEntry.key)){
						  Object baseValue = baseAttributeMap.get(attributeEntry.namespace).get(attributeEntry.key);
						  Object updateValue = attributeEntry.value;
						  Object updatedValue = addValue(baseValue, updateValue);
						  if(validateValue(updatedValue)){
							  baseAttributeMap.get(attributeEntry.namespace).put(attributeEntry.key, updatedValue);
						  }else{
							  return(null);
						  }
					  }else{
						  if(validateValue(attributeEntry.value)){
							  baseAttributeMap.get(attributeEntry.namespace).put(attributeEntry.key, attributeEntry.value);
						  }else{
							  return(null);
						  }
					  }
				  }else{
					  Map<String, Object> attributeValueMap = new HashMap<>();
					  if(validateValue(attributeEntry.value)){
						  attributeValueMap.put(attributeEntry.key, attributeEntry.value);
						  baseAttributeMap.put(attributeEntry.namespace, attributeValueMap);
					  }else{
						  return(null);
					  }
				  }
				  break;
			  case "UPDATE": 
				  if(baseAttributeMap.containsKey(attributeEntry.namespace)){
					  if(baseAttributeMap.get(attributeEntry.namespace).containsKey(attributeEntry.key)){
						  Object baseValue = baseAttributeMap.get(attributeEntry.namespace).get(attributeEntry.key);
						  Object updateValue = attributeEntry.value;
						  Object updatedValue = updateValue(baseValue, updateValue);
						  if(validateValue(updatedValue)){
							  baseAttributeMap.get(attributeEntry.namespace).put(attributeEntry.key, updatedValue);
						  }else{
							  return(null);
						  }
					  }else{
						  if(validateValue(attributeEntry.value)){
							  baseAttributeMap.get(attributeEntry.namespace).put(attributeEntry.key, attributeEntry.value);
						  }else{
							  return(null);
						  }
					  }
				  }else{
					  Map<String, Object> attributeValueMap = new HashMap<>();
					  if(validateValue(attributeEntry.value)){
						  attributeValueMap.put(attributeEntry.key, attributeEntry.value);
						  baseAttributeMap.put(attributeEntry.namespace, attributeValueMap);
					  }else{
						  return(null);
					  }
				  }
				  break;
			  case "DELETE": 
				  if(baseAttributeMap.containsKey(attributeEntry.namespace)){
					  if(baseAttributeMap.get(attributeEntry.namespace).containsKey(attributeEntry.key)){
						  Object baseValue = baseAttributeMap.get(attributeEntry.namespace).get(attributeEntry.key);
						  if(baseValue instanceof Object[]){
							  Object updateValue = attributeEntry.value;
							  Object updatedValue = deleteValue(baseValue, updateValue);
							  if(validateValue(updatedValue)){
								  baseAttributeMap.get(attributeEntry.namespace).put(attributeEntry.key, updatedValue);
							  }else{
								  return(null);
							  }
						  }else{
							  baseAttributeMap.get(attributeEntry.namespace).remove(attributeEntry.key);
							  if(baseAttributeMap.get(attributeEntry.namespace).isEmpty()){
								  baseAttributeMap.remove(attributeEntry.namespace);
							  }
						  }
					  }
				  }
				  break;
			  case "DELETEALL": 
				  if(baseAttributeMap.containsKey(attributeEntry.namespace)){
					  if(baseAttributeMap.get(attributeEntry.namespace).containsKey(attributeEntry.key)){
						  baseAttributeMap.get(attributeEntry.namespace).remove(attributeEntry.key);
						  if(baseAttributeMap.get(attributeEntry.namespace).isEmpty()){
							  baseAttributeMap.remove(attributeEntry.namespace);
						  }
					  }
				  }
				  break;
			  default: return( null);
			  }
			  
		  }
		  return(baseAttributeMap);
	  }
	  
	  public Object addValue(Object baseValue,Object updateValue){
		  if((baseValue instanceof Object[]) && (updateValue instanceof Object[])){
			  Object[] baseArray=(Object[])baseValue;
			  Object[] updateArray=(Object[])updateValue;
			  Object[] updatedArrayValue=new Object[baseArray.length+updateArray.length];
			  System.arraycopy(baseArray, 0, updatedArrayValue, 0, baseArray.length);
			  System.arraycopy(updateArray, 0, updatedArrayValue, baseArray.length, updateArray.length);
			  return(updatedArrayValue);
		  }else{
			 return(updateValue); 
		  }
	  }
	  
	  public Object updateValue(Object baseValue,Object updateValue){
		  
			 return(updateValue); 
		  
	  }
	  
	  public Object deleteValue(Object baseValue,Object updateValue){
		  if((baseValue instanceof Object[]) && (updateValue instanceof Object[])){
			  Object[] baseArray=(Object[])baseValue;
			  Object[] updateArray=(Object[])updateValue;
			  
			  List<Object> updatedArrayList = new ArrayList<>();
			  for(int i=0; i<baseArray.length;i++){
				  boolean flag=true;
				  for(int j=0; j<updateArray.length;j++)
					  if(baseArray[i].equals(updateArray[j])){
						  flag=false;
						  break;
					  }
				  if(flag)
					  updatedArrayList.add(baseArray[i]);
			  }
			  return(updatedArrayList.toArray());
		  }else if((baseValue instanceof Object[]) && !(updateValue instanceof Object[])){
			  return(null);
		  }else{
			  return(null);
		  }
	  }
	  
	  
	  public boolean validateValue(Object value){
		  if(value!=null){
			  if(value instanceof Object[]){
				  Object[] objArr = (Object[])value;
					
					for(int i=0; i<objArr.length;i++){
						if(!objArr[0].getClass().equals(objArr[i].getClass())){
							return(false);
						}
					}
			  }
		  }else{
			  return(false);
		  }
		  
		return(true);
		  
		  
	  }
	  
	  public void compareMaps(Map<String, Map<String, Object>> namespaceAttributeMapExpected,Map<String, Map<String, Object>> namespaceAttributeMapResponse, ArrayList<String> failures){
		  for(Map.Entry<String, Map<String,Object>> entry: namespaceAttributeMapExpected.entrySet()){
			  if(namespaceAttributeMapResponse.containsKey(entry.getKey())){
				  Map<String,Object> attributeMap1 = entry.getValue();
				  Map<String,Object> attributeMap2 = namespaceAttributeMapResponse.get(entry.getKey());
				  for(Map.Entry<String,Object> attributeEntry: attributeMap1.entrySet()){
					  if(attributeMap2.containsKey(attributeEntry.getKey())){
						  Object value1 =attributeEntry.getValue();
						  Object value2 = attributeMap2.get(attributeEntry.getKey());
						  if((value1 instanceof Object[]) && (value2 instanceof Object[])){
							  Object[] arrValue1 = (Object[])value1;
							  Object[] arrValue2 = (Object[])value2;
							  List<Object> arrListValue1 = Arrays.asList(arrValue1);
							  List<Object> arrListValue2 = Arrays.asList(arrValue2);
							  if(arrValue1.length == arrValue2.length){
								  boolean equal =true;
								  for(int i=0; i<arrValue1.length;i++){
									  if(!arrListValue1.contains(arrListValue2.get(i))){
										  equal=false;
									  }
								  }
								  if(!equal){
									  failures.add("attribute value list not matching: The attribute value for "+entry.getKey()+"."+attributeEntry.getKey()+" is "+getValue(value2)+" while expected value is "+getValue(value1));
								  }
							  }else{
								  failures.add("attribute value list not matching: The attribute value for "+entry.getKey()+"."+attributeEntry.getKey()+" is "+getValue(value2)+" while expected value is "+getValue(value1));
							  }
						  }else if(value1.equals(value2) ){
							  
						  }else{
							  failures.add("attribute value not matching: The attribute value for "+entry.getKey()+"."+attributeEntry.getKey()+" is "+value2+" while expected value is "+value1);
						  }
					  }else{
						  failures.add("attribute key doesnt exist: The attribute key "+attributeEntry.getKey()+" doesnt exist in the response");
					  }
				  }
			  }else{
				  failures.add("namespace doesnt exist: Namespace "+entry.getKey()+" does not exist");
			  }
		  }
	  }
}
