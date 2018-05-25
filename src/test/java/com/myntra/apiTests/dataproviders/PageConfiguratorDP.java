package com.myntra.apiTests.dataproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.myntra.apiTests.portalservices.configservice.pageconfig.PageConfigServiceApiHelper;
import com.myntra.lordoftherings.Toolbox;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * @author shankara.c
 *
 */
public class PageConfiguratorDP extends PageConfigServiceApiHelper
{

	/*@DataProvider
	public Object[][] getAllPageVersionsAssociatedWithWidgetIdDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "widget_1387464572272" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
	/*	@DataProvider
	public Object[][] getAllWidgetsAssociatedWithPageVersionDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "1396620783230" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

	/*	@DataProvider
	public Object[][] createNewPageVersionDataProvider(ITestContext testContext)
	{
		String[] arr1 = { "vivek.vasvani", "123456999", "HomePageVersion-910" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] createNewPageTypeDataProvider(ITestContext testContext)
	{
		String[] arr1 = { "1", "dummyurl/for/testing" + Math.random(), };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

/*	@DataProvider
	public Object[][] createNewPageVariantDataProvider(ITestContext testContext)
	{
		String[] arr1 = { "4", "checkout", "control" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

/*	@DataProvider
	public Object[][] mapPageVariantToversionDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "4", "checkout", "control", "1396335973397" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
/*	@DataProvider
	public Object[][] mapPageTypeToversionDataProvider(ITestContext testContext)
	{
		String[] arr1 = { "2", "/shop/fastracktest1", "1392787703578" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
	/*@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "2", "checkout", "control", "2" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
/*	@DataProvider
	public Object[][] createPageVersionWithWidgetDataDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "vivek.vasvani", "12121212", "demo", "FDW", "FWINFO",
				"SDW", "SWINFO" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
/*	@DataProvider
	public Object[][] createUpdateListOfWidgetsDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "FDW", "FWINFOUPDATED", "SDW", "SWINFOUPDATED",
				"TDW", "NEWLY CREATED" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

	/*	@DataProvider
	public Object[][] getAllVariantsAssociatedWithPageTypeDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "2" };
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	 */
	//-------------------------------------------------------------------------------------

	public Random random = new Random();
	Set<Integer> pageTypes = new HashSet<Integer>(getPageTypes());
	Object[] pageTypesArr = pageTypes.toArray();
	String pageType1 = pageTypesArr[0].toString();
	String pageType2 =pageTypesArr[1].toString();
	String pageType3 = pageTypesArr[2].toString();

