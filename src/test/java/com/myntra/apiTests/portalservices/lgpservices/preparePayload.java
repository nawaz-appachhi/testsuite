package com.myntra.apiTests.portalservices.lgpservices;

import java.util.HashMap;
import java.util.Iterator;

import com.myntra.apiTests.lgpservices.PayloadAction;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class preparePayload {
	
	static HashMap<String,String> registerObjectMap = new HashMap<String,String>();
	static HashMap<String,String> publishObjectMap = new HashMap<String,String>();
	static HashMap<String,String> feedsObjectMap = new HashMap<String,String>();
	static HashMap<String,String> streamsObjectMap = new HashMap<String,String>();
	public static void main(String args[]) throws JSONException, ParseException{
		PayloadAction pa = null;
		HashMap<String,HashMap<String,String>> payloadData = new HashMap();
		performActions(pa.SERVEASSERTION,payloadData);
		
	}
	
	@SuppressWarnings("unchecked")
	public static String performActions(PayloadAction payloadAction,HashMap<String,HashMap<String,String>> payloadData) throws JSONException, ParseException{
		switch (payloadAction){
		
		case REGISTEROBJECT:{
			
			registerObjectMap.put("type", "article");
			registerObjectMap.put("title", "StreamTestingKarnataka");
			registerObjectMap.put("description", "StreamTesting");
			registerObjectMap.put("url", "http://www.myntra.com/cart");
			registerObjectMap.put("imageUrl", "http://assets.myntassets.com/h_240,q_100,w_180/v1/image/style/properties/916996/RANGMANCH-BY-PANTALOONS-Women-Kurtas_1_086ca36b57a50e83e1adf99453fc55c0.jpg");
			registerObjectMap.put("aspectRatio", "1:1");
			registerObjectMap.put("extraData","");
			registerObjectMap.put("id", (new Integer(3808)).toString());
			registerObjectMap.put("name", "casual");
			registerObjectMap.put("type2", "occasion");
			registerObjectMap.put("displayName", "casual");
			registerObjectMap.put("topics", "");
			registerObjectMap.put("isPrivate", "false");
			registerObjectMap.put("createAction", "published");
			registerObjectMap.put("enabled", "true");
			registerObjectMap.put("isSpam", "false");
			registerObjectMap.put("count", "1");
			
			if(payloadData.containsKey("registerObject")){
				if(payloadData.get("registerObject")!=null){
					
					Iterator it = payloadData.get("registerObject").keySet().iterator();
					
					while(it.hasNext()){
						String entry = (String) it.next();
						System.out.print("entry is "+entry);
						if(registerObjectMap.containsKey(entry)){
							registerObjectMap.put(entry,payloadData.get("registerObject").get(entry).toString());
						}
					}
					
				}
			}
			
			
			JSONObject registerObjects = new JSONObject();
			
			JSONArray objects = new JSONArray();
			JSONObject objectsIds = new JSONObject();
			objectsIds.put("count",Integer.parseInt(registerObjectMap.get("count")));
			
			
			JSONObject headers = new JSONObject();
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Content-Type", "application/json");
			headers.put("Accept", "application/json");
			
			objectsIds.put("headers",headers);
			
			JSONObject obj = new JSONObject();
			obj.put("type", registerObjectMap.get("type"));
			obj.put("title", registerObjectMap.get("title"));
			obj.put("description", registerObjectMap.get("description"));
			obj.put("url",registerObjectMap.get("url"));
			obj.put("imageUrl", registerObjectMap.get("imageUrl"));
			
			
			JSONObject obj2 = new JSONObject();
			obj2.put("aspectRatio",registerObjectMap.get("aspectRatio"));
			obj.put("extraData",obj2.toString());
			JSONArray list = new JSONArray();
			
			JSONObject obj3 = new JSONObject();
			obj3.put("id", Integer.parseInt(registerObjectMap.get("id")));
			obj3.put("name",registerObjectMap.get("name"));
			obj3.put("type", registerObjectMap.get("type2"));
			obj3.put("displayName",registerObjectMap.get("displayName"));
			//list.add(obj3);
			
			String s="["+obj3+"]";
			
				JSONParser parser = new JSONParser();
				JSONArray json =  (JSONArray) parser.parse(s);
				obj.put("topics", json);
					
			
			//obj.put("topics", list);	
			obj.put("isPrivate", Boolean.valueOf(registerObjectMap.get("isPrivate")));
			obj.put("createAction", registerObjectMap.get("createAction"));
			obj.put("enabled", Boolean.valueOf(registerObjectMap.get("enabled")));
			obj.put("isSpam", Boolean.valueOf(registerObjectMap.get("isSpam")));
				
			objectsIds.put("payload",obj);	
			s="["+objectsIds+"]";
			JSONArray json2 = (JSONArray) parser.parse(s);
			//objects.add(objectsIds);
			
			registerObjects.put("objects", json2);
			
				System.out.println(registerObjects);
				return registerObjects.toString();
			
		}
		case PUBLISHOBJECT:{
			
			publishObjectMap.put("whomType", "Object");
			publishObjectMap.put("userMessage", "Check out this article!");
			publishObjectMap.put("name1", "android");
			publishObjectMap.put("name2", "ios");
			publishObjectMap.put("channels", "");
			publishObjectMap.put("persona", "unisex");
			//publishObjectMap.put("credits", "500");
			publishObjectMap.put("boost", "0.5");
			publishObjectMap.put("includeLocation", "punjab,karnataka");
			publishObjectMap.put("excludeLocation", "haryana");
			publishObjectMap.put("platform", "android");
			publishObjectMap.put("extraData", "");
			publishObjectMap.put("userTargettingType", "EVERYONE");
			publishObjectMap.put("segmentQuery", "");
			publishObjectMap.put("allowUsers", "");
			publishObjectMap.put("allowUsersUrl", "");
			publishObjectMap.put("denyUsers", "");
			publishObjectMap.put("denyUsersUrl", "");
			publishObjectMap.put("target", "");
	
			if(payloadData.containsKey("publishObject")){
				if(payloadData.get("publishObject")!=null){
					
					Iterator it = payloadData.get("publishObject").keySet().iterator();
					
					while(it.hasNext()){
						String entry = (String) it.next();
						System.out.print("entry is "+entry);
						if(publishObjectMap.containsKey(entry)){
							publishObjectMap.put(entry,payloadData.get("publishObject").get(entry).toString());
						}
					}
					
				}
			}
			
			JSONObject publishObjects = new JSONObject();
			
			JSONArray objects = new JSONArray();
			JSONObject objectsIds = new JSONObject();
			objectsIds.put("objectIds","unpublished");
			objectsIds.put("count",Integer.parseInt("1"));
			
			
			JSONObject headers = new JSONObject();
			headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
			headers.put("Content-Type", "application/json");
			headers.put("Accept", "application/json");
			
			objectsIds.put("headers",headers);
			
			
			JSONObject obj = new JSONObject();
			obj.put("whomType", publishObjectMap.get("whomType"));
			obj.put("userMessage", publishObjectMap.get("userMessage"));
			
			JSONObject obj2 = new JSONObject();
			obj2.put("name", publishObjectMap.get("name1"));
			JSONObject obj3 = new JSONObject();
			obj3.put("name", publishObjectMap.get("name2"));
			
			String s ="["+ obj2+","+obj3+"]";
			JSONParser parser = new JSONParser();
			JSONArray json =  (JSONArray) parser.parse(s);
			obj.put("channels", json);
			
			//obj.put("channels", list);
			obj.put("persona", publishObjectMap.get("persona"));
			
			JSONObject obj4 = new JSONObject();
			//obj4.put("credits", publishObjectMap.get("credits"));
			obj4.put("boost", publishObjectMap.get("boost"));
			obj4.put("includeLocation", publishObjectMap.get("includeLocation"));
			obj4.put("platform", publishObjectMap.get("platform"));
			obj.put("extraData",String.valueOf(obj4));
			
			JSONObject obj5 = new JSONObject();
			obj5.put("userTargettingType", publishObjectMap.get("userTargettingType"));
			obj5.put("segmentQuery", publishObjectMap.get("segmentQuery"));
			obj5.put("allowUsers", publishObjectMap.get("allowUsers"));
			obj5.put("allowUsersUrl", publishObjectMap.get("allowUsersUrl"));
			obj5.put("denyUsers", publishObjectMap.get("denyUsers"));
			obj5.put("denyUsersUrl", publishObjectMap.get("denyUsersUrl"));
			obj.put("target",obj5);
			
			
			objectsIds.put("payload",obj);
			
			System.out.println(obj);
			
			s="["+objectsIds+"]";
			JSONArray json2 = (JSONArray) parser.parse(s);
			//objects.add(objectsIds);
			publishObjects.put("objects", json2);
			System.out.println(publishObjects);
			return publishObjects.toString();
			
		}
		case SERVEASSERTION:{
			
			//if()
			feedsObjectMap.put("headers", "Object");
			feedsObjectMap.put("scenarioType", "location");
			feedsObjectMap.put("location", "");
			feedsObjectMap.put("previousIterations", "20");
			feedsObjectMap.put("platforms", "android");
			
			if(payloadData.containsKey("feedsObject")){
				if(payloadData.get("feedsObject")!=null){
					
					Iterator it = payloadData.get("feedsObject").keySet().iterator();
					
					while(it.hasNext()){
						String entry = (String) it.next();
						System.out.print("entry is "+entry);
						if(feedsObjectMap.containsKey(entry)){
							feedsObjectMap.put(entry,payloadData.get("feedsObject").get(entry).toString());
						}
					}
					
				}
			}
			JSONObject obj1 = new JSONObject();
			
			JSONObject headers = new JSONObject();
			headers.put("Content-Type", "application/json");
			headers.put("Accept", "application/json");
			obj1.put("headers", headers);
			obj1.put("scenarioType", feedsObjectMap.get("scenarioType"));
			String s=null;
			JSONParser parser = new JSONParser();
			if(!feedsObjectMap.get("location").isEmpty()){
				 s = "[\""+feedsObjectMap.get("location").toString()+"\"]";
				 JSONArray json2 = (JSONArray) parser.parse(s);
				 obj1.put("location", json2);
			}
			
			obj1.put("previousIterations", Integer.parseInt(feedsObjectMap.get("previousIterations")));
			
			s = "[\""+feedsObjectMap.get("platforms")+"\"]";
			JSONArray json3 = (JSONArray) parser.parse(s);
			obj1.put("platforms", json3);
			
			JSONArray jArray = new JSONArray();
			//jArray.add(obj1);
			
			streamsObjectMap.put("headers", "");
			streamsObjectMap.put("scenarioType", "Location");
			streamsObjectMap.put("location", "");
			streamsObjectMap.put("previousIterations", "0");
			streamsObjectMap.put("nextIterations", "20");
			streamsObjectMap.put("platforms", "android");
			streamsObjectMap.put("variants", "variantB");
			
			if(payloadData.containsKey("streamsObject")){
				if(payloadData.get("streamsObject")!=null){
					
					Iterator it = payloadData.get("streamsObject").keySet().iterator();
					
					while(it.hasNext()){
						String entry = (String) it.next();
						System.out.print("entry is "+entry);
						if(streamsObjectMap.containsKey(entry)){
							streamsObjectMap.put(entry,payloadData.get("streamsObject").get(entry).toString());
						}
					}
					
				}
			}
			JSONObject obj2 = new JSONObject();
			
			JSONObject headers2 = new JSONObject();
			headers2.put("Content-Type", "application/json");
			headers2.put("Accept", "application/json");
			obj2.put("headers", headers);
			obj2.put("scenarioType", streamsObjectMap.get("scenarioType"));
			if(!streamsObjectMap.get("location").equals("")){
				s = "[\""+streamsObjectMap.get("location")+"\"]";
				
				JSONArray json22 = (JSONArray) parser.parse(s);
				
				obj2.put("location", json22);
			}
			obj2.put("previousIterations", Integer.parseInt(streamsObjectMap.get("previousIterations")));
			obj2.put("nextIterations", Integer.parseInt(streamsObjectMap.get("nextIterations")));
			
			s = "[\""+streamsObjectMap.get("platforms")+"\"]";
			JSONArray json32 = (JSONArray) parser.parse(s);
			obj2.put("platforms", json32);
			
			s = "[\""+streamsObjectMap.get("variants")+"\"]";
			JSONArray json42 = (JSONArray) parser.parse(s);
			obj2.put("variants", json42);
			
			JSONObject finalObj = new JSONObject();
			finalObj.put("feeds",obj1);
			JSONObject finalObj2 = new JSONObject();
			finalObj2.put("streams",obj2);
			jArray.add(finalObj);
			jArray.add(finalObj2);
			
			return jArray.toString();
		}
		default: return null;
		}
		
	}

}
