package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.DataProvider;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.RethinkDBConnection;
import com.rethinkdb.ast.query.gen.Get;

public class LgpEndToEndDP {
	

	public  RethinkDB r = RethinkDB.r;
	  public  List<Map<String,String>> testCases = new ArrayList<Map<String,String>>();
	  
	  //calling the jsonToMap method to generate the arrayList.
	 // @Before
	  public void connectToDB(){
	  
	  RethinkDB r = RethinkDB.r;
	  RethinkDBConnection con = r.connect("bladerunner.myntra.com", 28015);
	  con.use("LGPDatabase");
	  try
	  { 
		 /* System.out.println(r.table("TestCaseEntries").run(con).size());
		  int i = 0;
		  while(r.table("TestCaseEntries").run(con).iterator().hasNext())
		  {
			  System.out.println(r.table("TestCaseEntries").run(con).get(i));
			  String s = r.table("TestCaseEntries").run(con).get(i).toString();
			  jsonToMap(s);
			  i++;
		  }*/Get ss = r.table("TestCaseEntries").get("id");
		  System.out.println("the 1 payload is"+ss);
		  Thread.sleep(10000);
	  List<Map<String,Object>> cursor = r.table("TestCaseEntries").run(con);
	  Thread.sleep(10000);
	  for (Object doc : cursor) {
	      String s = doc.toString();
	      jsonToMap(s);
	  }
	  }
	  catch(Exception e)
	  {
	   System.out.println("Failed to fetch the Rows from DB"+e);  
	  } 
	}
	  //Method converting the json from db into a map and storing it into the arraylist of maps
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
	  //Converting the arraylist of maps into 2d object and returning all the testcases in object form. This will be the dataProvider
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
	  }

}
