package com.myntra.apiTests.testdevelopmentrnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;

import com.myntra.apiTests.common.Myntra;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class eors {

	static Initialize init = new Initialize("/Data/configuration");
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	SpreadsheetService service = new SpreadsheetService("tmp");
  	String ApplicationId = null;
	String RestAPIKey = null;
	String MasterKey = null;
	String ObjectIdAndroid = null;
	String ObjectIdIos = null;
	String ObjectIdWP = null;
	List<Integer> fgIndex = new ArrayList<Integer>();
	List<Integer> kvIndex = new ArrayList<Integer>();
	LinkedHashMap<String,String> mapping_FG = new LinkedHashMap<String,String>();
	LinkedHashMap<String,String> mapping_KV = new LinkedHashMap<String,String>();
	String key_FG = null;
	String value_FG = null;
	String key_KV = null;
	String value_KV = null;
	String type = null;
	List<Integer> andIndex = new ArrayList<Integer>();
	List<Integer> iosIndex = new ArrayList<Integer>();
	List<Integer> wpIndex = new ArrayList<Integer>();  
	LinkedHashMap<String,String> mapping_and = new LinkedHashMap<String,String>();
	LinkedHashMap<String,String> mapping_ios = new LinkedHashMap<String,String>();
	LinkedHashMap<String,String> mapping_wp = new LinkedHashMap<String,String>();
	String key_and = null;
	String value_and = null;
	String key_ios = null;
	String value_ios = null;
	String key_wp = null;
	String value_wp = null;
//	String filePath = "/root/EORS/My Project-1026ab85224b.p12";
//	String filePath = "/Users/mohit.jain/Downloads/My Project-1026ab85224b.p12";
	String filePath = "/root/Desktop/JulyEORS/My Project-1026ab85224b.p12";

	public eors() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
		
		if (Env.toLowerCase().contains("production"))
		{
			ApplicationId = "KEzwVygKFjZJLmjh4S4zwYAw4CVgh5nbdWZTmj6G";
			 RestAPIKey = "380Tt0VcF1INumz6jZNuqq4cKOtDcSbZ76mrRoyp";
			 MasterKey = "klMty7MTsY1EuOYj5eXxPUryU7OU9F9qcR0YrE0V";
			 ObjectIdAndroid = "UOvyaelO9k";
			 ObjectIdIos ="ZnzJNy5kiU";
			 ObjectIdWP = "J01dCjnH1j";
		}
		
		else if (Env.toLowerCase().contains("fox7"))
		{
			 ApplicationId = "XIgnRH4YM66gtqAYyoVYmkVbdaP2w1S1Xbbw5fZx";
			 RestAPIKey = "CHbox6eS5bFSiOl5s5iGoHqFcOND0hXASitpXmwp";
			 MasterKey = "esXRx1dOBs2uyuUdR6LTJDhLlExiNiR6URacQDXg";
			 ObjectIdAndroid = "reFhukapc7";
			 ObjectIdIos ="fS9JCtbgaU";
			 ObjectIdWP = "fYjLitUe8W";
		}
	}
		
	@Test(groups="beforeCurtain")
	public void beforeCurtain()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "BeforeCurtain";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
  System.out.println("effective FG list = "+mapping_FG);
  System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
		  
		 // j =j +1;
  	}
	  else{
		  
	  }
  j=j+1;
  }
  	// ........................Before Curtain - update Section-B........................................
  	
	
	  /*URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=11").toURL();
	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
	  for (CellEntry cell :  cellfeed3.getEntries()) {
	      
	      if (cell.getCell().getInputValue().equals("android"))
	      {
	    	  int row = cell.getCell().getRow();
	    	  int col = cell.getCell().getCol();
	    	  System.out.println("row="+cell.getCell().getRow());
	    	  System.out.println("col="+cell.getCell().getCol());
	    	 
	    	  andIndex.add(row);
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
	    		  key_and = cell2.getCell().getInputValue();
	    		  System.out.println("key="+cell2.getCell().getInputValue());  
	    	  }
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
	    	  
	    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
	    		  value_and = cell4.getCell().getInputValue().toLowerCase();
	    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
	    	  }
	    	  
	    	  mapping_and.put(key_and, value_and);
	      }  
	    	  else if(cell.getCell().getInputValue().equals("ios"))
	    	  {
	    		  int row = cell.getCell().getRow();
		    	  int col = cell.getCell().getCol();
		    	  System.out.println("row="+cell.getCell().getRow());
		    	  System.out.println("col="+cell.getCell().getCol());
		    	 
		    	  iosIndex.add(row);
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
		    	  
		    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
		    		  key_ios = cell2.getCell().getInputValue();
		    		  System.out.println("key="+cell2.getCell().getInputValue());  
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
		    		  value_ios = cell4.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
		    	  }
		    	  
		    	  mapping_ios.put(key_ios, value_ios);
	    	  }
	    	  
	    	  else if (cell.getCell().getInputValue().equals("wp"))
	    	  {
	    		  int row = cell.getCell().getRow();
		    	  int col = cell.getCell().getCol();
		    	  System.out.println("row="+cell.getCell().getRow());
		    	  System.out.println("col="+cell.getCell().getCol());
		    	 
		    	  wpIndex.add(row);
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
		    	  
		    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
		    		  key_wp = cell2.getCell().getInputValue();
		    		  System.out.println("key="+cell2.getCell().getInputValue());  
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
		    		  value_wp = cell4.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
		    	  }
		    	  
		    	  mapping_wp.put(key_wp, value_wp);
	    	  }
	      		
	    	  else {
	    		  
	    	  }  
	      }
	  
	  System.out.println("effective Android list = "+mapping_and);
	  System.out.println("effective ios list = "+mapping_ios);
	  System.out.println("effective windows list = "+mapping_wp);
	
	  // ................................Before Curtain - update android................................................
	  
	  JSONObject jsonObj = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

	  
	  Set<Entry<String,String>>entrySet3 = mapping_and.entrySet();
	  int k=0;
	  for(Entry<String,String>entry3:entrySet3)
	  {
		  
		//  String ObjectId = getObjectIdforPlatform("android",ApplicationId,RestAPIKey,MasterKey);
		  
		//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj.get(dkey2)==null)
		  {
			  temp=1;
		  }
		  if(temp==0)
		  {
		  String current_value = jsonObj.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);
		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(andIndex.get(k),12,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - anddroid, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  // do nothing
		  }
		  k = k+1;
		  
	  }
		  
		//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
	      String main_string = modifiedExtractAndUpdateJson(ObjectIdAndroid, mapping_and,ApplicationId,RestAPIKey,MasterKey);
		  String finalpayload = getPayloadAsString("updatevalueobject",
					main_string);
		  
		  System.out.println("payload = " + finalpayload);
		  
		  boolean check = false;
		  RequestGenerator req=null;
		  do{
		   req = updateValueObject(ObjectIdAndroid,finalpayload,ApplicationId,RestAPIKey,MasterKey);

			System.out.println(req.respvalidate.returnresponseasstring());
			check = req.respvalidate.DoesNodeExists("code");
			System.out.println("value of check variable beforeCurtain - update android Place is:" +check);
			
		  }while(check);

			AssertJUnit.assertEquals("Update value object API is not working", 200,
					req.response.getStatus());
			
			// feed new value into spreadsheet
			
			  JSONObject jsonObj1 = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

			
			 k=0;
			  for(Entry<String,String>entry3:entrySet3)
			  {
				  int temp2=0;
		//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				  Object dkey2 = entry3.getKey();
				  if(jsonObj1.get(dkey2)==null)
				  {
					  temp2=1;
				  }
				  if(temp2==0)
				  {
				  String current_value = jsonObj1.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);
			CellEntry cellEntry1 = new CellEntry(andIndex.get(k),13,current_value);
			  cellfeed1.insert(cellEntry1);
				  }
				  else{
					  // do nothing
				  }
			  k = k+1;
			  }
	  
	  //....................................... Before curtain - update ios...........................................
			  
	  JSONObject jsonObj2 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

	  
	  Set<Entry<String,String>>entrySet4 = mapping_ios.entrySet();
	  int l=0;
	  for(Entry<String,String>entry3:entrySet4)
	  {
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj2.get(dkey2)==null)
		  {
			  temp=1;
		  }
		  if(temp==0)
		  {
		  String current_value = jsonObj2.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);

		//  String ObjectId = getObjectIdforPlatform("ios",ApplicationId,RestAPIKey,MasterKey);
		  
		//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(iosIndex.get(l),12,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - ios, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  // do nothing
		  }
		  l =l+1;
	  }
		  
	  String main_string1 = modifiedExtractAndUpdateJson(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);
		  String finalpayload1 = getPayloadAsString("updatevalueobject",
					main_string1);
		  
		  System.out.println("payload = " + finalpayload1);
		  
		  check = false;
		  RequestGenerator req1=null;
		  do{
		   req1 = updateValueObject(ObjectIdIos,finalpayload1,ApplicationId,RestAPIKey,MasterKey);

			System.out.println(req1.respvalidate.returnresponseasstring());
			check = req1.respvalidate.DoesNodeExists("code");
			System.out.println("value of check variable beforeCurtain - update ios Place is:" +check);
			
		  }while(check);

			AssertJUnit.assertEquals("Update value object API is not working", 200,
					req1.response.getStatus());
			
			  JSONObject jsonObj3 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

			
			// feed new value into spreadsheet
			  l=0;
			  for(Entry<String,String>entry3:entrySet4)
			  {
				  int temp2=0;
				  Object dkey2 = entry3.getKey();
				  if(jsonObj3.get(dkey2)==null)
				  {
					  temp2=1;
				  }
				  if(temp2==0)
				  {
				  String current_value = jsonObj3.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);
		//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			CellEntry cellEntry1 = new CellEntry(iosIndex.get(l),13,current_value);
			  cellfeed1.insert(cellEntry1);
				  }
				  else{
					  // do nothing
				  }
			  l = l+1;
	  }
	  
	  	  //........................................ update wp........................................
			  
	       JSONObject jsonObj4 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);
	  
		  Set<Entry<String,String>>entrySet5 = mapping_wp.entrySet();
		  int m=0;
		  for(Entry<String,String>entry3:entrySet5)
		  {
			  int temp=0;
			  Object dkey2 = entry3.getKey();
			  if(jsonObj4.get(dkey2)==null)
			  {
				  temp=1;
			  }
			  if(temp==0)
			  {
			  String current_value = jsonObj4.get(dkey2).toString();
			  System.out.println("current key =" + entry3.getKey());
			  System.out.println("current value =" + current_value);

			  
			//  String ObjectId = getObjectIdforPlatform("wp",ApplicationId,RestAPIKey,MasterKey);
			  
			 // String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			  
			  // feed current value into spreadsheet
			  CellEntry cellEntry = new CellEntry(wpIndex.get(m),12,current_value);
			  cellfeed1.insert(cellEntry);
			  
			  System.out.println("for platform - wp, for key = "+ entry3.getKey()+" current value = " + current_value);
			  }
			  else
			  {
				  // do nothing
			  }
			  m = m+1;
		  }
			  
			//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
		  String main_string2 = modifiedExtractAndUpdateJson(ObjectIdWP, mapping_wp,ApplicationId,RestAPIKey,MasterKey);

			  String finalpayload2 = getPayloadAsString("updatevalueobject",
						main_string2);
			  
			  System.out.println("payload = " + finalpayload2);
			  
			  check = false;
			  RequestGenerator req2=null;
			  do{
			   req2 = updateValueObject(ObjectIdWP,finalpayload2,ApplicationId,RestAPIKey,MasterKey);

				System.out.println(req2.respvalidate.returnresponseasstring());
				check = req2.respvalidate.DoesNodeExists("code");
				System.out.println("value of check variable beforeCurtain - update wp is:" +check);
			  }while(check);

				AssertJUnit.assertEquals("Update value object API is not working", 200,
						req2.response.getStatus());
				
				// feed new value into spreadsheet
				
			    JSONObject jsonObj5 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

				 m=0;
				 for(Entry<String,String>entry3:entrySet5)
				 {
					 int temp2=0;
	            	  Object dkey2 = entry3.getKey();
	            	  if(jsonObj5.get(dkey2)==null)
	            	  {
	            		  temp2=1;
	            	  }
	            	  if(temp2==0)
	            	  {
	      			  String current_value = jsonObj5.get(dkey2).toString();
	      			  System.out.println("current key =" + entry3.getKey());
	      			  System.out.println("current value =" + current_value);

			//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				CellEntry cellEntry1 = new CellEntry(wpIndex.get(m),13,current_value);
				  cellfeed1.insert(cellEntry1);
	            	  }
	            	  else{
	            		  // do nothing
	            	  }
				  m = m+1;
		  }
*/	  
	  
	}
	
   @Test(groups ="afterCurtain")
   public void afterCurtain()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
   {
			
		//.............................. After Curtain - update section-A.......................................
	   
		String worksheetname = "AfterCurtain";   
		GoogleCredential credential = refreshToken();
		SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
		WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);

	  URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	  CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
	  
	  for (CellEntry cell :  cellfeed1.getEntries()) {
	      
	      if (cell.getCell().getInputValue().equals("FG"))
	      {
	    	  int row = cell.getCell().getRow();
	    	  int col = cell.getCell().getCol();
	    	  System.out.println("row="+cell.getCell().getRow());
	    	  System.out.println("col="+cell.getCell().getCol());
	    	 
	    	  fgIndex.add(row);
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
	    		  key_FG = cell2.getCell().getInputValue();
	    		  System.out.println("key="+cell2.getCell().getInputValue());  
	    	  }
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
	    	  
	    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
	    	  {	
	    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
				  {
	    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
				  }
	    		  else
				  {
					  value_FG = cell3.getCell().getInputValue();
				  }
	    		 // value_FG = cell3.getCell().getInputValue().toLowerCase();
	    		  System.out.println("value="+cell3.getCell().getInputValue());  
	    	  }
	    	  
	    	  mapping_FG.put(key_FG, value_FG);
	      } 
	      
	      else if (cell.getCell().getInputValue().equals("KV")){
	    	  
	    	  int row = cell.getCell().getRow();
	    	  int col = cell.getCell().getCol();
	    	  System.out.println("row="+cell.getCell().getRow());
	    	  System.out.println("col="+cell.getCell().getCol());
	    	 
	    	  kvIndex.add(row);
	    	  col = col+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
	    		  key_KV = cell2.getCell().getInputValue();
	    		  System.out.println("key="+cell2.getCell().getInputValue());  
	    	  }
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
	    	  
	    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
	    	  {
	    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
				  {
	    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
				  }
	    		  else
				  {
	    			  
					  value_KV = cell3.getCell().getInputValue();
					  value_KV =  JSONObject.escape(value_KV);
	                  System.out.println("value="+value_KV);

				  }
	    		 // value_KV = cell3.getCell().getInputValue().toLowerCase();
	    		  System.out.println("value="+cell3.getCell().getInputValue());  
	    	  }
	    	  
	    	  mapping_KV.put(key_KV, value_KV);
	    	    
	      }
	      
	      else{
	    //	System.out.println("not a valid type");
	      }
	    }
		 
	  System.out.println("effective FG list = "+mapping_FG);
	  System.out.println("effective KV list = "+mapping_KV);

	  //................................After Curtain - Update FG..............................................
	  
	  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
	  int i=0;
	  for(Entry<String,String>entry:entrySet)
	  {
		  
		  int check = keyExists(entry.getKey(),"FG");
		  
		  if (check ==1 )
		  {

		  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
		  
		  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		  
		  System.out.println("current value =" +currentValue);
		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(fgIndex.get(i),5,currentValue);
		  cellfeed1.insert(cellEntry);
		  
		  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		  
		  updateKeyValuePairAPI("FG",payloadparams,description);

			// feed new value into spreadsheet
			String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
			
			 System.out.println("new value =" +NewValue);
			 
			 CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
			  cellfeed1.insert(cellEntry1);
		  }
		  
		  else{
			  // do nothing
		  }
			  
			  i =i +1;
	  	}
	  
	    //.................................After Curtain- update KV.................................................
	  	
	  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
	  int j=0;
	  
	  for(Entry<String,String>entry:entrySet1)
	  {
		  int check = keyExists(entry.getKey(),"KV");
		  
		  if (check ==1 )
		  {

		  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
		  
		  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		  
		  System.out.println("current value =" +currentValue);
		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(kvIndex.get(j),5,currentValue);
		  cellfeed1.insert(cellEntry);
		  
		  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		  
			updateKeyValuePairAPI("KV",payloadparams,description);

			// feed new value into spreadsheet
			String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
			
			 System.out.println("new value =" +NewValue);
			 
			 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
			  cellfeed1.insert(cellEntry1);
		  }
		  
		  else{
			  
		  }
			  
			  j =j +1;
	  	}
	  
	  	// ........................After Curtain - update Section-B........................................
	  	
		
