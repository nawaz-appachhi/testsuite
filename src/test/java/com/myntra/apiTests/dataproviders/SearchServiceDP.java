package com.myntra.apiTests.dataproviders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.myntra.apiTests.portalservices.searchservice.SearchServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class SearchServiceDP extends SearchServiceHelper
{

	/*@DataProvider
	public Object[][] dp_landingpage(ITestContext context)
	{
		String brand1 = "Nike";
		String brand2 = "Puma";
		String brand3 = "Adidas";
		String brand4 = "Reebok";
		String brand5 = "Biba";
		String brand6 = "Cat";

		// String payload1 = pl.ParamatereizedPayload_SearchServiceids2(arr1);
		// String payload1 = pl.ParamatereizedPayload_SearchServiceids2(arr1);

		Object[][] dataSet = new Object[][] { { brand1 }, { brand2 },
				{ brand3 }, { brand4 }, { brand5 }, { brand6 } };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] dp_autosuggest(ITestContext context)
	{
		Payload pl = new Payload(APINAME.AUTOSUGGEST,
				InitializeFramework.init.Configurations, PayloadType.JSON);
		String[] arr3 = { "3", "5" };
		String[] arr4 = { "4", "6" };
		String[] urlparam1 = { "ni" };
		String[] urlparam2 = { "ad" };
		// String payload1 = pl.ParamatereizedPayload_SearchServiceids2(arr1);
		// String payload1 = pl.ParamatereizedPayload_SearchServiceids2(arr1);

		Object[][] dataSet = new Object[][] { new Object[] { urlparam1, arr3 },
				new Object[] { urlparam1, arr4 },
				new Object[] { urlparam2, arr3 },
				new Object[] { urlparam2, arr4 }, };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] dp_parameterizedpage(ITestContext context)
	{
		String[] pagenumber = {"517"};
		String[] pagenumber2 = {"161"};
		Object[][] dataSet = new Object[][] {  pagenumber, pagenumber2  };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1, 2);
	}*/

	@DataProvider
	public Object[][] SearchService_getStyleInfo(){
		
		String[] arr1 = {"266289"};
//		String[] arr2 = {"3452671"};
//		String[] arr3 = {""};
		
		Object[][] dataSet = new Object[][]{arr1};
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] SearchService_getQueryWithtReturnDoc(ITestContext Context)
	{
		String[] arr1 = { "Nike", "50", "true", "false" };
		String[] arr2 = { "Puma", "50", "true", "false" };
		String[] arr3 = { "Adidas", "50", "true", "false" };
		String[] arr4 = { "Reebok", "50", "true", "false" };
		String[] arr5 = { "Biba", "50", "true", "false" };
		String[] arr6 = { "Cat", "50", "true", "false" };
		String[] arr7 = { "Nike", "24", "true", "false" };
		String[] arr8 = { "Nike", "24", "true", "true" };
		String[] arr9 = { "Nike", "50", "true", "true" };
		String[] arr10 = { "Puma", "24", "true", "false" };
		String[] arr11 = { "Puma", "24", "true", "true" };
		String[] arr12 = { "Puma", "50", "true", "true" };
		String[] arr13 = { "Adidas", "24", "true", "false" };
		String[] arr14 = { "Adidas", "24", "true", "true" };
		String[] arr15 = { "Adidas", "50", "true", "true" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 5,6);
	}
	
	@DataProvider
	public Object[][] SearchService_getQueryWithtFq(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "global_attr_article_type:(\\\"shirts\\\")"};
		String[] arr2 = { "Puma", "true", "false", "global_attr_age_group:(\\\"Adults-Men\\\" OR \\\"Adults-Unisex\\\")" };
		String[] arr3 = { "Adidas", "true", "false", "global_attr_sub_category:(\\\"socks\\\")"};
		String[] arr4 = { "Reebok", "true", "false", "global_attr_gender:(\\\"men\\\")"};
		String[] arr5 = { "Biba", "true", "false", "global_attr_article_type_facet:(\\\"Dresses\\\" OR \\\"Jumpsuit\\\")"};
		String[] arr6 = { "Cat", "true", "false", "global_attr_gender:(\\\"Women\\\")"};
		String[] arr7 = { "Nike", "true", "false", "global_attr_gender:(\\\"Women\\\")"};
		String[] arr8 = { "Nike", "true", "true", "global_attr_article_type:(\\\"tshirts\\\")"};
		String[] arr9 = { "Nike", "true", "true","global_attr_article_type:(\\\"Socks\\\")"};
		String[] arr10 = { "Puma", "true", "false","global_attr_article_type:(\\\"shoes\\\")"};
		String[] arr11 = { "Puma", "true", "true","global_attr_article_type:(\\\"jackets\\\")"};
		String[] arr12 = { "Puma", "true", "true","global_attr_article_type:(\\\"sweaters\\\")"};
		String[] arr13 = { "Adidas", "true", "false","global_attr_article_type:(\\\"jackets\\\")"};
		String[] arr14 = { "Adidas", "true", "true","global_attr_article_type:(\\\"shorts\\\")"};
		String[] arr15 = { "Adidas", "true", "true","global_attr_article_type:(\\\"shoes\\\")"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 5,6);
	}
	
	/*@DataProvider
	public Object[][] SearchService_filteredQuerySolrSharding(ITestContext Context)
	{
		
		
		int totalCount = 10;
		String queries = null;
		try {
			queries = getFileAsString("./Data/payloads/JSON/filteredSearchPayloads");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] queriesFromFile = queries.split("\n");
		Object[][] dataSet = new Object[totalCount][totalCount];
		int j = 0;
		//for(String str : queriesFromFile){
		for(int i = 0 ;i < totalCount; i++){
			if(queriesFromFile[i].equalsIgnoreCase("-") || queriesFromFile[i].equalsIgnoreCase("_") || queriesFromFile[i].isEmpty())
				continue;
			dataSet[j] = new String[]{queriesFromFile[i]};
			j++;
		}
		return dataSet;
	}*/
	
	
	public static String getFileAsString(String Path) throws Exception{
		File f = new File(Path);
		if(f.exists()){
			String line;
			StringBuffer sb = new StringBuffer();
			BufferedReader bf = new BufferedReader(new FileReader(f));
			while((line = bf.readLine())!=null){
				sb.append(line+"\n");
			}
			return sb.toString();	
		}else{
			System.out.println("ERROR  : "+Path+" file not found");
			return "";
		}

	}

