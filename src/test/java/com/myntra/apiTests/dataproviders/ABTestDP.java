package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.configservice.abtest.ABtestServiceHelper;
import com.myntra.lordoftherings.Toolbox;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * @author shankara.c
 *
 */
public class ABTestDP extends ABtestServiceHelper
{
	/*@DataProvider
	public Object [][] getABTestDataProvider(ITestContext testContext)
	{
		String [] ABTestName = {"njswHP"};		
		Object [][] dataSet = new Object [][] {ABTestName};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);		
	}*/
	/*
	@DataProvider
	public Object[][] getABTestForGivenListDataProvider(ITestContext testContext)
	{
		String [] ABTestName = {"\"njswHP\"", "\"pdpv1\""};		
		Object [][] dataSet = new Object [][] {ABTestName};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);

	}*/

/*	@DataProvider
	public Object[][] getAllABTestVariantsDataProvider(ITestContext testContext)
	{
		String [] ABTestName = {"njswHP"};		
		Object [][] dataSet = new Object [][] {ABTestName};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);

	}
*/
	/*	@DataProvider
	public Object[][] createNewABTestDataProvider(ITestContext testContext)
	{
		{
		    "variations":[{
		        "name":"${0}",
		        "percentProbability":${1},
		        "inlineHtml":${2},
		        "finalVariant":${3},
		        "jsFile":${4},
		        "cssFile":${5},
		        "configJson":"${6}",
		        "algoConfigJson":"${7}"
		    }],
		    "auditLogs":${8},
		    "name":"${9}",
		    "isEnabled":${10},
		    "gaSlot":${11},
		    "omniSlot":${12},
		    "filters":${13},
		    "segmentationAlgo":"${14}",
		    "sourceType":"$15}",
		    "isCompleted":${16},
		    "apiVersion":${17}
		}
		String [] arr1 = {"splash","30","\"\"","false","\"\"","\"\"","null","null","[]","createnewabtest","true","4","0","\"\"","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);

	}*/

	/*	@DataProvider
	public Object[][] getVariantOfGivenABTestDataProvider(ITestContext testContext){
		String[] ABTestName = {"ListScrollLock"};
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}*/

/*	@DataProvider
	public Object[][] deleteExistingABTestDataProvider(ITestContext testContext){
		String[] ABTestName = {"createnewabtest"};
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] updateExistingABTestDataProvider(ITestContext testContext){
				{
		    "variations":[{
		        "name":"${0}",
		        "percentProbability":${1},
		        "inlineHtml":"${2}",
		        "finalVariant":${3}
		    },{
		        "name":"${4}",
		        "percentProbability":${5},
		        "inlineHtml":"${6}",
		        "finalVariant":${7}
		    }],
		    "auditLogs":[],
		    "name":"${8}",
		    "isEnabled":${9},
		    "gaSlot":${10},
		    "omniSlot":${11},
		    "filters":"${12}",
		    "segmentationAlgo":"${13}",
		    "sourceType":"${14}",
		    "isCompleted":${15},
		    "apiVersion":${16}
		}
		String [] arr1 = {"splash","30","","false","splash1","31","","false","[]","createnewabtest","true","4","0","","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);

	}*/

