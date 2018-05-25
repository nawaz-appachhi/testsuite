package com.myntra.apiTests.portalservices.all;

import com.myntra.apiTests.dataproviders.DOTDDP;
import com.myntra.apiTests.portalservices.dealservice.DOTDHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class DealsOfTheDay extends DOTDDP {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealsOfTheDay.class);
	APIUtilities apiUtil = new APIUtilities();
	static String rcvSytleId="";
	StyleServiceApiHelper styleServiceApiHelper=new StyleServiceApiHelper();
	DOTDHelper dotdHelp= new DOTDHelper();
	ArrayList dealIds= new ArrayList();
	
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealDP", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_createNewDeal_single(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealShowFalseDP", priority=1,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.ShowInCard should be false")
	public void dotd_createNewDeal_showIncardFalse(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","false", showInCardTF);

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealvfDiscisZeroDP", priority=2,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.VFDiscount should be zero")
	public void dotd_createNewDeal_vfDiscountisZero(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealcfDiscisZeroDP", priority=3,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.VFDiscount should be zero")
	public void dotd_createNewDeal_cfDiscountisZero(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealfloatDiscDP", priority=4,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.VFDiscount should be zero")
	public void dotd_createNewDeal_floatDiscount(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealSoldOutDP", priority=5,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.VFDiscount should be zero")
	public void dotd_createNewDeal_soldOut(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard, String styleStartTime, String styleEndTime)
	{
			
		RequestGenerator request= dotdHelp.getRequestSoldOut(APINAME.CREATENEWDEALSOLDOUT, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard, styleStartTime, styleEndTime);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	

	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealMultipleDP", priority=6,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id \n 4.VFDiscount should be zero")
	public void dotd_createnewdeal_multipleStyles(String name, String desc, String startTime, String endTime, 
			String styleId1, String vfDiscPercent1,String cfDiscPercent1, String showInCard1, String styleId2, String vfDiscPercent2,String cfDiscPercent2, String showInCard2,String styleId3, String vfDiscPercent3,String cfDiscPercent3, String showInCard3,String styleId4, String vfDiscPercent4,String cfDiscPercent4, String showInCard4,String styleId5, String vfDiscPercent5,String cfDiscPercent5, String showInCard5,String styleId6, String vfDiscPercent6,String cfDiscPercent6, String showInCard6,String styleId7, String vfDiscPercent7,String cfDiscPercent7, String showInCard7)
	{
			
		RequestGenerator request= dotdHelp.getRequestMultipleStyles(APINAME.CREATEMULTIPLESTYLEDEAL, name, desc, startTime, endTime, styleId1, vfDiscPercent1,cfDiscPercent1, showInCard1, styleId2, vfDiscPercent2, cfDiscPercent2, showInCard2, styleId3, vfDiscPercent3,cfDiscPercent3,  showInCard3, styleId4,  vfDiscPercent4, cfDiscPercent4,  showInCard4, styleId5, vfDiscPercent5, cfDiscPercent5,  showInCard5,styleId6,  vfDiscPercent6, cfDiscPercent6, showInCard6, styleId7, vfDiscPercent7, cfDiscPercent7, showInCard7);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:" + id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);

	}
	
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealDP", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_getDeal(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);
		//int dealId = Integer.parseInt(id);
	
		RequestGenerator req = dotdHelp.getDeals(id);
		String getDealResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting get deal API response : \n\n"+getDealResponse+"\n");
		String getDealId = JsonPath.read(getDealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("Deal Id Doesn't match", getDealId, id);
	}
	
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealDP", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_getAllDeal_Future(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);
		//int dealId = Integer.parseInt(id);
	
		RequestGenerator req = dotdHelp.getAllDeals();
		String getDealResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting get deal API response : \n\n"+getDealResponse+"\n");
		String getDealId = JsonPath.read(getDealResponse, "$..id").toString().replace("\"", "").trim();
		
		AssertJUnit.assertEquals("Deal Id Doesn't match", getDealId, getDealId );
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealDP", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id  \n 4.Verfiy the same deal id for Future and Expired Deal")
	public void dotd_getAllDeal_futureExpired(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);
		//int dealId = Integer.parseInt(id);
	
		RequestGenerator req = dotdHelp.getAllDealsFE();
		String getDealResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting get deal API response : \n\n"+getDealResponse+"\n");
		String getDealId = JsonPath.read(getDealResponse, "$..id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("deal id from getall deals"+getDealId);
		AssertJUnit.assertEquals("Deal Id Doesn't match", getDealId, id);
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealCDP", priority=0,description="\n 1.Create new deal \n 2.Verify 200 status code \n 3.Note down the deal Id \n 4.Verify the same deal id for Current deal")
	public void dotd_getAllDeal_Current(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$..id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);
		//int dealId = Integer.parseInt(id);
		
		try {
			TimeUnit.SECONDS.sleep(90);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		//dealIds= JsonPath.read(showInCardTF, "$..id" );
		
	
	
		RequestGenerator req = dotdHelp.getAllDealsC();
		String getDealResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting get deal current API response : \n\n"+getDealResponse+"\n");
		String getDealId = JsonPath.read(getDealResponse, "$..id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println(getDealId);
		
		AssertJUnit.assertEquals("Deal Id Doesn't match", getDealId, id);
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "createNewDealDP", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_getAllDeal_expired(String name, String desc, String startTime, String endTime, 
			String styleId, String vfDiscPercent,String cfDiscPercent, String showInCard)
	{
			
		RequestGenerator request= dotdHelp.getRequest(APINAME.CREATENEWDEALS, name, desc, startTime, endTime, styleId, vfDiscPercent, cfDiscPercent, showInCard);
		System.out.println("Response of create deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("createnewdeal API is not working",200, request.response.getStatus());
		
		String id= JsonPath.read(dealResponse, "$.id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		String showInCardTF= JsonPath.read(dealResponse, "$..showInCard").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		System.out.println("Id of Deal generated:"+id);
		AssertJUnit.assertEquals("Error in showIncard","true", showInCardTF);
		//int dealId = Integer.parseInt(id);
	
		RequestGenerator req = dotdHelp.getAllDealsE();
		String getDealResponse = req.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting get deal API response : \n\n"+getDealResponse+"\n");
		String getDealId = JsonPath.read(getDealResponse, "$..id").toString().replace("[", "").replace("]", "").replace("\"", "").trim();
		
		AssertJUnit.assertEquals("Deal Id Doesn't match", getDealId, id);
		
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "userSubscribe", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_userSubscribed(String name)
	{
		RequestGenerator request= dotdHelp.userSubscribed(APINAME.USERSUBSCRIBED, name);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("Subscribe API is not working",204, request.response.getStatus());
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "userSubscribeFalse", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_userSubscribed_false(String name)
	{
		RequestGenerator request= dotdHelp.userSubscribedFalse(APINAME.USERSUBSCRIBEDFALSE, name);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("Subscribe API is not working",204, request.response.getStatus());
	}
	
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "isUserSubscribe", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_isUserSubscribed_True(String uidx)
	{
		RequestGenerator request= dotdHelp.userSubscribedFalse(APINAME.USERSUBSCRIBED, uidx);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("Subscribe API is not working",204, request.response.getStatus());
		
		RequestGenerator request1= dotdHelp.isUserSubscribed(uidx);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse1=request1.respvalidate.returnresponseasstring();
		
		System.out.println("Value of Subscribed is" + dealResponse1);
		AssertJUnit.assertEquals("Subscribe API is not working",200, request1.response.getStatus());
		
		//AssertJUnit.assertEquals("Is User Subscribe API Response is True",false, dealResponse1);
		
		
	}
	
	@Test
	(groups = {"Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" }, 
			dataProvider = "isUserSubscribe", priority=0,description="\n 1. Create new deal \n 2. Verify 200 status code \n 3.Note down the deal Id ")
	public void dotd_isUserSubscribed_false(String name)
	{
		RequestGenerator request= dotdHelp.userSubscribedFalse(APINAME.USERSUBSCRIBEDFALSE, name);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse=request.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals("Subscribe API is not working",204, request.response.getStatus());
		
		RequestGenerator request1= dotdHelp.isUserSubscribed(name);
		System.out.println("Response of user subscribe deal "+ request.respvalidate.returnresponseasstring());
		String dealResponse1=request1.respvalidate.returnresponseasstring();
		
		System.out.println("Value of Subscribed is" + dealResponse1);
		AssertJUnit.assertEquals("Subscribe API is not working",200, request1.response.getStatus());
		
		//AssertJUnit.assertEquals("Is Subscribe API Response is True",false, dealResponse1);
		
		
	}
	
	
	
	
}

