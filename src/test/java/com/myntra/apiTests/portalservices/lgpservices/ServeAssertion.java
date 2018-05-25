package com.myntra.apiTests.portalservices.lgpservices;

import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.apiTests.lgpservices.PayloadAction;
import com.myntra.lordoftherings.Initialize;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.*;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.ServiceType;

public class ServeAssertion {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static APIUtilities utilities = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	String appId="2";
	String versionSpecification;
	int previouFeedMaxIterations=20;
	int previousStreamMaxIterations=20;
	int	nextStreamMaxIteration=20;
	//String persona = null;
	int excludeLocationCount = 0;
	List<String> excludeLocationFinalList=new ArrayList<String>();
	Set<String> excludeLocationList=new HashSet<String>();
	EndToEndHelper helper = new EndToEndHelper();
	preparePayload jsonPayload=new preparePayload();
	public ServeAssertion(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	
public void performActions(EndToEndRecorder record, ArrayList<String> failures, String serveAssertionPayload,HashMap<String,HashMap<String,String>> testData) throws JSONException, ParseException {
		
		JSONArray serveAssertionArray = new JSONArray(serveAssertionPayload);
		int arrayLength = serveAssertionArray.length();
		
		//change code
		//HashMap<String,HashMap<String,String>> testData= new HashMap<String,HashMap<String,String>>();
		
		PayloadAction payloadAction = PayloadAction.getValue("SERVEASSERTION");
		JSONArray assertionRelatedInfoArray = new JSONArray(jsonPayload.performActions(payloadAction,testData));
		  
		 
		for(int i=0;i<arrayLength;i++){
			/*
			JSONObject serveAssertionJson = serveAssertionArray.getJSONObject(i);
			//Get the serve Assertion Endpoint
			String assertionPoint = (String) serveAssertionJson.keys().next();
			JSONObject assertionRelatedInfo = serveAssertionJson.getJSONObject(assertionPoint);
			ScenarioType scenario = ScenarioType.getValue(assertionRelatedInfo.getString("scenarioType"));
			*/
			String assertionPoint = serveAssertionArray.getString(i);
			JSONObject assertionRelatedInfo = (JSONObject)assertionRelatedInfoArray.get(i);
			assertionRelatedInfo=(JSONObject)assertionRelatedInfo.get(assertionPoint);
			ScenarioType scenario = ScenarioType.getValue(assertionRelatedInfo.getString("scenarioType"));
			
			switch(assertionPoint.toLowerCase()){
			case "feeds":{
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//JSONObject assertionRelatedInfo = serveAssertionJson.getJSONObject(assertionPoint);
				assertFeeds(record,failures,assertionRelatedInfo,scenario);
			}
			break;
			case "streams":{
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				assertStreams(record, failures, assertionRelatedInfo, scenario);
			}
			break;
			}
		}
		
		
	}





	private void assertFeeds(EndToEndRecorder record, ArrayList<String> failures, JSONObject assertionRelatedInfo,ScenarioType scenario) throws JSONException {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(scenario){
		case FOLLOWTOPIC:{
			//Get all the users
			Set<String> users = record.getUsers();
			
			for(String uidx:users){
				//Get the topics followed by user
				List<String> topicsFollowedByUser = record.getUserProperty(uidx, UserProperty.TOPIC_FOLLOWED);
				String[] topicsArray = new String[100];
				for(int a=0;a<topicsFollowedByUser.size();a++){
					topicsArray[a]=topicsFollowedByUser.get(a);
				}
				//Get all the objects with matching topics
				List<String> objectsWithMatchingTopics = record.getObjectsWithAnyPropery(ObjectProperty.TOPICS, topicsArray);
				
				if(objectsWithMatchingTopics.size()>0){
					JSONArray platforms =null;
					int platformCount=1;
					if(assertionRelatedInfo.has("platforms")){
						platforms = assertionRelatedInfo.getJSONArray("platforms");
						platformCount=platforms.length();
					}
					if(assertionRelatedInfo.has("previousIterations")){
						previouFeedMaxIterations = assertionRelatedInfo.getInt("previousIterations");
					}
					//Fetch the headers
					HashMap<String, String> headers=new HashMap<>();
					if(assertionRelatedInfo.has("headers")){
						Iterator<String> headerKeys = assertionRelatedInfo.getJSONObject("headers").keys();
						while(headerKeys.hasNext()){
							String headerKey = headerKeys.next();
							headers.put(headerKey, assertionRelatedInfo.getJSONObject("headers").getString(headerKey));
						}
					}
					//Add the uidx into the header
					headers.put("uidx", uidx);
					for(int pCounter=0;pCounter<platformCount;pCounter++){
						//Add the platform into the header
						if(platforms!=null){
							if(!platforms.getString(pCounter).equals("web"))
								headers.put("platform", platforms.getString(pCounter));
						}
						validateFeedsForObjectIds(headers, objectsWithMatchingTopics, failures);
					}
 					
				}
				
				
				
			}
				
			}

			
		
		case LOCATION:{
			//Get all the users
			Set<String> users = record.getUsers();
			
			for(String uidx:users){
				//Get the locations for testing
				int locationsCount=1;
				JSONArray locationsArray=null;
				if(assertionRelatedInfo.has("location")){
					locationsArray = assertionRelatedInfo.getJSONArray("location");
					locationsCount=locationsArray.length();
				}
				
					JSONArray platforms =null;
					int platformCount=1;
					
					if(assertionRelatedInfo.has("platforms")){
						platforms = assertionRelatedInfo.getJSONArray("platforms");
						platformCount=platforms.length();
					}
					if(assertionRelatedInfo.has("previousIterations")){
						previouFeedMaxIterations = assertionRelatedInfo.getInt("previousIterations");
					}
					
					//Fetch the headers
					HashMap<String, String> headers=new HashMap<>();
					if(assertionRelatedInfo.has("headers")){
						Iterator<String> headerKeys = assertionRelatedInfo.getJSONObject("headers").keys();
						while(headerKeys.hasNext()){
							String headerKey = headerKeys.next();
							headers.put(headerKey, assertionRelatedInfo.getJSONObject("headers").getString(headerKey));
						}
					}
					
					//Add the uidx into the header
					/*if(!Pumps.persona.equals("unknown"))
					{
						headers.put("uidx", uidx);
					}
					*/
					for(int locCounter=0;locCounter<locationsCount;locCounter++){//locations loop
						
						Set<String> objectsWithMatchingTopicsSet=new HashSet<String>();
						List<String> objectsWithIncludeLocations=new ArrayList<String>();
						List<String> objectsWithMatchingTopics=new ArrayList<String>();
						List<String> objectsWithExcludeLocations=new ArrayList<String>();
						if(locationsArray!=null){
							String locationsHeader = locationsArray.getString(locCounter);
							String[] locationsList = locationsHeader.split(",");
							headers.put("location", locationsHeader);
							//Get all the objects with matching topics
							objectsWithIncludeLocations = record.getObjectsWithAnyPropery(ObjectProperty.INCLUDE_LOCATION, locationsList);
							objectsWithExcludeLocations = record.getObjectsWithAnyPropery(ObjectProperty.EXCLUDE_LOCATION, locationsList);
							objectsWithMatchingTopicsSet.addAll(objectsWithIncludeLocations);
							objectsWithMatchingTopicsSet.addAll(objectsWithExcludeLocations);
							objectsWithMatchingTopics.addAll(objectsWithMatchingTopicsSet);
							excludeLocationCount = objectsWithExcludeLocations.size();
							excludeLocationList.addAll(objectsWithExcludeLocations);
							excludeLocationFinalList.clear();
							excludeLocationFinalList.addAll(excludeLocationList);
							
						}
						if(locationsArray==null)
						{
							objectsWithMatchingTopics.addAll(record.getObjects());
						}
						if(objectsWithMatchingTopics.size()>=0){
							for(int pCounter=0;pCounter<platformCount;pCounter++){		//platforms loop
							
								//Add the platform into the header
								if(platforms!=null){
									if(!platforms.getString(pCounter).equals("web"))
										headers.put("platform", platforms.getString(pCounter));
								}
								List<String> objectIdList = validateFeedsForObjectIds(headers, objectsWithMatchingTopics, failures);
								if(objectIdList.size()==objectsWithExcludeLocations.size()){
										if(objectIdList.equals(objectsWithExcludeLocations)){
											if(Collections.disjoint(objectsWithExcludeLocations, objectsWithIncludeLocations)){
												if(Collections.disjoint(objectIdList, objectsWithIncludeLocations)){
													System.out.println("true");
												}
											}
										}
										else{
											String whichPlatform = (headers.get("platform")==null) ? "" : ", platform :"+headers.get("platform");
											failures.add("LgpObjectsNotFoundInFeeds: User: "+headers.get("uidx")+", No of objects: "+objectIdList.size()+", Objects not found : "+objectIdList.size()+", ObjectIds not found : "+objectIdList+whichPlatform);
										}
									}
								else{
									String whichPlatform = (headers.get("platform")==null) ? "" : ", platform :"+headers.get("platform");
									failures.add("LgpObjectsNotFoundInFeeds: User: "+headers.get("uidx")+", No of objects: "+objectIdList.size()+", Objects not found : "+objectIdList.size()+", ObjectIds not found : "+objectIdList+whichPlatform);
								}
							}
						}
					}
 					
				}
				
				
				
			}
				
			}
		
		
	}
	
	
	private void assertStreams(EndToEndRecorder record, ArrayList<String> failures, JSONObject assertionRelatedInfo,ScenarioType scenario) throws JSONException {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(scenario){
		case FOLLOWTOPIC:{
			//Get all the users
			Set<String> users = record.getUsers();
			
			for(String uidx:users){
				//Get the topics followed by user
				List<String> topicsFollowedByUser = record.getUserProperty(uidx, UserProperty.TOPIC_FOLLOWED);
				String[] topicsArray = new String[100];
				for(int a=0;a<topicsFollowedByUser.size();a++){
					topicsArray[a]=topicsFollowedByUser.get(a);
				}
				//Get all the objects with matching topics
				List<String> objectsWithMatchingTopics = record.getObjectsWithAnyPropery(ObjectProperty.TOPICS, topicsArray);
				
				if(objectsWithMatchingTopics.size()>0){
					int platformCount=1;
					int variantsCount=1;
					// Retreive platforms for platform specific testing
					JSONArray platforms =null;
					if(assertionRelatedInfo.has("platforms")){
						platforms = assertionRelatedInfo.getJSONArray("platforms");
						platformCount=platforms.length();
					}
					if(assertionRelatedInfo.has("nextIterations")){
						nextStreamMaxIteration = assertionRelatedInfo.getInt("nextIterations");
					}
					
					if(assertionRelatedInfo.has("previousIterations")){
						previousStreamMaxIterations = assertionRelatedInfo.getInt("previousIterations");
					}
					
					// Retreive variants 
					JSONArray variants =null;
					if(assertionRelatedInfo.has("variants")){
						variants = assertionRelatedInfo.getJSONArray("variants");
						variantsCount=variants.length();
					}
					//Fetch the headers
					HashMap<String, String> headers=new HashMap<>();
					if(assertionRelatedInfo.has("headers")){
						Iterator<String> headerKeys = assertionRelatedInfo.getJSONObject("headers").keys();
						while(headerKeys.hasNext()){
							String headerKey = headerKeys.next();
							headers.put(headerKey, assertionRelatedInfo.getJSONObject("headers").getString(headerKey));
						}
					}
					//Add the uidx into the header
					headers.put("uidx", uidx);
					for(int pCounter=0;pCounter<platformCount;pCounter++){
						for(int vCounter=0;vCounter<variantsCount;vCounter++){
							//Add the platform into the header
							if(platforms!=null){
								if(!platforms.getString(pCounter).equals("web"))
									headers.put("platform", platforms.getString(pCounter));
							}
							//Add variants into the header
							if(variants!=null){
								headers.put("x-myntra-abtest", "lgp.stream.variant="+platforms.getString(vCounter));
							}
							validateStreamsForObjectIds(headers, objectsWithMatchingTopics, failures);
						}
					}
				}
				
				
				
			}
				
			}
			
		case LOCATION:{
			//Get all the users
			Set<String> users = record.getUsers();
			
			for(String uidx:users){
				//Get the locations for testing
				int locationsCount=1;
				JSONArray locationsArray=null;
				if(assertionRelatedInfo.has("location")){
					locationsArray = assertionRelatedInfo.getJSONArray("location");
					locationsCount=locationsArray.length();
				}
				
					JSONArray platforms =null;
					int platformCount=1;
					int variantsCount=1;
					if(assertionRelatedInfo.has("platforms")){
						platforms = assertionRelatedInfo.getJSONArray("platforms");
						platformCount=platforms.length();
					}
					if(assertionRelatedInfo.has("nextIterations")){
						nextStreamMaxIteration = assertionRelatedInfo.getInt("nextIterations");
					}
					if(assertionRelatedInfo.has("previousIterations")){
						previousStreamMaxIterations = assertionRelatedInfo.getInt("previousIterations");
					}
					JSONArray variants =null;
					if(assertionRelatedInfo.has("variants")){
						variants = assertionRelatedInfo.getJSONArray("variants");
						variantsCount=variants.length();
					}
					//Fetch the headers
					HashMap<String, String> headers=new HashMap<>();
					if(assertionRelatedInfo.has("headers")){
						Iterator<String> headerKeys = assertionRelatedInfo.getJSONObject("headers").keys();
						while(headerKeys.hasNext()){
							String headerKey = headerKeys.next();
							headers.put(headerKey, assertionRelatedInfo.getJSONObject("headers").getString(headerKey));
						}
					}
					
					//Add the uidx into the header
					/*if(!Pumps.persona.equals("unknown"))
					{
						headers.put("uidx", uidx);
					}
					*/
					for(int locCounter=0;locCounter<locationsCount;locCounter++){//locations loop
						
						Set<String> objectsWithMatchingTopicsSet=new HashSet<String>();
						List<String> objectsWithIncludeLocations=new ArrayList<String>();
						List<String> objectsWithMatchingTopics=new ArrayList<String>();
						List<String> objectsWithExcludeLocations=new ArrayList<String>();
						
						if(locationsArray!=null){
							String locationsHeader = locationsArray.getString(locCounter);
							String[] locationsList = locationsHeader.split(",");
							headers.put("location", locationsHeader);
							//Get all the objects with matching topics
							
							objectsWithIncludeLocations = record.getObjectsWithAnyPropery(ObjectProperty.INCLUDE_LOCATION, locationsList);
							objectsWithExcludeLocations = record.getObjectsWithAnyPropery(ObjectProperty.EXCLUDE_LOCATION, locationsList);
							objectsWithMatchingTopicsSet.addAll(objectsWithIncludeLocations);
							objectsWithMatchingTopicsSet.addAll(objectsWithExcludeLocations);
							objectsWithMatchingTopics.addAll(objectsWithMatchingTopicsSet);
							excludeLocationCount = objectsWithExcludeLocations.size();
						}
						if(locationsArray==null)
						{
							objectsWithMatchingTopics.addAll(record.getObjects());
						}
						if(objectsWithMatchingTopics.size()>0){
							for(int pCounter=0;pCounter<platformCount;pCounter++){		//platforms loop
								for(int vCounter=0;vCounter<variantsCount;vCounter++){ //variant loop
							
								//Add the platform into the header
								if(platforms!=null){
									if(!platforms.getString(pCounter).equals("web"))
										headers.put("platform", platforms.getString(pCounter));
								}
								//Add the variants to headers
								if(variants!=null){
									headers.put("x-myntra-abtest", "lgp.stream.variant="+variants.getString(vCounter));
								}
								
								
								List<String> objectIdList = validateStreamsForObjectIds(headers, objectsWithMatchingTopics, failures);
								if(objectIdList.size()==objectsWithExcludeLocations.size()){
										if(objectIdList.equals(objectsWithExcludeLocations)){
											if(Collections.disjoint(objectsWithExcludeLocations, objectsWithIncludeLocations)){
												if(Collections.disjoint(objectIdList, objectsWithIncludeLocations)){
													System.out.println("true");
												}
											}
										}
										else{
											String whichPlatform = (headers.get("platform")==null) ? "" : ", platform :"+headers.get("platform");
											failures.add("LgpObjectsNotFoundInStream: User: "+headers.get("uidx")+", No of objects: "+objectIdList.size()+", Objects not found : "+objectIdList.size()+", ObjectIds not found : "+objectIdList+whichPlatform);
										}
									}
								else{
									String whichPlatform = (headers.get("platform")==null) ? "" : ", platform :"+headers.get("platform");
									failures.add("LgpObjectsNotFoundInStream: User: "+headers.get("uidx")+", No of objects: "+objectIdList.size()+", Objects not found : "+objectIdList.size()+", ObjectIds not found : "+objectIdList+whichPlatform);
								}
							}
						}
						}
					}
 					
				}
				
				
				
			}
		}
		
	}
	
	private List validateFeedsForObjectIds(HashMap<String,String> headers, List<String> objectIdList,ArrayList<String> failures) throws JSONException{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//Make the feed service call
			MyntraService getFeedsService = Myntra.getService(ServiceType.LGP_LGPSERVICEQA, APINAME.GETHOMEFEEDSQA,init.Configurations);
			getFeedsService.URL=utilities.prepareparameterizedURL(getFeedsService.URL, new String[]{versionSpecification});
			RequestGenerator requestGen = new RequestGenerator(getFeedsService, headers);
			String response = requestGen.returnresponseasstring();
			String previousURL = helper.readJsonPath(response, "$.page.previous");
			//Validate the response. Check if the object ids exist in the response
			List<String> objectIdsNotFoundInResponse=new ArrayList<String>();
				if(requestGen.response.getStatus()==200){
				int cardCountInResponse = Integer.parseInt(helper.readJsonPath(response, "$.count").toString());
				if(cardCountInResponse>0){
					objectIdsNotFoundInResponse = findObjectIDsInServeResponse(objectIdList, response);
				}else{
					failures.add("FeedCallFailed: Feed call failed to return cards "+response);
					return Collections.EMPTY_LIST;
				}
			}else{
				failures.add("FeedCallFailed: Failed to make feed call "+response);
				return Collections.EMPTY_LIST;
			}
				
				int iterationCount =0;
				while((objectIdsNotFoundInResponse.size()>0)&&(iterationCount++<previouFeedMaxIterations)){
					MyntraService getPreviousFeedsService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETPREVIOUSFEEDS,init.Configurations);
					getPreviousFeedsService.URL=utilities.prepareparameterizedURL(getPreviousFeedsService.URL, new String[]{previousURL});
					RequestGenerator requestGenPreviousFeed = new RequestGenerator(getPreviousFeedsService, headers);
					String previousFeedResponse = requestGenPreviousFeed.returnresponseasstring();
					previousURL = helper.readJsonPath(previousFeedResponse, "$.page.previous");
					if(requestGenPreviousFeed.response.getStatus()==200){
						int cardCountInResponse = Integer.parseInt(helper.readJsonPath(previousFeedResponse, "$.count").toString());
						if(cardCountInResponse>0){
							objectIdsNotFoundInResponse = findObjectIDsInServeResponse(objectIdsNotFoundInResponse, previousFeedResponse);
						}
						else if(excludeLocationCount>0 && excludeLocationFinalList.equals(objectIdsNotFoundInResponse)){
							break;
						}
						else{
							failures.add("PreviousFeedCallFailed: No more cards to show in previous stream :\n"+previousFeedResponse);
							break;
						}
					}else{
						failures.add("PreviousFeedCallFailed: Failed to make previous feed call :\n"+previousFeedResponse);
						break;
					}
				}
				
				return objectIdsNotFoundInResponse;
		
	}

	
	private List validateStreamsForObjectIds(HashMap<String,String> headers, List<String> objectIdList,ArrayList<String> failures) throws JSONException{
				//Make the feed service call
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MyntraService getStreamService = Myntra.getService(ServiceType.LGP_LGPSERVICEQA, APINAME.GETSTREAMQA,init.Configurations);
				getStreamService.URL=utilities.prepareparameterizedURL(getStreamService.URL, new String[]{versionSpecification});
				System.out.println("stream request is"+getStreamService.BaseURL);
				RequestGenerator requestGen = new RequestGenerator(getStreamService, headers);
				String response = requestGen.returnresponseasstring();
				//String nextURL = helper.readJsonPath(response, "$.page.next");
				String nextURL = helper.readJsonPath(response, "$.page.previous");
				if(nextURL!=null)
					nextURL=nextURL.substring(1);
				//Validate the response. Check if the object ids exist in the response
				List<String> objectIdsNotFoundInResponse=new ArrayList<String>();
					if(requestGen.response.getStatus()==200){
					int cardCountInResponse = Integer.parseInt(helper.readJsonPath(response, "$.count").toString());
					if(cardCountInResponse>0){
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						objectIdsNotFoundInResponse = findObjectIDsInServeResponse(objectIdList, response);
					}else{
						failures.add("StreamCallFailed: Stream call failed to return cards "+response);
						return Collections.EMPTY_LIST;
					}
				}else{
					failures.add("StreamCallFailed: Failed to make stream call "+response);
					return Collections.EMPTY_LIST;
				}
					
					int iterationCount =0;
					while((objectIdsNotFoundInResponse.size()>0)&&(iterationCount++<nextStreamMaxIteration)){
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						MyntraService getNextStreamService = Myntra.getService(ServiceType.LGP_LGPSERVICE, APINAME.GETNEXTSTREAM,init.Configurations);
						getNextStreamService.URL=utilities.prepareparameterizedURL(getNextStreamService.URL, new String[]{nextURL});
						RequestGenerator requestGenNextStream = new RequestGenerator(getNextStreamService, headers);
						String nextStreamResponse = requestGenNextStream.returnresponseasstring();
						//nextURL = helper.readJsonPath(nextStreamResponse, "$.page.next");
						nextURL = helper.readJsonPath(nextStreamResponse, "$.page.previous");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(requestGenNextStream.response.getStatus()==200){
							int cardCountInResponse = Integer.parseInt(helper.readJsonPath(nextStreamResponse, "$.count").toString());
							if(cardCountInResponse>0){
								objectIdsNotFoundInResponse = findObjectIDsInServeResponse(objectIdsNotFoundInResponse, nextStreamResponse);
							}
							else if(excludeLocationCount>0 && excludeLocationFinalList.equals(objectIdsNotFoundInResponse)){
								break;
							}
							else{
								failures.add("NextStreamCallFailed: No more cards to show in next call : \n"+nextStreamResponse);
								break;
							}
						}else{
							failures.add("NextStreamCallFailed: Failed to make previous Stream call :\n"+nextStreamResponse);
							break;
						}
					}
					return objectIdsNotFoundInResponse;
	}
	
