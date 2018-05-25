package com.myntra.apiTests.erpservices.lms.dp;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.CourierCode;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.utility.DataOrcUtil;
import com.myntra.lms.client.status.HubType;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.logistics.platform.domain.ShipmentUpdateActivityTypeSource;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.tms.hub.TMSHubType;
import com.myntra.tms.lane.LaneType;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Shubham gupta
 *
 */
public class LMSTestsDP {
	static Initialize init = new Initialize("/Data/configuration");

	@DataProvider
	public static Object [][] addDC(ITestContext testContext)
	{
		Object[] arr1 = {"MADC", "myntra_auto_dc_1", "auto_test_manger_1", "1", "auto_test_test_123", "Banaglore", "YLH_12", "KA",
				"998877", "0", "0", "1", "1", "ML", "1200002213", "ML", "801", "DELIVERY_CENTER added successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"MADC", "myntra_auto_dc_1", "auto_test_manger_1", "1", "auto_test_test_123", "Banaglore", "YLH_12", "KA",
				"989898", "0", "0", "1", "1", "ML", "1200002213", "FRANCHISE", "801", "DELIVERY_CENTER added successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"MADC", "myntra_auto_dc_1", "auto_test_manger_1", "1", "auto_test_test_123", "Banaglore", "YLH_12", "KA",
				"989897", "0", "0", "1", "1", "ML", "1200002213", "OTHER_LOGISTICS", "801", "DELIVERY_CENTER added successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"MADC", "myntra_auto_dc_1", "auto_test_manger_1", "1", "auto_test_test_123", "Banaglore", "YLH_12", "KA",
				"919191", "0", "0", "1", "1", "ML", "1200002213", "WHPL", "801", "DELIVERY_CENTER added successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"MADC", "myntra_auto_dc_1", "auto_test_manger_1", "3", "auto_test_test_123", "Banaglore", "YLH_12", "KA",
				"929292", "0", "0", "1", "1", "3PL", "1200002213", "WHPL", "801", "DELIVERY_CENTER added successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4, arr5 };
		//Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object [][] addDCNegative(ITestContext testContext)
	{
		Object[] arr1 = {"MRD", "MAGGADI ROAD (MRD)", "PRASANTH. T", "1", "Door 61,  3rd main,  Kottigeplaya, Magdi Main road,  (Adjoint, MYS outer ring road)", "Bangalore", "BLR", "Karnataka",
				"560092", "0", "0", "1", "1", "ML", "9731834522", "ML", "74","Unique Key contraint violation", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}


	@DataProvider
	public static Object [][] updateDC(ITestContext testContext)
	{
		Object[] arr1 = {"6", "EMP", "EMPLOYEE (EMP)", "Sreeni", "2", "EMP C/O MAYNTRA.COM", "BANGALORE", "BLR", "Karnataka", "560102", "0", "0", "1", "1", "ML", "7829542993", "ML"};
		Object[] arr2 = {"6", "EMP", "EMPLOYEE (EMP)", "Sreeni", "3", "EMP C/O MAYNTRA.COM", "BANGALORE", "BLR", "Karnataka", "560102", "0", "0", "1", "1", "ML", "7829542993", "ML"};
		Object[] arr3 = {"6", "EMP", "EMPLOYEE (EMP)", "Sreeni", "2", "EMP C/O MAYNTRA.COM Kudlu Gate", "BANGALORE", "BLR", "Karnataka", "560102", "0", "0", "1", "1", "ML", "7829542993", "ML"};
		Object[] arr4 = {"6", "EMP", "EMPLOYEE (EMP)", "Sreeni", "2", "EMP C/O MAYNTRA.COM", "BANGALORE", "BLR", "Karnataka", "560102", "1", "0", "0", "1", "ML", "7829542993", "ML"};
		Object[] arr5 = {"6", "EMP", "EMPLOYEE (EMP)", "Sreeni kumar", "2", "EMP C/O MAYNTRA.COM", "BANGALORE", "BLR", "Karnataka", "560102", "0", "1", "1", "0", "ML", "7829542989", "ML"};
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getDC(ITestContext testContext)
	{
		Object[] arr1 = {"6", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr10 = {"search?start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"509090", "1", "Row with given id/info not found", EnumSCM.ERROR};
		Object[] arr4 = {"search?q=code.like:HEB&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=courierCode.like:DE&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=active.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=pincode.like:560068&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"search?q=selfShipSupported.eq:true___isCardEnabled.eq:true___active.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"search?q=courierCode.like:EK___selfShipSupported.eq:false___isCardEnabled.eq:true___active.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr11 = {"search?fetchSize=-1&q=active.eq:true", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr12 = {"5", "803", "DELIVERY_CENTER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,arr11, arr12 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] addDeliveryStaff(ITestContext testContext)
	{
		Object[] arr1 = {"MNTRDS","Mynt DS","DeliveryStaff", "1", "9911772266", "lmsadmin", "true","false","BIKER", "M11121", "false","811", "Delivery Staff added successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"MNTRDS","Mynt DS","DeliveryStaff", "9", "9911772266", "lmsadmin", "true","false","BIKER", "M11121", "false","811", "Delivery Staff added successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"MNTRDS","Mynt DS","DeliveryStaff", "1", "9911772266", "lmsadmin", "true","false","CYCLIST", "M11121","true", "811", "Delivery Staff added successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"MNTRDS","Mynt DS","DeliveryStaff", "6", "9911772266", "lmsadmin", "true","false","CYCLIST", "M11121", "true","811", "Delivery Staff added successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}
	@DataProvider
	public static Object [][] updateDeliveryStaff(ITestContext testContext)
	{
		Object[] arr1 = {"6","V00301","MADAN","MADAN", "5", "741187749", "lmsadmin", "true","false","BIKER", "M09999", "false", "814", "Delivery Staff updated successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"6","V00301","MADAN","MADAN", "9", "741187780", "lmsadmin", "true","false","BIKER", "M09999","true", "814", "Delivery Staff updated successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"6","V00301","MADAN","KUMAR", "9", "741187749", "lmsadmin", "true","false","CYCLIST", "M09999", "false","814", "Delivery Staff updated successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"6","V00301","MADAN","MADAN", "6", "741187790", "lmsadmin", "true","false","DELIVERY_VAN", "M09999","true", "814", "Delivery Staff updated successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"6","V00301","MADAN","MADAN", "-1", "741187790", "lmsadmin", "true","false","DELIVERY_VAN", "M09999", "false","814", "Delivery Staff updated successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getDeliveryStaff(ITestContext testContext)
	{
		Object[] arr1 = {"10", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"509090", "56", "Error occurred while retrieving/processing data", EnumSCM.ERROR};
		Object[] arr4 = {"search?q=deleted.eq:false&start=0&fetchSize=10&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=code.eq:N00027___deleted.eq:false&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=mobile.eq:8861786571&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=firstName.like:NAVNEET___deleted.eq:false&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"search?q=available.eq:false___deleted.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"search?q=deliveryCenter.id.eq:5&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "813", "Delivery Staff(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] generateTrackingNumber(ITestContext testContext)
	{
		Object[] arr1 = {"123456%", "111","SQ","123456889","123456999","1", null, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"C23456%", "111","PL","23456889","23456999","1", "C", "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"C23456%", "0","EK","23456999","23456889","1", "C", "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTrackingNumber(ITestContext testContext)
	{
		Object[] arr1 = {"ML", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"EK", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"ML", "19", "true", LMS_PINCODE.ML_BLR,EnumSCM.NORMAL, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"ML", "6", "false", LMS_PINCODE.ML_BLR,EnumSCM.NORMAL, "921", "Tracking number has been retrieved successfully", EnumSCM.ERROR};
		Object[] arr5 = {"ML", "1", "true", "11001139993", EnumSCM.NORMAL,"51", "Error occurred", EnumSCM.ERROR};
		Object[] arr6 = {"MLL", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"866", "Invalid Courier Id", EnumSCM.ERROR};
		Object[] arr7 = {"ML", "36", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"ML","45","true",LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"ML","700","true",LMS_PINCODE.NORTH_DELHI,EnumSCM.NORMAL, "921", "Tracking number has been retrieved successfully", EnumSCM.ERROR};
		Object[] arr10 = {"ML","1","false",LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr11 = {"DE","45","true",LMS_PINCODE.NORTH_DELHI,EnumSCM.NORMAL, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr12 = {"IP","1","true",LMS_PINCODE.NORTH_DELHI, EnumSCM.NORMAL,"916", "Tracking number not available for courier: IP with warehouse id: 1 and isCod: true", EnumSCM.SUCCESS};
		Object[] arr13 = {"ML","1","true",LMS_PINCODE.ML_BLR, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr14 = {"ML","1","true",LMS_PINCODE.NORTH_DE, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr15 = {"ML","36","true","194101", EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr16 = {"ML", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr17 = {"EK", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr18 = {"ML", "19", "true", LMS_PINCODE.ML_BLR,EnumSCM.EXPRESS, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr19 = {"ML", "19", "false", LMS_PINCODE.ML_BLR,EnumSCM.EXPRESS, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr20 = {"ML", "1", "true", "11001139993", EnumSCM.EXPRESS,"51", "Error occurred", EnumSCM.ERROR};
		Object[] arr21 = {"MLL", "1", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"866", "Invalid Courier Id", EnumSCM.ERROR};
		Object[] arr22 = {"ML", "36", "true", LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr23 = {"ML","45","true",LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr24 = {"ML","700","true",LMS_PINCODE.NORTH_DELHI,EnumSCM.EXPRESS, "921", "Tracking number has been retrieved successfully", EnumSCM.ERROR};
		Object[] arr25 = {"ML","1","false",LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr26 = {"DE","45","true",LMS_PINCODE.NORTH_DELHI,EnumSCM.EXPRESS, "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr27 = {"IP","1","true",LMS_PINCODE.NORTH_DELHI, EnumSCM.EXPRESS,"916", "Tracking number not available for courier: IP with warehouse id: 1 and isCod: true", EnumSCM.SUCCESS};
		Object[] arr28 = {"ML","1","true",LMS_PINCODE.ML_BLR, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr29 = {"ML","1","true",LMS_PINCODE.NORTH_DE, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr30 = {"ML","1","true","194101", EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr31 = {"EK","28","true",LMS_PINCODE.ML_BLR, EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr32 = {"EK","28","false",LMS_PINCODE.ML_BLR, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr33 = {"EK","36","false",LMS_PINCODE.ML_BLR, EnumSCM.NORMAL,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr34 = {"ML","19","true","110001", EnumSCM.EXPRESS,"921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21,
				arr22, arr23, arr24, arr25,arr26, arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 40, 40);
	}

	@DataProvider
	public static Object [][] addCourier(ITestContext testContext)
	{
		Object[] arr1 = {"AUTST","#808090", "1000", "false","true","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "true", "false","true", "false","true", "STORED", "false","false", "http://www.test-courier.com/", "901", "Courier added successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"AUTST","#808090", "200", "true","true","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "false", "true","false", "true","false", "GENERATED", "false","false", "http://www.test-courier.com/", "901", "Courier added successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"AUTST","#808090", "1000", "false","false","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "false", "false","false", "true","false", "STORED", "false","false", "http://www.test-courier.com/", "901", "Courier added successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"AUTST","#808090", "0", "true","false","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "false", "false","false", "true","false", "PULLED", "false","false", "http://www.test-courier.com/", "901", "Courier added successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"VL","#FFBF00", "1000", "false","false","&lt;manifest_template&gt;&lt;", "VRL Logistics", "false", "false","false", "false","false", "STORED", "false","false", "http://www.vrllogistics.com/", "74", "Unique Key contraint violation", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] updateCourier(ITestContext testContext)
	{
		Object[] arr1 = {"544", "AUUCT","#808090", "1000", "false","true","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "true", "false","true", "false","true", "STORED", "false","false", "http://www.test-courier.com/", "904", "Courier updated successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"544", "AUUCT","#808090", "200", "true","true","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "false", "true","false", "true","false", "GENERATED", "false","false", "http://www.test.com/", "904", "Courier updated successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"544", "AUUCT","#808090", "1000", "false","false","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "false", "false","false", "true","false", "STORED", "false","false", "http://www.test-courier.com/", "904", "Courier updated successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"544", "AUUCT","#808090", "0", "true","false","&lt;manifest_template&gt;&lt;", "AUTOMATION Test Courier", "true", "false","false", "true","false", "PULLED", "false","false", "http://www.automation-courier.com/", "904", "Courier updated successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}
	@DataProvider/*(parallel = true)*/
	public static Object [][] getCourier(ITestContext testContext)
	{
		Object[] arr1 = {"search", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=code.like:ML&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"4", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"4990", "1", "Row with given id/info not found", EnumSCM.ERROR};
		Object[] arr5 = {"search?q=name.like:Blue+Dart&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=enabled.eq:true___returnSupported.eq:true___splitTrackingNumberEnabled.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=code.like:BD___name.like:Blue+Dart___returnSupported.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", "903", "Courier retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getDropDownValuesCourier(ITestContext testContext)
	{
		Object[] arr1 = {"ItemAttribute"};
		Object[] arr2 = {"ServiceType"};
		Object[] arr3 = {"PaymentMode"};
		Object[] arr4 = {"ShippingMethod"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripOrderAssignment(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.OFD};
		Object[] arr2 = {EnumSCM.DL};
		Object[] arr3 = {EnumSCM.FD};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] printMasterBagInvoice(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.RECEIVED};
		Object[] arr2 = {EnumSCM.CLOSED};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] getCourierForPincode(ITestContext testContext)
	{
		Object[] arr1 = {"560068", EnumSCM.SUCCESS};
		Object[] arr2 = {"411001", EnumSCM.SUCCESS};
		Object[] arr3 = {"400053", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] addRegion(ITestContext testContext)
	{
		Object[] arr1 = {"QWTZ","API_AUTO_REGION", "1031", "Region added successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"MAXLENGTH","Max length reach", "74", "Unique Key contraint violation", EnumSCM.ERROR};
		Object[] arr3 = {"","Automation region", "74", "Unique Key contraint violation", EnumSCM.ERROR};
		Object[] arr4 = {"AURG","", "74", "Unique Key contraint violation", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] updateRegion(ITestContext testContext)
	{
		Object[] arr1 = {"587", "AURE","Automation region", "824", "ROUTE updated successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"587","AURE","Automation region Update region", "824", "ROUTE updated successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"587","AUTRMAXLENGHT","Automation region", "58", "Error occurred while updating/processing data", EnumSCM.ERROR};
		Object[] arr4 = {"587","","Automation region", "74", "Unique Key contraint violation", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getRegion(ITestContext testContext)
	{
		Object[] arr1 = {"search", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"587", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"54000", "53", "Row with given id not found", EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=code.eq:BLRP", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=id.eq:36", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=id.eq:587&name.eq:TAREJn", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=name.eq:Bihar", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"search?fetchSize=-1", "1033", "Region(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] addPincode(ITestContext testContext)
	{
		Object[] arr1 = {"569968", "AURE", "AURE", "BLR", "Bangalore", "AURE", "Karnataka","KAR","Automation", "840", "PINCODE added successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"569968", "AUTDDD", "AUTD", "BLR", "Bangalore", "AUTD", "Karnataka","KAR","Automation", "54", "Error occurred while inserting/processing data", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] updatePincode(ITestContext testContext)
	{//id, areaCode, areaName, cityCode, cityName, regionCode, state, stateCode, createdBy
		Object[] arr1 = {"122000", "GGN", "Gurgaon", "GGN", "Gurgaon", "3PLNA", "HARYANA","HAR","Automation", "842", "PINCODE updated successfully", EnumSCM.SUCCESS};
//		Object[] arr2 = {"122000", "GGNN", "Gurgaon rural", "GGNN", "Gurgaon rural", "3PLNA", "HARYANAA","HAR","Automation", "842", "PINCODE updated successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"569968", "AUTDDD", "AUTD", "BLR", "Bangalore", "AURE", "Karnataka","KAR","Automation", "58", "Error occurred while updating/processing data", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getPincode(ITestContext testContext)
	{
		Object[] arr1 = {"search", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=id.eq:560068", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"search?q=regionCode.like:3PL&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=areaName.like:Bangalore&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=cityCode.like:BLR&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=stateCode.like:KAR&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=regionCode.like:MU___cityCode.like:BOM&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"560068", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"411001", "841", "PINCODE retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getPincodeV1(ITestContext testContext)
	{
		Object[] arr1 = {LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
		Object[] arr2 = {LMS_PINCODE.MUMBAI_DE_RHD, EnumSCM.SUCCESS};
		Object[] arr3 = {LMS_PINCODE.NORTH_DE, EnumSCM.SUCCESS};
		Object[] arr4 = {LMS_PINCODE.PUNE_EK, EnumSCM.SUCCESS};
		Object[] arr5 = {LMS_PINCODE.JAMMU_IP, EnumSCM.SUCCESS};
		Object[] arr6 = {"400", EnumSCM.ERROR};
		Object[] arr7 = {"009988", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getShippingCutoff(ITestContext testContext)
	{
		Object[] arr1 = {"24",LMS_PINCODE.ML_BLR,36, ShippingMethod.NORMAL,"ML", EnumSCM.SUCCESS};
		Object[] arr2 = {"16",LMS_PINCODE.ML_BLR,36, ShippingMethod.NORMAL,"ML", EnumSCM.SUCCESS};
		Object[] arr3 = {"24",LMS_PINCODE.ML_BLR,28, ShippingMethod.NORMAL,"ML", EnumSCM.SUCCESS};
		Object[] arr4 = {"24",LMS_PINCODE.ML_BLR,36, ShippingMethod.SDD,"ML", EnumSCM.SUCCESS};
		Object[] arr5 = {"24",LMS_PINCODE.ML_BLR,36, ShippingMethod.EXPRESS,"ML", EnumSCM.SUCCESS};
		Object[] arr6 = {"24",LMS_PINCODE.PUNE_EK,36, ShippingMethod.NORMAL,"EK", EnumSCM.SUCCESS};
		Object[] arr7 = {"24",LMS_PINCODE.NORTH_DE,36, ShippingMethod.NORMAL,"DE", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getDispatchHubFromWarehouse(ITestContext testContext)
	{
		Object[] arr1 = {1, EnumSCM.SUCCESS};
		Object[] arr2 = {36, EnumSCM.SUCCESS};
		Object[] arr3 = {28, EnumSCM.SUCCESS};
		Object[] arr4 = {19, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getDispatchHubWareHouseConfigList(ITestContext testContext)
	{
		Object[] arr1 = {"DH-BLR", EnumSCM.SUCCESS};
		Object[] arr2 = {"DH-DEL", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getReturnHubWareHouseConfig(ITestContext testContext)
	{
		Object[] arr1 = {"RT-BLR", EnumSCM.SUCCESS};
		Object[] arr2 = {"RT-DEL", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getHubCourierConfig(ITestContext testContext)
	{
		Object[] arr1 = {"DH-BLR","DE", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] uploadBulkOrderTracking(ITestContext testContext)
	{
		Object[] arr1 = {LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36", EnumSCM.NORMAL, EnumSCM.ERROR};
		Object[] arr2 = {LMS_PINCODE.JAMMU_IP, CourierCode.IP.toString(), "36", EnumSCM.NORMAL, EnumSCM.SUCCESS};
		Object[] arr3 = {LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), "36", EnumSCM.NORMAL, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] getCorrectionEvents(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.PACKED,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr2 = {EnumSCM.INSCANNED,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr3 = {EnumSCM.SHIPPED,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr4 = {EnumSCM.DELIVERED,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr5 = {EnumSCM.RTO_CONFIRMED,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr6 = {EnumSCM.LOST,LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString()};
		Object[] arr7 = {EnumSCM.SHIPPED,LMS_PINCODE.PUNE_EK, CourierCode.EK.toString()};
		Object[] arr8 = {EnumSCM.DELIVERED,LMS_PINCODE.PUNE_EK, CourierCode.EK.toString()};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getCourierByPincode(ITestContext testContext)
	{
		Object[] arr1 = {560068, EnumSCM.SUCCESS};
		Object[] arr2 = {411001, EnumSCM.SUCCESS};
		Object[] arr3 = {180001, EnumSCM.SUCCESS};
		Object[] arr4 = {89, EnumSCM.ERROR};
		Object[] arr5 = {400053, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getLmsOrders(ITestContext testContext)
	{
		Map<String, Object> orders = DBUtilities.exSelectQueryForSingleRecord("select order_id from order_to_ship order by last_modified_on DESC limit 1","lms");
		Object[] arr1 = {"dashboardSearch?q=order.status.eq:PK&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"dashboardSearch?q=orderId.eq:"+orders.get("order_id").toString()+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"dashboardSearch?q=order.status.eq:SH&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"dashboardSearch?q=order.status.eq:SH___order.orderShipmentAssociations.shipment.originPremisesType.eq:WH___order.orderShipmentAssociations"
				+ ".shipment.originPremisesId.eq:1&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"dashboardSearch?q=order.cod.eq:false&start=0&fetchSize=20&sortBy=id&sortOrder=DESC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"dashboardSearch?q=courierOperator.like:ML___order.status.eq:SH&start=0&fetchSize=20&sortBy=order.promiseDate&sortOrder=ASC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"dashboardSearch?q=courierOperator.like:ML___order.deliveryCenterId.eq:5___order.status.eq:SH&start=0&fetchSize=20&sortBy=order.promiseDate&sortOrder=ASC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr8 = {"dashboardSearch?q=courierOperator.like:ML___order.deliveryCenterId.eq:5___order.status.eq:RTO&start=0&fetchSize=20&sortBy=order.promiseDate&sortOrder=ASC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"dashboardSearch?q=courierOperator.like:ML___order.deliveryCenterId.eq:5___order.orderShipmentAssociations.status.eq:RECEIVED___order.status.eq:DL&start=0&fetchSize=20&sortBy=order.promiseDate&sortOrder=ASC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr10 = {"dashboardSearch?q=courierOperator.like:ML___order.orderShipmentAssociations.status.eq:IN_TRANSIT___order.status.eq:SH&start=0&fetchSize=20&sortBy=order.promiseDate&sortOrder=ASC", "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public static Object [][] orderInScan(ITestContext testContext)
	{	//String orderId, String trackingNumber, String warehouse, String isTryNbuy, String statusCode, String statusMessage, String statusType)
//		//If this fails please make sure the order Id is exist in LMS in PK status. If somehow it gets deleted please create an order and change the order id here, Check the warehouse is existing.
//		Object[] arr1 = {"70023010", "ML000010000","1", "true",LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
//		Object[] arr2 = {"70023011", "ML000010001","1", "false",LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
//		Object[] arr3 = {"70023010", "ML000010000","28","false", LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
		Object[] arr4 = {"989020522", "ML0008820101","1", "false",LMS_PINCODE.ML_BLR, EnumSCM.ERROR};//Wrong order ID
		Object[] arr5 = {null, null,"36","false", LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
		Object[] arr6 = {null, null,"36","true", LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
		Object[] arr7 = {null, null,"36","false", LMS_PINCODE.MUMBAI_DE_RHD, EnumSCM.SUCCESS};
		Object[] arr8 = {null, null,"36","false", LMS_PINCODE.PUNE_EK, EnumSCM.SUCCESS};
		Object[] arr9 = {null, null,"28","false", LMS_PINCODE.ML_BLR,EnumSCM.SUCCESS};
		Object[] arr10 = {null, null,"28","true", LMS_PINCODE.ML_BLR, EnumSCM.SUCCESS};
		Object[] arr11 = {null, null,"28","false", LMS_PINCODE.MUMBAI_DE_RHD, EnumSCM.SUCCESS};
		Object[] arr12 = {null, null,"28","false", LMS_PINCODE.PUNE_EK, EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { /*arr1, arr2, arr3, */arr4, arr5,arr6, arr7, arr8, arr9, arr10, arr11, arr12};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] selfMarkDL(ITestContext testContext)
	{ //String toStatus, String pincode, String courierCode, String shippingMethod, boolean istrynBuy, String statusType
		Object[] arr1 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, false,EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, false,EnumSCM.ERROR};
		Object[] arr3 = {EnumSCM.IS, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, false,EnumSCM.ERROR};
		Object[] arr9 = {EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, false,EnumSCM.ERROR};
		Object[] arr4 = {EnumSCM.OFD, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, false,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.NORMAL, true,EnumSCM.ERROR}; // TnB
		Object[] arr6 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), EnumSCM.XPRESS, false,EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.SH, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), EnumSCM.NORMAL, false,EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.SH, LMS_PINCODE.MUMBAI_DE_RHD, CourierCode.DE.toString(), EnumSCM.NORMAL, false,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] {  arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] ordersInScanWithDiffWHnProcess(ITestContext testContext)
	{ //String orderWHId, String inscanAt, String processInWH, String status
		Object[] arr1 = {"36","28","28",EnumSCM.SUCCESS};
		Object[] arr2 = {"36","28","36",EnumSCM.ERROR};

		Object[][] dataSet = new Object[][] {  arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] orderInScanGOR(ITestContext testContext){
		Object[] arr1 = {LMS_PINCODE.ML_BLR,"ML",EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {LMS_PINCODE.ML_BLR,"ML",EnumSCM.XPRESS,EnumSCM.ERROR};
		Object[] arr3 = {LMS_PINCODE.ML_BLR,"ML",EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr4 = {LMS_PINCODE.PUNE_EK,"EK",EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr5 = {LMS_PINCODE.JAMMU_IP,"IP",EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr6 = {LMS_PINCODE.JAMMU_IP,"IP",EnumSCM.SDD,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] {  arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] lmsLost(ITestContext testContext){
		//status, pincode, courierCode, warehouse, shippingMethod, paymentMode, isTnB, statusType
		Object[] arr1 = {EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.PK, LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.IS, LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.SH, LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.XPRESS, "cod", false, EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.SDD, "cod", false, EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "28",EnumSCM.NORMAL, "CC", false, EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "WALLET", false, EnumSCM.SUCCESS};
		Object[] arr9 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", true, EnumSCM.SUCCESS};
		Object[] arr10 = {EnumSCM.SH, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr11 = {EnumSCM.PK, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr12 = {EnumSCM.IS, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		//Object[] arr16 = {EnumSCM.ADDED_TO_MB, LMS_PINCODE.PUNE_EK, CourierCode.EK.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr13 = {EnumSCM.SH, LMS_PINCODE.MUMBAI_DE_RHD, CourierCode.DE.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		Object[] arr14 = {EnumSCM.SH, LMS_PINCODE.JAMMU_IP, CourierCode.IP.toString(), "36",EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
		//Object[] arr15 = {EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36",EnumSCM.NORMAL, "cod", true, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] {  arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] unrto(ITestContext testContext) {
		//status, pincode, courierCode, warehouse, shippingMethod, paymentMode, isTnB, statusType
		Object[] arr1 = {LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36", EnumSCM.NORMAL, "cod", false, EnumSCM.SUCCESS};
//		Object[] arr2 = {LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36", EnumSCM.NORMAL, "CC", false, EnumSCM.SUCCESS};
//		Object[] arr3 = {LMS_PINCODE.NORTH_CGH, CourierCode.ML.toString(), "36", EnumSCM.NORMAL, "cod", true, EnumSCM.SUCCESS};
//		Object[] arr4 = {LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36", EnumSCM.NORMAL, "WALLET", false, EnumSCM.SUCCESS};
//		Object[] arr5 = {LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36", EnumSCM.XPRESS, "cod", false, EnumSCM.SUCCESS};
//		Object[] arr6 = {LMS_PINCODE.ML_BLR, CourierCode.ML.toString(), "36", EnumSCM.SDD, "DC", false, EnumSCM.SUCCESS};
//		Object[][] dataSet = new Object[][] {  arr1, arr2, arr3, arr4, arr5, arr6};
		Object[][] dataSet = new Object[][] {  arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] ordersInScanNormal(ITestContext testContext)
	{	// String toStatus,String zipcode,String courierCode,String warehouseId,String inscanWhId,String shippingMethod,String paymentMode,boolean isTnB
		Object[] arr1 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "36","36",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "28","36",EnumSCM.NORMAL, "on",false, EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PK, LMS_PINCODE.PUNE_EK,"EK", "36","36",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.PK, LMS_PINCODE.PUNE_EK,"EK", "36","28",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.PK, LMS_PINCODE.MUMBAI_DE_RHD,"DE", "36","36",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.PK, LMS_PINCODE.MUMBAI_DE_RHD,"DE", "36","28",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.PK, LMS_PINCODE.NORTH_DE,"DE", "36","36",EnumSCM.NORMAL, "cod",false, EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "36","36",EnumSCM.NORMAL, "cod",true, EnumSCM.SUCCESS};
		Object[] arr9 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "28","36",EnumSCM.SDD, "on", true, EnumSCM.SUCCESS};
		Object[] arr10 = {EnumSCM.PK, LMS_PINCODE.PUNE_EK,"EK", "36","28",EnumSCM.SDD, "cod",false, EnumSCM.SUCCESS};
		Object[] arr11 = {EnumSCM.PK, LMS_PINCODE.MUMBAI_DE_RHD,"DE", "36","28",EnumSCM.SDD, "cod",false, EnumSCM.SUCCESS};
		Object[] arr12 = {EnumSCM.PK, LMS_PINCODE.NORTH_DE,"DE", "36","36",EnumSCM.SDD, "cod",false, EnumSCM.SUCCESS};
		Object[] arr13 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "36","36",EnumSCM.XPRESS, "cod",true, EnumSCM.SUCCESS};
		Object[] arr14 = {EnumSCM.PK, LMS_PINCODE.PUNE_EK,"EK", "36","28",EnumSCM.XPRESS, "on",false, EnumSCM.SUCCESS};
		Object[] arr15 = {EnumSCM.PK, LMS_PINCODE.MUMBAI_DE_RHD,"DE", "36","28",EnumSCM.XPRESS, "cod",false, EnumSCM.SUCCESS};
		Object[] arr16 = {EnumSCM.PK, LMS_PINCODE.NORTH_DE,"DE", "36","36",EnumSCM.XPRESS, "cod",false, EnumSCM.SUCCESS};
		Object[] arr17 = {EnumSCM.PK, LMS_PINCODE.JAMMU_IP,"IP", "36","36",EnumSCM.XPRESS, "cod",false, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17};
		//Object[][] dataSet = new Object[][] { arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] bulkUpload(ITestContext testContext)
	{
		Object[] arr1 = {"70020500", "ML0000654",EnumSCM.DL,EnumSCM.DL, "Test", "DC-ELECTRONIC_CITY", "ML", "836", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] ordersInScanWithDiffStatusOrders(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.ADDED_TO_MB,"ML",EnumSCM.ADDED_TO_MB,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr2 = {EnumSCM.SH,"ML",EnumSCM.SHIPPED,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr3 = {EnumSCM.DL,"ML",EnumSCM.DELIVERED,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr4 = {EnumSCM.FD,"ML",EnumSCM.FAILED_DELIVERY,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr5 = {EnumSCM.OFD,"ML",EnumSCM.OUT_FOR_DELIVERY,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr6 = {EnumSCM.CANCELLED_IN_HUB,"ML",EnumSCM.CANCELLED_IN_HUB,LMS_PINCODE.ML_BLR,EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.RTO,"ML",EnumSCM.RTO_CONFIRMED,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
//		Object[] arr8 = {EnumSCM.RECEIVED_IN_DISPATCH_HUB,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
//		Object[] arr9 = {EnumSCM.RECEIVED_IN_REGIONAL_HANDOVER_CENTER,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr10 = {EnumSCM.LOST,"ML",EnumSCM.LOST,LMS_PINCODE.ML_BLR,EnumSCM.ERROR};
		Object[] arr11 = {EnumSCM.ADDED_TO_MB,"EK",EnumSCM.ADDED_TO_MB,LMS_PINCODE.PUNE_EK,EnumSCM.ERROR};
		Object[] arr12 = {EnumSCM.SH,"DE",EnumSCM.SHIPPED,LMS_PINCODE.MUMBAI_DE_RHD,EnumSCM.ERROR};
		Object[] arr13 = {EnumSCM.DL,"DE",EnumSCM.DELIVERED,LMS_PINCODE.NORTH_DE,EnumSCM.ERROR};
		Object[] arr14 = {EnumSCM.CANCELLED_IN_HUB,"EK",EnumSCM.CANCELLED_IN_HUB,LMS_PINCODE.PUNE_EK,EnumSCM.SUCCESS};
		Object[] arr15 = {EnumSCM.ADDED_TO_MB,"DE",EnumSCM.ADDED_TO_MB,LMS_PINCODE.MUMBAI_DE_RHD,EnumSCM.ERROR};
		Object[] arr16 = {EnumSCM.DL,"DE",EnumSCM.DELIVERED,LMS_PINCODE.MUMBAI_DE_RHD,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7/*, arr8, arr9*/, arr10, arr11, arr12, arr13, arr14, arr15, arr16};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] ordersInScanWIthDiffCourierCreationStatus(ITestContext testContext)
	{ //String zipcode,String courierCode, String courierCreationStatus,String statusType
		Object[] arr1 = {LMS_PINCODE.PUNE_EK,"EK",EnumSCM.REJECTED,EnumSCM.ERROR};
		Object[] arr2 = {LMS_PINCODE.PUNE_EK,"EK",EnumSCM.NOT_INITIATED,EnumSCM.ERROR};
		Object[] arr3 = {LMS_PINCODE.PUNE_EK,"EK",EnumSCM.AWAITING,EnumSCM.ERROR};
		Object[] arr4 = {LMS_PINCODE.PUNE_EK,"EK",EnumSCM.ACCEPTED,EnumSCM.SUCCESS};
		Object[] arr5 = {LMS_PINCODE.NORTH_DE,"DE",EnumSCM.REJECTED,EnumSCM.ERROR};
		Object[] arr6 = {LMS_PINCODE.NORTH_DE,"DE",EnumSCM.AWAITING,EnumSCM.ERROR};
		Object[] arr7 = {LMS_PINCODE.ML_BLR,"ML",EnumSCM.REJECTED,EnumSCM.ERROR};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5 , arr6, arr7};
		//Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] ordersInScan3PLOffline(ITestContext testContext)
	{
		Object[] arr1 = {"0",EnumSCM.PK,LMS_PINCODE.KERALA_BD,"BD",EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr2 = {"0",EnumSCM.PK,LMS_PINCODE.JAMMU_IP,"IP",EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr3 = {"998877",EnumSCM.PK,LMS_PINCODE.KERALA_BD,"BD",EnumSCM.ERROR, EnumSCM.SUCCESS};
		Object[] arr4 = {"998877",EnumSCM.PK,LMS_PINCODE.JAMMU_IP,"IP",EnumSCM.ERROR, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] ordersInScanWithDiOffWHnProcess(ITestContext testContext)
	{ //String orderWHId, String inscanAt, String processInWH, String status
		Object[] arr1 = {"36","28","28",EnumSCM.SUCCESS};
		Object[] arr2 = {"36","28","36",EnumSCM.ERROR};

		Object[][] dataSet = new Object[][] {  arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}


	@DataProvider/*(parallel = true)*/
	public static Object [][] getMasterBagWithParam(ITestContext testContext)
	{
		Map<String, Object> shipment = DBUtilities.exSelectQueryForSingleRecord("select id from shipment where order_count >0 order by last_modified_on DESC","lms");
		String shipmentId = shipment.get("id").toString();
		Object[] arr1 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=shippingMethod.eq:EXPRESS&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"search?q=status.eq:IN_TRANSIT___shippingMethod.eq:NORMAL&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=status.eq:IN_TRANSIT___shippingMethod.eq:NORMAL___originPremisesType.eq:WH___originPremisesId.eq:1&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=id.eq:"+shipmentId+"&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=isDeleted.eq:true&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=transporterId.eq:7&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
//        Object[] arr8 = {"ssearch?q=status.eq:RECEIVED_AT_HANDOVER_CENTER&start=0&fetchSize=20&sortBy=id&sortOrder=DESC&um=true", "932","Shipment has been retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"18","5",EnumSCM.SUCCESS};
		Object[] arr2 = {"5","20",EnumSCM.SUCCESS};
		Object[] arr3 = {"18","2281",EnumSCM.SUCCESS};
		Object[] arr5 = {"18","2605",EnumSCM.SUCCESS};
		Object[] arr6 = {"1","5",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3,arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createMasterBag(ITestContext testContext)
	{
		Object[] arr1 = { "1", "WH", "Bangalore", "5", "DC", "Bangalore", EnumSCM.NORMAL, "931","Shipment has been created successfully", EnumSCM.SUCCESS};
		Object[] arr2 = { "1", "WH", "Bangalore", "5", "DC", "Bangalore", EnumSCM.EXPRESS, "931","Shipment has been created successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] updateMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"1", "WH", "Bengaluru", "5", "DC", "Bengaluru", EnumSCM.NORMAL, "NEW","933","Shipment has been updated successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"1", "WH", "Bangalore", "5", "DC", "Bangalore", EnumSCM.EXPRESS, "IN_TRANSIT","933","Shipment has been updated successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"1", "WH", "Bangalore", "5", "DC", "Bangalore", EnumSCM.EXPRESS, "RECEIVED","933","Shipment has been updated successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] scanOrderInMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"0", "109680093","9829252293", "858", "Order Id 9829252293 not found.", EnumSCM.ERROR};
		Object[] arr2 = {"0", "109680093","70115628", "879", "MasterBag is in RECEIVED state. Orders can be added to masterbags in NEW state only.", EnumSCM.ERROR};
		Object[] arr3 = {"1", EnumSCM.NORMAL,"70023011", "938", "The order or pickup can be added to the shipment", EnumSCM.SUCCESS};
		Object[] arr4 = {"2", EnumSCM.EXPRESS,"70023011", "879", "Normal order cannot be added to a Next Day Delivery master-bag!", EnumSCM.ERROR};
		Object[] arr5 = {"3", "DIFF","70023011", "939", "The destination DC(PCM-DC) does not serve pincode - 560068 for Order[orderId:70023011]. This pincode is served by ELC-DC. ", EnumSCM.ERROR};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] saveMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"109680572","70020522", "934", "Orders have been assigned with shipment successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] closeAndShipMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"ML",LMS_PINCODE.ML_BLR,"36", EnumSCM.NORMAL,false};
		Object[] arr2 = {"ML",LMS_PINCODE.ML_BLR,"36", EnumSCM.SDD,false};
		Object[] arr3 = {"ML",LMS_PINCODE.ML_BLR,"36", EnumSCM.XPRESS,false};
		Object[] arr4 = {"ML",LMS_PINCODE.ML_BLR,"36", EnumSCM.NORMAL,true};
		Object[] arr5 = {"EK",LMS_PINCODE.PUNE_EK,"36", EnumSCM.NORMAL,false};
		Object[] arr6 = {"DE",LMS_PINCODE.NORTH_DE,"36", EnumSCM.NORMAL,false};
		Object[] arr7 = {"EK",LMS_PINCODE.PUNE_EK,"36", EnumSCM.XPRESS,false};
		Object[] arr8 = {"DE",LMS_PINCODE.NORTH_DE,"36", EnumSCM.XPRESS,false};
		Object[][] dataSet = new Object[][] { arr1,arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		//Object[][] dataSet = new Object[][] { arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] shipMasterBag(ITestContext testContext)
	{
		Object[] arr1 = {"937", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] masterBagInScanAndUpdate(ITestContext testContext)
	{
		Object[] arr1 = {"109679947", EnumSCM.IN_TRANSIT, "DC-Electronics City (ELC)", "5", "DC", "933", "Shipment has been updated successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] reopenMaterBagAndShip(ITestContext testContext)
	{
		Object[] arr1 = {LMS_PINCODE.PUNE_EK,"EK"};
		Object[] arr2 = {LMS_PINCODE.ML_BLR,"ML"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] reopenMaterOnDiffStatus(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.SH};
		Object[] arr2 = {EnumSCM.DL};
		Object[] arr3 = {EnumSCM.ADDED_TO_MB};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] masterBagReceiveWithExpressNTOD(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.NORMAL, true};
		Object[] arr2 = {EnumSCM.EXPRESS, true};
		//Object[] arr3 = {EnumSCM.SDD, true};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] masterBagReciveClosedMB(ITestContext testContext)
	{
		Object[] arr1 = {"NEW"};
		Object[] arr2 = {"CLOSED"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	//String lp, String cb, String sku, String totalAmount, isCheckCOD, String codAmount, String isTod, String shippingMethod
	@DataProvider
	public static Object [][] createOrderInLMSForwardML(ITestContext testContext)
	{
		Object[] arr1 = {false,"CC"};
		Object[] arr2 = {false,"DC"};
		Object[] arr3 = {false,"WALLET"};
		Object[] arr4 = {false,"NETBANKING"};
		Object[] arr5 = {false,"on"};
		Object[] arr6 = {false,"cod"};
		Object[] arr7 = {true,"on"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	//String login, String lp, String cb, String sku, String totalAmount, isCheckCOD, String codAmount, String isTod, String shippingMethod
	@DataProvider
	public static Object [][] createOrderInLMSForwardMLTODandXpress(ITestContext testContext)
	{
		Object[] arr1 = {true, EnumSCM.NORMAL};
		Object[] arr2 = {false, EnumSCM.NORMAL};
		Object[] arr3 = {true, EnumSCM.SDD};
		Object[] arr4 = {false, EnumSCM.SDD};
		Object[] arr5 = {true, EnumSCM.XPRESS};
		Object[] arr6 = {false, EnumSCM.XPRESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createOrderInLMSForwardWithCancellation(ITestContext testContext)
	{
		Object[] arr1 = {"false", "false", "3913:2", "true","false", EnumSCM.NORMAL};
		Object[] arr2 = {"false", "false", "3913:1, 3866:1","true","false", EnumSCM.NORMAL};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createOrderInLMSValidateRTORTWH(ITestContext testContext)
	{
		Object[] arr1 = { LMS_PINCODE.ML_BLR, "36"};
		Object[] arr2 = {LMS_PINCODE.ML_BLR, "28"};
//		Object[] arr3 = {LMS_PINCODE.NORTH_DELHI, "36"};
//		Object[] arr4 = {LMS_PINCODE.NORTH_DELHI, "28"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] orderCreationAndInscan3PL(ITestContext testContext)
	{
		Object[] arr1 = {"EK"};
		Object[] arr2 = {"DE"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createOrderInLMSwithEXValidateRTORTWH(ITestContext testContext)
	{
		Object[] arr1 = {new String[]{"3874:1"}, "3874", LMS_PINCODE.ML_BLR, null};
		Object[] arr2 = {new String[]{"3867:1"}, "3875", LMS_PINCODE.ML_BLR,null};
//		Object[] arr3 = {new String[]{"3875:1"}, "3874", LMS_PINCODE.NORTH_DELHI,null};
//		Object[] arr4 = {new String[]{"3874:1"}, "3875", LMS_PINCODE.NORTH_DELHI,null};
//		Object[] arr5 = {new String[]{"3874:1"}, "3875", LMS_PINCODE.NORTH_DELHI,LMS_PINCODE.ML_BLR};
		Object[][] dataSet = new Object[][] {arr1, arr2/*, arr3, arr4, arr5*/};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createOrderInLMSReturns(ITestContext testContext)
	{
		Object[] arr1 = {"false", "false", "3913:2", "1998.00", "true","1998.00","false", EnumSCM.NORMAL};
		Object[] arr2 = {"false", "false", "3913:1, 3866:1", "1998.00","true", "1998.00","false", EnumSCM.NORMAL};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] generateTrackingNumberManual(ITestContext testContext)
	{
		Object[] arr1 = {"BD", "COD","","11992288312", "921", "Tracking number has been retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getOrder(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.DL,EnumSCM.NORMAL,"833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.RTO, EnumSCM.NORMAL,"833","ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PK, EnumSCM.NORMAL,"833","ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.SH, EnumSCM.NORMAL,"833","ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.DL,EnumSCM.EXPRESS,"833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.DL,EnumSCM.SDD,"833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getMLTrackingDetails(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.DELIVERED,EnumSCM.NORMAL, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.DELIVERED, EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.FAILED_DELIVERY, EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.LOST,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.RTO_CONFIRMED,EnumSCM.NORMAL,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getMLOrder(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.DELIVERED,EnumSCM.NORMAL, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.DELIVERED, EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.EXPECTED_IN_DC, EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.FAILED_DELIVERY, EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.LOST,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.RTO_CONFIRMED,EnumSCM.NORMAL,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}


	@DataProvider
	public static Object [][] getOrderTracking(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.DL, "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.SH, "833", "ORDER(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] pinCodeServiceable(ITestContext testContext)
	{
		Object[] arr1 = {"ML",LMS_PINCODE.ML_BLR,"COD", "843", "Pincode is serviceable", EnumSCM.SUCCESS};
		Object[] arr2 = {"ML",LMS_PINCODE.ML_BLR,"NON_COD", "843", "Pincode is serviceable", EnumSCM.SUCCESS};
		Object[] arr3 = {"ML","110001","COD", "843", "Pincode is serviceable", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] networkSearch(ITestContext testContext)
	{
		Object[] arr1 = {"1","5","WH","DC", "1042", "Network(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"28","5","WH","DC", "1042", "Network(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr3 = {"36","5","WH","DC", "1042", "Network(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}
	
	@DataProvider
	public static Object [][] reassignCourierInLMS(ITestContext testContext)
	{ //String status, String pincode,String from, String to, String shippingMethod, boolean isTod, String paymentMode, boolean isMultiseller, String statusType
		Object[] arr1 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "DE", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.XPRESS, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.SDD, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, true, "COD", true, EnumSCM.ERROR}; // TOD is only served by ML so throw ERROR
		Object[] arr6 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "CC", false, EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "IP", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "IP", EnumSCM.NORMAL, false, "COD", true, EnumSCM.ERROR};// Multiseller to single seller support
		Object[] arr9 = {EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr10 = {EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "COD", false, EnumSCM.ERROR};
		Object[] arr11 = {EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "COD", false, EnumSCM.ERROR};
		Object[] arr12 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "EK", "ML", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr13 = {EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "EK", EnumSCM.NORMAL, false, "COD", false, EnumSCM.ERROR};
		Object[] arr14 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "DE", "ML", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr15 = {EnumSCM.IS, LMS_PINCODE.ML_BLR, "DE", "ML", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr16 = {EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "BD", EnumSCM.NORMAL, false, "COD", false, EnumSCM.SUCCESS};
		Object[] arr17 = {EnumSCM.PK, LMS_PINCODE.JAMMU_IP, "IP", "BD", EnumSCM.NORMAL, false, "CC", false, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 50, 50);
	}
	
	@DataProvider
	public static Object [][] bulkOrderReassignmentForOnHand(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD", false, EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"on",false, EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.WP, LMS_PINCODE.PUNE_EK,"EK", "ML",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"on",false, EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.XPRESS, false,"COD",false, EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.SDD, false,"COD",false, EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.WP, LMS_PINCODE.PUNE_EK,"EK", "ML",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",false, EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.NORMAL, true,"COD",false, EnumSCM.ERROR}; // TOD can not be reassigned
		Object[] arr8 = {EnumSCM.PK, LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",false, EnumSCM.ERROR};
		Object[] arr9 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "IP",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",true, EnumSCM.ERROR};//supports_multiple_seller_shipment = `1` ~> supports_multiple_seller_shipment = `0`
		Object[] arr13 = {EnumSCM.WP, LMS_PINCODE.ML_BLR,"ML", "IP",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",false, EnumSCM.ERROR};
		Object[] arr10 = {EnumSCM.WP, LMS_PINCODE.ML_BLR, "ML", "DE",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",false, EnumSCM.SUCCESS};
		Object[] arr12 = {EnumSCM.WP, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "ML",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD",false, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr12, arr13};
		//Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] bulkOrderReassignmentForJIT(ITestContext testContext)
	{
		Object[] arr1 = {LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD", EnumSCM.SUCCESS};
		Object[] arr2 = {LMS_PINCODE.ML_BLR,"ML", "EK",EnumSCM.DELIVERY,EnumSCM.SDD, false,"COD", EnumSCM.SUCCESS};
		Object[] arr3 = {LMS_PINCODE.PUNE_EK,"EK", "ML",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD", EnumSCM.SUCCESS};
		Object[] arr5 = {LMS_PINCODE.ML_BLR,"ML", "IP",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD", EnumSCM.ERROR};
		Object[] arr7 = {LMS_PINCODE.MUMBAI_DE_RHD, "DE", "ML",EnumSCM.DELIVERY,EnumSCM.NORMAL, false,"COD", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr5, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] newAdminStatusCorrection(ITestContext testContext){
		
		// String status, String pincode, String courierCode, String warehouseId, String shippingMethod, String correctionStatus, String validateLMSStatus, String validateOMSStatus
		Object[] arr1 = { EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr2 = { EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr3 = { EnumSCM.DL, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr4 = { EnumSCM.DL, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr5 = { EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr6 = { EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, EnumSCM.LOST, EnumSCM.LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr7 = { EnumSCM.DL, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr8 = { EnumSCM.DL, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr9 = { EnumSCM.LOST, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr10 = { EnumSCM.LOST, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr11 = { EnumSCM.LOST, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr12 = { EnumSCM.LOST, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr13 = { EnumSCM.LOST, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr14 = { EnumSCM.LOST, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO_CONFIRMED, EnumSCM.RTO, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr15 = { EnumSCM.RTO, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.RTO_LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr16 = { EnumSCM.RTO, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.RTO_LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr17 = { EnumSCM.RTO, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.LOST, EnumSCM.RTO_LOST, EnumSCM.L, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr18 = { EnumSCM.RTO, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr19 = { EnumSCM.RTO, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr20 = { EnumSCM.RTO, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.DELIVERED, EnumSCM.DELIVERED, EnumSCM.D, ShipmentUpdateActivityTypeSource.Finance};
		Object[] arr21 = { EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, EnumSCM.FAILED_DELIVERY, EnumSCM.FAILED_DELIVERY, EnumSCM.S, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr22 = { EnumSCM.DL, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, EnumSCM.FAILED_DELIVERY, EnumSCM.FAILED_DELIVERY, EnumSCM.S, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr23 = { EnumSCM.DL, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, EnumSCM.FAILED_DELIVERY, EnumSCM.FAILED_DELIVERY, EnumSCM.S, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23};
		//Object[][] dataSet = new Object[][] { arr16 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider
	public static Object [][] newAdminStatusCorrectionStatusLostRto(ITestContext testContext)
	{   // String status, String pincode, String courierCode, String warehouseId, String shippingMethod, String correctionStatus, String validateLMSStatus, String validateOMSStatus
		Object[] arr1 = { EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr2 = { EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr3 = { EnumSCM.PK, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr4 = { EnumSCM.PK, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr5 = { EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr6 = { EnumSCM.IS, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr7 = { EnumSCM.IS, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr8 = { EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr9 = { EnumSCM.SH, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[] arr10 = { EnumSCM.SH, LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL, ShipmentUpdateActivityTypeSource.LogisticsAdmin};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripWithParam(ITestContext testContext)
	{
		Object[] arr1 = {"search?q=deliveryCenterId.eq:5___showAllDeliveryStaff.eq:false___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=deliveryCenterId.eq:5___deliveryStaffId.eq:3742___showAllDeliveryStaff.eq:false___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=deliveryCenterId.eq:5___showAllDeliveryStaff.eq:false___isCardEnabled.eq:true___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=deliveryCenterId.eq:1___showAllDeliveryStaff.eq:false___tripStatus.eq:OUT_FOR_DELIVERY___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=deliveryCenterId.eq:5___showAllDeliveryStaff.eq:false___tripStatus.eq:CREATED___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=deliveryCenterId.eq:5___showAllDeliveryStaff.eq:false___tripStatus.eq:COMPLETED___isInbound.eq:false&start=0&fetchSize=20&sortBy=tripDate&sortOrder=DESC", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr9 = {"getAllAvailableTripsForDC/5", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[] arr11 = {"search", "1003", "Trip(s) retrieved successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr4, arr5, arr6, arr7, arr9, arr11};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripByTripNumber(ITestContext testContext)
	{
		Object[] arr1 = {5,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripOrderByTripId(ITestContext testContext)
	{
		Object[] arr1 = {"findOrdersByTrip/TRIP_ID",  EnumSCM.SUCCESS};
//		Object[] arr2 = {"findOrdersByTrip/TRIP_ID/TRY_AND_BUY",  EnumSCM.SUCCESS};
//		Object[] arr3 = {"getTripUpdateDashboardInfo/TRIP_ID", EnumSCM.SUCCESS};
//		Object[] arr4 = {"findOrdersByTrip/TRIP_ID/PU", EnumSCM.SUCCESS};
//		Object[] arr5 = {"findExchangesByTrip/TRIP_ID", EnumSCM.SUCCESS};
//		Object[] arr6 = {"findOrdersByTrip/TRIP_ID/DL", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] {  arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20,20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripOrderByTripNumber(ITestContext testContext)
	{
		Object[] arr1 = {"findOrdersByTripNumber/TRIP_NUMBER/PU", EnumSCM.SUCCESS};
		Object[] arr2 = {"findOrdersByTripNumber/TRIP_NUMBER/DL", EnumSCM.SUCCESS};
		Object[] arr3 = {"findOrdersByTripNumber/TRIP_NUMBER/TRY_AND_BUY", EnumSCM.SUCCESS};
		Object[] arr4 = {"getTripUpdateDashboardInfoByTripNumber/TRIP_NUMBER",  EnumSCM.SUCCESS};
		Object[] arr5 = { "findExchangesByTripNumber/TRIP_NUMBER", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20,20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripReport(ITestContext testContext)
	{
		Object[] arr1 = { "getTripUpdateDashboardInfoV2/5", EnumSCM.SUCCESS};
		Object[] arr2 = { "getTripUpdateDashboardInfoV2/1", EnumSCM.SUCCESS};
		Object[] arr3 = { "getTripsDetail/5", EnumSCM.SUCCESS};
		Object[] arr4 = { "getTripsDetail/1", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripReportFinance(ITestContext testContext)
	{
		String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Object[] arr1 = { "getFinanceReport/"+dateFormat+"/"+dateFormat, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getAllIncompleteOrdersForDC(ITestContext testContext)
	{
		Object[] arr1 = {"getAllIncompleteOrdersForDC/5/PU/0/20?sortBy=zipcode&dir=DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr2 = {"getAllIncompleteOrdersForDC/9/PU/0/20?sortBy=zipcode&dir=DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr3 = {"getAllIncompleteExchangesForDC/5/0/100000?sortBy=zipcode&dir=DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr4 = {"getAllIncompleteOrdersForDC/5/TRY_AND_BUY/0/100000?sortBy=zipcode&dir=DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr5 = {"getAllIncompleteOrdersForDC/5/DL/0/99999?sortBy=id&dir=DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr6 = {"getAllIncompleteExchangesForDC/5/0/99999?sortBy=id&dir=DESC","3", EnumSCM.SUCCESS,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] createTrip(ITestContext testContext)
	{
		Object[] arr1 = { 2L, 4090L,"1001", "Trip added successfully", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider /*(parallel = true)*/
	public static Object [][] getOpenTripForStaff(ITestContext testContext)
	{
		Object[] arr1 = { "isAnyTripOpenedOnStaff/", EnumSCM.SUCCESS};
		Object[] arr2 = { "isAnyTripOpenedForToday/", EnumSCM.SUCCESS};
		Object[] arr3 = { "getOpenedTripsOnStaff/", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider /*(parallel = true)*/
	public static Object [][] isAutoCardEnabledForDCnDF(ITestContext testContext)
	{
		Object[] arr1 = { 5, 1, EnumSCM.SUCCESS};
		Object[] arr2 = { 5, 999, EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] startTrip(ITestContext testContext)
	{
		Object[] arr1 = { "464453","100", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] deleteTrip(ITestContext testContext)
	{
		Object[] arr1 = { EnumSCM.OUT_FOR_DELIVERY, EnumSCM.SUCCESS};
		Object[] arr2 = { EnumSCM.COMPLETED, EnumSCM.SUCCESS};
		Object[] arr3 = { EnumSCM.CREATED, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getReturnAddress(ITestContext testContext)
	{//String zipcode, String courierCode, long sourceWarehouseId, int statusCode, String statusMessage, String statusType
		Object[] arr1 = { "560067","ML",28L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr2 = { LMS_PINCODE.ML_BLR,"ML",1L,  2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr3 = { "110001","ML",28L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr4 = { LMS_PINCODE.ML_BLR,"ML",45L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr5 = { LMS_PINCODE.ML_BLR,"EK",28L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr6 = { "560067","EK",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr7 = { "0","EKK",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr8 = { LMS_PINCODE.MUMBAI_DE_RHD,"ML",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr9 = { LMS_PINCODE.PUNE_EK,"ML",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getRtoAddress(ITestContext testContext)
	{//String zipcode, String courierCode, long sourceWarehouseId, int statusCode, String statusMessage, String statusType
		Object[] arr1 = { "560067","ML",28L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr2 = { LMS_PINCODE.ML_BLR,"ML",1L,  2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr3 = { LMS_PINCODE.NORTH_DELHI,"ML",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr4 = { LMS_PINCODE.ML_BLR,"ML",45L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr5 = { LMS_PINCODE.ML_BLR,"EK",28L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr6 = { "560067","EK",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr7 = { "0","EKK",36L, 2001, "Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr8 = { LMS_PINCODE.MUMBAI_DE_RHD,"ML",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[] arr9 = { LMS_PINCODE.PUNE_EK,"ML",36L, 2001,"Ideal Return Warehouse Retrieved Successfully",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public static Object [][] addAndOutscanNewOrderToTrip(ITestContext testContext)
	{
		Object[] arr1 = { 468577L, "ML0000011241", "922", "Trip already started, cannot be modified", EnumSCM.ERROR};
		Object[] arr2 = { 468577L, "0000089892", "51", "Error occurred", EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider /*(parallel = true)*/
	public static Object [][] getTripResult(ITestContext testContext)
	{
		Object[] arr1 = {"getAllFailedExchanges/5/0/20/pincode/DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr2 = {"getAllFailedExchanges/5/0/20/zipcode/DESC", "3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTripResultPost(ITestContext testContext)
	{// param, deliveryCenterId, shipmentType
		Object[] arr1 = {"getAllFailedOrdersForDCBaedOnTripDate/", 5L, ShipmentType.TRY_AND_BUY,"3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr2 = {"getAllFailedOrdersForDCBaedOnTripDate/", 5L, ShipmentType.DL,"3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr3 = {"getAllFailedOrdersForDCBaedOnTripDate/", 5L, ShipmentType.PU,"3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[] arr4 = {"getAllOnHoldOrdersForDCBasedOnFilter/", 5L, ShipmentType.DL,"3", EnumSCM.SUCCESS, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getAllAvailableTripsForDC(ITestContext testContext)
	{// param, deliveryCenterId, shipmentType
		Object[] arr1 = {5,true};
		Object[] arr2 = {999,false};
		Object[][] dataSet = new Object[][] {arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}


	@DataProvider/*(parallel = true)*/
	public static Object [][] getReturnInLMS(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.REJECTED_ONHOLD_PICKUP_WITH_COURIER,EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.RETURN_IN_TRANSIT,EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PICKUP_SUCCESSFUL,EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.OUT_FOR_PICKUP,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.PICKUP_CREATED,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getOrderTrackingDetail(ITestContext testContext)
	{
		Object[] arr1 = {"ML","36",EnumSCM.SUCCESS};
		Object[] arr2 = {"ML","28",EnumSCM.SUCCESS};
		Object[] arr3 = {"ML","19",EnumSCM.SUCCESS};
		Object[] arr4 = {"DE","36",EnumSCM.SUCCESS};
		Object[] arr5 = {"EK","36",EnumSCM.SUCCESS};
		Object[] arr6 = {"DE","28",EnumSCM.SUCCESS};
		Object[] arr7 = {"EK","28",EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getOrderTrackingDetailWithData(ITestContext testContext)
	{
		Object[] arr1 = {"ML",EnumSCM.PACKED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr2 = {"ML",EnumSCM.SHIPPED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr3 = {"ML",EnumSCM.OUT_FOR_DELIVERY,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr4 = {"ML",EnumSCM.DELIVERED,EnumSCM.DL,EnumSCM.SUCCESS};
		Object[] arr5 = {"DE",EnumSCM.PACKED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr6 = {"DE",EnumSCM.SHIPPED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr7 = {"DE",EnumSCM.DELIVERED,EnumSCM.DL,EnumSCM.SUCCESS};
		Object[] arr8 = {"EK",EnumSCM.PACKED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr9 = {"EK",EnumSCM.SHIPPED,EnumSCM.FIT,EnumSCM.SUCCESS};
		Object[] arr10 = {"EK",EnumSCM.DELIVERED,EnumSCM.DL,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8,arr9, arr10};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] printShipmentLabel(ITestContext testContext)
	{
		Object[] arr1 = {"ML",5,EnumSCM.SUCCESS};//ML
		Object[] arr2 = {"ML",36,EnumSCM.SUCCESS};//DC->WH
//		Object[] arr3 = {"DE",2281,EnumSCM.SUCCESS};//RHD
//		Object[] arr4 = {"EK",2605,EnumSCM.SUCCESS};//DC 294

		Object[][] dataSet = new Object[][] { arr1, arr2/*, arr3, arr4*/};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] shipmentManifestReport(ITestContext testContext)
	{
		Object[] arr1 = {"5","ML",EnumSCM.SUCCESS};//ML
		Object[] arr2 = {"36","ML",EnumSCM.SUCCESS};//ML
		Object[] arr3 = {"2281","DE",EnumSCM.SUCCESS};//RHD/DE
		Object[] arr4 = {"1638","EK",EnumSCM.SUCCESS};//EK in_transit

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}


	@DataProvider/*(parallel = true)*/
	public static Object [][] handoverToRegionalCourier(ITestContext testContext)
	{
		Object[] arr1 = {109680132L,EnumSCM.ERROR};
		Object[] arr2 = {109680620L,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getHub(ITestContext testContext)
	{
		Object[] arr1 = {"search?fetchSize=-1&q=active.eq:true",EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=code.like:BLRAPT&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", EnumSCM.SUCCESS};
		Object[] arr3 = {"1?um=true", EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=courierCode.like:ML&start=0&fetchSize=20&sortBy=code&sortOrder=ASC", EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=courierCode.like:ML___city.like:Bangalore&start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=code.eq:DH-BLR",EnumSCM.SUCCESS};
		Object[] arr7 = {"search?start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] addHub(ITestContext testContext)
	{
		//String code, String name, String manager,String address, String city, String state, String pincode, HubType type,String statusType
		Object[] arr1 = {"DHTEST","Test_HubDH","TestManager","Test automation","Bangalore","KA","560070", HubType.DISPATCH_HUB,EnumSCM.SUCCESS};
		Object[] arr2 = {"RTTEST","Test_HubRT","TestManager","Test automation","Bangalore","KA","560070", HubType.RETURN_HUB,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}



	@DataProvider/*(parallel = true)*/
	public static Object [][] getQCPendencyPage(ITestContext testContext)
	{
		Object[] arr1 = {"findByDeliveryCenterIdAndStatus?deliveryCenterId=5&status[]=RECEIVED_IN_DC",EnumSCM.SUCCESS};
		Object[] arr2 = {"findByDeliveryCenterIdAndStatus?deliveryCenterId=5&status[]=RESHIP_TO_CUSTOMER&status[]=REJECTED_ONHOLD_PICKUP_WITH_DC", EnumSCM.SUCCESS};
		Object[] arr3 = {"findByDeliveryCenterIdAndStatus?deliveryCenterId=5&status[]=APPROVED_ONHOLD_PICKUP_WITH_DC", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getAllCourierDeatils(ITestContext testContext)
	{
		Object[] arr1 = {"true",EnumSCM.SUCCESS};
		Object[] arr2 = {"false",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] canCancelShipment(ITestContext testContext)
	{   //String lmsOrderStatus, int warehouseId, boolean isTransitionAllowed, String StatusType
		Object[] arr1 = {EnumSCM.WP,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.PACKED,36,true,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PACKED,19,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr4 = {EnumSCM.INSCANNED,19,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.INSCANNED,36,true,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr6 = {EnumSCM.ADDED_TO_MB,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr7 = {EnumSCM.ADDED_TO_MB,19,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.SHIPPED,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr9 = {EnumSCM.DELIVERED,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr10 = {EnumSCM.RTO_CONFIRMED,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr11 = {EnumSCM.LOST,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr12 = {EnumSCM.OUT_FOR_DELIVERY,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr13 = {EnumSCM.CANCELLED_IN_HUB,36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
//		Object[] arr14 = {"RECEIVED_IN_HUB",36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};
//		Object[] arr15 = {"ASSIGNED",36,false,EnumSCM.NORMAL,EnumSCM.SUCCESS};

		Object[] arr16 = {EnumSCM.WP,36,false,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr17 = {EnumSCM.PACKED,36,true,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr18 = {EnumSCM.PACKED,19,false,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr19 = {EnumSCM.INSCANNED,19,false,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr20 = {EnumSCM.INSCANNED,36,true,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr21 = {EnumSCM.ADDED_TO_MB,36,false,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr22 = {EnumSCM.ADDED_TO_MB,19,false,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr23 = {EnumSCM.SHIPPED,36,false,EnumSCM.SDD,EnumSCM.SUCCESS};

		Object[] arr24 = {EnumSCM.WP,36,false,EnumSCM.XPRESS,EnumSCM.SUCCESS};
		Object[] arr25 = {EnumSCM.PACKED,36,true,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr26 = {EnumSCM.PACKED,19,false,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr27 = {EnumSCM.INSCANNED,19,false,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr28 = {EnumSCM.INSCANNED,36,true,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr29 = {EnumSCM.ADDED_TO_MB,36,false,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr30 = {EnumSCM.ADDED_TO_MB,19,false,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr31 = {EnumSCM.SHIPPED,36,false,EnumSCM.EXPRESS,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, /*arr14,arr15,*/arr16,arr17,arr18,arr19,arr20,arr21,arr22,arr23,arr24,arr25,arr26,arr27,arr28,arr29,arr30,arr31};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 50, 50);
	}

	@DataProvider
	public static Object [][] cancelShipmentInLMS(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.PK,EnumSCM.PACKED,19,EnumSCM.NORMAL,false,"TRANSITION_NOT_ALLOWED"};
		Object[] arr2 = {EnumSCM.PK,EnumSCM.PACKED,36,EnumSCM.NORMAL,false,EnumSCM.SUCCESS};
		Object[] arr3 = {EnumSCM.PK,EnumSCM.INSCANNED,19,EnumSCM.NORMAL,false,"TRANSITION_NOT_ALLOWED"};
		Object[] arr4 = {EnumSCM.PK,EnumSCM.INSCANNED,36,EnumSCM.NORMAL,false,EnumSCM.SUCCESS};
		Object[] arr5 = {EnumSCM.PK,EnumSCM.ADDED_TO_MB,36,EnumSCM.NORMAL,false,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr6 = {EnumSCM.PK,EnumSCM.ADDED_TO_MB,19,EnumSCM.NORMAL,false,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr7 = {EnumSCM.PK,EnumSCM.PACKED,36,EnumSCM.EXPRESS,false,EnumSCM.SUCCESS};
		Object[] arr8 = {EnumSCM.PK,EnumSCM.INSCANNED,36,EnumSCM.SDD,false,EnumSCM.SUCCESS};
		Object[] arr9 = {EnumSCM.PK,EnumSCM.ADDED_TO_MB,36,EnumSCM.EXPRESS,false,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr10 = {EnumSCM.PK,EnumSCM.PACKED,36,EnumSCM.EXPRESS,true,EnumSCM.SUCCESS};
		Object[] arr11 = {EnumSCM.PK,EnumSCM.PACKED,36,EnumSCM.NORMAL,true,EnumSCM.SUCCESS};
		Object[] arr12 = {EnumSCM.PK,EnumSCM.PACKED,19,EnumSCM.NORMAL,true,"TRANSITION_NOT_ALLOWED"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] cancelShipmentInLMSNegative(ITestContext testContext)
	{   //String lmsOrderStatus, int warehouseId, boolean isTransitionAllowed, String StatusType

		Object[] arr1 = {EnumSCM.CANCELLED_IN_HUB,36,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {EnumSCM.SHIPPED,19,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr3 = {EnumSCM.SHIPPED,36,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
//		Object[] arr4 = {EnumSCM.DELIVERED,19,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr5 = {EnumSCM.DELIVERED,36,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
//		Object[] arr6 = {EnumSCM.RTO_CONFIRMED,19,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr7 = {EnumSCM.RTO_CONFIRMED,36,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
//		Object[] arr8 = {EnumSCM.OUT_FOR_DELIVERY,19,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr9 = {EnumSCM.OUT_FOR_DELIVERY,36,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr10 = {EnumSCM.ADDED_TO_MB,19,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr11 = {EnumSCM.ADDED_TO_MB,36,EnumSCM.NORMAL,"TRANSITION_NOT_CONFIGURED"};

		Object[] arr12 = {EnumSCM.CANCELLED_IN_HUB,36,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr13 = {EnumSCM.SHIPPED,36,EnumSCM.EXPRESS,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr14 = {EnumSCM.DELIVERED,36,EnumSCM.EXPRESS,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr15 = {EnumSCM.RTO_CONFIRMED,36,EnumSCM.EXPRESS,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr16 = {EnumSCM.ADDED_TO_MB,19,EnumSCM.EXPRESS,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr17 = {EnumSCM.ADDED_TO_MB,36,EnumSCM.EXPRESS,"TRANSITION_NOT_CONFIGURED"};

		Object[] arr18 = {EnumSCM.CANCELLED_IN_HUB,36,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[] arr19 = {EnumSCM.SHIPPED,36,EnumSCM.SDD,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr20 = {EnumSCM.DELIVERED,36,EnumSCM.SDD,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr21 = {EnumSCM.RTO_CONFIRMED,36,EnumSCM.SDD,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr22 = {EnumSCM.ADDED_TO_MB,19,EnumSCM.SDD,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr23 = {EnumSCM.ADDED_TO_MB,36,EnumSCM.SDD,"TRANSITION_NOT_CONFIGURED"};
		Object[] arr24 = {EnumSCM.WP,36,EnumSCM.NORMAL,"SHIPMENT_NOT_FOUND"};
		Object[] arr25 = {EnumSCM.WP,36,EnumSCM.XPRESS,"SHIPMENT_NOT_FOUND"};

		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr5, arr7, arr9, arr10, arr11, arr12,arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardDL(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,28,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr4 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};//EXPRESS
		Object[] arr5 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR}; // SDD
		Object[] arr6 = {"9911991991",36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};// non existing order
		Object[] arr7 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr8 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//add again
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8};
		//Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardWithCourierMismatch(ITestContext testContext) throws Exception {
		Object[] arr1 = {LMS_PINCODE.ML_BLR, "ML","EK",EnumSCM.ERROR};
		Object[] arr2 = {LMS_PINCODE.ML_BLR,"ML","DE",EnumSCM.ERROR};
		Object[] arr3 = {LMS_PINCODE.MUMBAI_DE_RHD,"DE","ML",EnumSCM.ERROR};
		Object[] arr4 = {LMS_PINCODE.ML_BLR,"ML","IP",EnumSCM.ERROR};
		Object[] arr5 = {LMS_PINCODE.PUNE_EK,"EK","ML",EnumSCM.ERROR};
		Object[] arr6 = {LMS_PINCODE.NORTH_DE,"DE","IP",EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardWithWarningTrueError(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//add again with wrong scenario
		Object[] arr3 = {releaseId,36,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};//add again with wrong scenario
		Object[] arr4 = {releaseId,28,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};//add again with wrong scenario
		Object[] arr5 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};//add again with wrong scenario
		Object[] arr6 = {releaseId,36,"WH",1638,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardTryAndBuy(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",true, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,28,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr4 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};//EXPRESS
		Object[] arr5 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR}; // SDD
		Object[] arr6 = {"987654300",36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};// non existing order
		Object[] arr7 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr8 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//add again
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardExpress(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.XPRESS,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,28,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};
		Object[] arr4 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr5 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr7 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[] arr8 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr7,arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardSDD(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.SDD,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,28,"WH",1,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr4 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr5 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};
		Object[] arr7 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagReverseDCtoWH(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.UNRTO, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
//		Object[] arr1 = {returnId,5,"DC",28,"WH","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.ERROR};
//		Object[] arr2 = {returnId,1,"DC",36,"WH","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.ERROR};
//		Object[] arr3 = {returnId,28,"DC",1,"WH","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.ERROR};
//		Object[] arr4 = {returnId,5,"DC",36,"WH","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr5 = {releaseId,1l,"DC",36l,"WH","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr6 = {releaseId,5l,"DC",28l,"WH","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr7 = {releaseId,5l,"DC",36l,"WH","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { /*arr1, arr2, arr3, arr4,*/ arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForward3PL(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",1,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,28,"WH",1,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr4 = {releaseId,36,"WH",1638,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};
		Object[] arr5 = {releaseId,36,"WH",1638,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR};
		Object[] arr6 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr7 = {releaseId,36,"WH",1638,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr8 = {releaseId,36,"WH",1638,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6,arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardDiffPincode(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,36,"WH",260,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardWithWarningTrue(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardAddItemToMultiBag(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardMBinDiffState(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {"CLOSED",releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {"RECEIVED",releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {"IN_TRANSIT",releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr4 = {"HANDED_OVER_TO_3PL",releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr5 = {"NEW",releaseId,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardWithDiffOrderStatus(ITestContext testContext) throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long masterBagId = lmsServiceHelper.createMasterBag(36, "WH", 5, "DC", EnumSCM.NORMAL, "ML").getEntries().get(0).getId();
		Object[] arr1 = {EnumSCM.PK,masterBagId,EnumSCM.PACKED,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {EnumSCM.DL,masterBagId,EnumSCM.DELIVERED,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {EnumSCM.RTO,masterBagId,EnumSCM.RTO_CONFIRMED,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr4 = {EnumSCM.LOST,masterBagId,EnumSCM.LOST,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr5 = {EnumSCM.OFD,masterBagId,EnumSCM.OUT_FOR_DELIVERY,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr6 = {EnumSCM.FD,masterBagId,EnumSCM.FAILED_DELIVERY,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr7 = {EnumSCM.CANCELLED_IN_HUB,masterBagId,EnumSCM.CANCELLED_IN_HUB,36,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6,arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardRHD(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL,"cod",false, true));
		String releaseId1 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.NORTH_DE, "DE", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"WH",5,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {releaseId,36,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};//EXPRESS
		Object[] arr4 = {releaseId,36,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR}; // SDD
		Object[] arr5 = {releaseId,36,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr6 = {releaseId,36,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//add again
		Object[] arr7 = {releaseId1,36,"WH",2281,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};//Try adding simple DE shipment in RHD MB
		Object[] arr8 = {releaseId1,36,"WH",1638,"DC","EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};//try adding DE shipment to EK MB
		Object[] arr9 = {releaseId1,36,"WH",85,"DC","DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//try adding DE shipment to DE MB
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6, arr7, arr8,arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardRHDWithNormalOrder(ITestContext testContext) throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long masterBagId = lmsServiceHelper.createMasterBag(28, "WH", 2281, "DC", EnumSCM.NORMAL, "DE").getEntries().get(0).getId();
		//long masterBagId, long source, long dest, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType
		Object[] arr1 = {masterBagId,28,5,LMS_PINCODE.ML_BLR,"ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {masterBagId,28,1638,LMS_PINCODE.PUNE_EK,"EK",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr3 = {masterBagId,36,85,LMS_PINCODE.NORTH_DE,"DE",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardLastMilePartner(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		
	    Object[] arr1 = {releaseId,5,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {releaseId,36,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.EXPRESS,EnumSCM.ERROR};//EXPRESS
		Object[] arr3 = {releaseId,36,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.SDD,EnumSCM.ERROR}; // SDD
		Object[] arr4 = {releaseId,5,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		//Object[] arr5 = {987655555,5,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//add again
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardDCtoDC(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,5,"DC",1,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[] arr2 = {releaseId,5,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};//EXPRESS
		Object[] arr3 = {releaseId,1,"DC",1802,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.ERROR}; // SDD
		Object[] arr4 = {releaseId,5,"DC",4,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagReturnDCtoDC(ITestContext testContext) throws Exception {
		Object[] arr1 = {"PICKUP_SUCCESSFUL",5,"DC",1,"DC","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[] arr2 = {"RETURN_REJECTED",5,"DC",1,"DC","ML",ShipmentType.PU,EnumSCM.NORMAL,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagForwardAddItemTosameBag(ITestContext testContext) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "28", EnumSCM.NORMAL,"cod",false, true));
		Object[] arr1 = {releaseId,28,"WH",5,"DC","ML",ShipmentType.DL,EnumSCM.NORMAL,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagWHtoWHPU(ITestContext testContext) throws Exception {
		Object[] arr1 = {"ML", ShipmentType.PU, EnumSCM.NORMAL, EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][]{arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] MasterBagReverseDCtoWHWithQuery(ITestContext testContext) throws Exception {
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		long masterBagId = lmsServiceHelper.createMasterBag(5, "DC", 36, "WH", EnumSCM.NORMAL, "ML").getEntries().get(0).getId();
		Object[] arr1 = {masterBagId,EnumSCM.RETURN_IN_TRANSIT,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr2 = {masterBagId,EnumSCM.PICKUP_CREATED,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr3 = {masterBagId,EnumSCM.RETURN_REJECTED,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr4 = {masterBagId,EnumSCM.REJECTED_ONHOLD_PICKUP_WITH_COURIER,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr5 = {masterBagId,EnumSCM.PICKUP_DONE_QC_PENDING,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr6 = {masterBagId,EnumSCM.RETURN_RECEIVED,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr7 = {masterBagId,EnumSCM.OUT_FOR_PICKUP,ShipmentType.PU,EnumSCM.ERROR};
		Object[] arr8 = {masterBagId,EnumSCM.ONHOLD_PICKUP_WITH_DC,ShipmentType.PU,EnumSCM.ERROR};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5,arr6,arr7,arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object[][] checkServiceability(ITestContext testContext) throws Exception {
		Object[] arr1 = {LMS_PINCODE.ML_BLR, 36,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr2 = {LMS_PINCODE.ML_BLR, 36,false, false, "on", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr3 = {LMS_PINCODE.PUNE_EK, 36,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr4 = {LMS_PINCODE.MUMBAI_DE_RHD, 36,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr5 = {LMS_PINCODE.NORTH_DELHI, 36,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr6 = {LMS_PINCODE.ML_BLR, 1,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr7 = {LMS_PINCODE.ML_BLR, 1,false, false, "on", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr8 = {LMS_PINCODE.PUNE_EK, 1,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr9 = {LMS_PINCODE.MUMBAI_DE_RHD, 1,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr10 = {LMS_PINCODE.NORTH_DELHI, 1,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr11 = {LMS_PINCODE.ML_BLR, 28,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr12 = {LMS_PINCODE.ML_BLR, 28,false, false, "on", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr13 = {LMS_PINCODE.PUNE_EK, 28,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr14 = {LMS_PINCODE.MUMBAI_DE_RHD, 28,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr15 = {LMS_PINCODE.NORTH_DELHI, 28,false, false, "cod", EnumSCM.DELIVERY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr16 = {LMS_PINCODE.ML_BLR, 28,false, false, "cod", EnumSCM.TRYNBUY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr17 = {LMS_PINCODE.ML_BLR, 36,false, false, "on", EnumSCM.TRYNBUY, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr18 = {LMS_PINCODE.ML_BLR, 36,false, false, "on", EnumSCM.PICKUP, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[] arr19 = {LMS_PINCODE.ML_BLR, 36,false, false, "on", EnumSCM.EXCHANGE, EnumSCM.NORMAL, false, false, false, false, false, false, 0, 10};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6,arr7,arr8,arr9,arr10,arr11,arr12,arr13,arr14,arr15, arr16, arr17, arr18, arr19};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] addTransporter(ITestContext testContext)
	{
		Object[] arr1 = {"AUTO1234","Automation Transporter", 6L, 5L};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] getAllTransporter(ITestContext testContext)
	{
		Object[] arr1 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=DESC"};
		Object[] arr2 = {"search?q=active.eq:true"};
		Object[][] dataSet = new Object[][] { arr1, arr2};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] assignReturntoTripNegative(ITestContext testContext)
	{
		Object[] arr1 = {EnumSCM.PICKED_UP_SUCCESSFULLY};
		Object[] arr2 = {EnumSCM.ONHOLD_PICKUP_WITH_CUSTOMER};
		Object[] arr3 = {EnumSCM.ONHOLD_PICKUP_WITH_DC};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTransporter(ITestContext testContext)
	{
		Object[] arr1 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr6 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=name.like:Test&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr3 = {"search?q=contactNumber.like:1234567890&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=mode.like:ROAD___type.like:DIRECT___active.eq:true&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=name.like:MISROUTE&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr7 = {"1",EnumSCM.SUCCESS};
		Object[] arr8 = {"search?q=name.eq:Hello",EnumSCM.SUCCESS};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getLane(ITestContext testContext)
	{
		Object[] arr1 = {"search?q=name.like:KKT-BLR___sourceHubCode.like:TH-BLR___destinationHubCode.like:TH-HYD___active.eq:true&start=0&fetchSize=20&sortBy=name&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=name.like:KKT-BLR&start=0&fetchSize=20&sortBy=name&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr3 = {"search?start=0&fetchSize=20&sortBy=name&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr4 = {"search?start=0&fetchSize=20&sortBy=name&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=name.like:MISROUTE___type.like:MISROUTE&start=0&fetchSize=20&sortBy=name&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr6 = {"5",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTmsHub(ITestContext testContext)
	{
		Object[] arr1 = {"search?start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr2 = {"search?q=code.like:TH-BLR&start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr3 = {"search?q=code.like:TH-BLR___type.like:TRANSPORT_HUB___city.like:Bangalore&start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=code.like:TH-BLR___type.like:TRANSPORT_HUB___manager.like:abhinav___city.like:Bangalore___state.like:KA___pincode.like:560068___active.eq:true&start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=pincode.like:560068&start=0&fetchSize=20&sortBy=code&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr6 = {"5",EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=code.eq:tenp",EnumSCM.SUCCESS};
		Object[] arr8 = {"search?q=name.eq:testHub",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getTransportHubPendency(ITestContext testContext)
	{
		Object[] arr1 = {"TH-BLR","ELC",EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-BLR","CGH",EnumSCM.SUCCESS};
		Object[] arr3 = {"TH-BLR","TH-DEL",EnumSCM.SUCCESS};
		Object[] arr4 = {"ALL","ALL",EnumSCM.SUCCESS};
		Object[] arr5 = {"TH-BLR","ALL",EnumSCM.SUCCESS};
		Object[] arr6 = {"ALL","TH-DEL",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getMasterbagPreAlert(ITestContext testContext)
	{
		Object[] arr1 = {"TH-BLR","ELC","Rivigo",EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-BLR","CGH","Rivigo",EnumSCM.SUCCESS};
		Object[] arr3 = {"TH-BLR","TH-DEL","Rivigo",EnumSCM.SUCCESS};
		Object[] arr4 = {"ALL","ALL","ALL",EnumSCM.SUCCESS};
		Object[] arr5 = {"TH-BLR","ELC","VRL",EnumSCM.SUCCESS};
		Object[] arr6 = {"TH-BLR","ELC","ALL",EnumSCM.SUCCESS};
		Object[] arr7 = {"TH-BLR","ALL","VRL",EnumSCM.SUCCESS};
		Object[] arr8 = {"ALL","ELC","VRL",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getContainer(ITestContext testContext)
	{
		Object[] arr1 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr2 = {"search?start=0&fetchSize=20&sortBy=id&sortOrder=ASC",EnumSCM.SUCCESS};
		Object[] arr3 = {"100001",EnumSCM.SUCCESS};
		Object[] arr4 = {"search?q=status.eq:NEW&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr5 = {"search?q=status.eq:IN_TRANSIT&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr6 = {"search?q=status.eq:IN_TRANSIT___originHubCode.like:TH-BLR___destinationHubCode.like:TH-DEL&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr7 = {"search?q=status.eq:IN_TRANSIT___originHubCode.like:TH-BLR___destinationHubCode.like:TH-DEL___laneId.eq:4___transporterId.eq:1&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr8 = {"search?q=originHubCode.like:TH-BLR&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[] arr9 = {"search?q=originHubCode.like:TH-BLR___transporterId.eq:1&start=0&fetchSize=20&sortBy=id&sortOrder=DESC",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getSupportedLane(ITestContext testContext)
	{
		Object[] arr1 = {"TH-BLR","TH-HYD",EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-BLR","ELC",EnumSCM.SUCCESS};
		Object[] arr3 = {"TH-BLR","TH-JPR",EnumSCM.SUCCESS};
		Object[] arr4 = {"TH-BLR","TH-DEL",EnumSCM.SUCCESS};
		Object[] arr5 = {"TH-DEL","TH-MU",EnumSCM.SUCCESS};
		Object[] arr6 = {"TH-MU","RHD",EnumSCM.SUCCESS};
		Object[] arr7 = {"TH-DEL","null",EnumSCM.SUCCESS};
		Object[] arr8 = {"TH-MU","null",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] getSupportedTransportersForLane(ITestContext testContext)
	{
		Object[] arr1 = {"1",EnumSCM.SUCCESS};
		Object[] arr2 = {"5",EnumSCM.SUCCESS};
		Object[] arr3 = {"10",EnumSCM.SUCCESS};
		Object[] arr4 = {"800",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] addTMSTransporter(ITestContext testContext)
	{
		Object[] arr1 = {"AIR",EnumSCM.SUCCESS};
		Object[] arr2 = {"ROAD",EnumSCM.SUCCESS};
		Object[] arr3 = {"WATER",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] addLane(ITestContext testContext)
	{
		Object[] arr1 = {"TH-DEL","TH-GWH", LaneType.INTERCITY,EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-BLR","TH-KKT", LaneType.INTRACITY,EnumSCM.SUCCESS};
		Object[] arr3 = {"TH-HYD","TH-KKT", LaneType.INTERCITY,EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] addTmsHub(ITestContext testContext)
	{
		Object[] arr1 = {"560089", TMSHubType.TRANSPORT_HUB, EnumSCM.SUCCESS};
		Object[] arr2 = {"110011", TMSHubType.TRANSPORT_HUB, EnumSCM.SUCCESS};
		Object[] arr3 = {"560089", TMSHubType.LOCATION_HUB, EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider
	public static Object [][] createAndReceiveMasterBagInTMS(ITestContext testContext)
	{
		Object[] arr1 = {"DH-BLR", "ELC", "TH-BLR", "TH",EnumSCM.SUCCESS};
		Object[] arr2 = {"DH-DEL", "ELC", "TH-DEL", "TH",EnumSCM.SUCCESS};
		Object[] arr3 = {"DH-BLR", "CAR", "TH-BLR", "TH",EnumSCM.SUCCESS};
		Object[] arr4 = {"DH-BLR", "RHD", "TH-BLR", "TH",EnumSCM.SUCCESS};
		Object[] arr5 = {"DH-DEL", "CAR", "TH-BLR", "TH",EnumSCM.WARNING};
		Object[] arr6 = {"ELC", "RT-BLR", "ELC", "DC",EnumSCM.SUCCESS};
		Object[] arr7 = {"T-JPR", "RT-BLR", "T-JPR", "DC",EnumSCM.SUCCESS};
		Object[] arr8 = {"RHD", "RT-DEL", "RHD", "DC",EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] createContainerForSourceDest(ITestContext testContext)
	{
		Object[] arr1 = {"TH-BLR", "TH-DEL",EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-DEL", "TH-BLR",EnumSCM.SUCCESS};
		Object[] arr3 = {"TH-JPR", "TH-HYD",EnumSCM.SUCCESS};
		Object[] arr4 = {"TH-MU", "TH-BLR", EnumSCM.SUCCESS};
		Object[] arr5 = {"TH-BLR", "ELC", EnumSCM.SUCCESS};
		Object[] arr6 = {"ELC", "TH-BLR", EnumSCM.SUCCESS};
		Object[] arr7 = {"T-JPR", "TH-JPR", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] createContainerForMisroute(ITestContext testContext)
	{
		Object[] arr1 = {"TH-BLR", "TH-DEL",EnumSCM.SUCCESS};
		Object[] arr2 = {"TH-BLR", "ELC", EnumSCM.SUCCESS};
		Object[] arr3 = {"ELC", "T-JPR", EnumSCM.SUCCESS};
		Object[] arr4 = {"TH-JPR","CAR", EnumSCM.SUCCESS};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}

	@DataProvider/*(parallel = true)*/
	static Object [][] skus(ITestContext testContext)
	{
		Object[] data = {"3913:4000001:36",/*"3914:5100001:36","3915:6000001:36","3916:7000001:36","3917:8000001:36",
				"3918:9000001:36","3919:1000001:36","3920:1100001:36",*/"3870:1200000:28","3869:1300000:36","3868:1400000:36","3867:1500000:36","3866:1600000:36","3876:1700000:28","3875:1800000:36",
				"3874:1900000:28","3873:2000000:36","3872:2100000:36","3871:2200000:28","3881:2300000:36","3880:2400000:19","3879:2500000:36","3878:2600000:28","3877:2700000:36"};

		Object[][] dataSet = new Object[data.length][];
		for(int i=0;i<data.length;i++){
			dataSet[i] = new Object[]{data[i]};
		}
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 50, 50);
	}

	@DataProvider
	public static Object [][] hubServiceabilityUpdate(ITestContext testContext)
	{
		Object[] arr1 = {"28",LMS_PINCODE.ML_BLR,new String[] {"ML","EK","DE","IP"}};
		Object[] arr2 = {"36",LMS_PINCODE.ML_BLR,new String[] {"ML","EK","DE","IP"}};
		Object[] arr3 = {"28",LMS_PINCODE.PUNE_EK,new String[] {"EK","ML"}};
		Object[] arr4 = {"36",LMS_PINCODE.PUNE_EK,new String[] {"EK","ML"}};
		Object[] arr5 = {"28",LMS_PINCODE.MUMBAI_DE_RHD,new String[] {"DE","EK","ML"}};
		Object[] arr6 = {"36",LMS_PINCODE.MUMBAI_DE_RHD,new String[] {"DE","EK","ML"}};
		Object[] arr7 = {"28",LMS_PINCODE.NORTH_DE,new String[] {"DE"}};
		Object[] arr8 = {"36",LMS_PINCODE.NORTH_DE,new String[] {"DE"}};
		Object[] arr9 = {"28",LMS_PINCODE.NORTH_CGH,new String[] {"ML","EK","DE"}};
		Object[] arr10 = {"36",LMS_PINCODE.NORTH_CGH,new String[] {"ML","EK","DE"}};
		Object[] arr11 = {"28",LMS_PINCODE.JAMMU_IP,new String[] {"IP"}};
		Object[] arr12 = {"36",LMS_PINCODE.JAMMU_IP,new String[] {"IP"}};
		Object[] arr13 = {"28",LMS_PINCODE.ODISHA_BD,new String[] {"BD"}};
		Object[] arr14 = {"36",LMS_PINCODE.ODISHA_BD,new String[] {"BD"}};
		Object[] arr15 = {"28",LMS_PINCODE.SEC_ML,new String[] {"ML"}};
		Object[] arr16 = {"36",LMS_PINCODE.SEC_ML,new String[] {"ML"}};
		Object[] arr17 = {"28",LMS_PINCODE.KKC_ML,new String[] {"ML"}};
		Object[] arr18 = {"36",LMS_PINCODE.KKC_ML,new String[] {"ML"}};
		Object[] arr19 = {"28",LMS_PINCODE.NORTH_DELHI,new String[] {"ML"}};
		Object[] arr20 = {"36",LMS_PINCODE.NORTH_DELHI,new String[] {"ML"}};
		Object[] arr21 = {"28",LMS_PINCODE.BBN_ML,new String[] {"ML"}};
		Object[] arr22 = {"36",LMS_PINCODE.BBN_ML,new String[] {"ML"}};
		Object[] arr23 = {"28",LMS_PINCODE.JPR_ML,new String[] {"ML"}};
		Object[] arr24 = {"36",LMS_PINCODE.JPR_ML,new String[] {"ML"}};
		Object[] arr25 = {"28",LMS_PINCODE.KOTA_ML,new String[] {"ML"}};
		Object[] arr26 = {"36",LMS_PINCODE.KOTA_ML,new String[] {"ML"}};
		Object[] arr27 = {"28",LMS_PINCODE.GGN_ML,new String[] {"ML"}};
		Object[] arr28 = {"36",LMS_PINCODE.GGN_ML,new String[] {"ML"}};
		Object[] arr29 = {"28",LMS_PINCODE.GRM_ML,new String[] {"ML"}};
		Object[] arr30 = {"36",LMS_PINCODE.GRM_ML,new String[] {"ML"}};
		Object[] arr31 = {"28",LMS_PINCODE.LXR_ML,new String[] {"ML"}};
		Object[] arr32 = {"36",LMS_PINCODE.LXR_ML,new String[] {"ML"}};
		Object[] arr33 = {"28",LMS_PINCODE.ND_ML,new String[] {"ML"}};
		Object[] arr34 = {"36",LMS_PINCODE.ND_ML,new String[] {"ML"}};
		Object[] arr35 = {"28",LMS_PINCODE.DRD_ML,new String[] {"ML"}};
		Object[] arr36 = {"36",LMS_PINCODE.DRD_ML,new String[] {"ML"}};
		Object[] arr37 = {"28",LMS_PINCODE.GWH_ML,new String[] {"ML"}};
		Object[] arr38 = {"36",LMS_PINCODE.GWH_ML,new String[] {"ML"}};
		Object[][] dataSet = new Object[][] {arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12,
				arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23, arr24, arr25, arr26, arr27,
				arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36, arr37, arr38};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] createOrdersInLMSForInitialDataSetup(ITestContext testContext)
	{
		ArrayList<String> status = new ArrayList<>();
		ArrayList<String> courier = new ArrayList<>();
		ArrayList<String> wareHouses = new ArrayList<>();
		ArrayList<String> paymentModes = new ArrayList<>();
		ArrayList<String> shippingMethod = new ArrayList<>();
		ArrayList<Boolean> tryNBuy = new ArrayList<>();

		status.add(EnumSCM.WP);
		status.add(EnumSCM.PK);
		status.add(EnumSCM.IS);
		status.add(EnumSCM.ADDED_TO_MB);
		status.add(EnumSCM.DL);
		status.add(EnumSCM.UNRTO);
		status.add(EnumSCM.CANCELLED_IN_HUB);
		status.add(EnumSCM.LOST);
		status.add(EnumSCM.FD);
		status.add(EnumSCM.OFD);
		status.add(EnumSCM.RECEIVE_IN_DC);

		courier.add("MLCGH");
		courier.add("MLBLR");
		courier.add("EK");
		courier.add("DE");
		courier.add("DERHD");
		/*courier.add("BD");
		courier.add("IP");*/

		wareHouses.add("19");
		wareHouses.add("28");
		wareHouses.add("36");

		paymentModes.add("CC");
		paymentModes.add("COD");

		shippingMethod.add(EnumSCM.NORMAL);
		shippingMethod.add(EnumSCM.XPRESS);
		shippingMethod.add(EnumSCM.SDD);

		tryNBuy.add(true);
		tryNBuy.add(false);

		DataOrcUtil combinations = new DataOrcUtil(status, courier, wareHouses, paymentModes, shippingMethod, tryNBuy);
		return combinations.Explode();
	}

	@DataProvider/*(parallel = true)*/
	public static Object [][] createMBInLMSForInitialDataSetup(ITestContext testContext)
	{ // source, sourceType, dest, destType, shippingMethod, courierCode
		Object[] arr1 = {36,"WH",5,"DC","NORMAL","ML"};
		Object[] arr2 = {36,"WH",5,"DC","EXPRESS","ML"};
		Object[] arr3 = {36,"WH",5,"DC","SDD","ML"};
		Object[] arr4 = {36,"WH",42,"DC","NORMAL","ML"};
		Object[] arr5 = {36,"WH",2281,"DC","NORMAL","ML"};
		Object[] arr6 = {36,"WH",2605,"DC","NORMAL","EK"};
		Object[] arr7 = {36,"WH",2281,"DC","NORMAL","DE"};

		Object[] arr8 = {28,"WH",5,"DC","NORMAL","ML"};
		Object[] arr9 = {28,"WH",5,"DC","EXPRESS","ML"};
		Object[] arr10 = {28,"WH",5,"DC","SDD","ML"};
		Object[] arr11 = {28,"WH",42,"DC","NORMAL","ML"};
		Object[] arr12 = {28,"WH",2281,"DC","NORMAL","ML"};
		Object[] arr13 = {28,"WH",2605,"DC","NORMAL","EK"};
		Object[] arr14 = {28,"WH",2281,"DC","NORMAL","DE"};

		Object[] arr15 = {5,"DC",36,"WH","NORMAL","ML"};

		Object[] arr16 = {1,"DC",5,"DC","NORMAL","ML"};
		Object[] arr17 = {5,"DC",1,"DC","NORMAL","ML"};

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 30, 30);
	}
	
	@DataProvider(parallel = true)
	public static Object[][] courierDP() {

		// String courier
		Object[] arr1 = { "ML" };
//		Object[] arr2 = { "EK" };
//		Object[] arr3 = { "DE" };
//		Object[] arr4 = { "IP" };
//		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		Object[][] dataSet = new Object[][] { arr1};

		return dataSet;
	}
}
