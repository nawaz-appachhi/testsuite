package com.myntra.apiTests.portalservices.lgpservices;

import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.lgpservices.PayloadAction;
import com.myntra.lordoftherings.Initialize;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class Pumps {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static APIUtilities utilities = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	String appId="2";
	String versionSpecification;
	int previouFeedMaxIterations=20;
	int previousStreamMaxIterations=20;
	int	nextStreamMaxIteration=20;
	static String persona = null;
	EndToEndHelper helper = new EndToEndHelper();
	preparePayload jsonPayload=new preparePayload();
	public Pumps(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	
	public void performActions(EndToEndRecorder record,ArrayList<String> failures/*,String pumpsPayload*/,JSONArray pumpsActionsArray,HashMap<String,HashMap<String,String>> testData) throws JSONException, ParseException{
		/*JSONArray pumpsJsonArray=null;
		try {
			pumpsJsonArray = new JSONArray(pumpsPayload);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		int arrayLength = pumpsJsonArray.length();
		for(int i=0; i<arrayLength;i++){
			JSONObject pumpsActionObject =(JSONObject) pumpsJsonArray.get(i);

			//Read the key of the action object which is the action
			String pumpsActionKey=(String)pumpsActionObject.keys().next();
			PumpsAction pumpsAction = PumpsAction.getValue((String)pumpsActionObject.keys().next());
			JSONObject pumpsActionJson = (JSONObject)pumpsActionObject.get(pumpsActionKey);
			performPumpsAction(pumpsAction,failures , pumpsActionJson, record);
			
		}
		*/
		
		int arrayLength = pumpsActionsArray.length();
		for(int i=0; i<arrayLength;i++){
			PayloadAction pa = null;
			//change it wrt to the data provider
			//HashMap<String,String> payloadData = new HashMap<String,String>();
			String pumpsActionKey=pumpsActionsArray.getString(i);
			PumpsAction pumpsAction = PumpsAction.getValue(pumpsActionKey);
			PayloadAction payloadAction = PayloadAction.getValue(pumpsActionKey);
			System.out.println("pumpsActionKey "+pumpsActionKey);
			//JSONObject json = (JSONObject) parser.parse(jsonPayload.performActions(payloadAction,payloadData));
			
			JSONParser parser = new JSONParser();
			//Object obj  = parser.parse(jsonPayload.performActions(payloadAction,payloadData));
			//JSONObject ob = new JSONObject();
			//ob=(JSONObject) obj;
			//array.add(obj);
			//Gson gson = new Gson();
			JSONObject jsonObject = new JSONObject(jsonPayload.performActions(payloadAction,testData));
			performPumpsAction(pumpsAction,failures , jsonObject, record);
			
		}
		
	}
	
	private void performPumpsAction(PumpsAction pumpsAction, ArrayList<String> failures, JSONObject pumpsActionJson,EndToEndRecorder record) throws JSONException{
		switch (pumpsAction){
		case FOLLOWTOPIC:{
			Set<String> userList = new HashSet<>();
			//Fetch the user lists
			if(pumpsActionJson.has("uidx")){
				JSONArray userListJson = pumpsActionJson.getJSONArray("uidx");
				for(int i=0; i<userListJson.length();i++){
					userList.add(userListJson.getString(i));
				}
			}else{
				userList=record.getUsers();
			}
			//Fetch the headers
			HashMap<String, String> headers=new HashMap<>();
			if(pumpsActionJson.has("headers")){
				Iterator<String> headerKeys = pumpsActionJson.getJSONObject("headers").keys();
				while(headerKeys.hasNext()){
					String headerKey = headerKeys.next();
					headers.put(headerKey, pumpsActionJson.getJSONObject("headers").getString(headerKey));
				}
			}
			
			if(pumpsActionJson.has("payload")){
				JSONArray topicsPayloadList = pumpsActionJson.getJSONArray("payload");
					for(String uidx: userList){
						for(int i=0;i<topicsPayloadList.length();i++){
							String payload = topicsPayloadList.getString(i);
							//Calling follow topic api
							MyntraService followTopicService = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHPUMPSACTIONTOPICFOLLOW,init.Configurations,payload);
							followTopicService.URL = utilities.prepareparameterizedURL(followTopicService.URL,uidx);
							System.out.println(followTopicService.URL);
							RequestGenerator requestGen = new RequestGenerator(followTopicService, headers);
							int responseStatus = requestGen.response.getStatus();
							if(responseStatus!=200){
								failures.add("Follow Topic: follow topic call failed for user "+uidx+" for topic "+helper.readJsonPath(payload, "$.whom"));
							}else{
								record.updateUserProperty(uidx, UserProperty.TOPIC_FOLLOWED, RecordKeeperAction.ADD, helper.readJsonPath(payload, "$.whom"));
								
							}
						}
					}
			}
			break;
		}
			
		case REGISTEROBJECT:{
			//Reading from the list of jsons for which register object
			JSONArray registerObjectList = pumpsActionJson.getJSONArray("objects");
			for(int i=0;i<registerObjectList.length();i++){
				JSONObject registerObjectJson = registerObjectList.getJSONObject(i);
				boolean randomIds=true;
				int count=1;
				ArrayList<String> objectIdList = new ArrayList<>();
				//Read the object ids if specified
				if(registerObjectJson.has("objectIds")){
					randomIds=false;
					JSONArray idListJson = registerObjectJson.getJSONArray("objectIds");
					count = idListJson.length();
					for(int j=0;j<count;j++){
						objectIdList.add(idListJson.getString(j));
					}
				}
				//Get the count of objects to be registered with the payload
				else if(registerObjectJson.has("count")){
					count = registerObjectJson.getInt("count");
				}
				
				//Fetch the headers
				HashMap<String, String> headers=new HashMap<>();
				if(registerObjectJson.has("headers")){
					Iterator<String> headerKeys = registerObjectJson.getJSONObject("headers").keys();
					while(headerKeys.hasNext()){
						String headerKey = headerKeys.next();
						headers.put(headerKey, registerObjectJson.getJSONObject("headers").getString(headerKey));
					}
				}
				
				//Get the payload for the Objects to be registered with
				JSONObject registerObjectPayload = registerObjectJson.getJSONObject("payload");
				
				// Register the objects with the given payload
				for(int j=0;j<count; j++){
					String objectId = "";
					if(randomIds){
						objectId = "article_"+System.currentTimeMillis();
					}else{
						objectId=objectIdList.get(j);
					}
					registerObjectPayload.put("appId", appId);
                    registerObjectPayload.put("refId", objectId);
					MyntraService registerObjectService = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT,init.Configurations,registerObjectPayload.toString());
					
					RequestGenerator requestGen = new RequestGenerator(registerObjectService, headers);
					int responseStatus = requestGen.response.getStatus();
					System.out.println(requestGen.returnresponseasstring());
					//TODO: CHECK THE FAILURE RESPONSE FOR EACH CALL
					if(responseStatus!=200 || !helper.readJsonPath(requestGen.returnresponseasstring(), "$.status.statusType").equals("SUCCESS")){
						failures.add("RegisterObject: Failed to registerObject "+objectId+". Response:"+requestGen.returnresponseasstring());
					}else{
						if(registerObjectPayload.has("topics")){
							record.updateObjectProperty(objectId, ObjectProperty.TOPICS, RecordKeeperAction.ADD, helper.getTopics(registerObjectPayload));
						}
						record.updateObjectProperty(objectId, ObjectProperty.UNPUBLISHED, RecordKeeperAction.ADD, "true");
						
					}
				}
				
				
			}
			
			break;
		}
		case PUBLISHOBJECT:{
			JSONArray publishObjectList = pumpsActionJson.getJSONArray("objects");
			for(int i=0;i<publishObjectList.length();i++){
				JSONObject publishObjectJson = publishObjectList.getJSONObject(i);
				boolean unpublishedIds=true;
				int count=1;
				List<String> objectIdList = new ArrayList<>();
				
				//Read the object ids if specified
				if(publishObjectJson.has("objectIds")){
					
					if(!publishObjectJson.getString("objectIds").equalsIgnoreCase("unpublished")){
						unpublishedIds=false;
						JSONArray idListJson = publishObjectJson.getJSONArray("objectIds");
						System.out.println("the objects are"+idListJson);
						count = idListJson.length();
						for(int j=0;j<count;j++){
							objectIdList.add(idListJson.getString(j));
						}
					}else{
						objectIdList=record.getObjectsWithAnyPropery(ObjectProperty.UNPUBLISHED, "true");
						count=objectIdList.size();
					}
				}
				//Get the count of objects to be published with the payload
				if(publishObjectJson.has("count")){
					count = publishObjectJson.getInt("count");
				}
				
				//Fetch the headers
				HashMap<String, String> headers=new HashMap<>();
				if(publishObjectJson.has("headers")){
					Iterator<String> headerKeys = publishObjectJson.getJSONObject("headers").keys();
					while(headerKeys.hasNext()){
						String headerKey = headerKeys.next();
						headers.put(headerKey, publishObjectJson.getJSONObject("headers").getString(headerKey));
					}
				}
				//Get the payload for the Objects to be published with
				JSONObject publishObjectPayload = publishObjectJson.getJSONObject("payload");
				// Publish the objects with the given payload
				persona = publishObjectPayload.get("persona").toString();
				for(int j=0;(j<count && j<objectIdList.size()) ; j++){
					String objectId = objectIdList.get(j);

					publishObjectPayload.put("whom", appId+":"+objectId);
					MyntraService publishObjectService = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHACTION,init.Configurations,publishObjectPayload.toString());
					publishObjectService.URL=utilities.prepareparameterizedURL(publishObjectService.URL, new String[]{appId});
					System.out.println("request is"+publishObjectService.BaseURL);
					RequestGenerator requestGen = new RequestGenerator(publishObjectService, headers);
					int responseStatus = requestGen.response.getStatus();
					//TODO: CHECK THE FAILURE RESPONSE FOR EACH CALL
					System.out.println("the publish object response is"+requestGen.response);
					System.out.println(requestGen.returnresponseasstring());
					if(responseStatus!=200 || !helper.readJsonPath(requestGen.returnresponseasstring(), "$.status.statusType").equals("SUCCESS")){
						failures.add("PublishObject: Failed to publishObject "+objectId+". Response:"+requestGen.returnresponseasstring());
					}
					
					else{
						record.updateObjectProperty(objectId, ObjectProperty.UNPUBLISHED, RecordKeeperAction.REMOVE, "true");
						if(helper.getIncludeLocation(publishObjectPayload)!=null)
						{
							record.updateObjectProperty(objectId, ObjectProperty.INCLUDE_LOCATION, RecordKeeperAction.ADD, helper.getIncludeLocation(publishObjectPayload));
						}
						else
						{
							record.updateObjectProperty(objectId, ObjectProperty.INCLUDE_LOCATION, RecordKeeperAction.ADD, new String[0] );
						}
						if(helper.getExcludeLocation(publishObjectPayload)!=null)
						{
							record.updateObjectProperty(objectId, ObjectProperty.EXCLUDE_LOCATION, RecordKeeperAction.ADD, helper.getExcludeLocation(publishObjectPayload));
						}
						else
						{
							record.updateObjectProperty(objectId, ObjectProperty.EXCLUDE_LOCATION, RecordKeeperAction.ADD, new String[0] );
						}
						
					}
				}
					
		}
		}
			break;
			
		case FOLLOW:
			break;
		}
		
	}

}