/*		  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=11").toURL();
		  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
		  for (CellEntry cell :  cellfeed3.getEntries()) {
		      
		      if (cell.getCell().getInputValue().equals("android"))
		      {
		    	  int row = cell.getCell().getRow();
		    	  int col = cell.getCell().getCol();
		    	  System.out.println("row="+cell.getCell().getRow());
		    	  System.out.println("col="+cell.getCell().getCol());
		    	 
		    	  andIndex.add(row);
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
		    	  
		    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
		    		  key_and = cell2.getCell().getInputValue();
		    		  System.out.println("key="+cell2.getCell().getInputValue());  
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
		    		  value_and = cell4.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
		    	  }
		    	  
		    	  mapping_and.put(key_and, value_and);
		      }  
		    	  else if(cell.getCell().getInputValue().equals("ios"))
		    	  {
		    		  int row = cell.getCell().getRow();
			    	  int col = cell.getCell().getCol();
			    	  System.out.println("row="+cell.getCell().getRow());
			    	  System.out.println("col="+cell.getCell().getCol());
			    	 
			    	  iosIndex.add(row);
			    	  
			    	  col = col+1;
			    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
			    	  
			    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
			    		  key_ios = cell2.getCell().getInputValue();
			    		  System.out.println("key="+cell2.getCell().getInputValue());  
			    	  }
			    	  
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
			    		  value_ios = cell4.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
			    	  }
			    	  
			    	  mapping_ios.put(key_ios, value_ios);
		    	  }
		    	  
		    	  else if (cell.getCell().getInputValue().equals("wp"))
		    	  {
		    		  int row = cell.getCell().getRow();
			    	  int col = cell.getCell().getCol();
			    	  System.out.println("row="+cell.getCell().getRow());
			    	  System.out.println("col="+cell.getCell().getCol());
			    	 
			    	  wpIndex.add(row);
			    	  
			    	  col = col+1;
			    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
			    	  
			    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
			    		  key_wp = cell2.getCell().getInputValue();
			    		  System.out.println("key="+cell2.getCell().getInputValue());  
			    	  }
			    	  
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
			    		  value_wp = cell4.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
			    	  }
			    	  
			    	  mapping_wp.put(key_wp, value_wp);
		    	  }
		      		
		    	  else {
		    		  
		    	  }  
		      }
		  
		  System.out.println("effective Android list = "+mapping_and);
		  System.out.println("effective ios list = "+mapping_ios);
		  System.out.println("effective windows list = "+mapping_wp);
		
		  //.................................. After Curtain - update android........................................
		  
		  JSONObject jsonObj = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

		  Set<Entry<String,String>>entrySet3 = mapping_and.entrySet();
		  int k=0;
		  for(Entry<String,String>entry3:entrySet3)
		  {
			  
			//  String ObjectId = getObjectIdforPlatform("android",ApplicationId,RestAPIKey,MasterKey);
			  
			//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			  int temp=0;
			  Object dkey2 = entry3.getKey();
			  if(jsonObj.get(dkey2)==null)
			  {
				  temp=1;
			  }
			  if(temp==0)
			  {
			  String current_value = jsonObj.get(dkey2).toString();
			  System.out.println("current key =" + entry3.getKey());
			  System.out.println("current value =" + current_value);

			  
			  // feed current value into spreadsheet
			  
			  CellEntry cellEntry = new CellEntry(andIndex.get(k),12,current_value);
			  cellfeed1.insert(cellEntry);
			  
			  System.out.println("for platform - anddroid, for key = "+ entry3.getKey()+" current value = " + current_value);
			  }
			  else{
				  // do nothing
			  }
			  k=k+1;
		  }
		  
			//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
	      String main_string = modifiedExtractAndUpdateJson(ObjectIdAndroid, mapping_and,ApplicationId,RestAPIKey,MasterKey);

			  String finalpayload = getPayloadAsString("updatevalueobject",
						main_string);
			  
			  System.out.println("payload = " + finalpayload);
			  
			  boolean check = false;
			  RequestGenerator req = null;
			  do{
			   req = updateValueObject(ObjectIdAndroid,finalpayload,ApplicationId,RestAPIKey,MasterKey);

				System.out.println(req.respvalidate.returnresponseasstring());
				
				check = req.respvalidate.DoesNodeExists("code");
				System.out.println("value of check variable afterCurtain - update android Place is:" +check);
				
			  }while(check);

				AssertJUnit.assertEquals("Update value object API is not working", 200,
						req.response.getStatus());
				
				  JSONObject jsonObj1 = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

				
				// feed new value into spreadsheet
				  k=0;
				  for(Entry<String,String>entry3:entrySet3)
				  {
					  int temp2=0;
					  Object dkey2 = entry3.getKey();
					  if(jsonObj1.get(dkey2)==null)
					  {
						  temp2=1;
					  }
					  if(temp2==0)
					  {
					  String current_value = jsonObj1.get(dkey2).toString();
					  System.out.println("current key =" + entry3.getKey());
					  System.out.println("current value =" + current_value);

				
			//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				CellEntry cellEntry1 = new CellEntry(andIndex.get(k),13,current_value);
				  cellfeed1.insert(cellEntry1);
					  }
					  else{
						  // do nothing
					  }
				  k = k+1;
		  }
		  
		  // ..................................After Curtain - update ios..........................................
				  
		  JSONObject jsonObj2 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

		  
		  Set<Entry<String,String>>entrySet4 = mapping_ios.entrySet();
		  int l=0;
		  for(Entry<String,String>entry3:entrySet4)
		  {
			  
			//  String ObjectId = getObjectIdforPlatform("ios",ApplicationId,RestAPIKey,MasterKey);
			  
			//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			  int temp=0;
			  Object dkey2 = entry3.getKey();
			  if(jsonObj2.get(dkey2)==null)
			  {
				  temp=1;
			  }
			  if(temp==0)
			  {
			  String current_value = jsonObj2.get(dkey2).toString();
			  System.out.println("current key =" + entry3.getKey());
			  System.out.println("current value =" + current_value);

			  
			  // feed current value into spreadsheet
			  CellEntry cellEntry = new CellEntry(iosIndex.get(l),12,current_value);
			  cellfeed1.insert(cellEntry);
			  
			  System.out.println("for platform - ios, for key = "+ entry3.getKey()+" current value = " + current_value);
			  }
			  else{
				  // do nothing
			  }
			  l=l+1;
		  }
			  
			//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
		  String main_string1 = modifiedExtractAndUpdateJson(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

			  String finalpayload1 = getPayloadAsString("updatevalueobject",
						main_string1);
			  
			  System.out.println("payload = " + finalpayload1);
			  
			  check = false;
			  RequestGenerator req1=null;
			  do{
			   req1 = updateValueObject(ObjectIdIos,finalpayload1,ApplicationId,RestAPIKey,MasterKey);

				System.out.println(req1.respvalidate.returnresponseasstring());
				
				check = req1.respvalidate.DoesNodeExists("code");
				System.out.println("value of check variable on demand - update android Place is:" +check);
			  }while(check);

				AssertJUnit.assertEquals("Update value object API is not working", 200,
						req1.response.getStatus());
				
				  JSONObject jsonObj3 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

				
				// feed new value into spreadsheet
				
				  l=0;
				  for(Entry<String,String>entry3:entrySet4)
				  {
					  int temp2=0;
					  Object dkey2 = entry3.getKey();
					  if(jsonObj3.get(dkey2)==null)
					  {
						  temp2=1;
					  }
					  if(temp2==0)
					  {
					  String current_value = jsonObj3.get(dkey2).toString();
					  System.out.println("current key =" + entry3.getKey());
					  System.out.println("current value =" + current_value);

			//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				CellEntry cellEntry1 = new CellEntry(iosIndex.get(l),13,current_value);
				  cellfeed1.insert(cellEntry1);
					  }
					  else
					  {
						  // do nothing
					  }
				  l = l+1;
		  }
		  
		  	  // .......................................After Curtain - update wp...................................
		  
			   JSONObject jsonObj4 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

			  Set<Entry<String,String>>entrySet5 = mapping_wp.entrySet();
			  int m=0;
			  for(Entry<String,String>entry3:entrySet5)
			  {
				  
				//  String ObjectId = getObjectIdforPlatform("wp",ApplicationId,RestAPIKey,MasterKey);
				  
				//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				  int temp=0;
				  Object dkey2 = entry3.getKey();
				  if(jsonObj4.get(dkey2)==null)
				  {
					  temp=1;
				  }
				  if(temp==0)
				  {
				  String current_value = jsonObj4.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);

				  
				  // feed current value into spreadsheet
				  CellEntry cellEntry = new CellEntry(wpIndex.get(m),12,current_value);
				  cellfeed1.insert(cellEntry);
				  
				  System.out.println("for platform - wp, for key = "+ entry3.getKey()+" current value = " + current_value);
				  }
				  else{
					  // do nothing
				  }
				  m=m+1;
			  }
				  
			//	  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
			  String main_string2 = modifiedExtractAndUpdateJson(ObjectIdWP, mapping_wp,ApplicationId,RestAPIKey,MasterKey);

				  String finalpayload2 = getPayloadAsString("updatevalueobject",
							main_string2);
				  
				  System.out.println("payload = " + finalpayload2);
				  
				  check = false;
				  RequestGenerator req2=null;
				  do{
				   req2 = updateValueObject(ObjectIdWP,finalpayload2,ApplicationId,RestAPIKey,MasterKey);

					System.out.println(req2.respvalidate.returnresponseasstring());
					check = req2.respvalidate.DoesNodeExists("code");
					System.out.println("value of check variable afterCurtain - update wp Place is:" +check);
				  }while(check);

					AssertJUnit.assertEquals("Update value object API is not working", 200,
							req2.response.getStatus());
					
				    JSONObject jsonObj5 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

					// feed new value into spreadsheet
					  m=0;
					  for(Entry<String,String>entry3:entrySet5)
					  {
						  int temp2=0;
		            	  Object dkey2 = entry3.getKey();
		            	  if(jsonObj5.get(dkey2)==null)
		            	  {
		            		  temp2=1;
		            	  }
		            	  if(temp2==0)
		            	  {
		      			  String current_value = jsonObj5.get(dkey2).toString();
		      			  System.out.println("current key =" + entry3.getKey());
		      			  System.out.println("current value =" + current_value);

				//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
					CellEntry cellEntry1 = new CellEntry(wpIndex.get(m),13,current_value);
					  cellfeed1.insert(cellEntry1);
		            	  }
		            	  else{
		            		  // do nothing
		            	  }
					  m = m+1;
			  }
*/   }
	
   @Test(groups="revert")
   public void revert()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{	   
		String worksheetname = "Revert";   
		GoogleCredential credential = refreshToken();
		SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
		WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);

		 URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
		 CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
		 
		 for (CellEntry cell :  cellfeed1.getEntries()) {
		     
     if (cell.getCell().getInputValue().equals("FG"))
     {
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  fgIndex.add(row);
   	  
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_FG = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
   	  {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
   		//  value_FG = cell3.getCell().getInputValue().toLowerCase();
   		  System.out.println("value="+cell3.getCell().getInputValue());  
   	  }
   	  
   	  mapping_FG.put(key_FG, value_FG);
     } 
     
     else if (cell.getCell().getInputValue().equals("KV")){
   	  
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  kvIndex.add(row);
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_KV = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
   	  {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
   		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
   		  System.out.println("value="+cell3.getCell().getInputValue().toLowerCase());  
   	  }
   	  
   	  mapping_KV.put(key_KV, value_KV);
   	    
     }
     
     else{
   //	System.out.println("not a valid type");
     }
   }
	 
 System.out.println("effective FG list = "+mapping_FG);
 System.out.println("effective KV list = "+mapping_KV);

 //.....................................Revert - Update FG...........................................
 
 Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
 int i=0;
 for(Entry<String,String>entry:entrySet)
 {
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {

	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  
	  updateKeyValuePairAPI("FG",payloadparams,description);

	  		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		  cellfeed1.insert(cellEntry1);
	  }
	  
	  else{
		  // do nothing
	  }
		  
		  i =i +1;
 	}
 
   // .........................................Revert - update KV...............................................
 	
 Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
 int j=0;
 
 for(Entry<String,String>entry:entrySet1)
 {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {

	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  
	  updateKeyValuePairAPI("KV",payloadparams,description);

	  		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
	  }
	  
	  else{
		  // do nothing
	  }
		  
		  j =j +1;
 	}
 
 	// ........................Revert - update Section-B........................................
 	
	
