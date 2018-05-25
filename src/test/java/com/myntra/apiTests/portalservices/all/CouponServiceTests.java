package com.myntra.apiTests.portalservices.all;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.CouponServiceDataProvider;
import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import argo.saj.InvalidSyntaxException;
import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListenerV2;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import com.myntra.apiTests.portalservices.commons.CommonUtils;


public class CouponServiceTests extends CouponServiceDataProvider {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(CouponServiceTests.class);
	ArrayList<Integer> Expire1 = new ArrayList<Integer>();

	APIUtilities apiUtil = new APIUtilities();
	public long currentTime = System.currentTimeMillis()/1000;
	String startTime = "" , endTime = "";
	String xID,sXid;
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();
	MyntraListenerV2 myntListner = new MyntraListenerV2();
	static APIUtilities utilities = new APIUtilities();
	int reval;
	Double d1,d2;
	Connection con;
	String randomPrefix;
	String id;
//	HashMap<String,String> templateType_id_prefix = new HashMap<String,String>();
	



/*	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getAllCouponsDP")
	public void CouponService_getAllCoupons(String userId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETALLCOUPONS, init.Configurations, null, new String[]{userId});
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getExpiredCouponsDP")
	public void CouponService_getExpiredCoupons(String userId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETEXPIREDCOUPONS, init.Configurations, null, new String[]{userId});
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
*/

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getFestivalCouponsForOrderDP", description="")
	public void CouponService_getFestivalCouponsForOrder(String orderId)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.FESTIVALCOUPONSFORORDER, init.Configurations, null, new String[]{orderId});
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "couponUsageHistoryDP")
	public void CouponService_couponUsageHistory(String userId) throws NumberFormatException, InvalidSyntaxException
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.COUPONUSAGEHISTORY, init.Configurations, null, new String[]{userId});
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResponse = req.respvalidate.returnresponseasstring();
		System.out.println(req.respvalidate.returnresponseasstring());
		int totalCount = Integer.parseInt(req.respvalidate.GetNodeValue("status.totalCount", jsonResponse));
		int noOfTimesUsed = Integer.parseInt(req.respvalidate.GetNodeValue("data.noOfTimesUsed", jsonResponse));
		double totalCountA = 0.00;
		for(int i=0;i<totalCount;i++){
			int countC = req.respvalidate.GetChildNodeCountUsingIndex("data.usercouponusagelist["+i+"].itemListEntity");
			for(int j=0;j<countC;j++){
				//System.out.println(JsonPath.read(req.respvalidate.returnresponseasstring(), "$..data.usercouponusagelist["+i+"].itemListEntity["+j+"].itemid").toString());
				double value = Double.parseDouble(JsonPath.read(req.respvalidate.returnresponseasstring(), "$..data.usercouponusagelist["+i+"].itemListEntity["+j+"].couponDiscountProduct").toString());
				totalCountA += value;
			}
		}
		
		
		double amountSaved = Double.parseDouble(req.respvalidate.GetNodeValue("data.amountSaved", jsonResponse));
		System.out.println(twoDecimalPlaces(totalCountA));
		System.out.println(twoDecimalPlaces(amountSaved));
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals("Validation of amount saved", twoDecimalPlaces(totalCountA), twoDecimalPlaces(amountSaved));
		AssertJUnit.assertEquals("status.totalCount = data.noOfTimesUsed ", totalCount, noOfTimesUsed);
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"}, dataProvider = "getCouponsAllActiveExpiredDP")
	public void CouponService_getCouponsAllActiveExpired(String userId, String status)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations );
		service.URL = apiUtil.prepareparameterizedURL(service.URL, new String[]{userId, status});
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getCouponsAllActiveExpiredMixDP")
	public void CouponService_getCouponsAllActiveExpiredMix(String userId)
	{
		//Defalt=Active
		MyntraService serviceA = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations );
		serviceA.URL = apiUtil.prepareparameterizedURL(serviceA.URL, new String[]{userId, ""});
		System.out.println(serviceA.URL);
		RequestGenerator reqA = new RequestGenerator(serviceA);
		String jsonResponseA = reqA.respvalidate.returnresponseasstring();
		//System.out.println(jsonResponseA);
		AssertJUnit.assertEquals(200, reqA.response.getStatus());
		
		//All
		MyntraService serviceB = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations );
		serviceB.URL = apiUtil.prepareparameterizedURL(serviceB.URL, new String[]{userId, "?status=all"});
		System.out.println(serviceB.URL);
		RequestGenerator reqB = new RequestGenerator(serviceB);
		String jsonResponseB = reqB.respvalidate.returnresponseasstring();
		//System.out.println(jsonResponseB);
		AssertJUnit.assertEquals(200, reqB.response.getStatus());
		
		//Expired
		MyntraService serviceC = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations );
		serviceC.URL = apiUtil.prepareparameterizedURL(serviceC.URL, new String[]{userId, "?status=expired"});
		System.out.println(serviceC.URL);
		RequestGenerator reqC = new RequestGenerator(serviceC);
		String jsonResponseC = reqC.respvalidate.returnresponseasstring();
		//System.out.println(jsonResponseB);
		AssertJUnit.assertEquals(200, reqC.response.getStatus());
		
		//Active
		MyntraService serviceD = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETCOUPONSALLEXPIREDACTIVE, init.Configurations );
		serviceD.URL = apiUtil.prepareparameterizedURL(serviceD.URL, new String[]{userId, "?status=active"});
		System.out.println(serviceD.URL);
		RequestGenerator reqD = new RequestGenerator(serviceD);
		String jsonResponseD = reqD.respvalidate.returnresponseasstring();
		//System.out.println(jsonResponseB);
		AssertJUnit.assertEquals(200, reqD.response.getStatus());
		
		//A=Default, B=all, C=expired, D=Active
		int tottalCountA = Integer.parseInt(reqA.respvalidate.GetNodeValue("status.totalCount", jsonResponseA));
		int tottalCountB = Integer.parseInt(reqB.respvalidate.GetNodeValue("status.totalCount", jsonResponseB));
		int tottalCountC = Integer.parseInt(reqC.respvalidate.GetNodeValue("status.totalCount", jsonResponseC));
		int tottalCountD = Integer.parseInt(reqD.respvalidate.GetNodeValue("status.totalCount", jsonResponseD));
		
		System.out.println("Total count [ Default = Active ] : "+tottalCountA);
		System.out.println("Total count [ All ] : "+tottalCountB);
		System.out.println("Total count [ Expired ] : "+tottalCountC);
		System.out.println("Total count [ Active ] : "+tottalCountD);
		
		AssertJUnit.assertEquals("Mismatch in total count [status=active and default]", tottalCountA, tottalCountD);
		AssertJUnit.assertEquals("Total count of all should be equal to active + expired ", tottalCountB, (tottalCountC + tottalCountD));
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression"}, dataProvider="getStyleidForCoupon", priority=1)
	public void CouponService_clickForOffer(String styleid)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);
		RequestGenerator rs = new RequestGenerator(service);
		String response = rs.returnresponseasstring();
		System.out.println("response-- >>> " + rs.returnresponseasstring());
		AssertJUnit.assertEquals("Response is not 200 OK", 200, rs.response.getStatus());
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression"}, dataProvider="getStyleidForCoupon", priority=2)
	public void CouponService_clickForOfferPercentageCouponWithLogin(String styleid)
	{
		startTime = String.valueOf(currentTime);

		modifyPercentageCoupon(startTime,startTime,"couponTesting","90","coupontesting@myntra.com");

		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);
		System.out.println("service URL-"+service.URL);

		HashMap<String,String> getParam = new HashMap<String,String>();
		getParam.put("login",
				"coupontesting@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("Response---===  ?? ? " + jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.message").toString().replace("[", "").replace("]", "").trim();
		String coupon = JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
        System.out.println("coupo ------------>" +coupon);
		if(coupon.equalsIgnoreCase("nocoupon"))
		{
			AssertJUnit.assertEquals("There is no coupon applicable for this style id", "The product is already at its best price.", msg1);

		}
		else
		{
		 String value = JsonPath.read(jsonRes, "$.data..coupon").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		 System.out.println("------------>>>>>>>>" + value);
		 AssertJUnit.assertEquals(value, "couponTesting");

		}
		AssertJUnit.assertEquals("Response is not 200 OK", 200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "modifyDualCoupon")
	public void CouponService_modifyDualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount, String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYDUALCOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName, mrpAmount,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "getCoupon")
	public void CouponService_getCouponDeatils(String couponName)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.GETCOUPONDETAILS, init.Configurations,PayloadType.JSON,
				new String[] {couponName}, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		String jsonRes =req.respvalidate.returnresponseasstring();
		
		 String coupon = JsonPath.read(jsonRes, "$.data.code").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		 AssertJUnit.assertEquals("Doesn't match", couponName, coupon);

		System.out.println("Get Coupon details response -\n" +req.respvalidate.returnresponseasstring());
		System.out.println("Get Coupon name -\n" +coupon);

		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Fox7Sanity","Regression", "ExhaustiveRegression"})
	public void CouponService_bestCouponWithLogin()
	{
		getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.BESTCOUPON, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("xid", xID);
		headersGetAdd.put("login", "coupontesting@myntra.com");

		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonRes = req.respvalidate.returnresponseasstring();

		List<String> couponname = JsonPath.read(jsonRes, "$.data..coupon");

//		int couponcount = couponname.size();
		List<Object> benifit = JsonPath.read(jsonRes, "$.data..couponPercentage");
		System.out.println("@@@@");
		System.out.println(benifit);
		
//		d1 =(Double) benifit.get(0);
//		d2 = (Double) benifit.get(1);
		
		for(int i=0; i<=benifit.size();i++){
			if((Double) benifit.get(i)<(Double) benifit.get(i+1)){
				d1 =(Double) benifit.get(i);
				d2 = (Double) benifit.get(i+1);
				break;
			}
				
			
		}
		reval = Double.compare(d1, d2);

		if(reval>0)
		{
			log.info(d1 +"is greate than" + d2 );
			for(int i=0;i< couponname.size(); i++)
			{
				if(couponname.get(i).equals("coupontesting1")){
					AssertJUnit.assertTrue(true);
						}
			}
			AssertJUnit.assertEquals(200, req.response.getStatus());
		}
		else
		{
			log.info(d2 +"is greate than" + d1 );
			Assert.assertTrue(false);
		}
			

		
		for(int i=0;i< couponname.size(); i++)
		{
			if(couponname.get(i).equals("coupontesting1")){
				AssertJUnit.assertTrue(true);
					}
		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression","Fox7Sanity", "Regression", "ExhaustiveRegression"})
	public void CouponService_bestCouponWithoutLogin()
	{
		getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.BESTCOUPON, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("xid", xID);

		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		System.out.println( "json paylod---- >" + service.Payload);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "modifyPercentageCoupon")
	public void CouponService_modifyPercentageCoupon(String startDate, String lastEdited ,String CouponName,String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYPERCENTAGECOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "modifyDualPercentageCoupon")
	public void CouponService_modifyPercentageDualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount,String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYPERCENTAGEDUALCOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName,mrpAmount,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Fox7Sanity","Regression"}, dataProvider="getStyleidForCoupon", priority=3)
	public void CouponService_clickForOfferDualCouponWithLogin(String styleid)
	{ 
		startTime = String.valueOf(currentTime);

		modifydualCoupon(startTime,startTime,"couponTesting","90","3000","coupontesting@myntra.com");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);

		HashMap<String,String> getParam = new HashMap<String,String>();
		getParam.put("login",
				"coupontesting@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.message").toString().replace("[", "").replace("]", "").trim();
		String coupon = JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
        System.out.println("coupo ------------>" +coupon);
		if(coupon.equalsIgnoreCase("nocoupon"))
		{
			AssertJUnit.assertEquals("There is no coupon applicable for this style id", "The product is already at its best price.", msg1);

		}
		else
		{
		 String value = JsonPath.read(jsonRes, "$.data..coupon").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		 if(value.equalsIgnoreCase("PLCtest")){
			 System.out.println("------------>>>>>>>>" + value);
			 AssertJUnit.assertEquals(value, "PLCtest"); 
		 }
		 else {
			 System.out.println("------------>>>>>>>>" + value);
			 AssertJUnit.assertEquals(value, "couponTesting"); 
		}
		 

		}
		AssertJUnit.assertEquals("Response is not 200 OK", 200, req.response.getStatus());
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression","Fox7Sanity", "Regression"}, dataProvider="getStyleidForCoupon", priority=4)
	public void CouponService_clickForOfferDualPercentageCouponWithLogin(String styleid) throws InterruptedException
	{ 
		startTime = String.valueOf(currentTime);

		modifyPercentageDualCoupon(startTime,startTime,"couponTesting","89","90","coupontesting@myntra.com");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);

		HashMap<String,String> getParam = new HashMap<String,String>();
		getParam.put("login",
				"coupontesting@myntra.com");
		Thread.sleep(3000);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		log.info(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.message").toString().replace("[", "").replace("]", "").trim();
		String coupon = JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		if(coupon.equalsIgnoreCase("nocoupon"))
		{
			AssertJUnit.assertEquals("There is no coupon applicable for this style id", "The product is already at its best price.", msg1);
		}
		else
		{
		 String value = JsonPath.read(jsonRes, "$.data..coupon").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		 AssertJUnit.assertEquals(value, "couponTesting");
		}
		AssertJUnit.assertEquals("Response is not 200 OK", 200, req.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","Fox7Sanity", "ExhaustiveRegression"})
	public void CouponService_create_data_for_applyCouponOnCart() throws FileNotFoundException
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.GETCART, init.Configurations, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
//		headersGetAdd.put("xid", "JJNebee39f05fe311e7b4e806727850f2bd1499081817G");
		headersGetAdd.put("xid", "JJNb8829dfb9bbd11e78da2b82a72d5e9101505662480G");
									
								  
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonRes = req.respvalidate.returnresponseasstring();
		log.info("Response :: " +jsonRes );
		
		PrintWriter pw = new PrintWriter(new File("./Data/payloads/CSV/applymyntcoupon.csv"));
        StringBuilder sb = new StringBuilder();

        List<Object> itemId  = JsonPath.read(jsonRes, "$.data..itemId");
        sb.append(itemId.get(0).toString());
        sb.append(',');
        List<Object> unitMrp  = JsonPath.read(jsonRes, "$.data..unitMrp");
        sb.append(unitMrp.get(0).toString());
        sb.append(',');
        List<Object> vatRate  = JsonPath.read(jsonRes, "$.data..vatRate");
        sb.append(vatRate.get(0).toString());
        sb.append(',');
        List<Object> quantity  = JsonPath.read(jsonRes, "$.data..quantity");
        sb.append(quantity.get(0).toString());
        sb.append(',');
        List<Object> unitAdditionalCharge  = JsonPath.read(jsonRes, "$.data..unitAdditionalCharge");
        sb.append(unitAdditionalCharge.get(0).toString());
        sb.append(',');
        List<Object> unitDiscount  = JsonPath.read(jsonRes, "$.data..unitDiscount");
        sb.append(unitDiscount.get(0).toString());
        sb.append(',');
        List<Object> productTypeId  = JsonPath.read(jsonRes, "$.data..productTypeId");
        sb.append(productTypeId.get(0).toString());
        sb.append(',');
        List<Object> brand  = JsonPath.read(jsonRes, "$.data..brand");
        sb.append(brand.get(0).toString());
        sb.append(',');
        List<Object> productStyleType  = JsonPath.read(jsonRes, "$.data..productStyleType");
        sb.append(productStyleType.get(0).toString());
        sb.append(',');
        List<Object> articleTypeId  = JsonPath.read(jsonRes, "$.data..articleTypeId");
        sb.append(articleTypeId.get(0).toString());
        sb.append(',');
        List<Object> sku  = JsonPath.read(jsonRes, "$.data..skuId");
        sb.append(sku.get(0).toString());
        sb.append(',');
        List<Object> styleId  = JsonPath.read(jsonRes, "$.data..styleId");
        sb.append(styleId.get(0).toString());
        sb.append(',');
        List<Object> sellerId  = JsonPath.read(jsonRes, "$.data..sellerId");
        sb.append(sellerId.get(0).toString());
        sb.append(',');
        List<Object> totalPrice  = JsonPath.read(jsonRes, "$.data..totalPrice");
        sb.append(totalPrice.get(0).toString());
        
        //for expired coupons
        StringBuilder sb_expirecoupons =new StringBuilder(sb);
        sb_expirecoupons.append(',');
        sb_expirecoupons.append("expiredcoupon23");
        sb_expirecoupons.append(',');
        sb_expirecoupons.append("Sorry this Coupon has expired.");
        sb_expirecoupons.append('\n');
        
        //for not applicable coupons
        StringBuilder sb_notapplicablecoupons =new StringBuilder(sb);
        sb_notapplicablecoupons.append(',');
        sb_notapplicablecoupons.append("TestCoupon11");
        sb_notapplicablecoupons.append(',');
        sb_notapplicablecoupons.append("Sorry Your cart has no applicable products for this coupon - TestCoupon11");
        sb_notapplicablecoupons.append('\n');
        
      //for personalised coupons
        StringBuilder sb_personalisedcoupons =new StringBuilder(sb);
        sb_personalisedcoupons.append(',');
        sb_personalisedcoupons.append("TEST_PERSONALISED_COUPON");
        sb_personalisedcoupons.append(',');
        sb_personalisedcoupons.append("Sorry this coupon is login specific coupon. Please login to use this coupon.");
        sb_personalisedcoupons.append('\n');
        
      //for invalid coupons
        StringBuilder sb_invalidcoupons =new StringBuilder(sb);
        sb_invalidcoupons.append(',');
        sb_invalidcoupons.append("TEST_INVALID_COUPON");
        sb_invalidcoupons.append(',');
        sb_invalidcoupons.append("Coupon code is not valid");
        sb_invalidcoupons.append('\n');
        
      //for valid coupons
        StringBuilder sb_validcoupons =new StringBuilder(sb);
        sb_validcoupons.append(',');
        sb_validcoupons.append("TestPLCCoup2");
        sb_validcoupons.append(',');
        sb_validcoupons.append("Applied Successfully");
        sb_validcoupons.append('\n');
        
      //for minimum item coupons
        StringBuilder sb_miniumitemcoupons =new StringBuilder(sb);
        sb_miniumitemcoupons.append(',');
        sb_miniumitemcoupons.append("TestMinimumItem");
        sb_miniumitemcoupons.append(',');
        sb_miniumitemcoupons.append("This coupon is only valid on a purchase of more than 2 number of items");
        sb_miniumitemcoupons.append('\n'); 
        
      //for minimum subtotal item coupons
        StringBuilder sb_maximumsubtotalcoupons =new StringBuilder(sb);
        sb_maximumsubtotalcoupons.append(',');
        sb_maximumsubtotalcoupons.append("TestMaxSubtotal");
        sb_maximumsubtotalcoupons.append(',');
        sb_maximumsubtotalcoupons.append("This coupon is only valid on a purchase less than Rs. 500.0.");
        sb_maximumsubtotalcoupons.append('\n'); 
        
        StringBuilder sb_main =new StringBuilder();
        sb_main.append(sb_expirecoupons.toString());
        sb_main.append(sb_notapplicablecoupons.toString());
        sb_main.append(sb_personalisedcoupons.toString());
        sb_main.append(sb_invalidcoupons.toString());
        sb_main.append(sb_validcoupons.toString());
        sb_main.append(sb_miniumitemcoupons.toString());
        sb_main.append(sb_maximumsubtotalcoupons.toString());

        log.info(sb_main.toString());
        pw.write(sb_main.toString());
        pw.close();
		
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","Fox7Sanity", "ExhaustiveRegression"}, dependsOnMethods="CouponService_create_data_for_applyCouponOnCart", dataProvider="applymyntcoupon")
	public void CouponService_applyCouponOnCart(String itemId, String unitMrp, String vatRate, String quantity,
			String unitAdditionalCharge, String unitDiscount, String productTypeId, String brand,
			String productStyleType, String articleTypeId, String skuId, String styleId, String sellerId,
			String totalPrice, String coupon, String output) throws IOException
	{
		String [] strPayload=new String[] {itemId, unitMrp, vatRate, quantity,
				unitAdditionalCharge, unitDiscount, productTypeId, brand,
				productStyleType, articleTypeId, skuId, styleId, sellerId,
				totalPrice, coupon};
		String payload=utilities.readFileAsString("./Data/payloads/JSON/coupon/applymyntcoupon");
	    payload=utilities.preparepayload(payload,strPayload);
	    log.info("Payload::"+payload);
	    
		MyntraService service = new MyntraService(ServiceType.PORTAL_COUPONSERVICE.toString(), APINAME.APPLYMYNTCOUPON.toString(),init.Configurations,payload);
		RequestGenerator req = new RequestGenerator(service);
		String jsonRes = req.respvalidate.returnresponseasstring();
		log.info("Response :: " +jsonRes );
		
		if (coupon.equals("TestPLCCoup2")){
			List<Object> status = JsonPath.read(jsonRes, "$.data..appliedCoupons..message");
			Assert.assertEquals(status.get(0).toString().replaceAll(",", ""), output);
		}else{
		List<Object> status = JsonPath.read(jsonRes, "$.status..statusMessage");
		Assert.assertEquals(status.get(0).toString().toLowerCase().replaceAll(",", ""), output.toLowerCase());
		}
		
	}
	
	
	// Have to check again
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "Fox7Sanity","ExhaustiveRegression"}, dataProvider="getLockedCoupon")
	public void CouponService_applyCouponOnCartWithLockedCouponMsg(String CouponName)
	{
		getAndSetTokens("coupontesting@myntra.com", "coupontesting@myntra.com");
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations,new String[] {CouponName}, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonRes = req.respvalidate.returnresponseasstring();
		String value = JsonPath.read(jsonRes, "$.data..appliedCoupons..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		System.out.println("------------->>" +value );
//		AssertJUnit.assertEquals("Something Wrong", "Sorry, this Coupon has expired.", value);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	private void modifydualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount, String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYDUALCOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName, mrpAmount,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "fLasMPDCoupon")
    public void CouponService_createMPDCoupon(String startDate, String ExpireDate ,String CouponName,String CreatedTime ,String mrpAmount, String mrpPercentage, String Description, String CouponType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.CREATEFLASHCOUPON, init.Configurations,
				new String[] { startDate, ExpireDate,CouponName, CreatedTime,mrpAmount,
						mrpPercentage,Description,CouponType}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "IntenCouponScore")
	public void CouponService_createIntentRule(String couponName, String score)
	{
		startTime = String.valueOf(currentTime);
		endTime=String.valueOf(currentTime+4000);
		createMPDCoupon(startTime,endTime,couponName,startTime,"30","40","Test","max_percentage_dual");
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.CREATEINTENTRULE, init.Configurations,
				new String[] { couponName, score}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();
		String message1 = JsonPath.read(jsonRes, "$.status.statusType").toString().trim();

		System.out.println("================>>>> " +  message);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals("Intent rule doesn't created", "Success", message);
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "IntenCouponScoreNegatice")
	public void CouponService_createIntentRule_Negative(String couponName, String score)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.CREATEINTENTRULE, init.Configurations,
				new String[] { couponName, score}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();
		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();
//		String message1 = JsonPath.read(jsonRes, "$.status.statusType").toString().trim();

		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals("Intent rule doesn't created","Unable to persisting the intent rule. Following exception occured Unable to persisting the intent rule. please create coupon first" , message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "GetIntent")
	public void CouponService_getIntentRule(String score)
	{
		String randomCoupon = generateRandomCoupon();
		System.out.println("Random Coupon------->> " + randomCoupon);
		createIntentRule(randomCoupon,score);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		List<Integer> couponCode = JsonPath.read(jsonRes, "$.data..couponCode");

//	 ArrayList<Integer> couponCode = JsonPath.read(jsonRes, "$.data..couponCode");

	 System.out.println("Array List------>" + couponCode.get(0));
	 for (int i= 0;i< couponCode.size();i++) {
		 if(couponCode.contains(randomCoupon))
		 {
			 AssertJUnit.assertEquals("Created Coupon code is not available", couponCode.get(couponCode.size()-1), randomCoupon);

		 }
		   }
//	 ArrayList<Integer> scoreValue = JsonPath.read(jsonRes, "$.data..score");
		List<Integer> scoreValue = JsonPath.read(jsonRes, "$.data..score");


	 Integer scoreCount=scoreValue.size();
	 Integer couponCodeCount = couponCode.size();
	 
	 for (int j= 0;j< scoreValue.size();j++) {
		 if(scoreValue.equals("7"))
		 {
			 AssertJUnit.assertEquals("score doesn't match", scoreValue.get(j), "7");
		 }
		   }
//		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();
////		String message1 = JsonPath.read(jsonRes, "$.status.statusType").toString().trim();
        AssertJUnit.assertEquals("Coupon and score count doesn't match", scoreCount, couponCodeCount);
		AssertJUnit.assertEquals(200, req.response.getStatus());
//		AssertJUnit.assertEquals("Intent rule doesn't created","Unable to persisting the intent rule. Following exception occured Unable to persisting the intent rule. please create coupon first" , message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	
	

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "GetIntentNegative")
	public void CouponService_getIntentRule_Negative(String score)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		
		String jsonRes = req.respvalidate.returnresponseasstring();
		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();

		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "GetIntentNegative1")
	public void CouponService_getIntentRule_Negative1(String score)
	{
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		
//		String jsonRes = req.respvalidate.returnresponseasstring();
//		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();

		
		AssertJUnit.assertEquals(500, req.response.getStatus());
//		AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	

	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "GetIntentNegative2")
	public void CouponService_getIntentRule_Negative2(String score)
	{
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		
//		String jsonRes = req.respvalidate.returnresponseasstring();
//		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();

		
		AssertJUnit.assertEquals(405, req.response.getStatus());
//		AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "DeleteIntent")
	public void CouponService_deleteIntentRule(String score)
	{
		String randomCoupon = generateRandomCoupon();
		System.out.println("Random Coupon------->> " + randomCoupon);
		createIntentRule(randomCoupon,score);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		
		
		MyntraService serv = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETINTENTRULE, init.Configurations);
		serv.URL = apiUtil.prepareparameterizedURL(serv.URL, score);
		RequestGenerator intentreq = new RequestGenerator(serv);
		String jsonResIntent = intentreq.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>" + jsonResIntent );
        String message = JsonPath.read(jsonResIntent, "$.status.statusMessage").toString().trim();
        AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);		
		AssertJUnit.assertEquals(200, req.response.getStatus());

	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "DeleteIntentNegative")
	public void CouponService_deleteIntentRule_Negative(String score)
	{
		
		
		String randomCoupon = generateRandomCoupon();
		System.out.println("Random Coupon------->> " + randomCoupon);
		createIntentRule(randomCoupon,score);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResIntent = req.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>" + jsonResIntent );
        String message = JsonPath.read(jsonResIntent, "$.status.statusMessage").toString().trim();
//        verify again wrong response
//        AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		
		

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Fox7Sanity","Regression"}, dataProvider="getStyleidForCoupon", priority=5)
	public void CouponService_clickForOfferFlashCouponWithLogin(String styleid)
	{
		System.out.println(styleid);//185358 198939 306502
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CLICKFOROFFER, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, styleid);

		HashMap<String,String> getParam = new HashMap<String,String>();
		getParam.put("login",
				"newintelli2@myntra.com");
		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println(jsonRes);
		String msg1 = JsonPath.read(jsonRes, "$.message").toString().replace("[", "").replace("]", "").trim();
		String coupon = JsonPath.read(jsonRes, "$.status..statusMessage").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
        System.out.println("coupo ------------>" +coupon);
		if(coupon.equalsIgnoreCase("nocoupon"))
		{
			AssertJUnit.assertEquals("There is no coupon applicable for this style id", "The product is already at its best price.", msg1);

		}
		else
		{
		 String value = JsonPath.read(jsonRes, "$.data..coupon").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		 System.out.println("------------>>>>>>>>" + value);
		 
		 if(value=="intelli2"){
			 AssertJUnit.assertEquals(value, "intelli2");

		 }
		 else if (value=="vat1") {
			 AssertJUnit.assertEquals(value, "vat1");

		} 
		 else if (value=="PLCtest") {
			 AssertJUnit.assertEquals(value, "PLCtest");

			
		}
		 

		}
		AssertJUnit.assertEquals("Response is not 200 OK", 200, req.response.getStatus());
	}
	
	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","Fox7Sanity", "ExhaustiveRegression"}, dataProvider="getFlashCouponname")
	public void CouponService_applyCouponOnCartWithApplicableFlashCoupon(String CouponName,String Email,String Passw0rd)
	{
		getAndSetTokens(Email, Passw0rd);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations,new String[] {CouponName}, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>>>"+jsonRes);
		String value = JsonPath.read(jsonRes, "$.data..appliedCoupons..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
//		System.out.println(req.respvalidate.returnresponseasstring());
		if(value.contains("Applied Successfully")){
			AssertJUnit.assertEquals("Coupon not applied suussfully", "Applied Successfully", value);

		}
		else {
			AssertJUnit.assertEquals("No flash Coupon for this user today", "Sorry, this coupon is time sensitive & won't apply today.", value);

		}
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}*/


	//@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider="getSpecifciFlashCoupon")
	public void CouponService_applyCouponOnCartNotApplicableFlashCoupon(String CouponName,String EmailID ,String Password)
	{
		getAndSetTokens(EmailID,Password);
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_CART, APINAME.APPLYCOUPONCART, init.Configurations,new String[] {CouponName}, PayloadType.JSON, PayloadType.JSON);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("X-CSRF-Token", sXid);
		headersGetAdd.put("xid", xID);
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonRes = req.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>>>"+jsonRes);
		String value = JsonPath.read(jsonRes, "$.data..appliedCoupons..message").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		//myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
//		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("Coupon not applied suussfully", "Sorry, this coupon is time sensitive & won't apply today.", value);
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "DeleteIntentNegative1")
	public void CouponService_deleteIntentRule_Negative1(String score)
	{
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResIntent = req.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>" + jsonResIntent );
        String message = JsonPath.read(jsonResIntent, "$.status.statusMessage").toString().trim();
//        verify again wrong response
        AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		
		

	}
	
	/*
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void CouponService_bestCouponForFlashWithLogin()
	{
		getAndSetTokens("newintelli2@myntra.com", "newintelli2@myntra.com");
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.BESTCOUPON, init.Configurations);
		myntListner.setServiceUrlAndPayload(service.URL, service.Payload);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("xid", xID);
		headersGetAdd.put("login", "newintelli2@myntra.com");

		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		System.out.println(req.respvalidate.returnresponseasstring());
		String jsonRes = req.respvalidate.returnresponseasstring();

//		String[] msg1 = JsonPath.read(jsonRes, "$.data.coupon").toString().replace("[", "").replace("]", "").trim();
		List<String> couponname = JsonPath.read(jsonRes, "$.data..coupon");
//		int couponcount = couponname.size();
		List<Double> benifit = JsonPath.read(jsonRes, "$.data..benefit");
		System.out.println("--------------------m" + benifit.get(0));
		if(benifit.size()>1)
		{
			 d1 =benifit.get(0);
				d2 = benifit.get(1);
				reval = Double.compare(d1, d2);
			

			if(reval>0)
			{
				System.out.println(d1 +"is greate than" + d2 );
				for(int i=0;i< couponname.size(); i++)
				{
					if(couponname.get(i).equals("intelli2")){
							}
				}
				AssertJUnit.assertEquals(200, req.response.getStatus());
			}
			else
			{
				System.out.println(d2 +"is greate than" + d1 );
				
			}
			for(int i=0;i< couponname.size(); i++)
			{
				if(couponname.get(i).equals("intelli2")){
						}
			}
		}
			
			

		
		
		AssertJUnit.assertEquals(200, req.response.getStatus());
//		AssertJUnit.assertEquals("best coupon is not exist", "couponTesting", msg1);
	}
	*/
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "DeleteIntentNegative2")
	public void CouponService_deleteIntentRule_Negative2(String score)
	{
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEINTENTRULE, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, score);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResIntent = req.respvalidate.returnresponseasstring();
		System.out.println("---------->>>>>>>>" + jsonResIntent );
//        String message = JsonPath.read(jsonResIntent, "$.status.statusMessage").toString().trim();
//        verify again wrong response
//        AssertJUnit.assertEquals("Intent rule doesn't created","Error in intent rule" , message);		
		AssertJUnit.assertEquals(405, req.response.getStatus());
		
		

	}
	
	/*
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression" }, dataProvider = "ExpiryBulkUpdateCoupon")
	public void CouponService_bulkUpdateEndDate(String groupName,
			String GroupNameValue, String expire, String expireDate,
			String offSetValue, String rowsPerpage) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, GroupNameValue,
						expire, expireDate }, PayloadType.JSON,
				PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Coupon Update URL---------->>>>>>>>" + service.URL);

		System.out.println("Coupon Update Paylaod---------->>>>>>>>"
				+ service.Payload);

		String jsonResIntent = req.respvalidate.returnresponseasstring();
		System.out.println("Coupon Update response---------->>>>>>>>"
				+ jsonResIntent);
		String lockType = JsonPath.read(jsonResIntent, "$.data.lockType");

		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.COUPONDETAILSBYGROUPNAME, init.Configurations,
				new String[] { GroupNameValue, offSetValue, rowsPerpage },
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req1 = new RequestGenerator(service1);
		System.out.println("Coupon name URL---------->>>>>>>>" + service1.URL);

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();
		System.out.println("Coupon name response---------->>>>>>>>"
				+ jsonResIntent1);
		List<Integer> Expire1 = JsonPath.read(jsonResIntent1,
				"$.data.coupons..expire[*]");
		System.out.println("EXPIredaye=============\n" + Expire1);
		Integer expireDate22 = Integer.parseInt(expireDate);
		int Count = 0;

		for (int i = 0; i < Expire1.size(); i++) {

			if (expireDate22.equals(Expire1.get(i))) {
				Count = Count + 1;

			}

		}
		System.out.println("COunt Item lllll " + Count);

		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals(200, req1.response.getStatus());
		AssertJUnit.assertEquals("Loctype fail to  release state", "RELEASED",
				lockType);
		;

	}*/
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "StartdateBulkUpdateCoupon")
	public void CouponService_bulkUpdateStartDate(String groupName,String GroupNameValue,String startdate ,String StartDateValue ,String offSetValue, String rowsPerpage)
	{
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE, init.Configurations,new String[] {groupName,GroupNameValue,startdate,StartDateValue}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println("Coupon Update URL---------->>>>>>>>" + service.URL );
		
		System.out.println("Coupon Update Paylaod---------->>>>>>>>" + service.Payload );


		String jsonResIntent = req.respvalidate.returnresponseasstring();
		System.out.println("Coupon Update response---------->>>>>>>>" + jsonResIntent );
		
		
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.COUPONDETAILSBYGROUPNAME, init.Configurations,new String[] {GroupNameValue,offSetValue,rowsPerpage}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req1 = new RequestGenerator(service1);
	System.out.println("Coupon name URL---------->>>>>>>>" + service1.URL );

		
		System.out.println("Coupon name Paylaod---------->>>>>>>>" + service1.Payload );

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();
		System.out.println("Coupon name response---------->>>>>>>>" + jsonResIntent1 );
		 List<Integer> start = JsonPath.read(jsonResIntent1, "$.data.coupons..startdate[*]");
	    
	    Integer startingDate = Integer.parseInt(StartDateValue);
	    int Count=0;
	    
	    for (int i = 0; i < start.size(); i++) {
	    	

			if(startingDate.equals(start.get(i)))
			{
				Count=Count+1;

			}
	    	
		}
	    
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals(200, req1.response.getStatus());

		
		

	}
	
	
	
	
	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "StatusBulkUpdateCoupon")
	public void CouponService_bulkUpdateStatus(String groupName,
			String GroupNameValue, String status, String statusValue, String offSetValue, String rowsPerpage) {
		
		
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATESTATUS,
				init.Configurations, new String[] { groupName,
						GroupNameValue, status, statusValue },
				PayloadType.JSON, PayloadType.JSON);
		System.out.println("coupon URL-----\n" +service.URL );
		System.out.println("coupon Payload-----\n" +service.Payload );

		RequestGenerator req = new RequestGenerator(service);

		
		String jsonResIntent = req.respvalidate.returnresponseasstring();

		System.out.println("Coupon Update response---------->>>>>>>>"
				+ jsonResIntent);
		String lockType = JsonPath.read(jsonResIntent, "$.data.lockType");
		AssertJUnit.assertEquals("Loctype fail to  release state",
				"RELEASED", lockType);
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.COUPONDETAILSBYGROUPNAME, init.Configurations,new String[] {GroupNameValue,offSetValue,rowsPerpage}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req1 = new RequestGenerator(service1);
	    System.out.println("Coupon name URL---------->>>>>>>>" + service1.URL );

		
		System.out.println("Coupon name Paylaod---------->>>>>>>>" + service1.Payload );

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();
		System.out.println("Coupon name response---------->>>>>>>>" + jsonResIntent1 );
		List<String> statustype = JsonPath.read(jsonResIntent1, "$.data.coupons..status[*]");
		
		int Count2=0;
	    
	    	for (int i = 0; i < statustype.size(); i++) {
	    
	    		if(String.valueOf(statustype.get(i)).contains(String.valueOf(statusValue))){
	    			Count2=Count2+1;
					System.out.println("Count2----------\n" + Count2);

	    		}
	    	}

		
		AssertJUnit.assertEquals(200, req1.response.getStatus());

	}*/
	
	
	/*@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "BulkUpdateLock")
	public void CouponService_bulkUpdateWithSameQueryParallel(String groupName,
			String GroupNameValue, String status, String statusValue) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, GroupNameValue,
						status, statusValue }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("coupon URL-----\n" + service.URL);
		System.out.println("coupon Payload-----\n" + service.Payload);

		RequestGenerator req = new RequestGenerator(service);

		String jsonResIntent = req.respvalidate.returnresponseasstring();

		System.out.println("Coupon Update response---------->>>>>>>>"
				+ jsonResIntent);
		String lockType = JsonPath.read(jsonResIntent, "$.data.lockType");

		AssertJUnit.assertEquals("Loctype fail to  release state", "RELEASED",
				lockType);
		String lockId = JsonPath.read(jsonResIntent, "$.data.lockId");
		String statusCompleted = JsonPath.read(jsonResIntent, "$.data.statusCompleted");

		String lockedOn = JsonPath.read(jsonResIntent, "$.data.lockedOn");
//		String lockflag = JsonPath.read(jsonResIntent, "$.data.lockflag");
		updateBulkLock(lockId, "ACQUIRED", "30", lockedOn, "true");
		
		
		
		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, GroupNameValue,
						status, statusValue }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("coupon URL-----\n" + service.URL);
		System.out.println("coupon Payload-----\n" + service.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();
		
		System.out.println("Coupon name Paylaod---------->>>>>>>>"
				+ jsonResIntent1);
		
		String statusMessage = JsonPath.read(jsonResIntent1, "$.status.statusMessage");
		String statusType = JsonPath.read(jsonResIntent1, "$.status.statusType");
		System.out.println("upfdate lock message "+statusMessage);
		System.out.println("update lock type "+statusType);



		AssertJUnit.assertEquals("Bulk Process is simultaneously going on", "Bulk process update already in progress id : "+lockedOn, statusMessage);
		AssertJUnit.assertEquals("Bulk Process is simultaneously going on", "ERROR", statusType);

		updateBulkLock(lockId, "RELEASED", "100", lockedOn, "true");

		AssertJUnit.assertEquals(200, req1.response.getStatus());


	}*/

