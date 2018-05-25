package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.myntra.apiTests.dataproviders.discountOverrideDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DataBaseConnection;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class DiscountOverride extends discountOverrideDP {
	
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = LoggerFactory.getLogger(DiscountOverride.class);
	APIUtilities apiUtil = new APIUtilities();
	public long currentTime = System.currentTimeMillis();
	static CollectionInFocusApiTests CollectionHelper = new CollectionInFocusApiTests();
	DataBaseConnection dbConnection=new DataBaseConnection();


	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	String startTime1 = String.valueOf(currentTime+30000);
	String endTime1 = String.valueOf(currentTime+2000000);
	String endTimeBfrCurTime=String.valueOf(currentTime-1000000);
	//String strtTimeForDisService=String.valueOf(currentTime+35000);
	String endTimeNull="";
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getstyleithData")
	public void createDiscountOverrideFlowWithExclusionAndTdRange(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException {
		
		
		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0","300");	
		deleteCollectionInFocusIds("0","300");
		String name = generateRandomString();
		String discountPushmin = generaterandomINteger();
		String discountPushmax = generaterandomINteger();

		System.out.println("MIn --- "  + discountPushmin);
		System.out.println("Max --- "  + discountPushmax);
		
		Integer discountPusminInt  =Integer.parseInt(discountPushmin);
		Integer discountPusmaxInt  =Integer.parseInt(discountPushmax);

		
		
		Integer discountPush = discountPusminInt+discountPusmaxInt; 
		System.out.println("main --- "  + discountPush);



		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
		
		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
        List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
        List<String> merchandiseType1 = JsonPath.read(jsonRes, "$.styleList..merchandiseType[*]");
        List<String> categoryManager = JsonPath.read(jsonRes, "$.styleList..categoryManager[*]");
        
        List<String> maximumRetailPrice = JsonPath.read(jsonRes, "$.styleList..maximumRetailPrice[*]");


//        String next = stylids.get(0).toString();
//        System.out.println("just check" + next);
        
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();

        for(int i=0 ;i < stylids.size();i++){
        	
        	MyntraService service1 = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,new String[] {
            		stylids.get(i).toString()}, new String[] {defaultpage}, PayloadType.JSON, PayloadType.JSON);
    		service.URL = apiUtil.prepareparameterizedURL(service.URL, defaultpage);
    		System.out.println("service url = " +service1.URL);
    		System.out.println("Service payload"  + service1.Payload);
    		RequestGenerator req = new RequestGenerator(service1);
    		String jsonRes1 = req.respvalidate.returnresponseasstring();
    		System.out.println("rsponse "  + jsonRes1 );
    		
    		if(jsonRes1.contains(stylids.get(i).toString())){
    			
    			String path = "$." + stylids.get(i) + ".tradeDiscount..discountPercent";
    			System.out.println("path---- > >"+ path );
    	        String discount1 = JsonPath.read(jsonRes1, path).toString();
    	         System.out.println("PRINT ____ < < " + JsonPath.read(jsonRes1, path));

   			hmap.put(stylids.get(i), discount1);

    			
    		}
    		
    		else{
    			hmap.put(stylids.get(i), "0");

    		}
//    		System.out.println("response  = " + req.returnresponseasstring());
//    		List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");
//
//    		Set set = hmap.entrySet();
//    		
//			hmap.put(stylids.get(i), id.get(i));


        	
        }
        String[] payloadparams = new String[] { stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString()};
		  
		 
        
        MyntraService service3 = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.CREATESTYLETDRANGE, init.Configurations,new String[] {stylids.get(3).toString(),startTime,endTime},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req3 = new RequestGenerator(service3);
		System.out.println(service3.URL);
		System.out.println("TD RANGE Payload ----  >>?? " + service3.Payload);
		System.out.println("TD RANGE Response ---? " + req3.respvalidate.returnresponseasstring());
		String jsonRes3 = req3.respvalidate.returnresponseasstring();
		String tdRangeId = JsonPath.read(jsonRes3, "$.id");
		System.out.println("TD RANGE ID --- +++ " +tdRangeId );
		
		String rangeMessage = updateTdRangeStatus(tdRangeId,"Active");
		System.out.println("TD RANGE UPDATE MESSAGE" + rangeMessage);
   
		 String finalPayload = getPayloadAsString("createstyletdexclusion",
					payloadparams,startTime,endTime,name);
        System.out.println("Print Hashmap" + hmap);
        MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.CREATESTYLETDEXCLUSION,
				init.Configurations,finalPayload);
		RequestGenerator req1 = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? " + req1.respvalidate.returnresponseasstring());
		String jsonRes2 = req1.respvalidate.returnresponseasstring();

		
		String Id = JsonPath.read(jsonRes2, "$.id");
		System.out.println("TD exclusion ID -- >> " + Id );
		String message= updateTdExclusion(Id ,"Active");
		AssertJUnit.assertEquals("TDExclusion status saved successfully", message);
		String d1 = "32" ;
		String d2 = "22" ;
		String d3 = "28" ;

		
		createDiscountOverride(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,discountPushmin,discountPushmax);
		
		String id = getDiscountOverridePageByStatus("60", "WaitingForApproval",name);
		System.out.println("ID----" + id);
		String doMessage = aprroveDiscountOverride(id);
		System.out.println("DO message----" + doMessage);
		String styelid1 = getDiscountOverrideDetailsById(id);
		System.out.println("name for get discount styles" +styelid1 );
		
		
		MyntraService service4 = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,new String[] {
				styelid1}, new String[] {defaultpage}, PayloadType.JSON, PayloadType.JSON);
		service4.URL = apiUtil.prepareparameterizedURL(service4.URL, defaultpage);
		service.URL = apiUtil.prepareparameterizedURL(service4.URL, defaultpage);
		System.out.println("service url = " +service4.URL);
		System.out.println("Service payload"  + service4.Payload);
		RequestGenerator req = new RequestGenerator(service4);
		String jsonRes1 = req.respvalidate.returnresponseasstring();
		System.out.println("rsponse "  + jsonRes1 );
		
		
			
			String path = "$." + styelid1 + ".tradeDiscount..discountPercent";
			System.out.println("path---- > >"+ path );
	        String discount1 = JsonPath.read(jsonRes1, path).toString().replace("[", "").replace("]", "").trim().toString();
	        Float discount1float = Float.valueOf(discount1); 
	        System.out.println("NEW DISCOUNT--------> "  + discount1float);
	        Integer discount1Int = Math.round(discount1float);
	        System.out.println("NEW DISCOUNT INTR--------> "  + discount1Int);
	      String discountstrFromPE= discount1Int.toString();
	      System.out.println("Discount from PE ------>> "+discountstrFromPE);
	        if(discount1Int==discountPush){
	        	
	        	System.out.println("FInally Testetd discount overerride");
	        	
	        }
		
