package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

/**
 * @author shankara.c
 *
 */
public class CatalogServiceDP extends CommonUtils
{
	Configuration con = new Configuration("./Data/configuration");
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	Commons commonUtil = new Commons();
	String Env;
	public CatalogServiceDP(){
		if(System.getenv("ENVIRONMENT")==null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	List<Integer> styleIdNike = styleHelper.performSeachServiceToGetStyleIds("nike", "15", "true", "false");
	List<Integer> styleIdAdidas = styleHelper.performSeachServiceToGetStyleIds("Adidas", "15", "true", "false");
	List<Integer> styleIdPuma = styleHelper.performSeachServiceToGetStyleIds("Puma", "15", "true", "false");
	List<Integer> styleIdJackets = styleHelper.performSeachServiceToGetStyleIds("Jackets", "15", "true", "false");
	List<Integer> styleIdShoes = styleHelper.performSeachServiceToGetStyleIds("Shoes", "15", "true", "false");
	List<Integer> styleIdJeans = styleHelper.performSeachServiceToGetStyleIds("Jeans", "15", "true", "false");
	List<Integer> styleIdShirts = styleHelper.performSeachServiceToGetStyleIds("Shirts", "15", "true", "false");
	List<Integer> styleIdTshirts = styleHelper.performSeachServiceToGetStyleIds("Tshirts", "15", "true", "false");
	List<Integer> styleIdSunglasses = styleHelper.performSeachServiceToGetStyleIds("Sunglasses", "15", "true", "false");
	List<Integer> styleIdWallets = styleHelper.performSeachServiceToGetStyleIds("Wallets", "15", "true", "false");
	List<Integer> styleIdSportsShoes = styleHelper.performSeachServiceToGetStyleIds("flip-flops", "15", "true", "false");


	@DataProvider
	public Object[][] getProductByIdDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
											  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
											  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
											  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
											  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
											  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
											  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
											  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
											  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
											  new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getProductByIdNegativeDataProvider(ITestContext testContext)
	{
		String[] conId1 = { "10000000000" };
		String[] conId2 = { "-1" };
		Object[][] dataSet = new Object[][] { conId1,
											  conId2,
											  new String[]{"9999999"+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
											  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdNike.size()))+"0000000000"},
											  new String[]{"-"+styleIdPuma.get(getRandomNumber(styleIdNike.size()))}
											  };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}


