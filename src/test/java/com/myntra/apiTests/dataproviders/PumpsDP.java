package com.myntra.apiTests.dataproviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.myntra.apiTests.portalservices.lgpservices.LgpPumpsHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;

public class PumpsDP  extends LgpPumpsHelper {
	
	
	public static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PumpsDP.class);
	//public static String envName = init.Configurations.properties.getPropertyValue("environment");
    public String envName = System.getenv("environment");

	public static String spamObject;
	public static String spamNullObject;
	public static String spamSpecialCharObject;
	
	@DataProvider
	public Object[][] createObjectDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
             System.out.println(" envName 11 "+envName);
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1041");
            expectedList1.add("Object added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(" ");
            inputList2.add("testobj_"+getcurrentTimestamp());
            inputList2.add("article");
            inputList2.add("Testing the Create Object API");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("2001");
            expectedList2.add("No AppId passed");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blanks
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    ");
            inputList3.add("testobj_"+getcurrentTimestamp());
            inputList3.add("article");
            inputList3.add("Testing the Create Object API");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2001");
            expectedList3.add("No AppId passed");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*");
            inputList4.add("testobj_"+getcurrentTimestamp());
            inputList4.add("article");
            inputList4.add("Testing the Create Object API");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("400");
            expectedList4.add("2001");
            expectedList4.add("No AppId passed");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            inputList5.add("       2");
            inputList5.add("testobj_"+getcurrentTimestamp());
            inputList5.add("article");
            inputList5.add("Testing the Create Object API");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1041");
            expectedList5.add("Object added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            inputList6.add("2     ");
            inputList6.add("testobj_"+getcurrentTimestamp());
            inputList6.add("article");
            inputList6.add("Testing the Create Object API");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1041");
            expectedList6.add("Object added successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2");
            inputList7.add("");
            inputList7.add("article");
            inputList7.add("Testing the Create Object API");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2004");
            expectedList7.add("Failed to register object with LGP");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2");
            inputList8.add("    ");
            inputList8.add("article");
            inputList8.add("Testing the Create Object API");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2004");
            expectedList8.add("Failed to register object with LGP");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With all object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2");
            inputList9.add("&^$%$#@_"+getcurrentTimestamp());
            inputList9.add("article");
            inputList9.add("Testing the Create Object API");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1041");
            expectedList9.add("Object added successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object ID having special chars not created successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 10 - With all object Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map10 = new HashMap<>();
            ArrayList<String> inputList10 = new ArrayList<>();
            inputList10.add("2");
            inputList10.add("null_"+getcurrentTimestamp());
            inputList10.add("article");
            inputList10.add("Testing the Create Object API");
            
            ArrayList<String> expectedList10 = new ArrayList<>();
            expectedList10.add("200");
            expectedList10.add("1041");
            expectedList10.add("Object added successfully");
            expectedList10.add("SUCCESS");
            expectedList10.add("Object ID having null Not Created successfully");
            map10.put(inputList10,expectedList10);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2");
            inputList11.add("        testobj_"+getcurrentTimestamp());
            inputList11.add("article");
            inputList11.add("Testing the Create Object API");
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("1041");
            expectedList11.add("Object added successfully");
            expectedList11.add("SUCCESS");
            expectedList11.add("Object ID having leading spaces Not Created successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2");
            inputList12.add("testobj_"+getcurrentTimestamp()+"        ");
            inputList12.add("article");
            inputList12.add("Testing the Create Object API");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1041");
            expectedList12.add("Object added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not Created successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null");
            inputList13.add("testobj_"+getcurrentTimestamp());
            inputList13.add("article");
            inputList13.add("Testing the Create Object API");
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("2001");
            expectedList13.add("No AppId passed");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With object Type  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("2");
            inputList14.add("testobj_"+getcurrentTimestamp());
            inputList14.add("");
            inputList14.add("Testing the Create Object API");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("1041");
            expectedList14.add("Object added successfully");
            expectedList14.add("SUCCESS");
            expectedList14.add("Object Type having empty Not Created successfully");
            map14.put(inputList14,expectedList14);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map10},{map11},{map12},{map13},{map14}};
            //dataSet = new Object[][]{{map13}};

        }
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1041");
            expectedList1.add("Object added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(" ");
            inputList2.add("testobj_"+getcurrentTimestamp());
            inputList2.add("article");
            inputList2.add("Testing the Create Object API");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("2001");
            expectedList2.add("No AppId passed");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blanks
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    ");
            inputList3.add("testobj_"+getcurrentTimestamp());
            inputList3.add("article");
            inputList3.add("Testing the Create Object API");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2001");
            expectedList3.add("No AppId passed");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*");
            inputList4.add("testobj_"+getcurrentTimestamp());
            inputList4.add("article");
            inputList4.add("Testing the Create Object API");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("2001");
            expectedList4.add("No AppId passed");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            inputList5.add("       2");
            inputList5.add("testobj_"+getcurrentTimestamp());
            inputList5.add("article");
            inputList5.add("Testing the Create Object API");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1041");
            expectedList5.add("Object added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            inputList6.add("2     ");
            inputList6.add("testobj_"+getcurrentTimestamp());
            inputList6.add("article");
            inputList6.add("Testing the Create Object API");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1041");
            expectedList6.add("Object added successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2");
            inputList7.add("");
            inputList7.add("article");
            inputList7.add("Testing the Create Object API");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2004");
            expectedList7.add("Failed to register object with LGP");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2");
            inputList8.add("    ");
            inputList8.add("article");
            inputList8.add("Testing the Create Object API");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2004");
            expectedList8.add("Failed to register object with LGP");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With all object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2");
            inputList9.add("&^$%$#@_"+getcurrentTimestamp());
            inputList9.add("article");
            inputList9.add("Testing the Create Object API");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1041");
            expectedList9.add("Object added successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object ID having special chars not created successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 10 - With all object Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map10 = new HashMap<>();
            ArrayList<String> inputList10 = new ArrayList<>();
            inputList10.add("2");
            inputList10.add("null_"+getcurrentTimestamp());
            inputList10.add("article");
            inputList10.add("Testing the Create Object API");
            
            ArrayList<String> expectedList10 = new ArrayList<>();
            expectedList10.add("200");
            expectedList10.add("1041");
            expectedList10.add("Object added successfully");
            expectedList10.add("SUCCESS");
            expectedList10.add("Object ID having null Not Created successfully");
            map10.put(inputList10,expectedList10);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2");
            inputList11.add("        testobj_"+getcurrentTimestamp());
            inputList11.add("article");
            inputList11.add("Testing the Create Object API");
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("1041");
            expectedList11.add("Object added successfully");
            expectedList11.add("SUCCESS");
            expectedList11.add("Object ID having leading spaces Not Created successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2");
            inputList12.add("testobj_"+getcurrentTimestamp()+"        ");
            inputList12.add("article");
            inputList12.add("Testing the Create Object API");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1041");
            expectedList12.add("Object added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not Created successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null");
            inputList13.add("testobj_"+getcurrentTimestamp());
            inputList13.add("article");
            inputList13.add("Testing the Create Object API");
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("2001");
            expectedList13.add("No AppId passed");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With object Type  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("2");
            inputList14.add("testobj_"+getcurrentTimestamp());
            inputList14.add("");
            inputList14.add("Testing the Create Object API");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("1041");
            expectedList14.add("Object added successfully");
            expectedList14.add("SUCCESS");
            expectedList14.add("Object Type having empty Not Created successfully");
            map14.put(inputList14,expectedList14);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map10},{map11},{map12},{map13},{map14}};
		}
	
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] createObjectMalformedPayloadDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa")|| envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With TITLE key not being sent as part of payload 
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2004");
            expectedList1.add("Failed to register object with LGP");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
            
			dataSet = new Object[][]{{map1}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With TITLE key not being sent as part of payload 
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2004");
            expectedList1.add("Failed to register object with LGP");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
            
			dataSet = new Object[][]{{map1}};
	
		}
	
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] createObjectWithoutTopicsPayloadDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With Topics key not being sent as part of payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2031");
            expectedList1.add("No Tags passed as part of the object");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With topics key not being sent as part of payload  for video card
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add("2");
            inputList2.add("testobj_"+getcurrentTimestamp());
            inputList2.add("video");
            inputList2.add("Testing the Create Object API");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("2031");
            expectedList2.add("No Tags passed as part of the object");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With Topics key not being sent as part of payload  for product card
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("2");
            inputList3.add("testobj_"+getcurrentTimestamp());
            inputList3.add("product");
            inputList3.add("Testing the Create Object API");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1041");
            expectedList3.add("Object added successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id without topics not registerd successfully");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map2},{map3}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With Topics key not being sent as part of payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2031");
            expectedList1.add("No Tags passed as part of the object");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With topics key not being sent as part of payload  for video card
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add("2");
            inputList2.add("testobj_"+getcurrentTimestamp());
            inputList2.add("video");
            inputList2.add("Testing the Create Object API");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("2031");
            expectedList2.add("No Tags passed as part of the object");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With Topics key not being sent as part of payload  for product card
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("2");
            inputList3.add("testobj_"+getcurrentTimestamp());
            inputList3.add("product");
            inputList3.add("Testing the Create Object API");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1041");
            expectedList3.add("Object added successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id without topics not registerd successfully");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map2},{map3}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] createObjectWithTagsFEFalsePayloadDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With TITLE key not being sent as part of payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2049");
            expectedList1.add("Topics not passed");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
         
			dataSet = new Object[][]{{map1}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With TITLE key not being sent as part of payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            inputList1.add("2");
            inputList1.add("testobj_"+getcurrentTimestamp());
            inputList1.add("article");
            inputList1.add("Testing the Create Object API");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2049");
            expectedList1.add("Topics not passed");
            expectedList1.add("ERROR");
            expectedList1.add("Appropriate Error Code not thrown");
            map1.put(inputList1,expectedList1);
         
			dataSet = new Object[][]{{map1}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] updateObjectImageUrlDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid Image Url being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With Image Url  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Image Url  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With Image Url being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With Image Url being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With Image Url being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With Image Url being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};
		}
		else if(envName.equalsIgnoreCase("production")){
			
			//Scenario 1 - Valid Image Url being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With Image Url  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Image Url  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With Image Url being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With Image Url being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With Image Url being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("http://assets.myntassets.com/assets/images/2015/8/4/11438709917576-file2         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With Image Url being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};

			
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] updateObjectTitleDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid title being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("Testing the Update Object with Title");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With title  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With title  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With title being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With title being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          Testing the Update Object with Title");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With title being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("Testing the Update Object with Title         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With title being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid title being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("Testing the Update Object with Title");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With title  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With title  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With title being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With title being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          Testing the Update Object with Title");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With title being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("Testing the Update Object with Title         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With title being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] updateObjectWithTopicsDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid Topics in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - Valid Topics in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Topics in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList3.add(objectId3);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map2},{map3}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid Topics in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - Valid Topics in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Topics in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList3.add(objectId3);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map2},{map3}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] updateObjectWithTopicsEmptyDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
            //Scenario 4 - With Topics as Empty in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("2031");
            expectedList4.add("No Tags passed as part of the object");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With Topics as Empty in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList5.add(objectId5);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("2031");
            expectedList5.add("No Tags passed as part of the object");
            expectedList5.add("ERROR");
            expectedList5.add("Appropriate Error Code not thrown");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With Topics as Empty in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList6.add(objectId6);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with Topics as empty not updated successfully");
            map6.put(inputList6,expectedList6);
            
         
         
			dataSet = new Object[][]{{map4},{map5},{map6}};
		}
		else if(envName.equalsIgnoreCase("production")){
			 //Scenario 4 - With Topics as Empty in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("2031");
            expectedList4.add("No Tags passed as part of the object");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With Topics as Empty in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList5.add(objectId5);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("2031");
            expectedList5.add("No Tags passed as part of the object");
            expectedList5.add("ERROR");
            expectedList5.add("Appropriate Error Code not thrown");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With Topics as Empty in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList6.add(objectId6);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with Topics as empty not updated successfully");
            map6.put(inputList6,expectedList6);
            
         
         
			dataSet = new Object[][]{{map4},{map5},{map6}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] updateObjectWithTopicsFEFalseDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
          //Scenario 7 - With Topics as Follow Enabled as False in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2049");
            expectedList7.add("Topics not passed");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With Topics as Follow Enabled as False in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            String objectId8=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList8.add(objectId8);
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2049");
            expectedList8.add("Topics not passed");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With Topics as Follow Enabled as False in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            String objectId9=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList9.add(objectId9);
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2049");
            expectedList9.add("Topics not passed");
            expectedList9.add("ERROR");
            expectedList9.add("Appropriate Error Code not thrown");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map7},{map8},{map9}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 7 - With Topics as Follow Enabled as False in payload for article card
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2049");
            expectedList7.add("Topics not passed");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With Topics as Follow Enabled as False in payload for video card
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            String objectId8=createObject("2","testobj_"+getcurrentTimestamp(),"video", "Creating a test Obj");
            inputList8.add(objectId8);
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2049");
            expectedList8.add("Topics not passed");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With Topics as Follow Enabled as False in payload for other cards
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            String objectId9=createObject("2","testobj_"+getcurrentTimestamp(),"product", "Creating a test Obj");
            inputList9.add(objectId9);
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2049");
            expectedList9.add("Topics not passed");
            expectedList9.add("ERROR");
            expectedList9.add("Appropriate Error Code not thrown");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map7},{map8},{map9}};
		}
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] updateObjectTypeDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid Object Type being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("product");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object Type  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Object Type  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object type being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With object Type being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          product");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With object Type being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("product         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With title being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid Object Type being sent as part of payload
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("product");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1044");
            expectedList1.add("Object updated successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object Type  being sent as empty in payload
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1044");
            expectedList2.add("Object updated successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With Object Type  being sent as null in payload
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            String objectId3=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList3.add(objectId3);
            inputList3.add("null");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("1044");
            expectedList3.add("Object updated successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object type being sent as special chars in payload
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            String objectId4=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList4.add(objectId4);
            inputList4.add("@#$%#$&!!@#$");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("1044");
            expectedList4.add("Object updated successfully");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with valid App Id not updated successfully");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With object Type being sent as leading spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add(objectId5);
            inputList5.add("          product");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1044");
            expectedList5.add("Object updated successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Object Id updated with valid App Id not updated successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With object Type being sent as trailing spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(objectId6);
            inputList6.add("product         ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("1044");
            expectedList6.add("Object updated successfully");
            expectedList6.add("SUCCESS");
            expectedList6.add("Object Id updated with valid App Id not updated successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With title being sent as blank spaces in payload
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            String objectId7=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList7.add(objectId7);
            inputList7.add("       ");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("1044");
            expectedList7.add("Object updated successfully");
            expectedList7.add("SUCCESS");
            expectedList7.add("Object Id updated with valid App Id not updated successfully");
            map7.put(inputList7,expectedList7);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] unpublishObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1045");
            expectedList1.add("Object unpublished successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1045");
            expectedList2.add("Object unpublished successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object unpublished successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 8 - With object id sent as already unpublished
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(inputList1.get(0));
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1045");
            expectedList9.add("Object unpublished successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1045");
            expectedList1.add("Object unpublished successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1045");
            expectedList2.add("Object unpublished successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object unpublished successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 8 - With object id sent as already unpublished
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(inputList1.get(0));
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1045");
            expectedList9.add("Object unpublished successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};

		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] markSpamObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            spamObject=objectId;
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1046");
            expectedList1.add("Object spammed successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            spamNullObject=objectId2;
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1046");
            expectedList2.add("Object spammed successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1046");
            expectedList3.add("Object spammed successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            spamSpecialCharObject=objectId4;
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1046");
//            expectedList4.add("Object spammed successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 8 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfljhoasfal");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Object Id updated with valid App Id not updated successfully");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With object id sent as already spammed
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(inputList1.get(0));
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1046");
            expectedList9.add("Object spammed successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            spamObject=objectId;
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1046");
            expectedList1.add("Object spammed successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            spamNullObject=objectId2;
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1046");
            expectedList2.add("Object spammed successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1046");
            expectedList3.add("Object spammed successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            spamSpecialCharObject=objectId4;
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1046");
//            expectedList4.add("Object spammed successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 8 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfljhoasfal");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Object Id updated with valid App Id not updated successfully");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With object id sent as already spammed
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(inputList1.get(0));
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1046");
            expectedList9.add("Object spammed successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] unmarkSpamObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(spamObject);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1047");
            expectedList1.add("Object unspammed successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(spamNullObject);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1047");
            expectedList2.add("Object unspammed successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1047");
            expectedList3.add("Object 7 successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            inputList4.add(spamSpecialCharObject);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1047");
//            expectedList4.add("Object unspammed successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 8 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfljhoasfal");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Object Id updated with valid App Id not updated successfully");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With object id sent as already spammed
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(spamObject);
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1047");
            expectedList9.add("Object unspammed successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(spamObject);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1047");
            expectedList1.add("Object unspammed successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(spamNullObject);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1047");
            expectedList2.add("Object unspammed successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1047");
            expectedList3.add("Object 7 successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            inputList4.add(spamSpecialCharObject);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1047");
//            expectedList4.add("Object unspammed successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 8 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfljhoasfal");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Object Id updated with valid App Id not updated successfully");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With object id sent as already spammed
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add(spamObject);
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("1047");
            expectedList9.add("Object unspammed successfully");
            expectedList9.add("SUCCESS");
            expectedList9.add("Object Id updated with valid App Id not updated successfully");
            map9.put(inputList9,expectedList9);
         
			dataSet = new Object[][]{{map1},{map2},{map3},{map8},{map9}};
		}
		return dataSet;
	}

	@DataProvider
	public Object[][] getObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1043");
            expectedList1.add("Object(s) retrieved successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Id updated not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1043");
            expectedList2.add("Object(s) retrieved successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with null not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object unpublished successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with blank not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map8}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1043");
            expectedList1.add("Object(s) retrieved successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Id updated not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1043");
            expectedList2.add("Object(s) retrieved successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with null not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object unpublished successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with blank not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map8}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] deleteObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1042");
            expectedList1.add("Object deleted successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1042");
            expectedList2.add("Object deleted successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1042");
            expectedList3.add("Object deleted successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map8}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1042");
            expectedList1.add("Object deleted successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Object Id updated with valid App Id not updated successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            String objectId2=createObject("2","null_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList2.add(objectId2);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("1042");
            expectedList2.add("Object deleted successfully");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with valid App Id not updated successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(":");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1042");
            expectedList3.add("Object deleted successfully");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id updated with valid App Id not updated successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
          //Scenario 7 - With object id sent as random
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:asdfkjalndf");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error code not thrown");
            map8.put(inputList8,expectedList8);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map8}};
		}
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] getObjectByIdDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String id=createObjectForGettingID("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            System.out.println(" id obj1 "+id);
            inputList1.add(id);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("56");
            expectedList1.add("Error occurred while retrieving/processing data");
            expectedList1.add("ERROR");
            expectedList1.add("Valid Object Id retrieved successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(null);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("400");
            expectedList2.add("1043");
            expectedList2.add("Object(s) not found");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with null not retrieved successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blanks
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object not found");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id  with blanks not retrieved successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
            
          //Scenario 4 - Valid object id being sent as trailing spaces
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            inputList4.add(id+"   ");
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("200");
//            expectedList4.add("1043");
//            expectedList4.add("Object(s) retrieved successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Valid Object Id retrieved successfully");
//            map4.put(inputList4,expectedList4);
            
            //Scenario 5 - Valid object id being sent as 0
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            inputList5.add("0");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("64");
            expectedList5.add("Invalid id");
            expectedList5.add("ERROR");
            expectedList5.add("Object Id as 0 not retrieved successfully");
            map5.put(inputList5,expectedList5);
            
            
          //Scenario 6 - With object id sent as random id
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            inputList6.add("12352234");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("56");
            expectedList6.add("Error occurred while retrieving/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Appropriate Error code not thrown");
            map6.put(inputList6,expectedList6);


          //Scenario 4 - With object id have * as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("*");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("400");
            expectedList4.add("1043");
            expectedList4.add("Object(s) not found");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with * not retrieved successfully");
            map4.put(inputList4,expectedList4);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6}};
            //dataSet = new Object[][]{{map1}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - Valid object id being sent as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String id=createObjectForGettingID("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(id);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1043");
            expectedList1.add("Object(s) retrieved successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Id retrieved successfully");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With object id have null as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(null);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("400");
            expectedList2.add("1043");
            expectedList2.add("Object(s) not found");
            expectedList2.add("SUCCESS");
            expectedList2.add("Object Id updated with null not retrieved successfully");
            map2.put(inputList2,expectedList2);
            
            //Scenario 3 - With object id as blanks
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("400");
            expectedList3.add("1045");
            expectedList3.add("Object not found");
            expectedList3.add("SUCCESS");
            expectedList3.add("Object Id  with blanks not retrieved successfully");
            map3.put(inputList3,expectedList3);
            
            //Scenario 4 - With object Id having special chars
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            String objectId4=createObject("2","$$%$"+getcurrentTimestamp(),"article", "Creating a test Obj");
//            inputList4.add(objectId4);
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("404");
//            expectedList4.add("1045");
//            expectedList4.add("Object unpublished successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Object Id updated with valid App Id not updated successfully");
//            map4.put(inputList4,expectedList4);
            
            
          //Scenario 4 - Valid object id being sent as trailing spaces
//            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> inputList4 = new ArrayList<>();
//            inputList4.add(id+"   ");
//            
//            ArrayList<String> expectedList4 = new ArrayList<>();
//            expectedList4.add("200");
//            expectedList4.add("1043");
//            expectedList4.add("Object(s) retrieved successfully");
//            expectedList4.add("SUCCESS");
//            expectedList4.add("Valid Object Id retrieved successfully");
//            map4.put(inputList4,expectedList4);
            
            //Scenario 5 - Valid object id being sent as 0
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            inputList5.add("0");
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("64");
            expectedList5.add("Invalid id");
            expectedList5.add("ERROR");
            expectedList5.add("Object Id as 0 not retrieved successfully");
            map5.put(inputList5,expectedList5);
            
            
          //Scenario 6 - With object id sent as random id
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            inputList6.add("12352234");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("2012");
            expectedList6.add("Object not found");
            expectedList6.add("SUCCESS");
            expectedList6.add("Appropriate Error code not thrown");
            map6.put(inputList6,expectedList6);
            
            
          //Scenario 4 - With object id have * as part of input
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("*");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("400");
            expectedList4.add("1043");
            expectedList4.add("Object(s) not found");
            expectedList4.add("SUCCESS");
            expectedList4.add("Object Id updated with * not retrieved successfully");
            map4.put(inputList4,expectedList4);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6}};
		}
		return dataSet;
	}
	
	@DataProvider
	public Object[][] publishActionDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] publishActionDuplicateObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
                      
			dataSet = new Object[][]{{map1}};
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
                      
			dataSet = new Object[][]{{map1}};
		}
	
		return dataSet;
	}
	

	@DataProvider
	public Object[][] publishActionInvalidAppIdURIObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("54");
            expectedList1.add("Error occurred while inserting/processing data"); //changed from 2001
            expectedList1.add("ERROR");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
            
          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("200");
