package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.PumpsDP;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class PumpsActionTests extends PumpsDP{

	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PumpsActionTests.class);
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static HashMap<String, String> headers = new HashMap<String, String>();
	static APIUtilities utilities = new APIUtilities();
	static String userId;

//	Test Cases Reference Link : https://docs.google.com/spreadsheets/d/1ZlKwMpRBlnpB42Vu1teKs6pRqZQdqA-jIO6C1eldrD4/edit#gid=2008242439
	
	@BeforeClass(alwaysRun = true)
	public void setUp() {
		String xid = null;
		if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			userId="970794db.228e.4133.933e.1574198ef08frA7cPl428x";
		} else if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("production")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			userId="a116dc5e.43c0.47a0.accf.a281e6fa0866bz4yszA2RX";//email:"iosapp11@myntra.com",pwd="qwerty"
	     } else if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("stage")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			userId="c183b37f.40fe.4d50.bbb9.13413cc2374cOQkQwB8zox";
		} else if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("sfqa")) {
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			userId="c183b37f.40fe.4d50.bbb9.13413cc2374cOQkQwB8zox";
		}
	}

	@Test(dataProvider = "publishActionDP", groups = "Sanity")
	public void publishActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishAction");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {"2"});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}

	}
	
	@Test(dataProvider = "publishActionDuplicateObjectDP", groups = "Sanity")
	public void publishActionDuplicateObjectTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishAction");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {"2"});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		String actualId=JsonPath.read(response, "$.data[0].id");
		
		// Trying to hit the api with the same object id
		MyntraService feedservice1 = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice1.URL = utilities.prepareparameterizedURL(feedservice1.URL,new String[] {"2"});
		System.out.println(feedservice1.URL);
		RequestGenerator requestGen1 = new RequestGenerator(feedservice1, headers);
		String response1 = requestGen1.respvalidate.returnresponseasstring();
		String expectedId=JsonPath.read(response1, "$.data[0].id");
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(actualId, expectedId,"The same id is not responded back when publishing the same object within one minute");

	}
	
	
	@Test(dataProvider = "publishActionDuplicateObjectDP", groups = "Sanity")
	public void publishActionDuplicateObjectMorethanOneMinuteTest(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException, InterruptedException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishAction");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {"2"});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		String actualId=JsonPath.read(response, "$.data[0].id");
		Thread.sleep(65000);
		// Trying to hit the api with the same object id
		MyntraService feedservice1 = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice1.URL = utilities.prepareparameterizedURL(feedservice1.URL,new String[] {"2"});
		System.out.println(feedservice1.URL);
		RequestGenerator requestGen1 = new RequestGenerator(feedservice1, headers);
		String response1 = requestGen1.respvalidate.returnresponseasstring();
		String expectedId=JsonPath.read(response1, "$.data[0].id");
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertNotEquals(actualId, expectedId,"The same id is not responded back when publishing the same object within one minute");

	}
	
	@Test(dataProvider = "publishActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void publishActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishAction");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTION,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
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
	
	@Test(dataProvider = "publishLikeActionDP", groups = "Sanity")
	public void likeActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONLIKE,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {userId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}
	}
		
	@Test(dataProvider = "likeActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void likeActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONLIKE,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
        if (requestGen.response.getStatus()==200) {
		 Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		 Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		 Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
        }

	}
	
	
	@Test(dataProvider = "publishShareActionDP", groups = "Sanity")
	public void shareActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONSHARE,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {userId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}
	}
		
	@Test(dataProvider = "likeActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void shareActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONSHARE,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		 if (requestGen.response.getStatus()==200) {
			 Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	        }

	}

	
	@Test(dataProvider = "publishclickActionDP", groups = "Sanity")
	public void clickActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONCLICK,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {userId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}
	}
		
	@Test(dataProvider = "likeActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void clickActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONCLICK,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		 if (requestGen.response.getStatus()==200) {
			 Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	        }

	}
	
	
	@Test(dataProvider = "followActionDP", groups = "Sanity")
	public void followActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionFollow");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONFOLLOW,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {userId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}
	}
		
	@Test(dataProvider = "followActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void followActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionFollow");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONFOLLOW,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		 if (requestGen.response.getStatus()==200) {
			 Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
			 Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
	        }

	}
	
	@Test(dataProvider = "publishLikeActionDP", groups = "Sanity")
	public void objectFollowActionTests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONOBJECTFOLLOW,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {userId});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		Assert.assertEquals(Integer.parseInt(JsonPath.read(response, "$.status.statusCode").toString()), Integer.parseInt(expectedValues.get(1)),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusMessage"), expectedValues.get(2),expectedValues.get(4));
		Assert.assertEquals(JsonPath.read(response, "$.status.statusType"), expectedValues.get(3),expectedValues.get(4));
		if (expectedValues.get(1).equals("1081")) {
			
			Assert.assertNotNull(JsonPath.read(response, "$.data[0].id"), "ObjectID needs to registered");
//			String queryObjectRequest = "select * from lgp.object_entity where app_object_ref_key ='"+objectIdRetrieved+"';";
//			String cassandraValidationResult  = cassandraRequest(queryObjectRequest,"-",objectIdRetrieved);
//			Assert.assertEquals(cassandraValidationResult, "true", "Cassandra Validation Failed");
		}
	}
		
	@Test(dataProvider = "followActionInvalidAppIdURIObjectDP", groups = "Sanity")
	public void objectFollowActionInvalidAppIdURITests(HashMap<ArrayList<String>, ArrayList<String>> values) throws IOException {
		
		System.out.println(values);
		String payload=utilities.readFileAsString("./Data/payloads/JSON/lgp-pumps/publishActionLikeClickShare");
		String objectIdRetrieved = values.keySet().toArray()[0].toString().split(",")[0].replace("[","").replace("]", "");
		payload=utilities.preparepayload(payload,new String[] {objectIdRetrieved});
		MyntraService feedservice = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONOBJECTFOLLOW,init.Configurations,payload);
		feedservice.URL = utilities.prepareparameterizedURL(feedservice.URL,new String[] {values.keySet().toArray()[0].toString().split(",")[1].replace("[","").replace("]", "").trim()});
		System.out.println(feedservice.URL);
		RequestGenerator requestGen = new RequestGenerator(feedservice, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		
		ArrayList<String> expectedValues = values.get(values.keySet().toArray()[0]);
		Assert.assertEquals(requestGen.response.getStatus(), Integer.parseInt(expectedValues.get(0)),expectedValues.get(4));
		 if (requestGen.response.getStatus()==200) {
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
