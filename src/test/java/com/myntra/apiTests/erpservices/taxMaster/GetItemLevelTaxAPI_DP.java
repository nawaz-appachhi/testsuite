package com.myntra.apiTests.erpservices.taxMaster;

import com.myntra.apiTests.erpservices.oms.TaxMasterServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.taxmaster.client.entry.request.OmsAdditionalChargesTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsDiscountEntry;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16553 on 06/04/17.
 */
public class GetItemLevelTaxAPI_DP {
	@DataProvider
	public static Object[][] itemLevelTaxCouponLessThan20DP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		long sellerId = 21;
		Date orderDate = new Date();

		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discount10Perct = (unitMrp * 10) / 100.0;
		Double discount17Perct = (unitMrp * 17) / 100.0;
		Double discount18Perct = (unitMrp * 18) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount17Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount18Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] itemLevelTaxOtherSellerDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		long sellerId = 21;
		Date orderDate = new Date();

		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discount10Perct = (unitMrp * 10) / 100.0;
		Double discount17Perct = (unitMrp * 17) / 100.0;
		Double discount23Perct = (unitMrp * 23) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount17Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);
		sellerId = 19;
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount23Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);
		sellerId = 25;
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] itemLevelTaxCouponGreaterThan20DP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long sellerId = 21;
		Date orderDate = new Date();
		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discount19Perct = (unitMrp * 19) / 100.0;
		Double discount20Perct = (unitMrp * 20) / 100.0;
		Double discount30Perct = (unitMrp * 30) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount19Perct);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount20Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount30Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] itemLevelTaxNegativeDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discountLessThan20 = (unitMrp * 10) / 100.0;
		Double discountGreaterThan20 = (unitMrp * 30) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discountLessThan20);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		long sellerId = 21;
		Date orderDate = new Date();

		list.add(new Object[] { null, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, (Long) null, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, destinationPincode, (Long) null, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, null, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, null, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, (Double) null,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, null, orderDate });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, null });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] bulkItemLevelTaxDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		long sellerId = 21;
		Date orderDate = new Date();
		long entityId = 123456789L;
		String entityType = "OrderLine";
		Boolean vatRecoveredFromCustomer = true;
		
		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discount10Perct = (unitMrp * 10) / 100.0;
		Double discount17Perct = (unitMrp * 17) / 100.0;
		Double discount18Perct = (unitMrp * 18) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] bulkItemLevelTaxNegativeDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();

		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 36;
		String courierCode = "ML";
		int quantity = 1;
		Double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discountLessThan20 = (unitMrp * 10) / 100.0;
		Double discountGreaterThan20 = (unitMrp * 30) / 100.0;
		Long entityId = 123456789L;
		String entityType = "OrderLine";
		Boolean vatRecoveredFromCustomer = true;
		
		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discountLessThan20);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		long sellerId = 21;
		Date orderDate = new Date();

		list.add(new Object[] { null, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, (Long) null, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, (Long) null, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, null, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, null, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, (Double) null,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, null, orderDate, entityId,
				entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, null, entityId, entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, null, null, entityType,vatRecoveredFromCustomer });
		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, null, entityId, null,vatRecoveredFromCustomer });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	/**
	 * @param styleId
	 * @return
	 */
	public static Double getUnitPriceForStyle(long styleId) {
		String query = "";
		HashMap<String, Object> productStyle = (HashMap<String, Object>) DBUtilities
				.exSelectQueryForSingleRecord("select * from mk_product_style where id =" + styleId, "myntra");
		Double unitPrice = (Double) productStyle.get("price");
		return unitPrice;

	}

	@DataProvider
	public static Object[][] getBulkTaxCustomerGSTDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);

		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		list.add(new Object[] { inputList,4,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] getBulkTaxCustomerGSTDifferentInputDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);
		HashMap<String, String> hmPositive2 = getMapValue("312", "10090.00", "ML", "HAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_61062010,
				date);
		HashMap<String, String> hmPositive3 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);
		HashMap<String, String> hmPositive4 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_94032090,
				date);
		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive1);
		inputList.add(hmPositive2);
		inputList.add(hmPositive3);
		inputList.add(hmPositive4);
		list.add(new Object[] { inputList,4,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] getBulkTaxCustomerGSTForThresHoldLimitDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);

		List<HashMap<String, String>> inputList1 = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> inputList2 = new ArrayList<HashMap<String, String>>();
		int thresholdLimit = 250;
		for (int i = 0; i < thresholdLimit; i++) {// Adding List till
			// thresholdLimit
			inputList1.add(hmPositive1);
			inputList2.add(hmPositive1);
		}

		for (int i = 0; i < 10; i++) {// Adding moreThan threasHold Limit
			inputList2.add(hmPositive1);
		}

		list.add(new Object[] { inputList1, false,250,0 });
		list.add(new Object[] { inputList2, true,0,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] getBulkTaxCustomerGSTNegativeDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmNegative1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", "", date); // Invalid
		// HsnCode
		HashMap<String, String> hmNegative2 = getMapValue("312", "", "ML", "DEL", "KAR", "khadi", "", date); // Invalid
		// MrpValue
		HashMap<String, String> hmNegative3 = getMapValue("312", "", "ML", "DEL", "KAR", "Test", "", date); // Invalid
		// materialType
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);
		HashMap<String, String> hmPositive2 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);

		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive1);
		inputList.add(hmPositive2);
		inputList.add(hmNegative1);
		inputList.add(hmNegative2);
		inputList.add(hmNegative3);
		list.add(new Object[] { inputList,2,3 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

	@DataProvider
	public static Object[][] itemLevelTaxCouponLessThan20OtherStateDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
		List<String> additionalCharges = new ArrayList<String>();
		additionalCharges.add(0, "additionalCharge:20");

		long styleId = 1541;
		long destinationPincode = 560068;
		long sourceWarehouseId = 28;
		String courierCode = "ML";
		int quantity = 1;
		long sellerId = 21;
		Date orderDate = new Date();

		double unitMrp = getUnitPriceForStyle(styleId);
		List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges = taxMasterServiceHelper
				.setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
		Double discount10Perct = (unitMrp * 10) / 100.0;
		Double discount17Perct = (unitMrp * 17) / 100.0;
		Double discount18Perct = (unitMrp * 18) / 100.0;

		OmsDiscountEntry discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
		List<OmsDiscountEntry> unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount17Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		discountEntry = taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount18Perct);
		unitLevelProratedDiscounts = new ArrayList<OmsDiscountEntry>();
		unitLevelProratedDiscounts.add(0, discountEntry);

		list.add(new Object[] { styleId, destinationPincode, sourceWarehouseId, courierCode, quantity, unitMrp,
				unitLevelProratedAdditionalCharges, unitLevelProratedDiscounts, sellerId, orderDate });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getBulkTaxCessDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);

		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		inputList.add(hmPositive);
		list.add(new Object[] { inputList,4,0,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getBulkTaxCessDifferentInputDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "herbal", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);
		HashMap<String, String> hmPositive2 = getMapValue("312", "10090.00", "ML", "HAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_61062010,
				date);
		HashMap<String, String> hmPositive3 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345678,
				date);
		HashMap<String, String> hmPositive4 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_94032090,
				date);
		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive1);
		inputList.add(hmPositive2);
		inputList.add(hmPositive3);
		inputList.add(hmPositive4);
		list.add(new Object[] { inputList,4,0,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getBulkTaxCessNegativeDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmNegative1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", "", date); // Invalid  HsnCode

		HashMap<String, String> hmNegative2 = getMapValue("312", "", "ML", "DEL", "KAR", "khadi", "", date); // Invalid MrpValue

		HashMap<String, String> hmNegative3 = getMapValue("312", "", "ML", "DEL", "KAR", "Test", "", date); // Invalid materialType
		
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_61062011, //Cess is expire
				date);
		HashMap<String, String> hmPositive2 = getMapValue("312", "10090.00", "ML", "KAR", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345679,
				date);

		List<HashMap<String, String>> inputList = new ArrayList<HashMap<String, String>>();
		inputList.add(hmPositive1);
		inputList.add(hmPositive2);
		inputList.add(hmNegative1);
		inputList.add(hmNegative2);
		inputList.add(hmNegative3);
		list.add(new Object[] { inputList,1,1,3 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getBulkTaxCessForThresHoldLimitDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		HashMap<String, String> hmPositive1 = getMapValue("312", "10090.00", "ML", "DEL", "KAR", "khadi", OMSTCConstants.hsnCodes.hsnCode_12345679,
				date);

		List<HashMap<String, String>> inputList1 = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> inputList2 = new ArrayList<HashMap<String, String>>();
		int thresholdLimit = 250;
		for (int i = 0; i < thresholdLimit; i++) {// Adding List till
			// thresholdLimit
			inputList1.add(hmPositive1);
			inputList2.add(hmPositive1);
		}

		for (int i = 0; i < 10; i++) {// Adding moreThan threasHold Limit
			inputList2.add(hmPositive1);
		}

		list.add(new Object[] { inputList1, false,250,0,0 });
		list.add(new Object[] { inputList2, true,0,0,0 });

		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getTaxCessPositiveDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();

		Double cessValue1 = getCessTaxValue(OMSTCConstants.hsnCodes.hsnCode_12345678,"herbal",800.0,date);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_12345678,"herbal",800.0,date,cessValue1,true });

		Double cessValue2 = getCessTaxValue(OMSTCConstants.hsnCodes.hsnCode_12345678,null,800.0,date);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_12345678,null,800.0,date,cessValue2,true });
		
		Double cessValue3 = getCessTaxValue(OMSTCConstants.hsnCodes.hsnCode_94032090,null,400.0,date);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_94032090,null,400.0,date,cessValue3,true });
		
		Double cessValue4 = getCessTaxValue(OMSTCConstants.hsnCodes.hsnCode_Invalid,null,400.0,date);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_Invalid,null,400.0,date,cessValue4,false });
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
		@DataProvider
	public static Object[][] getTaxCessNegativeDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		
		list.add(new Object[] {null,"herbal",800.0,date });//pass hsnCode as NULL

		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_12345678,"herbal",null,date });//Pass mrpValue as null
		
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_94032090,"herbal",400.0,null }); //Pass date as null
		
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
	public static Object[][] getAllTaxCessDP(ITestContext testContext) throws IOException {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date date = new Date();
		
		ArrayList<Double> expectedCessValues1 = getAllCessValuesListUsingInputs(OMSTCConstants.hsnCodes.hsnCode_12345678,"herbal",null,null);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_12345678,"herbal",null,null,true,expectedCessValues1 });
		
		ArrayList<Double> expectedCessValues2 = getAllCessValuesListUsingInputs(OMSTCConstants.hsnCodes.hsnCode_12345678,"silk",null,null);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_12345678,"silk",null,null,true,expectedCessValues2 });

		ArrayList<Double> expectedCessValues3 = getAllCessValuesListUsingInputs(OMSTCConstants.hsnCodes.hsnCode_94032090,"khadi",400.0,date);
		list.add(new Object[] {OMSTCConstants.hsnCodes.hsnCode_94032090,"khadi",400.0,date,true,expectedCessValues3 });
		
		ArrayList<Double> expectedCessValues4 = getAllCessValuesListUsingInputs(OMSTCConstants.hsnCodes.hsnCode_Invalid,null,400.0,date);
		list.add(new Object[] {"61052",null,400.0,date,false,expectedCessValues4 });//hsnCode is not present
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	






	/**
	 * Return HasMap for input values
	 * @param articleId
	 * @param mrpValue
	 * @param courierCode
	 * @param destStateCode
	 * @param sourceStateCode
	 * @param material
	 * @param hsnCode
	 * @param date
	 * @return
	 */
	public static HashMap<String, String> getMapValue(String articleId, String mrpValue, String courierCode,
			String destStateCode, String sourceStateCode, String material, String hsnCode, Date date) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("articleId", articleId);
		hm.put("mrpValue", mrpValue);
		hm.put("courierCode", courierCode);
		hm.put("destStateCode", destStateCode);
		hm.put("sourceStateCode", sourceStateCode);
		hm.put("material", material);
		hm.put("hsnCode", hsnCode);
		hm.put("date", "" + date);
		return hm;

	}
	
	/**
	 * Get CessTax Value from hsncode
	 * @param hsnCode
	 * @param material
	 * @param mrpValue
	 * @param date
	 * @return
	 */
	public static Double getCessTaxValue(String hsnCode, String material, Double mrpValue, Date date){
		HashMap<String, Object> row = null;
        List resultSet = null;
        String materialSubString = "";
        String mrpValueSubString = "";
        String dateSubString = "";
        Double cessValue = null;
        
        if(material!=null){
        	 materialSubString = " and (material='"+material+"')";
        }else{
        	 materialSubString = " and  (material is null)";
        }
        
        if(mrpValue!=null){
        	mrpValueSubString = " and (mrp_lower_bound < "+mrpValue+" or mrp_lower_bound is null) and (mrp_upper_bound > "+mrpValue+" or mrp_upper_bound is null)";
        }
        if(date!=null){
        	dateSubString = " and (date_upper_bound >'"+date+"' or date_upper_bound is null)";
       }
		String query = "select * from `cess_rule_system` where hsn_code='"+hsnCode+"'"+materialSubString+ mrpValueSubString
				+ dateSubString +";";
		
		resultSet = DBUtilities.exSelectQuery(query, "myntra_taxmaster");
		if(resultSet==null){
			return null;
		}

		row = (HashMap<String, Object>) resultSet.get(0);
		cessValue = Double.parseDouble(row.get("cess").toString());

		return cessValue;

	}
	

	
	/**
	 * Get All matching Cess Tax from HsnCode
	 * @param hsnCode
	 * @param material
	 * @param mrpValue
	 * @param date
	 * @return
	 */
	public static ArrayList<Double> getAllCessValuesListUsingInputs(String hsnCode, String material, Double mrpValue, Date date){
        HashMap<String, Object> row = null;
        List resultSet = null;
        ArrayList<Double> expectedOutPutList = new ArrayList<>();
        String materialSubString = "";
        String mrpValueSubString = "";
        String dateSubString = "";
        
        if(material!=null){
        	 materialSubString = " and (material='"+material+"' or material is null)";
        }
        if(mrpValue!=null){
        	mrpValueSubString = " and (mrp_lower_bound < "+mrpValue+" or mrp_lower_bound is null) and (mrp_upper_bound > "+mrpValue+" or mrp_upper_bound is null)";
        }
        if(date!=null){
        	dateSubString = " and (date_upper_bound >'"+date+"' or date_upper_bound is null)";
       }
		String query = "select * from `cess_rule_system` where hsn_code='"+hsnCode+"'"+materialSubString+ mrpValueSubString
				+ dateSubString +";";
		
		resultSet = DBUtilities.exSelectQuery(query, "myntra_taxmaster");
		if(resultSet==null){
			return expectedOutPutList;
		}
			
		
		for(int i=0;i<resultSet.size();i++){
			row = (HashMap<String, Object>) resultSet.get(i);
			Double cessValue = Double.parseDouble(row.get("cess").toString());
			expectedOutPutList.add(cessValue);
		}
		
		return expectedOutPutList;
	}

}
