package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.myntra.apiTests.portalservices.dealservice.DOTDHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

public class DOTDDP extends DOTDHelper {
	//public long startTime1=System.currentTimeMillis();
	Random random = new Random();
	public long currentTime = System.currentTimeMillis();
	String startTime = "" , endTime = "" , soldOutTime="";
	String styleId1 = "", styleId2 = "", styleId3 = "";
	
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
	

	@DataProvider
	public Object[][] createNewDealDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+20000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[0].toString(), "20", "20","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] createNewDealCDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime);
	endTime = String.valueOf(currentTime+1000000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[30].toString(), "20", "20","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	

	@DataProvider
	public Object[][] createNewDealShowFalseDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+20000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[1].toString(), "20", "20","0"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] createNewDealvfDiscisZeroDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+20000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[2].toString(), "0", "20","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] createNewDealcfDiscisZeroDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+25000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[3].toString(), "20", "0","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] createNewDealfloatDiscDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+25000);
	String[] arr1 = { "DOTD", "60% Off", startTime, endTime, styles[4].toString(), "20", "10.5","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	@DataProvider
	public Object[][] createNewDealSoldOutDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+25000);
	soldOutTime= String.valueOf(currentTime+20000);
	String[] arr1 = { "DOTD", "SoldOut", startTime, endTime, styles[5].toString(), "20", "10","1",startTime, soldOutTime};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	@DataProvider
	public Object[][] createNewDealMultipleDP(ITestContext testContext)
	{
	startTime = String.valueOf(currentTime+5000);
	endTime = String.valueOf(currentTime+25000);
	soldOutTime= String.valueOf(currentTime+20000);
	String[] arr1 = { "DOTD", "Max.Sale", startTime, endTime, styles[6].toString(), "20", "10","1",styles[7].toString(),"30", "10","1",styles[8].toString(), "20", "30","1",styles[9].toString(), "25", "10","1",styles[10].toString(), "20", "25","1",styles[11].toString(), "27", "30","1",styles[12].toString(), "20", "10","1"};
	
	Object[][] dataSet = new Object[][] {arr1};
	
	return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 1,1);
	}
	
	
	
	@DataProvider
	public Object[][] userSubscribe(ITestContext testContext)
	{
		String[] arr1 = {"1f477023.7995.4957.9357.b866100c57d9bW6vsJenKP"};
		
		Object[][] dataSet = new Object[][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,1);
	}
	@DataProvider
	public Object[][] userSubscribeFalse(ITestContext testContext)
	{
		String[] arr1 = {"1f477023.7995.4957.9357.b866100c57d9bW6vsJenKP"};
		
		Object[][] dataSet = new Object[][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,1);
	}
	
	
	@DataProvider
	public Object[][] isUserSubscribe(ITestContext testContext)
	{
		String[] arr1 = {"733f17d0.8b75.4e0f.8fe9.43c198afc416DXSk5OcTxp"};
		
		Object[][] dataSet = new Object[][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1,1);
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
}
