package com.myntra.apiTests.dataproviders;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationDP {
	//INFO: Android Convention: deviceID:qa_androidXXX userID: qa_android.XXX
	//INFO: IOS Convention: deviceID: qa_iosXXX userID: qa_iosXXX
	
	@DataProvider
	public Object[][] notificationsDp() throws JSONException{
		//BASE Json template
		JSONObject notificationsPayloadTemplate = new JSONObject();
		JSONObject target  = new JSONObject();
		notificationsPayloadTemplate.put("whom", "11:notification_marketing");
		notificationsPayloadTemplate.put("whomType", "Object");
		
		
		
		ArrayList<JSONObject> channels  = new ArrayList<>();
		JSONObject channel1  = new JSONObject();
		channel1.put("name", "Inapp");
		JSONObject extraFields  = new JSONObject();
		extraFields.put("heading", "Myntra");
		extraFields.put("title", "Sale is on");
		extraFields.put("description", "Get your games on!");
		extraFields.put("notificationType", "Engagement");
		extraFields.put("pageUrl", "http://www.myntra.com");
		channel1.put("extraFields", extraFields);
		
		ArrayList<JSONObject> platforms  = new ArrayList<>();
		JSONObject platform1 = new JSONObject();
		platform1.put("name", "android");
		platforms.add(platform1);
		ArrayList<JSONObject> platformsList = new ArrayList<JSONObject>();
		platformsList.add(new JSONObject().put("name", "android"));
		channel1.put("platforms", platformsList);
		channels.add( channel1);
		target.put("channels", channels);
		notificationsPayloadTemplate.put("target", target);
		//BASE Json template
		
		//BASE TemplateData
		JSONObject templateDataTemplaate = new JSONObject();
		templateDataTemplaate.put("tgId", "notification_marketing");
		templateDataTemplaate.put("appId", 11);
		templateDataTemplaate.append("tags", "push");
		templateDataTemplaate.put("version", 1);
		templateDataTemplaate.put("placeholders", new JSONObject().put("message", "coco").put("description", "popo").put("pageUrl", "http://www.myntra.com"));
		
		//BASE TemplateData
		
		//Case1
		Map<String, Object> testData1 = new HashMap<>();
		JSONObject notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");
		
		testData1.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData1.put("Case", "Case1: Device id list inapp");
		
		//Case2
		Map<String, Object> testData2 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		testData2.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData2.put("Case", "Case2: User id list beacon");

		//Case3
		Map<String, Object> testData3 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		testData3.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData3.put("Case", "Case3: Device id url inapp");
		
		//Case4
		Map<String, Object> testData4 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		testData4.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData4.put("Case", "Case4: Device id url beacon");

		//Case5
		Map<String, Object> testData5 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("title", "{$name$}");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("flags", new JSONObject().put("template", true));
		testData5.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData5.put("Case", "Case5: Device id url with placeholders inapp");
		
		//Case6
		Map<String, Object> testData6 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("title", "{$name$}");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("flags", new JSONObject().put("template", true));
		testData6.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData6.put("Case", "Case6: Device id url with placeholders beacon");

		//Case7
		Map<String, Object> testData7 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template5.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		JSONObject templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData7.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData7.put("Case", "Case7: Device id url  with template service inapp");
		
		//Case8
		Map<String, Object> testData8 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template5.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData8.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData8.put("Case", "Case8: Device id url beacon with template service beacon");
		
		
		//Case9
		Map<String, Object> testData9 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		testData9.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData9.put("Case", "Case9: Device id list push");
		
		//Case10
		Map<String, Object> testData10 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		testData10.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData10.put("Case", "Case10: User id list push");
		
		//Case11
		Map<String, Object> testData11 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		testData11.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData11.put("Case", "Case11: Device id url push");
		
		//Case12
		Map<String, Object> testData12 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("title", "{$name$}");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("extraFields").put("flags", new JSONObject().put("template", true));
		testData12.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData12.put("Case", "Case12: Device id url with placeholders push");
		
		//Case13
		Map<String, Object> testData13 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template5.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData13.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData13.put("Case", "Case13: Device id url with placeholder with template service push");
		
		//Case14
		Map<String, Object> testData14 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		testData14.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData14.put("Case", "Case14: Broadcast push");

		//Case15
		Map<String, Object> testData15 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		testData15.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData15.put("Case", "Case15: Broadcast inapp");

		//Case16
		Map<String, Object> testData16 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
		testData16.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData16.put("Case", "Case16: Broadcast beacon");

		//Case17
		Map<String, Object> testData17 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData17.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData17.put("Case", "Case17: Device id list with template service push");

		//Case18
		Map<String, Object> testData18 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData18.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData18.put("Case", "Case18: Device id list with template service inapp");

		//Case19
		Map<String, Object> testData19 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).remove("extraFields");
		templateData=(JSONObject)copyObject(templateDataTemplaate);
		notificationsPayload.put("templateData",templateData);
		testData19.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData19.put("Case", "Case19: Device id list with template service beacon");

		//Case20
		Map<String, Object> testData20 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		testData20.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData20.put("Case", "Case20: User id list inapp");

		//Case21
		Map<String, Object> testData21 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		testData21.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData21.put("Case", "Case21: Device id list beacon");

		//Case22
		Map<String, Object> testData22 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_auto.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
		testData22.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData22.put("Case", "Case22: User id url inapp");

		//Case23
		Map<String, Object> testData23 = new HashMap<>();
		notificationsPayload = (JSONObject)copyObject(notificationsPayloadTemplate);
		notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_auto.csv");
		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
		testData23.put("notificationPayload", new JSONObject[]{ notificationsPayload});
		testData23.put("Case", "Case23: User id url beacon");

		
		
		return new Object[][]{{testData1},{testData2},{testData3},{testData4},{testData5},{testData6},{testData7},{testData8},{testData9},{testData10},{testData11},{testData12},{testData13},
				/*{testData14},*/{testData15},{testData16},{testData17},{testData18},{testData19},{testData20},{testData21},{testData22},{testData23}};
	}


	@DataProvider
	public Object[][] notificationSendDp() throws JSONException{
		try {
			//BASE Json template
			JSONObject notificationsPayloadTemplate = new JSONObject();
			JSONObject target = new JSONObject();
			notificationsPayloadTemplate.put("whom", "11:notification_marketing");
			notificationsPayloadTemplate.put("whomType", "Object");
			notificationsPayloadTemplate.put("notificationType", "Engagement");


			ArrayList<JSONObject> channels = new ArrayList<>();
			JSONObject channel1 = new JSONObject();
			channel1.put("name", "Inapp");
			JSONObject extraFields = new JSONObject();
			extraFields.put("heading", "Myntra");
			extraFields.put("title", "Sale is on");
			extraFields.put("description", "Get your games on!");

			extraFields.put("pageUrl", "http://www.myntra.com");
			channel1.put("payload", extraFields);
			ArrayList<JSONObject> platforms = new ArrayList<>();
			JSONObject platform1 = new JSONObject();
			platform1.put("name", "android");
			platforms.add(platform1);
			ArrayList<JSONObject> platformsList = new ArrayList<JSONObject>();
			platformsList.add(new JSONObject().put("name", "android"));
			channel1.put("platforms", platformsList);
			channels.add(channel1);
			target.put("channels", channels);
			notificationsPayloadTemplate.put("target", target);
			//BASE Json template

			//BASE TemplateData
			JSONObject templateDataTemplaate_push = new JSONObject();
			templateDataTemplaate_push.put("tgId", "notification_marketing");
			templateDataTemplaate_push.put("appId", 11);
			templateDataTemplaate_push.append("tags", "push");
			templateDataTemplaate_push.put("version", 14);
			templateDataTemplaate_push.put("placeholderMap", new JSONObject().put("title", "auto test title").put("text", "Auto test description"));


			JSONObject templateDataTemplaate_inapp = new JSONObject();
			templateDataTemplaate_inapp.put("tgId", "notification_marketing");
			templateDataTemplaate_inapp.put("appId", 11);
			templateDataTemplaate_inapp.append("tags", "push");
			templateDataTemplaate_inapp.put("version", 15);
			templateDataTemplaate_inapp.put("placeholderMap", new JSONObject().put("title", "auto test title").put("text", "Auto test description"));


			JSONObject templateDataTemplaate_beacon = new JSONObject();
			templateDataTemplaate_beacon.put("tgId", "notification_marketing");
			templateDataTemplaate_beacon.put("appId", 11);
			templateDataTemplaate_beacon.append("tags", "push");
			templateDataTemplaate_beacon.put("version", 16);
			templateDataTemplaate_beacon.put("placeholderMap", new JSONObject().put("title", "auto test title").put("text", "Auto test description"));


			//BASE TemplateData

			//Case1
			Map<String, Object> testData1 = new HashMap<>();
			JSONObject notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");

			testData1.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData1.put("Case", "Case1: Device id list inapp");

			//Case2
			Map<String, Object> testData2 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			testData2.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData2.put("Case", "Case2: User id list beacon");

			//Case3
			Map<String, Object> testData3 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			testData3.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData3.put("Case", "Case3: Device id url inapp");

			//Case4
			Map<String, Object> testData4 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			testData4.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData4.put("Case", "Case4: Device id url beacon");

			//Case5
			Map<String, Object> testData5 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData5.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData5.put("Case", "Case5: Device id url with placeholders inapp");

			//Case6
			Map<String, Object> testData6 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData6.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData6.put("Case", "Case6: Device id url with placeholders beacon");

			//Case7
			Map<String, Object> testData7 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_sendauto.csv");
			//notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			JSONObject templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData7.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData7.put("Case", "Case7: Device id url  with template service inapp");

			//Case8
			Map<String, Object> testData8 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_sendauto.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData8.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData8.put("Case", "Case8: Device id url beacon with template service beacon");


			//Case9
			Map<String, Object> testData9 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData9.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData9.put("Case", "Case9: Device id list push");

			//Case10
			Map<String, Object> testData10 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData10.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData10.put("Case", "Case10: User id list push");

			//Case11
			Map<String, Object> testData11 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template3.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData11.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData11.put("Case", "Case11: Device id url push");

			//Case12
			Map<String, Object> testData12 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template4.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData12.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData12.put("Case", "Case12: Device id url with placeholders push");

			//Case13
			Map<String, Object> testData13 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_sendauto.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData13.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData13.put("Case", "Case13: Device id url with placeholder with template service push");

			//Case14
			Map<String, Object> testData14 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData14.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData14.put("Case", "Case14: Broadcast push");

			//Case15
			Map<String, Object> testData15 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			testData15.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData15.put("Case", "Case15: Broadcast inapp");

			//Case16
			Map<String, Object> testData16 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			testData16.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData16.put("Case", "Case16: Broadcast beacon");

			//Case17
			Map<String, Object> testData17 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData17.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData17.put("Case", "Case17: Device id list with template service push");

			//Case18
			Map<String, Object> testData18 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData18.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData18.put("Case", "Case18: Device id list with template service inapp");

			//Case19
			Map<String, Object> testData19 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData19.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData19.put("Case", "Case19: Device id list with template service beacon");

			//Case20
			Map<String, Object> testData20 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			testData20.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData20.put("Case", "Case20: User id list inapp");

			//Case21
			Map<String, Object> testData21 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			testData21.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData21.put("Case", "Case21: Device id list beacon");

			//Case22
			Map<String, Object> testData22 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_auto.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			testData22.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData22.put("Case", "Case22: User id url inapp");

			//Case23
			Map<String, Object> testData23 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_auto.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			testData23.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData23.put("Case", "Case23: User id url beacon");

			//Case24
			Map<String, Object> testData24 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData24.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData24.put("Case", "Case24: User id url with placeholders inapp");

			//Case25
			Map<String, Object> testData25 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData25.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData25.put("Case", "Case25: User id url with placeholders beacon");

			//Case26
			Map<String, Object> testData26 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_sendauto.csv");
			//notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData26.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData26.put("Case", "Case26: User id url  with template service inapp");

			//Case27
			Map<String, Object> testData27 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_sendauto.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData27.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData27.put("Case", "Case27: User id url beacon with template service beacon");

			//Case28
			Map<String, Object> testData28 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData28.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData28.put("Case", "Case28: User id list push");

			//Case29
			Map<String, Object> testData29 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			testData29.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData29.put("Case", "Case29: User id url push");

			//Case30
			Map<String, Object> testData30 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			testData30.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData30.put("Case", "Case30: User id url with placeholders push");

			//Case31
			Map<String, Object> testData31 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_sendauto.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData31.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData31.put("Case", "Case31: User id url with template service push");

			//Case32
			Map<String, Object> testData32 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData32.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData32.put("Case", "Case32: User id list with template service push");

			//Case33
			Map<String, Object> testData33 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData33.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData33.put("Case", "Case33: User id list with template service inapp");

			//Case34
			Map<String, Object> testData34 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData34.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData34.put("Case", "Case34: User id list with template service beacon");


			//Case35
			Map<String, Object> testData35 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5,qa_android6,qa_android7,qa_android8,qa_android9,qa_android10,qa_android11,qa_android12,qa_android13,qa_android14,qa_android15,qa_android16,qa_android17,qa_android18,qa_android19,qa_android20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData35.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData35.put("Case", "Case35: Device id list with min version, max and exclude version inapp");

			//Case36
			Map<String, Object> testData36 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5,qa_android6,qa_android7,qa_android8,qa_android9,qa_android10,qa_android11,qa_android12,qa_android13,qa_android14,qa_android15,qa_android16,qa_android17,qa_android18,qa_android19,qa_android20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData36.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData36.put("Case", "Case36: Device id list with min version, max and exclude version beacon");


			//Case37
			Map<String, Object> testData37 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_android1,qa_android2,qa_android3,qa_android4,qa_android5,qa_android6,qa_android7,qa_android8,qa_android9,qa_android10,qa_android11,qa_android12,qa_android13,qa_android14,qa_android15,qa_android16,qa_android17,qa_android18,qa_android19,qa_android20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData37.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData37.put("Case", "Case37: Device id list with min version, max and exclude version push");

			//Case38
			Map<String, Object> testData38 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5,qa_android.6,qa_android.7,qa_android.8,qa_android.9,qa_android.10,qa_android.11,qa_android.12,qa_android.13,qa_android.14,qa_android.15,qa_android.16,qa_android.17,qa_android.18,qa_android.19,qa_android.20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData38.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData38.put("Case", "Case38: User id list with min version, max and exclude version inapp");

			//Case39
			Map<String, Object> testData39 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5,qa_android.6,qa_android.7,qa_android.8,qa_android.9,qa_android.10,qa_android.11,qa_android.12,qa_android.13,qa_android.14,qa_android.15,qa_android.16,qa_android.17,qa_android.18,qa_android.19,qa_android.20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData39.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData39.put("Case", "Case39: User id list with min version, max and exclude version beacon");

			//Case40
			Map<String, Object> testData40 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_android.1,qa_android.2,qa_android.3,qa_android.4,qa_android.5,qa_android.6,qa_android.7,qa_android.8,qa_android.9,qa_android.10,qa_android.11,qa_android.12,qa_android.13,qa_android.14,qa_android.15,qa_android.16,qa_android.17,qa_android.18,qa_android.19,qa_android.20");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData40.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData40.put("Case", "Case40: User id list with min version, max and exclude version push");

			//Case41
			Map<String, Object> testData41 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData41.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData41.put("Case", "Case41: Device id url with min version, max and exclude version inapp");

			//Case42
			Map<String, Object> testData42 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData42.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData42.put("Case", "Case42: Device id url with min version, max and exclude version beacon");

			//Case43
			Map<String, Object> testData43 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_deviceid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData43.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData43.put("Case", "Case43: Device id url with min version, max and exclude version push");

			//Case44
			Map<String, Object> testData44 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData44.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData44.put("Case", "Case44: User id url with min version, max and exclude version inapp");

			//Case45
			Map<String, Object> testData45 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData45.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData45.put("Case", "Case45: User id url with min version, max and exclude version beacon");

			//Case46
			Map<String, Object> testData46 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_long.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("minVersion","2.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("maxVersion","7.5.0");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("excludeVersions","5.5.0,5.4.0,5.3.0,5.2.0,5.1.0,5.0.0");
			testData46.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData46.put("Case", "Case46: User id url with min version, max and exclude version push");

			//Case47
			Map<String, Object> testData47 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1,qa_ios2,qa_ios3,qa_ios4,qa_ios5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData47.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData47.put("Case", "Case47: Device id ios list inapp");

			//Case48
			Map<String, Object> testData48 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_ios.1,qa_ios.2,qa_ios.3,qa_ios.4,qa_ios.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData48.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData48.put("Case", "Case48: User id ios list beacon");

			//Case49
			Map<String, Object> testData49 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosDeviceID.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData49.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData49.put("Case", "Case49: Device id ios url inapp");

			//Case50
			Map<String, Object> testData50 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosDeviceID.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData50.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData50.put("Case", "Case50: Device id ios url beacon");

			//Case5
			Map<String, Object> testData51 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosPlaceholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData51.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData51.put("Case", "Case51: Device id url ios with placeholders inapp");

			//Case52
			Map<String, Object> testData52 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosPlaceholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData52.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData52.put("Case", "Case52: Device id url ios with placeholders beacon");

			//Case53
			Map<String, Object> testData53 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_templateService_deviceid_ios.csv");
			//notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData53.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData53.put("Case", "Case53: Device id url ios  with template service inapp");

			//Case54
			Map<String, Object> testData54 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_templateService_deviceid_ios.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData54.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData54.put("Case", "Case54: Device id url ios with template service beacon");


			//Case55
			Map<String, Object> testData55 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1,qa_ios2,qa_ios3,qa_ios4,qa_ios5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData55.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData55.put("Case", "Case55: Device id ios list push");

			//Case56
			Map<String, Object> testData56 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_ios.1,qa_ios.2,qa_ios.3,qa_ios.4,qa_ios.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData56.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData56.put("Case", "Case56: User id ios list push");

			//Case57
			Map<String, Object> testData57 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosDeviceID.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData57.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData57.put("Case", "Case57: Device id ios url push");

			//Case58
			Map<String, Object> testData58 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_iosPlaceholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData58.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData58.put("Case", "Case58: Device id ios url with placeholders push");

			//Case59
			Map<String, Object> testData59 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_templateService_deviceid_ios.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData59.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData59.put("Case", "Case60: Device id ios url with placeholder with template service push");

			//Case60
			Map<String, Object> testData60 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData60.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData60.put("Case", "Case60: Broadcast ios push");

			//Case61
			Map<String, Object> testData61 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData61.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData61.put("Case", "Case61: Broadcast ios inapp");

			//Case62
			Map<String, Object> testData62 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "EVERYONE");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData62.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData62.put("Case", "Case62: Broadcast ios beacon");

			//Case63
			Map<String, Object> testData63 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_push);
			notificationsPayload.put("templateData", templateData);
			testData63.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData63.put("Case", "Case63: Device id ios list with template service push");

			//Case64
			Map<String, Object> testData64 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData64.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData64.put("Case", "Case64: Device id ios list with template service inapp");

			//Case65
			Map<String, Object> testData65 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData65.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData65.put("Case", "Case65: Device id ios list with template service beacon");

			//Case66
			Map<String, Object> testData66 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_ios.1,qa_ios.2,qa_ios.3,qa_ios.4,qa_ios.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData66.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData66.put("Case", "Case66: User id ios list inapp");

			//Case67
			Map<String, Object> testData67 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN LIST qa_ios1,qa_ios2,qa_ios3,qa_ios4,qa_ios5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData67.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData67.put("Case", "Case67: Device id ios list beacon");

			//Case68
			Map<String, Object> testData68 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_ios.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData68.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData68.put("Case", "Case68: User id ios url inapp");

			//Case69
			Map<String, Object> testData69 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_ios.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData69.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData69.put("Case", "Case69: User id ios url beacon");

			//Case70
			Map<String, Object> testData70 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_ios_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData70.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData70.put("Case", "Case70: User id ios url with placeholders inapp");

			//Case71
			Map<String, Object> testData71 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_ios_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "beacon");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData71.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData71.put("Case", "Case71: User id ios url with placeholders beacon");

			//Case72
			Map<String, Object> testData72 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_templateService_userid_ios.csv");
			//notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "inapp");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_inapp);
			notificationsPayload.put("templateData", templateData);
			testData72.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData72.put("Case", "Case72: User id ios url  with template service inapp");

			//Case73
			Map<String, Object> testData73 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_templateService_userid_ios.csv");
