package com.myntra.apiTests.portalservices.lgpservices;

import com.myntra.apiTests.dataproviders.NotificationDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.ConnectException;
import java.util.*;

public class NotificationTest extends NotificationDP {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	public static Logger log = Logger.getLogger(NotificationTest.class);
	NotificationsHelper helper = new NotificationsHelper();

	static APIUtilities apiUtil = new APIUtilities();
	static HashMap<String, String> headers = new HashMap<String, String>();
	

	@BeforeClass(alwaysRun = true)
	public void setUp() throws ConnectException {
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		if (init.Configurations.GetTestEnvironemnt().toString().toLowerCase().contains("fox7")) {
			helper.dbHost = "qalg2"; //qalg2
			helper.dbPort = 9160;
			helper.aerospikeHost = "qalg3"; //qalg3
			helper.aerospikePort = 3000;
			helper.redisHosts = new String[] { "qalg2"};//
			helper.redisPorts = new int[] {7002 };
			helper.cassandraConfigHost="qalg2";
			helper.cassandraConfigPort=9042;
			//TODO: REMOVE COMMENTS
			//Insert android entries in to DB
//			helper.addDummyUserIdsInAerospike(helper.aerospikeHost, helper.aerospikePort, "android", 100,helper.aerospikeEntryConvention);
			//Insert ios entries in to DB
//			helper.addDummyUserIdsInAerospike(helper.aerospikeHost,helper.aerospikePort, "ios", 100,helper.aerospikeEntryConvention);
		}

	}

