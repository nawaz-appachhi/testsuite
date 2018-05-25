package com.myntra.apiTests.portalservices.lgpServe.feed;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.lgpservices.*;
import com.myntra.lordoftherings.*;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class FeedObjectHelper extends CommonUtils {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(FeedObjectHelper.class);
	String envName = init.Configurations.properties.getPropertyValue("environment");
	
	Map<String, JsonElement> jsonKeyMap  = new ConcurrentHashMap<String,JsonElement>();
	ArrayList<String> keyList = new ArrayList<String>();

	public RequestGenerator getQueryRequestMultiParam(APINAME apiName, String objectReferenceIdParam, String versionSpecificationParam){
		
		MyntraService service = Myntra.getService(ServiceType.LGP_LGPSERVICE, apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, new String[]{versionSpecificationParam,objectReferenceIdParam});
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		return req;
		
	}
	
	public RequestGenerator getQueryRequestSingleParam(APINAME apiName, String param){
		
		
		MyntraService service = Myntra.getService(ServiceType.LGP_LGPSERVICE, apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		return req;
		
	}
	
	public RequestGenerator getQueryRequestSingleParam(APINAME apiName, String param, HashMap<String,String> instanceHeaders){
		
		HashMap<String,String> headers = new HashMap<String,String>();

		String instanceXid = instanceHeaders.get("xid");		
		headers.put("xid", instanceXid);
		
		
		MyntraService service = Myntra.getService(ServiceType.LGP_LGPSERVICE, apiName, init.Configurations);
		APIUtilities utilities = new APIUtilities();
		service.URL = utilities.prepareparameterizedURL(service.URL, param);
		
		RequestGenerator req;
		if(service.BaseURL.contains("lgp.myntra.com")){
			
			 req = new RequestGenerator(service,instanceHeaders);
			
		}else{
			
			 req = new RequestGenerator(service,headers);
			
		}
		
		
		System.out.println(service.URL);
		log.info(service.URL);
		return req;
		
	}
	
	public boolean jsonKeyListFindUtility() throws IOException{
		
		String jsonSample = new Toolbox().readFileAsString("Data/SchemaSet/JSON/lgp/versions/v2.1/sampleJson.txt");
		System.out.println(jsonSample);
		
		keyList = new ArrayList<String>();
		JsonParser parser = new JsonParser();
		
		JsonElement jsonElement =  parser.parse(jsonSample);
		
		if(jsonElement.isJsonObject()){
			
		
			JsonObject jsonObj =  jsonElement.getAsJsonObject();
			
			for(Map.Entry<String, JsonElement> entrySet : jsonObj.entrySet()){
				
				String key = entrySet.getKey();
				JsonElement valueElement = entrySet.getValue();
				keyList.add(key);
				String valueElementAsString = valueElement.toString();
				if(valueElementAsString.startsWith("{") || valueElementAsString.startsWith("[")){
					
					jsonKeyMap.put(key, valueElement);
					
					
				}
				
			}
			jsonKeyListFindUtilitySupport(jsonKeyMap);
		    System.out.println(keyList);
			
		}

		return false;
	}
	
	public Map<String,JsonElement> jsonKeyListFindUtilitySupport(Map<String,JsonElement> jsonMap){
		
		if(jsonMap.isEmpty()){
			
			return jsonMap;
			
		}
		
		for(Map.Entry<String, JsonElement> jsonMapEntrySet : jsonMap.entrySet() ){
			
			String parentKey = jsonMapEntrySet.getKey();
			
			if(parentKey.equalsIgnoreCase("children")){
				
				jsonMap.remove("children");
				
			}
			
			JsonElement mapJsonElement = jsonMap.get(parentKey);
			if(!mapJsonElement.isJsonNull() && mapJsonElement.isJsonArray()){
				
				JsonArray mapJsonArray = mapJsonElement.getAsJsonArray();
				
				for(int mapJsonArrayIndex = 0; mapJsonArrayIndex < mapJsonArray.size(); mapJsonArrayIndex++){
					
					JsonElement arrayJsonElement = mapJsonArray.get(mapJsonArrayIndex);
					
					if(!arrayJsonElement.isJsonNull() && arrayJsonElement.isJsonObject()){
						
						JsonObject arrayJsonObject = arrayJsonElement.getAsJsonObject();
						
						for(Map.Entry<String, JsonElement> arrayJsonObjectEntrySet : arrayJsonObject.entrySet()){
								
							String childKey = arrayJsonObjectEntrySet.getKey();
							JsonElement childKeyValueElement = arrayJsonObjectEntrySet.getValue();
								
							String keyPath = parentKey+"."+childKey;
							keyList.add(keyPath);
								
							String childKeyValueElementAsString = childKeyValueElement.toString();
							if(childKeyValueElementAsString.startsWith("{") || childKeyValueElementAsString.startsWith("[")){
									
								jsonMap.put(keyPath, childKeyValueElement);
									
							}
								
						}
						
					}
						
				}
				
			}
			
			else if(!mapJsonElement.isJsonNull() && mapJsonElement.isJsonObject()){
				
				JsonObject mapJsonObject = mapJsonElement.getAsJsonObject();
				
				for(Map.Entry<String, JsonElement> mapJsonObjectEntrySet : mapJsonObject.entrySet()){
					
					String childKey = mapJsonObjectEntrySet.getKey();
					JsonElement childKeyValueElement = mapJsonObjectEntrySet.getValue();
					
					String keyPath = parentKey+"."+childKey;
					keyList.add(keyPath);
					
					String childKeyValueElementAsString = childKeyValueElement.toString();
					if(childKeyValueElementAsString.startsWith("{") || childKeyValueElementAsString.startsWith("[")){
							
						jsonMap.put(keyPath, childKeyValueElement);
							
					}
					
				}
				
			}
			
			jsonMap.remove(parentKey);
		
		}
		
		return jsonKeyListFindUtilitySupport(jsonMap);
		
	}
	
	public String getComponentCardSchema(String filename, String versionSpecification) throws IOException{
		
		String defaultPath =  "Data/SchemaSet/JSON/lgp/versions/";
		String absolutePath = defaultPath.concat(versionSpecification).concat("/").concat(filename);
		System.out.println("Absolute Path : "+absolutePath);
		
		String jsonSchemaReturn = new Toolbox().readFileAsString(absolutePath);
		
		return jsonSchemaReturn;
		
	}
	
	public boolean componentCardSchemaValidation(String response, String schema, String versionSpecification){
		
		boolean schemaValidationResult = false;
		try {
			
			schemaValidationResult = validateJsonSchema(response, schema); 
			List<String> missingNodeList = validateServiceResponseNodesUsingSchemaValidator(response, schema);
			if(!missingNodeList.isEmpty()){
				
				System.out.println(missingNodeList+"Are the nodes missing in the response of feed request for article card for version -> "+versionSpecification);
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return schemaValidationResult;
		
		
	}
	
	public int jsonUrlValidation(String url){
		
		WebTarget target;
		ClientConfig config;
		Client client;
		Response response;
		
		System.out.println(url);
		
		config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		target = client.target(url);
		response = target.request().accept(MediaType.APPLICATION_JSON).get();
		
		if(response.getStatus() != 200){
			
			System.out.println(url.concat(" >>>>>>>> Does not return status 200"));
			
		}
		
		
		return response.getStatus();
	}
	
	public boolean jsonKeyValidationsForFeedCards(CardPayloads componentCard, String versionSpecification, String response,int j){
		
		String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
		
		
		switch(componentCard){
		
			case ARTICLE_CARD:
				
				String[] articleJsonPaths = vCardImplementation.getArticleCardJsonPaths();
				result = jsonKeyValidationProcess(articleJsonPaths, jsonResponse,j);
			    break;
				
				
			case BANNER_CARD:
				
				String[] bannerJsonPaths = vCardImplementation.getBannerCardJsonPaths();
				result = jsonKeyValidationProcess(bannerJsonPaths, jsonResponse,j);
				break;
				
			case POLL_CARD:
				
				String[] pollJsonPaths = vCardImplementation.getPollCardJsonpaths();
				result = jsonKeyValidationProcess(pollJsonPaths, jsonResponse,j);
				break;
				
			case QUESTION_CARD:
				
				String[] questionJsonPaths = vCardImplementation.getQuestionCardJsonPaths();
				result = jsonKeyValidationProcess(questionJsonPaths, jsonResponse,j);
				break;
				
			case SPLIT_BANNER_CARD:
				
				String[] splitBannerJsonpaths = vCardImplementation.getSplitBannerCardJsonPaths();
				result = jsonKeyValidationProcess(splitBannerJsonpaths, jsonResponse,j);
				break;
				
			case VIDEO_CARD:

				String[] videoJsonpaths = vCardImplementation.getVideoCardJsonPaths();
				result = jsonKeyValidationProcess(videoJsonpaths, jsonResponse,j);
				break;	
				
			default:
				result=true;
				break;
		}
		return result;
	}
	
	public ILgpJsonKeys cardsImplementationInstance(String versionSpecification){
		
		
		ILgpJsonKeys iCardDeclarationInstance = null;
		
		switch(versionSpecification){
		
		 	case "v2.8": iCardDeclarationInstance = new Card2o8Impl();
			 break;    
		 	
		 	case "v2.7": iCardDeclarationInstance = new Card2o7Impl();
			 			 break;
		
			case "v2.6": iCardDeclarationInstance = new Card2o6Impl();
						 break;
		
			case "v2.5": iCardDeclarationInstance = new Card2o5Impl();
						 break;
		
		    case "v2.4": iCardDeclarationInstance = new Card2o4Impl();
                         break;
			
		    case "v2.3": iCardDeclarationInstance = new Card2o3Impl();
		                 break;
		    
			case "v2.1": iCardDeclarationInstance = new Card2o1Impl();
						 break;
						 
			case "v2.0": iCardDeclarationInstance = new Card2o0Impl();
						 break;
						
			case "v2":   iCardDeclarationInstance = new Card2o0Impl();
			             break;
						
			case "v1.2": iCardDeclarationInstance = new Card1o2Impl();
						 break;
		}
		
		return iCardDeclarationInstance;
	}
	
	public boolean jsonKeyValidationProcess(String[] jsonPathListings, String response,int j){
		
		List<Object> referenceList = new ArrayList<Object>();
		List<Integer> referenceUrlList = new ArrayList<>();
		boolean finalResultFlag = false;
		for(int jsonPathListingsIndex = 0; jsonPathListingsIndex< jsonPathListings.length; jsonPathListingsIndex++){
			
			String jsonpath=jsonPathListings[jsonPathListingsIndex].replace("#", String.valueOf(j));
			
			if(jsonpath.contains("url") || jsonpath.contains("src")){
				JSONArray urlsRetrieved=null;
				String urlRetrieved = null;
				int responseImageCode;
				try
				{
				  urlRetrieved = JsonPath.read(response,jsonpath);
				}
				catch(Exception e)
				{
				  urlsRetrieved = JsonPath.read(response,jsonpath);
				}
				
				if(envName.equalsIgnoreCase("production")){
				
					    if(urlsRetrieved!=null)
					    {
					      for(int i=0;i<urlsRetrieved.size();i++)
					      {
					    	  responseImageCode= urlGenericValidations(urlsRetrieved.get(i).toString(),response,jsonpath);
					    	  referenceUrlList.add(responseImageCode);
					      }
					    }
					    
					    if(urlRetrieved!=null)
					    {
					    	responseImageCode= urlGenericValidations(urlRetrieved,response,jsonpath);
					    	referenceUrlList.add(responseImageCode);
					    }
						
				}
			}
			else{
				try
				{
				  String jsonValue = JsonPath.read(response,jsonpath.toString());
                  referenceList.add(jsonValue);
				}
				catch(Exception e)
				{
					try
					{
						JSONObject jsonValues = JsonPath.read(response,jsonpath.toString());
					    referenceList.add(jsonValues.toString());
					}
					 catch(Exception e1)
					 {
						 try
						 {
				           JSONArray jsonValues = JsonPath.read(response,jsonpath.toString());
						   referenceList.add(jsonValues);
						 }
						 catch(Exception e2)
						 {
							 String jsonValue = JsonPath.read(response,jsonpath.toString()).toString();
							 referenceList.add(jsonValue); 
						 }
					 }
				}
				System.out.println((Character) JsonPath.read(response,jsonpath.toString()));
			}
			
		}
		printList(referenceList);
		System.out.println("Reference URL List : "+referenceUrlList);
		boolean resultBoolean = resultGeneration(referenceList);
		boolean urlResultBoolean = urlResultGeneration(referenceUrlList);		
		
		finalResultFlag = (resultBoolean && urlResultBoolean);
		
		return finalResultFlag;
	}
	
	public void printList(List<Object> list){
		
		for(int listIndex = 0; listIndex < list.size(); listIndex++){
			
			System.out.println(list.get(listIndex));
			
		}
		
	}
	
	public boolean resultGeneration(List<Object> referenceList){
		
		for(int referenceListIndex = 0; referenceListIndex < referenceList.size(); referenceListIndex++){
			
			if(referenceList.get(referenceListIndex).toString().equalsIgnoreCase("null")){
				
				return false;
			}
			
			if(referenceList.get(referenceListIndex).toString().isEmpty()){
				
				return false;
			}
			
			//to care of  Empty array Validation
            if(referenceList.get(referenceListIndex).toString().equals("[]")){
				
				return false;
			}
            
            //to care of Empty Object Validation
            if(referenceList.get(referenceListIndex).toString().equals("{}")){
				
				return false;
			}
		}
		
		return true;
	}
	
	public boolean urlResultGeneration(List<Integer> urlReferenceList){
		
		for(int urlReferenceListIndex = 0; urlReferenceListIndex < urlReferenceList.size(); urlReferenceListIndex++){
			
			if(urlReferenceList.get(urlReferenceListIndex) != 200 || urlReferenceList.get(urlReferenceListIndex) == 0){
				
				return false;
			}
			
		}
		
		return true;
	
	}
	
    public boolean hotListValidations(String response,String versionSpecification){
		
		String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
		
		HashMap<String, String> hotListJsonPaths = vCardImplementation.getHotListCardJsonPaths();
		result = hotListValidationProcess(hotListJsonPaths, jsonResponse);
		
	    return result;
	}

    private boolean hotListValidationProcess(HashMap<String, String> hotListJsonPaths,
		String jsonResponse) {
	  boolean finalValidation = false;
	  String type = JsonPath.read(jsonResponse, hotListJsonPaths.get("type"));
	  String link_men = JsonPath.read(jsonResponse, hotListJsonPaths.get("link-men"));
	  String link_women = JsonPath.read(jsonResponse, hotListJsonPaths.get("link-women"));
	  String link_kid = JsonPath.read(jsonResponse, hotListJsonPaths.get("link-kid"));
	  
	  if(type.equalsIgnoreCase("split_banner")&&link_men.equalsIgnoreCase("/shop/men")&&link_women.equalsIgnoreCase("/shop/women")&&link_kid.equalsIgnoreCase("/shop/kids"))
	  {
		  finalValidation=true;
	  }
	  
	return finalValidation;
}

    public boolean streamNavValidations(String response, String versionSpecification) {
	String jsonResponse = response;
	boolean result =false;
	ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
	
	HashMap<String, String> hotListJsonPaths = vCardImplementation.getStreamCardJsonPaths();
	result = streamNavValidationProcess(hotListJsonPaths, jsonResponse);
	
    return result;
   }

    private boolean streamNavValidationProcess(
		HashMap<String, String> hotListJsonPaths, String jsonResponse) {
	  boolean finalValidation = false;
	  String type = JsonPath.read(jsonResponse, hotListJsonPaths.get("type"));
	  String link1 = JsonPath.read(jsonResponse, hotListJsonPaths.get("link1"));
	  String link2 = JsonPath.read(jsonResponse, hotListJsonPaths.get("link2"));
	  String link3 = JsonPath.read(jsonResponse, hotListJsonPaths.get("link3"));
	  
	  if(type.equalsIgnoreCase("split-banner")&&
			  ((link1.equalsIgnoreCase("/shop/men")||link1.equalsIgnoreCase("/shop/women")||link1.equalsIgnoreCase("/shop/kids"))&&
			   (link2.equalsIgnoreCase("/shop/men")||link2.equalsIgnoreCase("/shop/women")||link2.equalsIgnoreCase("/shop/kids"))&&
			   (link3.equalsIgnoreCase("/shop/men")||link3.equalsIgnoreCase("/shop/women")||link3.equalsIgnoreCase("/shop/kids"))))
	  {
		  finalValidation=true;
	  }
	  
	return finalValidation;
}

    public boolean streamSlideShowValidations(String response,
		 
    	String versionSpecification) {

		String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
		
		HashMap<String, String> hotListJsonPaths = vCardImplementation.getStreamSlideshowCardJsonPaths();
		result = streamSlideshowValidationProcess(hotListJsonPaths, jsonResponse);
		
	    return result;
	}

    private boolean streamSlideshowValidationProcess(
    		
		HashMap<String, String> hotListJsonPaths, String jsonResponse) {
	
		  boolean finalValidation = false;
		  String type = JsonPath.read(jsonResponse, hotListJsonPaths.get("type"));
		  String children_type = JsonPath.read(jsonResponse, hotListJsonPaths.get("children_type"));
		  
		  if(type.equalsIgnoreCase("slideshow")&&children_type.equalsIgnoreCase("SLIDESHOW"))
		  {
			  finalValidation=true;
		  }
		  
		 return finalValidation;
	
}

	public String getPayLoadForCard(String cardType) {
		String payload=null;
		switch (cardType)
		{
		case "article":
		     try {
		    	
			    payload = new Toolbox().readFileAsString("Data/payloads/JSON/article_card");
		     } catch (IOException e) {
			// TODO Auto-generated catch block
			    e.printStackTrace();
		      }
		   break;
		case "banner":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/banner_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
		   break;
		case "answer":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/answer_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
		   break;
		case "poll":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/poll_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break;
		case "product":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/product_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break;
		case "question":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/question_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break;
		case "splitbanner":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/splitbanner_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }	 
		   break; 
		case "productstory":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/productstory_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break; 
		case "video":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/video_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break;
		case "collection":
			 try {
				payload = new Toolbox().readFileAsString("Data/payloads/JSON/collection_card");
			     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
			 break; 	 
		   
		 default:
			 payload ="";
		 
		}
		
		return payload;
	}

	public HashMap<String, String> createandGetCardCreationDetails(String cardType,String uidx,HashMap<String, String> headers) {
		// TODO Auto-generated method stub
		HashMap<String, String> card_details = new HashMap<>();
		String payload=getPayLoadForCard(cardType);
		payload =new APIUtilities().prepareparameterizedURL(payload, new String[]{getCurrentTimestamp(),uidx});
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.CREATEOBJECT, init.Configurations,payload);
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        System.out.println(response);
        card_details.put("responseCode", String.valueOf(request.response.getStatus()));
        try
        {
         card_details.put("statusCode",String.valueOf(JsonPath.read(response,"$status.statusCode")));
         card_details.put("statusType",(String) JsonPath.read(response,"$status.statusType"));
         card_details.put("refId",(String) JsonPath.read(response,"$data[0].refId"));
         card_details.put("appId",String.valueOf(JsonPath.read(response,"$data[0].appId")));
        }
        catch(Exception e)
        {
        	
        }
		return card_details;
	}
	
	public String getCurrentTimestamp(){
		String timeStamp=null;
		timeStamp= String.valueOf(System.currentTimeMillis());
		return timeStamp;	
	}
	
	public HashMap<String, String> publishandGetResponseDetails(String whom,HashMap<String, String> headers) {
		// TODO Auto-generated method stub
		HashMap<String, String> card_details = new HashMap<>();
		String payload = null;
		try {
			payload = new Toolbox().readFileAsString("Data/payloads/JSON/fumespublishobject");
			payload =new APIUtilities().prepareparameterizedURL(payload, new String[]{whom});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHACTION, init.Configurations,payload);
		service.URL=new APIUtilities().prepareparameterizedURL(service.URL, new String[]{whom.split(":")[0]});
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        System.out.println(response);
        card_details.put("responseCode", String.valueOf(request.response.getStatus()));
        try
        {
         card_details.put("statusCode",String.valueOf(JsonPath.read(response,"$status.statusCode")));
         card_details.put("statusType",(String) JsonPath.read(response,"$status.statusType"));
         card_details.put("whom",(String) JsonPath.read(response,"$data[0].whom"));
         card_details.put("id",(String) JsonPath.read(response,"$data[0].id"));
        }
        catch(Exception e)
        {
        	
        }
		return card_details;
	}

	public HashMap<String, String> deleteandGetResponseDetails(String whom,
			HashMap<String, String> headers) {
		HashMap<String, String> card_details = new HashMap<>();
		MyntraService service = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.DELETEOBJECT, init.Configurations);
		service.URL=new APIUtilities().prepareparameterizedURL(service.URL, new String[]{whom.split(":")[0],whom.split(":")[1]});
        RequestGenerator request = new RequestGenerator(service, headers);
        String response = request.returnresponseasstring();
        System.out.println(response);
        card_details.put("responseCode", String.valueOf(request.response.getStatus()));
        try
        {
         card_details.put("statusCode",String.valueOf(JsonPath.read(response,"$status.statusCode")));
         card_details.put("statusType",(String) JsonPath.read(response,"$status.statusType"));
        }
        catch(Exception e)
        {
        	
        }
		return card_details;
	}
	
	
	public boolean jsonKeyValidationsForComponents(String component, String versionSpecification, String response,int j,String tests){
		
		String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
		String card_type = null;
		String onboard_card_id = null;
		if(tests != "CardObjectTests"){
			
			 card_type  = JsonPath.read(response, "$.props.meta.og:name");
			 onboard_card_id = JsonPath.read(response, "$.props.id");
		}
		
		switch(component){
		
			case "HEADER_CONTEXT":
				
				if(tests.contains("timelines")){
					
					result = true;
					
				}else{
					
					String[] headerContextJsonPaths = vCardImplementation.getHeaderContextJsonPaths();
					result = jsonKeyValidationProcess(headerContextJsonPaths, jsonResponse,j);
					
				}
			    break;
				
				
			case "COMPONENT_CARD":
				
				if(tests.contains("hotlist"))
				{
					String[] componentCardJsonPaths = vCardImplementation.getComponentCardHotListJsonPaths();
					result = jsonKeyValidationProcess(componentCardJsonPaths, jsonResponse,j);
				}
				else
				{
					String[] componentCardJsonPaths = vCardImplementation.getComponentCardJsonPaths();
					result = jsonKeyValidationProcess(componentCardJsonPaths, jsonResponse,j);
				}
				break;
				
			case "CREATOR_TITLE":
				
				String[] creatorTitleJsonPaths = vCardImplementation.getCreatorTitleJsonPaths();
				result = jsonKeyValidationProcess(creatorTitleJsonPaths, jsonResponse,j);
				break;
				
			case "DESCRIPTION_TEXT":
				
				String[] descriptionTextJsonPaths = vCardImplementation.getDescriptionTextJsonPaths();
				result = jsonKeyValidationProcess(descriptionTextJsonPaths, jsonResponse,j);
				break;
				
			case "FEEDBACK_VIEW_LIKE":
				
				String[] feedbackViewLikeJsonpaths = vCardImplementation.getFeedbackViewlikeJsonPaths();
				result = jsonKeyValidationProcess(feedbackViewLikeJsonpaths, jsonResponse,j);
				break;
				
			case "FOOTER_LIKE_SHARE":

				String[] footerLikeShareJsonpaths = vCardImplementation.getFooterLikeShareJsonPaths();
				result = jsonKeyValidationProcess(footerLikeShareJsonpaths, jsonResponse,j);
				break;	
				
			case "IMAGE_SINGLE":

				String[] imageSingleJsonpaths = vCardImplementation.getImageSingleJsonPaths();
				result = jsonKeyValidationProcess(imageSingleJsonpaths, jsonResponse,j);
				break;	
				
			case "TITLE_TEXT":

				String[] titleTextJsonpaths = vCardImplementation.getTitleTextJsonPaths();
				result = jsonKeyValidationProcess(titleTextJsonpaths, jsonResponse,j);
				break;
				
			case "PRODUCT_RACK":

				String[] productRackJsonpaths = vCardImplementation.getProductRackJsonPaths();
				result = jsonKeyValidationProcess(productRackJsonpaths, jsonResponse,j);
				break;
				
			case "MOSAIC":

				String[] mosaicJsonpaths = vCardImplementation.getMosaicJsonPaths();
				result = jsonKeyValidationProcess(mosaicJsonpaths, jsonResponse,j);
				break;
				
			case "BANNER":

				String[] bannerJsonpaths = vCardImplementation.getBannerJsonPaths();
				result = jsonKeyValidationProcess(bannerJsonpaths, jsonResponse,j);
				break;		
				
			case "SPLIT_BANNER":

				String[] splitBannerJsonpaths = vCardImplementation.getSplitBannerJsonPaths();
				result = jsonKeyValidationProcess(splitBannerJsonpaths, jsonResponse,j);
				break;	
				
			case "CAROUSEL_BANNER":

				String[] carouselBannerJsonpaths = vCardImplementation.getCarouselJsonPaths();
				result = jsonKeyValidationProcess(carouselBannerJsonpaths, jsonResponse,j);
				break;	
			
			case "VIDEO":

				String[] videoBannerJsonpaths = vCardImplementation.getVideoJsonPaths();
				result = jsonKeyValidationProcess(videoBannerJsonpaths, jsonResponse,j);
				break;	
				
			case "FOOTER_SHARE":

				String[] footerShareJsonpaths = vCardImplementation.getFooterShareJsonPaths();
				result = jsonKeyValidationProcess(footerShareJsonpaths, jsonResponse,j);
				break;	
			
			case "FOOTER_LINKS":

				String[] footerLinksJsonpaths = vCardImplementation.getFooterLinksJsonPaths();
				result = jsonKeyValidationProcess(footerLinksJsonpaths, jsonResponse,j);
				break;	
				
			case "PRODUCT_SINGLE":

				String[] productSingleJsonpaths = vCardImplementation.getProductSingleJsonPaths();
				result = jsonKeyValidationProcess(productSingleJsonpaths, jsonResponse,j);
				break;	
				
			case "ANSWER_SUMMARY":

				String[] answerSummaryJsonpaths = vCardImplementation.getAnswerSummaryJsonPaths();
				result = jsonKeyValidationProcess(answerSummaryJsonpaths, jsonResponse,j);
				break;
				
			case "ONBOARD":

				if(card_type.equals("onboard-d0-welcome"))
				{
				  String[] onboardWelcomeJsonpaths = vCardImplementation.getOnBoardForWelComeJsonPaths();
				  result = jsonKeyValidationProcess(onboardWelcomeJsonpaths, jsonResponse,j);
				}
				else if(card_type.equals("onboard-d0-welback"))
				{
				  String[] onboardWelcomeJsonpaths = vCardImplementation.getOnBoardForWelBackJsonPaths();
				  result = jsonKeyValidationProcess(onboardWelcomeJsonpaths, jsonResponse,j);
				}
				else if(card_type.equals("onboard-d0-fashion"))
				{
				  String[] onboardWelcomeJsonpaths = vCardImplementation.getOnBoardForWelComeJsonPaths();
				  result = jsonKeyValidationProcess(onboardWelcomeJsonpaths, jsonResponse,j);
				}
				break;		
				
			case "ONBOARD_CUSTOM_CONTENT":

				String[] onboardCustomContentJsonpaths = vCardImplementation.getOnBoardCustomContentJsonPaths();
				result = jsonKeyValidationProcess(onboardCustomContentJsonpaths, jsonResponse,j);
				break;	
			
			case "ONBOARD_CUSTOM_INFO":

				String[] onboardCustomInfoJsonpaths = vCardImplementation.getOnBoardCustomInfoJsonPaths();
				result = jsonKeyValidationProcess(onboardCustomInfoJsonpaths, jsonResponse,j);
				break;	
				
			case "ONBOARD_CUSTOM_FOOTER":
				
				if(onboard_card_id.equals("3:onboard-d0-carousel-shots") || onboard_card_id.equals("3:onboard-d0-carousel-forum-questions") 
						|| onboard_card_id.equals("3:onboard-d0-carousel-collections")){
					
					String[] onboardCustomFooterJsonpathsForShorts = vCardImplementation.getOnBoardCustomFooterJsonPathsForCarousal();
					result = jsonKeyValidationProcess(onboardCustomFooterJsonpathsForShorts, jsonResponse,j);
					
				}
				
				else
				{
					String[] onboardCustomFooterJsonpaths = vCardImplementation.getOnBoardCustomFooterJsonPaths();
					result = jsonKeyValidationProcess(onboardCustomFooterJsonpaths, jsonResponse,j);
					
				}

				break;	
	
			case "PERSONALIZED_TOPICS_RACK":

				String[] personlizedTopicsJsonpaths = vCardImplementation.getPersonalizedTopicsRackJsonPaths();
				result = jsonKeyValidationProcess(personlizedTopicsJsonpaths, jsonResponse,j);
				break;	
				
			case "ONBOARD_EDIT_PROFILE":

				String[] onBoardEditProfileJsonpaths = vCardImplementation.getOnBoardEditProfileJsonPaths();
				result = jsonKeyValidationProcess(onBoardEditProfileJsonpaths, jsonResponse,j);
				break;	
				
			case "PRODUCT":
				
				String[] productJsonpaths = vCardImplementation.getProductV2JsonPaths();
				result = jsonKeyValidationProcess(productJsonpaths, jsonResponse,j);
				break;
				
			case "SLIDESHOW":
				
				String[] slideShowJsonpaths = vCardImplementation.getSlideShowJsonPaths();
				result = jsonKeyValidationProcess(slideShowJsonpaths, jsonResponse,j);
				break;	
				
			/*case "CAROUSEL_CARD":
				 
				String[] carousalCardJsonPaths = vCardImplementation.getCarouselCardJsonPaths();
				result = jsonKeyValidationProcess(carousalCardJsonPaths, jsonResponse,j);
				break; */
				
			default:
				result=true;
				break;
		}
		return result;
	}

    public boolean componentValidationsForOnBoarding(String cardType, String versionSpecification, String response)
    {
    	String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification); 
		
		
		switch(cardType){
		
			case "onboard-d0-welcome":
				
				String[] welcomeComponents = vCardImplementation.getComponentsForOnboardWelcome();
				result = onBoardingComponentValidationProcess(welcomeComponents, jsonResponse);
			    break;
				
			case "onboard-d0-welback":
				
				String[] welbackComponents = vCardImplementation.getComponentsForOnboardWelBack();
				result = onBoardingComponentValidationProcess(welbackComponents, jsonResponse);
				break;
				
			case "onboard-d0-fashion":
				
				String[] fashionComponents = vCardImplementation.getComponentsForOnboardFashion();
				result = onBoardingComponentValidationProcess(fashionComponents, jsonResponse);
				break;
				
			case "onboard-d0-coupon-referral":
				
				String[] couponReferralComponents = vCardImplementation.getComponentsForOnboardCouponReferral();
				result = onBoardingComponentValidationProcess(couponReferralComponents, jsonResponse);
				break;
				
			case "onboard-d0-coupon-guestuser":
				
				String[] couponGuestUserComponents = vCardImplementation.getComponentsForOnboardGuestUser();
				result = onBoardingComponentValidationProcess(couponGuestUserComponents, jsonResponse);
				break;
				
			case "onboard-d0-coupon":

				String[] couponComponents = vCardImplementation.getComponentsForOnboardCoupon();
				result = onBoardingComponentValidationProcess(couponComponents, jsonResponse);
				break;	
				
			case "onboard-d0-collections":

				String[] collectionsComponents = vCardImplementation.getComponentsForOnboardCollections();
				result = onBoardingComponentValidationProcess(collectionsComponents, jsonResponse);
				break;	
				
			case "onboard-d0-topics":

				String[] topicsComponents = vCardImplementation.getComponentsForOnboardTopics();
				result = onBoardingComponentValidationProcess(topicsComponents, jsonResponse);
				break;
				
			case "onboard-d0-edit-profile":

				String[] editProfileComponents = vCardImplementation.getComponentsForOnboardEditProfile();
				result = onBoardingComponentValidationProcess(editProfileComponents, jsonResponse);
				break;
				
			case "onboard-d0-style-forum-question":

				String[] styleForumComponents = vCardImplementation.getComponentsForOnboardStyleForumQuestion();
				result = onBoardingComponentValidationProcess(styleForumComponents, jsonResponse);
				break;
				
			case "onboard-d0-returnexchange":

				String[] returnExchangeComponents = vCardImplementation.getComponentsForOnboardReturnExchange();
				result = onBoardingComponentValidationProcess(returnExchangeComponents, jsonResponse);
				break;	
				
			case "onboard-d0-delivery":

				String[] deliveryComponents = vCardImplementation.getComponentsForOnboardDelivery();
				result = onBoardingComponentValidationProcess(deliveryComponents, jsonResponse);
				break;	
				
			case "onboard-d0-payment":

				String[] paymentComponents = vCardImplementation.getComponentsForOnboardPayment();
				result = onBoardingComponentValidationProcess(paymentComponents, jsonResponse);
				break;	
				
			case "onboard-d0-carousel":
				String[] carouselComponents = vCardImplementation.getComponentsForOnBoardCarousel();
				result = onBoardingComponentValidationProcess(carouselComponents, jsonResponse);
				break;
								
			default:
				result=true;
				break;
		}
		return result;

    	
    }

	private boolean onBoardingComponentValidationProcess(
			String[] welcomeComponents, String jsonResponse) {
		
		boolean result=false;
		ArrayList<String> referenceList = new ArrayList<String>();
		JSONArray components = JsonPath.read(jsonResponse,"$.children");
		for(int i=0;i<components.size();i++)
		{
			String componentType = JsonPath.read(components.get(i),"$.type");
			if(ArrayUtils.contains(welcomeComponents, componentType))
			{
				referenceList.add(componentType+"-true");
			}
			else{
				referenceList.add(componentType+"-false");
			}
		}
		System.out.println("OnBoarding Components Validations--------------> " + referenceList);
		result=checkFinalResult(referenceList);
		return result;
		
	}
	
	protected boolean feedComponentValidationProcess(String[] feedCardComponents, String jsonResponse) {
		
		boolean result=false;
		ArrayList<String> referenceList = new ArrayList<String>();
		JSONArray components = JsonPath.read(jsonResponse,"$.children[*]..type");
		for(int i=0;i<feedCardComponents.length;i++)
		{
			if(ArrayUtils.contains(components.toArray(),feedCardComponents[i]))
			{
				referenceList.add(feedCardComponents[i]+"-true");
			}
			else{
				referenceList.add(feedCardComponents[i]+"-false");
			}
		}
		System.out.println("Card Components Validations--------------> " + referenceList);
		result=checkFinalResult(referenceList);
		return result;
		
	}

	private boolean checkFinalResult(ArrayList<String> referenceList) {
		for(int i=0;i<referenceList.size();i++)
		{
			if(referenceList.get(i).contains("false"))
			{
				return false;
			}
		}
		return true;
	}

	public boolean checkTitleTextResult(String title) {
		if(!title.contains("@user"))
			{
				return false;
			}
		return true;
	}
	
	
	public boolean componentValidationsForFeeds(String cardType, String versionSpecification, String response)
    {
    	String jsonResponse = response;
		boolean result =false;
		ILgpJsonKeys vCardImplementation = cardsImplementationInstance(versionSpecification);
		
		
		
		switch(cardType){
		
			case "answer":
				
				String[] welcomeComponents = vCardImplementation.getComponentsForAnswerCard(jsonResponse);
				if(JsonPath.read(response,"$.props.meta.source").toString().trim().equalsIgnoreCase("Sponsored"))
					welcomeComponents = (String[]) ArrayUtils.removeElement(welcomeComponents, "FOOTER_LIKE_SHARE");
				result = feedComponentValidationProcess(welcomeComponents, jsonResponse);
			    break;
				
				
			case "article":
				
				String[] welbackComponents = vCardImplementation.getComponentsForArticleCard(jsonResponse);
				if(JsonPath.read(response,"$.props.meta.source").toString().trim().equalsIgnoreCase("Sponsored"))
					welbackComponents = (String[]) ArrayUtils.removeElement(welbackComponents, "FOOTER_LIKE_SHARE");
				result = feedComponentValidationProcess(welbackComponents, jsonResponse);
				break;
				
			case "banner":
				
				String[] fashionComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(fashionComponents, jsonResponse);
				break;
				
			case "carousel-banner":
				
				String[] couponReferralComponents = vCardImplementation.getComponentsForcarouselBannerCard(jsonResponse);
				result = feedComponentValidationProcess(couponReferralComponents, jsonResponse);
				break;
				
			case "collections":
				
				String[] couponGuestUserComponents = vCardImplementation.getComponentsForCollectionsBannerCard(jsonResponse);
				result = feedComponentValidationProcess(couponGuestUserComponents, jsonResponse);
				break;
				
			case "product-story":

				String[] couponComponents = vCardImplementation.getComponentsForProductStoryCard(jsonResponse);
				result = feedComponentValidationProcess(couponComponents, jsonResponse);
				break;	
				
			case "poll":

				String[] collectionsComponents = vCardImplementation.getComponentsForPollCard(jsonResponse);
				result = feedComponentValidationProcess(collectionsComponents, jsonResponse);
				break;	
				
			case "post.shot":

				String[] topicsComponents = vCardImplementation.getComponentsForPostShotCard(jsonResponse);
				result = feedComponentValidationProcess(topicsComponents, jsonResponse);
				break;
				
			case "post.styleupdate":

				String[] editProfileComponents = vCardImplementation.getComponentsForPostStyleUpdateCard(jsonResponse);
				result = feedComponentValidationProcess(editProfileComponents, jsonResponse);
				break;
				
			case "product":

				String[] styleForumComponents = vCardImplementation.getComponentsForProductCard(jsonResponse);
				result = feedComponentValidationProcess(styleForumComponents, jsonResponse);
				break;
				
			case "product.v2":

				String[] returnExchangeComponents = vCardImplementation.getComponentsForProductV2Card(jsonResponse);
				result = feedComponentValidationProcess(returnExchangeComponents, jsonResponse);
				break;	
				
			case "question":

				String[] questionComponents = vCardImplementation.getComponentsForQuestionCard(jsonResponse);
				result = feedComponentValidationProcess(questionComponents, jsonResponse);
				break;	
				
			case "slideshow":

				String[] slideShowComponents = vCardImplementation.getComponentsForSlideshowCard(jsonResponse);
				result = feedComponentValidationProcess(slideShowComponents, jsonResponse);
				break;	
				
			case "split-banner":

				String[] splitBannerComponents = vCardImplementation.getComponentsForSplitBannerCard(jsonResponse);
				result = feedComponentValidationProcess(splitBannerComponents, jsonResponse);
				break;	
				
			case "styletip":

				String[] styletipComponents = vCardImplementation.getComponentsForSplitBannerCard(jsonResponse);
				result = feedComponentValidationProcess(styletipComponents, jsonResponse);
				break;
				
			case "video":
				
				String[] videoComponents = vCardImplementation.getComponentsForVideoCard(jsonResponse);
				result = feedComponentValidationProcess(videoComponents, jsonResponse);
				break;	
				
			case "carousel":
                
				String[] carouselComponents = vCardImplementation.getComponentsForCarousel(jsonResponse);
				result = feedComponentValidationProcess(carouselComponents, jsonResponse);
				break;	
				
			case "budget_store":
				
				String[] budgetStoreComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(budgetStoreComponents, jsonResponse);
				break;
				
			case "edit_profile":
				
				String[] editProfileComponent = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(editProfileComponent, jsonResponse);
				break;
				
			case "referral":
				
				String[] referralComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(referralComponents, jsonResponse);
				break;
				
			case "signup":
				
				String[] signupComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(signupComponents, jsonResponse);
				break;
				
			case "out-of-stock":
				
				String[] outOfStockComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(outOfStockComponents, jsonResponse);
				break;
				
			case "shot-carousel":
				
				String[] shotCarouselComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(shotCarouselComponents, jsonResponse);
				break;
				
			case "custom-product":
				
				String[] customProductComponents = vCardImplementation.getComponentsForBannerCard(jsonResponse);
				result = feedComponentValidationProcess(customProductComponents, jsonResponse);
				break;
								
			default:
				result=true;
				break;
		}
		return result;

    	
    }
	
	
	private int urlGenericValidations(String urlRetrieved,String response,String jsonpath)
	{
		new ArrayList<Integer>();
		int statuscode;
		if(urlRetrieved.isEmpty()){
			statuscode=0;
 		}
		else if(urlRetrieved.startsWith("/")){
			String urlPrefix = "http://www.myntra.com";
			String absoluteUrl = urlPrefix.concat(urlRetrieved);
			statuscode = jsonUrlValidation(absoluteUrl);
		}
		else if(!urlRetrieved.startsWith("http")){
			String videoUrlPrefix = "https://www.youtube.com/watch?v=";
			String absoluteUrl = videoUrlPrefix.concat(urlRetrieved);
			statuscode = jsonUrlValidation(absoluteUrl);
		}
		else{
			statuscode = jsonUrlValidation(urlRetrieved);
		}
		return statuscode;
	}
	
}