//		notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Beacon");
			notificationsPayload.getJSONObject("target").remove("channels");
			templateData = (JSONObject) copyObject(templateDataTemplaate_beacon);
			notificationsPayload.put("templateData", templateData);
			testData73.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData73.put("Case", "Case73: User id ios url beacon with template service beacon");

			//Case74
			Map<String, Object> testData74 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN LIST qa_ios.1,qa_ios.2,qa_ios.3,qa_ios.4,qa_ios.5");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData28.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData28.put("Case", "Case28: User id ios list push");

			//Case75
			Map<String, Object> testData75 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "USER_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_userid_ios.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData75.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData75.put("Case", "Case75: User id ios url push");

			//Case76
			Map<String, Object> testData76 = new HashMap<>();
			notificationsPayload = (JSONObject) copyObject(notificationsPayloadTemplate);
			notificationsPayload.getJSONObject("target").put("includeExpression", "DEVICE_ID IN URL http://qa_lg1:8080/myntra/spe/files/template_ios_userid_placeholder.csv");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).put("name", "Push");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("title", "{$name$}");
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONObject("payload").put("flags", new JSONObject().put("template", true));
			notificationsPayload.getJSONObject("target").getJSONArray("channels").getJSONObject(0).getJSONArray("platforms").getJSONObject(0).put("name","ios");
			testData76.put("notificationPayload", new JSONObject[]{notificationsPayload});
			testData76.put("Case", "Case76: User id ios url with placeholders push");


			return new Object[][]{{testData1}, {testData2}, {testData3}, {testData4}, {testData5}, {testData6},{testData7}, {testData8}, {testData9}, {testData10}, {testData11}, {testData12}, {testData13},
				/*{testData14},*/{testData15}, {testData16}, {testData17}, {testData18}, {testData19}, {testData20}, {testData21}, {testData22}, {testData23},
			{testData24},{testData25}, {testData26}, {testData27}, {testData28}, {testData29}, {testData20}, {testData31}, {testData32}, {testData33}, {testData34},
					{testData35},{testData36},{testData37},{testData38},{testData39},{testData40},{testData41},{testData42},{testData43},{testData44},{testData45},
					{testData46},{testData47},{testData48},{testData49},{testData49},{testData50},{testData51},{testData52},{testData53},{testData54},{testData55},
					{testData56},{testData57},{testData58},{testData59},{testData60},{testData61},{testData62},{testData63},{testData64},{testData65},{testData66},
					{testData67},{testData68},{testData69},{testData70},{testData71},{testData72},{testData73}};
//			return new Object[][]{{testData13},
//				/*{testData14},*/{testData15}, {testData16}, {testData17}, {testData18}, {testData19}, {testData20}, {testData21}, {testData22}, {testData23}};
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	private Object copyObject(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }
}

