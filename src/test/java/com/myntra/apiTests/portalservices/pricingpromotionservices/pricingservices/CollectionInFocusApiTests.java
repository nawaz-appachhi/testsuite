package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.dataproviders.CollectionInFocusDP;
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
import com.myntra.apiTests.common.Myntra;

public class CollectionInFocusApiTests extends CollectionInFocusDP {
	
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = LoggerFactory.getLogger(CollectionInFocusApiTests.class);
	public long currentTime = System.currentTimeMillis();
//	String startTime = "" , endTime = "";
	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void createStyleCollection(String page, String data,
			String merchandiseType ,String CollectionInFocusBoost,String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0","300");
		deleteCollectionInFocusIds("0","300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());
		
	for (int i = 0; i < stylids.size(); i++) {
		
		
		MyntraService myntraService2 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICY,
				APINAME.FINDSTYLECOLLECTIONINFOCUS, init.Configurations,
				new String[] { stylids.get(i).toString()}, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println(myntraService2.URL);
		RequestGenerator requ = new RequestGenerator(myntraService2);
		System.out.println("Payload for filter td range ----  >>?? "
				+ myntraService2.Payload);
		System.out.println("Response for filter td range ---? "
				+ requ.respvalidate.returnresponseasstring());
		String jsonRes4 = requ.respvalidate.returnresponseasstring();
		String collectionTd = JsonPath
				.read(jsonRes4, "$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
				.toString().replace("[", "").replace("]", "").trim();
		System.out.println("td range value for filter td range ---- "
				+ collectionTd);
		String var = "null";
		if (collectionTd.contains(var)) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.CREATECOLLECTIONINFOCUSUSINGPAYLOAD, init.Configurations,
					new String[] { startTime,endTime,CollectionInFocusBoost,stylids.get(i).toString(),name,description },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService1.URL);
			HashMap getParam = new HashMap();
			getParam.put("user",
					"manishkumar.gupta@myntra.com");
			RequestGenerator req = new RequestGenerator(myntraService1, getParam);
			System.out.println("Payload for Creating collection in focus ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for Creating collection in focus ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String CollectionInFocusID = JsonPath.read(jsonRes1,"$.id").toString();

			System.out.println(" collection ID in focus ---- "
					+ CollectionInFocusID);
		

				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
						new String[] { CollectionInFocusID, "Active" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE collection in focus"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE collection in focus ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE collection in focus ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE collection in focus" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
						PayloadType.JSON, new String[] { CollectionInFocusID },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req5 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET collection in focus----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET collection in focus---? "
						+ req5.respvalidate.returnresponseasstring());
				String jsonRes5 = req5.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes5, "$.status");
				System.out.println("message for  FOR collection in focus" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Active", message4);
				String message5 = deleteCollectionInFocus(CollectionInFocusID);
				System.out.println("message for delete" + message5);

			}

	
			// String user = getTdExlusion(idfound);
			// AssertJUnit.assertEquals(name, user);
			// deleteTdExlusion(idfound);
			// AssertJUnit.assertEquals("TDExclusion status saved successfully",
			// message);

			AssertJUnit.assertEquals("Status not equal to 200", 200,
					requestGenerator.response.getStatus());

		
	}
	}
	
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void rejectStyleCollectionAfterCreating(String page, String data,
			String merchandiseType, String CollectionInFocusBoost,
			String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());

		for (int i = 0; i < stylids.size(); i++) {

			MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.FINDSTYLECOLLECTIONINFOCUS, init.Configurations,
					new String[] { stylids.get(i).toString() },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService2.URL);
			RequestGenerator requ = new RequestGenerator(myntraService2);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService2.Payload);
			System.out.println("Response for filter td range ---? "
					+ requ.respvalidate.returnresponseasstring());
			String jsonRes4 = requ.respvalidate.returnresponseasstring();
			String collectionTd = JsonPath
					.read(jsonRes4,
							"$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ collectionTd);
			String var = "null";
			if (collectionTd.contains(var)) {

				MyntraService myntraService1 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.CREATECOLLECTIONINFOCUSUSINGPAYLOAD,
						init.Configurations, new String[] { startTime, endTime,
								CollectionInFocusBoost,
								stylids.get(i).toString(), name, description },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println(myntraService1.URL);
				HashMap getParam = new HashMap();
				getParam.put("user", "manishkumar.gupta@myntra.com");
				RequestGenerator req = new RequestGenerator(myntraService1,
						getParam);
				System.out
						.println("Payload for Creating collection in focus ----  >>?? "
								+ myntraService1.Payload);
				System.out
						.println("Response for Creating collection in focus ---? "
								+ req.respvalidate.returnresponseasstring());
				String jsonRes1 = req.respvalidate.returnresponseasstring();
				String CollectionInFocusID = JsonPath.read(jsonRes1, "$.id")
						.toString();

				System.out.println(" collection ID in focus ---- "
						+ CollectionInFocusID);

				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
						new String[] { CollectionInFocusID, "Rejected" },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL FOR UPDATE collection in focus"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out
						.println("Payload FOR UPDATE collection in focus ----  >>?? "
								+ myntraService3.Payload);
				System.out
						.println("Response FOR UPDATE collection in focus ---? "
								+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE collection in focus"
								+ message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
						PayloadType.JSON, new String[] { CollectionInFocusID },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req5 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out
						.println("Payload  FOR GET collection in focus----  >>?? "
								+ myntraService4.Payload);
				System.out.println("Response  FOR GET collection in focus---? "
						+ req5.respvalidate.returnresponseasstring());
				String jsonRes5 = req5.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes5, "$.status");
				System.out.println("message for  FOR collection in focus"
						+ message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteCollectionInFocus(CollectionInFocusID);
				System.out.println("message for delete" + message5);

			}

			// String user = getTdExlusion(idfound);
			// AssertJUnit.assertEquals(name, user);
			// deleteTdExlusion(idfound);
			// AssertJUnit.assertEquals("TDExclusion status saved successfully",
			// message);

			AssertJUnit.assertEquals("Status not equal to 200", 200,
					requestGenerator.response.getStatus());

		}
	}
	
	
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void createBrandStyleCollection(String page, String data,
			String merchandiseType, String CollectionInFocusBoost,
			String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		List<String> brands = JsonPath.read(jsonRes, "$.styleList..brand[*]");

		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());