/*	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "BulkUpdateMoreThan2Query")
	public void CouponService_bulkUpdateMoreThan2query(String groupName,
			String GroupNameValue,  String expire, String expireDate,String expireDate1,String expireDate2,String groupName1,String groupName2) {

		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, GroupNameValue,
						expire, expireDate }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("coupon URL-----\n" + service.URL);
		System.out.println("coupon Payload-----\n" + service.Payload);

		RequestGenerator req = new RequestGenerator(service);

		String jsonResIntent = req.respvalidate.returnresponseasstring();

		System.out.println("Coupon Update response---------->>>>>>>>"
				+ jsonResIntent);
		String lockType = JsonPath.read(jsonResIntent, "$.data.lockType");

		AssertJUnit.assertEquals("Loctype fail to  release state", "RELEASED",
				lockType);
		String lockId = JsonPath.read(jsonResIntent, "$.data.lockId");
		String statusCompleted = JsonPath.read(jsonResIntent,
				"$.data.statusCompleted");

		String lockedOn = JsonPath.read(jsonResIntent, "$.data.lockedOn");
		// String lockflag = JsonPath.read(jsonResIntent, "$.data.lockflag");
		updateBulkLock(lockId, "ACQUIRED", "30", lockedOn, "true");
		System.out.println("locked one ---->>> \n" + lockedOn);


		MyntraService service1 = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, groupName1,
						expire, expireDate1 }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("coupon URL-----\n" + service1.URL);
		System.out.println("coupon Payload-----\n" + service1.Payload);

		RequestGenerator req1 = new RequestGenerator(service1);

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();

		System.out.println("Coupon name Paylaod---------->>>>>>>>"
				+ jsonResIntent1);
		
		
		String lockId1 = JsonPath.read(jsonResIntent1, "$.data.lockId");
		String statusCompleted1= JsonPath.read(jsonResIntent1,
				"$.data.statusCompleted");

		String lockedOn1 = JsonPath.read(jsonResIntent, "$.data.lockedOn");
		System.out.println("locked two ---->>> \n" + lockedOn1);
		// String lockflag = JsonPath.read(jsonResIntent, "$.data.lockflag");
		updateBulkLock(lockId1, "ACQUIRED", "30", lockedOn1, "true");
		
		
		
		
		
		MyntraService service2 = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATE,
				init.Configurations, new String[] { groupName, groupName2,
						expire, expireDate2 }, PayloadType.JSON,
				PayloadType.JSON);
		System.out.println("coupon URL-----\n" + service2.URL);
		System.out.println("coupon Payload-----\n" + service2.Payload);

		RequestGenerator req2 = new RequestGenerator(service2);

		String jsonResIntent2 = req2.respvalidate.returnresponseasstring();

		System.out.println("Coupon name Paylaod---------->>>>>>>>"
				+ jsonResIntent1);
		
		

		String statusMessage = JsonPath.read(jsonResIntent2,
				"$.status.statusMessage");
		String statusType = JsonPath
				.read(jsonResIntent2, "$.status.statusType");
		System.out.println("update lock message " + statusMessage);
		System.out.println("update lock type " + statusType);

//		AssertJUnit.assertEquals("Bulk Process is simultaneously going on",
//				"Bulk process update already in progress id : " + lockedOn,
//				statusMessage);
//		AssertJUnit.assertEquals("Bulk Process is simultaneously going on",
//				"ERROR", statusType);

		updateBulkLock(lockId, "RELEASED", "100", lockedOn, "true");
		updateBulkLock(lockId1, "RELEASED", "100", lockedOn1, "true");

		

		AssertJUnit.assertEquals(200, req1.response.getStatus());

	}*/
	
	
	
	
	
	
	
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "CouponTypeBulkUpdateCoupon")
	public void CouponService_bulkUpdateCouponType(String groupName,String GroupNameValue,String couponType,String couponTypeValue,String mrpAmount,String mrpAmountValue,String mrpPercentage,String mrpPercentageValue,String offSetValue, String rowsPerpage)
	{
		
		
		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.BULKUPDATECOUPONTYPE, init.Configurations,new String[] {groupName,GroupNameValue,couponType,couponTypeValue,mrpAmount,mrpAmountValue,mrpPercentage,mrpPercentageValue}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Coupon Update URL---------->>>>>>>>" + service.URL );
		System.out.println("Coupon Update Paylaod---------->>>>>>>>" + service.Payload );


		RequestGenerator req = new RequestGenerator(service);
		String jsonResIntent12 = req.respvalidate.returnresponseasstring();
		System.out.println("REsponse for update bulk upload\n" + jsonResIntent12);

		

		
		
		
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.COUPONDETAILSBYGROUPNAME, init.Configurations,new String[] {GroupNameValue,offSetValue,rowsPerpage}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req1 = new RequestGenerator(service1);
	    System.out.println("Coupon name URL---------->>>>>>>>" + service1.URL );

		
		System.out.println("Coupon name Paylaod---------->>>>>>>>" + service1.Payload );

		String jsonResIntent1 = req1.respvalidate.returnresponseasstring();
		System.out.println("Coupon name response---------->>>>>>>>" + jsonResIntent1 );
		List<String> CouponType = JsonPath.read(jsonResIntent1, "$.data.coupons..couponType[*]");
		List<String> mrpAmt = JsonPath.read(jsonResIntent1, "$.data.coupons..mrpAmount[*]");
		List<Integer> mrpPer = JsonPath.read(jsonResIntent1, "$.data.coupons..mrpPercentage[*]");
		System.out.println("Mrp Amount=====\n" +mrpAmt);
		System.out.println("Mrp per=====\n" +mrpPer);


		 int Count=0;
		    
		    for (int i = 0; i < CouponType.size(); i++) {
		    	

				if(couponTypeValue.equals(CouponType.get(i)))
				{
					Count=Count+1;

				}
		    	
			}
		    int Count1=0;
		     Integer mrpAmountValue1 = Integer.parseInt(mrpAmountValue);
		    
		    	for (int i = 0; i < mrpAmt.size(); i++) {
		    
		    		if(String.valueOf(mrpAmt.get(i)).contains(String.valueOf(mrpAmountValue1))){
		    			Count1=Count1+1;
						System.out.println("Count1----------\n" + Count1);

		    		}
		    		
		    		
		    		 
		    	}
		    	

		    	int Count2=0;
			     Integer mrpPercentageValue1 = Integer.parseInt(mrpAmountValue);
			    
			    	for (int i = 0; i < mrpPer.size(); i++) {
			    
			    		if(String.valueOf(mrpPer.get(i)).contains(String.valueOf(mrpPercentageValue1))){
			    			Count2=Count2+1;
							System.out.println("Count2----------\n" + Count2);

			    		}
			    	}
		


		String jsonResIntent = req1.respvalidate.returnresponseasstring();
		System.out.println("Coupon Update response---------->>>>>>>>" + jsonResIntent );
		
		
		
	    System.out.println("EXPIredaye=============\n" +Expire1 );
	    
	    
	    
//	    for(String str: Expire1) {
//	        if(str.trim().contains(expireDate)){
//	           Count++;
//	        System.out.println("COunt+++++++++++\n" + Count);
//	        }
//	    }
//	    
		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		AssertJUnit.assertEquals(200, req1.response.getStatus());

		
		

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "createcoupongroup")
	public void CouponService_CreateCouponGroup(String groupName, String channel, String groupType,
			String applicableDevice, String couponGroupStatus, String groupBehaviour, String timeCreated,
			String creator) throws IOException {
		SoftAssert softAssert = new SoftAssert();

		groupName = groupName + RandomStringUtils.randomAlphanumeric(5).toUpperCase();
		String[] strPayload = new String[] { groupName, channel, groupType, applicableDevice, couponGroupStatus,
				groupBehaviour, timeCreated, creator };

		String payload = utilities.readFileAsString("./Data/payloads/JSON/coupon/createcoupongroup");
		payload = utilities.preparepayload(payload, strPayload);

		MyntraService service = new MyntraService(ServiceType.PORTAL_COUPONSERVICE.toString(),
				APINAME.CREATECOUPONGROUP.toString(), init.Configurations, payload);

		RequestGenerator req = new RequestGenerator(service);
		Assert.assertEquals(200, req.response.getStatus());
		
		String q_get_group = "select * from mk_coupon_group where groupname = '"+ groupName + "'";
		Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(q_get_group, "myntra_coupon");
		
		softAssert.assertNotNull(rs.get("groupid").toString());
		softAssert.assertEquals(groupName, rs.get("groupName").toString());
		softAssert.assertEquals(channel, rs.get("channel").toString());
		softAssert.assertEquals(groupType, rs.get("group_type").toString());
		softAssert.assertEquals(applicableDevice, rs.get("applicable_device").toString());
		softAssert.assertEquals(couponGroupStatus, rs.get("couponGroupStatus").toString());
		softAssert.assertEquals(groupBehaviour, rs.get("groupBehaviour").toString());
		softAssert.assertNotNull(rs.get("timeCreated").toString());
		softAssert.assertEquals(creator, rs.get("creator").toString());
		
		couponGroupStatus="DISABLED";
		PrintWriter pw = new PrintWriter(new File("./Data/payloads/CSV/updatecoupongroup.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append(groupName);
        sb.append(',');
        sb.append(couponGroupStatus);
        sb.append('\n');
        
        couponGroupStatus="ACTIVE";
        sb.append(groupName);
        sb.append(',');
        sb.append(couponGroupStatus);
        
        pw.write(sb.toString());
        pw.close();
		
		
	}
	
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "updatecoupongroup", dependsOnMethods = { "CouponService_CreateCouponGroup" })
	public void CouponService_UpdateCouponGroup(String groupName, String couponGroupStatus) throws IOException {
		SoftAssert softAssert = new SoftAssert();
		
		// update group - by changing its status to disabled
		
				String[] strUpdatePayload = new String[] { groupName, couponGroupStatus};

				String updatePayload = utilities.readFileAsString("./Data/payloads/JSON/coupon/updatecoupongroup");
				updatePayload = utilities.preparepayload(updatePayload, strUpdatePayload);

				MyntraService updateService = new MyntraService(ServiceType.PORTAL_COUPONSERVICE.toString(),
						APINAME.UPDATECOUPONGROUP.toString(), init.Configurations, updatePayload);
				
				RequestGenerator reqUpdate = new RequestGenerator(updateService);
				String jsonResp = reqUpdate.respvalidate.returnresponseasstring();
				
				Assert.assertEquals(200, reqUpdate.response.getStatus());
				
				String q_updated_group = "select * from mk_coupon_group where groupname = '"+ groupName + "'";
				Map<String, Object> rs_update = DBUtilities.exSelectQueryForSingleRecord(q_updated_group, "myntra_coupon");
				
				softAssert.assertNotNull(rs_update.get("groupid").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..groupName").toString(), rs_update.get("groupName").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..channel").toString(), rs_update.get("channel").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..groupType").toString(), rs_update.get("group_type").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..applicableDevice").toString(), rs_update.get("applicable_device").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..couponGroupStatus").toString(), rs_update.get("couponGroupStatus").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..groupBehaviour").toString(), rs_update.get("groupBehaviour").toString());
				softAssert.assertNotNull(rs_update.get("timeCreated").toString());
				softAssert.assertEquals(JsonPath.read(jsonResp, "$.data..creator").toString(), rs_update.get("creator").toString());
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void CouponService_SearchAllCouponGroups()
	{
		SoftAssert softAssert = new SoftAssert();
		HashMap<String, Object> row = null;
		int i=0;
		
		String groupNamePrefix = "TST_DoNotAddAnyCoupon_";
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.SEARCHALLCOUPONGROUPS,
				init.Configurations, new String[] {groupNamePrefix}, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		Assert.assertEquals(200, req.response.getStatus());
		
		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info(((List<String>)(JsonPath.read(jsonResp, "$.data..groupid"))).size());
		
		String q_count_row = "select count(groupid) from mk_coupon_group where groupname like '"+ groupNamePrefix+"%'";
		Map<String, Object> rs_count = DBUtilities.exSelectQueryForSingleRecord(q_count_row, "myntra_coupon");
		softAssert.assertEquals(Integer.parseInt(rs_count.get("count(groupid)").toString()), ((List<String>)(JsonPath.read(jsonResp, "$.data..groupid"))).size());

		String q_get_all_col = "select * from mk_coupon_group where groupname like '"+ groupNamePrefix+"%'";
		List rs = DBUtilities.exSelectQuery(q_get_all_col, "myntra_coupon");
		
		row = (HashMap<String, Object>) rs.get(0);

		while(i < Integer.parseInt(rs_count.get("count(groupid)").toString())){
			row = (HashMap<String, Object>) rs.get(i);
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..groupid"))).get(i).toString(), row.get("groupid").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..groupname"))).get(i).toString(), row.get("groupname").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..channel"))).get(i).toString(), row.get("channel").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..creator"))).get(i).toString(), row.get("creator").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..groupType"))).get(i).toString(), row.get("group_type").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..applicableDevice"))).get(i).toString(), row.get("applicable_device").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..couponGroupStatus"))).get(i).toString(), row.get("couponGroupStatus").toString());
			softAssert.assertEquals(((List)(JsonPath.read(jsonResp, "$.data..groupBehaviour"))).get(i).toString(), row.get("groupBehaviour").toString());
			i++;
		}
		
		


	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, dataProvider = "couponGroup")
	public void CouponService_SearchCoupon(String couponGroup ,String []coupon) {
		int flag=1;
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.SEARCHCOUPON,
				init.Configurations, new String[]{couponGroup}, PayloadType.JSON,
				PayloadType.JSON);

		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		System.out.print(jsonResp);
		List<String> Coupons = JsonPath.read(jsonResp, "$.data.coupons..code");
		
		System.out.println(Coupons);
		for (int i = 0; i <coupon.length; i++) {
			
			if (Coupons.contains(coupon[i])){
				Assert.assertTrue(Coupons.contains(coupon[i]));
				flag=1;
			}
		}
		
		if (flag==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
		@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider = "brandExclusinSuccessMsg")
		public void CouponService_GetBrandExclusions(String brandExclusionSuccessMessage)
		{

			MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETBRANDEXCLUSION, init.Configurations );
			System.out.println("coupon URL-----\n" + service.URL);
			RequestGenerator req = new RequestGenerator(service);
			String jsonResp = req.respvalidate.returnresponseasstring();
			String successMsg=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/ ]","");
			//System.out.print(successMsg);
			Assert.assertTrue(brandExclusionSuccessMessage.trim().equalsIgnoreCase(successMsg.trim()));
		}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider = "CreatebrandExclusinSuccessMsg")
	public void CouponService_CreateBrandExclusion(String createBrandExclusionSuccessMessage ,String[]brand)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.CREATEBRANDEXCLUSION, init.Configurations, brand, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		String successMsg=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/ ]","");
		Assert.assertTrue(createBrandExclusionSuccessMessage.trim().equalsIgnoreCase(successMsg.trim()));
		
		String query = "select * from coupon_excluded_styles where brand='"+brand[0].toString()+"'";
		Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_coupon");
		log.info(rs.get("brand"));
		Assert.assertEquals(brand[0].toString(), rs.get("brand"));
	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression","Regression", "ExhaustiveRegression"}, dependsOnMethods="CouponService_CreateBrandExclusion")
	public void CouponService_DeleteBrandinclusion()
	{
		String brand = "Arrow";
		String query = "select * from coupon_excluded_styles where brand='"+brand+"'";
		Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_coupon");
		log.info(rs.get("id"));
		Assert.assertEquals(brand, rs.get("brand"));
		
		MyntraService serviceA = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.DELETEBRANDEXCLUSION, init.Configurations);
		serviceA.URL = apiUtil.prepareparameterizedURL(serviceA.URL, rs.get("id").toString());
		RequestGenerator req1 = new RequestGenerator(serviceA);
		String jsonResp1 = req1.respvalidate.returnresponseasstring();
		
		String q_check = "select * from coupon_excluded_styles where brand='"+brand+"'";
		Map<String, Object> rs_check = DBUtilities.exSelectQueryForSingleRecord(q_check, "myntra_coupon");
		log.info(rs.get("id"));
		Assert.assertEquals(brand, rs.get("brand"));


	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider = "styleInclusionExclusion")
	public void CouponService_StyleInclusionExclusion(String styleInclusionExclusionmsg ,String []couponGroup) {


		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEINCLUSIONEXCLUSION, init.Configurations, couponGroup, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		//System.out.print(jsonResp);
		String successMsg=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/ ]","");
		//System.out.println(successMsg);
		Assert.assertTrue(styleInclusionExclusionmsg.trim().equalsIgnoreCase(successMsg.trim()));


	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"},dataProvider = "lockUserCoupon")
	public void CouponService_LockUserCoupon(String coupon,String user,String successMSg) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.LOCKUSERCOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		System.out.print(jsonResp);
		String successMsgREsponse=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/: ]","");
		System.out.println(successMsgREsponse);
		Assert.assertTrue(successMSg.trim().equalsIgnoreCase(successMsgREsponse.trim()));



	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"},dataProvider = "unlockUserCoupon")
	public void CouponService_unLockUserCoupon(String coupon,String user,String successMSg) {

		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.UNLOCKUSERCOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		System.out.print(jsonResp);
		String successMsgREsponse=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/: ]","");
		System.out.println(successMsgREsponse);
		Assert.assertTrue(successMSg.trim().equalsIgnoreCase(successMsgREsponse.trim()));



	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"}, priority=6)
	public void CouponService_getAllDiscountCap() {
		//creating Random styleId Of Five digit
		Random r = new Random( System.currentTimeMillis() );
		int rand= ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
		String styleID1=Integer.toString(rand);
		String styleid2=Integer.toString(rand+1);
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("user", "coupontesting@myntra.com");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEDISCOUNTCAPINSERT, init.Configurations,new String[]{styleID1,styleid2},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info(jsonResp);
		MyntraService service1 = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETALLDISCOUNTCAP, init.Configurations);
		RequestGenerator req1 = new RequestGenerator(service1);
		String jsonResp1 = req1.respvalidate.returnresponseasstring();
		log.info(jsonResp1);
		List<String> Styleid = JsonPath.read(jsonResp1, "$.data..styleid");