//----- A5
	        MyntraService service5 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,
					APINAME.GETDISCOUNTOVERRIDEDETAILSBYID, init.Configurations,
					PayloadType.JSON, new String[] {id},
					PayloadType.JSON);
	        RequestGenerator req2 = new RequestGenerator(service5);
			 String jsonres = req2.respvalidate.returnresponseasstring();
			 System.out.println("Printing jsonresp  --  >> "+jsonres);
			String discountIds = JsonPath.read(jsonres, "$.discountOverrideDiscounts[0]").toString();
			 System.out.println(" IMR Printing discount ids -- > "+discountIds);
			 
			 MyntraService service6 = Myntra.getService(
						ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDISCOUNTFORDISCOUNTID,
						init.Configurations,PayloadType.JSON,new String [] {discountIds},PayloadType.JSON);
			 RequestGenerator req4 = new RequestGenerator(service6);
			 String DisServiceRes =req4.respvalidate.returnresponseasstring();
			 System.out.println(" IMR Get discount from discount id ---------------->>>>>>>>"
						+ DisServiceRes);
			 String disIdFrmDisService =JsonPath.read(DisServiceRes, "$..percent").toString().replace("[","").replace("]", "");
			 System.out.println("DiscountId frm discount service is : "+ disIdFrmDisService);
			 Assert.assertEquals(disIdFrmDisService, discountstrFromPE);

			 AssertJUnit.assertEquals("Status not equal to 200", 200,requestGenerator.response.getStatus());

//		System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getDiscountOverrideData")
	public void createDiscountOverrideFlowWithStyleWhichIsNOtInPricingSnapShot(String defaultpage)
			throws InterruptedException {
		Integer random = generaterandomINtegerWIth4digit();
		System.out.println("RANDOM NUMBER  " + random);
		List<Integer> stylids = new ArrayList<Integer> ();
		stylids.add(0,generaterandomINtegerWIth4digit());
		stylids.add(1, generaterandomINtegerWIth4digit());
		stylids.add(2,generaterandomINtegerWIth4digit());
		stylids.add(3,generaterandomINtegerWIth4digit());
		stylids.add(4,generaterandomINtegerWIth4digit());




		deleteTdRangeIds("0", "250");
		deleteTdExclusionIds("0", "300");
		deleteCollectionInFocusIds("0", "300");
		String name = generateRandomString();
		String discountPushmin = generaterandomINteger();
		String discountPushmax = generaterandomINteger();

		System.out.println("MIn --- " + discountPushmin);
		System.out.println("Max --- " + discountPushmax);

		Integer discountPusminInt = Integer.parseInt(discountPushmin);
		Integer discountPusmaxInt = Integer.parseInt(discountPushmax);

		Integer discountPush = discountPusminInt + discountPusmaxInt;
		System.out.println("main --- " + discountPush);

//		MyntraService service = Myntra.getService(
//				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
//				init.Configurations, PayloadType.JSON, new String[] { page,
//						data, merchandiseType }, PayloadType.JSON);
//
//		RequestGenerator requestGenerator = new RequestGenerator(service);
//		System.out.println("service url = " + service.URL);
//		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		List<Integer> stylids = JsonPath.read(jsonRes,
//				"$.styleList..styleId[*]");
//		List<String> merchandiseType1 = JsonPath.read(jsonRes,
//				"$.styleList..merchandiseType[*]");
//		List<String> categoryManager = JsonPath.read(jsonRes,
//				"$.styleList..categoryManager[*]");
//
//		List<String> maximumRetailPrice = JsonPath.read(jsonRes,
//				"$.styleList..maximumRetailPrice[*]");

		// String next = stylids.get(0).toString();
		// System.out.println("just check" + next);

		HashMap<Integer, String> hmap = new HashMap<Integer, String>();

		for (int i = 0; i < stylids.size(); i++) {

			MyntraService service1 = Myntra.getService(
					ServiceType.PORTAL_DISCOUNTSERVICEV2,
					APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,
					new String[] { stylids.get(i).toString() },
					new String[] { defaultpage }, PayloadType.JSON,
					PayloadType.JSON);
			service1.URL = apiUtil.prepareparameterizedURL(service1.URL,
					defaultpage);
			System.out.println("service url = " + service1.URL);
			System.out.println("Service payload" + service1.Payload);
			RequestGenerator req = new RequestGenerator(service1);
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			System.out.println("rsponse " + jsonRes1);

			if (jsonRes1.contains(stylids.get(i).toString())) {

				String path = "$." + stylids.get(i)
						+ ".tradeDiscount..discountPercent";
				System.out.println("path---- > >" + path);
				String discount1 = JsonPath.read(jsonRes1, path).toString();
				System.out.println("PRINT ____ < < "
						+ JsonPath.read(jsonRes1, path));

				hmap.put(stylids.get(i), discount1);

			}

			else {
				hmap.put(stylids.get(i), "0");

			}
			// System.out.println("response  = " +
			// req.returnresponseasstring());
			// List<String> id = JsonPath.read(jsonRes1,
			// "$.tdExclusionDTOs..id[*]");
			//
			// Set set = hmap.entrySet();
			//
			// hmap.put(stylids.get(i), id.get(i));

		}
		String[] payloadparams = new String[] { stylids.get(0).toString(),
				stylids.get(1).toString(), stylids.get(2).toString() };

		MyntraService service3 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATESTYLETDRANGE, init.Configurations, new String[] {
						stylids.get(3).toString(), startTime, endTime },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req3 = new RequestGenerator(service3);
		System.out.println(service3.URL);
		System.out.println("TD RANGE Payload ----  >>?? " + service3.Payload);
		System.out.println("TD RANGE Response ---? "
				+ req3.respvalidate.returnresponseasstring());
		String jsonRes3 = req3.respvalidate.returnresponseasstring();
		String tdRangeId = JsonPath.read(jsonRes3, "$.id");
		System.out.println("TD RANGE ID --- +++ " + tdRangeId);

		String rangeMessage = updateTdRangeStatus(tdRangeId, "Active");
		System.out.println("TD RANGE UPDATE MESSAGE" + rangeMessage);

		String finalPayload = getPayloadAsString("createstyletdexclusion",
				payloadparams, startTime, endTime, name);
		System.out.println("Print Hashmap" + hmap);
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.CREATESTYLETDEXCLUSION, init.Configurations,
				finalPayload);
		RequestGenerator req1 = new RequestGenerator(myntraService);
		System.out.println(myntraService.URL);
		System.out.println("Payload ----  >>?? " + myntraService.Payload);
		System.out.println("Response ---? "
				+ req1.respvalidate.returnresponseasstring());
		String jsonRes2 = req1.respvalidate.returnresponseasstring();

		String Id = JsonPath.read(jsonRes2, "$.id");
		System.out.println("TD exclusion ID -- >> " + Id);
		String message = updateTdExclusion(Id, "Active");
		AssertJUnit.assertEquals("TDExclusion status saved successfully",
				message);
		String d1 = "32";
		String d2 = "22";
		String d3 = "28";

		createDiscountOverride(stylids.get(0).toString(), stylids.get(1)
				.toString(), stylids.get(2).toString(), stylids.get(3)
				.toString(), stylids.get(4).toString(), startTime, endTime,
				name, discountPushmin, discountPushmax);

		String id = getDiscountOverridePageByStatus("60", "WaitingForApproval",
				name);
		System.out.println("ID----" + id);
		String doMessage = aprroveDiscountOverride(id);
		System.out.println("DO message----" + doMessage);
		String styelid1 = getDiscountOverrideDetailsById(id);
		System.out.println("name for get discount styles" + styelid1);

		MyntraService service4 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,
				new String[] { styelid1 }, new String[] { defaultpage },
				PayloadType.JSON, PayloadType.JSON);
		service4.URL = apiUtil.prepareparameterizedURL(service4.URL,
				defaultpage);
		service4.URL = apiUtil
				.prepareparameterizedURL(service4.URL, defaultpage);
		System.out.println("service url = " + service4.URL);
		System.out.println("Service payload" + service4.Payload);
		RequestGenerator req = new RequestGenerator(service4);
		String jsonRes1 = req.respvalidate.returnresponseasstring();
		System.out.println("rsponse " + jsonRes1);

		String path = "$." + styelid1 + ".tradeDiscount..discountPercent";
		System.out.println("path---- > >" + path);
		String discount1 = JsonPath.read(jsonRes1, path).toString()
				.replace("[", "").replace("]", "").trim().toString();
		Float discount1float = Float.valueOf(discount1);
		System.out.println("NEW DISCOUNT--------> " + discount1float);
		Integer discount1Int = Math.round(discount1float);
		System.out.println("NEW DISCOUNT INTR--------> " + discount1Int);

		if (discount1Int == discountPush) {

			System.out.println("FInally Testetd discount overerride");

		}