		for (int i = 0; i < stylids.size(); i++) {
			
			
			
			
			MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.FINDBRANDSTYLECOLLECTIONINFOCUS, init.Configurations,
					new String[] { stylids.get(i).toString(),brands.get(i).toString()}, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService2.URL);
			RequestGenerator requ = new RequestGenerator(myntraService2);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService2.Payload);
			System.out.println("Response for filter td range ---? "
					+ requ.respvalidate.returnresponseasstring());
			String jsonRes4 = requ.respvalidate.returnresponseasstring();
			String collectionTd = JsonPath
					.read(jsonRes4, "$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ collectionTd);
			String var = "null";
			if (collectionTd.contains(var)) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.CREATEBRANDSTYLECOLLECTIONINFOCUS,
					init.Configurations, new String[] { startTime, endTime,
							CollectionInFocusBoost, stylids.get(i).toString(),brands.get(i).toString(),
							name, description }, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService1.URL);
			HashMap getParam = new HashMap();
			getParam.put("user", "manishkumar.gupta@myntra.com");
			RequestGenerator req = new RequestGenerator(myntraService1,
					getParam);
			System.out
					.println("Payload for Creating collection in focus ----  >>?? "
							+ myntraService1.Payload);
			System.out
					.println("Response for Creating collection in focus ---? "
							+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String CollectionInFocusID = JsonPath.read(jsonRes1, "$.id")
					.toString();

			System.out.println(" collection ID in focus ---- "
					+ CollectionInFocusID);

			MyntraService myntraService3 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
					new String[] { CollectionInFocusID, "Active" },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println("URL FOR UPDATE collection in focus"
					+ myntraService3.URL);
			RequestGenerator req3 = new RequestGenerator(myntraService3);
			System.out
					.println("Payload FOR UPDATE collection in focus ----  >>?? "
							+ myntraService3.Payload);
			System.out.println("Response FOR UPDATE collection in focus ---? "
					+ req3.respvalidate.returnresponseasstring());
			String jsonRes3 = req3.respvalidate.returnresponseasstring();

			String message = JsonPath.read(jsonRes3, "$.message");
			System.out.println("messsage for FOR UPDATE collection in focus"
					+ message);

			MyntraService myntraService4 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
					PayloadType.JSON, new String[] { CollectionInFocusID },
					PayloadType.JSON);
			System.out.println("URL FOR GET TD Range" + myntraService1.URL);

			RequestGenerator req5 = new RequestGenerator(myntraService4);
			// System.out.println(myntraService1.URL);
			System.out
					.println("Payload  FOR GET collection in focus----  >>?? "
							+ myntraService4.Payload);
			System.out.println("Response  FOR GET collection in focus---? "
					+ req5.respvalidate.returnresponseasstring());
			String jsonRes5 = req5.respvalidate.returnresponseasstring();

			String message4 = JsonPath.read(jsonRes5, "$.status");
			System.out.println("message for  FOR collection in focus"
					+ message4);
			// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
			// System.out.println("name FOR GET TD Rang===== >> "+name1);

			AssertJUnit.assertEquals("Active", message4);
			String message5 = deleteCollectionInFocus(CollectionInFocusID);
			System.out.println("message for delete" + message5);

		}

		// String user = getTdExlusion(idfound);
		// AssertJUnit.assertEquals(name, user);
		// deleteTdExlusion(idfound);
		// AssertJUnit.assertEquals("TDExclusion status saved successfully",
		// message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());

	}
	
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void rejectBrandStyleCollectionAfterCreating(String page, String data,
			String merchandiseType, String CollectionInFocusBoost,
			String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		List<String> brands = JsonPath.read(jsonRes, "$.styleList..brand[*]");

		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());

		for (int i = 0; i < stylids.size(); i++) {

			MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.FINDBRANDSTYLECOLLECTIONINFOCUS,
					init.Configurations,
					new String[] { stylids.get(i).toString(),
							brands.get(i).toString() }, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService2.URL);
			RequestGenerator requ = new RequestGenerator(myntraService2);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService2.Payload);
			System.out.println("Response for filter td range ---? "
					+ requ.respvalidate.returnresponseasstring());
			String jsonRes4 = requ.respvalidate.returnresponseasstring();
			String collectionTd = JsonPath
					.read(jsonRes4,
							"$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ collectionTd);
			String var = "null";
			if (collectionTd.contains(var)) {

				MyntraService myntraService1 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.CREATEBRANDSTYLECOLLECTIONINFOCUS,
						init.Configurations, new String[] { startTime, endTime,
								CollectionInFocusBoost,
								stylids.get(i).toString(),
								brands.get(i).toString(), name, description },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println(myntraService1.URL);
				HashMap getParam = new HashMap();
				getParam.put("user", "manishkumar.gupta@myntra.com");
				RequestGenerator req = new RequestGenerator(myntraService1,
						getParam);
				System.out
						.println("Payload for Creating collection in focus ----  >>?? "
								+ myntraService1.Payload);
				System.out
						.println("Response for Creating collection in focus ---? "
								+ req.respvalidate.returnresponseasstring());
				String jsonRes1 = req.respvalidate.returnresponseasstring();
				String CollectionInFocusID = JsonPath.read(jsonRes1, "$.id")
						.toString();

				System.out.println(" collection ID in focus ---- "
						+ CollectionInFocusID);

				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
						new String[] { CollectionInFocusID, "Rejected" },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL FOR UPDATE collection in focus"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out
						.println("Payload FOR UPDATE collection in focus ----  >>?? "
								+ myntraService3.Payload);
				System.out
						.println("Response FOR UPDATE collection in focus ---? "
								+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE collection in focus"
								+ message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
						PayloadType.JSON, new String[] { CollectionInFocusID },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req5 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out
						.println("Payload  FOR GET collection in focus----  >>?? "
								+ myntraService4.Payload);
				System.out.println("Response  FOR GET collection in focus---? "
						+ req5.respvalidate.returnresponseasstring());
				String jsonRes5 = req5.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes5, "$.status");
				System.out.println("message for  FOR collection in focus"
						+ message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteCollectionInFocus(CollectionInFocusID);
				System.out.println("message for delete" + message5);

			}

			// String user = getTdExlusion(idfound);
			// AssertJUnit.assertEquals(name, user);
			// deleteTdExlusion(idfound);
			// AssertJUnit.assertEquals("TDExclusion status saved successfully",
			// message);

			AssertJUnit.assertEquals("Status not equal to 200", 200,
					requestGenerator.response.getStatus());

		}

	}
	
	
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void createBrandStyleManagerCollection(String page, String data,
			String merchandiseType, String CollectionInFocusBoost,
			String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		List<String> brands = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		List<String> categoryManager = JsonPath.read(jsonRes, "$.styleList..categoryManager[*]");


		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());

		for (int i = 0; i < stylids.size(); i++) {

			MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.FINDBRANDMANAGERSTYLECOLLECTIONINFOCUS, init.Configurations,
					new String[] { stylids.get(i).toString(),brands.get(i).toString(),categoryManager.get(i).toString() },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService2.URL);
			RequestGenerator requ = new RequestGenerator(myntraService2);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService2.Payload);
			System.out.println("Response for filter td range ---? "
					+ requ.respvalidate.returnresponseasstring());
			String jsonRes4 = requ.respvalidate.returnresponseasstring();
			String collectionTd = JsonPath
					.read(jsonRes4,
							"$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ collectionTd);
			String var = "null";
			if (collectionTd.contains(var)) {

				MyntraService myntraService1 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.CREATEBRANDMANAGERSTYLECOLLECTIONINFOCUS,
						init.Configurations, new String[] { startTime, endTime,
								CollectionInFocusBoost,
								stylids.get(i).toString(),
								brands.get(i).toString(),categoryManager.get(i).toString(),name, description },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println(myntraService1.URL);
				HashMap getParam = new HashMap();
				getParam.put("user", "manishkumar.gupta@myntra.com");
				RequestGenerator req = new RequestGenerator(myntraService1,
						getParam);
				System.out
						.println("Payload for Creating collection in focus ----  >>?? "
								+ myntraService1.Payload);
				System.out
						.println("Response for Creating collection in focus ---? "
								+ req.respvalidate.returnresponseasstring());
				String jsonRes1 = req.respvalidate.returnresponseasstring();
				String CollectionInFocusID = JsonPath.read(jsonRes1, "$.id")
						.toString();

				System.out.println(" collection ID in focus ---- "
						+ CollectionInFocusID);

				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
						new String[] { CollectionInFocusID, "Active" },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL FOR UPDATE collection in focus"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out
						.println("Payload FOR UPDATE collection in focus ----  >>?? "
								+ myntraService3.Payload);
				System.out
						.println("Response FOR UPDATE collection in focus ---? "
								+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE collection in focus"
								+ message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
						PayloadType.JSON, new String[] { CollectionInFocusID },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req5 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out
						.println("Payload  FOR GET collection in focus----  >>?? "
								+ myntraService4.Payload);
				System.out.println("Response  FOR GET collection in focus---? "
						+ req5.respvalidate.returnresponseasstring());
				String jsonRes5 = req5.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes5, "$.status");
				System.out.println("message for  FOR collection in focus"
						+ message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Active", message4);
				String message5 = deleteCollectionInFocus(CollectionInFocusID);
				System.out.println("message for delete" + message5);

			}

			// String user = getTdExlusion(idfound);
			// AssertJUnit.assertEquals(name, user);
			// deleteTdExlusion(idfound);
			// AssertJUnit.assertEquals("TDExclusion status saved successfully",
			// message);

			AssertJUnit.assertEquals("Status not equal to 200", 200,
					requestGenerator.response.getStatus());

		}

	}
	
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "collectionInFocusDP")
	public void rejectBrandStyleManagerCollectionAfterCreating(String page, String data,
			String merchandiseType, String CollectionInFocusBoost,
			String description) {
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");

		String name = generateRandomString();
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations, PayloadType.JSON, new String[] { page,
						data, merchandiseType }, PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);

		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
				+ requestGenerator.respvalidate.returnresponseasstring());
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
		List<Integer> stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		List<String> brands = JsonPath.read(jsonRes, "$.styleList..brand[*]");
		List<String> categoryManager = JsonPath.read(jsonRes,
				"$.styleList..categoryManager[*]");

		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());

		for (int i = 0; i < stylids.size(); i++) {

			MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY,
					APINAME.FINDBRANDMANAGERSTYLECOLLECTIONINFOCUS,
					init.Configurations, new String[] {
							stylids.get(i).toString(),
							brands.get(i).toString(),
							categoryManager.get(i).toString() },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService2.URL);
			RequestGenerator requ = new RequestGenerator(myntraService2);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService2.Payload);
			System.out.println("Response for filter td range ---? "
					+ requ.respvalidate.returnresponseasstring());
			String jsonRes4 = requ.respvalidate.returnresponseasstring();
			String collectionTd = JsonPath
					.read(jsonRes4,
							"$..styleCollectionInfocusDTOList.collectionInfocusBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ collectionTd);
			String var = "null";
			if (collectionTd.contains(var)) {

				MyntraService myntraService1 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.CREATEBRANDMANAGERSTYLECOLLECTIONINFOCUS,
						init.Configurations, new String[] { startTime, endTime,
								CollectionInFocusBoost,
								stylids.get(i).toString(),
								brands.get(i).toString(),
								categoryManager.get(i).toString(), name,
								description }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println(myntraService1.URL);
				HashMap getParam = new HashMap();
				getParam.put("user", "manishkumar.gupta@myntra.com");
				RequestGenerator req = new RequestGenerator(myntraService1,
						getParam);
				System.out
						.println("Payload for Creating collection in focus ----  >>?? "
								+ myntraService1.Payload);
				System.out
						.println("Response for Creating collection in focus ---? "
								+ req.respvalidate.returnresponseasstring());
				String jsonRes1 = req.respvalidate.returnresponseasstring();
				String CollectionInFocusID = JsonPath.read(jsonRes1, "$.id")
						.toString();

				System.out.println(" collection ID in focus ---- "
						+ CollectionInFocusID);

				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.UPDATECOLLECTIONINFOCUS, init.Configurations,
						new String[] { CollectionInFocusID, "Rejected" },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL FOR UPDATE collection in focus"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out
						.println("Payload FOR UPDATE collection in focus ----  >>?? "
								+ myntraService3.Payload);
				System.out
						.println("Response FOR UPDATE collection in focus ---? "
								+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE collection in focus"
								+ message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICY,
						APINAME.GETCOLLECTIONINFOCUS, init.Configurations,
						PayloadType.JSON, new String[] { CollectionInFocusID },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req5 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out
						.println("Payload  FOR GET collection in focus----  >>?? "
								+ myntraService4.Payload);
				System.out.println("Response  FOR GET collection in focus---? "
						+ req5.respvalidate.returnresponseasstring());
				String jsonRes5 = req5.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes5, "$.status");
				System.out.println("message for  FOR collection in focus"
						+ message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteCollectionInFocus(CollectionInFocusID);
				System.out.println("message for delete" + message5);

			}

			// String user = getTdExlusion(idfound);
			// AssertJUnit.assertEquals(name, user);
			// deleteTdExlusion(idfound);
			// AssertJUnit.assertEquals("TDExclusion status saved successfully",
			// message);

			AssertJUnit.assertEquals("Status not equal to 200", 200,
					requestGenerator.response.getStatus());

		}

	}

	
	
	
	