//		System.out.println(Styleid);
//		System.out.println(styleID1);
		Assert.assertTrue(Styleid.contains(rand));






	}
	
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"},dataProvider = "createcoupon")
	public void createcoupon(String code, String startDate, String expire, String times, String maximum,
			String isInfinite, String maxUsagePerCart, String timesLocked, String maxUsageByUser, String lastEditedBy,
			String isInfinitePerUser, String minimum, String timeCreated, String users, String groupName,
			String couponType, String mrpAmount, String mrpPercentage, String status, String showInMyMyntra,
			String timesUsed, String comments, String description, String maxAmount, String freeShipping,
			String excludeUsers, String productTypeId, String excludeProductTypeIds, String styleIds,
			String excludeStyleIds, String catStyleIds, String excludeCatStyleIds, String categoryIds,
			String excludeCategoryIds, String skus, String excludeSKUs, String min_count, String max_count,
			String tag) throws IOException {
		
		code = "TST"+RandomStringUtils.randomAlphanumeric(7).toUpperCase();
		startDate = String.valueOf(Instant.now().getEpochSecond());
		expire = String.valueOf(Instant.now().getEpochSecond()+600000);
		
		String[] strPayload= {code, startDate, expire, times, maximum,
				isInfinite, maxUsagePerCart, timesLocked, maxUsageByUser, lastEditedBy,
				isInfinitePerUser, minimum, timeCreated, users, groupName,
				couponType, mrpAmount, mrpPercentage, status, showInMyMyntra,
				timesUsed, comments, description, maxAmount, freeShipping,
				excludeUsers, productTypeId, excludeProductTypeIds, styleIds,
				excludeStyleIds, catStyleIds, excludeCatStyleIds, categoryIds,
				excludeCategoryIds, skus, excludeSKUs, min_count, max_count,
				tag};
		
		String payload=utilities.readFileAsString("./Data/payloads/JSON/coupon/createcoupon");
	    payload=utilities.preparepayload(payload,strPayload);
	    log.info("Payload::"+payload);
		
	    MyntraService service = new MyntraService(ServiceType.PORTAL_COUPONSERVICE.toString(), APINAME.CREATECOUPON.toString(),init.Configurations,payload);
		RequestGenerator req_create_coupon = new RequestGenerator(service);
		String jsonResp_create = req_create_coupon.respvalidate.returnresponseasstring();
		log.info(jsonResp_create);
		
		FileWriter writer = new FileWriter("./Data/payloads/CSV/updatecoupon.csv");
		writer.append(code);
		writer.append(" ,");
		writer.append("b5a4869c.db5a.4d05.bdd9.ddea59c8d184kJyUo4cVAO");
		writer.append(" ,");
		writer.append("SUCCESS");
		writer.flush();
	    writer.close();
	    String query = "select * from xcart_discount_coupons where coupon='"+code+"'";
	    Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_coupon");
	    Assert.assertEquals(code, rs.get("coupon"));
	    Assert.assertEquals("", rs.get("users"));
	    
		
	}
	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"},dataProvider = "updatecoupon", dependsOnMethods="createcoupon")
     public void CouponService_updateCoupon(String coupon,String user,String successMSg)
	{
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.UPDATECOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info(jsonResp);
		String statusMsg=JsonPath.read(jsonResp,"$.status.statusType").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String couponCode=JsonPath.read(jsonResp,"$.data.code").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String users=JsonPath.read(jsonResp,"$.data.users").toString().replaceAll("[^A-Za-z0-9/.: ]","");
		Assert.assertEquals(successMSg,statusMsg);
		Assert.assertEquals(coupon,couponCode);
		Assert.assertEquals(user,users);
		String query = "select * from xcart_discount_coupons where coupon='"+couponCode+"'";
	    Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_coupon");
		Assert.assertEquals(coupon, rs.get("coupon"));
	    Assert.assertEquals(user, rs.get("users"));
		
	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression"})
	public void CouponService_styleDiscountCapInsert()
	{
		int discountcap=0;
		String user=null;
		//creating Random styleId Of Five digit
		Random r = new Random( System.currentTimeMillis() );
		
		int rand= ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
		String styleID1=Integer.toString(rand);
		String styleid2=Integer.toString(rand+1);
		
		HashMap<String, String> headersGetAdd = new HashMap<String, String>();
		headersGetAdd.put("user", "coupontesting@myntra.com");
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.STYLEDISCOUNTCAPINSERT, init.Configurations,new String[]{styleID1,styleid2},PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service, headersGetAdd);
		
		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info(jsonResp);
		String query="SELECT * FROM myntra.style_max_discount_cap where styleid="+styleID1;
		Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_coupon");
		log.info("buyCount query is : "+query);
		discountcap=(int) ((float)rs.get("maxDiscountCap"));
		user=(String) rs.get("updatedBy");

		Assert.assertEquals(discountcap,1,"discount cap doesnot match"+discountcap);
		Assert.assertEquals(user,"coupontesting@myntra.com","user missMatch"+user);

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"})
	public void CouponService_AddBinRange()
	{
//		Random r = new Random( System.currentTimeMillis() );
//		int rand=r.nextInt(700) + 100;
//		System.out.println(rand);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.ADDBINRANGE, init.Configurations );
		System.out.println("coupon URL-----\n" + service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResp);
		String groupNAme=JsonPath.read(jsonResp,"$.couponBinRangeEntry..groupName").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String startBin=JsonPath.read(jsonResp,"$.couponBinRangeEntry..startBin").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String endBin=JsonPath.read(jsonResp,"$.couponBinRangeEntry..endBin").toString().replaceAll("[^A-Za-z0-9/: ]","");
        System.out.println(groupNAme+"-"+startBin+"-"+endBin);
		Assert.assertEquals(groupNAme,"swatMNT");
		Assert.assertEquals(startBin,"100","start bin value"+startBin);
		Assert.assertEquals(endBin,"200","end bin value"+endBin);

	}
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression","ExhaustiveRegression"},dataProvider = "getBinRange",dependsOnMethods ="CouponService_AddBinRange" )
	public void CouponService_GetBinRange(String coupon)
	{
//		Random r = new Random( System.currentTimeMillis() );
//		int rand=r.nextInt(700) + 100;
//		System.out.println(rand);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.GETBINRANGE, init.Configurations );
		service.URL = apiUtil.prepareparameterizedURL(service.URL,coupon);
		System.out.println("coupon URL-----\n" + service.URL);
		RequestGenerator req = new RequestGenerator(service);
		String jsonResp = req.respvalidate.returnresponseasstring();
		System.out.println(jsonResp);
		String groupNAme=JsonPath.read(jsonResp,"$.couponBinRangeEntry..groupName").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String startBin=JsonPath.read(jsonResp,"$.couponBinRangeEntry..startBin").toString().replaceAll("[^A-Za-z0-9/: ]","");
		String endBin=JsonPath.read(jsonResp,"$.couponBinRangeEntry..endBin").toString().replaceAll("[^A-Za-z0-9/: ]","");
		System.out.println(groupNAme+"-"+startBin+"-"+endBin);
		Assert.assertEquals(groupNAme,"swatMNT");
		Assert.assertEquals(startBin,"100","start bin value"+startBin);
		Assert.assertEquals(endBin,"200","end bin value"+endBin);


	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression","createcoupontemplate"}, dataProvider = "createcoupontemplate")
	public void CouponService_createCouponTemplate(String prefix, String descripton, String templateType,
			String couponTag, String couponFundingType, String maxCartTotal, String minCartTotal, String times,
			String startDate, String endDate, String status, String groupName, String isInfinite,
			String maxUsagePerCart, String maxUsageByUser, String isInfinitePerUser, String users, String excludeUsers,
			String comments, String couponType, String percentage, String freeShipping, String showInMyMyntra,
			String triggerPoint, String minCount, String maxCount, String filePath, String maxUsers,
			String suffixLength,String expiryDaysForRule,String expiryDaysFromToday, String successMSg, String auditType, String approvalCount) throws ClassNotFoundException, IOException, InterruptedException {
		
		randomPrefix = "TST"+RandomStringUtils.randomAlphanumeric(7).toUpperCase();
		String [] strPayload=null;
		String payload=null;
		
		
		if (templateType.equals("Single") || templateType.equals("Bulk")){
			strPayload=new String[] { randomPrefix, descripton, templateType, couponTag, couponFundingType, maxCartTotal,
					minCartTotal, times, startDate, endDate, status, groupName, isInfinite, maxUsagePerCart,
					maxUsageByUser, isInfinitePerUser, users, excludeUsers, comments, couponType, percentage,
					freeShipping, showInMyMyntra, triggerPoint, minCount, maxCount, filePath, maxUsers,
					suffixLength};
			
			payload=utilities.readFileAsString("./Data/payloads/JSON/coupon/createcoupontemplate");
		    payload=utilities.preparepayload(payload,strPayload);
		    log.info("Payload::"+payload);
		
		}else if (templateType.equals("RuleBased")){
			strPayload=new String[] { randomPrefix, descripton, templateType, couponTag, couponFundingType, maxCartTotal,
					minCartTotal, times, startDate, endDate, status, groupName, isInfinite, maxUsagePerCart,
					maxUsageByUser, isInfinitePerUser, users, excludeUsers, comments, couponType, percentage,
					freeShipping, showInMyMyntra, triggerPoint, minCount, maxCount, filePath, maxUsers,
					suffixLength,expiryDaysForRule, expiryDaysFromToday};
			
			payload=utilities.readFileAsString("./Data/payloads/JSON/coupon/createrulebasedcoupontemplate");
		    payload=utilities.preparepayload(payload,strPayload);
		    log.info("Payload::"+payload);
		    
		}
		
			
		MyntraService service = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(), APINAME.CREATECOUPONTEMPLATE.toString(),init.Configurations,payload);
		log.info("service"+service);
		HashMap getParam = new HashMap();
		String User = "shweta.prasad@myntra.com";
		getParam.put("user", User);

		RequestGenerator req = new RequestGenerator(service, getParam);
		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info("Response:: "+jsonResp);
		
		if (successMSg.equals("Successfully created template")) {
			
			Assert.assertNotNull(
					JsonPath.read(jsonResp, "$.data.masterTemplateId").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			Assert.assertNotNull(
					JsonPath.read(jsonResp, "$.data.version").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.id").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.prefix").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					randomPrefix);
			id = JsonPath.read(jsonResp, "$.data.id").toString();
			if (suffixLength.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.suffixLength"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.suffixLength").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						suffixLength);

			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.description").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					descripton);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.couponTag").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					couponTag);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.couponFundingType").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					couponFundingType);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.templateType").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					templateType);

			if (minCartTotal.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.minCartTotal"));
			else
				Assert.assertEquals(Double.parseDouble(JsonPath.read(jsonResp, "$.data.minCartTotal").toString()),
						Double.parseDouble(minCartTotal));

			if (maxCartTotal.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxCartTotal"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.maxCartTotal").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						maxCartTotal);

			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.times").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					times);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.startDate").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					startDate);
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.endDate").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					endDate);
			
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.couponRequestStatus").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					"WaitingForApproval");
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.status").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					status);
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.provider").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					"oneToTwoprovider");
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.groupName").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					groupName);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.isInfinite").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					Boolean.toString(isInfinite.equals("1")));
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.maxUsagePerCart").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					maxUsagePerCart);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.maxUsageByUser").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					maxUsageByUser);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.isInfinitePerUser").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					Boolean.toString(isInfinitePerUser.equals("1")));
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.users").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					users);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.excludeUsers").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					excludeUsers);

			Assert.assertNull(JsonPath.read(jsonResp, "$.data.paymentMethod"));
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.comments").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					comments);
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.couponType").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					couponType);

			Assert.assertNull(JsonPath.read(jsonResp, "$.data.amount"));
			Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxAmount"));
			Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxPercent"));

			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.percentage"), Double.parseDouble(percentage));
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.freeShipping").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					freeShipping);
			Assert.assertNull(JsonPath.read(jsonResp, "$.data.styleIds"));
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.showInMyMyntra").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					Boolean.toString(showInMyMyntra.equals("1")));
			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.triggerPoint").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
					triggerPoint);

			if (minCount.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.minCount"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.minCount").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						minCount);

			if (maxCount.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxCount"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.maxCount").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						maxCount);

			Assert.assertNotNull(
					JsonPath.read(jsonResp, "$.data.createdOn").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			Assert.assertNotNull(
					JsonPath.read(jsonResp, "$.data.updatedOn").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.createdBy").toString(), User);
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.updatedBy").toString(), User);

			Assert.assertNull(JsonPath.read(jsonResp, "$.data.assignedType"));
			Assert.assertNull(JsonPath.read(jsonResp, "$.data.assignedOn"));

			if (maxUsers.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxUsers"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.maxUsers").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						maxUsers);

			Assert.assertEquals(
					JsonPath.read(jsonResp, "$.data.auditType").toString().replaceAll("[^A-Za-z0-9/: ]", ""), "CREATE");

			if (filePath.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.filePath"));
			else
				Assert.assertNotNull(
						JsonPath.read(jsonResp, "$.data.filePath").toString().replaceAll("[^A-Za-z0-9/: ]", ""));
			
			if (expiryDaysForRule.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.expiryDaysForRule"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.expiryDaysForRule").toString(),
						expiryDaysForRule);
			
			if (expiryDaysFromToday.equals(""))
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.expiryDaysFromToday"));
			else
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.expiryDaysFromToday").toString().replaceAll("[^A-Za-z0-9/: ]", ""),
						expiryDaysFromToday);
				
			String query = "SELECT * FROM coupon_request_template where id="
					+ JsonPath.read(jsonResp, "$.data.id").toString();
			