/*	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=11").toURL();
	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
	  for (CellEntry cell :  cellfeed3.getEntries()) {
	      
	      if (cell.getCell().getInputValue().equals("android"))
	      {
	    	  int row = cell.getCell().getRow();
	    	  int col = cell.getCell().getCol();
	    	  System.out.println("row="+cell.getCell().getRow());
	    	  System.out.println("col="+cell.getCell().getCol());
	    	 
	    	  andIndex.add(row);
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
	    		  key_and = cell2.getCell().getInputValue();
	    		  System.out.println("key="+cell2.getCell().getInputValue());  
	    	  }
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
	    	  
	    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
	    		  value_and = cell4.getCell().getInputValue().toLowerCase();
	    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
	    	  }
	    	  
	    	  mapping_and.put(key_and, value_and);
	      }  
	    	  else if(cell.getCell().getInputValue().equals("ios"))
	    	  {
	    		  int row = cell.getCell().getRow();
		    	  int col = cell.getCell().getCol();
		    	  System.out.println("row="+cell.getCell().getRow());
		    	  System.out.println("col="+cell.getCell().getCol());
		    	 
		    	  iosIndex.add(row);
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
		    	  
		    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
		    		  key_ios = cell2.getCell().getInputValue();
		    		  System.out.println("key="+cell2.getCell().getInputValue());  
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
		    		  value_ios = cell4.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
		    	  }
		    	  
		    	  mapping_ios.put(key_ios, value_ios);
	    	  }
	    	  
	    	  else if (cell.getCell().getInputValue().equals("wp"))
	    	  {
	    		  int row = cell.getCell().getRow();
		    	  int col = cell.getCell().getCol();
		    	  System.out.println("row="+cell.getCell().getRow());
		    	  System.out.println("col="+cell.getCell().getCol());
		    	 
		    	  wpIndex.add(row);
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
		    	  
		    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
		    		  key_wp = cell2.getCell().getInputValue();
		    		  System.out.println("key="+cell2.getCell().getInputValue());  
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    	  for (CellEntry cell4 :  cellfeed4.getEntries()) {
		    		  value_wp = cell4.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell4.getCell().getInputValue().toLowerCase());  
		    	  }
		    	  
		    	  mapping_wp.put(key_wp, value_wp);
	    	  }
	      		
	    	  else {
	    		  
	    	  }  
	      }
	  
	  System.out.println("effective Android list = "+mapping_and);
	  System.out.println("effective ios list = "+mapping_ios);
	  System.out.println("effective windows list = "+mapping_wp);
	
	  //.............................. Revert - update android..........................................
	  
	  JSONObject jsonObj = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

	  Set<Entry<String,String>>entrySet3 = mapping_and.entrySet();
	  int k=0;
	  for(Entry<String,String>entry3:entrySet3)
	  {
		  
		//  String ObjectId = getObjectIdforPlatform("android",ApplicationId,RestAPIKey,MasterKey);
		  
		//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj.get(dkey2)==null)
		  {
			  temp=1;
		  }
		  if(temp==0)
		  {
		  String current_value = jsonObj.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);

		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(andIndex.get(k),12,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - anddroid, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  // do nothing
		  }
		  k=k+1;
	  }
		  
		//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
      String main_string = modifiedExtractAndUpdateJson(ObjectIdAndroid, mapping_and,ApplicationId,RestAPIKey,MasterKey);

		  String finalpayload = getPayloadAsString("updatevalueobject",
					main_string);
		  
		  System.out.println("payload = " + finalpayload);
		  
		  boolean check = false;
		  RequestGenerator req = null;
		  do{
		  
		   req = updateValueObject(ObjectIdAndroid,finalpayload,ApplicationId,RestAPIKey,MasterKey);

			System.out.println(req.respvalidate.returnresponseasstring());
			
			check = req.respvalidate.DoesNodeExists("code");
			System.out.println("value of check variable revert - update android Place is:" +check);
			
		  }while(check);

			AssertJUnit.assertEquals("Update value object API is not working", 200,
					req.response.getStatus());
			
			  JSONObject jsonObj1 = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

			// feed new value into spreadsheet
			  k=0;
			  for(Entry<String,String>entry3:entrySet3)
			  {
				  int temp2=0;
		//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				  Object dkey2 = entry3.getKey();
				  if(jsonObj1.get(dkey2)==null)
				  {
					  temp2=1;
				  }
				  if(temp2==0)
				  {
				  String current_value = jsonObj1.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);

			CellEntry cellEntry1 = new CellEntry(andIndex.get(k),13,current_value);
			  cellfeed1.insert(cellEntry1);
				  }
				  else{
					  // do nothing
				  }
			  k = k+1;
	  }
	  
	  //............................. Revert - update ios........................................
			  
	  JSONObject jsonObj2 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

	  
	  Set<Entry<String,String>>entrySet4 = mapping_ios.entrySet();
	  int l=0;
	  for(Entry<String,String>entry3:entrySet4)
	  {
		  
		//  String ObjectId = getObjectIdforPlatform("ios",ApplicationId,RestAPIKey,MasterKey);
		  
		//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj2.get(dkey2)==null)
		  {
		  temp=1;
		  }
		  if(temp==0)
		  {
		  String current_value = jsonObj2.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);

		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(iosIndex.get(l),12,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - ios, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  // do nothing
		  }
		  l=l+1;
	  }
		  
		//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
	  String main_string1 = modifiedExtractAndUpdateJson(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

		  String finalpayload1 = getPayloadAsString("updatevalueobject",
					main_string1);
		  
		  System.out.println("payload = " + finalpayload1);
		  
		  check = false;
		  RequestGenerator req1= null;
		  do{
		  
		   req1 = updateValueObject(ObjectIdIos,finalpayload1,ApplicationId,RestAPIKey,MasterKey);

			System.out.println(req1.respvalidate.returnresponseasstring());
			
			check = req1.respvalidate.DoesNodeExists("code");
			System.out.println("value of check variable revert - update ios Place is:" +check);
			
		  }while(check);

			AssertJUnit.assertEquals("Update value object API is not working", 200,
					req1.response.getStatus());
			
			  JSONObject jsonObj3 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

			// feed new value into spreadsheet
			
			l=0;
			  for(Entry<String,String>entry3:entrySet4)
			  {
		//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				  int temp2=0;
				  Object dkey2 = entry3.getKey();
				  if(jsonObj3.get(dkey2)==null)
				  {
					  temp2=1;
				  }
				  if(temp2==0)
				  {
				  String current_value = jsonObj3.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);

			CellEntry cellEntry1 = new CellEntry(iosIndex.get(l),13,current_value);
			  cellfeed1.insert(cellEntry1);
				  }
				  else
				  {
					  // do nothing
				  }
			  l = l+1;
	  }
	  
	  	  //...................................Revert - update wp..........................................
	  
		  JSONObject jsonObj4 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

		  Set<Entry<String,String>>entrySet5 = mapping_wp.entrySet();
		  int m=0;
		  for(Entry<String,String>entry3:entrySet5)
		  {
			  int temp=0;
			  Object dkey2 = entry3.getKey();
			  if(jsonObj4.get(dkey2)==null)
			  {
				  temp=1;
			  }
			  if(temp==0)
			  {
			  String current_value = jsonObj4.get(dkey2).toString();
			  System.out.println("current key =" + entry3.getKey());
			  System.out.println("current value =" + current_value);

			//  String ObjectId = getObjectIdforPlatform("wp",ApplicationId,RestAPIKey,MasterKey);
			  
			//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			  
			  // feed current value into spreadsheet
			  CellEntry cellEntry = new CellEntry(wpIndex.get(m),12,current_value);
			  cellfeed1.insert(cellEntry);
			  
			  System.out.println("for platform - wp, for key = "+ entry3.getKey()+" current value = " + current_value);
			  }
			  else
			  {
				  // do nothing
			  }
			  m=m+1;
		  }
			  
			//  String main_string = extractAndUpdateJson(ObjectId, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);
		  String main_string2 = modifiedExtractAndUpdateJson(ObjectIdWP, mapping_wp,ApplicationId,RestAPIKey,MasterKey);

			  String finalpayload2 = getPayloadAsString("updatevalueobject",
						main_string2);
			  
			  System.out.println("payload = " + finalpayload2);
			  
			  check = false;
			  RequestGenerator req2=null;
			  do{
			  
			   req2 = updateValueObject(ObjectIdWP,finalpayload2,ApplicationId,RestAPIKey,MasterKey);

				System.out.println(req2.respvalidate.returnresponseasstring());
				
				check = req2.respvalidate.DoesNodeExists("code");
				System.out.println("value of check variable revert - update wp Place is:" +check);
				
			  }while(check);

				AssertJUnit.assertEquals("Update value object API is not working", 200,
						req2.response.getStatus());
				
			    JSONObject jsonObj5 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

				// feed new value into spreadsheet
				
				m=0;
				  for(Entry<String,String>entry3:entrySet5)
				  {
					  int temp2=0;
				//String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
	            	  Object dkey2 = entry3.getKey();
	            	  if(jsonObj5.get(dkey2)==null)
	            	  {
	            		  temp2=1;
	            	  }
	            	  if(temp2==0)
	            	  {
	      			  String current_value = jsonObj5.get(dkey2).toString();
	      			  System.out.println("current key =" + entry3.getKey());
	      			  System.out.println("current value =" + current_value);

				CellEntry cellEntry1 = new CellEntry(wpIndex.get(m),13,current_value);
				  cellfeed1.insert(cellEntry1);
	            	  }
	            	  else
	            	  {
	            		  // do nothing
	            	  }
				  m = m+1;
		  }
*/	}
   
   private int keyExists(String keyname, Object keytype)
   {
	   Object keyFound = "KeyValuePair(s) retrieved successfully";
	   Object keyNotFound = "Key doesn't exists";
	   int check = 0;
	   MyntraService service = Myntra.getService();
	   if (keytype.equals("FG"))
	   {
	    service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { keyname },
				PayloadType.JSON);
	   }
	   else if (keytype.equals("KV"))
	   {
		    service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
					init.Configurations, PayloadType.JSON, new String[] { keyname },
					PayloadType.JSON);

	   }
	   	   
		RequestGenerator req = new RequestGenerator(service);
	//	System.out.println(service.URL);

		String message = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();
	//	System.out.println("status message for " + key + "is :" + message);

		if (message.equals(keyFound))
		{
			check = 1;
		}
		else if (message.equals(keyNotFound))
		{
			check =0;
		}
		else 
			check =0;
		
		return check;
   }
   
   private GoogleCredential refreshToken() throws GeneralSecurityException, IOException
   {
	   ArrayList scopes = new ArrayList();
	    scopes.add(0, DriveScopes.DRIVE_FILE);
	    scopes.add(1, "https://spreadsheets.google.com/feeds");
	    GoogleCredential credential = new GoogleCredential.Builder()
	        .setTransport(new NetHttpTransport())
	        .setJsonFactory(new JacksonFactory())
	        .setServiceAccountId(
	            "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	        .setServiceAccountPrivateKeyFromP12File(new File(filePath))
	        .setServiceAccountScopes(scopes).build();

	    credential.refreshToken();
	    
	    return credential;

   }
   
   private SpreadsheetEntry getSpreadsheetName (GoogleCredential credential) throws IOException, ServiceException
   {
	 //  SpreadsheetService service = new SpreadsheetService("tmp");
	    service.setOAuth2Credentials(credential);
	    URL SPREADSHEET_FEED_URL = new URL(
	            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

	        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
	        for (SpreadsheetEntry spreadsheet : spreadsheets) {
	         System.out.println(spreadsheet.getTitle().getPlainText());
	         if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Sale_July18-19"))
	         {
	         	myspreadsheet = spreadsheet;
	         	System.out.println("spreadsheet name in loop = " +myspreadsheet);
	         }
	     }
	      
	     System.out.println("spreadsheet name = " +myspreadsheet);
	     System.out.println(myspreadsheet.getTitle().getPlainText());
	     
	     return myspreadsheet;

   }
   
   private WorksheetEntry workSheetEntry (SpreadsheetEntry myspreadsheet,String worksheetname) throws IOException, ServiceException
   {
	   
	   URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
	   System.out.println("worksheet feed URL = " +worksheetFeedUrl);
	   
	   WorksheetFeed worksheetFeed= service.getFeed (
	    	    worksheetFeedUrl, WorksheetFeed.class);
	   
	   List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
	   	  
	//   return worksheetEntrys;
	   
	   WorksheetEntry myworksheet = new WorksheetEntry();

		for (WorksheetEntry worksheetEntry :worksheetEntrys)
		 {
		 	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
		 	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase(worksheetname))
		      {
		 		 myworksheet = worksheetEntry;
		      	System.out.println("worksheet name in loop = " +myworksheet);
		      }
		 }
		return myworksheet;
	   
   }
   
   private void updateKeyValuePairAPI(Object keyType, String [] payloadparams, String description )
   {
	   MyntraService service1 = Myntra.getService();
	   if(keyType.equals("FG"))
	   {
	   String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
				payloadparams, description);
	  
	   service1 = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
				finalPayload);		
	   }
	   else if(keyType.equals("KV"))
	   {
		String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
				payloadparams, description);
	   
	   service1 = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION,
				APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
				finalPayload);
		
	   }
	   
	   RequestGenerator req = new RequestGenerator(service1);

		String statusMessage = req.respvalidate
				.NodeValue("status.statusMessage", true).replaceAll("\"", "")
				.trim();

		AssertJUnit.assertTrue("Status Message doesn't match",
				statusMessage.contains("KeyValuePair updated successfully"));


   }
  
   @Test(groups="onDemand")
   public void onDemand() throws URISyntaxException, GeneralSecurityException, IOException, ServiceException, JSONException, ParseException
	{
	String worksheetname = "onDemand";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	 
	 URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
	 CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
	 
	 for (CellEntry cell :  cellfeed1.getEntries())
	 {
     
	  if(cell.getCell().getInputValue().equals("yes"))
	  {
	  int row1 = cell.getCell().getRow();
   	  int col1 = cell.getCell().getCol();
   	  col1 = col1+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2:cellfeed2.getEntries()) 
   	  {	  
	    	  if (cell2.getCell().getInputValue().equals("FG"))
	    	  {
	    		  int row = cell2.getCell().getRow();
	    		  int col = cell2.getCell().getCol();
	    	 
	    		  fgIndex.add(row);
	    	  
	    		  col = col+1;
	    		  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    		  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
	    	  
	    		  for (CellEntry cell3 :  cellfeed3.getEntries()) 
	    		  {
	    		  key_FG = cell3.getCell().getInputValue();
	    		  }
	    	  
	    		  col = col+1;
	    		  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    		  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
	    	  
	    		  for (CellEntry cell4 :  cellfeed4.getEntries()) 
	    		  {
	    			  if((cell4.getCell().getInputValue().toLowerCase().equals("false")||(cell4.getCell().getInputValue().toLowerCase().equals("true"))))
	    					  {
	    				  value_FG = cell4.getCell().getInputValue().toLowerCase();
	    					  }
	    			  else
	    			  {
	    				  value_FG = cell4.getCell().getInputValue();
	    			  }
	    		  }
	    	  
	    		  	mapping_FG.put(key_FG, value_FG);
	    	  } 
     
	    	  else if (cell2.getCell().getInputValue().equals("KV"))
		      {
		    	  
		    	  int row = cell2.getCell().getRow();
		    	  int col = cell2.getCell().getCol();
		    	 
		    	  kvIndex.add(row);
		    	  col = col+1;
		    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
		    	  
	    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
	    	  {
	    		  key_KV = cell5.getCell().getInputValue();
	    	  }
	    	  
	    	  col = col+1;
	    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
	    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
	    	  
	    	  for (CellEntry cell6 :  cellfeed6.getEntries()) 
	    	  {
	    		  if((cell6.getCell().getInputValue().toLowerCase().equals("false")||(cell6.getCell().getInputValue().toLowerCase().equals("true"))))
				  {
	    			  value_KV = cell6.getCell().getInputValue().toLowerCase();
				  }
	    		  else
				  {
					  value_KV = cell6.getCell().getInputValue();
					  value_KV =  JSONObject.escape(value_KV);
	                  System.out.println("value="+value_KV);

				  }
	    	  }
   	  
	    	  mapping_KV.put(key_KV, value_KV);
   	    
		   }
     
     else{
   //	System.out.println("not a valid type");
     }
   }
 }
 }
 System.out.println("effective FG list = "+mapping_FG);
 System.out.println("effective KV list = "+mapping_KV);

 //............................On Demand - Update FG...............................................
 
 Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
 int i=0;
 for(Entry<String,String>entry:entrySet)
 {
	 int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		
		updateKeyValuePairAPI("FG",payloadparams,description);
		
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),7,NewValue);
		  cellfeed1.insert(cellEntry1);
		  
	  }
	  else{
		  // do nothing
	  }
  i=i+1;
 }
 
   //..................................On Demand - update KV................................................
 	
 Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
 int j=0;
 
 for(Entry<String,String>entry:entrySet1)
 {
	 int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	 		
		updateKeyValuePairAPI("KV",payloadparams,description);

		
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),7,NewValue);
		  cellfeed1.insert(cellEntry1);
		  
 	}
	  else{
		  // do nothing
	  }
	  j=j+1;
 }
 
 	// ........................On Demand -update Section-B........................................
 	
	
	  URL cellFeedUrl13 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=10&max-col=13").toURL();
	  CellFeed cellfeed13 = service.getFeed(cellFeedUrl13, CellFeed.class);
	  for (CellEntry cell13 :  cellfeed13.getEntries()) 
	  {
		  System.out.println("test value = "+ cell13.getCell().getInputValue());
		  
	      if (cell13.getCell().getInputValue().equals("yes"))
	      {
	    	  int row1 = cell13.getCell().getRow();
	    	  int col1 = cell13.getCell().getCol();
	    	  System.out.println("row="+cell13.getCell().getRow());
	    	  System.out.println("col="+cell13.getCell().getCol());
	    	 // onDemand.add(row1);
	    	  col1 = col1+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2:cellfeed2.getEntries()) 
	    	  {
	    		  if (cell2.getCell().getInputValue().equals("android"))
	    		  {
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	 
			    	  andIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) 
			    	  {
			    		  key_and = cell4.getCell().getInputValue();
			    		  System.out.println("key="+cell4.getCell().getInputValue());  
			    	  }
	    	  
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
		    	  
			    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
			    	  {
		    		  value_and = cell5.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell5.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_and.put(key_and, value_and);
	    		  	}  
	    		  
	    		  else if(cell2.getCell().getInputValue().equals("ios"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	 
			    	  iosIndex.add(row);
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
			    	  
			    	  for (CellEntry cell6 :  cellfeed6.getEntries())
			    	  {
			    		  key_ios = cell6.getCell().getInputValue();
			    		  System.out.println("key="+cell6.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl7 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed7 = service.getFeed(cellFeedUrl7, CellFeed.class);
			    	  
			    	  for (CellEntry cell7 :  cellfeed7.getEntries()) 
			    	  {
		    		  value_ios = cell7.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell7.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_ios.put(key_ios, value_ios);
	    		  }
	    	  
	    		  else if (cell2.getCell().getInputValue().equals("wp"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	 
			    	  wpIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl8 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed8 = service.getFeed(cellFeedUrl8, CellFeed.class);
			    	  
			    	  for (CellEntry cell8 :  cellfeed8.getEntries()) 
			    	  {
			    		  key_wp = cell8.getCell().getInputValue();
			    		  System.out.println("key="+cell8.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl9 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed9 = service.getFeed(cellFeedUrl9, CellFeed.class);
			    	  
			    	  for (CellEntry cell9 :  cellfeed9.getEntries()) 
			    	  {
			    		  value_wp = cell9.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell9.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_wp.put(key_wp, value_wp);
	    		  }
	      		
	    	  else {
	    		  
	    	  	   }  
	    	  }
	  }
	  }
	  
	  System.out.println("effective Android list = "+mapping_and);
	  System.out.println("effective ios list = "+mapping_ios);
	  System.out.println("effective windows list = "+mapping_wp);
	
	  // .................On Demand - update android ......................................
	  
     //	String ObjectIdAndroid = getObjectIdforPlatform("android",ApplicationId,RestAPIKey,MasterKey);
	  JSONObject jsonObj = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);

	  Set<Entry<String,String>>entrySet3 = mapping_and.entrySet();
	  int k=0;
	  for(Entry<String,String>entry3:entrySet3)
	  {
		//  String ObjectId = getObjectIdforPlatform("android",ApplicationId,RestAPIKey,MasterKey);
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  System.out.println(jsonObj.get(dkey2));
		  if(jsonObj.get(dkey2)==null)
		  {
			  System.out.println(dkey2+"- key does not exist");
			  temp = 1;
		  }
		  if(temp==0){
		  String current_value = jsonObj.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);
	 // ......................feed current value into spreadsheet................................
		  CellEntry cellEntry = new CellEntry(andIndex.get(k),14,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - anddroid, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  // do nothing
		  }
		  
		k = k+1;
	  }
	  
	  String main_string = modifiedExtractAndUpdateJson(ObjectIdAndroid, mapping_and,ApplicationId,RestAPIKey,MasterKey);
	  String finalpayload = getPayloadAsString("updatevalueobject",
				main_string);
	  
	  System.out.println("payload = " + finalpayload);
	//  String response ="";
	  boolean check = false;
	  RequestGenerator req = null;
	  do{
	   req = updateValueObject(ObjectIdAndroid,finalpayload,ApplicationId,RestAPIKey,MasterKey);

		System.out.println(req.respvalidate.returnresponseasstring());
		
		check = req.respvalidate.DoesNodeExists("code");
		System.out.println("value of check variable on demand - update android Place is:" +check);
	  }while (check);


		AssertJUnit.assertEquals("Update value object API is not working", 200,
				req.response.getStatus());
		
	  
	//  modifiedExtractAndUpdateJson(ObjectIdAndroid, entry3.getKey(),current_value, entry3.getValue(),ApplicationId,RestAPIKey,MasterKey);

	 // feed new value into spreadsheet
		
		  JSONObject jsonObj1 = modifiedGetCurrentValueForMobile(ObjectIdAndroid,mapping_and,ApplicationId,RestAPIKey,MasterKey);


	  k =0 ;
	  
	  for(Entry<String,String>entry3:entrySet3)
	  {
		  int temp2=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj1.get(dkey2)==null)
		  {
			  temp2=1;
		  }
		  if(temp2==0){
		  String current_value = jsonObj1.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);

		//	String new_value = getCurrentValueForMobile(ObjectIdAndroid,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			CellEntry cellEntry1 = new CellEntry(andIndex.get(k),15,current_value);
			  cellfeed1.insert(cellEntry1);
		  }
		  else{
			  // do nothing
		  }
			  
			  k = k+1;

	  }
	  
	
	  
	  // ................................On Demand - update ios...........................................
	  
	  JSONObject jsonObj2 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

	  
	  Set<Entry<String,String>>entrySet4 = mapping_ios.entrySet();
	  int l=0;
	  for(Entry<String,String>entry3:entrySet4)
	  {
		  
		//  String ObjectId = getObjectIdforPlatform("ios",ApplicationId,RestAPIKey,MasterKey);
		  int temp=0;
		  Object dkey2 = entry3.getKey();
		  if(jsonObj2.get(dkey2)==null)
		  {
			  temp=1;
		  }
		  if(temp==0){
		  String current_value = jsonObj2.get(dkey2).toString();
		  System.out.println("current key =" + entry3.getKey());
		  System.out.println("current value =" + current_value);

		  
		//  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
		  
		  // feed current value into spreadsheet
		  CellEntry cellEntry = new CellEntry(iosIndex.get(l),14,current_value);
		  cellfeed1.insert(cellEntry);
		  
		  System.out.println("for platform - ios, for key = "+ entry3.getKey()+" current value = " + current_value);
		  }
		  else{
			  //do nothing
		  }
		  l=l+1;
	  }
		  String main_string1 = modifiedExtractAndUpdateJson(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);
		  String finalpayload1 = getPayloadAsString("updatevalueobject",
					main_string1);
		  
		  System.out.println("payload = " + finalpayload1);
		  
		  RequestGenerator req1 = null;
		  check = false;
		  do{
		   req1 = updateValueObject(ObjectIdIos,finalpayload1,ApplicationId,RestAPIKey,MasterKey);

			System.out.println(req1.respvalidate.returnresponseasstring());
			
			check = req1.respvalidate.DoesNodeExists("code");
			System.out.println("value of check variable on demand - update ios Place is:" +check);

			
		  }while(check);

			AssertJUnit.assertEquals("Update value object API is not working", 200,
					req1.response.getStatus());
			
			// feed new value into spreadsheet
			
			  JSONObject jsonObj3 = modifiedGetCurrentValueForMobile(ObjectIdIos,mapping_ios,ApplicationId,RestAPIKey,MasterKey);

		     l=0;
			  for(Entry<String,String>entry3:entrySet4)
			  {
				  int temp2=0;
				  Object dkey2 = entry3.getKey();
				  if(jsonObj3.get(dkey2)==null)
				  {
					  temp2=1;
				  }
				  if(temp2==0){
				  String current_value = jsonObj3.get(dkey2).toString();
				  System.out.println("current key =" + entry3.getKey());
				  System.out.println("current value =" + current_value);
			//String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			CellEntry cellEntry1 = new CellEntry(iosIndex.get(l),15,current_value);
			  cellfeed1.insert(cellEntry1);
				  }
				  else{
					  // do nothing
				  }
			  l = l+1;
			  }
	  
	  
	  	  // ..............................On Demand - update wp.............................................
	    JSONObject jsonObj4 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);

	  
		  Set<Entry<String,String>>entrySet5 = mapping_wp.entrySet();
		  int m=0;
		  for(Entry<String,String>entry3:entrySet5)
		  {
			  int temp=0;
			  Object dkey2 = entry3.getKey();
			  if(jsonObj4.get(dkey2)==null)
			  {
				  temp=1;
			  }
			  if(temp==0){
			  String current_value = jsonObj4.get(dkey2).toString();
			  System.out.println("current key =" + entry3.getKey());
			  System.out.println("current value =" + current_value);
		//	  String ObjectId = getObjectIdforPlatform("wp",ApplicationId,RestAPIKey,MasterKey);
			  
		//	  String current_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
			  
			  // feed current value into spreadsheet
			  CellEntry cellEntry = new CellEntry(wpIndex.get(m),14,current_value);
			  cellfeed1.insert(cellEntry);
			  
			  System.out.println("for platform - wp, for key = "+ entry3.getKey()+" current value = " + current_value);
			  }
			  else{
				  // do nothing
			  }
			  m = m+1;
		  }
			  
			  String main_string2 = modifiedExtractAndUpdateJson(ObjectIdWP, mapping_wp,ApplicationId,RestAPIKey,MasterKey);
			  String finalpayload2 = getPayloadAsString("updatevalueobject",
						main_string2);
			  
			  System.out.println("payload = " + finalpayload2);
			  
			  check = false;
			  RequestGenerator req2 = null;
			  do{
			  
			   req2 = updateValueObject(ObjectIdWP,finalpayload2,ApplicationId,RestAPIKey,MasterKey);

				System.out.println(req2.respvalidate.returnresponseasstring());
				
				check = req2.respvalidate.DoesNodeExists("code");
				System.out.println("value of check variable on demand - update wp Place is:" +check);
				
			  }while(check);

				AssertJUnit.assertEquals("Update value object API is not working", 200,
						req2.response.getStatus());
				
			    JSONObject jsonObj5 = modifiedGetCurrentValueForMobile(ObjectIdWP,mapping_wp,ApplicationId,RestAPIKey,MasterKey);
			    
               m =0 ;
				
               for(Entry<String,String>entry3:entrySet5)
     		  {
     			   int temp2=0;
				// feed new value into spreadsheet
				
            	   Object dkey2 = entry3.getKey();
            	   if(jsonObj5.get(dkey2)==null){
            		   temp2=1;
            	   }
            	   if(temp2==0){
     			  String current_value = jsonObj5.get(dkey2).toString();
     			  System.out.println("current key =" + entry3.getKey());
     			  System.out.println("current value =" + current_value);
			//	String new_value = getCurrentValueForMobile(ObjectId,entry3.getKey(),ApplicationId,RestAPIKey,MasterKey);
				CellEntry cellEntry1 = new CellEntry(wpIndex.get(m),15,current_value);
				  cellfeed1.insert(cellEntry1);
            	   }
            	   else{
            		   // do nothing
            	   }
				  
				  m = m+1;
		  }
	  
	  }
   
	@Test(groups="NewProfile")
	public void NewProfile()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "NewProfile";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
 
   for (CellEntry cell :  cellfeed1.getEntries()) {
     
     if (cell.getCell().getInputValue().equals("FG"))
     {
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  fgIndex.add(row);
   	  
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_FG = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
   		  System.out.println("value="+cell3.getCell().getInputValue());  
   	  }
   	  
   	  mapping_FG.put(key_FG, value_FG);
     } 
     
     else if (cell.getCell().getInputValue().equals("KV")){
   	  
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  kvIndex.add(row);
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_KV = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
   	  {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
			  }
   		  
   		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
   		  System.out.println("value="+cell3.getCell().getInputValue());  
   	  }
   	  
   	  mapping_KV.put(key_KV, value_KV);
   	    
     }
     
     else{
   //	System.out.println("not a valid type");
     }
   }
	 
 System.out.println("effective FG list = "+mapping_FG);
 System.out.println("effective KV list = "+mapping_KV);

 //............................Before Curtain - Update FG................................................................
 
 Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
 int i=0;
 for(Entry<String,String>entry:entrySet)
 {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
 	}
 
   // .......................................Before Curtain - update KV................................................
 	
 Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
 int j=0;
 
 for(Entry<String,String>entry:entrySet1)
 {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
		  
		 // j =j +1;
 	}
	  else{
		  
	  }
 j=j+1;
 }
	  
	  
	}
	
	@Test(groups="NewProfile1")
	public void NewProfile1()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "NewProfile1";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
 
   for (CellEntry cell :  cellfeed1.getEntries()) {
     
     if (cell.getCell().getInputValue().equals("FG"))
     {
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  fgIndex.add(row);
   	  
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_FG = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
   		  System.out.println("value="+cell3.getCell().getInputValue());  
   	  }
   	  
   	  mapping_FG.put(key_FG, value_FG);
     } 
     
     else if (cell.getCell().getInputValue().equals("KV")){
   	  
   	  int row = cell.getCell().getRow();
   	  int col = cell.getCell().getCol();
   	  System.out.println("row="+cell.getCell().getRow());
   	  System.out.println("col="+cell.getCell().getCol());
   	 
   	  kvIndex.add(row);
   	  col = col+1;
   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
   	  
   	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
   		  key_KV = cell2.getCell().getInputValue();
   		  System.out.println("key="+cell2.getCell().getInputValue());  
   	  }
   	  
   	  col = col+1;
   	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
   	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
   	  
   	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
   	  {
   		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
   			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
   		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
   		  
   		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
   		  System.out.println("value="+cell3.getCell().getInputValue());  
   	  }
   	  
   	  mapping_KV.put(key_KV, value_KV);
   	    
     }
     
     else{
   //	System.out.println("not a valid type");
     }
   }
	 
 System.out.println("effective FG list = "+mapping_FG);
 System.out.println("effective KV list = "+mapping_KV);

 //............................Before Curtain - Update FG................................................................
 
 Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
 int i=0;
 for(Entry<String,String>entry:entrySet)
 {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
 	}
 
   // .......................................Before Curtain - update KV................................................
 	
 Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
 int j=0;
 
 for(Entry<String,String>entry:entrySet1)
 {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),5,currentValue);
	  cellfeed1.insert(cellEntry);
	  
	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
		  
		 // j =j +1;
 	}
	  else{
		  
	  }
 j=j+1;
 }
	  
	  
	}
	
	@Test(groups="beforeCurtain-Compare")
	public void beforeCurtainCompare()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "BeforeCurtain";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
				  
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
 // System.out.println("effective FG list = "+mapping_FG);
 // System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
		  CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