private String deleteTdRange(String id){
		
		
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDRANGE,
				init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
		System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
		System.out.println("Payload for filter td range ----  >>?? " + myntraService1.Payload);
		System.out.println("Response for filter td range ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();
		String message = JsonPath.read(jsonRes1, "$.message");
		
		return message;
		
	}


private String deleteCollectionInFocus(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICY, APINAME.DELETECOLLECTIONINFOCUS,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
	System.out.println("Payload for Delete colelction in foxus ----  >>?? " + myntraService1.Payload);
	System.out.println("Response for Delete colelction in foxus  " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}

private String deleteTdExlusion(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDEXCLUSION,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
	System.out.println("Payload for DELETE TD EXCLUSION ----  >>?? " + myntraService1.Payload);
	System.out.println("Response for DELETE TD EXCLUSION  ---? " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}


String deleteTdRangeIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW,
			APINAME.GETTDRANGEBYPAGESIZE, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no TD range");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.tdRangeDTOs..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteTdRange(ids.get(i).toString());

			}

		}

	
	}

	return "0";


}


private String deleteTdExclusionIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW,
			APINAME.GETTDEXCLUSIONS, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no TD EXLUSION");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.tdExclusionDTOs..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteTdExlusion(ids.get(i).toString());

			}

		}

	
	}

	return "0";


}



private String deleteCollectionInFocusIds(String pageIndex, String pageSize) {

	MyntraService service = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICY,
			APINAME.GETCOLLECTIONSINFOCUSBYPAGE, init.Configurations,
			PayloadType.JSON, new String[] { pageIndex, pageSize },
			PayloadType.JSON);
	RequestGenerator requestGenerator = new RequestGenerator(service);

	System.out.println("service url For STYLE DATAPAGE FOR COLLECTION IN FOCUS = " + service.URL);
	System.out.println("Response For STYLE DATAPAGE FOR COLLECTION IN FOCUS ---->>>>>>"
			+ requestGenerator.respvalidate.returnresponseasstring());
	String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
	Integer count=  JsonPath.read(jsonRes, "$.count");
	if(count == 0)
	{
		System.out.println("there is no COLLECTION IN FOCUS");

	}
	else {
		List<String> ids = JsonPath.read(jsonRes, "$.collectionInfocusDTOList..id[*]");
		{
			
			for (int i = 0; i < ids.size(); i++) {
				String deleteMsg = deleteCollectionInFocus(ids.get(i).toString());

			}

		}

	
	}

	return "0";


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
