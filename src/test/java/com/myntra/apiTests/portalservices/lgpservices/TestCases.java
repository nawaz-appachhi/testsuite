package com.myntra.apiTests.portalservices.lgpservices;

import com.myntra.apiTests.dataproviders.LgpE2EDP;
import com.myntra.apiTests.portalservices.lgpServicesOnDemand.LgpEndToEnd;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TestCases extends LgpE2EDP{
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(LgpEndToEnd.class);
	EndToEndHelper helper = new EndToEndHelper();
	
	static APIUtilities apiUtil = new APIUtilities();
	@Test (groups = {"Regression"}, dataProvider = "pumpsAction")
  public void test(HashMap<String, HashMap<String,String>> testData) throws JSONException, IOException, ParseException {
	  EndToEndRecorder record = new EndToEndRecorder();
	  //String jsonData = (String)testData.get("payload");
	  String jsonData = readFile("./Data/payloads/JSON/lgp/endToEnd/testData", StandardCharsets.UTF_8);
	  //System.out.println("Data is /n"+jsonData);
	  JSONArray testDataPayload = new JSONObject(jsonData).getJSONArray("testPayload");
	  ArrayList<String> failures = new ArrayList<>();
	  User user= new User();
	  Pumps pumps = new Pumps();
	  preparePayload jsonPayload=new preparePayload();
	  DataBaseAssertion dbAssertion = new DataBaseAssertion();
	  ServeAssertion serveAssertion = new ServeAssertion();
	  int i=0;
	  while(i<testDataPayload.length()){
		  JSONObject actionPayload = testDataPayload.getJSONObject(i);
		  String mainActionKey = (String) actionPayload.keys().next();
		  switch(mainActionKey){
		  case "user":
			  user.performActions(record,failures, actionPayload.getString("user"));
			  System.out.println("It came to users");
			  break;
		  case "pumps":
			  JSONObject jObject = new JSONObject(actionPayload.getString("pumps"));
			  JSONArray pumpsActionsArray=null;
			  pumpsActionsArray=jObject.getJSONArray("pumpsActions");
			  System.out.println("array is "+pumpsActionsArray);
			  pumps.performActions(record,failures,pumpsActionsArray,testData);
			  System.out.println("It came to pumps");
			  break;
		  case "dataBaseAssertions":
			  dbAssertion.performActions(record,failures, actionPayload.getString("dataBaseAssertions"));
			  System.out.println("It came to DBassertions");
			  break;
		  case "serveAssertions":
			  try {
					Thread.sleep(9000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONArray jsonA = new JSONArray();
				jsonA = actionPayload.getJSONArray("serveAssertions");
				serveAssertion.performActions(record,failures, jsonA.toString(),testData);
				
			  //serveAssertion.performActions(record,failures, actionPayload.getString("serveAssertions"));
			  System.out.println("It came to serveassertions");
			  break;
		  }
		  i++;
	  }
	  if(failures.size()>0){
			Assert.fail(helper.getFailuresPoints(failures));
		}
	  
  }
  static String readFile(String path, Charset encoding) 
		  throws IOException 
		{
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return new String(encoded, encoding);
		}
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }


}