/*	@DataProvider
	public Object[][] SearchService_GetQuery_withfalsereturndoc()
	{

		APINAME apiundertest = APINAME.GETQUERY;
		Payload pl = new Payload(apiundertest,
				InitializeFramework.init.Configurations, PayloadType.JSON);
		String[] arr1 = { "Nike", "50", "false", "false" };
		String[] arr2 = { "Puma", "50", "false", "false" };
		String[] arr3 = { "Adidas", "50", "false", "false" };
		String[] arr4 = { "Reebok", "50", "false", "false" };
		String[] arr5 = { "Biba", "50", "false", "false" };
		String[] arr6 = { "Cat", "50", "false", "false" };
		String[] arr7 = { "Nike", "24", "false", "false" };
		String[] arr8 = { "Nike", "24", "false", "true" };
		String[] arr11 = { "Nike", "50", "false", "true" };
		String[] arr17 = { "Puma", "24", "false", "false" };
		String[] arr18 = { "Puma", "24", "false", "true" };
		String[] arr21 = { "Puma", "50", "false", "true" };
		String[] arr27 = { "Adidas", "24", "false", "false" };
		String[] arr28 = { "Adidas", "24", "false", "true" };
		String[] arr31 = { "Adidas", "50", "false", "true" };

		return new Object[][] {
				new Object[] { pl.ParamatereizedPayload(arr1) },
				new Object[] { pl.ParamatereizedPayload(arr2) },
				new Object[] { pl.ParamatereizedPayload(arr3) },
				new Object[] { pl.ParamatereizedPayload(arr4) },
				new Object[] { pl.ParamatereizedPayload(arr5) },
				new Object[] { pl.ParamatereizedPayload(arr6) },
				new Object[] { pl.ParamatereizedPayload(arr7) },
				new Object[] { pl.ParamatereizedPayload(arr8) },
				new Object[] { pl.ParamatereizedPayload(arr11) },
				new Object[] { pl.ParamatereizedPayload(arr17) },
				new Object[] { pl.ParamatereizedPayload(arr18) },
				new Object[] { pl.ParamatereizedPayload(arr21) },
				new Object[] { pl.ParamatereizedPayload(arr27) },
				new Object[] { pl.ParamatereizedPayload(arr28) },
				new Object[] { pl.ParamatereizedPayload(arr31) }, };
	}
*/
	/*@DataProvider
	public Object[][] SearchService_GetQuery_positives()
	{

		APINAME apiundertest = APINAME.GETQUERY;
		Payload pl = new Payload(apiundertest,
				InitializeFramework.init.Configurations, PayloadType.JSON);
		String[] arr1 = { "Nike", "50", "true", "false" };
		String[] arr2 = { "Puma", "50", "true", "false" };
		String[] arr3 = { "Adidas", "50", "true", "false" };
		String[] arr4 = { "Reebok", "50", "true", "false" };
		String[] arr5 = { "Biba", "50", "true", "false" };
		String[] arr6 = { "Cat", "50", "true", "false" };
		String[] arr7 = { "Nike", "24", "true", "false" };
		String[] arr8 = { "Nike", "24", "true", "true" };
		String[] arr9 = { "Nike", "24", "false", "false" };
		String[] arr10 = { "Nike", "24", "false", "false" };
		String[] arr11 = { "Nike", "50", "true", "true" };
		String[] arr12 = { "Nike", "50", "false", "false" };
		String[] arr17 = { "Puma", "24", "true", "false" };
		String[] arr18 = { "Puma", "24", "true", "true" };
		String[] arr19 = { "Puma", "24", "false", "false" };
		String[] arr20 = { "Puma", "24", "false", "false" };
		String[] arr21 = { "Puma", "50", "true", "true" };
		String[] arr22 = { "Puma", "50", "false", "false" };
		String[] arr27 = { "Adidas", "24", "true", "false" };
		String[] arr28 = { "Adidas", "24", "true", "true" };
		String[] arr29 = { "Adidas", "24", "false", "false" };
		String[] arr30 = { "Adidas", "24", "false", "false" };
		String[] arr31 = { "Adidas", "50", "true", "true" };
		String[] arr32 = { "Adidas", "50", "false", "false" };

		return new Object[][] {
				new Object[] { pl.ParamatereizedPayload(arr1) },
				new Object[] { pl.ParamatereizedPayload(arr2) },
				new Object[] { pl.ParamatereizedPayload(arr3) },
				new Object[] { pl.ParamatereizedPayload(arr4) },
				new Object[] { pl.ParamatereizedPayload(arr5) },
				new Object[] { pl.ParamatereizedPayload(arr6) },
				new Object[] { pl.ParamatereizedPayload(arr7) },
				new Object[] { pl.ParamatereizedPayload(arr8) },
				new Object[] { pl.ParamatereizedPayload(arr9) },
				new Object[] { pl.ParamatereizedPayload(arr10) },
				new Object[] { pl.ParamatereizedPayload(arr11) },
				new Object[] { pl.ParamatereizedPayload(arr12) },
				new Object[] { pl.ParamatereizedPayload(arr17) },
				new Object[] { pl.ParamatereizedPayload(arr18) },
				new Object[] { pl.ParamatereizedPayload(arr19) },
				new Object[] { pl.ParamatereizedPayload(arr20) },
				new Object[] { pl.ParamatereizedPayload(arr21) },
				new Object[] { pl.ParamatereizedPayload(arr22) },
				new Object[] { pl.ParamatereizedPayload(arr27) },
				new Object[] { pl.ParamatereizedPayload(arr28) },
				new Object[] { pl.ParamatereizedPayload(arr29) },
				new Object[] { pl.ParamatereizedPayload(arr30) },
				new Object[] { pl.ParamatereizedPayload(arr31) },
				new Object[] { pl.ParamatereizedPayload(arr32) } };
	}

	@DataProvider
	public Object[][] SearchService_GetQuery_ErrorCases()
	{

		APINAME apiundertest = APINAME.GETQUERY;
		Payload pl = new Payload(apiundertest,
				InitializeFramework.init.Configurations, PayloadType.JSON);

		String[] arr1 = { "akhdkshadfiu", "500", "true", "false" };
		String[] arr2 = { "*&(*&*(&(&", "500", "true", "false" };
		String[] arr3 = { "Nike", "5000", "blah", "false" };
		String[] arr4 = { "Nike", "500", "true", "blah" };
		String[] arr5 = { "Nike", "5000", "blah", "*&*(&(^^!^!#$" };
		String[] arr6 = { "Nike", "5000", "*!&*&@(!", "false" };
		String[] arr7 = { "Nike", "null", "false", "false" };
		String[] arr8 = { "Nike", null, "false", "false" };
		String[] arr9 = { "Nike", "", "false", "false" };
		String[] arr10 = { "", "5000", "false", "false" };
		String[] arr11 = { "Nike", "5000", "", "false" };
		String[] arr12 = { "Nike", "5000", "false", "" };
		String[] arr13 = { "Nike", "5000", "", "" };
		String[] arr14 = { null, null, null, null };
		String[] arr15 = { "", "", "", "" };
		String[] arr16 = { "Nike", "5000", "false", null };
		String[] arr17 = { null, "5000", "false", "false" };
		String[] arr18 = { "Nike", "AJHDKJHK", "false", "false" };
		String[] arr19 = { "Nike", "5000", "false", "false" };
		String[] arr20 = { "Nike", "5000", "true87", "false" };
		String[] arr21 = { "Nike", "5000", "false", "false78" };
		String[] arr22 = { "Nike", "50000000000000", "true", "false" };
		String[] arr23 = { "Nike", "50000000000000", "true", "true" };
		String[] arr24 = { "Nike", "50000000000000", "false", "false" };
		String[] arr25 = { "Nike", "50000000000000", "false", "true" };
		String[] arr26 = { "Puma", "50000000000000", "true", "false" };
		String[] arr27 = { "Puma", "50000000000000", "true", "true" };
		String[] arr28 = { "Puma", "50000000000000", "false", "false" };
		String[] arr29 = { "Puma", "50000000000000", "false", "true" };
		String[] arr30 = { "Adidas", "50000000000000", "true", "false" };
		String[] arr31 = { "Adidas", "50000000000000", "true", "true" };
		String[] arr32 = { "Adidas", "50000000000000", "false", "false" };
		String[] arr33 = { "Adidas", "50000000000000", "false", "true" };

		return new Object[][] {
				new Object[] { pl.ParamatereizedPayload(arr1) },
				new Object[] { pl.ParamatereizedPayload(arr2) },
				new Object[] { pl.ParamatereizedPayload(arr3) },
				new Object[] { pl.ParamatereizedPayload(arr4) },
				new Object[] { pl.ParamatereizedPayload(arr5) },
				new Object[] { pl.ParamatereizedPayload(arr6) },
				new Object[] { pl.ParamatereizedPayload(arr7) },
				new Object[] { pl.ParamatereizedPayload(arr8) },
				new Object[] { pl.ParamatereizedPayload(arr9) },
				new Object[] { pl.ParamatereizedPayload(arr10) },
				new Object[] { pl.ParamatereizedPayload(arr11) },
				new Object[] { pl.ParamatereizedPayload(arr12) },
				new Object[] { pl.ParamatereizedPayload(arr13) },
				new Object[] { pl.ParamatereizedPayload(arr14) },
				new Object[] { pl.ParamatereizedPayload(arr15) },
				new Object[] { pl.ParamatereizedPayload(arr16) },
				new Object[] { pl.ParamatereizedPayload(arr17) },
				new Object[] { pl.ParamatereizedPayload(arr18) },
				new Object[] { pl.ParamatereizedPayload(arr19) },
				new Object[] { pl.ParamatereizedPayload(arr20) },
				new Object[] { pl.ParamatereizedPayload(arr21) },
				new Object[] { pl.ParamatereizedPayload(arr22) },
				new Object[] { pl.ParamatereizedPayload(arr23) },
				new Object[] { pl.ParamatereizedPayload(arr24) },
				new Object[] { pl.ParamatereizedPayload(arr25) },
				new Object[] { pl.ParamatereizedPayload(arr26) },
				new Object[] { pl.ParamatereizedPayload(arr27) },
				new Object[] { pl.ParamatereizedPayload(arr28) },
				new Object[] { pl.ParamatereizedPayload(arr29) },
				new Object[] { pl.ParamatereizedPayload(arr30) },
				new Object[] { pl.ParamatereizedPayload(arr31) },
				new Object[] { pl.ParamatereizedPayload(arr32) },
				new Object[] { pl.ParamatereizedPayload(arr33) }, };
	}
*/
	/*@DataProvider
	public Object[][] curatedSearchListByParamPageIdDataProvider(
			ITestContext testContext)
	{
		String[] pageId1 = { "517" };
		String[] pageId2 = { "161" };
		Object[][] dataSet = new Object[][] { pageId1 , pageId2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);

	}*/

	/*@DataProvider
	public Object[][] curatedSearchListByParamPageKeyDataProvider(
			ITestContext testContext)
	{
		String[] pagekey1 = { "78b952d4d2ffa92ddebdfbdbe1428caa" };
		String[] pagekey2 = { "a312bcd933f3f77f9a36c44a2dcb2033" };
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);

	}*/

	/*@DataProvider
	public Object[][] saveOrUpdateParamPageDataProvider(ITestContext testContext)
	{
		String[] arr1 = {
				"(brands_filter_facet:(\\\"UCB\\\") AND global_attr_article_type_facet:(\\\"UCB Tshirts\\\"))",
				"UCB T Shirts added" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] addnewLandingPageDataProvider(ITestContext testContext)
	{
		String[] arr1 = { "jockey-new", "8122", "Jocky new brand" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/

	/*@DataProvider
	public Object[][] updateExistingLandingPageDataProvider(
			ITestContext testContext)
	{
		String[] arr1 = { "7809", "jocky", "9001", "ye ander ki baat hai",
				"false" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
*/
	//------------------------------------------------------------------------------------------------------
	
	/**
	 * Get a Landing Page.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] landingpageDP(ITestContext context)
	{
		String[] brand1 = {"nike", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
	//	String[] brand2 = {"puma", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand3 = {"adidas", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand4 = {"reebok", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand5 = {"biba", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand6 = {"cat", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};

		Object[][] dataSet = new Object[][] { brand1, brand3, brand4, brand5, brand6 };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 6,4);
	}
	
	/**
	 * Get a Landing Page.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] landingpageDP_negative(ITestContext testContext)
	{
	//	String [] arr1 = {"$%#$%$#%#$", "10004", "\"Landing Page is not found\"", "\"ERROR\"", "0"};
		String[] arr2 = {"puma", "19002", "\"Found the required landing page\"", "\"SUCCESS\"","0"};
		String [] arr3 = {"fsdafsdfsd", "10004", "\"Landing Page is not found\"", "\"ERROR\"", "0"};
		Object [][] dataSet = new Object [][] { arr2,arr3};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);		
	}
	
	/**
	 * Get a Landing Page.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] landingpageDP_negative1(ITestContext testContext)
	{
		String [] arr1 = {""};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}
	
	/**
	 * Get a Parameterized Page by Page Key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] parameterisedPageByPageKeyDP(
			ITestContext testContext)
	{
		String[] pagekey1 = { "78b952d4d2ffa92ddebdfbdbe1428caa", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\"" };
		String[] pagekey2 = { "a312bcd933f3f77f9a36c44a2dcb2033", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\"" };
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}	
	
	/**
	 * Get a Parameterized Page by Page Key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] parameterisedPageByPageKeyDP_negative(ITestContext testContext)
	{
		String [] arr1 = {"$%#$%$#%#$", "10007", "\"parameterized Page is not found\"", "\"ERROR\"", "0"};
		String [] arr2 = {"dsf23423dsf34435df", "10007", "\"parameterized Page is not found\"", "\"ERROR\"", "0"};
		Object [][] dataSet = new Object [][] {arr1, arr2};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);		
	}
	
	/**
	 * Get a Parameterized Page by Page Key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] parameterisedPageByPageKeyDP_negative1(ITestContext testContext)
	{
		String [] arr1 = {""};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);		
	}
	
	/**
	 * Get a Parameterized Page by Page Id.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] parameterizedpageByPageIdDP(ITestContext context)
	{
		String[] pageId = {"517", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\""};
		String[] pageId2 = {"161", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\""};
		Object[][] dataSet = new Object[][] {  pageId, pageId2 };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get a Parameterized Page by Page Key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] parameterizedpageByPageIdDP_negative(ITestContext testContext)
	{
		String [] arr1 = {"123", "10007", "\"parameterized Page is not found\"", "\"ERROR\"", "0"};
		String [] arr2 = {"001", "10007", "\"parameterized Page is not found\"", "\"ERROR\"", "0"};
		String [] arr3 = {"-234", "10007", "\"parameterized Page is not found\"", "\"ERROR\"", "0"};
		Object [][] dataSet = new Object [][] {arr1, arr2, arr3};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3,4);		
	}
	
	/**
	 * Get a Parameterized Page by Page Key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object [][] parameterizedpageByPageIdDP_negative1(ITestContext testContext)
	{
		String [] arr1 = {"dsf"};
		String [] arr2 = {""};
		String [] arr3 = {"$%&^&"};
		Object [][] dataSet = new Object [][] {arr1, arr2, arr3};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3,4);		
	}
	
	/**
	 * Get Curated Search List by Parameterized Page Id.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageIdDP(
			ITestContext testContext)
	{
		String[] pageId1 = { "517", "19010", "\"Found the required curated search\"", "\"SUCCESS\"" };
		String[] pageId2 = { "161", "19010", "\"Found the required curated search\"", "\"SUCCESS\""  };
		Object[][] dataSet = new Object[][] { pageId1 , pageId2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page Id.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageIdDP_negative(
			ITestContext testContext)
	{
		String[] pageId1 = { "5170", "100012", "\"Error while retrieving curated search list\"", "\"ERROR\"", "0" };
		String[] pageId2 = { "16100", "100012", "\"Error while retrieving curated search list\"", "\"ERROR\"", "0"  };
		Object[][] dataSet = new Object[][] { pageId1 , pageId2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page Id.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageIdDP_negative1(
			ITestContext testContext)
	{
		String[] pageId1 = { "sdfj"};
		String[] pageId2 = { "$%^%&5%^%$^"};
		Object[][] dataSet = new Object[][] { pageId1, pageId2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page Id.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageIdDP_negative2(
			ITestContext testContext)
	{
		String[] pageId1 = { ""};
		Object[][] dataSet = new Object[][] { pageId1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageKeyDP(
			ITestContext testContext)
	{
		String[] pagekey1 = { "78b952d4d2ffa92ddebdfbdbe1428caa", "19010", "\"Found the required curated search\"", "\"SUCCESS\"", "0" };
		String[] pagekey2 = { "a312bcd933f3f77f9a36c44a2dcb2033", "19010", "\"Found the required curated search\"", "\"SUCCESS\"", "0" };
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageKeyDP_negative(
			ITestContext testContext)
	{
		String[] pagekey1 = { "78b952", "100012", "\"Error while retrieving curated search list\"", "\"ERROR\"", "0" };
		String[] pagekey2 = { "asdfsdfdwer", "100012", "\"Error while retrieving curated search list\"", "\"ERROR\"", "0" };
		String[] pagekey3 = { "$@#^%^%^%&", "100012", "\"Error while retrieving curated search list\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2, pagekey3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get Curated Search List by Parameterized Page key.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] curatedSearchListByParamPageKeyDP_negative1(
			ITestContext testContext)
	{
		String[] pagekey1 = { ""};
		Object[][] dataSet = new Object[][] { pagekey1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 * Get a Query
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] SearchService_GetQuery_withfalsereturndoc(ITestContext Context)
	{
		String[] arr1 = { "Nike", "50", "false", "true" };
		String[] arr2 = { "Puma", "50", "false", "false" };
		String[] arr3 = { "Adidas", "50", "false", "false" };
		String[] arr4 = { "Reebok", "50", "false", "false" };
		String[] arr5 = { "Biba", "50", "false", "false" };
		String[] arr6 = { "Cat", "50", "false", "false" };
		String[] arr7 = { "Nike", "24", "false", "false" };
		String[] arr8 = { "Nike", "24", "false", "true" };
		String[] arr9 = { "Nike", "50", "false", "true" };
		String[] arr10 = { "Puma", "24", "false", "false" };
		String[] arr11 = { "Puma", "24", "false", "true" };
		String[] arr12 = { "Puma", "50", "false", "true" };
		String[] arr13 = { "Adidas", "24", "false", "false" };
		String[] arr14 = { "Adidas", "24", "false", "true" };
		String[] arr15 = { "Adidas", "50", "false", "true" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14,
				arr15 };
		return Toolbox.returnReducedDataSet(dataSet,
				Context.getIncludedGroups(), 3,4);
	}

	/**
	 * invalidate cache.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] invalidateCacheDP(ITestContext context)
	{
		String message = "\"Invalidated cache entry with key: ";
		String[] brand1 = {"nike", "true", message+"nike\""};
		String[] brand2 = {"puma", "true", message+"puma\""};
		String[] brand3 = {"adidas", "true", message+"adidas\""};
		String[] brand4 = {"reebok", "true", message+"reebok\""};
		String[] brand5 = {"biba", "true", message+"biba\""};
		String[] brand6 = {"cat", "true", message+"cat\""};
		String[] brand7 = {"nik", "true", message+"nik\""};
	//	String[] brand8 = {"$^%&", "true", message+"$^%&\""};
		String[] brand9 = {"", "true", "\"Invalidated all cache entries\""};
		String[] brand10 = {"sfsdfdsf", "true", message+"sfsdfdsf\""};

		Object[][] dataSet = new Object[][] { brand1, brand2, brand3, brand4, brand5, brand6, brand7, brand9, brand10 };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 5,6);
	}
	
	/**
	 * invalidate cache.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] reCachedDP(ITestContext context)
	{
		String message = "\"Re-cached offer page: ";
		String[] brand1 = {"nike", "true", message+"nike\""};
		String[] brand2 = {"puma", "true", message+"puma\""};
		String[] brand3 = {"adidas", "true", message+"adidas\""};
		String[] brand4 = {"reebok", "true", message+"reebok\""};
		String[] brand5 = {"biba", "true", message+"biba\""};
		String[] brand6 = {"cat", "true", message+"cat\""};
		String[] brand7 = {"nik", "true", message+"nik\""};
	//	String[] brand8 = {"$^%&", "true", message+"$^%&\""};
		String[] brand9 = {"sfsdfdsf", "true", message+"sfsdfdsf\""};

		Object[][] dataSet = new Object[][] { brand1, brand2, brand3, brand4, brand5, brand6, brand7, brand9 };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 4,5);
	}
	
	/**
	 * invalidate cache.
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] reCachedDP_negative(ITestContext context)
	{
		String[] brand1 = {""};

		Object[][] dataSet = new Object[][] { brand1 };
		return Toolbox.returnReducedDataSet(dataSet,
				context.getIncludedGroups(), 1,2);
	}
	//-----------------------------Below data provides are commented due to theses Apis are skipped ------------------------
	
/*	*//**
	 * Save or Update a Parameterized Page.
	 * @author jhansi.bai
	 *//*
	@DataProvider
	public Object[][] saveOrUpdateParamPageDP(ITestContext testContext)
	{
		String[] arr1 = {
				"(brands_filter_facet:(\\\"UCB_Automation\\\") AND global_attr_article_type_facet:(\\\"UCB_AutomationTest Tshirts\\\"))",
				"UCB_AutomationTest T Shirts added",  "", "", ""};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	*//**
	 *  Add a new Landing Page.
	 * @author jhansi.bai
	 *//*
	@DataProvider
	public Object[][] addnewLandingPageDP_negative1(ITestContext testContext)
	{
		String[] arr1 = { "cat", "325743", "cat", "10009",  "\"Error while adding a new landing page\"", "\"ERROR\"", "0",
				"false", "\"landing page not added; page url already exists\""};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	*//**
	 *  Add a new Landing Page.
	 * @author jhansi.bai
	 *//*
	@DataProvider
	public Object[][] addnewLandingPageDP_negative2(ITestContext testContext)
	{
		String[] arr1 = { "jockey-new", "8122", "Jocky new brand", "10009",  "\"Error while adding a new landing page\"", "\"ERROR\"", "0",
				"false", "\"parameterized page entity does not exist for given id\""};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	*//**
	 *  Add a new Landing Page.
	 * @author jhansi.bai
	 *//*
	@DataProvider
	public Object[][] addnewLandingPageDP_negative3(ITestContext testContext)
	{
		String[] arr1 = { "jockeyTest", "81", "Jocky new brand"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}*/
	
	@DataProvider
	public Object[][] SearchServiceDP_landingPageForBrands_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		String[] brand1 = {"nike", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand2 = {"puma", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand3 = {"adidas", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand4 = {"reebok", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand5 = {"biba", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};
		String[] brand6 = {"cat", "19002", "\"Found the required landing page\"", "\"SUCCESS\""};

		Object[][] dataSet = new Object[][] { brand1, brand2, brand3, brand4, brand5, brand6 };
		return Toolbox.returnReducedDataSet(dataSet, context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_parameterisedPageByPageKey_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] pagekey1 = { "78b952d4d2ffa92ddebdfbdbe1428caa", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\"" };
		String[] pagekey2 = { "a312bcd933f3f77f9a36c44a2dcb2033", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\"" };
		
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_parameterizedPageById_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		String[] pageId = {"517", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\""};
		String[] pageId2 = {"161", "19006", "\"Found the required parameterized page\"", "\"SUCCESS\""};
		
		Object[][] dataSet = new Object[][] { pageId, pageId2 };
		return Toolbox.returnReducedDataSet(dataSet, context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_curatedSearchListByParamPageId_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] pageId1 = { "517", "19010", "\"Found the required curated search\"", "\"SUCCESS\"" };
		String[] pageId2 = { "161", "19010", "\"Found the required curated search\"", "\"SUCCESS\""  };
		
		Object[][] dataSet = new Object[][] { pageId1 , pageId2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] SearchServiceDP_curatedSearchListByParamPageKey_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] pagekey1 = { "78b952d4d2ffa92ddebdfbdbe1428caa", "19010", "\"Found the required curated search\"", "\"SUCCESS\"", "0" };
		String[] pagekey2 = { "a312bcd933f3f77f9a36c44a2dcb2033", "19010", "\"Found the required curated search\"", "\"SUCCESS\"", "0" };
		
		Object[][] dataSet = new Object[][] { pagekey1 , pagekey2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_getQueryWithtReturnDoc_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { "Nike", "50", "true", "false" };
		String[] arr2 = { "Puma", "50", "true", "false" };
		String[] arr3 = { "Adidas", "50", "true", "false" };
		String[] arr4 = { "Reebok", "50", "true", "false" };
		String[] arr5 = { "Biba", "50", "true", "false" };
		String[] arr6 = { "Cat", "50", "true", "false" };
		String[] arr7 = { "Nike", "24", "true", "false" };
		String[] arr8 = { "Nike", "24", "true", "true" };
		String[] arr9 = { "Nike", "50", "true", "true" };
		String[] arr10 = { "Puma", "24", "true", "false" };
		String[] arr11 = { "Puma", "24", "true", "true" };
		String[] arr12 = { "Puma", "50", "true", "true" };
		String[] arr13 = { "Adidas", "24", "true", "false" };
		String[] arr14 = { "Adidas", "24", "true", "true" };
		String[] arr15 = { "Adidas", "50", "true", "true" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_getQueryWithfReturnDoc_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context)
	{
		String[] arr1 = { "Nike", "50", "false", "true" };
		String[] arr2 = { "Puma", "50", "false", "false" };
		String[] arr3 = { "Adidas", "50", "false", "false" };
		String[] arr4 = { "Reebok", "50", "false", "false" };
		String[] arr5 = { "Biba", "50", "false", "false" };
		String[] arr6 = { "Cat", "50", "false", "false" };
		String[] arr7 = { "Nike", "24", "false", "false" };
		String[] arr8 = { "Nike", "24", "false", "true" };
		String[] arr9 = { "Nike", "50", "false", "true" };
		String[] arr10 = { "Puma", "24", "false", "false" };
		String[] arr11 = { "Puma", "24", "false", "true" };
		String[] arr12 = { "Puma", "50", "false", "true" };
		String[] arr13 = { "Adidas", "24", "false", "false" };
		String[] arr14 = { "Adidas", "24", "false", "true" };
		String[] arr15 = { "Adidas", "50", "false", "true" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_invalidateCache_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		String message = "\"Invalidated cache entry with key: ";
		String[] brand1 = {"nike", "true", message+"nike\""};
		String[] brand2 = {"puma", "true", message+"puma\""};
		String[] brand3 = {"adidas", "true", message+"adidas\""};
		String[] brand4 = {"reebok", "true", message+"reebok\""};
		String[] brand5 = {"biba", "true", message+"biba\""};
		String[] brand6 = {"cat", "true", message+"cat\""};
		String[] brand7 = {"nik", "true", message+"nik\""};
	//	String[] brand8 = {"$^%&", "true", message+"$^%&\""};
		String[] brand9 = {"", "true", "\"Invalidated all cache entries\""};
		String[] brand10 = {"sfsdfdsf", "true", message+"sfsdfdsf\""};

		Object[][] dataSet = new Object[][] { brand1, brand2, brand3, brand4, brand5, brand6, brand7,  brand9, brand10 };
		return Toolbox.returnReducedDataSet(dataSet, context.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] SearchServiceDP_reCache_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		String message = "\"Re-cached offer page: ";
		String[] brand1 = {"nike", "true", message+"nike\""};
		String[] brand2 = {"puma", "true", message+"puma\""};
		String[] brand3 = {"adidas", "true", message+"adidas\""};
		String[] brand4 = {"reebok", "true", message+"reebok\""};
		String[] brand5 = {"biba", "true", message+"biba\""};
		String[] brand6 = {"cat", "true", message+"cat\""};
		String[] brand7 = {"nik", "true", message+"nik\""};
	//	String[] brand8 = {"$^%&", "true", message+"$^%&\""};
		String[] brand9 = {"sfsdfdsf", "true", message+"sfsdfdsf\""};

		Object[][] dataSet = new Object[][] { brand1, brand2, brand3, brand4, brand5, brand6, brand7,  brand9 };
		return Toolbox.returnReducedDataSet(dataSet, context.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] SearchService_createCuratedPageDataProvider(ITestContext Context)
	{
		String[] arr1 = { "Nike-shoes", "354914,297066,297052,297222" };
		String[] arr2 = { "Puma", "222453,61445,163941" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1, 2);
	}
	
	/**
	 *  Params for getQuery.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryWithValidInputs(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "true", "true", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQuery having return_docs as false.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryWithReturnDocsAsFalseDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "false", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "false", "true", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "false", "true", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "false", "true", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "false", "true", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "false", "true", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQuery having is_facet as false.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryWithIsFacetAsFalseDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	
	
	/**
	 *  Params for getQuery having is_facet as false.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryWithSearchDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQuery having is_facet as false.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryNavNodeValidation(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com" };
		String[] arr2 = { "Adidas", "true", "false", "sneha.test02@myntra.com" };
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithSuccessMessageDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "true", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "true", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQueryGET having return_docs as false
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithReturnDocsAsFalseDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "false", "true", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "false", "true", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "false", "true", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "false", "true", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "false", "true", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "false", "true", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithIsFacetAsFalseDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithValidationSearch(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithValidationQueryResult(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithValidationSolr(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithValidationGuidedNav(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		
		String[] arr2 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
	
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,6);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithValidationRoot(ITestContext Context)
	{
		String[] arr1 = { "Nike", "true", "false", "sneha.test01@myntra.com", "48" };
		String[] arr2 = { "Puma", "true", "false", "sneha.test02@myntra.com","24" };
		String[] arr3 = { "Adidas", "true", "false", "sneha.test03@myntra.com", "10" };
		String[] arr4 = { "Reebok", "true", "false", "sneha.test04@myntra.com", "60" };
		String[] arr5 = { "Biba", "true", "false", "sneha.test05@myntra.com", "96" };
		String[] arr6 = { "Cat", "true", "false", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	
	
	
	/**
	 *  Params for filteredSerachForBrand.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchDP(ITestContext Context)
	{
		String[] arr1 = { "(brands_filter_facet:(\\\"Nike\\\"))"};
		String[] arr2 = { "(global_attr_sub_category_facet:(\\\"Shoes\\\"))"};
		String[] arr3 = { "(global_attr_gender:(\\\"Women\\\"))"};
		String[] arr4 = { "(global_attr_base_colour:(\\\"green\\\" OR \\\"white\\\"))"};
		String[] arr5 = { "(full_text_myntra:(\\\"American\\\" ))"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	/**
	 *  Params for filteredSerachForBrand.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchRootNodeDP(ITestContext Context)
	{
		String[] arr1 = { "(brands_filter_facet:(\\\"Nike\\\"))"};
		String[] arr2 = { "(global_attr_sub_category_facet:(\\\"Shoes\\\"))"};
		String[] arr3 = { "(global_attr_gender:(\\\"Women\\\"))"};
		String[] arr4 = { "(global_attr_base_colour:(\\\"green\\\" OR \\\"white\\\"))"};
		String[] arr5 = { "(full_text_myntra:(\\\"American\\\" ))"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	
	/**
	 *  Params for filteredSerachForBrand.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchFilterRootNodeDP(ITestContext Context)
	{
		String[] arr1 = { "(brands_filter_facet:(\\\"Nike\\\"))"};
		String[] arr2 = { "(global_attr_sub_category_facet:(\\\"Shoes\\\"))"};
		String[] arr3 = { "(global_attr_gender:(\\\"Women\\\"))"};
		String[] arr4 = { "(global_attr_base_colour:(\\\"green\\\" OR \\\"white\\\"))"};
		String[] arr5 = { "(full_text_myntra:(\\\"American\\\" ))"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	/**
	 *  Params for filteredSerachForBrand.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchProductNodeDP(ITestContext Context)
	{
		String[] arr1 = { "(brands_filter_facet:(\\\"Nike\\\"))"};
		String[] arr2 = { "(global_attr_sub_category_facet:(\\\"Shoes\\\"))"};
		String[] arr3 = { "(global_attr_gender:(\\\"Women\\\"))"};
		String[] arr4 = { "(global_attr_base_colour:(\\\"green\\\" OR \\\"white\\\"))"};
		String[] arr5 = { "(full_text_myntra:(\\\"American\\\" ))"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	/**
	 *  Params for autoSuggest.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_autoSuggestDP(ITestContext Context)
	{
		String[] arr1 = {"reebok"};
		String[] arr2 = {"adi"};
		String[] arr3 = {"NI"};
		String[] arr4 = {"Ba"};
		String[] arr5 = {"wo"};
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,4);
	}
	
	/**
	 *  Params for autoSuggest.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_autoSuggest_validatingStatusSubNodesDP(ITestContext Context)
	{
		String[] arr1 = { "Nike", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		String[] arr2 = { "Puma", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		String[] arr3 = { "adi", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		String[] arr4 = { "NI", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		String[] arr5 = { "Ba", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		String[] arr6 = { "wo", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\"" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for autoSuggest - negative scenario - query values for which solr does not entries.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_autoSuggest_negativeCases1DP(ITestContext Context)
	{
		String[] arr1 = { "jjhfhejhjkfhkehjfiue" };
		String[] arr2 = { "123456" };
		String[] arr3 = { "%40%24%5E%25%26%5E%5E%25%5E%26" };
		String[] arr4 = { "fvhkjej.1i313hj;hjh34" };
		String[] arr5 = { "123.456" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	/**
	 *  Params for autoSuggest - negative scenario1 - query values for which solr does not entries.
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_autoSuggest_negativeCases1_validatingStatusSubNodesDP(ITestContext Context)
	{
		String[] arr1 = { "jjhfhejhjkfhkehjfiue", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\""  };
		String[] arr2 = { "123456", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\""  };
		String[] arr3 = { "%40%24%5E%25%26%5E%5E%25%5E%26", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\""  };
		String[] arr4 = { "fvhkjej.1i313hj;hjh34", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\""  };
		String[] arr5 = { "123.456", "19001", "\"Found the required information from SOLR\"", "\"SUCCESS\""  };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 5,5);
	}
	
	/**
	 *  Params for autoSuggest - for negative scenario2 - blank query
	 * @author sneha.deep
	 */
	@DataProvider
	public Object[][] SearchService_autoSuggest_negativeCases2_blankQueryDP(
			ITestContext testContext)
	{
		String[] query1 = { "" };
		Object[][] dataSet = new Object[][] { query1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,2);
	}
	
	/**
	 *  Params for autoSuggest - negative scenario - bad gateway.
	 * @author sneha.deep
	 *//*
	@DataProvider
	public Object[][] SearchService_autoSuggest_negativeCases2_badRequestDP(ITestContext Context)
	{
		String[] arr1 = { " " };
		
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,2);
	}*/

	@DataProvider
	public Object[][] SearchService_getQueryWithValidInput(ITestContext Context)
	{
		String[] arr1 = { "Shirt", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "true", "true", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 5,6);
	}
	
	
	@DataProvider
	public Object[][] SearchService_getQueryWithValidInputForSolrNodes(ITestContext Context)
	{
		String[] arr1 = { "Shirts", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "Puma", "true", "true", "sneha.test02@myntra.com" };
		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com" };
		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com" };
		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com" };
		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
//		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 5,6);
	}
	
	
	@DataProvider
	public Object[][] SearchService_getQueryWithValidInputForAllArticleNodes(ITestContext Context)
	{
		String[] arr1 = { "Shirt", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "Jeans", "true", "true", "sneha.test02@myntra.com" };
		String[] arr3 = { "Tshirts", "true", "true", "sneha.test03@myntra.com" };
		String[] arr4 = { "Skirts", "true", "true", "sneha.test04@myntra.com" };
		String[] arr5 = { "Sari", "true", "true", "sneha.test05@myntra.com" };
    	String[] arr6 = { "Kurtas", "true", "true", "sneha.test06@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6 };
		
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 5,6);
	}
	
	/**
	 *  Params for Trends
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_trends(ITestContext Context)
	{
		String[] arr1 = { "10" };
		String[] arr2 = { "20" };
		String[] arr3 = { "67" };
		String[] arr4 = { "40" };
		String[] arr5 = { "100" };
		String[] arr6 = { "150" };
		String[] arr7 = { "1000" };
		
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 7,7);
		
		
	}
	
	/**
	 *  Params for Trends.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_trendsNegative(ITestContext Context)
	{
		String[] arr1 = { "-40" };
		String[] arr2 = { "8/9" };
		
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 9,9);
		
		
	}
	
	/**
	 *  Params for getQueryPOST PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrder(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test01@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 1,1);
		
		
	}
	
	/**
	 *  Params for getQueryGET PreOrder.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithPreOrderDP(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test02@myntra.com","48"};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 1,1);
	}
	
	/**
	 *  Params for getQueryPOST PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderWithCMS(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test01@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 1,1);
		
		
	}
	/**
	 *  Params for getQueryPOST PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderWithPdpStyle(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "12348", "true", "true", "sneha.test01@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 2,2);
		
		
	}
	
	/**
	 *  Params for getQueryGET PreOrder.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_getQueryGetWithPreOrderStyleDP(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test02@myntra.com","48"};
		String[] arr2 = { "12348", "true", "true", "sneha.test02@myntra.com","48"};

		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQueryPOST PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderWithCMSActiveisFalse(ITestContext Context)
	{
		String[] arr1 = { "12347", "true", "true", "sneha.test01@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 1,1);
		
		
	}
	
	/**
	 *  Params for getQueryPOST PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderCMSActiveisTrue(ITestContext Context)
	{
		String[] arr1 = { "12349", "true", "true", "sneha.test02@myntra.com","48"};
		
		Object[][] dataSet = new Object[][] { arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 1,1);
		
		
	}
	
	/**
	 *  Params for PDP data PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderWithPdpdata(ITestContext Context)
	{
		String[] arr1 = { "12349" };
		String[] arr2 = { "12345" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 2,2);
		
		
	}
	
	
	/**
	 *  Params for PDP data PreOrder.
	 * @author jitender.kumar1
	 */
	
	@DataProvider
	public Object[][] SearchService_preOrderWithPdpandCMSdata(ITestContext Context)
	{
		String[] arr1 = { "12347" };
		
		Object[][] dataSet = new Object[][] { arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 1,1);
		
		
	}
	
	
	/**
	 *  Params for filteredSerachForPreOrder.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchPreOrder(ITestContext Context)
	{
		String[] arr1 = { "(styleid:12348)"};
		String[] arr2 = { "(styleid:12349)"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	
	/**
	 *  Params for filteredSerachForPreOrderCMS.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_filteredSearchPreOrderCMS(ITestContext Context)
	{
		String[] arr1 = { "(styleid:12348)", "12348"};
		String[] arr2 = { "(styleid:12349)", "12349"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_StaggeredComboGet(ITestContext Context)
	{
		String[] arr1 = { "308468", "true", "true", "sneha.test01@myntra.com", "48" };
		
		String[] arr2 = { "267620", "true", "true", "sneha.test02@myntra.com","48" };
//		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com", "10" };
//		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com", "60" };
//		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com", "96" };
//		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_StaggeredComboDis(ITestContext Context)
	{
		String[] arr1 = { "308484", "true", "true", "sneha.test01@myntra.com", "48" };
		
		String[] arr2 = { "375405", "true", "true", "sneha.test02@myntra.com","48" };
//		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com", "10" };
//		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com", "60" };
//		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com", "96" };
//		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQueryGET.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_FixedPriceDis(ITestContext Context)
	{
		String[] arr1 = { "308477", "true", "true", "sneha.test01@myntra.com", "48" };
		
		String[] arr2 = { "308481", "true", "true", "sneha.test02@myntra.com","48" };
//		String[] arr3 = { "Adidas", "true", "true", "sneha.test03@myntra.com", "10" };
//		String[] arr4 = { "Reebok", "true", "true", "sneha.test04@myntra.com", "60" };
//		String[] arr5 = { "Biba", "true", "true", "sneha.test05@myntra.com", "96" };
//		String[] arr6 = { "Cat", "true", "true", "sneha.test06@myntra.com","12" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQuery.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_FixedPricePost(ITestContext Context)
	{
		String[] arr1 = { "308477", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "308481", "true", "true", "sneha.test02@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 2,2);
	}
	
	/**
	 *  Params for getQuery.
	 * @author jitender.kumar1
	 */
	@DataProvider
	public Object[][] SearchService_StaggeredComboPost(ITestContext Context)
	{
		String[] arr1 = { "308484", "true", "true", "sneha.test01@myntra.com" };
		String[] arr2 = { "375405", "true", "true", "sneha.test02@myntra.com" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,Context.getIncludedGroups(), 2,2);
	}
}