//			if (templateType.equals("Bulk") || templateType.equals("RuleBased"))
				Thread.sleep(4000);
			
					Map<String, Object> rs1 = DBUtilities.exSelectQueryForSingleRecord(query, "pricingengine");
					Assert.assertEquals((JsonPath.read(jsonResp, "$.data.id")).toString(), rs1.get("id").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.masterTemplateId").toString(),
							rs1.get("master_template_id").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.version").toString(), rs1.get("version").toString());
					Assert.assertTrue(rs1.get("prefix").toString().equals(randomPrefix));
					Assert.assertEquals(suffixLength, rs1.get("suffix_length").toString());
					Assert.assertEquals(descripton, rs1.get("description").toString());
					Assert.assertEquals(couponTag, rs1.get("coupon_tag").toString());
					Assert.assertEquals(couponFundingType, rs1.get("funding_type").toString());
					Assert.assertEquals(templateType, rs1.get("template_type").toString());
					
					Assert.assertEquals(minCartTotal, String.valueOf((int)(Double.parseDouble(rs1.get("min_cart_total").toString()))));
					Assert.assertEquals(maxCartTotal.equals("")?"0": maxCartTotal,rs1.get("max_cart_total")==null?"0":String.valueOf((int)(Double.parseDouble(rs1.get("max_cart_total").toString()))));
					Assert.assertEquals(times, rs1.get("times").toString());
					
					Assert.assertNotNull((rs1.get("start_date").toString()));
					Assert.assertNotNull(rs1.get("end_date").toString());
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.startDate").toString());
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.endDate").toString());
					
					
					if (couponFundingType.equals("Seller"))
						Assert.assertEquals("WaitingForApproval", rs1.get("request_status").toString());
					else
						Assert.assertEquals("Active", rs1.get("request_status").toString());
					
					Assert.assertEquals(status, rs1.get("status").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.provider").toString(),
							rs1.get("provider").toString());
					Assert.assertEquals(groupName, rs1.get("group_name").toString());
					Assert.assertEquals(isInfinite.equals("1") ? "true" : "false", rs1.get("is_infinite").toString());
					Assert.assertEquals(maxUsagePerCart, rs1.get("max_usage_per_cart").toString());
					Assert.assertEquals(maxUsageByUser,rs1.get("max_usage_by_user").toString());
					
					Assert.assertEquals(isInfinitePerUser.equals("1") ? "true" : "false", rs1.get("is_infinite_per_user").toString());
					Assert.assertEquals(users, rs1.get("users").toString());
					Assert.assertEquals(excludeUsers, rs1.get("exclude_users").toString());
					
					if (rs1.get("payment_method")!=null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.paymentMethod"),
								rs1.get("payment_method").toString());
					
					Assert.assertEquals(comments, rs1.get("comments").toString());
					Assert.assertEquals(couponType, rs1.get("coupon_type").toString());
					
					if (couponType.equals("max_percentage_dual")) {
						Assert.assertEquals(
								(JsonPath.read(jsonResp, "$.data.maxPercent") == null) ? "0.00"
										: (JsonPath.read(jsonResp, "$.data.maxPercent")).toString(),
								Double.toString(Double.parseDouble(rs1.get("max_percent").toString())));
						Assert.assertEquals(percentage,
								String.valueOf((int)(Double.parseDouble(rs1.get("percentage").toString()))));
					} else if (couponType.equals("percentage")) {
						Assert.assertEquals(percentage,
								String.valueOf((int)(Double.parseDouble(rs1.get("percentage").toString()))));
					} else if (couponType.equals("absolute")) {
						Assert.assertEquals(
								JsonPath.read(jsonResp, "$.data.amount") == null ? "0.00"
										: JsonPath.read(jsonResp, "$.data.amount").toString(),
								Double.toString(Double.parseDouble(rs1.get("amount").toString())));
					} else if (couponType.equals("dual")) {
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxAmount"), rs1.get("max_amount").toString());
						Assert.assertEquals(percentage, rs1.get("percentage").toString());
					}
					
					Assert.assertEquals(freeShipping, rs1.get("free_shipping").toString().equals("true") ? "1" : "0");
					Assert.assertEquals(showInMyMyntra, rs1.get("show_in_my_myntra").toString().equals("true") ? "1" : "0");

					Assert.assertEquals(minCount.equals("")?"0":minCount, rs1.get("min_count")==null?"0":rs1.get("min_count").toString());
					Assert.assertEquals(maxCount.equals("")?"0":maxCount, rs1.get("max_count")==null?"0":rs1.get("max_count").toString());
					
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.createdOn").toString());
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.updatedOn").toString());
					Assert.assertNotNull(rs1.get("created_on").toString());
					Assert.assertNotNull(rs1.get("updated_on").toString());
					Assert.assertEquals(User, rs1.get("created_by").toString());
					Assert.assertEquals(User, rs1.get("updated_by").toString());
					
					Assert.assertEquals(maxUsers.equals("")?null:maxUsers, rs1.get("max_users")==null?null:rs1.get("max_users").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.auditType").toString(),
							rs1.get("audit_type").toString());
					Assert.assertEquals(filePath, rs1.get("file_path").toString());
					 Assert.assertEquals(expiryDaysForRule.equals("")?null:expiryDaysForRule,rs1.get("expiry_days_for_rule")==null?null:rs1.get("expiry_days_for_rule").toString());

					 Assert.assertEquals(expiryDaysFromToday.equals("")?null:expiryDaysFromToday,rs1.get("expiry_days_for_coupon")==null?null:rs1.get("expiry_days_for_coupon").toString());
				String approvalStatus="";
				if (couponFundingType.equals("Seller")){
					approvalStatus=approveCouponsByAlphaSellers(JsonPath.read(jsonResp, "$.data.id").toString(), approvalCount);
				}
					
				if (templateType.equals("RuleBased") && (approvalStatus.equals("Subscribe")||couponFundingType.equals("Platform"))){
					createRuleBasedCoup(JsonPath.read(jsonResp, "$.data.id").toString());
				}
					/*
					 * Validating xcart_discount_coupons table - whether coupon got created or not
					*/
					if (couponFundingType.equals("Platform") || approvalStatus.equals("Subscribe")){
						String qry_xcart = "select * from xcart_discount_coupons where templateId="
								+ JsonPath.read(jsonResp, "$.data.id").toString();
						Map<String, Object> rs_xcart = DBUtilities.exSelectQueryForSingleRecord(qry_xcart, "myntra_coupon");
						
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.id").toString(), rs_xcart.get("templateId").toString());
						Assert.assertTrue(rs_xcart.get("coupon").toString().contains(randomPrefix));
						Assert.assertEquals(descripton, rs_xcart.get("description").toString());
						Assert.assertEquals(couponTag, rs_xcart.get("coupon_type").toString());
								
								if (couponFundingType.equals("Platform"))
									Assert.assertEquals("true", rs_xcart.get("isPlatformFunded").toString());
								else
									Assert.assertEquals("false", rs_xcart.get("isPlatformFunded").toString());
								
								
								if (!(rs_xcart.get("minimum").toString().equals("0.00")))
								Assert.assertEquals(Double.parseDouble(minCartTotal),
										Double.parseDouble(rs_xcart.get("minimum").toString()));
								
								if (!(rs_xcart.get("maximum").toString().equals("0.00")))
								Assert.assertEquals(Double.parseDouble(maxCartTotal),
										Double.parseDouble(rs_xcart.get("maximum").toString()));
								
								Assert.assertEquals(times, rs_xcart.get("times").toString());
								
								Assert.assertNotNull(rs_xcart.get("startdate").toString());
								Assert.assertNotNull(rs_xcart.get("expire").toString());
								
								Assert.assertEquals("A",rs_xcart.get("status").toString());
								
								
								
								Assert.assertEquals(groupName, rs_xcart.get("groupName").toString());
								Assert.assertEquals(isInfinite.equals("1") ? "true" : "false", rs_xcart.get("isInfinite").toString());
								Assert.assertEquals(maxUsagePerCart, rs_xcart.get("maxUsagePerCart").toString());
								
								Assert.assertEquals(isInfinite.equals("1") ? "true" : "false", rs_xcart.get("isInfinitePerUser").toString());
								Assert.assertNotNull(rs_xcart.get("users").toString());
								Assert.assertEquals(excludeUsers, rs_xcart.get("excludeUsers")==null?"":rs_xcart.get("excludeUsers").toString());
								
								if (rs_xcart.get("paymentMethod")!=null)
								Assert.assertEquals(JsonPath.read(jsonResp, "$.data.paymentMethod").toString(),
										rs_xcart.get("paymentMethod").toString());
								
								Assert.assertEquals(comments, rs_xcart.get("comments").toString());
								
								if (couponType.equals("max_percentage_dual")) {
									Assert.assertEquals(
											(JsonPath.read(jsonResp, "$.data.maxPercent") == null) ? "0.00"
													: (JsonPath.read(jsonResp, "$.data.maxPercent")).toString(),
											Double.toString(Double.parseDouble(rs_xcart.get("MRPAmount").toString())));
									Assert.assertEquals(Integer.parseInt(percentage),
											(int)(Double.parseDouble(rs_xcart.get("MRPpercentage").toString())));
								} else if (couponType.equals("percentage")) {
									Assert.assertEquals(Integer.parseInt(percentage),
											(int)(Double.parseDouble(rs_xcart.get("MRPpercentage").toString())));
								} else if (couponType.equals("absolute")) {
									Assert.assertEquals(
											JsonPath.read(jsonResp, "$.data.amount") == null ? "0.00"
													: JsonPath.read(jsonResp, "$.data.amount").toString(),
											Double.toString(Double.parseDouble(rs_xcart.get("MRPAmount").toString())));
								} else if (couponType.equals("dual")) {
									Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxAmount"), rs_xcart.get("MRPAmount").toString());
									Assert.assertEquals(Integer.parseInt(percentage), (int)(Double.parseDouble(rs_xcart.get("MRPpercentage").toString())));
								}
								
								Assert.assertEquals(couponType, rs_xcart.get("couponType").toString());

								Assert.assertEquals(freeShipping, rs_xcart.get("freeShipping").toString().equals("true") ? "1" : "0");
								Assert.assertEquals(showInMyMyntra, rs_xcart.get("showInMyMyntra").toString().equals("true") ? "1" : "0");

								Assert.assertEquals(minCount.equals("") ? "0" : minCount, rs_xcart.get("min_count").toString());
								Assert.assertEquals(maxCount.equals("") ? "0" : maxCount, rs_xcart.get("max_count").toString());
								Assert.assertNotNull(rs_xcart.get("timeCreated").toString());
								Assert.assertNotNull(rs_xcart.get("lastEdited").toString());
								Assert.assertEquals(User, rs_xcart.get("lastEditedBy").toString());

							}
				}

			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusCode").toString(),
					successMSg.equals("Successfully created template") ? "10001" : "1002");
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
					successMSg);
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
					successMSg.equals("Successfully created template") ? "SUCCESS" : "ERROR");
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.totalCount").toString(),
					successMSg.equals("Successfully created template") ? "1" : "0");
					

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression" },dataProvider = "updatecoupontemplate")
	public void CouponService_updateCouponTemplate(String prefix, String description, String templateType,
			String couponTag, String couponFundingType, String maxCartTotal, String minCartTotal, String times,
			String startDate, String endDate, String status, String groupName, String isInfinite,
			String maxUsagePerCart, String maxUsageByUser, String isInfinitePerUser, String users, String excludeUsers,
			String comments, String couponType, String percentage, String freeShipping, String showInMyMyntra,
			String triggerPoint, String minCount, String maxCount, String filePath, String maxUsers,
			String suffixLength, String expiryDaysForRule, String expiryDaysFromToday, String successMSg,
			String auditType, String approvalCount) throws ClassNotFoundException, IOException {

		String[] strPayload = null;
		String payload = null;
		String jsonResp = "";
		String User = "shweta.prasad@myntra.com";
		String updateUser = "shweta.prasad@myntra.com";
		String approvalStatus = "";

		if (auditType.equals("0")) {
			randomPrefix = "TST" + RandomStringUtils.randomAlphanumeric(7).toUpperCase();

			if (templateType.equals("Single") || templateType.equals("Bulk")) {
				strPayload = new String[] { randomPrefix, description, templateType, couponTag, couponFundingType,
						maxCartTotal, minCartTotal, times, startDate, endDate, status, groupName, isInfinite,
						maxUsagePerCart, maxUsageByUser, isInfinitePerUser, users, excludeUsers, comments, couponType,
						percentage, freeShipping, showInMyMyntra, triggerPoint, minCount, maxCount, filePath, maxUsers,
						suffixLength };

				payload = utilities.readFileAsString("./Data/payloads/JSON/coupon/updatecoupontemplate");
				payload = utilities.preparepayload(payload, strPayload);
				log.info("Payload :: " + payload);

			} else if (templateType.equals("RuleBased")) {
				strPayload = new String[] { randomPrefix, description, templateType, couponTag, couponFundingType,
						maxCartTotal, minCartTotal, times, startDate, endDate, status, groupName, isInfinite,
						maxUsagePerCart, maxUsageByUser, isInfinitePerUser, users, excludeUsers, comments, couponType,
						percentage, freeShipping, showInMyMyntra, triggerPoint, minCount, maxCount, filePath, maxUsers,
						suffixLength, expiryDaysForRule, expiryDaysFromToday };

				payload = utilities
						.readFileAsString("./Data/payloads/JSON/coupon/updaterulebasedcoupontemplate");
				payload = utilities.preparepayload(payload, strPayload);
				log.info("payload is" + payload);

			}

			MyntraService service = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(),
					APINAME.CREATECOUPONTEMPLATE.toString(), init.Configurations, payload);
			HashMap<String,String> getParam = new HashMap<String,String>();
			getParam.put("user", User);

			RequestGenerator req = new RequestGenerator(service, getParam);
			jsonResp = req.respvalidate.returnresponseasstring();
			log.info("Response" + jsonResp);

			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
					successMSg.equals("Successfully created template") ? "SUCCESS" : "ERROR");
			id = JsonPath.read(jsonResp, "$.data.id").toString();

		} else {
			String[] strUpdatePayload = new String[] { randomPrefix, description, templateType, couponTag,
					couponFundingType, maxCartTotal, minCartTotal, times, startDate, endDate, status, groupName,
					isInfinite, maxUsagePerCart, maxUsageByUser, isInfinitePerUser, users, excludeUsers, comments,
					couponType, percentage, freeShipping, showInMyMyntra, triggerPoint, minCount, maxCount, filePath,
					maxUsers, suffixLength };

			String updatePayload = utilities
					.readFileAsString("./Data/payloads/JSON/coupon/updatecoupontemplate");
			updatePayload = utilities.preparepayload(updatePayload, strUpdatePayload);
			log.info("payload is" + updatePayload);

			MyntraService serviceUpdate = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(),
					APINAME.UPDATECOUPONTEMPLATE.toString(), init.Configurations, updatePayload);
			HashMap<String,String> getUpdateParam = new HashMap<String,String>();
			getUpdateParam.put("user", updateUser);

			serviceUpdate.URL = serviceUpdate.URL + "/" + id;

			log.info(serviceUpdate.URL);
			RequestGenerator req = new RequestGenerator(serviceUpdate, getUpdateParam);
			jsonResp = req.respvalidate.returnresponseasstring();
			log.info("Response :: " + jsonResp);

			if (successMSg.contains("Error")) {
				Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
						successMSg);
			} else {
				Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
						successMSg.equals("Successfully updated template created a new version") ? "SUCCESS" : "ERROR");
				id = JsonPath.read(jsonResp, "$.data.id").toString();
			}

		}

		if (couponFundingType.equals("Seller") && successMSg.contains("Successfully")) {
			approvalStatus = approveCouponsByAlphaSellers(JsonPath.read(jsonResp, "$.data.id").toString(),
					approvalCount);
		}
		if (templateType.equals("RuleBased") && approvalStatus.equals("Subscribe")){
			createRuleBasedCoup(JsonPath.read(jsonResp, "$.data.id").toString());
		}

		if (successMSg.contains("Successfully") && approvalStatus.equals("Subscribe")) {

			String query = "SELECT * FROM pricingengine.coupon_request_template where id="
					+ JsonPath.read(jsonResp, "$.data.id").toString();
			Map<String, Object> rs1 = DBUtilities.exSelectQueryForSingleRecord(query, "pricingengine");

			log.info(rs1.get("id").toString());
			Assert.assertNotNull(rs1.get("master_template_id").toString());

			Assert.assertNotNull(rs1.get("version").toString());
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.id").toString(), (rs1.get("id").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.prefix").equals(randomPrefix)
					&& randomPrefix.equals(rs1.get("prefix").toString()));
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.description").equals(description)
					&& description.equals(rs1.get("description").toString()));
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.couponTag").equals(couponTag)
					&& couponTag.equals(rs1.get("coupon_tag").toString()));
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.couponFundingType").equals(couponFundingType)
					&& couponFundingType.equals(rs1.get("funding_type").toString()));
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.templateType").equals(templateType)
					&& templateType.equals(rs1.get("template_type").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.minCartTotal").toString().equals(minCartTotal)
					&& minCartTotal.equals(Double.toString(Double.parseDouble(rs1.get("min_cart_total").toString()))));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.maxCartTotal").toString().equals(maxCartTotal)
					&& maxCartTotal.equals(Double.toString(Double.parseDouble(rs1.get("max_cart_total").toString()))));

			Assert.assertNotNull((rs1.get("start_date").toString()));
			Assert.assertNotNull(rs1.get("end_date").toString());
			Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.startDate"));
			Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.endDate"));

			Assert.assertEquals("Active", rs1.get("request_status").toString());

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.status").equals(status)
					&& status.equals(rs1.get("status").toString()));
			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.provider"), rs1.get("provider").toString());
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.groupName").equals(groupName)
					&& groupName.equals(rs1.get("group_name").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.isInfinite").toString()
					.equals(isInfinite.equals("1") ? "true" : "false")
					&& JsonPath.read(jsonResp, "$.data.isInfinite").toString()
							.equals(rs1.get("is_infinite").toString()));

			Assert.assertEquals(JsonPath.read(jsonResp, "$.data.auditType").toString(),
					rs1.get("audit_type").toString());
			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.maxUsagePerCart").toString().equals(maxUsagePerCart)
					&& maxUsagePerCart.equals(rs1.get("max_usage_per_cart").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.maxUsageByUser").toString().equals(maxUsageByUser)
					&& maxUsageByUser.equals(rs1.get("max_usage_by_user").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.isInfinitePerUser").toString()
					.equals(isInfinitePerUser.equals("1") ? "true" : "false")
					&& JsonPath.read(jsonResp, "$.data.isInfinitePerUser").toString()
							.equals(rs1.get("is_infinite_per_user").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.users").toString().equals(users)
					&& users.equals(rs1.get("users").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.excludeUsers").toString().equals(excludeUsers)
					&& excludeUsers.equals(rs1.get("exclude_users").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.comments").toString().equals(comments)
					&& comments.equals(rs1.get("comments").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.couponType").toString().equals(couponType)
					&& couponType.equals(rs1.get("coupon_type").toString()));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.percentage").toString().equals(percentage)
					&& percentage.equals(Double.toString(Double.parseDouble(rs1.get("percentage").toString()))));

			Assert.assertTrue(JsonPath.read(jsonResp, "$.data.showInMyMyntra").toString()
					.equals(showInMyMyntra.equals("1") ? "true" : "false")
					&& showInMyMyntra.equals(rs1.get("show_in_my_myntra").toString().equals("true") ? "1" : "0"));

			if (minCount == null || minCount.equals("")) {
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.minCount"));
				Assert.assertNull(rs1.get("min_count") == "0" ? null : rs1.get("min_count"));
			} else
				Assert.assertTrue(JsonPath.read(jsonResp, "$.data.minCount").toString().equals(minCount)
						&& minCount.equals(rs1.get("min_count").toString()));

			if (maxCount == null || maxCount.equals("")) {
				Assert.assertNull(JsonPath.read(jsonResp, "$.data.maxCount"));
				Assert.assertNull(rs1.get("max_count"));
			} else
				Assert.assertTrue(JsonPath.read(jsonResp, "$.data.maxCount").toString().equals(maxCount)
						&& maxCount.equals(rs1.get("max_count").toString()));

		

		/*
		 * Validating xcart_discount_coupons table - whether coupon got created
		 * or not
		 */
		if (couponFundingType.equals("Platform") || approvalStatus.equals("Subscribe")) {

			String qry_xcart = "select * from xcart_discount_coupons where templateId="
					+ id;

			Map<String, Object> rs_xcart = DBUtilities.exSelectQueryForSingleRecord(qry_xcart, "myntra_coupon");

			Assert.assertEquals((JsonPath.read(jsonResp, "$.data.id")).toString(),
					rs_xcart.get("templateId").toString());

			Assert.assertTrue(rs_xcart.get("coupon").toString().contains(randomPrefix));
			Assert.assertEquals(description, rs_xcart.get("description").toString());
			// Assert.assertEquals(couponTag,
			// rs_xcart.getString("coupon_type"));

			if (couponFundingType.equals("Platform"))
				Assert.assertEquals("true", rs_xcart.get("isPlatformFunded").toString());
			else
				Assert.assertEquals("false", rs_xcart.get("isPlatformFunded").toString());

			Assert.assertEquals(Double.parseDouble(minCartTotal),
					Double.parseDouble(rs_xcart.get("minimum").toString()));
			Assert.assertEquals(Double.parseDouble(maxCartTotal),
					Double.parseDouble(rs_xcart.get("maximum").toString()));

			Assert.assertEquals(times, rs_xcart.get("times").toString());

			Assert.assertNotNull(rs_xcart.get("startdate").toString());
			Assert.assertNotNull(rs_xcart.get("expire").toString());

			Assert.assertEquals("A", rs_xcart.get("status").toString());

			Assert.assertEquals(groupName, rs_xcart.get("groupName").toString());
			Assert.assertEquals(isInfinite.equals("1") ? "true" : "false", rs_xcart.get("isInfinite").toString());
			Assert.assertEquals(maxUsagePerCart, rs_xcart.get("maxUsagePerCart").toString());

			Assert.assertEquals(isInfinitePerUser.equals("1") ? "true" : "false",
					rs_xcart.get("isInfinitePerUser").toString());
			Assert.assertNotNull(rs_xcart.get("users").toString());
			Assert.assertEquals(excludeUsers,
					rs_xcart.get("excludeUsers") == null ? "" : rs_xcart.get("excludeUsers").toString());
			if (rs_xcart.get("paymentMethod") != null)
				Assert.assertEquals(JsonPath.read(jsonResp, "$.data.paymentMethod").toString(),
						rs_xcart.get("paymentMethod").toString());

			Assert.assertEquals(comments, rs_xcart.get("comments").toString());

			if (couponType.equals("max_percentage_dual")) {
				Assert.assertEquals(
						(JsonPath.read(jsonResp, "$.data.maxPercent") == null) ? "0.00"
								: (JsonPath.read(jsonResp, "$.data.maxPercent")).toString(),
						Double.toString(Double.parseDouble(rs_xcart.get("MRPAmount").toString())));
				Assert.assertEquals(percentage,
						Double.toString(Double.parseDouble(rs_xcart.get("MRPpercentage").toString())));
			} else if (couponType.equals("percentage")) {
				Assert.assertEquals(percentage,
						Double.toString(Double.parseDouble(rs_xcart.get("MRPpercentage").toString())));
			} else if (couponType.equals("absolute")) {
				Assert.assertEquals(
						JsonPath.read(jsonResp, "$.data.amount") == null ? "0.00"
								: JsonPath.read(jsonResp, "$.data.amount").toString(),
						Double.toString(Double.parseDouble(rs_xcart.get("MRPAmount").toString())));
			} else if (couponType.equals("dual")) {
				Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxAmount"), rs_xcart.get("MRPAmount").toString());
				Assert.assertEquals(percentage, rs_xcart.get("MRPpercentage").toString());
			}

			Assert.assertEquals(couponType, rs_xcart.get("couponType").toString());

			Assert.assertEquals(freeShipping, rs_xcart.get("freeShipping").toString().equals("true") ? "1" : "0");
			Assert.assertEquals(showInMyMyntra, rs_xcart.get("showInMyMyntra").toString().equals("true") ? "1" : "0");

			Assert.assertEquals(minCount.equals("") ? "0" : minCount, rs_xcart.get("min_count").toString());
			Assert.assertEquals(maxCount.equals("") ? "0" : maxCount, rs_xcart.get("max_count").toString());
			Assert.assertNotNull(rs_xcart.get("timeCreated").toString());
			Assert.assertNotNull(rs_xcart.get("lastEdited").toString());
			Assert.assertEquals(updateUser, rs_xcart.get("lastEditedBy").toString());

		}
	}
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusCode").toString(),
				successMSg.contains("Successfully") ? "10001" : "1002");
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
				successMSg);
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
				successMSg.contains("Successfully") ? "SUCCESS" : "ERROR");
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.totalCount").toString(),
				successMSg.contains("Successfully") ? "1" : "0");

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "listcoupontemplate")
	public void CouponService_listCouponTemplate(String id, String successMSg)
			throws ClassNotFoundException {
		MyntraService service = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(), APINAME.LISTCOUPONTEMPLATE.toString(),
				init.Configurations);
		service.URL = service.URL + "/" + id;

		RequestGenerator req = new RequestGenerator(service);

		String jsonResp = req.respvalidate.returnresponseasstring();

		if (successMSg.equals("Successfully Fetched template details") && !(id.equals("")) && !(id.equals(null))) {
			// DB validation
			String query = "SELECT * FROM coupon_request_template where id=" + id;
			Map<String, Object> rs1 = DBUtilities.exSelectQueryForSingleRecord(query,"pricingengine");

					Assert.assertEquals((int) (JsonPath.read(jsonResp, "$.data.id")), Integer.parseInt(rs1.get("id").toString()));

					Assert.assertEquals((int) (JsonPath.read(jsonResp, "$.data.masterTemplateId")),
							Integer.parseInt(rs1.get("master_template_id").toString()));
					Assert.assertEquals((int) (JsonPath.read(jsonResp, "$.data.version")), Integer.parseInt(rs1.get("version").toString()));

					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.prefix"), rs1.get("prefix").toString());
					Assert.assertEquals(
							JsonPath.read(jsonResp, "$.data.suffixLength") == null ? 0
									:  Integer.parseInt((JsonPath.read(jsonResp, "$.data.suffixLength")).toString()),
							Integer.parseInt(rs1.get("suffix_Length").toString()));

					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.description"), rs1.get("description").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.couponTag"), rs1.get("coupon_tag").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.couponFundingType"),
							rs1.get("funding_type").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.templateType"), rs1.get("template_type").toString());
					Assert.assertEquals((double) (JsonPath.read(jsonResp, "$.data.minCartTotal")),
							Double.parseDouble(rs1.get("min_cart_total").toString()));
					
					if (rs1.get("max_cart_total")!=null)
						Assert.assertEquals(
								JsonPath.read(jsonResp, "$.data.maxCartTotal") == null ? 0.0
										: (double) (JsonPath.read(jsonResp, "$.data.maxCartTotal")),
								Double.parseDouble(rs1.get("max_cart_total").toString()));

					Assert.assertEquals((int) (JsonPath.read(jsonResp, "$.data.times")), Integer.parseInt(rs1.get("times").toString()));

					Assert.assertNotNull(rs1.get("start_date").toString());
					Assert.assertNotNull(rs1.get("end_date").toString());
					
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.startDate"));
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.endDate"));
					
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.status"), rs1.get("status").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.groupName"), rs1.get("group_name").toString());
					Assert.assertEquals(Boolean.parseBoolean((JsonPath.read(jsonResp, "$.data.isInfinite")).toString()),
							Boolean.parseBoolean(rs1.get("is_infinite").toString()));
					
					Assert.assertEquals((int) JsonPath.read(jsonResp, "$.data.maxUsagePerCart"),
							Integer.parseInt(rs1.get("max_usage_per_cart").toString()));
					Assert.assertEquals((int) JsonPath.read(jsonResp, "$.data.maxUsageByUser"),
							Integer.parseInt(rs1.get("max_usage_by_user").toString()));
					Assert.assertEquals(Boolean.parseBoolean((JsonPath.read(jsonResp, "$.data.isInfinitePerUser")).toString()),
							Boolean.parseBoolean(rs1.get("is_infinite_per_user").toString()));
					
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.users"), rs1.get("users").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.excludeUsers"), rs1.get("exclude_users").toString());
					
					if (rs1.get("payment_method") !=null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.paymentMethod").toString(),
								rs1.get("payment_method").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.couponType"), rs1.get("coupon_type").toString());
					
					if (rs1.get("amount")!=null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.amount")==null?0:Double.parseDouble((JsonPath.read(jsonResp, "$.data.amount")).toString()), Double.parseDouble(rs1.get("amount").toString()));
					
					if (JsonPath.read(jsonResp, "$.data.maxAmount")==null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxAmount"), rs1.get("max_amount"));
					else
						Assert.assertEquals(Double.parseDouble((JsonPath.read(jsonResp, "$.data.maxAmount").toString())), Double.parseDouble(rs1.get("max_amount").toString()));
					
					
					if (JsonPath.read(jsonResp, "$.data.maxPercent")==null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxPercent"), rs1.get("max_percent"));
					else
						Assert.assertEquals(Double.parseDouble(JsonPath.read(jsonResp, "$.data.maxPercent").toString()), Double.parseDouble(rs1.get("max_percent").toString()));
					
					Assert.assertEquals(Double.parseDouble((JsonPath.read(jsonResp, "$.data.percentage")).toString()),  Double.parseDouble(rs1.get("percentage").toString()));
					
					Assert.assertEquals((JsonPath.read(jsonResp, "$.data.freeShipping")).toString(),
							rs1.get("free_shipping").toString()=="false"?"0":"1");
					Assert.assertEquals(((JsonPath.read(jsonResp, "$.data.showInMyMyntra")).toString()),
							rs1.get("show_in_my_myntra").toString());
					
					if (rs1.get("min_count")!=null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.minCount")==null?0:(int) JsonPath.read(jsonResp, "$.data.minCount"), Long.parseLong(rs1.get("min_count").toString()));
					
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxCount")==null?null: Integer.parseInt(JsonPath.read(jsonResp, "$.data.maxCount").toString()),rs1.get("max_count")!=null?Integer.parseInt(rs1.get("max_count").toString()):rs1.get("max_count"));
					
					
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.createdOn"));
					Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.updatedOn"));
					Assert.assertNotNull(rs1.get("created_on").toString());
					Assert.assertNotNull(rs1.get("updated_on").toString());
					
					
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.createdBy"), rs1.get("created_by").toString());
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.updatedBy"), rs1.get("updated_by").toString());
					
					if (JsonPath.read(jsonResp, "$.data.maxUsers")==null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.maxUsers"), rs1.get("max_users"));
					else
						Assert.assertEquals(Integer.parseInt(JsonPath.read(jsonResp, "$.data.maxUsers").toString()), Integer.parseInt(rs1.get("max_users").toString()));
					
					Assert.assertEquals(JsonPath.read(jsonResp, "$.data.auditType"), rs1.get("audit_type").toString());
					
					if (rs1.get("template_type").toString().equals("Bulk")){
						Assert.assertNotNull(JsonPath.read(jsonResp, "$.data.filePath"));
						Assert.assertNotNull(rs1.get("file_path").toString());
					}else{
						Assert.assertNull((JsonPath.read(jsonResp, "$.data.filePath")));
						Assert.assertTrue(rs1.get("file_path")==null || rs1.get("file_path").toString()=="");
					}
					
					if (JsonPath.read(jsonResp, "$.data.expiryDaysForRule")==null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.expiryDaysForRule"),
								rs1.get("expiry_days_for_rule"));
					else
						Assert.assertEquals(Integer.parseInt(JsonPath.read(jsonResp, "$.data.expiryDaysForRule").toString()),
								Integer.parseInt(rs1.get("expiry_days_for_rule").toString()));

					if (JsonPath.read(jsonResp, "$.data.expiryDaysFromToday")==null)
						Assert.assertEquals(JsonPath.read(jsonResp, "$.data.expiryDaysFromToday"),
								rs1.get("expiry_days_for_coupon"));
					else
						Assert.assertEquals(Integer.parseInt(JsonPath.read(jsonResp, "$.data.expiryDaysFromToday").toString()),
								Integer.parseInt(rs1.get("expiry_days_for_coupon").toString()));
					
					Assert.assertEquals("Active", rs1.get("request_status").toString());

		} else if (successMSg.contains("Error")) {
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
					successMSg);
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
					successMSg.equals("Successfully Fetched template details") ? "SUCCESS" : "ERROR");
			Assert.assertEquals(JsonPath.read(jsonResp, "$.status.totalCount").toString(),
					successMSg.equals("Successfully Fetched template details") ? "1" : "0");
		} else {
			
			String query = "SELECT count(id) FROM coupon_request_template";
			
			Map<String, Object> rs1 = DBUtilities.exSelectQueryForSingleRecord(query,"pricingengine");

				Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
						successMSg);
				Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
						successMSg.equals("Successfully fetched templates") ? "SUCCESS" : "ERROR");
				Assert.assertEquals(JsonPath.read(jsonResp, "$.status.totalCount").toString(),
						successMSg.equals("Successfully fetched templates") ? rs1.get("count(id)").toString() : "0");
		}

	}

	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression",
			"ExhaustiveRegression" }, dataProvider = "getcoupontemplatebyattribute")
	public void CouponService_getCouponTemplateByAttribute(String startDate, String status, String pageIndex,
			String pageSize, String templateType, String createdBy, String updatedBy, String successMSg)
			throws ClassNotFoundException {
		MyntraService service = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(),
				APINAME.GETCOUPONTEMPLATEBYATTRIBUTE.toString(), init.Configurations);

		String queryString = "?";
		String condition = "";
		boolean firstArg = false;
		int totalRecords = 0;

		Date d_start_date = new Date(new Long(startDate));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String start_date = format.format(d_start_date);

		if (!(start_date.trim().isEmpty())) {
			queryString = queryString + "from=" + startDate;
			firstArg = true;
			condition = condition + " updated_on>='" + start_date + "'";
		}
		if (!(status.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";
				condition = condition + " and ";
			}
			queryString = queryString + "status=" + status;
			condition = condition + " request_status='" + status + "'";

		}
		if (!(createdBy.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";
				condition = condition + " and ";

			}
			queryString = queryString + "createdBy=" + createdBy;
			condition = condition + " created_by= '" + createdBy + "'";
		}
		if (!(updatedBy.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";
				condition = condition + " and ";

			}
			queryString = queryString + "updatedBy=" + updatedBy;
			condition = condition + " updated_by='" + updatedBy + "'";
		}
		if (!(pageIndex.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";

			}
			queryString = queryString + "pageIndex=" + pageIndex;
		}
		if (!(pageSize.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";
			}
			queryString = queryString + "pageSize=" + pageSize;

		}
		if (!(templateType.trim().isEmpty())) {
			if (firstArg) {
				queryString = queryString + "&";
				condition = condition + " and ";
			}
			queryString = queryString + "templateType=" + templateType;
			condition = condition + " template_type='" + templateType + "'";
		}
		service.URL = service.URL + queryString;

		log.info(service.URL);
		RequestGenerator req = new RequestGenerator(service);

		String jsonResp = req.respvalidate.returnresponseasstring();
		log.info(jsonResp);

			
		String queryGetRecord = "SELECT count(id) FROM coupon_request_template where " + condition;
		log.info("Query is : " + queryGetRecord);
		
		Map<String, Object> rs1 = DBUtilities.exSelectQueryForSingleRecord(queryGetRecord,"pricingengine");

		totalRecords= Integer.parseInt(rs1.get("count(id)").toString());
		log.info("totalRecords::::::" + totalRecords);
		
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusMessage").toString().replaceAll(",", ""),
				successMSg);
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.statusType"),
				successMSg.equals("Successfully fetched templates") ? "SUCCESS" : "ERROR");
		Assert.assertEquals(JsonPath.read(jsonResp, "$.status.totalCount").toString(),
				successMSg.equals("Successfully fetched templates") ? Integer.toString(totalRecords) : "0");
	}

	
	@Test(groups = { "Smoke", "Sanity", "Miniregression", "Regression", "ExhaustiveRegression", "Fox7Sanity"},dataProvider = "lockUserCoupon")
    public void CouponService_LockUserCouponSchemaVAlidation(String coupon,String user,String successMSg) {
		CommonUtils cu = new CommonUtils();
        MyntraService service = Myntra.getService(ServiceType.PORTAL_COUPONSERVICE, APINAME.LOCKUSERCOUPON, init.Configurations, new String[]{coupon,user},PayloadType.JSON, PayloadType.JSON);
        RequestGenerator req = new RequestGenerator(service);
        String response = req.respvalidate.returnresponseasstring();
        System.out.print(response);
        String jsonschema = new String();
        List<String> missingNodeList = new ArrayList();
        try
        {
            jsonschema = new Toolbox().readFileAsString("Data/SchemaSet/JSON/CouponService_LockUserCouponSchema");
            missingNodeList = cu.validateServiceResponseNodesUsingSchemaValidator(response, jsonschema);
            System.out.println("test" + missingNodeList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(missingNodeList + " nodes are missing in CouponService_LockUserCoupon API response", CollectionUtils.isEmpty(missingNodeList));


    }
	
	private void createMPDCoupon(String startDate, String ExpireDate ,String CouponName,String CreatedTime ,String mrpAmount, String mrpPercentage, String Description, String CouponType)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.CREATEFLASHCOUPON, init.Configurations,
				new String[] { startDate, ExpireDate,CouponName, CreatedTime,mrpAmount,
						mrpPercentage,Description,CouponType}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	
	
	
	private void modifyPercentageCoupon(String startDate, String lastEdited ,String CouponName,String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYPERCENTAGECOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println("Response in modify coupon--->>> " + req.respvalidate.returnresponseasstring());
	}
	
	private void modifyPercentageDualCoupon(String startDate, String lastEdited ,String CouponName,String mrpAmount,String mrpPercentage,String user)
	{
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.MODIFYPERCENTAGEDUALCOUPON, init.Configurations,
				new String[] { startDate, lastEdited, CouponName,mrpAmount,
						mrpPercentage,user}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());
	}
	private void getAndSetTokens(String userName, String password)
	{
		MyntraService serviceSignIn = Myntra.getService(ServiceType.PORTAL_IDP, APINAME.SIGNIN, init.Configurations, new String[]{ userName, password });
		RequestGenerator reqSignIn = new RequestGenerator(serviceSignIn);
		MultivaluedMap<String, Object> map = reqSignIn.response.getHeaders();
		System.out.println(reqSignIn.respvalidate.returnresponseasstring());
		
		for (Map.Entry entry : map.entrySet())
		{
			if (entry.getKey().toString().equalsIgnoreCase("xid"))
				xID = entry.getValue().toString();
		}
		xID = xID.substring((xID.indexOf("[") + 1), xID.lastIndexOf("]"));
		sXid = reqSignIn.respvalidate.GetNodeTextUsingIndex("xsrfToken");
		System.out.println(xID);
		System.out.println(sXid);
		if(sXid.contains("'"))
			sXid = sXid.substring(sXid.indexOf("'")+1, sXid.lastIndexOf("'"));
		else
			sXid = sXid.substring(sXid.indexOf("[")+1, sXid.lastIndexOf("]"));
	}
	
	private java.math.BigDecimal twoDecimalPlaces(final double d) {
	    return new java.math.BigDecimal(d).setScale(2, java.math.RoundingMode.HALF_UP);
	}
	
	private void createIntentRule(String couponName, String score)
	{
		startTime = String.valueOf(currentTime);
		endTime=String.valueOf(currentTime+4000);
		createMPDCoupon(startTime,endTime,couponName,startTime,"32","42","Test","max_percentage_dual");
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.CREATEINTENTRULE, init.Configurations,
				new String[] { couponName, score}, PayloadType.JSON, PayloadType.JSON);
		System.out.println( "json paylod---- >" + service.Payload);
		RequestGenerator req = new RequestGenerator(service);
		
		System.out.println(req.respvalidate.returnresponseasstring());
//		String jsonRes = req.respvalidate.returnresponseasstring();
//		String message = JsonPath.read(jsonRes, "$.status.statusMessage").toString().trim();
//		String message1 = JsonPath.read(jsonRes, "$.status.statusType").toString().trim();
//
//		System.out.println("================>>>> " +  message);
//		AssertJUnit.assertEquals(200, req.response.getStatus());
//		AssertJUnit.assertEquals("Intent rule doesn't created", "Success", message);
//		AssertJUnit.assertEquals("Intent rule doesn't created", "SUCCESS", message);
	}
	
	private void updateBulkLock(String lockId, String lockType,String statusCompleted,
            String lockedOn,String lockflag){
		
		
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_COUPONSERVICE,
				APINAME.UPDATEBULKLOCK, init.Configurations,
				new String[] { lockId, lockType,statusCompleted,lockedOn,lockflag}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("update lock URL -------\n" +service.URL);
		System.out.println("update lock Payload -------\n" +service.Payload);		
		RequestGenerator req = new RequestGenerator(service);
				

				
				AssertJUnit.assertEquals(200, req.response.getStatus());


		
		
		
	}
	
	private String generateRandomCoupon()
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
	
	
	 public void v_connectionPoolFor100Users_myntraemp20_fix(String login, String email) //String email
	    
	    { 
	        RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, "password1", "C", "M", "Mr", "FirstName", "LastName",email, "9898989898");//email
	        System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
	        log.info("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
	        
	    }
	 
	 /*
	 	Coupon template's helper methods
	 */
		public void tearDown_createTemplate(String id) throws ClassNotFoundException{
			String q_deleteFromSellers = "delete from coupon_request_sellers where coupon_template_id="+id;
			String q_deleteFromAudit = "delete from coupon_template_audit where coupon_template_id="+id;
			String q_deleteFromMasterReqTemplate = "delete from master_coupon_request_template where last_updated_template="+id;
			String q_deleteFromRequest = "delete from coupon_request_template where id="+id;
			
			Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(q_deleteFromSellers,"pricingengine");
			/*	
			 * once cascade delete is implemented for tables [master_coupon_request_template and coupon_request_sellers] - post that will implement this
			*/
		}
		
		