	//---------------------------------------------------------------------------------------------------------------

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getABTestsForGivenListDP(ITestContext testContext)
	{
		String [] arr1 = {"\"ListScrollLock\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String [] arr2 = {"\"ListScrollLock,styleExclusion\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String [] arr3 = {"\"ListScrollLock,styleExclusion,codEnableAllZipCode\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String [] arr4 = {"\"ListScrollLock,styleExclusion,codEnableAllZipCode,returnenabled\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		Object [][] dataSet = new Object [][] {arr1, arr2, arr3, arr4};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);		
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getABTestsForGivenListDP_negative(ITestContext testContext)
	{
		String [] arr1 = {"$%#$%$#%#$", "10008", "\"ABTest not found\"", "\"SUCCESS\"", "0"};
		String [] arr2 = {"fsdafsdfsd", "10008", "\"ABTest not found\"", "\"SUCCESS\"", "0"};
		String [] arr3 = {"checkInStock3242,styleExclusion,codEnableAllZipCode345", "10008", "\"ABTest not found\"", "\"SUCCESS\"", "0"};
		String [] arr4 = {"checkInStock,styleExclusion,$%#$%$#%#$,returnenabled", "10008", "\"ABTest not found\"", "\"SUCCESS\"", "0"};
		Object [][] dataSet = new Object [][] {arr1, arr2, arr3, arr4};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);		
	}

	/**
	 * Get ABTests for a given list.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getABTestsForGivenListDP_negative1(ITestContext testContext)
	{
		String [] arr1 = {",,"};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}

	/**
	 * Get ABTests for a given name.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] getABTestDP(ITestContext testContext)
	{
		String [] arr1 = {"ListScrollLock", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String [] arr2 = {"cab", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}

	/**
	 * Get ABTests for a given name.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] getABTestDP_negative(ITestContext testContext)
	{
		String [] arr1 = {"$%#$%$#%#$", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		String [] arr2 = {"fsdafsdfsd", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}

	/**
	 * Get ABTests for a given name.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] getABTestDP_negative1(ITestContext testContext)
	{
		String [] arr1 = {"$%#$%$#%#$", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String [] arr2 = {"fsdafsdfsd", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}

	/**
	 * Get ABTests for a given name.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] getABTestDP_negative2(ITestContext testContext)
	{
		String [] arr1 = {""};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewABTestDP(ITestContext testContext)
	{
		/*{
			    "variations":[{
			        "name":"${0}",
			        "percentProbability":${1},
			        "inlineHtml":${2},
			        "finalVariant":${3},
			        "jsFile":${4},
			        "cssFile":${5},
			        "configJson":"${6}",
			        "algoConfigJson":"${7}"
			    }],
			    "auditLogs":${8},
			    "name":"${9}",
			    "isEnabled":${10},
			    "gaSlot":${11},
			    "omniSlot":${12},
			    "filters":${13},
			    "segmentationAlgo":"${14}",
			    "sourceType":"$15}",
			    "isCompleted":${16},
			    "apiVersion":${17}
			}*/
		String [] arr1 = {"automationABTest2","30","\"\"","false","\"\"","\"\"","null","null","[]",
				"automationName5","true","4","0","\"\"","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		String [] arr2 = {"automationABTest1","30","\"\"","false","\"\"","\"\"","null","null","[]",
				"automationName1","true","4","0","\"\"","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		Object [][] dataSet = new Object [][] {arr1/*, arr2*/};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewABTestDP_Duplicate_negative(ITestContext testContext)
	{
		String [] arr1 = {"automationABTest","30","\"\"","false","\"\"","\"\"","null","null","[]",
				"automationName","true","4","0","\"\"","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		String [] arr2 = {"automationABTest1","30","\"\"","false","\"\"","\"\"","null","null","[]",
				"automationName1","true","4","0","\"\"","abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Create a new ABTest.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewABTestDP_negative(ITestContext testContext)
	{
		String [] arr1 = {"automationABTest","30","\"\"","false","\"\"","\"\"","null","null","[]","","true","4","0","\"\"",
				"abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		String [] arr2 = {"automationABTest","30","\"\"","false","\"\"","\"\"","null","null","[]","","true","4","0","\"\"",
				"abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};		
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateExistingABTestDP(ITestContext testContext){
		/*		{
		    "variations":[{
		        "name":"${0}",
		        "percentProbability":${1},
		        "inlineHtml":"${2}",
		        "finalVariant":${3}
		    },{
		        "name":"${4}",
		        "percentProbability":${5},
		        "inlineHtml":"${6}",
		        "finalVariant":${7}
		    }],
		    "auditLogs":[],
		    "name":"${8}",
		    "isEnabled":${9},
		    "gaSlot":${10},
		    "omniSlot":${11},
		    "filters":"${12}",
		    "segmentationAlgo":"${13}",
		    "sourceType":"${14}",
		    "isCompleted":${15},
		    "apiVersion":${16}
		}*/
		String [] arr1 = {"splash","30","","false","splash1","31","","false","[]","createnewabtest","true","4","0","",
				"abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpl","false","1"};	
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);

	}
	
	/**
	 * Update an existing ABTest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateExistingABTestDP_negative(ITestContext testContext){
		String [] arr1 = {"automationABTest","30","","false","automationName","31","","false","[]","updatenewabtest","true","4","0","",
				"abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo","tpupdate","true","1"};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestDP(ITestContext testContext){
		String[] ABTestName = {"splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestDP_negative(ITestContext testContext){
		String[] ABTestName = {"random", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		String [] ABTestName2 = {"$%#$%$#%#$", "10008", "\"ABTest not found\"", "\"ERROR\"", "0"};
		Object[][] dataSet = new Object[][]{ABTestName, ABTestName2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestDP_negative1(ITestContext testContext){
		String[] ABTestName = {""};
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	/**
	 * Get variant of a given ABTest  with filter: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestWithFilterDP(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName2 = {"", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName3 = {"abc@xyz.com", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest  with filter: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestWithFilterDP_negative(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "random"};
		String[] ABTestName2 = {"", "$%#$%$#%#$"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest  with filter: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantOfGivenABTestWithFilterDP_negative1(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", ""};
		String[] ABTestName2 = {"", ""};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest  with segment: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithsegmentDP(ITestContext testContext){
		String[] ABTestName1 = {"JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName2 = {"", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName3 = {"JJN004392cd250a3c347a6be5549415a754b", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest  with segment: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithsegmentDP_negative(ITestContext testContext){
		String[] ABTestName1 = {"JJN004392cd250a3c347a6be5549415a754be4", "random"};
		String[] ABTestName2 = {"", "$%#$%$#%#$"};
		String[] ABTestName3 = {"JJN004392cd250a3c347a6be5549415a754b", "random1"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variant of a given ABTest  with segment: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithsegmentDP_negative1(ITestContext testContext){
		String[] ABTestName1 = {"JJN004392cd250a3c347a6be5549415a754be4", ""};
		String[] ABTestName2 = {"", ""};
		String[] ABTestName3 = {"JJN004392cd250a3c347a6be5549415a754b", ""};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3,4);
	}
	
	/**
	 * Get variant of a given ABTest  with abtest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithABtestDP(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName2 = {"laxmi@myntra.com", "", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName3 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754b", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName4 = {"", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		String[] ABTestName5 = {"laxmi@abc.com", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3, ABTestName4, ABTestName5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get variant of a given ABTest  with abtest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithABtestDP_negative(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4", "random"};
		String[] ABTestName2 = {"laxmi@myntra.com", "", "random"};
		String[] ABTestName3 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754b", "random1"};
		String[] ABTestName4 = {"", "JJN004392cd250a3c347a6be5549415a754be4", "$%#$%$#%#$"};
		String[] ABTestName5 = {"laxmi@abc.com", "JJN004392cd250a3c347a6be5549415a754be4", "$%#$%$#%#$"};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3, ABTestName4, ABTestName5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get variant of a given ABTest  with abtest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getvariantofgivenabtestwithABtestDP_negative1(ITestContext testContext){
		String[] ABTestName1 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4", ""};
		String[] ABTestName2 = {"laxmi@myntra.com", "", ""};
		String[] ABTestName3 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754b", ""};
		String[] ABTestName4 = {"", "JJN004392cd250a3c347a6be5549415a754be4", ""};
		String[] ABTestName5 = {"laxmi@abc.com", "JJN004392cd250a3c347a6be5549415a754be4", ""};
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3, ABTestName4, ABTestName5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get variants of all active ABTests with filter: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantsOfAllActiveABTestsWithFilterDP(ITestContext testContext){
		String[] arr1 = {"laxmi@myntra.com", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr2 = {"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr3 = {"abc@xyz.com", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variants of all active ABTests with segment: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantsOfAllActiveABTestswithsegmentDP(ITestContext testContext){
		String[] arr1 = {"JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr2 = {"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr3 = {"JJN004392cd250a3c347a6be5549415a754b", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get variants of all active ABTests with abtest: 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getVariantsOfAllActiveABTestswithABtestsDP(ITestContext testContext){
		String[] arr1 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr2 = {"", "", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr3 = {"abc@xyz.com","JJN004392cd250a3c347a6be5549415a754b", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr4 = {"", "JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr5 = {"laxmi@myntra.com", "","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		String[] arr6 = {"laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a7","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\""};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3,4);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getABTestsForGivenList_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "\"ListScrollLock\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String [] arr2 = { "\"ListScrollLock,styleExclusion\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String [] arr3 = { "\"ListScrollLock,styleExclusion,codEnableAllZipCode\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String [] arr4 = { "\"ListScrollLock,styleExclusion,codEnableAllZipCode,returnenabled\"", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object [][] dataSet = new Object [][] { arr1, arr2, arr3, arr4 };		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object [][] ABTestDP_getABTest_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "ListScrollLock", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String [] arr2 = { "cab", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object [][] dataSet = new Object [][] { arr1, arr2 };		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object [][] ABTestDP_getABTestVariants_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "ListScrollLock", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String [] arr2 = { "cab", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object [][] dataSet = new Object [][] { arr1, arr2 };		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] ABTestDP_updateExistingABTest_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String [] arr1 = { "splash", "30", "", "false", "splash1", "31", "", "false", "[]", "createnewabtest", "true", "4", "0", "", 
				"abtest\\\\algos\\\\impl\\\\RandomSegmentationAlgo", "tpl", "false", "1" };
		
		Object [][] dataSet = new Object [][] { arr1 };		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantOfGivenABTest_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] ABTestName = { "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1"};
		
		Object[][] dataSet = new Object[][]{ABTestName};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantOfGivenABTestWithFilter_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] ABTestName1 = { "laxmi@myntra.com", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName2 = { "", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName3 = { "abc@xyz.com", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		
		Object[][] dataSet = new Object[][]{ABTestName1, ABTestName2, ABTestName3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantOfGivenABTestWithSegment_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] ABTestName1 = { "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName2 = { "", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName3 = { "JJN004392cd250a3c347a6be5549415a754b", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		
		Object[][] dataSet = new Object[][]{ ABTestName1, ABTestName2, ABTestName3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] ABTestDP_getVariantOfGivenABTestWithABtest_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] ABTestName1 = { "laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", 
				"\"SUCCESS\"", "1" };
		String[] ABTestName2 = { "laxmi@myntra.com", "", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName3 = { "laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754b", "splash", "15002", "\"ABTest(s) retrieved successfully\"", 
				"\"SUCCESS\"", "1" };
		String[] ABTestName4 = { "", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		String[] ABTestName5 = { "laxmi@abc.com", "JJN004392cd250a3c347a6be5549415a754be4", "splash", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"", "1" };
		
		Object[][] dataSet = new Object[][]{ ABTestName1, ABTestName2, ABTestName3, ABTestName4, ABTestName5 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantsOfAllActiveABTestsWithFilter_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "laxmi@myntra.com", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr2 = { "", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr3 = { "abc@xyz.com", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantsOfAllActiveABTestsWithSegment_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr2 = { "", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr3 = { "JJN004392cd250a3c347a6be5549415a754b", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] ABTestDP_getVariantsOfAllActiveABTestsWithABtests_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr2 = { "", "", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr3 = { "abc@xyz.com","JJN004392cd250a3c347a6be5549415a754b", "15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr4 = { "", "JJN004392cd250a3c347a6be5549415a754be4","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr5 = { "laxmi@myntra.com", "","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		String[] arr6 = { "laxmi@myntra.com", "JJN004392cd250a3c347a6be5549415a7","15002", "\"ABTest(s) retrieved successfully\"", "\"SUCCESS\"" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
}
