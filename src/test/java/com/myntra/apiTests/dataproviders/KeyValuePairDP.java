package com.myntra.apiTests.dataproviders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.myntra.apiTests.portalservices.configservice.kvpair.KeyValuePairServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 * 
 */
public class KeyValuePairDP extends KeyValuePairServiceHelper {
	/*
	 * @DataProvider public Object [][] getKeyValuePairDataProvider(ITestContext
	 * testContext) { // String [] arr1 = {"checkInStock"}; String [] arr1 =
	 * {"shipping.charges.amount"}; Object [][] dataSet = new Object [][]
	 * {arr1}; return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 1, 2); }
	 * 
	 * @DataProvider public Object[][]
	 * getKeyValuePairForGivenListDataProvider(ITestContext testContext) {
	 * String [] arr1 = {"\"checkInStock\"", "\"styleExclusion\""}; Object [][]
	 * dataSet = new Object [][] {arr1}; return
	 * Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,
	 * 2); }
	 * 
	 * @DataProvider public Object[][]
	 * createNewFeatureKeyValuePairDataProvider(ITestContext testContext) {
	 * String [] arr1 = {"testKey1","testValue1","Description1"}; String [] arr2
	 * = {"testKey2","testValue2","Description2"}; Object [][] dataSet = new
	 * Object [][] {arr1,arr2}; return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 1, 2); }
	 */
	/*
	 * @DataProvider public Object[][]
	 * deleteFeatureKeyValuePairDataProvider(ITestContext testContext) { String
	 * [] arr1 = {"testKey1"}; Object [][] dataSet = new Object [][] {arr1};
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 1, 2); }
	 * 
	 * @DataProvider public Object[][]
	 * updateFeatureKeyValuePairDataProvider(ITestContext testContext) { String
	 * [] arr1 = {"testKey1","Changing testkey1 value","updated value"}; Object
	 * [][] dataSet = new Object [][] {arr1}; return
	 * Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,
	 * 2); }
	 * 
	 * @DataProvider public Object[][]
	 * updateListOfFeatureKeyValuePairDataProvider(ITestContext testContext){
	 * String [] arr1 =
	 * {"testKey1","Changing testkey1 value","updated testkey1",
	 * "testKey2","Changing testkey2 value","updated testkey2"}; Object [][]
	 * dataSet = new Object [][] {arr1}; return
	 * Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1,
	 * 2); }
	 */
	// --------------------------------------Feature Gate
	// -------------------------------------------------------------------

