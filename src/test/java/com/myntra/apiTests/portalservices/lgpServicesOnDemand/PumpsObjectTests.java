package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.dataproviders.PumpsDP;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.datastax.driver.core.Session;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.lgpservices.CassandraConnectHelper;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class PumpsObjectTests extends PumpsDP {
 
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PumpsObjectTests.class);
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static HashMap<String, String> headers = new HashMap<String, String>();
	static APIUtilities utilities = new APIUtilities();
	Session cassandraSession=null;
	//public static String environment = init.Configurations.GetTestEnvironemnt().toString();
	String environment = System.getenv("environment");

//	Test Cases Reference Link : https://docs.google.com/spreadsheets/d/1ZlKwMpRBlnpB42Vu1teKs6pRqZQdqA-jIO6C1eldrD4/edit#gid=2008242439

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		
		/*try {
			if (environment.toLowerCase().contains("stage"))
				cassandraSession = CassandraConnectHelper.getSession("lgpcassandra.stage.myntra.com", 9042);

			else if (environment.toLowerCase().contains("fox7"))
			 cassandraSession = CassandraConnectHelper.getSession("qa_lg2", 9042);

			else if (environment.toLowerCase().contains("sfqa"))
				cassandraSession = CassandraConnectHelper.getSession("lgpcassandra.sfqa.myntra.com", 9042);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String xid = null;
		if (environment.toLowerCase().contains("fox7") || environment.toLowerCase().contains("stage") || environment.toLowerCase().contains("sfqa")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
		} else if (environment.toLowerCase().contains("production")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
	     }
	}

	@Test(dataProvider = "createObjectDP", groups = "Sanity")
	public void createObjectTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObject");
		
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim()+":"+values.keySet().toArray()[0].toString().split(",")[1].trim();
		
		payload=utilities.preparepayload(payload,new String[] {values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim(),values.keySet().toArray()[0].toString().split(",")[1].trim(),values.keySet().toArray()[0].toString().split(",")[2].trim(),values.keySet().toArray()[0].toString().split(",")[3].replace("]","").trim()});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		
		if(requestGen.response.getStatus() != 200 ){
			
			Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
			
		}else{
			
			Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1).toString()),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
		
		if (expectedValues.get(1).equals("1041")) {
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "ObjectID needs to registered");
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
			
			/* Commenting Cassandra Validation as we migrated to Aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"app_object_ref_key",objectIdRetrieved,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			
			aerospikeResultValidation(objectIdRetrieved,"success");
		}
		else
			aerospikeResultValidation(objectIdRetrieved,"error");

	}
	
	@Test(dataProvider = "createObjectMalformedPayloadDP",groups = "Sanity")
	public void createObjectWithoutTitleKeyTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObjectWithoutTitle");
		
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim()+":"+values.keySet().toArray()[0].toString().split(",")[1].trim();
		
		payload=utilities.preparepayload(payload,new String[] {values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim(),values.keySet().toArray()[0].toString().split(",")[1].trim(),values.keySet().toArray()[0].toString().split(",")[2].trim(),values.keySet().toArray()[0].toString().split(",")[3].replace("]","").trim()});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		
		if (expectedValues.get(1).equals("1041")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "ObjectID needs to registered");
			/*String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"app_object_ref_key",objectIdRetrieved,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");*/
			aerospikeResultValidation(objectIdRetrieved,"success");
		}
		else
			aerospikeResultValidation(objectIdRetrieved,"error");
	}
	
	
	@Test(dataProvider = "createObjectMalformedPayloadDP",groups = "Sanity")
	public void createObjectWithoutImageUrlKeyTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObjectWithoutImageUrl");
		
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim()+":"+values.keySet().toArray()[0].toString().split(",")[1].trim();
		
		payload=utilities.preparepayload(payload,new String[] {values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim(),values.keySet().toArray()[0].toString().split(",")[1].trim(),values.keySet().toArray()[0].toString().split(",")[2].trim(),values.keySet().toArray()[0].toString().split(",")[3].replace("]","").trim()});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		
		if (expectedValues.get(1).equals("1041")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "ObjectID needs to registered");
			/*String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"app_object_ref_key",objectIdRetrieved,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");*/
			aerospikeResultValidation(objectIdRetrieved,"success");
		}
		else
			aerospikeResultValidation(objectIdRetrieved, "error");
	}
	
	@Test(dataProvider = "createObjectWithoutTopicsPayloadDP",groups = "Sanity")
	public void createObjectWithoutTopicsKeyTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObjectWithoutTopics");
		payload=utilities.preparepayload(payload,new String[] {values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim(),values.keySet().toArray()[0].toString().split(",")[1].trim(),values.keySet().toArray()[0].toString().split(",")[2].trim(),values.keySet().toArray()[0].toString().split(",")[3].replace("]","").trim()});
	
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim()+":"+values.keySet().toArray()[0].toString().split(",")[1].trim();
		
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		
		if (expectedValues.get(1).equals("1041")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "ObjectID needs to registered");
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
			/* Commenting the Cassandra Validation as we have Migrated to Aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"app_object_ref_key",objectIdRetrieved,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			aerospikeResultValidation(objectIdRetrieved,"success");
		}
		else
			aerospikeResultValidation(objectIdRetrieved,"error");
	}
	
	@Test(dataProvider = "createObjectWithTagsFEFalsePayloadDP",groups = "Sanity")
	public void createObjectWithTagsFEFalseTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/createObjectWithTagsFollowEnabledFalse");
		payload=utilities.preparepayload(payload,new String[] {values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim(),values.keySet().toArray()[0].toString().split(",")[1].trim(),values.keySet().toArray()[0].toString().split(",")[2].trim(),values.keySet().toArray()[0].toString().split(",")[3].replace("]","").trim()});
	
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").trim()+":"+values.keySet().toArray()[0].toString().split(",")[1].trim();
		
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] { values.keySet().toArray()[0].toString() });
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1041")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].refId"), "ObjectID needs to registered");
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
			/* Commenting Cassandra Validation as we migrated to Aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"app_object_ref_key",objectIdRetrieved,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			aerospikeResultValidation(objectIdRetrieved,"success");
		}
		else
			aerospikeResultValidation(objectIdRetrieved,"error");
	}
	
	@Test(dataProvider = "updateObjectImageUrlDP",groups = "Sanity")
	public void updateObjectImageUrlTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectImageUrl");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("[","").trim();
		String imageUrlFromDP = inputValues[1].replace("]", "").trim();
		
		payload=utilities.preparepayload(payload,new String[] {inputValues[1].replace("]","").trim()});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1044")) {
			
			Assert.assertEquals(JsonPath.read(response, "$.data[0].imageUrl"),inputValues[1].replace("]","").trim(), "Image Url have not updated successfully");
			String completeObjectId = appId+":"+objectId ;
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+completeObjectId+"';";
			/* Commenting the Cassandra Validation as we have migrated to Aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"image_url",imageUrlFromDP,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			aerospikeResultValidation(appId+":"+objectId,"success","image_url::"+imageUrlFromDP);
		}
		else
			aerospikeResultValidation(appId+":"+objectId,"error","image_url::"+imageUrlFromDP);
	}
	
	@Test(dataProvider = "updateObjectTitleDP",groups = "Sanity")
	public void updateObjectTitleTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectTitle");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("[","").trim();
		
		String titleFromDP= inputValues[1].replace("]", "").trim();
		
		payload=utilities.preparepayload(payload,new String[] {inputValues[1].replace("]","").trim()});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1044")) {
			
			Assert.assertEquals(JsonPath.read(response, "$.data[0].title"),inputValues[1].replace("]","").trim(), "Title have not updated successfully");
			String completeObjectId = appId+":"+objectId ;
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+completeObjectId+"';";
			/* Commenting the Cassandra Validation as we have migrated to aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"title",titleFromDP,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			aerospikeResultValidation(appId+":"+objectId,"success","title::"+titleFromDP);
		}
		else
			aerospikeResultValidation(appId+":"+objectId,"error","title::"+titleFromDP);
	}
	
	@Test(dataProvider = "updateObjectWithTopicsDP",groups = "Sanity")
	public void updateObjectTopicsDP(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectWithTopics");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("]","").trim();
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	}
	
	@Test(dataProvider = "updateObjectWithTopicsEmptyDP",groups = "Sanity")
	public void updateObjectTopicsEmptyDP(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectWithTopicsEmpty");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("]","").trim();
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	}
	
	@Test(dataProvider = "updateObjectWithTopicsFEFalseDP",groups = "Sanity")
	public void updateObjectWithTopicsFEFalseTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectWithTopicsFollowEnabledFalse");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("]","").trim();
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	}
	
	@Test(dataProvider = "updateObjectTypeDP",groups = "Sanity")
	public void updateObjectTitleTypeTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/updateObjectType");
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","").trim();
		String objectId=inputValues[0].split(":")[1].replace("[","").trim();
		payload=utilities.preparepayload(payload,new String[] {inputValues[1].replace("]","").trim()});
		
		String objectTypeFromDP= inputValues[1].replace("]", "").trim();
		
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UPDATEOBJECT,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1044")) {
			
			Assert.assertEquals(JsonPath.read(response, "$.data[0].type"),inputValues[1].replace("]","").trim(), "Type have not updated successfully");
			String completeObjectId = appId+":"+objectId ;
			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+completeObjectId+"';";
			
			/* Commenting Cassandra Validation as we migrated to Aerospike
			boolean cassandraValidationResult  = cassandraRequestValidation(queryObjectRequest,"type",objectTypeFromDP,cassandraSession);
			Assert.assertEquals(cassandraValidationResult, true, "Cassandra Validation Failed");
			*/
			aerospikeResultValidation(appId+":"+objectId,"success","type::"+objectTypeFromDP);
		}
	}
	
	@Test(dataProvider = "unpublishObjectDP",groups = "Sanity")
	public void unpublishObjectTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","");
		String objectId=inputValues[0].split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UNPUBLISHOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
		if(expectedValues.get(1).equals("1045"))
			aerospikeResultValidation(appId+":"+objectId,"success","enabled::false");
		else
			if(expectedValues.get(2).equalsIgnoreCase("Object not found"))
				aerospikeResultValidation(appId+":"+objectId,"error");
				else
			aerospikeResultValidation(appId+":"+objectId,"error","enabled::true");
	}
	
	@Test(dataProvider = "markSpamObjectDP",groups = "Sanity")
	public void markspamObjectTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","");
		String objectId=inputValues[0].split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.SPAMOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
		if(expectedValues.get(1).equals("1046"))
			aerospikeResultValidation(appId+":"+objectId,"success","is_spam::true");
		else
			if(expectedValues.get(2).equalsIgnoreCase("Object not found"))
			aerospikeResultValidation(appId+":"+objectId,"error");
			else
			aerospikeResultValidation(appId+":"+objectId,"error","is_spam::false");

	}

	@Test(dataProvider = "unmarkSpamObjectDP",groups = "Sanity")
	public void unmarkspamObjectTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","");
		String objectId=inputValues[0].split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.UNSPAMOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
		if(expectedValues.get(1).equals("1047"))
			aerospikeResultValidation(appId+":"+objectId,"success","is_spam::false");
		else
			if(expectedValues.get(2).equalsIgnoreCase("Object not found"))
				aerospikeResultValidation(appId+":"+objectId,"error");
				else
			aerospikeResultValidation(appId+":"+objectId,"error","is_spam::true");
	}
	
	@Test(dataProvider = "getObjectDP",groups = "Sanity")
	public void getObjectTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","");
		String objectId=inputValues[0].split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.GETPUMPSOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
		if(expectedValues.get(1).equals("1043"))
			aerospikeResultValidation(appId+":"+objectId,"success");
		else
			aerospikeResultValidation(appId+":"+objectId,"error");
	}
	
	@Test(dataProvider = "deleteObjectDP",groups = "Sanity")
	public void deleteObjectTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String appId=inputValues[0].split(":")[0].replace("[","");
		String objectId=inputValues[0].split(":")[1].replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.DELETEPUMPSOBJECT,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {appId,objectId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring(); 
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
			aerospikeResultValidation(appId+":"+objectId,"error");
		}
		
	}
	
	@Test(dataProvider = "getObjectByIdDP",groups = "Sanity")
	public void getObjectByIdTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String[] inputValues = values.keySet().toArray()[0].toString().split(",");
		String id=inputValues[0].replace("[","").replace("]","");
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.GETPUMPSOBJECTBYID,init.Configurations,"");
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {id});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		if(requestGen.response.getStatus()==200)
		{
			Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		}
	}

	
	@AfterClass
	public void deleteAllObjectIds()
	{
		for(int i=0;i<objectIdsToBeDeleted.size();i++)
		{
			deleteObjectId(objectIdsToBeDeleted.get(i));
		}
	}
}