//
//		AssertJUnit.assertEquals("Status not equal to 200", 200,
//				requestGenerator.response.getStatus());

		//
		//

		// System.out.println("---------------->>>>>>>>"
		// +requestGenerator.respvalidate.returnresponseasstring() );
//		AssertJUnit.assertEquals("Status not equal to 200", 200,
//				requestGenerator.response.getStatus());
	}
	
	
	
	
	
	
	
	
	
	
	private String createDiscountOverride(String style1 , String style2, String style3,String style4,String style5,String startdate,String enddate,String name,String discountPushmin ,String discountPushmax ){
		
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDE, init.Configurations,new String[] {
				style1,style2,style3,style4,style5,startdate,enddate,name,discountPushmin,discountPushmax},PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service1, getParam);
		
		System.out.println("Disocunt Override URL--- " +service1.URL);
		System.out.println("Disocunt Override Payload--- " +service1.Payload);
	

		String jsonRes1 = req.respvalidate.returnresponseasstring();
		System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);

		String message = JsonPath.read(jsonRes1, "$.message");
		System.out.println("MEssage for override" + message);


		
		return message;
		
	}
	
	private String aprroveDiscountOverride(String id){
		
		String val1 = "";
		 MyntraService myntraService2 = Myntra.getService(
					ServiceType.PORTAL_PRICINGENGINE,
					APINAME.APPROVEDISCOUNTOVERRIDE, init.Configurations,
					new String [] {val1},new String[] {id},PayloadType.JSON,
					PayloadType.JSON);
		 System.out.println("Service URLfor approval " +myntraService2.URL );
		 RequestGenerator req2 = new RequestGenerator(myntraService2);
			System.out.println("---------------->>>>>>>>"
					+ req2.respvalidate.returnresponseasstring());
			String jsonRes = req2.respvalidate.returnresponseasstring();
//			System.out.println("Response for apprioael =-= " +jsonRes );
			String message = JsonPath.read(jsonRes, "$.message");
		 
		 return message;
		
	}
	
	private String updateTdRangeStatus(String id ,String status){
		
		
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.UPDATESTYLETDRANGE, init.Configurations,
				new String[] {id,status}, PayloadType.JSON, PayloadType.JSON);
		System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.message");
		return message;
		
	}
	
	private String getDiscountOverrideDetailsById(String id) throws InterruptedException{
		
		MyntraService myntraService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.GETDISCOUNTOVERRIDEDETAILSBYID, init.Configurations,
				PayloadType.JSON, new String[] {id},
				PayloadType.JSON);
		 RequestGenerator req2 = new RequestGenerator(myntraService);
		 System.out.println("Get discount Override---------------->>>>>>>>"
					+ req2.respvalidate.returnresponseasstring());
		 String jsonres = req2.respvalidate.returnresponseasstring();
		 String name = JsonPath.read(jsonres, "$.name");
		 List<String> exlusionreason = JsonPath.read(jsonres,
					"$..discountOverrideStyles.exclusionReason[*]");
			List<Integer> styleids = JsonPath.read(jsonres, "$..discountOverrideStyles.styleId[*]");
			
			System.out.println("COntent Exclusion----- > > " +exlusionreason );
			System.out.println("COntent style ids----- > > " +styleids );

		 
		 HashMap<Integer, String> hmap = new HashMap<Integer, String>();
