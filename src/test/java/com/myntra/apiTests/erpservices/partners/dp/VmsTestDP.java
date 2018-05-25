package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.erpservices.partners.VMSHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.vms.client.code.VmsSuccessCodes;

public class VmsTestDP {
	
	static VMSHelper vmsHelper=new VMSHelper();

	@DataProvider
	public static Object[][] findVendorbyId(ITestContext testContext) {
		String[] arr1 = { "966", "1605", "SUCCESS" };
		String[] arr2 = { "1010", "1605", "SUCCESS" };
		/*
		 * String [] arr3 = {"34","1036","ERROR"}; String [] arr4 =
		 * {"162","1036","ERROR"};
		 */
		String[] arr3 = { "960", "1605", "SUCCESS" };
		String[] arr4 = { "1295", "1605", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] thirdPartyExport(ITestContext testContext) {
		String[] arr1 = { "1605", "SUCCESS", "Vendor has been retrieved successfully" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] whSearch(ITestContext testContext) {
		String[] arr1 = { "966", "1611", "SUCCESS" };
		String[] arr2 = { "1010", "1611", "SUCCESS" };
		String[] arr3 = { "960", "1611", "SUCCESS" };
		String[] arr4 = { "1295", "1611", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] returnTermSearch(ITestContext testContext) {
		String[] arr1 = { "966", "1614", "SUCCESS" };
		String[] arr2 = { "1010", "1614", "SUCCESS" };
		String[] arr3 = { "960", "1614", "SUCCESS" };
		String[] arr4 = { "1295", "1614", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] categoryManagerSearch(ITestContext testContext) {
		String[] arr1 = { "966", "1617", "SUCCESS" };
		String[] arr2 = { "1010", "1617", "SUCCESS" };
		String[] arr3 = { "960", "1617", "SUCCESS" };
		String[] arr4 = { "1295", "1617", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] searchVendorBrand(ITestContext testContext) {
		String[] arr1 = { "50", "1608", "SUCCESS", "Vendor Brand has been retrieved successfully" };
		String[] arr2 = { "1", "1608", "SUCCESS", "Vendor Brand has been retrieved successfully" };
		String[] arr3 = { "15", "1608", "SUCCESS", "Vendor Brand has been retrieved successfully" };
		String[] arr4 = { "20", "1608", "SUCCESS", "Vendor Brand has been retrieved successfully" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] getVendorTerms(ITestContext testContext) {
		String[] arr1 = { "1", "1602", "SUCCESS", "Vendor Term(s) retrieved successfully" };
		String[] arr2 = { "15", "1602", "SUCCESS", "Vendor Term(s) retrieved successfully" };
		String[] arr3 = { "25", "1602", "SUCCESS", "Vendor Term(s) retrieved successfully" };
		String[] arr4 = { "50", "1602", "SUCCESS", "Vendor Term(s) retrieved successfully" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] getVendorContactInfo(ITestContext testContext) {
		String[] arr1 = { "1", "103", "SUCCESS", "Contact(s) retrieved successfully" };
		String[] arr2 = { "2", "103", "SUCCESS", "Contact(s) retrieved successfully" };
		String[] arr3 = { "4", "103", "SUCCESS", "Contact(s) retrieved successfully" };
		String[] arr4 = { "2102", "103", "SUCCESS", "Contact(s) retrieved successfully" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider(name = "addVendor")
	public static Object[][] addVendor(ITestContext testContext) {
		String[] arr1 = { "commercial_team@myntra.com", "good", "Admin", "kumarvivek@gmail.com", "true", "true"
				 };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorContact(ITestContext testContext) {
		String[] arr1 = { "1", "com.myntra.vms.service.entity.Vendor", "shubham", "gupta", "gupta", "shubham@gmai.com",
				"878778968", "shubham@gmail.com", "989679689", "shubha" };
		String[] arr2 = { "2", "com.myntra.vms.service.entity.Vendor", "shubham", "gupta", "gupta", "shubham@gmai.com",
				"878778968", "shubham@gmail.com", "989679689", "shubha"};
		String[] arr3 = { "4", "com.myntra.vms.service.entity.Vendor", "shubham", "gupta", "gupta", "shubham@gmai.com",
				"878778968", "shubham@gmail.com", "989679689", "shubha"};
		String[] arr4 = { "2102", "com.myntra.vms.service.entity.Vendor", "shubham", "gupta", "gupta",
				"shubham@gmai.com", "878778968", "shubham@gmail.com", "989679689", "shubha"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorContactNegative(ITestContext testContext) {
		String[] arr1 = { "1", "com.myntra.vms.service.entity.Vendor", "", "gupta", "gupta", "shubham@gmai.com",
				"878778968", "shubham@gmail.com", "989679689", "shubha", "151", "ERROR",
				"First Name field absent or empty" };
		String[] arr2 = { "-1", "com.myntra.vms.service.entity.Vendor", "shubham", "gupta", "gupta", "shubham@gmai.com",
				"878778968", "shubham@gmail.com", "989679689", "shubha", "152", "ERROR", "Invalid entity id" };
		String[] arr3 = { "4", "", "shubham", "gupta", "gupta", "shubham@gmai.com", "878778968", "shubham@gmail.com",
				"989679689", "shubha", "153", "ERROR", "Entity identifier name absent/empty" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	
	@DataProvider
	public static Object[][] addVendorAddress(ITestContext testContext) {
		String[] arr1 = { "White Field", "Kudlu gate", "Bangalore", "india", "1",
				"com.myntra.vms.service.entity.Vendor", "kjahsdfja", "560068", "shubham@gmail.com", "576575756",
				"shubham@gmail.com", "8785656756", "Karnataka"};
		String[] arr2 = { "White Field", "Kudlu gate", "Bangalore", "india", "2",
				"com.myntra.vms.service.entity.Vendor", "kjahsdfja", "560068", "shubham@gmail.com", "576575756",
				"shubham@gmail.com", "8785656756", "Karnataka"};
		String[] arr3 = { "White Field", "Kudlu gate", "Bangalore", "india", "4",
				"com.myntra.vms.service.entity.Vendor", "kjahsdfja", "560068", "shubham@gmail.com", "576575756",
				"shubham@gmail.com", "8785656756", "Karnataka"};
		String[] arr4 = { "White Field", "Kudlu gate", "Bangalore", "india", "2102",
				"com.myntra.vms.service.entity.Vendor", "kjahsdfja", "560068", "shubham@gmail.com", "576575756",
				"shubham@gmail.com", "8785656756", "Karnataka"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorAddressNegative(ITestContext testContext) {
		String[] arr1 = { "White Field", "Kudlu gate", "Bangalore", "india", "-1",
				"com.myntra.vms.service.entity.Vendor", "kjahsdfja", "560068", "shubham@gmail.com", "576575756",
				"shubham@gmail.com", "8785656756", "Karnatka", "152", "ERROR", "Invalid entity id" };
		String[] arr2 = { "White Field", "Kudlu gate", "Bangalore", "india", "", "com.myntra.vms.service.entity.Vendor",
				"kjahsdfja", "560068", "shubham@gmail.com", "576575756", "shubham@gmail.com", "8785656756", "Karnatka",
				"54", "ERROR", "Error occurred while inserting/processing data" };
		String[] arr3 = { "White Field", "Kudlu gate", "Bangalore", "india", "1", "", "kjahsdfja", "560068",
				"shubham@gmail.com", "576575756", "shubham@gmail.com", "8785656756", "Karnatka", "153", "ERROR",
				"Entity identifier name absent/empty" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorCategoryManager(ITestContext testContext) {
		String[] arr1 = { "true", "3", "50" };
		String[] arr2 = { "true", "5", "50"};
		String[] arr3 = { "true", "3", "100"};
		String[] arr4 = { "true", "5", "100"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorCategoryManagerNegative(ITestContext testContext) {
		String[] arr1 = { "true", "-1", "50", "1616", "SUCCESS",
				"Vendor Category Manager has been added successfully" };
		String[] arr2 = { "true", "0", "50", "1616", "SUCCESS", "Vendor Category Manager has been added successfully" };
		String[] arr3 = { "true", "10998", "50", "1616", "SUCCESS",
				"Vendor Category Manager has been added successfully" };
		String[] arr4 = { "true", "1", "-1", "1601", "ERROR", "Vendor with the given ID is not found" };
		String[] arr5 = { "true", "1", "875876", "1601", "ERROR", "Vendor with the given ID is not found" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorBrand(ITestContext testContext) {
		String[] arr1 = { "7380", "Nabeel", "true", "50" };
		String[] arr2 = { "2", "Puma", "true", "2",  };
		String[] arr3 = { "21", "Adidas", "true", "1"};
		String[] arr4 = { "30", "Action", "true", "18"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorBrandNegative(ITestContext testContext) {
		String[] arr1 = { "7380", "Nabeel", "true", "-1", "1601", "ERROR", "Vendor with the given ID is not found" };
		String[] arr2 = { "7380", "Nabeel", "true", "20017", "1601", "ERROR", "Vendor with the given ID is not found" };
		String[] arr3 = { "", "Nabeel", "true", "1", "62", "ERROR", "Bad search parameters provided" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorWarehouse(ITestContext testContext) {
		String[] arr1 = { "10", "DROP_SHIP", "CST", "8", "3"};
		String[] arr2 = { "5", "DROP_SHIP", "CST", "8", "2"};
		String[] arr3 = { "5", "DROP_SHIP", "CST", "5", "2"};
		String[] arr4 = { "8", "DROP_SHIP", "CST", "3", "3"};
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] addVendorWarehouseNegative(ITestContext testContext) {
		String[] arr1 = { "10", "DROP_SHI", "CST", "8", "3", "62", "ERROR", "Bad search parameters provided" };
		String[] arr2 = { "5", "DROP_SHIP", "CS", "8", "2", "62", "ERROR", "Bad search parameters provided" };
		String[] arr3 = { "5", "DROP_SHIP", "CST", "-1", "2", "1601", "ERROR",
				"Vendor with the given ID is not found" };
		String[] arr4 = { "8", "DROP_SHIP", "CST", "3", "-1", "1610", "SUCCESS",
				"Vendor Warehouse has been added successfully" };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] addBankDetails(ITestContext testContext) {
		String[] arr1 = { "90","Bangalore", "true" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] addAdditionalDetails(ITestContext testContext) {
		String[] arr1 = { "LIMITED_COMPANY","no" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] addKycDetails(ITestContext testContext) {
		String[] arr1 = { "GSTIN_REGISTRATION","07GMPLK1234FLKA","VENDOR","1","BILLING","ADDRESS" };
		String[] arr2 = { "GSTIN_REGISTRATION","07GMPLK1234FLKA","VENDOR","1","SHIPPING","ADDRESS" };
		Object[][] dataSet = new Object[][] { arr1,arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] createAddress(ITestContext testContext) {
		String[] arr1 = { "SHIPPING","india gate5","india gate5","Bangalore","karnataka","560048","India","8893009255","8891112109",
				"myntra51@myntra.com","myntra51@myntra.com","test10","VENDOR","false","1"};
		String[] arr2 = { "BILLING","india gate5","india gate5","Bangalore","karnataka","560045","India","8893009355","8891112119",
				"myntra51@myntra.com","myntra51@myntra.com","test10","VENDOR","false","1"};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] markVendorToReadyStatus(ITestContext testContext) {
		String[] arr1 = { "READY","test@test.com","test 2","true","true","true" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] markVendorToApprovedStatus(ITestContext testContext) {
		String[] arr1 = { "APPROVED","test@test.com","test 2","true","true","true" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] disableBrandForVendor(ITestContext testContext) {
		String[] arr1 = { "Nabeel"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getPartyIdBySourceSystemId(ITestContext testContext) {
		String[] arr1 = { "1"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getPartyIdByVendorId(ITestContext testContext) {
		String[] arr1 = { "1"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getContractsBySeller(ITestContext testContext) {
		String[] arr1 = { "14"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getContractsByBuyer(ITestContext testContext) {
		String[] arr1 = { vmsHelper.getPartner2Id()};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getAllBuyersBySeller(ITestContext testContext) {
		String[] arr1 = { "1"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	
	@DataProvider
	public static Object[][] getAllSellersByBuyer(ITestContext testContext) {
		String[] arr1 = { vmsHelper.getPartner1Id() };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getAllStoresBySeller(ITestContext testContext) {
		String[] arr1 = { vmsHelper.getPartner2IdForStore()};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getAllSellersByStore(ITestContext testContext) {
		String[] arr1 = { "10"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getTermsBySellerAndBuyer(ITestContext testContext) {
		String[] arr1 = { "2297","4033"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
	
	@DataProvider
	public static Object[][] getTermsByBuyerAndSeller(ITestContext testContext) {
		String buyer=vmsHelper.getBuyerId();
		String[] arr1 = { vmsHelper.getPartner2Id(buyer),buyer};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}
}
