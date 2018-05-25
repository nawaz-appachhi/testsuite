package com.myntra.apiTests.dataproviders;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class SalesActivityDP {
	
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public SalesActivityDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	@DataProvider
	public Object[][] BeforeSales_updateMobileFeatureGateDP(
			ITestContext testContext) {
		String[] arr1 = { "wp","enableTapForOffer","false",Env };
		String[] arr2 = { "android","enableTapForOffer","false",Env};
		String[] arr3 = { "ios","enableTapForOffer","false",Env };

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
	
	@DataProvider
	public Object[][] AfterSales_updateMobileFeatureGateDP(
			ITestContext testContext) {
		String[] arr1 = { "wp","enableTapForOffer","true",Env };
		String[] arr2 = { "android","enableTapForOffer","true",Env };
		String[] arr3 = { "ios","enableTapForOffer","true",Env };

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
	
	@DataProvider
	public Object[][] BeforeSales_UpdateFG_FromCsv(
			ITestContext testContext) {
		
		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMapBeforeSales(pathOfFile);
		Set<Entry<String, String>> entrySet = keyvalepairmap.entrySet();
		int i = 0, j = 0;
		String[] arr = new String[entrySet.size()];
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			System.out.println("Key = " + key);
			String value = entry.getValue();
			System.out.println("value = " + value);
			arr[i] = entry.getKey() + "," + entry.getValue();
			++i;
		}

		Object[][] dataSet = new Object[arr.length][];
		for (String item : arr) {
			dataSet[j] = new Object[] { item.split(",")[0], item.split(",")[1] };
			++j;
		}
		/*if (keyFromJenkins != null && valueFromJenkins != null) {
			dataSet[j] = new Object[] { keyFromJenkins, valueFromJenkins };
		}*/

		return dataSet;
	}
	
	@DataProvider
	public Object[][] BeforeSales_WidgetKeyValueFromCSV(
			ITestContext testContext) {
		
		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair1.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key1.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMapBeforeSales(pathOfFile);
		Set<Entry<String, String>> entrySet = keyvalepairmap.entrySet();
		int i = 0, j = 0;
		String[] arr = new String[entrySet.size()];
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			System.out.println("Key = " + key);
			String value = entry.getValue();
			System.out.println("value = " + value);
			arr[i] = entry.getKey() + "," + entry.getValue();
			++i;
		}

		Object[][] dataSet = new Object[arr.length][];
		for (String item : arr) {
			dataSet[j] = new Object[] { item.split(",")[0], item.split(",")[1] };
			++j;
		}
		/*if (keyFromJenkins != null && valueFromJenkins != null) {
			dataSet[j] = new Object[] { keyFromJenkins, valueFromJenkins };
		}*/

		return dataSet;
	}
	
	/*@DataProvider
	public Object[][] BeforeSales_WidgetKeyValueFromJenkins(
			ITestContext testContext) {
		String keyFromJenkins = System.getProperty("keyfromui");
		if (null == keyFromJenkins) {
			keyFromJenkins = System.getenv("keyfromui");
		}
		if (null == keyFromJenkins) {
			keyFromJenkins = "default-key";
		}
		String valueFromJenkins = System.getProperty("valuefromui");
		if (null == valueFromJenkins) {
			valueFromJenkins = System.getenv("valuefromui");
		}
		if (null == valueFromJenkins) {
			valueFromJenkins = "default-value";
		}

		String[] arr1 = { keyFromJenkins, valueFromJenkins };
		
		Object[][] dataSet = new Object[][] { arr1 };

		return dataSet;
	}*/
	
	@DataProvider
	public Object[][] AfterSales_UpdateFGFromCSV(
			ITestContext testContext) {

		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMapAfterSales(pathOfFile);
		Set<Entry<String, String>> entrySet = keyvalepairmap.entrySet();
		int i = 0, j = 0;
		String[] arr = new String[entrySet.size()];
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			System.out.println("Key = " + key);
			String value = entry.getValue();
			System.out.println("value = " + value);
			arr[i] = entry.getKey() + "," + entry.getValue();
			++i;
		}

		Object[][] dataSet = new Object[arr.length][];
		for (String item : arr) {
			dataSet[j] = new Object[] { item.split(",")[0], item.split(",")[1] };
			++j;
		}
		/*if (keyFromJenkins != null && valueFromJenkins != null) {
			dataSet[j] = new Object[] { keyFromJenkins, valueFromJenkins };
		}*/

		return dataSet;
	}
	
	@DataProvider
	public Object[][] AfterSales_UpdateWidgetKeyFromCSV(
			ITestContext testContext) {

		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair1.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMapAfterSales(pathOfFile);
		Set<Entry<String, String>> entrySet = keyvalepairmap.entrySet();
		int i = 0, j = 0;
		String[] arr = new String[entrySet.size()];
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			System.out.println("Key = " + key);
			String value = entry.getValue();
			System.out.println("value = " + value);
			arr[i] = entry.getKey() + "," + entry.getValue();
			++i;
		}

		Object[][] dataSet = new Object[arr.length][];
		for (String item : arr) {
			dataSet[j] = new Object[] { item.split(",")[0], item.split(",")[1] };
			++j;
		}
		/*if (keyFromJenkins != null && valueFromJenkins != null) {
			dataSet[j] = new Object[] { keyFromJenkins, valueFromJenkins };
		}*/

		return dataSet;
	}
	
	
	
	/*@DataProvider
	public Object[][] AfterSales_WidgetKeyFromJenkins(
			ITestContext testContext) {
		String keyFromJenkins = System.getProperty("keyfromui");
		if (null == keyFromJenkins) {
			keyFromJenkins = System.getenv("keyfromui");
		}
		if (null == keyFromJenkins) {
			keyFromJenkins = "default-key";
		}
		String valueFromJenkins = System.getProperty("valuefromui");
		if (null == valueFromJenkins) {
			valueFromJenkins = System.getenv("valuefromui");
		}
		if (null == valueFromJenkins) {
			valueFromJenkins = "default-value";
		}

		String[] arr1 = { keyFromJenkins, valueFromJenkins };
	
		Object[][] dataSet = new Object[][] { arr1 };

		return dataSet;
	}*/



	
	public static LinkedHashMap<String, String> getKeyValueMapBeforeSales(String pathOfFile) {
		LinkedHashMap<String, String> keyValueMap = new LinkedHashMap<String, String>();
		// String pathOfFile =
		// "/Users/mohit.jain/Desktop/keyvaluepairlist/keyvaluepair.csv";
		// String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		BufferedReader fileReader = null;
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(pathOfFile));
		
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(",");
				keyValueMap.put(tokens[0], tokens[1]);
			}
			System.out.println("map= " + keyValueMap);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return keyValueMap;
	}
	
	
	
	public static LinkedHashMap<String, String> getKeyValueMapAfterSales(String pathOfFile) {
		LinkedHashMap<String, String> keyValueMap = new LinkedHashMap<String, String>();
		// String pathOfFile =
		// "/Users/mohit.jain/Desktop/keyvaluepairlist/keyvaluepair.csv";
		// String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		BufferedReader fileReader = null;
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(pathOfFile));

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(",");
				keyValueMap.put(tokens[0], tokens[2]);
			}
			System.out.println("map= " + keyValueMap);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return keyValueMap;
	}
	
}
