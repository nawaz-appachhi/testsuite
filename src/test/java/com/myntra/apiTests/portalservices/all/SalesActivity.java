package com.myntra.apiTests.portalservices.all;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.myntra.apiTests.common.Myntra;
import org.apache.commons.lang.StringUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.SalesActivityDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class SalesActivity extends SalesActivityDP {

	static Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil = new APIUtilities();
	
	
	/*static String ApplicationId = "XIgnRH4YM66gtqAYyoVYmkVbdaP2w1S1Xbbw5fZx";
	static String RestAPIKey = "CHbox6eS5bFSiOl5s5iGoHqFcOND0hXASitpXmwp";*/

//	static String initial_value = "";

	@Test(groups = { "BeforeSales" }, dataProvider = "BeforeSales_updateMobileFeatureGateDP")
	public void BeforeSales_updateMobileFeatureGateValues(String Platform,
			String keyName, String keyValue, String environment) 
	{
		String ApplicationId = null;
		String RestAPIKey = null;
		String MasterKey = null;
		if (environment.toLowerCase().contains("production"))
		{
			ApplicationId = "KEzwVygKFjZJLmjh4S4zwYAw4CVgh5nbdWZTmj6G";
			 RestAPIKey = "380Tt0VcF1INumz6jZNuqq4cKOtDcSbZ76mrRoyp";
			 MasterKey = "klMty7MTsY1EuOYj5eXxPUryU7OU9F9qcR0YrE0V";
		}
		
		else if (environment.toLowerCase().contains("fox7"))
		{
			 ApplicationId = "XIgnRH4YM66gtqAYyoVYmkVbdaP2w1S1Xbbw5fZx";
			 RestAPIKey = "CHbox6eS5bFSiOl5s5iGoHqFcOND0hXASitpXmwp";
			 MasterKey = "esXRx1dOBs2uyuUdR6LTJDhLlExiNiR6URacQDXg";
		}
		
//		System.out.println("App id =" +ApplicationId);
//		System.out.println("rest = " +RestAPIKey);
		
		String initial_value = "true";
		String platform = Platform;
		String key_name = keyName;
		String key_value = keyValue;

		String ObjectId = getObjectIdforPlatform(platform,ApplicationId,RestAPIKey,MasterKey);

		String main_string = extractAndUpdateJson(ObjectId, key_name,initial_value, key_value,ApplicationId,RestAPIKey,MasterKey);

		String finalpayload = getPayloadAsString("updatevalueobject",
				main_string);
		System.out.println("payload = " + finalpayload);
		
		RequestGenerator req = updateValueObject(ObjectId,finalpayload,ApplicationId,RestAPIKey,MasterKey);

		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("Update value object API is not working", 200,
				req.response.getStatus());

	}
	
	@Test(groups = { "AfterSales" }, dataProvider = "AfterSales_updateMobileFeatureGateDP")
	public void AfterSales_updateMobileFeatureGateValues(String Platform,
			String keyName, String keyValue,String environment) 
	{
		
		String ApplicationId = null;
		String RestAPIKey = null;
		String MasterKey = null;
		if (environment.toLowerCase().contains("production"))
		{
			ApplicationId = "KEzwVygKFjZJLmjh4S4zwYAw4CVgh5nbdWZTmj6G";
			 RestAPIKey = "380Tt0VcF1INumz6jZNuqq4cKOtDcSbZ76mrRoyp";
			 MasterKey = "klMty7MTsY1EuOYj5eXxPUryU7OU9F9qcR0YrE0V";
		}
		
		else if (environment.toLowerCase().contains("fox7"))
		{
			 ApplicationId = "XIgnRH4YM66gtqAYyoVYmkVbdaP2w1S1Xbbw5fZx";
			 RestAPIKey = "CHbox6eS5bFSiOl5s5iGoHqFcOND0hXASitpXmwp";
			 MasterKey = "esXRx1dOBs2uyuUdR6LTJDhLlExiNiR6URacQDXg";
		}
		
	
		String initial_value = "false";
		String platform = Platform;
		String key_name = keyName;
		String key_value = keyValue;

		String ObjectId = getObjectIdforPlatform(platform,ApplicationId,RestAPIKey,MasterKey);

		String main_string = extractAndUpdateJson(ObjectId, key_name,initial_value, key_value,ApplicationId,RestAPIKey,MasterKey);

		String finalpayload = getPayloadAsString("updatevalueobject",
				main_string);
		System.out.println("payload = " + finalpayload);
		
		RequestGenerator req = updateValueObject(ObjectId,finalpayload,ApplicationId,RestAPIKey,MasterKey);

		System.out.println(req.respvalidate.returnresponseasstring());

		AssertJUnit.assertEquals("Update value object API is not working", 200,
				req.response.getStatus());

	}
	
	   // Test case for SalesOps - Update Feature Gate Values- Before Sales Starts
		@Test(groups = { "BeforeSales" }, dataProvider = "BeforeSales_UpdateFG_FromCsv")
		public void BeforeSales_updateFeatureGateValuesFromCSV(
				String key, String value) {

			String description = getDescriptionForFeatureGateKey(key);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}
		
		@Test(groups = { "BeforeSales" }, dataProvider = "BeforeSales_WidgetKeyValueFromCSV")
		public void BeforeSales_updateWidgetKeyValuesFromCSV(
				String key, String value) {
			
			String description = getDescriptionForWidgetKeyValue(key);
			
			System.out.println("Description for key - " + "key:" + description);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}
		
		// Test case for SalesOps - Update Widget Key Value Pair - Before Sales
		
		/*@Test(groups = { "BeforeSales" }, dataProvider = "BeforeSales_WidgetKeyValueFromJenkins")
		public void BeforeSales_updateWidgetKeyValuesFromJenkins(
				String key, String value) {
			
			String description = getDescriptionForWidgetKeyValue(key);
			System.out.println("Description for key - " + "key:" + description);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}
		*/
		
		
		
		// Test case for SalesOps - Update Feature Gate Values- After Sales Ends
		@Test(groups = { "AfterSales" }, dataProvider = "AfterSales_UpdateFGFromCSV")
		public void AfterSales_updateFeatureGateValuesFromCSV(
				String key, String value) {
			String description = getDescriptionForFeatureGateKey(key);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatefeaturekeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEFEATUREKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}
		
		@Test(groups = { "AfterSales" }, dataProvider = "AfterSales_UpdateWidgetKeyFromCSV")
		public void AfterSales_updateWidgetKeyValuesFromCSV(
				String key, String value) {
		
			String description = getDescriptionForWidgetKeyValue(key);
			System.out.println("Description for key - " + "key:" + description);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}

		
		// Test case for SalesOps - Update Widget Key Value Pair - After Sales
		// Starts
		/*@Test(groups = { "AfterSales" }, dataProvider = "AfterSales_WidgetKeyFromJenkins")
		public void AfterSales_updateWidgetKeyValuesFromJenkins(
				String key, String value) {
		
			String description = getDescriptionForWidgetKeyValue(key);
			System.out.println("Description for key - " + "key:" + description);
			String[] payloadparams = new String[] { key, value };
			String finalPayload = getPayloadAsString("updatewidgetkeyvaluepair",
					payloadparams, description);

			MyntraService service = Myntra.getService(
					ServiceType.PORTAL_CONFIGURATION,
					APINAME.UPDATEWIDGETKEYVALUEPAIR, init.Configurations,
					finalPayload);
			RequestGenerator req = new RequestGenerator(service);
			System.out.println(service.URL);

			String statusMessage = req.respvalidate
					.NodeValue("status.statusMessage", true).replaceAll("\"", "")
					.trim();
			System.out.println("statusMessage : " + statusMessage);

			AssertJUnit.assertTrue("Status Message doesn't match",
					statusMessage.contains("KeyValuePair updated successfully"));
		}
		*/
		



	private String extractAndUpdateJson(String matchingObjectId, String key,String initialValue,
			String finalValue,String ApplicationId,String RestAPIKey,String MasterKey) 
	{
	
		String ObjectId = matchingObjectId;
		String keyname = key;
		//String initial_value = "true";
		String final_value = finalValue;
		String initial_value = initialValue;

		RequestGenerator req = getValueOfObject(ObjectId,ApplicationId,RestAPIKey,MasterKey);

		String response = req.respvalidate.returnresponseasstring();
		System.out.println("initial response =" + response);

		String nodepath = "$.value." + keyname;
		System.out.println("node path =" + nodepath);
		
		String nodeValue_from_response = JsonPath.read(response, nodepath).toString();

		System.out.println("node value from response =" + nodeValue_from_response);

		String value1 = JsonPath.read(response, "$..value").toString();
		String value2 = value1.replaceAll("\\\\", "");
		String string_toModify = value2.substring(1, value2.length() - 1);

		//System.out.println("value = " + value1);
		//System.out.println("value2 = " + value2);
		System.out.println("String to modify = " + string_toModify);

		if (nodeValue_from_response.equals(initial_value)) {

			String value4 = "";
			String value5 = "";

			String[] valuearray = string_toModify.split(",");
			for (int i = 0; i < valuearray.length; i++) {
				if (valuearray[i].contains(keyname)) {
					value5 = valuearray[i].replace(initial_value, final_value)
							+ ",";
				}
				if (StringUtils.isEmpty(value5)) {
					value4 = value4 + valuearray[i] + ",";
				} else {
					value4 = value4 + value5;
					value5 = "";
				}
			}

			String modifiedString = value4.substring(0, value4.length() - 1);

			System.out.println("modified string :" + modifiedString);
			return modifiedString;
		} else {
			// do not update
			System.out.println("modified string: " + string_toModify);
			return string_toModify;
		}

	}

	private String getObjectIdforPlatform(String platform,String ApplicationId, String RestAPIKey, String MasterKey) 
	{
		
		String desired_platform = platform;
		String matchingObjectId = "";

		RequestGenerator req = getAllObjectIDs(ApplicationId,RestAPIKey,MasterKey);
		
		String jsonRes = req.respvalidate.returnresponseasstring();

		System.out.println(jsonRes);
		List<String> objectids = JsonPath.read(jsonRes,
				"$.results..objectId[*]");
		for (String s : objectids) {
			System.out.println("..........For object id...... " + s);
			
			RequestGenerator req1 = getValueOfObject(s,ApplicationId,RestAPIKey,MasterKey);

			String response = req1.respvalidate.returnresponseasstring();
			System.out.println("initial response =" + response);

			String actual_platform = JsonPath.read(response, "$.key")
					.toString();
			System.out.println("Actual platform =" + actual_platform);

			if (actual_platform.equals(desired_platform)) {
				System.out.println("Matching objectid =" + s);
				matchingObjectId = s;
				return matchingObjectId;
			}

		}
		return matchingObjectId;

	}
	
	private RequestGenerator getAllObjectIDs (String ApplicationId, String RestAPIKey, String MasterKey)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PARSE,
				APINAME.GETALLOBJECTID, init.Configurations);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		return new RequestGenerator(service, getParam);
	}
	
	private RequestGenerator getValueOfObject(String s, String ApplicationId, String RestAPIKey, String MasterKey)
	{
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PARSE, APINAME.GETVALUEOFOBJECT,
				init.Configurations, PayloadType.JSON, new String[] { s },
				PayloadType.JSON);
		System.out.println(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		return new RequestGenerator(service, getParam);
	}

	private RequestGenerator updateValueObject(String ObjectId, String finalpayload, String ApplicationId, String RestAPIKey, String MasterKey )
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PARSE,
				APINAME.UPDATEVALUEOBJECT, init.Configurations, finalpayload);

		APIUtilities apiUtil = new APIUtilities();
		service.URL = apiUtil.prepareparameterizedURL(service.URL, ObjectId);

		System.out.println("service url = " + service.URL);
		System.out.println("service payload = " + service.Payload);
		HashMap getParam = new HashMap();
		getParam.put("X-Parse-Application-Id",
				ApplicationId);
		getParam.put("X-Parse-REST-API-Key",
				RestAPIKey);
		getParam.put("X-Parse-Master-Key", MasterKey);
		
		return new RequestGenerator(service, getParam);
	}
	private String getPayloadAsString(String payloadName, String payloadparams) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();

		return finalPayload.replace("${0}", payloadparams);
	}
	
	private String getDescriptionForFeatureGateKey(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.GETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}
	
	private String getDescriptionForWidgetKeyValue(String key) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CONFIGURATION, APINAME.WIDGETGETKEYVALUE,
				init.Configurations, PayloadType.JSON, new String[] { key },
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);

		String description = req.respvalidate
				.NodeValue("data.description", true).replaceAll("\"", "")
				.trim();
		System.out.println("description for " + key + "is :" + description);

		return description;
	}
	
	private String getPayloadAsString(String payloadName,
			String[] payloadparams, String toReplace) {
		String customPayloadDir = System.getProperty("user.dir")
				+ File.separator + "Data" + File.separator + "payloads"
				+ File.separator + "JSON";
		StringBuffer sb = new StringBuffer();
		String finalPayload = "";
		Scanner sc;
		try {
			sc = new Scanner(new File(customPayloadDir + File.separator
					+ payloadName));
			while (sc.hasNextLine())
				sb.append(sc.nextLine() + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		finalPayload = sb.toString();
		for (int i = 0; i < payloadparams.length; i++) {
			finalPayload = finalPayload.replace("${" + i + "}",
					payloadparams[i]);
		}
		finalPayload = finalPayload.replace("${2}", toReplace);
		// System.out.println(finalPayload);
		return finalPayload;
	}
	

}
