package com.myntra.apiTests;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UtilDev {
	
	static Map<String, JsonElement> jsonKeyMap  = new ConcurrentHashMap<String,JsonElement>();
	static ArrayList<String> keyList = new ArrayList<String>();
	
	static ArrayList<String> modifiedKeyList = new ArrayList<String>();
	

	
    public static void main(String[] args) throws IOException {
		
    	jsonKeyListFindUtility();
	}	
	
	public static String readFileAsString(String filePath) throws IOException
	{
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		return IOUtils.toString(reader);
	}

	
	
	public static boolean jsonKeyListFindUtility() throws IOException{
		
		String jsonSample = readFileAsString("Data/sampleJson.txt");
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
				jsonTypeIdentifier(key, valueElement);
				String valueElementAsString = valueElement.toString();
				if(valueElementAsString.startsWith("{") || valueElementAsString.startsWith("[")){
					
					jsonKeyMap.put(key, valueElement);
					
					
				}
				
			}
			jsonKeyListFindUtilitySupport(jsonKeyMap);
			System.out.println("==============================================================");
			System.out.println("PRINTING ONLY THE JSON KEYS");
			for(int i = 0; i< keyList.size(); i++){
				
				System.out.println(keyList.get(i));
				
				
			}
			System.out.println("==============================================================");
			System.out.println("PRINTING THE JSON KEYS WITH THEIR JSONTYPES");
			for(int i = 0; i< modifiedKeyList.size(); i++){
				
				System.out.println(modifiedKeyList.get(i));
				
				
			}
			
		}

		return false;
	}
	
	public static Map<String,JsonElement> jsonKeyListFindUtilitySupport(Map<String,JsonElement> jsonMap){
		
		if(jsonMap.isEmpty()){
			
			return jsonMap;
			
		}
		
		for(Map.Entry<String, JsonElement> jsonMapEntrySet : jsonMap.entrySet() ){
			
			String parentKey = jsonMapEntrySet.getKey();
			
			JsonElement mapJsonElement = jsonMap.get(parentKey);
			jsonTypeIdentifier(parentKey, mapJsonElement);
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
							jsonTypeIdentifier(keyPath, childKeyValueElement);
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
					jsonTypeIdentifier(keyPath, childKeyValueElement);
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
	
	static void jsonTypeIdentifier(String key, JsonElement valueElement){
		
		if(valueElement.isJsonPrimitive()){
			
			
			modifiedKeyList.add(key+" : "+JSONTYPE.JSONPRIMITIVE.jsonType);
		}
		
		else if(valueElement.isJsonObject()){
			
			
			modifiedKeyList.add(key+" : "+JSONTYPE.JSONOBJECT.jsonType);
		}
		
		else if(valueElement.isJsonArray()){
			
			modifiedKeyList.add(key+" : "+JSONTYPE.JSONARRAY.jsonType);
		}
		
	}
	
}