	@DataProvider
	public Object[][] searchForProductByCriteriaDataProvider(ITestContext testContext)
	{
		String[] conId1 = { "q=productId.in:291966,292335&styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId2 = { "q=productId.lt:292698___productId.gt:292500" };
		String[] conId3 = { "q=productId.in:293937,360726___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId4 = { "q=productId.lt:5000___styleType.eq:A&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId5 = { "q=productId.in:291966,292335,293937,360726___styleType.eq:P&start=0&fetchSize=10" };
		String[] conId6 = { "q=styleType.eq:P&f=productId,styleType&tart=0&fetchSize=100" };
		String[] conId7 = { "q=productId.lt:290000___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId8 = { "q=productId.gt:290000&f=productId,styleType&start=0&fetchSize=100&sortBy=productId&sortOrder=DESC" };
		String[] conId9 = { "q=productId.in:293937,360726___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10&sortBy=productId&sortOrder=ASC" };
		
		String[] conId10 = { "q=productId.gt:315068&amp;isCustomizable.eq:false&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId11 = { "q=productId.gt:315226&amp;styleType.eq:A&amp;f=productId&amp;start=10&amp;fetchSize=500&amp;sortBy=productId&amp;sortOrder=ASC" };
		String[] conId12 = { "q=productId.lt:315443&amp;isCustomizable.eq:true&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId13 = { "q=productId.gt:314980&amp;styleType.eq:P&amp;f=productId&amp;start=10&amp;fetchSize=1000&amp;sortBy=productId&amp;sortOrder=DESC" };
		String[] conId14 = { "q=productId.lt:317039&amp;styleType.eq:D&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=DESC" };
		String[] conId15 = { "q=productId.lt:315201&amp;isCustomizable.eq:true&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId16 = { "q=productId.in:315673,316043,316697,315674,316045,316048,314988,317457,255872,255803,316943,255191,315462,255239,315323,255209,315324,315325,255249,255210,255241&amp;styleType.eq:P&amp;f=styleType&amp;start=0&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=DESC" };
		String[] conId17 = { "q=productId.lt:255242&amp;codEnabled.eq:N&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC"};
		String[] conId18 = { "q=productId.in:255215,255255,255216,255257,255258,255259,255561,255643,315628,255345,317045,255354,317048,315632,315633,255174,255176,255185,315221,315565,315600&amp;styleType.eq:D&amp;f=productId&amp;start=0&amp;fetchSize=1000&amp;sortBy=productId&amp;sortOrder=DESC"};
		String[] conId19 = { "q=productId.in:315567,315447,254734,255218,315572,255219,254739,254744,317040,315621,315592,315597,254740,255338,254741,318102,255869,256492,255177,255898,315631&amp;styleType.eq:P&amp;f=styleType&amp;start=0&amp;fetchSize=100&amp;sortBy=styleType&amp;sortOrder=ASC"};
		
		Object[][] dataSet = new Object[][] { //conId1, conId2, conId3, conId4, conId5, conId6, conId7, conId8, conId9,
											  conId10, conId11, conId12, conId13, conId14, conId15, conId16, conId17, conId18, conId19 
											};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] searchForProductByCriteriaNegativeDataProvider(ITestContext testContext)
	{
		String[] conId1 = { "q=productId.in:1,2" };
		String[] conId2 = { "q=productId.lt:1&amp;productId.gt:100000000000000" };
		String[] conId3 = { "q=productId.in:293937,360726&amp;styleType.eq:Automation" };
				
		Object[][] dataSet = new Object[][] { conId1,conId2,conId3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 3);
	}
	
	
	@DataProvider
	public Object[][] clearCacheForProductDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
				  new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] clearCacheForProductNegativeDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))+"0000000000,"+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
		};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}


	@DataProvider
	public Object[][] clearCacheForListOfProductsDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()},
				  new String[]{getRandomCommaSeperatedStyleIds()}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] getSizeChartOfProductByIdDataProvider(
			ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
				  new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] findByListOfProductIdsDataProvider(
			ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()},
											  new String[]{getRandomCommaSeperatedStyleIds()}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] findByListOfProductIdsNegativeDataProvider(
			ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds().replaceAll("0", "0000000000").replaceAll("1", "111111111111111").replaceAll("2", "222222222222222").replaceAll("3", "333333333333333").replaceAll("4", "444444444444444").replaceAll("5", "555555555555555")},
											  new String[]{getRandomCommaSeperatedStyleIds().replaceAll("1", "1111111111").replaceAll("2", "222222222222222").replaceAll("3", "333333333333333").replaceAll("4", "444444444444444").replaceAll("5", "555555555555555")},
		};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	private int getRandomNumber(int size){
		Random ran = new Random();
		return ran.nextInt(size);
	}
	
	private Object[] sumOfAllStyleIds(){
		int size = styleIdAdidas.size();
		Object[] adidasJackets = ArrayUtils.addAll(styleIdAdidas.toArray(), styleIdJackets.toArray());
		Object[] jeansNike = ArrayUtils.addAll(styleIdJeans.toArray(), styleIdNike.toArray());
		Object[] pumaShirts = ArrayUtils.addAll(styleIdPuma.toArray(), styleIdShirts.toArray());
		Object[] shoesSportsShoes = ArrayUtils.addAll(styleIdShoes.toArray(), styleIdSportsShoes.toArray());
		Object[] tshirtsWallets = ArrayUtils.addAll(styleIdTshirts.toArray(), styleIdWallets.toArray());
		
		Object[] adidasJacketsJeansNike = ArrayUtils.addAll(adidasJackets, jeansNike);
		Object[] pumaShirtsShoesSportsShoes = ArrayUtils.addAll(pumaShirts, shoesSportsShoes);
		
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoes = ArrayUtils.addAll(adidasJacketsJeansNike, pumaShirtsShoesSportsShoes);
		
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets = ArrayUtils.addAll(adidasJacketsJeansNikePumaShirtsShoesSportsShoes, tshirtsWallets);
		
		return adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets;
	}
	
	private String getRandomCommaSeperatedStyleIds(){
		String styleIds = null;
		try {
			styleIds = commonUtil.getCommaSeperatedValuesFromArray(sumOfAllStyleIds(), 30, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return styleIds;
		
	}
	
	@DataProvider
	public Object[][] CatalogServiceDP_getProductById_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ""+styleIdNike.get(getRandomNumber(styleIdNike.size())), "200" };
		String[] arr2 = new String[]{ ""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size())), "200" };
		String[] arr3 = new String[]{ ""+styleIdPuma.get(getRandomNumber(styleIdPuma.size())), "200" };
		String[] arr4 = new String[]{ ""+styleIdJackets.get(getRandomNumber(styleIdJackets.size())), "200" };
		String[] arr5 = new String[]{ ""+styleIdShoes.get(getRandomNumber(styleIdShoes.size())), "200" };
		String[] arr6 = new String[]{ ""+styleIdJeans.get(getRandomNumber(styleIdJeans.size())), "200" };
		String[] arr7 = new String[]{ ""+styleIdShirts.get(getRandomNumber(styleIdShirts.size())), "200" };
		String[] arr8 = new String[]{ ""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size())), "200" };
		String[] arr9 = new String[]{ ""+styleIdWallets.get(getRandomNumber(styleIdWallets.size())), "200" };
		String[] arr10 = new String[]{ ""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CatalogServiceDP_searchForProductByCriteria_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] conId1 = { "q=productId.in:291966,292335&styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId2 = { "q=productId.lt:292698___productId.gt:292500" };
		String[] conId3 = { "q=productId.in:293937,360726___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId4 = { "q=productId.lt:5000___styleType.eq:A&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId5 = { "q=productId.in:291966,292335,293937,360726___styleType.eq:P&start=0&fetchSize=10" };
		String[] conId6 = { "q=styleType.eq:P&f=productId,styleType&tart=0&fetchSize=100" };
		String[] conId7 = { "q=productId.lt:290000___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10" };
		String[] conId8 = { "q=productId.gt:290000&f=productId,styleType&start=0&fetchSize=100&sortBy=productId&sortOrder=DESC" };
		String[] conId9 = { "q=productId.in:293937,360726___styleType.eq:P&f=productId,styleType&start=0&fetchSize=10&sortBy=productId&sortOrder=ASC" };
		
		String[] conId10 = { "q=productId.gt:315068&amp;isCustomizable.eq:false&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId11 = { "q=productId.gt:315226&amp;styleType.eq:A&amp;f=productId&amp;start=10&amp;fetchSize=500&amp;sortBy=productId&amp;sortOrder=ASC" };
		String[] conId12 = { "q=productId.lt:315443&amp;isCustomizable.eq:true&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId13 = { "q=productId.gt:314980&amp;styleType.eq:P&amp;f=productId&amp;start=10&amp;fetchSize=1000&amp;sortBy=productId&amp;sortOrder=DESC" };
		String[] conId14 = { "q=productId.lt:317039&amp;styleType.eq:D&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=DESC" };
		String[] conId15 = { "q=productId.lt:315201&amp;isCustomizable.eq:true&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC" };
		String[] conId16 = { "q=productId.in:315673,316043,316697,315674,316045,316048,314988,317457,255872,255803,316943,255191,315462,255239,315323,255209,315324,315325,255249,255210,255241&amp;styleType.eq:P&amp;f=styleType&amp;start=0&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=DESC" };
		String[] conId17 = { "q=productId.lt:255242&amp;codEnabled.eq:N&amp;f=styleType&amp;start=10&amp;fetchSize=1000&amp;sortBy=styleType&amp;sortOrder=ASC"};
		String[] conId18 = { "q=productId.in:255215,255255,255216,255257,255258,255259,255561,255643,315628,255345,317045,255354,317048,315632,315633,255174,255176,255185,315221,315565,315600&amp;styleType.eq:D&amp;f=productId&amp;start=0&amp;fetchSize=1000&amp;sortBy=productId&amp;sortOrder=DESC"};
		String[] conId19 = { "q=productId.in:315567,315447,254734,255218,315572,255219,254739,254744,317040,315621,315592,315597,254740,255338,254741,318102,255869,256492,255177,255898,315631&amp;styleType.eq:P&amp;f=styleType&amp;start=0&amp;fetchSize=100&amp;sortBy=styleType&amp;sortOrder=ASC"};
		
		Object[][] dataSet = new Object[][] { //conId1, conId2, conId3, conId4, conId5, conId6, conId7, conId8, conId9,
											  conId10, conId11, conId12, conId13, conId14, conId15, conId16, conId17, conId18, conId19 
											};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CatalogServiceDP_clearCacheForProduct_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ""+styleIdNike.get(getRandomNumber(styleIdNike.size())), "200" };
		String[] arr2 = new String[]{ ""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size())), "200" };
		String[] arr3 = new String[]{ ""+styleIdPuma.get(getRandomNumber(styleIdPuma.size())), "200" };
		String[] arr4 = new String[]{ ""+styleIdJackets.get(getRandomNumber(styleIdJackets.size())), "200" };
		String[] arr5 = new String[]{ ""+styleIdShoes.get(getRandomNumber(styleIdShoes.size())), "200" };
		String[] arr6 = new String[]{ ""+styleIdJeans.get(getRandomNumber(styleIdJeans.size())), "200" };
		String[] arr7 = new String[]{ ""+styleIdShirts.get(getRandomNumber(styleIdShirts.size())), "200" };
		String[] arr8 = new String[]{ ""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size())), "200" };
		String[] arr9 = new String[]{ ""+styleIdWallets.get(getRandomNumber(styleIdWallets.size())), "200" };
		String[] arr10 = new String[]{ ""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CatalogServiceDP_clearCacheForListOfProducts_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr2 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr3 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr4 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr5 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr6 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr7 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr8 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr9 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr10 = new String[]{ getRandomCommaSeperatedStyleIds() };	
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] CatalogServiceDP_getSizeChartOfProductById_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ ""+styleIdNike.get(getRandomNumber(styleIdNike.size())), "200" };
		String[] arr2 = new String[]{ ""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size())), "200" };
		String[] arr3 = new String[]{ ""+styleIdPuma.get(getRandomNumber(styleIdPuma.size())), "200" };
		String[] arr4 = new String[]{ ""+styleIdJackets.get(getRandomNumber(styleIdJackets.size())), "200" };
		String[] arr5 = new String[]{ ""+styleIdShoes.get(getRandomNumber(styleIdShoes.size())), "200" };
		String[] arr6 = new String[]{ ""+styleIdJeans.get(getRandomNumber(styleIdJeans.size())), "200" };
		String[] arr7 = new String[]{ ""+styleIdShirts.get(getRandomNumber(styleIdShirts.size())), "200" };
		String[] arr8 = new String[]{ ""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size())), "200" };
		String[] arr9 = new String[]{ ""+styleIdWallets.get(getRandomNumber(styleIdWallets.size())), "200" };
		String[] arr10 = new String[]{ ""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CatalogServiceDP_findByListOfProductIds_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr2 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr3 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr4 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr5 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr6 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr7 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr8 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr9 = new String[]{ getRandomCommaSeperatedStyleIds() };
		String[] arr10 = new String[]{ getRandomCommaSeperatedStyleIds() };	
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}
