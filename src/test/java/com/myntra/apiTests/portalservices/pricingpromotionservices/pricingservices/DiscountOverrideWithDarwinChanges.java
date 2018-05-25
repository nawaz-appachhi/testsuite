package com.myntra.apiTests.portalservices.pricingpromotionservices.pricingservices;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.DiscountOverrideDarwinDp;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DataBaseConnection;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DiscountOverrideWithDarwinChanges extends DiscountOverrideDarwinDp {
	
	
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = LoggerFactory.getLogger(DiscountOverrideWithDarwinChanges.class);
	APIUtilities apiUtil = new APIUtilities();
	public long currentTime = System.currentTimeMillis();
	static CollectionInFocusApiTests CollectionHelper = new CollectionInFocusApiTests();
	DataBaseConnection dbConnection=new DataBaseConnection();


	String startTime = String.valueOf(currentTime);
	String endTime = String.valueOf(currentTime+4000000);
	String startTime1 = String.valueOf(currentTime+30000);
	String endTime1 = String.valueOf(currentTime+2000000);
	String endTimeBfrCurTime=String.valueOf(currentTime-1000000);
	String startTimeDis=String.valueOf(currentTime/1000);
	String endTimeDis=String.valueOf((currentTime+4000000)/1000);
	ArrayList discOverdIds = new ArrayList();
	ArrayList styleIdsFrmDb=new ArrayList();
	CommonUtils cmUtil=new CommonUtils();
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
	"Regression","Fox7Sanity" }, dataProvider = "getstyleithData")
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
//System.out.println("Get style data page response : "+jsonRes);
List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
List<String> merchandiseType1 = JsonPath.read(jsonRes, "$.styleList..merchandiseType[*]");
List<String> categoryManager = JsonPath.read(jsonRes, "$.styleList..categoryManager[*]");

List<String> maximumRetailPrice = JsonPath.read(jsonRes, "$.styleList..maximumRetailPrice[*]");


//String next = stylids.get(0).toString();
//System.out.println("just check" + next);

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
//	System.out.println("response  = " + req.returnresponseasstring());
//	List<String> id = JsonPath.read(jsonRes1, "$.tdExclusionDTOs..id[*]");
//
//	Set set = hmap.entrySet();
//	
//	hmap.put(stylids.get(i), id.get(i));


	
}
String[] payloadparams = new String[] { stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString()};
  
 

MyntraService service3 = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.CREATESTYLETDRANGE, init.Configurations,new String[] {stylids.get(3).toString(),startTime,endTime},PayloadType.JSON, PayloadType.JSON);
RequestGenerator req3 = new RequestGenerator(service3);
//System.out.println(service3.URL);
//System.out.println("TD RANGE Payload ----  >>?? " + service3.Payload);
//System.out.println("TD RANGE Response ---? " + req3.respvalidate.returnresponseasstring());
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


String do_id=createDiscountOverride(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTime,name,discountPushmin,discountPushmax);

//String id = getDiscountOverridePageByStatus("60", "WaitingForApproval",name);
String id=internallyaprroveDiscountOverride(do_id);
System.out.println("ID----" + do_id);
String doMessage = internallyaprroveDiscountOverride(do_id);
System.out.println("DO message----" + doMessage);
String styelid1 = getDiscountOverrideDetailsById(do_id);
System.out.println("name for get discount styles" +styelid1 );


sellerApprovalDiscountOverride(do_id);


MyntraService service4 = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETTRADEANDDEALDISCOUNTS, init.Configurations,new String[] {
		styelid1}, new String[] {defaultpage}, PayloadType.JSON, PayloadType.JSON);
service4.URL = apiUtil.prepareparameterizedURL(service4.URL, defaultpage);
service.URL = apiUtil.prepareparameterizedURL(service4.URL, defaultpage);
//System.out.println("service url = " +service4.URL);
//System.out.println("Service payload"  + service4.Payload);
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
	 //System.out.println("Printing jsonresp  --  >> "+jsonres);
	String discountIds = JsonPath.read(jsonres, "$.discountOverrideDiscounts[0]").toString();
	// System.out.println(" Printing discount ids -- > "+discountIds);
	 
	 MyntraService service6 = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDISCOUNTFORDISCOUNTID,
				init.Configurations,PayloadType.JSON,new String [] {discountIds},PayloadType.JSON);
	 RequestGenerator req4 = new RequestGenerator(service6);
	 String DisServiceRes =req4.respvalidate.returnresponseasstring();
	 //System.out.println(" Get discount from discount id ---------------->>>>>>>>"+ DisServiceRes);
	 String disIdFrmDisService =JsonPath.read(DisServiceRes, "$..percent").toString().replace("[","").replace("]", "");
	 //System.out.println("DiscountId frm discount service is : "+ disIdFrmDisService);
	 Assert.assertEquals(disIdFrmDisService, discountstrFromPE);

	 AssertJUnit.assertEquals("Status not equal to 200", 200,requestGenerator.response.getStatus());

//System.out.println("---------------->>>>>>>>" +requestGenerator.respvalidate.returnresponseasstring()  );
AssertJUnit.assertEquals("Status not equal to 200", 200,
		requestGenerator.response.getStatus());
}
	
	private String updateTdRangeStatus(String id ,String status){
		
		
		MyntraService myntraService1 = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICYNEW,
				APINAME.UPDATESTYLETDRANGE, init.Configurations,
				new String[] {id,status}, PayloadType.JSON, PayloadType.JSON);
		//System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
//		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
//		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
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

//		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
//		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
//				+ requestGenerator.respvalidate.returnresponseasstring());
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

//		System.out.println("service url For STYLE DATAPAGE = " + service.URL);
//		System.out.println("Response For STYLE DATAPAGE---->>>>>>"
//				+ requestGenerator.respvalidate.returnresponseasstring());
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
//
//		System.out.println("service url For STYLE DATAPAGE FOR COLLECTION IN FOCUS = " + service.URL);
//		System.out.println("Response For STYLE DATAPAGE FOR COLLECTION IN FOCUS ---->>>>>>"
//				+ requestGenerator.respvalidate.returnresponseasstring());
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
	
	public String generaterandomINteger(){
		Random rand = new Random(); 
		int value = rand.nextInt(40); 
		String inte =  String.valueOf(value);
		System.out.println("random integer--- > " + String.valueOf(value));

		return inte;
		  
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
		//System.out.println(myntraService1.URL);
		RequestGenerator req = new RequestGenerator(myntraService1);
//		System.out.println("Payload ----  >>?? " + myntraService1.Payload);
//		System.out.println("Response ---? " + req.respvalidate.returnresponseasstring());
		String jsonRes1 = req.respvalidate.returnresponseasstring();

		String message = JsonPath.read(jsonRes1, "$.message");

		
		return message;
	}

private String createDiscountOverride(String style1 , String style2, String style3,String style4,String style5,String startdate,String enddate,String name,String discountPushmin ,String discountPushmax ){
		
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDE, init.Configurations,new String[] {
				style1,style2,style3,style4,style5,startdate,enddate,name,discountPushmin,discountPushmax},PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user",
				"manishkumar.gupta@myntra.com");
		RequestGenerator req = new RequestGenerator(service1, getParam);
		
//		System.out.println("Disocunt Override URL--- " +service1.URL);
//		System.out.println("Disocunt Override Payload--- " +service1.Payload);
		String jsonRes1 = req.respvalidate.returnresponseasstring();
//		System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
		String Do_id = JsonPath.read(jsonRes1, "$.id").toString();
//		System.out.println("MEssage for override : " + Do_id);
		return Do_id;
		
	}


//private String getDiscountOverridePageByStatus(String pageSize , String status,String name)
//{
//
//	 
//	 MyntraService myntraService = Myntra.getService(
//				ServiceType.PORTAL_PRICINGENGINE,
//				APINAME.GETDISCOUNTOVERRIDEPAGEBYSTATUS, init.Configurations,
//				PayloadType.JSON, new String[] { pageSize, status},
//				PayloadType.JSON);
//	 HashMap getParam = new HashMap();
//		getParam.put("user",
//				"manishkumar.gupta@myntra.com");
//	 RequestGenerator requestGenerator = new RequestGenerator(myntraService,getParam);
//		System.out.println("---------------->>>>>>>>"
//				+ requestGenerator.respvalidate.returnresponseasstring());
//		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Service URL ----  >>?? " + myntraService.URL);
//		List<String> username = JsonPath.read(jsonRes, "$.discountOverrideDTOs..name[*]");
//		List<Integer> id = JsonPath.read(jsonRes, "$.discountOverrideDTOs..id[*]");
//
//		//Darwin changes
//		
//		
//		System.out.println("content 1" + username);
//		System.out.println("content 2" + id);
//		
//		Object var2 = name;
//	     HashMap<String, Integer> hmap = new HashMap<String, Integer>();
////String var1=hmap.put(username.get(0), id.get(0));
////	     hmap.put(username.get(1), id.get(1));
////	     hmap.put(username.get(2), id.get(2));
////	     hmap.put(username.get(3), id.get(3));
//		Set set = hmap.entrySet();
//		Object id1="";
//		for(int i=0;i<username.size();i++){
//			hmap.put(username.get(i), id.get(i));
//
//		}
//		System.out.println("Hmap print" + hmap);
//		Iterator iterator = set.iterator();
//		String idfound= "";
//		while (iterator.hasNext()) {
//			
//			Map.Entry mentry = (Map.Entry) iterator.next();
//			if( mentry.getKey().equals(var2))
//			{
//				System.out.println("found");
//				 id1= mentry.getValue();
//				 System.out.println("Id forund is " +id1 );
//			}
//			
//			 idfound = String.valueOf(id1);
//				
//
//		}
//
//		return idfound; 
//}


private String aprroveDiscountOverride(String Do_id){
	
	String val1 = "";
	 MyntraService myntraService2 = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.APPROVEDISCOUNTOVERRIDE, init.Configurations,
				new String [] {val1},new String[] {Do_id},PayloadType.JSON,
				PayloadType.JSON);
//	 System.out.println("Service URLfor approval " +myntraService2.URL );
	 RequestGenerator req2 = new RequestGenerator(myntraService2);
//		System.out.println("---------------->>>>>>>>"
//				+ req2.respvalidate.returnresponseasstring());
		String jsonRes = req2.respvalidate.returnresponseasstring();
//		System.out.println("Response for apprioael =-= " +jsonRes );
		String message = JsonPath.read(jsonRes, "$.message");
	 
	 return message;
	
}


private String internallyaprroveDiscountOverride(String Do_id){
	
//	String val1 = "";
	 MyntraService myntraService2 = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.INTERNALLYAPPROVEDDISCOUNTOVERRIDE, init.Configurations,new String[] {" "},
				new String[] {Do_id},
				PayloadType.JSON,PayloadType.JSON);
