package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class BannerServiceDP extends CommonUtils
{
	@DataProvider
	public Object[][] getPersonalizedBannerDataProvider(ITestContext testContext) {
		String[] arr1 = { "topnav-images", "static", "women" };
		String[] arr2 = { "fashion-story", "static",
				"women-sandals-1-peep-show-fs" };
		String[] arr3 = { "fashion-trends", "static",
				"women-sandals-2-braided-brights-fs" };
		String[] arr4 = { "home", "slideshow", "women-sari" };
		String[] arr5 = { "landing", "slideshow", "women-seashore" };
		String[] arr6 = { "login", "static", "women-shirts-tops" };
		String[] arr7 = { "mobilehome", "static", "women-shirts-tops-tees" };
		String[] arr8 = { "new-home-features", "static", "women-shrugs-jackets" };
		String[] arr9 = { "new-home-subbanners", "static", "women" };
		String[] arr10 = { "new-login", "static", "women" };
		String[] arr11 = { "new-login-big", "static", "women" };
		String[] arr12 = { "search", "static", "women" };
		String[] arr13 = { "storyboard", "static", "women" };
		String[] arr14 = { "topnav-images", "static", "women" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 4);

	}

	@DataProvider
	public Object[][] getPersonalizedBannerNodesDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "topnav-images", "static", "women", "101",
				"Personalized banner retrival successful", "SUCCESS", "0" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// check
	@DataProvider
	public Object[][] getPersonalizedBannerNodesNegetiveValuesDataProvider(
			ITestContext testContext) {

		String[] arr1 = { "", "", "", "101",
				"Personalized banner retrival successful", "SUCCESS", "0" };
		String[] arr2 = { "topnav-images", "", "", "101",
				"Personalized banner retrival successful", "SUCCESS", "0" };
		String[] arr3 = { "", "static", "", "101",
				"Personalized banner retrival successful", "SUCCESS", "0" };
		String[] arr4 = { "", "", "women", "101",
				"Personalized banner retrival successful", "SUCCESS", "0" };
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}
	
	@DataProvider
	public Object[][] BannerServiceDP_getPersonalizedBanner_verifyResponseDataNodesUsingSchemaValidations(ITestContext testContext) 
	{
		String[] arr1 = { "topnav-images", "static", "women" };
		String[] arr2 = { "fashion-story", "static", "women-sandals-1-peep-show-fs" };
		String[] arr3 = { "fashion-trends", "static", "women-sandals-2-braided-brights-fs" };
		String[] arr4 = { "home", "slideshow", "women-sari" };
		String[] arr5 = { "landing", "slideshow", "women-seashore" };
		String[] arr6 = { "login", "static", "women-shirts-tops" };
		String[] arr7 = { "mobilehome", "static", "women-shirts-tops-tees" };
		String[] arr8 = { "new-home-features", "static", "women-shrugs-jackets" };
		String[] arr9 = { "new-home-subbanners", "static", "women" };
		String[] arr10 = { "new-login", "static", "women" };
		String[] arr11 = { "new-login-big", "static", "women" };
		String[] arr12 = { "search", "static", "women" };
		String[] arr13 = { "storyboard", "static", "women" };
		String[] arr14 = { "topnav-images", "static", "women" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}

	
}