	Random random = new Random();
	String key1 = getAllKeys().get(random.nextInt(getAllKeys().size()));
	String key2 = getAllKeys().get(random.nextInt(getAllKeys().size()));
	String key3 = getAllKeys().get(random.nextInt(getAllKeys().size()));
	String key4 = getAllKeys().get(random.nextInt(getAllKeys().size()));

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { key1 };
		String[] arr2 = { key2 };
		String[] arr3 = { key3 };
		String[] arr4 = { "" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairDP1(ITestContext testContext) {
		List<String> keys = new ArrayList<String>();
		List<String> vals = new ArrayList<String>();
		List<String> descriptions = new ArrayList<String>();
		RequestGenerator req = getRequest(APINAME.GETALLKEYVALUEPAIR);
		keys = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..key");
		vals = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..value");
		descriptions = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..description");
		String[] arr1 = { keys.get(0), vals.get(0), descriptions.get(0) };
		String[] arr2 = { keys.get(1), vals.get(1), descriptions.get(1) };
		String[] arr3 = { keys.get(2), vals.get(2), descriptions.get(2) };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairDP_negative(ITestContext testContext) {
	//	String[] arr1 = { "$%#$%$#%#$" };
		String[] arr2 = { "shipping.charges.amount" };
		String[] arr3 = { "codEnableAllZipCode123" };
		Object[][] dataSet = new Object[][] { arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairForGivenListDP(ITestContext testContext) {
		String[] arr1 = { key1 };
		String[] arr2 = { "\"" + key1 + "," + key2 + "\"" };
		String[] arr3 = { "\"" + key1 + "," + key2 + "," + key3 + "\"" };
		String[] arr4 = { "\"" + key1 + "," + key2 + "," + key3 + "," + key4
				+ "\"" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairForGivenListDP_negative(
			ITestContext testContext) {
	//	String[] arr1 = { "$%#$%$#%#$" };
		String[] arr2 = { "fsdafsdfsd" };
		String[] arr3 = { key1 + "3242," + key2 + "3242," + key3 + "3242" };
		String[] arr4 = { key1 + "3242, $%#$%$#%#$" + key3 + "3242" };
		Object[][] dataSet = new Object[][] {  arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a Feature Gate key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getKeyValuePairForGivenListDP_negative1(
			ITestContext testContext) {
		String[] arr1 = { ",," };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewFeatureKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { "Key1", "testValue1", "Description1", "15003",
				"\"KeyValuePair added successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "testKey2", "testValue2", "Description2", "15003",
				"\"KeyValuePair added successfully\"", "\"SUCCESS\"", "1" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewFeatureKeyValuePairDP_negative(
			ITestContext testContext) {
		String[] arr1 = { "Key1", "testValue1", "Description1", "54",
				"\"Duplicate entry 'Key1' for key 'key'\"", "\"ERROR\"", "0" };
		String[] arr2 = { "testKey2", "testValue2", "Description2", "54",
				"\"Duplicate entry 'testKey2' for key 'key'\"", "\"ERROR\"",
				"0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewFeatureKeyValuePairDP_negative1(
			ITestContext testContext) {
		String[] arr1 = { "", "testValue1", "Description1", "10001",
				"\"Empty key is not allowed\"", "\"ERROR\"", "0" };
		String[] arr2 = { "", "testValue2", "Description2", "10001",
				"\"Empty key is not allowed\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { "key for Automation", "key value Automation123",
				"Description123", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "key1 for Automation", "key1 value for Automation",
				"Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateKeyValuePairDP_negative(ITestContext testContext) {
		String[] arr1 = { "", "testValue1", "Description1", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		String[] arr2 = { "", "testValue2", "Description2", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update multiple key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateKeyValuePairForGivenListDP(ITestContext testContext) {
		String[] arr1 = { "key for Automation", "ValueAutomation123",
				"Description123", "key1 for Automation",
				"automation testValue234", "changed Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "2" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update multiple key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateKeyValuePairForGivenListDP_negative(
			ITestContext testContext) {
		String[] arr1 = { "", "testValue123", "Description123",
				"key1 for Automation", "changed testValue234",
				"changed Description234", "10008", "\"Key not found\"",
				"\"ERROR\"", "0" };
		String[] arr2 = { "key for Automation", "testValue123",
				"Description123", "", "changed testValue234",
				"changed Description234", "10008", "\"Key not found\"",
				"\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] deleteKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { "Key1", "15004",
				"\"KeyValuePair deleted successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "testKey2", "15004",
				"\"KeyValuePair deleted successfully\"", "\"SUCCESS\"", "1" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Delete key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] deleteKeyValuePairDP_negative(ITestContext testContext) {
		String[] arr1 = { "Key#$%345", "10008", "\"Key not found\"",
				"\"ERROR\"", "0" };
		String[] arr2 = { "testKey2", "10008", "\"Key not found\"",
				"\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// --------------------------------------widget
	// -------------------------------------------------------------------

	List<String> allWidgetKeys = getAllWidgetKeys();
	String widegetKey1 = allWidgetKeys.get(0);
	String widegetKey2 = allWidgetKeys.get(1);
	String widegetKey3 = allWidgetKeys.get(2);
	String widegetKey4 = allWidgetKeys.get(3);

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { widegetKey1 };
		String[] arr2 = { widegetKey2 };
		String[] arr3 = { widegetKey3 };
		String[] arr4 = { "" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairDP_negative(ITestContext testContext) {
	//	String[] arr1 = { "$%#$%$#%#$" };
		String[] arr2 = { widegetKey1 + ".amount" };
		String[] arr3 = { widegetKey2 + "123" };
		Object[][] dataSet = new Object[][] {  arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairForGivenListDP(
			ITestContext testContext) {
		String[] arr1 = { widegetKey1 };
		String[] arr2 = { "\"" + widegetKey1 + "," + widegetKey2 + "\"" };
		String[] arr3 = { "\"" + widegetKey1 + "," + widegetKey2 + ","
				+ widegetKey3 + "\"" };
		String[] arr4 = { "\"" + widegetKey1 + "," + widegetKey2 + ","
				+ widegetKey3 + "," + widegetKey4 + "\"" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 5);
	}

	/**
	 * Get a Feature Gate key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairForGivenListDP1(
			ITestContext testContext) {
		List<String> widgetKeys = new ArrayList<String>();
		List<String> vals = new ArrayList<String>();
		List<String> descriptions = new ArrayList<String>();
		RequestGenerator req = getRequest(APINAME.GETALLWIDGETKEYVALUEPAIR);
		widgetKeys = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..key");
		vals = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..value");
		descriptions = JsonPath.read(req.respvalidate.returnresponseasstring(),
				"$.data..description");
		String[] arr1 = { widgetKeys.get(0), vals.get(0), descriptions.get(0) };
		String[] arr2 = { widgetKeys.get(1), vals.get(1), descriptions.get(1) };
		String[] arr3 = { widgetKeys.get(2), vals.get(2), descriptions.get(2) };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairForGivenListDP_negative(
			ITestContext testContext) {
	//	String[] arr1 = { "$%#$%$#%#$" };
		String[] arr2 = { "fsdafsdfsd" };
		String[] arr3 = { widegetKey1 + "3242," + widegetKey2 + "3242,"
				+ widegetKey3 + "3242" };
		String[] arr4 = { widegetKey1 + "3242,$%#$%$#%#$," + widegetKey3
				+ "3242" };
		Object[][] dataSet = new Object[][] { arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 3);
	}

	/**
	 * Get a widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] getWidgetKeyValuePairForGivenListDP_negative1(
			ITestContext testContext) {
		String[] arr1 = { ",," };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewWidgetKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "testValue1", "Description1", "15003",
				"\"KeyValuePair added successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "WidgetKey2", "testValue2", "Description2", "15003",
				"\"KeyValuePair added successfully\"", "\"SUCCESS\"", "1" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewWidgetKeyValuePairDP_negative(
			ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "testValue1", "Description1", "54",
				"\"Duplicate entry 'Key1' for key 'key'\"", "\"ERROR\"", "0" };
		String[] arr2 = { "WidgetKey2", "testValue2", "Description2", "54",
				"\"Duplicate entry 'testKey2' for key 'key'\"", "\"ERROR\"",
				"0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * Create a new widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] createNewWidgetKeyValuePairDP_negative1(
			ITestContext testContext) {
		String[] arr1 = { "", "testValue1", "Description1", "10001",
				"\"Empty key is not allowed\"", "\"ERROR\"", "0" };
		String[] arr2 = { "", "testValue2", "Description2", "10001",
				"\"Empty key is not allowed\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateWidgetKeyValuePairDP(ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "key value Automation123",
				"Description123", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "WidgetKey2", "key1 value for Automation",
				"Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		Object[][] dataSet = new Object[][] { arr1 /* ,arr2 */};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update widget key value pair.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateWidgetKeyValuePairDP_negative(
			ITestContext testContext) {
		String[] arr1 = { "", "testValue1", "Description1", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		String[] arr2 = { "", "testValue2", "Description2", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update multiple widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateWidgetKeyValuePairForGivenListDP(
			ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "ValueAutomation123", "Description123",
				"WidgetKey2", "automation testValue234",
				"changed Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "2" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	/**
	 * update multiple widget key value pairs for a given list.
	 * 
	 * @author jhansi.bai
	 */
	@DataProvider
	public Object[][] updateWidgetKeyValuePairForGivenListDP_negative(
			ITestContext testContext) {
		String[] arr1 = { "", "testValue123", "Description123", "WidgetKey1",
				"changed testValue234", "changed Description234", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		String[] arr2 = { "WidgetKey2", "testValue123", "Description123", "",
				"changed testValue234", "changed Description234", "10008",
				"\"Key not found\"", "\"ERROR\"", "0" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] updateWidgetKeyValuePairOnProduction(
			ITestContext testContext) {
		String[] arr1 = { "testkey", "false" };
		// String [] arr2 = {"testkey","true"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_getKeyValue_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { key1 };
		String[] arr2 = { key2 };
		String[] arr3 = { key3 };
		String[] arr4 = { "" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_getKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { key1 };
		String[] arr2 = { "\"" + key1 + "," + key2 + "\"" };
		String[] arr3 = { "\"" + key1 + "," + key2 + "," + key3 + "\"" };
		String[] arr4 = { "\"" + key1 + "," + key2 + "," + key3 + "," + key4
				+ "\"" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_updateKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "key for Automation", "key value Automation123",
				"Description123", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "key1 for Automation", "key1 value for Automation",
				"Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_updateKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "key for Automation", "ValueAutomation123",
				"Description123", "key1 for Automation",
				"automation testValue234", "changed Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "2" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_getWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { widegetKey1 };
		String[] arr2 = { widegetKey2 };
		String[] arr3 = { widegetKey3 };
		String[] arr4 = { "" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_getWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { widegetKey1 };
		String[] arr2 = { "\"" + widegetKey1 + "," + widegetKey2 + "\"" };
		String[] arr3 = { "\"" + widegetKey1 + "," + widegetKey2 + ","
				+ widegetKey3 + "\"" };
		String[] arr4 = { "\"" + widegetKey1 + "," + widegetKey2 + ","
				+ widegetKey3 + "," + widegetKey4 + "\"" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_updateWidgetKeyValuePair_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "key value Automation123",
				"Description123", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };
		String[] arr2 = { "WidgetKey2", "key1 value for Automation",
				"Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "1" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_updateWidgetKeyValuePairForGivenList_verifyResponseDataNodesUsingSchemaValidations(
			ITestContext testContext) {
		String[] arr1 = { "WidgetKey1", "ValueAutomation123", "Description123",
				"WidgetKey2", "automation testValue234",
				"changed Description234", "15003",
				"\"KeyValuePair updated successfully\"", "\"SUCCESS\"", "2" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public Object[][] KeyValuePairDP_UpdateFG_SalesActivityBeforeSales(
			ITestContext testContext) {
		/*String keyFromJenkins = System.getProperty("keyfromui");
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
		}*/

		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMap(pathOfFile);
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
	public Object[][] KeyValuePairDP_UpdateWidgetKeyValue_SalesActivityBeforeSales(
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
		// String [] arr2 = {valueFromJenkins};

		// String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		/*
		 * LinkedHashMap<String,String> keyvalepairmap =
		 * getKeyValueMap(pathOfFile); Set<Entry<String,String>> entrySet =
		 * keyvalepairmap.entrySet(); int i = 0, j = 0; String[] arr = new
		 * String[entrySet.size()]; for(Entry<String,String> entry : entrySet){
		 * String key = entry.getKey(); System.out.println("Key = "+ key);
		 * String value = entry.getValue(); System.out.println("value = "
		 * +value); arr[i] = entry.getKey()+","+entry.getValue(); ++i; }
		 */
		/*
		 * Object [][] dataSet = new Object [arr.length + 1][]; for (String item
		 * : arr) { dataSet[j] = new Object[] {
		 * item.split(",")[0],item.split(",")[1] }; ++j; }
		 */
		/* if(keyFromJenkins !=null && valueFromJenkins!=null ){ */
		Object[][] dataSet = new Object[][] { arr1 };

		return dataSet;
	}

	@DataProvider
	public Object[][] KeyValuePairDP_UpdateFG_SalesActivityAfterSales(
			ITestContext testContext) {
		/*String keyFromJenkins = System.getProperty("keyfromui");
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
		}*/

		String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair1.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		LinkedHashMap<String, String> keyvalepairmap = getKeyValueMap(pathOfFile);
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
	public Object[][] KeyValuePairDP_UpdateWidgetKeyValue_SalesActivityAfterSales(
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
		// String [] arr2 = {valueFromJenkins};

		// String pathOfFile = "/root/Desktop/SalesOps/keyvaluepair.csv";
		// String pathOfFile = "/Users/mohit.jain/Desktop/key.csv";
		/*
		 * LinkedHashMap<String,String> keyvalepairmap =
		 * getKeyValueMap(pathOfFile); Set<Entry<String,String>> entrySet =
		 * keyvalepairmap.entrySet(); int i = 0, j = 0; String[] arr = new
		 * String[entrySet.size()]; for(Entry<String,String> entry : entrySet){
		 * String key = entry.getKey(); System.out.println("Key = "+ key);
		 * String value = entry.getValue(); System.out.println("value = "
		 * +value); arr[i] = entry.getKey()+","+entry.getValue(); ++i; }
		 */
		/*
		 * Object [][] dataSet = new Object [arr.length + 1][]; for (String item
		 * : arr) { dataSet[j] = new Object[] {
		 * item.split(",")[0],item.split(",")[1] }; ++j; }
		 */
		/* if(keyFromJenkins !=null && valueFromJenkins!=null ){ */
		Object[][] dataSet = new Object[][] { arr1 };

		return dataSet;
	}

	public static LinkedHashMap<String, String> getKeyValueMap(String pathOfFile) {
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
}
