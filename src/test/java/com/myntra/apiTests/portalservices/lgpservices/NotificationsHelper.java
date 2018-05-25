package com.myntra.apiTests.portalservices.lgpservices;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Info;
import com.aerospike.client.Key;
import com.aerospike.client.policy.InfoPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import com.myntra.lordoftherings.Initialize;

import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.log.Log;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationsHelper {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static APIUtilities utilities = new APIUtilities();
	Logger log=NotificationTest.log;
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	String versionSpecification;
	String persona = null;
	public boolean urlHasPlaceholders=false;
	public boolean templateService = false;
	public boolean template = false;
	public String dbHost;
	public int dbPort;
	public String aerospikeHost;
	public int aerospikePort;
	public String[] redisHosts;
	public int[] redisPorts;
	public String cassandraConfigHost;
	public int cassandraConfigPort;
	public String aerospikeEntryConvention = "qa_";
	public int aerospikeRange = 20;
	public boolean validData=true;
	public boolean broadcast=false;
	public boolean validChannelData=true;
	public Map<String,Map<String, Integer>> cappingData=new HashMap<>();
	static APIUtilities apiUtil = new APIUtilities();
	public NotificationsHelper(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
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
	
	public Map<String,JSONObject> readURLForIdListAndTemplatePlaceholders(String idUrl,Map<String,JSONObject> idAndTemplatePlaceholders,Set<String> idSet) throws IOException, JSONException{
		BufferedReader reader;
		try{
			URL fileUrl = new URL(idUrl);
			reader = new BufferedReader(new InputStreamReader(fileUrl.openStream()));
			
		}catch(IOException e){
			throw new IOException("The file url is invalid :" + idUrl);
		}
		String line=reader.readLine();
		
		if(line==null || line.isEmpty()){
			throw new IOException("The file url is empty :" + idUrl);
		}
		line=line.replace("\"", "");
		String[] headerKeys=line.split(" ");
		//Check if the file contains template values
		if(headerKeys.length>1){
			urlHasPlaceholders=true;
			log.info("File has place holder values: "+true);
		}
		headerKeys[0]="id";
		line=reader.readLine();
		
		while(line!=null && !line.isEmpty()){
			line=line.replace("\"", "");
			String[] values = line.split(" ");
			JSONObject placeholders = null;
			if(urlHasPlaceholders){
				placeholders=new JSONObject();
				for(int i=1;(i<headerKeys.length && i<values.length);i++){
					placeholders.put(headerKeys[i], values[i]);
				}
			}
			//Only one id should be sent the notification
			if(!idAndTemplatePlaceholders.containsKey(values[0]))
				idAndTemplatePlaceholders.put(values[0],placeholders);
			idSet.add(values[0]);
			line=reader.readLine();
		}
		log.info("Id list with placeholder values : "+idAndTemplatePlaceholders);
		return(idAndTemplatePlaceholders);
	}
			
	
	public String getTemplateData(JSONObject templatePayload) throws Exception{
		JSONObject templateRequestPayload =new JSONObject();
		templateRequestPayload.put("tgid", templatePayload.getString("tgId"));
		templateRequestPayload.put("appid", templatePayload.get("appId"));
		templateRequestPayload.put("tags", templatePayload.getJSONArray("tags"));
		templateRequestPayload.put("version", templatePayload.get("version"));
		String payload = templateRequestPayload.toString();
		MyntraService getTemplateCall = Myntra.getService(ServiceType.PORTAL_TEMPLATE, APINAME.GETTEMPLATE,init.Configurations,payload);
		RequestGenerator requestGen = new RequestGenerator(getTemplateCall);
		String response = requestGen.returnresponseasstring();
		
		if(!readJsonPath(response, "$.status.statusMessage").equals("SUCCESS")){
			throw new Exception("Failed to get the template data");
		}
		
		String templateData = readJsonPath(response, "$.data.template");
		//JSONObject templateDataAsJsonObject = new JSONObject(templateData);
		
		
		
		return(templateData);
	}
	
	public String populatePlaceholderData(String extraFieldsStr, JSONObject placeholders) throws JSONException{
		//String extraFieldsStr = extraFields.toString();
		Iterator<String> keys = placeholders.keys();
		while(keys.hasNext()){
			String key = keys.next();
			String placeholderKey = Pattern.quote("{$")+key+Pattern.quote("$}");
			extraFieldsStr = extraFieldsStr.replaceAll(placeholderKey, placeholders.getString(key));
		}
		Pattern pat = Pattern.compile(Pattern.quote("{$")+"[.[^("+Pattern.quote("$")+Pattern.quote("}")+")]]*"+Pattern.quote("$}"));
		Matcher mat = pat.matcher(extraFieldsStr);
		//Check if all the placeholder values are filled with correct values.
		if(mat.find()){
			return(null);
		}else{
			//JSONObject finalExtraField =new JSONObject(extraFieldsStr);
			return(extraFieldsStr);
			
		}
	}
	//TODO: MOCK Class
//	private class Row {
//		JSONObject extraFields;
//		String id;
//		Row(JSONObject extraFields,String id){
//			this.extraFields=extraFields;
//			this.id=id;
//		}
//		String getString(String key) throws JSONException{
//			switch(key){
//			case "notificationtitle": return(extraFields.getString("title"));
//			case "title": return(extraFields.getString("title"));
//			case "notificationtext": return(extraFields.getString("description"));
//			case "text": return(extraFields.getString("description"));
//			case "notificationtype": return(extraFields.getString("notificationType"));
//			case "type": return(extraFields.getString("notificationType"));
//			case "urlforlandingpage": return(extraFields.getString("pageUrl"));
//			case "pageurl": return(extraFields.getString("pageUrl"));
//			case "heading": return(extraFields.getString("heading"));
//			case "userid": return(id);
//			default: return(null);
//			}
//		}
//	}
	
	public boolean validateFieldValues(JSONObject nidRow, JSONObject extraFields,String notificationName, ArrayList<String> failures,String nid) throws JSONException{
		
		if(extraFields.has("title")&&extraFields.getString("title")!=null&&!extraFields.getString("title").isEmpty()){
			if(!extraFields.getString("title").equals(nidRow.getString("notificationtitle"))){
				failures.add("The titles do not match for id "+nidRow.getString("userid"));
			}
		}else{
			failures.add("Title field cannot be null for id "+nidRow.getString("userid")+". Notification still created");
		}
		if(extraFields.has("description")&&extraFields.getString("description")!=null&&!extraFields.getString("description").isEmpty()){
			if(!extraFields.getString("description").equals(nidRow.getString("notificationtext"))){
				failures.add("The descriptions do not match for id "+nidRow.getString("userid"));
			}
		}else{
			failures.add("description field cannot be null for id "+nidRow.getString("userid")+". Notification still created");
		}
		if(extraFields.has("notificationType")&&extraFields.getString("notificationType")!=null&&!extraFields.getString("notificationType").isEmpty()){
			if(!extraFields.getString("notificationType").equalsIgnoreCase(nidRow.getString("notificationtype"))){ 
				failures.add("The notificationType do not match for id "+nidRow.getString("userid")+". Notification still created");
			}
		}else{
			failures.add("notificationType field cannot be null for id "+nidRow.getString("userid"));
		}
		if(extraFields.has("pageUrl")&&extraFields.getString("pageUrl")!=null&&!extraFields.getString("pageUrl").isEmpty()){
			if(!matchPageUrl(extraFields.getString("pageUrl"),nidRow.getString("pageurl"),nid)){
				failures.add("The pageUrl do not match for id "+nidRow.getString("userid")+"\n" +
						extraFields.getString("pageUrl")+","+nidRow.getString("pageurl"));
			}
		}else{
			failures.add("pageUrl field cannot be null for id "+nidRow.getString("userid")+". Notification still created");
		}

		if(extraFields.has("imageUrl")&&extraFields.getString("imageUrl")!=null&&!extraFields.getString("imageUrl").isEmpty()){
			if(!extraFields.getString("imageUrl").equals(nidRow.getString("cdnurlforimage"))){
				failures.add("The imageUrl do not match for id "+nidRow.getString("userid"));
			}
		}
		if(extraFields.has("iconUrl")&&extraFields.getString("iconUrl")!=null&&!extraFields.getString("iconUrl").isEmpty()){
			if(!extraFields.getString("iconUrl").equals(nidRow.getString("cdnurlforicon"))){
				failures.add("The iconUrl do not match for id "+nidRow.getString("userid"));
			}
		}
		if(notificationName.equalsIgnoreCase("Beacon")){
			
			if(extraFields.has("heading")&&extraFields.getString("heading")!=null&&!extraFields.getString("heading").isEmpty()){
				if(!extraFields.getString("heading").equalsIgnoreCase(nidRow.getString("heading"))){
					failures.add("The heading do not match for id "+nidRow.getString("userid")+". Notification still created");
				}
			}else{
				failures.add("heading field cannot be null for id "+nidRow.getString("userid"));
			}
		}
		return true;
	}

	public boolean matchPageUrl(String requestUrl,String recordUrl,String nid){
		ArrayList<String> queryParams=new ArrayList<>();
		String queryParamsInrequestUrl="";
		if(requestUrl.contains("?"))
			queryParamsInrequestUrl=requestUrl.substring(requestUrl.indexOf("?")+1,requestUrl.length());
		String baseUrlInRequestUrl=requestUrl.substring(0,recordUrl.indexOf("?"));
		queryParams.addAll(Arrays.asList(queryParamsInrequestUrl.split("&")));
		queryParams.add("utm_medium=Notif");
		queryParams.add("utm_nid="+nid.toLowerCase());
		log.info("verifying with query params "+ queryParams);
		boolean valid=true;
		if(!recordUrl.contains(baseUrlInRequestUrl)){
			valid=false;
		}
		for(String queryParam:queryParams){
			if(!recordUrl.contains(queryParam)){
				valid=false;
			}
		}
		return (valid);
	}
	
	
	
	public String getFailuresPoints(ArrayList<String> failures){
		String failureAsPoints="";
		int i=1;
		for(String errMsg:failures){
			failureAsPoints= failureAsPoints +(i++)+". "+errMsg+"\n";
		}
		return(failureAsPoints);
	}
	
	public void addDummyUserIdsInAerospike(String host,int port,String platform,int entriesCount,String entryConvention) throws ConnectException{
		boolean connected=true;
		int maxRetry=10;
		AerospikeClient qalClient=null;
		do{
			try{
				qalClient = new AerospikeClient(host, port);
			}catch(Exception e){
				connected=false;
			}
		}while(!connected&&maxRetry-->0);
		if(!connected){
				throw new ConnectException("Aerospike Connection failed");
		}
		WritePolicy policy = new WritePolicy();
		policy.sendKey=true;
		for(int i = 0;i<entriesCount;i++){
			Key key = new Key("tokens", platform, entryConvention+platform+i);
			Bin bin0 = new Bin("PK", entryConvention+platform+i);
			Bin bin1 = new Bin("user_id", entryConvention+platform+"."+i);
			Bin bin2 = new Bin("device_token", entryConvention+platform+i);
			Random rand=new Random();
			Bin bin3 = new Bin("app_version", rand.nextInt(10)+"."+rand.nextInt(10)+"."+rand.nextInt(10));
			qalClient.put(policy, key, bin0,bin1,bin2,bin3);
		}
		NotificationTest.log.info("Added dummy "+platform+" user to aerospike");
		qalClient.close();
	}
	
	public void clearCappingCounterEntryFromRedis(String appId,String platform,Set<String> idSet,String idType,JSONObject extraFields,Map<String,JSONObject> idMapWithExtraFields) throws ConnectException, JSONException{
		//TODO: Remove
//		if(true){
//			return;
//		}
		Calendar cal = Calendar.getInstance();
		int doy=cal.get(Calendar.DAY_OF_YEAR);
		String nType="";
		
		for(String id:idSet){
			if(urlHasPlaceholders){
				extraFields=idMapWithExtraFields.get(id);
				
			}
			if(extraFields!=null && extraFields.has("notificationType")){
				nType=extraFields.getString("notificationType");
			}else{
				validChannelData=false;
			}
			JSONArray userRecordSet=getUserRecord("tokens",id, idType);
			if(userRecordSet==null)
				return;
			int size=userRecordSet.length();
			for(int i=0;i<size;i++){
				JSONObject userRecord=userRecordSet.getJSONObject(i);
				log.info("Retrieved user record"+userRecord.toString());
				if(userRecord.has("PK")) {
					String deviceID = userRecord.getString("PK");
					String keyConstruct = "nc_" + nType.toLowerCase() + "_" + platform + "_" + deviceID + "_" + appId + "_" + doy;
					if (deleteCappingCounter(keyConstruct)) {
						log.info("Capping data deleted successfully for " + keyConstruct);
					}else{
						log.error("Failed to delete capping counter");
						throw new ConnectException("Failed to delete cpping counter");
					}
				}
			}
			
		}
		Log.info("Cleared capping data successfully for "+platform);
	}

	public void clearCappingCounterEntryFromRedis(String appId,String platform,Set<String> idSet,String idType,String nType) throws ConnectException, JSONException{
		Calendar cal = Calendar.getInstance();
		int doy=cal.get(Calendar.DAY_OF_YEAR);

		for(String id:idSet){
			JSONArray userRecordSet=getUserRecord("tokens",id, idType);
			if(userRecordSet==null)
				return;
			int size=userRecordSet.length();
			for(int i=0;i<size;i++){
				JSONObject userRecord=userRecordSet.getJSONObject(i);
				log.info("Retrieved user record"+userRecord.toString());
				if(userRecord.has("PK")) {
					String deviceID = userRecord.getString("PK");
					String keyConstruct = "nc_" + nType.toLowerCase() + "_" + platform + "_" + deviceID + "_" + appId + "_" + doy;
					if (deleteCappingCounter(keyConstruct)) {
						log.info("Capping data deleted successfully for " + keyConstruct);
					}else{
						log.error("Failed to delete capping counter");
						throw new ConnectException("Failed to delete cpping counter");
					}
				}
			}

		}
		Log.info("Cleared capping data successfully for "+platform);
	}
	
	public boolean modifyNotificationCappingConfig(Map<String,Integer> config) throws JSONException{
//		CassandraHelper casHelper = CassandraHelper.getInstance();
//		Session cassandraSession = casHelper.connect(cassandraConfigHost, cassandraConfigPort);
		JSONObject configValue = new JSONObject();
		JSONObject cappingMap =new JSONObject();
		for(Map.Entry<String, Integer> entry: config.entrySet()){
			cappingMap.put(entry.getKey(), entry.getValue());
			
		}
		log.info("Adding capping config to config db"+cappingMap.toString());
		configValue.put("CappingMap", cappingMap);
//		Statement updateStatement = QueryBuilder.update("lgp", "notification_conf").with(QueryBuilder.set("value", configValue.toString())).where(QueryBuilder.eq("module", "pushkar")).and(QueryBuilder.eq("submodule", "dynamic_config")).and(QueryBuilder.eq("key", "features"));
//		cassandraSession.execute(updateStatement);
//		cassandraSession.close();
		
		MyntraService updateCappingCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONCAPPING, APINAME.UPDATECAPPING,init.Configurations,configValue.toString());
		System.out.println("Updating the capping limit with "+configValue.toString());
		RequestGenerator requestGen = new RequestGenerator(updateCappingCall);
		String response = requestGen.returnresponseasstring();
		
		if(requestGen.response.getStatus()==200){
			if(this.readJsonPath(response, "$.statusMessage").equalsIgnoreCase("Capping updated")){
				log.info("Updated the capping limit successfully to "+configValue);
				return(true);
			}else{
				log.error("Failed to update capping config");
				return(false);
			}
		}else{
			log.error("Failed to update capping config");
			return(false);
		}

		
	}
	
	public Map<String,Integer> getNotificationCappingConfig() throws JSONException{

		MyntraService getCappingCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONCAPPING, APINAME.VIEWCAPPING,init.Configurations);
		RequestGenerator requestGen = new RequestGenerator(getCappingCall);
		String response = requestGen.returnresponseasstring();
		if(requestGen.response.getStatus()==200){
			if(this.readJsonPath(response, "$.statusMessage").equalsIgnoreCase("Capping retrieved")){
				log.info("Retrieved the capping config successfully");
			}else{
				log.error("Failed to retrieve the capping config");
				return(null);
			}
		}else{
			log.error("Failed to retrieve capping config");
			return(null);
		}
		JSONObject cappingConfigJSON = new JSONObject(readJsonPath(response, "$.result.CappingMap"));
		Map<String,Integer> cappingConfig = parseCappingMapFromJSON(cappingConfigJSON);
		//cassandraSession.close();
		return(cappingConfig);
	}
	
	public Set<String> getCappingCounter(String key) throws ConnectException {
		MyntraService getCappingCounterCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONRESULTS, APINAME.CAPPINGCOUNTER,init.Configurations);
		getCappingCounterCall.URL = apiUtil.prepareparameterizedURL(getCappingCounterCall.URL,new String[] { redisHosts[0],""+redisPorts[0],key });
		System.out.println("Querying for notifications results "+getCappingCounterCall.URL);
		RequestGenerator requestGen=getRequestGen(getCappingCounterCall);
		String response = requestGen.returnresponseasstring();
		Set<String> cappingCounter = new HashSet<>();
		if(requestGen.response.getStatus()==200 && readJsonPath(response, "$.status").equalsIgnoreCase("success")){
			Gson gson= new Gson();

			cappingCounter = (Set<String>) gson.fromJson(readJsonPath(response, "$.data"), cappingCounter.getClass());
		}else{
			throw new ConnectException("Failed to retrieve capping data");
		}

		return(cappingCounter);
		
	}
	
	public boolean deleteCappingCounter(String key){
		MyntraService getCappingCounterCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONRESULTS, APINAME.DELETECAPPINGCOUNTER,init.Configurations);
		getCappingCounterCall.URL = apiUtil.prepareparameterizedURL(getCappingCounterCall.URL,new String[] { redisHosts[0],""+redisPorts[0],key });
		System.out.println("Querying for notifications results "+getCappingCounterCall.URL);
		RequestGenerator requestGen=getRequestGen(getCappingCounterCall);
		String response = requestGen.returnresponseasstring();
		if(requestGen.response.getStatus()==200) {

				return (true);

		}else{
			return(false);
		}
	}
	
	public Map<String,Integer> parseCappingMapFromJSON(JSONObject cappingConfigJSON) throws JSONException{
		Iterator<?> keys = cappingConfigJSON.keys();
		Map<String,Integer> cappingConfig = new HashMap<>();
		while(keys.hasNext()){
			String key = (String)keys.next();
			cappingConfig.put(key, cappingConfigJSON.getInt(key));
			
		}
		return(cappingConfig);
	}
	
	public String evaluateIncludeExpression(String notificationIncludeExpression,Map<String, JSONObject> idsWithTemplatePlaceholders, Set<String> idSet) throws JSONException{
		String[] includeExpressionList;
		String idType="";
		if (notificationIncludeExpression.equals("EVERYONE")) {
			log.info("Broadcast : "+true);
			broadcast = true; 
		} else {
			includeExpressionList = notificationIncludeExpression.split(" ");
			idType = includeExpressionList[0];
			log.info("Id type: "+idType);
			if (includeExpressionList[2].equals("LIST")) {
				log.info("Parsing ids from list");
				for (int i = 3; i < includeExpressionList.length - 1; i++)
					includeExpressionList[3] = includeExpressionList[3] + includeExpressionList[i + 1];
				String[] idList = includeExpressionList[3].split(",");
				log.info("Id list: "+idList);
				for (String id : idList) {
					idSet.add(id);
				}
				// Resolve the URL for ids and template placeholders value
			} else if (includeExpressionList[2].equals("URL")) {
				try {
					log.info("Parsing ids from URL");
					this.readURLForIdListAndTemplatePlaceholders(includeExpressionList[3],idsWithTemplatePlaceholders,idSet);
					template = urlHasPlaceholders;
				} catch (IOException e) {
					validData = false;
				}
			}
		}
		return(idType);
	}
	
	public JSONObject getDBEntryForInappBeacon(String notificationName,String nid,String id) throws JSONException{
		MyntraService getDBEntryCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONRESULTS, APINAME.NOTIFICATIONDBENTRY,init.Configurations);
		getDBEntryCall.URL = apiUtil.prepareparameterizedURL(getDBEntryCall.URL,new String[] { dbHost, notificationName.toLowerCase(),nid.toLowerCase(),id});
		log.info("Querying for notifications DBentry "+getDBEntryCall.URL);
		RequestGenerator requestGen=getRequestGen(getDBEntryCall);
		String response = requestGen.returnresponseasstring();
		if(requestGen.response.getStatus()==200 && readJsonPath(response, "$.status").equalsIgnoreCase("success")){
			JSONObject responseJson= new JSONObject(response);
			JSONObject dbEntry= responseJson.getJSONObject("data");
			return(dbEntry);
		}else{
			return(null);
		}
		
	}
	
	public void validateInappAndBeacon(Map<String, Object> testData,String notificationName, JSONObject extraFields, String nid, Set<String> idSet,Map<String, JSONObject> idMapWithExtraFields,Map<String, Boolean> idValidMapping, ArrayList<String> failures,String nType) throws JSONException{

		if(broadcast){
			idSet.add("__all__");
			
		}
		int failCount = 0;
		String failedIDs="";
		for (String id : idSet) {
			if (urlHasPlaceholders) {
				extraFields = idMapWithExtraFields.get(id);
				validChannelData = idValidMapping.get(id);
			}
			log.info("id: "+id);
			JSONObject result = getDBEntryForInappBeacon(notificationName, nid, id);
			if (result!=null) {
				log.info("NID ROW : "+result.toString());
				if(nType!=null){
					extraFields.put("notificationType",nType);
				}
				validateFieldValues(result, extraFields, notificationName, failures,nid);
			} else {
				if (testData.containsKey("failureExpected")) {
					failCount++;
					failedIDs+=id+", ";
				} else if (validData && validChannelData) {
					failures.add("The notification for " + id + " is not created");
					continue;
				} else{
					failures.add("WARNING: The payload passed is not valid for "+ id);
					continue;
				}
			}

		}
		log.info("Failed ids "+failedIDs);
		//If it is broadcast and failure is expected
		if(broadcast && testData.containsKey("failureExpected") && !(failCount>0)){
			failures.add("The broadcast has not failed as expected");
		}else if (testData.containsKey("failureExpected")) {
			if( (int) testData.get("failureExpected") != failCount)
				failures.add("The no of failures ("+failCount+") not match the expected failure count ("+testData.get("failureExpected")+")");
		}else if(failCount>0){
			failures.add("There are unexpected failures. Fail count: "+failCount+" Failed ids : "+failedIDs);
		}
	}

	private RequestGenerator getRequestGen(MyntraService service){
		int retryCount=5;
		boolean success=false;
		RequestGenerator requestGen=null;
		do {
			try {
				requestGen = new RequestGenerator(service);
				success=true;
			}catch(Exception e){
				log.warn("Failed to get push status (GCM/APNS response). retrying");
			}
		}while(!success && retryCount-->0);
		return (requestGen);
	}
	
	
	private Map<String,String> getGCMAPNSResultMap(String nid) throws JSONException{
		Map<String,String> resultsMap = new HashMap<>();
		MyntraService pushStatusCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONCAPPING, APINAME.NOTIFICATIONSTATUS,init.Configurations);
		pushStatusCall.URL = apiUtil.prepareparameterizedURL(pushStatusCall.URL,new String[] { nid });
		System.out.println("Querying for notifications results "+pushStatusCall.URL);
		RequestGenerator requestGen=getRequestGen(pushStatusCall);


		String response = requestGen.respvalidate.returnresponseasstring();
		if(requestGen.response.getStatus()==200 && readJsonPath(response, "$.statusMessage").equalsIgnoreCase("Status retrieved")){
			JSONObject responseObject = new JSONObject(readJsonPath(response, "$.result.status"));
			Iterator<String> keys = (Iterator<String>) responseObject.keys();
			while(keys.hasNext()){
				String key = keys.next();
				resultsMap.put(key, responseObject.getString(key));
			}
		}
		return(resultsMap);
		
	}
	
	
	public void validatePush(String nid,String appId, Set<String> idSet, String idType, JSONObject extraFields,Map<String,JSONObject> idMapWithExtraFields, Map<String, Integer> cappingConfig,JSONArray platforms,ArrayList<String> failures,Map<String,Object> testData,Map<String, Boolean> idValidMapping,String nType) throws JSONException, ConnectException{
		

		if(broadcast){
			log.info("Verifying push notification for broadcast");
			boolean connected=true;
			int maxRetry=5;
			AerospikeClient asClient=null;
			do{
				try{
					asClient = new AerospikeClient(aerospikeHost, aerospikePort);
					log.info("Connected to aerospike for device id details");
				}catch(Exception e){
					connected=false;
				}
			}while(!connected&&maxRetry-->0);
			if(!connected){
				log.info("Failed to connect to Aerospike for device id details");
					throw new ConnectException("Aerospike Connection failed");
			}
			String[] setInfos=Info.request(new InfoPolicy(), asClient.getNodes()[0], "sets").split(";");
			int androidObjectCount = 0;
			int iosObjectCount = 0;
			for(String setInfo:setInfos){
				Pattern setPattern = Pattern.compile("set=(android)|(ios)");
				Matcher setMatcher = setPattern.matcher(setInfo);
				if(setMatcher.find()){
					Pattern objPattern = Pattern.compile("objects=[0-9]*");
					Matcher objMatcher = objPattern.matcher(setInfo);
					objMatcher.find();
					String objCount = objMatcher.group(0);
					if(setMatcher.group(0).contains("android")){
						androidObjectCount = Integer.parseInt(objCount.substring(objCount.indexOf("=")+1));
					}else{
						iosObjectCount = Integer.parseInt(objCount.substring(objCount.indexOf("=")+1));
					}
				}
			}
			log.info("Android count in db: "+androidObjectCount);
			log.info("Ios count in db: "+iosObjectCount);
			
			int androidGCMHitCount = 0;
			int iosAPNSHitCount = 0;
			int androidGCMHitCount_previous = 0;
			int iosAPNSHitCount_previous = 0;
			int retryCount=60;
			boolean success=true;
			do{
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Map<String, String> notificationResultMap = getGCMAPNSResultMap(nid);//jedisClust.hgetAll("n_" + redisKey);//TODO:Remove mock//mockNotifiationResultMap(androidObjectCount, iosObjectCount);
				log.info("Notification Result Map: "+notificationResultMap);
				if(notificationResultMap.containsKey("as")||notificationResultMap.containsKey("xs")){
					log.info("androidGCMHitCount_previous: "+androidGCMHitCount_previous+"androidGCMHitCount: "+androidGCMHitCount);
					androidGCMHitCount_previous=androidGCMHitCount;
					androidGCMHitCount = Integer.parseInt(notificationResultMap.get("as"))+Integer.parseInt(notificationResultMap.get("af"));
					log.info("iosAPNSHitCount_previous: "+iosAPNSHitCount_previous+"iosAPNSHitCount: "+iosAPNSHitCount);
					iosAPNSHitCount_previous=iosAPNSHitCount;
					iosAPNSHitCount = Integer.parseInt(notificationResultMap.get("xs"))+Integer.parseInt(notificationResultMap.get("xf"));
				}else{
					success=false;
				}
				
				retryCount--;
			}while(((androidGCMHitCount_previous!=androidGCMHitCount)||(iosAPNSHitCount!=iosAPNSHitCount_previous))&&(retryCount>0));
			
			if(success){
				if(platforms.toString().contains("android")){
					log.info("Checking for 99.99% success rate for android");
					int expectedSuccess = (int)(androidObjectCount*99.99/100);
					if(androidGCMHitCount<expectedSuccess){
						log.info("Android success rate was less then 99.99%");
						failures.add("The GCM hits for android did not reach the expected success rate of 99.99%");
					}
				}
				
				if(platforms.toString().contains("ios")){
					log.info("Checking for 99.99% success rate for ios");
					int expectedSuccess = (int)(iosObjectCount*99.99/100);
					if(iosAPNSHitCount<expectedSuccess){
						log.info("Ios success rate was less then 99.99%");
						failures.add("The APNS hits for ios did not reach the expected success rate of 99.99%");
					}
				}
			}else if(!testData.containsKey("failureExpected")){
				failures.add("Broadcast request was fail");
			}
			asClient.close();
				
			
		}else{
			int failCount=0;
			int androidSuccessCount=0;
			int iosSuccessCount=0;
			ArrayList<String> failedIds=new ArrayList<>();
			int cappedCount=0;
			ArrayList<String> cappedIds=new ArrayList<>();
			
			
			Calendar cal = Calendar.getInstance();
			int doy=cal.get(Calendar.DAY_OF_YEAR);
			String dayOfYear=String.format("%d", doy);
			
			for(String id:idSet){
				if(urlHasPlaceholders){
					extraFields=idMapWithExtraFields.get(id);
					validChannelData=idValidMapping.get(id);
				}
				if(validChannelData && extraFields.has("notificationType")){
					if(nType==null)
						nType=extraFields.getString("notificationType");
				}else if(nType==null){
					validChannelData=false;
				}
				JSONArray userRecordSet=getUserRecord("tokens",id, idType);
				if(userRecordSet==null){
					failedIds.add(id);
					failCount++;
					continue;
				}
				for(int i=0;i<userRecordSet.length();i++){
					JSONObject userRecord=userRecordSet.getJSONObject(i);
					log.info("Retrieved user record"+userRecord.toString());
					String devicePlatform = getDevicePlatform(id);
					if(!userRecord.has("PK")){
						continue;
					}
					String deviceID=userRecord.getString("PK");
					String cappingCountKey="nc_"+nType.toLowerCase()+"_"+devicePlatform+"_"+deviceID+"_"+appId+"_" + dayOfYear;
					log.info("Retrieving the notification capping set");
					Set<String> notificationCappingMap = getCappingCounter(cappingCountKey);
					log.info("Notification capping set "+notificationCappingMap);
					int cappingCount=notificationCappingMap.size();
					boolean cappingExceeded=false;
					
					//If the capping limit is exceeded expect failure
					if(cappingData.containsKey(deviceID) && cappingData.get(deviceID).containsKey(appId+"_"+nType)){
						int cappingCountInData = cappingData.get(deviceID).get(appId+"_"+nType);
						if(cappingCountInData>=cappingConfig.get(nType.toLowerCase())){
							cappingExceeded=true;
						}
					}
					
					boolean success=false;
					
					if(cappingCount!=0){
						if(cappingData.containsKey(deviceID) && cappingData.get(deviceID).containsKey(appId+"_"+nType)){
							int cappingCountInData = cappingData.get(deviceID).get(appId+"_"+nType);
							if(cappingCount ==(cappingCountInData+1)){
								success=true;
								cappingData.get(deviceID).put(appId+"_"+nType,cappingCount);
							}
						}else{
							if(cappingCount==1){
								success=true;
								if(cappingData.containsKey(deviceID)){
									cappingData.get(deviceID).put(appId+"_"+nType, 1);
								}else{
									Map<String,Integer> ntypeAppidMap=new HashMap<>();
									ntypeAppidMap.put(appId+"_"+nType, 1);
									cappingData.put(id, ntypeAppidMap);
								}
							}
						}
					}
					
					//If the platform doesnt match expect failure
					if(!success && cappingExceeded){
						cappedCount++;
						cappedIds.add(id);
					}else if(!success && (comparePlatform(id, idType, platforms, userRecord) && validChannelData && validData)){
						
							failCount++;
							failedIds.add(id);
						
					}else if(success){
						if(!comparePlatform(id, idType, platforms, userRecord)){
							failures.add("The notification has succeeded for id "+id+" while the plaform didnt match");
						}else if(cappingExceeded){
							failures.add("The notification for id "+id+" has succeeded after capping limit has exceeded");
						}else if(!validChannelData || !validData){
							failures.add("Notification has pass for invalid data for id "+id);
						}
						if(devicePlatform=="android")
							androidSuccessCount++;
						else
							iosSuccessCount++;
					}
				}
			}
			log.info("Capped devices "+cappedIds);
			log.info("Failed devices "+failedIds);
			if (testData.containsKey("failureExpected")) {
				if((int) testData.get("failureExpected") != failCount){
					failures.add("The no of failures ("+failCount+") not match the expected failure count ("+testData.get("failureExpected")+") "+". Failed IDs:"+failedIds);
				}
			}else if(failCount>0){
				failures.add("Some of the id have failed :"+failedIds);
			}
			
			if(iosSuccessCount>0|| androidSuccessCount>0){
				HashCode mmh3 = com.google.common.hash.Hashing.murmur3_128().hashString("nanlyt_nid_" + nid,StandardCharsets.UTF_8);
				String redisKey = new Base64().encodeAsString(mmh3.asBytes());
				Map<String, String> notificationResultMap = getGCMAPNSResultMap(nid);//jedisClust.hgetAll("n_" + redisKey);//TODO: Remove mock//mockNotifiationResultMap(androidSuccessCount, iosSuccessCount);
				NotificationTest.log.info("Notification Result Map: "+notificationResultMap);
				int androidtotalGCMHits = 0;
				if(notificationResultMap.containsKey("as")){
					androidtotalGCMHits=Integer.parseInt(notificationResultMap.get("as"));
					androidtotalGCMHits += Integer.parseInt(notificationResultMap.get("af"));
				}
				int iosTotalApnsHits =0;
				if(notificationResultMap.containsKey("xs")){
					iosTotalApnsHits=Integer.parseInt(notificationResultMap.get("xs"));
					iosTotalApnsHits += Integer.parseInt(notificationResultMap.get("xf"));
				}
				if(urlHasPlaceholders && saveResults) {
					androidSuccess+=androidSuccessCount;
					iosSuccess+=iosSuccessCount;
					gcmHits=androidtotalGCMHits;
					apnsHits=iosTotalApnsHits;

				}else{
					if (androidSuccessCount != androidtotalGCMHits) {
						failures.add("Not all the ids went through to GCM for android devices. Total GCM Hits: " + androidtotalGCMHits);
					}
					if (iosSuccessCount != iosTotalApnsHits) {
						failures.add("Not all the ids went through to APNS for ios devices. Total APNS Hits: " + iosTotalApnsHits);
					}
				}
			}
			
		}
		
	}
	public int androidSuccess=0;
	public int iosSuccess=0;
	public int apnsHits=0;
	public int gcmHits=0;
	public boolean saveResults=false;


	
	public String getNTypeKey(String nType){
		switch(nType.toLowerCase()){
		case "marketing":return("m");
		case "remarketing":return("r");
		case "engagement":return("e");
		case "transactional":return("t");
		case "forum":return("f");
		case "community":return("c");
		default:return("x");
		
		}
	}
	
	public String getDevicePlatform(String id){
		if(id.contains("android")){
			return("android");
		}else if(id.contains("ios")){
			return("ios");
		}else{
			return("");
		}
	}
	
	public JSONArray getUserRecord(String namespace,String id, String idType) throws ConnectException, JSONException {

		String deviceOSPlatform = getDevicePlatform(id);
		MyntraService getDeviceRecordCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONRESULTS, APINAME.GETDEVICERECORD,init.Configurations);
		getDeviceRecordCall.URL = apiUtil.prepareparameterizedURL(getDeviceRecordCall.URL,new String[] { namespace,deviceOSPlatform,id,idType});
		log.info("Querying for device Record "+getDeviceRecordCall.URL);
		RequestGenerator requestGen=getRequestGen(getDeviceRecordCall);

		String response = requestGen.returnresponseasstring();
		if(requestGen.response.getStatus()==200 && readJsonPath(response, "$.status").equalsIgnoreCase("success")){
			JSONObject responseJson= new JSONObject(response);
			JSONArray dbEntry= responseJson.getJSONArray("data");
			return(dbEntry);
		}else{
			return(null);
		}
		
	}
	
	public boolean comparePlatform(String id, String idType, JSONArray platforms,JSONObject userRecord) throws JSONException{
		
		String deviceOSPlatform = getDevicePlatform(id);

		boolean exists=false;
		JSONObject platform=null;
		for(int i=0;i<platforms.length();i++){
			platform = (JSONObject) platforms.get(i);
			if(platform.getString("name").equalsIgnoreCase(deviceOSPlatform)){
				exists=true;
				break;
			}
		}
		log.info("Comparing platform "+platform+" with record "+userRecord);
		String deviceVersion = userRecord.getString("app_version");
		if(exists){
			if(platform.has("minVersion")){
				String minversion = platform.getString("minVersion");
				log.info("comparing min version");
				int diff = compareVersions(deviceVersion, minversion);
				log.info("version diff of "+deviceVersion+","+minversion+" is "+diff);
				if(diff<0){
					return(false);
				}
			}
			if(platform.has("maxVersion")){
				String maxversion = platform.getString("maxVersion");
				int diff = compareVersions(deviceVersion, maxversion);
				log.info("comparing max version");
				log.info("version diff of "+deviceVersion+","+maxversion+" is "+diff);
				if(diff>0){
					return(false);
				}
			}
			if(platform.has("excludeVersions")){
				String excludeVersions=platform.getString("excludeVersions");
				if(excludeVersions.contains(deviceVersion)){
					return(false);
				}
			}
			return(true);
		}else{
			return(false);
		}
		
	}
	
	public int compareVersions(String version1, String version2){
		String val1[] = version1.split("\\.");
		String val2[]= version2.split("\\.");
		int diff=0;
		for(int i=0;i<val1.length&&i<val2.length;i++){
			diff=Integer.parseInt(val1[i])-Integer.parseInt(val2[i]);
			if(diff!=0)
				return(diff);
		}
		
		return(diff);
	}

	public String getSendNotificationPayload(JSONObject publishPayload) throws JSONException {
		JSONObject sendPayload=new JSONObject();
		sendPayload.put("target",getSendTarget(readJsonPath(publishPayload.toString(),"$.target.includeExpression")));
		if(publishPayload.has("notificationType")){
			sendPayload.put("notificationType",publishPayload.getString("notificationType"));
		}
		if(publishPayload.getJSONObject("target").has("channels")){
			sendPayload.put("channels",publishPayload.getJSONObject("target").get("channels"));

		}else if(publishPayload.has("templateData")){
			sendPayload.put("templateData",publishPayload.get("templateData"));
		}

		if(publishPayload.has("startTime")) {
			sendPayload.put("startTime", publishPayload.get("startTime"));
		}
		if(publishPayload.has("endTime")){
			sendPayload.put("endTime", publishPayload.get("endTime"));
		}
		return (sendPayload.toString());
	}

	public JSONObject getSendTarget(String includeExpression) throws JSONException {
		JSONObject target=new JSONObject();
		if(includeExpression.equalsIgnoreCase("EVERYONE")){
			target.put("segment","EVERYONE");
		}else {

			String[] includeList = includeExpression.split(" ");
			target.put("targetType",includeList[0]);
			target.put("targetContainer",includeList[2]);
			String targetCommaSeperated="";
			for(int i=3;i<includeList.length;i++){
				targetCommaSeperated+=includeList[i];
				if(i!=includeList.length-1)
					targetCommaSeperated+=",";
			}
			target.put("target",targetCommaSeperated);

		}
		return (target);

	}

	public String sendNotification(HashMap<String,String> headers,String payload,String whom,Map<String,Object> testData) throws JSONException {
		String sendpayload=getSendNotificationPayload(new JSONObject(payload));
		MyntraService sendCall = Myntra.getService(ServiceType.LGP_NOTIFICATIONCAPPING, APINAME.SENDNOTIFICATION,init.Configurations, sendpayload);
		//sendCall.URL = apiUtil.prepareparameterizedURL(sendCall.URL,new String[] { whom.substring(0, whom.indexOf(":")) });
		log.info("Sending notification to url: "+sendCall.URL+"\n" +
				"with Payload:\n"+sendpayload);

		RequestGenerator requestGen = new RequestGenerator(sendCall, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		log.info(response);
		if (requestGen.response.getStatus() != 200) {
			if (testData.containsKey("expectedResponse")) {
				if (requestGen.response.getStatus() != (int) testData.get("expectedResponse")){
					Assert.fail("Response " + requestGen.response.getStatus() + " not matching with expected response "+ testData.get("expectedResponse"));
					return("");
				}
			} else {
				Assert.fail("Send notification call failed. Response : " + requestGen.response.getStatus());
				return("");
			}
		}

		String responseStatusType = readJsonPath(response, "$.statusMessage");

		if (responseStatusType != null && !responseStatusType.equals("Send accepted")) {
			if (testData.containsKey("expectedStatusType")) {
				if (!testData.get("expectedStatusType").equals(responseStatusType)) {
					Assert.fail("StatusType " + responseStatusType + " not matching with expected StatusType "+ testData.get("expectedStatusType"));
					return("");
				}
			} else {
				Assert.fail("Send notification call failed. StatusType : " + responseStatusType);
				return("");

			}
		}

		if (responseStatusType == null) {
			Assert.fail("No StatusType in response");
			return("");
		}
		return(response);
	}
	public String publishNotification(HashMap<String,String> headers,String payload,String whom,Map<String,Object> testData){

		MyntraService publishCall = Myntra.getService(ServiceType.LGP_PUMPSSERVICE, APINAME.PUBLISHACTION,init.Configurations, payload);
		publishCall.URL = apiUtil.prepareparameterizedURL(publishCall.URL,new String[] { whom.substring(0, whom.indexOf(":")) });
		log.info("Publishing notification to url: "+publishCall.URL+"\n" +
				"with Payload:\n"+payload);

		RequestGenerator requestGen = new RequestGenerator(publishCall, headers);
		String response = requestGen.respvalidate.returnresponseasstring();
		System.out.println(response);
		if (requestGen.response.getStatus() != 200) {
			if (testData.containsKey("expectedResponse")) {
				if (requestGen.response.getStatus() != (int) testData.get("expectedResponse")){
					Assert.fail("Response " + requestGen.response.getStatus() + " not matching with expected response "+ testData.get("expectedResponse"));
					return("");
				}
			} else {
				Assert.fail("Publish notification call failed. Response : " + requestGen.response.getStatus());
				return("");
			}
		}

		String responseStatusType = readJsonPath(response, "$.status.statusType");

		if (responseStatusType != null && !responseStatusType.equals("SUCCESS")) {
			if (testData.containsKey("expectedStatusType")) {
				if (!testData.get("expectedStatusType").equals(responseStatusType)) {
					Assert.fail("StatusType " + responseStatusType + " not matching with expected StatusType "+ testData.get("expectedStatusType"));
					return("");
				}
			} else {
				Assert.fail("Publish notification call failed. StatusType : " + responseStatusType);
				return("");

			}
		}

		if (responseStatusType == null) {
			Assert.fail("No StatusType in response");
			return("");
		}
		return(response);
	}
			
	public void populateExtraFieldsFromTemplateDataAndPlaceholders(JSONObject extraFields,JSONObject templateData,Map<String, JSONObject> idsWithTemplatePlaceholders,Map<String, JSONObject> idMapWithExtraFields,Map<String,Boolean> idValidMapping,JSONObject placeHolders) throws JSONException{
		
		// Get the template data into the extra fields
		if (template) {
			log.info("Populating template data");
			Iterator<String> templateKeys = templateData.keys();
			while (templateKeys.hasNext()) {
				String key = templateKeys.next();
				extraFields.put(key, templateData.get(key));
			}
			// Populate the placeholder into the ids
			if (urlHasPlaceholders) {
				for (Map.Entry<String, JSONObject> entry : idsWithTemplatePlaceholders.entrySet()) {
					JSONObject extraFieldsAfterPopulated = new JSONObject(populatePlaceholderData(extraFields.toString(),entry.getValue()));
					if (extraFieldsAfterPopulated != null) {
						idMapWithExtraFields.put(entry.getKey(), extraFieldsAfterPopulated);
						log.info("Mapping "+entry.getKey()+"with extraField:"+extraFieldsAfterPopulated.toString());
						idValidMapping.put(entry.getKey(), true);
					} else {
						idValidMapping.put(entry.getKey(), false);
					}
				}
			} else {
				JSONObject extraFieldsAfterPopulated = new JSONObject(populatePlaceholderData(extraFields.toString(), placeHolders));
				if (extraFieldsAfterPopulated == null) {
					validChannelData=false;
				}else{
					copyJsonValues(extraFields, extraFieldsAfterPopulated);
					log.info("ExtraField after template population : "+extraFields.toString());
				}
			}
			
		}
	}

	public JSONArray populateChannels(JSONArray templateData,Map<String, JSONObject> idsWithTemplatePlaceholders,Map<String, JSONArray> idMapWithChannelsData,Map<String,Boolean> idValidMapping,JSONObject placeHolders) throws JSONException {
		// Populate the placeholder into the ids
		if (urlHasPlaceholders) {
			for (Map.Entry<String, JSONObject> entry : idsWithTemplatePlaceholders.entrySet()) {
				JSONArray channelsAfterPopulated = new JSONArray(populatePlaceholderData(templateData.toString(),entry.getValue()));
				if (channelsAfterPopulated != null) {
					idMapWithChannelsData.put(entry.getKey(), channelsAfterPopulated);
					log.info("Mapping "+entry.getKey()+"with extraField:"+channelsAfterPopulated.toString());
					idValidMapping.put(entry.getKey(), true);
				} else {
					idValidMapping.put(entry.getKey(), false);
				}
			}
			return(null);
		} else {
			JSONArray channels = new JSONArray(populatePlaceholderData(templateData.toString(), placeHolders));
			if (channels == null) {
				validChannelData=false;
				return (null);
			}else{
				//copyJsonValues(templateData, extraFieldsAfterPopulated);
				log.info("ExtraField after template population : "+templateData.toString());
				return (channels);
			}
		}
	}
	
	public void copyJsonValues(JSONObject objBase,JSONObject objValues) throws JSONException{
		Iterator<String> keys = objValues.keys();
		while(keys.hasNext()){
			String key =keys.next();
			objBase.put(key, objValues.get(key));
		}
	}

	
}