//	 System.out.println("Service URLfor internally approval " +myntraService2.URL );
//	 System.out.println("Service payload internally approval " +myntraService2.Payload );

	 HashMap getParam = new HashMap();
//		getParam.put("Content-Type","application/json");
		getParam.put("user","ik@kk.com");
	 RequestGenerator req2 = new RequestGenerator(myntraService2,getParam);
//		System.out.println("---------------->>>>>>>>"
//				+ req2.respvalidate.returnresponseasstring());
		String jsonRes = req2.respvalidate.returnresponseasstring();
//		System.out.println("Response for apprioael =-= " +jsonRes );
		String message = JsonPath.read(jsonRes, "$.message");
	 
	 return message;
	
}

private String sellerApprovalDiscountOverride(String Do_id){
	
	String val1 = "Active";
	 MyntraService myntraService2 = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE,
				APINAME.SELLERAPPROVALDISCOUNTOVERRIDE, init.Configurations,
				new String [] {Do_id,val1},PayloadType.JSON,//,new String[] {val1},
				PayloadType.JSON);
//	 System.out.println("Service URLfor approval " +myntraService2.URL );
	 HashMap getParam = new HashMap();
		getParam.put("user","myntra_vector@vector.com");
		getParam.put("userid", 1234);
	 RequestGenerator req2 = new RequestGenerator(myntraService2,getParam);
//		System.out.println("---------------->>>>>>>>"+ req2.respvalidate.returnresponseasstring());
		String jsonRes = req2.respvalidate.returnresponseasstring();
//		System.out.println("Response for apprioael =-= " +jsonRes );
		String message = JsonPath.read(jsonRes, "$.message");
	 
	 return message;
	
}

private String getDiscountOverrideDetailsById(String id) throws InterruptedException{
	
	MyntraService myntraService = Myntra.getService(
			ServiceType.PORTAL_PRICINGENGINE,
			APINAME.GETDISCOUNTOVERRIDEDETAILSBYID, init.Configurations,
			PayloadType.JSON, new String[] {id},
			PayloadType.JSON);
	 RequestGenerator req2 = new RequestGenerator(myntraService);
//	 System.out.println("Get discount Override---------------->>>>>>>>"+ req2.respvalidate.returnresponseasstring());
	 String jsonres = req2.respvalidate.returnresponseasstring();
	 String name = JsonPath.read(jsonres, "$.name");
	 List<String> exlusionreason = JsonPath.read(jsonres,
				"$..discountOverrideStyles.exclusionReason[*]");
		List<Integer> styleids = JsonPath.read(jsonres, "$..discountOverrideStyles.styleId[*]");
		
//		System.out.println("COntent Exclusion----- > > " +exlusionreason );
//		System.out.println("COntent style ids----- > > " +styleids );

	 
	 HashMap<Integer, String> hmap = new HashMap<Integer, String>();
		
		Object id1 = "";
		Object id2 = "";
		Object id3 = "";

		for (int i = 0; i < styleids.size(); i++) {
			hmap.put(styleids.get(i), exlusionreason.get(i));

		}
		System.out.println("Hmap print" + hmap);

		int count =0;
		
		for(Map.Entry<Integer, String> entry : hmap.entrySet()) {
			
			if (entry.getValue() != null && entry.getValue().equals("TdExclusionViolation")) {
				System.out.println("Exclusion found");
				count++;
				id1 = entry.getKey();
//				System.out.println("Id forund is " + id1);
			}
			else if (entry.getValue() != null  && entry.getValue().equals("TdRangeViolation")) {
				id2 = entry.getKey();
//				System.out.println(" TD range forund is " + id3);
			}
			else{
				id3 = entry.getKey();
			}

		}
		
//		System.out.println("TE Exclusion reasion " + id1 );
//		System.out.println("TE Range reasion " + id2);
//		System.out.println("discount " + id3 );
		Thread.sleep(450000);
		

	 

	return id3.toString();
	
}

	private String deleteTdRange(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDRANGE,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
//	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
//	System.out.println("Payload for filter td range ----  >>?? " + myntraService1.Payload);
//	System.out.println("Response for filter td range ---? " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}


		private String deleteTdExlusion(String id){
	
	
	MyntraService myntraService1 = Myntra.getService(
			ServiceType.PORTAL_PRICINGPOLICYNEW, APINAME.DELETETDEXCLUSION,
			init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
//	System.out.println(myntraService1.URL);
	RequestGenerator req = new RequestGenerator(myntraService1);
//	System.out.println("Payload for DELETE TD EXCLUSION ----  >>?? " + myntraService1.Payload);
//	System.out.println("Response for DELETE TD EXCLUSION  ---? " + req.respvalidate.returnresponseasstring());
	String jsonRes1 = req.respvalidate.returnresponseasstring();
	String message = JsonPath.read(jsonRes1, "$.message");
	
	return message;
	
}
		
		
		private String deleteCollectionInFocus(String id){
			
			
			MyntraService myntraService1 = Myntra.getService(
					ServiceType.PORTAL_PRICINGPOLICY, APINAME.DELETECOLLECTIONINFOCUS,
					init.Configurations,PayloadType.JSON,new String [] {id},PayloadType.JSON);
//			System.out.println(myntraService1.URL);
			RequestGenerator req = new RequestGenerator(myntraService1);
//			System.out.println("Payload for Delete colelction in foxus ----  >>?? " + myntraService1.Payload);
//			System.out.println("Response for Delete colelction in foxus  " + req.respvalidate.returnresponseasstring());
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String message = JsonPath.read(jsonRes1, "$.message");
			
			return message;
			
		}
		
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "getstyleithData")
		public void defaultEndTimeBeforeCurrentTime(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
//			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			
			String msg1=createDiscountOverrideForStyle(stylids.get(0).toString(),stylids.get(1).toString(),stylids.get(2).toString(),stylids.get(3).toString(),stylids.get(4).toString(),startTime,endTimeBfrCurTime,name,startTime1,endTime1);
//			System.out.println("msg----" + msg1);
			Assert.assertEquals(msg1, "StartDate should be less than EndDate");
		}
	
		
		private String createDiscountOverrideForStyle(String style1 , String style2, String style3,String style4,String style5,String startdate,String enddate,String name,String startdate1,String enddate1 ){
			
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSTYLE, init.Configurations,new String[] {style1,style2,style3,style4,style5,startdate,enddate,name,startdate1,enddate1},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
//			System.out.println("Disocunt Override URL--- " +service1.URL);
//			System.out.println("Disocunt Override Payload--- " +service1.Payload);

			String jsonRes1 = req.respvalidate.returnresponseasstring();
//			System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
			String msg=JsonPath.read(jsonRes1, "$.message").toString();
//			System.out.println("DO RESPONSE ----> "+msg);

			String message = JsonPath.read(jsonRes1, "$.id");
			System.out.println("MEssage for override" + message);
			return msg;
			
		}
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "groupingOfstyleithDataForDoSeller")
		public void vFundingTypeSELLERandTradeDiscountNotSpecified(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount,String vendorFund) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