	@Test(dataProvider = "notificationsDp",groups ="deprecated")
	public void testPublishCall(Map<String, Object> testData) throws JSONException, ConnectException {
		try{
		ArrayList<String> failures = new ArrayList<>();
		JSONObject[] notificationPayloads = (JSONObject[]) testData.get("notificationPayload");
		log.info("Starting test for "+testData.get("Case"));
		for(JSONObject notificationPayload:notificationPayloads){
			Long endTime = System.currentTimeMillis() + 18000000;
			if(!notificationPayload.has("endTime"))
				notificationPayload.put("endTime", endTime);
			String payload = notificationPayload.toString();
			String whom = notificationPayload.getString("whom");
			helper.urlHasPlaceholders=false;
			helper.template=false;
//			// Reset the capping capability for all the ids
//			Set<HostAndPort> hpPort = new HashSet<>();
//			for (String host : helper.redisHosts) {
//				for (int port : helper.redisPorts)
//					hpPort.add(new HostAndPort(host, port));
//			}
			String appId = notificationPayload.getString("whom");
			
			// Evaluate the include expression
			String notificationIncludeExpression = notificationPayload.getJSONObject("target").getString("includeExpression");
			log.info("IncludeExpression: "+notificationIncludeExpression);
			helper.broadcast = false;
			helper.validData = true;
			helper.validChannelData=true;
			
			// DEVICEID or USERID
			Map<String, JSONObject> idsWithTemplatePlaceholders = new HashMap<String, JSONObject>();
			Set<String> idSet = new HashSet<>();
			
			//Retrieve the ids from the include expression string
			log.info("Evaluating include expression");
			String idType=helper.evaluateIncludeExpression(notificationIncludeExpression, idsWithTemplatePlaceholders, idSet);
			
			// Resolve the templateData section for getting the field values
			JSONObject templateData = new JSONObject();
			JSONObject placeHolders = new JSONObject();
			if (notificationPayload.has("templateData")) {
				helper.template = true;
				log.info("Template data: true");
				JSONObject templatePayload = notificationPayload.getJSONObject("templateData");
				try {
					templateData = new JSONObject(helper.getTemplateData(templatePayload));
				} catch (Exception e) {
					log.warn("Failed to get template data due to exception", e);
					log.info("Validating for invalid template");
					helper.validChannelData=false;
				}
				if (templatePayload.has("placeholders")) {
					log.info("Parsing placeholders for template");
					placeHolders = templatePayload.getJSONObject("placeholders");
				}
			}
			//Repeat the requests for
			int repeatRequest = 1;
			if(notificationPayload.has("repeatRequest")){
				repeatRequest=notificationPayload.getInt("repeatRequest");
				log.info("Notification repeat cycle: "+repeatRequest);
			}
			Map<String,Integer> cappingConfig=null;
			if(notificationPayload.has("updateCappingConfig")){
				
				cappingConfig=helper.parseCappingMapFromJSON(notificationPayload.getJSONObject("updateCappingConfig"));
				log.info("Changing the capping config : "+cappingConfig);
				if(helper.modifyNotificationCappingConfig(cappingConfig)){
					
				}else{
					Assert.fail("Failed to update the capping configuration");
				}
			}else{
				cappingConfig=helper.getNotificationCappingConfig();
			}
			log.info("Current capping config: "+cappingConfig);
			
			String response="";
			String nid="";
				JSONArray channels = notificationPayload.getJSONObject("target").getJSONArray("channels");
				for (int i = 0; i < channels.length(); i++) {
					JSONObject channel = channels.getJSONObject(i);
					JSONObject extraFields = new JSONObject();
					JSONArray platforms = new JSONArray();
					if (channel.has("extraFields")) {
						extraFields = channel.getJSONObject("extraFields");
					}
					if(channel.has("platforms")){
						platforms=channel.getJSONArray("platforms");
					}
					
					Map<String, JSONObject> idMapWithExtraFields = new HashMap<>();
					Map<String, Boolean> idValidMapping = new HashMap<>();
					
					//Populate the extra fields with template data and placeholder data.
					helper.populateExtraFieldsFromTemplateDataAndPlaceholders( extraFields,templateData, idsWithTemplatePlaceholders, idMapWithExtraFields, idValidMapping, placeHolders);

					if(i==0){
						if(!(notificationPayload.has("retainCapping")&& ((boolean)notificationPayload.get("retainCapping")))){
							log.info("Clearing the capping data from redis");
							helper.clearCappingCounterEntryFromRedis(appId, "android", idSet,idType,extraFields,idMapWithExtraFields);
							helper.clearCappingCounterEntryFromRedis(appId, "ios",  idSet,idType,extraFields,idMapWithExtraFields);
							log.info("Clearing capping data from local map");
							helper.cappingData.clear();
						}
						log.info("Publishing the notification");
						response= helper.publishNotification(headers, payload, whom, testData);
						try{
							Thread.sleep(5000);
						}catch(InterruptedException e){
							
						}
						String statusType=helper.readJsonPath(response, "$.status.statusType");
						if (statusType!=null && statusType.equals("SUCCESS")) {
							 nid= helper.readJsonPath(response, "$.data[0].id");
							log.info("nid: "+nid);
						}else{
							Assert.fail("Failed to publish the notification");
						}
					}
					String notificationName = channel.getString("name");
					switch (notificationName.toLowerCase()) {
					case "push": {
						log.info("Validating push notification");
						helper.validatePush(nid, appId, idSet, idType, extraFields, idMapWithExtraFields, cappingConfig, platforms, failures, testData, idValidMapping,null);
	
					}
						break;
					case "inapp": {
						
						helper.validateInappAndBeacon(testData, notificationName, extraFields, nid, idSet, idMapWithExtraFields, idValidMapping,  failures,null);
						break;
					}
					case "beacon": {
						helper.validateInappAndBeacon(testData, notificationName, extraFields, nid, idSet,  idMapWithExtraFields, idValidMapping, failures,null);
						break;
					}
					default:
						failures.add("Invalid notification name :" + notificationName);
					}
				}
			}
		
		if (failures.size() > 0) {
			Assert.fail(helper.getFailuresPoints(failures));
		}else{
			Assert.assertEquals(true, true);
		}
		}catch(Exception e){
			log.error("Execution failed due to exception", e);
			Assert.fail("Execution failed due to exception"+e);
		}

	}

