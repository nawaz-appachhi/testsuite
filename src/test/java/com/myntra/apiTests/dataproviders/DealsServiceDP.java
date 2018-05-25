package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.myntra.apiTests.portalservices.OrderFlowWIthDiscountHelper.OrderFlowWIthDiscountServiceHelper;
import com.myntra.apiTests.portalservices.dealservice.DealServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.legolas.Commons;

/**
 * @author shankara.c
 *
 */
public class DealsServiceDP extends DealServiceHelper
{

	Random random = new Random();
	public long currentTime = System.currentTimeMillis()/1000;
	String startTime = "" , endTime = "";
	String styleId1 = "", styleId2 = "", styleId3 = "";
	List<Integer> dealIds = new ArrayList<>();
	List<Integer> dealIds1 = new ArrayList<>();
	static OrderFlowWIthDiscountServiceHelper OrderDiscounhelper = new OrderFlowWIthDiscountServiceHelper();

	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	Commons commonUtil = new Commons();

	List<Integer> styleIdNike = styleHelper.performSeachServiceToGetStyleIds("nike", "200", "true", "false");
	List<Integer> styleIdAdidas = styleHelper.performSeachServiceToGetStyleIds("Adidas", "200", "true", "false");
	List<Integer> styleIdPuma = styleHelper.performSeachServiceToGetStyleIds("Puma", "200", "true", "false");
	List<Integer> styleIdJackets = styleHelper.performSeachServiceToGetStyleIds("Jackets", "200", "true", "false");
	List<Integer> styleIdShoes = styleHelper.performSeachServiceToGetStyleIds("Shoes", "200", "true", "false");
	List<Integer> styleIdJeans = styleHelper.performSeachServiceToGetStyleIds("Jeans", "200", "true", "false");
	List<Integer> styleIdShirts = styleHelper.performSeachServiceToGetStyleIds("Shirts", "200", "true", "false");
	List<Integer> styleIdTshirts = styleHelper.performSeachServiceToGetStyleIds("Tshirts", "200", "true", "false");
	List<Integer> styleIdSunglasses = styleHelper.performSeachServiceToGetStyleIds("Sunglasses", "200", "true", "false");
	List<Integer> styleIdWallets = styleHelper.performSeachServiceToGetStyleIds("Wallets", "200", "true", "false");
	List<Integer> styleIdWatches = styleHelper.performSeachServiceToGetStyleIds("watches", "200", "true", "false");
	
