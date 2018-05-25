package com.myntra.apiTests.erpservices.wms.dp;

/**
 * @author santwana.samantray
 *
 */

import com.myntra.apiTests.erpservices.utility.PurchaseOrderUtil;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WMSPipelineDP {
	static PurchaseOrderUtil purchaseOrderUtil = new PurchaseOrderUtil();



	@DataProvider
	public static Object[][] Validate_GRN_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"NIKE060515-12",
				"barcode,poCode,lotBarcode,vendorName,inwardedCount,subTotal,taxAmount,grandTotal,lotReceivedDate,createdOn,id",
				"0", "20", "id", "DESC", "200", "707",
				"Grn(s) retrieved successfully", "SUCCESS", "0" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_IOS_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"IN03071500002",
				"id,barcode,orderType,orderStatus,description,approver,totalCount,createdOn,createdBy,approvedOn",
				"0", "10", "200", "7028", "Internal Order(s) Retrieved",
				"SUCCESS", "1", "erpadmin", "2015-07-03T15:18:51+05:30",
				"8139", "avinashg", "IN03071500002", "Mayank", "CREATED",
				"B2B_Sales", "0.0" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Invoices_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"TEST+INVOICE-1",
				"id,barcode,lot.barcode,invoiceNumber,invoiceStatus,createdOn,createdBy",
				"0", "20", "id", "DESC", "200", "7035",
				"Invoice retrieved Retrieved", "SUCCESS", "0", "shrinath", "1" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Items_API_DP(ITestContext testContext) {

		String[] arr1 = { "NIKETSRM04509", "0", "20", "id", "DESC", "200",
				"6518", "Item(s) retrieved successfully", "SUCCESS", "68",
				"erpMessageQueue", "1000135" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Orders_API_DP(ITestContext testContext) {

		String[] arr1 = { "70018739", "70018739", "0", "20", "id", "DESC",
				"200", "7071", "Order Release Retrieved", "SUCCESS", "0" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_SKUs_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"PUMABKPK00099",
				"id,code,vendorArticleNo,vendorArticleName,size,brandName,brandId,articleTypeName,articleTypeId,gtin,jitSourced,adminDisabled,enabled,remarks,createdOn,createdBy,lastModifiedOn,lastUser",
				"0", "20", "id", "DESC", "200", "6515",
				"SKU Code(s) retrieved successfully", "SUCCESS", "1", "3816",
				"PUMABKPK00099" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Rejected_Items_API_DP(
			ITestContext testContext) {

		String[] arr1 = { "100000995136", "0", "20", "id", "DESC", "200",
				"751", "Entity Retrieved", "SUCCESS", "1", "Mohit",
				"100000995136" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_RO_API_DP(ITestContext testContext) {

		String[] arr1 = { "RO0000003398", "COMPLETED", "0", "20", "id", "DESC",
				"200", "7129", "Return Order Retrieved successfully", "SUCCESS", "0", "erpadmin",
				"3354" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_RGP_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"RBA00000586",
				"id,barcode,warehouse.barcode,rgpStatus,rgpType,totalCount,rgpValue,returnedCount,returnedCount,cartonCount,remarks,issuedTo,lrAwbNumber,issuedDate,createdOn,createdBy",
				"0", "20", "id", "DESC", "200", "7055",
				"Returnable Gatepass Retrieved", "SUCCESS", "1", "erpadmin",
				"RBA00000586" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_STN_API_DP(ITestContext testContext) {

		String[] arr1 = {
				"STNBADE030715-00",
				"barcode,stnStatus,sourceWareHouse.barcode,destinationWareHouse.barcode,"
						+ "itemStatus,totalQuantity,value,quality,approver,createdOn,createdBy,approvalDate,dispatchDate,"
						+ "receivedDate,noOfCartons,remarks,id,id", "0", "20",
				"id", "DESC", "200", "7006", "STN retrieved successfully",
				"SUCCESS", "1", "erpadmin", "STNBADE030715-00" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_STNItems_API_DP(ITestContext testContext) {

		String[] arr1 = { "STNBADE270515-01",
				"stn.barcode,cartonBarcode,itemId,received", "0", "20", "id",
				"DESC", "200", "7023", "STN Item retrieved successfully",
				"SUCCESS", "2", "799900783046", "STNBADE270515-01" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Station_Bins_API_DP(
			ITestContext testContext) {

		String[] arr1 = { "C0+Consolidation",
				"section.name,binBarcode,enabled", "0", "20", "id", "DESC",
				"200", "7100", "Mappings Retrieved Successfully", "SUCCESS",
				"2614", "DEFUCO-01-A-05", "C0 Consolidation" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Purchase_Orders_API_DP(
			ITestContext testContext) {

		String[] arr1 = {
				"NIKE060515-12",
				"pi.isTermValidated,barcode,pi.barcode,pi.piType,vendor.name,warehouse.name,pi.jit,pi.isSplitPI,pi.seasonId,pi.seasonYear,pi.description,totalUnits,grandTotal,disabledItemsCount,poStatus,pi.creditBasisAsOn,pi.paymentTerms,pi.stockOrigin,pi.brandType,approvalDate,workedBy,createdOn,estimatedShipmentDate,actualShipmentDate,pi.piPrioritization,enabled,createdBy,id,id",
				"0", "20", "id", "DESC", "200", "6522",
				"Purchase Request(s) retrieved successfully", "SUCCESS", "1",
				"erpadmin", "38404" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] Validate_Purchase_Intent_API_DP(
			ITestContext testContext) {

		String[] arr1 = {
				"PINIKE060515-12",
				"id,isTermValidated,barcode,piType,vendor.name,jit,isSplitPI,seasonId,"
						+ "seasonYear,description,piStatus,creditBasisAsOn,paymentTerms,"
						+ "stockOrigin,brandType,createdOn,estimatedShipmentDate,piPrioritization,createdBy",
				"0", "20", "id", "DESC", "200", "6560",
				"Purchase Intent(s) retrieved successfully", "SUCCESS", "1",
				"erpadmin", "34484" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] AddRoINWARD_REJECTS(ITestContext testContext) {

		String[] arr1 = { "1", "1", "19", "0", "PICK_UP", "", "200", "7128",
				"Return Order Added Successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] AddRoCUSTOMER_RETURNS(ITestContext testContext) {

		String[] arr1 = { "1", "1", "19", "0", "PICK_UP", "", "200", "7128",
				"Return Order Added Successfully", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] AddRoOUTWARD_REJECTS(ITestContext testContext) {

		String[] arr1 = { "1", "1", "19", "0", "PICK_UP", "", "200", "7128",
				"Return Order Added Successfully", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] AddRoSTOCK_CORRECTION(ITestContext testContext) {

		String[] arr1 = { "1", "1", "19", "0", "PICK_UP", "", "200", "7128",
				"Return Order Added Successfully", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] AddSKU_to_STOCK_CORRECTION_RO(
			ITestContext testContext) {

		String[] arr1 = { "NIKESHSM00778", "200", "7128",
				"ReturnOrder Sku(s) Added Successfully", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 1.order cancellation
	@DataProvider
	public Object[][] orderCancellation(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "1000148", "STATUS", "Quality", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 2.accepted return to not_found
	@DataProvider
	public static Object[][] acceptedReturnToNotFound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000121", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 3.accepted return to shipped
	@DataProvider
	public static Object[][] acceptedReturnToShipped(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000122", ItemStatus.SHIPPED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 4.customer_return to stored
	@DataProvider
	public static Object[][] customer_ReturnToStored(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000123", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 5.customer_returned to not_found
	@DataProvider
	public static Object[][] customer_ReturnToNotFound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000124", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 6.detached to accepted_return
	@DataProvider
	public static Object[][] detached_to_accepted_return(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000125", ItemStatus.ACCEPTED_RETURNS, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 7.detached to processing
	@DataProvider
	public static Object[][] detached_to_processing(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000126", ItemStatus.PROCESSING, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 8.detached to stored
	@DataProvider
	public static Object[][] detached_to_stored(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000150", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 9.found to stored
	@DataProvider
	public static Object[][] found_to_stored(ITestContext testContext) {
		Object[] arr1 = { "1000127", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	// 9.found to stored
		@DataProvider
		public static Object[][] processing_to_issued_for_ops(ITestContext testContext) {
			Object[] arr1 = { "1000159", ItemStatus.ISSUED_FOR_OPS, "Q1", "200" };
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
		}
	// 10.found to transit
	@DataProvider
	public static Object[][] found_to_transit(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000128", ItemStatus.TRANSIT, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 11.issued to shipped
	@DataProvider
	public static Object[][] issued_to_shipped(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000129", ItemStatus.SHIPPED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 12.issued to not found
	@DataProvider
	public static Object[][] issued_to_notfound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000130", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 13.issued to rfo
	@DataProvider
	public static Object[][] issued_to_rfo(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000131", ItemStatus.RETURN_FROM_OPS, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 14.not_found to shrinkage
	@DataProvider
	public static Object[][] not_found_to_shrinkage(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000132", ItemStatus.SHRINKAGE, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 15.not received to stored
	@DataProvider
	public static Object[][] not_receivedto_stored(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000158", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 16.processing to not found
	@DataProvider
	public static Object[][] processingToNotfound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000134", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 17.processing to stored
	@DataProvider
	public static Object[][] processing_to_stored(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000135", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 18.rfo to not found
	@DataProvider
	public static Object[][] rfo_to_not_found(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000136", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 19.rfo to stored
	@DataProvider
	public static Object[][] rfo_to_stored(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000137", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 20.shrinkage to found
	@DataProvider
	public static Object[][] shrinkage_to_found(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000138", ItemStatus.FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 21.stored to notfound
	@DataProvider
	public static Object[][] stored_to_notfound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000139", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 22.stored to return
	@DataProvider
	public static Object[][] stored_to_return(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000140", ItemStatus.RETURNED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 23.stored to processing
	@DataProvider
	public static Object[][] stored_to_processing(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000141", ItemStatus.PROCESSING, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 24.stored to transit
	@DataProvider
	public static Object[][] stored_to_transit(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000142", ItemStatus.TRANSIT, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 25.transit to detached
	@DataProvider
	public static Object[][] transit_to_detached(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000143", ItemStatus.DETACHED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 26.transit to not found
	@DataProvider
	public static Object[][] transit_to_notfound(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000144", ItemStatus.NOT_FOUND, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 27. stored q1 to q2
	@DataProvider
	public static Object[][] storedq1toq2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000149", ItemStatus.STORED, "Q2", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 28.rfo to store q1 to q2
	@DataProvider
	public static Object[][] rfo_to_storeq1toq2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000145", ItemStatus.STORED, "Q2", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 29.rfo to rfo q1 to q2
	@DataProvider
	public static Object[][] rfo_to_rfo_q1_to_q2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000146", ItemStatus.RETURN_FROM_OPS, "Q2", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 30.found to stores q1 to q2
	@DataProvider
	public static Object[][] found_to_stores_q1_to_q2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000147", ItemStatus.STORED, "Q2", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 31. stored q2 to q1
	@DataProvider
	public static Object[][] storedq2toq1(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000155", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 32.rfo to rfo q2 to q1
	@DataProvider
	public static Object[][] rfo_to_rfo_q2_to_q1(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000157", ItemStatus.RETURN_FROM_OPS, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 33.found to stores q2 to q1
	@DataProvider
	public static Object[][] found_to_stored_q2_to_q1(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000156", ItemStatus.STORED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 34.stored to deleted
	@DataProvider
	public static Object[][] stored_to_deleted(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000151", ItemStatus.DELETED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 35.found to deleted
	@DataProvider
	public static Object[][] found_to_deleted(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000152", ItemStatus.DELETED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// 36.not_found to deleted
	@DataProvider
	public static Object[][] not_found_to_deleted(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000153", ItemStatus.DELETED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// processing to deleted
	@DataProvider
	public static Object[][] processing_to_deleted(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000154", ItemStatus.DELETED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	   //issued_for_ops to transit

	@DataProvider
	public static Object[][] issued_for_ops_to_transit(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000170", ItemStatus.TRANSIT, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	//issued_for_ops to liquidated
	@DataProvider
	public static Object[][] issued_for_ops_to_liquidated(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000171", ItemStatus.LIQUIDATED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	//issued_for_ops to returned
	@DataProvider
	public static Object[][] issued_for_ops_to_returned(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "1000172", ItemStatus.RETURNED, "Q1", "200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// add a PI
	@DataProvider
	public static Object[][] addPI(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "10173", getDate(), "200", "6558",
				"Purchase Intent added successfully", "SUCCESS", "1" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// add a PI with vendor null
	@DataProvider
	public static Object[][] addPIWithVendorNull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "", "10173", getDate(), "200", "766",
				"Vendor not found.", "ERROR", "0" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// Bulk upload sku PI with qty more than 300 per sku to enable split
	@DataProvider
	public static Object[][] bulkuploadSKUPIQty302(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "10173", getDate(), "302", "983980",
				"BLFLDRSS00047", "BFDR-1012", "L", "BFDR-1012 L", "983981",
				"BLFLDRSS00048", "BFDR-1012", "XL", "BFDR-1012 XL", "28,36,81",
				"200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// Split a PI for Less than 300 items
	@DataProvider
	public static Object[][] SplitAlreadySplittedPI(ITestContext testContext) {
		// paramOrderId, paramRespCode
		Object[] arr1 = { "34725", "200", 6046, "Pi is already splitted.",
				"ERROR", 0 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);

	}

	// Split a ALready Splitted PI
	@DataProvider
	public static Object[][] SplitPIQty2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "10173", getDate(), "2", "983980",
				"BLFLDRSS00047", "BFDR-1012", "L", "BFDR-1012 L", "983981",
				"BLFLDRSS00048", "BFDR-1012", "XL", "BFDR-1012 XL", "200",
				"6048", "Total quanity less than 300", "ERROR" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);

	}

	// Try to mark a PI Planner Approved when in ready state
	@DataProvider
	public static Object[][] SendPIForPlannerApprovalAfterReadyState(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "34732", "2197", "PLANNER_APPROVAL", "10173", "200",
				"6078", "Cannot change status from READY to PLANNER_APPROVAL",
				"ERROR" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// Try to mark a PI create when in ready state
	@DataProvider
	public static Object[][] SendPIForReadyWhenInCreateState(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "34725", "2197", "READY", "10173", "200", "6078",
				"Cannot change status from CREATED to READY", "ERROR" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// Try to mark a PO Approved in create state
	@DataProvider
	public static Object[][] SendPOToApprovedWhenInCreateState(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "38883", "2197", "28", getDate(), "APPROVED", "200",
				"6078", "Cannot change status from CREATED to APPROVED",
				"ERROR" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// add a lot
	@DataProvider
	public static Object[][] addALOT(ITestContext testContext) throws Exception {
		// paramOrderId, paramRespCode
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		Thread.sleep(50000);
		String[] arr1 = { po_id, "1", "1", "200", "713", "Lot Created",
				"SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	// add a lot
	@DataProvider
	public static Object[][] updateALotWithoutInvoiceNotVerified (
			ITestContext testContext) throws Exception {
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		Thread.sleep(50000);
		// paramOrderId, paramRespCode
		String[] arr1 = { po_id, "1", "1", "200", "713", "Lot Created",
				"SUCCESS", "STICKER_READY", "200", "6165",
				"No Verified Invoice found for given Lot.", "ERROR" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] addInvoice(ITestContext testContext) throws Exception {
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		String[] arr1 = { po_id, "1", "1", "200", "713", "Lot Created",
				"SUCCESS", "200", "7034", "Invoice Created Successfully",
				"SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] updateInvoiceVerified(ITestContext testContext) throws Exception {
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		Thread.sleep(50000);
		// paramOrderId, paramRespCode
		String[] arr1 = { po_id, "1", "1", "200", "713", "Lot Created",
				"SUCCESS", "200", "7034", "Invoice Created Successfully",
				"SUCCESS", "READY", "VERIFIED", "200", "7036",
				"Invoice updated" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] updateInvoiceInvalidtransitions(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "42463", "LRHIE05051607", "38233", "VERIFIED", "200",
				"6078", "Cannot change status from OPEN to VERIFIED" };
		String[] arr2 = { "42463", "LRHIE05051607", "38904", "OPEN", "200",
				"6078", "Cannot change status from READY to OPEN" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] markLotStickerReady(ITestContext testContext) throws Exception {
		String po_id= String.valueOf(purchaseOrderUtil.CreateOIPOInApproveStatus(false,2));
		Thread.sleep(50000);
		// paramOrderId, paramRespCode
		String[] arr1 = { po_id, "1", "1", "200", "713", "Lot Created",
				"SUCCESS", "200", "7034", "Invoice Created Successfully",
				"SUCCESS", "READY", "VERIFIED", "200", "7036",
				"Invoice updated" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] addinvoiceskus(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "38904", "1", "1", "200", "713", "Lot Created",
				"SUCCESS", "200", "7034", "Invoice Created Successfully",
				"SUCCESS", "READY", "VERIFIED", "200", "7036",
				"Invoice updated" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] GenerateItemSplitPOForTwoWarehouses(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "10173", getDate(), "151", "983978",
				"BLFLDRSS00045", "BFDR-1012", "S", "BFDR-1012 S", "983981",
				"BLFLDRSS00048", "BFDR-1012", "XL", "BFDR-1012 XL", "36,28",
				"200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] PIwithoutsplit(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = { "2197", "10173", getDate(), "1", "983980",
				"BLFLDRSS00047", "BFDR-1012", "L", "BFDR-1012 L", "983981",
				"BLFLDRSS00048", "BFDR-1012", "XL", "BFDR-1012 XL", "36",
				"200" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] inwardItemMarkQCPass(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"LUCBI12061700", "9100000086116", "C001231231", "200", "6550","Items inwarded successfully"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] inwardItemMarkQCFail(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] arr1 = {"LUCBI12061700", "9100000086116", "C001231231", "200", "6550",
				"Items inwarded successfully","rejectReason=STAIN_DIRTY&rejectReasonDescription=Stain%2Fdirty&quality=Q2"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] Reconcileiteminsamebin(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] itemarr={"1000162","1000165"};
		
		Object[] arr1 = {"BWSTGC-02-A-11",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",2};
		//Object[] arr2 = {"BWSTGC-02-A-11",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",0};

		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] Reconcileitemindifferentbin(
			ITestContext testContext) {
		String[] itemarr={"1000160","1000163","1000162","1000165"};
		Object[] arr1 = {"BWSTGC-02-A-11",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",2};
	//	Object[] arr2 = {"BWSTGC-02-A-11",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",2};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public static Object[][] ReconcileitemMarkNotfound(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] itemarr={"1000162","1000165"};
		Object[] arr1 = {"BWSTGC-02-A-17",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",4};
		//Object[] arr2 = {"BWSTGC-02-A-17",itemarr,"200",7021,"\"Reconciliation done successfully\"","SUCCESS",2};
		Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date()).toString();
		// System.out.println(date);GenerateItemSplitPOForTwoWarehouses
		return date;
	}
//"reconciliationResponse":{"status":{"statusCode":7021,"statusMessage":"\"Reconciliation done successfully\"","statusType":"SUCCESS"
}
