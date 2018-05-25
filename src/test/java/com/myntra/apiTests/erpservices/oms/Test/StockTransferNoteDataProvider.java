/**
 * 
 */
package com.myntra.apiTests.erpservices.oms.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.StnCategory;

/**
 * @author 15138
 *
 */
public class StockTransferNoteDataProvider {
	
	private static Logger log = LoggerFactory.getLogger(StockTransferNoteDataProvider.class);
	

	@DataProvider
	public static Object[][] getDataSetStatingDistinctQualityAndItemStatusCombinations(
			ITestContext testContext) throws Exception {
		Object[] dataSetForQualityType1AndItemStatusAsStored =	{Quality.Q1,ItemStatus.STORED,StnCategory.NORMAL};
		//Object[] dataSetForQualityType2AndItemStatusAsStored =	{Quality.Q2,ItemStatus.STORED,StnCategory.NORMAL};
		//Object[] dataSetForQualityType3AndItemStatusAsStored =	{Quality.Q3,ItemStatus.STORED,StnCategory.NORMAL};
		//Object[] dataSetForQualityType4AndItemStatusAsStored =	{Quality.Q4,ItemStatus.STORED,StnCategory.NORMAL};

		//Object[] dataSetForQualityType1AndItemStatusAsAcceptedReturns =	{Quality.Q1,ItemStatus.ACCEPTED_RETURNS,StnCategory.NORMAL};
		//Object[] dataSetForQualityType2AndItemStatusAsSAcceptedReturns =	{Quality.Q2,ItemStatus.ACCEPTED_RETURNS,StnCategory.NORMAL};
		//Object[] dataSetForQualityType3AndItemStatusAsAcceptedReturns =	{Quality.Q3,ItemStatus.ACCEPTED_RETURNS,StnCategory.NORMAL};
		//Object[] dataSetForQualityType4AndItemStatusAsAcceptedReturns =	{Quality.Q4,ItemStatus.ACCEPTED_RETURNS,StnCategory.NORMAL};
		
				
		//Object[][] dataSet = new Object[][] {dataSetForQualityType1AndItemStatusAsStored,dataSetForQualityType2AndItemStatusAsStored,dataSetForQualityType3AndItemStatusAsStored,
			//dataSetForQualityType4AndItemStatusAsStored,dataSetForQualityType1AndItemStatusAsAcceptedReturns,dataSetForQualityType3AndItemStatusAsAcceptedReturns,dataSetForQualityType4AndItemStatusAsAcceptedReturns,
			//dataSetForQualityType2AndItemStatusAsSAcceptedReturns	};
			
		Object[][] dataSet = new Object[][] {dataSetForQualityType1AndItemStatusAsStored};
		log.info("The total data sets to test this method are  "+ dataSet.length);			
		return dataSet;
	}

}