	Object[] styles = sumOfAllStyleIds();

	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewDealDataProvider(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] {arr1,arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31};
		
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10,20);
	}

	/**
	 * create invisible deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createInvisibleNewDealDP(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[31].toString(), "0" };
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[32].toString(), "1"  };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[33].toString(), "1"  };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+15000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[34].toString(), "0"  };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	

	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewDealDataProvider_negative(ITestContext testContext)
	{
		//start time
		startTime = String.valueOf(currentTime-1000);
		endTime = String.valueOf(currentTime+1000);
		String[] arr1 = { "All_Essentials Today", "All essentials for you Today", "1", startTime, endTime, "false", "1", 
				"0", "http://lorempixel.com/478/335/fashion/2/", styles[35].toString(), "1" };
		// wrong expiry date
		startTime = String.valueOf(currentTime+1900);
		endTime = String.valueOf(currentTime);
		String[] arr2 = { "All_Essentials Today", "All essentials for you Today", "0", startTime, endTime, "false", "1", 
				"40.0", "https://summeressentials.com", styles[36].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime-5000);
		String[] arr3 = { "All_Essentials Today", "All essentials for you Today", "0", startTime, endTime, "true", "1", 
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[37].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime-1400);
		String[] arr4 = { "All_Essentials Today", "All essentials for you Today", "1", startTime, endTime, "false", "1", 
				"30.25", "https://summeressentials.com", styles[38].toString(), "3" };
		//same start time and end time
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime);
		String[] arr5 = { "All_Essentials Today", "All essentials for you Today", "1", startTime, endTime, "false", "1", 
				"0", "http://lorempixel.com/478/335/fashion/2/", styles[39].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+1000);
		String[] arr6 = { "All_Essentials Today", "All essentials for you Today", "1", startTime, endTime, "false", "1", 
				"-60", "http://lorempixel.com/478/335/fashion/2/", styles[40].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr7 = { "All_Essentials Today", "All essentials for you Today", "0", startTime, endTime, "true", "1", 
				"110", "http://lorempixel.com/478/335/fashion/2/", styles[41].toString(), "1" };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,4);
	}

	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewDealDataProvider_negative1(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+900);
		endTime = String.valueOf(currentTime+1500);
		String[] arr1 = { "All_Essentials Today", "All essentials for you Today", "10", startTime, endTime, "false", "1", 
				"40", "https://summeressentials.com", styles[42].toString(), "0" };
		
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+1400);
		String[] arr2 = { "All_Essentials Today", "All essentials for you Today", "3", startTime, endTime, "false", "1", 
				"30", "https://summeressentials.com", styles[43].toString(), "5" };

		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewDealDataProvider_negative2(ITestContext testContext)
	{
		styleId1 = styles[44].toString(); 
		String startTime1 = String.valueOf(currentTime+1000);
		String endTime1 = String.valueOf(currentTime+2500);
		String startTime2 = String.valueOf(currentTime+500);
		String endTime2 = String.valueOf(currentTime+1500);
		String startTime3 = String.valueOf(currentTime+1500);
		String endTime3 = String.valueOf(currentTime+3000);
		String startTime4 = String.valueOf(currentTime+500);
		String endTime4 = String.valueOf(currentTime+5000);
		String startTime5 = String.valueOf(currentTime+1500);
		String endTime5 = String.valueOf(currentTime+2500);
		
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, "All_Essentials Today", "All essentials for you Today", "0",
				startTime1, endTime1,  "false", "0", "30", "https://summeressentials.com", styleId1, "0" );
		
		String[] arr2 = { "All_Essentials Today", "All essentials for you Today", "0", startTime2, endTime2, "false", "0", 
				"60", "https://summeressentials.com", styleId1, "1" };
		String[] arr3 = { "All_Essentials Today", "All essentials for you Today", "0", startTime3, endTime3, "true", "1", 
				"40", "https://summeressentials.com", styleId1, "0" };		
		String[] arr4 = { "All_Essentials Today", "All essentials for you Today", "1", startTime4, endTime4, "false", "1", 
				"30", "https://summeressentials.com", styleId1, "2" };
		String[] arr5 = { "All_Essentials Today", "All essentials for you Today", "1", startTime5, endTime5, "true", "0", 
				"80", "https://summeressentials.com", styleId1, "0" };
		
		Object[][] dataSet = new Object[][] { arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return "1412044920", "1412053800"
	 */
	@DataProvider
	public Object[][] createNewDealWithMultipleStyleIdsChannelTypesDP(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+25000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "1", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[45].toString(), styles[46].toString(), styles[47].toString(), 
				"0", "1", "0" };
		
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+20000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "1", startTime, endTime,  "true", "1", 
				"60", "https://summeressentials.com", styles[48].toString(), styles[49].toString(), styles[50].toString(),
				"0", "0", "1"};
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "2", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[51].toString(), styles[52].toString(), styles[53].toString(), 
				"1", "1", "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+7000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "1", startTime, endTime,  "true", "0", 
				"60", "https://summeressentials.com", styles[54].toString(), styles[55].toString(), styles[56].toString(),
				"0", "1", "1"};
		
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "2", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[57].toString(), styles[58].toString(), styles[59].toString(), 
				"1", "1", "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+10000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[60].toString(), styles[61].toString(), styles[62].toString(),
				"0", "0", "1"};
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[63].toString(), styles[64].toString(), styles[65].toString(),
				"0", "0", "0"};
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+7000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[66].toString(), styles[67].toString(), styles[69].toString(),
				"0", "1", "0"};
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(),3,5);
	}

	/**
	 * create deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return "1412044920", "1412053800"
	 */
	@DataProvider
	public Object[][] createNewDealDataProvider_duplicate_negative(ITestContext testContext)
	{
		styleId1 = styles[70].toString(); 
		styleId2 = styles[71].toString(); 
		styleId3 = styles[72].toString(); 
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+1000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "1", startTime, endTime, "true", "1", "30",
				"https://summeressentials.com", styleId1, "0", 
				"failed", "There already exists a deal between given timeframe for style :"+styleId1};
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+1500);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "1", "40",
				"https://summeressentials.com", styleId2, "0", 
				"failed", "There already exists a deal between given timeframe for style :"+styleId2};
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+2000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styleId3, "1", 
				"failed", "There already exists a deal between given timeframe for style :"+styleId3};

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * deal Ids
	 * @param testContext
	 * @author jhansi.bai        
	 */

	@DataProvider
	public Object[][] deleteDealIdDP_negative(ITestContext Context){
		/*
		styleId1 = styles[73].toString(); 
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+1000);
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, "All_Essentials Today1", "All essentials for you Today", "1",
				startTime, endTime,  "true", "1", "30", "https://summeressentials.com", styleId1, "0" );
		String dealId1 =  crReq.respvalidate.NodeValue(getDealNodesWithOutDiscId().get(0), true).replaceAll("\"", "");
		
		styleId2 = styles[74].toString(); 
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+1200);
		RequestGenerator crReq1 = getRequest(APINAME.CREATENEWDEAL, "All_Essentials Today2", "All essentials for you Today1", "1",
				startTime, endTime,  "true", "1", "60", "https://summeressentials.com", styleId2, "1" );
		String dealId2 =  crReq1.respvalidate.NodeValue(getDealNodesWithOutDiscId().get(0), true).replaceAll("\"", "");
		*/
		List dealIds = JsonPath.read(getRequest(APINAME.GETALLDEALS,1).respvalidate.returnresponseasstring(), "$[*].id");
		String dealId1 = dealIds.get(0).toString();
		String dealId2 = dealIds.get(1).toString();
		String dealId3 = dealIds.get(2).toString();
				
		
		String[] arr1 = {dealId1, "failed", "Cannot update an active deal: "+dealId1};		
		String[] arr2 = {dealId2, "failed", "Cannot update an active deal: "+dealId2};
		String[] arr3 = {dealId2, "failed", "Cannot update an active deal: "+dealId2};			

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */

	@DataProvider
	public Object[][] addStyleToDealIdDP(ITestContext Context){
		List<Integer> dealIds = new ArrayList<>();
		RequestGenerator dealReq1 = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq1.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";
		String dealId3 = dealIds.get(random.nextInt(dealIds.size()))+"";
		String dealId4 = dealIds.get(random.nextInt(dealIds.size()))+"";
		String[] arr1 = {dealId1, styles[75].toString()};
		String[] arr2 = {dealId2, styles[76].toString()};
		String[] arr3 = {dealId3, styles[77].toString()};
		String[] arr4 = {dealId4, styles[78].toString()};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,3);
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */

	@DataProvider
	public Object[][] addStyleToDealIdDP_negative(ITestContext Context){
		styleId1 = styles[79].toString(); 
		styleId2 = styles[80].toString(); 
		styleId3 = styles[81].toString(); 
		RequestGenerator dealReq1 = getRequest(APINAME.GETALLDEALS, 1);
		dealIds = JsonPath.read(dealReq1.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId3 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId4 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String[] arr1 = {dealId1, styleId1, "failed", "Cannot update an active deal: "+dealId1};
		String[] arr2 = {dealId2, styleId2, "failed", "Cannot update an active deal: "+dealId2};
		String[] arr3 = {dealId2, styleId3, "failed", "Cannot update an active deal: "+dealId2};
		String[] arr4 = {dealId4, styleId1, "failed", "Cannot update an active deal: "+dealId4};
		String[] arr5 = {dealId3, styleId2, "failed", "Cannot update an active deal: "+dealId3};
		String[] arr6 = {dealId1, styleId3, "failed", "Cannot update an active deal: "+dealId1};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,4);
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */

	@DataProvider
	public Object[][] addStyleToDealIdDP_negative1(ITestContext Context){
		//Negative deal and Style Id
		styleId1 = styles[82].toString(); 
		String[] arr1 = {"-43497", "-"+styleId1, "failed", "null for uri: null"};
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * add style Ids
	 * @param testContext
	 * @author jhansi.bai
	 */

	@DataProvider
	public Object[][] addStyleToDealIdDP_duplicate_negative(ITestContext Context){
		styleId1 = styles[83].toString(); 
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		/*
		RequestGenerator crReq = getRequest(APINAME.CREATENEWDEAL, "All_Essentials Today", "All essentials for you Today", "0",
				startTime, endTime,  "true", "1", "30", "https://summeressentials.com", styleId1, "0" );
		String dealId = crReq.respvalidate.NodeValue(getDealNodesWithOutDiscId().get(0), true).replaceAll("\"", "");
		*/
		List dealIds = JsonPath.read(getRequest(APINAME.GETALLVISIBLEDEALS,1).respvalidate.returnresponseasstring(), "$[*].id");
		String dealId1 = dealIds.get(0).toString();
		
		String[] arr1 = {dealId1, styleId1, "failed", "Cannot update an active deal: "+dealId1};
		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * get all deals
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getallDealsDP(ITestContext Context){
		String[] arr1 = {"true", "0", "0", "0"};	
		String[] arr2 = {"true", "0", "0", "1"};	
		String[] arr3 = {"true", "0", "0", "2"};	
		String[] arr4 = {"true", "0", "1", "0"};	
		String[] arr5 = {"true", "0", "1", "1"};	
		String[] arr6 = {"true", "0", "1", "2"};			
		String[] arr7 = {"true", "1", "0", "0"};	
		String[] arr8 = {"true", "1", "0", "1"};	
		String[] arr9 = {"true", "1", "0", "2"};	
		String[] arr10 = {"true", "1", "1", "0"};	
		String[] arr11 = {"true", "1", "1", "1"};	
		String[] arr12 = {"true", "1", "1", "2"};		
		String[] arr13 = {"true", "2", "0", "0"};	
		String[] arr14 = {"true", "2", "0", "1"};	
		String[] arr15 = {"true", "2", "0", "2"};	
		String[] arr16 = {"true", "2", "1", "0"};	
		String[] arr17 = {"true", "2", "1", "1"};	
		String[] arr18 = {"true", "2", "1", "2"};	
		
		String[] arr19 = {"false", "0", "0", "0"};	
		String[] arr20 = {"false", "0", "0", "1"};	
		String[] arr21 = {"false", "0", "0", "2"};	
		String[] arr22 = {"false", "0", "1", "0"};	
		String[] arr23 = {"false", "0", "1", "1"};	
		String[] arr24 = {"false", "0", "1", "2"};			
		String[] arr25 = {"false", "1", "0", "0"};	
		String[] arr26 = {"false", "1", "0", "1"};	
		String[] arr27 = {"false", "1", "0", "2"};	
		String[] arr28 = {"false", "1", "1", "0"};	
		String[] arr29 = {"false", "1", "1", "1"};	
		String[] arr30 = {"false", "1", "1", "2"};		
		String[] arr31 = {"false", "2", "0", "0"};	
		String[] arr32 = {"false", "2", "0", "1"};	
		String[] arr33 = {"false", "2", "0", "2"};	
		String[] arr34 = {"false", "2", "1", "0"};	
		String[] arr35 = {"false", "2", "1", "1"};	
		String[] arr36 = {"false", "2", "1", "2"};	
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24,
				arr25, arr26, arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 10,15);
	}

	/**
	 * get all deals
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getallDealsDP_negative(ITestContext Context){
		String[] arr1 = {"ds", "0", "0", "0"};	
		String[] arr2 = {"0", "sd", "0", "0"};
		String[] arr3 = {"0", "0", "sd", "1"};
		String[] arr4 = {"0", "1", "1", "sd"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * deal Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] dealIdDP(ITestContext Context){
		List<Integer> dealIds = new ArrayList<>();
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId3 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String dealId4 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String[] arr1 = {dealId1};
		String[] arr2 = {dealId2};
		String[] arr3 = {dealId3};
		String[] arr4 = {dealId4};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * deal Ids
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] dealIdDP_negative(ITestContext Context){		
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		System.out.println("ALL DEALS DEAL ID --->> "+dealIds);
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String[] arr1 = {"-"+dealId1};
		String[] arr2 = {"dsfsdafsdafsdf"};
		String[] arr3 = {dealId1+"werqwer"};
		String[] arr4 = {"@#$#%#$%$^%"};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update deal
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateDealDP(ITestContext Context){
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId3 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		int one = random.nextInt(100);
		startTime = String.valueOf(currentTime+2000);
		endTime = String.valueOf(currentTime+45000);
		String[] arr1 = {dealId1, "Update name"+one, "Updated Desc"+one, "1", startTime, endTime, "60", "false", "0", 
		"https://summeressentials1.com"};

		int two = random.nextInt(100);
		startTime = String.valueOf(currentTime+2000);
		endTime = String.valueOf(currentTime+45000);
		String[] arr2 = {dealId2, "Update name"+two, "Updated Desc"+two, "1", startTime, endTime, "90", "true", "0",
		"https://summeressentials1.com"};

		int three = random.nextInt(100);
		startTime = String.valueOf(currentTime+2000);
		endTime = String.valueOf(currentTime+50000);
		String[] arr3 = {dealId3, "Update name"+three, "Updated Desc"+three, "0", startTime, endTime,"30", "false", "0",
		"https://summeressentials1.com" };

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update deal
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateDealDP_negative(ITestContext Context){
		List<Integer> dealIds = new ArrayList<>();
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";
		
		RequestGenerator dealReq1 = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq1.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";
		int two = random.nextInt(100);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime-500);
		String[] arr1 = {dealId1, "Update name"+two, "Updated Desc"+two, "0", startTime, endTime, "90", "false", "0",
				"https://summeressentials1.com", "failed", "deal end time cannot be less than current time"};
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime-1000);
		String[] arr2 = {dealId1, "Update name2", "Updated Desc2", "1", startTime, endTime, "40", "true", "0", 
				"https://summeressentials1.com", "failed", "deal end time cannot be less than current time"};
	
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+1000);
		String[] arr3 = {dealId2, "Update name3", "Updated Desc3", "0",startTime, endTime, "-10", "true", "0",
				"https://summeressentials1.com", "failed", "please provide valid non zero discount percent"};
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+1500);
		String[] arr4 = {dealId2, "Update name4", "", "0",startTime, endTime, "-100", "false", "0",
				"https://summeressentials1.com4", "failed", "please provide valid non zero discount percent"};

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * deal visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] dealvisibilityDP(ITestContext Context){
		String[] arr1 = {"0", "0", "0"};
		String[] arr2 = {"0", "0", "1"};
		String[] arr3 = {"0", "0", "2"};
		String[] arr4 = {"0", "1", "0"};
		String[] arr5 = {"0", "1", "1"};
		String[] arr6 = {"0", "1", "2"};
		String[] arr7 = {"1", "0", "0"};
		String[] arr8 = {"1", "0", "1"};
		String[] arr9 = {"1", "0", "2"};
		String[] arr10 = {"1", "1", "0"};
		String[] arr11 = {"1", "1", "1"};
		String[] arr12 = {"1", "1", "2"};
		String[] arr13 = {"2", "0", "0"};
		String[] arr14 = {"2", "0", "1"};
		String[] arr15 = {"2", "0", "2"};
		String[] arr16 = {"2", "1", "0"};
		String[] arr17 = {"2", "1", "1"};
		String[] arr18 = {"2", "1", "2"};
		//invalid
		String[] arr19 = {"-2", "0", "0"};
		String[] arr20 = {"0", "2", "1"};
		String[] arr21 = {"1", "1", "3"};
		String[] arr22 = {"-1", "0", "0"};
		String[] arr23 = {"0", "-1", "1"};
		String[] arr24 = {"1", "1", "-1"};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 10,15);
	}

	/**
	 * deal visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] dealvisibilityDP_negative(ITestContext Context){

		String[] arr1 = {"\"Invalid\"", "0", "0"};
		String[] arr2 = {"0", "\"Invalid\"", "1"};
		String[] arr3 = {"1", "1", "\"Invalid\""};

		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 3,3);
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateStateDP(ITestContext Context){
		RequestGenerator dealReq1 =  getRequest(APINAME.GETALLVISIBLEDEALS,0);
		ArrayList<Integer> visibledealIdsWith0 = JsonPath.read(dealReq1.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = visibledealIdsWith0.get(random.nextInt(visibledealIdsWith0.size()))+"";
		String dealId2 = visibledealIdsWith0.get(random.nextInt(visibledealIdsWith0.size()))+"";
		
		RequestGenerator dealReq2 =  getRequest(APINAME.GETALLVISIBLEDEALS,1);
		ArrayList<Integer> visibledealIdsWith1 = JsonPath.read(dealReq2.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId3 = visibledealIdsWith1.get(random.nextInt(visibledealIdsWith1.size()))+"";
		String dealId4 = visibledealIdsWith1.get(random.nextInt(visibledealIdsWith1.size()))+"";
		String[] arr1 = {dealId1, "1"};
		String[] arr2 = {dealId2, "1"};
		String[] arr3 = {dealId3, "0"};
		String[] arr4 = {dealId4, "0"};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 4,4);
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateStateDP_negative(ITestContext Context){
		String[] arr1 = {"sjsdjflj", "0"};

		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateStateDP_negative1(ITestContext Context){
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String[] arr1 = {dealId1, "2", "failed", "org.hibernate.exception.ConstraintViolationException: Column 'state' cannot be null"};
		String[] arr2 = {dealId2, "-1", "failed", "org.hibernate.exception.ConstraintViolationException: Column 'state' cannot be null"};

		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update state
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateStateDP_negative2(ITestContext Context){
		RequestGenerator dealReq =  getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String[] arr1 = {dealId1, "sdjflsd"};

		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,1);
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateVisibilityDP(ITestContext Context){
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		
		RequestGenerator dealReq1 = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq1.respvalidate.returnresponseasstring(), "$[*].id");
		String dealId3 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String dealId4 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String[] arr1 = {dealId1, "false"};
		String[] arr2 = {dealId2, "false"};
		String[] arr3 = {dealId3, "true"};
		String[] arr4 = {dealId4, "true"};

		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateVisibilityDP_negative(ITestContext Context){
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String dealId2 = dealIds.get(random.nextInt(dealIds.size()))+"";	
		String[] arr1 = {dealId1, "dfsfg"};
		String[] arr2 = {dealId2, "42542"};

		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}

	/**
	 * update visibility
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateVisibilityDP_negative1(ITestContext Context){
		RequestGenerator dealReq = getRequest(APINAME.GETALLDEALS);
		dealIds = JsonPath.read(dealReq.respvalidate.returnresponseasstring(), "$[*].id"); 
		String dealId1 = dealIds.get(random.nextInt(dealIds.size()))+"";		
		String[] arr1 = {"", "true"};	
		String[] arr2 = {"-"+dealId1, "false"};

		Object[][] dataSet = new Object[][]{arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}	
	
	/**
	 * create invisible deal DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createInvisibleNewDealDPForDelete(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[84].toString(), "0" };
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[85].toString(), "1"  };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[86].toString(), "1"  };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+15000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[87].toString(), "0"  };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,4);
	}

	
	private Object[] sumOfAllStyleIds(){
		int size = styleIdAdidas.size();
		Set setForAllUniqueStyleIds = new HashSet();
		Object[] adidasJackets = ArrayUtils.addAll(styleIdAdidas.toArray(), styleIdJackets.toArray());
		Object[] jeansNike = ArrayUtils.addAll(styleIdJeans.toArray(), styleIdNike.toArray());
		Object[] pumaShirts = ArrayUtils.addAll(styleIdPuma.toArray(), styleIdShirts.toArray());
		Object[] shoesSportsShoes = ArrayUtils.addAll(styleIdShoes.toArray(), styleIdWatches.toArray());
		Object[] tshirtsWallets = ArrayUtils.addAll(styleIdTshirts.toArray(), styleIdWallets.toArray());
		
		Object[] adidasJacketsJeansNike = ArrayUtils.addAll(adidasJackets, jeansNike);
		Object[] pumaShirtsShoesSportsShoes = ArrayUtils.addAll(pumaShirts, shoesSportsShoes);
		
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoes = ArrayUtils.addAll(adidasJacketsJeansNike, pumaShirtsShoesSportsShoes);
		
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets = ArrayUtils.addAll(adidasJacketsJeansNikePumaShirtsShoesSportsShoes, tshirtsWallets);
		
		for(int i=0;i<adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets.length;i++){
			setForAllUniqueStyleIds.add(adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets[i]);
		}
		List stylesArray = new ArrayList(setForAllUniqueStyleIds);
		Collections.shuffle(stylesArray);
		return stylesArray.toArray();
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_createNewDeal_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, 
				arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_createNewDealWithMultipleStyleIdsChannelTypes_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+25000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "1", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[45].toString(), styles[46].toString(), styles[47].toString(), 
				"0", "1", "0" };
		
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+20000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "1", startTime, endTime,  "true", "1", 
				"60", "https://summeressentials.com", styles[48].toString(), styles[49].toString(), styles[50].toString(),
				"0", "0", "1"};
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "2", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[51].toString(), styles[52].toString(), styles[53].toString(), 
				"1", "1", "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+7000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "1", startTime, endTime,  "true", "0", 
				"60", "https://summeressentials.com", styles[54].toString(), styles[55].toString(), styles[56].toString(),
				"0", "1", "1"};
		
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "2", startTime, endTime, "true", "1", 
				"30", "https://summeressentials.com", styles[57].toString(), styles[58].toString(), styles[59].toString(), 
				"1", "1", "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+10000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[60].toString(), styles[61].toString(), styles[62].toString(),
				"0", "0", "1"};
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[63].toString(), styles[64].toString(), styles[65].toString(),
				"0", "0", "0"};
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+7000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[66].toString(), styles[67].toString(), styles[69].toString(),
				"0", "1", "0"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_createInvisibleNewDeal_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[31].toString(), "0" };
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[32].toString(), "1"  };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[33].toString(), "1"  };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+15000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[34].toString(), "0"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_createInvisibleNewDealForDelete_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[84].toString(), "0" };
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[85].toString(), "1"  };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[86].toString(), "1"  };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+15000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[87].toString(), "0"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_getAllDeals_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, 
				arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_getDealById_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_updateDeal_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	/*
	 * 
	@DataProvider
	public Object[][] DealsServiceDP_deleteDeal_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+1000);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"60", "https://summeressentials.com", styles[0].toString(), "1"  };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[0].toString(), "1"  };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+15000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[0].toString(), "0"  };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 4);
	}*/
	
	@DataProvider
	public Object[][] DealsServiceDP_getAllVisibleDeals_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19,
				arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_updateState_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0", "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "true", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0", "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0", "1" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0", "1" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0", "1" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1", "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1", "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1", "1" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1", "1" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1", "1" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1", "1" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0", "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1", "1" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "true", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1", "1" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "true", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1", "1" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1", "1" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19,
				arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DealsServiceDP_updateVisibility_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+15000);
		String[] arr2 = { "All_Essentials Today2", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[1].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+8000);
		String[] arr3 = { "All_Essentials Today3", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[2].toString(), "0", "true" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr4 = { "All_Essentials Today4", "All essentials for you Today", "0", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[3].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+15000);
		String[] arr5 = { "All_Essentials Today5", "All essentials for you Today", "0", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[4].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+22000);
		String[] arr6 = { "All_Essentials Today6", "All essentials for you Today", "0", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[5].toString(), "0", "true" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr7 = { "All_Essentials Today7", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"80", "https://summeressentials.com", styles[6].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+600);
		endTime = String.valueOf(currentTime+11000);
		String[] arr8 = { "All_Essentials Today8", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"66", "https://summeressentials.com", styles[7].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+13000);
		String[] arr9= { "All_Essentials Today9", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[8].toString(), "0", "true" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+17000);
		String[] arr10 = { "All_Essentials Today10", "All essentials for you Today", "1", startTime, endTime,  "false", "1",
				"35", "https://summeressentials.com", styles[9].toString(), "0", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr11 = { "All_Essentials Today11", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"23", "https://summeressentials.com", styles[10].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+13000);
		String[] arr12= { "All_Essentials Today12", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[11].toString(), "0", "true" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+10000);
		String[] arr13 = { "All_Essentials Today13", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[12].toString(), "0", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+8000);
		String[] arr14 = { "All_Essentials Today14", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"65", "https://summeressentials.com", styles[13].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+33000);
		String[] arr15= { "All_Essentials Today15", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[14].toString(), "0", "true" };
		
		startTime = String.valueOf(currentTime+700);
		endTime = String.valueOf(currentTime+14000);
		String[] arr16 = { "All_Essentials Today16", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[15].toString(), "1", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+9000);
		String[] arr17 = { "All_Essentials Today17", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"23", "https://summeressentials.com", styles[16].toString(), "1", "true" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+15000);
		String[] arr18= { "All_Essentials Today18", "All essentials for you Today", "1", startTime, endTime,  "true", "0",
				"30", "https://summeressentials.com", styles[17].toString(), "1", "true" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+17000);
		String[] arr19 = { "All_Essentials Today19", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[18].toString(), "1", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+16000);
		String[] arr20 = { "All_Essentials Today20", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"70", "https://summeressentials.com", styles[19].toString(), "1", "true" };
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+13000);
		String[] arr21= { "All_Essentials Today21", "All essentials for you Today", "1", startTime, endTime,  "true", "1",
				"30", "https://summeressentials.com", styles[20].toString(), "1", "true" };
		
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+20000);
		String[] arr22 = { "All_Essentials Today22", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[21].toString(), "1", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+23000);
		String[] arr23 = { "All_Essentials Today23", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"75", "https://summeressentials.com", styles[22].toString(), "1", "true" };
		startTime = String.valueOf(currentTime+100);
		endTime = String.valueOf(currentTime+3000);
		String[] arr24= { "All_Essentials Today24", "All essentials for you Today", "1", startTime, endTime,  "false", "0",
				"30", "https://summeressentials.com", styles[23].toString(), "1", "true" };
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+6000);
		String[] arr25 = { "All_Essentials Today25", "All essentials for you Today", "2", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[24].toString(), "0", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+5000);
		String[] arr26 = { "All_Essentials Today26", "All essentials for you Today", "2", startTime, endTime,  "true", "1", 
				"30", "https://summeressentials.com", styles[25].toString(), "0", "true" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+11000);
		String[] arr27 = { "All_Essentials Today27", "All essentials for you Today", "2", startTime, endTime,  "true", "0",
				"10", "http://lorempixel.com/478/335/fashion/2/", styles[26].toString(), "1", "true" };
		
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+13000);
		String[] arr28 = { "All_Essentials Today28", "All essentials for you Today", "2", startTime, endTime, "false", "0", 
				"40", "https://summeressentials.com", styles[27].toString(), "0", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+15000);
		String[] arr29 = { "All_Essentials Today29", "All essentials for you Today", "2", startTime, endTime,  "false", "0", 
				"30", "https://summeressentials.com", styles[28].toString(), "1", "true" };
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+11000);
		String[] arr30 = { "All_Essentials Today30", "All essentials for you Today", "2", startTime, endTime,  "false", "1",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[29].toString(), "1", "true" };
		startTime = String.valueOf(currentTime+200);
		endTime = String.valueOf(currentTime+12000);
		String[] arr31 = { "All_Essentials Today31", "All essentials for you Today", "2", startTime, endTime,  "false", "0",
				"100", "http://lorempixel.com/478/335/fashion/2/", styles[30].toString(), "1", "true" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19,
				arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] createNewDealAndMatchDiscountwithPDP(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		List<Integer> styleIdList = OrderDiscounhelper.getStyleIdsUsingSearchAPI();
		String styleId1=styleIdList.get(0).toString();
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "1", 
				"55", "https://summeressentials.com", styleId1, "0" };
		

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] createNewDealAndVerifyEndTime(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+250);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] addNewStyleForExistingDealDataProvider(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] createNewDealStartTimeEndtimeDataProvider(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+300);
		endTime = String.valueOf(currentTime+10000);
		String[] arr1 = { "All_Essentials Today1", "All essentials for you Today", "0", startTime, endTime, "true", "0", 
				"40", "https://summeressentials.com", styles[0].toString(), "0" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
}