package com.myntra.apiTests.inbound.dp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.myntra.apiTests.inbound.helper.ESConstants;
import com.myntra.apiTests.inbound.helper.SpotConstants;
import com.myntra.apiTests.inbound.helper.SpotServiceHelper;
import com.myntra.apiTests.inbound.test.InBoundTests;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class InboundServiceDP {
	@DataProvider(name = "sourcepathsDP")
	public static Object[][] sourcepathsDP(ITestContext testContext) throws Exception {
			String[] arr1 = { ESConstants.INDEX.CMS };
			String[] arr2 = { ESConstants.INDEX.AMAZON };
			String[] arr3 = { ESConstants.INDEX.JABONG };
			Object[][] dataSet = new Object[][] { arr1, arr2, arr3, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);

	}

	@DataProvider(name = "myntrasourcepathsDP")
	public static Object[][] myntrasourcepathsDP(ITestContext testContext) throws Exception {
			String[] arr1 = { ESConstants.INDEX.CMS };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider(name = "jabaongsourcepathsDP")
	public static Object[][] jabongsourcepathsDP(ITestContext testContext) throws Exception {
			String[] arr1 = { ESConstants.INDEX.JABONG };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider(name = "amazonsourcepathsDP")
	public static Object[][] amazonsourcepathsDP(ITestContext testContext) throws Exception {
			String[] arr1 = { ESConstants.INDEX.AMAZON };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider(name = "externalsourcepathsDP")
	public static Object[][] externalsourcepathsDP(ITestContext testContext) throws Exception {
			String[] arr1 = { ESConstants.INDEX.AMAZON };
			String[] arr2 = { ESConstants.INDEX.JABONG };
			Object[][] dataSet = new Object[][] { arr1, arr2 };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider(name = "spotnodefilterDP")
	public static Object[][] spotnodefilterDP(ITestContext testContext) throws Exception {
			String collectionName = InBoundTests.spotNodeCollectionName;
			String val = "{\"filters\":[{\"asp\":{\"greater_than_equal_to\":890,\"less_than_equal_to\":890}}],\"coll_id\":"
					+ "\"" + collectionName + "\"" + ",\"sort_by\":\"add_to_cart_count\",\"source\":\"myntra\"}";
			String[] arr1 = { val };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider(name = "spotsearchdataDP")
	public static Object[][] spotsearchdataDP(ITestContext testContext) throws Exception {
			String collectionName = InBoundTests.spotNodeCollectionName;
			String val = "{\"filters\":[{\"asp\":{\"greater_than_equal_to\":890,\"less_than_equal_to\":890}}],\"coll_id\":"
					+ "\"" + collectionName + "\"" + ",\"sort_by\":\"add_to_cart_count\",\"source\":\"myntra\"}";
			String[] arr1 = { val };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	@DataProvider(name = "spotrefinesearchdataDP")
	public static Object[][] spotrefinesearchdataDP(ITestContext testContext) throws Exception {
		try {
			// String collectionName=InBoundTests.spotNodeCollectionName;
			String val = "{\"filters\":[{\"asp\":\"890\"}]}";
			String[] arr1 = { val };
			Object[][] dataSet = new Object[][] { arr1, };
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@DataProvider(name = "spotCalenderData")
	public static Object[][] getSpotCalenderData() throws Exception {
		SpotServiceHelper spot = new SpotServiceHelper();
		// Rows - Number of times your test has to be repeated.
		// Columns - Number of parameters in test data.
		Object[][] data = new Object[12][4];

		// 1st row
		data[0][0] = spot.last15DaysCalender();
		data[0][1] = spot.getCurrentTimeStamp();
		data[0][2] = spot.last15DaysCalender();
		data[0][3] = spot.getCurrentTimeStamp();

		// 2nd row
		data[1][0] = spot.last15DaysCalender();
		data[1][1] = spot.getCurrentTimeStamp();
		data[1][2] = spot.last30DaysCalender();
		data[1][3] = spot.getCurrentTimeStamp();

		// 3rd row
		data[2][0] = spot.last15DaysCalender();
		data[2][1] = spot.getCurrentTimeStamp();
		data[2][2] = spot.last45DaysCalender();
		data[2][3] = spot.getCurrentTimeStamp();

		// 4rth row
		data[3][0] = spot.last15DaysCalender();
		data[3][1] = spot.getCurrentTimeStamp();
		data[3][2] = spot.lastMonthStartDate();
		data[3][3] = spot.lastMonthLastDate();

		// 5th row
		data[4][0] = spot.last30DaysCalender();
		data[4][1] = spot.getCurrentTimeStamp();
		data[4][2] = spot.last15DaysCalender();
		data[4][3] = spot.getCurrentTimeStamp();

		// 6th row
		data[5][0] = spot.last30DaysCalender();
		data[5][1] = spot.getCurrentTimeStamp();
		data[5][2] = spot.last30DaysCalender();
		data[5][3] = spot.getCurrentTimeStamp();

		// 7th row
		data[6][0] = spot.last30DaysCalender();
		data[6][1] = spot.getCurrentTimeStamp();
		data[6][2] = spot.last45DaysCalender();
		data[6][3] = spot.getCurrentTimeStamp();

		// 8th row
		data[7][0] = spot.last30DaysCalender();
		data[7][1] = spot.getCurrentTimeStamp();
		data[7][2] = spot.lastMonthStartDate();
		data[7][3] = spot.lastMonthLastDate();

		// 9th row
		data[8][0] = spot.last45DaysCalender();
		data[8][1] = spot.getCurrentTimeStamp();
		data[8][2] = spot.last15DaysCalender();
		data[8][3] = spot.getCurrentTimeStamp();

		// 10th row
		data[9][0] = spot.last45DaysCalender();
		data[9][1] = spot.getCurrentTimeStamp();
		data[9][2] = spot.last30DaysCalender();
		data[9][3] = spot.getCurrentTimeStamp();

		// 11th row
		data[10][0] = spot.last45DaysCalender();
		data[10][1] = spot.getCurrentTimeStamp();
		data[10][2] = spot.last45DaysCalender();
		data[10][3] = spot.getCurrentTimeStamp();

		// 12th row
		data[11][0] = spot.last45DaysCalender();
		data[11][1] = spot.getCurrentTimeStamp();
		data[11][2] = spot.lastMonthStartDate();
		data[11][3] = spot.lastMonthLastDate();

		return data;
	}

	// , new Object[]{Source.AMAZON}, new Object[]{Source.JABONG}
	@DataProvider(name = "spotGenderData")
	public static Object[][] getspotGenderData() throws Exception {
		Object[][] data = new Object[][] { new Object[] { SpotConstants.Gender.MEN }, new Object[] { SpotConstants.Gender.WOMEN },
				new Object[] { SpotConstants.Gender.BOYS }, new Object[] { SpotConstants.Gender.GIRLS } };
		return data;
	}

	@DataProvider(name = "sources")
	public static Object[][] getspotSourcesData() throws Exception {
		Object[][] data = new Object[][] { new Object[] { SpotConstants.Source.MYNTRA }};
		return data;
	}

	@DataProvider(name = "spotSourcesAndCalenderDP") 
	public static Object[][] getObjectCodes() throws Exception {
		return combine(getspotSourcesData(), getSpotCalenderData());
	}
	
	@DataProvider(name = "validate_rapid_api_for_myntra") /////////////*******
	public static Object[][] validate_rapid_api_for_myntra(ITestContext testContext) throws Exception {
		SpotServiceHelper spothelper=new SpotServiceHelper();
		
		Object[] arr1={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.MEN, SpotConstants.Source.MYNTRA};
		Object[] arr2={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.WOMEN, SpotConstants.Source.MYNTRA};
		Object[] arr3={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.UNISEX, SpotConstants.Source.MYNTRA};
		Object[] arr4={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.BOYS, SpotConstants.Source.MYNTRA};
		Object[] arr5={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.GIRLS, SpotConstants.Source.MYNTRA};
		
		//Last 30 days
		Object[] arr6={spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.KURTAS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.MEN, SpotConstants.Source.MYNTRA};
		
		Object[] arr8={spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.JEANS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.WOMEN, SpotConstants.Source.MYNTRA};
		
		//Last 45 days
		Object[] arr9={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.GIRLS, SpotConstants.Source.MYNTRA};
		
		Object[] arr11={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.KURTAS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.BOYS, SpotConstants.Source.MYNTRA};
				
		//Last Month
		
		Object[] arr12={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.MEN, SpotConstants.Source.MYNTRA};
		Object[] arr13={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL, SpotConstants.SeasonCode.SEASONCODE, SpotConstants.Gender.WOMEN, SpotConstants.Source.MYNTRA};
		
		
		Object[][] dataSet = new Object[][] {arr1,arr2,arr3,arr4,arr5,arr6,arr8,arr9,arr11,arr12,arr13};
		//Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 13, 13);
	}
	
	@DataProvider(name = "validate_rapid_api_for_jabong") /////////////*******
	public static Object[][] validate_rapid_api_for_jabong(ITestContext testContext) throws Exception {
		
		SpotServiceHelper spothelper=new SpotServiceHelper();
		
		Object[] arr1={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.MEN, SpotConstants.Source.JABONG};
		
		Object[] arr2={spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.WOMEN, SpotConstants.Source.JABONG};
		Object[] arr3={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.BOYS, SpotConstants.Source.JABONG};
		Object[] arr4={spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.GIRLS, SpotConstants.Source.JABONG};
		Object[] arr6={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.UNISEX, SpotConstants.Source.JABONG};
		Object[] arr7={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.JEANS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.MEN, SpotConstants.Source.JABONG};
		Object[] arr8={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.KURTAS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.WOMEN, SpotConstants.Source.JABONG};
				
		
		Object[][] dataSet = new Object[][] {arr1,arr2,arr3,arr4,arr6,arr7,arr8};
//		Object[][] dataSet = new Object[][] {arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
	}
	
	@DataProvider(name = "validate_rapid_api_for_amazon") /////////////*******
	public static Object[][] validate_rapid_api_for_amazon(ITestContext testContext) throws Exception {
		
		SpotServiceHelper spothelper=new SpotServiceHelper();
		
		Object[] arr1={spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last15DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.MEN, SpotConstants.Source.AMAZON};
		
		Object[] arr2={spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last30DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.WOMEN, SpotConstants.Source.AMAZON};
		Object[] arr3={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.BOYS, SpotConstants.Source.AMAZON};
		Object[] arr4={spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.GIRLS, SpotConstants.Source.AMAZON};
		Object[] arr5={spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last90DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.MEN, SpotConstants.Source.AMAZON};
		Object[] arr6={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.TSHIRTS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.UNISEX, SpotConstants.Source.AMAZON};
		Object[] arr7={spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				spothelper.lastMonthStartDate(),spothelper.lastMonthLastDate(),
				SpotConstants.ArticleType.JEANS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.MEN, SpotConstants.Source.AMAZON};
		Object[] arr8={spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				spothelper.last45DaysCalender(),spothelper.getCurrentTimeStamp(),
				SpotConstants.ArticleType.KURTAS, SpotConstants.Brands.ALL,"", SpotConstants.Jabong_Gender.WOMEN, SpotConstants.Source.AMAZON};
				
		
		Object[][] dataSet = new Object[][] {arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8};
//		Object[][] dataSet = new Object[][] {arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider(name = "spotMyntraFilterTypes") /////////////*******
	public static Object[][] spotMyntraFilterTypes(ITestContext testContext) throws Exception {
		
//		Object[] arr1={FILTER_TYPE.TOP_SELLER};
//		Object[] arr2={FILTER_TYPE.TOP_SELLER_ROUND};
		
				
		
		Object[][] dataSet = new Object[][] { };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	public static Object[][] combine(Object[][] a1, Object[][] a2) throws Exception {
		List<Object[]> objectCodesList = new LinkedList<Object[]>();
		for (Object[] o : a1) {
			for (Object[] o2 : a2) {
				objectCodesList.add(concatAll(o, o2));
			}
		}
		return objectCodesList.toArray(new Object[0][0]);
	}

	public static <T> T[] concatAll(T[] first, T[]... rest) throws Exception {
		// calculate the total length of the final object array after the concat
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		// copy the first array to result array and then copy each array
		// completely to result
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		return result;
	}
	
	@DataProvider(name = "getClusterData") /////////////*******
	public static Object[][] getClusterData(ITestContext testContext) throws Exception {
		
		String valid_payload = "{\"style_id\":\"687828\"}";
		
		Object[] arr1={valid_payload};
		
				
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider(name = "getClusterDataForInvalidStyleId") /////////////*******
	public static Object[][] getClusterDataForInvalidStyleId(ITestContext testContext) throws Exception {
		
		String invalid_payload = "{\"style_id\":\"000000\"}";
		Object[] arr2={invalid_payload};
		
				
		
		Object[][] dataSet = new Object[][] { arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	//
	@DataProvider(name = "getProductDatafromSpotAPI") /////////////*******
	public static Object[][] getProductDatafromSpotAPI(ITestContext testContext) throws Exception {
		
		String payload1 = "{\"source\":\"cms\",\"source_id\":[\"1769389\"]}";
		String payload2 = "{\"source\":\"cms\",\"source_id\":[\"687828\"]}";
		
		Object[] arr1={payload1,"1111250"};
		Object[] arr2={payload2,"687828"};
		
				
		
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider(name = "fetch_Attributes_Values") /////////////*******
	public static Object[][] fetch_Attributes_Values(ITestContext testContext) throws Exception {
		
		String payload1 = "{\"source\":\"cms\",\"attributes\":[\"article_type\",\"brand\",\"gender\",\"season_code\"]}";
		String payload2 = "{\"source\":\"Amazon\",\"attributes\":[\"article_type\",\"brand\",\"gender\"]}";
		String payload3 = "{\"source\":\"Jabong\",\"attributes\":[\"article_type\",\"brand\",\"gender\"]}";
		
		
		Object[] arr1={payload1};
		Object[] arr2={payload2};
		Object[] arr3={payload3};
		
				
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider(name = "filter_Styles") /////////////*******
	public static Object[][] filter_Styles(ITestContext testContext) throws Exception {
		
		String payload1 = "{\"source\":\"cms\","
				+"\"style_ids\": [\"104896\", \"104570\",\"108096\",\"1080308\",\"108199\"],"
				+"\"filters\" : {\"base_colour\" : \"black,White\",\"Pattern\": \"Solid\" }}";

	Object[] arr1 = { payload1 };
//	Object[] arr2 = { payload2 };
//	Object[] arr3 = { payload3 };

	Object[][] dataSet = new Object[][] { arr1};
	return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),3,3);
}

}
