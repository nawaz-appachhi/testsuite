package com.myntra.apiTests.portalservices.lgpservices;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class LgpPumpsHelper {
	
	public static Initialize init = new Initialize("/Data/configuration");
	public static String envName = init.Configurations.properties.getPropertyValue("environment");
	static HashMap<String, String> headers = new HashMap<String, String>();
	static APIUtilities utilities = new APIUtilities();
	public static ArrayList<String> objectIdsToBeDeleted = new ArrayList<>();
	public static int timeCounter=0;
	
	static Logger log = Logger.getLogger(LgpPumpsHelper.class);
	
	public  boolean cassandraRequestValidation(String queryObject,String columnName, String expectedValue, Session session){
		
		if(envName.equalsIgnoreCase("fox7")){
			
			try {
				
				/*Cluster cluster = Cluster.builder().addContactPoint("qa_lg2").withPort(9042).build();
				Metadata metadata = cluster.getMetadata();
				System.out.println(metadata.getClusterName());
				for (Host host : metadata.getAllHosts()) {
					System.out.println(host.getDatacenter());
					System.out.println(host.getAddress()); 
					System.out.println(host.getRack());
				}
				session = cluster.connect();*/
				
				
				ResultSet rs = session.execute(queryObject);
				
				
				/*ResultSet rs = session.execute(queryObject);
				String data = rs.one().getString("app_object_ref_key");
				ResultSet rs2 = session.execute(queryObject);
				String data2 =  rs2.one().getString(columnName);*/
				
					
				boolean res = expectedValue.equals(rs.one().getString(columnName));
				
				return res;
				
				
			} catch (Exception e) {
				
				System.out.println("The exception occured is related to : "+e.getMessage());
				
				return false;
			}
		}
		return true;
		
	}
	
	
	public void deleteObjectId(String objectIdForDeletion)
	{
		setHeaders();
		String appId=objectIdForDeletion.split(":")[0].replace("[","");
		String objectId=objectIdForDeletion.split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.DELETEPUMPSOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		String message =JsonPath.read(response, "$.status.statusMessage");
		if(message.equals("Object deleted successfully"));
		{
		  System.out.println("Deleted Successfully");
		}
	}
	
	

	public long getcurrentTimestamp() {
		// TODO Auto-generated method stub
		Date date=new Date();
		int seconds=date.getSeconds();
		date.setSeconds(seconds+timeCounter);
		timeCounter=timeCounter+1;
		return date.getTime();
	}
	
	
	public String createObject(String appId,String refId,String objectType,String title) throws IOException{
		String objectId=null;
		setHeaders();
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		payload=utilities.preparepayload(payload,new String[] {appId,refId,objectType,title});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,refId,objectType,title});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		objectId=JsonPath.read(response, "$.data[0].refId");
		objectIdsToBeDeleted.add("2:"+objectId);
		return "2:"+objectId;
		
	}
	
	public String createObjectForGettingID(String appId,String refId,String objectType,String title) throws IOException{
		String id=null;
		setHeaders();
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		payload=utilities.preparepayload(payload,new String[] {appId,refId,objectType,title});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,refId,objectType,title});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		id=String.valueOf(JsonPath.read(response, "$.data[0].id").toString());
		String objectId=JsonPath.read(response, "$.data[0].refId");
		objectIdsToBeDeleted.add("2:"+objectId);
		return id;
		
	}
	
	public RequestGenerator getQueryRequestSingleParam(APINAME apiName, String param){
		
		setHeaders();
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		
		RequestGenerator req = new RequestGenerator(service,headers);
		System.out.println(service.URL);
		log.info(service.URL);
		return req;
		
	}

	public void setHeaders() {
		// TODO Auto-generated method stub
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
	}
	
	/**
	 * Hits the Aerospike get API with the parameter given.
	 * @param param appID and the refID which are sending as part of the end point.
	 * @return RequestGenerator instance for the Aerospike API
	 * @author Suhas.kashyap
	 */
	public RequestGenerator getAerospikeResponse(String[] param){
		setHeaders();
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.GETAEROSPIKE, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);

		RequestGenerator req = new RequestGenerator(service,headers);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		return req;
	}

	/**
	 * Validates the response of the Aerospike get API.
	 * @param objectID objectId of the pump object which needs to be verified.
	 * @param type If the response needs to validate for the Success, give value as "Success". 
	 * If the response needs to validate for the error,give any string value except Success.Ideally specify "error".
	 * @param attributes Key and the value which needs to be validated. separate the key and value by the delimiter "::".
	 * Specify any number of key value pairs as a String values one after the other string.
	 * @author Suhas.Kashyap
	 */
	public void aerospikeResultValidation(String objectID,String type,String...attributes){
		String[] app_ref_ID = objectID.split(":");
		int app_ref_ID_len = app_ref_ID.length;
		boolean bothValue = true;
		if(app_ref_ID_len <= 1)
			bothValue = false;
		else{
			for(int l=0;l<app_ref_ID_len;l++){
				Pattern p = Pattern.compile("\\s");
				java.util.regex.Matcher m = p.matcher(app_ref_ID[l]);
				if(m.find())
					bothValue = false;
				if(app_ref_ID[l].equals("") ){
					bothValue = false;
				}
			}
		}
		RequestGenerator req = null;
		try{
			req = getAerospikeResponse(app_ref_ID);
		} catch(IllegalArgumentException iAE){
			System.out.println(iAE.getMessage());
			System.out.println(iAE.getCause());
			if(!(objectID.contains("#") || objectID.contains("%") || !bothValue ))
				Assert.assertTrue(false);
			return;
		}
		System.out.println("----AeroSpike Response Starts-----");
		String aerospikeResult = req.respvalidate.returnresponseasstring();
		System.out.println("----AeroSpike Response Ends-----");
		if((objectID.contains("#") || objectID.contains("null") || objectID.contains("%") || !bothValue) ){
			int status = req.response.getStatus();

			if(status == 200 || status == 400){
				Assert.assertTrue(true);
				if(status == 400)
					return;
			}
		}
		else
			Assert.assertEquals(req.response.getStatus(), 200,"Status is not 200");
		if(type.equalsIgnoreCase("success")){
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusCode").toString(), "1043","Status Code in Aerospike result is not matched");
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusMessage").toString(), "Object(s) retrieved successfully"," Status message in Aerospike result is not matched");
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusType").toString().toLowerCase(),"success","Status type in Aerospike result is not success");
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.data..app_object_ref_key").toString().replace("[\"", "").replace("\"]", ""), objectID,"Object Id in Aerospike result doesn't matched");
			for(String attribute : attributes){
				Pattern checkKeyValue = Pattern.compile(".+::.+");
				java.util.regex.Matcher mat = checkKeyValue.matcher(attribute);
				String[] attributeVal = attribute.split("::");
				if(mat.find()){
					Assert.assertEquals(JsonPath.read(aerospikeResult, "$.data.."+attributeVal[0]).toString().toLowerCase().replace("[\"", "").replace("\"]", "").replace("[", "").replace("]", "").replace("\\", ""), attributeVal[1].toLowerCase(),attributeVal[0]+" Aerospike result doesn't matched");
				}
				else{
					Pattern checkKey = Pattern.compile(".+::$");
					java.util.regex.Matcher matKey = checkKey.matcher(attribute);
					if(matKey.find()){
						attributeVal = attribute.split("::");
						String attributeVal1 = "";
						Assert.assertEquals(JsonPath.read(aerospikeResult, "$.data.."+attributeVal[0]).toString().toLowerCase().replace("[\"", "").replace("\"]", "").replace("[", "").replace("]", ""), attributeVal1,attributeVal[0]+" Aerospike result doesn't matched");
					}
				}
			}
		}
		else{
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusCode").toString(), "2012","Status Code in Aerospike result is not matched");
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusMessage").toString(), "Object not found"," Status message in Aerospike result is not matched");
			Assert.assertEquals(JsonPath.read(aerospikeResult, "$.status.statusType").toString().toLowerCase(),"error","Status type in Aerospike result is not success");
			for(String attribute : attributes){
				Pattern checkKeyValue = Pattern.compile(".+::.+");
				java.util.regex.Matcher mat = checkKeyValue.matcher(attribute);
				String[] attributeVal = attribute.split("::");
				if(mat.find())
					Assert.assertNotEquals(JsonPath.read(aerospikeResult, "$.data.."+attributeVal[0]).toString().toLowerCase().replace("[\"", "").replace("\"]", "").replace("[", "").replace("]", ""), attributeVal[1].toLowerCase(),attributeVal[0]+" Aerospike result doesn't matched");
				else{
					Pattern checkKey = Pattern.compile(".+::$");
					java.util.regex.Matcher matKey = checkKey.matcher(attribute);
					if(matKey.find()){
						attributeVal = attribute.split("::");
						String attributeVal1 = "";
						Assert.assertNotEquals(JsonPath.read(aerospikeResult, "$.data.."+attributeVal[0]).toString().toLowerCase().replace("[\"", "").replace("\"]", "").replace("[", "").replace("]", ""), attributeVal1,attributeVal[0]+" Aerospike result doesn't matched");
					}
				}
			}

		}

	}


}
