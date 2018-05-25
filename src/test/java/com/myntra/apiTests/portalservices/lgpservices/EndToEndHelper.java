package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;
import java.util.Arrays;
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

public class EndToEndHelper {
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
	public EndToEndHelper(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	
	
	
	public String[] getTopics(JSONObject payload) throws JSONException{
		
		JSONArray topics = payload.getJSONArray("topics");
		ArrayList<String> topicsList = new ArrayList<>();
		for(int i=0;i<topics.length();i++){
			topicsList.add(topics.getJSONObject(i).getString("type")+":"+topics.getJSONObject(i).getString("name"));
		}
		return (String[]) (topicsList.toArray(new String[topicsList.size()]));
	}
	
	public String[] getIncludeLocation(JSONObject payload) throws JSONException{
		
		String locationString = payload.get("extraData").toString();
		List<String> locationList = new ArrayList<>();
		Pattern p = Pattern.compile("\"([^\"]*)\"");
    	Matcher m = p.matcher(locationString);
    	int flag = 0;
    	if(locationString.contains("includeLocation"))
    	{
    		while (m.find()){
        		if(flag==1){
        	    	System.out.println(m.group(1));
        	    	locationList = Arrays.asList(m.group(1).split("\\s*,\\s*"));
        	    	break;
        		}
        		if(m.group(1).equals("includeLocation")){
        			flag=1;
        		}
        	}
    	}
    	if(locationList.isEmpty())
    	{
    		return null;
    	}
    	return locationList.toArray(new String[locationList.size()]);
	}
	
	public String[] getExcludeLocation(JSONObject payload) throws JSONException{
		
		String locationString = payload.get("extraData").toString();
		List<String> locationList = new ArrayList<>();
		Pattern p = Pattern.compile("\"([^\"]*)\"");
    	Matcher m = p.matcher(locationString);
    	int flag = 0;
    	if(locationString.contains("excludeLocation"))
    	{
    		while (m.find()){
        		if(flag==1){
        	    	System.out.println(m.group(1));
        	    	locationList = Arrays.asList(m.group(1).split("\\s*,\\s*"));
        	    	break;
        		}
        		if(m.group(1).equals("excludeLocation")){
        			flag=1;
        		}
        	}
    	}
    	if(locationList.isEmpty())
    	{
    		return null;
    	}
    	return locationList.toArray(new String[locationList.size()]);
	}
	public String readJsonPath(String jsonString,String jsonPath){
		String jsonResult;
		try{
			jsonResult = JsonPath.read(jsonString, jsonPath).toString();
		}catch(Exception e){
			return (null);
		}
		return(jsonResult);
	}

	
	public String getFailuresPoints(ArrayList<String> failures){
		String failureAsPoints="";
		//int i=1;
		failureAsPoints="Scenario:"+failures.get(failures.size()-2)+"\n Description"+":"+failures.get(failures.size()-1)+"\n";
		/*for(String errMsg:failures){
			failureAsPoints= failureAsPoints +(i++)+". "+errMsg+"\n";
		}*/
		for(int i=0;i<failures.size()-2;i++){
			String errMsg = failures.get(i);
			failureAsPoints= failureAsPoints + i +". "+errMsg+"\n";
		}
		return(failureAsPoints);
	}

}
