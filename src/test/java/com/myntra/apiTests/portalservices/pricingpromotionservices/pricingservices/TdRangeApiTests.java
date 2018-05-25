package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.PricingPolicyApiHelper.PricingPolicyApiServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.dataproviders.tdRangeDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class TdRangeApiTests extends tdRangeDP {
	
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = LoggerFactory.getLogger(TdRangeApiTests.class);
	public long currentTime = System.currentTimeMillis();
//	String startTime = "" , endTime = "";
	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	PricingPolicyApiServiceHelper pps=new PricingPolicyApiServiceHelper();
	
	
//	
//	@BeforeClass(alwaysRun = true)
//	public void openHome()
//	{
//		deleteTdRangeIds("0","150");
//	}


	private String deleteTdRangeIds(String pageIndex, String pageSize) {

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
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
		return "0";

	
	}
		
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createStyleTdRange(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0","150");

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
		List<Integer>stylids = JsonPath.read(jsonRes,
				"$.styleList..styleId[*]");
		System.out.println("Styleid For STYLE DATAPAGE " + stylids);
		System.out.println("size ---> " + stylids.size());
		
		for(int i=0;i<stylids.size();i++)
		{
			
			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERSTYLETDRANGE, init.Configurations,
					new String[] {stylids.get(i).toString()}, PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? " + myntraService1.Payload);
			System.out.println("Response for filter td range ---? " + req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO").toString().replace("[","").replace("]","").trim();
			System.out.println("td range value for filter td range ---- " + tdrange);
			String var="null";
            if(tdrange.contains(var))
            {
            	MyntraService myntraService2 = Myntra.getService(
    					ServiceType.PORTAL_PRICINGPOLICYNEW,
    					APINAME.CREATESTYLETDRANGE, init.Configurations,
    					new String[] {stylids.get(i).toString(),startTime,endTime}, PayloadType.JSON, PayloadType.JSON);
    			System.out.println("URL for create td range ----  >>?? " + myntraService2.URL);

    			System.out.println("Payload for create td range ----  >>?? " + myntraService2.Payload);

            	RequestGenerator req2 = new RequestGenerator(myntraService2);
    			System.out.println("Response for create td range---? " + req2.respvalidate.returnresponseasstring());
    			String jsonRes2 = req2.respvalidate.returnresponseasstring();
    			String ids = JsonPath.read(jsonRes2, "$.id");
            	
            	System.out.println("ID for td range" + ids );
            	MyntraService myntraService3 = Myntra.getService(
        				ServiceType.PORTAL_PRICINGPOLICYNEW,
        				APINAME.UPDATESTYLETDRANGE, init.Configurations,
        				new String[] {ids,"Active"}, PayloadType.JSON, PayloadType.JSON);
        		System.out.println("URL FOR UPDATE TD RANGE"+ myntraService3.URL);
        		RequestGenerator req3 = new RequestGenerator(myntraService3);
        		System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? " + myntraService3.Payload);
        		System.out.println("Response FOR UPDATE TD RANGE ---? " + req3.respvalidate.returnresponseasstring());
        		String jsonRes3 = req3.respvalidate.returnresponseasstring();

        		String message = JsonPath.read(jsonRes3, "$.message");
        		System.out.println("messsage for FOR UPDATE TD RANGE"+ message);
        		
        		MyntraService myntraService4 = Myntra.getService(
        				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.GETTDRANGE,
        				init.Configurations,PayloadType.JSON,new String [] {ids},PayloadType.JSON);
        		System.out.println("URL FOR GET TD Range" + myntraService1.URL);

        		
        		RequestGenerator req4 = new RequestGenerator(myntraService4);
//        		System.out.println(myntraService1.URL);
        		System.out.println("Payload  FOR GET TD Rang----  >>?? " + myntraService4.Payload);
        		System.out.println("Response  FOR GET TD Rang---? " + req4.respvalidate.returnresponseasstring());
        		String jsonRes4 = req4.respvalidate.returnresponseasstring();

        		String message4 = JsonPath.read(jsonRes4, "$.status");
        		System.out.println("message for  FOR GET TD Rang" + message4);
//        		String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
//        		System.out.println("name FOR GET TD Rang===== >> "+name1);

        		AssertJUnit.assertEquals("Active", message4);
        		String message5 = deleteTdRange(ids);
        		System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		
        		
            }
			
		
		

//		String user = getTdExlusion(idfound);
//		AssertJUnit.assertEquals(name, user);
//		deleteTdExlusion(idfound);
//		AssertJUnit.assertEquals("TDExclusion status saved successfully",
//				message);

		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
		
		
		
	}
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectStyleTdRangeAfterCreating(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0", "150");

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

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERSTYLETDRANGE, init.Configurations,
					new String[] { stylids.get(i).toString() },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for filter td range ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath
					.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ tdrange);
			String var = "null";
			if (tdrange.contains(var)) {
				MyntraService myntraService2 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.CREATESTYLETDRANGE, init.Configurations,
						new String[] { stylids.get(i).toString(), startTime,
								endTime }, PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL for create td range ----  >>?? "
						+ myntraService2.URL);

				System.out.println("Payload for create td range ----  >>?? "
						+ myntraService2.Payload);

				RequestGenerator req2 = new RequestGenerator(myntraService2);
				System.out.println("Response for create td range---? "
						+ req2.respvalidate.returnresponseasstring());
				String jsonRes2 = req2.respvalidate.returnresponseasstring();
				String ids = JsonPath.read(jsonRes2, "$.id");

				System.out.println("ID for td range" + ids);
				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.UPDATESTYLETDRANGE, init.Configurations,
						new String[] { ids, "Rejected" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE TD RANGE"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE TD RANGE ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE TD RANGE" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.GETTDRANGE, init.Configurations,
						PayloadType.JSON, new String[] { ids },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req4 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET TD Rang----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET TD Rang---? "
						+ req4.respvalidate.returnresponseasstring());
				String jsonRes4 = req4.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes4, "$.status");
				System.out.println("message for  FOR GET TD Rang" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteTdRange(ids);
				System.out.println("message for delete" + message5);
				System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		

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
			"Regression" }, dataProvider = "getstyleithData")
	public void createBrandManagerStyleIdTdRange(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0","150");

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
		List<String> brands = JsonPath.read(jsonRes,
				"$.styleList..brand[*]");
		List<String> managers = JsonPath.read(jsonRes,
				"$.styleList..categoryManager[*]");
		System.out.println("Styleid For Brand DATAPAGE " + brands);
		System.out.println("size ---> " + brands.size());

		for (int i = 0; i < brands.size(); i++) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERBRANDMANAGERTDRANGE, init.Configurations,
					new String[] {stylids.get(i).toString(), brands.get(i).toString(),managers.get(i).toString() },
					PayloadType.JSON, PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for filter td range ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath
					.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ tdrange);
			String var = "null";
			if (tdrange.contains(var)) {
				MyntraService myntraService2 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.CREATEBRANDMANAGERTDRANGE, init.Configurations,
						new String[] { stylids.get(i).toString(), brands.get(i).toString(), startTime,
								endTime,managers.get(i).toString()}, PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL for create td range ----  >>?? "
						+ myntraService2.URL);

				System.out.println("Payload for create td range ----  >>?? "
						+ myntraService2.Payload);

				RequestGenerator req2 = new RequestGenerator(myntraService2);
				System.out.println("Response for create td range---? "
						+ req2.respvalidate.returnresponseasstring());
				String jsonRes2 = req2.respvalidate.returnresponseasstring();
				String ids = JsonPath.read(jsonRes2, "$.id");

				System.out.println("ID for td range" + ids);
				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.UPDATESTYLETDRANGE, init.Configurations,
						new String[] { ids, "Active" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE TD RANGE"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE TD RANGE ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE TD RANGE" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.GETTDRANGE, init.Configurations,
						PayloadType.JSON, new String[] { ids },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req4 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET TD Rang----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET TD Rang---? "
						+ req4.respvalidate.returnresponseasstring());
				String jsonRes4 = req4.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes4, "$.status");
				System.out.println("message for  FOR GET TD Rang" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Active", message4);
				String message5 = deleteTdRange(ids);
				System.out.println("message for delete" + message5);
				System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		

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
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectedBrandManagerStyleIdTdRangeAfterCreating(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0", "150");

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
		List<String> managers = JsonPath.read(jsonRes,
				"$.styleList..categoryManager[*]");
		System.out.println("Styleid For Brand DATAPAGE " + brands);
		System.out.println("size ---> " + brands.size());

		for (int i = 0; i < brands.size(); i++) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERBRANDMANAGERTDRANGE, init.Configurations,
					new String[] { stylids.get(i).toString(),
							brands.get(i).toString(),
							managers.get(i).toString() }, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for filter td range ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath
					.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ tdrange);
			String var = "null";
			if (tdrange.contains(var)) {
				MyntraService myntraService2 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.CREATEBRANDMANAGERTDRANGE, init.Configurations,
						new String[] { stylids.get(i).toString(),
								brands.get(i).toString(), startTime, endTime,
								managers.get(i).toString() }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL for create td range ----  >>?? "
						+ myntraService2.URL);

				System.out.println("Payload for create td range ----  >>?? "
						+ myntraService2.Payload);

				RequestGenerator req2 = new RequestGenerator(myntraService2);
				System.out.println("Response for create td range---? "
						+ req2.respvalidate.returnresponseasstring());
				String jsonRes2 = req2.respvalidate.returnresponseasstring();
				String ids = JsonPath.read(jsonRes2, "$.id");

				System.out.println("ID for td range" + ids);
				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.UPDATESTYLETDRANGE, init.Configurations,
						new String[] { ids, "Rejected" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE TD RANGE"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE TD RANGE ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE TD RANGE" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.GETTDRANGE, init.Configurations,
						PayloadType.JSON, new String[] { ids },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req4 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET TD Rang----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET TD Rang---? "
						+ req4.respvalidate.returnresponseasstring());
				String jsonRes4 = req4.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes4, "$.status");
				System.out.println("message for  FOR GET TD Rang" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteTdRange(ids);
				System.out.println("message for delete" + message5);
				System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		

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
			"Regression" }, dataProvider = "getstyleithData")
	public void createBrandTdRange(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0", "150");

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
		System.out.println("Styleid For Brand DATAPAGE " + brands);
		System.out.println("size ---> " + brands.size());

		for (int i = 0; i < brands.size(); i++) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERBRANDTDRANGE, init.Configurations,
					new String[] { stylids.get(i).toString(),
							brands.get(i).toString() }, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for filter td range ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath
					.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ tdrange);
			String var = "null";
			if (tdrange.contains(var)) {
				MyntraService myntraService2 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.CREATEBRANDTDRANGE, init.Configurations,
						new String[] { stylids.get(i).toString(),
								brands.get(i).toString(), startTime, endTime },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL for create td range ----  >>?? "
						+ myntraService2.URL);

				System.out.println("Payload for create td range ----  >>?? "
						+ myntraService2.Payload);

				RequestGenerator req2 = new RequestGenerator(myntraService2);
				System.out.println("Response for create td range---? "
						+ req2.respvalidate.returnresponseasstring());
				String jsonRes2 = req2.respvalidate.returnresponseasstring();
				String ids = JsonPath.read(jsonRes2, "$.id");

				System.out.println("ID for td range" + ids);
				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.UPDATESTYLETDRANGE, init.Configurations,
						new String[] { ids, "Active" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE TD RANGE"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE TD RANGE ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE TD RANGE" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.GETTDRANGE, init.Configurations,
						PayloadType.JSON, new String[] { ids },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req4 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET TD Rang----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET TD Rang---? "
						+ req4.respvalidate.returnresponseasstring());
				String jsonRes4 = req4.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes4, "$.status");
				System.out.println("message for  FOR GET TD Rang" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Active", message4);
				String message5 = deleteTdRange(ids);
				System.out.println("message for delete" + message5);
				System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		

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
			"Regression" }, dataProvider = "getstyleithData")
	public void rejectedBrandTdRangeafterCreating(String page, String data,
			String merchandiseType) {
		deleteTdRangeIds("0", "150");

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
		System.out.println("Styleid For Brand DATAPAGE " + brands);
		System.out.println("size ---> " + brands.size());

		for (int i = 0; i < brands.size(); i++) {

			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICYNEW,
					APINAME.FILTERBRANDTDRANGE, init.Configurations,
					new String[] { stylids.get(i).toString(),
							brands.get(i).toString() }, PayloadType.JSON,
					PayloadType.JSON);
			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
			System.out.println("Payload for filter td range ----  >>?? "
					+ myntraService1.Payload);
			System.out.println("Response for filter td range ---? "
					+ req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String tdrange = JsonPath
					.read(jsonRes1, "$..styleTDRangeDTOs.tdRangeBasicDTO")
					.toString().replace("[", "").replace("]", "").trim();
			System.out.println("td range value for filter td range ---- "
					+ tdrange);
			String var = "null";
			if (tdrange.contains(var)) {
				MyntraService myntraService2 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.CREATEBRANDTDRANGE, init.Configurations,
						new String[] { stylids.get(i).toString(),
								brands.get(i).toString(), startTime, endTime },
						PayloadType.JSON, PayloadType.JSON);
				System.out.println("URL for create td range ----  >>?? "
						+ myntraService2.URL);

				System.out.println("Payload for create td range ----  >>?? "
						+ myntraService2.Payload);

				RequestGenerator req2 = new RequestGenerator(myntraService2);
				System.out.println("Response for create td range---? "
						+ req2.respvalidate.returnresponseasstring());
				String jsonRes2 = req2.respvalidate.returnresponseasstring();
				String ids = JsonPath.read(jsonRes2, "$.id");

				System.out.println("ID for td range" + ids);
				MyntraService myntraService3 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.UPDATESTYLETDRANGE, init.Configurations,
						new String[] { ids, "Rejected" }, PayloadType.JSON,
						PayloadType.JSON);
				System.out.println("URL FOR UPDATE TD RANGE"
						+ myntraService3.URL);
				RequestGenerator req3 = new RequestGenerator(myntraService3);
				System.out.println("Payload FOR UPDATE TD RANGE ----  >>?? "
						+ myntraService3.Payload);
				System.out.println("Response FOR UPDATE TD RANGE ---? "
						+ req3.respvalidate.returnresponseasstring());
				String jsonRes3 = req3.respvalidate.returnresponseasstring();

				String message = JsonPath.read(jsonRes3, "$.message");
				System.out
						.println("messsage for FOR UPDATE TD RANGE" + message);

				MyntraService myntraService4 = Myntra.getService(
						ServiceType.PORTAL_PRICINGPOLICYNEW,
						APINAME.GETTDRANGE, init.Configurations,
						PayloadType.JSON, new String[] { ids },
						PayloadType.JSON);
				System.out.println("URL FOR GET TD Range" + myntraService1.URL);

				RequestGenerator req4 = new RequestGenerator(myntraService4);
				// System.out.println(myntraService1.URL);
				System.out.println("Payload  FOR GET TD Rang----  >>?? "
						+ myntraService4.Payload);
				System.out.println("Response  FOR GET TD Rang---? "
						+ req4.respvalidate.returnresponseasstring());
				String jsonRes4 = req4.respvalidate.returnresponseasstring();

				String message4 = JsonPath.read(jsonRes4, "$.status");
				System.out.println("message for  FOR GET TD Rang" + message4);
				// String name1 = JsonPath.read(jsonRes1, "$.criterias.name");
				// System.out.println("name FOR GET TD Rang===== >> "+name1);

				AssertJUnit.assertEquals("Rejected", message4);
				String message5 = deleteTdRange(ids);
				System.out.println("message for delete" + message5);
				System.out.println("message for delete" + message5);
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req2.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req3.response.getStatus());
        		AssertJUnit.assertEquals("Status not equal to 200", 200,
        				req4.response.getStatus());
        		

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
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				req.response.getStatus());
		return message;
		
	}
	
	
}
