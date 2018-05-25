package com.myntra.apiTests.erpservices.lms.dp;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.logistics.platform.domain.ShipmentUpdateEvent;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.myntra.lordoftherings.Toolbox;

public class CtsEklDP {
	
	@DataProvider(parallel = true)
	public static Object [][] ctsTCs(ITestContext testContext)
	{   //orderId, vendor_tracking_id, shipment_type, event, statusType, location, merchant_name, merchant_code, seller_id, Courier_name, date
		Object[] arr1 = {"","", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi","Myntra","MYN","MYN","flipkartlogistics", "now","2", "No results found", "ERROR"};
		Object[] arr2 = {"","MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi","Myntra","MYN","MYN","flipkartlogistics","now", "2", "No results found", "ERROR"};
		Object[] arr3 = {"70006853", "MYNC00087229", "", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Myntra","MYN","MYN","flipkartlogistics","now", "2", "No results found", "ERROR"};
		Object[] arr4 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Myntra","MYN","","flipkartlogistics","now","2", "No results found", "ERROR"};
		Object[] arr5 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Myntra","MYN","MYN","flipkartlogistics","now","2", "No results found", "ERROR"};
		Object[] arr6 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Myntra","","MYN","flipkartlogistics","now","2", "No results found", "ERROR"};
		Object[] arr7 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Myntra","MYN","MYN","","now","2", "No results found", "ERROR"};
		Object[] arr8 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","now","2", "No results found", "ERROR"};
		Object[] arr9 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","A:10","2", "No results found", "ERROR"};
		Object[] arr10 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","A:1","2", "No results found", "ERROR"};
		Object[] arr11 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","A:100","2", "No results found", "ERROR"};
		Object[] arr12 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:100","2", "No results found", "ERROR"};
		Object[] arr13 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr14 = {"70006853", "", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr15 = {"70006853", "MYNC00087229", "outgoing", "", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr16 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr17 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr18 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr19 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","","S:31","2", "No results found", "ERROR"};
		Object[] arr20 = {"70006853", "MYNC00087229", "outgoing", "shipment_out_for_delivery", "out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "","MYN","MYN","flipkartlogistics","S:31","2", "No results found", "ERROR"};
		Object[] arr21 = {"109840281", "ML0038506758", "OutgoingShipment", "shipment_created", "shipment_created", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Bangalore","MYN","MYN","flipkartlogistics-cod","now","3", "Success", "SUCCESS"};
		Object[] arr22 = {"109840281", "ML0038506758", "OutgoingShipment", "shipment_out_for_delivery", "shipment_out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Bangalore","MYN","MYN","flipkartlogistics-cod","now","3", "Success", "SUCCESS"};
		//That undelivered attemp working from api but not working Through automation
		//Object[] arr23 = {"109840281", "ML0038506758", "OutgoingShipment", "shipment_undelivered_attempted", "shipment_undelivered_attempted", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Bangalore","MYN","MYN","flipkartlogistics-cod","now","3", "Success", "SUCCESS"};
		Object[] arr24 = {"109840281", "ML0038506758", "OutgoingShipment", "shipment_out_for_delivery", "shipment_out_for_delivery", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Bangalore","MYN","MYN","flipkartlogistics-cod","now","3", "Success", "SUCCESS"};
		Object[] arr25 = {"109840281", "ML0038506758", "OutgoingShipment", "shipment_delivered", "shipment_delivered", "ABC_TRADING_PRIVATE_LIMITED_Palam_Vihar_Delhi", "Bangalore","MYN","MYN","flipkartlogistics-cod","now","3", "Success", "SUCCESS"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12,arr13,arr14,arr15,arr16,arr17,arr18,arr19,arr20, arr21, arr21, arr22, arr24, arr25};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 40, 40);
	}

	@DataProvider(parallel = true)
	public static Object [][] ctsNegativeCasesUsingGenericApi(ITestContext testContext) {
		//String orderId,String vendor_tracking_id, String courierCode, ShipmentUpdateEvent event, ShipmentType shipmentType,String statusType
		Object[] arr1 = {"109840281", "ML0038506758", "EK", ShipmentUpdateEvent.OUT_FOR_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr2 = {"109840281", "ML0038506758", "EK", ShipmentUpdateEvent.DELIVERED, ShipmentType.DL,"SUCCESS"};
		Object[] arr3 = {"109840281", "ML0038506758", "EK", ShipmentUpdateEvent.FAILED_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr4 = {"109840281", "ML0038506758", "EK", ShipmentUpdateEvent.FAILED_PICKUP, ShipmentType.PU,"SUCCESS"};
		Object[] arr5 = {"109840281", "ML0038506758", "DE", ShipmentUpdateEvent.OUT_FOR_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr6 = {"109840281", "ML0038506758", "DE", ShipmentUpdateEvent.DELIVERED, ShipmentType.DL,"SUCCESS"};
		Object[] arr7 = {"109840281", "ML0038506758", "DE", ShipmentUpdateEvent.FAILED_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr8 = {"109840281", "ML0038506758", "DE", ShipmentUpdateEvent.FAILED_PICKUP, ShipmentType.PU,"SUCCESS"};
		Object[] arr9 = {"109840281", "ML0038506758", "WOW", ShipmentUpdateEvent.OUT_FOR_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr10 = {"109840281", "ML0038506758", "WOW", ShipmentUpdateEvent.DELIVERED, ShipmentType.DL,"SUCCESS"};
		Object[] arr11 = {"109840281", "ML0038506758", "WOW", ShipmentUpdateEvent.FAILED_DELIVERY, ShipmentType.DL,"SUCCESS"};
		Object[] arr12 = {"109840281", "ML0038506758", "WOW", ShipmentUpdateEvent.FAILED_PICKUP, ShipmentType.PU,"SUCCESS"};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 40, 40);
	}

	@DataProvider
	public static Object [][] ctsMarkDL(ITestContext testContext) {
		//String pincode, String courierCode, String warehouse, String shippingMethod
		Object[] arr1 = {LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL};
		Object[] arr2 = {LMS_PINCODE.NORTH_DE, "DE", "36", EnumSCM.NORMAL};
		Object[] arr3 = {LMS_PINCODE.JAMMU_IP, "IP", "36", EnumSCM.NORMAL};
		Object[] arr4 = {LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL};
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 40, 40);
	}
}
