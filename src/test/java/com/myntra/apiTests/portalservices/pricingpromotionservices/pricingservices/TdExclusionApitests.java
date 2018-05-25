package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.PricingDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;


public class TdExclusionApitests extends PricingDP {
	
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = LoggerFactory.getLogger(TdExclusionApitests.class);
	public long currentTime = System.currentTimeMillis();
//	String startTime = "" , endTime = "";
	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	
	
	
//	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//			"Regression" })
//	public void getInternalPricingPolicyNew() {
//		MyntraService myntraService = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETSTYLEPAGE, init.Configurations);
//
//		System.out.println("COMplete  URL ____>>>>>>>>>>"  + myntraService.URL);
//		RequestGenerator requestGenerator = new RequestGenerator(myntraService);
//		
//		AssertJUnit.assertEquals("Status not equal to 200", 200,
//				requestGenerator.response.getStatus());
//	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" },dataProvider = "getstyleithData" )
	public void createStyleTdExclusion(String page , String data ,String merchandiseType,String pageIndex,String pageSize ) {
		
		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
		
		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		String[] payloadparams = new String[] { stylids.get(0).toString(), stylids.get(1).toString(),stylids.get(2).toString()};
		  
		  String finalPayload = getPayloadAsString("createstyletdexclusion",
					payloadparams,startTime,endTime,name);
//		System.out.println("content 2" + stylids.get(1).toString());
//		System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee"  + jsonRes );
		
		
		
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.CREATESTYLETDEXCLUSION,
				init.Configurations,finalPayload);
		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());

		
		

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations,PayloadType.JSON,new String [] {pageIndex,pageSize},PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.Payload);
		System.out.println("Response 2nd api ---? " + req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		
		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
	     HashMap<String, String> hmap = new HashMap<String, String>();
String var1=hmap.put(username.get(0), id.get(0));
//	     hmap.put(username.get(1), id.get(1));
//	     hmap.put(username.get(2), id.get(2));
//	     hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1="";
		for(int i=0;i<username.size();i++){
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound= "";
		while (iterator.hasNext()) {
			
			Map.Entry mentry = (Map.Entry) iterator.next();
			if( mentry.getKey().equals(var2))
			{
				System.out.println("found");
				 id1= mentry.getValue();
				 System.out.println("Id forund is " +id1 );
			}
			
			 idfound = String.valueOf(id1);
				

		}

		String message = updateTdExclusion(idfound,"Active");
		String user = getTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully", message);

		

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void RejectStyleTdExclusionAftercreate(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		String[] payloadparams = new String[] { stylids.get(0).toString(),
				stylids.get(1).toString(), stylids.get(2).toString() };

		String finalPayload = getPayloadAsString("createstyletdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATESTYLETDEXCLUSION, init.Configurations,
				finalPayload);
		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Rejected");
		String user = getRejectedTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
//		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully", message);


		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createBrandTdExclusion(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> stylids = JsonPath.read(jsonRes,
				"$.styleList..brand[*]");
		String[] payloadparams = new String[] { stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString() };

		String finalPayload = getPayloadAsString("createbrandtdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDTDEXCLUSION, init.Configurations,
				finalPayload);
		System.out.println("URL---- "+myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Active");
		String user = getTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		 deleteTdExlusion(idfound);
			AssertJUnit.assertEquals("TDExclusion status saved successfully", message);


		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectBrandTdExclusionAfterCreate(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> stylids = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		String[] payloadparams = new String[] { stylids.get(0).toString(),
				stylids.get(1).toString(), stylids.get(2).toString() };

		String finalPayload = getPayloadAsString("createbrandtdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDTDEXCLUSION, init.Configurations,
				finalPayload);
		System.out.println("URL---- " + myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Rejected");
		String user = getRejectedTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
//		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createMrpTdExclusion(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..maximumRetailPrice[*]");

		Object obj = Collections.max(stylids);
		Object obj1 = Collections.min(stylids);

		String max = String.valueOf(obj);
		String min = String.valueOf(obj1);
		System.out.println("Maxium value form array --->>> " + max);
		System.out.println("Minumum value form array --->>> " + min);

		String[] payloadparams = new String[] { max, min };

		String finalPayload = getPayloadAsTwoString("createmrptdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEMRPTDEXCLUSION, init.Configurations, finalPayload);
		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Active");
		String user = getTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		// deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectMrpTdExclusionAfterCreate(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..maximumRetailPrice[*]");

		Object obj = Collections.max(stylids);
		Object obj1 = Collections.min(stylids);

		String max = String.valueOf(obj);
		String min = String.valueOf(obj1);
		System.out.println("Maxium value form array --->>> " + max);
		System.out.println("Minumum value form array --->>> " + min);

		String[] payloadparams = new String[] { max, min };

		String finalPayload = getPayloadAsTwoString("createmrptdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEMRPTDEXCLUSION, init.Configurations, finalPayload);
		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Rejected");
		String user = getRejectedTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		// deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	
	

	

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createBrandArticleTdExclusion(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> articleType = JsonPath.read(jsonRes,
				"$.styleList..articleType[*]");
		List<String> brand = JsonPath.read(jsonRes,
				"$.styleList..brand[*]");
		String[] payloadparams = new String[] { articleType.get(0).toString(),articleType.get(1).toString(),articleType.get(2).toString(),brand.get(0).toString(),brand.get(1).toString(),brand.get(2).toString() };

		String finalPayload = getPayloadAsManyString("createbrandarticletdexclusion",
				payloadparams, startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDARTICLETDEXCLUSION, init.Configurations,
				finalPayload);
		System.out.println("URL---- "+myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Active");
		String user = getTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully", message);


		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectBrandArticleTdExclusionAfterCreate(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> articleType = JsonPath.read(jsonRes,
				"$.styleList..articleType[*]");
		List<String> brand = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		String[] payloadparams = new String[] { articleType.get(0).toString(),
				articleType.get(1).toString(), articleType.get(2).toString(),
				brand.get(0).toString(), brand.get(1).toString(),
				brand.get(2).toString() };

		String finalPayload = getPayloadAsManyString(
				"createbrandarticletdexclusion", payloadparams, startTime,
				endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDARTICLETDEXCLUSION, init.Configurations,
				finalPayload);
		System.out.println("URL---- " + myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Rejected");
		String user = getRejectedTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createBrandArticleSeasonTdExclusion(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> articleType = JsonPath.read(jsonRes,
				"$.styleList..articleType[*]");
		List<String> brand = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		List<String> season = JsonPath.read(jsonRes, "$.styleList..season[*]");

		String[] payloadparams = new String[] { articleType.get(0).toString(),
				articleType.get(1).toString(), articleType.get(2).toString(),
				brand.get(0).toString(), brand.get(1).toString(),
				brand.get(2).toString(),season.get(0).toString(), season.get(1).toString(),
				season.get(2).toString() };

		String finalPayload = getPayloadAsString1(
				"createbrandarticleseasontdexclusion", payloadparams, startTime,
				endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDARTICLESEASONTDEXCLUSION, init.Configurations,
				finalPayload);
		System.out.println("URL---- " + myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Active");
		String user = getTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		 deleteTdExlusion(idfound);
	   AssertJUnit.assertEquals("TDExclusion status saved successfully", message);


		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectBrandArticleSeasonTdExclusionAfterCreate(String page, String data,
			String merchandiseType, String pageIndex, String pageSize) {

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " + service.URL);
		System.out.println("---------------->>>>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<String> articleType = JsonPath.read(jsonRes,
				"$.styleList..articleType[*]");
		List<String> brand = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		List<String> season = JsonPath.read(jsonRes, "$.styleList..season[*]");

		String[] payloadparams = new String[] { articleType.get(0).toString(),
				articleType.get(1).toString(), articleType.get(2).toString(),
				brand.get(0).toString(), brand.get(1).toString(),
				brand.get(2).toString(), season.get(0).toString(),
				season.get(1).toString(), season.get(2).toString() };

		String finalPayload = getPayloadAsString1(
				"createbrandarticleseasontdexclusion", payloadparams,
				startTime, endTime, name);
		// System.out.println("content 2" + stylids.get(1).toString());
		// System.out.println("content 3" + stylids.get(2).toString());

		System.out.println("resoncsee" + jsonRes);

		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATEBRANDARTICLESEASONTDEXCLUSION,
				init.Configurations, finalPayload);
		System.out.println("URL---- " + myntraService.URL);
		System.out.println("Payload kumar ----  >>?? " + myntraService.Payload);

		RequestGenerator req = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Response kumar---? "
				+ req.respvalidate.returnresponseasstring());

		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSIONS,
				init.Configurations, PayloadType.JSON, new String[] {
						pageIndex, pageSize }, PayloadType.JSON);
		System.out.println("Payload  2nd api----  >>?? " + myntraService1.URL);

		RequestGenerator req1 = new RequestGenerator(myntraService1);
		String jsonRes1 = req1.respvalidate.returnresponseasstring();
		System.out.println("2nd api url " + myntraService1.URL);
		System.out.println("Payload  2nd api----  >>?? "
				+ myntraService1.Payload);
		System.out.println("Response 2nd api ---? "
				+ req1.respvalidate.returnresponseasstring());
		List<String> username = JsonPath.read(jsonRes1,
				"$.tdExclusionDTOs..criterias.name[*]");
		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");

		System.out.println("content 1" + username);
		System.out.println("content 2" + id);
		Object var2 = name;
		HashMap<String, String> hmap = new HashMap<String, String>();
		String var1 = hmap.put(username.get(0), id.get(0));
		// hmap.put(username.get(1), id.get(1));
		// hmap.put(username.get(2), id.get(2));
		// hmap.put(username.get(3), id.get(3));
		Set set = hmap.entrySet();
		Object id1 = "";
		for (int i = 0; i < username.size(); i++) {
			hmap.put(username.get(i), id.get(i));

		}
		System.out.println("Hmap print" + hmap);
		Iterator iterator = set.iterator();
		String idfound = "";
		while (iterator.hasNext()) {

			Map.Entry mentry = (Map.Entry) iterator.next();
			if (mentry.getKey().equals(var2)) {
				System.out.println("found");
				id1 = mentry.getValue();
				System.out.println("Id forund is " + id1);
			}

			idfound = String.valueOf(id1);

		}

		String message = updateTdExclusion(idfound, "Rejected");
		String user = getRejectedTdExlusion(idfound);
		AssertJUnit.assertEquals(name, user);
		deleteTdExlusion(idfound);
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	
	private String getPayloadAsString(String payloadName,
			String[] payloadparams,String startTime ,String endTime,String name) {
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
		// System.out.println(finalPayload);
		finalPayload = finalPayload.replace("${3}", startTime);
		finalPayload = finalPayload.replace("${4}", endTime);
		finalPayload = finalPayload.replace("${5}", name);

		

		

		return finalPayload;
	}
	
	
	private String getPayloadAsTwoString(String payloadName,
			String[] payloadparams,String startTime ,String endTime,String name) {
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
		// System.out.println(finalPayload);
		finalPayload = finalPayload.replace("${2}", startTime);
		finalPayload = finalPayload.replace("${3}", endTime);
		finalPayload = finalPayload.replace("${4}", name);

		

		

		return finalPayload;
	}
	
	
	
	
	private String getPayloadAsManyString(String payloadName,
			String[] payloadparams,String startTime ,String endTime,String name) {
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
		// System.out.println(finalPayload);
		finalPayload = finalPayload.replace("${6}", startTime);
		finalPayload = finalPayload.replace("${7}", endTime);
		finalPayload = finalPayload.replace("${8}", name);

		

		

		return finalPayload;
	}
	
	private String getPayloadAsString1(String payloadName,
			String[] payloadparams,String startTime ,String endTime,String name) {
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
		// System.out.println(finalPayload);
		finalPayload = finalPayload.replace("${9}", startTime);
		finalPayload = finalPayload.replace("${10}", endTime);
		finalPayload = finalPayload.replace("${11}", name);

		

		

		return finalPayload;
	}
	
	
	private String updateTdExclusion(String id , String status)
	{
		
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.UPDATETDEXCLUSION, init.Configurations,
				new String[] {id,status}, PayloadType.JSON, PayloadType.JSON);
		System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.message");

		
		return message;
	}
	
	private String getTdExlusion(String id)
	{
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSION,
				init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
		System.out.println("URL FOR GET TD EXCLUSUION" + myntraService1.URL);

		
		RequestGenerator req = new RequestGenerator(myntraService1);
//		System.out.println(myntraService1.URL);
		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.status");
		System.out.println("messsage ===== >> "+message);
		String name = JsonPath.read(jsonRes1, "$.criterias.name");
		System.out.println("name ===== >> "+name	);

		AssertJUnit.assertEquals("Active", message);

		
		return name;
	}
	
	private String getRejectedTdExlusion(String id)
	{
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDEXCLUSION,
				init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
		System.out.println("URL FOR GET TD EXCLUSUION" + myntraService1.URL);

		
		RequestGenerator req = new RequestGenerator(myntraService1);
//		System.out.println(myntraService1.URL);
		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.status");
		System.out.println("messsage ===== >> "+message);
		String name = JsonPath.read(jsonRes1, "$.criterias.name");
		System.out.println("name ===== >> "+name	);

		AssertJUnit.assertEquals("Rejected", message);

		
		return name;
	}
	
	
	private String deleteTdExlusion(String id)
	{
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDEXCLUSION,
				init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
		System.out.println("URL FOR delete TD EXCLUSUION" + myntraService1.URL);

		
		RequestGenerator req = new RequestGenerator(myntraService1);
//		System.out.println(myntraService1.URL);
		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.status");
		System.out.println("messsage ===== >> "+message);
//		String name = JsonPath.read(jsonRes1, "$.criterias.name");
//		System.out.println("name ===== >> "+name	);
//
//		AssertJUnit.assertEquals("Active", message);

		
		return message;
	}
	
	private String generateRandomString()
	{
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
		int randomLimitedInt = leftLimit + (int) 
		(new Random().nextFloat() * (rightLimit - leftLimit));
		buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		System.out.println(generatedString);
		return generatedString;
	}

}
