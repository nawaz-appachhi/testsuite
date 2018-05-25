package com.myntra.apiTests.dataproviders;

import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.mobileappservices.MobileStyleServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class MobileStyleServiceDP extends CommonUtils
{
	public static MobileStyleServiceHelper mobileStyleServiceHelper = new MobileStyleServiceHelper();
	
	String[] brands = { "Nike", "Adidas", "Jeans", "Tshirts", "Shoes", "Titan" };
	
	List<Integer>  styleIds1 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[0], "6", "true", "false");
	List<Integer>  styleIds2 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[1], "6", "true", "false");
	List<Integer>  styleIds3 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[2], "6", "true", "false");
	List<Integer>  styleIds4 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[3], "6", "true", "false");
	List<Integer>  styleIds5 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[4], "6", "true", "false");
	List<Integer>  styleIds6 = mobileStyleServiceHelper.performSeachServiceToGetStyleIds(brands[5], "6", "true", "false");
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifySuccessResponse(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyMetaTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyNotificationTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyDataTagNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyArticleAttributeDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyStyleImagesDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyMasterCatagoryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifySubCatagoryDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifySubArticleDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyProductDescriptorDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyStyleOptionsDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyDiscountDataNodes(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
	private int getRandomNumber(int maxLen){
		return new Random().nextInt(maxLen);
	}
	
	@DataProvider
	public Object[][] MobileStyleServiceDP_getStyleByIdDataProvider_verifyResponseDataNodesUsingSchemaValidations(ITestContext iTestContext)
	{
		String[] arr1 = { String.valueOf(styleIds1.get(getRandomNumber(styleIds1.size()))) };
		String[] arr2 = { String.valueOf(styleIds2.get(getRandomNumber(styleIds2.size()))) };
		String[] arr3 = { String.valueOf(styleIds3.get(getRandomNumber(styleIds3.size()))) };
		String[] arr4 = { String.valueOf(styleIds4.get(getRandomNumber(styleIds4.size()))) };
		String[] arr5 = { String.valueOf(styleIds5.get(getRandomNumber(styleIds5.size()))) };
		String[] arr6 = { String.valueOf(styleIds6.get(getRandomNumber(styleIds6.size()))) };
	
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet, iTestContext.getIncludedGroups(), 1, 2);
	}
	
}