//            expectedList2.add("54");
//            expectedList2.add("Error occurred while inserting/processing data");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2002"); //changed from 2001 error code
            expectedList3.add("App {0} is not registered to publish object(s)");
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map3}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2001");
            expectedList1.add("No AppId passed");
            expectedList1.add("ERROR");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
            
          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("200");
//            expectedList2.add("2001");
//            expectedList2.add("No AppId passed");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2001");
            expectedList3.add("No AppId passed");
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map3}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] publishShareActionDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);

            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2009");
            expectedList9.add("Source vertex not found");
            expectedList9.add("ERROR");
            expectedList9.add("Valid Object Not Created");
            //map1.put(inputList1,expectedList1);

            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            //map1.put(inputList1,expectedList1);
            map1.put(inputList1, expectedList9);
            

            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);

            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList9);

            /*ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("2009");
            expectedList5.add("Source vertex not found");
            expectedList5.add("ERROR");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);*/
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList91 = new ArrayList<>();
            expectedList91.add("200");
            expectedList91.add("2012");
            expectedList91.add("Object not found");
            expectedList91.add("ERROR");
            expectedList91.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList91);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");

            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList9);
            
           /* ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("2009");
            expectedList12.add("Source vertex not found");
            expectedList12.add("ERROR");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);*/
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
//			dataSet = new Object[][]{{map12}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
		}
	
		return dataSet;
		
	}
	
	
	@DataProvider
	public Object[][] publishLikeActionDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("2032");
            expectedList12.add("Edge already present");
            expectedList12.add("ERROR");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			//dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
            //dataSet = new Object[][]{{map12}};
            //dataSet = new Object[][]{{map2},{map3},{map4},{map6},{map7},{map8},{map9},{map11},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
            dataSet = new Object[][]{{map2},{map3},{map4},{map6},{map7},{map8},{map9},{map11},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};


        }
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("2032");
            expectedList12.add("Edge already present");
            expectedList12.add("ERROR");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] likeActionInvalidAppIdURIObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage")  ){
	      
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");

            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("54");//changed from source vertex not found error code
            expectedList11.add("Error occurred while inserting/processing data");
            expectedList11.add("ERROR");
            expectedList11.add("Valid App Id  Not Passed in Input");
            //map1.put(inputList1,expectedList11);
            //System.out.println(" MAP!!! "+map1);

            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2059");//changed from source vertex not found error code
            expectedList1.add("user id is invalid");
            expectedList1.add("ERROR");
            expectedList1.add("Valid App Id  Not Passed in Input");
            map1.put(inputList1,expectedList11);
            System.out.println(" MAP!!! "+map1);


           /* ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("54");//changed from source vertex not found error code
            expectedList1.add("Error occurred while inserting/processing data");
            expectedList1.add("ERROR");
            expectedList1.add("Valid App Id  Not Passed in Input");
            map1.put(inputList1,expectedList11);
            System.out.println(" MAP!!! "+map1);*/



          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("200");