	@Test(dataProvider = "notificationSendDp",groups ="Sanity")
	public void testSendCall(Map<String, Object> testData) throws JSONException, ConnectException {
		try{
			ArrayList<String> failures = new ArrayList<>();
			JSONObject[] notificationPayloads = (JSONObject[]) testData.get("notificationPayload");
			log.info("Starting test for "+testData.get("Case"));
			for(JSONObject notificationPayload:notificationPayloads){
				//Long endTime = System.currentTimeMillis() + 18000000;
				//notificationPayload.put("endTime", endTime);
				String payload = notificationPayload.toString();
				String whom = notificationPayload.getString("whom");
				headers.put("app-id",whom);
				helper.urlHasPlaceholders=false;
				helper.template=false;
//			// Reset the capping capability for all the ids
//			Set<HostAndPort> hpPort = new HashSet<>();
//			for (String host : helper.redisHosts) {
//				for (int port : helper.redisPorts)
//					hpPort.add(new HostAndPort(host, port));
//			}
				String appId = notificationPayload.getString("whom");

				// Evaluate the include expression
				String notificationIncludeExpression = notificationPayload.getJSONObject("target").getString("includeExpression");
				log.info("IncludeExpression: "+notificationIncludeExpression);
				helper.broadcast = false;
				helper.validData = true;
				helper.validChannelData=true;

				// DEVICEID or USERID
				Map<String, JSONObject> idsWithTemplatePlaceholders = new HashMap<String, JSONObject>();
				Set<String> idSet = new HashSet<>();

				//Retrieve the ids from the include expression string
				log.info("Evaluating include expression");
				String idType=helper.evaluateIncludeExpression(notificationIncludeExpression, idsWithTemplatePlaceholders, idSet);

				// Resolve the templateData section for getting the field values
				JSONArray templateData = new JSONArray();
				JSONObject placeHolders = new JSONObject();
				if (notificationPayload.has("templateData")) {
					helper.template = true;
					log.info("Template data: true");
					JSONObject templatePayload = notificationPayload.getJSONObject("templateData");
					try {
						templateData = new JSONArray(helper.getTemplateData(templatePayload));
					} catch (Exception e) {
						log.warn("Failed to get template data due to exception", e);
						log.info("Validating for invalid template");
						helper.validData=false;
					}
					if (templatePayload.has("placeholderMap")) {
						log.info("Parsing placeholders for template");
						placeHolders = templatePayload.getJSONObject("placeholderMap");
					}
				}
				//Repeat the requests for
				int repeatRequest = 1;
				if(notificationPayload.has("repeatRequest")){
					repeatRequest=notificationPayload.getInt("repeatRequest");
					log.info("Notification repeat cycle: "+repeatRequest);
				}
				Map<String,Integer> cappingConfig=null;
				if(notificationPayload.has("updateCappingConfig")){

					cappingConfig=helper.parseCappingMapFromJSON(notificationPayload.getJSONObject("updateCappingConfig"));
					log.info("Changing the capping config : "+cappingConfig);
					if(helper.modifyNotificationCappingConfig(cappingConfig)){

					}else{
						Assert.fail("Failed to update the capping configuration");
					}
				}else{
					cappingConfig=helper.getNotificationCappingConfig();
				}
				log.info("Current capping config: "+cappingConfig);

				String response="";
				String nid="";
				JSONArray channels=new JSONArray();
				Map<String, JSONArray> idMapWithChannels = new HashMap<>();
				Map<String, Boolean> idValidMapping = new HashMap<>();
				if(helper.template || helper.urlHasPlaceholders) {
					if(!notificationPayload.has("templateData") && helper.urlHasPlaceholders){
						templateData=notificationPayload.getJSONObject("target").getJSONArray("channels");

					}
					channels=helper.populateChannels(templateData,idsWithTemplatePlaceholders,idMapWithChannels,idValidMapping,placeHolders);

				}else {
					channels = notificationPayload.getJSONObject("target").getJSONArray("channels");
				}

				String nType="";
				if(notificationPayload.has("notificationType")) {
					nType = notificationPayload.getString("notificationType");
				}
				if (!(notificationPayload.has("retainCapping") && ((boolean) notificationPayload.get("retainCapping")))) {
					log.info("Clearing the capping data from redis");
					helper.clearCappingCounterEntryFromRedis(appId, "android", idSet, idType, nType);
					helper.clearCappingCounterEntryFromRedis(appId, "ios", idSet, idType, nType);
					log.info("Clearing capping data from local map");
					helper.cappingData.clear();
				}
				log.info("Publishing the notification");
				response = helper.sendNotification(headers, payload, whom, testData);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {

				}
				String statusType = helper.readJsonPath(response, "$.statusMessage");
				if (statusType != null && statusType.equalsIgnoreCase("Send accepted")) {
					nid = helper.readJsonPath(response, "$.notificationId");
					log.info("nid: " + nid);
				} else {
					Assert.fail("Failed to publish the notification");
				}
				int templateCount=1;
				Iterator<String> idIterator=null;
				if(helper.urlHasPlaceholders){
					helper.saveResults=true;
					templateCount=idMapWithChannels.size();
					idIterator=idMapWithChannels.keySet().iterator();
				}
				int templateindex=0;
				while(templateCount>templateindex) {
					Map<String,JSONObject> idMapWithExtraFields=new HashMap<>();
					String id="";
					if(helper.urlHasPlaceholders){
						if(idIterator.hasNext()) {
							id= idIterator.next();
							channels=idMapWithChannels.get(id);

						}

					}
					templateindex++;

					for (int i = 0; i < channels.length(); i++) {
						JSONObject channel = channels.getJSONObject(i);
						JSONObject extraFields = new JSONObject();
						JSONArray platforms = new JSONArray();
						if (channel.has("payload")) {
							extraFields = channel.getJSONObject("payload");
						}
						if (channel.has("platforms")) {
							platforms = channel.getJSONArray("platforms");
						}

						if(helper.urlHasPlaceholders){
							idSet=new HashSet<>();
							idSet.add(id);
							idMapWithExtraFields.put(id,channel.getJSONObject("payload"));

						}


						//Populate the extra fields with template data and placeholder data.
						//helper.populateExtraFieldsFromTemplateDataAndPlaceholders(channel, extraFields, platforms, templateData, idsWithTemplatePlaceholders, idMapWithChannels, idValidMapping, placeHolders);


						String notificationName = channel.getString("name");
						switch (notificationName.toLowerCase()) {
							case "push": {
								log.info("Validating push notification");
								helper.validatePush(nid, appId, idSet, idType, extraFields, idMapWithExtraFields, cappingConfig, platforms, failures, testData, idValidMapping,nType);

							}
							break;
							case "inapp": {

								helper.validateInappAndBeacon(testData, notificationName, extraFields, nid, idSet, idMapWithExtraFields, idValidMapping, failures,nType);
								break;
							}
							case "beacon": {
								helper.validateInappAndBeacon(testData, notificationName, extraFields, nid, idSet, idMapWithExtraFields, idValidMapping, failures,nType);
								break;
							}
							default:
								failures.add("Invalid notification name :" + notificationName);
						}
					}
				}
				if(helper.urlHasPlaceholders){
					if (helper.androidSuccess!= helper.gcmHits) {
						failures.add("Not all the ids went through to GCM for android devices. Total GCM Hits: " + helper.gcmHits+",androidSuccess: "+helper.androidSuccess);
					}
					if (helper.iosSuccess != helper.apnsHits) {
						failures.add("Not all the ids went through to APNS for ios devices. Total APNS Hits: " + helper.apnsHits+",iosSuccess: "+helper.iosSuccess);
					}
					helper.androidSuccess=0;
					helper.iosSuccess=0;
					helper.gcmHits=0;
					helper.apnsHits=0;
					helper.saveResults=false;
				}
			}

			if (failures.size() > 0) {
				Assert.fail(helper.getFailuresPoints(failures));
			}else{
				Assert.assertEquals(true, true);
			}
		}catch(Exception e){
			log.error("Execution failed due to exception", e);
			Assert.fail("Execution failed due to exception"+e);
		}

	}

}
