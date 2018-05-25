package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * @author shankara.c
 *
 */
public class CMSServiceDP extends CommonUtils
{
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	Commons commonUtil = new Commons();
	
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
	List<Integer> styleIdSportsShoes = styleHelper.performSeachServiceToGetStyleIds("SportsShoes", "15", "true", "false");
	
	String content1, content2, content3, content4 = null; 
	
	@DataProvider
	public Object[][] getContentByIdDataProvider(ITestContext testContext)
	{
		/*
		String[] conId1 = { "1" };
		String[] conId2 = { "100" };
		String[] conId3 = { "1000" };
		String[] conId4 = { "10000" };
		String[] conId5 = { "-1" };
		String[] conId6 = { "0" };
		String[] conId7 = { "A" };
		String[] conId8 = { "125000" };
		Object[][] dataSet = new Object[][] { conId1, conId2, conId3, conId4,
				conId5, conId6, conId7, conId8 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
		*/
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdNike.size()))},
				  //new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,
		testContext.getIncludedGroups(), 9, 9);
	}
	
	@DataProvider
	public Object[][] getContentByIdNagativeDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size())).toString().replaceAll("0", "00000000000").replaceAll("1", "111111111111111").replaceAll("2", "222222222222222").replaceAll("3", "333333333333333").replaceAll("4", "444444444444444").replaceAll("5", "555555555555555") },
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size())).toString().replaceAll("0", "00000000000").replaceAll("1", "1111111111").replaceAll("2", "2222222222").replaceAll("3", "333333333333333").replaceAll("4", "444444444444444").replaceAll("5", "555555555555555") }
		};
		return Toolbox.returnReducedDataSet(dataSet,
		testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] filteredContentSearchDataProvider(ITestContext testContext)
	{
		/*
		String[] conId1 = { "contentIds=1,2" };
		String[] conId2 = { "contentIds=1,2,10,17" };
		String[] conId3 = { "contentIds=1000,20000" };
		String[] conId4 = { "contentIds=-1,-3" };
		String[] conId5 = { "contentIds=1,A" };
		String[] conId6 = { "contentIds=1,14000" };
		String[] conId7 = { "contentIds=14299,2" };
		String[] tags1 = { "type=winter" };
		String[] tags2 = { "tags=summer" };
		String[] tags3 = { "tags=winter,home" };
		String[] tags4 = { "tags=test, style-savings, home" };
		String[] tags5 = { "tags=test,winter" };
		String[] tags6 = { "tags=formal, test" };
		Object[][] dataSet = new Object[][] { conId1, conId2, conId3, conId4,
				conId5, conId6, conId7, tags1, tags2, tags3, tags4, tags5,
				tags6 };
		*/
		Object[][] dataSet = new Object[][] {new String[]{"contentIds="+getRandomCommaSeperatedStyleIds(10)},
											 new String[]{"contentIds="+getRandomCommaSeperatedStyleIds(10)},
											 new String[]{"contentIds="+getRandomCommaSeperatedStyleIds(10)},
											 new String[]{"contentIds="+getRandomCommaSeperatedStyleIds(10)},
											 new String[]{"contentIds="+getRandomCommaSeperatedStyleIds(10)},
											 new String[]{"tags="+typeFilterCriteria(3)},
											 new String[]{"tags="+typeFilterCriteria(3)},
											 new String[]{"tags="+typeFilterCriteria(3)},
											 new String[]{"tags="+typeFilterCriteria(3)},
											 new String[]{"tags="+typeFilterCriteria(3)}
		};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public Object[][] relatedContentSearchDataProvider(ITestContext testContext)
	{
		String[] conId1 = { "look", "lookContainsProduct", "177803" };
		String[] conId2 = { "banner", "productClusterMember", "197264" };
		
		Object[][] dataSet = new Object[][] { conId1 , conId2};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] clearContentCacheDataProvider(ITestContext testContext)
	{
		/*
		String[] conId1 = { "1" };
		String[] conId2 = { "100" };
		String[] conId3 = { "1000" };
		String[] conId4 = { "10000" };
		String[] conId5 = { "-1" };
		String[] conId6 = { "0" };
		String[] conId7 = { "140000" };
		String[] conId8 = { "125000" };
		Object[][] dataSet = new Object[][] { conId1, conId2, conId3, conId4,
				conId5, conId6, conId7, conId8 };
		*/
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
				  //new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] clearRelatedContentCacheDataProvider(
			ITestContext testContext)
	{
		String[] conId1 = { "177803", "look", "lookContainsProduct " };
		String[] conId2 = { "197264", "banner", "productClusterMember " };
		Object[][] dataSet = new Object[][] { conId1 ,conId2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][] createContentUsingBodyDataProvider(ITestContext context){
		content1 = getRandomNumberUpto(100000);
		content2 = getRandomNumberUpto(200000);
		content3 = getRandomNumberUpto(300000);
		content4 = getRandomNumberUpto(900000);
		String[] conId1 = { content1, "CMSAutomation1", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:beinghuman:brand" };
		String[] conId2 = { content2, "CMSAutomation2", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Nike:brand" };
		String[] conId3 = { content3, "CMSAutomation3", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Puma:brand" };
		String[] conId4 = { content4, "CMSAutomation4", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Adidas:Gender" };
		Object[][] dataSet = new Object[][]{conId1, conId2, conId3, conId4};
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 4, 4);			
	}
	
	@DataProvider
	public Object[][] updateContentUsingBodyDataProvider(ITestContext context){
		Object[][] dataSet = null;
		if(content1==null && content2==null && content3==null && content4==null){
			String[] conId1 = { "3", "CMSAutomation", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:beinghuman" };
			dataSet = new Object[][]{ conId1 };
		}else{
			String[] conId1 = { content1, "CMSAutomation"+content1, "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Nike" };
			String[] conId2 = { content2, "CMSAutomation"+content2, "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Puma" };
			String[] conId3 = { content3, "CMSAutomation"+content3, "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Adidas" };
			String[] conId4 = { content4, "CMSAutomation"+content4, "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:HRX" };
			dataSet = new Object[][]{ conId1, conId2, conId3, conId4 };
		}
		
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] getProductDetailsFromCmsDataProvider(ITestContext context){
		/*
		String[] conId1 = { "3" };
		Object[][] dataSet = new Object[][]{conId1};
		*/
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
				  //new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] getLookBookByIdCmsDataProvider(ITestContext context){
		/*
		String[] conId1 = { "3" };
		String[] conId2 = { "15529" };
		Object[][] dataSet = new Object[][]{conId1,conId2};
		*/
		
		Object[][] dataSet = new Object[][] { new String[]{""+styleIdNike.get(getRandomNumber(styleIdNike.size()))},
				  new String[]{""+styleIdAdidas.get(getRandomNumber(styleIdAdidas.size()))},
				  new String[]{""+styleIdPuma.get(getRandomNumber(styleIdPuma.size()))},
				  new String[]{""+styleIdJackets.get(getRandomNumber(styleIdJackets.size()))},
				  new String[]{""+styleIdShoes.get(getRandomNumber(styleIdShoes.size()))},
				  new String[]{""+styleIdJeans.get(getRandomNumber(styleIdJeans.size()))},
				  new String[]{""+styleIdShirts.get(getRandomNumber(styleIdShirts.size()))},
				  new String[]{""+styleIdTshirts.get(getRandomNumber(styleIdTshirts.size()))},
				  new String[]{""+styleIdWallets.get(getRandomNumber(styleIdWallets.size()))},
				  //new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size()))}
		};
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 11, 2);		
	}
	
	@DataProvider
	public Object[][] getImage360ProductIDDataProvider()
	{
		return new Object[][]{{"111794",200,1},{"abc",404,0},{"#$%",405,0},{"OR 1=1",404,0},{"111793$#%^",404,0},{"abc,bca",404,0}};
	}
	
	private int getRandomNumber(int size){
		Random ran = new Random();
		if(size==0)
			return 0;
		else
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
	
	private String getRandomCommaSeperatedStyleIds(int size){
		String styleIds = null;
		try {
			styleIds = commonUtil.getCommaSeperatedValuesFromArray(sumOfAllStyleIds(), size, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return styleIds;
	}
	
	private String typeFilterCriteria(int size){
		Object[] tags = { "winter", "summer", "home","test", "style-savings", "home", "formal" };
		String toReturn = "";
		try {
			toReturn = commonUtil.getCommaSeperatedValuesFromArray(tags, size, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}
	
	private String getRandomNumberUpto(int max){
		return new Random().nextInt(max)+"";
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_getContentByStyleId_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
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
		//String[] arr10 = new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_filteredContentSearch_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		
		String[] arr1 = new String[]{ "contentIds="+getRandomCommaSeperatedStyleIds(10), "200" };
		String[] arr2 = new String[]{ "contentIds="+getRandomCommaSeperatedStyleIds(10), "200" };
		String[] arr3 = new String[]{ "contentIds="+getRandomCommaSeperatedStyleIds(10), "200" };
		String[] arr4 = new String[]{ "contentIds="+getRandomCommaSeperatedStyleIds(10), "200" };
		String[] arr5 = new String[]{ "contentIds="+getRandomCommaSeperatedStyleIds(10), "200" };
		String[] arr6 = new String[]{ "tags="+typeFilterCriteria(3), "200" };
		String[] arr7 = new String[]{ "tags="+typeFilterCriteria(3), "200" };
		String[] arr8 = new String[]{ "tags="+typeFilterCriteria(3), "200" };
		String[] arr9 = new String[]{ "tags="+typeFilterCriteria(3), "200" };
		String[] arr10 = new String[]{ "tags="+typeFilterCriteria(3), "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_relatedContentSearch_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "look", "lookContainsProduct", "177803", "200" };
		String[] arr2 = { "banner", "productClusterMember", "197264", "200" };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_clearContentSearchCache_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
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
		//String[] arr10 = new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_clearRelatedContentSearchCache_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext)
	{
		String[] arr1 = { "177803", "look", "lookContainsProduct " };
		String[] arr2 = { "197264", "banner", "productClusterMember " };
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public Object[][] CMSServiceDP_getProductDetailsFromCms_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
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
		//String[] arr10 = new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_getLookBookByIdCms_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
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
		//String[] arr10 = new String[]{""+styleIdSportsShoes.get(getRandomNumber(styleIdSportsShoes.size())), "200" };		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9 };
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);		
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_createContentUsingBody_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		content1 = getRandomNumberUpto(100000);
		content2 = getRandomNumberUpto(200000);
		content3 = getRandomNumberUpto(300000);
		content4 = getRandomNumberUpto(900000);
		
		String[] arr1 = { content1, "CMSAutomation1", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:beinghuman:brand" };
		String[] arr2 = { content2, "CMSAutomation2", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Nike:brand" };
		String[] arr3 = { content3, "CMSAutomation3", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Puma:brand" };
		String[] arr4 = { content4, "CMSAutomation4", "productAlbum",getRandomNumberUpto(5),"Accessories:Arrow:Adidas:Gender" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);			
	}
	
	@DataProvider
	public Object[][] singlestyle() {
		return new Object[][] { new Object[] { "351285" },
				new Object[] { "310704" }, new Object[] { "373908" },
				new Object[] { "322478" }, new Object[] { "325962" },
				new Object[] { "373900" }, new Object[] { "267511" } };
	}
	
	@DataProvider
	public Object[][] CMSServiceDP_updateContentUsingBody_verifyResponseDataNodesUsingSchemaValidations(ITestContext context)
	{
		Object[][] dataSet = null;
		if(content1==null && content2==null && content3==null && content4==null){
			String[] arr1 = { "3", "CMSAutomation", "productAlbum", getRandomNumberUpto(5),"Accessories:Arrow:beinghuman" };
			dataSet = new Object[][]{ arr1 };
		}else{
			String[] arr1 = { content1, "CMSAutomation"+content1, "productAlbum", getRandomNumberUpto(5),"Accessories:Arrow:Nike" };
			String[] arr2 = { content2, "CMSAutomation"+content2, "productAlbum", getRandomNumberUpto(5),"Accessories:Arrow:Puma" };
			String[] arr3 = { content3, "CMSAutomation"+content3, "productAlbum", getRandomNumberUpto(5),"Accessories:Arrow:Adidas" };
			String[] arr4 = { content4, "CMSAutomation"+content4, "productAlbum", getRandomNumberUpto(5),"Accessories:Arrow:HRX" };
			dataSet = new Object[][]{ arr1, arr2, arr3, arr4 };
		}
		
		return Toolbox.returnReducedDataSet(dataSet,context.getIncludedGroups(), 1, 2);		
	}
}