//            expectedList2.add("2059");
//            expectedList2.add("user id is invalid");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");
            
           /* ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data"); //changed from source vertex not found error code
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList3);*/

            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2059");
            expectedList3.add("user id is invalid"); //changed from source vertex not found error code
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList11);
            
			dataSet = new Object[][]{{map1},{map3}};
            //dataSet = new Object[][]{{map3}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2009");
            expectedList1.add("Source vertex not found");
            expectedList1.add("ERROR");
            expectedList1.add("Valid App Id  Not Passed in Input");
            map1.put(inputList1,expectedList1);
            
            
          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("400");
//            expectedList2.add("2001");
//            expectedList2.add("No AppId passed");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2009");
            expectedList3.add("Source vertex not found");
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map3}};

		}

		return dataSet;
	}
	
	@DataProvider
	public Object[][] followContactsDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa")){
			

			String[] uidx1 = {"97105c38.baec.4d63.ada1.bf4f7649f91f7FfTntyvJN","case1"};
			String[] uidx2 = {"97105c38.b649f91f7FfTntyvJN","case2"};
			
			
			dataSet  = new Object[][] {uidx1,uidx2};
		}

		else if(envName.equalsIgnoreCase("stage")){
            String[] uidx1 = {"c183b37f.40fe.4d50.bbb9.13413cc2374cOQkQwB8zox","case1"};
            String[] uidx2 = {"97105c38.b649f91f7FfTntyvJN","case2"};


            dataSet  = new Object[][] {uidx1,uidx2};
        }
		
		else if(envName.equalsIgnoreCase("production")){
			
			String[] uidx1 = {"d6e7671c.37b8.4893.85ea.ad7c3c2dfa1ezrcq9fawWr","case1"};
			String[] uidx2 = {"d6e7671c.37b8.4dfa1ezrcq9fawWr","case2"};
			
			
			dataSet  = new Object[][] {uidx1,uidx2};
			
		}
		
		return dataSet;
	}

    @DataProvider
	public Object[][] followActionDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            //String uidx="a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl";
            String uidx="8f834e80.de59.493e.8f0c.6e5c7ba4037exMxqroPg2Q";


            inputList1.add(uidx);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all uidx as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add("");
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all uidx as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    ");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all uidx as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@@%^*");
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all uidx as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("   "+uidx);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all uidx as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add(uidx+"      ");
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("2032");
            expectedList6.add("Edge already present"); // This scenario will fail as QA env Graph needs to be reset.
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
         
          //Scenario 13 - With  uidx as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null");
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("2059");
            expectedList13.add("user id is invalid");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
                     
            //Scenario 20 - With uidx as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("qrqrqerqr.2345.q325.34636x.254wt");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2059");
            expectedList20.add("user id is invalid");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			//dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map13},{map20}};
            dataSet = new Object[][]{{map2},{map3},{map4}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			
				//Scenario 1 - With all valid data (valid app id and objectId)
	            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> inputList1 = new ArrayList<>();
	            String uidx="a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl";
	            inputList1.add(uidx);
	            
	            ArrayList<String> expectedList1 = new ArrayList<>();
	            expectedList1.add("200");
	            expectedList1.add("1081");
	            expectedList1.add("Action added successfully");
	            expectedList1.add("SUCCESS");
	            expectedList1.add("Valid Object Not Created");
	            map1.put(inputList1,expectedList1);
	            
	          //Scenario 2 - With all uidx as empty
	            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> inputList2 = new ArrayList<>();
	            inputList2.add("");
	            
	            ArrayList<String> expectedList2 = new ArrayList<>();
	            expectedList2.add("200");
	            expectedList2.add("54");
	            expectedList2.add("Error occurred while inserting/processing data");
	            expectedList2.add("ERROR");
	            expectedList2.add("Appropriate Error Code not thrown");
	            map2.put(inputList2,expectedList2);
	            
				
				//Scenario 3 - With all uidx as blank spaces
	            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
	            ArrayList<String> inputList3 = new ArrayList<>();
	            inputList3.add("    ");
	            
	            ArrayList<String> expectedList3 = new ArrayList<>();
	            expectedList3.add("200");
	            expectedList3.add("54");
	            expectedList3.add("Error occurred while inserting/processing data");
	            expectedList3.add("ERROR");
	            expectedList3.add("Appropriate Error Code not thrown");
	            map3.put(inputList3,expectedList3);
	            
	          //Scenario 4 - With all uidx as special chars
	            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
	            ArrayList<String> inputList4 = new ArrayList<>();
	            inputList4.add("@#@#%^&*");
	            
	            ArrayList<String> expectedList4 = new ArrayList<>();
	            expectedList4.add("200");
	            expectedList4.add("2010");
	            expectedList4.add("Target vertex not found");
	            expectedList4.add("ERROR");
	            expectedList4.add("Appropriate Error Code not thrown");
	            map4.put(inputList4,expectedList4);
	            
	          //Scenario 5 - With all uidx as leading spaces
	            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> inputList5 = new ArrayList<>();
	            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
	            inputList5.add("   "+uidx);
	            
	            ArrayList<String> expectedList5 = new ArrayList<>();
	            expectedList5.add("200");
	            expectedList5.add("1081");
	            expectedList5.add("Action added successfully");
	            expectedList5.add("SUCCESS");
	            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
	            map5.put(inputList5,expectedList5);
	            
	          //Scenario 6 - With all uidx as trailing spaces
	            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
	            ArrayList<String> inputList6 = new ArrayList<>();
	            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
	            inputList6.add(uidx+"      ");
	            
	            ArrayList<String> expectedList6 = new ArrayList<>();
	            expectedList6.add("200");
	            expectedList6.add("1081");
	            expectedList6.add("Action added successfully");
	            expectedList6.add("SUCCESS");
	            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
	            map6.put(inputList6,expectedList6);
	            
	         
	          //Scenario 13 - With  uidx as null
	            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
	            ArrayList<String> inputList13 = new ArrayList<>();
	            inputList13.add("null");
	            
	            ArrayList<String> expectedList13 = new ArrayList<>();
	            expectedList13.add("200");
	            expectedList13.add("2010");
	            expectedList13.add("Target vertex not found");
	            expectedList13.add("ERROR");
	            expectedList13.add("Appropriate Error Code not thrown");
	            map13.put(inputList13,expectedList13);
	            
	                     
	            //Scenario 20 - With uidx as invalid id
	            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
	            ArrayList<String> inputList20 = new ArrayList<>();
	            inputList20.add("qrqrqerqr.2345.q325.34636x.254wt");
	            
	            ArrayList<String> expectedList20 = new ArrayList<>();
	            expectedList20.add("200");
	            expectedList20.add("2010");
	            expectedList20.add("Target vertex not found");
	            expectedList20.add("ERROR");
	            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
	            map20.put(inputList20,expectedList20);
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map13},{map20}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] followActionInvalidAppIdURIObjectDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");


            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("54");// changed from 2009(Source vertex not found) as it has been fixed to thrown.
            expectedList5.add("Error occurred while inserting/processing data");
            expectedList5.add("ERROR");
            expectedList5.add("Valid App Id  Not Passed in Input");
            //map1.put(inputList1,expectedList5);

            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2059");// changed from 2009(Source vertex not found) as it has been fixed to thrown.
            expectedList1.add("user id is invalid");
            expectedList1.add("ERROR");
            expectedList1.add("Valid App Id  Not Passed in Input");
            map1.put(inputList1,expectedList5);
            
            
          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("400");