*/		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
		  CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
*/		  
		 // j =j +1;
  	}
	  else{
		  
		  
	  }
  j=j+1;
  }
	  
	}

	
	@Test(groups="afterCurtain-Compare")
	public void afterCurtainCompare()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "AfterCurtain";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
 // System.out.println("effective FG list = "+mapping_FG);
 // System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
		  CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6," ");
		  cellfeed1.insert(cellEntry1);

	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
*/		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
		  CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6," ");
		  cellfeed1.insert(cellEntry1);
  
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
*/		  
		 // j =j +1;
  	}
	  else{
		  
	  }
  j=j+1;
  }
	  
	}

	@Test(groups="revert-Compare")
	public void revertCompare()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "Revert";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
//  System.out.println("effective FG list = "+mapping_FG);
//  System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
		  CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
*/		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
		  CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
*/		  
		 // j =j +1;
  	}
	  else{
		  
	  }
  j=j+1;
  }
	  
	}

	@Test(groups="NewProfile-Compare")
	public void NewProfileCompare()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "NewProfile";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
    
    
 // System.out.println("effective FG list = "+mapping_FG);
 // System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	  
		  CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6," ");
		  cellfeed1.insert(cellEntry1);

	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
*/		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
		  CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6," ");
		  cellfeed1.insert(cellEntry1);

	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