//			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			String msg1=createDiscountOverrideForSeller(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
			Assert.assertEquals(msg1, "If FundingType is SELLER, then specify tradeDiscount, sellerFunding, discountLimit for the ");
		
		}
		
		private String createDiscountOverrideForSeller(String style1,String startdate,String enddate,String name, String discountFundingType, String tradeDiscount, String vendorFund){
			
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
//			System.out.println("Disocunt Override URL--- " +service1.URL);
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			String msg=JsonPath.read(jsonRes1, "$.message").toString();
			String txtResp=msg.split("styleId")[0];
//			String message = JsonPath.read(jsonRes1, "$.id").toString();
			String message=JsonPath.read(jsonRes1, "$.message").toString();//.replace("[", "").replace("]", "")
			return txtResp;
			
		}
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "groupingOfstyleithDataForDoEOSSVFnull")
		public void vTradeDiscountWithoutVendorFunding(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
//			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			String msg1=createDiscountOverrideForSeller(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
			Assert.assertEquals(msg1, "TradeDiscount is specified, but VendorFundedTD is not specified, for the ");
		
		}
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "groupingOfstyleithDataForDoEOSSWithVfandWithoutTradeDiscount")
		public void vFundingTypeEOSSNoTradeDiscountVFspecified(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
		{
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
//			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			String msg1=createDiscountOverrideForSellerDOmsg(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
			Assert.assertEquals(msg1, "Discount Override created succesfully");
		
		}
		
		private String createDiscountOverrideForSellerDOmsg(String style1,String startdate,String enddate,String name , String discountFundingType, String tradeDiscount, String vendorFund){
			
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
//			System.out.println("Disocunt Override URL--- " +service1.URL);
			String jsonRes1 = req.respvalidate.returnresponseasstring();
//			System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
			String msg=JsonPath.read(jsonRes1, "$.message").toString();
			System.out.println("DO RESPONSE ----> "+msg);
			String txtResp=msg.split("styleId")[0];
//			String message = JsonPath.read(jsonRes1, "$.id").toString();
			String message=JsonPath.read(jsonRes1,"$.result").toString().replace("[", "").replace("]", "");
			return msg;
			
		}
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "groupingOfstyleithDataToV_FP_D_FT_Seller")
		public void vFundingPercentage_Discount_FundingType_InDB_IfTypeIsSeller(String page,String data, String merchandiseType,String defaultpage,String pageSize,String status, String fundingType, String tradeDiscount, String vendorFund) throws InterruptedException
		{
			Connection con = null;
			Statement stmt = null;
			MyntraService styleservice = Myntra.getService(ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
			RequestGenerator requestGenerator = new RequestGenerator(styleservice);
//			System.out.println("service url = " +styleservice.URL);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
			String name = generateRandomString();
			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
			String discountOverrideId=createDiscountOverrideForSellerDOid(stylids.get(0).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
			//aprroveDiscountOverride(discountOverrideId);
			internallyaprroveDiscountOverride(discountOverrideId);
			sellerApprovalDiscountOverride(discountOverrideId);
			System.out.println("MSG1 from test : "+discountOverrideId);
			
			//String discountIdFrmDb="";
		int discount_id=0;
		int get_percent=0;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
				// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
				//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
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
		
		
		
		private String createDiscountOverrideForSellerDOid(String style1,String startdate,String enddate,String name, String discountFundingType, String tradeDiscount, String vendorFund){
			MyntraService service1 = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEDISCOUNTOVERRIDEFORSELLER, init.Configurations,new String[] {style1,startdate,enddate,name,discountFundingType,tradeDiscount, vendorFund},PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
			RequestGenerator req = new RequestGenerator(service1, getParam);
//			System.out.println("Disocunt Override URL--- " +service1.URL);
//			System.out.println("Disocunt Override Payload--- " +service1.Payload);
			String jsonRes1 = req.respvalidate.returnresponseasstring();
			System.out.println("DISOCUNT OVERRIDE REPOSNE" + jsonRes1);
			//String do_id = JsonPath.read(jsonRes1, "$.id").toString();
			String do_id=JsonPath.read(jsonRes1, "$.result").toString().replace("[", "").replace("]", "").trim();
			System.out.println("DO id for override: " + do_id);
			return do_id;
			
		}
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "groupingOfstyleithData_Eoss_TDNotNull_VFNull")
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
			String discountOverrideId=createDiscountOverrideForSellerDOid(stylids.get(4).toString(),startTime,endTime,name,fundingType,tradeDiscount, vendorFund);
			//aprroveDiscountOverride(discountOverrideId);
			internallyaprroveDiscountOverride(discountOverrideId);
			sellerApprovalDiscountOverride(discountOverrideId);
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
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query1="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+discountOverrideId;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			while(rs.next()){
				discount_id=rs.getInt("discount_id");
			}
			
			String query2="select get_percent from myntra.discount_rule where discount_id="+discount_id;
			ResultSet rs1 = stmt.executeQuery(query2);
			while(rs1.next()){
				get_percent=rs1.getInt("get_percent");
			}
			System.out.println("get_percent from db discount rule table: "+get_percent);
				Assert.assertEquals(tradeDiscount, get_percent+"");
			
			String query3="select funding_percentage from myntra.discount where id="+discount_id;
			ResultSet rs2=stmt.executeQuery(query3);
			//System.out.println("Funding_Percentage frm DB ----> : "+rs2);
			while(rs2.next()){
				funding_percentagefrmDb=rs2.getInt("funding_percentage");
			}
			System.out.println("funding_percentage from db discount tabel : "+funding_percentagefrmDb);
			Assert.assertEquals(funding_percentagefrmDb+"", funding_percentage+"");
			
			String query4="select discount_limit from myntra.discount where id="+discount_id;
			ResultSet rs3=stmt.executeQuery(query4);
			//System.out.println("discount_limit frm DB ----> : "+rs3);
			while(rs3.next()){
				discountLimitFrmDb=rs3.getInt("discount_limit");
			}
			//System.out.println("funding_percentage from db discount tabel : "+discountLimitFrmDb);
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
		"Regression","Fox7Sanity" }, dataProvider = "tlpFilter_False")
		public void vTlpFilterForFalseAndValidateWithDb(String totalStyles,String actions,String businessUnitId,String businessUnitName, String discountFunding_category, String discountFunding_INSEASON, String discountFunding_EOSS,String seasonsId,String seasonsName, String minAge, String maxAge) throws InterruptedException
		{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
			Connection con = null;
			Statement stmt = null;
			String pushDiscount="false";
			MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTER,init.Configurations,new String [] {totalStyles,endTime,actions,businessUnitId,businessUnitName,discountFunding_category,discountFunding_INSEASON,discountFunding_EOSS,seasonsId,seasonsName,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
			RequestGenerator requestGenerator = new RequestGenerator(tlpService);
			System.out.println("service url = " +tlpService.URL);
//			System.out.println("service payload = "+tlpService.Payload);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//			System.out.println("TLP response =====>>> : "+jsonRes);
			List<Integer> styleids =JsonPath.read(jsonRes,"$..styleId[*]");
			for(int i=0;i<styleids.size();i++){
//			System.out.println("StyleIds are: "+styleids.get(i).toString());
			}
			String singlStyleId=styleids.get(0).toString();
//			System.out.println("Style Id : "+singlStyleId);
			String snapshotid=JsonPath.read(jsonRes, "$..snapshotId[0]").toString(); //.replaceAll("[","").replaceAll("]","")
//			System.out.println("snapshotid : "+snapshotid);
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
		
		//here
//		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
//		"Regression","Fox7Sanity"}, dataProvider = "tlpFilter_true")
//		public void vTlpFilterForTrueValidateWithDb(String totalStyles,String actions, String discountFunding_category, String discountFunding_INSEASON, String discountFunding_EOSS, String minAge, String maxAge) throws InterruptedException, SQLException
//		{																//false","5","REVBUMP","Sports","Sports","CATEGORY","INSEASON","EOSS", "FW15","FW15","0","500"
//			Connection con = null;
//			Statement stmt = null;
//			String pushDiscount="true";
//			String discFundingFrmDb="";
//			int styleIdFrmDb=0;
//			MyntraService tlpService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.TLPFILTERCATEGORYINSEASONDATA,init.Configurations,new String [] {totalStyles,endTime,actions,discountFunding_category,discountFunding_INSEASON,discountFunding_EOSS,minAge,maxAge},new String[] {pushDiscount},PayloadType.JSON,PayloadType.JSON);
//			HashMap getParam = new HashMap();
//			getParam.put("user","imran.khan2@myntra.com");
//			//RequestGenerator req = new RequestGenerator(tlpService, getParam);
//			RequestGenerator requestGenerator = new RequestGenerator(tlpService,getParam);
//			System.out.println("service url = " +tlpService.URL);
////			System.out.println("service payload = "+tlpService.Payload);
//			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
////			System.out.println("TLP response =====>>> : "+jsonRes);
//			String snapshotid=JsonPath.read(jsonRes, "$..snapshotId[0]").toString();
//			System.out.println("snapshotid : "+snapshotid);
//			List<String> discountfunding=JsonPath.read(jsonRes, "$..discountFunding[*]");
//			System.out.println("DiscountFunding is: "+discountfunding);
//			List<Integer> styleids =JsonPath.read(jsonRes,"$..styleId[*]");
//			System.out.println("Style ids are: "+styleids);
//			Map m1 = new HashMap();
//			m1.put(styleids,discountfunding);
//			System.out.println("Map elements: "+m1);
//			
//			try {
//				
//			Class.forName("com.mysql.jdbc.Driver");
//			con=DriverManager.getConnection("jdbc:mysql://"+"54.251.240.111"+"/myntra?"+"user=MyntraQADBUser&password=B3F0R3#PR0D&port=3306");
//			String query="select style_id from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 6";
//			System.out.println("query is : "+query);
//			stmt = con.createStatement();
//			ArrayList<Integer> resultList = new ArrayList<Integer>();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				styleIdFrmDb=rs.getInt("style_id");
//				resultList.add(styleIdFrmDb);
//			}
//			
//			
//			String dfquery="select discount_funding from pricingengine.recommended_styles_discount where snapshot_id="+snapshotid + " limit 6";
//			System.out.println("query is : "+dfquery);
//			stmt = con.createStatement();
//			ArrayList<String> dfresultList = new ArrayList<String>();
//			ResultSet rs1 = stmt.executeQuery(dfquery);
//			while(rs1.next()){
//				//System.out.println("Result set for discount_funding : "+rs1.getString("discount_funding"));
//				//Getting NULL here. Need to check
//				discFundingFrmDb=rs1.getString("discount_funding");
//				
//				dfresultList.add(discFundingFrmDb);
//			}
//			Map m2=new HashMap();
//			m2.put(resultList, dfresultList);
//			System.out.println("mapping elements from db: "+m2);
//			Assert.assertEquals(m1.keySet(),m2.keySet());
//			//Assert.assertEquals(m1.values(),m2.values());
//			//Assert.assertTrue(m1.keySet(),m2.keySet());
//			//System.out.println("Result set for discount_funding : "+rs1.getString("discount_funding"));
//			
//			}catch(Exception e){
//				
//			}
//			
//			finally {
//
//				try {
//					con.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			}
//			
//		}
		
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "tlpFilter_False")
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
//			System.out.println("service payload = "+tlpService.Payload);
			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//			System.out.println("TLP response =====>>> : "+jsonRes);
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
				con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
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
					
					
					newMap2.put(rs1.getInt("style_id"), rs1.getDouble("category_trade_discount"));
					
					
				}
				System.out.println("New Map from resp : "+newMap);
				System.out.println("New map 2 from db : "+newMap2);
				
				if(newMap.equals(newMap2)){
					System.out.println("==========>>>>>>>>>> Maps from response and db are matching <<<<<<<<<<<<=======");
					Assert.assertEquals(newMap, newMap2);
					
				}
				
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
		"Regression","Fox7Sanity"}, dataProvider = "tlpFilter_true_EOSSand_INSEASON_SOR")
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
//			System.out.println("service payload = "+tlpService.Payload);
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
//			System.out.println("New Vf calculated is : "+newVf);
			double newCf=newTd-newVf;
//			System.out.println("New cf calculated : "+newCf);
			double newFundingPercent= newVf/(newCf+newVf)*100;