	/**
	 * get current version for page type DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getPageType(
			ITestContext testContext)
	{
		String[] arr1 = { pageType2 };
		String[] arr2 = { pageType3 };		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	Set<String> widgetIds = new HashSet<String>(getWidgetIds());
	Object[] widgetIdsArr = widgetIds.toArray();
	String widgetIds1 = widgetIdsArr[0].toString();
	String widgetIds2 =widgetIdsArr[1].toString();
	String widgetIds3 = widgetIdsArr[2].toString();
	String widgetIds4 = widgetIdsArr[3].toString();
	String widgetIds5 =widgetIdsArr[4].toString();
	String widgetIds6 = widgetIdsArr[5].toString();

	/**
	 * Get all the page versions associated with a widget given a widgetId DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getallpageversionsassociatedwithwidgetidDataProviderPositive(
			ITestContext testContext)
	{
		String[] arr1 = { widgetIds1 };
		String[] arr2 = { widgetIds2 };		
		String[] arr3 = { widgetIds3 };
		String[] arr4 = { widgetIds4 };
		String[] arr5 = { widgetIds5 };		
		String[] arr6 = { widgetIds6 };
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}

	/**
	 * Get all the page versions associated with a widget given a widgetId DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getallpageversionsassociatedwithwidgetidDataProviderNegative(
			ITestContext testContext)
	{
		String[] arr1 = { "1410859381326" };
		//String[] arr2 = { null };		
		String[] arr3 = { "widget1410859381326" };
		//String[] arr4 = { "9d@#$%%" };
		String[] arr5 = { "-widget_1410859381326" };
		String[] arr6 = {"widget-1" };
		String[] arr7 = {"abcdef_1410859381326" };
		Object[][] dataSet = new Object[][] {arr1, /*arr2, arr4,*/ arr3, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}

	Set<Integer> pageVersions = new HashSet<Integer>(getAllPageVersions());
	Object[] pageVersionsArr = pageVersions.toArray();
	String pageVersions1 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();
	String pageVersions2 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();
	String pageVersions3 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();
	String pageVersions4 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();
	String pageVersions5 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();
	String pageVersions6 = pageVersionsArr[random.nextInt(pageVersionsArr.length)].toString();

	/**
	 * Get all the widgets associated with a page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getVersionDP(
			ITestContext testContext)
	{
		String[] arr1 = { pageVersions1 };
		String[] arr2 = { pageVersions2 };		
		String[] arr3 = { pageVersions3 };
		String[] arr4 = { pageVersions4 };
		String[] arr5 = { pageVersions5 };		
		String[] arr6 = { pageVersions6 };
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}

	/**
	 * Get all the widgets associated with a page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getVersionDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { "version-3242341234" };
		String[] arr2 = { null };		
		String[] arr3 = { "-143425345435" };
		//String[] arr4 = { "9d@#$%%" };
		String[] arr5 = { "11 " };
		String[] arr6 = {"42349832version" };
		String[] arr7 = {"abcdef" };
		Object[][] dataSet = new Object[][] {arr1, /*arr2,*/ arr3, /* arr4,*/ arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4,5);
	}

	/**
	 * Get page variants for page type DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getPageTypeNegative(
			ITestContext testContext)
	{
		String[] arr1 = { "111111111111" };
		String[] arr2 = { "-1" };
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * Get page variants for page type DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getPageTypeNegative1(
			ITestContext testContext)
	{
		String[] arr1 = { null };		
		String[] arr2 = { "9d@#$%%" };
		String[] arr3 = { "pagetype" };
		String[] arr4 = {"pagetype-3598347985" };
		String[] arr5 = {"abcdef" };
		Object[][] dataSet = new Object[][] {/*arr1, */arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}

	/**
	 * create page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewPageVersionDataProvider(ITestContext testContext)
	{
		int one = random.nextInt(10000);
		String[] arr1 = { "vivek.vasvani", random.nextInt(10000)+"12345"+one, "HomePageVersion-910" };
		int two = random.nextInt(10000);
		String[] arr2 = { "laxmi", random.nextInt(10000)+"12322"+two, random.nextInt(10000)+"PageVersion-"+two };
		int three = random.nextInt(10000);
		String[] arr3 = { "3242314243", random.nextInt(10000)+"12312"+three, random.nextInt(10000)+"HomePageVersion-"+three };
		int four = random.nextInt(10000);
		String[] arr4 = { "vivek.vasvani", random.nextInt(10000)+"12365"+four, random.nextInt(10000)+"HomePageVersion-"+four };
		String[] arr5 = { "laxmi", null, "HomePageVersion-910" };
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4/*, arr5*/};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}

	/**
	 * create page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewPageVersionDataProvider_negative(ITestContext testContext)
	{
		//duplicate version
		String[] arr1 = { "vivek.vasvani", pageVersions1, "HomePageVersion-910" };
		String[] arr2 = { null, null, "HomePageVersion-910" };
		Object[][] dataSet = new Object[][] {arr1/*, arr2*/};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	//---------------------------------------
	List<String> createdBys = getAllPageVersionsDetails().get(0); 
	List<String> versions = getAllPageVersionsDetails().get(1); 
	List<String> labels = getAllPageVersionsDetails().get(2); 
	String updatedVersion = "";
	//------------------------------------------------------

	/**
	 * delete page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] updatePageVersionDP(ITestContext testContext)
	{
		updatedVersion = "2"+random.nextInt(1000000000);
		String[] arr1 = { createdBys.get(0), updatedVersion, labels.get(0) };

		updatedVersion = "9"+random.nextInt(1000000000);
		String[] arr2 = { createdBys.get(1), updatedVersion, labels.get(1) };

		updatedVersion = "6"+random.nextInt(1000000000);
		String[] arr3 = { createdBys.get(2), updatedVersion, labels.get(2) };

		String[] arr4 = { createdBys.get(2), random.nextInt(10000000)+"", labels.get(0) };
		String[] arr5 = { createdBys.get(2), null, labels.get(0) };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4/*, arr5*/};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * delete page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] updatePageVersionDP_negative(ITestContext testContext)
	{
		String[] arr1 = { createdBys.get(0), versions.get(0), labels.get(0) };
		String[] arr2 = { createdBys.get(1), versions.get(1), labels.get(1) };
		String[] arr3 = { createdBys.get(2), versions.get(2), labels.get(2) };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}

	/**
	 * delete page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] deletePageVersionDP(ITestContext testContext)
	{
		String[] arr1 = { createdBys.get(3), versions.get(3), labels.get(3) };
		String[] arr2 = { createdBys.get(1), versions.get(1), labels.get(1)};
		String[] arr3 = { createdBys.get(2), versions.get(2), labels.get(2) };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * delete page version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] deletePageVersionDP_negative(ITestContext testContext)
	{
		String[] arr1 = { createdBys.get(3)+"sdf", "df"+random.nextInt(100000000), labels.get(3) };
		String[] arr2 = { createdBys.get(1), null, labels.get(1)};
		String[] arr3 = { "-"+createdBys.get(2), "40823@$#@#$%#@%$", labels.get(2) };

		Object[][] dataSet = new Object[][] {arr1, /*arr2, */arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Map Widget to Version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] mapWidgetToVersionDP(
			ITestContext testContext)
	{
		String[] arr1 = { widgetIds1, pageVersions1};
		String[] arr2 = { widgetIds2, pageVersions2};
		String[] arr3 = { widgetIds3, pageVersions3};
		String[] arr4 = { widgetIds4, pageVersions4};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Map Widget to Version DataProvider
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] mapWidgetToVersionDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { null, null };
		String[] arr2 = { "$$$$1342311", "89074901010499" };
		String[] arr3 = { widgetIds2, "3284#$%#$%@$#%" };
		String[] arr4 = { "3284#$%#$%@$#%", "3284#$%#$%@$#%" };
		String[] arr5 = { null, "3284#$%#$%@$#%" };
		String[] arr6 = { widgetIds2, null };
		Object[][] dataSet = new Object[][] { /*arr1, */arr2, arr3, arr4, /*arr5, arr6 */};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get Current version for a given pageType and pageVariant
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDP(
			ITestContext testContext)
	{
		String[] arr1 = { "3", "0", "2", "1" };		
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get Current version for a given pageType and pageVariant
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { random.nextInt(100)+"", "@#$@#!$@#4", "0", "1"};
		String[] arr2 = {"23423", "", "0", "1" };
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
/*	*//**
	 * Get Current version for a given pageType and pageVariant
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDP_negative1(
			ITestContext testContext)
	{
		String[] arr1 = { "-2342", "checkout", "3", "2" };		
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	
	/**
	 * Get Current version for a given pageType and pageVariant
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] getCurrentVersionForPageTypeAndVariantDP_negative2(
			ITestContext testContext)
	{
		String[] arr1 = { random.nextInt(100)+"", "checkout", "2", "sdfsd" };		
		String[] arr2 = { "@#$@#!$@#4", "sadfsdf", "1", "0"};
		String[] arr3 = { null, "checkout", "1", pageType2 };
		Object[][] dataSet = new Object[][] {arr1, arr2/*, arr3*/};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	
	/**
	 * Create a new page type
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewPageTypeDP(ITestContext testContext)
	{
		String[] arr1 = { random.nextInt(1000)+"", "dummyurl/for/testing"+random.nextInt(100000) };
		String[] arr2 = { random.nextInt(100)+"", "432423434"+random.nextInt(100000) };
		String[] arr3 = { random.nextInt(1000)+"", "%^&%^&%^&^%&"+random.nextInt(100000) };
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}
	
	/**
	 * Create a new page type
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createNewPageTypeDP_negative(ITestContext testContext)
	{
		String[] arr1 = { "sdfdff", "dummyurl/for/testing" + random.nextInt(100) };
			
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Create/update widgets
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createUpdateWidgetsDP(
			ITestContext testContext)
	{
		String[] arr1 = { "widget"+random.nextInt(100), "widget data 1", "widget"+random.nextInt(100), "widget data 2",
				"widget"+random.nextInt(100), "widget data 3" };
		String[] arr2 = { "widget"+random.nextInt(100), "widget data 4", "widget"+random.nextInt(100), "widget data 5",
				"widget"+random.nextInt(100), "widget data 6" };
		
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Create/update widgets
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] createUpdateWidgetsDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { "widget", "widget data 4", "349239840923809948809234099234", "widget data 5",
				"234099238wwejfowwow#@$#@$#@$", "widget data 6" };
		
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Invalidate Cache
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] cachesDP(
			ITestContext testContext)
	{
		String[] arr1 = { "widgetKeyValueCache" };
		String[] arr2 = { "featureGateKeyValueCache" };		
		String[] arr3 = { "abtestCache" };
		String[] arr4 = { "topnavCache" };
		String[] arr5 = { "pageConfiguratorCache" };		
		String[] arr6 = { "pageVariantCache" };
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3,4);
	}
	
	/**
	 * Invalidate Cache
	 * @param testContext
	 * @author jhansi.bai
	 * @return
	 */
	@DataProvider
	public Object[][] cachesDP_negative(
			ITestContext testContext)
	{
		String[] arr1 = { "widgetValueCache" };
		String[] arr2 = { null };		
		String[] arr3 = { "@#$@#$%#%" };
		
		Object[][] dataSet = new Object[][] {arr1, /*arr2, */arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}

	@DataProvider
	public Object[][] PageConfiguratorDP_getAllInfoGivenPageTypeAndPageUrl_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageType2 };
		String[] arr2 = { pageType3 };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getAllPageVersionsAssociatedWithWidgetId_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { widgetIds1 };
		String[] arr2 = { widgetIds2 };		
		String[] arr3 = { widgetIds3 };
		String[] arr4 = { widgetIds4 };
		String[] arr5 = { widgetIds5 };		
		String[] arr6 = { widgetIds6 };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getAllWidgetsAssociatedWithPageVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageVersions1 };
		String[] arr2 = { pageVersions2 };		
		String[] arr3 = { pageVersions3 };
		String[] arr4 = { pageVersions4 };
		String[] arr5 = { pageVersions5 };		
		String[] arr6 = { pageVersions6 };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getCurrentVersionForPageTypeAndVariant_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "3", "0", "2", "1" };		
		
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getAllVariantsAssociatedWithPageType_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageType2 };
		String[] arr2 = { pageType3 };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getPageVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageVersions1 };
		String[] arr2 = { pageVersions2 };		
		String[] arr3 = { pageVersions3 };
		String[] arr4 = { pageVersions4 };
		String[] arr5 = { pageVersions5 };		
		String[] arr6 = { pageVersions6 };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] PageConfiguratorDP_getPageMappingVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageVersions1 };
		String[] arr2 = { pageVersions2 };		
		String[] arr3 = { pageVersions3 };
		String[] arr4 = { pageVersions4 };
		String[] arr5 = { pageVersions5 };		
		String[] arr6 = { pageVersions6 };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_getPageMapPage_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { pageType2 };
		String[] arr2 = { pageType3 };	
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_createNewPageVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		int one = random.nextInt(10000);
		String[] arr1 = { "vivek.vasvani", random.nextInt(10000)+"12345"+one, "HomePageVersion-910" };
		int two = random.nextInt(10000);
		String[] arr2 = { "laxmi", random.nextInt(10000)+"12322"+two, random.nextInt(10000)+"PageVersion-"+two };
		int three = random.nextInt(10000);
		String[] arr3 = { "3242314243", random.nextInt(10000)+"12312"+three, random.nextInt(10000)+"HomePageVersion-"+three };
		int four = random.nextInt(10000);
		String[] arr4 = { "vivek.vasvani", random.nextInt(10000)+"12365"+four, random.nextInt(10000)+"HomePageVersion-"+four };
		String[] arr5 = { "laxmi", null, "HomePageVersion-910" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4/*, arr5*/ };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_updatePageVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		updatedVersion = "2"+random.nextInt(1000000000);
		String[] arr1 = { createdBys.get(0), updatedVersion, labels.get(0) };

		updatedVersion = "9"+random.nextInt(1000000000);
		String[] arr2 = { createdBys.get(1), updatedVersion, labels.get(1) };

		updatedVersion = "6"+random.nextInt(1000000000);
		String[] arr3 = { createdBys.get(2), updatedVersion, labels.get(2) };

		String[] arr4 = { createdBys.get(2), random.nextInt(10000000)+"", labels.get(0) };
		String[] arr5 = { createdBys.get(2), null, labels.get(0) };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4/*, arr5*/ };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] PageConfiguratorDP_deletePageVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { createdBys.get(3), versions.get(3), labels.get(3) };
		String[] arr2 = { createdBys.get(1), versions.get(1), labels.get(1)};
		String[] arr3 = { createdBys.get(2), versions.get(2), labels.get(2) };

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_createNewPageType_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { random.nextInt(1000)+"", "dummyurl/for/testing"+random.nextInt(100000) };
		String[] arr2 = { random.nextInt(100)+"", "432423434"+random.nextInt(100000) };
		String[] arr3 = { random.nextInt(1000)+"", "%^&%^&%^&^%&"+random.nextInt(100000) };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_mapWidgetToVersion_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { widgetIds1, pageVersions1 };
		String[] arr2 = { widgetIds2, pageVersions2 };
		String[] arr3 = { widgetIds3, pageVersions3 };
		String[] arr4 = { widgetIds4, pageVersions4 };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_inValidateCache_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "widgetKeyValueCache" };
		String[] arr2 = { "featureGateKeyValueCache" };		
		String[] arr3 = { "abtestCache" };
		String[] arr4 = { "topnavCache" };
		String[] arr5 = { "pageConfiguratorCache" };		
		String[] arr6 = { "pageVariantCache" };
		
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] PageConfiguratorDP_createUpdateWidgets_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "widget"+random.nextInt(100), "widget data 1", "widget"+random.nextInt(100), "widget data 2",
				"widget"+random.nextInt(100), "widget data 3" };
		
		String[] arr2 = { "widget"+random.nextInt(100), "widget data 4", "widget"+random.nextInt(100), "widget data 5",
				"widget"+random.nextInt(100), "widget data 6" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
}