	private List<String> findObjectIDsInServeResponse(List<String> objectIdList,String response) throws JSONException{
		//Retrieve all the object ids from the serve response
		List<String> objectIdsFromServeResponse=getObjectIdsFromServeResponse(response);
		List<String> unfoundObjectIds = new ArrayList<>();
		if(objectIdsFromServeResponse.size()>0){
			for(String objectIdFromRecord:objectIdList){
				boolean found=false;
				for(String objectIdServe:objectIdsFromServeResponse){
					if(objectIdServe.equalsIgnoreCase(objectIdFromRecord)){
						found=true;
						break;
					}
				}
				if(!found){
					unfoundObjectIds.add(objectIdFromRecord);
				}
			}
		}
		return(unfoundObjectIds);
		
	}
	
	private List<String> getObjectIdsFromServeResponse(String response) throws JSONException{
		JSONObject serveResponseJson =new JSONObject(response);
		List <String> objectIdsFromServeResponse=new ArrayList<>();
		if(serveResponseJson.has("cards")){
			JSONArray cardsArrayFromResponse =serveResponseJson.getJSONArray("cards");
			
			for(int i=0;i<cardsArrayFromResponse.length();i++){
				String objectId = cardsArrayFromResponse.getJSONObject(i).getJSONObject("props").getString("id");
				objectId=objectId.substring(objectId.indexOf(":")+1);
				objectIdsFromServeResponse.add(objectId);
			}
		}
		return(objectIdsFromServeResponse);
	}

}