//		This will create rule based coupon. 
//		** Note - if the funding type is seller then first call approveCouponsByAlphaSellers
		public void createRuleBasedCoup(String id) throws ClassNotFoundException, IOException{
			String ruleId="";
			
			String q_ruleId = "select ruleId from coupon_generation_rules where templateId="+id;
			Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(q_ruleId,"myntra_coupon");
			
			ruleId = rs.get("ruleId").toString();
			log.info("Rule Id ::"+ruleId);
	        
        	String[] strPayload =new String[] { ruleId, "shweta.prasad@myntra.com"};
		    
		    String payload=utilities.readFileAsString("./Data/payloads/JSON/coupon/createcouponwithrules");
		    payload=utilities.preparepayload(payload,strPayload);
		    log.info("payload:: "+payload);
			    
			MyntraService service = new MyntraService(ServiceType.PORTAL_COUPONSERVICE.toString(), APINAME.CREATECOUPONWITHRULE.toString(),init.Configurations,payload);
			log.info("createcouponwithrules"+service);
			RequestGenerator req = new RequestGenerator(service);
			String jsonResp = req.respvalidate.returnresponseasstring();
			log.info(jsonResp);
			
			String successMsg=JsonPath.read(jsonResp,"$.status.statusMessage").toString();
			log.info(successMsg);
			Assert.assertTrue("Successfully created coupons for given users".trim().equalsIgnoreCase(successMsg.trim()));
			Assert.assertTrue("200".trim().equalsIgnoreCase(JsonPath.read(jsonResp,"$.status.statusCode").toString().replaceAll("[^A-Za-z0-9/ ]","")));
		}