//			String var1 = hmap.put(jsonres.get(0), exlusionreason.get(0));
			// hmap.put(username.get(1), id.get(1));
			// hmap.put(username.get(2), id.get(2));
			// hmap.put(username.get(3), id.get(3));
			
			Object id1 = "";
			Object id2 = "";
			Object id3 = "";

			for (int i = 0; i < styleids.size(); i++) {
//				if (exlusionreason.get(i) instanceof String) {
//					System.out.println("BACHGEDDD");
//				}
//				if(styleids.get(i) instanceof Integer) {
//					System.out.println("BACHGEDDD");
//				}
				hmap.put(styleids.get(i), exlusionreason.get(i));

			}
			System.out.println("Hmap print" + hmap);

			int count =0;
			
			for(Map.Entry<Integer, String> entry : hmap.entrySet()) {
				
				if (entry.getValue() != null && entry.getValue().equals("TdExclusionViolation")) {
					System.out.println("Exclusion found");
					count++;
					id1 = entry.getKey();
//					System.out.println("Id forund is " + id1);
				}
				else if (entry.getValue() != null  && entry.getValue().equals("TdRangeViolation")) {
					id2 = entry.getKey();
//					System.out.println(" TD range forund is " + id3);
				}
				else{
					id3 = entry.getKey();
				}

				
	
			}
			
			System.out.println("TE Exclusion reasion " + id1 );
			System.out.println("TE Range reasion " + id2);
			System.out.println("discount " + id3 );
			Thread.sleep(450000);
			

		 

		return id3.toString();
		
	}
	
	 private String getDiscountOverridePageByStatus(String pageSize , String status,String name)
	 {
	 
		 
		 MyntraService myntraService = Myntra.getService(
					ServiceType.PORTAL_PRICINGENGINE,
					APINAME.GETDISCOUNTOVERRIDEPAGEBYSTATUS, init.Configurations,
					PayloadType.JSON, new String[] { pageSize, status},
					PayloadType.JSON);
		 HashMap getParam = new HashMap();
			getParam.put("user",
					"manishkumar.gupta@myntra.com");
		 RequestGenerator requestGenerator = new RequestGenerator(myntraService,getParam);
			System.out.println("---------------->>>>>>>>"
					+ requestGenerator.respvalidate.returnresponseasstring());
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			System.out.println("Service URL ----  >>?? " + myntraService.URL);
			List<String> username = JsonPath.read(jsonRes, "$.discountOverrideDTOs..name[*]");
			List<Integer> id = JsonPath.read(jsonRes, "$.discountOverrideDTOs..id[*]");

			
			System.out.println("content 1" + username);
			System.out.println("content 2" + id);
			
			Object var2 = name;
		     HashMap<String, Integer> hmap = new HashMap<String, Integer>();
//	String var1=hmap.put(username.get(0), id.get(0));
//		     hmap.put(username.get(1), id.get(1));
//		     hmap.put(username.get(2), id.get(2));
//		     hmap.put(username.get(3), id.get(3));
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

			return idfound; 
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
	
	public String generaterandomINteger(){
		Random rand = new Random(); 
		int value = rand.nextInt(40); 
		String inte =  String.valueOf(value);
		System.out.println("random integer--- > " + String.valueOf(value));

		return inte;
		  
	}
	
	
	public static int generaterandomINtegerWIth4digit(){
		return ((int) (Math.random()*(9999 - 1000))) + 1000;

		  
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

// ---- 05

		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression" }, dataProvider = "getstyleithData")
		public void defaultEndTimeBeforeCurrentTime(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String msg1=createDiscountOverrideForStyle(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTimeBfrCurTime,name,startTime1,endTime1);
			System.out.println("msg----" + msg1);
			Assert.assertEquals(msg1, "StartDate should be less than EndDate");
		}
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression" }, dataProvider = "getstyleithData")
		public void styleSpecificEndTimeNill(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String msg1=createDiscountOverrideForStyle(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,startTime1,endTimeNull);
			System.out.println("msg----" + msg1);
			if((msg1.contains("null")))
			{
				String str=msg1.substring(0,msg1.indexOf("null")).trim();
			System.out.println("Failed validation msg ---->> "+str);
			Assert.assertEquals(str, "Both startDate and endDate either should be");
			};
			
		}
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression" }, dataProvider = "getstyleithData")
		public void styleSpecificEndTimeBeforeCurrentTime(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String msg1=createDiscountOverrideForStyle(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,startTime1,endTimeBfrCurTime);
			System.out.println("msg----" + msg1);
			Assert.assertEquals(msg1, "StartDate should be less than EndDate");
			
		}
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression" }, dataProvider = "getstyleithDataForDo")
		public void vDefaultAndStyleSpecificStrtTmEndTm(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String id=createDiscountOverride_Id(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,startTime1,endTime1);
			System.out.println("Do_ID ------>  " + id);
			aprroveDiscountOverride(id);
			
			//---
			MyntraService myntraService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.GETDISCOUNTOVERRIDEPAGEBYSTATUS, init.Configurations,PayloadType.JSON, new String[] { pageSize,status},PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","manishkumar.gupta@myntra.com");
			RequestGenerator do_PgStus = new RequestGenerator(myntraService,getParam);
//			System.out.println("---------------->>>>>>>>"
//					+ do_PgStus.respvalidate.returnresponseasstring());
			String do_PgStus_Resp = do_PgStus.respvalidate.returnresponseasstring();
			System.out.println("DO_PgStus ---->> "+do_PgStus_Resp);
			List<Integer> disIds=JsonPath.read(do_PgStus_Resp,"$.discountOverrideDTOs[0].discountOverrideDiscounts");
			System.out.println("DiscountIds ---- >> "+disIds+"");
			
			for (int DisIds1 : disIds) {
				System.out.println("Inside for loop: "+DisIds1);

				 MyntraService service6 = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDISCOUNTFORDISCOUNTID,
							init.Configurations,PayloadType.JSON,new String [] {DisIds1+""},PayloadType.JSON);
				 RequestGenerator req4 = new RequestGenerator(service6);
				 String DisServiceRes =req4.respvalidate.returnresponseasstring();
				 System.out.println("Discount from discount id Resp:  -----  >> "+DisServiceRes);
				 String styleStrtTm=JsonPath.read(DisServiceRes, "$.startedOn").toString();
				// String startTimeDisSer=startTime1+TimeUnit.MINUTES.toMillis(5);

				 //Unable to add 5 minutes to do_strtTimePls5. Once added 5 min this will pass
				 
				 String do_strtTimePls5=String.valueOf(currentTime+35000);
				// long do1=Long.parseLong(do_strtTimePls5)+70000;
				// String str=Long.toString(do_strtTimePls5);
				 System.out.println("StyleSpecificStartTime from discount service is: --->> "+styleStrtTm);
				 System.out.println("StyleSpecificStartTime from discount override is: --->> "+do_strtTimePls5);
				 Assert.assertEquals(styleStrtTm, do_strtTimePls5);
				
			}
			
		}
		
		

		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression" }, dataProvider = "groupingOfstyleithDataForDo")
		public void vGroupingOfDiscountForSameStrtEndTimeCfVf(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String id=createDiscountOverride_Id(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,startTime1,endTime1);
			System.out.println("Do_ID ------>  " + id);
			aprroveDiscountOverride(id);
			
			//---
			MyntraService myntraService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.GETDISCOUNTOVERRIDEPAGEBYSTATUS, init.Configurations,PayloadType.JSON, new String[] { pageSize,status},PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","manishkumar.gupta@myntra.com");
			RequestGenerator do_PgStus = new RequestGenerator(myntraService,getParam);
			String do_PgStus_Resp = do_PgStus.respvalidate.returnresponseasstring();
			System.out.println("DO_PgStus ---->> "+do_PgStus_Resp);
			List<Integer> disIds=JsonPath.read(do_PgStus_Resp,"$.discountOverrideDTOs[0].discountOverrideDiscounts");
			System.out.println("DiscountIds ---- >> "+disIds+"");
			
			for (int DisIds1 : disIds) {
				System.out.println("Inside for loop: "+DisIds1);

				 MyntraService service6 = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDISCOUNTFORDISCOUNTID,
							init.Configurations,PayloadType.JSON,new String [] {DisIds1+""},PayloadType.JSON);
				 RequestGenerator req4 = new RequestGenerator(service6);
				 String DisServiceRes =req4.respvalidate.returnresponseasstring();
				 System.out.println("Discount from discount id Resp:  -----  >> "+DisServiceRes);
				List<Integer> styleFromDisResp=JsonPath.read(DisServiceRes, "$.discountStyles..styleId");
				// System.out.println("Style id from Discount service:------ >>> "+styleFromDisResp);
				
				 Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
				 map.put(DisIds1, styleFromDisResp);
				 for (Entry<Integer, List<Integer>> entry : map.entrySet()) {
			            int key = entry.getKey();
			            List<Integer> values = entry.getValue();
			            System.out.println("DiscountId = " + key+" -> "+"StyleIds = " + values);
			        }
				 
				 for (int i = 0; i < styleFromDisResp.size(); i++) {
					 
					 
					 if(stylids.get(i).toString().contains(styleFromDisResp.get(i).toString())){
						 Assert.assertTrue(stylids.get(i).toString().contains(styleFromDisResp.get(i).toString()));
					 }
					
					 //get
				}
		}
		}
		
		private String createDiscountOverrideForStyle(String style1 , String style2, String style3,String style4,String style5,String startdate,String enddate,String name,String startdate1,String enddate1 ){
			
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSTYLE, init.Configurations,new String[] {style1,style2,style3,style4,style5,startdate,enddate,name,startdate1,enddate1},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","manishkumar.gupta@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
			System.out.println("Disocunt Override URL--- " +service1.URL);
			System.out.println("Disocunt Override Payload--- " +service1.Payload);

			String jsonRes1 = req.respvalidate.returnresponseasstring();
			System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
			String msg=JsonPath.read(jsonRes1, "$.message").toString();
			System.out.println("DO RESPONSE ----> "+msg);

			String message = JsonPath.read(jsonRes1, "$.id");
			System.out.println("MEssage for override" + message);
			return msg;
			
		}
		
			private String createDiscountOverride_Id(String style1 , String style2, String style3,String style4,String style5,String startdate,String enddate,String name,String startdate1,String enddate1 ){
			
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSTYLE, init.Configurations,new String[] {style1,style2,style3,style4,style5,startdate,enddate,name,startdate1,enddate1},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
			System.out.println("Disocunt Override URL--- " +service1.URL);
			System.out.println("Disocunt Override Payload--- " +service1.Payload);

			String jsonRes1 = req.respvalidate.returnresponseasstring();
			System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
			String msg=JsonPath.read(jsonRes1, "$.message").toString();
			System.out.println("DO RESPONSE ----> "+msg);

			String message = JsonPath.read(jsonRes1, "$.id");
			System.out.println("MEssage for override" + message);
			return message;
			
		}
			
			private String createDiscountOverrideForSeller(String style1,String startdate,String enddate,String name, String discountFundingType, String tradeDiscount, String vendorFund){
				
				MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
				HashMap getParam = new HashMap();
				getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator req = new RequestGenerator(service1, getParam);
				System.out.println("Disocunt Override URL--- " +service1.URL);
				//System.out.println("Disocunt Override Payload--- " +service1.Payload);

				String jsonRes1 = req.respvalidate.returnresponseasstring();
				//System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
				String msg=JsonPath.read(jsonRes1, "$.message").toString();
				//System.out.println("DO RESPONSE ----> "+msg);
				String txtResp=msg.split("styleId")[0];
				//System.out.println("Response after split: "+txtResp);

				String message = JsonPath.read(jsonRes1, "$.id");
				//System.out.println("MEssage for override" + message);
				return txtResp;
				
			}
			
				private String createDiscountOverrideForSellerDOmsg(String style1,String startdate,String enddate,String name , String discountFundingType, String tradeDiscount, String vendorFund){
				
				MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
				HashMap getParam = new HashMap();
				getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator req = new RequestGenerator(service1, getParam);
				System.out.println("Disocunt Override URL--- " +service1.URL);
				//System.out.println("Disocunt Override Payload--- " +service1.Payload);

				String jsonRes1 = req.respvalidate.returnresponseasstring();
				System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
				String msg=JsonPath.read(jsonRes1, "$.message").toString();
				System.out.println("DO RESPONSE ----> "+msg);
				String txtResp=msg.split("styleId")[0];
				//System.out.println("Response after split: "+txtResp);

				String message = JsonPath.read(jsonRes1, "$.id");
				//System.out.println("MEssage for override" + message);
				return msg;
				
			}
				
				private String createDiscountOverrideForSellerDOid(String style1,String startdate,String enddate,String name, String discountFundingType, String tradeDiscount, String vendorFund){
					//ArrayList al = new ArrayList();
					MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
					HashMap getParam = new HashMap();
					getParam.put("user","imran.khan2@myntra.com");
					RequestGenerator req = new RequestGenerator(service1, getParam);
					System.out.println("Disocunt Override URL--- " +service1.URL);
					System.out.println("Disocunt Override Payload--- " +service1.Payload);

					String jsonRes1 = req.respvalidate.returnresponseasstring();
					System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
//					String msg=JsonPath.read(jsonRes1, "$.message").toString();
//					System.out.println("DO RESPONSE ----> "+msg);
//					String txtResp=msg.split("styleId")[0];
					//System.out.println("Response after split: "+txtResp);

					String do_id = JsonPath.read(jsonRes1, "$.id");
					//String cf=JsonPath.read(jsonRes1, "$..categoryFundedTD");
					System.out.println("DO id for override: " + do_id);
//					al.add(Do_id);
//					al.add(cf);
					return do_id;
					
				}
			
			//23
			
			@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "groupingOfstyleithDataForDoSeller")
			public void vFundingTypeSELLERandTradeDiscountNotSpecified(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount,String vendorFund) throws InterruptedException
			{
				MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
				
				RequestGenerator requestGenerator = new RequestGenerator(styleservice);
				System.out.println("service url = " +styleservice.URL);
				String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
				String name = generateRandomString();
				List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
				String msg1=createDiscountOverrideForSeller(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
				Assert.assertEquals(msg1, "If FundingType is SELLER, then specify tradeDiscount, sellerFunding, discountLimit for the ");
			
			}
			
			@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "groupingOfstyleithDataForDoEOSSVFnull")
			public void vTradeDiscountWithoutVendorFunding(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
			{
				MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
				
				RequestGenerator requestGenerator = new RequestGenerator(styleservice);
				System.out.println("service url = " +styleservice.URL);
				String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
				String name = generateRandomString();
				List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
				String msg1=createDiscountOverrideForSeller(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
				Assert.assertEquals(msg1, "TradeDiscount is specified, but VendorFundedTD is not specified, for the ");
			
			}
			

			@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "groupingOfstyleithDataForDoEOSSWithVfandWithoutTradeDiscount")
			public void vFundingTypeEOSSNoTradeDiscountVFspecified(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
			{
				MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
				
				RequestGenerator requestGenerator = new RequestGenerator(styleservice);
				System.out.println("service url = " +styleservice.URL);
				String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
				String name = generateRandomString();
				List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
				String msg1=createDiscountOverrideForSellerDOmsg(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
				Assert.assertEquals(msg1, "DiscountOverride created successfully");
			
			}
			
			@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "groupingOfstyleithDataToV_FP_D_FT_Seller")
			public void vFundingPercentage_Discount_FundingType_InDB_IfTypeIsSeller(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
			{
				Connection con = null;
				Statement stmt = null;
				MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
				
				RequestGenerator requestGenerator = new RequestGenerator(styleservice);
				System.out.println("service url = " +styleservice.URL);
				String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
				String name = generateRandomString();
				List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
				String discountOverrideId=createDiscountOverrideForSellerDOid(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
				aprroveDiscountOverride(discountOverrideId);
				System.out.println("MSG1 from test : "+discountOverrideId);
				
				//String discountIdFrmDb="";
			int discount_id=0;
			int get_percent=0;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
					String query1="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+discountOverrideId;
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query1);
					while(rs.next()){
						discount_id=rs.getInt("discount_id");
					}
					
					String query2="select get_percent from myntra.discount_rule where discount_id="+discount_id;
					ResultSet rs1 = stmt.executeQuery(query2);
					System.out.println("QUERY FROM DB: "+rs1);
					while(rs1.next()){
						get_percent=rs1.getInt("get_percent");
					}
					System.out.println("get_percent from db discount rule table: "+get_percent);
						Assert.assertEquals(tradeDiscount, get_percent+"");
					
				}catch(Exception e){
					
				}
				
				finally {

					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
				
				
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "groupingOfstyleithData_Eoss_TDNull_VFNull")
				public void vFundingPercentage_Discount_FundingType_InDB_IfTypeIsEOSS(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
				{
					Connection con = null;
					Statement stmt = null;
					int categoryFunded=9;
					MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
					
					RequestGenerator requestGenerator = new RequestGenerator(styleservice);
					System.out.println("service url = " +styleservice.URL);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					String name = generateRandomString();
					List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
					String discountOverrideId=createDiscountOverrideForSellerDOid(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
					aprroveDiscountOverride(discountOverrideId);
					System.out.println("MSG1 from test : "+discountOverrideId);
				
			
				int discount_limit=100;
				int get_percent=0;
				int discount_id=0;
				int funding_percentage=100;
				int funding_percentagefrmDb=0;
				int discountLimitFrmDb=0;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
					String query1="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+discountOverrideId;
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query1);
					while(rs.next()){
						discount_id=rs.getInt("discount_id");
					}
					
					String query2="select get_percent from myntra.discount_rule where discount_id="+discount_id;
					ResultSet rs1 = stmt.executeQuery(query2);
					System.out.println("QUERY FROM DB: "+rs1);
					while(rs1.next()){
						get_percent=rs1.getInt("get_percent");
					}
					System.out.println("get_percent from db discount rule table: "+get_percent);
						Assert.assertEquals(categoryFunded+"", get_percent+"");
					
					String query3="select funding_percentage from myntra.discount where id="+discount_id;
					ResultSet rs2=stmt.executeQuery(query3);
					System.out.println("Funding_Percentage frm DB ----> : "+rs2);
					while(rs2.next()){
						funding_percentagefrmDb=rs2.getInt("funding_percentage");
					}
					System.out.println("funding_percentage from db discount tabel : "+funding_percentagefrmDb);
					Assert.assertEquals(funding_percentagefrmDb+"", funding_percentage+"");
					
					String query4="select discount_limit from myntra.discount where id="+discount_id;
					ResultSet rs3=stmt.executeQuery(query4);
					System.out.println("discount_limit frm DB ----> : "+rs3);
					while(rs3.next()){
						discountLimitFrmDb=rs3.getInt("funding_percentage");
					}
					System.out.println("funding_percentage from db discount tabel : "+discountLimitFrmDb);
					Assert.assertEquals(discountLimitFrmDb+"", discount_limit+"");
					
				}catch(Exception e){
					
				}
				
				finally {

					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
				
				

				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "groupingOfstyleithData_Eoss_TDNotNull_VFNull")
				public void vFundingPercentage_Discount_FundingType_InDB_IfTypeIsEOSSAndTDisNotNull(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
				{
					Connection con = null;
					Statement stmt = null;
					int categoryFunded=9;
					MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
					
					RequestGenerator requestGenerator = new RequestGenerator(styleservice);
					System.out.println("service url = " +styleservice.URL);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					String name = generateRandomString();
					List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
					String discountOverrideId=createDiscountOverrideForSellerDOid(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
					aprroveDiscountOverride(discountOverrideId);
					System.out.println("MSG1 from test : "+discountOverrideId);
				
			
				//int discount_limit=100;
				int get_percent=0;
				int discount_id=0;
				int funding_percentage=100;
				int funding_percentagefrmDb=0;
				int discountLimitFrmDb=0;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
					String query1="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+discountOverrideId;
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query1);
					while(rs.next()){
						discount_id=rs.getInt("discount_id");
					}
					
					String query2="select get_percent from myntra.discount_rule where discount_id="+discount_id;
					ResultSet rs1 = stmt.executeQuery(query2);
					System.out.println("QUERY FROM DB: "+rs1);
					while(rs1.next()){
						get_percent=rs1.getInt("get_percent");
					}
					System.out.println("get_percent from db discount rule table: "+get_percent);
						Assert.assertEquals(tradeDiscount, get_percent+"");
					
					String query3="select funding_percentage from myntra.discount where id="+discount_id;
					ResultSet rs2=stmt.executeQuery(query3);
					System.out.println("Funding_Percentage frm DB ----> : "+rs2);
					while(rs2.next()){
						funding_percentagefrmDb=rs2.getInt("funding_percentage");
					}
					System.out.println("funding_percentage from db discount tabel : "+funding_percentagefrmDb);
					Assert.assertEquals(funding_percentagefrmDb+"", funding_percentage+"");
					
					String query4="select discount_limit from myntra.discount where id="+discount_id;
					ResultSet rs3=stmt.executeQuery(query4);
					System.out.println("discount_limit frm DB ----> : "+rs3);
					while(rs3.next()){
						discountLimitFrmDb=rs3.getInt("discount_limit");
					}
					System.out.println("funding_percentage from db discount tabel : "+discountLimitFrmDb);
					Assert.assertEquals(discountLimitFrmDb+"", vendorFund);
					
				}catch(Exception e){
					
				}
				
				finally {

					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
				
				
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "tlpFilter_False")
				public void vTlpFilterForFalseAndValidateWithDb(String totalStyles,String actions,String businessUnitId,String businessUnitName, String discountFunding_category, String discountFunding_INSEASON, String discountFunding_EOSS,String seasonsId,String seasonsName, String minAge, String maxAge) throws InterruptedException
				{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
					Connection con = null;
					Statement stmt = null;
					String pushDiscount="false";
					MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTER,init.Configurations,new String [] {totalStyles,endTime,actions,businessUnitId,businessUnitName,discountFunding_category,discountFunding_INSEASON,discountFunding_EOSS,seasonsId,seasonsName,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
					RequestGenerator requestGenerator = new RequestGenerator(tlpService);
					System.out.println("service url = " +tlpService.URL);
					System.out.println("service payload = "+tlpService.Payload);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					System.out.println("TLP response =====>>> : "+jsonRes);
					List<Integer> styleids =JsonPath.read(jsonRes,"$..styleId[*]");
					for(int i=0;i<styleids.size();i++){
					System.out.println("StyleIds are: "+styleids.get(i).toString());
					}
					String singlStyleId=styleids.get(0).toString();
					System.out.println("Style Id : "+singlStyleId);
					String snapshotid=JsonPath.read(jsonRes, "$..snapshotId[0]").toString(); //.replaceAll("[","").replaceAll("]","")
					System.out.println("snapshotid : "+snapshotid);
					String id=JsonPath.read(jsonRes,"$..id[0]").toString();
					System.out.println("id : "+id);
					String currentTd=JsonPath.read(jsonRes, "$..currentTd[0]").toString();
					System.out.println("currentTD : "+currentTd);
					String newTd=JsonPath.read(jsonRes, "$..newTd[0]").toString();
					System.out.println("newTd : "+newTd);
					String minDisc=JsonPath.read(jsonRes, "$..minDiscount[0]").toString();
					System.out.println("minDiscount : "+minDisc);
					String maxDisc=JsonPath.read(jsonRes, "$..maxDiscount[0]").toString();
					System.out.println("maxDiscount : "+ maxDisc);
					String discFunding=JsonPath.read(jsonRes, "$..discountFunding[0]").toString();
					System.out.println("discFunding : "+discFunding);
					String snapShotType=JsonPath.read(jsonRes, "$..snapshotType[0]").toString();
					System.out.println("snapshotType : "+snapShotType);
					String businessUnit=JsonPath.read(jsonRes, "$..businessUnit[0]").toString();
					System.out.println("businessUnit : "+businessUnit);
					
					int styleIdFrmDb=0;
					float currtdFrmdb_f=0f;
					float newTdFromDb_f=0f;
					float minDiscFrmDb_f=0f;
					float maxDiscFrmDb_f=0f;
					String discFundingFrmDb="";
					String snpShtTypFrmDb="";
					int snpshtIdFrmDb=0;
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						String query1="select style_id from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query1 is : "+query1);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query1);
						while(rs.next()){
							styleIdFrmDb=rs.getInt("style_id");
						}
						System.out.println("Style id from db : "+styleIdFrmDb);
						
						
						String query2="select current_td from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query2 is : "+query2);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(query2);
						while(rs1.next()){
						
							currtdFrmdb_f=rs1.getFloat("current_td");
						}
						System.out.println("Current td from db in float format: "+currtdFrmdb_f);
						
						

						String query3="select new_td from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query3 is : "+query3);
						stmt = con.createStatement();
						ResultSet rs2 = stmt.executeQuery(query3);
						while(rs2.next()){
							newTdFromDb_f=rs2.getFloat("new_td");
						}
						System.out.println("new td from db in flaot format : "+newTdFromDb_f);
						
						
						String query4="select min_discount from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query4 is : "+query4);
						stmt = con.createStatement();
						ResultSet rs3 = stmt.executeQuery(query4);
						while(rs3.next()){
							minDiscFrmDb_f=rs3.getFloat("min_discount");
						}
						System.out.println("minimum discount from db in float format : "+minDiscFrmDb_f);
						
						
						String query5="select max_discount from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query5 is : "+query5);
						stmt = con.createStatement();
						ResultSet rs4 = stmt.executeQuery(query5);
						while(rs4.next()){
							maxDiscFrmDb_f=rs4.getFloat("max_discount");
						}
						System.out.println("maximum discount from db in flaot format : "+maxDiscFrmDb_f);
						
						
						String query6="select discount_funding from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("query6 is : "+query6);
						stmt = con.createStatement();
						ResultSet rs5 = stmt.executeQuery(query6);
						while(rs5.next()){
							discFundingFrmDb=rs5.getString("discount_funding");
						}
						System.out.println("Discount funding from db : "+discFundingFrmDb);
						
						
						String query7="select file_type from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("file type  is : "+query7);
						stmt = con.createStatement();
						ResultSet rs6 = stmt.executeQuery(query7);
						while(rs6.next()){
							snpShtTypFrmDb=rs6.getString("file_type");
						}
						System.out.println("Snap shot type from db : "+snpShtTypFrmDb);
						
						String query8="select snapshot_id from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 1";
						System.out.println("SnapShot id  is : "+query8);
						stmt = con.createStatement();
						ResultSet rs7 = stmt.executeQuery(query8);
						while(rs7.next()){
							snpshtIdFrmDb=rs7.getInt("snapshot_id");
						}
						System.out.println("snap shot id from db : "+ snpshtIdFrmDb);
						
						
						Assert.assertEquals(singlStyleId, styleIdFrmDb+"");
						Assert.assertEquals(currentTd, currtdFrmdb_f+"");
						Assert.assertEquals(newTd, newTdFromDb_f+"");
						Assert.assertEquals(minDisc, minDiscFrmDb_f+"");
						Assert.assertEquals(maxDisc, maxDiscFrmDb_f+"");
						Assert.assertEquals(discFunding, discFundingFrmDb+"");
						Assert.assertEquals(snapShotType, snpShtTypFrmDb+"");
						Assert.assertEquals(snapshotid, snpshtIdFrmDb+"");
						
						
					}catch(Exception e){
						
					}
					
					finally {

						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				
					
				}	
				
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "tlpFilter_true")
				public void vTlpFilterForTrueValidateWithDb(String totalStyles,String actions, String discountFunding_category, String discountFunding_INSEASON, String discountFunding_EOSS, String minAge, String maxAge) throws InterruptedException, SQLException
				{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
					Connection con = null;
					Statement stmt = null;
					String pushDiscount="true";
					String discFundingFrmDb="";
					int styleIdFrmDb=0;
					MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTERCATEGORYINSEASONDATA,init.Configurations,new String [] {totalStyles,endTime,actions,discountFunding_category,discountFunding_INSEASON,discountFunding_EOSS,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
					HashMap getParam = new HashMap();
					getParam.put("user","imran.khan2@myntra.com");
					//RequestGenerator req = new RequestGenerator(tlpService, getParam);
					RequestGenerator requestGenerator = new RequestGenerator(tlpService,getParam);
					System.out.println("service url = " +tlpService.URL);
					System.out.println("service payload = "+tlpService.Payload);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					System.out.println("TLP response =====>>> : "+jsonRes);
					String snapshotid=JsonPath.read(jsonRes, "$..snapshotId[0]").toString();
					System.out.println("snapshotid : "+snapshotid);
					List<String> discountfunding=JsonPath.read(jsonRes, "$..discountFunding[*]");
					System.out.println("DiscountFunding is: "+discountfunding);
					List<Integer> styleids =JsonPath.read(jsonRes,"$..styleId[*]");
					System.out.println("Style ids are: "+styleids);
					Map m1 = new HashMap();
					m1.put(styleids,discountfunding);
					System.out.println("Map elements: "+m1);
					
					try {
						
					Class.forName("com.mysql.jdbc.Driver");
					//con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
					//con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","MyntraQADBUser","B3F0R3#PR0D");
					con=DriverManager.getConnection("jdbc:mysql://"+"54.251.240.111"+"/myntra?"+"user=MyntraQADBUser&password=B3F0R3#PR0D&port=3306");
					String query="select style_id from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 6";
					System.out.println("query is : "+query);
					stmt = con.createStatement();
					ArrayList<Integer> resultList = new ArrayList<Integer>();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						styleIdFrmDb=rs.getInt("style_id");
						resultList.add(styleIdFrmDb);
					}
//					for (Integer stylid : resultList) {
//						System.out.println("stle id from db : "+stylid);
//					}
					//System.out.println("stle id from db : "+styleIdFrmDb);
					
					
					String dfquery="select discount_funding from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 6";
					System.out.println("query is : "+dfquery);
					stmt = con.createStatement();
					ArrayList<String> dfresultList = new ArrayList<String>();
					ResultSet rs1 = stmt.executeQuery(dfquery);
					while(rs1.next()){
						//System.out.println("Result set for discount_funding : "+rs1.getString("discount_funding"));
						//Getting NULL here. Need to check
						discFundingFrmDb=rs1.getString("discount_funding");
						
						dfresultList.add(discFundingFrmDb);
					}
//					for (String dfStr : dfresultList) {
//						System.out.println("discount funding from db: "+dfStr);
//						
//					}
					Map m2=new HashMap();
					m2.put(resultList, dfresultList);
					System.out.println("mapping elements from db: "+m2);
					Assert.assertEquals(m1.keySet(), m2.keySet());
					//System.out.println("Result set for discount_funding : "+rs1.getString("discount_funding"));
					
					}catch(Exception e){
						
					}
					
					finally {

						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
					
				}

				
				
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "tlpFilter_False")
				public void vTlpFilterForTrueAndValidateWithCategoryInDb(String totalStyles,String actions,String businessUnitId,String businessUnitName, String discountFunding_category, String discountFunding_INSEASON, String discountFunding_EOSS,String seasonsId,String seasonsName, String minAge, String maxAge) throws InterruptedException
				{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
					Connection con = null;
					Statement stmt = null;
					int styleidFrmDb=0;
					String pushDiscount="true";
					ArrayList<Integer> stylresLst = new ArrayList<Integer>();
					ArrayList<Float> catResLst=new ArrayList<Float>();
					float catTrDiscFrmDb=0f;
					MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTER,init.Configurations,new String [] {totalStyles,endTime,actions,businessUnitId,businessUnitName,discountFunding_category,discountFunding_INSEASON,discountFunding_EOSS,seasonsId,seasonsName,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
					HashMap getParam = new HashMap();
					getParam.put("user","imran.khan2@myntra.com");
					RequestGenerator requestGenerator = new RequestGenerator(tlpService,getParam);
					System.out.println("service url = " +tlpService.URL);
					System.out.println("service payload = "+tlpService.Payload);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					System.out.println("TLP response =====>>> : "+jsonRes);
					List<Integer> styleids =JsonPath.read(jsonRes,"$..styleId[*]");
					
					String snapshotid=JsonPath.read(jsonRes, "$..snapshotId[0]").toString(); //.replaceAll("[","").replaceAll("]","")
					List<Double> cfval=JsonPath.read(jsonRes, "$..newTd[*]");
					
					Map<Integer, Double> newMap=new HashMap<Integer, Double>();
					for(int i=0;i<styleids.size();i++)
					{
						newMap.put(styleids.get(i),cfval.get(i));
					}
					
					
					Map m1 = new HashMap();
					m1.put(styleids, cfval);
					System.out.println("maping elements from api response: "+m1);
					
					String do_id=JsonPath.read(jsonRes, "$.discountOverrideIds").toString().replace("[", "").replace("]", "").trim();
					int doid_int=Integer.parseInt(do_id);
					System.out.println("Do-Id int is: "+doid_int);
					
					
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						String query="select style_id from pricingengine.discount_override_style_map where discount_override_id="+doid_int;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while(rs.next()){
							styleidFrmDb=rs.getInt("style_id");
							stylresLst.add(styleidFrmDb);
						}
						for (Integer styleIdint : stylresLst) {
							System.out.println("Style id from db are : "+styleIdint);
							
						}
						
						//String query1="select category_trade_discount from pricingengine.discount_override_style_map where discount_override_id="+doid_int;
						String customQuery="select category_trade_discount,style_id from pricingengine.discount_override_style_map where discount_override_id="+doid_int;
						System.out.println("query is : "+customQuery);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(customQuery);
						
						Map<Integer, Double> newMap2=new HashMap<>();
						
						while(rs1.next()){
							
							//rs1.getInt("style_id");
							//catTrDiscFrmDb=rs1.getFloat("category_trade_discount");
							
							newMap2.put(rs1.getInt("style_id"), rs1.getDouble("category_trade_discount"));
							catResLst.add(catTrDiscFrmDb);
						}
						
						boolean mapMatches=false;
						Map m2= new HashMap();
						m2.put(stylresLst, catResLst);
						System.out.println("mapping elements from db: "+m2);
						Assert.assertEquals(newMap, newMap2);
						//Assert.assertEquals(m1.values(), m2.values());
						//Assert.assertTrue(m1.containsKey(m2));
						
						
					}catch(Exception e){}
						finally {

							try {
								con.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						}
				}
				

				
				@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
				"Regression" }, dataProvider = "tlpFilter_true_EOSSand_INSEASON_SOR")
				public void vTlpFilterForChangesInCalculation(String totalStyles,String actions,String businessUnitId, String businessUnitName,String discountFunding_EOSS_SOR, String discountFunding_INSEASON_SOR, String discountFunding_INSEASON, String discountFunding_EOSS, String seasonsId,String seasonsName,String minAge, String maxAge) throws InterruptedException, SQLException
				{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
					Connection con = null;
					Statement stmt = null;
					double newDiscoutLimit;
					double cfFrmDb=0.0;
					double vfFrmDb=0.0;
					double fundingPercntFrmDb=0.0;
					double discLmtFrmDb =0.0;
					String pushDiscount="true";
					MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTEREOSSSORINSEASONSOR,init.Configurations,new String [] {totalStyles,endTime,actions,businessUnitId,businessUnitName,discountFunding_EOSS_SOR,discountFunding_INSEASON_SOR,discountFunding_INSEASON,discountFunding_EOSS,seasonsId,seasonsName,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
					HashMap getParam = new HashMap();
					getParam.put("user","imran.khan2@myntra.com");
					RequestGenerator requestGenerator = new RequestGenerator(tlpService,getParam);
					System.out.println("service url = " +tlpService.URL);
					System.out.println("service payload = "+tlpService.Payload);
					String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
					System.out.println("TLP response =====>>> : "+jsonRes);
					int do_Id=JsonPath.read(jsonRes, "$..discountOverrideIds[0]");
					int styleids =JsonPath.read(jsonRes,"$..styleId[0]");
					double currentTd=JsonPath.read(jsonRes,"$..currentTd[0]");
					double newTd=JsonPath.read(jsonRes,"$..newTd[0]");
					double fundingPercent=JsonPath.read(jsonRes, "$..fundingPercentage[0]");
					double discLimit=JsonPath.read(jsonRes, "$..discountLimit[0]");
					
					System.out.println("DiscountOverrideID : "+do_Id+"\n"+"Styleid : "+styleids+"\n"+"CurrentTD : "+currentTd+"\n"+"newTd : "+newTd+"\n"+"fundingPercent : "+fundingPercent+"\n"+"discountLimit : "+discLimit);
					double newVf=(fundingPercent*currentTd)/100;
					System.out.println("New Vf calculated is : "+newVf);
					double newCf=newTd-newVf;
					System.out.println("New cf calculated : "+newCf);
					double newFundingPercent= newVf/(newCf+newVf)*100;
					System.out.println("New funding percent calcuated : "+newFundingPercent);
					if(discLimit==100){
						 newDiscoutLimit= newVf;
					}
					else{
						 newDiscoutLimit=discLimit;
					}
					
					System.out.println("New discount limit calculated : "+newDiscoutLimit);
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						String query="select category_trade_discount from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while(rs.next()){
							cfFrmDb=rs.getDouble("category_trade_discount");
							
							Assert.assertEquals(cfFrmDb, newCf);
							
						}
						System.out.println("CF from db : "+cfFrmDb);
						while(rs.next()){
							vfFrmDb=rs.getDouble("vendor_trade_discount");
							Assert.assertEquals(vfFrmDb, newVf);
						}
						System.out.println("Vf From Db : "+vfFrmDb);
						
						String customQuery="select vendor_trade_discount from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
						System.out.println("query is : "+customQuery);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(customQuery);
						
						while(rs1.next()){
							vfFrmDb=rs.getDouble("vendor_trade_discount");
							//System.out.println("Vf From Db : "+vfFrmDb);
							Assert.assertEquals(vfFrmDb, newVf);
						}
						System.out.println("Vf From Db : "+vfFrmDb);
						String Query1="select funding_percentage from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
						System.out.println("query is : "+Query1);
						stmt = con.createStatement();
						ResultSet rs2 = stmt.executeQuery(Query1);
						
						while(rs2.next()){
							fundingPercntFrmDb=rs.getDouble("funding_percentage");
							
							Assert.assertEquals(fundingPercntFrmDb, fundingPercent);
							
						}
						System.out.println("Funding percentage from db : "+fundingPercntFrmDb);
						String Query2="select discount_limit from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
						System.out.println("query is : "+Query2);
						stmt = con.createStatement();
						ResultSet rs3 = stmt.executeQuery(Query2);
						
						while(rs3.next()){
							discLmtFrmDb=rs.getDouble("discount_limit");
							
							Assert.assertEquals(discLmtFrmDb, discLimit);
						}
						System.out.println("Discount Limit from db : "+discLmtFrmDb);
					}catch(Exception e){}
							finally {

								try {
									con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							}
					
				}
}

				

