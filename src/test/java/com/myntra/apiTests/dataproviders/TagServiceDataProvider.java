package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.tagService.tagServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class TagServiceDataProvider extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");
	com.myntra.apiTests.portalservices.tagService.tagServiceHelper tagServiceHelper = new tagServiceHelper();

	public TagServiceDataProvider() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");

	}

	@DataProvider
	public Object[][] publishTagAddDataProvider(ITestContext testContext) {

		String[] arr1 = { tagServiceHelper.getOneStyleID(), "ashuTag1",
				"ashuTag2", "ashuNamespace" };

		String[] arr2 = { tagServiceHelper.getOneStyleID(), "ashuTag3",
				"ashuTag4", "ashuNamespace" };

		String[] arr3 = { tagServiceHelper.getOneStyleID(), "ashuTag5",
				"ashuTag6", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(), "ashuTag7",
				"ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] publishTagAddBulk(ITestContext testContext) {
		String[] arr1 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag1" };
		String[] arr2 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag2" };
		String[] arr3 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag3" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag4" };
		String[] arr5 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag5" };
		String[] arr6 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag6" };
		String[] arr7 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag7" };
		String[] arr8 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag8" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] publishTagRemove(ITestContext testContext) {

		String[] arr1 = { tagServiceHelper.getOneStyleID(), "ashuTag1",
				"ashuTag2", "ashuNamespace" };

		String[] arr2 = { tagServiceHelper.getOneStyleID(), "ashuTag3",
				"ashuTag4", "ashuNamespace" };

		String[] arr3 = { tagServiceHelper.getOneStyleID(), "ashuTag5",
				"ashuTag6", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(), "ashuTag7",
				"ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] publishTagRemoveBulk(ITestContext testContext) {
		String[] arr1 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag1", "ashuNamespace" };
		String[] arr2 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag2", "ashuNamespace" };
		String[] arr3 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag3", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag4", "ashuNamespace" };
		String[] arr5 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag5", "ashuNamespace" };
		String[] arr6 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag6", "ashuNamespace" };
		String[] arr7 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag7", "ashuNamespace" };
		String[] arr8 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] publishTagRemoveBulkTag(ITestContext testContext) {
		String[] arr1 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag1", "ashuNamespace" };
		String[] arr2 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag2", "ashuNamespace" };
		String[] arr3 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag3", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag4", "ashuNamespace" };
		String[] arr5 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag5", "ashuNamespace" };
		String[] arr6 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag6", "ashuNamespace" };
		String[] arr7 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag7", "ashuNamespace" };
		String[] arr8 = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,
				arr6, arr7, arr8 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] publishTagRemoveAll(ITestContext iTestContext) {
		String[] arr = { tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(),
				tagServiceHelper.getOneStyleID(), "ashuNamespace", "ashuTag" };

		Object[][] dataSet = new Object[][] { arr };

		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] publishTagFullRefresh(ITestContext iTestContext) {
		String[] arr = { tagServiceHelper.getOneStyleID(), "ashuTag1",
				"ashuTag2", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr };

		return Toolbox.returnReducedDataSet(dataSet,
				iTestContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider
	public Object[][] fetchTag(ITestContext testContext) {

		String[] arr1 = { tagServiceHelper.getOneStyleID(), "ashuTag1",
				"ashuTag2", "ashuNamespace" };

		String[] arr2 = { tagServiceHelper.getOneStyleID(), "ashuTag3",
				"ashuTag4", "ashuNamespace" };

		String[] arr3 = { tagServiceHelper.getOneStyleID(), "ashuTag5",
				"ashuTag6", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(), "ashuTag7",
				"ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}

	@DataProvider
	public Object[][] fetchStyle(ITestContext testContext) {

		String[] arr1 = { tagServiceHelper.getOneStyleID(), "ashuTag1",
				"ashuTag2", "ashuNamespace" };

		String[] arr2 = { tagServiceHelper.getOneStyleID(), "ashuTag3",
				"ashuTag4", "ashuNamespace" };

		String[] arr3 = { tagServiceHelper.getOneStyleID(), "ashuTag5",
				"ashuTag6", "ashuNamespace" };
		String[] arr4 = { tagServiceHelper.getOneStyleID(), "ashuTag7",
				"ashuTag8", "ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] publishTagAddBulkCustomPayload(ITestContext testContext) {
		String[] arr1 = {
				tagServiceHelper.getAllStyleIDs("10000", new String[] { "nike",
						"adidas", "puma", "shoes", "tshirts", "reebok",
						"saree", "suit", "shirt", "casual shoes" }), "ashuTag",
				"ashuNamespace" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 1);
	}

}
