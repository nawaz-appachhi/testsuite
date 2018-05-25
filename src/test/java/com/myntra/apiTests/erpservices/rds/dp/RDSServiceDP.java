package com.myntra.apiTests.erpservices.rds.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class RDSServiceDP {
	@DataProvider
	public static  Object[][] baseFormulaVerification(
			ITestContext testContext) {
		//String articleName, String brandName,String color,String gender,String gtin,String size,long piSkuId,double qty,long skuId,String vendorArticleName,String vendorArticleNo,String[] logparameterlist 
		String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] MumbaiAllocationZeroSplitInGurNBangwithMumInvTermHigh(
			ITestContext testContext) {
		String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] INvTermnegativeformumbai(
			ITestContext testContext) {
		String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] GurgaonAllocationZeroSplitInMumNBangwithGurInvTermHigh(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] BangloreAllocationZeroSplitInMumNGurwithBangInvTermHigh(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] sku_idSet0SearchWithBAGSCinfo(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] sku_idSet0andCSetOtherandSearchWithBAGinfo(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] sku_idSet0andBGCSetOtherandSearchWithAinfo(
			ITestContext testContext) {
		String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] DivideEquallyWhenAllsetToOthers(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] AllinventoryGettingAssignedToMumbai(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] AllinventoryGettingAssignedToGurgaon(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] AllinventoryGettingAssignedToBanglore(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SomeSkuWithBAGSCinfoAndOtherWithAllinfo(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitIn3warehouses(
			ITestContext testContext) {
				String[] rdsentries={"dresses,belle fille,pink,women,08906041771468,2883913,302,l,983980,bfdr-1012 l,bfdr-1012"};
		String[] loglevelentries={"3392077,1","3392078,2","3392079,3"};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitInTwoWarehouses(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitInTwoWarehouseWithOneInvTermHigh(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitInThreeWarehousesWithOneInvTermHigh(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitInThreeWarehousesWithTwoInvTermHigh(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitTwoWarehouseWithBAGSCinfo(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitTwoWarehouseWithBAGinfo(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitTwoWarehouseWithArticleinfo(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static  Object[][] SplitTwoWarehouseWithNoinfo(
			ITestContext testContext) {
				String[] rdsentries={"","","","","",""};
		String[] loglevelentries={"","","","","","",""};
		Object[] arr1 = {rdsentries,loglevelentries,"200",6525,"PI Sku updated successfully","SUCCESS"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
}