*/		  
		 // j =j +1;
  	}
	  else{
		  
	  }
  j=j+1;
  }
	  
	}
	
	@Test(groups="NewProfile1-Compare")
	public void NewProfile1Compare()  throws IOException, ServiceException, URISyntaxException, GeneralSecurityException, JSONException, ParseException
	{
	String worksheetname = "NewProfile1";   
	GoogleCredential credential = refreshToken();
	SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
	WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
	
	URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
  
    for (CellEntry cell :  cellfeed1.getEntries()) {
      
      if (cell.getCell().getInputValue().equals("FG"))
      {
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  fgIndex.add(row);
    	  
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_FG = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_FG = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_FG = cell3.getCell().getInputValue();
			  }
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_FG.put(key_FG, value_FG);
      } 
      
      else if (cell.getCell().getInputValue().equals("KV")){
    	  
    	  int row = cell.getCell().getRow();
    	  int col = cell.getCell().getCol();
    	  System.out.println("row="+cell.getCell().getRow());
    	  System.out.println("col="+cell.getCell().getCol());
    	 
    	  kvIndex.add(row);
    	  col = col+1;
    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
    	  
    	  for (CellEntry cell2 :  cellfeed2.getEntries()) {
    		  key_KV = cell2.getCell().getInputValue();
    		  System.out.println("key="+cell2.getCell().getInputValue());  
    	  }
    	  
    	  col = col+1;
    	  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
    	  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
    	  
    	  for (CellEntry cell3 :  cellfeed3.getEntries()) 
    	  {
    		  if((cell3.getCell().getInputValue().toLowerCase().equals("false")||(cell3.getCell().getInputValue().toLowerCase().equals("true"))))
			  {
    			  value_KV = cell3.getCell().getInputValue().toLowerCase();
			  }
    		  else
			  {
				  value_KV = cell3.getCell().getInputValue();
				  value_KV =  JSONObject.escape(value_KV);
                  System.out.println("value="+value_KV);

			  }
    		  
    		//  value_KV = cell3.getCell().getInputValue().toLowerCase();
    		  System.out.println("value="+cell3.getCell().getInputValue());  
    	  }
    	  
    	  mapping_KV.put(key_KV, value_KV);
    	    
      }
      
      else{
    //	System.out.println("not a valid type");
      }
    }
	 
 // System.out.println("effective FG list = "+mapping_FG);
 // System.out.println("effective KV list = "+mapping_KV);

  //............................Before Curtain - Update FG................................................................
  
  Set<Entry<String,String>>entrySet = mapping_FG.entrySet();
  int i=0;
  for(Entry<String,String>entry:entrySet)
  {
	  
	  int check = keyExists(entry.getKey(),"FG");
	  
	  if (check ==1 )
	  {
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"FG");
	   
		  CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(fgIndex.get(i),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
	  updateKeyValuePairAPI("FG",payloadparams,description);
	  
		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"FG");
		System.out.println("new value =" +NewValue);
		 
		CellEntry cellEntry1 = new CellEntry(fgIndex.get(i),6,NewValue);
		cellfeed1.insert(cellEntry1);
*/		  
	  }
	  
	  else
	  {
		  // do nothing
	  }
		  i =i +1;
		  
  	}
  
    // .......................................Before Curtain - update KV................................................
  	
  Set<Entry<String,String>>entrySet1 = mapping_KV.entrySet();
  int j=0;
  
  for(Entry<String,String>entry:entrySet1)
  {
	  int check = keyExists(entry.getKey(),"KV");
	  
	  if (check ==1 )
	  {
	  
	//  String description = getDescriptionForFeatureGateKey(entry.getKey(),"KV");
	  
		  CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6," ");
		  cellfeed1.insert(cellEntry1);
	  
	  String currentValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
	  
	  System.out.println("current value =" +currentValue);
	  
	  // feed current value into spreadsheet
	  CellEntry cellEntry = new CellEntry(kvIndex.get(j),6,currentValue);
	  cellfeed1.insert(cellEntry);
	  
/*	  String[] payloadparams = new String[] { entry.getKey(), entry.getValue() };
		updateKeyValuePairAPI("KV",payloadparams,description);

	 		// feed new value into spreadsheet
		String NewValue = getCurrentValueForFeatureGateKey(entry.getKey(),"KV");
		
		 System.out.println("new value =" +NewValue);
		 
		 CellEntry cellEntry1 = new CellEntry(kvIndex.get(j),6,NewValue);
		  cellfeed1.insert(cellEntry1);
*/		  
		 // j =j +1;
  	}
	  else{
		  
	  }
  j=j+1;
  }
	  
	}


   @Test(groups="General")
   public void General() throws GeneralSecurityException, IOException, ServiceException, URISyntaxException
   {    
	    int row_bc = 3;
	    int row_ac = 3;
	    int row_revert = 3;
	    int row_bc_secb = 3;
	    int row_ac_secb = 3;
	    int row_revert_secb = 3;


		String worksheetname = "General";   
		GoogleCredential credential = refreshToken();
		SpreadsheetEntry myspreadsheet = getSpreadsheetName(credential);
		WorksheetEntry myworksheet = workSheetEntry(myspreadsheet,worksheetname);
		
		// Section - A
		 URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
		 CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
		 
		 for (CellEntry cell :  cellfeed1.getEntries())
		 {
	     
		  if(cell.getCell().getInputValue().equals("BeforeCurtain"))
		  {
		  int row1 = cell.getCell().getRow();
	   	  int col1 = cell.getCell().getCol();
	   	  col1 = col1+1;
	   	  

	   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	   	  
	   	  for (CellEntry cell2:cellfeed2.getEntries()) 
	   	  {	  
		    	  if (cell2.getCell().getInputValue().equals("FG"))
		    	  {
		    		  int row = cell2.getCell().getRow();
		    		  int col = cell2.getCell().getCol();
		    	      type = "FG";
		    		  fgIndex.add(row);
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
		    	  
		    		  for (CellEntry cell3 :  cellfeed3.getEntries()) 
		    		  {
		    		  key_FG = cell3.getCell().getInputValue();
		    		  }
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    		  for (CellEntry cell4 :  cellfeed4.getEntries()) 
		    		  {
		    			  if((cell4.getCell().getInputValue().toLowerCase().equals("false")||(cell4.getCell().getInputValue().toLowerCase().equals("true"))))
		    					  {
		    				  value_FG = cell4.getCell().getInputValue().toLowerCase();
		    					  }
		    			  else
		    			  {
		    				  value_FG = cell4.getCell().getInputValue();
		    			  }
		    		  }
		    	  
		    		  	mapping_FG.put(key_FG, value_FG);
		    		  	
		   			 String worksheetname_write = "BeforeCurtain"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_bc,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_bc,3,key_FG);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_bc,4,value_FG);
				     cellfeed_BC.insert(cellEntry_BC_value);
                     row_bc = row_bc +1;

		    	  } 
	     
		    	  else if (cell2.getCell().getInputValue().equals("KV"))
			      {
			    	  
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  type ="KV";
			    	  kvIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
			    	  
		    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
		    	  {
		    		  key_KV = cell5.getCell().getInputValue();
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
		    	  
		    	  for (CellEntry cell6 :  cellfeed6.getEntries()) 
		    	  {
		    		  if((cell6.getCell().getInputValue().toLowerCase().equals("false")||(cell6.getCell().getInputValue().toLowerCase().equals("true"))))
					  {
		    			  value_KV = cell6.getCell().getInputValue().toLowerCase();
					  }
		    		  else
					  {
						  value_KV = cell6.getCell().getInputValue();
					  }
		    	  }
	   	  
		    	  mapping_KV.put(key_KV, value_KV);
		    	  
		   			 String worksheetname_write = "BeforeCurtain"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_bc,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_bc,3,key_KV);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_bc,4,value_KV);
				     cellfeed_BC.insert(cellEntry_BC_value);
                     row_bc = row_bc +1;
	   	    
			   }
	     
	     else{
	   //	System.out.println("not a valid type");
	     }
	   }
	 }
		  
		  else if(cell.getCell().getInputValue().equals("AfterCurtain"))
		  {
		  int row1 = cell.getCell().getRow();
	   	  int col1 = cell.getCell().getCol();
	   	  col1 = col1+1;
	   	  

	   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	   	  
	   	  for (CellEntry cell2:cellfeed2.getEntries()) 
	   	  {	  
		    	  if (cell2.getCell().getInputValue().equals("FG"))
		    	  {
		    		  int row = cell2.getCell().getRow();
		    		  int col = cell2.getCell().getCol();
		    	      type = "FG";
		    		  fgIndex.add(row);
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
		    	  
		    		  for (CellEntry cell3 :  cellfeed3.getEntries()) 
		    		  {
		    		  key_FG = cell3.getCell().getInputValue();
		    		  }
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    		  for (CellEntry cell4 :  cellfeed4.getEntries()) 
		    		  {
		    			  if((cell4.getCell().getInputValue().toLowerCase().equals("false")||(cell4.getCell().getInputValue().toLowerCase().equals("true"))))
		    					  {
		    				  value_FG = cell4.getCell().getInputValue().toLowerCase();
		    					  }
		    			  else
		    			  {
		    				  value_FG = cell4.getCell().getInputValue();
		    			  }
		    		  }
		    	  
		    		  	mapping_FG.put(key_FG, value_FG);
		    		  	
		   			 String worksheetname_write = "AfterCurtain"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_ac,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_ac,3,key_FG);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_ac,4,value_FG);
				     cellfeed_BC.insert(cellEntry_BC_value);
				     row_ac = row_ac +1;

		    	  } 
	     
		    	  else if (cell2.getCell().getInputValue().equals("KV"))
			      {
			    	  
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  type ="KV";
			    	  kvIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
			    	  
		    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
		    	  {
		    		  key_KV = cell5.getCell().getInputValue();
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
		    	  
		    	  for (CellEntry cell6 :  cellfeed6.getEntries()) 
		    	  {
		    		  if((cell6.getCell().getInputValue().toLowerCase().equals("false")||(cell6.getCell().getInputValue().toLowerCase().equals("true"))))
					  {
		    			  value_KV = cell6.getCell().getInputValue().toLowerCase();
					  }
		    		  else
					  {
						  value_KV = cell6.getCell().getInputValue();
					  }
		    	  }
	   	  
		    	  mapping_KV.put(key_KV, value_KV);
		    	  
		   			 String worksheetname_write = "AfterCurtain"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_ac,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_ac,3,key_KV);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_ac,4,value_KV);
				     cellfeed_BC.insert(cellEntry_BC_value);
				     row_ac = row_ac +1;
	   	    
			   }
	     
	     else{
	   //	System.out.println("not a valid type");
	     }
	   }
	 }
		  
		  else if(cell.getCell().getInputValue().equals("Revert"))
		  {
		  int row1 = cell.getCell().getRow();
	   	  int col1 = cell.getCell().getCol();
	   	  col1 = col1+1;
	   	  

	   	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	   	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	   	  
	   	  for (CellEntry cell2:cellfeed2.getEntries()) 
	   	  {	  
		    	  if (cell2.getCell().getInputValue().equals("FG"))
		    	  {
		    		  int row = cell2.getCell().getRow();
		    		  int col = cell2.getCell().getCol();
		    	      type = "FG";
		    		  fgIndex.add(row);
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl3 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed3 = service.getFeed(cellFeedUrl3, CellFeed.class);
		    	  
		    		  for (CellEntry cell3 :  cellfeed3.getEntries()) 
		    		  {
		    		  key_FG = cell3.getCell().getInputValue();
		    		  }
		    	  
		    		  col = col+1;
		    		  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    		  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
		    	  
		    		  for (CellEntry cell4 :  cellfeed4.getEntries()) 
		    		  {
		    			  if((cell4.getCell().getInputValue().toLowerCase().equals("false")||(cell4.getCell().getInputValue().toLowerCase().equals("true"))))
		    					  {
		    				  value_FG = cell4.getCell().getInputValue().toLowerCase();
		    					  }
		    			  else
		    			  {
		    				  value_FG = cell4.getCell().getInputValue();
		    			  }
		    		  }
		    	  
		    		  	mapping_FG.put(key_FG, value_FG);
		    		  	
		   			 String worksheetname_write = "Revert"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_revert,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_revert,3,key_FG);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_revert,4,value_FG);
				     cellfeed_BC.insert(cellEntry_BC_value);
				     row_revert = row_revert +1;

		    	  } 
	     
		    	  else if (cell2.getCell().getInputValue().equals("KV"))
			      {
			    	  
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  type ="KV";
			    	  kvIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
			    	  
		    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
		    	  {
		    		  key_KV = cell5.getCell().getInputValue();
		    	  }
		    	  
		    	  col = col+1;
		    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
		    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
		    	  
		    	  for (CellEntry cell6 :  cellfeed6.getEntries()) 
		    	  {
		    		  if((cell6.getCell().getInputValue().toLowerCase().equals("false")||(cell6.getCell().getInputValue().toLowerCase().equals("true"))))
					  {
		    			  value_KV = cell6.getCell().getInputValue().toLowerCase();
					  }
		    		  else
					  {
						  value_KV = cell6.getCell().getInputValue();
					  }
		    	  }
	   	  
		    	  mapping_KV.put(key_KV, value_KV);
		    	  
		   			 String worksheetname_write = "Revert"; 
					 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
					 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=5").toURL();
					 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

			   	     CellEntry cellEntry_BC_type = new CellEntry(row_revert,2,type);
				     cellfeed_BC.insert(cellEntry_BC_type);
				     
			   	     CellEntry cellEntry_BC_key = new CellEntry(row_revert,3,key_KV);
				     cellfeed_BC.insert(cellEntry_BC_key);

			   	     CellEntry cellEntry_BC_value = new CellEntry(row_revert,4,value_KV);
				     cellfeed_BC.insert(cellEntry_BC_value);
				     row_revert = row_revert +1;
	   	    
			   }
	     
	     else{
	   //	System.out.println("not a valid type");
	     }
	   }
	 }
		  
	 }
	 System.out.println("effective FG list = "+mapping_FG);
	 System.out.println("effective KV list = "+mapping_KV);
	 
	 // Section B
		
	  URL cellFeedUrl13 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=10&max-col=13").toURL();
	  CellFeed cellfeed13 = service.getFeed(cellFeedUrl13, CellFeed.class);
	  for (CellEntry cell13 :  cellfeed13.getEntries()) 
	  {
		  System.out.println("test value = "+ cell13.getCell().getInputValue());
		  
	      if (cell13.getCell().getInputValue().equals("BeforeCurtain"))
	      {
	    	  int row1 = cell13.getCell().getRow();
	    	  int col1 = cell13.getCell().getCol();
	    	  System.out.println("row="+cell13.getCell().getRow());
	    	  System.out.println("col="+cell13.getCell().getCol());
	    	 // onDemand.add(row1);
	    	  col1 = col1+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2:cellfeed2.getEntries()) 
	    	  {
	    		  if (cell2.getCell().getInputValue().equals("android"))
	    		  {
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "android";
			    	  andIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) 
			    	  {
			    		  key_and = cell4.getCell().getInputValue();
			    		  System.out.println("key="+cell4.getCell().getInputValue());  
			    	  }
	    	  
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
		    	  
			    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
			    	  {
		    		  value_and = cell5.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell5.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_and.put(key_and, value_and);
			   			 String worksheetname_write = "BeforeCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_bc_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_bc_secb,10,key_and);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_bc_secb,11,value_and);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_bc_secb = row_bc_secb +1;

	    		  	}  
	    		  
	    		  else if(cell2.getCell().getInputValue().equals("ios"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "ios";
			    	  iosIndex.add(row);
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
			    	  
			    	  for (CellEntry cell6 :  cellfeed6.getEntries())
			    	  {
			    		  key_ios = cell6.getCell().getInputValue();
			    		  System.out.println("key="+cell6.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl7 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed7 = service.getFeed(cellFeedUrl7, CellFeed.class);
			    	  
			    	  for (CellEntry cell7 :  cellfeed7.getEntries()) 
			    	  {
		    		  value_ios = cell7.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell7.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_ios.put(key_ios, value_ios);
			   			 String worksheetname_write = "BeforeCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_bc_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_bc_secb,10,key_ios);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_bc_secb,11,value_ios);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_bc_secb = row_bc_secb +1;

	    		  }
	    	  
	    		  else if (cell2.getCell().getInputValue().equals("wp"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "wp";
			    	  wpIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl8 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed8 = service.getFeed(cellFeedUrl8, CellFeed.class);
			    	  
			    	  for (CellEntry cell8 :  cellfeed8.getEntries()) 
			    	  {
			    		  key_wp = cell8.getCell().getInputValue();
			    		  System.out.println("key="+cell8.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl9 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed9 = service.getFeed(cellFeedUrl9, CellFeed.class);
			    	  
			    	  for (CellEntry cell9 :  cellfeed9.getEntries()) 
			    	  {
			    		  value_wp = cell9.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell9.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_wp.put(key_wp, value_wp);
			   			 String worksheetname_write = "BeforeCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_bc_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_bc_secb,10,key_wp);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_bc_secb,11,value_wp);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_bc_secb = row_bc_secb +1;

	    		  }
	      		
	    	  else {
	    		  
	    	  	   }  
	    	  }
	  }
	      
	      else if (cell13.getCell().getInputValue().equals("AfterCurtain"))
	      {
	    	  int row1 = cell13.getCell().getRow();
	    	  int col1 = cell13.getCell().getCol();
	    	  System.out.println("row="+cell13.getCell().getRow());
	    	  System.out.println("col="+cell13.getCell().getCol());
	    	 // onDemand.add(row1);
	    	  col1 = col1+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2:cellfeed2.getEntries()) 
	    	  {
	    		  if (cell2.getCell().getInputValue().equals("android"))
	    		  {
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "android";
			    	  andIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) 
			    	  {
			    		  key_and = cell4.getCell().getInputValue();
			    		  System.out.println("key="+cell4.getCell().getInputValue());  
			    	  }
	    	  
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
		    	  
			    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
			    	  {
		    		  value_and = cell5.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell5.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_and.put(key_and, value_and);
			   			 String worksheetname_write = "AfterCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_ac_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_ac_secb,10,key_and);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_ac_secb,11,value_and);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_ac_secb = row_ac_secb +1;

	    		  	}  
	    		  
	    		  else if(cell2.getCell().getInputValue().equals("ios"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "ios";
			    	  iosIndex.add(row);
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
			    	  
			    	  for (CellEntry cell6 :  cellfeed6.getEntries())
			    	  {
			    		  key_ios = cell6.getCell().getInputValue();
			    		  System.out.println("key="+cell6.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl7 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed7 = service.getFeed(cellFeedUrl7, CellFeed.class);
			    	  
			    	  for (CellEntry cell7 :  cellfeed7.getEntries()) 
			    	  {
		    		  value_ios = cell7.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell7.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_ios.put(key_ios, value_ios);
			   			 String worksheetname_write = "AfterCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_ac_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_ac_secb,10,key_ios);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_ac_secb,11,value_ios);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_ac_secb = row_ac_secb +1;

	    		  }
	    	  
	    		  else if (cell2.getCell().getInputValue().equals("wp"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "wp";
			    	  wpIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl8 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed8 = service.getFeed(cellFeedUrl8, CellFeed.class);
			    	  
			    	  for (CellEntry cell8 :  cellfeed8.getEntries()) 
			    	  {
			    		  key_wp = cell8.getCell().getInputValue();
			    		  System.out.println("key="+cell8.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl9 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed9 = service.getFeed(cellFeedUrl9, CellFeed.class);
			    	  
			    	  for (CellEntry cell9 :  cellfeed9.getEntries()) 
			    	  {
			    		  value_wp = cell9.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell9.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_wp.put(key_wp, value_wp);
			   			 String worksheetname_write = "AfterCurtain"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_ac_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_ac_secb,10,key_wp);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_ac_secb,11,value_wp);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_ac_secb = row_ac_secb +1;

	    		  }
	      		
	    	  else {
	    		  
	    	  	   }  
	    	  }
	  }
	      
	      else if (cell13.getCell().getInputValue().equals("Revert"))
	      {
	    	  int row1 = cell13.getCell().getRow();
	    	  int col1 = cell13.getCell().getCol();
	    	  System.out.println("row="+cell13.getCell().getRow());
	    	  System.out.println("col="+cell13.getCell().getCol());
	    	 // onDemand.add(row1);
	    	  col1 = col1+1;
	    	  URL cellFeedUrl2 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row1+"&max-row="+row1+"&min-col="+col1+"&max-col="+col1).toURL();
	    	  CellFeed cellfeed2 = service.getFeed(cellFeedUrl2, CellFeed.class);
	    	  
	    	  for (CellEntry cell2:cellfeed2.getEntries()) 
	    	  {
	    		  if (cell2.getCell().getInputValue().equals("android"))
	    		  {
			    	  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "android";
			    	  andIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl4 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed4 = service.getFeed(cellFeedUrl4, CellFeed.class);
			    	  
			    	  for (CellEntry cell4 :  cellfeed4.getEntries()) 
			    	  {
			    		  key_and = cell4.getCell().getInputValue();
			    		  System.out.println("key="+cell4.getCell().getInputValue());  
			    	  }
	    	  
			    	  col = col+1;
			    	  URL cellFeedUrl5 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed5 = service.getFeed(cellFeedUrl5, CellFeed.class);
		    	  
			    	  for (CellEntry cell5 :  cellfeed5.getEntries()) 
			    	  {
		    		  value_and = cell5.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell5.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_and.put(key_and, value_and);
			   			 String worksheetname_write = "Revert"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_revert_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_revert_secb,10,key_and);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_revert_secb,11,value_and);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_revert_secb = row_revert_secb +1;

	    		  	}  
	    		  
	    		  else if(cell2.getCell().getInputValue().equals("ios"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "ios";
			    	  iosIndex.add(row);
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl6 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed6 = service.getFeed(cellFeedUrl6, CellFeed.class);
			    	  
			    	  for (CellEntry cell6 :  cellfeed6.getEntries())
			    	  {
			    		  key_ios = cell6.getCell().getInputValue();
			    		  System.out.println("key="+cell6.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl7 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed7 = service.getFeed(cellFeedUrl7, CellFeed.class);
			    	  
			    	  for (CellEntry cell7 :  cellfeed7.getEntries()) 
			    	  {
		    		  value_ios = cell7.getCell().getInputValue().toLowerCase();
		    		  System.out.println("value="+cell7.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_ios.put(key_ios, value_ios);
			   			 String worksheetname_write = "Revert"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_revert_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_revert_secb,10,key_ios);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_revert_secb,11,value_ios);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_ac_secb = row_ac_secb +1;

	    		  }
	    	  
	    		  else if (cell2.getCell().getInputValue().equals("wp"))
	    		  {
		    		  int row = cell2.getCell().getRow();
			    	  int col = cell2.getCell().getCol();
			    	  System.out.println("row="+cell2.getCell().getRow());
			    	  System.out.println("col="+cell2.getCell().getCol());
			    	  type = "wp";
			    	  wpIndex.add(row);
			    	  col = col+1;
			    	  URL cellFeedUrl8 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed8 = service.getFeed(cellFeedUrl8, CellFeed.class);
			    	  
			    	  for (CellEntry cell8 :  cellfeed8.getEntries()) 
			    	  {
			    		  key_wp = cell8.getCell().getInputValue();
			    		  System.out.println("key="+cell8.getCell().getInputValue());  
			    	  }
		    	  
			    	  col = col+1;
			    	  URL cellFeedUrl9 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+row+"&max-row="+row+"&min-col="+col+"&max-col="+col).toURL();
			    	  CellFeed cellfeed9 = service.getFeed(cellFeedUrl9, CellFeed.class);
			    	  
			    	  for (CellEntry cell9 :  cellfeed9.getEntries()) 
			    	  {
			    		  value_wp = cell9.getCell().getInputValue().toLowerCase();
			    		  System.out.println("value="+cell9.getCell().getInputValue().toLowerCase());  
			    	  }
		    	  
			    	  mapping_wp.put(key_wp, value_wp);
			   			 String worksheetname_write = "Revert"; 
						 WorksheetEntry myworksheet_write = workSheetEntry(myspreadsheet,worksheetname_write);
						 URL cellFeedUrl_BC = new URI(myworksheet_write.getCellFeedUrl().toString()+"?min-row=3&min-col=9&max-col=13").toURL();
						 CellFeed cellfeed_BC = service.getFeed(cellFeedUrl_BC, CellFeed.class);

				   	     CellEntry cellEntry_BC_type = new CellEntry(row_revert_secb,9,type);
					     cellfeed_BC.insert(cellEntry_BC_type);
					     
				   	     CellEntry cellEntry_BC_key = new CellEntry(row_revert_secb,10,key_wp);
					     cellfeed_BC.insert(cellEntry_BC_key);

				   	     CellEntry cellEntry_BC_value = new CellEntry(row_revert_secb,11,value_wp);
					     cellfeed_BC.insert(cellEntry_BC_value);
					     row_revert_secb = row_revert_secb +1;

	    		  }
	      		
	    	  else {
	    		  
	    	  	   }  
	    	  }
	  }
	      
	  }
	  
	  System.out.println("effective Android list = "+mapping_and);
	  System.out.println("effective ios list = "+mapping_ios);
	  System.out.println("effective windows list = "+mapping_wp);


   }


/*	private String getCurrentValueForMobile(String matchingObjectId, String key,String ApplicationId,String RestAPIKey,String MasterKey)
			{
		String ObjectId = matchingObjectId;
		String keyname = key;
		
		RequestGenerator req = getValueOfObject(ObjectId,ApplicationId,RestAPIKey,MasterKey);
		
		String response = req.respvalidate.returnresponseasstring();
		System.out.println("initial response =" + response);

		String nodepath = "$.value." + keyname;
		System.out.println("node path =" + nodepath);
		
		String nodeValue_from_response = JsonPath.read(response, nodepath).toString();

		System.out.println("node value from response =" + nodeValue_from_response);
		
		return nodeValue_from_response;
			}
*/	
	
	private JSONObject modifiedGetCurrentValueForMobile(String matchingObjectId, LinkedHashMap<String,String> map1,String ApplicationId,String RestAPIKey,String MasterKey) throws ParseException
	{
		String ObjectId = matchingObjectId;
	//	LinkedHashMap<String,String> modifiedMapping_FG = new LinkedHashMap<String,String>();

		boolean check = false;
		String response = "";
		do {
		RequestGenerator req = getValueOfObject(ObjectId,ApplicationId,RestAPIKey,MasterKey);
		
		response = req.respvalidate.returnresponseasstring();
		
	//	int check = checkResponse(response);
		check = req.respvalidate.DoesNodeExists("code");
		System.out.println("value of check variable First Place is:" +check);
		}while (check);
		
		String value1 = JsonPath.read(response, "$..value").toString();
		System.out.println("value1="+value1); // main extracted json

	//	System.out.println("initial response =" + response);

	//	Set<Entry<String,String>>entrySet = map1.entrySet();
		
		String jsonString = value1.substring(1, value1.length() - 1);
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonString.toString());
		
		System.out.println("json object =" +jsonObj);
		return jsonObj;

	}

/*	private int checkResponse(String response)
	{
		int check = 0;
		if (response.("Please reach us at migration"))
		{
			check = 1;
		}
		else
		{
			check=0;
		}
		return check;
	}
*/	
	
/*	private String extractAndUpdateJson(String matchingObjectId, String key,String initialValue,
			String finalValue,String ApplicationId,String RestAPIKey,String MasterKey) throws JSONException, JsonProcessingException, IOException, ParseException 
	{
	
		String ObjectId = matchingObjectId;
		String keyname = key;
		//String initial_value = "true";
		String final_value = finalValue;
		String initial_value = initialValue;

		RequestGenerator req = getValueOfObject(ObjectId,ApplicationId,RestAPIKey,MasterKey);

		String response = req.respvalidate.returnresponseasstring();
		System.out.println("initial response =" + response);

		String nodepath = "$.value." + keyname;
		System.out.println("node path =" + nodepath);
		
		String nodeValue_from_response = JsonPath.read(response, nodepath).toString();

		System.out.println("node value from response =" + nodeValue_from_response);

		String value1 = JsonPath.read(response, "$..value").toString();
		System.out.println("value1="+value1); // main extracted json
		
		String dummy = JsonPath.read(value1, "$..profileCoverImageImageEntry").toString();
		System.out.println("value of skipped string is ="+dummy); // inner json to skip
		
		String dummy1 = JsonPath.read(value1, "$..*").toString();
		System.out.println("All values of json structure ="+dummy1); // All values of json
			    				
		String value2 = value1.replaceAll("\\\\", "");
		System.out.println("value2="+value2);
		String string_toModify = value2.substring(1, value2.length() - 1);

		//System.out.println("value = " + value1);
		//System.out.println("value2 = " + value2);
		System.out.println("String to modify = " + string_toModify);

		if (nodeValue_from_response.equals(initial_value)) {

			String value4 = "";
			String value5 = "";

			String[] valuearray = string_toModify.split(",");
			for (int i = 0; i < valuearray.length; i++) {
				if (valuearray[i].contains(keyname)) {
					value5 = valuearray[i].replace(initial_value, final_value)
							+ ",";
					System.out.println("value5="+value5);
				}
				if (StringUtils.isEmpty(value5)) {
					value4 = value4 + valuearray[i] + ",";
					System.out.println("value4="+value4);
				} else {
					value4 = value4 + value5;
					System.out.println("value4="+value4);
					value5 = "";
				}
			}

			String modifiedString = value4.substring(0, value4.length() - 1);

			System.out.println("modified string :" + modifiedString);
			return modifiedString;
		} else {
			// do not update
			System.out.println("modified string: " + string_toModify);
			return string_toModify;
		}

	}
*/	
	private String modifiedExtractAndUpdateJson(String matchingObjectId, LinkedHashMap<String,String> map1, String ApplicationId,String RestAPIKey,String MasterKey) throws JSONException, JsonProcessingException, IOException, ParseException 
	{
	
		
		String ObjectId = matchingObjectId;
		String keyname = "";
		//String initial_value = "true";
		String final_value = "";
	//	String initial_value = initialValue;
		
		String response ="";
		boolean check = false;
		do{
		RequestGenerator req = getValueOfObject(ObjectId,ApplicationId,RestAPIKey,MasterKey);
		
		 response = req.respvalidate.returnresponseasstring();
		System.out.println("initial response =" + response);
		
		 check = req.respvalidate.DoesNodeExists("code");
		System.out.println("value of check variable Second place is:" +check);
		}while(check);

		String nodepath = "$.value." + keyname;
		System.out.println("node path =" + nodepath);
		
		String nodeValue_from_response = JsonPath.read(response, nodepath).toString();

		System.out.println("node value from response =" + nodeValue_from_response);

		String value1 = JsonPath.read(response, "$..value").toString();
		System.out.println("value1="+value1); // main extracted json
		
		String jsonString = value1.substring(1, value1.length() - 1);
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonString.toString());
		//JSONObject jsonObj = new JSONObject(jsonString);
		
				

		Set<Entry<String,String>>entrySet = map1.entrySet();
		for(Entry<String,String>entry:entrySet)
		{
			keyname = entry.getKey();
			final_value = entry.getValue();
			
			Object dkey = keyname;
			int temp1=0;
			if(jsonObj.get(dkey)==null)
			{
				temp1=1;
			}
			
			if(temp1==0)
			{
			System.out.println("current value of key :" +dkey + "is: "+jsonObj.get(dkey));
		//	Object initial_value = jsonObj.get(dkey);
			
		//	Object dvalue = initial_value.toString().replace(target, replacement)
			Object dvalue = final_value;
			
			if (dvalue.equals("true"))
			{
				//String val1 = dvalue.toString();
				//val1.replaceAll("\"", "");
				dvalue = new Boolean(true);
				System.out.println(dvalue);
			}
			
			else if(dvalue.equals("false"))
			{
				dvalue = new Boolean(false);
				System.out.println(dvalue);
			}
			
			jsonObj.put(dkey, dvalue);
			
			System.out.println("New value for key :" +dkey + "is: "+ jsonObj.get(dkey));
			}
			else{
				// do nothing
			}
		
		}

		String ddval = jsonObj.toString();
		System.out.println("converting json object back to string - " + ddval);
		
		/*for (Object keyd : jsonObj.keySet()) {
	        String keyStr = (String)keyd;
	        Object keyvalue = jsonObj.get(keyStr);

	        System.out.println("key: "+ keyStr + " value: " + keyvalue.toString());
	    }
        */
		return ddval;		
	}

	
	
	private RequestGenerator updateValueObject(String ObjectId, String finalpayload, String ApplicationId, String RestAPIKey, String MasterKey )
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PARSE,
				APINAME.UPDATEVALUEOBJECT, init.Configurations, finalpayload);

		APIUtilities apiUtil = new APIUtilities();
		service.URL = apiUtil.prepareparameterizedURL(service.URL, ObjectId);

		System.out.println("service url = " + service.URL);
		System.out.println("service payload = " + service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		
		return new RequestGenerator(service, getParam);
	}
	
	private String getPayloadAsString(String payloadName, String payloadparams) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();

		return finalPayload.replace("${0}", payloadparams);
	}
	
/*	private String getObjectIdforPlatform(String platform,String ApplicationId, String RestAPIKey, String MasterKey) 
	{
		
		String desired_platform = platform;
		String matchingObjectId = "";

		RequestGenerator req = getAllObjectIDs(ApplicationId,RestAPIKey,MasterKey);
		
		String jsonRes = req.respvalidate.returnresponseasstring();

		System.out.println(jsonRes);
		List<String> objectids = JsonPath.read(jsonRes,
				"$.results..objectId[*]");
		for (String s : objectids) {
			System.out.println("..........For object id...... " + s);
			
			RequestGenerator req1 = getValueOfObject(s,ApplicationId,RestAPIKey,MasterKey);

			String response = req1.respvalidate.returnresponseasstring();
			System.out.println("initial response =" + response);

			String actual_platform = JsonPath.read(response, "$.key")
					.toString();
			System.out.println("Actual platform =" + actual_platform);

			if (actual_platform.equals(desired_platform)) {
				System.out.println("Matching objectid =" + s);
				matchingObjectId = s;
				return matchingObjectId;
			}

		}
		return matchingObjectId;

	}
*/	
/*	private RequestGenerator getAllObjectIDs (String ApplicationId, String RestAPIKey, String MasterKey)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PARSE,
				APINAME.GETALLOBJECTID, init.Configurations);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		return new RequestGenerator(service, getParam);
	}
*/	
	private RequestGenerator getValueOfObject(String s, String ApplicationId, String RestAPIKey, String MasterKey)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PARSE, APINAME.GETVALUEOFOBJECT,
				init.Configurations, PayloadType.JSON, new String[] { s },
				PayloadType.JSON);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		return new RequestGenerator(service, getParam);
	}
	
	private String getDescriptionForFeatureGateKey(String key, Object Keytype) {
		MyntraService service = Myntra.getService();
		if (Keytype.equals("FG"))
		   {
		 service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		   }
		else if (Keytype.equals("KV"))
		   {
			 service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
					init.Configurations, PayloadType.JSON, new String[] { key },
					PayloadType.JSON);
		   }
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}
	
	/*private String getDescriptionForWidgetKeyValue(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}
*/	
	private String getCurrentValueForFeatureGateKey(String key, Object Keytype) {
		MyntraService service = Myntra.getService();
		if (Keytype.equals("FG"))
		   {
		
		 service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		   }
		else if (Keytype.equals("KV"))
		   {
			 service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
					init.Configurations, PayloadType.JSON, new String[] { key },
					PayloadType.JSON);
		   }
		
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String currentValue = req.respvalidate
				.NodeValue("data.value", true).replaceAll("\"", "")
				.trim();
		System.out.println("Current Value for " + key + "is :" + currentValue);

		return currentValue;
	}
	
	/*private String getCurrentValueForWidgetKeyValue(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String currentValue = req.respvalidate
				.NodeValue("data.value", true).replaceAll("\"", "")
				.trim();
		System.out.println("Current Value for " + key + "is :" + currentValue);

		return currentValue;
	}
*/	private String getPayloadAsString(String payloadName,
			String[] payloadparams, String toReplace) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();
		for (int i = 0; i < payloadparams.length; i++) {
			finalPayload = finalPayload.replace("${" + i + "}",
					payloadparams[i]);
		}
		finalPayload = finalPayload.replace("${2}", toReplace);
		// System.out.println(finalPayload);
		return finalPayload;
	}
	
	 
	
}
