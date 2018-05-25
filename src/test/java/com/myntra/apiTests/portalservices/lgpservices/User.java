package com.myntra.apiTests.portalservices.lgpservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class User {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static APIUtilities utilities = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	String appId="2";
	String versionSpecification;
	int previouFeedMaxIterations=20;
	int previousStreamMaxIterations=20;
	int	nextStreamMaxIteration=20;
	String persona = null;
	EndToEndHelper helper = new EndToEndHelper();
	public User(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	
	public String performActions(EndToEndRecorder record,ArrayList<String> failures,String userActionPayload) throws JSONException,IOException{
		
		JSONArray jObject = new JSONArray(userActionPayload);
		int arrayLength = jObject.length();
		System.out.println(arrayLength);
		String[] empty ={"a","b"};
		System.out.println("The payload being sent is"+userActionPayload);
        for(int i=0; i<arrayLength;i++){
        	
        	JSONObject objects = jObject.getJSONObject(i);
        	System.out.println("object is"+objects); 
        	List<String> key = new ArrayList<String>();
        	Iterator<?> keys = objects.keys();
        	while( keys.hasNext() ) {
        	    String keyss = (String) keys.next();
        	    System.out.println("Key: " + keyss);
        	    key.add(keyss);
        	}
            switch(key.get(0).toString())
        	{
            case "randomUsers":
            	int count = Integer.parseInt(objects.getString(key.get(0)));
            	System.out.println(count); 
            	int j=0;
            	while(j<count)
            	{
            		String timestamp = new SimpleDateFormat("MMddHHmmss").format(new java.util.Date());
            		String userName = "abc"+timestamp+"@myntra.com";
            		System.out.println("user name is"+userName);
            		RequestGenerator devApiSignUpReqGen = devApiServiceHelper.invokeDevApiSignUp(userName, timestamp);
            		String ActionResponse = devApiSignUpReqGen.respvalidate.returnresponseasstring();
            		System.out.println("\nPrinting LGP Serve Action API response :\n\n"+ActionResponse+"\n");
            		if(devApiSignUpReqGen.response.getStatus()!=200)
            		{
            			failures.add("User Creation :"+ActionResponse);
            		}
            		System.out.println("uidx is"+JsonPath.read(ActionResponse, "$.data.uidx").toString());
            		record.updateUserProperty(JsonPath.read(ActionResponse, "$.data.uidx").toString(),null, RecordKeeperAction.ADD, empty);
            		j++;
            	}
            	break;
            case "uidx":
            	String temp = objects.get(key.get(0).toString()).toString();
            	if(temp.equals(null))
            	break;
            	else{
            		Pattern p = Pattern.compile("\"([^\"]*)\"");
                	Matcher m = p.matcher(temp);
                	while (m.find())
                	record.updateUserProperty(m.group(1),null, RecordKeeperAction.ADD, empty);
                	break;
            	}
        	}
        }
		return("");
	}
		
}
