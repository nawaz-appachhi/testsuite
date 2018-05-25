package com.myntra.apiTests.portalservices.lgpservices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.RethinkDBConnection;
public class EndToEndDataReader {
	
	public static RethinkDB r = RethinkDB.r;
	  public static List<Map<String,String>> TestCases = new ArrayList<Map<String,String>>();
	  
	  //calling the jsonToMap method to generate the arrayList.
	  @Before
	  public static void ConnectRethinkDb(String[] args){
	  
	  RethinkDB r = RethinkDB.r;
	  RethinkDBConnection con = r.connect("bladerunner.myntra.com", 28015);
	  con.use("LGPDatabase");
	  try
	  { 
		  String ss = r.table("TestCaseEntries").run(con).get(0).get("Payload").toString();
		  System.out.println("the 1 payload is"+ss);
	  List cursor = r.table("TestCaseEntries").run(con);
	  for (Object doc : cursor) {
	      String s = doc.toString();
	      jsonToMap(s);
	  }
	  }
	  catch(Exception e)
	  {
	   System.out.println("Failed to fetch the Rows from DB");  
	   con.close();
	  } 
	 con.close();
	  //endToEndTestCases();
	}
	  //Method converting the json from db into a map and storing it into the arraylist of maps
	  public static void jsonToMap(String t) throws JSONException {
	        HashMap<String, String> map = new HashMap<String, String>();
	        JSONObject jObject = new JSONObject(t);
	        Iterator<?> keys = jObject.keys();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            String value = jObject.getString(key); 
	            map.put(key, value);
	            

	        }
	        TestCases.add(map);
	        System.out.println("map : "+map);
	    }
	  //Converting the arraylist of maps into 2d object and returning all the testcases in object form. This will be the dataProvider
	  //@dataProvider
	  public static Object[][] endToEndTestCases() {
	    
	    Object[][] dataSet = new Object[TestCases.size()][0];
	    for(int i = 0;i<TestCases.size();i++)
	    {
	      Object[] obj = new Object[1];
	      obj[0] = TestCases.get(i);
	      dataSet[i]=  obj;
	      System.out.println("dataset is"+dataSet[i][0]);
	    }
	    return dataSet;
	    
	  }

}
