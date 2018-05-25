package com.myntra.apiTests.erpservices.wms.dp;

import com.myntra.apiTests.erpservices.InternalOrderTestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.myntra.client.wms.codes.utils.InternalOrderType;


public class InternalOrderTestsDataProvider {
	private static Logger log = LoggerFactory.getLogger(InternalOrderTestsDataProvider.class);

	@DataProvider
	public static Object[][] getOrderTypes(
			ITestContext testContext) throws Exception {
		Object[] orderTypePromotional =	{InternalOrderType.Promotional_Goods, InternalOrderTestConstants.DEFAULT_TEST_SKU_ID,InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST};
		Object[] orderTypeOther =	{InternalOrderType.Other,InternalOrderTestConstants.DEFAULT_TEST_SKU_ID,InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST};
	
		Object[][] dataSet = new Object[][] {orderTypePromotional,
			orderTypeOther};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}

	@DataProvider
	public static Object[][] getOrderTypesToTestNoInventorySkuAssociation(
			ITestContext testContext) throws Exception {
		Object[] orderTypePromotional =	{InternalOrderType.Promotional_Goods,InternalOrderTestConstants.DEFAULT_SKU_ID_WITH_NO_INVENTORY,InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST};
		Object[] orderTypeOther =	{InternalOrderType.Other,InternalOrderTestConstants.DEFAULT_SKU_ID_WITH_NO_INVENTORY,InternalOrderTestConstants.DEFAULT_QUANTITY_OF_ITEMS_TO_TEST};
	
		Object[][] dataSet = new Object[][] {orderTypePromotional,
			orderTypeOther};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}
	
	
}