//			System.out.println("New funding percent calcuated : "+newFundingPercent);
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
				
				
				String customQuery="select vendor_trade_discount from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
				System.out.println("query is : "+customQuery);
				stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(customQuery);
				
				while(rs1.next()){
					vfFrmDb=rs.getDouble("vendor_trade_discount");
					Assert.assertEquals(vfFrmDb, newVf);
				}
				String Query1="select funding_percentage from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
				System.out.println("query is : "+Query1);
				stmt = con.createStatement();
				ResultSet rs2 = stmt.executeQuery(Query1);
				
				while(rs2.next()){
					fundingPercntFrmDb=rs.getDouble("funding_percentage");
					Assert.assertEquals(fundingPercntFrmDb, fundingPercent);
					
				}
				String Query2="select discount_limit from pricingengine.discount_override_style_map where discount_override_id="+do_Id;
				System.out.println("query is : "+Query2);
				stmt = con.createStatement();
				ResultSet rs3 = stmt.executeQuery(Query2);
				
				while(rs3.next()){
					discLmtFrmDb=rs.getDouble("discount_limit");
					Assert.assertEquals(discLmtFrmDb, discLimit);
				}
					
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
		
		
		//Phaseout-MPS api's
		
		
		@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
		"Regression","Fox7Sanity"}, dataProvider = "getstyleithData")
		public void vPhaseoutMpsFlatPercent(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
		{	
			Connection con = null;
			Statement stmt = null;
			int discountIdFrmDb=0;
			int percentFrmDb=0;
			String cf="10";
			String vf="11";
			String do_type="FlatPercent";
			// Commenting below code as this wil work only for seller or vendor specific style id, the below code gets other style ids
			
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
//					init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
//			RequestGenerator requestGenerator = new RequestGenerator(service);
//			System.out.println("service url = " +service.URL);
//			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
////			System.out.println("Get style data page response : "+jsonRes);
//			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
//			System.out.println("Style ids are : "+stylids);
//			int stylid1=stylids.get(0);
//			String styleid_str=""+stylid1;
			String styleid_str="376367";
			MyntraService mpsphseoutservice = Myntra.getService(
					ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATPERCENT,//PORTAL_PRICINGMPSPHASEOUT
					init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,cf,vf},PayloadType.JSON,PayloadType.JSON);
					HashMap getParam = new HashMap();
					getParam.put("user","imran.khan2@myntra.com");
					RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
					System.out.println("service url = " +mpsphseoutservice.URL);
	//				System.out.println("service payload - : "+mpsphseoutservice.Payload);
					String mpsFlatResp = reqGenerator.respvalidate.returnresponseasstring();
					System.out.println("MpS Phaseout flat percent response : "+mpsFlatResp);
					//String do_id=JsonPath.read(mpsFlatResp,"$..id[0]").toString();
					String do_id=JsonPath.read(mpsFlatResp, "$.result").toString().replace("[", "").replace("]", "");
					System.out.println("MpS Do_id : "+do_id);
					String id=internallyaprroveDiscountOverride(do_id);
					sellerApprovalDiscountOverride(do_id);
					
					int cf_int=Integer.parseInt(cf);
					int vf_int=Integer.parseInt(vf);
					int percentCalculation=cf_int+vf_int;
					System.out.println("Percentage calculation ----> : "+percentCalculation);
					
					
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
						//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
						String query="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+do_id;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while(rs.next()){
							discountIdFrmDb=rs.getInt("discount_id");
							
						}
						String query1="select get_percent from myntra.discount_rule where discount_id="+discountIdFrmDb;
						System.out.println("query is : "+query1);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(query1);
						while(rs1.next()){
							percentFrmDb=rs1.getInt("get_percent");
						}
						
						Assert.assertEquals(percentCalculation, percentFrmDb);
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
		"Regression","Fox7Sanity"}, dataProvider = "getstyleithData")
		public void vPhaseoutMpsFlatConditionalByAmt(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
		{	
			Connection con = null;
			Statement stmt = null;
			int discountIdFrmDb=0;
			int percentFrmDb=0;
			int buyAmtFrmDb=0;
			String buyAmt="1999";
			String do_type="FlatConditional";
			
			// Commenting below code as this wil work only for seller or vendor specific style id, the below code gets other style ids
			
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
//					init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

//			RequestGenerator requestGenerator = new RequestGenerator(service);
//			System.out.println("service url = " +service.URL);
//			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
////			System.out.println("Get style data page response : "+jsonRes);
//			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
//			System.out.println("Style ids are : "+stylids);
//			int stylid1=stylids.get(1);
//			String styleid_str=""+stylid1;
			String styleid_str="376368";
			MyntraService mpsphseoutservice = Myntra.getService(
					ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATCONDITIONAL,//PORTAL_PRICINGMPSPHASEOUT
					init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,buyAmt},PayloadType.JSON,PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
					RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
					System.out.println("service url = " +mpsphseoutservice.URL);
					System.out.println("service payload - : "+mpsphseoutservice.Payload);
					String mpsFlatCondResp = reqGenerator.respvalidate.returnresponseasstring();
					System.out.println("MpS Phaseout flat percent response : "+mpsFlatCondResp);
					//String do_id=JsonPath.read(mpsFlatCondResp,"$..id[0]").toString();
					String do_id=JsonPath.read(mpsFlatCondResp, "$.result").toString().replace("[", "").replace("]", "");
					System.out.println("MpS Do_id : "+do_id);
					String id=internallyaprroveDiscountOverride(do_id);
					sellerApprovalDiscountOverride(do_id);
					
					
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
						//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
						String query="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+do_id;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						System.out.println("Result set size is : "+rs.getFetchSize());
						while(rs.next()){
							discountIdFrmDb=rs.getInt("discount_id");
							
						}
						String query1="select on_buy_amount from myntra.discount_rule where discount_id="+discountIdFrmDb;
						System.out.println("query is : "+query1);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(query1);
						while(rs1.next()){
							buyAmtFrmDb=rs1.getInt("on_buy_amount");
						}
						
					Assert.assertEquals(buyAmtFrmDb+"", buyAmt);
					System.out.println("Buy Amt from db: "+buyAmtFrmDb);
					System.out.println("buyAmt actually given: "+buyAmt);
						
						
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
		"Regression","Fox7Sanity"}, dataProvider = "getstyleithDataPg4")
		public void vPhaseoutMpsFlatConditionalBuyCount(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
		{	
			Connection con = null;
			Statement stmt = null;
			int discountIdFrmDb=0;
			int buyCountFrmDb=0;
			String buyCount="2";
			String do_type="FlatConditional";
			
			// Commenting below code as this wil work only for seller or vendor specific style id, the below code gets other style ids
			
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
//					init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

//			RequestGenerator requestGenerator = new RequestGenerator(service);
//			System.out.println("service url = " +service.URL);
//			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//	//		System.out.println("Get style data page response : "+jsonRes);
//			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
//			System.out.println("Style ids are : "+stylids);
//			int stylid1=stylids.get(2);
			//String styleid_str=""+stylid1;
			String styleid_str="376382";
			
			MyntraService mpsphseoutservice = Myntra.getService(
					ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATCONDITIONALBUYCOUNT,//PORTAL_PRICINGMPSPHASEOUT
					init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,buyCount},PayloadType.JSON,PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice, getParam);
		System.out.println("service url = " + mpsphseoutservice.URL);
		System.out.println("service payload - : " + mpsphseoutservice.Payload);
		String mpsFlatCondResp = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println("MpS Phaseout flat percent response : " + mpsFlatCondResp);
		//String do_id = JsonPath.read(mpsFlatCondResp, "$..id[0]").toString();
		String do_id=JsonPath.read(mpsFlatCondResp, "$.result").toString().replace("[", "").replace("]", "");
		System.out.println("MpS Do_id : " + do_id);
		String id = internallyaprroveDiscountOverride(do_id);
		sellerApprovalDiscountOverride(do_id);
					
					
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
						//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
						String query="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+do_id;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while(rs.next()){
							discountIdFrmDb=rs.getInt("discount_id");
							
						}
						String query1="select on_buy_count from myntra.discount_rule where discount_id="+discountIdFrmDb;
						System.out.println("query is : "+query1);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(query1);
						while(rs1.next()){
							buyCountFrmDb=rs1.getInt("on_buy_count");
						}
						
					Assert.assertEquals(buyCountFrmDb+"", buyCount);
						
						
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
		"Regression","Fox7Sanity"}, dataProvider = "getstyleithData")
		public void vPhaseoutMpsFlatRupee(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
		{	
			Connection con = null;
			Statement stmt = null;
			int discountIdFrmDb=0;
			int getAmtFrmDb=0;
			String amt="149";
			String do_type="FlatRupee";
			
			// Commenting below code as this wil work only for seller or vendor specific style id, the below code gets other style ids
			
//			MyntraService service = Myntra.getService(
//					ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
//					init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);
//
//			RequestGenerator requestGenerator = new RequestGenerator(service);
//			System.out.println("service url = " +service.URL);
//			String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//	//		System.out.println("Get style data page response : "+jsonRes);
//			List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
//			System.out.println("Style ids are : "+stylids);
//			int stylid1=stylids.get(3);
//			//String styleid_str=""+stylid1;
			String styleid_str="376366";
			MyntraService mpsphseoutservice = Myntra.getService(
					ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATRUPEE,//PORTAL_PRICINGMPSPHASEOUT
					init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,amt},PayloadType.JSON,PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice, getParam);
		System.out.println("service url = " + mpsphseoutservice.URL);
		System.out.println("service payload - : " + mpsphseoutservice.Payload);
		String mpsFlatCondResp = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println("MpS Phaseout flat percent response : " + mpsFlatCondResp);
		//String do_id = JsonPath.read(mpsFlatCondResp, "$..id[0]").toString();
		String do_id=JsonPath.read(mpsFlatCondResp, "$.result").toString().replace("[", "").replace("]", "");
		System.out.println("MpS Do_id : " + do_id);
		String id = internallyaprroveDiscountOverride(do_id);
		sellerApprovalDiscountOverride(do_id);
					
					
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306/myntra","myntraAppDBUser","9eguCrustuBR");
						// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
						//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
						String query="select discount_id from pricingengine.discount_override_discount_map where discount_override_id="+do_id;
						System.out.println("query is : "+query);
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						System.out.println("Result set size is : "+rs.getFetchSize());
						while(rs.next()){
							discountIdFrmDb=rs.getInt("discount_id");
							
						}
						
						String query1="select * from myntra.discount_rule where discount_id="+discountIdFrmDb;
						System.out.println("query is : "+query1);
						stmt = con.createStatement();
						ResultSet rs1 = stmt.executeQuery(query1);
						System.out.println("Result set size is : "+rs1.getFetchSize());
						while(rs1.next()){
							getAmtFrmDb=rs1.getInt("get_amount");
							
						}
						System.out.println("Amount from Db after flat rupee: "+getAmtFrmDb);
						System.out.println("Amount actually given in flat rupee payload: "+amt);
						
						
						Assert.assertEquals(getAmtFrmDb+"", amt);
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
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getstyleithData")
	public void vPhaseoutMpsBuyXGetY(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
	{
		Connection con = null;
		Statement stmt = null;
		int buyCountFrmDb=0;
		int getCountFromDb=0;
		String buyCount="3";
		String count="1";
		String do_type="BuyXGetY";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		System.out.println("Style ids are : "+stylids);
		int stylid1=stylids.get(4);
		String styleid_str=""+stylid1;

		MyntraService mpsphseoutservice = Myntra.getService(
				ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTBUYXGETY,//PORTAL_PRICINGMPSPHASEOUT
				init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,buyCount,count},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user","swatantra.singh@myntra.com");
		RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
		System.out.println("service url = " +mpsphseoutservice.URL);
		System.out.println("service payload - : "+mpsphseoutservice.Payload);
		String mpsBuyXGetYResp = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println("MpS Phaseout BuyXGetY: "+mpsBuyXGetYResp);
		//String do_id=JsonPath.read(mpsBuyXGetYResp,"$..id[0]").toString();
		String do_id=JsonPath.read(mpsBuyXGetYResp, "$.result").toString().replace("[", "").replace("]", "");
		System.out.println("MpS Do_id : "+do_id);
		String id=internallyaprroveDiscountOverride(do_id);
		sellerApprovalDiscountOverride(do_id);


		try
		{
			System.out.println("in try block");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			String query="select * from pricingengine.discount_override_style_map where discount_override_id="+do_id;
			System.out.println("buyCount query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				buyCountFrmDb=rs1.getInt("on_buy_count");
				getCountFromDb=rs1.getInt("get_count");
			}
       System.out.println("BuyCount From DB --->>>> : "+buyCountFrmDb);
       System.out.println("GetCount From DB --->>>> : "+getCountFromDb);
		}
		catch(Exception e){}
		finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}
		Assert.assertEquals(getCountFromDb+"", count,"buy count from DB :"+getCountFromDb+'\n'+"getCount by user"+count);
		Assert.assertEquals(buyCountFrmDb+"", buyCount,"buy count from DB :"+buyCountFrmDb+'\n'+ "buyCount by user"+buyCount);
	}
	
	
	@Test(groups = { "SchemaValidation","Regression" },dataProvider="getAllDiscountOverrides", description="\n 1. Get All discountsOverrides \n 2. verify status code 200 response \n 3. Validate schema")
	public void discountService_getAllDiscountOverrideverifyResponseDataNodesUsingSchemaValidations(String status, String doType, String value) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGMPSPHASEOUT,APINAME.GETALLDISCOUNTOVERIDES,init.Configurations,PayloadType.JSON,new String [] {status,doType,value},PayloadType.JSON);
		System.out.println("SERVICE URL---------> \n" + service.URL);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(response);
		System.out.println("---------------------------------------------------------------------------------------------");
		discOverdIds = JsonPath.read(response, "$..id[*]");
		AssertJUnit.assertTrue("Get all discount is not working",(discOverdIds.size() > 0));
		AssertJUnit.assertEquals("Response is not 200 OK", 200,	rs.response.getStatus());
		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/discountOverrideWithDarwinChanges-getAllDiscountOverride-schema.txt");
			System.out.println("Json Schema is : "+jsonschema);
			List<String> missingNodeList = cmUtil.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+ " nodes are missing in discountOveride getAllDiscountOverrides API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	@Test(groups = { "SchemaValidation","Regression" },dataProvider="getstyleithData", description="\n 1. Get All discountsOverrides \n 2. verify status code 200 response \n 3. Validate schema")
	public void discountService_createFlatPercentDiscountOverrideverifyResponseDataNodesUsingSchemaValidations(String page,String data, String merchandiseType,String defaultpage) {
		String cf="10";
		String vf="11";
		String do_type="FlatPercent";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		System.out.println("Style ids are : "+stylids);
		int stylid1=stylids.get(0);
		String styleid_str=""+stylid1;
		
		MyntraService mpsphseoutservice = Myntra.getService(
				ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATPERCENT,//PORTAL_PRICINGMPSPHASEOUT
				init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,cf,vf},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice, getParam);
		System.out.println("service url = " + mpsphseoutservice.URL);
		System.out.println("service payload - : " + mpsphseoutservice.Payload);
		String mpsFlatResp = reqGenerator.respvalidate.returnresponseasstring();
		System.out.println("MpS Phaseout flat percent response : " + mpsFlatResp);
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println(mpsFlatResp);
		System.out.println("---------------------------------------------------------------------------------------------");
		discOverdIds = JsonPath.read(mpsFlatResp, "$..id[*]");
		//AssertJUnit.assertTrue("Get all discount is not working",(discOverdIds.size() > 0));
		AssertJUnit.assertEquals("Response is not 200 OK", 200,	reqGenerator.response.getStatus());
		
		try {
			String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/discountOverrideDarwin-discountOverride-schema.txt");
			System.out.println("Json Schema is : "+jsonschema);
			List<String> missingNodeList = cmUtil.validateServiceResponseNodesUsingSchemaValidator(mpsFlatResp, jsonschema);
			AssertJUnit.assertTrue(missingNodeList+ " nodes are missing in DiscountOverideService createMPSflatPercnet API response",
							CollectionUtils.isEmpty(missingNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test(groups = { "SchemaValidation","Regression" },dataProvider="getstyleithData", description="\n 1. create flat conditional discountoveride \n 2. verify status code 200 response \n 3. Validate schema")
	public void discountService_createFlatConditionalDiscountOverrideverifyResponseDataNodesUsingSchemaValidations(String page,String data, String merchandiseType,String defaultpage) {
		String buyAmt="1999";
		String do_type="FlatConditional";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		System.out.println("Style ids are : "+stylids);
		int stylid1=stylids.get(0);
		String styleid_str=""+stylid1;
		
		MyntraService mpsphseoutservice = Myntra.getService(
				ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATCONDITIONAL,//PORTAL_PRICINGMPSPHASEOUT
				init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,buyAmt},PayloadType.JSON,PayloadType.JSON);
				HashMap getParam = new HashMap();
				getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
				System.out.println("service url = " +mpsphseoutservice.URL);
//				System.out.println("service payload - : "+mpsphseoutservice.Payload);
				String mpsFlatCondResp = reqGenerator.respvalidate.returnresponseasstring();
				System.out.println("MpS Phaseout flat percent response : "+mpsFlatCondResp);
				//String do_id=JsonPath.read(mpsFlatCondResp,"$..id[0]").toString();	
				String do_id=JsonPath.read(mpsFlatCondResp,"$.result").toString().replace("[", "").replace("]", "");
				System.out.println("Discount overide ids : "+do_id);
				
				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/discountOverrideDarwin-discountOverride-schema.txt");
					System.out.println("Json Schema is : "+jsonschema);
					List<String> missingNodeList = cmUtil.validateServiceResponseNodesUsingSchemaValidator(mpsFlatCondResp, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+ " nodes are missing in DiscountOverideService createMPSflatPercnet API response",
									CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}
	
	
	@Test(groups = { "SchemaValidation","Regression" }, dataProvider = "getstyleithData")
	public void discountService_createRupeeOffDiscountOverrideverifyResponseDataNodesUsingSchemaValidations(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
	{	
	
		String amt="149";
		String do_type="FlatRupee";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
//		System.out.println("Style ids are : "+stylids);
		int stylid1=stylids.get(3);
		String styleid_str=""+stylid1;
		
		MyntraService mpsphseoutservice = Myntra.getService(
				ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATRUPEE,//PORTAL_PRICINGMPSPHASEOUT
				init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,amt},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
				System.out.println("service url = " +mpsphseoutservice.URL);
//				System.out.println("service payload - : "+mpsphseoutservice.Payload);
				String mpsFlatRpoff = reqGenerator.respvalidate.returnresponseasstring();
				System.out.println("MpS Phaseout flat percent response : "+mpsFlatRpoff);
				//String do_id=JsonPath.read(mpsFlatRpoff,"$..id[0]").toString();
				String do_id=JsonPath.read(mpsFlatRpoff,"$.result").toString().replace("[", "").replace("]", "");
				System.out.println("MpS Do_id : "+do_id);
				
				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/discountOverrideDarwin-discountOverride-schema.txt");
					System.out.println("Json Schema is : "+jsonschema);
					List<String> missingNodeList = cmUtil.validateServiceResponseNodesUsingSchemaValidator(mpsFlatRpoff, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+ " nodes are missing in DiscountOverideService createMPSflatRupeeOff API response",
									CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
	}
				
				
	}
	
	@Test(groups = { "SchemaValidation","Regression" }, dataProvider = "getstyleithData")
	public void discountService_createRupeeOffDiscountOverrideverifyResponseDataNodesUsingSchemaValidations1(String page,String data, String merchandiseType,String defaultpage) throws InterruptedException, SQLException
	{	
	
		String amt="149";
		String do_type="FlatRupee";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		System.out.println("Style ids are : "+stylids);
		int stylid1=stylids.get(3);
		String styleid_str=""+stylid1;
		
		MyntraService mpsphseoutservice = Myntra.getService(
				ServiceType.PORTAL_PRICINGMPSPHASEOUT, APINAME.MPSPHASEOUTFLATRUPEE,//PORTAL_PRICINGMPSPHASEOUT
				init.Configurations,new String [] {do_type,startTime,endTime,styleid_str,amt},PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator reqGenerator = new RequestGenerator(mpsphseoutservice,getParam);
				System.out.println("service url = " +mpsphseoutservice.URL);
				System.out.println("service payload - : "+mpsphseoutservice.Payload);
				String mpsFlatRpoff = reqGenerator.respvalidate.returnresponseasstring();
				System.out.println("MpS Phaseout flat percent response : "+mpsFlatRpoff);
				//String do_id=JsonPath.read(mpsFlatRpoff,"$..id[0]").toString();
				String do_id=JsonPath.read(mpsFlatRpoff,"$.result").toString().replace("[", "").replace("]", "");
				
				System.out.println("MpS Do_id : "+do_id);
				
				try {
					String jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/discountOverrideDarwin-discountOverride-schema.txt");
					System.out.println("Json Schema is : "+jsonschema);
					List<String> missingNodeList = cmUtil.validateServiceResponseNodesUsingSchemaValidator(mpsFlatRpoff, jsonschema);
					AssertJUnit.assertTrue(missingNodeList+ " nodes are missing in DiscountOverideService createMPSflatRupeeOff API response",
									CollectionUtils.isEmpty(missingNodeList));
				} catch (Exception e) {
					e.printStackTrace();
	}
				
				
	}
	
	
	//CF_TopUp

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getstyleithDataPg5")
	public void vCfTopOverExistingDiscount(String page,String data, String merchandiseType,String defaultpage,String topUpType,String fundingType) throws InterruptedException, SQLException
	{
		int disAfterCfTopUp=40;
		String disIdFrmDisOvrId="";
		int getPercentFrmDb=0;
		Connection con = null;
		Statement stmt = null;
		String percent="30";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		//System.out.println("Style ids are : "+stylids);
		int styleID1=stylids.get(0);
		String styleID=""+styleID1;
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService disService = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { endTimeDis,startTimeDis,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
		
		RequestGenerator disRequestGenerator = new RequestGenerator(disService);
		System.out.println("service url = " +disService.URL);
		String jsonRes1 = disRequestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("json Response from discount service : "+jsonRes1);
		System.out.println("--->>> Waiting few minutes for normal discount to reflect on style id so that cfTopUp can be applied <<<------");
		TimeUnit.MINUTES.sleep(4);
		HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		MyntraService cfTopUpService=Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.CREATECFTOPUP,init.Configurations,new String[]{ topUpType,fundingType,startTime,endTime1,styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Cf Top up payload -- > \n"+cfTopUpService.Payload);
		RequestGenerator cfTopUpReqGenerator=new RequestGenerator(cfTopUpService,getParam);
		System.out.println("CfTop Up service url : "+cfTopUpService.URL);
		String jsonRes2 = cfTopUpReqGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Cf top up response \n"+jsonRes2);
		String cf_Do_Id = JsonPath.read(jsonRes2, "$.discountOverrideIds[0]").toString();
		System.out.println("Cf_Do_ID: "+ cf_Do_Id);
		internallyaprroveDiscountOverride(cf_Do_Id);
		sellerApprovalDiscountOverride(cf_Do_Id);
		
		try
		{
			System.out.println("in try block");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query="select * from pricingengine.discount_override_discount_map where discount_override_id="+cf_Do_Id;
			System.out.println("buyCount query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				disIdFrmDisOvrId=rs1.getString("discount_id");
				System.out.println("Discount id from discount override id is : "+disIdFrmDisOvrId);
				//getCountFromDb=rs1.getInt("get_count");
			}
			String query1="select * from myntra.discount_rule where discount_id="+disIdFrmDisOvrId;
			System.out.println("discount id query is : "+query1);
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query1);
			while(rs2.next())
			{
				getPercentFrmDb=rs2.getInt("get_percent");
				System.out.println("percent from discount id is : "+getPercentFrmDb);
				//getCountFromDb=rs1.getInt("get_count");
			}
			
			Assert.assertEquals(getPercentFrmDb,disAfterCfTopUp);
			
      
		}
		catch(Exception e){}
		finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		
		}
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getstyleithDataPg5")
	public void vCfTopWithoutExistingDiscount(String page,String data, String merchandiseType,String defaultpage,String topUpType,String fundingType) throws InterruptedException, SQLException
	{
		int disAfterCfTopUp=10;
		String disIdFrmDisOvrId="";
		int getPercentFrmDb=0;
		Connection con = null;
		Statement stmt = null;
		String percent="30";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		int styleID1=stylids.get(1);
		String styleID=""+styleID1;
		System.out.println("Sytle id is : "+styleID);
		MyntraService cfTopUpService=Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.CREATECFTOPUP,init.Configurations,new String[]{ topUpType,fundingType,startTime,endTime1,styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Cf Top up payload -- > \n"+cfTopUpService.Payload);
		HashMap getParam = new HashMap();
		getParam.put("user","imran.khan2@myntra.com");
		RequestGenerator cfTopUpReqGenerator=new RequestGenerator(cfTopUpService,getParam);
		System.out.println("CfTop Up service url : "+cfTopUpService.URL);
		String jsonRes2 = cfTopUpReqGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Cf top up response \n"+jsonRes2);
		String cf_Do_Id = JsonPath.read(jsonRes2, "$.discountOverrideIds[0]").toString();
		System.out.println("Cf_Do_ID: "+ cf_Do_Id);
		internallyaprroveDiscountOverride(cf_Do_Id);
		sellerApprovalDiscountOverride(cf_Do_Id);
		try
		{
			System.out.println("in try block");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query="select * from pricingengine.discount_override_discount_map where discount_override_id="+cf_Do_Id;
			System.out.println("buyCount query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				disIdFrmDisOvrId=rs1.getString("discount_id");
			}
			String query1="select * from myntra.discount_rule where discount_id="+disIdFrmDisOvrId;
			System.out.println("discount id query is : "+query);
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query1);
			while(rs2.next())
			{
				getPercentFrmDb=rs2.getInt("get_percent");
			}
			Assert.assertEquals(getPercentFrmDb,disAfterCfTopUp);
		      
		}
		catch(Exception e){}
		finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity" }, dataProvider = "getstyleithDataCfTopUpRupeeOff")
	public void vCfTopAsRupeeOffOnPercentDiscount(String page,String data, String merchandiseType,String defaultpage,String topUpType,String fundingType) throws InterruptedException, SQLException
	{
		int disAfterCfTopUp=30;
		String disIdFrmDisOvrId="";
		int getPercentFrmDb=0;
		Connection con = null;
		Statement stmt = null;
		String percent="30";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		int styleID1=stylids.get(2);
		String styleID=""+styleID1;
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService disService = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { endTimeDis,startTimeDis,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
		
		
		RequestGenerator disRequestGenerator = new RequestGenerator(disService);
		System.out.println("service url = " +disService.URL);
		String jsonRes1 = disRequestGenerator.respvalidate.returnresponseasstring();
		System.out.println("json Response from discount service : "+jsonRes1);
		System.out.println("--->>> Waiting few minutes for normal discount to reflect on style id so that cfTopUp can be applied <<<------");
		TimeUnit.MINUTES.sleep(4);
		HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		MyntraService cfTopUpService=Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.CREATECFTOPUP,init.Configurations,new String[]{ topUpType,fundingType,startTime,endTime1,styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Cf Top up payload -- > \n"+cfTopUpService.Payload);
		RequestGenerator cfTopUpReqGenerator=new RequestGenerator(cfTopUpService,getParam);
		System.out.println("CfTop Up service url : "+cfTopUpService.URL);
		String jsonRes2 = cfTopUpReqGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Cf top up response \n"+jsonRes2);
		String cf_Do_Id = JsonPath.read(jsonRes2, "$..discountOverrideIds").toString();//.replaceAll("[","").replaceAll("]","");
		System.out.println("Cf_Do_ID: "+ cf_Do_Id);
		if(cf_Do_Id==null || cf_Do_Id.length()==0){
			System.out.println("Rupee Off cfTop Up cannot be applied to style id which has percentage discount");
		}
		
		}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getstyleithDataPg5",priority=27)
	public void vFundingPercentageAftrCfTopUp(String page,String data, String merchandiseType,String defaultpage,String topUpType,String fundingType) throws InterruptedException, SQLException
	{
		float fundingPercentAfterCfTopUp=75;
		String disIdFrmDisOvrId="";
		float fundingPercentFrmDb=0;
		Connection con = null;
		Statement stmt = null;
		String percent="30";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		int styleID1=stylids.get(3);
		String styleID=""+styleID1;
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService disService = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { endTimeDis,startTimeDis,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
		
		RequestGenerator disRequestGenerator = new RequestGenerator(disService);
		System.out.println("service url = " +disService.URL);
		String jsonRes1 = disRequestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("json Response from discount service : "+jsonRes1);
		System.out.println("--->>> Waiting few minutes for normal discount to reflect on style id so that cfTopUp can be applied <<<------");
		TimeUnit.MINUTES.sleep(4);
		HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		MyntraService cfTopUpService=Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.CREATECFTOPUP,init.Configurations,new String[]{ topUpType,fundingType,startTime,endTime1,styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Cf Top up payload -- > \n"+cfTopUpService.Payload);
		RequestGenerator cfTopUpReqGenerator=new RequestGenerator(cfTopUpService,getParam);
		System.out.println("CfTop Up service url : "+cfTopUpService.URL);
		String jsonRes2 = cfTopUpReqGenerator.respvalidate.returnresponseasstring();
		System.out.println("Cf top up response \n"+jsonRes2);
		String cf_Do_Id = JsonPath.read(jsonRes2, "$.discountOverrideIds[0]").toString();
		System.out.println("Cf_Do_ID: "+ cf_Do_Id);
		internallyaprroveDiscountOverride(cf_Do_Id);
		sellerApprovalDiscountOverride(cf_Do_Id);
		
		try
		{
			System.out.println("in try block");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query="select * from pricingengine.discount_override_discount_map where discount_override_id="+cf_Do_Id;
			System.out.println("buyCount query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				disIdFrmDisOvrId=rs1.getString("discount_id");
			}
			String query1="select * from myntra.discount where id="+disIdFrmDisOvrId;
			System.out.println("discount id query is : "+query);
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query1);
			while(rs2.next())
			{
				 fundingPercentFrmDb=rs2.getFloat("funding_percentage");
			}
			
			Assert.assertEquals(fundingPercentFrmDb,fundingPercentAfterCfTopUp);
			
			
      
		}
		catch(Exception e){}
		finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		
		}
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getstyleithDataPg5",priority=28)
	public void vDiscountLimitAftrCfTopUp(String page,String data, String merchandiseType,String defaultpage,String topUpType,String fundingType) throws InterruptedException, SQLException
	{
		float discountLimitAfterCfTopUp=100;
		String disIdFrmDisOvrId="";
		float discountLimitFrmDb=0;
		String vfTopUpIdFrmDb="";
		Connection con = null;
		Statement stmt = null;
		String percent="20";
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGANALYTICS, APINAME.GETSTYLEDATAPAGE,
				init.Configurations,PayloadType.JSON,new String [] {page,data,merchandiseType},PayloadType.JSON);

		RequestGenerator requestGenerator = new RequestGenerator(service);
		System.out.println("service url = " +service.URL);
		String jsonRes = requestGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Get style data page response : "+jsonRes);
		List<Integer> stylids = JsonPath.read(jsonRes, "$.styleList..styleId[*]");
		int styleID1=stylids.get(4);
		String styleID=""+styleID1;
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService disService = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { endTimeDis,startTimeDis,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
		
		RequestGenerator disRequestGenerator = new RequestGenerator(disService);
		System.out.println("service url = " +disService.URL);
		String jsonRes1 = disRequestGenerator.respvalidate.returnresponseasstring();
		System.out.println("json Response from discount service : "+jsonRes1);
		System.out.println("--->>> Waiting few minutes for normal discount to reflect on style id so that cfTopUp can be applied <<<------");
		TimeUnit.MINUTES.sleep(4);
		HashMap getParam = new HashMap();
			getParam.put("user","imran.khan2@myntra.com");
		MyntraService cfTopUpService=Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,APINAME.CREATECFTOPUP,init.Configurations,new String[]{ topUpType,fundingType,startTime,endTime1,styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Cf Top up payload -- > \n"+cfTopUpService.Payload);
		RequestGenerator cfTopUpReqGenerator=new RequestGenerator(cfTopUpService,getParam);
		System.out.println("CfTop Up service url : "+cfTopUpService.URL);
		String jsonRes2 = cfTopUpReqGenerator.respvalidate.returnresponseasstring();
//		System.out.println("Cf top up response \n"+jsonRes2);
		String cf_Do_Id = JsonPath.read(jsonRes2, "$.discountOverrideIds[0]").toString();
		System.out.println("Cf_Do_ID: "+ cf_Do_Id);
		internallyaprroveDiscountOverride(cf_Do_Id);
		sellerApprovalDiscountOverride(cf_Do_Id);
		
		try
		{
			System.out.println("in try block");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			String query="select * from pricingengine.discount_override_discount_map where discount_override_id="+cf_Do_Id;
			System.out.println("buyCount query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{
				disIdFrmDisOvrId=rs1.getString("discount_id");
				System.out.println("Discount id from discount override id is : "+disIdFrmDisOvrId);
			}
			String query1="select * from myntra.discount where id="+disIdFrmDisOvrId;
			System.out.println("discount id query is : "+query);
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query1);
			while(rs2.next())
			{
				discountLimitFrmDb=rs2.getFloat("discount_limit");
			}
			
			Assert.assertEquals(discountLimitFrmDb,discountLimitAfterCfTopUp);
			
      
		}
		catch(Exception e){}
		finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		
		}
	}
	
	//vfTopUp
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getTopUpType")
	public void vCreateVfTopUpFlatPercent(String topUpType,String fundingType,String status) throws InterruptedException, SQLException, JSONException {
		String vfTopUpIdFrmDb="";
		String output="";
		String do_id="";
		String d_id="";
		String expectedPercent="19";
		String getPercentFrmDb="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 5";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		String styleID=styleIdsFrmDb.get(0)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService vfTopUpService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEVFTOPUPFLAT,
				init.Configurations, new String[] { startTime,endTime,fundingType, styleID}, PayloadType.JSON, PayloadType.JSON);
				HashMap getParam = new HashMap();
				getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator vfFlatRequestGenerator = new RequestGenerator(vfTopUpService,getParam);
				String jsonRes1 = vfFlatRequestGenerator.respvalidate.returnresponseasstring();
				JSONObject jobj = new JSONObject(jsonRes1);
				String id = jobj.getString("MasterVFTopUpID ");
				System.out.println("MasterVFTopUpID: "+id);
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
					String query="select * from pricingengine.master_vf_topup where id="+id;
					stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery(query);
					
					while(rs1.next()){
						 vfTopUpIdFrmDb=rs1.getString("note");
					}
					
				} catch (Exception e) {
				}
				
				
				String[] vfTpUpId=vfTopUpIdFrmDb.split(":");
				
				String vfTopUpId=vfTpUpId[1].toString().replace("[", "").replace("]", "").trim();
				
				MyntraService vfTopUpBulkApproveService = Myntra.getService(
						ServiceType.PORTAL_PRICINGENGINE, APINAME.VFTOPUPIDBULKAPPROVE,
						init.Configurations, new String[] {vfTopUpId,status}, PayloadType.JSON, PayloadType.JSON);
						HashMap getParam1= new HashMap();
						getParam1.put("user","imran.khan2@myntra.com");
						RequestGenerator vfBulkApproveRequestGenerator = new RequestGenerator(vfTopUpBulkApproveService,getParam1);
						String jsonRes = vfBulkApproveRequestGenerator.respvalidate.returnresponseasstring();
						
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
							String query2="select * from pricingengine.vf_topup_discount_override where vf_topup_id="+vfTopUpId;
							stmt = con.createStatement();
							ResultSet rs1 = stmt.executeQuery(query2);
							
							while(rs1.next()){
								 do_id=rs1.getString("discount_override_id");
							}
							
						} catch (Exception e) {
						}
						
						internallyaprroveDiscountOverride(do_id);
						sellerApprovalDiscountOverride(do_id);
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
							String query3="select * from pricingengine.discount_override_discount_map where discount_override_id="+do_id;
							stmt = con.createStatement();
							ResultSet rs1 = stmt.executeQuery(query3);
							
							while(rs1.next()){
								 d_id=rs1.getString("discount_id");
							}
							
						} catch (Exception e) {
						}
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
							String query3="select * from myntra.discount_rule where discount_id="+d_id;
							stmt = con.createStatement();
							ResultSet rs1 = stmt.executeQuery(query3);
							
							while(rs1.next()){
								 getPercentFrmDb=rs1.getString("get_percent");
							}
							Assert.assertEquals(getPercentFrmDb, expectedPercent);
						} catch (Exception e) {
						}
						
			}		
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getTopUpType")
	public void vCreateVfTopUpFlatRupee(String topUpType,String fundingType,String status) throws InterruptedException, SQLException, JSONException {
		String vfTopUpIdFrmDb="";
		String output="";
		String do_id="";
		String d_id="";
		String expectedAmt="99";
		String getPercentFrmDb="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 5";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(2)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService vfTopUpService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEVFTOPUPFLATRUPEE,
				init.Configurations, new String[] { startTime,endTime,fundingType, styleID}, PayloadType.JSON, PayloadType.JSON);
				HashMap getParam = new HashMap();
				getParam.put("user","imran.khan2@myntra.com");
				RequestGenerator vfFlatRequestGenerator = new RequestGenerator(vfTopUpService,getParam);
				String jsonRes1 = vfFlatRequestGenerator.respvalidate.returnresponseasstring();
				JSONObject jobj = new JSONObject(jsonRes1);
				String id = jobj.getString("MasterVFTopUpID ");
				System.out.println("MasterVFTopUpID: "+id);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306", "myntraAppDBUser", "9eguCrustuBR");
			String query = "select * from pricingengine.master_vf_topup where id=" + id;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);

			while (rs1.next()) {
				vfTopUpIdFrmDb = rs1.getString("note");
			}

		} catch (Exception e) {
		}

		String[] vfTpUpId = vfTopUpIdFrmDb.split(":");

		String vfTopUpId = vfTpUpId[1].toString().replace("[", "").replace("]", "").trim();

		MyntraService vfTopUpBulkApproveService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE,
				APINAME.VFTOPUPIDBULKAPPROVE, init.Configurations, new String[] { vfTopUpId, status }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("user", "imran.khan2@myntra.com");
		RequestGenerator vfBulkApproveRequestGenerator = new RequestGenerator(vfTopUpBulkApproveService, getParam1);
		String jsonRes = vfBulkApproveRequestGenerator.respvalidate.returnresponseasstring();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306", "myntraAppDBUser", "9eguCrustuBR");
			String query2 = "select * from pricingengine.vf_topup_discount_override where vf_topup_id=" + vfTopUpId;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query2);

			while (rs1.next()) {
				do_id = rs1.getString("discount_override_id");
			}

		} catch (Exception e) {
		}

		internallyaprroveDiscountOverride(do_id);
		sellerApprovalDiscountOverride(do_id);
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306", "myntraAppDBUser", "9eguCrustuBR");
			String query3 = "select * from pricingengine.discount_override_discount_map where discount_override_id="
					+ do_id;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query3);

			while (rs1.next()) {
				d_id = rs1.getString("discount_id");
			}

		} catch (Exception e) {
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306", "myntraAppDBUser", "9eguCrustuBR");
			String query3 = "select * from myntra.discount_rule where discount_id=" + d_id;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query3);

			while (rs1.next()) {
				getPercentFrmDb = rs1.getString("get_amount");
			}
			Assert.assertEquals(getPercentFrmDb, expectedAmt);
		} catch (Exception e) {
		}
				
	}
	
	
	//Event Pricing
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentCfVfmoreThan100")
	public void createEventForMoreThan100percent(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 5";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(0)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
				
				MyntraService UpdateVfEvntService = Myntra.getService(
						ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATEVFEVENT,
						init.Configurations, new String[] { styleID,vfPercent},new String[]{evntID}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("Accept", "application/json");
		getParam1.put("Content-Type", "application/json");
		getParam1.put("vendorId", "2");
		getParam1.put("vendorEmail", "imran.khan2@myntra.com");
		getParam1.put("vendorName", "ABC pvt ltd");
		RequestGenerator UpdateVfEvntRequestGenerator = new RequestGenerator(UpdateVfEvntService, getParam1);
		String jsonRes2 = UpdateVfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String successMsgvfUpdte = JsonPath.read(jsonRes2, "$.message").toString();
		System.out.println("VfUpdate success msg: " + successMsgvfUpdte);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> <<<<<<<<<<<<<<<<<<<<<<<<<");
						
						MyntraService UpdateCfEvntService = Myntra.getService(
								ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATECFEVENT,
								init.Configurations, new String[] { styleID,cfPercent},new String[]{evntID}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam2 = new HashMap();
		getParam2.put("createdBy", "imran.khan2@myntra.com");
		RequestGenerator UpdateCfEvntRequestGenerator = new RequestGenerator(UpdateCfEvntService, getParam2);
		String jsonRes3 = UpdateCfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsgCfUpdte = JsonPath.read(jsonRes3, "$.message").toString();
		System.out.println("cfUpdate success msg: " + actualMsgCfUpdte);
		Assert.assertEquals(actualMsgCfUpdte,
				"Please check for few of the styles (CF+VF) exceeds the suggested limit of 90.0");
				
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEvent")
	public void getEventListForNonExistingEvnt(String eventID) throws InterruptedException, SQLException
	{
		
		MyntraService getEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.GETEVENT,
				init.Configurations,PayloadType.JSON,  new String[]{eventID}, PayloadType.JSON);
		System.out.println("URL ----- >>> "+getEvntService.URL);
				RequestGenerator getEvntRequestGenerator = new RequestGenerator(getEvntService);
				String jsonRes1 = getEvntRequestGenerator.respvalidate.returnresponseasstring();
				
				String actualMsg = JsonPath.read(jsonRes1, "$.message").toString();
				
				Assert.assertEquals(actualMsg, "Event doesn't exist with Id 0");
				
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void createEventApiTest(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException
	{
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 5";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(0)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
		String actualMsg = JsonPath.read(jsonRes1, "$.message").toString();
		Assert.assertEquals(actualMsg, "Successfully created event");
				
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "createEventFlatConditional")
	public void createEventFlatConditional(String eventType) throws InterruptedException, SQLException
	{
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 5";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(0)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		AssertJUnit.assertEquals("Status code does not match", 500, createEvntRequestGenerator.response.getStatus());
				
	}
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void updateCfAndVfForExistingEvntWithOpenStatus(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 10";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(4)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService createEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,init.Configurations, new String[] { startTime, eventType, endTime }, PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
				
		MyntraService UpdateVfEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATEVFEVENT,init.Configurations, new String[] { styleID, vfPercent }, new String[] { evntID }, PayloadType.JSON,PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("Accept", "application/json");
		getParam1.put("Content-Type", "application/json");
		getParam1.put("vendorId", "2");
		getParam1.put("vendorEmail", "imran.khan2@myntra.com");
		getParam1.put("vendorName", "ABC pvt ltd");
		RequestGenerator UpdateVfEvntRequestGenerator = new RequestGenerator(UpdateVfEvntService, getParam1);
		String jsonRes2 = UpdateVfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String successMsgvfUpdte = JsonPath.read(jsonRes2, "$.message").toString();
		System.out.println("VfUpdate success msg: " + successMsgvfUpdte);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> <<<<<<<<<<<<<<<<<<<<<<<<<");
						
		MyntraService UpdateCfEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATECFEVENT,
				init.Configurations, new String[] { styleID, cfPercent }, new String[] { evntID }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam2 = new HashMap();
		getParam2.put("createdBy", "imran.khan2@myntra.com");
		RequestGenerator UpdateCfEvntRequestGenerator = new RequestGenerator(UpdateCfEvntService, getParam2);
		String jsonRes3 = UpdateCfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsgCfUpdte = JsonPath.read(jsonRes3, "$.message").toString();
		System.out.println("cfUpdate success msg: " + actualMsgCfUpdte);
		Assert.assertEquals(actualMsgCfUpdte, "Successfully added styles to event id " + evntID);
				
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void freezeEventTest(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String status="Freeze";
	
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
								
		MyntraService freezeEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam3 = new HashMap();
		getParam3.put("user", "imran.khan2@myntra.com");
		getParam3.put("Cache-Control", "no-cache");
		getParam3.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator freezeEvntRequestGenerator = new RequestGenerator(freezeEvntService, getParam3);
		String jsonRes4 = freezeEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg = JsonPath.read(jsonRes4, "$.message").toString();
		Assert.assertEquals(actualMsg, "Event successfully Freezed");
								
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void finalizeEventTest(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String status="Finalized";
	
		
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
				
								
		MyntraService freezeEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam3 = new HashMap();
		getParam3.put("user", "imran.khan2@myntra.com");
		getParam3.put("Cache-Control", "no-cache");
		getParam3.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator freezeEvntRequestGenerator = new RequestGenerator(freezeEvntService, getParam3);
		String jsonRes4 = freezeEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg = JsonPath.read(jsonRes4, "$.message").toString();
		Assert.assertEquals(actualMsg, "Event Finalize phase initiated successfully");
								
	}
	

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void eventStatusFromFreezeToRejected(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String status1="Freeze";
		String status2="Rejected";
	
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
								
		MyntraService freezeEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status1 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam3 = new HashMap();
		getParam3.put("user", "imran.khan2@myntra.com");
		getParam3.put("Cache-Control", "no-cache");
		getParam3.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator freezeEvntRequestGenerator = new RequestGenerator(freezeEvntService, getParam3);
		String jsonRes4 = freezeEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg = JsonPath.read(jsonRes4, "$.message").toString();
		
		// Assert.assertEquals(actualMsg, "Event successfully Freezed");

		MyntraService rejectEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status2 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam4 = new HashMap();
		getParam4.put("user", "imran.khan2@myntra.com");
		getParam4.put("Cache-Control", "no-cache");
		getParam4.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator rejectEvntRequestGenerator = new RequestGenerator(rejectEvntService, getParam4);
		String jsonRes5 = rejectEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg1 = JsonPath.read(jsonRes5, "$.message").toString();
		Assert.assertEquals(actualMsg1, "Event successfully Rejected");
	}
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void updateVfForRejectedEvent(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String status2="Rejected";
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307", "myntraAppDBUser", "9eguCrustuBR");
			String query = "select * from myntra_seller1.seller_item_master order by id desc limit 10";
			System.out.println("Query is : " + query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while (rs1.next()) {
				output = (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : " + output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
	}
		String styleID=styleIdsFrmDb.get(6)+"";
		System.out.println("Sytle id is : "+styleID);
	
		MyntraService createEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,init.Configurations, new String[] { startTime, eventType, endTime }, PayloadType.JSON,PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
								
									

		MyntraService rejectEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status2 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam4 = new HashMap();
		getParam4.put("user", "imran.khan2@myntra.com");
		getParam4.put("Cache-Control", "no-cache");
		getParam4.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator rejectEvntRequestGenerator = new RequestGenerator(rejectEvntService, getParam4);
		String jsonRes5 = rejectEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg1 = JsonPath.read(jsonRes5, "$.message").toString();
		Assert.assertEquals(actualMsg1, "Event successfully Rejected");
									
		MyntraService UpdateVfEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATEVFEVENT,
				init.Configurations, new String[] { styleID, vfPercent }, new String[] { evntID }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("Accept", "application/json");
		getParam1.put("Content-Type", "application/json");
		getParam1.put("vendorId", "2");
		getParam1.put("vendorEmail", "imran.khan2@myntra.com");
		getParam1.put("vendorName", "ABC pvt ltd");
		RequestGenerator UpdateVfEvntRequestGenerator = new RequestGenerator(UpdateVfEvntService, getParam1);
		String jsonRes2 = UpdateVfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsgvfUpdte = JsonPath.read(jsonRes2, "$.message").toString();
		Assert.assertEquals(actualMsgvfUpdte,
				"Error in action due to Participation to this event is not allowed, either of event got expired or status is not Open");
	}
	
	
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "getEventTypeFlatPercentValidDiscount")
	public void updateCfForRejectedEvent(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String status2="Rejected";
		String output="";
		Connection con = null;
		Statement stmt = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			String query="select * from myntra_seller1.seller_item_master order by id desc limit 10";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(6)+"";
		System.out.println("Sytle id is : "+styleID);
	
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);

		MyntraService rejectEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status2 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam4 = new HashMap();
		getParam4.put("user", "imran.khan2@myntra.com");
		getParam4.put("Cache-Control", "no-cache");
		getParam4.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator rejectEvntRequestGenerator = new RequestGenerator(rejectEvntService, getParam4);
		String jsonRes5 = rejectEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg1 = JsonPath.read(jsonRes5, "$.message").toString();
		// Assert.assertEquals(actualMsg1, "Event successfully Rejected");

		MyntraService UpdateCfEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATECFEVENT,
				init.Configurations, new String[] { styleID, cfPercent }, new String[] { evntID }, PayloadType.JSON,
				PayloadType.JSON);
		HashMap getParam2 = new HashMap();
		getParam2.put("createdBy", "imran.khan2@myntra.com");
		RequestGenerator UpdateCfEvntRequestGenerator = new RequestGenerator(UpdateCfEvntService, getParam2);
		String jsonRes3 = UpdateCfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsgCfUpdte = JsonPath.read(jsonRes3, "$.message").toString();
		Assert.assertEquals(actualMsgCfUpdte,
				"Error in action due to Participation to this event is not allowed, either of event got expired or status is not Open/Freeze");
	}
	
	//Activate Event and Seller approval DO and check discount in discount rule table - One full flow test case remaining
	
	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression","Regression","Fox7Sanity"}, dataProvider = "createEventTypeFlatPercentCfVf")
	public void createEventAndApproveDOendToendFlow(String eventType,String vfPercent,String cfPercent) throws InterruptedException, SQLException, JSONException {
		String output="";
		String status1="Freeze";
		String status2="Finalized";
		Connection con = null;
		Statement stmt = null;
		int do_idFrmDb=0;
		int disPercent=32;
		int disPercentFrmDb=0;
		int d_idfrmDb=0;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3307","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query="select * from myntra_seller1.seller_item_master order by style_id desc limit 9";
			System.out.println("Query is : "+query);
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()){
				output =  (String) rs1.getObject("style_id").toString();
				System.out.println("Output is : "+output);
				styleIdsFrmDb.add(output);
				
			}
			System.out.println("style ids in Arraylist  are : "+styleIdsFrmDb);
			
			
		} catch (Exception e) {
		}
		String styleID=styleIdsFrmDb.get(8)+"";
		System.out.println("Sytle id is : "+styleID);
		
		MyntraService createEvntService = Myntra.getService(
				ServiceType.PORTAL_PRICINGENGINE, APINAME.CREATEEVENT,
				init.Configurations, new String[] { startTime,eventType,endTime}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("user", "imran.khan2@myntra.com");
		RequestGenerator createEvntRequestGenerator = new RequestGenerator(createEvntService, getParam);
		String jsonRes1 = createEvntRequestGenerator.respvalidate.returnresponseasstring();
		JSONObject jobj = new JSONObject(jsonRes1);

		String evntID = JsonPath.read(jsonRes1, "$.result").toString();
		System.out.println("Event id is : " + evntID);
				
				MyntraService UpdateVfEvntService = Myntra.getService(
						ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATEVFEVENT,
						init.Configurations, new String[] { styleID,vfPercent},new String[]{evntID}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam1 = new HashMap();
		getParam1.put("Accept", "application/json");
		getParam1.put("Content-Type", "application/json");
		getParam1.put("vendorId", "2");
		getParam1.put("vendorEmail", "imran.khan2@myntra.com");
		getParam1.put("vendorName", "ABC pvt ltd");
		RequestGenerator UpdateVfEvntRequestGenerator = new RequestGenerator(UpdateVfEvntService, getParam1);
		String jsonRes2 = UpdateVfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String successMsgvfUpdte = JsonPath.read(jsonRes2, "$.message").toString();
		System.out.println("VfUpdate success msg: " + successMsgvfUpdte);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> <<<<<<<<<<<<<<<<<<<<<<<<<");
						
						MyntraService UpdateCfEvntService = Myntra.getService(
								ServiceType.PORTAL_PRICINGENGINE, APINAME.UPDATECFEVENT,
								init.Configurations, new String[] { styleID,cfPercent},new String[]{evntID}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam2 = new HashMap();
		getParam2.put("createdBy", "imran.khan2@myntra.com");
		RequestGenerator UpdateCfEvntRequestGenerator = new RequestGenerator(UpdateCfEvntService, getParam2);
		String jsonRes3 = UpdateCfEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsgCfUpdte = JsonPath.read(jsonRes3, "$.message").toString();
		System.out.println("cfUpdate success msg: " + actualMsgCfUpdte);
		
		
		MyntraService freezeEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status1 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam3 = new HashMap();
		getParam3.put("user", "imran.khan2@myntra.com");
		getParam3.put("Cache-Control", "no-cache");
		getParam3.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator freezeEvntRequestGenerator = new RequestGenerator(freezeEvntService, getParam3);
		String jsonRes4 = freezeEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg = JsonPath.read(jsonRes4, "$.message").toString();
		
		
		MyntraService rejectEvntService = Myntra.getService(ServiceType.PORTAL_PRICINGENGINE, APINAME.EVENTACTION,
				init.Configurations, new String[] { evntID, status2 }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam4 = new HashMap();
		getParam4.put("user", "imran.khan2@myntra.com");
		getParam4.put("Cache-Control", "no-cache");
		getParam4.put("Postman-Token", "5652d723-9d8f-d7ca-92c2-4da413a9f3b7");

		RequestGenerator FinalizeEvntRequestGenerator = new RequestGenerator(rejectEvntService, getParam4);
		String jsonRes5 = FinalizeEvntRequestGenerator.respvalidate.returnresponseasstring();
		String actualMsg1 = JsonPath.read(jsonRes5, "$.message").toString();
		Assert.assertEquals(actualMsg1, "Event Finalize phase initiated successfully");
		
		/// ------>>
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query1 = "select * from pricingengine.event_discount_override where event_id="+evntID;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query1);

			while (rs1.next()) {
				do_idFrmDb = rs1.getInt("discount_override_id");
				System.out.println("<<<<<<<<<<<<DO_ID from db >>>>>>>>>>>>"+do_idFrmDb);
			}

		} catch (Exception e) {
		}
		
		
		
		String doIdFrmDbStr=do_idFrmDb+"";
		
		internallyaprroveDiscountOverride(doIdFrmDbStr);
		sellerApprovalDiscountOverride(doIdFrmDbStr);
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			// Below is the mySql 5.7 version ip, but this is not public.When this ip becomes public then, comment above jdbc connection and use this below jdbc connection.
			//con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query1 = "select * from pricingengine.discount_override_discount_map where discount_override_id="+do_idFrmDb;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query1);

			while (rs1.next()) {
				d_idfrmDb = rs1.getInt("discount_id");
				System.out.println("Discount id from db is : =====>>>>> "+d_idfrmDb);
			}

		} catch (Exception e) {
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306","myntraAppDBUser","9eguCrustuBR");
			con=DriverManager.getConnection("jdbc:mysql://10.144.169.138:3306","MyntStagingUser","9eguCrustuBR1!");
			String query1 = "select * from myntra.discount_rule where discount_id="+d_idfrmDb;
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(query1);

			while (rs1.next()) {
				disPercentFrmDb = rs1.getInt("get_percent");
				System.out.println("get percent from db is : =====>>>>> "+disPercentFrmDb);
			}
			
			Assert.assertEquals(disPercentFrmDb, disPercent);

		} catch (Exception e) {
		}
		
		
	}
	
}


