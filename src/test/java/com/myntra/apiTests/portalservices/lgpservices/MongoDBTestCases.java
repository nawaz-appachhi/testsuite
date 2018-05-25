package com.myntra.apiTests.portalservices.lgpservices;
import com.mongodb.*;
import com.myntra.apiTests.dataproviders.LgpE2EDP;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.util.*;
public class MongoDBTestCases {
	
	public  List<Map<String,String>> testCases = new ArrayList<Map<String,String>>();
	public List<String> useCase = new ArrayList<String>();
	LgpE2EDP dataProvider = new LgpE2EDP();
	public void connectToDB() {
		
	      try{
	    	  
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient( "54.251.251.162", 27017);
				List<String> payloadList = new ArrayList<String>();
	         // Now connect to your databases
	         DB db = mongoClient.getDB( "LGPDatabase" );
	         System.out.println("Connect to database successfully");
	         //boolean auth = db.authenticate(myUserName, myPassword);
	         //System.out.println("Authentication: "+auth);
				
	         //DBCollection coll = db.createCollection("TestCaseEntries", null);
	         DBCollection coll = db.createCollection("EndToEndUseCases", null);
	         
	         System.out.println("Collection created successfully");
	         
	         DBCursor cursor = coll.find();
	         int i = 1;
				
	         while (cursor.hasNext()) { 
	        	 
	        	 DBObject Features = cursor.next();
	        	 useCase.add(Features.get("ScenarioType").toString());
	                payloadList.add(Features.get("Payload").toString());

	            System.out.println("Inserted Document: "+Features.get("Payload").toString()); 
	            jsonToMap(Features.toString());
	            i++;
	         }
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	   }

	 public void jsonToMap(String t) throws JSONException {
	        HashMap<String, String> map = new HashMap<String, String>();
	        JSONObject jObject = new JSONObject(t);
	        Iterator<?> keys = jObject.keys();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            String value = jObject.getString(key); 
	            map.put(key, value);
	            

	        }
	        testCases.add(map);
	        System.out.println("map : "+map);
	    }
	 /*
	 @DataProvider
	  public Object[][] endToEndTestCases() {
	    connectToDB();
	    Object[][] dataSet = new Object[testCases.size()][1];
	    for(int i = 0;i<testCases.size();i++)
	    {
	      Object[] obj = new Object[1];
	      obj[0] = testCases.get(i);
	      dataSet[i]=  obj;
	      System.out.println("dataset is"+dataSet[i][0]);
	    }
	    return dataSet;
	  }*/
	 
	  @DataProvider
	  public Object[][] endToEndTestCases() {
	    connectToDB();
	    int totalTestCases=0;
	    ArrayList<HashMap<String,HashMap<String,String>>> testcaseList= new ArrayList();
	    for(int i=0;i<testCases.size();i++){
	    	testcaseList = dataProvider.dataProviders(useCase.get(i));
	    	totalTestCases=testcaseList.size();
	    }
	    Object[][] dataSet = new Object[totalTestCases][2];
	    for(int i=0;i<testCases.size();i++){
	    	
		    testcaseList = dataProvider.dataProviders(useCase.get(i));
		    System.out.println("List is"+testcaseList);
		    
		    for(int j = 0;j<testcaseList.size();j++){
		    	
		    	dataSet[j][0] = testCases.get(i);
		    	dataSet[j][1]= testcaseList.get(j);
		    }
		    System.out.println("dataset is"+dataSet);
	    }
	    
	    
	    /*for(int i = 0;i<testCases.size();i++)
	    {
	      Object[] obj = new Object[1];
	      obj[0] = testCases.get(i);
	      dataSet[i]=  obj;
	      System.out.println("dataset is"+dataSet[i][0]);
	    }*/
	    
	    return dataSet;
	  }
	  
	  
}
