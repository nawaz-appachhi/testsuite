package com.myntra.apiTests.erpservices.wms.dp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class LocationsTestDataProvider {

	private static Logger log = LoggerFactory.getLogger(LocationsTestDataProvider.class);

	@DataProvider
	public static Object[][] getTestWareHouses(
			ITestContext testContext) throws Exception {
		String wareHouseName="TestWareHouse1";
		Object[] wareHouseNames =	{wareHouseName};
	
		Object[][] dataSet = new Object[][] {wareHouseNames};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}
	
	@DataProvider
	public static Object[][] getTestWareHouseCodes(
			ITestContext testContext) throws Exception {
		String wareHouseCode="NT";
		Object[] wareHouseCodes =	{wareHouseCode};
	
		Object[][] dataSet = new Object[][] {wareHouseCodes};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}
	public static Object[][] getTestZoneCodes(
			ITestContext testContext) throws Exception {
		String zoneCode="NTTR";
		Object[] zoneCodes =	{zoneCode};
	
		Object[][] dataSet = new Object[][] {zoneCodes};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}
	
	

	
	
}