//		This will approve coupon template by alpha sellers
		public String approveCouponsByAlphaSellers(String id, String approvalCount)  {
			
			String status= null;
			Boolean rejectedFlag=false;
			String sellerTemplateStatus="";
			try{
			        System.out.println("Id ::"+id);
			        
					String[] strApprovePayload =new String[] { id,"Subscribe", "Making "+id+ " Active"};
				    
				    String approvePayload=utilities.readFileAsString("./Data/payloads/JSON/coupon/coupontemplateaction");
				    approvePayload=utilities.preparepayload(approvePayload,strApprovePayload);
				    log.info("payload :: "+approvePayload);
					    
					MyntraService serviceApprove = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(), APINAME.COUPONTEMPLATEACTION.toString(),init.Configurations,approvePayload);
					log.info("createcouponwithrules"+serviceApprove.URL);
					
					String[] strRejectPayload =new String[] { id,"Rejected", "Making "+id+ " Rejected"};
				    
				    String rejectPayload=utilities.readFileAsString("./Data/payloads/JSON/coupon/coupontemplateaction");
				    rejectPayload=utilities.preparepayload(rejectPayload,strRejectPayload);
				    log.info("Payload :: "+rejectPayload);
					    
					MyntraService serviceRejected = new MyntraService(ServiceType.PORTAL_PRICINGENGINECOUPON.toString(), APINAME.COUPONTEMPLATEACTION.toString(),init.Configurations,rejectPayload);
					log.info("createcouponwithrules"+serviceRejected.URL);
					
					String [] sellerIds = new String[]{"19","21","25","29","84"};
					HashMap<String,String> getParam = new HashMap<String,String>();
					RequestGenerator req = null;
					for (int i =0; i <sellerIds.length;i++){
						getParam.clear();
						req=null;
						getParam.put("sellerId", sellerIds[i]);
						getParam.put("sellerEmail", "x4@myntra.com");
						log.info("SellerIds"+getParam.toString());
						
						if (i<=(Integer.parseInt(approvalCount)-1)){
							req = new RequestGenerator(serviceApprove,getParam);
							status="Subscribe";
							sellerTemplateStatus=status;
						}else{
							req = new RequestGenerator(serviceRejected,getParam);
							status="Rejected";
							if (rejectedFlag==true)
								sellerTemplateStatus="WaitingForApproval";
							else
								sellerTemplateStatus= status;
							rejectedFlag= true;
						}
						String jsonResp = req.respvalidate.returnresponseasstring();
						log.info(jsonResp);
						
						String successMsg=JsonPath.read(jsonResp,"$.status.statusMessage").toString().replaceAll("[^A-Za-z0-9/ ]","");
						System.out.print(successMsg);
						Assert.assertTrue("Successfully finished actions".trim().equalsIgnoreCase(successMsg.trim()));
						Assert.assertTrue("1001".equals(JsonPath.read(jsonResp,"$.status.statusCode").toString()));
						
						checkSellerStatus(id, sellerIds[i],sellerTemplateStatus);
						
					}
					
					String query = "SELECT * FROM coupon_request_template where id="
							+ id;
					Map<String, Object> rs_check = DBUtilities.exSelectQueryForSingleRecord(query,"pricingengine");
//					if (rs_check.get("template_type").toString().contains("Bulk"))
						Thread.sleep(5000); // bulk takes time to get updated in db 
					
					Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query,"pricingengine");
					
					if (status.equals("Subscribe"))
						Assert.assertEquals("Active", rs.get("request_status"));
					else
						Assert.assertEquals(status, rs.get("request_status"));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		
		public void checkSellerStatus(String id, String sellerId, String status) throws ClassNotFoundException{
			
			String query = "SELECT * FROM coupon_request_sellers where coupon_template_id="+id +" and seller_id="+sellerId;
			
			Map<String, Object> rs = DBUtilities.exSelectQueryForSingleRecord(query,"pricingengine");
			Assert.assertEquals(status, rs.get("status"));
		}
	     //calling Stored procedure "release_aquired_lock" in myntra db to delete rows with lock type acquired

@BeforeClass
	public void deleteAllRowsWithLockTypeAquired() {
	System.out.print("in before class");
		Connection con = null;
		Statement stmt = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://54.251.240.111:3306", "myntraAppDBUser", "9eguCrustuBR");
			String query = " CALL `release_aquired_lock`()";
			System.out.println("query is : " + query);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
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

}
