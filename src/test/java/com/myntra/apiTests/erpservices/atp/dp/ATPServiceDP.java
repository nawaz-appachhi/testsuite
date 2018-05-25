package com.myntra.apiTests.erpservices.atp.dp;

import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Created by abhijit.pati on 11/03/16.
 */

public class ATPServiceDP {

    private static Long vectorSellerID;

    static {
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
    }


	@DataProvider
	public static Object[][] blockinventoryMultiplesku(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",522816,ON_HAND", "5,"+vectorSellerID+",445383,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinventoryNegqty(ITestContext testContext) {
        DBUtilities.exUpdateQuery("update inventory set blocked_order_count=0 where sku_id in (445383,522816) and seller_id="+vectorSellerID, "myntra_atp");
		// paramOrderId, paramRespCode
		String[] blockentries = { "-1,"+vectorSellerID+",445383,ON_HAND", "-1,"+vectorSellerID+",522816,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySinglesku(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",522814,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySeller2(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,18,895234,ON_HAND", "5,18,895235,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySuppplyTypeON_HAND(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",579498,ON_HAND","5,"+vectorSellerID+",752692,ON_HAND","5,"+vectorSellerID+",772382,ON_HAND","5,"+vectorSellerID+",586239,ON_HAND","5,"+vectorSellerID+",579497,ON_HAND","5,"+vectorSellerID+",586238,ON_HAND","5,"+vectorSellerID+",579499,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySuppplyTypeJIT(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",818521,JUST_IN_TIME",
				"5,"+vectorSellerID+",818522,JUST_IN_TIME" };
		Object[] arr1 = { blockentries, "1", "200", 1003,
				"Inventory updated successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventoryLessThanAvailableInv(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",3857,ON_HAND", "5,"+vectorSellerID+",3858,ON_HAND"};
		Object[] arr1 = {blockentries,"1","200",1119,"Failed to block 5-quantity for skuId:3857, storeId: 1, sellerId:"+vectorSellerID+" and supplyTypeON_HAND","ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventoryQtynull(ITestContext testContext) {
		// paramOrderId, paramRespCode
        Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.ALFA);
		String[] blockentries = { "null,"+vectorSellerID+",522816,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1107,
				"Unable to reserve Inventory", "ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySellernull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,null,522816,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1107,
				"Unable to reserve Inventory", "ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventorySKu_idnull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",null,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1107,
				"Unable to reserve Inventory", "ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] blockinventoryStore_idnull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "5,"+vectorSellerID+",null,ON_HAND" };
		Object[] arr1 = { blockentries, "null", "200", 67,
				"No data to process", "ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventorySellerid1(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",112352,ON_HAND" };
		String[] blockentries1 = { vectorSellerID+",818525,JUST_IN_TIME" };
		Object[] arr1 = { blockentries, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] orderInventorySellerid_idnull(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { "null,112352,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1107,
				"No inventory found", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventorySkuid_idnull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",null,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200" ,56,
				"Error occurred while retrieving/processing data", "ERROR"  };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventoryMultipleorderInventriesEntries(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",112352,ON_HAND",vectorSellerID+",445386,ON_HAND" };
		Object[] arr1 = { blockentries, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventoryMultipleorderInventriesEntriesSupplytypeONHand(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries1 = { vectorSellerID+",112352,ON_HAND",vectorSellerID+",445384,ON_HAND"};
		String[] blockentries2 = { "18,1044943,ON_HAND","18,1044944,ON_HAND"};
		String[] blockentries3 = { "20,12094380,ON_HAND","20,12094357,ON_HAND"};

		Object[] arr1 = { blockentries1, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[] arr2 = { blockentries2, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[] arr3 = { blockentries3, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1,arr2 ,arr3};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public static Object[][] orderInventoryMultipleorderInventriesEntriesSupplytypeJIT(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",818572,JUST_IN_TIME",vectorSellerID+",818573,JUST_IN_TIME"};
		Object[] arr1 = { blockentries, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventoryMultipleorderInventriesEntriesSupplytypeONHandAndJIT(
			ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",112352,ON_HAND",vectorSellerID+",818573,JUST_IN_TIME" };
		Object[] arr1 = { blockentries, "1", "200", 1002,
				"Inventory Retreived successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] orderInventoryStore_idnull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries = { vectorSellerID+",112352,ON_HAND" };
		Object[] arr1 = { blockentries, null, "200", 67, "No data to process",
				"ERROR" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] orderInventoryWithboc(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] blockentries0 = { vectorSellerID+",275610,ON_HAND",vectorSellerID+",612416,ON_HAND"};
		String[] blockentries1 = { "18,1320551,ON_HAND","18,1320558,ON_HAND"};
		String[] blockentries2 = { "20,12094805,ON_HAND","20,12094808,ON_HAND"};
		String[] blockentries3 = { vectorSellerID+",818529,JUST_IN_TIME",vectorSellerID+",818530,JUST_IN_TIME"};
		Object[] arr1 = { blockentries0, "1", "200",  1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[] arr2 = { blockentries1, "1", "200",  1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[] arr3 = { blockentries2, "1", "200",  1002,
				"Inventory Retreived successfully", "SUCCESS" };
		Object[] arr4 = { blockentries3, "1", "200",  1002,
				"Inventory Retreived successfully", "SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
	}
	@DataProvider
	public static Object[][] portalInventoryMultipleskuid(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] skuentries = { "818525","112352"};
		Object[] arr1 = { skuentries, "1", "200", 1002, "Inventory Retreived successfully",
				"SUCCESS" };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] portalInventoryStoreidnull(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] skuentries = { "818525","112352","818526"};
		Object[] arr1 = { skuentries, "null", "200",  67, "No data to process",
				"ERROR"  };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] portalInventoryBothJITANDONHANDWithINvForBothPresent(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] skuentries = { "18902","791088","112359"};
		Object[] arr1 = { skuentries, "1","200", 1002, "Inventory Retreived successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] portalInventoryBothJITANDOnhandWithInvPresentForJIT(ITestContext testContext) {
		// paramOrderId, paramRespCode
		String[] skuentries = { "275556","275570"};
		Object[] arr1 = { skuentries, "1","200", 1002, "Inventory Retreived successfully","SUCCESS" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] portalInventoryBothJITANDOnhandWithInvPresentForOnhand(ITestContext testContext) {
			// paramOrderId, paramRespCode
			String[] skuentries = { "791089","445386"};
			Object[] arr1 = { skuentries, "1", "200", 1002, "Inventory Retreived successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] portalInventoryBothJITANDOnhandWithInvNotPresentForBoth(ITestContext testContext) {
			// paramOrderId, paramRespCode
			String[] skuentries = { "791089","445386"};
			Object[] arr1 = { skuentries, "1", "200", 1002, "Inventory Retreived successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] disabledSku(ITestContext testContext) {
			// paramOrderId, paramRespCode
			String[] skuentries = { "748033","764270"};
			Object[] arr1 = { skuentries, "1", "200", 1107, "No inventory found","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		//String availableInWhs, String inventoryOperation, String leadTime, String quantity,
		//String sellerId, String skuCode, String skuId, String storeId, String supplyType
		@DataProvider
		public static Object[][] syncInvDecrementBOC(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","DCR_BOC","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","DCR_BOC","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","DCR_BOC","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
	
		//String availableInWhs, String inventoryOperation, String leadTime, String quantity,
				//String sellerId, String skuCode, String skuId, String storeId, String supplyType
		@DataProvider
		public static Object[][] syncInvDecrementInv(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","DCR_INV","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","DCR_INV","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","DCR_INV","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] syncDcrIncrementInvAndBOC(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","DCR_INV_BOC","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","DCR_INV_BOC","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","DCR_INV_BOC","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] syncInvDisableInventory(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","DISABLE_INV","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","DISABLE_INV","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","DISABLE_INV","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] syncInvEnableInventory(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","ENABLE_INV","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","ENABLE_INV","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","ENABLE_INV","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] syncInvIncrementBOC(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","INR_BOC","2","1","21","MSMRFHSR00005","1152954","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","INR_BOC","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","INR_BOC","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] syncInvJITInvUpdate(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","JIT_INV_UPDATE","2","1","21","MRTNDRSS00005","818507","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","JIT_INV_UPDATE","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","JIT_INV_UPDATE","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		
		@DataProvider
		public static Object[][] syncInvIncrementInv(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "20","INR_INV","2","1","21","MSMRFHSR00005","818507","1","JUST_IN_TIME","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr2 = { "36","INR_INV","2","1","21","CTLNHeels00259","603116","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};
			Object[] arr3 = { "46","INR_INV","2","1","18","ALSLTRSR00460","1044944","1","ON_HAND","1003","Inventory updated successfully","SUCCESS"};

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 3, 2);
	}
		@DataProvider
		public static Object[][] getBoc(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "19","1152954","1","JUST_IN_TIME","1002","Inventory Retreived successfully","SUCCESS"};
			Object[] arr2 = { "21","1152954","1","JUST_IN_TIME","1002","Inventory Retreived successfully","SUCCESS"};
			Object[] arr3 = { "21","764269","1","ON_HAND","1002","Inventory Retreived successfully","SUCCESS"};
			Object[] arr4 = { "18","1044944","1","ON_HAND","1002","Inventory Retreived successfully","SUCCESS"};
			Object[] arr5 = { "21","1044944","1","ON_HAND","1002","Inventory Retreived successfully","SUCCESS"};
			Object[] arr6 = { "21","1152954","1","ON_HAND","1002","Inventory Retreived successfully","SUCCESS"};




		Object[][] dataSet = new Object[][] { arr1, arr2,arr3,arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 6, 2);
	}
		
		
		@DataProvider
		public static Object[][] getBocSellerNull(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "null","1152954","1","JUST_IN_TIME",404};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] getBocStoreIdNull(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "19","1152954","null","JUST_IN_TIME",404};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] getBocSkuIdNull(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "19","null","1","JUST_IN_TIME",404};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
		@DataProvider
		public static Object[][] getBocSupplyTypeNull(ITestContext testContext) {
			// paramOrderId, paramRespCode
			Object[] arr1 = { "19","1152954","1","null",404};
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
}