//            expectedList2.add("2001");
//            expectedList2.add("No AppId passed");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");

            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2059");// changed from 2009(Source vertex not found) as it has been fixed to thrown.
            expectedList3.add("user id is invalid");
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList5);
            
			dataSet = new Object[][]{{map1},{map3}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With null App Id in URI
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            inputList1.add("null");
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("2009");
            expectedList1.add("Source vertex not found");
            expectedList1.add("ERROR");
            expectedList1.add("Valid App Id  Not Passed in Input");
            map1.put(inputList1,expectedList1);
            
            
          //Scenario 2 - With blank spaces in URI
//            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> inputList2 = new ArrayList<>();
//            inputList2.add(objectId);
//            inputList2.add("   ");
//            
//            ArrayList<String> expectedList2 = new ArrayList<>();
//            expectedList2.add("400");
//            expectedList2.add("2001");
//            expectedList2.add("No AppId passed");
//            expectedList2.add("ERROR");
//            expectedList2.add("Valid Object Not Created");
//            map2.put(inputList2,expectedList2);
            
          //Scenario 3 - With random in URI
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add(objectId);
            inputList3.add("346346");
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("2009");
            expectedList3.add("Source vertex not found");
            expectedList3.add("ERROR");
            expectedList3.add("Valid Object Not Created");
            map3.put(inputList3,expectedList3);
            
			dataSet = new Object[][]{{map1},{map3}};
	
		}
	
		return dataSet;
	}
	

	/**************************  Pumps App  *******************************/
	@DataProvider
	public Object[][] getActionByIdDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
			
			String[] actionId1 = {"1","case1"};
			String[] actionId2 = {"2","case1"};
			String[] actionId3 = {"3","case1"};
			String[] actionId4 = {"4","case1"};
			String[] actionId5 = {"5","case1"};
			String[] actionId6 = {"6","case1"};
			String[] actionId7 = {"7","case1"};
			String[] actionId8 = {"8","case1"};
			String[] actionId9 = {"9","case1"};
			String[] actionId10 = {"10","case1"};
			
			String[] actionId11 = {"0","case2"};
			
			String[] actionId12 = {"43","case3"};
			
			
			dataSet  = new Object[][] {actionId1,actionId2,actionId3,actionId4,actionId5,actionId6,actionId7,actionId8,
				actionId9,actionId10,actionId11,actionId12};
				}
			else if(envName.equalsIgnoreCase("production")){
				String[] actionId1 = {"1","case1"};
				String[] actionId2 = {"2","case1"};
				String[] actionId3 = {"3","case1"};
				String[] actionId4 = {"4","case1"};
				String[] actionId5 = {"5","case1"};
				String[] actionId6 = {"6","case1"};
				String[] actionId7 = {"7","case1"};
				String[] actionId8 = {"8","case1"};
				String[] actionId9 = {"9","case1"};
				String[] actionId10 = {"10","case1"};
				
				String[] actionId11 = {"0","case2"};
				
				String[] actionId12 = {"43","case3"};
				
				
				dataSet  = new Object[][] {actionId1,actionId2,actionId3,actionId4,actionId5,actionId6,actionId7,actionId8,
					actionId9,actionId10,actionId11,actionId12};
			}
		return dataSet;
	}
	
	/**************************  Pumps User  *******************************/
	
	@DataProvider
	public Object[][] userPrivacyDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") ){
			

			String[] privacyStatus1 = {"0","97105c38.baec.4d63.ada1.bf4f7649f91f7FfTntyvJN","case1"};
			String[] privacyStatus2 = {"1","97105c38.baec.4d63.ada1.bf4f7649f91f7FfTntyvJN","case2"};
			String[] privacyStatus3 = {"1","97105c38.baec7649f91f7FfTntyvJN","case3"};
			
			
			dataSet  = new Object[][] {privacyStatus1,privacyStatus2,privacyStatus3};
		}
		else if(envName.equalsIgnoreCase("stage") ){
            String[] privacyStatus1 = {"0","c183b37f.40fe.4d50.bbb9.13413cc2374cOQkQwB8zox","case1"};
            String[] privacyStatus2 = {"1","c183b37f.40fe.4d50.bbb9.13413cc2374cOQkQwB8zox","case2"};
            String[] privacyStatus3 = {"1","97105c38.baec7649f91f7FfTntyvJN","case3"};


            dataSet  = new Object[][] {privacyStatus1,privacyStatus2,privacyStatus3};
        }
		
		else if(envName.equalsIgnoreCase("production")){
			
			String[] privacyStatus1 = {"0","d6e7671c.37b8.4893.85ea.ad7c3c2dfa1ezrcq9fawWr","case1"};
			String[] privacyStatus2 = {"1","d6e7671c.37b8.4893.85ea.ad7c3c2dfa1ezrcq9fawWr","case2"};
			String[] privacyStatus3 = {"1","d6e7671c.37b8.4dfa1ezrcq9fawWr","case3"};
			
			
			dataSet  = new Object[][] {privacyStatus1,privacyStatus2,privacyStatus3};
			
		}
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] publishclickActionDP() throws IOException{
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("sfqa") || envName.equalsIgnoreCase("stage") ){
	      
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
//			dataSet = new Object[][]{{map12}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
			//Scenario 1 - With all valid data (valid app id and objectId)
            HashMap<ArrayList<String>,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> inputList1 = new ArrayList<>();
            String objectId=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList1.add(objectId);
            
            ArrayList<String> expectedList1 = new ArrayList<>();
            expectedList1.add("200");
            expectedList1.add("1081");
            expectedList1.add("Action added successfully");
            expectedList1.add("SUCCESS");
            expectedList1.add("Valid Object Not Created");
            map1.put(inputList1,expectedList1);
            
          //Scenario 2 - With all app Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> inputList2 = new ArrayList<>();
            inputList2.add(":"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList2 = new ArrayList<>();
            expectedList2.add("200");
            expectedList2.add("54");
            expectedList2.add("Error occurred while inserting/processing data");
            expectedList2.add("ERROR");
            expectedList2.add("Appropriate Error Code not thrown");
            map2.put(inputList2,expectedList2);
            
			
			//Scenario 3 - With all app Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> inputList3 = new ArrayList<>();
            inputList3.add("    :"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList3 = new ArrayList<>();
            expectedList3.add("200");
            expectedList3.add("54");
            expectedList3.add("Error occurred while inserting/processing data");
            expectedList3.add("ERROR");
            expectedList3.add("Appropriate Error Code not thrown");
            map3.put(inputList3,expectedList3);
            
          //Scenario 4 - With all app Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> inputList4 = new ArrayList<>();
            inputList4.add("@#@#%^&*:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList4 = new ArrayList<>();
            expectedList4.add("200");
            expectedList4.add("54");
            expectedList4.add("Error occurred while inserting/processing data");
            expectedList4.add("ERROR");
            expectedList4.add("Appropriate Error Code not thrown");
            map4.put(inputList4,expectedList4);
            
          //Scenario 5 - With all app Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> inputList5 = new ArrayList<>();
            String objectId5=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList5.add("       2:"+objectId5.split(":")[1]);
            
            ArrayList<String> expectedList5 = new ArrayList<>();
            expectedList5.add("200");
            expectedList5.add("1081");
            expectedList5.add("Action added successfully");
            expectedList5.add("SUCCESS");
            expectedList5.add("Leading Spaces App ID comprised Object ID Not Created successfully");
            map5.put(inputList5,expectedList5);
            
          //Scenario 6 - With all app Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> inputList6 = new ArrayList<>();
            String objectId6=createObject("2","testobj_"+getcurrentTimestamp(),"article", "Creating a test Obj");
            inputList6.add("2     :"+objectId6.split(":")[1]);
            
            ArrayList<String> expectedList6 = new ArrayList<>();
            expectedList6.add("200");
            expectedList6.add("54");
            expectedList6.add("Error occurred while inserting/processing data");
            expectedList6.add("ERROR");
            expectedList6.add("Tailing Spaces App ID comprised Object ID Not Created successfully");
            map6.put(inputList6,expectedList6);
            
          //Scenario 7 - With all object Id as empty
            HashMap<ArrayList<String>,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> inputList7 = new ArrayList<>();
            inputList7.add("2:");
            
            ArrayList<String> expectedList7 = new ArrayList<>();
            expectedList7.add("200");
            expectedList7.add("2012");
            expectedList7.add("Object not found");
            expectedList7.add("ERROR");
            expectedList7.add("Appropriate Error Code not thrown");
            map7.put(inputList7,expectedList7);
            
          //Scenario 8 - With all object Id as blank spaces
            HashMap<ArrayList<String>,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> inputList8 = new ArrayList<>();
            inputList8.add("2:"+"    ");
            
            ArrayList<String> expectedList8 = new ArrayList<>();
            expectedList8.add("200");
            expectedList8.add("2012");
            expectedList8.add("Object not found");
            expectedList8.add("ERROR");
            expectedList8.add("Appropriate Error Code not thrown");
            map8.put(inputList8,expectedList8);
            
          //Scenario 9 - With  object Id as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map9 = new HashMap<>();
            ArrayList<String> inputList9 = new ArrayList<>();
            inputList9.add("2:&^$%$#@");
            
            ArrayList<String> expectedList9 = new ArrayList<>();
            expectedList9.add("200");
            expectedList9.add("2012");
            expectedList9.add("Object not found");
            expectedList9.add("ERROR");
            expectedList9.add("Object ID having special chars not published successfully");
            map9.put(inputList9,expectedList9);
            
          //Scenario 11 - With all object Id as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map11 = new HashMap<>();
            ArrayList<String> inputList11 = new ArrayList<>();
            inputList11.add("2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList11 = new ArrayList<>();
            expectedList11.add("200");
            expectedList11.add("2012");
            expectedList11.add("Object not found");
            expectedList11.add("ERROR");
            expectedList11.add("Object ID having leading spaces Not published successfully");
            map11.put(inputList11,expectedList11);
            
            //Scenario 12 - With all object Id as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map12 = new HashMap<>();
            ArrayList<String> inputList12 = new ArrayList<>();
            inputList12.add("2:"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList12 = new ArrayList<>();
            expectedList12.add("200");
            expectedList12.add("1081");
            expectedList12.add("Action added successfully");
            expectedList12.add("SUCCESS");
            expectedList12.add("Object ID having trailing spaces Not published successfully");
            map12.put(inputList12,expectedList12);
            
          //Scenario 13 - With  app Id as null
            HashMap<ArrayList<String>,ArrayList<String>> map13 = new HashMap<>();
            ArrayList<String> inputList13 = new ArrayList<>();
            inputList13.add("null:"+objectId.split(":")[1]);
            
            ArrayList<String> expectedList13 = new ArrayList<>();
            expectedList13.add("200");
            expectedList13.add("54");
            expectedList13.add("Error occurred while inserting/processing data");
            expectedList13.add("ERROR");
            expectedList13.add("Appropriate Error Code not thrown");
            map13.put(inputList13,expectedList13);
            
          //Scenario 14 - With both app id and object Id  as empty
            HashMap<ArrayList<String>,ArrayList<String>> map14 = new HashMap<>();
            ArrayList<String> inputList14 = new ArrayList<>();
            inputList14.add("");
            
            ArrayList<String> expectedList14 = new ArrayList<>();
            expectedList14.add("200");
            expectedList14.add("2058");
            expectedList14.add("Object Id not passed.");
            expectedList14.add("ERROR");
            expectedList14.add("Both App and Object ID having empty Not published successfully");
            map14.put(inputList14,expectedList14);
            
          //Scenario 15 - With both app id and object Id  as blanks spaces
            HashMap<ArrayList<String>,ArrayList<String>> map15 = new HashMap<>();
            ArrayList<String> inputList15 = new ArrayList<>();
            inputList15.add("  ");
            
            ArrayList<String> expectedList15 = new ArrayList<>();
            expectedList15.add("200");
            expectedList15.add("2058");
            expectedList15.add("Object Id not passed.");
            expectedList15.add("ERROR");
            expectedList15.add("Both app ID and Object ID having blank spaces Not published successfully");
            map15.put(inputList15,expectedList15);
            
          //Scenario 16 - With both app id and object Id  as null
            HashMap<ArrayList<String>,ArrayList<String>> map16 = new HashMap<>();
            ArrayList<String> inputList16 = new ArrayList<>();
            inputList16.add("null");
            
            ArrayList<String> expectedList16 = new ArrayList<>();
            expectedList16.add("200");
            expectedList16.add("2016");
            expectedList16.add("Invalid app object reference key");
            expectedList16.add("ERROR");
            expectedList16.add("Both app ID and Object ID having null Not published successfully");
            map16.put(inputList16,expectedList16);
            
            //Scenario 17 - With both app id and object Id  as leading spaces
            HashMap<ArrayList<String>,ArrayList<String>> map17 = new HashMap<>();
            ArrayList<String> inputList17 = new ArrayList<>();
            inputList17.add("        2:      "+objectId.split(":")[1]);
            
            ArrayList<String> expectedList17 = new ArrayList<>();
            expectedList17.add("200");
            expectedList17.add("2012");
            expectedList17.add("Object not found");
            expectedList17.add("ERROR");
            expectedList17.add("Both app ID and Object ID having leading spaces Not published successfully");
            map17.put(inputList17,expectedList17);
            
          //Scenario 18 - With both app id and object Id  as trailing spaces
            HashMap<ArrayList<String>,ArrayList<String>> map18 = new HashMap<>();
            ArrayList<String> inputList18 = new ArrayList<>();
            inputList18.add("2    :"+objectId.split(":")[1]+"     ");
            
            ArrayList<String> expectedList18 = new ArrayList<>();
            expectedList18.add("200");
            expectedList18.add("54");
            expectedList18.add("Error occurred while inserting/processing data");
            expectedList18.add("ERROR");
            expectedList18.add("Both app ID and Object ID having trailing spaces Not published successfully");
            map18.put(inputList18,expectedList18);
            
            
          //Scenario 19 - With both app id and object Id  as special chars
            HashMap<ArrayList<String>,ArrayList<String>> map19 = new HashMap<>();
            ArrayList<String> inputList19 = new ArrayList<>();
            inputList19.add("#$^$%^:#$%#^$6");
            
            ArrayList<String> expectedList19 = new ArrayList<>();
            expectedList19.add("200");
            expectedList19.add("54");
            expectedList19.add("Error occurred while inserting/processing data");
            expectedList19.add("ERROR");
            expectedList19.add("Both app ID and Object ID having special chars Not published successfully");
            map19.put(inputList19,expectedList19);
            
            //Scenario 20 - With both app id and object Id  as invalid id
            HashMap<ArrayList<String>,ArrayList<String>> map20 = new HashMap<>();
            ArrayList<String> inputList20 = new ArrayList<>();
            inputList20.add("334:qrqrqerqr");
            
            ArrayList<String> expectedList20 = new ArrayList<>();
            expectedList20.add("200");
            expectedList20.add("2012");
            expectedList20.add("Object not found");
            expectedList20.add("ERROR");
            expectedList20.add("Both app ID and Object ID having special chars Not published successfully");
            map20.put(inputList20,expectedList20);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5},{map6},{map7},{map8},{map9},{map11},{map12},{map13},{map14},{map15},{map16},{map17},{map18},{map19},{map20}};
		}
	
		return dataSet;
	}
		


}















